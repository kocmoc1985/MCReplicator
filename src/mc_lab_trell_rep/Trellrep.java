/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mc_lab_trell_rep;

import Logger.SimpleLoggerLight;
import SuperClasses.BufferedDBWriterSuper;
import static SuperClasses.BufferedDBWriterSuper.total_nr_recorded_entries;
import Supplementary.GP;
import Supplementary.HelpM;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import SuperClasses.MCReplicatorSuper;
import static SuperClasses.MCReplicatorSuper.MAIN_LOG;

/**
 *
 * @author KOCMOC
 */
public class Trellrep extends MCReplicatorSuper {

    private final BuffDBWriterTrell bWriterTrell = new BuffDBWriterTrell(this);

    public Trellrep() {
        if (connect_reconnect(this.getClass().getName()) == false) {
            SimpleLoggerLight.logg(MAIN_LOG, "Connection to sql_src failed, program will close");
            System.exit(0);
        }
        start_this_thread(this, this.getClass().getName());
        start_closer_thread();
    }

    public Trellrep(String dateFrom, String dateTo) {
        //
        if (connect_reconnect(this.getClass().getName()) == false) {
            SimpleLoggerLight.logg(MAIN_LOG, "Connection to sql_src failed, program will close");
            System.exit(0);
        }
        //
        try {
            main_flow_manual_export(dateFrom, dateTo);
        } catch (SQLException ex) {
            Logger.getLogger(Trellrep.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void start_closer_thread() {
        Thread closerThread = new Thread(new Closer());
        closerThread.start();
    }

    public static void main(String[] args) {
        if (GP.ERR_OUTPUT_FILE) {
            HelpM.err_output_to_file();
        }

        //===========================================
        Trellrep trellrep = new Trellrep();
//        try {
//            trellrep.main_flow();
//        } catch (SQLException ex) {
//            Logger.getLogger(Trellrep.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    private void main_flow_manual_export(String dateFrom, String dateTo) throws SQLException {
        // in deed i don't need this "function", because this module is intended to run as background service with check interval
        start_operation_complete_monitoring(this);
        BuffDBWriterTrell.total_nr_recorded_entries = 0;
        //======================
        NEW_RECORDS_FOUND = false;
//        String last_export_date = dateFrom;
//        last_export_date = HelpM.get_date_time_minus_some_time_in_ms(last_export_date, "yyyy-MM-dd HH:mm:ss", 600000);// 10 min
        //======================
        export_procedure_2(dateFrom, dateTo);
        //======================
        //
        if (NEW_RECORDS_FOUND) {
            wait_();
            String msg = " (nr rec.= " + (BufferedDBWriterSuper.total_nr_recorded_entries - 1) + " )";
            SimpleLoggerLight.logg("main_flow.log", msg);
//            write_to_update_table("main_flow", "", DBT_trell.INTERFACE_TRIGER_TABLE_NAME,
//                    "mc",
//                    "trell",
//                    bWriterTrell);
        } else {
            SimpleLoggerLight.logg_no_append("last_check.log", "-> LAST CHECK");
//            write_to_update_table(
//                    "main_flow", "last_check", DBT_trell.INTERFACE_TRIGER_TABLE_NAME,
//                    "mc",
//                    "trell",
//                    bWriterTrell);
        }
    }

    private void export_procedure_2(String dateFrom, String dateTo) throws SQLException {
        ResultSet rs;
        //
        rs = execute(sql_source, SqlQ.get_select_query_2(dateFrom, dateTo));
        int summEntries = 0;
        //
        while (rs.next()) {
            summEntries++;
        }
        //
        TrellExportManual.jLabel5.setText("To record: " + summEntries);
        //
        rs.beforeFirst();
        //
        while (rs.next()) {
            int i = 1;
            TableToCopy toCopy = new TableToCopy(
                    rs.getString(i++),
                    rs.getInt(i++),
                    rs.getString(i++),
                    rs.getString(i++),
                    rs.getString(i++),
                    rs.getString(i++),
                    rs.getInt(i++),
                    rs.getDouble(i++),
                    rs.getString(i++),
                    rs.getDouble(i++),
                    rs.getDouble(i++),
                    rs.getDouble(i++),
                    rs.getDouble(i++),
                    rs.getString(i++),
                    rs.getString(i++),
                    rs.getString(i++));
            //
//            bWriterTrell.add(toCopy); -> leads to Java heap mem. exceeded, when having to much data to export
            //
            if (bWriterTrell.execute_insert_or_update_statement(bWriterTrell.sql_dest, SqlQ.insert_into_table_to_copy(toCopy))) {
                TrellExportManual.jLabel3.setText("recorded: " + total_nr_recorded_entries);
            } else {
                BufferedDBWriterSuper.total_nr_failed_entries++;
                TrellExportManual.jLabel3.setText("failed: " + BufferedDBWriterSuper.total_nr_failed_entries);
            }
            //
            NEW_RECORDS_FOUND = true;
        }
    }

    private void main_flow() throws SQLException {
        // in deed i don't need this "function", because this module is intended to run as background service with check interval
        start_operation_complete_monitoring(this);
        BuffDBWriterTrell.total_nr_recorded_entries = 0;
        //======================
        NEW_RECORDS_FOUND = false;
        String last_export_date = get_last_export_date(SqlQ.get_latest_export_date());
        last_export_date = HelpM.get_date_time_minus_some_time_in_ms(last_export_date, "yyyy-MM-dd HH:mm:ss", 600000);// 10 min
        //======================
        export_procedure_1(last_export_date);
        //======================

        if (NEW_RECORDS_FOUND) {
            wait_();
            String msg = " (nr rec.= " + (BufferedDBWriterSuper.total_nr_recorded_entries - 1) + " )";
            SimpleLoggerLight.logg("main_flow.log", msg);
//            write_to_update_table("main_flow", "", DBT_trell.INTERFACE_TRIGER_TABLE_NAME,
//                    "mc",
//                    "trell",
//                    bWriterTrell);
        } else {
            SimpleLoggerLight.logg_no_append("last_check.log", "-> LAST CHECK");
//            write_to_update_table(
//                    "main_flow", "last_check", DBT_trell.INTERFACE_TRIGER_TABLE_NAME,
//                    "mc",
//                    "trell",
//                    bWriterTrell);
        }
    }

    private void export_procedure_1(String last_export_date) throws SQLException {
        ResultSet rs;

        rs = execute(sql_source, SqlQ.get_select_query_1(last_export_date));

        while (rs.next()) {
            int i = 1;
            TableToCopy toCopy = new TableToCopy(
                    rs.getString(i++),
                    rs.getInt(i++),
                    rs.getString(i++),
                    rs.getString(i++),
                    rs.getString(i++),
                    rs.getString(i++),
                    rs.getInt(i++),
                    rs.getDouble(i++),
                    rs.getString(i++),
                    rs.getDouble(i++),
                    rs.getDouble(i++),
                    rs.getDouble(i++),
                    rs.getDouble(i++),
                    rs.getString(i++),
                    rs.getString(i++),
                    rs.getString(i++));
            bWriterTrell.add(toCopy);
            NEW_RECORDS_FOUND = true;
        }
    }

    private String get_last_export_date(String query) {
        ResultSet rs;
        try {
            rs = bWriterTrell.execute(bWriterTrell.sql_dest, query);
            rs.next();
            return rs.getString(DBT_trell.MCTOTRELL_EXPORT_TIME);
        } catch (SQLException ex) {
            // dont need to throw this one, it's not so important
//            Logger.getLogger(MCReplicator.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }
    }

    class Closer implements Runnable {

        @Override
        public void run() {
            while (true) {
                wait__(86400000); // 24 Howrs
                SimpleLoggerLight.logg(MAIN_LOG, "Closed by Closer-Thread");
                System.exit(0); //
            }
        }

        private void wait__(int millis) {
            synchronized (this) {
                try {
                    wait(millis);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Trellrep.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                main_flow();
            } catch (SQLException ex) {
                Logger.getLogger(Trellrep.class.getName()).log(Level.SEVERE, null, ex);
            }
            int interval = Integer.parseInt(GP.CHECK_INTERVAL_MINUTES);
            wait_((int) HelpM.minutes_to_milliseconds_converter(interval));
        }
    }
}
