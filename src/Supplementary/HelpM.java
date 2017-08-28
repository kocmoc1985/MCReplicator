/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Supplementary;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.MainForm;
import main.MainFormCustomer;

/**
 *
 * @author KOCMOC
 */
public class HelpM {

    public static void err_output_to_file() {
        //Write error stream to a file
        create_dir_if_missing("err_output");
        try {
            String err_file = "err_" + get_date_time() + ".txt";
            String output_path = "err_output/" + err_file;

            PrintStream out = new PrintStream(new FileOutputStream(output_path));
            System.setErr(out);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(HelpM.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void create_dir_if_missing(String path_and_folder_name) {
        File f = new File(path_and_folder_name);
        if (f.exists() == false) {
            f.mkdir();
        }
    }

    public static Properties properties_load_properties(String path_andOr_fileName) {
        Properties p = new Properties();
        try {
            p.load(new FileInputStream(path_andOr_fileName));
        } catch (IOException ex) {
            System.out.println("" + ex);
        }
        return p;
    }

    public static String delete_last_letter_in_string(String str) {
        if (str == null) {
            return "";
        }
        if (str.isEmpty()) {
            return "";
        }
        int a = str.length() - 1;
        return str.substring(0, a);
    }

    public static String get_date_time_minus_some_time_in_ms(String date, String date_format, long time_to_minus) {
        if (date.isEmpty()) {
            return "";
        }
        long ms = dateToMillisConverter3(date, date_format);
        long new_date_in_ms = ms - time_to_minus;
        String new_date = millisToDateConverter("" + new_date_in_ms, date_format);
        return new_date;
    }

    private static long dateToMillisConverter3(String date, String date_format) {
        DateFormat formatter = new SimpleDateFormat(date_format);
        try {
            return formatter.parse(date).getTime();
        } catch (ParseException ex) {
            Logger.getLogger(HelpM.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }

    private static String millisToDateConverter(String millis, String format) {
        DateFormat formatter = new SimpleDateFormat(format); // this works to!
        long now = Long.parseLong(millis);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(now);
        return formatter.format(calendar.getTime());
    }

    public static String get_proper_date_time_same_format_on_all_computers() {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        return formatter.format(calendar.getTime());
    }

    public static String get_date_time() {
        DateFormat formatter = new SimpleDateFormat("yyyy_MM_dd HH_mm_ss");
        Calendar calendar = Calendar.getInstance();
        return formatter.format(calendar.getTime());
    }

    public static long minutes_to_milliseconds_converter(long minutes) {
        return minutes * 60000;
    }

    public static boolean verify_item_code(String itemCode) {
        if (itemCode.length() > 5) {
            return false;
        } else if (itemCode.contains("-")) {
            return false;
        } else {
            return true;
        }
    }

    
    /**
     * Calculating the previous release for a recipe on Hoogezand
     * 85-8-1637 -> prev release 84-8-1637 (85,84 this is release for Hoogezand)
     * @param recipe
     * @return 
     */
    public static String get_prev_release_for_recipe(String recipe) {
        String[] arr = recipe.split("-");
        int release;
        try {
            release = Integer.parseInt(arr[0]);
        } catch (Exception ex) {
            return "";
        }

        int prev_release = release - 1;

        if (prev_release < 0) {
            return "";
        }
        //==
        String prev_release_str;

        if (prev_release < 10) {
            prev_release_str = "0" + prev_release;
        } else {
            prev_release_str = "" + prev_release;
        }

        return prev_release_str + "-" + arr[1] + "-" + arr[2];
    }

    public static void main(String[] args) {

        String recipe = "";
        for (int i = 0; i < 100; i++) {
            if (i == 0) {
                recipe = get_prev_release_for_recipe("95-8-1637");
                System.out.println("" + recipe);
            }


            recipe = get_prev_release_for_recipe(recipe);
            System.out.println(recipe);

        }

    }

    public static void show_record_speed_on_graph(int speed) {
        if (MainFormCustomer.speed_graph_1 != null) {
            MainFormCustomer.speed_graph_1.go((double) speed);
        }
    }

    public static void show_calc_speed_on_graph(int speed) {
        if (MainFormCustomer.speed_graph_2 != null) {
            MainFormCustomer.speed_graph_2.go((double) speed);
        }
    }

    public static void show_record_speed(long ms) {
        if (MainForm.jLabel1MsPerRecord != null) {
            MainForm.jLabel1MsPerRecord.setText("ms/record = " + ms);
        }
    }

    public static void show_nr_records_added_to_buffer(int str) {
        if (MainForm.jLabel1AddedToBuffer != null) {
            MainForm.jLabel1AddedToBuffer.setText("added to buf.: " + str);
        } else {
            MainFormCustomer.jLabel1AddedToBuffer.setText("added to buf.: " + str);
        }

    }

    public static void show_nr_recorded_entries(int rst) {
        if (MainForm.jLabel1RecordsProcessed != null) {
            MainForm.jLabel1RecordsProcessed.setText("recorded: " + rst);
        } else {
            MainFormCustomer.jLabel1RecordsProcessed.setText("recorded: " + rst);
        }

    }

    public static void set_how_many_records_to_transfer_info(int summ) {
        if (MainForm.jLabel1ToTransfer != null) {
            MainForm.jLabel1ToTransfer.setText("to transfer: " + (summ - 1));
        } else {
            MainFormCustomer.jLabel1ToTransfer.setText("to transfer: " + (summ - 1));
        }

    }

    public static void set_how_many_records_to_transfer_info_approx(int summ) {
        if (MainForm.jLabel1ToTransfer != null) {
            MainForm.jLabel1ToTransfer.setText("to transfer approx.: " + summ);
        } else {
            MainFormCustomer.jLabel1ToTransfer.setText("to transfer approx.: " + summ);
        }

    }

    public static void set_current_table_beeing_updated_info(String str) {
        if (MainForm.jLabel1CurrTable != null) {
            MainForm.jLabel1CurrTable.setText("table beeing updated: " + str);
        } else {
            MainFormCustomer.jLabel1CurrTable.setText("table beeing updated: " + str);
        }

    }
}
