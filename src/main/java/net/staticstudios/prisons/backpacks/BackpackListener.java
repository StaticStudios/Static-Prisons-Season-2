package net.staticstudios.prisons.backpacks;

import net.staticstudios.prisons.backpacks.gui.BackpackMenus;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class BackpackListener implements Listener {

    @EventHandler
    void onClick(PlayerInteractEvent e) {
        if (e.getHand() == null) return;
        if (e.getHand().equals(EquipmentSlot.OFF_HAND)) return;
        PrisonBackpack backpack = PrisonBackpack.fromItem(e.getPlayer().getInventory().getItemInMainHand());
        if (backpack == null) return;
        e.setCancelled(true);
        BackpackMenus.open(e.getPlayer(), backpack);
    }
}
