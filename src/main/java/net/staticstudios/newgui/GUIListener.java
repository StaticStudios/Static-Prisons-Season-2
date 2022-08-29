package net.staticstudios.newgui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.ItemStack;

/**
 * @author Sam (GitHub: <a href="https://github.com/Sammster10">Sam's GitHub</a>)
 */
public class GUIListener implements Listener {

    @EventHandler
    void onClick(InventoryClickEvent e) {
        if (e.getClickedInventory() == null) return;

        Player player = (Player) e.getWhoClicked();
        StaticGUI gui;
        GUIButton button;

        if (e.getClickedInventory().getHolder() instanceof StaticGUI _gui) {
            gui = _gui;
            if (!StaticGUI.MENUS.contains(gui)) return;
            button = gui.getButtons()[e.getSlot()];
            if (button == null) {
                //The player is using one of their own items
                if (!gui.getSettings().allowPlayerItems()) {
                    e.setCancelled(true);
                }
                return;
            }
        } else if (e.getInventory().getHolder() instanceof StaticGUI _gui) {
            gui = _gui;
            if (!StaticGUI.MENUS.contains(gui)) return;
            if (e.getClick().isShiftClick()) {
                //The player shift-clicked an item from their inventory, into the GUI
                if (!gui.getSettings().allowPlayerItems()) {
                    e.setCancelled(true);
                }
            }
            return;
        } else {
            return;
        }


        try {
            if (e.getClick().isLeftClick()) {
                button.onLeftClick().accept(player);
            } else if (e.getClick().isRightClick()) {
                button.onRightClick().accept(player);
            } else if (e.getClick().equals(ClickType.MIDDLE)) {
                button.onMiddleClick().accept(player);
            }
            button.onClick().accept(e, gui);
        } catch (Exception ex) {
            Bukkit.getServer().getLogger().severe("Error while handling GUI click event");
            ex.printStackTrace();
        }
        e.setCancelled(true);

    }

    @EventHandler
    void onDrag(InventoryDragEvent e) { //Prevent players from being able to lose items that they attempt to put into the GUI's inventory
        if (e.getInventory().getHolder() instanceof StaticGUI) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    void invClosed(InventoryCloseEvent e) {
        if (!(e.getInventory().getHolder() instanceof StaticGUI gui)) return;
        if (!StaticGUI.MENUS.contains(gui)) return;
        Player player = (Player) e.getPlayer();

        if (e.getReason().equals(InventoryCloseEvent.Reason.PLAYER)) { //Only call the onClose method if the player closed the GUI. The GUI could've been closed by another GUI opening which could lead to a stack overflow.
            if (gui.onClose() != null) {
                Bukkit.getScheduler().runTaskLater(StaticGUI.getParent(), () -> gui.onClose().accept(player, gui), 1); //Run 1 tick later to prevent inventory weirdness
            }
        }

        if (gui.getSettings().allowPlayerItems()) { //Give the player their items back
            ItemStack[] contents = gui.getInventory().getContents();
            for (int i = 0; i < contents.length; i++) {
                if (gui.getButtons()[i] == null) {
                    ItemStack item = gui.getInventory().getContents()[i];
                    if (item != null && item.getType() != Material.AIR) {
                        player.getInventory().addItem(item);
                        contents[i] = null; //Remove the item from the GUI's inventory in the event 2 players are viewing it to prevent item duplication.
                    }
                }
            }
        }

        if (!gui.isPersistent()) {
            gui.destroy();
        }
    }
    @EventHandler
    void invOpened(InventoryOpenEvent e) {
        if (!(e.getInventory().getHolder() instanceof StaticGUI gui)) return;
        if (gui.onOpen() == null) return;

        gui.onOpen().accept((Player) e.getPlayer(), gui);
    }
}
