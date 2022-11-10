package net.staticstudios.prisons.customitems.handler;

import net.staticstudios.prisons.StaticPrisons;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public interface CustomItem {

    NamespacedKey customItemNamespace = new NamespacedKey(StaticPrisons.getInstance(), "customItem");
    NamespacedKey CUSTOM_ITEM_ID = new NamespacedKey(StaticPrisons.getInstance(), "customItemId");

    default void register() {
        CustomItems.ITEMS.put(getId(), this);
    }

    String getId();

    ItemStack getItem(Player player);

    default ItemStack getItem(Player player, String[] args) {
        return getItem(player);
    }

    /**
     * Called when a player right-clicks an item and if the event has not been yet canceled.
     *
     * @param e The event.
     * @return Whether the event should be canceled.
     */
    default boolean onInteract(PlayerInteractEvent e) {
        return false;
    }

    /**
     * Marks this as a custom item. This will also be used to help call the onInteract method.
     * @param item The item to set as a custom item.
     * @return The item.
     */
    default ItemStack setCustomItem(ItemStack item, CustomItem customItem) {
        item.editMeta(itemMeta -> {
            itemMeta.getPersistentDataContainer().set(customItemNamespace, PersistentDataType.BYTE, (byte) 1);
            itemMeta.getPersistentDataContainer().set(CUSTOM_ITEM_ID, PersistentDataType.STRING, customItem.getId());
        });
        return item;
    }
}
