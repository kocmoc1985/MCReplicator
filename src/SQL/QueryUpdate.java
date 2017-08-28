/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SQL;

import Supplementary.GP;
import Supplementary.HelpM;

/**
 *
 * @author KOCMOC
 */
public class QueryUpdate {

    public static String update_raw_material(String itemCode, String itemName,
            String prefferedLocation, int shelfLifeDays, String groupTechnologyCode,
            String familyItemNumber, String rawMaterialStatus, String dateExport, String status) {
        String rst = "update " + DBT2.raw_material.tableName + " set "
                + DBT2.raw_material.itemName + "='" + itemName + "',"
                + DBT2.raw_material.prefferedLocation + "='" + prefferedLocation + "',"
                + DBT2.raw_material.shelfLifeDays + "=" + shelfLifeDays + ","
                + DBT2.raw_material.groupTechnologyCode + "='" + groupTechnologyCode + "',"
                + DBT2.raw_material.familyItemNumber + "='" + familyItemNumber + "',"
                + DBT2.raw_material.rawMaterialStatus + "='" + rawMaterialStatus + "',"
                + DBT2.raw_material.dateExport + "='" + dateExport + "',"
                + DBT2.raw_material.status + "='" + status + "'"
                + " where " + DBT2.raw_material.itemCode + "='" + itemCode + "'";
        return rst;
    }

    public static String update_recipe(String parentItemCode, String familySubGroup, String groupName, String workInstructions, String description, String recipeStatus, String developedOn, String lastUpdate, String dateExport, String status) {
        String rst = "update " + DBT2.recipe.tableName + " set "
                + DBT2.recipe.parentItemCode + "='" + parentItemCode + "',"
                + DBT2.recipe.familySubGroup + "='" + familySubGroup + "',"
                + DBT2.recipe.groupName + "='" + groupName + "',"
                + DBT2.recipe.workInstructions + "='" + workInstructions + "',"
                + DBT2.recipe.description + "='" + description + "',"
                + DBT2.recipe.recipeStatus + "='" + recipeStatus + "',"
                + DBT2.recipe.developedOn + "='" + developedOn + "',"
                + DBT2.recipe.lastUpdate + "='" + lastUpdate + "',"
                + DBT2.recipe.dateExport + "='" + dateExport + "',"
                + DBT2.recipe.status + "='" + status + "'"
                + " where " + DBT2.recipe.parentItemCode + "='" + parentItemCode + "'";
        return rst;
    }

    public static String update_recipe_raw_material(String parentItemCode, String itemCode, String pointOfUse, double requiredQuantity, String dateExport, String status) {
        String rst = "update " + DBT2.recipe_raw_material.tableName + " set "
                + DBT2.recipe_raw_material.parentItemCode + "='" + parentItemCode + "',"
                + DBT2.recipe_raw_material.itemCode + "='" + itemCode + "',"
                + DBT2.recipe_raw_material.pointOfUse + "='" + pointOfUse + "',"
                + DBT2.recipe_raw_material.requiredQuantity + "=" + requiredQuantity + ","
                + DBT2.recipe_raw_material.dateExport + "='" + dateExport + "',"
                + DBT2.recipe_raw_material.status + "='" + status + "'"
                + " where " + DBT2.recipe_raw_material.parentItemCode + "='" + parentItemCode + "'"
                + " and " + DBT2.recipe_raw_material.itemCode + "='" + itemCode + "'";
        return rst;
    }

    //==========================================================================
    //==========================================================================
    public static String update_dateProcessed_any_table(String tableName, String row_id) {
        String rst = "update " + tableName
                + " set " + GP.dateProcessed + "='"
                + HelpM.get_proper_date_time_same_format_on_all_computers() + "'"
                + " where " + GP.rowId + "=" + row_id;
        return rst;
    }

    public static void main(String[] args) {
//        System.out.println("" + update_raw_material("a01", "a02", "a03", 5, "a04", "a05", "a06", "a07", "a08"));
//        System.out.println("" + update_recipe("adasd","AA", "dasdasd", "2013-12-12", "test"));
//        System.out.println("" + update_recipe_raw_material("A01", "B02", "aaaa", 2.05, "2013-12-11", "test"));
    }
}
