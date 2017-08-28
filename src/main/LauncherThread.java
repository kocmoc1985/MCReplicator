/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import Supplementary.MainFormIF;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author KOCMOC
 */
public class LauncherThread implements Runnable {

    private MainFormIF mainForm;
    private static int METHOD_TO_EXECUTE = 0;
    private static final int EXPORT_RECIPE_ONLY = 1;
    private static final int EXPORT_RAW_MATERIAL_INITIAL = 2;
    private static final int EXPORT_RAW_MATERIAL_PARTIAL = 3;
    //======================================================
    private static final int EXPORT_FLOW_RECIPE_INITIAL_NO_ERASE = 4;
    private static final int EXPORT_FLOW_RECIPE_PARTIAL = 5;
    private static final int EXPORT_FLOW_RECIPE_PARTIAL_CUSTOM_DATE = 6;
    private static final int EXPORT_FLOW_RECIPE_INITIAL_WITH_ERASE = 7;
    //======================================================
    private static final int TEST = 100;

    public LauncherThread(MainFormIF mf) {
        this.mainForm = mf;
    }

    private void startThread() {
        Thread x = new Thread(this);
        x.setName("LauncherThread");
        x.start();
    }

    public synchronized void test() {
        METHOD_TO_EXECUTE = TEST;
        startThread();
    }

    public synchronized void export_flow_recipe_partial_custom_date() {
        METHOD_TO_EXECUTE = EXPORT_FLOW_RECIPE_PARTIAL_CUSTOM_DATE;
        startThread();
    }

    public synchronized void export_flow_recipe_partial() {
        METHOD_TO_EXECUTE = EXPORT_FLOW_RECIPE_PARTIAL;
        startThread();
    }

    public synchronized void export_flow_recipe_initial_no_erase() {
        METHOD_TO_EXECUTE = EXPORT_FLOW_RECIPE_INITIAL_NO_ERASE;
        startThread();
    }
    
    public synchronized void export_flow_recipe_initial_with_erase() {
        METHOD_TO_EXECUTE = EXPORT_FLOW_RECIPE_INITIAL_WITH_ERASE;
        startThread();
    }

    public synchronized void export_recipe() {
        METHOD_TO_EXECUTE = EXPORT_RECIPE_ONLY;
        startThread();
    }

    public synchronized void export_raw_material_initial() {
        METHOD_TO_EXECUTE = EXPORT_RAW_MATERIAL_INITIAL;
        startThread();
    }

    public synchronized void export_raw_material_partial() {
        METHOD_TO_EXECUTE = EXPORT_RAW_MATERIAL_PARTIAL;
        startThread();
    }

    @Override
    public void run() {
        try {
            if (METHOD_TO_EXECUTE == EXPORT_RECIPE_ONLY) {
                new MCReplicator(mainForm).export_flow_recipe_only_initial();
            } else if (METHOD_TO_EXECUTE == EXPORT_RAW_MATERIAL_INITIAL) {
                new MCReplicator(mainForm).export_flow_raw_material_initial();
            } else if (METHOD_TO_EXECUTE == EXPORT_RAW_MATERIAL_PARTIAL) {
                new MCReplicator(mainForm).export_flow_raw_material_partial();
            } else if (METHOD_TO_EXECUTE == EXPORT_FLOW_RECIPE_INITIAL_NO_ERASE) {
                new MCReplicator(mainForm).export_flow_recipe_initial(false);
            }else if (METHOD_TO_EXECUTE == EXPORT_FLOW_RECIPE_INITIAL_WITH_ERASE) {
                new MCReplicator(mainForm).export_flow_recipe_initial(true);
            } else if (METHOD_TO_EXECUTE == EXPORT_FLOW_RECIPE_PARTIAL) {
                new MCReplicator(mainForm).export_flow_recipe_partial(1);
            } else if (METHOD_TO_EXECUTE == EXPORT_FLOW_RECIPE_PARTIAL_CUSTOM_DATE) {
                new MCReplicator(mainForm).export_flow_recipe_partial_custom_date();
            } else if (METHOD_TO_EXECUTE == TEST) {
                new MCReplicator(mainForm).test();
            }
        } catch (SQLException ex) {
            Logger.getLogger(LauncherThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
