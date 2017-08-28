/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SQL;

import Logger.SimpleLoggerLight;
import Supplementary.GP;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.MCReplicator;

/**
 *
 * @author Administrator
 */
public class Sql {

    private Connection connection;
    private Statement statement;
    private Statement statement2;
    private PreparedStatement p_statement;
    //=====================================
    private String ip;
    private String port;
    private String db_name;
    private String user;
    private String pass;
    //=====================================
    public static final int STATEMENT_TYPE_SIMPLE = 1;
    public static final int STATEMENT_TYPE_ADVANCED = 2;
    //======================================
     private boolean CREATE_STATEMENT_SIMPLE;
    private int SQL_LOGIN_TIME_OUT = 60;

    public Statement getStatement() {
        return statement;
    }

    public Sql() {

    }

    public Sql(String ip, String port, String db_name, String user, String pass) {
        save_connection_cridentials(ip, port, db_name, user, pass);
        try {
            connect(ip, port, db_name, user, pass);
        } catch (SQLException ex) {
            Logger.getLogger(MCReplicator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void save_connection_cridentials(String ip, String port, String db_name, String user, String pass) {
        this.ip = ip;
        this.port = port;
        this.db_name = db_name;
        this.user = user;
        this.pass = pass;
    }

    public final void reconnectMsSql() throws SQLException {
        connect(ip, port, db_name, user, pass);
    }

    public void connectB(String host, String port, String databaseName, String userName, String password) throws SQLException {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            String port_ = "";
            if (port.isEmpty() == false) {
                port_ = ":" + port;
            }

            String connectionUrl = "jdbc:sqlserver://" + host + port_ + ";"
                    + "databaseName=" + databaseName + ";user=" + userName + ";password=" + password;

            connection = DriverManager.getConnection(connectionUrl);
            statement = createStatement();
            statement2 = createStatement();

        } catch (ClassNotFoundException e1) {
            Logger.getLogger(Sql.class.getName()).log(Level.SEVERE, null, e1);
        }
    }
    
    public boolean connect(String host, String port, String databaseName, String userName, String password) throws SQLException {
        boolean connectionOk = true;
        try {
            //Class.forName("com.mysql.jdbc.Driver");
            Class.forName("net.sourceforge.jtds.jdbc.Driver");

            String port_ = "";
            if (port.isEmpty() == false) {
                port_ = ":" + port;
            }
            //
            String connectionUrl = "jdbc:jtds:sqlserver://" + host + port_ + ";"
                    + "databaseName=" + databaseName + ";user=" + userName + ";password=" + password;//+ ";namedPipe=true" -> requires "domain=" paramter!
            //
            //
            DriverManager.setLoginTimeout(SQL_LOGIN_TIME_OUT);
            //
            connection = DriverManager.getConnection(connectionUrl);
            //
            if (connectionOk) {
                if (CREATE_STATEMENT_SIMPLE == true) {
                    statement = connection.createStatement();
                    statement2 = createStatement();
                } else {
                    statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    statement2 = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                }
            }
            //
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Sql.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (statement == null) {
            SimpleLoggerLight.logg("sql_conn.log", "Connection to: " + host + " / dbname: " + databaseName + " failed");
            return false;
        }else{
            return true;
        }
    }

    public Statement createStatement() {
        try {
            if (GP.STATEMENT_TYPE == STATEMENT_TYPE_SIMPLE) {
                return connection.createStatement();
            } else if (GP.STATEMENT_TYPE == STATEMENT_TYPE_ADVANCED) {
                return connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Sql.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void sql_error_handling(SQLException ex) {
        if (ex.toString().contains(GP.SQL_EXCEPTION_PATTERN_1) || ex.toString().contains(GP.SQL_EXCEPTION_PATTERN_2)) {
            try {
                reconnectMsSql();
            } catch (SQLException ex1) {
                Logger.getLogger(Sql.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }

    //==========================================================================
    public void connectMySql(String host, String port, String databaseName, String userName, String password) throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + databaseName, userName, password);
            statement = connection.createStatement();
        } catch (ClassNotFoundException e1) {
            System.out.println("Databas-driver hittades ej: " + e1);
        }
    }

    /**
     * For connecting with ODBC. Fits for ACCESS databases also!!
     *
     * @param user
     * @param pass
     * @param odbc
     * @throws SQLException
     */
    public void connectODBC(String user, String pass, String odbc) throws SQLException {
        try {
            //Class.forName("com.mysql.jdbc.Driver");
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            String connectionUrl = "jdbc:odbc:" + odbc;

            try {
//                connection = DriverManager.getConnection(connectionUrl, "mixcont", "mixcont");
                connection = DriverManager.getConnection(connectionUrl, user, pass);
                statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            } catch (SQLException e) {
                Logger.getLogger(Sql.class.getName()).log(Level.SEVERE, null, e);
            }
        } catch (ClassNotFoundException e1) {
            System.out.println("Databas-driver hittades ej: " + e1);
        }
    }

     public PreparedStatement prepareStatement(String q) throws SQLException {
        return connection.prepareStatement(q);
    }

    public int executeUpdatePreparedStatement(PreparedStatement ps) throws SQLException {
        return ps.executeUpdate();
    }

    public ResultSet execute(String sql) throws SQLException {
        if (statement.execute(sql)) {
            return statement.getResultSet();
        }
        return null;
    }

    public ResultSet execute2(String sql) throws SQLException {
        if (statement2.execute(sql)) {
            return statement2.getResultSet();
        }
        return null;
    }

    public int update(String sql) throws SQLException {
        return statement.executeUpdate(sql);
    }

    public void close() throws SQLException {
        statement.close();
        connection.close();
    }
    
    public boolean table_contains(String query) {
        ResultSet rs;
        try {
            rs = execute(query);
            return rs.next();
        } catch (SQLException ex) {
            Logger.getLogger(Sql.class.getName()).log(Level.SEVERE, null, ex);
            return true;
        }
    }

    public void loggSqlExceptionWithQuerry(String logFile, SQLException ex, String query) {
        if (ex.toString().contains("String or binary data would be truncated")) {
            SimpleLoggerLight.logg(logFile, "!IMPORTANT! Exeption: " + ex.toString() + "\nQuery: " + query);
        } else {
            SimpleLoggerLight.logg(logFile, "Exeption: " + ex.toString() + "\nQuery: " + query);
        }
    }
}
