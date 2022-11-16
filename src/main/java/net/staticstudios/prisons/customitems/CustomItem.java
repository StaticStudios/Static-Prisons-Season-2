package net.staticstudios.prisons.customitems;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.utils.Prefix;
import org.bukkit.NamespacedKey;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.Optional;

public interface CustomItem {

    NamespacedKey customItemNamespace = new NamespacedKey(StaticPrisons.getInstance(), "customItem");
    NamespacedKey CUSTOM_ITEM_ID = new NamespacedKey(StaticPrisons.getInstance(), "customItemId");

    default void register() {
        CustomItems.ITEMS.put(getId(), this);
    }

    String getId();

    ItemStack getItem(Audience audience);

    default ItemStack getItem(Audience audience, String[] args) {
        return getItem(audience);
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
     *
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

    default Optional<Long> validateInput(Audience sender, String value) {
        long l;
        try {
            l = Long.parseLong(value);
        } catch (NumberFormatException e) {
            sender.sendMessage(Prefix.STATIC_PRISONS
                    .append(Component.text("Invalid number for money note: "))
                    .append(Component.text(value)));

            return Optional.of(-1L);
        }

        if (l < 1) {
            sender.sendMessage(Prefix.STATIC_PRISONS.append(Component.text("Amount must be greater than 0!")));

            return Optional.of(-1L);
        }
        return Optional.of(l);
    }
}
