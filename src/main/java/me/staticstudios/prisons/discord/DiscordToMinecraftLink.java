package me.staticstudios.prisons.discord;

import me.staticstudios.prisons.discord.SQL.SQLDataBase;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DiscordToMinecraftLink {

    public static boolean checkIfAccountIsLinkedFromDiscordID(String discordID) {
        ResultSet rs = SQLDataBase.executeQuery("SELECT discordID, UUID FROM discordToUUID;");
        try {
            while (rs.next()) {
                if (rs.getString("discordID").equals(discordID)) return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean checkIfAccountIsLinkedFromUUID(String UUID) {
        ResultSet rs = SQLDataBase.executeQuery("SELECT discordID, UUID FROM discordToUUID;");
        try {
            while (rs.next()) {
                if (rs.getString("UUID").equals(UUID)) return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String getDiscordIDFromUUID(String UUID) {
        ResultSet rs = SQLDataBase.executeQuery("SELECT discordID, UUID FROM discordToUUID;");
        try {
            while (rs.next()) {
                if (rs.getString("UUID").equals(UUID)) rs.getString("discordID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getUUIDFromLinkedDiscordID(String discordID) {
        ResultSet rs = SQLDataBase.executeQuery("SELECT discordID, UUID FROM discordToUUID;");
        try {
            while (rs.next()) {
                if (rs.getString("discordID").equals(discordID)) rs.getString("UUID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
