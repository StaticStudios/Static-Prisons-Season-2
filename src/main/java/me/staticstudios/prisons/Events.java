package me.staticstudios.prisons;

import me.staticstudios.prisons.gameplay.crates.CrateManager;
import me.staticstudios.prisons.gameplay.customItems.Vouchers;
import me.staticstudios.prisons.core.data.serverData.PlayerData;
import me.staticstudios.prisons.core.enchants.EnchantEffects;
import me.staticstudios.prisons.core.enchants.PrisonEnchants;
import me.staticstudios.prisons.gameplay.events.EventManager;
import me.staticstudios.prisons.gameplay.gui.GUI;
import me.staticstudios.prisons.gameplay.islands.special.robots.BaseRobot;
import me.staticstudios.prisons.gameplay.Warps;
import me.staticstudios.prisons.gameplay.chat.CustomChatMessage;
import me.staticstudios.prisons.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.persistence.PersistentDataType;

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
        }
    }
    @EventHandler
    void entityInteract(PlayerInteractEntityEvent e) {
        if (BaseRobot.entityClicked(e)) return;
    }
}
