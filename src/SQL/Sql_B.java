/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SQL;

//import com.microsoft.sqlserver.jdbc.SQLServerException;
import Logger.SimpleLoggerLight;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrator
 */
public class Sql_B {

    private Connection connection;
    private Statement statement;
    private Statement statement_2;
    private PreparedStatement p_statement;
    //
    private boolean CREATE_STATEMENT_SIMPLE;
    private int SQL_LOGIN_TIME_OUT = 60;
    //
    private boolean LOGG_CONNECTION_STRING;

    public Sql_B(boolean statementSimple, boolean loggConnectionStr) {
        this.CREATE_STATEMENT_SIMPLE = statementSimple;
        this.LOGG_CONNECTION_STRING = loggConnectionStr;
    }

    public Sql_B(boolean statementSimple, int loginTimeOut, boolean loggConnectionStr) {
        this.CREATE_STATEMENT_SIMPLE = statementSimple;
        this.SQL_LOGIN_TIME_OUT = loginTimeOut;
        this.LOGG_CONNECTION_STRING = loggConnectionStr;
    }

    public Connection getConnection() {
        return this.connection;
    }

    public Statement getStatement() {
        return statement;
    }

    public boolean isConnected() {
        boolean closed;
        try {
            closed = statement.isClosed();
        } catch (SQLException ex) {
            Logger.getLogger(Sql.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        //
        if (closed) {
            return false;
        } else {
            return true;
        }
    }

    /**
     *
     * @param host
     * @param port
     * @param databaseName
     * @param userName
     * @param password
     * @param useNamedPipes
     * @param domain - is used only if named pipe are used
     * @param instance - shall not be null, if not used use ""
     * @throws SQLException
     */
    public void connect_tds(String host, String port, String databaseName, String userName, String password, boolean useNamedPipes, String domain, String instance) throws SQLException {
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
            if (useNamedPipes) {
                connectionUrl += ";namedPipe=true;domain:" + domain;

            }
            //
            if (instance != null && instance.isEmpty() == false) {
                connectionUrl += ";instance=" + instance;
            }
            //
            logg_connection_string(connectionUrl);
            //
            DriverManager.setLoginTimeout(SQL_LOGIN_TIME_OUT);
            //
            connection = DriverManager.getConnection(connectionUrl);
            //
            if (connectionOk) {
                if (CREATE_STATEMENT_SIMPLE == true) {
                    statement = connection.createStatement();
                } else {
                    statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                }
            }
            //
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Sql.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (statement == null) {
            SimpleLoggerLight.logg("sql_conn.log", "Connection to: " + host + " / dbname: " + databaseName + " failed");
        }
    }

    private void logg_connection_string(String url) {
        if (LOGG_CONNECTION_STRING) {
            SimpleLoggerLight.logg("connection_string.log", url);
        }
    }

    public void connect_jdbc(String host, String port, String databaseName, String userName, String password) throws SQLException {
        try {
            //Class.forName("com.mysql.jdbc.Driver");
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            //
            String port_ = "";
            if (port.isEmpty() == false) {
                port_ = ":" + port;
            }
            //
            String connectionUrl = "jdbc:sqlserver://" + host + port_ + ";"
                    + "databaseName=" + databaseName + ";user=" + userName + ";password=" + password;
            //
            logg_connection_string(connectionUrl);
            //
            //For Trelleborgs connection it seems to be important!!
            DriverManager.setLoginTimeout(SQL_LOGIN_TIME_OUT);
            //
            connection = DriverManager.getConnection(connectionUrl);
            //
            if (CREATE_STATEMENT_SIMPLE == false) {
                statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                statement_2 = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            } else {
                statement = connection.createStatement();
                statement_2 = connection.createStatement();
            }
            //
        } catch (ClassNotFoundException e1) {
            Logger.getLogger(Sql_B.class.getName()).log(Level.SEVERE, null, e1);
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
    public void connect_odbc(String user, String pass, String odbc) throws SQLException {
        try {
            //Class.forName("com.mysql.jdbc.Driver");
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            String connectionUrl = "jdbc:odbc:" + odbc;
            //
            logg_connection_string(connectionUrl);
            //
            connection = DriverManager.getConnection(connectionUrl, user, pass);
            //
            if (CREATE_STATEMENT_SIMPLE == false) {
                statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                statement_2 = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            } else {
                statement = connection.createStatement();
                statement_2 = connection.createStatement();
            }
        } catch (ClassNotFoundException e1) {
            System.out.println("Databas-driver hittades ej: " + e1);
        }
    }

    //    
    public void connectMySql(String host, String port, String databaseName, String userName, String password) throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
//            connection = DriverManager.getConnection("jdbc:mysql://195.178.232.239:3306/m09k2847","m09k2847","636363");
            connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + databaseName, userName, password);
            statement = connection.createStatement();
        } catch (ClassNotFoundException e1) {
            System.out.println("Databas-driver hittades ej: " + e1);
        }
    }

    public PreparedStatement prepareStatement(String q) throws SQLException {
        return connection.prepareStatement(q);
    }

    public int executeUpdatePreparedStatement() throws SQLException {
        return p_statement.executeUpdate();
    }

    public int executeUpdatePreparedStatement(PreparedStatement ps) throws SQLException {
        return ps.executeUpdate();
    }

    public void loggSqlExceptionWithQuerry(String logFile, SQLException ex, String query) {
        if (ex.toString().contains("String or binary data would be truncated")) {
            SimpleLoggerLight.logg(logFile, "!IMPORTANT! Exeption: " + ex.toString() + "\nQuery: " + query);
        } else {
            SimpleLoggerLight.logg(logFile, "Exeption: " + ex.toString() + "\nQuery: " + query);
        }
    }

    public ResultSet execute(String sql) throws SQLException {
        if (statement.execute(sql)) {
            return statement.getResultSet();
        }
        return null;
    }

    public ResultSet execute_2(String sql) throws SQLException {
        if (statement_2.execute(sql)) {
            return statement_2.getResultSet();
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
}
