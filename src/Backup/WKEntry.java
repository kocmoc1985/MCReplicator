/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Backup;

/**
 * OBS! See doc_06
 * @author KOCMOC
 */
public class WKEntry {
    private String recipe_code;
    private String command_name;
    private String command_value;//or phase for better understanding
    private String step;

    public WKEntry(String recipe_code, String command_name, String command_value, String step) {
        this.recipe_code = recipe_code;
        this.command_name = command_name;
        this.command_value = command_value;
        this.step = step;
    }

   
    
    
}
