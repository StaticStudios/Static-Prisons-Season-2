package net.staticstudios.prisons.customitems.handler;

import net.staticstudios.prisons.StaticPrisons;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public interface CustomItem {

    NamespacedKey customItemNamespace = new NamespacedKey(StaticPrisons.getInstance(), "customItem");

    default void register() {
        CustomItems.ITEMS.put(getID(), this);
    }

    String getID();

    ItemStack getItem(Player player);

    /**
     * Called when a player right-clicks an item and if the event has not been yet canceled.
     *
     * @param e The event.
     * @return Whether the event should be canceled.
     */
    default boolean onInteract(PlayerInteractEvent e) {
        return false;
    }

    default ItemStack setCustomItem(ItemStack item) {
        item.editMeta(itemMeta -> itemMeta.getPersistentDataContainer().set(customItemNamespace, PersistentDataType.BYTE, (byte) 1));
        return item;
    }
}
