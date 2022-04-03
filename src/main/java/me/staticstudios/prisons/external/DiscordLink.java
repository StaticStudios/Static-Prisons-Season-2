package me.staticstudios.prisons.external;

import me.staticstudios.prisons.Main;
import me.staticstudios.prisons.data.serverData.PlayerData;
import me.staticstudios.prisons.data.serverData.ServerData;
import me.staticstudios.prisons.data.sql.MySQLConnection;
import me.staticstudios.prisons.utils.Utils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

public class DiscordLink {
    public static final int SERVER_ID = Integer.parseInt(Utils.getFileContents("./data/discord/serverID.txt"));
    public static final int SECONDS_TO_KEEP_LINK_REQUESTS_ALIVE = 300;
    public static final int LINK_CODE_LENGTH = 7;

    public static void initialize() {
        Bukkit.getScheduler().runTaskAsynchronously(Main.getMain(), MySQLConnection::connect);
        Bukkit.getScheduler().runTaskTimerAsynchronously(Main.getMain(), DiscordLink::callBackExecutor, 100, 20); //Wait 10sec and then run every second
    }


    public static void playerJoined(Player player) {
        //Set default values before checking the database for the correct values
        PlayerData playerData = new PlayerData(player);
        playerData.setIsDiscordLinked(false);
        playerData.setDiscordName("null");
        playerData.setDiscordID("");
        playerData.setIsNitroBoosting(false);
        playerData.updateTabListPrefixID();



        //Run DB actions async
        Bukkit.getScheduler().runTaskAsynchronously(Main.getMain(), () -> {

            //Update the uuidIGN table in the database
            while (MySQLConnection.getConnection() == null) {
                //If the connection is null, wait 1 second and try again
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            try (Statement stmt = MySQLConnection.getConnection().createStatement()) {
                ResultSet rs = stmt.executeQuery("SELECT * FROM `linkedAccounts` WHERE accountUUID = '" + player.getUniqueId() + "'");
                rs.next();
                rs.getString("accountUUID"); //Check if it throws an error
                playerData.setDiscordID(rs.getString("discordID"));
                playerData.setIsDiscordLinked(true);
                try (Statement _stmt = MySQLConnection.getConnection().createStatement()) {
                    ResultSet _rs = stmt.executeQuery("SELECT * FROM `discordNames` WHERE discordID = '" + playerData.getDiscordID() + "'");
                    _rs.next();
                    _rs.getString("discordID"); //Check if it throws an error
                    playerData.setDiscordName(_rs.getString("username"));
                } catch (SQLException e) {}
            } catch (SQLException e) {
                return;
            }
            if (playerData.getIsDiscordLinked()) {
                try (Statement stmt = MySQLConnection.getConnection().createStatement()) {
                    ResultSet rs = stmt.executeQuery("SELECT * FROM `nitroBoosterIDs` WHERE discordID = '" + playerData.getDiscordID() + "'");
                    rs.next();
                    rs.getString("discordID"); //Check if it throws an error
                    playerData.setIsNitroBoosting(true);
                    playerData.updateTabListPrefixID();
                } catch (SQLException e) {}
            }

            try (Statement stmt = MySQLConnection.getConnection().createStatement()) {
                stmt.executeUpdate("DELETE FROM `uuidIGN` WHERE uuid = '" + player.getUniqueId() + "'");
                stmt.executeUpdate("INSERT INTO `uuidIGN` (`id`, `uuid`, `ign`) VALUES (NULL, '" + player.getUniqueId() + "', '" + player.getName() + "')");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public static void unlinkAccount(UUID playerUUID) {
        while (MySQLConnection.getConnection() == null) {
            //If the connection is null, wait 1 second and try again
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        String query = "SELECT accountUUID FROM `linkedAccounts` WHERE EXISTS(SELECT * FROM `uuidIGN` WHERE uuid = '" + playerUUID + "')";
        try (Statement stmt = MySQLConnection.getConnection().createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            rs.getString("accountUUID"); //Check if it throws an error
            String update = "DELETE FROM `linkedAccounts` WHERE accountUUID = '" + playerUUID + "'";
            if (Bukkit.getPlayer(playerUUID) != null) Bukkit.getPlayer(playerUUID).sendMessage(org.bukkit.ChatColor.AQUA + "You have successfully unlinked your account!");
            stmt.executeUpdate(update);
        } catch (SQLException e) {
            if (Bukkit.getPlayer(playerUUID) != null) Bukkit.getPlayer(playerUUID).sendMessage(org.bukkit.ChatColor.AQUA + "Your account is not linked! To link your account, type " + ChatColor.ITALIC + "\"/discord link\"");
        }
        PlayerData playerData = new PlayerData(playerUUID);
        playerData.setIsDiscordLinked(false);
        playerData.setDiscordName("null");
        playerData.setDiscordID("");
        playerData.setIsNitroBoosting(false);
        playerData.updateTabListPrefixID();
    }

    public static void initiateLinkRequest(UUID playerUUID) {
        while (MySQLConnection.getConnection() == null) {
            //If the connection is null, wait 1 second and try again
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        String query = "SELECT accountUUID FROM `linkedAccounts` WHERE accountUUID = '" + playerUUID + "'";
        try (Statement stmt = MySQLConnection.getConnection().createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            rs.getString("accountUUID"); //Check if it throws an error
            if (Bukkit.getPlayer(playerUUID) != null) Bukkit.getPlayer(playerUUID).sendMessage(org.bukkit.ChatColor.AQUA + "Your account is already linked to a discord account! If you wish to unlink it, type" + ChatColor.ITALIC + " \"/discord unlink\"");
        } catch (SQLException e) {
            String code = generateLinkingCode();
            if (Bukkit.getPlayer(playerUUID) != null) Bukkit.getPlayer(playerUUID).sendMessage(org.bukkit.ChatColor.AQUA + "To link your discord account to your Minecraft account, please join our discord and type in the following in #bot-commands: " + ChatColor.LIGHT_PURPLE + "!link " + code + "\n\n" + org.bukkit.ChatColor.GRAY + "" + org.bukkit.ChatColor.ITALIC + "This code will expire in 5 minutes.");
            sendBotRequest("LINKACCOUNTREQUEST " + playerUUID + " " + code  + " " + SECONDS_TO_KEEP_LINK_REQUESTS_ALIVE);
        }
    }
    static String generateLinkingCode() {
        char[] ALPHANUMERIC ="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();

        StringBuilder random = new StringBuilder();

        for(int i = 0; i < LINK_CODE_LENGTH; i++) {
            int index = (int) (Math.random() * ALPHANUMERIC.length);
            random.append(ALPHANUMERIC[index]);
        }
        return random.toString();
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
            //If the connection is null, try again in a little while -- it will automatically try again later
            return;
        }
        String query = "SELECT * FROM `serverCallback`";
        try (Statement stmt = MySQLConnection.getConnection().createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                if (rs.getInt("server") != SERVER_ID && rs.getInt("server") != -1) continue;
                String callback = rs.getString("callback");
                while (MySQLConnection.getConnection() == null) {
                    //If the connection is null, wait 1 second and try again
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                List<String> args = new ArrayList<>(List.of(callback.split(" ")));
                args.remove(0);
                switch (callback.split(" ")[0]) {
                    case "SENDPLAYERMESSAGE" -> {
                        //who, what...
                        UUID uuid = UUID.fromString(args.get(0));
                        if (Bukkit.getPlayer(uuid) == null) continue;
                            StringBuilder message = new StringBuilder();
                            for (int i = 1; i < args.size(); i++) message.append(args.get(i)).append(" ");
                            Bukkit.getPlayer(uuid).sendMessage(ChatColor.translateAlternateColorCodes('&', message.toString()));
                    }
                    case "LINKSUCCESS" -> {
                        StringBuilder accountName = new StringBuilder();
                        for (int i = 2; i < args.size(); i++) accountName.append(args.get(i)).append(" ");
                        Bukkit.getLogger().log(Level.INFO, "" + args.get(0) + " ( " + new ServerData().getPlayerNameFromUUID(args.get(0)) + " ) was linked to discord account " + args.get(1) + " ( " + accountName + ")");
                        //Check if they are boosting
                        PlayerData playerData = new PlayerData(args.get(0));
                        try (Statement _stmt = MySQLConnection.getConnection().createStatement()) {
                            ResultSet _rs = _stmt.executeQuery("SELECT * FROM `nitroBoosterIDs` WHERE discordID = '" + args.get(1) + "'");
                            _rs.next();
                            _rs.getString("discordID"); //Check if it throws an error
                            playerData.setIsNitroBoosting(true);
                            playerData.updateTabListPrefixID();
                        } catch (SQLException e) {
                        }
                    }
                    case "PLAYERSTARTEDBOOSTING" -> {
                        UUID uuid = UUID.fromString(callback.split(" ")[1]);
                        PlayerData playerData = new PlayerData(uuid);
                        playerData.setIsNitroBoosting(true);
                        playerData.updateTabListPrefixID();
                    }
                    case "PLAYERSTOPPEDBOOSTING" -> {
                        UUID uuid = UUID.fromString(callback.split(" ")[1]);
                        PlayerData playerData = new PlayerData(uuid);
                        playerData.setIsNitroBoosting(false);
                        playerData.updateTabListPrefixID();
                    }
                    case "UNLINKEDACCOUNT" -> {
                        UUID uuid = UUID.fromString(callback.split(" ")[1]);
                        PlayerData playerData = new PlayerData(uuid);
                        playerData.setIsNitroBoosting(false);
                        playerData.setIsDiscordLinked(false);
                        playerData.setDiscordID("");
                        playerData.setDiscordName("null");
                        playerData.updateTabListPrefixID();
                    }
                    default -> Bukkit.getLogger().warning("Got a callback with an unknown request! Callback: " + callback);
                }
                MySQLConnection.getConnection().createStatement().executeUpdate("DELETE FROM `serverCallback` WHERE id = " + rs.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
