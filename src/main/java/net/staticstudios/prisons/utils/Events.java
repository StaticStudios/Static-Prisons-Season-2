package net.staticstudios.prisons.utils;

import net.staticstudios.prisons.chat.CustomChatMessage;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

public class Events implements Listener {
    @EventHandler
    void onChat(AsyncPlayerChatEvent e) {
        new CustomChatMessage(e).sendFormatted();
//        ChatEvents.chatMessageReceived(e);
    }

    @EventHandler
    void onDrop(PlayerDropItemEvent e) {
        if (PrisonUtils.checkIsPrisonPickaxe(e.getItemDrop().getItemStack())) {
            e.setCancelled(true);
            e.getPlayer().sendMessage(ChatColor.RED + "You cannot drop this item! Type /dropitem to drop it!");
            return;
        }
    }
}
