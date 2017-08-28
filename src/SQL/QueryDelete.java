/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SQL;

/**
 *
 * @author KOCMOC
 */
public class QueryDelete {

    public static String delete_tables_before_executing_recipe_flow_initial() {
        String rst = "delete from " + DBT2.recipe_raw_material.tableName + ";"
                + " delete from " + DBT2.recipe.tableName + ";"
                + " delete from " + DBT2.raw_material.tableName + ";";
        return rst;
    }

    public static void main(String[] args) {
//        System.out.println("" + delete_from_recipe_raw_material_before_inserting("A01"));
//        System.out.println("" + delete_raw_materials_belonging_to_given_recipe("A01"));
        System.out.println(delete_tables_before_executing_recipe_flow_initial());
    }

    /**
     *
     * @param parentItemCode = recipeID
     * @param itemCode = rawMaterialID
     * @return
     */
    public static String delete_from_recipe_raw_material_before_inserting(String parentItemCode) {
        String rst = "delete from " + DBT2.recipe_raw_material.tableName
                + " where " + DBT2.recipe_raw_material.parentItemCode + " ='" + parentItemCode + "'";
        return rst;
    }

    /**
     * This cannot be done for the same reason as the method below
     *
     * @deprecated
     * @param itemCode = rawMaterialID
     * @return
     */
    public static String delete_from_raw_material_before_inserting(String itemCode) {
        String rst = "delete from " + DBT2.raw_material.tableName
                + " where " + DBT2.raw_material.itemCode + " = '" + itemCode + "'";
        return rst;
    }

    /**
     * This can be a good example but this cannot work, because a rawMaterial
     * belongs to many different recipes, so i can't delete it from
     * "raw_material" table when it's still used in "recipe_raw_material"
     *
     * @deprecated
     * @param parentItemCode
     * @return
     */
    public static String delete_raw_materials_belonging_to_given_recipe(String parentItemCode) {
        String rst = "delete from  " + DBT2.raw_material.tableName
                + " inner join " + DBT2.recipe_raw_material.tableName
                + " on " + DBT2.recipe_raw_material.tableName + "." + DBT2.recipe_raw_material.itemCode
                + " = " + DBT2.raw_material.tableName + "." + DBT2.raw_material.itemCode
                + " where " + DBT2.recipe_raw_material.parentItemCode
                + " = '" + parentItemCode + "'";
        return rst;
    }
}
