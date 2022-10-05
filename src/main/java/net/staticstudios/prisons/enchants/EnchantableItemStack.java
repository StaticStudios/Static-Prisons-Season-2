package net.staticstudios.prisons.enchants;

import net.staticstudios.prisons.utils.items.SpreadOutExecution;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public interface EnchantableItemStack extends Enchantable, SpreadOutExecution {

    NamespacedKey getNamespacedKey();

    UUID getUuid();

    @Nullable
    ItemStack getItem();

    void setItem(ItemStack item);

    default void updateItem() {
        queueExecution();
    }

    default void updateItemNow() {
        if (getItem() == null) return;
        getItem().editMeta(meta -> {
            updateItemName(meta);
            updateItemLore(meta);
        });
    }

    void updateItemName(ItemMeta meta);

    void updateItemLore(ItemMeta meta);

    @Override
    default void execute() {
        updateItem();
    }

}
