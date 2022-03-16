package me.staticstudios.prisons.gameplay.leaderboards;

import me.staticstudios.prisons.core.data.serverData.PlayerData;
import me.staticstudios.prisons.core.data.serverData.ServerData;
import me.staticstudios.prisons.utils.Utils;
import org.bukkit.Bukkit;

import java.math.BigInteger;
import java.util.*;

public class BlocksMinedTop {
    static final String npc1Name = "leaderboardsBlocksMined1";
    static final String npc2Name = "leaderboardsBlocksMined2";
    static final String npc3Name = "leaderboardsBlocksMined3";
    public static List<UUID> top100UUIDs = new ArrayList<>();
    public static void calculateLeaderBoard() {
        List<String> topUUIDs = new ArrayList<>();
        List<BigInteger> topValues = new ArrayList<>();
        for (String uuid : new ServerData().getPlayerUUIDsToNamesMap().keySet()) {
            boolean ranked = false;
            PlayerData playerData = new PlayerData(uuid);
            if (playerData.getIsExemptFromLeaderboards()) continue;
            for (int i = 0; i < topUUIDs.size(); i++) {
                if (topValues.get(i).compareTo(playerData.getBlocksMined()) < 0) {
                    ranked = true;
                    topValues.add(i, playerData.getBlocksMined());
                    topUUIDs.add(i, playerData.getUUID());
                    break;
                }
            }
            if (!ranked && topUUIDs.size() < 100) {
                topValues.add(playerData.getBlocksMined());
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
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc hologram add &a&l" + new ServerData().getPlayerNameFromUUID(top100UUIDs.get(0).toString()) + " (" + Utils.prettyNum(new PlayerData(top100UUIDs.get(0)).getBlocksMined()) + ")");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc command remove 1");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc command add stats " + new ServerData().getPlayerNameFromUUID(top100UUIDs.get(0).toString()) + " -r -l -p");

        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc select " + npc2Name);
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc skin " + new ServerData().getPlayerNameFromUUID(top100UUIDs.get(1).toString()));
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc hologram remove 1");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc hologram add &a&l" + new ServerData().getPlayerNameFromUUID(top100UUIDs.get(1).toString()) + " (" + Utils.prettyNum(new PlayerData(top100UUIDs.get(1)).getBlocksMined()) + ")");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc command remove 1");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc command add stats " + new ServerData().getPlayerNameFromUUID(top100UUIDs.get(1).toString()) + " -r -l -p");

        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc select " + npc3Name);
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc skin " + new ServerData().getPlayerNameFromUUID(top100UUIDs.get(2).toString()));
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc hologram remove 1");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc hologram add &a&l" + new ServerData().getPlayerNameFromUUID(top100UUIDs.get(2).toString()) + " (" + Utils.prettyNum(new PlayerData(top100UUIDs.get(2)).getBlocksMined()) + ")");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc command remove 1");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc command add stats " + new ServerData().getPlayerNameFromUUID(top100UUIDs.get(2).toString()) + " -r -l -p");
    }
}
