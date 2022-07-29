package net.staticstudios.prisons.lootboxes;

import net.staticstudios.prisons.backpacks.BackpackMenus;
import net.staticstudios.prisons.backpacks.PrisonBackpack;
import org.bukkit.Particle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class LootBoxListener implements Listener {

    @EventHandler
    void onClick(PlayerInteractEvent e) {
        if (e.getHand() == null) return;
        if (e.getHand().equals(EquipmentSlot.OFF_HAND)) return;
        LootBox lootBox = LootBox.fromItem(e.getPlayer().getInventory().getItemInMainHand());
        if (lootBox == null) return;
        e.setCancelled(true);
        if (lootBox.tryToClaim(e.getPlayer())) {
            e.getPlayer().getWorld().spawnParticle(Particle.LAVA, e.getPlayer().getLocation(), 20, 0.5, 0.5, 0.5);
        }
    }
}
