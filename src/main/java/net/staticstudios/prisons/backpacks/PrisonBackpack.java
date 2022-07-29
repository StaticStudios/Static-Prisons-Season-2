package net.staticstudios.prisons.backpacks;

import dev.dbassett.skullcreator.SkullCreator;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.mines.MineBlock;
import net.staticstudios.prisons.utils.ComponentUtil;
import net.staticstudios.prisons.utils.Constants;
import net.staticstudios.prisons.utils.PrisonUtils;
import net.staticstudios.prisons.utils.items.SpreadOutExecution;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class PrisonBackpack implements SpreadOutExecution {

    public static final Map<String, PrisonBackpack> ALL_BACKPACKS = new HashMap<>();

    public static void init() {
        loadBackpacks();
        Bukkit.getScheduler().runTaskTimer(StaticPrisons.getInstance(), () -> {
            PrisonBackpack.saveBackpacks();
        }, 20 * 60 * 5 + 20 * 17, 20 * 60); //Save them async every 5min
        StaticPrisons.getInstance().getServer().getPluginManager().registerEvents(new BackpackListener(), StaticPrisons.getInstance());
    }

    public static void loadBackpacks() {
        ALL_BACKPACKS.clear();
        FileConfiguration data = YamlConfiguration.loadConfiguration(new File(StaticPrisons.getInstance().getDataFolder(), "backpacks.yml"));
        for (String key : data.getKeys(false)) {
            PrisonBackpack backpack = new PrisonBackpack(key);
            backpack.tier = data.getInt(key + ".tier");
            backpack.size = data.getLong(key + ".size");
            backpack.itemCount = data.getLong(key + ".itemCount");
            backpack.value = data.getLong(key + ".value");
            ALL_BACKPACKS.put(key, backpack);
        }
    }

    public static void saveBackpacks() {
        Map<String, PrisonBackpack> temp = new HashMap<>(ALL_BACKPACKS);
        Bukkit.getScheduler().runTaskAsynchronously(StaticPrisons.getInstance(), () -> {
            PrisonBackpack.saveBackpacks(temp);
        });
    }

    public static void saveBackpacksNow() {
        saveBackpacks(ALL_BACKPACKS);
    }


    private static void saveBackpacks(Map<String, PrisonBackpack> data) {
        FileConfiguration fileConfig = new YamlConfiguration();
        for (Map.Entry<String, PrisonBackpack> entry : data.entrySet()) {
            ConfigurationSection section = fileConfig.createSection(entry.getKey());
            section.set("tier", entry.getValue().tier);
            section.set("size", entry.getValue().size);
            section.set("itemCount", entry.getValue().itemCount);
            section.set("value", entry.getValue().value);
        }
        try {
            fileConfig.save(new File(StaticPrisons.getInstance().getDataFolder(), "backpacks.yml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static PrisonBackpack fromItem(ItemStack item) {
        if (item == null) return null;
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return null;
        PrisonBackpack backpack = fromUUID(meta.getPersistentDataContainer().get(Constants.UUID_NAMESPACEKEY, PersistentDataType.STRING));
        if (backpack == null) return null;
        backpack.item = item;
        return backpack;
    }

    private static PrisonBackpack fromUUID(String uuid) {
        return ALL_BACKPACKS.get(uuid);
    }

    private String uuid;
    private ItemStack item;

    public String getUUID() {
        return uuid;
    }
    public ItemStack getItem() {
        return item;
    }

    //Stats
    private int tier = 1;
    private long size = 0;
    private long itemCount;
    private long value;

    public PrisonBackpack(ItemStack item, int tier) {
        uuid = UUID.randomUUID().toString();
        this.item = item;
        this.tier = tier;
        item.editMeta(meta -> meta.getPersistentDataContainer().set(Constants.UUID_NAMESPACEKEY, PersistentDataType.STRING, uuid));
        updateItem();
        ALL_BACKPACKS.put(uuid, this);
    }

    private PrisonBackpack(String uuid) {
        this.uuid = uuid;
        ALL_BACKPACKS.put(uuid, this);
    }

    public boolean isFull() {
        return itemCount >= size;
    }

    public int getTier() {
        return tier;
    }

    public long getSize() {
        return size;
    }
    public void setSize(long size) {
        this.size = size;
        updateItem();
    }
    public void addSize(long size) {
        setSize(this.size + size);
    }
    public void removeSize(long size) {
        setSize(this.size - size);
    }

    public long getItemCount() {
        return itemCount;
    }
    public void setItemCount(long itemCount) {
        this.itemCount = itemCount;
        updateItem();
    }
    public void addItemCount(long itemCount) {
        setItemCount(this.itemCount + itemCount);
    }
    public void removeItemCount(long itemCount) {
        setItemCount(this.itemCount - itemCount);
    }

    public long getValue() {
        return value;
    }
    public void setValue(long value) {
        this.value = value;
        updateItem();
    }
    public void addValue(long value) {
        setValue(this.value + value);
    }
    public void removeValue(long value) {
        setValue(this.value - value);
    }

    public void resetCount() {
        itemCount = 0;
        value = 0;
        updateItem();
    }

    /**
     *
     * @param contents what to add
     * @return the amount of items that could not be added
     */
    public Map<MineBlock, Long> addToBackpack(Map<MineBlock, Long> contents) {
        contents.remove(null);
        Map<MineBlock, Long> leftOver = new HashMap<>();

        for (Map.Entry<MineBlock, Long> entry : contents.entrySet()) {
            if (itemCount >= size) { //The backpack is full
                leftOver.put(entry.getKey(), entry.getValue());
                continue;
            }
            long blockValue = entry.getKey().value();
            long amount = entry.getValue();
            if (size >= itemCount + amount) { //The backpack can add the whole entry
                value += blockValue * amount;
                itemCount += amount;
            } else { //The backpack is not currently full but will be full after adding this entry, figure out how much can fit in the backpack and add only that amount
                amount = size - itemCount;
                value += blockValue * amount;
                itemCount = size;
                leftOver.put(entry.getKey(), entry.getValue() - amount); //The entry was partially added, so add the left over to the leftOver map
            }
        }
        updateItem();
        return leftOver;
    }





    public static Component getName(int tier) {
        switch (tier) {
            default -> {
                return BackpackConstants.tier1Name;
            }
            case 2 -> {
                return BackpackConstants.tier2Name;
            }
            case 3 -> {
                return BackpackConstants.tier3Name;
            }
            case 4 -> {
                return BackpackConstants.tier4Name;
            }
            case 5 -> {
                return BackpackConstants.tier5Name;
            }
            case 6 -> {
                return BackpackConstants.tier6Name;
            }
            case 7 -> {
                return BackpackConstants.tier7Name;
            }
            case 8 -> {
                return BackpackConstants.tier8Name;
            }
            case 9 -> {
                return BackpackConstants.tier9Name;
            }
            case 10 -> {
                return BackpackConstants.tier10Name;
            }
        }
    }


    public void updateItem() {
        queueExecution();
    }

    @Override
    public void runSpreadOutExecution() {
        updateItemName();
        updateItemLore();
    }

    public void updateItemName() {
        ItemMeta meta = item.getItemMeta();
        Component percentFull = Component.text(BigDecimal.valueOf(size <= 0 ? 100 : (double) itemCount / size * 100).setScale(2, RoundingMode.FLOOR) + "% Full");
        if (isFull()) {
            percentFull = percentFull.color(ComponentUtil.RED);
        }
        else {
            percentFull = percentFull.color(ComponentUtil.LIGHT_GRAY);
        }
        meta.displayName(Component.empty().append(getName(tier))
                .append(Component.text(" [" + PrisonUtils.prettyNum(itemCount) + "/" + PrisonUtils.prettyNum(size) + " | ").color(ComponentUtil.LIGHT_GRAY)).append(percentFull).append(Component.text( "]").color(ComponentUtil.LIGHT_GRAY)).decoration(TextDecoration.ITALIC, false));
        item.setItemMeta(meta);
    }

    private static final Component backpackLore = Component.text("Store additional items in your inventory").color(ComponentUtil.LIGHT_GRAY).decorate(TextDecoration.ITALIC);
    private static final Component bottomLore1 = Component.text("Right-click to upgrade!").color(ComponentUtil.LIGHT_GRAY).decoration(TextDecoration.ITALIC, false);

    public void updateItemLore() {
        //Cache the components and don't always recreate them if they haven't changed


        ItemMeta meta = item.getItemMeta();
        meta.lore(List.of(
                backpackLore,
                Component.empty(),
                Component.empty().append(Component.text("| ").color(ComponentUtil.GREEN).decorate(TextDecoration.BOLD)).append(Component.text("Tier: ").color(ComponentUtil.GREEN)).append(Component.text(PrisonUtils.addCommasToNumber(tier)).color(ComponentUtil.WHITE)).decoration(TextDecoration.ITALIC, false),
                Component.empty().append(Component.text("| ").color(ComponentUtil.GREEN).decorate(TextDecoration.BOLD)).append(Component.text("Capacity: ").color(ComponentUtil.GREEN)).append(Component.text(PrisonUtils.addCommasToNumber(size) + " Items").color(ComponentUtil.WHITE)).decoration(TextDecoration.ITALIC, false),
                Component.empty(),
                bottomLore1
        ));
        item.setItemMeta(meta);
    }



}
