package net.staticstudios.prisons.pickaxe;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.commands.CommandManager;
import net.staticstudios.prisons.pickaxe.abilities.handler.BaseAbility;
import net.staticstudios.prisons.pickaxe.abilities.handler.PickaxeAbilities;
import net.staticstudios.prisons.pickaxe.commands.AddPickaxeBlocksMinedCommand;
import net.staticstudios.prisons.pickaxe.commands.AddPickaxeXPCommand;
import net.staticstudios.prisons.pickaxe.commands.EnchantCommand;
import net.staticstudios.prisons.pickaxe.commands.PickaxeCommand;
import net.staticstudios.prisons.pickaxe.enchants.handler.BaseEnchant;
import net.staticstudios.prisons.pickaxe.enchants.handler.PickaxeEnchants;
import net.staticstudios.prisons.utils.ComponentUtil;
import net.staticstudios.prisons.utils.PrisonUtils;
import net.staticstudios.prisons.utils.items.SpreadOutExecution;
import net.staticstudios.prisons.utils.items.SpreadOutExecutor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;

public class PrisonPickaxe implements SpreadOutExecution {

    public static final Map<String, PrisonPickaxe> UUID_TO_PICKAXE_MAP = new HashMap<>();
    public static final NamespacedKey PICKAXE_KEY = new NamespacedKey(StaticPrisons.getInstance(), "pickaxeUUID");

    final static Component LORE_DIVIDER = Component.text("---------------").color(ComponentUtil.LIGHT_GRAY);
    private final String uuidAsString;
    private final Map<String, Integer> enchLevelMap = new HashMap<>();
    private final Map<String, Integer> enchTierMap = new HashMap<>();
    private final Map<String, Integer> abilityLevelMap = new HashMap<>();
    private final Map<String, Long> lastUsedAbilityAtMap = new HashMap<>();
    private final Set<String> disabledEnchants = new HashSet<>();
    public ItemStack item = null;
    private long level = 0;
    private long xp = 0;
    private long blocksBroken = 0;
    private long rawBlocksBroken = 0;
    private String name = "&b&lPrison Pickaxe";
    private Component nameAsComponent = LegacyComponentSerializer.legacyAmpersand().deserialize(name);
    private List<String> topLore = new ArrayList<>();
    private List<Component> topLoreAsComponent = new ArrayList<>();
    private List<String> bottomLore = new ArrayList<>();
    private List<Component> bottomLoreAsComponent = new ArrayList<>();

    /**
     * Construct a new PrisonPickaxe from a KNOWN uuid
     *
     * @param uuid A UUID (as a string) that is known to be a PrisonPickaxe. The ItemStack may or may not be known at this time but one does exist with this UUID.
     */
    public PrisonPickaxe(String uuid) {
        uuidAsString = uuid;
        UUID_TO_PICKAXE_MAP.put(uuid, this);
    }

    /**
     * Create a completely new PrisonPickaxe with a new UUID
     *
     * @param item The ItemStack that the PrisonPickaxe will be based on. This will be the pickaxe that the player will use.
     */
    public PrisonPickaxe(ItemStack item) {
        String uuid = UUID.randomUUID().toString();
        uuidAsString = uuid;
        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(PICKAXE_KEY, PersistentDataType.STRING, uuid);
        item.setItemMeta(meta);
        UUID_TO_PICKAXE_MAP.put(uuid, this);
    }

    public static void init() {
        CommandManager.registerCommand("addpickaxexp", new AddPickaxeXPCommand());
        CommandManager.registerCommand("addpickaxeblocksmined", new AddPickaxeBlocksMinedCommand());
        CommandManager.registerCommand("getnewpickaxe", new PickaxeCommand());
        CommandManager.registerCommand("enchant", new EnchantCommand());


        loadPickaxeData();
        StaticPrisons.getInstance().getServer().getPluginManager().registerEvents(new PickaxeListener(), StaticPrisons.getInstance());
    }

    /**
     * Load all pickaxe data from disk
     */
    public static void loadPickaxeData() {
        UUID_TO_PICKAXE_MAP.clear();
        File pickaxeData = new File(StaticPrisons.getInstance().getDataFolder(), "data/pickaxe_data.yml");
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

                pickaxe.enchLevelMap.put(enchantKey, lvl);
                pickaxe.enchTierMap.put(enchantKey, tier);
            }

            //Abilities
            for (String abilitiesKey : abilitiesSection.getKeys(false)) {
                ConfigurationSection attributeSection = abilitiesSection.getConfigurationSection(abilitiesKey);
                if (attributeSection == null) continue;
                int lvl = attributeSection.getInt("lvl");
                long lastUsed = attributeSection.getLong("lastUsed");

                pickaxe.abilityLevelMap.put(abilitiesKey, lvl);
                pickaxe.lastUsedAbilityAtMap.put(abilitiesKey, lastUsed);
            }

            //Don't update the lore
            SpreadOutExecutor.remove(pickaxe);
        }
    }

    /**
     * Save all pickaxe data to disk asynchronously
     */
    public static void savePickaxeData() {
        Map<String, PrisonPickaxe> temp = new HashMap<>(UUID_TO_PICKAXE_MAP);
        Bukkit.getScheduler().runTaskAsynchronously(StaticPrisons.getInstance(), () -> {
            savePickaxeData(temp);
        });
    }

    /**
     * Save all pickaxe data to disk synchronously
     */
    public static void savePickaxeDataNow() {
        savePickaxeData(UUID_TO_PICKAXE_MAP);
    }

    /**
     * Save all pickaxe data to disk synchronously
     *
     * @param pickaxeUUIDToPrisonPickaxe The map of data to save
     */
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
            for (String enchantKey : pickaxe.enchLevelMap.keySet()) {
                ConfigurationSection enchantSection = enchantsSection.createSection(enchantKey);
                enchantSection.set("lvl", pickaxe.enchLevelMap.get(enchantKey));
                enchantSection.set("tier", pickaxe.enchTierMap.get(enchantKey));
                enchantSection.set("enabled", !pickaxe.disabledEnchants.contains(enchantKey));
            }

            //Abilities
            for (String abilityKey : pickaxe.abilityLevelMap.keySet()) {
                ConfigurationSection abilitySection = abilitiesSection.createSection(abilityKey);
                abilitySection.set("lvl", pickaxe.abilityLevelMap.get(abilityKey));
                abilitySection.set("lastUsed", pickaxe.lastUsedAbilityAtMap.get(abilityKey));
            }

        }
        try {
            allData.save(new File(dataFolder, "pickaxe_data.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Bukkit.getServer().getLogger().log(Level.INFO, "Saved all pickaxe data data/pickaxe_data.yml");
    }

    /**
     * Get a PrisonPickaxe from an ItemStack. The ItemStack needs to already have been used to create a PrisonPickaxe at some point.
     *
     * @param item The ItemStack to get the PrisonPickaxe from
     * @return The PrisonPickaxe, or null if it doesn't exist
     */
    public static PrisonPickaxe fromItem(ItemStack item) {
        if (item == null) return null;
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return null;
        PrisonPickaxe pickaxe = PrisonPickaxe.fromUUID(meta.getPersistentDataContainer().get(PICKAXE_KEY, PersistentDataType.STRING));
        if (pickaxe != null) pickaxe.item = item;
        return pickaxe;
    }

    /**
     * Get a PrisonPickaxe from a UUID. The UUID needs to already have been used to create a PrisonPickaxe at some point.
     *
     * @param pickaxeUUID The UUID to get the PrisonPickaxe from
     * @return The PrisonPickaxe, or null if it doesn't exist
     */
    public static PrisonPickaxe fromUUID(String pickaxeUUID) {
        return UUID_TO_PICKAXE_MAP.get(pickaxeUUID);
    }

    /**
     * Simple check to see if the ItemStack has been used to create a PrisonPickaxe at some point.
     *
     * @param item The ItemStack to check
     * @return True if it has been used to create a PrisonPickaxe at some point, false otherwise
     */
    public static boolean checkIsPrisonPickaxe(ItemStack item) {
        if (item == null) return false;
        if (!item.hasItemMeta()) return false;
        return item.getItemMeta().getPersistentDataContainer().has(PICKAXE_KEY, PersistentDataType.STRING);
    }

    /**
     * Create a new PrisonPickaxe & ItemStack.
     *
     * @return A diamond pickaxe with an associated PrisonPickaxe
     */
    public static ItemStack createNewPickaxe() {
        ItemStack item = new ItemStack(Material.DIAMOND_PICKAXE);
        PrisonPickaxe pickaxe = new PrisonPickaxe(item);
        ItemMeta meta = item.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addEnchant(Enchantment.DIG_SPEED, 100, true);
        item.setItemMeta(meta);
        pickaxe.setEnchantsLevel(PickaxeEnchants.FORTUNE, 5);
        pickaxe.setEnchantsLevel(PickaxeEnchants.DOUBLE_FORTUNE, 5);
        pickaxe.setEnchantsLevel(PickaxeEnchants.TOKENATOR, 1);
        pickaxe.setEnchantsLevel(PickaxeEnchants.JACK_HAMMER, 1);
        updateLore(item);
        return item;
    }

    //BASE = 2500
    //ROI = 2.4
    //BASE * lvl + lvl * (ROI * lvl)^ROI
    public static long getLevelRequirement(long level) {
        if (level <= 0) return 2500;
        return (long) (2500 * level + level * Math.pow(2.4 * level, 2.4));
    }

    public static void updateLore(ItemStack item) {
        PrisonPickaxe pickaxe = fromItem(item);
        ItemMeta meta = item.getItemMeta();
        meta.lore(pickaxe.buildLore());
        meta.displayName(Component.empty().append(pickaxe.nameAsComponent)
                .append(Component.text(" [" + PrisonUtils.addCommasToNumber(pickaxe.rawBlocksBroken) + " Blocks Mined]").color(ComponentUtil.LIGHT_GRAY))
                .decoration(TextDecoration.ITALIC, false)
        );
        item.setItemMeta(meta);
        SpreadOutExecutor.remove(pickaxe);
    }

    public List<BaseEnchant> getEnchants() {
        List<BaseEnchant> enchants = new ArrayList<>();
        for (String enchantID : enchLevelMap.keySet()) {
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
        if (enchLevelMap.containsKey(enchantID)) {
            return enchLevelMap.get(enchantID);
        }
        return 0;
    }

    public int getEnchantTier(BaseEnchant enchant) {
        return getEnchantTier(enchant.ENCHANT_ID);
    }

    public int getEnchantTier(String enchantID) {
        if (enchTierMap.containsKey(enchantID)) {
            return enchTierMap.get(enchantID);
        }
        return 0;
    }

    public void setEnchantTier(BaseEnchant enchant, int tier) {
        setEnchantTier(enchant.ENCHANT_ID, tier);
    }

    public void setEnchantTier(String enchantID, int tier) {
        enchTierMap.put(enchantID, tier);
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
        queueExecution();
        enchLevelMap.put(enchant.ENCHANT_ID, level);
    }

    public void setEnchantsLevel(String enchant, int level) {
        queueExecution();
        enchLevelMap.put(enchant, level);
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
        for (String abilityID : abilityLevelMap.keySet()) {
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
        if (abilityLevelMap.containsKey(abilityID)) {
            return abilityLevelMap.get(abilityID);
        }
        return 0;
    }

    public void setAbilityLevel(BaseAbility ability, int level) {
        queueExecution();
        abilityLevelMap.put(ability.ABILITY_ID, level);
    }

    public void setAbilityLevel(String abilityID, int level) {
        queueExecution();
        abilityLevelMap.put(abilityID, level);
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
        return lastUsedAbilityAtMap.getOrDefault(abilityID, 0L);
    }

    public void setLastActivatedAbilityAt(BaseAbility ability, long time) {
        setLastActivatedAbilityAt(ability.ABILITY_ID, time);
    }

    public void setLastActivatedAbilityAt(String abilityID, long time) {
        lastUsedAbilityAtMap.put(abilityID, time);
    }

    public long getLevel() {
        return level;
    }

    public void setLevel(long level) {
        if (this.level != level) {
            queueExecution();
        }
        this.level = level;
    }

    public long getXp() {
        return xp;
    }

    public void setXp(long xp) {
        if (this.xp != xp) {
            queueExecution();
        }
        this.xp = xp;
    }

    public long getBlocksBroken() {
        return blocksBroken;
    }

    public void setBlocksBroken(long blocksBroken) {
        if (this.blocksBroken != blocksBroken) {
            queueExecution();
        }
        this.blocksBroken = blocksBroken;
    }

    public long getRawBlocksBroken() {
        return rawBlocksBroken;
    }

    public void setRawBlocksBroken(long rawBlocksBroken) {
        if (this.rawBlocksBroken != rawBlocksBroken) {
            queueExecution();
        }
        this.rawBlocksBroken = rawBlocksBroken;
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

    public Component getName() {
        return nameAsComponent;
    }

    public void setName(String name) {
        this.name = name;
        nameAsComponent = LegacyComponentSerializer.legacyAmpersand().deserialize(name);
    }

    public Component getNameAsMessagePrefix() {
        return Component.empty().append(nameAsComponent).append(Component.text(" >> ").color(ComponentUtil.DARK_GRAY).decorate(TextDecoration.BOLD));
    }

    public PrisonPickaxe setTopLore(List<String> topLore) {
        this.topLore = topLore;
        if (topLore == null) return this;
        this.topLoreAsComponent = new ArrayList<>();
        for (String line : topLore) {
            this.topLoreAsComponent.add(LegacyComponentSerializer.legacyAmpersand().deserialize(line));
        }
        return this;
    }

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
    public void execute() {
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

    public PrisonPickaxe tryToUpdateLore() {
        if (item == null) return this;
        updateLore(item);
        return this;
    }

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
     * <p>
     * Once a pickaxe is removed, it can no longer be used by a player
     */
    public PrisonPickaxe delete() {
        UUID_TO_PICKAXE_MAP.remove(uuidAsString);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrisonPickaxe that = (PrisonPickaxe) o;
        return Objects.equals(uuidAsString, that.uuidAsString);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuidAsString);
    }
}
