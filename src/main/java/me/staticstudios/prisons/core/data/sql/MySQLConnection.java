package me.staticstudios.prisons.core.data.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnection { //TODO: put this in a config file
    private static String host = "51.83.66.220";
    private static String database = "staticst_discordLink";
    private static String port = "3306";
    private static String username = "staticst_discordLink";
    private static String password = "S7b1R0(q0peM";

    private static Connection connection;

    public static boolean isConnected() {
        return (connection != null);
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