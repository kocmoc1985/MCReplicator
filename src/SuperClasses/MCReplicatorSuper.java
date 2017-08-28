/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SuperClasses;

import Logger.SimpleLoggerLight;
import SQL.QueryInsert;
import SQL.Sql;
import Supplementary.GP;
import Supplementary.MCReplicatorIF;
import com.jezhumble.javasysmon.JavaSysMon;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.BufferedDBWriter;
import main.MCReplicator;
import main.OperationComplete;

/**
 *
 * @author KOCMOC
 */
public class MCReplicatorSuper implements MCReplicatorIF,Runnable{

    public Sql sql_source = new Sql();
    public OperationComplete operationComplete;
    public static final String MAIN_LOG = "main.log";
    public static final String SQL_LOG_0 = "sql_exceptions.log";
    public static final String SQL_LOG_1 = "sql_error_quiries.log";
    private final JavaSysMon monitor = new JavaSysMon();
    public boolean NEW_RECORDS_FOUND = false;
    public String PID;

    
    public boolean connect_reconnect(String class_name) {
        PID = ""+monitor.currentPid();
        SimpleLoggerLight.logg(MAIN_LOG, "Program started, PID = " + PID);
        try {
           return sql_source.connect(GP.SQL_SRC_HOST, GP.SQL_SRC_PORT, GP.SQL_SRC_DB_NAME,
                    GP.SQL_SRC_USERNAME, GP.SQL_SRC_PASSWORD);           
        } catch (SQLException ex) {
            Logger.getLogger(BufferedDBWriter.class.getName()).log(Level.SEVERE, null, ex);
            
            return false;
        }
    }

    public void start_this_thread(Object obj, String class_name) {
        Thread x = new Thread((Runnable) obj);
        x.setName(class_name + "-Thr");
        x.start();
    }

    public void start_operation_complete_monitoring(MCReplicatorIF replicatorIF) {
        operationComplete = new OperationComplete(replicatorIF);
    }

    /**
     *
     * @param flow
     * @param status
     * @param table_name
     * @param sender
     * @param reciever
     * @param bdbw
     */
    public void write_to_update_table(String flow, String status, String table_name, String sender, String reciever, BufferedDBWriterSuper bdbw) {
        if (status.isEmpty()) {
            status += " (nr rec.= " + (BufferedDBWriterSuper.total_nr_recorded_entries - 1) + " )";
        }

        String control_query = QueryInsert.insert_into_update_table(
                table_name,
                flow,
                sender,
                reciever,
                status);
        bdbw.add_interface_trigger_update_query(control_query);
    }
    
    public ResultSet execute(Sql sql,String query) {
        try {
            return sql.execute(query);
        } catch (SQLException ex) {
            Logger.getLogger(MCReplicator.class.getName()).log(Level.SEVERE, null, ex);
            sql_errors_logging(ex, query);
            sql.sql_error_handling(ex);
            return null;
        }
    }

    public ResultSet execute2(Sql sql,String query) {
        try {
            return sql.execute2(query);
        } catch (SQLException ex) {
            Logger.getLogger(MCReplicator.class.getName()).log(Level.SEVERE, null, ex);
            sql_errors_logging(ex, query);
            sql.sql_error_handling(ex);
            return null;
        }
    }

    public void sql_errors_logging(Exception ex, String query) {
        SimpleLoggerLight.logg(SQL_LOG_1, query);
        //==================================================================
        SimpleLoggerLight.logg(SQL_LOG_0, query);
        SimpleLoggerLight.logg(SQL_LOG_0, ex.toString());
        SimpleLoggerLight.logg(SQL_LOG_0, "*********************************");
    }

    public void wait_() {
        synchronized (this) {
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(BufferedDBWriter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * This wait_(..) method implements advanced things
     * because otherwise the object is notified before
     * it have waited the requested time
     * @consumer - run() method
     * @param millis 
     */
    public void wait_(long millis) {
        long start = System.currentTimeMillis();
        synchronized (this) {
            try {
                wait(millis);
                long stop = System.currentTimeMillis();

                long time_waited = Math.abs(stop - start);
                if (time_waited < millis) {
                    long time_left_to_wait = millis - time_waited;
                    wait(time_left_to_wait);
                }

            } catch (InterruptedException ex) {
                Logger.getLogger(BufferedDBWriter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void set_last_activity_forwarding(long ms) {
        operationComplete.set_last_activity(ms);
    }

    @Override
    public void enable_btn_forwarding() {
        //
    }

    @Override
    public void run() {
        //
    }
}
