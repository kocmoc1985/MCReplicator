/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mc_to_omsk_lab;

import Logger.SimpleLoggerLight;
import SQL.Sql_B;
import Supplementary.GP;
import Supplementary.HelpM;
import Supplementary.OtherInstanceRunning;
import com.jezhumble.javasysmon.JavaSysMon;
import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

/**
 * Find more info in: CUSTOMER SERVICE SCHEMA A.vsd
 *
 * @author KOCMOC
 */
public class Main implements Runnable {

    private Sql_B sql_src = new Sql_B(true, false);
    private Sql_B sql_dest = new Sql_B(true, false);
    private final static int COPY_INTERVAL_MAIN_MS = 240000; // 4 MIN
    private final static boolean RUN_LOCALLY = false;

    public static void main(String[] args) {
        if (HelpM.runningInNetBeans() == false) {
            HelpM.err_output_to_file();
        }
        Main m = new Main();
    }

    public Main() {
        another_instance_running();
        toTray();
        connect();
        start_replicating_thread();
    }

    private void another_instance_running() {
        // added on 2024-11-23
        new Thread(new OtherInstanceRunning(4448, true, true, false)).start();
    }

    private void start_replicating_thread() {
        Thread x = new Thread(this);
        x.start();
    }

    private void connect() {
        if (RUN_LOCALLY) {
            connect_sql_local();
        } else {
            connect_sql_real();
        }
    }

    private void connect_sql_real() {
        //
        try {
            sql_src.connectMySql("localhost", "3306", "database_mc", "root", "0000");
        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        //
        try {
            sql_dest.connect_jdbc("localhost", "49734", "MCLAB", "sa", "sql2005");
        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        //

    }

    private void connect_sql_local() {
        //
        try {
            sql_src.connectMySql("localhost", "3306", "omsk", "root", "0000");
        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        //
        try {
            sql_dest.connect_jdbc("localhost", "1433", "MIXCONT", "sa", "Kocmoc4765");
        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        //

    }

    @Override
    public void run() {
        while (true) {
            flow_a();
            wait_(COPY_INTERVAL_MAIN_MS);
        }
    }

    private synchronized void wait_(int millis) {
        try {
            wait(millis);
        } catch (InterruptedException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private ArrayList<Insert_Entry> list;

    private void flow_a() {
        //
//        System.out.println("flow_a: started");
        //
        list = new ArrayList<>();
        //
        check_connections();
        //
        initial_delete();
        //
        step_1();
        //
        step_2();
        //
        SimpleLoggerLight.logg_no_append("last_export.log", "Last export");
        //
    }

    private void check_connections() {
        if (sql_src.isConnected() == false || sql_dest.isConnected() == false) {
            connect();
        }
    }

    private void initial_delete() {
        //
        String q = "delete from lab_a";
        //
        try {
            sql_dest.execute(q);
        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void step_2() {
        //
        for (Insert_Entry e : list) {
            //
            String q = get_insert_query(e.ORDERID, e.RECIPEID, e.BATCHNR, e.STARTDATE, e.LINENR);
            //
            try {
                sql_dest.getStatement().addBatch(q);
            } catch (SQLException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
            //
        }
        //
        try {
            sql_dest.getStatement().executeBatch();
        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
    }

    private void step_1() {
        //
        String q = "select * from mc_batchinfo order by PROD_DATE desc limit 500";
        //
        try {
            //
            ResultSet rs = sql_src.execute(q);
            //
            while (rs.next()) {
                //
                String oderid = rs.getString("OrderName");
                String recipeid = rs.getNString("RECIPEID");
                int batchnr = rs.getInt("BATCHNO");
                String startdate = rs.getString("PROD_DATE");
                //
                Insert_Entry entry = new Insert_Entry(oderid, recipeid, batchnr, startdate, 110);
                //
                list.add(entry);
                //
            }
            //
        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static String get_insert_query(String orderid, String recipeid, int batchnr, String startdate, int linenr) {
        String rst = "insert into lab_a"
                + " ("
                + "orderid,"
                + "recipeid,"
                + "batchnr,"
                + "startdate,"
                + "linenr"
                + ")"
                + " values ("
                + "N'" + orderid + "',"
                + "N'" + recipeid + "',"
                + "" + batchnr + ","
                + "'" + startdate + "',"
                + "" + linenr + ""
                + ")";
        return rst;
    }

    private PopupMenu popup;
    private MenuItem exit;
    private SystemTray tray;
    private TrayIcon trayIcon;
    private JavaSysMon monitor = new JavaSysMon();

    private void toTray() {
        if (SystemTray.isSupported()) {

            tray = SystemTray.getSystemTray();
            Image img = new ImageIcon(GP.IMAGE_ICON_URL).getImage();

            ActionListener actionListener = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == exit) {
                        System.exit(0);
                    }
                }
            };

            popup = new PopupMenu();
            exit = new MenuItem("EXIT");

            exit.addActionListener(actionListener);

            popup.add(exit);

            trayIcon = new TrayIcon(img, "Передача данных в лабораторию" + " (" + monitor.currentPid() + ")", popup);

            trayIcon.setImageAutoSize(true);
            trayIcon.addActionListener(actionListener);

            try {
                tray.add(trayIcon);

            } catch (AWTException e) {
                System.err.println("TrayIcon could not be added.");
            }
        }
    }

}
