package me.staticstudios.prisons.external.discord_old;

import me.staticstudios.prisons.utils.Utils;

import java.time.Instant;
import java.util.*;

public class LinkHandler {

    public static Map<String,String> discordToUUID = new HashMap<>();
    public static Map<String,String> uuidToDiscord = new HashMap<>();
    private static Map<String,Long> discordTimeStampLinked = new HashMap<>();

    public static void initialize() {
        List<String> lines = Utils.getAllLinesInAFile("./data/discord/discordToUUID.txt");
        for (String line : lines) {
            if (line.equals("")) continue;
            String[] ids = line.split(" \\| ")[0].split(": ");
            discordToUUID.put(ids[0], ids[1]);
            uuidToDiscord.put(ids[1], ids[0]);
            discordTimeStampLinked.put(ids[0], Instant.now().toEpochMilli());
        }
    }

    public static boolean linkAccount(String discordID, String uuid) {
        discordToUUID.put(discordID, uuid);
        uuidToDiscord.put(uuid, discordID);
        discordTimeStampLinked.put(discordID, Instant.now().toEpochMilli());
        List<String> list = new ArrayList<>();
        list.add(discordID + ": " + uuid + " | " + Instant.now().toEpochMilli());
        for (String key : discordToUUID.keySet()) {
            list.add(key + ": " + discordToUUID.get(key) + " | " + discordTimeStampLinked.get(key));
        }
        Utils.writeToAFile("./data/discord/discordToUUID.txt", list, false);
        DiscordAddRoles.giveRolesFromDiscordID(discordID);
        return true;
    }

    public static boolean checkIfLinkedFromUUID(String uuid) {
        return uuidToDiscord.containsKey(uuid);
    }
    public static long getTimeLinkedFromUUID(String uuid) {
        return discordTimeStampLinked.get(getLinkedDiscordIDFromUUID(uuid));
    }
    public static long getTimeLinkedFromDiscordID(String id) {
        return discordTimeStampLinked.get(id);
    }


    public static boolean checkIfLinkedFromDiscordID(String discordID) {
        return discordToUUID.keySet().contains(discordID);
    }

    public static String getLinkedDiscordIDFromUUID(String uuid) {
        return uuidToDiscord.get(uuid);
    }
    public static String getLinkedUUIDFromDiscordID(String id) {
        return discordToUUID.get(id);
    }
    public static void unlinkFromUUID(String uuid) {
        DiscordAddRoles.removeAllRoles(getLinkedDiscordIDFromUUID(uuid));
        discordToUUID.remove(getLinkedDiscordIDFromUUID(uuid));
        uuidToDiscord.remove(uuid);
    }
    public static void unlinkFromDiscordID(String discordID) {
        DiscordAddRoles.removeAllRoles(discordID);
        uuidToDiscord.remove(getLinkedUUIDFromDiscordID(discordID));
        discordToUUID.remove(discordID);
    }

}
