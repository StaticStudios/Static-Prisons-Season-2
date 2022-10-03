package net.staticstudios.prisons.fishing;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.staticstudios.prisons.fishing.events.FishCaughtEvent;
import net.staticstudios.prisons.utils.ComponentUtil;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DefaultFishingRod implements PrisonFishingRod {

    private final UUID uuid;
    private ItemStack item;
    private String name;
    private Component displayName;
    private int itemsCaught;
    private int caughtNothing;
    private int durability = 10;
    private int level;
    private long xp;

    public DefaultFishingRod() {
        this.uuid = UUID.randomUUID();
        item = new ItemStack(Material.FISHING_ROD);
        item.editMeta(meta -> {
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE);
            meta.setUnbreakable(true);
            meta.getPersistentDataContainer().set(FISHING_ROD_KEY, PersistentDataType.STRING, uuid.toString());
        });

        setName("&e&lFishing Rod");
        updateItemNow();
        register();
    }

    public DefaultFishingRod(UUID uuid, String name) {
        this.uuid = uuid;
        setName(name);
    }

    public static double getMaxDurability(int level) {
        return 10;
    }

    @Override
    public UUID getUuid() {
        return uuid;
    }

    @Override
    public @Nullable ItemStack getItem() {
        return item;
    }

    @Override
    public void setItem(ItemStack item) {
        this.item = item;
    }

    @Override
    public double getDurability() {
        return durability;
    }

    @Override
    public int getItemsCaught() {
        return itemsCaught;
    }

    @Override
    public int getCaughtNothing() {
        return caughtNothing;
    }

    @Override
    public Component getDisplayName() {
        return displayName;
    }

    @Override
    public void setName(String name) {
        this.name = name;
        displayName = LegacyComponentSerializer.legacyAmpersand().deserialize(name);
    }

    @Override
    public void updateItemNow() {
        if (getItem() == null) return;
        ItemMeta meta = getItem().getItemMeta();
        updateItemName(meta);
        updateItemLore(meta);
        getItem().setItemMeta(meta);
    }

    @Override
    public void onCatchFish(FishCaughtEvent e) {

        //TODO: enchantments

        //TODO: default rewards if enchants havent triggered something

        switch (e.getType()) {
            case NOTHING -> {
                setXp(xp + 15);
                caughtNothing++;
            }
            case TOKENS -> {
                setXp(xp + 25);
                itemsCaught++;
            }
            case SHARDS -> {
                setXp(xp + 75);
                itemsCaught++;
            }
            case PLAYER_XP -> {
                setXp(xp + 30);
                itemsCaught++;
            }
            case ITEM -> {
                setXp(xp + 30);
                itemsCaught++;
            }
        }

        durability -= e.getDurabilityLost();

        updateItem();
    }

    @Override
    public void onCastRod(PlayerFishEvent e) {
        if (durability <= 0) {
            e.getPlayer().sendMessage(Component.text("Your fishing rod has broken!"));
            e.setCancelled(true);
        }

        e.getHook().setMinWaitTime(2);
        e.getHook().setMaxWaitTime(10); //todo: temp
    }

    @Override
    public ConfigurationSection serialize() {
        ConfigurationSection config = new YamlConfiguration();
        return config;
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public long getXp() {
        return xp;
    }

    public void setXp(long xp) {
        this.xp = xp;

        while (xp >= getXpRequired()) {
            level++;
        }
    }


    private void updateItemName(ItemMeta meta) {
        meta.displayName(displayName.append(Component.text(" [" + itemsCaught + " Items Caught]")
                        .color(ComponentUtil.LIGHT_GRAY).decoration(TextDecoration.BOLD, false))
                .decoration(TextDecoration.ITALIC, false));
    }

    private void updateItemLore(ItemMeta meta) {

        Component durability = Component.text('|');
        Component durabilityBar = Component.empty();

        int durabilityBars = 40;

        for (int i = 0; i < durabilityBars; i++) {
            if (i < (this.durability / getMaxDurability(level) * durabilityBars)) {
                durabilityBar = durabilityBar.append(durability.color(ComponentUtil.GREEN));
            } else {
                durabilityBar = durabilityBar.append(durability.color(ComponentUtil.RED));
            }
        }

        List<Component> lore = new ArrayList<>();
        lore.add(Component.empty().append(Component.text("Level: ").color(ComponentUtil.YELLOW))
                .append(Component.text(PrisonUtils.addCommasToNumber(level)).color(ComponentUtil.WHITE))
                .decoration(TextDecoration.ITALIC, false));
        lore.add(Component.empty().append(Component.text("Experience: ").color(ComponentUtil.YELLOW))
                .append(Component.text(PrisonUtils.prettyNum(getXp()) + " / " + PrisonUtils.prettyNum(getXpRequired())).color(ComponentUtil.WHITE))
                .decoration(TextDecoration.ITALIC, false));
        lore.add(Component.empty().append(Component.text("Items Caught: ").color(ComponentUtil.YELLOW))
                .append(Component.text(PrisonUtils.addCommasToNumber(itemsCaught)).color(ComponentUtil.WHITE))
                .decoration(TextDecoration.ITALIC, false));
        lore.add(Component.empty().append(Component.text("Caught Nothing: ").color(ComponentUtil.YELLOW))
                .append(Component.text(PrisonUtils.addCommasToNumber(caughtNothing)).color(ComponentUtil.WHITE))
                .decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("Durability: ").color(ComponentUtil.YELLOW)
                .append(Component.text('[').color(ComponentUtil.LIGHT_GRAY))
                .append(durabilityBar)
                .append(Component.text(']').color(ComponentUtil.LIGHT_GRAY))
                .decoration(TextDecoration.ITALIC, false));
        meta.lore(lore);
    }
}
