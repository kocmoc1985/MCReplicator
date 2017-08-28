/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SuperClasses;

import Logger.SimpleLoggerLight;
import SQL.Sql;
import Supplementary.GP;
import Supplementary.MCReplicatorIF;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.BufferedDBWriter;
import mc_lab_trell_rep.TrellExportManual;
import sap_to_cpmd_rep.QueryAll;

/**
 *
 * @author KOCMOC
 */
public class BufferedDBWriterSuper implements Runnable {
    
    private final MCReplicatorIF mCReplicator;
    public Sql sql_dest = new Sql();
    public LinkedList<Object> buffer = new LinkedList();
    //================================================
    public static int total_nr_entries_added_to_buffer;
    public static int total_nr_recorded_entries;
    public static int total_nr_failed_entries;
    private boolean RUN = true;
    //========================================= private static final String SQL_LOG_0 = GP.LOG_PREFIX_BUFF_DB_W + "sql_exceptions.log";
    private static final String SQL_LOG_0 = GP.LOG_PREFIX_BUFF_DB_W + "sql_exceptions.log";
    private static final String SQL_LOG_1 = GP.LOG_PREFIX_BUFF_DB_W + "sql_error_quiries.log";
    private static final String SQL_LOG_2 = GP.LOG_PREFIX_BUFF_DB_W + "materials_only_in_RecipeMC1.log";
    
    public BufferedDBWriterSuper(MCReplicatorIF mCReplicator) {
        this.mCReplicator = mCReplicator;
        startThread();
    }
    
    private void startThread() {
        Thread thread = new Thread(this);
        thread.setName("DBWriter-Thr");
        thread.start();
    }
    
    public boolean connectReconnect() {
        try {
            sql_dest.connect(GP.SQL_DEST_HOST, GP.SQL_DEST_PORT, GP.SQL_DEST_DB_NAME,
                    GP.SQL_DEST_USERNAME, GP.SQL_DEST_PASSWORD);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(BufferedDBWriter.class.getName()).log(Level.SEVERE, null, ex);
            sql_dest.sql_error_handling(ex);
            return false;
        }
    }
    
    public synchronized void add(Object obj) {
        total_nr_entries_added_to_buffer++;
        buffer.add(obj);
    }
    
    public void writeToDb(Object obj) {
        //
    }

    /**
     * This one is to be able to record to db when the last check was performed
     * and also shows that the program is active
     *
     * @param sql
     * @param table_name
     */
    public void record_update_string_last_check(Sql sql, String table_name) {
        String query_exists = QueryAll.table_contains_last_check(table_name);
        if (table_contains(sql, query_exists)) {
            execute_insert_or_update_statement(sql, QueryAll.update_last_check_interface_trigger(table_name));
        } else {
            execute_insert_or_update_statement(sql, QueryAll.insert_last_check_into_interface_trigger(table_name));
        }
    }
    
    public boolean execute_insert_or_update_statement(Sql sql, String query) {
        try {
            sql.execute(query);
            total_nr_recorded_entries++;
//            SimpleLoggerLight.logg_no_append("record_sql.log", "-> Last sql record");
            return true;
        } catch (SQLException ex) {
            sql.sql_error_handling(ex);
            sql_errors_logging(ex, query);
            return false;
        }
    }
    
    public void sql_errors_logging(SQLException ex, String query) {
//        System.out.println("QUERY_WHICH_CAUSED_ERR = " + query);
        if (ex.toString().contains(GP.SQL_ERR_LOGGING_PATTERN_1)) {
            SimpleLoggerLight.logg(SQL_LOG_2, query);
        } else {
            SimpleLoggerLight.logg(SQL_LOG_1, query);
            //==================================================================
            SimpleLoggerLight.logg(SQL_LOG_0, query);
            SimpleLoggerLight.logg(SQL_LOG_0, ex.toString());
            SimpleLoggerLight.logg(SQL_LOG_0, "**********************************************");
        }
    }
    
    public ResultSet execute(Sql sql, String query) {
        try {
            return sql.execute(query);
        } catch (SQLException ex) {
            SimpleLoggerLight.logg(SQL_LOG_0, query);
            SimpleLoggerLight.logg(SQL_LOG_0, ex.toString());
            SimpleLoggerLight.logg(SQL_LOG_0, "**********************************************");
            Logger.getLogger(BufferedDBWriter.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public boolean table_contains(Sql sql, String query) {
        ResultSet rs;
        try {
            rs = sql.execute(query);
            if (rs.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(BufferedDBWriter.class.getName()).log(Level.SEVERE, null, ex);
            return true;
        }
    }
    
    public void add_interface_trigger_update_query(String query) {
        add(query);
    }
    
    @Override
    public void run() {
        while (RUN) {
            wait_(1000);
            if (buffer.size() != 0) {
                while (buffer.size() > 0 && buffer.peek() != null) {
                    writeToDb(buffer.poll());
                    mCReplicator.set_last_activity_forwarding(System.currentTimeMillis());
                    wait_(10);
                }
            }
        }
    }
    
    private void wait_(int millis) {
        synchronized (this) {
            try {
                wait(millis);
            } catch (InterruptedException ex) {
                Logger.getLogger(BufferedDBWriter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
