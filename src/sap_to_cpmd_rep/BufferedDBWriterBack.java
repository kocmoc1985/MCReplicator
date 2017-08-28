/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sap_to_cpmd_rep;

import SuperClasses.BufferedDBWriterSuper;
import DBT2.update_back;
import Logger.SimpleLoggerLight;
import Objects.ItemPrice;
import Objects.Vendor;
import Objects.VendorContact;
import SQL.QueryUpdate;
import SQL.Sql;
import Supplementary.GP;
import Supplementary.MCReplicatorIF;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.BufferedDBWriter;

/**
 *
 * @author KOCMOC
 */
public class BufferedDBWriterBack extends BufferedDBWriterSuper {

    private Sql sql_mcsap_source = new Sql();

    public BufferedDBWriterBack(MCReplicatorIF replicatorIF) {
        super(replicatorIF);
        super.connectReconnect();
        connect_reconnect_mcsap();
    }

    private void connect_reconnect_mcsap() {
        try {
            sql_mcsap_source.connect(GP.SQL_SRC_HOST, GP.SQL_SRC_PORT, GP.SQL_SRC_DB_NAME,
                    GP.SQL_SRC_USERNAME, GP.SQL_SRC_PASSWORD);
            SimpleLoggerLight.logg(MCReplicatorBack.MC_REPLCICATOR_BACK_LOG, "BufferedDBWriterBack: connection to src db " + GP.SQL_SRC_DB_NAME + " established");
        } catch (SQLException ex) {
            Logger.getLogger(BufferedDBWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void writeToDb(Object obj) {
        if (obj instanceof Vendor) {
            record_vendor_object(obj);
        } else if (obj instanceof VendorContact) {
            record_vendor_contact_object(obj);
        } else if (obj instanceof ItemPrice) {
            record_item_price_object(obj);
        } else if (obj instanceof String) {
            String query = (String) obj;

            if (query.contains("'last_check'") == false) {
                execute_insert_or_update_statement(sql_dest, update_back.table_name);
            } else {
                record_update_string_last_check(sql_dest, update_back.table_name);
            }
        }
    }

    private void record_item_price_object(Object obj) {
        ItemPrice itemPrice = (ItemPrice) obj;

        String query_exists = QueryAll.table_contains_item_price(itemPrice.getItem_code());

        boolean sql_execution_ok;

        if (table_contains(sql_dest, query_exists)) {
            sql_execution_ok = execute_insert_or_update_statement(sql_dest, QueryAll.update_item_price(itemPrice));
        } else {
            sql_execution_ok = execute_insert_or_update_statement(sql_dest, QueryAll.insert_into_item_price(itemPrice));
        }

        // The code section below is mechanism responsible
        // for the process of updating the "dateProcessed" field
        // in the table from which the data is beeing exported
        if (sql_execution_ok) {
            String q = QueryUpdate.update_dateProcessed_any_table(DBT2.item_price.tableName, itemPrice.getRow_id());
            execute(sql_mcsap_source, q);
        }
    }

    private void record_vendor_contact_object(Object obj) {
        VendorContact vendor_contact = (VendorContact) obj;

        String query_exists = QueryAll.table_contains_vendor_contact(vendor_contact.getVendor_id(), vendor_contact.getContactName());

        boolean sql_execution_ok;

        if (table_contains(sql_dest, query_exists)) {
            sql_execution_ok = execute_insert_or_update_statement(sql_dest, QueryAll.update_vendor_contact(vendor_contact));
        } else {
            sql_execution_ok = execute_insert_or_update_statement(sql_dest, QueryAll.insert_into_vendor_contact(vendor_contact));
        }

        // The code section below is mechanism responsible
        // for the process of updating the "dateProcessed" field
        // in the table from which the data is beeing exported
        if (sql_execution_ok) {
            String q = QueryUpdate.update_dateProcessed_any_table(DBT2.vendor_contact.tableName, vendor_contact.getRow_id());
            execute(sql_mcsap_source, q);
        }
    }

    private void record_vendor_object(Object obj) {
        Vendor vendor = (Vendor) obj;

        String query_exists = QueryAll.table_contains_vendor(vendor.getVendor_id());

        boolean sql_execution_ok;

        if (table_contains(sql_dest, query_exists)) {
            sql_execution_ok = execute_insert_or_update_statement(sql_dest, QueryAll.update_vendor(vendor));
        } else {
            sql_execution_ok = execute_insert_or_update_statement(sql_dest, QueryAll.insert_into_vendor(vendor));
        }

        // The code section below is mechanism responsible
        // for the process of updating the "dateProcessed" field
        // in the table from which the data is beeing exported
        if (sql_execution_ok) {
            String q = QueryUpdate.update_dateProcessed_any_table(DBT2.vendor.tableName, vendor.getRow_id());
            execute(sql_mcsap_source, q);
        }
    }
}
