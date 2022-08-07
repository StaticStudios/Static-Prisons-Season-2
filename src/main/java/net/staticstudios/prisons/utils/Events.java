package net.staticstudios.prisons.utils;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.staticstudios.prisons.chat.CustomChatMessage;
import net.staticstudios.prisons.pickaxe.PrisonPickaxe;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class Events implements Listener {
    @EventHandler
    void onChat(AsyncChatEvent e) {
        new CustomChatMessage(e).sendFormatted();
//        ChatEvents.chatMessageReceived(e);
    }

    @EventHandler
    void onDrop(PlayerDropItemEvent e) {
        if (PrisonPickaxe.checkIsPrisonPickaxe(e.getItemDrop().getItemStack())) {
            e.setCancelled(true);
            e.getPlayer().sendMessage(ChatColor.RED + "You cannot drop this item! Type /dropitem to drop it!");
            return;
        }
    }
}
