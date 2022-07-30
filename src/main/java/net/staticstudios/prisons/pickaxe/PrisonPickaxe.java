package net.staticstudios.prisons.pickaxe;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.pickaxe.abilities.handler.BaseAbility;
import net.staticstudios.prisons.pickaxe.abilities.handler.PickaxeAbilities;
import net.staticstudios.prisons.pickaxe.enchants.handler.BaseEnchant;
import net.staticstudios.prisons.pickaxe.enchants.handler.PickaxeEnchants;
import net.staticstudios.prisons.utils.ComponentUtil;
import net.staticstudios.prisons.utils.Constants;
import net.staticstudios.prisons.utils.PrisonUtils;
import net.staticstudios.prisons.utils.items.SpreadOutExecution;
import net.staticstudios.prisons.utils.items.SpreadOutExecutor;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;

public class PrisonPickaxe implements SpreadOutExecution {

    public static final NamespacedKey PICKAXE_KEY = new NamespacedKey(StaticPrisons.getInstance(), "pickaxeUUID");

    private static Map<String, PrisonPickaxe> pickaxeUUIDToPrisonPickaxe = new HashMap<>();


    private static void addPickaxeToUpdateLore(PrisonPickaxe pickaxe) {
        if (pickaxe.item == null) return;
        pickaxe.queueExecution();
    }

    public static void init() {
        loadPickaxeData();
        StaticPrisons.getInstance().getServer().getPluginManager().registerEvents(new Listener(), StaticPrisons.getInstance());

    }

    public static void loadPickaxeData() {
        pickaxeUUIDToPrisonPickaxe.clear();
        File pickaxeData = new File(StaticPrisons.getInstance().getDataFolder(), "data/pickaxeData.yml");
        FileConfiguration ymlData = YamlConfiguration.loadConfiguration(pickaxeData);


        for (String key : ymlData.getKeys(false)) {
            ConfigurationSection pickaxeSection = ymlData.getConfigurationSection(key);
            ConfigurationSection statsSection = pickaxeSection.getConfigurationSection("stats");
            ConfigurationSection enchantsSection = pickaxeSection.getConfigurationSection("enchants");
            ConfigurationSection abilitiesSection = pickaxeSection.getConfigurationSection("attributes");


            PrisonPickaxe pickaxe = new PrisonPickaxe(key);

            //Stats
            pickaxe.xp = statsSection.getInt("xp");
            pickaxe.level = statsSection.getInt("level");
            pickaxe.blocksBroken = statsSection.getInt("blocksBroken");
            pickaxe.rawBlocksBroken = statsSection.getInt("rawBlocksBroken");
            pickaxe.topLore = statsSection.getStringList("topLore");
            pickaxe.bottomLore = statsSection.getStringList("bottomLore");
            String name = statsSection.getString("name");
            if (name != null) {
                pickaxe.name = name;
            }
            pickaxe.nameAsComponent = LegacyComponentSerializer.legacyAmpersand().deserialize(pickaxe.name);

            //Enchants
            for (String enchantKey : enchantsSection.getKeys(false)) {
                ConfigurationSection enchantSection = enchantsSection.getConfigurationSection(enchantKey);
                if (enchantSection == null) continue;
                int lvl = enchantSection.getInt("lvl");
                int tier = enchantSection.getInt("tier");
                boolean enabled = enchantSection.getBoolean("enabled");
                if (!enabled) {
                    pickaxe.disabledEnchants.add(enchantKey);
                }

                pickaxe.enchantLevels.put(enchantKey, lvl);
                pickaxe.enchantTiers.put(enchantKey, tier);
            }

            //Abilities
            for (String abilitiesKey : abilitiesSection.getKeys(false)) {
                ConfigurationSection attributeSection = abilitiesSection.getConfigurationSection(abilitiesKey);
                if (attributeSection == null) continue;
                int lvl = attributeSection.getInt("lvl");
                long lastUsed = attributeSection.getLong("lastUsed");

                pickaxe.abilityLevels.put(abilitiesKey, lvl);
                pickaxe.lastActivatedAbilitiesAt.put(abilitiesKey, lastUsed);
            }

            //Don't update the lore
            SpreadOutExecutor.remove(pickaxe);
        }
    }

    public static void savePickaxeData() {
        Map<String, PrisonPickaxe> temp = new HashMap<>(pickaxeUUIDToPrisonPickaxe);
        Bukkit.getScheduler().runTaskAsynchronously(StaticPrisons.getInstance(), () -> {
            savePickaxeData(temp);
        });
    }

    public static void savePickaxeDataNow() {
        savePickaxeData(pickaxeUUIDToPrisonPickaxe);
    }


    private static void savePickaxeData(Map<String, PrisonPickaxe> pickaxeUUIDToPrisonPickaxe) {
        File dataFolder = new File(StaticPrisons.getInstance().getDataFolder(), "/data");
        FileConfiguration allData = new YamlConfiguration();

        for (String key : pickaxeUUIDToPrisonPickaxe.keySet()) {
            ConfigurationSection pickaxeSection = allData.createSection(key);
            ConfigurationSection statsSection = pickaxeSection.createSection("stats");
            ConfigurationSection enchantsSection = pickaxeSection.createSection("enchants");
            ConfigurationSection abilitiesSection = pickaxeSection.createSection("attributes");


            PrisonPickaxe pickaxe = pickaxeUUIDToPrisonPickaxe.get(key);

            //Stats
            statsSection.set("xp", pickaxe.xp);
            statsSection.set("level", pickaxe.level);
            statsSection.set("blocksBroken", pickaxe.blocksBroken);
            statsSection.set("rawBlocksBroken", pickaxe.rawBlocksBroken);
            statsSection.set("topLore", pickaxe.topLore);
            statsSection.set("bottomLore", pickaxe.bottomLore);
            statsSection.set("name", pickaxe.name);

            //Enchants
            for (String enchantKey : pickaxe.enchantLevels.keySet()) {
                ConfigurationSection enchantSection = enchantsSection.createSection(enchantKey);
                enchantSection.set("lvl", pickaxe.enchantLevels.get(enchantKey));
                enchantSection.set("tier", pickaxe.enchantTiers.get(enchantKey));
                enchantSection.set("enabled", !pickaxe.disabledEnchants.contains(enchantKey));
            }

            //Abilities
            for (String abilityKey : pickaxe.abilityLevels.keySet()) {
                ConfigurationSection abilitySection = abilitiesSection.createSection(abilityKey);
                abilitySection.set("lvl", pickaxe.abilityLevels.get(abilityKey));
                abilitySection.set("lastUsed", pickaxe.lastActivatedAbilitiesAt.get(abilityKey));
            }

        }
        try {
            allData.save(new File(dataFolder, "pickaxeData.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Bukkit.getLogger().log(Level.INFO, "Saved all pickaxe data data/pickaxeData.yml");
    }

    public static PrisonPickaxe fromItem(ItemStack item) {
        if (item == null) return null;
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return null;
        PrisonPickaxe pickaxe = PrisonPickaxe.fromID(meta.getPersistentDataContainer().get(PICKAXE_KEY, PersistentDataType.STRING));
        if (pickaxe != null) pickaxe.item = item;
        return pickaxe;
    }

    public static PrisonPickaxe fromID(String pickaxeUUID) {
        return pickaxeUUIDToPrisonPickaxe.get(pickaxeUUID);
    }

    private final String pickaxeUUID;
    public ItemStack item = null;
    private Map<String, Integer> enchantLevels = new HashMap<>();
    private Map<String, Integer> enchantTiers = new HashMap<>();
    private Map<String, Integer> abilityLevels = new HashMap<>();
    private Map<String, Long> lastActivatedAbilitiesAt = new HashMap<>();
    private Set<String> disabledEnchants = new HashSet<>();

    private long level = 0;
    private long xp = 0;
    private long blocksBroken = 0;
    private long rawBlocksBroken = 0;
    private String name = "&b&lPrison Pickaxe";
    private Component nameAsComponent = LegacyComponentSerializer.legacyAmpersand().deserialize(name);

    public PrisonPickaxe(String uuid) {
        pickaxeUUID = uuid;
        pickaxeUUIDToPrisonPickaxe.put(uuid, this);
    }

    public PrisonPickaxe(ItemStack item) {
        String uuid = UUID.randomUUID().toString();
        pickaxeUUID = uuid;
        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(PICKAXE_KEY, PersistentDataType.STRING, uuid);
        item.setItemMeta(meta);
        pickaxeUUIDToPrisonPickaxe.put(uuid, this);
    }

    public List<BaseEnchant> getEnchants() {
        List<BaseEnchant> enchants = new ArrayList<>();
        for(String enchantID : enchantLevels.keySet()) {
            if (getEnchantLevel(enchantID) > 0) {
                enchants.add(PickaxeEnchants.enchantIDToEnchant.get(enchantID));
            }
        }
        return enchants;
    }

    public int getEnchantLevel(BaseEnchant enchant) {
        return getEnchantLevel(enchant.ENCHANT_ID);
    }
    public int getEnchantLevel(String enchantID) {
        if (enchantLevels.containsKey(enchantID)) {
            return enchantLevels.get(enchantID);
        }
        return 0;
    }

    public int getEnchantTier(BaseEnchant enchant) {
        return getEnchantTier(enchant.ENCHANT_ID);
    }
    public int getEnchantTier(String enchantID) {
        if (enchantTiers.containsKey(enchantID)) {
            return enchantTiers.get(enchantID);
        }
        return 0;
    }


    public void setIsEnchantEnabled(Player player, BaseEnchant enchant, boolean enabled) {
        setIsEnchantEnabled(player, enchant.ENCHANT_ID, enabled);
    }
    public void setIsEnchantEnabled(Player player, String enchantID, boolean enabled) {
        if (enabled) {
            disabledEnchants.remove(enchantID);
        } else disabledEnchants.add(enchantID);

        //activate or deactivate enchants that have been enabled or disabled if the player is holding the pickaxe

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




    public List<BaseAbility> getAbilities() {
        List<BaseAbility> abilities = new ArrayList<>();
        for(String abilityID : abilityLevels.keySet()) {
            if (getAbilityLevel(abilityID) > 0) {
                abilities.add(PickaxeAbilities.abilityIDToAbility.get(abilityID));
            }
        }
        return abilities;
    }
    public int getAbilityLevel(BaseAbility ability) {
        return getAbilityLevel(ability.ABILITY_ID);
    }
    public int getAbilityLevel(String abilityID) {
        if (abilityLevels.containsKey(abilityID)) {
            return abilityLevels.get(abilityID);
        }
        return 0;
    }

    public void setAbilityLevel(BaseAbility ability, int level) {
        addPickaxeToUpdateLore(this);
        abilityLevels.put(ability.ABILITY_ID, level);
    }
    public void setAbilityLevel(String abilityID, int level) {
        addPickaxeToUpdateLore(this);
        abilityLevels.put(abilityID, level);
    }

    public void addAbilityLevel(BaseAbility ability, int level) {
        setAbilityLevel(ability, getAbilityLevel(ability) + level);
    }
    public void addAbilityLevel(String abilityID, int level) {
        setAbilityLevel(abilityID, getAbilityLevel(abilityID) + level);
    }

    public void removeAbilityLevel(BaseAbility ability, int level) {
        setAbilityLevel(ability, Math.max(0, getAbilityLevel(ability) - level));
    }

    public long getLastActivatedAbilityAt(BaseAbility ability) {
        return getLastActivatedAbilityAt(ability.ABILITY_ID);
    }
    public long getLastActivatedAbilityAt(String abilityID) {
        return lastActivatedAbilitiesAt.getOrDefault(abilityID, 0L);
    }
    public void setLastActivatedAbilityAt(BaseAbility ability, long time) {
        setLastActivatedAbilityAt(ability.ABILITY_ID, time);
    }
    public void setLastActivatedAbilityAt(String abilityID, long time) {
        lastActivatedAbilitiesAt.put(abilityID, time);
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
    public static long getLevelRequirement(long level) {
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


    public void setName(String name) {
        this.name = name;
        nameAsComponent = LegacyComponentSerializer.legacyAmpersand().deserialize(name);
    }



    private List<String> topLore = new ArrayList<>();
    private List<Component> topLoreAsComponent = new ArrayList<>();
    public PrisonPickaxe setTopLore(List<String> topLore) {
        this.topLore = topLore;
        if (topLore == null) return this;
        this.topLoreAsComponent = new ArrayList<>();
        for (String line : topLore) {
            this.topLoreAsComponent.add(LegacyComponentSerializer.legacyAmpersand().deserialize(line));
        }
        return this;
    }
    private List<String> bottomLore = new ArrayList<>();
    private List<Component> bottomLoreAsComponent = new ArrayList<>();
    public PrisonPickaxe setBottomLore(List<String> bottomLore) {
        this.bottomLore = bottomLore;
        if (topLore == null) return this;
        this.bottomLoreAsComponent = new ArrayList<>();
        for (String line : bottomLore) {
            this.bottomLoreAsComponent.add(LegacyComponentSerializer.legacyAmpersand().deserialize(line));
        }
        return this;
    }

    @Override
    public void runSpreadOutExecution() {
        if (item == null) return;
        ItemMeta meta = item.getItemMeta();
        meta.lore(buildLore());
        meta.displayName(
                Component.empty().append(nameAsComponent).append(
                        Component.text(" [" + PrisonUtils.addCommasToNumber(rawBlocksBroken) + " Blocks Mined]").color(ComponentUtil.LIGHT_GRAY))
                        .decoration(TextDecoration.ITALIC, false)
        );
        item.setItemMeta(meta);
    }


//    static final int DUMP_INTERVAL = 20; //The amount of ticks that this operation is spread across. It might take DUMP_INTERVAL * 2 ticks before a pickaxe's lore is updated.
//    static ArrayList<PrisonPickaxe>[] pickaxeDumpQueue = new ArrayList[DUMP_INTERVAL]; //The array of lists of pickaxes that need to be updated. Each list in the array represents the pickaxes that should be done in that index's tick.
//    static int currentDumpQueueTick = 0; //Number 1 - 100 representing the current tick.
//
//    public static void dumpLoreToAllPickaxes() { //This method should be called every tick
//        if (currentDumpQueueTick == 0) { //Build the queue for the next 100 ticks
//            pickaxeDumpQueue = new ArrayList[DUMP_INTERVAL];
//            int i = 0; //Represents the index in the pickaxesToUpdateLore list
//            int iteration = 0; //Represents the amount of times the loop has run.
//            while (i < pickaxesToUpdateLore.size()) {
//                int amountToDumpThisTick = (int) Math.ceil(((double) pickaxesToUpdateLore.size() - i) / (DUMP_INTERVAL - iteration));
//                ArrayList<PrisonPickaxe> pickaxes = new ArrayList<>();
//                for (int x = i; x < i + amountToDumpThisTick; x++) {
//                    pickaxes.add(pickaxesToUpdateLore.get(x));
//                }
//                pickaxeDumpQueue[iteration] = pickaxes;
//                i += amountToDumpThisTick;
//                iteration++;
//            }
//        }
//
//        if (pickaxeDumpQueue[currentDumpQueueTick] != null) {
//            for (PrisonPickaxe pickaxe : pickaxeDumpQueue[currentDumpQueueTick]) {
//
//                if (!pickaxesToUpdateLore.contains(pickaxe)) continue; //The lore was updated elsewhere
//
//            }
//            pickaxesToUpdateLore.removeAll(pickaxeDumpQueue[currentDumpQueueTick]);
//        }
//        currentDumpQueueTick = (currentDumpQueueTick + 1) % DUMP_INTERVAL;
//    }

    public static void updateLore(ItemStack item) {
        PrisonPickaxe pickaxe = fromItem(item);
        ItemMeta meta = item.getItemMeta();
        meta.lore(pickaxe.buildLore());
        meta.displayName(
                Component.empty().append(pickaxe.nameAsComponent).append(Component.text(" [" + PrisonUtils.addCommasToNumber(pickaxe.rawBlocksBroken) + " Blocks Mined]").color(ComponentUtil.LIGHT_GRAY)).decoration(TextDecoration.ITALIC, false)
        );
        item.setItemMeta(meta);
        SpreadOutExecutor.remove(pickaxe);
    }

    public PrisonPickaxe tryToUpdateLore() {
        if (item == null) return this;
        updateLore(item);
        return this;
    }

    final Component LORE_DIVIDER = Component.text("---------------").color(ComponentUtil.LIGHT_GRAY);
    public List<Component> buildLore() {
        List<Component> lore = new ArrayList<>();
        if (topLore != null) lore.addAll(topLoreAsComponent);
        lore.addAll(buildStatLore());
        lore.add(LORE_DIVIDER);
        lore.addAll(buildEnchantLore());
        lore.addAll(buildAbilityLore());
        if (bottomLore != null && !bottomLore.isEmpty()) {
            lore.add(LORE_DIVIDER);
            lore.addAll(bottomLoreAsComponent);
        }
        lore.replaceAll(line -> line.decoration(TextDecoration.ITALIC, false));
        return lore;
    }

    private List<Component> buildStatLore() {
        List<Component> lore = new ArrayList<>();
        lore.add(Component.empty().append(Component.text("Level: ").color(ComponentUtil.GREEN)).append(Component.text(PrisonUtils.addCommasToNumber(level)).color(ComponentUtil.WHITE)));
        lore.add(Component.empty().append(Component.text("Experience: ").color(ComponentUtil.GREEN)).append(Component.text(PrisonUtils.prettyNum(xp) + " / " + PrisonUtils.prettyNum(getLevelRequirement(level))).color(ComponentUtil.WHITE)));
        lore.add(Component.empty().append(Component.text("Blocks Mined: ").color(ComponentUtil.GREEN)).append(Component.text(PrisonUtils.addCommasToNumber(rawBlocksBroken)).color(ComponentUtil.WHITE)));
        lore.add(Component.empty().append(Component.text("Blocks Broken: ").color(ComponentUtil.GREEN)).append(Component.text(PrisonUtils.addCommasToNumber(blocksBroken)).color(ComponentUtil.WHITE)));
        return lore;
    }

    private List<Component> buildEnchantLore() {
        List<Component> lore = new ArrayList<>();
        for (BaseEnchant ench : PickaxeEnchants.ORDERED_ENCHANTS) {
            int level = getEnchantLevel(ench);
            if (level > 0) {
                lore.add(Component.empty().append(Component.text(ench.UNFORMATTED_DISPLAY_NAME + ": " + PrisonUtils.addCommasToNumber(level)).color(ComponentUtil.AQUA)));
            }
        }
        return lore;
    }

    private List<Component> buildAbilityLore() {
        List<Component> lore = new ArrayList<>();
        for (BaseAbility ability : PickaxeAbilities.ORDERED_ABILITIES) {
            int level = getAbilityLevel(ability);
            if (level > 0) {
                lore.add(Component.empty().append(Component.text(ability.UNFORMATTED_DISPLAY_NAME + ": " + PrisonUtils.addCommasToNumber(level)).color(ComponentUtil.RED)));
            }
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
