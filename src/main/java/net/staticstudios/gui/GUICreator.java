package net.staticstudios.gui;

import org.bukkit.inventory.ItemStack;

/**
 * Use this class for ANY menu that is only being viewed by one player
 * Use this to create "one time" menus
 */
public class GUICreator extends StaticGUI {
    public GUICreator(int size, String title) {
        super(size, title);
        setDestroyOnClose(true);
    }
    public void createMenu(Runnable r) {
        r.run();
    }

    public GUICreator setItems(ItemStack... items) {
        getInventory().clear();
        int i = 0;
        for (ItemStack item : items) {
            getInventory().setItem(i, item);
            i++;
        }
        return this;
    }
    public GUICreator setItem(int index, ItemStack item) {
        getInventory().setItem(index, item);
        return this;
    }
    public GUICreator addItem(ItemStack... item) {
        getInventory().addItem(item);
        return this;
    }
}
