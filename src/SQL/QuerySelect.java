/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SQL;

import DBT.IngredTradeName;
import DBT2.raw_material_vendor;
import DBT2.vendor;
import Supplementary.GC;

/**
 *
 * @author KOCMOC
 */
public class QuerySelect {

    /**
     * By incomplete i mean that information from table MCCPWARE will be missing
     * Note: when we use "outer join" we get all rows from both of the tables
     * even if there is no binding by id (code) on both sides
     *
     * @tested 2013-12-10
     * @return
     */
    public static String get_raw_material_object_incomplete_no_filter() {
        String rst = "select "
                + DBT.IngredMC1.tableName + "." + DBT.IngredMC1.code
                + "," + DBT.IngredMC1.itemName
                + "," + DBT.MCCPWARE.shelfLifeDays
                + "," + DBT.IngredMC1.groupTechnologyCode
                + "," + DBT.IngredMC1.rawMaterialStatus
                + " from " + DBT.MCCPWARE.tableName
                + " right outer join "
                + DBT.IngredMC1.tableName
                + " on " + DBT.MCCPWARE.tableName + "." + DBT.MCCPWARE.code
                + " = " + DBT.IngredMC1.tableName + "." + DBT.IngredMC1.code
                + DBT.A.COLLATE
                + " where "
                + DBT.IngredMC1.class_ + " = '" + GC.SOLL_RAW_MAT_CLASS + "'";
        return rst;
    }

    public static String get_raw_material_object_incomplete_date_filter(String date_yyyy_MM_dd) {
        String rst = "select "
                + DBT.IngredMC1.tableName + "." + DBT.IngredMC1.code
                + "," + DBT.IngredMC1.itemName
                + "," + DBT.MCCPWARE.shelfLifeDays
                + "," + DBT.IngredMC1.groupTechnologyCode
                + "," + DBT.IngredMC1.rawMaterialStatus
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

    /*
     * @tested 2013-12-05
     */
    public static String get_recipe_codes_distinct() {
        String rst = "select distinct "
                + DBT.RecipePropMain.parentItemCode + ","
                + DBT.RecipePropMain.familySubGroup + ","
                + DBT.RecipePropMain.description + ","
                + DBT.RecipePropMain.recipeStatus + ","
                + DBT.RecipePropMain.developedOn + ","
                + DBT.RecipePropMain.lastUpdate
                + " from " + DBT.RecipePropMain.tableName
                + " where " + DBT.RecipePropMain.release + "='" + GC.SOLL_RECIPE_RELEASE + "'"
                + " and " + DBT.RecipePropMain.class_ + "='" + GC.SOLL_RECIPE_CLASS + "'"
                + " order by " + DBT.RecipePropMain.parentItemCode + " asc";
        return rst;
    }

    /**
     * @tested 2013-12-12
     * @param date_yyyy_MM_dd
     * @return
     */
    public static String get_recipe_codes_distinct_date_filter(String date_yyyy_MM_dd) {
        String rst = "select distinct "
                + DBT.RecipePropMain.parentItemCode + ","
                + DBT.RecipePropMain.familySubGroup + ","
                + DBT.RecipePropMain.description + ","
                + DBT.RecipePropMain.recipeStatus + ","
                + DBT.RecipePropMain.developedOn + ","
                + DBT.RecipePropMain.lastUpdate
                + " from " + DBT.RecipePropMain.tableName
                + " where "
                + DBT.RecipePropMain.tableName + "." + DBT.RecipePropMain.lastUpdate + " >='" + date_yyyy_MM_dd + "'"
                + " and " + DBT.RecipePropMain.release + "='" + GC.SOLL_RECIPE_RELEASE + "'"
                + " and " + DBT.RecipePropMain.class_ + "='" + GC.SOLL_RECIPE_CLASS + "'"
                + " order by " + DBT.RecipePropMain.parentItemCode + " asc";
        return rst;
    }

    //==========================================================================
    /**
     * OBS! Look at doc_01.jpg
     *
     * @return
     */
    public static String get_raw_materials_corresponding_to_recipe(String parentItemCode) {
        String rst = "select "
                + DBT.RecipeMC1.parentItemCode
                + "," + DBT.RecipeMC1.itemCode
                + "," + DBT.RecipeMC1.requiredQuantity
                + "," + DBT.RecipeMC1.matIndex
                + " from " + DBT.RecipeMC1.tableName
                + " where "
                + DBT.RecipeMC1.parentItemCode + "='" + parentItemCode + "'"
                + " and "
                + DBT.RecipeMC1.release + "='" + GC.SOLL_RECIPE_RELEASE + "'"
                + " order by " + DBT.RecipeMC1.phase + " asc";
        return rst;
    }

    public static void main(String[] args) {
//        System.out.println("" + get_raw_material_object_incomplete_no_filter());
//        System.out.println("" + get_raw_material_object_incomplete_date_filter("2013-09-01"));
//        System.out.println("" + get_venodrs());
//        System.out.println("" + get_vendors_date_filter("2014-01-01"));
        System.out.println("" + get_raw_materials_corresponding_to_recipe("12-0-0034"));
    }
    //=========================================================================

    public static String get_venodrs() {
        String rst = "select " + vendor.vendor_name
                + "," + vendor.row_id
                + " from " + vendor.tableName;
//                + " where " + vendor.dateProcessed + " is NULL";
        return rst;
    }

    public static String get_vendors_date_filter(String date_yyyy_MM_dd) {
        String rst = "select " + vendor.vendor_name
                + "," + vendor.row_id
                + " from " + vendor.tableName
                + " where " + vendor.dateExport + " >='" + date_yyyy_MM_dd + "'"
                + " and " + vendor.dateProcessed + " is NULL";
        return rst;
    }

    //=========================================================================
    public static String get_raw_materials_corresponding_to_vendor(String vendor_name) {
        String rst = "select " + IngredTradeName.itemCode
                + "," + IngredTradeName.tradename
                + " from " + IngredTradeName.tableName
                + " where " + IngredTradeName.vendor_name
                + " like '%" + vendor_name + "%'";
        return rst;
    }

    //=========================================================================
    public static String get_latest_update_date_for_raw_material_flow() {
        String rst = "select top 1 * from " + DBT2.update.table_name
                + " where " + DBT2.update.dataStream + "='" + DBT2.update.RAW_MATERIAL_DATA_FLOW + "'"
                + " order by " + DBT2.update.dateSend + " desc";
        return rst;
    }

    public static String get_latest_update_date_for_recipe_flow() {
        String rst = "select top 1 * from " + DBT2.update.table_name
                + " where " + DBT2.update.dataStream + "='" + DBT2.update.RECIPE_RAW_MATERIAL_DATA_FLOW + "'"
                + " order by " + DBT2.update.dateSend + " desc";
        return rst;
    }

    //=========================================================================
    //=========================================================================
    //=========================================================================
    /**
     * This is used to verify if the table contains entries with given
     * "itemCode"
     *
     * @param itemCode = rawMaterialId
     * @return
     */
    public static String table_contains_raw_material(String itemCode) {
        String rst = "select * from " + DBT2.raw_material.tableName
                + " where " + DBT2.raw_material.itemCode + "='" + itemCode + "'";
        return rst;
    }

    public static String table_contains_recipe(String parentItemCode) {
        String rst = "select * from " + DBT2.recipe.tableName
                + " where " + DBT2.recipe.parentItemCode + "='" + parentItemCode + "'";
        return rst;
    }

    public static String table_contains_recipe_raw_material(String parentItemCode, String itemCode) {
        String rst = "select * from " + DBT2.recipe_raw_material.tableName
                + " where " + DBT2.recipe_raw_material.parentItemCode + "='" + parentItemCode + "'"
                + " and " + DBT2.recipe_raw_material.itemCode + "='" + itemCode + "'";
        return rst;
    }

    public static String table_contains_raw_material_vendor(String itemCode, String vendorId) {
        String rst = "select * from " + raw_material_vendor.tableName
                + " where " + raw_material_vendor.itemCode + " = '" + itemCode + "'"
                + " and " + raw_material_vendor.vendorName + " ='" + vendorId + "'";
        return rst;
    }
}
