/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import Supplementary.MCReplicatorIF;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author KOCMOC
 */
public class OperationComplete implements Runnable {

    private final MCReplicatorIF mc_replicator;
    private long last_activity;
    private boolean RUN = true;

    public OperationComplete(MCReplicatorIF mcr) {
        this.mc_replicator = mcr;
        startThread();
    }

    private void startThread() {
        Thread x = new Thread(this);
        x.setName("OperationComplete-Thr");
        x.start();
    }

    public void set_last_activity(long ms) {
        last_activity = ms;
    }

    private synchronized void check_if_operation_complete() {
        if(last_activity == 0){ // very important!
            return;
        }
        long diff = last_activity - System.currentTimeMillis();
        if (Math.abs(millis_to_seconds(diff)) > 5) {
            synchronized (mc_replicator) {
                mc_replicator.notify();
                System.out.println("NOTIFY - BY OPERATION COMPLETE");
                mc_replicator.enable_btn_forwarding();
                RUN = false;
            }
        }
    }

    public static double millis_to_seconds(long millis) {
        return millis / 1000;
    }

    @Override
    public void run() {
        while (RUN) {
            wait_();
            check_if_operation_complete();
        }
    }

    
    private void wait_() {
        synchronized (this) {
            try {
                wait(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(OperationComplete.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
