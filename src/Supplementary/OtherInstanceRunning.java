/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Supplementary;

import Logger.SimpleLoggerLight;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Checks wether another instance of the same program is running if it does the
 * program exits if not it continues
 *
 * @author Administrator
 */
public class OtherInstanceRunning implements Runnable {

    private Socket socket;//used!
    private ServerSocket serverSocket;//used!
    private int port;
    private final static String LOGFILE = "other_instance_running.log";
    private final static String COMMAND_1 = "cmd_exit";
    private final static String COMMAND_2 = "cmd_received";
    private int ID = random();
    private boolean LOGG_ENABLED = false;
    private boolean EXIT_ON_OTHER_INSTANCE_RUNNING = false;
    private boolean SEND_CLOSE_ON_OTHER_INSTANCE_RUNNING = true;
    private boolean run = true;

    public OtherInstanceRunning(int port) {
        this.port = port;
        logg("Entering Constructor");
        //
        Recieve recieve = new Recieve();
        recieve.start_recieve();
    }

    public OtherInstanceRunning(int port, boolean logg_enabled, boolean exit, boolean send_close) {
        this.port = port;
        this.LOGG_ENABLED = logg_enabled;
        this.EXIT_ON_OTHER_INSTANCE_RUNNING = exit;
        this.SEND_CLOSE_ON_OTHER_INSTANCE_RUNNING = send_close;
        //
        logg("Entering Constructor");
        //
        Recieve recieve = new Recieve();
        recieve.start_recieve();
        //
    }

    public static int random() {
        int x = (int) ((Math.random() * 9999) + 1);
        return x;
    }

    @Override
    public void run() {
        //
        go();
        //
        while (run) {
            acceptConnections();
        }
        //
    }

    private void go() {
        if (check_if_another_instance_of_program_is_running()) {
            logg("Another instance of program is running on port = " + port);
            if (EXIT_ON_OTHER_INSTANCE_RUNNING) {
                System.exit(0);
            }
        } else {
            logg("No other instances of this program detected, the program will start.");
            run_server();
        }
    }

    private boolean run_server() {
        try {
            serverSocket = new ServerSocket(port);
            return true;
        } catch (IOException ex) {
            logg("Server could not be run - " + ex.toString() + "\n\n\n");
//            Logger.getLogger(OtherInstanceRunning.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    /**
     * Very important, without this method the Server crashes after the first
     * connection attempt!!!
     */
    private void acceptConnections() {
        try {
            //
            if (serverSocket == null) {
                return;
            }
            //
            socket = serverSocket.accept();
            //
        } catch (IOException ex) {
            run = false;
            Logger.getLogger(OtherInstanceRunning.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private synchronized void send(String message) {
        try {
            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
            output.writeObject(message);
            output.flush(); // M�ste g�ras!!!!
        } catch (IOException ex) {
            Logger.getLogger(OtherInstanceRunning.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @return true if running
     */
    private boolean check_if_another_instance_of_program_is_running() {
        try {
            //
            socket = new Socket("localhost", port);
            //
            if (SEND_CLOSE_ON_OTHER_INSTANCE_RUNNING) {
                send(COMMAND_1);
                logg("Command Sent: " + COMMAND_1);
            }
            //
            return true;
            //
        } catch (UnknownHostException ex) {
            //NOT a failure! This means that antoher instance is not running
            return false;
        } catch (IOException ex) {
            //NOT a failure! This means that antoher instance is not running
            return false;
        }

    }

    private void logg(String message) {
        if (LOGG_ENABLED) {
            SimpleLoggerLight.logg(LOGFILE, " ID: " + ID + "  " + message);
        }
    }

    class Recieve implements Runnable {

        public void start_recieve() {
            Thread x = new Thread(this);
            x.setName("Executor-OtherInstanceRunning-Recieve-Thr");
            x.start();
        }

        public void run() {
            while (true) {
                try {
                    recieve();
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(OtherInstanceRunning.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    protected void recieve() throws ClassNotFoundException {
        try {
            //
            if (socket != null) {
                ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
                String message = (String) input.readObject();

                if (message.contains(COMMAND_1)) {
                    logg("Executing actions: " + COMMAND_1);
                    send(COMMAND_2);
                    System.exit(0);
                } else {
                    if (message.contains(COMMAND_2)) {
                        logg("Executing actions: " + COMMAND_2);
                        //
                        //
                        while (run_server() == false) {
                            wait_(100);
                        }
                    }
                }
            }
        } catch (IOException e) {
//            Logger.getLogger(ClientEx.class.getName()).log(Level.SEVERE, null, e);
        }
        wait_(100);
    }

    private void wait_(int millis) {
        synchronized (this) {
            try {
                wait(millis);
            } catch (InterruptedException ex) {
                Logger.getLogger(OtherInstanceRunning.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
