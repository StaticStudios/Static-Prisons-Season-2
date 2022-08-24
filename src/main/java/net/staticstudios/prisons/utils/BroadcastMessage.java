package net.staticstudios.prisons.utils;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;

public class BroadcastMessage {
    public static void send(String msg) {
        Bukkit.broadcast(Prefix.BROADCAST.append(Component.text(msg)));
    }
}
