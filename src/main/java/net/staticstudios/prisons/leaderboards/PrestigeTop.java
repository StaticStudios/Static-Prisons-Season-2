package net.staticstudios.prisons.leaderboards;

import net.staticstudios.prisons.data.dataHandling.PlayerData;
import net.staticstudios.prisons.data.dataHandling.serverData.ServerData;
import net.staticstudios.prisons.utils.Utils;
import org.bukkit.Bukkit;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PrestigeTop {
    static final String npc1Name = "leaderboardsPrestige1";
    static final String npc2Name = "leaderboardsPrestige2";
    static final String npc3Name = "leaderboardsPrestige3";
    public static List<UUID> top100UUIDs = new ArrayList<>();
    public static void calculateLeaderBoard() {
        List<UUID> topUUIDs = new ArrayList<>();
        List<BigInteger> topValues = new ArrayList<>();
        for (String uuid : ServerData.PLAYERS.getAllUUIDsAsStrings()) {
            boolean ranked = false;
            PlayerData playerData = new PlayerData(uuid);
            if (playerData.getIsExemptFromLeaderboards()) continue;
            for (int i = 0; i < topUUIDs.size(); i++) {
                if (topValues.get(i).compareTo(playerData.getPrestige()) < 0) {
                    ranked = true;
                    topValues.add(i, playerData.getPrestige());
                    topUUIDs.add(i, playerData.getUUID());
                    break;
                }
            }
            if (!ranked && topUUIDs.size() < 100) {
                topValues.add(playerData.getPrestige());
                topUUIDs.add(playerData.getUUID());
            }
        }
        top100UUIDs = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            if (topUUIDs.size() >= i + 1) top100UUIDs.add(topUUIDs.get(i));
        }
        updateNPC();
    }
    static void updateNPC() {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc select " + npc1Name);
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc skin " + ServerData.PLAYERS.getUUID(top100UUIDs.get(0).toString()));
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc hologram remove 1");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc hologram add &a&l" + ServerData.PLAYERS.getUUID(top100UUIDs.get(0).toString()) + " (" + Utils.prettyNum(new PlayerData(top100UUIDs.get(0)).getPrestige()) + ")");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc command remove 1");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc command add stats " + ServerData.PLAYERS.getUUID(top100UUIDs.get(0).toString()) + " -r -l -p");

        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc select " + npc2Name);
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc skin " + ServerData.PLAYERS.getUUID(top100UUIDs.get(1).toString()));
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc hologram remove 1");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc hologram add &a&l" + ServerData.PLAYERS.getUUID(top100UUIDs.get(1).toString()) + " (" + Utils.prettyNum(new PlayerData(top100UUIDs.get(1)).getPrestige()) + ")");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc command remove 1");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc command add stats " + ServerData.PLAYERS.getUUID(top100UUIDs.get(1).toString()) + " -r -l -p");

        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc select " + npc3Name);
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc skin " + ServerData.PLAYERS.getUUID(top100UUIDs.get(2).toString()));
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc hologram remove 1");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc hologram add &a&l" + ServerData.PLAYERS.getUUID(top100UUIDs.get(2).toString()) + " (" + Utils.prettyNum(new PlayerData(top100UUIDs.get(2)).getPrestige()) + ")");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc command remove 1");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc command add stats " + ServerData.PLAYERS.getUUID(top100UUIDs.get(2).toString()) + " -r -l -p");
    }
}
