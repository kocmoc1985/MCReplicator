/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SQL;

import Supplementary.GC;

/**
 *
 * @author KOCMOC
 */
public class QuerySpecial {
    
    public static String count_ammount_of_recipe_records() {
        String rst = "select count(distinct CODE) "
                + " from " + DBT.RecipePropMain.tableName
                + " where " + DBT.RecipePropMain.release + "='" + GC.SOLL_RECIPE_RELEASE + "'"
                + " and " + DBT.RecipePropMain.class_ + "='" + GC.SOLL_RECIPE_CLASS + "'";
        return rst;
    }
    
    public static String count_ammount_of_recipe_records_date_filter(String date_yyyy_MM_dd) {
        String rst = "select count(distinct CODE) "
                + " from " + DBT.RecipePropMain.tableName
                + " where " + DBT.RecipePropMain.lastUpdate + " >='" + date_yyyy_MM_dd + "'"
                + " and " + DBT.RecipePropMain.release + "='" + GC.SOLL_RECIPE_RELEASE + "'"
                + " and " + DBT.RecipePropMain.class_ + "='" + GC.SOLL_RECIPE_CLASS + "'";
        return rst;
    }

    //==========================================================================
    public static String count_ammount_of_raw_material_records() {
        String rst = "select count(*) "
                + " from " + DBT.MCCPWARE.tableName
                + " right outer join "
                + DBT.IngredMC1.tableName
                + " on " + DBT.MCCPWARE.tableName + "." + DBT.MCCPWARE.code
                + " = " + DBT.IngredMC1.tableName + "." + DBT.IngredMC1.code
                + DBT.A.COLLATE
                + " where "
                + DBT.IngredMC1.class_ + "='" + GC.SOLL_RAW_MAT_CLASS + "'";
        return rst;
    }
    
    public static String count_ammount_of_raw_material_records_date_filter(String date_yyyy_MM_dd) {
        if (date_yyyy_MM_dd.isEmpty()) {
            return count_ammount_of_raw_material_records();
        }
        String rst = "select count(*) "
                + " from " + DBT.MCCPWARE.tableName
                + " right outer join "
                + DBT.IngredMC1.tableName
                + " on " + DBT.MCCPWARE.tableName + "." + DBT.MCCPWARE.code
                + " = " + DBT.IngredMC1.tableName + "." + DBT.IngredMC1.code
                + DBT.A.COLLATE
                + " where "
                + DBT.IngredMC1.class_ + "='" + GC.SOLL_RAW_MAT_CLASS + "'"
                + " and "
                + DBT.IngredMC1.tableName + "." + DBT.IngredMC1.lastUpdate + " >='" + date_yyyy_MM_dd + "'";
        return rst;
    }

    //==========================================================================
    public static String count_ammount_of_recipe_raw_material_records() {
        String rst = "select count(*) "
                + " from " + DBT.RecipeMC1.tableName
                + " where " + DBT.RecipeMC1.release + " ='" + GC.SOLL_RECIPE_RELEASE + "'";
        return rst;
    }
    
    public static String count_ammount_of_recipe_raw_material_records_date_filter(String date_yyyy_MM_dd) {
        String rst = "select count(*) "
                + " from " + DBT.RecipeMC1.tableName
                + " inner join "
                + DBT.RecipePropMain.tableName
                + " on "
                + DBT.RecipePropMain.tableName + "." + DBT.RecipePropMain.parentItemCode
                + " = " + DBT.RecipeMC1.tableName + "." + DBT.RecipeMC1.parentItemCode
                + " where "
                + DBT.RecipeMC1.tableName + "." + DBT.RecipeMC1.release + "='" + GC.SOLL_RECIPE_RELEASE + "'"
                + " and "
                + DBT.RecipePropMain.tableName + "." + DBT.RecipePropMain.release + "='" + GC.SOLL_RECIPE_RELEASE + "'"
                + " and "
                + DBT.RecipePropMain.class_ + "='" + GC.SOLL_RECIPE_CLASS + "'"
                + " and "
                + DBT.RecipePropMain.lastUpdate + " >='" + date_yyyy_MM_dd + "'";
        return rst;
    }

    //==========================================================================
    //==========================================================================
    //==========================================================================
    /**
     *
     * @param parentItemCode
     * @return
     */
    public static String workInstructions_get_sequence_01(String parentItemCode) {
        String rst = "select distinct " + DBT.MixCTR_SequenceMC1.origin_code + ","
                + DBT.MixCTR_SequenceMC1.command_name + ","
                + DBT.MixCTR_SequenceMC1.command_parameter + ","
                //                + DBT.MixCTR_SequenceMC1.step
                + " CAST (" + DBT.MixCTR_SequenceMC1.step + " as decimal) as " + DBT.MixCTR_SequenceMC1.step
                + " from " + DBT.MixCTR_SequenceMC1.tableName
                + " where "
                + DBT.MixCTR_SequenceMC1.origin_code + "='" + parentItemCode + "'"
                + " and " + DBT.MixCTR_SequenceMC1.command_status + "='" + GC.SOLL_COMMAND_STATUS_MixCTR_Seq_MC1_TABLE + "'"
                + " and " + DBT.MixCTR_SequenceMC1.step + " !=''"
                + " order by CAST(" + DBT.MixCTR_SequenceMC1.step + " AS decimal)";
        return rst;
    }
    
     public static void main(String[] args) {
//        System.out.println("" + workInstructions_get_sequence_01("00-0-0000"));
//        System.out.println("" + workInstructions_get_material_of_addPhase_02("06-0-N4765", "2"));
//        System.out.println("" + pointOfUse_get_required_information("06-0-N476"));
//        System.out.println("" + preffered_location_get_required_info("00504"));
//        System.out.println("" + familyItemNumber_get_required_info("FBB"));
//        System.out.println("" + count_ammount_of_recipe_records());
//        System.out.println("" + count_ammount_of_recipe_records_date_filter(""));
//        System.out.println("" + workInstructions_get_sequence_01("00-0-0333"));
//        System.out.println("" + workInstructions_get_sequence_01("00-0-1134"));
        System.out.println("" + workInstructions_get_material_of_addPhase_02("95-0-1637", "1"));
//        System.out.println("" + workInstructions_get_sequence_01("95-0-1637"));
    }
    
    
    public static String workInstructions_get_material_of_addPhase_02(String parentItemCode, String phase) {
        double phase_ = Double.parseDouble(phase);
        int phase__ = (int) phase_;
        //==========
        String rst = "select " + DBT.RecipeMC1.parentItemCode + ","
                + DBT.RecipeMC1.itemCode + ","
                + DBT.RecipeMC1.silo_id + ","
                + DBT.RecipeMC1.phase
                + " from " + DBT.RecipeMC1.tableName
                + " where " + DBT.RecipeMC1.parentItemCode + "='" + parentItemCode + "'"
                + " and " + DBT.RecipeMC1.phase + "='" + phase__ + "'"
                + " and " + DBT.RecipeMC1.release + "='" + GC.SOLL_RECIPE_RELEASE + "'";
        return rst;
    }
    
   

    //==========================================================================
    //==========================================================================
    //==========================================================================
    public static String pointOfUse_get_required_information(String parentItemCode) {
        String rst = "select "
                + DBT.RecipeMC1.tableName + "." + DBT.RecipeMC1.parentItemCode + ","
                + DBT.RecipeMC1.itemCode + ","
                + DBT.RecipeMC1.tableName + "." + DBT.RecipeMC1.silo_id + ","
                + DBT.RecipeMC1.phase
                //                + DBT.MCCPWARE.tableName + "." + DBT.MCCPWARE.siloId
                + " from " + DBT.RecipeMC1.tableName
                + " inner join " + DBT.MCCPWARE.tableName
                + " on " + DBT.MCCPWARE.tableName + "." + DBT.MCCPWARE.code + " = "
                + DBT.RecipeMC1.tableName + "." + DBT.RecipeMC1.itemCode
                + DBT.A.COLLATE
                + " where " + DBT.RecipeMC1.tableName + "." + DBT.RecipeMC1.parentItemCode + " = '" + parentItemCode + "'"
                //                + " and " + DBT.RecipeMC1.tableName + "." + DBT.RecipeMC1.silo_id + " != '" + GC.SOLL_RECIPEMC1_SILO_ID_POINTOFUSE + "'"
                + " and " + DBT.RecipeMC1.tableName + "." + DBT.RecipeMC1.release + " = '" + GC.SOLL_RECIPE_RELEASE + "'"
                + " and " + DBT.MCCPWARE.tableName + "." + DBT.MCCPWARE.siloId + " ='" + GC.SOLL_MCCPWARE_SILO_ID_POINTOFUSE + "'"
                + " order by "
                + DBT.RecipeMC1.phase + ","
                + DBT.RecipeMC1.tableName + "." + DBT.RecipeMC1.silo_id;
        return rst;
    }

    //==========================================================================
    /**
     *
     * @param itemCode - material id
     * @return
     */
    public static String preffered_location_get_required_info(String itemCode) {
        String rst = "select "
                + DBT.IngredFeeinfoMc1.freeinfo + ","
                + DBT.IngredFeeinfoMc1.value + ","
                + DBT.IngredFeeinfoMc1.itemCode
                + " from " + DBT.IngredFeeinfoMc1.tableName
                + " where " + DBT.IngredFeeinfoMc1.freeinfo + " = '" + GC.SOLL_FREEINFO_VALUE + "'"
                + " and " + DBT.IngredFeeinfoMc1.itemCode + " = '" + itemCode + "'";
        return rst;
    }

    //==========================================================================
    /**
     *
     * @param groupTechnologyCode - find it in "raw_material" table (value
     * example = FBB)
     * @return
     */
    public static String familyItemNumber_get_required_info(String groupTechnologyCode) {
        String rst = "select "
                + DBT.Stoff_Gr$.grupp + ","
                + DBT.Stoff_Gr$.parentItemNumber
                + " from " + DBT.Stoff_Gr$.tableName
                + " where "
                + DBT.Stoff_Gr$.grupp + " = '" + groupTechnologyCode + "'";
        return rst;
    }

    //==========================================================================
    /**
     *
     * @param familySubGroup
     * @return
     */
    public static String get_groupname_for_recipe_table(String familySubGroup) {
        return "select "
                + DBT.Rzpt_Gr$.groupName
                + " from " + DBT.Rzpt_Gr$.tableName
                + " where " + DBT.Rzpt_Gr$.familySubGroup + " = '" + familySubGroup + "'";
    }
}
