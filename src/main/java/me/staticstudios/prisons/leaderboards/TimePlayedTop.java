package me.staticstudios.prisons.leaderboards;

import me.staticstudios.prisons.data.serverData.PlayerData;
import me.staticstudios.prisons.data.serverData.ServerData;
import org.bukkit.Bukkit;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TimePlayedTop {
    static final String npc1Name = "leaderboardsTime1";
    static final String npc2Name = "leaderboardsTime2";
    static final String npc3Name = "leaderboardsTime3";
    public static List<UUID> top100UUIDs = new ArrayList<>();
    public static void calculateLeaderBoard() {
        List<String> topUUIDs = new ArrayList<>();
        List<BigInteger> topValues = new ArrayList<>();
        for (String uuid : new ServerData().getPlayerUUIDsToNamesMap().keySet()) {
            boolean ranked = false;
            PlayerData playerData = new PlayerData(uuid);
            if (playerData.getIsExemptFromLeaderboards()) continue;
            for (int i = 0; i < topUUIDs.size(); i++) {
                if (topValues.get(i).compareTo(playerData.getTimePlayed()) < 0) {
                    ranked = true;
                    topValues.add(i, playerData.getTimePlayed());
                    topUUIDs.add(i, playerData.getUUID());
                    break;
                }
            }
            if (!ranked && topUUIDs.size() < 100) {
                topValues.add(playerData.getTimePlayed());
                topUUIDs.add(playerData.getUUID());
            }
        }
        top100UUIDs = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            if (topUUIDs.size() >= i + 1) top100UUIDs.add(UUID.fromString(topUUIDs.get(i)));
        }
        updateNPC();
    }
    static void updateNPC() {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc select " + npc1Name);
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc skin " + new ServerData().getPlayerNameFromUUID(top100UUIDs.get(0).toString()));
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc hologram remove 1");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc hologram add &a&l" + new ServerData().getPlayerNameFromUUID(top100UUIDs.get(0).toString()) + " (" + new PlayerData(top100UUIDs.get(0)).getTimePlayed().divide(BigInteger.valueOf(3600)) + " Hours)");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc command remove 1");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc command add stats " + new ServerData().getPlayerNameFromUUID(top100UUIDs.get(0).toString()) + " -r -l -p");

        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc select " + npc2Name);
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc skin " + new ServerData().getPlayerNameFromUUID(top100UUIDs.get(1).toString()));
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc hologram remove 1");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc hologram add &a&l" + new ServerData().getPlayerNameFromUUID(top100UUIDs.get(1).toString()) + " (" + new PlayerData(top100UUIDs.get(1)).getTimePlayed().divide(BigInteger.valueOf(3600)) + " Hours)");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc command remove 1");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc command add stats " + new ServerData().getPlayerNameFromUUID(top100UUIDs.get(1).toString()) + " -r -l -p");

        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc select " + npc3Name);
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc skin " + new ServerData().getPlayerNameFromUUID(top100UUIDs.get(2).toString()));
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc hologram remove 1");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc hologram add &a&l" + new ServerData().getPlayerNameFromUUID(top100UUIDs.get(2).toString()) + " (" + new PlayerData(top100UUIDs.get(2)).getTimePlayed().divide(BigInteger.valueOf(3600)) + " Hours)");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc command remove 1");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc command add stats " + new ServerData().getPlayerNameFromUUID(top100UUIDs.get(2).toString()) + " -r -l -p");
    }
}
