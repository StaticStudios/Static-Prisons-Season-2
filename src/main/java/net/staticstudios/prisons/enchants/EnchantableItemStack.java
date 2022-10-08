package net.staticstudios.prisons.enchants;

import net.staticstudios.prisons.utils.items.SpreadOutExecution;
import net.staticstudios.prisons.utils.items.SpreadOutExecutor;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public abstract class EnchantableItemStack implements Enchantable, SpreadOutExecution {

    private static final Map<Class<? extends EnchantableItemStack>, NamespacedKey> NAMESPACED_KEYS = new HashMap<>();
    private static final Map<Class<? extends EnchantableItemStack>, Map<UUID, EnchantableItemStack>> ITEMS = new HashMap<>();

    private final UUID uuid;
    private ItemStack itemStack;

    public EnchantableItemStack(UUID uuid) {
        this.uuid = uuid;
    }

    public static <T extends EnchantableItemStack> T fromItem(Class<T> clazz, ItemStack itemStack) {
        if (!isEnchantable(clazz, itemStack)) return null;
        String uuidAsString = itemStack.getItemMeta().getPersistentDataContainer().get(getNamespacedKey(clazz), PersistentDataType.STRING);
        if (uuidAsString == null) return null;
        UUID uuid = UUID.fromString(uuidAsString);
        T obj = fromUid(clazz, uuid);
        if (obj == null) return null;
        obj.setItem(itemStack);
        return obj;
    }

    public static <T extends EnchantableItemStack> T fromUid(Class<T> clazz, UUID uuid) {
        return uuid == null ? null : (T) ITEMS.getOrDefault(clazz, Collections.emptyMap()).get(uuid);
    }

    public static boolean isEnchantable(Class<? extends EnchantableItemStack> clazz, ItemStack itemStack) {
        if (itemStack == null) return false;
        ItemMeta meta = itemStack.getItemMeta();
        if (meta == null) return false;
        return meta.getPersistentDataContainer().has(getNamespacedKey(clazz), PersistentDataType.STRING);
    }

    public static NamespacedKey getNamespacedKey(Class<? extends EnchantableItemStack> clazz) {
        return NAMESPACED_KEYS.get(clazz);
    }

    public static void setNamespacedKey(Class<? extends  EnchantableItemStack> clazz, NamespacedKey key) {
        NAMESPACED_KEYS.put(clazz, key);
    }

    protected <T extends EnchantableItemStack> void register(T instance) {
        ITEMS.computeIfAbsent(instance.getClass(), k -> new HashMap<>()).put(instance.getUid(), instance);
    }

    public static <T extends EnchantableItemStack> Map<UUID, T> getMap(Class<T> clazz) {
        return (Map<UUID, T>) ITEMS.get(clazz);
    }


    public UUID getUid() {
        return uuid;
    }

    public boolean hasItem() {
        return itemStack != null;
    }

    @Nullable
    public ItemStack getItem() {
        return itemStack;
    }

    public void setItem(ItemStack item) {
        this.itemStack = item;
    }

    public void keyItem() {
        if (!hasItem()) return;
        getItem().editMeta(meta -> meta.getPersistentDataContainer().set(getNamespacedKey(getClass()), PersistentDataType.STRING, getUid().toString()));
    }

    public void updateItem() {
        queueExecution();
    }

    public void updateItemNow() {
        if (getItem() == null) return;
        getItem().editMeta(meta -> {
            updateItemName(meta);
            updateItemLore(meta);
        });

        SpreadOutExecutor.remove(this);
    }

    public abstract void updateItemName(ItemMeta meta);

    public abstract void updateItemLore(ItemMeta meta);

    @Override
    public void execute() {
        updateItemNow();
    }

}
