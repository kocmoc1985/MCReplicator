/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Supplementary;

import java.net.URL;
import java.util.Properties;

/**
 *
 * @author KOCMOC
 */
public class GP {

    public static final URL IMAGE_ICON_URL = GP.class.getResource("icon.png");
    public static final String STATUS_ALL = "";
    //========================================================
    public static final String dateProcessed = "dateProcessed";
    public static final String rowId = "ID";
    //========================================================
    public static final String LOG_PREFIX_BUFF_DB_W = "buffered_db_writer__";
    public static final String LOG_PREFIX_MCREPLICATOR = "mcreplicator__";
    public static final String LOG_PREFIX_MCREPLICATOR_BACK = "mcreplicatorback__";
    //=========================================================
    public static final String SQL_EXCEPTION_PATTERN_1 = "The connection is closed";
    public static final String SQL_EXCEPTION_PATTERN_2 = "check that an instance of SQL Server";
    public static final String SQL_EXCEPTION_PATTERN_3 = "Connection reset";
    //=========================================================
    public static final String SQL_ERR_LOGGING_PATTERN_1 = "fk_rrm_item_code";
    //=========================================================
    private static final Properties MAIN_PROPERTIES = HelpM.properties_load_properties("properties/main.properties");
    //=========
    public final static int STATEMENT_TYPE = Integer.parseInt(MAIN_PROPERTIES.getProperty("statement_type", "2"));//1 = simple
    //=========
    public final static boolean ERR_OUTPUT_FILE = Boolean.parseBoolean(MAIN_PROPERTIES.getProperty("error_output_to_file", "false"));
    //=========
    public final static String SQL_SRC_HOST = MAIN_PROPERTIES.getProperty("sql_source_host", "localhost");
    public final static String SQL_SRC_PORT = MAIN_PROPERTIES.getProperty("sql_source_port", "1433");
    public final static String SQL_SRC_DB_NAME = MAIN_PROPERTIES.getProperty("sql_source_db_name", "");
    public final static String SQL_SRC_USERNAME = MAIN_PROPERTIES.getProperty("sql_source_username", "sa");
    public final static String SQL_SRC_PASSWORD = MAIN_PROPERTIES.getProperty("sql_source_pass", "");
    //=========
    public final static String SQL_DEST_HOST = MAIN_PROPERTIES.getProperty("sql_dest_host", "localhost");
    public final static String SQL_DEST_PORT = MAIN_PROPERTIES.getProperty("sql_dest_port", "1433");
    public final static String SQL_DEST_DB_NAME = MAIN_PROPERTIES.getProperty("sql_dest_db_name", "");
    public final static String SQL_DEST_USERNAME = MAIN_PROPERTIES.getProperty("sql_dest_username", "sa");
    public final static String SQL_DEST_PASSWORD = MAIN_PROPERTIES.getProperty("sql_dest_pass", "");
    //=========================================================
    public final static String CHECK_INTERVAL_MINUTES = MAIN_PROPERTIES.getProperty("check_interval_in_minutes", "10");
    public final static String MC_REPLICATOR_BACK_CHECK_INTERVAL = MAIN_PROPERTIES.getProperty("mc_replicator_back_check_interval_in_minutes", "10");
}
