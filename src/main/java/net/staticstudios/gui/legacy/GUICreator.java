package net.staticstudios.gui.legacy;

import org.bukkit.event.inventory.InventoryType;

/**
 * Use this class for ANY menu that can change in any way
 */
@Deprecated
public class GUICreator extends StaticGUI {
    public GUICreator(int size, String title) {
        super(size, title);
        setDestroyOnClose(true);
    }

    public GUICreator(String title, InventoryType inventoryType) {
        super(title, inventoryType);
        setDestroyOnClose(true);
    }


}
