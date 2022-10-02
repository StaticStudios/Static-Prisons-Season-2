package net.staticstudios.prisons.crates;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

class CrateListener implements org.bukkit.event.Listener {
    @EventHandler
    void onInteract(PlayerInteractEvent e) {
        if (e.getClickedBlock() == null) return;
        Crate crate = null;
        for (Crate c : Crate.CRATES)
            if (e.getClickedBlock().getLocation().equals(c.LOCATION)) {
                crate = c;
                break;
            }
        if (crate == null) return;
        Player player = e.getPlayer();
        ItemStack key = player.getInventory().getItemInMainHand();
        e.setCancelled(true);
        if (key.hasItemMeta() && key.getItemMeta().getPersistentDataContainer().has(Crate.CRATE_KEY_NAMESPACE_KEY)) {
            boolean usingKey = key.getItemMeta().getPersistentDataContainer().get(Crate.CRATE_KEY_NAMESPACE_KEY, PersistentDataType.STRING).equals(crate.CRATE_KEY_ID);
            if (usingKey && e.getAction().isRightClick()) {
                crate.open(player);
                key.setAmount(key.getAmount() - 1);
            } else crate.preview(player);
        } else crate.preview(player);
    }
}
