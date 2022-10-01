package net.staticstudios.prisons.data.serverdata;

import net.staticstudios.prisons.backpacks.Backpack;
import net.staticstudios.prisons.backpacks.BackpackManager;
import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.pickaxe.PrisonPickaxe;
import net.staticstudios.prisons.utils.PlayerUtils;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.logging.Level;

public class ServerData {

    public static final ServerDataServer SERVER = new ServerDataServer();
    public static final ServerDataPlayers PLAYERS = new ServerDataPlayers();
    public static final ServerDataIslands ISLANDS = new ServerDataIslands();
    public static final ServerDataPrivateMines PRIVATE_MINES = new ServerDataPrivateMines();
    public static final ServerDataReclaim RECLAIM = new ServerDataReclaim();

    public static void playerJoined(Player player) { //todo: clean this up
        if (!PLAYERS.getAllUUIDs().contains(player.getUniqueId())) {
            //First time joining
            Bukkit.getServer().getLogger().log(Level.INFO, "Player has joined for the first time with the uuid: " + player.getUniqueId() + " and the name: " + player.getName());
            Bukkit.broadcastMessage(org.bukkit.ChatColor.LIGHT_PURPLE + player.getName() + org.bukkit.ChatColor.GREEN + " joined for the first time! " + org.bukkit.ChatColor.GRAY + "(" + "#" + PrisonUtils.addCommasToNumber(PLAYERS.getAllUUIDs().size() + 1) + ")");
            PlayerUtils.addToInventory(player, PrisonPickaxe.createNewPickaxe());
            Backpack backpack = BackpackManager.createBackpack(1);
            backpack.setCapacity(2500);
            PlayerUtils.addToInventory(player, backpack.getItem());
            backpack.updateItemNow();
            PlayerData playerData = new PlayerData(player);
            playerData.setPlayerRank("member");
            playerData.setStaffRank("member");
        }
        PLAYERS.updateNameAndUUID(player.getName(), player.getUniqueId());
    }
}
