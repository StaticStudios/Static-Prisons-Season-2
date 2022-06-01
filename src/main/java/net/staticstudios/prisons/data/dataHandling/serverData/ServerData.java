package net.staticstudios.prisons.data.dataHandling.serverData;

import net.staticstudios.prisons.data.dataHandling.PlayerData;
import net.staticstudios.prisons.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.math.BigInteger;
import java.util.logging.Level;

public class ServerData {

    public static final ServerDataServer SERVER = new ServerDataServer();
    public static final ServerDataPlayers PLAYERS = new ServerDataPlayers();
    public static final ServerDataIslands ISLANDS = new ServerDataIslands();

    public static void playerJoined(Player player) {
        if (!PLAYERS.getAllUUIDsAsStrings().contains(player.getUniqueId().toString())) {
            //First time joining
            Bukkit.getLogger().log(Level.INFO, "Player has joined for the first time with the uuid: " + player.getUniqueId() + " and the name: " + player.getName());
            Bukkit.broadcastMessage(org.bukkit.ChatColor.LIGHT_PURPLE + player.getName() + org.bukkit.ChatColor.GREEN + " joined for the first time! " + org.bukkit.ChatColor.GRAY + "(" + "#" + Utils.addCommasToNumber(PLAYERS.getAllUUIDsAsStrings().size() + 1) + ")");
            Utils.Players.addToInventory(player, Utils.createNewPickaxe());
            PlayerData playerData = new PlayerData(player);
            playerData.setBackpackSize(BigInteger.valueOf(25000));
            playerData.setPlayerRank("member");
            playerData.setStaffRank("member");
        }
        PLAYERS.updateNameAndUUID(player.getName(), player.getUniqueId());
    }
}
