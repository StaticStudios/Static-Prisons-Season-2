package net.staticstudios.prisons.fishing;

import net.kyori.adventure.text.Component;
import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.fishing.events.FishCaughtEvent;
import net.staticstudios.prisons.utils.items.SpreadOutExecution;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public interface PrisonFishingRod extends SpreadOutExecution {

    Map<UUID, PrisonFishingRod> FISHING_RODS = new HashMap<>();
    NamespacedKey FISHING_ROD_KEY = new NamespacedKey(StaticPrisons.getInstance(), "fishingRod");

    static PrisonFishingRod fromUuid(UUID uuid) {
        return FISHING_RODS.get(uuid);
    }

    static PrisonFishingRod fromItem(ItemStack item) {
        if (item == null) return null;
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return null;
        String uuid = meta.getPersistentDataContainer().get(FISHING_ROD_KEY, PersistentDataType.STRING);
        if (uuid == null) return null;
        PrisonFishingRod fishingRod = fromUuid(UUID.fromString(uuid));
        if (fishingRod == null) return null;
        fishingRod.setItem(item);
        return fishingRod;
    }

    default void register() {
        FISHING_RODS.put(getUuid(), this);
    }

    UUID getUuid();

    @Nullable
    ItemStack getItem();

    void setItem(ItemStack item);

    double getDurability();

    int getItemsCaught();

    int getCaughtNothing();

    Component getDisplayName();

    void setName(String name);

    void updateItemNow();

    void onCatchFish(FishCaughtEvent e);

    void onCastRod(PlayerFishEvent e);

    ConfigurationSection serialize();

    int getLevel();

    long getXp();

    default long getXpRequired() {
        return getXpRequired(getLevel());
    }

    default long getXpRequired(int level) {
        return (long) (Math.pow(level + 1, 2) * 100);
    }

    @Override
    default void execute() {
        updateItemNow();
    }

    default void updateItem() {
        if (getItem() == null) return;
        queueExecution();
    }

}
