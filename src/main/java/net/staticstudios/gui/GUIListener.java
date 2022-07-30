package net.staticstudios.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

import java.util.function.Consumer;

public class GUIListener implements Listener {

    @EventHandler
    void onClick(InventoryClickEvent e) {
        if (e.getClickedInventory() == null) return;
        if (!(e.getClickedInventory().getHolder() instanceof StaticGUI gui)) {
            if (e.getInventory().getHolder() instanceof StaticGUI) e.setCancelled(e.getClick().isShiftClick());
            return;
        }

        if (e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR)) {
            if (gui.getOnClickEmptySlot() != null) gui.getOnClickEmptySlot().accept(e);
            else e.setCancelled(true);
            return;
        }


        Consumer<InventoryClickEvent> listener = new GUIItem(e.getCurrentItem()).getListener();
        if (listener == null) {
            e.setCancelled(true);
            new GUIItem(e.getCurrentItem()).getRunnable().run((Player) e.getWhoClicked(), e.getClick());
            return;
        }
        listener.accept(e);
    }
    @EventHandler
    void onDrag(InventoryDragEvent e) { //Prevent players from being able to lose items that they attempt to put into the GUI's inventory
        if (e.getInventory().getHolder() instanceof StaticGUI) e.setCancelled(true);
    }

    @EventHandler
    void invClosed(InventoryCloseEvent e) {
        if (!(e.getInventory().getHolder() instanceof StaticGUI gui)) return;
        Player player = (Player) e.getPlayer();
        if (gui.destroyOnClose) gui.destroy();
        if (e.getReason().equals(InventoryCloseEvent.Reason.PLAYER)) if (gui.getMenuToOpenOnClose() != null)  {
            Bukkit.getScheduler().runTaskLater(StaticGUI.getParent(), () -> gui.getMenuToOpenOnClose().open(player), 1);
        } else if (gui.getOnCloseRun() != null) {
            Bukkit.getScheduler().runTaskLater(StaticGUI.getParent(), () -> gui.getOnCloseRun().run(player, null), 1);
        }
    }
    @EventHandler
    void invOpened(InventoryOpenEvent e) {
        if (!(e.getInventory().getHolder() instanceof StaticGUI)) return;
    }
}
