/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qew_dbf_replicator;

import SQL.Sql_B;
import Supplementary.GP;
import Supplementary.HelpM;
import Supplementary.OUT;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author KOCMOC
 */
public class DBFReplicator {

    private Sql_B sql_odbc_source;
    private Sql_B sql_1;
    private OUT OUT;

    public DBFReplicator(OUT out) {
        this.OUT = out;
        connect();
        go();
    }

    public static void main(String[] args) {
        TestOut out = new TestOut();
        DBFReplicator dbfr = new DBFReplicator(out);
    }

    private void go_test() {
        //        executeProcedureSync(SQL_PROCEDURES.generate_Empty_CSVColumn(5));
//        executeProcedureSync(SQL_PROCEDURES.generate_Empty_CSVColumn(7));
//        //
//        //
//        executeAsyncProcedure(SQL_PROCEDURES.generate_Empty_CSVColumn(5));
//        executeAsyncProcedure(SQL_PROCEDURES.generate_Empty_CSVColumn(7));
//        executeAsyncProcedure(SQL_PROCEDURES.generate_Empty_CSVColumn(4));
//        executeAsyncProcedure(SQL_PROCEDURES.generate_Empty_CSVColumn(8));
//        executeAsyncProcedure(SQL_PROCEDURES.generate_Empty_CSVColumn(3));
//        executeAsyncProcedure(SQL_PROCEDURES.generate_Empty_CSVColumn(5));
    }

    private void go() {
        //        executeProcedureSync(SQL_PROCEDURES.DELETE_CREATE_ALL_RECIPE());
        //
        //
        try {
            //
            //
            sql_1.execute(SQL_COMMANDS.delete_from_tables_before_replication());
            System.out.println("Delete tables ok!\n");
        } catch (SQLException ex) {
            Logger.getLogger(DBFReplicator.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        //
        ArrayList<String> Async_proc_list = SQL_PROCEDURES.async_procedure_list;
        for (String procedure : Async_proc_list) {
            executeAsyncProcedure(procedure);
        }
        //
        //
        //
        ArrayList<String> sync_proc_list = SQL_PROCEDURES.sync_procedure_list;
        for (String procedure : sync_proc_list) {
            if (executeProcedureSync(procedure) != 0) {
                break;
            }
        }
        //
        //
    }

    private int executeProcedureSync(String procedure) {
        try {
            return runProcedureIntegerReturn_A(sql_1.getConnection(), procedure);
        } catch (SQLException ex) {
            Logger.getLogger(DBFReplicator.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }

    private void executeAsyncProcedure(String procedure) {
        Thread proc = new Thread(new RunProcedureAsync(procedure));
        proc.start();
        //Have this section if you want to wait untill the thread executes to end...
//        try {
//            proc.join();
//        } catch (InterruptedException ex) {
//            Logger.getLogger(DBFReplicator.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    private void connect() {
        try {
            connect1();
            OUT.displayMessage("Connection to DEST succeded: " + GP.SQL_DEST_DB_NAME);
        } catch (SQLException ex) {
            Logger.getLogger(DBFReplicator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private synchronized void connectSource() throws SQLException {
        sql_odbc_source = new Sql_B(false, true);
        sql_odbc_source.connect_odbc("", "", GP.SQL_SRC_DB_NAME);
    }

    private synchronized void connect1() throws SQLException {
        sql_1 = new Sql_B(false, true);
        sql_1.connect_tds(
                GP.SQL_DEST_HOST,
                GP.SQL_DEST_PORT,
                GP.SQL_DEST_DB_NAME,
                GP.SQL_DEST_USERNAME,
                GP.SQL_DEST_PASSWORD,
                true, "workgroup", null);
    }

    /**
     *
     * @param sqlConnection
     * @param procedure - ready procedure with parameters if any
     * @return
     * @throws SQLException
     */
    private synchronized int runProcedureIntegerReturn_A(Connection sqlConnection, String procedure) throws SQLException {
        long enter = System.currentTimeMillis();
//        System.out.println("\nenter " + procedure + ": " + enter);
        //
        CallableStatement proc = sqlConnection.prepareCall("{ ? = call " + procedure + " }");
        proc.registerOutParameter(1, Types.INTEGER);
        proc.execute();
        int ret = proc.getInt(1);
        //
        long exit = System.currentTimeMillis();
        System.out.println("RETURN: " + ret + " / " + procedure + " / duration:" + (exit - enter));
//        System.out.println("\nexit " + procedure + ": " + exit);
        return ret;
    }

    /**
     * Here Should Procedures be run, which can be executed simultaneously
     */
    class RunProcedureAsync implements Runnable {

        private String PROCEDURE_WITH_PARAMS;

        public RunProcedureAsync(String PROCEDURE_WITH_PARAMS) {
            this.PROCEDURE_WITH_PARAMS = PROCEDURE_WITH_PARAMS;
        }

        private int runProcedureIntegerReturn_B(Connection sqlConnection, String procedure, OUT out) throws SQLException {
            long enter = System.currentTimeMillis();
            out.displayMessage("enter " + procedure + ": " + enter);
            //===
            CallableStatement proc = sqlConnection.prepareCall("{ ? = call " + procedure + " }");
            proc.registerOutParameter(1, Types.INTEGER);
            proc.execute();
            int ret = proc.getInt(1);
            //===
            long exit = System.currentTimeMillis();
            out.displayMessage("RETURN: " + ret + " / " + procedure + " / duration:" + (exit - enter));
            return ret;
        }

        @Override
        public void run() {
            try {
                //Consider as example for now!
                runProcedureIntegerReturn_B(sql_1.getConnection(), PROCEDURE_WITH_PARAMS, OUT);
            } catch (SQLException ex) {
                Logger.getLogger(DBFReplicator.class.getName()).log(Level.SEVERE, null, ex);
            }
//            OUT.displayMessage("ThreadOut: " + PROCEDURE_WITH_PARAMS + " / " + System.currentTimeMillis());
        }
    }
}

class TestOut implements OUT {

    @Override
    public void displayMessage(String msg) {
        System.out.println(HelpM.get_proper_date_time_same_format_on_all_computers() + "  " + msg + "\n");
    }
    
}
