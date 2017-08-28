/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Supplementary;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;

/**
 *
 * @author KOCMOC
 */
public class ErrorOutPutShow implements Runnable {

    private final JTextArea jTextArea;
    private Scanner scanner;
    private String error_path;

    public ErrorOutPutShow(JTextArea jTextArea, String error_path) {
        this.jTextArea = jTextArea;
        this.error_path = error_path;
        try {
            loadReloadScanner(error_path);
            startThread();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ErrorOutPutShow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadReloadScanner(String erorr_path) throws FileNotFoundException {
        this.scanner = new Scanner(new File(erorr_path));
    }

    private void startThread() {
        Thread x = new Thread(this);
        x.setName("ErrorOutPutShow-Thr");
        x.start();
    }

    @Override
    public void run() {
        while (true) {
            wait_(5000);
            readFile();
        }
    }

    private void readFile() {
        try {
            loadReloadScanner(error_path);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ErrorOutPutShow.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        String exception = "";
        while (scanner.hasNext()) {
            String str = scanner.nextLine() + "\r\n";
            exception += str;
        }
        
        //OBS!!!! It works super bad without "synchronized"
        //it hangs up the whole program
        synchronized(jTextArea){ 
           jTextArea.setText(exception); 
        }
    }

    private void wait_(int millis) {
        synchronized (this) {
            try {
                wait(millis);
            } catch (InterruptedException ex) {
                Logger.getLogger(ErrorOutPutShow.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
