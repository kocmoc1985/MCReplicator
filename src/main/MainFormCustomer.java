/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import Logger.SimpleLoggerLight;
import Supplementary.AddToBufferSpeedGraph;
import Supplementary.ErrorOutPutShow;
import Supplementary.GP;
import Supplementary.HelpM;
import Supplementary.Kalendar;
import Supplementary.MainFormIF;
import Supplementary.RecordSpeedGraph;
import com.jezhumble.javasysmon.JavaSysMon;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author KOCMOC
 */
public class MainFormCustomer extends javax.swing.JFrame implements MainFormIF {

    private JButton curr_btn;
    public static RecordSpeedGraph speed_graph_1;
    public static AddToBufferSpeedGraph speed_graph_2;
    private Kalendar kalendar;
    public static String chosen_kalendar_date = "";
    private JavaSysMon monitor = new JavaSysMon();

    /**
     * Creates new form MainForm
     */
    public MainFormCustomer() {
        initComponents();
        this.setIconImage(new ImageIcon(GP.IMAGE_ICON_URL).getImage());
        this.setTitle("MCReplicator (" + monitor.currentPid() + ")");
        this.jLabel1OperationReady.setVisible(false);
        add_graphical_components();
    }

    private void add_graphical_components() {
        Color c = new Color(248, 248, 248);
        Font f = new Font(Font.SANS_SERIF, Font.BOLD, 24);
        speed_graph_1 = new RecordSpeedGraph("Record speed", "ms/r", c, f);
        jPanel1.add(speed_graph_1.getGraph());
        //===
        speed_graph_2 = new AddToBufferSpeedGraph("Calc speed", "ms/e", c, f);
        jPanel2.add(speed_graph_2.getGraph());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1RecordsProcessed = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jButton4 = new javax.swing.JButton();
        jLabel1CurrTable = new javax.swing.JLabel();
        jLabel1ToTransfer = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jLabel1AddedToBuffer = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jSeparator2 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton6 = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel1OperationReady = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBounds(new java.awt.Rectangle(0, 0, 0, 0));

        jLabel1RecordsProcessed.setText("Records tot.");

        jButton4.setText("Recipe flow (initial)");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel1CurrTable.setText("Curr. Table");

        jLabel1ToTransfer.setText("To transfer");

        jButton5.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jButton5.setText("Recipe flow (partial)");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jLabel1AddedToBuffer.setText("Added to buf.");

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.setLayout(new java.awt.GridLayout(1, 1));

        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel2.setLayout(new java.awt.GridLayout(1, 1));

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jTextArea1.setBorder(javax.swing.BorderFactory.createTitledBorder("Erorr output"));
        jScrollPane1.setViewportView(jTextArea1);

        jButton6.setText("Custom date recipe flow");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jLabel1OperationReady.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1OperationReady.setForeground(new java.awt.Color(7, 179, 7));
        jLabel1OperationReady.setText("Ready!");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(136, 136, 136)
                .addComponent(jLabel1OperationReady, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton6))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel1ToTransfer, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel1AddedToBuffer)
                                    .addComponent(jLabel1RecordsProcessed, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel1CurrTable, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jScrollPane1))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4)
                    .addComponent(jButton6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jLabel1OperationReady, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1ToTransfer)
                .addGap(13, 13, 13)
                .addComponent(jLabel1AddedToBuffer)
                .addGap(13, 13, 13)
                .addComponent(jLabel1RecordsProcessed)
                .addGap(10, 10, 10)
                .addComponent(jLabel1CurrTable)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 8, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE)
                .addGap(8, 8, 8))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        String menu = "Choose alternative:\n1. Copy without erase \n2. Copy with erase (use with caution) \n3. Cancel";
        String val = JOptionPane.showInputDialog(menu);

        if (Integer.parseInt(val) < 3) {
            boolean choise = JOptionPane.showConfirmDialog(null, "Confirm your choise", "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
            if (choise == false) {
                return;
            }
        }

        LauncherThread lt = new LauncherThread(this);

        switch (val) {
            case "1":
                lt.export_flow_recipe_initial_no_erase();
                break;
            case "2":
                lt.export_flow_recipe_initial_with_erase();
                break;
            default:
                break;
        }

        if (val.equals("1") || val.equals("2")) {
            disable_btn((JButton) evt.getSource());
        }

    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        LauncherThread lt = new LauncherThread(this);
        lt.export_flow_recipe_partial();
        disable_btn((JButton) evt.getSource());
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        choose_date_and_start();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void choose_date_and_start() {
        final JFrame f = new JFrame("Choose date");
        Container c = f.getContentPane();
        c.setLayout(new FlowLayout());

        final JButton button = new JButton("Ok");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (ae.getSource() == button) {
                    f.setVisible(false);
                    chosen_kalendar_date = kalendar.get_current_date_YYYY_MM_DD();
                    choose_date_and_start_2();
                }
            }
        });

        kalendar = new Kalendar();
        c.add(kalendar);
        c.add(button);

        f.setIconImage(new ImageIcon(GP.IMAGE_ICON_URL).getImage());
        f.pack();
        f.setVisible(true);
    }

    private void choose_date_and_start_2() {
        LauncherThread lt = new LauncherThread(this);
        lt.export_flow_recipe_partial_custom_date();
        disable_btn(this.jButton6);
    }

    private void disable_btn(JButton jButton) {
        curr_btn = jButton;
        curr_btn.setEnabled(false);
        this.jLabel1OperationReady.setVisible(false);
    }

    @Override
    public void enable_btn() {
        curr_btn.setEnabled(true);
        this.jLabel1OperationReady.setVisible(true);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFormCustomer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFormCustomer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFormCustomer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFormCustomer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>



        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                //===
                HelpM.create_dir_if_missing("err_output");
                //===
                String err_path = "err_output/err_" + HelpM.get_date_time() + ".txt";
                if (GP.ERR_OUTPUT_FILE == true) {
                    try {
                        PrintStream out = new PrintStream(new FileOutputStream(err_path));
                        System.setErr(out);
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(MainFormCustomer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                MainFormCustomer mfc = new MainFormCustomer();
                mfc.setVisible(true);
                jTextArea1.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 9));
                ErrorOutPutShow eops = new ErrorOutPutShow(jTextArea1, err_path);
                //====
            }
        });

    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    public static javax.swing.JLabel jLabel1AddedToBuffer;
    public static javax.swing.JLabel jLabel1CurrTable;
    private javax.swing.JLabel jLabel1OperationReady;
    public static javax.swing.JLabel jLabel1RecordsProcessed;
    public static javax.swing.JLabel jLabel1ToTransfer;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    public static javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables
}