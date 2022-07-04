package net.staticstudios.prisons.leaderboards;

import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.data.serverData.ServerData;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.Bukkit;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RawBlocksMinedTop {
    static final String npc1Name = "leaderboardsRawBlocksMined1";
    static final String npc2Name = "leaderboardsRawBlocksMined2";
    static final String npc3Name = "leaderboardsRawBlocksMined3";
    public static List<UUID> top100UUIDs = new ArrayList<>();
    public static void calculateLeaderBoard() {
        List<UUID> topUUIDs = new ArrayList<>();
        List<BigInteger> topValues = new ArrayList<>();
        for (UUID uuid : ServerData.PLAYERS.getAllUUIDs()) {
            boolean ranked = false;
            PlayerData playerData = new PlayerData(uuid);
            if (playerData.getIsExemptFromLeaderboards()) continue;
            for (int i = 0; i < topUUIDs.size(); i++) {
                if (topValues.get(i).compareTo(playerData.getRawBlocksMined()) < 0) {
                    ranked = true;
                    topValues.add(i, playerData.getRawBlocksMined());
                    topUUIDs.add(i, playerData.getUUID());
                    break;
                }
            }
            if (!ranked && topUUIDs.size() < 100) {
                topValues.add(playerData.getRawBlocksMined());
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
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc skin " + ServerData.PLAYERS.getName(top100UUIDs.get(0)));
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc hologram remove 1");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc hologram add &a&l" + ServerData.PLAYERS.getName(top100UUIDs.get(0)) + " (" + PrisonUtils.prettyNum(new PlayerData(top100UUIDs.get(0)).getRawBlocksMined()) + ")");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc command remove 1");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc command add stats " + ServerData.PLAYERS.getName(top100UUIDs.get(0)) + " -r -l -p");

        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc select " + npc2Name);
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc skin " + ServerData.PLAYERS.getName(top100UUIDs.get(1)));
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc hologram remove 1");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc hologram add &a&l" + ServerData.PLAYERS.getName(top100UUIDs.get(1)) + " (" + PrisonUtils.prettyNum(new PlayerData(top100UUIDs.get(1)).getRawBlocksMined()) + ")");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc command remove 1");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc command add stats " + ServerData.PLAYERS.getName(top100UUIDs.get(1)) + " -r -l -p");

        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc select " + npc3Name);
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc skin " + ServerData.PLAYERS.getName(top100UUIDs.get(2)));
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc hologram remove 1");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc hologram add &a&l" + ServerData.PLAYERS.getName(top100UUIDs.get(2)) + " (" + PrisonUtils.prettyNum(new PlayerData(top100UUIDs.get(2)).getRawBlocksMined()) + ")");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc command remove 1");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc command add stats " + ServerData.PLAYERS.getName(top100UUIDs.get(2)) + " -r -l -p");
    }
}
