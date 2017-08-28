/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import DBT.IngredTradeName;
import DBT2.vendor;
import Objects.RawMaterial;
import Objects.RawMaterialVendor;
import Objects.Recipe;
import Objects.RecipeRawMaterial;
import SQL.QueryDelete;
import SQL.QueryInsert;
import SQL.QuerySelect;
import SQL.QuerySpecial;
import SQL.Sql;
import SuperClasses.MCReplicatorSuper;
import Supplementary.GP;
import Supplementary.HelpM;
import Supplementary.MainFormIF;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author KOCMOC
 */
public class MCReplicator extends MCReplicatorSuper {

    private MainFormIF mainForm;
    private Sql sql_mcsap_dest = new Sql();
    private BufferedDBWriter dBWriter;
    private Calc calc = new Calc();
    //=========================================================
    private static final double DEPTH_COEFF = 1;

    public MCReplicator(MainFormIF mainForm) {
        this.mainForm = mainForm;
        dBWriter = new BufferedDBWriter(this);
        operationComplete = new OperationComplete(this); // This must be here!!
        delete_log_files_on_start();
        connect_sql();
        start_this_thread(this, this.getClass().getName());
    }

    private void delete_log_files_on_start() {
        File[] f = new File(".").listFiles();
        for (File file : f) {
            if (file.getName().toLowerCase().contains(".log")) {
                file.delete();
            }
        }
    }

    private void connect_sql() {
        connect_reconnect(this.getClass().getName());
        connect_reconnect_mcsap();
    }

    private void connect_reconnect_mcsap() {
        try {
            sql_mcsap_dest.connect(GP.SQL_DEST_HOST, GP.SQL_DEST_PORT, GP.SQL_DEST_DB_NAME,
                    GP.SQL_DEST_USERNAME, GP.SQL_DEST_PASSWORD);
        } catch (SQLException ex) {
            Logger.getLogger(BufferedDBWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void write_to_update_table(String flow, String status) {
        status += " (nr rec.= " + (BufferedDBWriter.total_nr_recorded_entries - 1) + " )";
        String control_query = QueryInsert.insert_into_update_table(
                DBT2.update.table_name,
                flow,
                "mc",
                "sap",
                status);
        dBWriter.add_interface_trigger_update_query(control_query);
    }

    //==========================================================================
    //==========================================================================
    /**
     * For testing purposes
     *
     * @throws SQLException
     */
    public void test() throws SQLException {
        export_recipe_raw_material("");
//        export_raw_material_vendor("");
    }

    public void export_flow_recipe_only_initial() throws SQLException {
        start_operation_complete_monitoring(this);
        //====
        count_nr_records_to_transfer_recipe_only_flow_initial();
        export_recipe("");
        //=====
        wait_();//Notifies from "OperationComplete.class"
        write_to_update_table(DBT2.update.RECIPE_ONLY_DATA_FLOW_TEST, DBT2.update.STATUS_INITIAL_UPDATE);
    }

    public void export_flow_raw_material_initial() throws SQLException {
        start_operation_complete_monitoring(this);
        //====
        count_nr_records_to_transfer_raw_material_flow_initial();
        export_raw_materials("");
        //=====
        wait_();//Notifies from "OperationComplete.class"
        write_to_update_table(DBT2.update.RAW_MATERIAL_DATA_FLOW, DBT2.update.STATUS_INITIAL_UPDATE);
    }

    public void export_flow_raw_material_partial() throws SQLException {
        start_operation_complete_monitoring(this);
        //====
        String date = get_last_update_all_flows(QuerySelect.get_latest_update_date_for_raw_material_flow());
        count_nr_records_to_transfer_raw_material_flow_partial(date);
        export_raw_materials(date);
        //=====
        wait_();//Notifies from "OperationComplete.class"
        write_to_update_table(DBT2.update.RAW_MATERIAL_DATA_FLOW, DBT2.update.STATUS_PARTIAL_UPDATE);
    }

    /**
     * This flow doesn't take into account the last transfer date but performs
     * transfer or update of all records
     *
     * @throws SQLException
     */
    public void export_flow_recipe_initial(boolean initial_with_erase) throws SQLException {
        if (initial_with_erase) {
            dBWriter.execute(sql_mcsap_dest, QueryDelete.delete_tables_before_executing_recipe_flow_initial());
        }

        start_operation_complete_monitoring(this);
        //====
        count_nr_records_to_transfer_recipe_flow_initial();
        export_recipe("");
        export_raw_materials("");
        export_recipe_raw_material("");
        //=====
        wait_();//Notifies from "OperationComplete.class"
        write_to_update_table(DBT2.update.RECIPE_RAW_MATERIAL_DATA_FLOW, DBT2.update.STATUS_INITIAL_UPDATE);
    }

    /**
     *
     * @param mode - 1 = date defined from db, 2 = date defined by user
     * @throws SQLException
     */
    public void export_flow_recipe_partial(int mode) throws SQLException {
        start_operation_complete_monitoring(this);
        //======
        String date = "";
        if (mode == 1) {
            date = get_last_update_all_flows(QuerySelect.get_latest_update_date_for_recipe_flow());
        } else if (mode == 2) {
            date = MainFormCustomer.chosen_kalendar_date;
        }

//        String date = "2013-11-01";
        int ammount = count_nr_records_to_transfer_recipe_flow_partial(date);

        // OBS Very important, otherwise if there is nothing to export it will wait 4ever
        if (ammount == 0) {
            enable_btn_forwarding();
            return;
        }

        export_recipe(date);
        export_raw_materials(date);
        export_recipe_raw_material(date);
        //=======
        wait_();//Notifies from "OperationComplete.class"
        write_to_update_table(DBT2.update.RECIPE_RAW_MATERIAL_DATA_FLOW, DBT2.update.STATUS_PARTIAL_UPDATE);
    }

    public void export_flow_recipe_partial_custom_date() throws SQLException {
        export_flow_recipe_partial(2);
    }

    //==========================================================================
    //==========================================================================
    private void export_raw_materials(String date_yyyy_MM_dd) throws SQLException {
        ResultSet rs;
        if (date_yyyy_MM_dd.isEmpty()) {
            rs = execute(sql_source, QuerySelect.get_raw_material_object_incomplete_no_filter());
        } else {
            rs = execute(sql_source, QuerySelect.get_raw_material_object_incomplete_date_filter(date_yyyy_MM_dd));
        }

        while (rs.next()) {
            String itemCode = rs.getString(DBT.IngredMC1.code).trim();
            String itemName = rs.getString(DBT.IngredMC1.itemName).trim();
            String prefferedLocation = calc.get_prefferedLocation(itemCode);
            int shelfLifeDays = rs.getInt(DBT.MCCPWARE.shelfLifeDays);
            String groupTechnologyCode = rs.getString(DBT.IngredMC1.groupTechnologyCode);
            String familyItemNumber = calc.get_familyItemNumber(groupTechnologyCode);
            String rawMaterialStatus = rs.getString(DBT.IngredMC1.rawMaterialStatus);
            String dateExport = HelpM.get_proper_date_time_same_format_on_all_computers();
            String dateProcessed = "";
            String status = GP.STATUS_ALL;

            RawMaterial rawMaterial = new RawMaterial(itemCode, itemName, prefferedLocation, shelfLifeDays, groupTechnologyCode, familyItemNumber, rawMaterialStatus, dateExport, dateProcessed, status);
            dBWriter.add(rawMaterial);
        }
    }

    private void export_recipe(String date_yyyy_MM_dd) throws SQLException {
        ResultSet rs;

        if (date_yyyy_MM_dd.isEmpty()) {
            rs = execute(sql_source, QuerySelect.get_recipe_codes_distinct());
        } else {
            rs = execute(sql_source, QuerySelect.get_recipe_codes_distinct_date_filter(date_yyyy_MM_dd));
        }

        while (rs.next()) {
            String parentItemCode = rs.getString(DBT.RecipePropMain.parentItemCode).trim();
//            String parentItemCode = "06-0-N476";
            String familySubGroup = rs.getString(DBT.RecipePropMain.familySubGroup);
            String groupName = calc.get_groupname_for_recipe(familySubGroup);

            //=============
            String workInstructions = calc.calc_WorkInstructions_recipe_table(parentItemCode, parentItemCode);

            String recipe_prev_release = parentItemCode;
//            System.out.println("" + recipe_prev_release);
            while (workInstructions.isEmpty()) {
                recipe_prev_release = HelpM.get_prev_release_for_recipe(recipe_prev_release);
//                System.out.println("" + recipe_prev_release);
                if (recipe_prev_release.isEmpty()) {
                    break;
                } else {
                    workInstructions = calc.calc_WorkInstructions_recipe_table(recipe_prev_release, parentItemCode);
                    if (workInstructions.isEmpty() == false) {
                        break; //Work instructions found
                    }
                }
            }
            //==============

            String description = rs.getString(DBT.RecipePropMain.description);
            String recipeStatus = rs.getString(DBT.RecipePropMain.recipeStatus);
            String developedOn = rs.getString(DBT.RecipePropMain.developedOn);
            String lastUpdate = rs.getString(DBT.RecipePropMain.lastUpdate);
            String dateExport = HelpM.get_proper_date_time_same_format_on_all_computers();
            String status = GP.STATUS_ALL;

            Recipe recipe = new Recipe(parentItemCode, familySubGroup, groupName, workInstructions, description, recipeStatus, developedOn, lastUpdate, dateExport, status);
            dBWriter.add(recipe);
        }
        //
        BufferedDBWriter.preparedStatement_recipe.close();
        BufferedDBWriter.preparedStatement_recipe = null;
        //
    }

    private void export_recipe_raw_material(String date_yyyy_MM_dd) throws SQLException {
        ResultSet rs;
        if (date_yyyy_MM_dd.isEmpty()) {
            rs = execute(sql_source, QuerySelect.get_recipe_codes_distinct());
        } else {
            rs = execute(sql_source, QuerySelect.get_recipe_codes_distinct_date_filter(date_yyyy_MM_dd));
        }
        while (rs.next()) {
            String parentItemCode = rs.getString(DBT.RecipePropMain.parentItemCode).trim();
//            String parentItemCode = "12-0-0034"; // For debugging pupose
            ResultSet rs2 = execute2(sql_source, QuerySelect.get_raw_materials_corresponding_to_recipe(parentItemCode));
            //========
            HashMap pointOfUse_Map = calc.calc_PointOfUse_recipe_raw_material_table(parentItemCode);

            while (rs2.next()) {
                String itemCode = rs2.getString(DBT.RecipeMC1.itemCode).trim();
                String pointOfUse = (String) pointOfUse_Map.get(itemCode);
                double requiredQuantity = rs2.getDouble(DBT.RecipeMC1.requiredQuantity);
                String dateExport = HelpM.get_proper_date_time_same_format_on_all_computers();
                String status = GP.STATUS_ALL;
                //
                String matIndex = rs2.getString(DBT.RecipeMC1.matIndex);

                //=================<extract_materials_from_recipe>==============
                //==============================================================
                //This code
                //
                //This prevents a "never ending loop" and prevents that 
                //the record with same parentItemCode & itemCode is tried to be saved to DB, which causes error
//                if (parentItemCode.trim().equals(itemCode.trim())) {
//                    continue;
//                }

                // see doc_01.jpg & doc_05.vsd
//                if (matIndex.equals("R")) { 
//                    LauncherThread2 launcherThread2 = new LauncherThread2(this, new Sql("10.57.69.133", "1433", "CPMD", "sa", ""), parentItemCode, itemCode, 0, "MCR");
//                    continue;
//                }
                //==============================================================
                //=================</extract_materials_from_recipe>=============

                RecipeRawMaterial recipeRawMaterial = new RecipeRawMaterial(parentItemCode, itemCode, pointOfUse, requiredQuantity, dateExport, status);
                dBWriter.add(recipeRawMaterial);
            }
        }
    }

    private void export_raw_material_vendor(String date_yyyy_MM_dd) throws SQLException {
        ResultSet rs;
        if (date_yyyy_MM_dd.isEmpty()) {
            //OBS!! Dont forget activating "is NULL" in the queries after debugg!!!!
            rs = dBWriter.execute(sql_mcsap_dest, QuerySelect.get_venodrs());
        } else {
            rs = dBWriter.execute(sql_mcsap_dest, QuerySelect.get_vendors_date_filter(date_yyyy_MM_dd));
        }

        while (rs.next()) {

            String vendor_table_row_id = rs.getString(vendor.row_id);

//            String vendor_name = rs.getString(vendor.vendor_name).toLowerCase();
            String vendor_name = "Lanxess";

            ResultSet rs2 = execute2(sql_source, QuerySelect.get_raw_materials_corresponding_to_vendor(vendor_name));

            String itemCode;
            String tradeName;
            while (rs2.next()) {
                itemCode = rs2.getString(IngredTradeName.itemCode);
                tradeName = rs2.getString(IngredTradeName.tradename);
                RawMaterialVendor rmv = new RawMaterialVendor(vendor_table_row_id, itemCode, vendor_name, tradeName);
                dBWriter.add(rmv);
            }
//            System.out.println("");
        }
    }
    //=========================================================================

    private String get_last_update_all_flows(String query) {
        ResultSet rs;
        try {
            rs = dBWriter.execute(sql_mcsap_dest, query);
            rs.next();
            return rs.getString(DBT2.update.dateSend);
        } catch (SQLException ex) {
            // dont need to throw this one, it's not so important
//            Logger.getLogger(MCReplicator.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }
    }

    private void count_nr_records_to_transfer_recipe_flow_initial() throws SQLException {
        int summ = 0;
        ResultSet rs = execute(sql_source, QuerySpecial.count_ammount_of_recipe_records());
        rs.next();
        summ += rs.getInt(1);
        rs = execute(sql_source, QuerySpecial.count_ammount_of_raw_material_records());
        rs.next();
        summ += rs.getInt(1);
        rs = execute(sql_source, QuerySpecial.count_ammount_of_recipe_raw_material_records());
        rs.next();
        summ += rs.getInt(1);
        summ *= DEPTH_COEFF;
        HelpM.set_how_many_records_to_transfer_info_approx(summ);
    }

    private int count_nr_records_to_transfer_recipe_flow_partial(String date_yyyy_MM_dd) throws SQLException {
        int summ = 0;
        ResultSet rs = execute(sql_source, QuerySpecial.count_ammount_of_recipe_records_date_filter(date_yyyy_MM_dd));
        rs.next();
        summ += rs.getInt(1);
        rs = execute(sql_source, QuerySpecial.count_ammount_of_raw_material_records_date_filter(date_yyyy_MM_dd));
        rs.next();
        summ += rs.getInt(1);
        rs = execute(sql_source, QuerySpecial.count_ammount_of_recipe_raw_material_records_date_filter(date_yyyy_MM_dd));
        rs.next();
        summ += rs.getInt(1);
        summ *= DEPTH_COEFF;
        HelpM.set_how_many_records_to_transfer_info_approx(summ);
        return summ;
    }

    private void count_nr_records_to_transfer_recipe_only_flow_initial() throws SQLException {
        int summ = 0;
        ResultSet rs = execute(sql_source, QuerySpecial.count_ammount_of_recipe_records());
        rs.next();
        summ += rs.getInt(1);
        HelpM.set_how_many_records_to_transfer_info(summ);
    }

    private void count_nr_records_to_transfer_raw_material_flow_initial() throws SQLException {
        int summ = 0;
        ResultSet rs = execute(sql_source, QuerySpecial.count_ammount_of_raw_material_records());
        rs.next();
        summ += rs.getInt(1);
        HelpM.set_how_many_records_to_transfer_info(summ);
    }

    private void count_nr_records_to_transfer_raw_material_flow_partial(String date_yyyy_MM_dd) throws SQLException {
        int summ = 0;
        ResultSet rs = execute(sql_source, QuerySpecial.count_ammount_of_raw_material_records_date_filter(date_yyyy_MM_dd));
        rs.next();
        summ += rs.getInt(1);
        HelpM.set_how_many_records_to_transfer_info(summ);
    }

    //=========================================================================
    public synchronized void add_to_db_writer_forwarding(Object obj) {
        dBWriter.add(obj);
    }
    //=========================================================================

    @Override
    public synchronized void enable_btn_forwarding() {
        mainForm.enable_btn();
    }
}
