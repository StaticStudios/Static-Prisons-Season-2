package me.staticstudios.prisons.chat;

import me.staticstudios.prisons.newData.dataHandling.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MessageHandler {
    public static Map<UUID, UUID> playerToRecentMessaged = new HashMap<>();

    public static void sendMessage(Player from, Player to, String message) {
        to.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + from.getName() + ChatColor.DARK_GRAY + " > " + ChatColor.GREEN + ChatColor.BOLD + "You " + ChatColor.RESET + ChatColor.DARK_GRAY + "| " + ChatColor.WHITE + message);
        from.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "You " + ChatColor.DARK_GRAY + "> " + ChatColor.YELLOW + "" + ChatColor.BOLD + to.getName() + ChatColor.RESET + ChatColor.DARK_GRAY + " | " + ChatColor.WHITE + message);
        for (Player p : Bukkit.getOnlinePlayers()) if (new PlayerData(p).getIsWatchingMessages()) p.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + from.getName() + " said to " + to.getName() + ": " + message);
    }

}
