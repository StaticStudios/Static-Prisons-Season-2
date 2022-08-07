package net.staticstudios.prisons.customItems;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public interface ICustomItem {


    default void register() {
        CustomItems.ITEMS.put(getID(), this);
    }

    String getID();
    ItemStack getItem(Player player);

    /**
     * Called when a player right-clicks an item and if the event has not been yet canceled.
     * @param e The event.
     * @return Whether the event should be canceled.
     */
    default boolean onInteract(PlayerInteractEvent e) {
        return false;
    }
}
