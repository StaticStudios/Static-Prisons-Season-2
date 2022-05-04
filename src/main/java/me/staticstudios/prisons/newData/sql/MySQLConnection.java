package me.staticstudios.prisons.newData.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnection {
    public static String host;
    public static String database;
    public static String port;
    public static String username;
    public static String password;

    private static Connection connection;

    public static boolean isConnected() {
        try {
            return (connection != null) && !connection.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static Connection connect()  {
        try {
            if (!isConnected()) {
                connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?useSSL=false",
                        username, password);
            }
        } catch (SQLException ignore) {}
        return null;
    }

    public static void disconnect() {
        if(isConnected()) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static Connection getConnection() {
        if (!isConnected()) return connect();
        return connection;
    }
}