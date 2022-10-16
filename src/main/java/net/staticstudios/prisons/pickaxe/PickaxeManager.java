package net.staticstudios.prisons.pickaxe;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.commands.CommandManager;
import net.staticstudios.prisons.enchants.EnchantableItemStack;
import net.staticstudios.prisons.pickaxe.commands.AddPickaxeBlocksMinedCommand;
import net.staticstudios.prisons.pickaxe.commands.AddPickaxeXPCommand;
import net.staticstudios.prisons.pickaxe.commands.EnchantCommand;
import net.staticstudios.prisons.pickaxe.commands.PickaxeCommand;
import net.staticstudios.prisons.utils.ComponentUtil;
import net.staticstudios.prisons.utils.items.SpreadOutExecutor;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;

public class PickaxeManager {
    public static final Component DEFAULT_PICKAXE_NAME = Component.empty().append(Component.text("Prison Pickaxe"))
            .color(ComponentUtil.AQUA)
            .decorate(TextDecoration.BOLD)
            .decoration(TextDecoration.ITALIC, false);
    /**
     * Load all pickaxe data from disk
     */
    public static void loadPickaxeData() {
        EnchantableItemStack.getMap(PrisonPickaxe.class).clear();
        File pickaxeData = new File(StaticPrisons.getInstance().getDataFolder(), "data/pickaxe_data.yml");
        FileConfiguration ymlData = YamlConfiguration.loadConfiguration(pickaxeData);


        for (String key : ymlData.getKeys(false)) {
            ConfigurationSection pickaxeSection = ymlData.getConfigurationSection(key);
            ConfigurationSection statsSection = pickaxeSection.getConfigurationSection("stats");
            ConfigurationSection enchantsSection = pickaxeSection.getConfigurationSection("enchants");
            ConfigurationSection abilitiesSection = pickaxeSection.getConfigurationSection("attributes");


            PrisonPickaxe pickaxe = new PrisonPickaxe(UUID.fromString(key));

            //Stats
            pickaxe.setXp(statsSection.getLong("xp"));
            pickaxe.setLevel(statsSection.getInt("level"));
            pickaxe.setBlocksBroken(statsSection.getLong("blocksBroken"));
            pickaxe.setRawBlocksBroken(statsSection.getLong("rawBlocksBroken"));
            pickaxe.setTopLore(statsSection.getStringList("topLore").stream().map(MiniMessage.miniMessage()::deserialize).toList());
            pickaxe.setBottomLore(statsSection.getStringList("bottomLore").stream().map(MiniMessage.miniMessage()::deserialize).toList());

            String name = statsSection.getString("name");
            pickaxe.setName(name != null ? MiniMessage.miniMessage().deserialize(name) : DEFAULT_PICKAXE_NAME);


            pickaxe.deserialize(enchantsSection);
            //todo: tiers

            //Abilities
//            for (String abilitiesKey : abilitiesSection.getKeys(false)) {
//                ConfigurationSection attributeSection = abilitiesSection.getConfigurationSection(abilitiesKey);
//                if (attributeSection == null) continue;
//                int lvl = attributeSection.getInt("lvl");
//                long lastUsed = attributeSection.getLong("lastUsed");
//
//                pickaxe.abilityLevelMap.put(abilitiesKey, lvl);
//                pickaxe.lastUsedAbilityAtMap.put(abilitiesKey, lastUsed);
//            }

            //Don't update the lore
            SpreadOutExecutor.remove(pickaxe);
        }
    }

    /**
     * Save all pickaxe data to disk asynchronously
     */
    public static void savePickaxeData() {
        Map<UUID, PrisonPickaxe> temp = new HashMap<>(EnchantableItemStack.getMap(PrisonPickaxe.class));
        Bukkit.getScheduler().runTaskAsynchronously(StaticPrisons.getInstance(), () -> {
            savePickaxeData(temp);
        });
    }

    /**
     * Save all pickaxe data to disk synchronously
     */
    public static void savePickaxeDataNow() {
        savePickaxeData(EnchantableItemStack.getMap(PrisonPickaxe.class));
    }

    /**
     * Save all pickaxe data to disk synchronously
     *
     * @param pickaxeUUIDToPrisonPickaxe The map of data to save
     */
    private static void savePickaxeData(Map<UUID, PrisonPickaxe> pickaxeUUIDToPrisonPickaxe) {
        File dataFolder = new File(StaticPrisons.getInstance().getDataFolder(), "/data");
        FileConfiguration allData = new YamlConfiguration();

        for (UUID uuid : pickaxeUUIDToPrisonPickaxe.keySet()) {
            ConfigurationSection pickaxeSection = allData.createSection(uuid.toString());
            ConfigurationSection statsSection = pickaxeSection.createSection("stats");
            ConfigurationSection abilitiesSection = pickaxeSection.createSection("attributes");


            PrisonPickaxe pickaxe = pickaxeUUIDToPrisonPickaxe.get(uuid);

            //Stats
            statsSection.set("xp", pickaxe.getXp());
            statsSection.set("level", pickaxe.getLevel());
            statsSection.set("blocksBroken", pickaxe.getBlocksBroken());
            statsSection.set("rawBlocksBroken", pickaxe.getRawBlocksBroken());
            statsSection.set("topLore", pickaxe.getTopLore().stream().map(MiniMessage.miniMessage()::serialize).toList());
            statsSection.set("bottomLore", pickaxe.getBottomLore().stream().map(MiniMessage.miniMessage()::serialize).toList());
            statsSection.set("name", MiniMessage.miniMessage().serialize(pickaxe.getName()));

            //todo: tiers
            pickaxeSection.set("enchants", pickaxe.serialize());

            //Abilities
//            for (String abilityKey : pickaxe.abilityLevelMap.keySet()) {
//                ConfigurationSection abilitySection = abilitiesSection.createSection(abilityKey);
//                abilitySection.set("lvl", pickaxe.abilityLevelMap.get(abilityKey));
//                abilitySection.set("lastUsed", pickaxe.lastUsedAbilityAtMap.get(abilityKey));
//            }

        }
        try {
            allData.save(new File(dataFolder, "pickaxe_data.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Bukkit.getServer().getLogger().log(Level.INFO, "Saved all pickaxe data data/pickaxe_data.yml");
    }

    public static void init() {
        CommandManager.registerCommand("addpickaxexp", new AddPickaxeXPCommand());
        CommandManager.registerCommand("addpickaxeblocksmined", new AddPickaxeBlocksMinedCommand());
        CommandManager.registerCommand("getnewpickaxe", new PickaxeCommand());
        CommandManager.registerCommand("enchant", new EnchantCommand());

        net.staticstudios.prisons.pickaxe.enchants.handler.PickaxeEnchants.init();

        loadPickaxeData();
        StaticPrisons.getInstance().getServer().getPluginManager().registerEvents(new PickaxeListener(), StaticPrisons.getInstance());
    }
}

//    public static void updateLore(ItemStack item) {
//        PrisonPickaxe pickaxe = fromItem(item);
//        ItemMeta meta = item.getItemMeta();
//        meta.lore(pickaxe.buildLore());
//        meta.displayName(Component.empty().append(pickaxe.displayName)
//                .append(Component.text(" [" + PrisonUtils.addCommasToNumber(pickaxe.rawBlocksBroken) + " Blocks Mined]").color(ComponentUtil.LIGHT_GRAY))
//                .decoration(TextDecoration.ITALIC, false)
//        );
//        item.setItemMeta(meta);
//        SpreadOutExecutor.remove(pickaxe);
//    }

//    public List<BaseEnchant> getEnchants() {
//        List<BaseEnchant> enchants = new ArrayList<>();
//        for (String enchantID : enchLevelMap.keySet()) {
//            if (getEnchantLevel(enchantID) > 0) {
//                enchants.add(PickaxeEnchants.enchantIDToEnchant.get(enchantID));
//            }
//        }
//        return enchants;
//    }
//
//    public int getEnchantLevel(BaseEnchant enchant) {
//        return getEnchantLevel(enchant.ENCHANT_ID);
//    }
//
//    public int getEnchantLevel(String enchantID) {
//        if (enchLevelMap.containsKey(enchantID)) {
//            return enchLevelMap.get(enchantID);
//        }
//        return 0;
//    }
//
//    public int getEnchantTier(BaseEnchant enchant) {
//        return getEnchantTier(enchant.ENCHANT_ID);
//    }
//
//    public int getEnchantTier(String enchantID) {
//        if (enchTierMap.containsKey(enchantID)) {
//            return enchTierMap.get(enchantID);
//        }
//        return 0;
//    }
//
//    public void setEnchantTier(BaseEnchant enchant, int tier) {
//        setEnchantTier(enchant.ENCHANT_ID, tier);
//    }
//
//    public void setEnchantTier(String enchantID, int tier) {
//        enchTierMap.put(enchantID, tier);
//    }
//
//    public void setIsEnchantEnabled(Player player, BaseEnchant enchant, boolean enabled) {
//        setIsEnchantEnabled(player, enchant.ENCHANT_ID, enabled);
//    }
//
//    public void setIsEnchantEnabled(Player player, String enchantID, boolean enabled) {
//        if (enabled) {
//            disabledEnchants.remove(enchantID);
//        } else disabledEnchants.add(enchantID);
//
//        //activate or deactivate enchants that have been enabled or disabled if the player is holding the pickaxe
//
//        if (player.getInventory().getItemInMainHand().equals(item)) {
//            if (enabled) {
//                //The player is holding the pickaxe and the held method should be called
//                for (BaseEnchant enchant : getEnchants()) {
//                    if (enchant.ENCHANT_ID.equals(enchantID)) {
//                        enchant.onPickaxeHeld(player, this);
//                        break;
//                    }
//                }
//            } else {
//                //The player is holding the pickaxe and the unheld method should be called
//                for (BaseEnchant enchant : getEnchants()) {
//                    if (enchant.ENCHANT_ID.equals(enchantID)) {
//                        enchant.onPickaxeUnHeld(player, this);
//                        break;
//                    }
//                }
//            }
//
//        }
//    }
//
//    public boolean getIsEnchantEnabled(BaseEnchant enchant) {
//        return getIsEnchantEnabled(enchant.ENCHANT_ID);
//    }
//
//    public boolean getIsEnchantEnabled(String enchantID) {
//        return !disabledEnchants.contains(enchantID);
//    }
//
//    public void setEnchantsLevel(BaseEnchant enchant, int level) {
//        queueExecution();
//        enchLevelMap.put(enchant.ENCHANT_ID, level);
//    }
//
//    public void setEnchantsLevel(String enchant, int level) {
//        queueExecution();
//        enchLevelMap.put(enchant, level);
//    }
//
//    public void addEnchantLevel(BaseEnchant enchant, int level) {
//        setEnchantsLevel(enchant, getEnchantLevel(enchant) + level);
//    }
//
//    public void addEnchantLevel(String enchant, int level) {
//        setEnchantsLevel(enchant, getEnchantLevel(enchant) + level);
//    }
//
//    public void removeEnchantLevel(BaseEnchant enchant, int level) {
//        setEnchantsLevel(enchant, Math.max(0, getEnchantLevel(enchant) - level));
//    }

//    public List<BaseAbility> getAbilities() {
//        List<BaseAbility> abilities = new ArrayList<>();
//        for (String abilityID : abilityLevelMap.keySet()) {
//            if (getAbilityLevel(abilityID) > 0) {
//                abilities.add(PickaxeAbilities.abilityIDToAbility.get(abilityID));
//            }
//        }
//        return abilities;
//    }
//
//    public int getAbilityLevel(BaseAbility ability) {
//        return getAbilityLevel(ability.ABILITY_ID);
//    }
//
//    public int getAbilityLevel(String abilityID) {
//        if (abilityLevelMap.containsKey(abilityID)) {
//            return abilityLevelMap.get(abilityID);
//        }
//        return 0;
//    }
//
//    public void setAbilityLevel(BaseAbility ability, int level) {
//        queueExecution();
//        abilityLevelMap.put(ability.ABILITY_ID, level);
//    }
//
//    public void setAbilityLevel(String abilityID, int level) {
//        queueExecution();
//        abilityLevelMap.put(abilityID, level);
//    }
//
//    public void addAbilityLevel(BaseAbility ability, int level) {
//        setAbilityLevel(ability, getAbilityLevel(ability) + level);
//    }
//
//    public void addAbilityLevel(String abilityID, int level) {
//        setAbilityLevel(abilityID, getAbilityLevel(abilityID) + level);
//    }
//
//    public void removeAbilityLevel(BaseAbility ability, int level) {
//        setAbilityLevel(ability, Math.max(0, getAbilityLevel(ability) - level));
//    }
//
//    public long getLastActivatedAbilityAt(BaseAbility ability) {
//        return getLastActivatedAbilityAt(ability.ABILITY_ID);
//    }
//
//    public long getLastActivatedAbilityAt(String abilityID) {
//        return lastUsedAbilityAtMap.getOrDefault(abilityID, 0L);
//    }
//
//    public void setLastActivatedAbilityAt(BaseAbility ability, long time) {
//        setLastActivatedAbilityAt(ability.ABILITY_ID, time);
//    }
//
//    public void setLastActivatedAbilityAt(String abilityID, long time) {
//        lastUsedAbilityAtMap.put(abilityID, time);
//    }

//    private List<Component> buildAbilityLore() {
//        List<Component> lore = new ArrayList<>();
//        for (BaseAbility ability : PickaxeAbilities.ORDERED_ABILITIES) {
//            int level = getAbilityLevel(ability);
//            if (level > 0) {
//                lore.add(Component.empty().append(Component.text(ability.UNFORMATTED_DISPLAY_NAME + ": " + PrisonUtils.addCommasToNumber(level)).color(ComponentUtil.RED)));
//            }
//        }
//        return lore;
//    }