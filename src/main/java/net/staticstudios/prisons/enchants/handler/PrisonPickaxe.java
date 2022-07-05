package net.staticstudios.prisons.enchants.handler;

import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.utils.Constants;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
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
    private static List<PrisonPickaxe> updateLoreQueue = new ArrayList<>();

    public static void init() {
        pickaxeUUIDToPrisonPickaxe = new HashMap<>();
        File dataFolder = new File(StaticPrisons.getInstance().getDataFolder(), "/data");
        dataFolder.mkdirs();
        File pickaxeData = new File(dataFolder, "pickaxeData.yml");
        if (!pickaxeData.exists()) return;
        FileConfiguration ymlData = YamlConfiguration.loadConfiguration(pickaxeData);
        for (String key : ymlData.getKeys(false)) {
            ConfigurationSection section = ymlData.getConfigurationSection(key);
            PrisonPickaxe pickaxe = new PrisonPickaxe(key);
            pickaxe.disabledEnchants = new HashSet<>(section.getStringList("disabledEnchants"));
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
        Map<String, PrisonPickaxe> temp = new HashMap<>(pickaxeUUIDToPrisonPickaxe);
        Bukkit.getScheduler().runTaskAsynchronously(StaticPrisons.getInstance(), () -> {
            File dataFolder = new File(StaticPrisons.getInstance().getDataFolder(), "/data");
            FileConfiguration ymlData = new YamlConfiguration();
            for (String key : temp.keySet()) {
                ConfigurationSection section = ymlData.createSection(key);
                PrisonPickaxe pickaxe = temp.get(key);
                for (BaseEnchant enchant : pickaxe.getEnchants()) section.set(enchant.ENCHANT_ID, pickaxe.getEnchantLevel(enchant.ENCHANT_ID));
                section.set("disabledEnchants", new ArrayList<>(pickaxe.disabledEnchants));
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
        });
    }

    public static void savePickaxeDataNow() {
        File dataFolder = new File(StaticPrisons.getInstance().getDataFolder(), "/data");
        FileConfiguration ymlData = new YamlConfiguration();
        for (String key : pickaxeUUIDToPrisonPickaxe.keySet()) {
            ConfigurationSection section = ymlData.createSection(key);
            PrisonPickaxe pickaxe = pickaxeUUIDToPrisonPickaxe.get(key);
            for (BaseEnchant enchant : pickaxe.getEnchants()) section.set(enchant.ENCHANT_ID, pickaxe.getEnchantLevel(enchant.ENCHANT_ID));
            section.set("disabledEnchants", new ArrayList<>(pickaxe.disabledEnchants));
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

    private final String pickaxeUUID;
    public ItemStack item = null;
    private Map<String, Integer> enchantLevels = new HashMap<>();
    private Set<String> disabledEnchants = new HashSet<>();
    private List<String> topLore = new ArrayList<>();
    public PrisonPickaxe setTopLore(List<String> topLore) {
        this.topLore = topLore;
        return this;
    }
    private List<String> bottomLore = new ArrayList<>();
    public PrisonPickaxe setBottomLore(List<String> bottomLore) {
        this.bottomLore = bottomLore;
        return this;
    }
    private long level = 0;
    private long xp = 0;
    private long blocksBroken = 0;
    private long rawBlocksBroken = 0;

    public PrisonPickaxe(String uuid) {
        pickaxeUUID = uuid;
        pickaxeUUIDToPrisonPickaxe.put(uuid, this);
    }

    public PrisonPickaxe(ItemStack item) {
        String uuid = UUID.randomUUID().toString();
        pickaxeUUID = uuid;
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


    public void setIsEnchantEnabled(Player player, BaseEnchant enchant, boolean enabled) {
        setIsEnchantEnabled(player, enchant.ENCHANT_ID, enabled);
    }
    public void setIsEnchantEnabled(Player player, String enchantID, boolean enabled) {
        if (enabled) {
            disabledEnchants.remove(enchantID);
        } else disabledEnchants.add(enchantID);

        if (player.getInventory().getItemInMainHand().equals(item)) {
            if (enabled) {
                //The player is holding the pickaxe and the held method should be called
                for (BaseEnchant enchant : getEnchants()) {
                    if (enchant.ENCHANT_ID.equals(enchantID)) {
                        enchant.onPickaxeHeld(player, this);
                        break;
                    }
                }
            } else {
                //The player is holding the pickaxe and the unheld method should be called
                for (BaseEnchant enchant : getEnchants()) {
                    if (enchant.ENCHANT_ID.equals(enchantID)) {
                        enchant.onPickaxeUnHeld(player, this);
                        break;
                    }
                }
            }

        }
    }
    public boolean getIsEnchantEnabled(BaseEnchant enchant) {
        return getIsEnchantEnabled(enchant.ENCHANT_ID);
    }
    public boolean getIsEnchantEnabled(String enchantID) {
        return !disabledEnchants.contains(enchantID);
    }



    public void setEnchantsLevel(BaseEnchant enchant, int level) {
        updateLoreQueue.add(this);
        enchantLevels.put(enchant.ENCHANT_ID, level);
    }
    public void setEnchantsLevel(String enchant, int level) {
        updateLoreQueue.add(this);
        enchantLevels.put(enchant, level);
    }

    public void addEnchantLevel(BaseEnchant enchant, int level) {
        setEnchantsLevel(enchant, getEnchantLevel(enchant) + level);
    }
    public void addEnchantLevel(String enchant, int level) {
        setEnchantsLevel(enchant, getEnchantLevel(enchant) + level);
    }

    public void removeEnchantLevel(BaseEnchant enchant, int level) {
        setEnchantsLevel(enchant, Math.max(0, getEnchantLevel(enchant) - level));
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
        if (this.level != level) updateLoreQueue.add(this);
        this.level = level;
    }
    public void setXp(long xp) {
        if (this.xp != xp) updateLoreQueue.add(this);
        this.xp = xp;
    }
    public void setBlocksBroken(long blocksBroken) {
        if (this.blocksBroken != blocksBroken) updateLoreQueue.add(this);
        this.blocksBroken = blocksBroken;
    }
    public void setRawBlocksBroken(long rawBlocksBroken) {
        if (this.rawBlocksBroken != rawBlocksBroken) updateLoreQueue.add(this);
        this.rawBlocksBroken = rawBlocksBroken;
    }
//BASE = 2500
//ROI = 2.4
//BASE * lvl + lvl * (ROI * lvl)^ROI
    static long getLevelRequirement(long level) {
        if (level <= 0) return 2500;
        return (long) (2500 * level + level * Math.pow(2.4 * level, 2.4));
    }

    void calcLevel() {
        long level = this.level;
        while (getXp() >= getLevelRequirement(level)) level++;
        this.level = level;
    }

    public void addXp(long xp) {
        if (this.xp >= getLevelRequirement(level)) calcLevel(); //The pickaxe should level up
        setXp(this.xp + xp);
    }
    public void addBlocksBroken(long blocksBroken) {
        setBlocksBroken(this.blocksBroken + blocksBroken);
    }
    public void addRawBlocksBroken(long rawBlocksBroken) {
        setRawBlocksBroken(this.rawBlocksBroken + rawBlocksBroken);
    }

    public static void dumpLoreToAllPickaxes() { //todo: spread all of the pickaxes across 100 ticks to prevent spikes
        for (PrisonPickaxe pickaxe : updateLoreQueue) {
            if (pickaxe.item == null) continue;
            ItemMeta meta = pickaxe.item.getItemMeta();
            meta.setLore(pickaxe.buildLore());
            pickaxe.item.setItemMeta(meta);
        }
        updateLoreQueue.clear();
    }

    public static void updateLore(ItemStack item) {
        PrisonPickaxe pickaxe = fromItem(item);
        ItemMeta meta = item.getItemMeta();
        meta.setLore(pickaxe.buildLore());
        item.setItemMeta(meta);
        updateLoreQueue.remove(pickaxe);
    }

    public PrisonPickaxe tryToUpdateLore() {
        if (item == null) return this;
        updateLore(item);
        return this;
    }

    final String LORE_DIVIDER = ChatColor.translateAlternateColorCodes('&', "&7---------------");
    public List<String> buildLore() {
        List<String> lore = new ArrayList<>();
        if (topLore != null) lore.addAll(topLore);
        lore.addAll(buildStatLore());
        lore.add(LORE_DIVIDER);
        lore.addAll(buildEnchantLore());
        if (bottomLore != null && !bottomLore.isEmpty()) {
            lore.add(LORE_DIVIDER);
            lore.addAll(bottomLore);
        }
        for (int i = 0; i < lore.size(); i++) lore.set(i, ChatColor.translateAlternateColorCodes('&', lore.get(i)));
        return lore;
    }

    private List<String> buildStatLore() {
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GREEN + "Level: " + ChatColor.WHITE + PrisonUtils.addCommasToNumber(level));
        lore.add(ChatColor.GREEN + "Experience: " + ChatColor.WHITE + PrisonUtils.prettyNum(xp) + " / " + PrisonUtils.prettyNum(getLevelRequirement(level)));
        lore.add(ChatColor.GREEN + "Blocks Mined: " + ChatColor.WHITE + PrisonUtils.addCommasToNumber(rawBlocksBroken));
        lore.add(ChatColor.GREEN + "Blocks Broken: " + ChatColor.WHITE + PrisonUtils.addCommasToNumber(blocksBroken));
        return lore;
    }

    private List<String> buildEnchantLore() {
        List<String> lore = new ArrayList<>();
        for (BaseEnchant ench : PrisonEnchants.ORDERED_ENCHANTS) {
            int level = getEnchantLevel(ench);
            if (level > 0) lore.add(ChatColor.AQUA + ench.UNFORMATTED_DISPLAY_NAME + ": " + PrisonUtils.addCommasToNumber(level));
        }
        return lore;
    }


    /**
     * Remove this pickaxe from the internal mapping, this should only be called on pickaxes that are "templates" and will only ever be used to view a preview of the ItemStack
     *
     * Once a pickaxe is removed, it can no longer be used by a player
     */
    public PrisonPickaxe delete() {
        pickaxeUUIDToPrisonPickaxe.remove(pickaxeUUID);
        return this;
    }
}
