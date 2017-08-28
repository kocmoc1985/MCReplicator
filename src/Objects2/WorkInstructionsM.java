/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Objects2;

import java.util.HashMap;

/**
 *
 * @author KOCMOC
 */
public class WorkInstructionsM {

    private final static String WAIT_UNTIL_TIME = "WAIT UNTIL (time)";
    private final static String WAIT_UNTIL_TEMP = "WAIT UNTIL (temp)";
    private final static String SET_ROTOR_SPEED = "SET ROTOR (speed)";
    private final static String SWEEP = "SWEEP (time)";
    public final static String ADD_PHASE = "ADD PHASE";
    private final static String SET_RAM = "SET RAM (0=UP/2=HALF/1=DOWN)";
    private final static String SET_DISCHARGE = "SET DISCHARGE (1=OPEN/0=CLOSE)";
    //=====================
    private final static HashMap dict_map = new HashMap();

    static {
        dict_map.put(WAIT_UNTIL_TIME, "time ");
        dict_map.put(WAIT_UNTIL_TEMP, "temp ");
        dict_map.put(SET_ROTOR_SPEED, "speed ");
        dict_map.put(SWEEP, "sweep ");
        dict_map.put(ADD_PHASE, "add ");
        dict_map.put(SET_RAM, "ram ");
        dict_map.put(SET_DISCHARGE, "disch ");
    }
    //=====================
    private String WORK_INSTRUCTION = "";

    public boolean add(String command_name, String command_value) {

        if (command_name.contains(ADD_PHASE) || command_name.contains(SET_DISCHARGE)) {
            WORK_INSTRUCTION += dict_map.get(command_name) + " " + command_value + "; \n\n";
        } else if (command_name.contains(SET_RAM)) {
            //Ram up command should not be visible according to Cor (see letter dated 2014-01-08)
//            WORK_INSTRUCTION += dict_map.get(command_name) + " " + process_set_ram(command_value) + ", ";
        } else {
            WORK_INSTRUCTION += dict_map.get(command_name) + " " + command_value + ", ";
        }

        if (command_name.contains(SET_DISCHARGE)) {
            return true;
        }

        return false;
    }
    
     public String getWorkInstruction() {
        return WORK_INSTRUCTION;
    }

    private String process_set_ram(String value) {
        if (value == null) {
            return "";
        }
        value = value.trim();
        switch (value) {
            case "0":
            case "0.0":
                return "up";
            case "1":
            case "1.0":
                return "down";
            case "2":
            case "2.0":
                return "half";
            default:
                return "";
        }
    }
}
