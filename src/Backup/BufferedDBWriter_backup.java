/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Backup;

import Logger.SimpleLoggerLight;
import Objects.RawMaterial;
import Objects.RawMaterialVendor;
import Objects.Recipe;
import Objects.RecipeRawMaterial;
import SQL.QueryInsert;
import SQL.QuerySelect;
import SQL.QueryUpdate;
import SQL.Sql;
import Supplementary.GP;
import Supplementary.HelpM;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import static main.BufferedDBWriter.prev_time_flag;
import static main.BufferedDBWriter.total_nr_entries_added_to_buffer;
import static main.BufferedDBWriter.total_nr_recorded_entries;
import main.MCReplicator;

/**
 *
 * @author KOCMOC
 */
public class BufferedDBWriter_backup implements Runnable{
     private MCReplicator mCReplicator;
    private Sql sql = new Sql();
    private LinkedList<Object> buffer = new LinkedList();
    //================================================
    public static int total_nr_entries_added_to_buffer;
    public static int total_nr_recorded_entries;
    private boolean RUN = true;
    //=================================================
    private static final String SQL_LOG_0 = GP.LOG_PREFIX_BUFF_DB_W + "sql_exceptions.log";
    private static final String SQL_LOG_1 = GP.LOG_PREFIX_BUFF_DB_W + "sql_error_quiries.log";
    private static final String SQL_LOG_2 = GP.LOG_PREFIX_BUFF_DB_W + "materials_only_in_RecipeMC1.log";
    //=================================================
    public static long prev_time_flag = 0;

    public BufferedDBWriter_backup(MCReplicator mCReplicator) {
        this.mCReplicator = mCReplicator;
        connectReconnect();
        startThread();
    }

    private void startThread() {
        Thread thread = new Thread(this);
        thread.setName("DBWriter-Thr");
        thread.start();
    }

    private void connectReconnect() {
        try {
            sql.connect(GP.SQL_DEST_HOST, GP.SQL_DEST_PORT, GP.SQL_DEST_DB_NAME,
                    GP.SQL_DEST_USERNAME, GP.SQL_DEST_PASSWORD);
        } catch (SQLException ex) {
            Logger.getLogger(main.BufferedDBWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public synchronized void add(Object obj) {
        if (obj instanceof String == false) {
            HelpM.show_nr_records_added_to_buffer(total_nr_entries_added_to_buffer++);
        }
        long a = System.currentTimeMillis();
        //===============
        buffer.add(obj);
        //===============
        long ms_per_second = -1;
        if (prev_time_flag != 0) {
            ms_per_second = a - prev_time_flag;
        }
        prev_time_flag = a;
        HelpM.show_calc_speed_on_graph((int) ms_per_second);
        notify();
    }

    private void writeToDb(Object obj) {
//        System.gc();
        if (obj instanceof RawMaterial) {
            record_raw_material_object(obj);
            HelpM.set_current_table_beeing_updated_info("raw_material");
        } else if (obj instanceof Recipe) {
            record_recipe_object(obj);
            HelpM.set_current_table_beeing_updated_info("recipe");
        } else if (obj instanceof RecipeRawMaterial) {
            record_recipe_raw_material_object(obj);
            HelpM.set_current_table_beeing_updated_info("recipe_raw_material");
        } else if (obj instanceof RawMaterialVendor) {
            record_raw_material_vendor_object(obj);
            HelpM.set_current_table_beeing_updated_info("raw_material_vendor");
        } else if (obj instanceof String) {
            execute_insert_statement((String) obj);
        }
    }

    private void record_raw_material_vendor_object(Object obj) {
        RawMaterialVendor rmv = (RawMaterialVendor) obj;
         //
        String insert_query;
        String update_query;
        //
    }

    private void record_recipe_raw_material_object(Object obj) {
        RecipeRawMaterial recipeRawMaterial = (RecipeRawMaterial) obj;
        //
        String insert_query;
        String update_query;
        //
        String table_contains_query =
                QuerySelect.table_contains_recipe_raw_material(recipeRawMaterial.getParentItemCode(), recipeRawMaterial.getItemCode());
        if (table_contains(table_contains_query)) {
            update_query = QueryUpdate.update_recipe_raw_material(
                    recipeRawMaterial.getParentItemCode(),
                    recipeRawMaterial.getItemCode(),
                    recipeRawMaterial.getPointOfUse_CALC(),
                    recipeRawMaterial.getRequiredQuantity_CALC(),
                    recipeRawMaterial.getDateExport(),
                    recipeRawMaterial.getStatus());
            //
            execute_update_statement(update_query);
        } else {
            insert_query = QueryInsert.insert_into_recipe_raw_material_table(
                    recipeRawMaterial.getParentItemCode(),
                    recipeRawMaterial.getItemCode(),
                    recipeRawMaterial.getPointOfUse_CALC(),
                    recipeRawMaterial.getRequiredQuantity_CALC(),
                    recipeRawMaterial.getDateExport(),
                    recipeRawMaterial.getStatus());
            //
            execute_insert_statement(insert_query);
        }
    }

    private void record_recipe_object(Object obj) {
        Recipe recipe = (Recipe) obj;
        //
        String insert_query;
        String update_query;
        //
        String table_contains_query =
                QuerySelect.table_contains_recipe(recipe.getParentItemCode());
        if (table_contains(table_contains_query)) {
            update_query = QueryUpdate.update_recipe(
                    recipe.getParentItemCode(),
                    recipe.getFamilySubGroup(),
                    recipe.getGroupName(),
                    recipe.getWorkInstructions(),
                    recipe.getDescription(),
                    recipe.getRecipeStatus(),
                    recipe.getDevelopedOn(),
                    recipe.getLastUpdate(),
                    recipe.getDateExport(),
                    recipe.getStatus());
            //
            execute_update_statement(update_query);
        } else {
            insert_query = QueryInsert.insert_into_recipe_table(
                    recipe.getParentItemCode(),
                    recipe.getFamilySubGroup(),
                    recipe.getGroupName(),
                    recipe.getWorkInstructions(),
                    recipe.getDescription(),
                    recipe.getRecipeStatus(),
                    recipe.getDevelopedOn(),
                    recipe.getLastUpdate(),
                    recipe.getDateExport(),
                    recipe.getStatus());
            //
            execute_insert_statement(insert_query);
        }
    }

    private void record_raw_material_object(Object obj) {
        RawMaterial rawMaterial = (RawMaterial) obj;
        //
        String insert_query;
        String update_query;
        //
        String table_contains_query =
                QuerySelect.table_contains_raw_material(rawMaterial.getItemCode());
        if (table_contains(table_contains_query)) {
            update_query = QueryUpdate.update_raw_material(
                    rawMaterial.getItemCode(),
                    rawMaterial.getItemName(),
                    rawMaterial.getPrefferedLocation(),
                    rawMaterial.getShelfLifeDays_CALC(),
                    rawMaterial.getGroupTechnologyCode(),
                    rawMaterial.getFamilyItemNumber(),
                    rawMaterial.getRawMaterialStatus(),
                    rawMaterial.getDateExport(),
                    rawMaterial.getStatus());
            //
            execute_update_statement(update_query);
        } else {
            insert_query = QueryInsert.insert_into_raw_materials_table(
                    rawMaterial.getItemCode(),
                    rawMaterial.getItemName(),
                    rawMaterial.getPrefferedLocation(),
                    rawMaterial.getShelfLifeDays_CALC(),
                    rawMaterial.getGroupTechnologyCode(),
                    rawMaterial.getFamilyItemNumber(),
                    rawMaterial.getRawMaterialStatus(),
                    rawMaterial.getDateExport(),
                    rawMaterial.getStatus());
            //
            execute_insert_statement(insert_query);
        }
        //================================================================
    }

    private void execute_insert_statement(String query) {
        try {
            long a = System.currentTimeMillis();
            //===============
            sql.execute(query);
            //===============
            long ms_per_record = System.currentTimeMillis() - a;
            if (ms_per_record > 10) {
                //=======
            }
            HelpM.show_record_speed_on_graph((int) ms_per_record);
            HelpM.show_record_speed(ms_per_record);
            HelpM.show_nr_recorded_entries(total_nr_recorded_entries++);
        } catch (SQLException ex) {
            sql.sql_error_handling(ex);
            sql_errors_logging(ex, query);
            if (ex.toString().contains(GP.SQL_ERR_LOGGING_PATTERN_1) == false) {
                //to reduce err output
                Logger.getLogger(main.BufferedDBWriter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void sql_errors_logging(SQLException ex, String query) {
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

    private void execute_update_statement(String query) {
        execute_insert_statement(query);
    }

    private void execute_delete_statement(String query) {
        execute(query);
    }

    public void execute_control_statement(String query) {
        add(query);
    }

    public ResultSet execute(String query) {
        try {
            return sql.execute(query);
        } catch (SQLException ex) {
            SimpleLoggerLight.logg(SQL_LOG_0, query);
            SimpleLoggerLight.logg(SQL_LOG_0, ex.toString());
            SimpleLoggerLight.logg(SQL_LOG_0, "**********************************************");
            Logger.getLogger(main.BufferedDBWriter.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    private boolean table_contains(String query) {
        ResultSet rs;
        try {
            rs = sql.execute(query);
            if (rs.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(main.BufferedDBWriter.class.getName()).log(Level.SEVERE, null, ex);
            return true;
        }
    }

    @Override
    public void run() {
        while (RUN) {
            wait_(1000);
            if (buffer.size() != 0) {
                while (buffer.size() > 0 && buffer.peek() != null) {
                    writeToDb(buffer.poll());
                    mCReplicator.set_last_activity_forwarding(System.currentTimeMillis());
                }
            }
        }
    }

    private void wait_(int millis) {
        synchronized (this) {
            try {
                wait(millis);
            } catch (InterruptedException ex) {
                Logger.getLogger(main.BufferedDBWriter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
