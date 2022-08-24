package net.staticstudios.prisons.utils;

import net.staticstudios.prisons.pickaxe.PrisonPickaxe;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.NamedTextColor.RED;

public class Events implements Listener {

    @EventHandler
    void onDrop(PlayerDropItemEvent e) {
        if (PrisonPickaxe.checkIsPrisonPickaxe(e.getItemDrop().getItemStack())) {
            e.setCancelled(true);
            e.getPlayer().sendMessage(text("You cannot drop this item! Type /dropitem to drop it!").color(RED));
        }
    }
}
