/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mc_lab_trell_rep;

import Logger.SimpleLoggerLight;
import Supplementary.MCReplicatorIF;
import SuperClasses.BufferedDBWriterSuper;
import static SuperClasses.MCReplicatorSuper.MAIN_LOG;

/**
 *
 * @author KOCMOC
 */
public class BuffDBWriterTrell extends BufferedDBWriterSuper {

    public BuffDBWriterTrell(MCReplicatorIF mCReplicator) {
        super(mCReplicator);
        if (connectReconnect() == false) {
            SimpleLoggerLight.logg(MAIN_LOG, "Connection to sql_dest failed, program will close");
            System.exit(0);
        }
    }

    @Override
    public void writeToDb(Object obj) {
        if (obj instanceof TableToCopy) {
            record_table_to_copy_object(obj);
        } else if (obj instanceof String) {
            String query = (String) obj;

            if (query.contains("'last_check'") == false) {
                execute_insert_or_update_statement(sql_dest, query);
            } else {
                record_update_string_last_check(sql_dest, DBT_trell.INTERFACE_TRIGER_TABLE_NAME);
            }
        }
    }

    private void record_table_to_copy_object(Object obj) {
        TableToCopy ttc = (TableToCopy) obj;
        boolean sql_execution_ok = execute_insert_or_update_statement(sql_dest, SqlQ.insert_into_table_to_copy(ttc));
    }
}
