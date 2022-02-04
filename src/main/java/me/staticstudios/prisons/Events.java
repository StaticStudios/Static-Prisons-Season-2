package me.staticstudios.prisons;

import me.staticstudios.prisons.data.serverData.PlayerData;
import me.staticstudios.prisons.data.serverData.ServerData;
import me.staticstudios.prisons.misc.chat.CustomChatMessage;
import me.staticstudios.prisons.misc.scoreboard.CustomScoreboard;
import me.staticstudios.prisons.misc.tablist.TabList;
import me.staticstudios.prisons.utils.Utils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.math.BigInteger;

public class Events implements Listener {
    @EventHandler
    void playerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        //Check if they have joined before
        if (!new ServerData().checkIfPlayerHasJoinedBeforeByUUID(player.getUniqueId().toString())) {
            Bukkit.broadcastMessage(org.bukkit.ChatColor.LIGHT_PURPLE + player.getName() + org.bukkit.ChatColor.GREEN + " has joined the server for the first time! " + org.bukkit.ChatColor.GRAY + "(" + org.bukkit.ChatColor.AQUA + "#" + Utils.addCommasToInt(new ServerData().getPlayerUUIDsToNamesMap().size() + 1) + org.bukkit.ChatColor.GRAY + ")");
            Utils.addItemToPlayersInventoryAndDropExtra(player, Utils.createNewPickaxe());
            PlayerData playerData = new PlayerData(player);
            playerData.setPlayerRank("member");
            playerData.setStaffRank("member");
        }
        //Update the playernames
        new ServerData().putPlayerNamesToUUID(player.getName(), player.getUniqueId().toString());
        new ServerData().putPlayerUUIDsToName(player.getUniqueId().toString(), player.getName());

        //Set up the player's tablist
        TabList.onJoin(player);
        //Set up the player's scoreboard
        CustomScoreboard.updateSingleScoreboard(player);
    }
    @EventHandler
    void playerQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        //Remove player from the scoreboard map to prevent updating an offline player's scoreboard
        CustomScoreboard.playerLeft(player.getUniqueId().toString());
    }
    @EventHandler
    void onChat(AsyncPlayerChatEvent e) {
        new CustomChatMessage(e).sendFormatted();
    }
}
