package net.staticstudios.prisons.pickaxe;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.commands.CommandManager;
import net.staticstudios.prisons.enchants.EnchantableItemStack;
import net.staticstudios.prisons.pickaxe.commands.AddPickaxeBlocksMinedCommand;
import net.staticstudios.prisons.pickaxe.commands.AddPickaxeXPCommand;
import net.staticstudios.prisons.pickaxe.commands.EnchantCommand;
import net.staticstudios.prisons.pickaxe.commands.PickaxeCommand;
import net.staticstudios.prisons.pickaxe.enchants.handler.PickaxeEnchants;
import net.staticstudios.prisons.utils.ComponentUtil;
import net.staticstudios.prisons.utils.items.SpreadOutExecutor;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
            pickaxe.setTopLore(statsSection.getStringList("topLore").stream().map(StaticPrisons.miniMessage()::deserialize).toList());
            pickaxe.setBottomLore(statsSection.getStringList("bottomLore").stream().map(StaticPrisons.miniMessage()::deserialize).toList());

            String name = statsSection.getString("name");
            pickaxe.setName(name != null ? StaticPrisons.miniMessage().deserialize(name) : DEFAULT_PICKAXE_NAME);


            pickaxe.deserialize(enchantsSection, (enchant, config) -> pickaxe.setEnchantmentTier(enchant.getClass(), config.getInt("tier", 0)));

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
            statsSection.set("topLore", pickaxe.getTopLore().stream().map(ComponentUtil::toString).toList());
            statsSection.set("bottomLore", pickaxe.getBottomLore().stream().map(ComponentUtil::toString).toList());
            statsSection.set("name", ComponentUtil.toString(pickaxe.getName()));

            pickaxeSection.set("enchants", pickaxe.serialize((enchantHolder, config) -> config.set("tier", pickaxe.getEnchantmentTier(enchantHolder.enchantment()))));

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
    }

    public static void init() {
        CommandManager.registerCommand("addpickaxexp", new AddPickaxeXPCommand());
        CommandManager.registerCommand("addpickaxeblocksmined", new AddPickaxeBlocksMinedCommand());
        CommandManager.registerCommand("getnewpickaxe", new PickaxeCommand());
        CommandManager.registerCommand("enchant", new EnchantCommand());

        PickaxeEnchants.init();

        loadPickaxeData();
        StaticPrisons.getInstance().getServer().getPluginManager().registerEvents(new PickaxeListener(), StaticPrisons.getInstance());
        Bukkit.getScheduler().runTaskTimer(StaticPrisons.getInstance(), () -> savePickaxeData(), 20 * (60 * 5 + 14), 20 * 60 * 5);
    }
}