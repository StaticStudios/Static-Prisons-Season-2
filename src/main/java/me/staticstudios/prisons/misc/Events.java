package me.staticstudios.prisons.misc;

import me.staticstudios.prisons.enchants.EnchantEffects;
import me.staticstudios.prisons.events.EventManager;
import me.staticstudios.prisons.islands.special.robots.BaseRobot;
import me.staticstudios.prisons.chat.CustomChatMessage;
import me.staticstudios.prisons.mineBombs.MineBombItem;
import me.staticstudios.prisons.utils.Utils;
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
        EventManager.chatMessageReceived(e);
    }
    @EventHandler
    void onChangeHeld(PlayerItemHeldEvent e) {
        EnchantEffects.giveEffect(e.getPlayer(), e.getPlayer().getInventory().getItem(e.getNewSlot()));
    }
    @EventHandler
    void invClick(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player)) return;
        EnchantEffects.giveEffect((Player) e.getWhoClicked(), e.getWhoClicked().getInventory().getItemInMainHand());
    }

    @EventHandler
    void onDrop(PlayerDropItemEvent e) {
        if (Utils.checkIsPrisonPickaxe(e.getItemDrop().getItemStack())) {
            e.setCancelled(true);
            e.getPlayer().sendMessage(ChatColor.RED + "You cannot drop this item! Type /dropitem to drop it!");
            return;
        }
        MineBombItem.itemDropped(e);
    }
    @EventHandler
    void entityInteract(PlayerInteractEntityEvent e) {
        if (BaseRobot.entityClicked(e)) return;
    }
}
