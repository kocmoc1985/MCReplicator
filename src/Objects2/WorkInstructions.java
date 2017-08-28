/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Objects2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * 
 *@deprecated - Use "WorkInstructionsM"
 * @author KOCMOC
 */
public class WorkInstructions {

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
    private boolean new_row = true;
    private int sweep_nr;
    private LinkedList<SweepMade> sweep_made_list = new LinkedList<>();
    private BeforeSweep beforeSweep;
    private boolean sweep_made = false;
    //=====================
    private ArrayList<String> command_sequence_list = new ArrayList();
    //=====================
    private String WORK_INSTRUCTION = "";

    /**
     *
     * @param command_name
     * @param command_value
     * @return
     */
    public boolean add(String command_name, String command_value) {
        //OBS! Very important
        if (command_name.contains(SWEEP) == false) {
            add_to_command_sequence_list(command_name, -1);
        }

        if (command_name.contains(SWEEP)) {
            sweep_nr++;
            sweep_made = true;
            SweepMade sm;
            if (sweep_nr == 1) { // only "SWEEP 1" has "BeforeSweep"!!
                sm = new SweepMade(sweep_nr, beforeSweep);
            } else {
                sm = new SweepMade(sweep_nr, null);
            }

            sm.add(command_name, command_value);
            sweep_made_list.add(sm);
            return false;
        } else {

            if (sweep_made) {
                sweep_made_list.peekLast().add(command_name, command_value);
            }

            if (new_row && sweep_made == false) {
                new_row = false;
                beforeSweep = new BeforeSweep(sweep_nr);
            }

            if (sweep_made == false) {
                beforeSweep.add(command_name, command_value);
            }
        }

        //==
        if (command_name.contains(ADD_PHASE)) {
            build_row();
        }
        if (command_name.contains(SET_DISCHARGE)) {
            build_row();
            return true;
        }
        return false;
    }

    private void add_to_command_sequence_list(String command_name, int pos) {
        if (command_sequence_list.contains(command_name) == false) {
            if (pos == -1) { //default
                command_sequence_list.add(command_name);
            } else {
                command_sequence_list.add(pos, command_name);
            }

        }
    }

    private void reset_command_sequence_list() {
        command_sequence_list = new ArrayList<>();
    }

    private void build_row() {
        if (sweep_made_list.isEmpty()) {
            build_row_no_sweep();
        } else {
            build_row_with_sweeps();
        }
        //==
        sweep_made_list = new LinkedList<>();
        beforeSweep = null;
        sweep_nr = 0;
        sweep_made = false;
        new_row = true;
    }

    private void build_row_no_sweep() {

        String rst = build(beforeSweep);
        reset_command_sequence_list();

        WORK_INSTRUCTION += rst + "\n\n";
    }

    private void build_row_with_sweeps() {
        String rst = "";

        for (SweepMade sweepMade : sweep_made_list) {
            BeforeSweep bs = sweepMade.getBeforeSweep();

            if (bs != null) {
                rst += build(bs);
            }

            rst += build(sweepMade);
        }
        reset_command_sequence_list();

        WORK_INSTRUCTION += rst + "\n\n";
    }

    /**
     *
     * @param sif
     * @param cmd_arr
     */
    private String build(SweepIF sif) {
        add_to_command_sequence_list(SWEEP, 0);

        int arr_length = command_sequence_list.size();
        int counter = 0;
        String rst = "";

        for (String cmd : command_sequence_list) {
            counter++;
            String val = sif.getValue(cmd);
            if (val != null) {
                if (cmd.contains(ADD_PHASE) || cmd.contains(SET_DISCHARGE) || counter == arr_length) {
                    rst += dict_map.get(cmd) + " " + val + "; ";
                } else {
                    if (cmd.contains(SET_RAM)) {
                        rst += dict_map.get(cmd) + " " + process_set_ram(val) + ", ";
                    } else {
                        rst += dict_map.get(cmd) + " " + val + ", ";
                    }
                }
            }
        }

        return rst;
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

    public String getWorkInstruction() {
        return WORK_INSTRUCTION;
    }
}
