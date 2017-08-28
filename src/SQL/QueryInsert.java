/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SQL;

import DBT2.raw_material_vendor;
import Supplementary.HelpM;

/**
 *
 * @author KOCMOC
 */
public class QueryInsert {

    /**
     * You should always delete the item by "itemCode" before inserting the new
     * one
     *
     * @param itemCode
     * @param itemCode
     * @param prefferedLocation
     * @param shelfLifeDays
     * @param groupTechnologyCode
     * @param familyItemNumber
     * @param familySubGroup
     * @param dateExport
     * @param dateProcessed
     * @param status
     * @return
     */
    public static String insert_into_raw_materials_table(String itemCode,
            String itemName, String prefferedLocation, int shelfLifeDays, String groupTechnologyCode,
            String familyItemNumber, String rawMaterialStatus, String dateExport, String status) {
        String rst = "insert into " + DBT2.raw_material.tableName
                + " ("
                + DBT2.raw_material.itemCode
                + "," + DBT2.raw_material.itemName
                + "," + DBT2.raw_material.prefferedLocation
                + "," + DBT2.raw_material.shelfLifeDays
                + "," + DBT2.raw_material.groupTechnologyCode
                + "," + DBT2.raw_material.familyItemNumber
                + "," + DBT2.raw_material.rawMaterialStatus
                + "," + DBT2.raw_material.dateExport
                + "," + DBT2.raw_material.status + ")"
                + " values("
                + "'" + itemCode + "'"
                + ",'" + itemName + "'"
                + ",'" + prefferedLocation + "'"
                + "," + shelfLifeDays
                + ",'" + groupTechnologyCode + "'"
                + ",'" + familyItemNumber + "'"
                + ",'" + rawMaterialStatus + "'"
                + ",'" + dateExport + "'"
                + ",'" + status + "')";
        return rst;
    }

    public static String insert_into_recipe_table(String parentItemCode, String familySubGroup, String groupName, String workInstructions, String description, String recipeStatus, String developedOn, String lastUpdate, String dateExport, String status) {
        String rst = "insert into " + DBT2.recipe.tableName + " ("
                + DBT2.recipe.parentItemCode + ","
                + DBT2.recipe.familySubGroup + ","
                + DBT2.recipe.groupName + ","
                + DBT2.recipe.workInstructions + ","
                + DBT2.recipe.description + ","
                + DBT2.recipe.recipeStatus + ","
                + DBT2.recipe.developedOn + ","
                + DBT2.recipe.lastUpdate + ","
                + DBT2.recipe.dateExport + ","
                + DBT2.recipe.status + ")"
                + " values("
                + "'" + parentItemCode + "',"
                + "'" + familySubGroup + "',"
                + "'" + groupName + "',"
                + "'" + workInstructions + "',"
                + "'" + description + "',"
                + "'" + recipeStatus + "',"
                + "'" + developedOn + "',"
                + "'" + lastUpdate + "',"
                + "'" + dateExport + "',"
                + "'" + status
                + "')";
        return rst;
    }

    public static void main(String[] args) {
        System.out.println("" + insert_into_recipe_table_prepared());
    }

    public static String insert_into_recipe_table_prepared() {
        String rst = "insert into " + DBT2.recipe.tableName + " ("
                + DBT2.recipe.parentItemCode + ","
                + DBT2.recipe.familySubGroup + ","
                + DBT2.recipe.groupName + ","
                + DBT2.recipe.workInstructions + ","
                + DBT2.recipe.description + ","
                + DBT2.recipe.recipeStatus + ","
                + DBT2.recipe.developedOn + ","
                + DBT2.recipe.lastUpdate + ","
                + DBT2.recipe.dateExport + ","
                + DBT2.recipe.status + ")"
                + " values("
                + "?,"
                + "?,"
                + "?,"
                + "?,"
                + "?,"
                + "?,"
                + "?,"
                + "?,"
                + "?,"
                + "?"
                + ")";
        return rst;
    }

    public static String insert_into_recipe_raw_material_table(String parentItemCode, String itemCode, String pointOfUse, double requiredQuantity, String dateExport, String status) {
        String rst = "insert into " + DBT2.recipe_raw_material.tableName + " ("
                + DBT2.recipe_raw_material.parentItemCode + ","
                + DBT2.recipe_raw_material.itemCode + ","
                + DBT2.recipe_raw_material.pointOfUse + ","
                + DBT2.recipe_raw_material.requiredQuantity + ","
                + DBT2.recipe_raw_material.dateExport + ","
                + DBT2.recipe_raw_material.status
                + ")"
                + " values("
                + "'" + parentItemCode + "',"
                + "'" + itemCode + "',"
                + "'" + pointOfUse + "',"
                + "" + requiredQuantity + ","
                + "'" + dateExport + "',"
                + "'" + status + "'"
                + ")";
        return rst;
    }

    public static String insert_into_raw_material_vendor_table(String itemCode, String vendorName, String tradeName) {
        String rst = "insert into " + raw_material_vendor.tableName
                + " ("
                + raw_material_vendor.itemCode + ","
                + raw_material_vendor.vendorName + ","
                + raw_material_vendor.tradeName + ","
                + raw_material_vendor.dateExport
                + ")"
                + " values ("
                + "'" + itemCode + "',"
                + "'" + vendorName + "',"
                + "'" + tradeName + "',"
                + "'" + HelpM.get_proper_date_time_same_format_on_all_computers() + "'"
                + ")";
        return rst;
    }

    public static String insert_into_update_table(String table_name, String dataStream, String sender, String reciever, String status) {
        String rst = "insert into " + table_name + " ("
                + DBT2.update.dataStream + ","
                + DBT2.update.sender + ","
                + DBT2.update.reciever + ","
                + DBT2.update.dateSend + ","
                + DBT2.update.status + ")"
                + " values ("
                + "'" + dataStream + "',"
                + "'" + sender + "',"
                + "'" + reciever + "',"
                + "'" + HelpM.get_proper_date_time_same_format_on_all_computers() + "',"
                + "'" + status
                + "')";
        return rst;
    }
}
