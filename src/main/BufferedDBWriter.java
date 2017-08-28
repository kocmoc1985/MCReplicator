/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import DBT2.vendor;
import Objects.RawMaterial;
import Objects.RawMaterialVendor;
import Objects.Recipe;
import Objects.RecipeRawMaterial;
import SQL.QueryInsert;
import SQL.QueryUpdate;
import SQL.Sql;
import SuperClasses.BufferedDBWriterSuper;
import Supplementary.GP;
import Supplementary.HelpM;
import Supplementary.MCReplicatorIF;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author KOCMOC
 */
public class BufferedDBWriter extends BufferedDBWriterSuper {

    public static long prev_time_flag = 0;
    public static final String LOG_FILE = "buff_dbw_sql.log";

    public BufferedDBWriter(MCReplicatorIF mCReplicator) {
        super(mCReplicator);
        connectReconnect();
    }

    @Override
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

    @Override
    public void writeToDb(Object obj) {
//        System.gc();
        if (obj instanceof RawMaterial) {
            record_raw_material_object(obj);
            HelpM.set_current_table_beeing_updated_info("raw_material");
        } else if (obj instanceof Recipe) {
//            record_recipe_object(obj);
            record_recipe_object_B(obj); // Using prepared statement, not tested yet
            HelpM.set_current_table_beeing_updated_info("recipe");
        } else if (obj instanceof RecipeRawMaterial) {
            record_recipe_raw_material_object(obj);
            HelpM.set_current_table_beeing_updated_info("recipe_raw_material");
        } else if (obj instanceof RawMaterialVendor) {
            record_raw_material_vendor_object(obj);
            HelpM.set_current_table_beeing_updated_info("raw_material_vendor");
        } else if (obj instanceof String) {
            execute_insert_or_update_statement(sql_dest, (String) obj);
        }
    }

    private void record_raw_material_vendor_object(Object obj) {
        RawMaterialVendor rmv = (RawMaterialVendor) obj;
        //
        String insert_query = QueryInsert.insert_into_raw_material_vendor_table(
                rmv.getItemCode(),
                rmv.getVendorName(),
                rmv.getTradeName());
        //
        // The code section below is mechanism responsible
        // for the process of updating the "dateProcessed" field
        // in the table from which the data is beeing exported
        if (execute_insert_or_update_statement(sql_dest, insert_query)) {
            String q = QueryUpdate.update_dateProcessed_any_table(vendor.tableName, rmv.getVendor_table_row_id());
            execute(sql_dest, q);
        }
    }

    private void record_recipe_raw_material_object(Object obj) {
        RecipeRawMaterial recipeRawMaterial = (RecipeRawMaterial) obj;
        //
        String insert_query;
        //
        insert_query = QueryInsert.insert_into_recipe_raw_material_table(
                recipeRawMaterial.getParentItemCode(),
                recipeRawMaterial.getItemCode(),
                recipeRawMaterial.getPointOfUse_CALC(),
                recipeRawMaterial.getRequiredQuantity_CALC(),
                recipeRawMaterial.getDateExport(),
                recipeRawMaterial.getStatus());
        //
        execute_insert_or_update_statement(sql_dest, insert_query);

    }

    private void record_recipe_object(Object obj) {
        Recipe recipe = (Recipe) obj;
        //
        String insert_query;

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
        execute_insert_or_update_statement(sql_dest, insert_query);
    }

    public static PreparedStatement preparedStatement_recipe;

    private void record_recipe_object_B(Object obj) {
        //
        Recipe recipe = (Recipe) obj;
        //
        if (preparedStatement_recipe == null) {
            try {
                String insert_query = QueryInsert.insert_into_recipe_table_prepared();
                preparedStatement_recipe = sql_dest.prepareStatement(insert_query);
            } catch (SQLException ex) {
                Logger.getLogger(BufferedDBWriter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //
        if (preparedStatement_recipe != null) {
            try {
                preparedStatement_recipe.setString(1, recipe.getParentItemCode());
                preparedStatement_recipe.setString(2, recipe.getFamilySubGroup());
                preparedStatement_recipe.setString(3, recipe.getGroupName());
                preparedStatement_recipe.setString(4, recipe.getWorkInstructions());
                preparedStatement_recipe.setString(5, recipe.getDescription());
                preparedStatement_recipe.setString(6, recipe.getRecipeStatus());
                preparedStatement_recipe.setString(7, recipe.getDevelopedOn());
                preparedStatement_recipe.setString(8, recipe.getLastUpdate());
                preparedStatement_recipe.setString(9, recipe.getDateExport());
                preparedStatement_recipe.setString(10, recipe.getStatus());
            } catch (SQLException ex) {
                Logger.getLogger(BufferedDBWriter.class.getName()).log(Level.SEVERE, null, ex);
            }
            //
            execute_insert_or_update_statement_B(sql_dest, preparedStatement_recipe);
            //
        }
    }

    private void record_raw_material_object(Object obj) {
        RawMaterial rawMaterial = (RawMaterial) obj;
        //
        String insert_query;

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
        execute_insert_or_update_statement(sql_dest, insert_query);
    }

    @Override
    public boolean execute_insert_or_update_statement(Sql sql, String query) {
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
            return true;
        } catch (SQLException ex) {
            sql.sql_error_handling(ex);
            sql_errors_logging(ex, query);
            if (ex.toString().contains(GP.SQL_ERR_LOGGING_PATTERN_1) == false) {
                //to reduce err output
                Logger.getLogger(BufferedDBWriter.class.getName()).log(Level.SEVERE, null, ex);
                sql.loggSqlExceptionWithQuerry(LOG_FILE, ex, query);
            }
            return false;
        }
    }

    public boolean execute_insert_or_update_statement_B(Sql sql, PreparedStatement ps) {
        try {
            long a = System.currentTimeMillis();
            //===============
            ps.executeUpdate();
            //===============
            long ms_per_record = System.currentTimeMillis() - a;
            if (ms_per_record > 10) {
                //=======
            }
            HelpM.show_record_speed_on_graph((int) ms_per_record);
            HelpM.show_record_speed(ms_per_record);
            HelpM.show_nr_recorded_entries(total_nr_recorded_entries++);
            return true;
        } catch (SQLException ex) {
            sql.sql_error_handling(ex);
            if (ex.toString().contains(GP.SQL_ERR_LOGGING_PATTERN_1) == false) {
                //to reduce err output
                Logger.getLogger(BufferedDBWriter.class.getName()).log(Level.SEVERE, null, ex);
            }
            return false;
        }
    }
}
