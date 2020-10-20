package JDBC;

import java.sql.*;

import utilities.Logs;
import utilities.Property;

public class JDBCConnection {
    private static final String connectionUrl =
            "jdbc:sqlserver://"+Property.getDBProperty("mtn.DBServer")+";"
                    + "database=lfsbaku;"
                    + "user=" + Property.getDBProperty("mtn.DBLogin") + ";"
                    + "password=" + Property.getDBProperty("mtn.DBPassword") + ";";

    private static Connection connection = null;
    private static Statement statement = null;
    private static ResultSet result = null;

    public static Connection connectToDB() {
        Logs.info("Establish DB connection...");
        try {
            connection = DriverManager.getConnection(connectionUrl);
            Logs.info("DB connection is successful");
        } catch (SQLException e) {
            Logs.info("DB connection is failed");
            Logs.error(e.getMessage());
        }
        return connection;
    }

    public static ResultSet selectFromDB(String query) {
        try {
            statement = connectToDB().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            Logs.info("Executing Select statement:" + query);
            result = statement.executeQuery(query);
            result.first();
            Logs.info("Data is retrieved");
        } catch (SQLException e) {
            Logs.error(e.getMessage());
        }
        return result;
    }

    public static void closeConnection() {
        if (result != null) {
            try {
                result.close();
                Logs.info("ResultSet is closed");
            } catch (SQLException e) {
                Logs.error(e.getMessage());
            }
        }
        if (statement != null) {
            try {
                statement.close();
                Logs.info("Statement is closed");
            } catch (SQLException e) {
                Logs.error(e.getMessage());
            }
        }

        /*if (prstaement != null) {
            try {
                prstaement.close();
                Logs.info("Prepared statement is closed");
            } catch (SQLException e) {
                Logs.error(e.getMessage());
            }
        }

        if (callstm != null) {
            try {
                callstm.close();
                Logs.info("Callable Statement is closed");
            } catch (SQLException e) {
                Logs.error(e.getMessage());
            }
        }*/

        if (connection != null) {
            try {
                connection.close();
                Logs.info("DB connection is closed");
            } catch (SQLException e) {
                Logs.error(e.getMessage());
            }
        }
    }
}
