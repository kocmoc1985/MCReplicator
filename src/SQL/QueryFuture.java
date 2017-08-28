/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SQL;

/**
 *
 * @author KOCMOC
 */
public class QueryFuture {

    /**
     * This provides the same results as "get_recipe_with_releases_2(..)"
     * @param parentItemCode
     * @return 
     */
    public static String get_recipe_with_releases(String parentItemCode) {
        String rst = "select "
                + DBT.RecipePropMain.parentItemCode + ","
                + DBT.RecipePropMain.release
                + " from " + DBT.RecipePropMain.tableName
                + " where " + DBT.RecipePropMain.parentItemCode + "='" + parentItemCode + "'"
                + " order by " + DBT.RecipePropMain.parentItemCode + " asc";
        return rst;
    }
    
    /**
     * This provides the same results as "get_recipe_with_releases(..)"
     * @param parentItemCode
     * @return 
     */
    public static String get_recipe_with_releases_2(String parentItemCode) {
        String rst = "select "
                + DBT.RecipeMC1.parentItemCode + ","
                + DBT.RecipeMC1.release
                + " from " + DBT.RecipeMC1.tableName
                + " where " + DBT.RecipeMC1.parentItemCode + "='" + parentItemCode + "'"
                + " order by " + DBT.RecipeMC1.parentItemCode + " asc";
        return rst;
    }
    
     

    /**
     * Good example recipes: 00-0-L814, 00-L-3215
     * @param parentItemCode
     * @param release
     * @return 
     */
    public static String get_raw_materials_corresponding_to_recipe_and_release_proper(String parentItemCode, String release) {
        String rst = "select "
                + DBT.RecipeMC1.parentItemCode + ","
                + DBT.RecipeMC1.release + ","
                + DBT.RecipeMC1.itemCode
                + " from " + DBT.RecipeMC1.tableName
                + " where " + DBT.RecipeMC1.parentItemCode + "='" + parentItemCode + "'"
                + " and " + DBT.RecipeMC1.release + "='" + release + "'"
                + " order by " + DBT.RecipeMC1.itemCode + " asc";
        return rst;
    }
    
    public static void main(String[] args) {
        System.out.println("" + get_raw_materials_corresponding_to_recipe_and_release_proper("00-0-L814", "A"));
          System.out.println("" + get_recipe_with_releases_2("00-0-L814"));
          System.out.println(""  + get_recipe_with_releases("00-0-L814"));
      }

    
    //=========================================================================
    /**
     * Recipe 00-0-L814 shows very strange results
     *
     * @param parentItemCode
     * @param release
     * @deprecated
     * @return
     */
    public static String get_raw_materials_corresponding_to_recipe_and_release(String parentItemCode, String release) {
        String rst = "select "
                + DBT.RecipeMC1.tableName + "." + DBT.RecipeMC1.parentItemCode
                + "," + DBT.RecipeMC1.itemCode
                + "," + DBT.RecipeMC1.requiredQuantity
                + "," + DBT.RecipeMC1.matIndex
                + "," + DBT.RecipePropMain.tableName + "." + DBT.RecipePropMain.release
                + " from " + DBT.RecipeMC1.tableName
                + " inner join "
                + DBT.RecipePropMain.tableName
                + " on " + DBT.RecipePropMain.tableName + "." + DBT.RecipePropMain.parentItemCode
                + " = " + DBT.RecipeMC1.tableName + "." + DBT.RecipeMC1.parentItemCode
                + " where "
                + DBT.RecipeMC1.tableName + "." + DBT.RecipeMC1.parentItemCode + "='" + parentItemCode + "'"
                + " and "
                + DBT.RecipePropMain.tableName + "." + DBT.RecipePropMain.release + "='" + release + "'"
                + " order by " + DBT.RecipeMC1.itemCode + " asc";
        return rst;
    }
}
