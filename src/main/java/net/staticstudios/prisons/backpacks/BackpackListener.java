package net.staticstudios.prisons.backpacks;

import net.staticstudios.prisons.backpacks.gui.BackpackMenus;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class BackpackListener implements Listener {

    @EventHandler
    void onClick(PlayerInteractEvent e) {
        if (!e.getAction().isRightClick()) return;
        if (e.getItem() == null) return;
        Backpack backpack = Backpack.fromItem(e.getItem());
        if (backpack == null) return;
        e.setCancelled(true);
        BackpackMenus.upgradeBackpack(e.getPlayer(), backpack, true);
    }
}
