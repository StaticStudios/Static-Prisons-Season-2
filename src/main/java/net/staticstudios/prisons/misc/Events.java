package net.staticstudios.prisons.misc;

import net.staticstudios.prisons.chat.events.ChatEvents;
import net.staticstudios.prisons.islands.special.robots.BaseRobot;
import net.staticstudios.prisons.chat.CustomChatMessage;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;

public class Events implements Listener {
    @EventHandler
    void onChat(AsyncPlayerChatEvent e) {
        new CustomChatMessage(e).sendFormatted();
//        ChatEvents.chatMessageReceived(e);
    }
    @EventHandler
    void onChangeHeld(PlayerItemHeldEvent e) {
        //EnchantEffects.giveEffect(e.getPlayer(), e.getPlayer().getInventory().getItem(e.getNewSlot()));
    }
    @EventHandler
    void invClick(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player)) return;
        //EnchantEffects.giveEffect((Player) e.getWhoClicked(), e.getWhoClicked().getInventory().getItemInMainHand());
    }

    @EventHandler
    void onDrop(PlayerDropItemEvent e) {
        if (PrisonUtils.checkIsPrisonPickaxe(e.getItemDrop().getItemStack())) {
            e.setCancelled(true);
            e.getPlayer().sendMessage(ChatColor.RED + "You cannot drop this item! Type /dropitem to drop it!");
            return;
        }
    }
    @EventHandler
    void entityInteract(PlayerInteractEntityEvent e) {
        if (BaseRobot.entityClicked(e)) return;
    }
}
