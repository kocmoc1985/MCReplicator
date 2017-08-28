/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Backup;

/**
 * OBS! See doc_06
 * @author KOCMOC
 */
public class AddPhaseEntry {

    private String recipe_code;
    private String material_code;
    private String silo_id;
    private int phase;

    public AddPhaseEntry(String recipe_code, String material_code, String silo_id, int phase) {
        this.recipe_code = recipe_code;
        this.material_code = material_code;
        this.silo_id = silo_id;
        this.phase = phase;
    }
}
