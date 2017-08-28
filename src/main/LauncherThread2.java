/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import Objects.RecipeRawMaterial;
import SQL.QuerySelect;
import SQL.Sql;
import Supplementary.GP;
import Supplementary.HelpM;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author KOCMOC
 */
public class LauncherThread2 implements Runnable {

    private MCReplicator mCReplicator;
    private Sql sql;
    private String parentItemCode_for_which_materials_are_to_be_recorded;
    private String parenItemCode_for_which_material_are_to_be_found;
    private Calc calc = new Calc();
    private int depth = 0;

    /**
     *
     * @param writer
     * @param sql
     * @param parentItemCode
     * @param itemCode_which_is_indeed_parentItemCode
     */
    public LauncherThread2(MCReplicator mCReplicator, Sql sql, String parentItemCode, String itemCode_which_is_indeed_parentItemCode, int depth, String caller) {
        this.mCReplicator = mCReplicator;
        this.sql = sql;
        this.parentItemCode_for_which_materials_are_to_be_recorded = parentItemCode;
        this.parenItemCode_for_which_material_are_to_be_found = itemCode_which_is_indeed_parentItemCode;
        this.depth = depth;
        if (depth > 0) {
            System.out.println("code_record = " + parentItemCode_for_which_materials_are_to_be_recorded
                    + " | code_for_which_to_find = " + parenItemCode_for_which_material_are_to_be_found
                    + " | itemCode = " + parenItemCode_for_which_material_are_to_be_found
                    + " | depth = " + depth
                    + " | caller = " + caller);
        }
        startThread();
    }

    private void startThread() {
        Thread x = new Thread(this);
        x.setName("LauncherThr2-" + parenItemCode_for_which_material_are_to_be_found);
        x.start();
    }

    private void export_recipe_raw_material() throws SQLException {
        ResultSet rs2 = execute(QuerySelect.get_raw_materials_corresponding_to_recipe(parenItemCode_for_which_material_are_to_be_found));
        HashMap pointOfUse_Map = calc.calc_PointOfUse_recipe_raw_material_table(parenItemCode_for_which_material_are_to_be_found);

        while (rs2.next()) {
            String itemCode = rs2.getString(DBT.RecipeMC1.itemCode).trim();
            String pointOfUse = (String) pointOfUse_Map.get(itemCode);
            double requiredQuantity = rs2.getFloat(DBT.RecipeMC1.requiredQuantity);
            String dateExport = HelpM.get_proper_date_time_same_format_on_all_computers();
            String status = GP.STATUS_ALL;
            //
            String matIndex = rs2.getString(DBT.RecipeMC1.matIndex);


            //This prevents a "never ending loop" and prevents that 
            //the record with same parentItemCode & itemCode is tried to be saved to DB, which causes error
            //$FIND_01$
            if (parenItemCode_for_which_material_are_to_be_found.trim().equals(itemCode.trim())
                    || parentItemCode_for_which_materials_are_to_be_recorded.trim().equals(itemCode.trim())) {
//                System.out.println("");
                continue;
            }

            if (matIndex.equals("R")) {// see doc_01.jpg
                depth++;
                LauncherThread2 lt = new LauncherThread2(
                        mCReplicator,
                        new Sql(GP.SQL_SRC_HOST, GP.SQL_SRC_PORT, GP.SQL_SRC_DB_NAME, GP.SQL_SRC_USERNAME, GP.SQL_SRC_PASSWORD),
                        parentItemCode_for_which_materials_are_to_be_recorded,
                        itemCode,
                        depth,
                        "LT2");
                continue;
            }

            RecipeRawMaterial recipeRawMaterial = new RecipeRawMaterial(
                    parentItemCode_for_which_materials_are_to_be_recorded,
                    itemCode, pointOfUse, requiredQuantity, dateExport, status);

            mCReplicator.add_to_db_writer_forwarding(recipeRawMaterial);
        }

    }

    private ResultSet execute(String query) {
        try {
            return sql.execute2(query);
        } catch (SQLException ex) {
            Logger.getLogger(LauncherThread2.class.getName()).log(Level.SEVERE, null, ex);
            return try_executing_aggain(ex, query);
        }
    }

    private ResultSet try_executing_aggain(SQLException ex, String query) {
        if (ex.toString().contains(GP.SQL_EXCEPTION_PATTERN_1)) {
            connectReconnect();
            try {
                return sql.execute2(query);
            } catch (SQLException ex1) {
                Logger.getLogger(LauncherThread2.class.getName()).log(Level.SEVERE, null, ex1);
                return null;
            }
        } else {
            return null;
        }
    }

    private void connectReconnect() {
        this.sql = new Sql(GP.SQL_SRC_HOST, GP.SQL_SRC_PORT, GP.SQL_SRC_DB_NAME, GP.SQL_SRC_USERNAME, GP.SQL_SRC_PASSWORD);
    }

    @Override
    public void run() {
        try {
            export_recipe_raw_material();
            sql.close();
        } catch (SQLException ex) {
            Logger.getLogger(LauncherThread2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
