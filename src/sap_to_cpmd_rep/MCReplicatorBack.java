/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sap_to_cpmd_rep;

import SuperClasses.MCReplicatorSuper;
import DBT2.update_back;
import Objects.ItemPrice;
import Objects.Vendor;
import Objects.VendorContact;
import Supplementary.GP;
import Supplementary.HelpM;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author KOCMOC
 */
public class MCReplicatorBack extends MCReplicatorSuper {

    private BufferedDBWriterBack buffDBWriter = new BufferedDBWriterBack(this);
    public static final String MC_REPLCICATOR_BACK_LOG = GP.LOG_PREFIX_MCREPLICATOR_BACK + "main.log";

    //==============
    public MCReplicatorBack() {
        connect_reconnect(this.getClass().getName());
        start_this_thread(this, this.getClass().getName());
    }

    public static void main(String[] args) {
        if (GP.ERR_OUTPUT_FILE) {
            HelpM.err_output_to_file();
        }
        //===========================================
        MCReplicatorBack get = new MCReplicatorBack();
//        try {
//            get.export_vendor_from_mcsap();
//            get.export_vendor_contact_from_mcsap();
//            get.export_item_price_from_mcsap();
//            get.main_flow();
//        } catch (SQLException ex) {
//            Logger.getLogger(MCReplicatorBack.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                main_flow();
            } catch (SQLException ex) {
                Logger.getLogger(MCReplicatorBack.class.getName()).log(Level.SEVERE, null, ex);
            }
            int interval = Integer.parseInt(GP.MC_REPLICATOR_BACK_CHECK_INTERVAL);
            wait_((int) HelpM.minutes_to_milliseconds_converter(interval));
        }
    }

    private void main_flow() throws SQLException {
//        SimpleLoggerLight1.logg(MC_REPLCICATOR_BACK_LOG, "main_flow");
        BufferedDBWriterBack.total_nr_recorded_entries = 0;
        start_operation_complete_monitoring(this);
        //============================
        NEW_RECORDS_FOUND = false;
        export_vendor_from_mcsap();
        export_vendor_contact_from_mcsap();
        export_item_price_from_mcsap();
        //============================

        if (NEW_RECORDS_FOUND) {
            wait_();
            write_to_update_table(update_back.MAIN_FLOW, "", DBT2.update_back.table_name, "mc", "mccompound", buffDBWriter);
        } else {
            write_to_update_table(update_back.MAIN_FLOW, "last_check", DBT2.update_back.table_name, "", "", buffDBWriter);
        }
    }

    private void export_item_price_from_mcsap() throws SQLException {
        ResultSet rs;

        rs = execute(sql_source, QueryAll.get_item_price_from_mcsap());


        while (rs.next()) {
            int i = 1;
            ItemPrice item_price = new ItemPrice(
                    rs.getString(i++),
                    rs.getString(i++),
                    rs.getString(i++),
                    rs.getString(i++),
                    HelpM.get_proper_date_time_same_format_on_all_computers(),
                    null,
                    "");

            buffDBWriter.add(item_price);
            NEW_RECORDS_FOUND = true;
        }
    }

    private void export_vendor_contact_from_mcsap() throws SQLException {
        ResultSet rs;

        rs = execute(sql_source, QueryAll.get_vendors_contacts_from_mcsap());


        while (rs.next()) {
            int i = 1;

            VendorContact contact = new VendorContact(
                    rs.getString(i++),
                    rs.getString(i++).replace("'", ""),
                    rs.getString(i++).replace("'", ""),
                    rs.getString(i++).replace("'", ""),
                    rs.getString(i++).replace("'", ""),
                    rs.getString(i++).replace("'", ""),
                    HelpM.get_proper_date_time_same_format_on_all_computers(),
                    null,
                    "");

            buffDBWriter.add(contact);
            NEW_RECORDS_FOUND = true;
        }
    }

    /**
     *
     * @throws SQLException
     */
    private void export_vendor_from_mcsap() throws SQLException {
        ResultSet rs;

        rs = execute(sql_source, QueryAll.get_vendors_from_mcsap());

        while (rs.next()) {
            int i = 1;

            Vendor vendor = new Vendor(
                    rs.getString(i++),
                    rs.getString(i++).replace("'", ""), // I make this "replace" beacuse som names in "vendor" table contains "'" char which causes failure when inserting to db
                    rs.getString(i++).replace("'", ""),
                    rs.getString(i++).replace("'", ""),
                    rs.getString(i++).replace("'", ""),
                    rs.getString(i++).replace("'", ""),
                    rs.getString(i++).replace("'", ""),
                    rs.getString(i++).replace("'", ""),
                    rs.getString(i++).replace("'", ""),
                    rs.getString(i++).replace("'", ""),
                    rs.getString(i++).replace("'", ""),
                    HelpM.get_proper_date_time_same_format_on_all_computers(),
                    null,
                    "");
//            System.out.println("" + i);
            buffDBWriter.add(vendor);
            NEW_RECORDS_FOUND = true;
        }
    }
}
