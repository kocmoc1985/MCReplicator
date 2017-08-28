/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qew_dbf_replicator;

/**
 *
 * @author KOCMOC
 */
public class SQL_COMMANDS {

    public static String delete_from_tables_before_replication() {
        return "DELETE FROM [NCPD].[dbo].[Recipe_Sequence_Steps]\n"
                + "DELETE FROM [NCPD].[dbo].Recipe_Sequence_Main\n"
                + "DELETE FROM [NCPD].[dbo].[Recipe_Sequence_Commands]\n"
                + "DELETE FROM [NCPD].[dbo].[Recipe_Prop_Free_Info]\n"
                + "DELETE FROM [NCPD].[dbo].[Recipe_Prop_Free_Text]\n"
                + "DELETE FROM [NCPD].[dbo].[Recipe_Recipe]\n"
                + "DELETE FROM [NCPD].[dbo].[Recipe_Prop_Main]\n"
                + "DELETE FROM [NCPD].[dbo].[Mixer_InfoBasic]\n"
                + "DELETE FROM [NCPD].[dbo].[Recipe_Group]\n"
                //                + "--Ingredient Group tables\n"
                + "DELETE FROM [NCPD].[dbo].[Ingredient_phys_Properties]\n"
                + "DELETE FROM [NCPD].[dbo].[Ingredient_Code]\n"
                + "DELETE FROM [NCPD].[dbo].[Ingred_Info]\n"
                + "DELETE FROM [NCPD].[dbo].[Ingredient_Warehouse]\n"
                + "DELETE FROM [NCPD].[dbo].[Ingred_Preise]\n"
                + "DELETE FROM [NCPD].[dbo].[Ingred_Properties]\n"
                + "DELETE FROM [NCPD].[dbo].[_INTRF_IngredientCode_ID__Ingredient_Aeging_Code_ID]\n"
                + "DELETE FROM [NCPD].[dbo].[_INTRF_IngredientCode_ID__Ingredient_Vulco_Code_ID]\n"
                + "DELETE FROM [NCPD].[dbo].[Ingredient_Aeging_Code]\n"
                + "DELETE FROM [NCPD].[dbo].[Ingredient_Vulco_Code]\n"
                //                + "--Ingred Safety\n"
                + "DELETE FROM [NCPD].[dbo].[Ingred_Safety_Components]\n"
                + "DELETE FROM [NCPD].[dbo].[Ingred_Safety_Main]\n"
                + "DELETE FROM [NCPD].[dbo].[Ingred_Preise]\n"
                //                + "DELETE FROM [NCPD].[dbo].[Casno_ID]\n"
                + "DELETE FROM [NCPD].[dbo].[_INTRF_IngredientCode_ID__Tradename_Main_ID]\n"
                + "DELETE FROM [NCPD].[dbo].[Tradename_Main]\n"
                + "DELETE FROM [NCPD].[dbo].[_INTRF_IngredientCode_ID__Vendor_ID]\n"
                + "DELETE FROM [NCPD].[dbo].[Vendor_Contact]\n"
                + "DELETE FROM [NCPD].[dbo].[Vendor]";
    }
}
