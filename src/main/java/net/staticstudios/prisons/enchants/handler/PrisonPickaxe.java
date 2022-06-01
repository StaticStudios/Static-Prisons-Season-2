package net.staticstudios.prisons.enchants.handler;

import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.utils.Constants;
import net.staticstudios.prisons.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.*;
import java.util.logging.Level;

public class PrisonPickaxe {
    private static Map<String, PrisonPickaxe> pickaxeUUIDToPrisonPickaxe = new HashMap<>();

    public static void loadPickaxeData() {
        pickaxeUUIDToPrisonPickaxe = new HashMap<>();
        File dataFolder = new File(StaticPrisons.getInstance().getDataFolder(), "/data");
        dataFolder.mkdirs();
        File pickaxeData = new File(dataFolder, "pickaxeData.yml");
        if (!pickaxeData.exists()) return;
        FileConfiguration ymlData = YamlConfiguration.loadConfiguration(pickaxeData);
        for (String key : ymlData.getKeys(false)) {
            ConfigurationSection section = ymlData.getConfigurationSection(key);
            PrisonPickaxe pickaxe = new PrisonPickaxe(key);
            pickaxe.level = section.getLong("level");
            pickaxe.xp = section.getLong("xp");
            pickaxe.blocksBroken = section.getLong("blocksBroken");
            pickaxe.rawBlocksBroken = section.getLong("rawBlocksBroken");
            for (String _key : section.getKeys(false)) {
                switch (_key) { case "level", "xp", "blocksBroken", "rawBlocksBroken", "topLore", "bottomLore" -> { continue; }}
                pickaxe.setEnchantsLevel(_key, section.getInt(_key));
            }
        }

    }

    public static void savePickaxeData() {
        Bukkit.getScheduler().runTaskAsynchronously(StaticPrisons.getInstance(), PrisonPickaxe::savePickaxeDataNow);
    }

    public static void savePickaxeDataNow() {
        File dataFolder = new File(StaticPrisons.getInstance().getDataFolder(), "/data");
        File oldData = new File(dataFolder, "old");
        oldData.mkdirs();
        File pickaxeData = new File(dataFolder, "pickaxeData.yml");
        if (pickaxeData.exists()) pickaxeData.renameTo(new File(oldData, Instant.now().toEpochMilli() + ".yml"));
        FileConfiguration ymlData = new YamlConfiguration();
        for (String key : pickaxeUUIDToPrisonPickaxe.keySet()) {
            ConfigurationSection section = ymlData.createSection(key);
            PrisonPickaxe pickaxe = pickaxeUUIDToPrisonPickaxe.get(key);
            for (BaseEnchant enchant : pickaxe.getEnchants()) section.set(enchant.ENCHANT_ID, pickaxe.getEnchantLevel(enchant.ENCHANT_ID));
            section.set("level", pickaxe.level);
            section.set("xp", pickaxe.xp);
            section.set("blocksBroken", pickaxe.blocksBroken);
            section.set("rawBlocksBroken", pickaxe.rawBlocksBroken);
            section.set("topLore", pickaxe.topLore);
            section.set("bottomLore", pickaxe.bottomLore);
        }
        try {
            ymlData.save(new File(dataFolder, "pickaxeData.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Bukkit.getLogger().log(Level.INFO, "Saved all pickaxe data data/pickaxeData.yml");
    }

    public static PrisonPickaxe fromItem(ItemStack item) {
        PrisonPickaxe pickaxe = PrisonPickaxe.fromID(item.getItemMeta().getPersistentDataContainer().get(Constants.UUID_NAMESPACEKEY, PersistentDataType.STRING));
        if (pickaxe != null) pickaxe.item = item;
        return pickaxe;
    }

    public static PrisonPickaxe fromID(String pickaxeUUID) {
        return pickaxeUUIDToPrisonPickaxe.get(pickaxeUUID);
    }

    public ItemStack item = null;
    private Map<String, Integer> enchantLevels = new HashMap<>();
    private List<String> topLore = new ArrayList<>();
    private List<String> bottomLore = new ArrayList<>();
    private long level = 0;
    private long xp = 0;
    private long blocksBroken = 0;
    private long rawBlocksBroken = 0;

    public PrisonPickaxe(String uuid) {
        pickaxeUUIDToPrisonPickaxe.put(uuid, this);
    }

    public PrisonPickaxe(ItemStack item) {
        String uuid = UUID.randomUUID().toString();
        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(Constants.UUID_NAMESPACEKEY, PersistentDataType.STRING, uuid);
        item.setItemMeta(meta);
        pickaxeUUIDToPrisonPickaxe.put(uuid, this);
    }

    public List<BaseEnchant> getEnchants() {
        List<BaseEnchant> enchants = new ArrayList<>();
        for(String enchantID : enchantLevels.keySet()) if (getEnchantLevel(enchantID) > 0) enchants.add(PrisonEnchants.enchantIDToEnchant.get(enchantID));
        return enchants;
    }

    public int getEnchantLevel(BaseEnchant enchant) {
        return (getEnchantLevel(enchant.ENCHANT_ID));
    }
    public int getEnchantLevel(String enchantID) {
        if (enchantLevels.containsKey(enchantID)) return enchantLevels.get(enchantID);
        return 0;
    }

    public void setEnchantsLevel(BaseEnchant enchant, int level) {
        enchantLevels.put(enchant.ENCHANT_ID, level);
    }
    public void setEnchantsLevel(String enchant, int level) {
        enchantLevels.put(enchant, level);
    }

    public void addEnchantLevel(BaseEnchant enchant, int level) {
        setEnchantsLevel(enchant, getEnchantLevel(enchant) + level);
    }
    public void addEnchantLevel(String enchant, int level) {
        setEnchantsLevel(enchant, getEnchantLevel(enchant) + level);
    }

    public void removeEnchantLevel(BaseEnchant enchant, int level) {
        setEnchantsLevel(enchant,  Math.max(0, getEnchantLevel(enchant) - level));
    }

    public long getLevel() {
        return level;
    }
    public long getXp() {
        return xp;
    }
    public long getBlocksBroken() {
        return blocksBroken;
    }
    public long getRawBlocksBroken() {
        return rawBlocksBroken;
    }
    public void setLevel(long level) {
        this.level = level;
    }
    public void setXp(long xp) {
        this.xp = xp;
    }
    public void setBlocksBroken(long blocksBroken) {
        this.blocksBroken = blocksBroken;
    }
    public void setRawBlocksBroken(long rawBlocksBroken) {
        this.rawBlocksBroken = rawBlocksBroken;
    }
    public void addXp(long xp) {
        //todo recalc level
        this.xp += xp;
    }
    public void addBlocksBroken(long blocksBroken) {
        this.blocksBroken += blocksBroken;
    }
    public void addRawBlocksBroken(long rawBlocksBroken) {
        this.rawBlocksBroken += rawBlocksBroken;
    }

    public static void updateLore(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        meta.setLore(fromItem(item).buildLore());
        item.setItemMeta(meta);
    }

    public boolean tryToUpdateLore() {
        if (item == null) return false;
        updateLore(item);
        return true;
    }

    final String LORE_DIVIDER = ChatColor.translateAlternateColorCodes('&', "&7---------------");
    public List<String> buildLore() {
        List<String> lore = new ArrayList<>(topLore);
        lore.addAll(buildStatLore());
        lore.add(LORE_DIVIDER);
        lore.addAll(buildEnchantLore());
        if (!bottomLore.isEmpty()) {
            lore.add(LORE_DIVIDER);
            lore.addAll(bottomLore);
        }
        return lore;
    }

    private List<String> buildStatLore() {
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GREEN + "Level: " + ChatColor.WHITE + Utils.addCommasToNumber(level));
        lore.add(ChatColor.GREEN + "Experience: " + ChatColor.WHITE + Utils.addCommasToNumber(xp));
        lore.add(ChatColor.GREEN + "Blocks Mined: " + ChatColor.WHITE + Utils.addCommasToNumber(rawBlocksBroken));
        lore.add(ChatColor.GREEN + "Blocks Broken: " + ChatColor.WHITE + Utils.addCommasToNumber(blocksBroken));
        return lore;
    }

    private List<String> buildEnchantLore() {
        List<String> lore = new ArrayList<>();
        for (String enchantID : enchantLevels.keySet()) lore.add(ChatColor.AQUA + PrisonEnchants.enchantIDToEnchant.get(enchantID).UNFORMATTED_DISPLAY_NAME + ": " + Utils.addCommasToNumber(getEnchantLevel(enchantID)));
        return lore;
    }

/*
    public static Map<ItemStack, Map<String, Integer>> cachedPickaxeEnchants = new ConcurrentHashMap<>(); //Make sure that this keeps the current player's item in their main hand
    public static Map<ItemStack, Map<String, Long>> pickaxeStatsBuffer = new HashMap<>();

    public static Map<String, Integer> getCachedEnchants(ItemStack item) {
        if (!cachedPickaxeEnchants.containsKey(item)) updateCachedStats(item);
        return cachedPickaxeEnchants.get(item);
    }

    public static void updateCachedStats(ItemStack item) {
        Map<String, Integer> map = new HashMap<>();
        if (!Utils.checkIsPrisonPickaxe(item)) return;
        for (String ench : PrisonEnchants.enchantIDsToNames.keySet()) map.put(ench, (int) CustomEnchants.getEnchantLevel(item, ench));
        cachedPickaxeEnchants.put(item, map);
    }



    public static final long BASE_XP_PER_BLOCK_BROKEN = 2;
    public static final long BASE_XP_PER_PICKAXE_LEVEL = 15000;
    public static final int XP_INCREASES_EVERY_X_LEVELS = 25;
    public static long getXpRequiredForPickaxeLevel(long level) {
        long cost = BASE_XP_PER_BLOCK_BROKEN;
        for (int i = 0; i < level; i++) cost += (i / XP_INCREASES_EVERY_X_LEVELS + 1) * BASE_XP_PER_PICKAXE_LEVEL;
        return cost;
    }

    public static void dumpStatsToAllPickaxe() {
        for (ItemStack key : pickaxeStatsBuffer.keySet()) dumpStatsToPickaxe(key);
        //temp
        cachedPickaxeEnchants = new ConcurrentHashMap<>();
        pickaxeStatsBuffer = new HashMap<>();
    }

    public static void putPickaxeInBuffer(ItemStack pickaxe) {
        if (!Utils.checkIsPrisonPickaxe(pickaxe)) return;
        if (!pickaxeStatsBuffer.containsKey(pickaxe)) pickaxeStatsBuffer.put(pickaxe, new HashMap<>());
        Map<String, Long> stats = pickaxeStatsBuffer.get(pickaxe);
        stats.put("level", getLevelOnPickaxe(pickaxe));
        stats.put("xp", getXPOnPickaxe(pickaxe));
        stats.put("blocksMined", getBlocksMinedOnPickaxe(pickaxe));
        stats.put("blocksBroken", getBlocksBrokenOnPickaxe(pickaxe));
        stats.put("lastEdited", Instant.now().toEpochMilli());
        stats.put("lastDumped", Instant.now().toEpochMilli());
        pickaxeStatsBuffer.put(pickaxe, stats);
    }
    public static void dumpStatsToPickaxe(ItemStack item) {
        if (item == null) return;
        Map<String, Long> stats = pickaxeStatsBuffer.get(item);
        if (stats == null) return;
        if (stats.get("lastEdited") < stats.get("lastDumped")) return; //The values on the pick are the same as the ones in the map, no point to update
        setStatOnPickaxe(item, stats.get("level"), "level", "Level", Utils.addCommasToNumber(stats.get("level")));
        setStatOnPickaxe(item, stats.get("xp"), "xp", "Experience", Utils.prettyNum(stats.get("xp")) + " / " + Utils.prettyNum(getXpRequiredForPickaxeLevel(stats.get("level") + 1)));
        setStatOnPickaxe(item, stats.get("blocksMined"), "blocksMined", "Blocks Mined", Utils.addCommasToNumber(stats.get("blocksMined")));
        setStatOnPickaxe(item, stats.get("blocksBroken"), "blocksBroken", "Blocks Broken", Utils.addCommasToNumber(stats.get("blocksBroken")));
        stats.put("lastDumped", Instant.now().toEpochMilli());
    }
    public static void verifyPickIsInBuffer(ItemStack pickaxe) {
        if (!pickaxeStatsBuffer.containsKey(pickaxe)) putPickaxeInBuffer(pickaxe);
    }

    private static long getLevelOnPickaxe(ItemStack pickaxe) {
        ItemMeta meta = pickaxe.getItemMeta();
        long level = 0;
        if (meta.getPersistentDataContainer().has(new NamespacedKey(StaticPrisons.getInstance(), "level"), PersistentDataType.LONG)) {
            level = meta.getPersistentDataContainer().get(new NamespacedKey(StaticPrisons.getInstance(), "level"), PersistentDataType.LONG);
        }
        return level;
    }
    private static long getXPOnPickaxe(ItemStack pickaxe) {
        ItemMeta meta = pickaxe.getItemMeta();
        long xp = 0;
        if (meta.getPersistentDataContainer().has(new NamespacedKey(StaticPrisons.getInstance(), "xp"), PersistentDataType.LONG)) {
            xp = meta.getPersistentDataContainer().get(new NamespacedKey(StaticPrisons.getInstance(), "xp"), PersistentDataType.LONG);
        }
        return xp;
    }
    private static long getBlocksMinedOnPickaxe(ItemStack pickaxe) {
        ItemMeta meta = pickaxe.getItemMeta();
        long level = 0;
        if (meta.getPersistentDataContainer().has(new NamespacedKey(StaticPrisons.getInstance(), "blocksMined"), PersistentDataType.LONG)) {
            level = meta.getPersistentDataContainer().get(new NamespacedKey(StaticPrisons.getInstance(), "blocksMined"), PersistentDataType.LONG);
        }
        return level;
    }
    private static long getBlocksBrokenOnPickaxe(ItemStack pickaxe) {
        ItemMeta meta = pickaxe.getItemMeta();
        long level = 0;
        if (meta.getPersistentDataContainer().has(new NamespacedKey(StaticPrisons.getInstance(), "blocksBroken"), PersistentDataType.LONG)) {
            level = meta.getPersistentDataContainer().get(new NamespacedKey(StaticPrisons.getInstance(), "blocksBroken"), PersistentDataType.LONG);
        }
        return level;
    }



    public static void addLevel(ItemStack pickaxe, long levelsToAdd) {
        Map<String, Long> stats = pickaxeStatsBuffer.get(pickaxe);
        stats.put("level", stats.get("level") + levelsToAdd);
        stats.put("lastEdited", Instant.now().toEpochMilli());
    }
    public static long getLevel(ItemStack pickaxe) {
        return pickaxeStatsBuffer.get(pickaxe).get("level");
    }
    public static boolean addXP(ItemStack pickaxe, long xpToAdd) {
        Map<String, Long> stats = pickaxeStatsBuffer.get(pickaxe);
        stats.put("xp", stats.get("xp") + xpToAdd);
        stats.put("lastEdited", Instant.now().toEpochMilli());
        if (getXpRequiredForPickaxeLevel(getLevel(pickaxe) + 1) <= stats.get("xp")) {
            addLevel(pickaxe, 1);
            return true;
        }
        return false;
    }
    public static long getXP(ItemStack pickaxe) {
        return pickaxeStatsBuffer.get(pickaxe).get("xp");
    }
    public static void addBlocksMined(ItemStack pickaxe, long blocksMined) {
        Map<String, Long> stats = pickaxeStatsBuffer.get(pickaxe);
        stats.put("blocksMined", stats.get("blocksMined") + blocksMined);
        stats.put("lastEdited", Instant.now().toEpochMilli());
    }
    public static long getBlocksMined(ItemStack pickaxe) {
        return pickaxeStatsBuffer.get(pickaxe).get("blocksMined");
    }

    public static void addBlocksBroken(ItemStack pickaxe, long blocksBroken) {
        Map<String, Long> stats = pickaxeStatsBuffer.get(pickaxe);
        stats.put("blocksBroken", stats.get("blocksBroken") + blocksBroken);
        stats.put("lastEdited", Instant.now().toEpochMilli());
    }
    public static long getBlocksBroken(ItemStack pickaxe) {
        return pickaxeStatsBuffer.get(pickaxe).get("blocksBroken");
    }

    public static void setStatOnPickaxe(ItemStack pickaxe, Long stat, String statID, String loreDisplay, String loreValue) {
        ItemMeta meta = pickaxe.getItemMeta();
        meta.getPersistentDataContainer().set(new NamespacedKey(StaticPrisons.getInstance(), statID), PersistentDataType.LONG, stat);
        boolean updated = false;
        List<String> lore = new ArrayList<>();
        if (meta.hasLore()) {
            lore = meta.getLore();
            for (int i = 0; i < lore.size(); i++) {
                String line = lore.get(i);
                if (ChatColor.stripColor(line).startsWith(loreDisplay + ":")) {
                    lore.set(i, ChatColor.GREEN + loreDisplay + ": " + ChatColor.WHITE + loreValue);
                    updated = true;
                    break;
                }
            }
            if (!updated) lore.add(ChatColor.GREEN + loreDisplay + ": " + ChatColor.WHITE + loreValue);
        } else {
            lore.add(ChatColor.GREEN + loreDisplay + ": " + ChatColor.WHITE + loreValue);
        }
        meta.setLore(lore);
        pickaxe.setItemMeta(meta);
    }

 */
}
