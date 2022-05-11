package net.staticstudios.prisons.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class BroadcastMessage {
    public static void send(String msg) {
        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&8[&l&dServer Broadcast&r&8]&r " + msg));
    }
}
