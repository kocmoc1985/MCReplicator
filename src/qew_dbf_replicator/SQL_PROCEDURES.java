/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qew_dbf_replicator;

import java.util.ArrayList;

/**
 *
 * @author KOCMOC
 */
public class SQL_PROCEDURES {

    //Example of calling procedure with parameters
    private static String generate_CSVColumn(String recipeName, String release, int column, String order, int ammount) {
        return "generate_CSVColumn '" + recipeName + "','" + release + "'," + column + ",'" + order + "'," + ammount;
    }

    public static String generate_Empty_CSVColumn(int column) {
        return "generate_Empty_CSVColumn " + column;
    }
    //===============================================
    //===============================================
    //===============================================
    //===============================================

    public static String DELETE_CREATE_ALL_RECIPE() {
        return "DELETE_CREATE_ALL_RECIPE";
    }
    //===============================================
    //
    public static final ArrayList<String> sync_procedure_list = new ArrayList<String>();
    public static final ArrayList<String> async_procedure_list = new ArrayList<String>();
    //
    //

    static {
        sync_procedure_list.add(Import_to_Recipe_Group__1());
        sync_procedure_list.add(Import_to_MixerInfoBasic_00__2());
        sync_procedure_list.add(Import_to_Recipe_Prop_Main__3());
        sync_procedure_list.add(Insert_to_REcipe_Prop_Free_info__4());
        sync_procedure_list.add(Insert_to_REcipe_Prop_Free_Text__5());
        sync_procedure_list.add(Import_Ingred_Code_Primary__6());
        sync_procedure_list.add(Import_to_Recipe_Recipe_Actual__7());
        sync_procedure_list.add(Insert_Sequence_Steps_SSSS__8());
        sync_procedure_list.add(Insert_Sequence_Main__9());
        sync_procedure_list.add(Insert_Sequence_Commands__10());
        sync_procedure_list.add(Insert_Sequence_Steps__11());
        sync_procedure_list.add(Insert_Into_Recipe_Test_Procedures_12());
        sync_procedure_list.add(Insert_Into_Ingredient_phys_Properties_13());
        sync_procedure_list.add(INSERT_INTO_Ingred_Properties_14());
        sync_procedure_list.add(INSERT_INTO_Ingred_Preise_14_2());
        sync_procedure_list.add(Insert_Into_Ingredient_Vulco_Code_15());
        sync_procedure_list.add(Insert_Into_Ingredient_Aeging_Code_16());
        sync_procedure_list.add(Insert_Into_Ingredient_Warehouse_17());
        //=====================
       
//        
//        async_procedure_list.add(Insert_Vendor_17_2());
//        async_procedure_list.add(INSERT_INTO_TRADENAME_MAIN_18());
//        //    async_procedure_list.add(Insert_CASNO_ID_19());
//        async_procedure_list.add(Insert_GLTABLE_20());
//        async_procedure_list.add(Insert_Into_Recipe_csv_21());
    }

    public static String Import_to_Recipe_Group__1() {
        return "Import_to_Recipe_Group__1";
    }

    public static String Import_to_MixerInfoBasic_00__2() {
        return "Import_to_MixerInfoBasic_00__2";
    }

    public static String Import_to_Recipe_Prop_Main__3() {
        return "Import_to_Recipe_Prop_Main__3";
    }

    public static String Insert_to_REcipe_Prop_Free_info__4() {
        return "Insert_to_REcipe_Prop_Free_info__4";
    }

    public static String Insert_to_REcipe_Prop_Free_Text__5() {
        return "Insert_to_REcipe_Prop_Free_Text__5";
    }

    public static String Import_Ingred_Code_Primary__6() {
        return "Import_Ingred_Code_Primary__6";
    }

    public static String Import_to_Recipe_Recipe_Actual__7() {
        return "Import_to_Recipe_Recipe_Actual__7";
    }

    public static String Insert_Sequence_Steps_SSSS__8() {
        return "Insert_Sequence_Steps_SSSS__8";
    }

    public static String Insert_Sequence_Main__9() {
        return "Insert_Sequence_Main__9";
    }

    public static String Insert_Sequence_Commands__10() {
        return "Insert_Sequence_Commands__10";
    }

    public static String Insert_Sequence_Steps__11() {
        return "Insert_Sequence_Steps__11";
    }

    public static String Insert_Into_Recipe_Test_Procedures_12() {
        return "Insert_Into_Recipe_Test_Procedures_12";
    }

    public static String Insert_Into_Ingredient_phys_Properties_13() {
        return "Insert_Into_Ingredient_phys_Properties_13";
    }

    public static String INSERT_INTO_Ingred_Properties_14() {
        return "INSERT_INTO_Ingred_Properties_14";
    }

    public static String INSERT_INTO_Ingred_Preise_14_2() {
        return "INSERT_INTO_Ingred_Preise_14";
    }

    public static String Insert_Into_Ingredient_Vulco_Code_15() {
        return "Insert_Into_Ingredient_Vulco_Code_15";
    }

    public static String Insert_Into_Ingredient_Aeging_Code_16() {
        return "Insert_Into_Ingredient_Aeging_Code_16";
    }

    public static String Insert_Into_Ingredient_Warehouse_17() {
        return "Insert_Into_Ingredient_Warehouse_17";
    }

    public static String Insert_Vendor_17_2() {
        return "Insert_Vendor_17";
    }

    public static String INSERT_INTO_TRADENAME_MAIN_18() {
        return "INSERT_INTO_TRADENAME_MAIN_18";
    }

    public static String Insert_CASNO_ID_19() {
        return "Insert_CASNO_ID_19";
    }

    public static String Insert_GLTABLE_20() {
        return "Insert_GLTABLE_20";
    }

    public static String Insert_Into_Recipe_csv_21() {
        return "Insert_Into_Recipe_csv_21";
    }
}
