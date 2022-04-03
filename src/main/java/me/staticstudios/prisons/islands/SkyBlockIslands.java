package me.staticstudios.prisons.islands;

import me.staticstudios.prisons.data.serverData.PlayerData;
import org.bukkit.entity.Player;

public class SkyBlockIslands {
    public static boolean checkIfPlayerHasIsland(Player player) {
        return checkIfPlayerHasIsland(player.getUniqueId().toString());
    }
    public static boolean checkIfPlayerHasIsland(String playerUUID) {
        PlayerData playerData = new PlayerData(playerUUID);
        return playerData.getIfPlayerHasIsland();
    }
    public static SkyBlockIsland getSkyBlockIsland(String islandUuid) {
        return new SkyBlockIsland(islandUuid);
    }
}
