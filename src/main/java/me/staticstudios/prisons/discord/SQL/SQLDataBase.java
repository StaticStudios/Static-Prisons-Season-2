package me.staticstudios.prisons.discord.SQL;

import java.sql.*;
import java.util.Properties;

public class SQLDataBase {
    private static final String userName = "staticst_discordLink";
    private static final String password = "RDCJV!75EAEHjLB";
    private static Connection establishConnection() {
        try {
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://51.83.66.220:3306/staticst_discordLink",
                    userName, password);
            System.out.println("Connected to the database successfully!");
            return conn;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ResultSet executeQuery(String stmt) {
        try {
            Statement statement = establishConnection().createStatement();
            return statement.executeQuery(stmt);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
