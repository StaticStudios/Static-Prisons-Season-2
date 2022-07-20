package net.staticstudios.prisons.pickaxe;

import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.pickaxe.enchants.handler.BaseEnchant;
import net.staticstudios.prisons.pickaxe.enchants.handler.PrisonEnchants;
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
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;

public class PrisonPickaxe {
    private static Map<String, PrisonPickaxe> pickaxeUUIDToPrisonPickaxe = new HashMap<>();
    private static final List<PrisonPickaxe> pickaxesToUpdateLore = new LinkedList<>();
    private static void addPickaxeToUpdateLore(PrisonPickaxe pickaxe) {
        if (pickaxe.item == null) return;
        if (!pickaxesToUpdateLore.contains(pickaxe)) {
            pickaxesToUpdateLore.add(pickaxe);
        }
    }

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
            pickaxesToUpdateLore.remove(pickaxe);
        }
        StaticPrisons.getInstance().getServer().getPluginManager().registerEvents(new Listener(), StaticPrisons.getInstance());

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
        addPickaxeToUpdateLore(this);
        enchantLevels.put(enchant.ENCHANT_ID, level);
    }
    public void setEnchantsLevel(String enchant, int level) {
        addPickaxeToUpdateLore(this);
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
        if (this.level != level) addPickaxeToUpdateLore(this);
        this.level = level;
    }
    public void setXp(long xp) {
        if (this.xp != xp) addPickaxeToUpdateLore(this);
        this.xp = xp;
    }
    public void setBlocksBroken(long blocksBroken) {
        if (this.blocksBroken != blocksBroken) addPickaxeToUpdateLore(this);
        this.blocksBroken = blocksBroken;
    }
    public void setRawBlocksBroken(long rawBlocksBroken) {
        if (this.rawBlocksBroken != rawBlocksBroken) addPickaxeToUpdateLore(this);
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


    /**
     * Updates all pickaxe lore instantly, clears the queue. This method should be called when the server shuts down.
     */
    public static void dumpLoreToAllPickaxesNow() {
        for (PrisonPickaxe pickaxe : pickaxesToUpdateLore) {
            if (pickaxe.item == null) continue;
            ItemMeta meta = pickaxe.item.getItemMeta();
            meta.setLore(pickaxe.buildLore());
            pickaxe.item.setItemMeta(meta);
        }
        pickaxesToUpdateLore.clear();

        currentDumpQueueTick = 0;
    }

    static final int DUMP_INTERVAL = 100; //The amount of ticks that this operation is spread across. It might take DUMP_INTERVAL * 2 ticks before a pickaxe's lore is updated.
    static ArrayList<PrisonPickaxe>[] pickaxeDumpQueue = new ArrayList[DUMP_INTERVAL]; //The array of lists of pickaxes that need to be updated. Each list in the array represents the pickaxes that should be done in that index's tick.
    static int currentDumpQueueTick = 0; //Number 1 - 100 representing the current tick.

    public static void dumpLoreToAllPickaxes() { //This method should be called every tick
        if (currentDumpQueueTick == 0) { //Build the queue for the next 100 ticks
            pickaxeDumpQueue = new ArrayList[DUMP_INTERVAL];
            int i = 0; //Represents the index in the pickaxesToUpdateLore list
            int iteration = 0; //Represents the amount of times the loop has run.
            while (i < pickaxesToUpdateLore.size()) {
                int amountToDumpThisTick = (int) Math.ceil(((double) pickaxesToUpdateLore.size() - i) / (DUMP_INTERVAL - iteration));
                ArrayList<PrisonPickaxe> pickaxes = new ArrayList<>();
                for (int x = i; x < i + amountToDumpThisTick; x++) {
                    pickaxes.add(pickaxesToUpdateLore.get(x));
                }
                pickaxeDumpQueue[iteration] = pickaxes;
                i += amountToDumpThisTick;
                iteration++;
            }
        }

        if (pickaxeDumpQueue[currentDumpQueueTick] != null) {
            for (PrisonPickaxe pickaxe : pickaxeDumpQueue[currentDumpQueueTick]) {
                if (pickaxe.item == null) continue;
                if (!pickaxesToUpdateLore.contains(pickaxe)) continue; //The lore was updated elsewhere
                pickaxe.item.editMeta(meta -> meta.setLore(pickaxe.buildLore()));
            }
            pickaxesToUpdateLore.removeAll(pickaxeDumpQueue[currentDumpQueueTick]);
        }
        currentDumpQueueTick = (currentDumpQueueTick + 1) % DUMP_INTERVAL;
    }

    public static void updateLore(ItemStack item) {
        PrisonPickaxe pickaxe = fromItem(item);
        item.editMeta(meta -> meta.setLore(pickaxe.buildLore()));
        pickaxesToUpdateLore.remove(pickaxe);
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
        lore.replaceAll(textToTranslate -> ChatColor.translateAlternateColorCodes('&', textToTranslate));
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrisonPickaxe that = (PrisonPickaxe) o;
        return Objects.equals(pickaxeUUID, that.pickaxeUUID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pickaxeUUID);
    }


    static class Listener implements org.bukkit.event.Listener {
        @EventHandler
        void onInteract(PlayerInteractEvent e) {
            Player player = e.getPlayer();
            if (Objects.equals(e.getHand(), EquipmentSlot.OFF_HAND)) return;
            //Check if the player is holding a pickaxe and is trying to open the enchants menu
            if (e.getAction().isRightClick()) {
                if (player.isSneaking()) {
                    if (PrisonUtils.checkIsPrisonPickaxe(player.getInventory().getItemInMainHand())) {
                        if (new PlayerData(player).getIsMobile()) return;
                        PickaxeMenus.open(player, PrisonPickaxe.fromItem(player.getInventory().getItemInMainHand()));
                        e.setCancelled(true);
                    }
                }
            }
        }
    }
}
