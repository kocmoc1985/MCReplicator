/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Objects2;

import Objects2.SweepIF;
import java.util.HashMap;

/**
 *
 * @author KOCMOC
 */
public class SweepMade implements SweepIF {

    private int nr;
    private HashMap instr_map = new HashMap();
    private BeforeSweep beforeSweep;

    public SweepMade(int nr, BeforeSweep beforeSweep) {
        this.nr = nr;
        this.beforeSweep = beforeSweep;
    }

    public void add(String command_name, String command_value) {
        instr_map.put(command_name, command_value);
    }

    @Override
    public String getValue(String command_name) {
        return (String) instr_map.get(command_name);
    }

    public int getNr() {
        return nr;
    }

    public BeforeSweep getBeforeSweep() {
        return beforeSweep;
    }
}
