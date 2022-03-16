package me.staticstudios.prisons.external;

import me.staticstudios.prisons.Main;
import me.staticstudios.prisons.core.data.MySQLConnection;
import org.bukkit.Bukkit;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

public class DiscordLink {
    public static final int SERVER_ID = 0;

    public static void initialize() {
        Bukkit.getScheduler().runTaskAsynchronously(Main.getMain(), MySQLConnection::connect);
        Bukkit.getScheduler().runTaskTimerAsynchronously(Main.getMain(), () -> {
            callBackExecutor();
        }, 100, 20); //Wait 10sec and then run every second
    }

    public static void initiateLinkRequest(UUID playerUUID) {

    }
    public static void updatePlayerCount() {
        sendBotRequest("PLAYERCOUNT " + Bukkit.getOnlinePlayers().size());
    }
    public static void sendBotRequest(String request) {
        Bukkit.getScheduler().runTaskAsynchronously(Main.getMain(), () -> {
            String query = "INSERT INTO `botRequests` (`id`, `request`, `fromServer`) VALUES (NULL, '" + request + "', '" + SERVER_ID + "')";
            if (MySQLConnection.getConnection() == null) {
                //If the connection is null, wait 1 second and try again
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                sendBotRequest(request);
                return;
            }
            try (Statement stmt = MySQLConnection.getConnection().createStatement()) {
                stmt.executeUpdate(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }
    public static void callBackExecutor() {
        if (MySQLConnection.getConnection() == null) {
            //If the connection is null, try again in a little while
            return;
        }
        String query = "SELECT * FROM `serverCallback`";
        try (Statement stmt = MySQLConnection.getConnection().createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                if (rs.getInt("server") != 0) continue;
                String callback = rs.getString("callback");
                MySQLConnection.getConnection().createStatement().executeUpdate("DELETE FROM `serverCallback` WHERE id = " + rs.getInt("id"));

                switch (callback.split(" ")[0].toLowerCase()) {
                    case "sendmessage" -> {
                        //sendmessage who(UUID) what...

                    }
                    default -> Bukkit.getLogger().warning("Got a callback with an unknown request! Callback: " + callback);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
