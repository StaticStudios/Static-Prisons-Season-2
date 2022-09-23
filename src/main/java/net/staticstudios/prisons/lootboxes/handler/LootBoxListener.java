package net.staticstudios.prisons.lootboxes.handler;

import net.staticstudios.prisons.blockbreak.BlockBreak;
import net.staticstudios.prisons.blockbreak.BlockBreakProcessEvent;
import org.bukkit.Particle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.LinkedList;
import java.util.List;

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

    @EventHandler
    void onBlockBreakProcess(BlockBreakProcessEvent e) {
        BlockBreak blockBreak = e.getBlockBreak();
        if (blockBreak.getPlayer() == null) return;
        List<LootBox> lootBoxes = new LinkedList<>();
        for (ItemStack itemStack : blockBreak.getPlayer().getInventory().getContents()) {
            LootBox lootBox = LootBox.fromItem(itemStack);
            if (lootBox != null) lootBoxes.add(lootBox);
        }
        for (LootBox lootBox : lootBoxes) {
            lootBox.onBlockBreak(blockBreak);
        }
    }
}
