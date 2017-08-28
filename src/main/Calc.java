/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import Objects2.PointOfUseEntry;
import Objects2.WorkInstructionsM;
import SQL.QuerySpecial;
import SQL.Sql;
import Supplementary.GP;
import Supplementary.HelpM;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author KOCMOC
 */
public class Calc {
    
    private final Sql sql = new Sql();
    public static final String LOG_FILE = "calc.log";
    
    public Calc() {
        connectReconnect();
    }
    
    private void connectReconnect() {
        try {
            sql.connect(GP.SQL_SRC_HOST, GP.SQL_SRC_PORT, GP.SQL_SRC_DB_NAME,
                    GP.SQL_SRC_USERNAME, GP.SQL_SRC_PASSWORD);
        } catch (SQLException ex) {
            Logger.getLogger(MCReplicator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //==========================================================================
    /**
     * OBS! See doc_06
     *
     * @param parentItemCode
     * @return
     */
    /**
     *
     * @param parentItemCode - in some cases when a release of a recipe don't
     * have workinstructions i must go back and search for workInstructions in
     * previous releases - so this parameter can be the previous release.
     * @param parentItemCode_origin - This is the recipe nr which is never
     * changed!!
     * @return
     */
    public String calc_WorkInstructions_recipe_table(String parentItemCode, String parentItemCode_origin) {
        try {
            return h01_calc_workInstructions(parentItemCode, parentItemCode_origin);
        } catch (SQLException ex) {
            Logger.getLogger(Calc.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }
        
    }

    /**
     * OBS! See doc_06
     *
     * @param parentItemCode
     * @return
     * @throws SQLException
     */
    private String h01_calc_workInstructions(String parentItemCode, String parentItemCode_origin) throws SQLException {
//        parentItemCode = "27-0-N911";//DEBUG
        ResultSet rs = execute(SQL.QuerySpecial.workInstructions_get_sequence_01(parentItemCode));
        
        WorkInstructionsM instructions = new WorkInstructionsM();
        
        while (rs.next()) {
            String command_name = rs.getString(DBT.MixCTR_SequenceMC1.command_name).trim();
            String command_value = rs.getString(DBT.MixCTR_SequenceMC1.command_parameter).trim();
            
            if (command_name.contains(WorkInstructionsM.ADD_PHASE)) {
                String value = h02_find_rawmaterials_of_a_phase(parentItemCode_origin, command_value);
                instructions.add(command_name, value);
            } else {
                boolean set_disch_happend = instructions.add(command_name, command_value);
                if (set_disch_happend) {
                    break;// it doesn't make sence to continue if "SET DISCHARGE" command 
                }
            }
        }
        return instructions.getWorkInstruction().replaceAll("null", "");
    }

    /**
     * OBS! See doc_06
     *
     * @param parentItemCode
     * @param phase
     * @return
     * @throws SQLException
     */
    private String h02_find_rawmaterials_of_a_phase(String parentItemCode, String phase) throws SQLException {
        ResultSet rs = execute_2(SQL.QuerySpecial.workInstructions_get_material_of_addPhase_02(parentItemCode, phase));
        String str = "";
        while (rs.next()) {
            str += rs.getString(DBT.RecipeMC1.itemCode).trim() + ",";
        }
        return HelpM.delete_last_letter_in_string(str);
    }

    //==========================================================================
    //==========================================================================
    /**
     *
     * @param parentItemCode
     * @param itemCode
     * @return
     */
    public HashMap calc_PointOfUse_recipe_raw_material_table(String parentItemCode) throws SQLException {
        ResultSet rs = execute(SQL.QuerySpecial.pointOfUse_get_required_information(parentItemCode));
        
        ArrayList<PointOfUseEntry> list = new ArrayList<>();
        ArrayList<String> silo_id_list = new ArrayList<>();
        
        String FIRST_LETTER = "";
        while (rs.next()) {
            String recipe_id = rs.getString(DBT.RecipeMC1.parentItemCode).trim();
            String material_id = rs.getString(DBT.RecipeMC1.itemCode).trim();
            String silo_id = rs.getString(DBT.RecipeMC1.silo_id).trim();

            //Build "silo_id/silo_letter list"
            //Makes an arrayList containing: {B,D,E}
            add_to_arr_list_no_duplicates(silo_id_list, silo_id);
            
            PointOfUseEntry poue = new PointOfUseEntry(recipe_id, material_id, silo_id);
            list.add(poue);
            if (list.size() == 1) {
                FIRST_LETTER = silo_id;
            }
        }
        
        HashMap pointOfUse_map = new HashMap();
        
        for (PointOfUseEntry entry : list) {
            String itemCode = entry.getItemCode();
            String silo_id = entry.getSiloId();
//            String p_o_u = calc_point_of_use(FIRST_LETTER, silo_id);
            String p_o_u = calc_point_of_use_new(FIRST_LETTER, silo_id, silo_id_list);
            pointOfUse_map.put(itemCode, p_o_u);
        }
        
        return pointOfUse_map;
    }
    
    private static String calc_point_of_use_new(String first_letter, String letter_to_find_nr_for, ArrayList<String> list) {
        first_letter = first_letter.toUpperCase().trim();
        letter_to_find_nr_for = letter_to_find_nr_for.toUpperCase().trim();
        
        boolean zero_found = false;
        int nr = 0;
        
        for (String act_letter : list) {
            if (act_letter.equals(first_letter)) {
                zero_found = true;
            } else if (zero_found) {
                nr++;
            }
            
            if (act_letter.equals(letter_to_find_nr_for)) {
                return "2" + nr;
            }
        }
        
        return "";
    }
    
    private static void add_to_arr_list_no_duplicates(ArrayList<String> list, String str_to_add) {
        if (list.contains(str_to_add) == false) {
            list.add(str_to_add);
        }
    }

    /**
     * @deprecated @param first_letter
     * @param letter_to_find_nr_for
     * @return
     */
    private static String calc_point_of_use(String first_letter, String letter_to_find_nr_for) {
        first_letter = first_letter.toUpperCase().trim();
        letter_to_find_nr_for = letter_to_find_nr_for.toUpperCase().trim();
        
        String[] arr = {"0", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M"};

//        String[] arr = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M"};
        boolean zero_found = false;
        int nr = 0;
        for (String act_letter : arr) {
            if (act_letter.equals(first_letter)) {
                zero_found = true;
            } else if (zero_found) {
                nr++;
            }
            
            if (act_letter.equals(letter_to_find_nr_for)) {
                return "2" + nr;
            }
        }
        
        return "";
    }

    //==========================================================================
    //==========================================================================
    /**
     *
     * @param itemCode
     * @return
     * @throws SQLException
     */
    public String get_prefferedLocation(String itemCode) throws SQLException {
        ResultSet rs = execute(SQL.QuerySpecial.preffered_location_get_required_info(itemCode));
        
        if (rs.next()) {
            return rs.getString(DBT.IngredFeeinfoMc1.value);
        } else {
            return "";
        }
    }
    
    public String get_familyItemNumber(String groupTechnologyCode) throws SQLException {
        ResultSet rs = execute(SQL.QuerySpecial.familyItemNumber_get_required_info(groupTechnologyCode));
        
        if (rs.next()) {
            return rs.getString(DBT.Stoff_Gr$.parentItemNumber);
        } else {
            return "";
        }
    }
    
    public String get_groupname_for_recipe(String familySubGroup) throws SQLException {
        ResultSet rs = execute(QuerySpecial.get_groupname_for_recipe_table(familySubGroup));
        
        if (rs.next()) {
            return rs.getString(DBT.Rzpt_Gr$.groupName);
        } else {
            return "";
        }
    }

    //==========================================================================
    //==========================================================================
    private ResultSet execute(String query) {
        try {
            ResultSet rs = sql.execute(query);
            return rs;
        } catch (SQLException ex) {
            Logger.getLogger(Calc.class.getName()).log(Level.SEVERE, null, ex);
            sql.sql_error_handling(ex);
            sql.loggSqlExceptionWithQuerry(LOG_FILE, ex, query);
            return null;
        }
    }
    
    private ResultSet execute_2(String query) {
        try {
            ResultSet rs = sql.execute2(query);
            return rs;
        } catch (SQLException ex) {
            Logger.getLogger(Calc.class.getName()).log(Level.SEVERE, null, ex);
            sql.sql_error_handling(ex);
            return null;
        }
    }
}
