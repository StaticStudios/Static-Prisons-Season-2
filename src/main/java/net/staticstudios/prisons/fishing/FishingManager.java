package net.staticstudios.prisons.fishing;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.commands.CommandManager;
import net.staticstudios.prisons.enchants.EnchantableItemStack;
import net.staticstudios.prisons.fishing.enchants.handler.FishingEnchants;
import net.staticstudios.prisons.pickaxe.PickaxeListener;
import net.staticstudios.prisons.pickaxe.commands.AddPickaxeBlocksMinedCommand;
import net.staticstudios.prisons.pickaxe.commands.AddPickaxeXPCommand;
import net.staticstudios.prisons.pickaxe.commands.EnchantCommand;
import net.staticstudios.prisons.pickaxe.commands.PickaxeCommand;
import net.staticstudios.prisons.pickaxe.enchants.handler.PickaxeEnchants;
import net.staticstudios.prisons.utils.ComponentUtil;
import net.staticstudios.prisons.utils.StaticFileSystemManager;
import net.staticstudios.prisons.utils.items.SpreadOutExecutor;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class FishingManager {
    public static final Component DEFAULT_NAME = Component.text("Fishing Rod")
            .color(ComponentUtil.YELLOW)
            .decoration(TextDecoration.ITALIC, false)
            .decoration(TextDecoration.BOLD, true);

    public static void init() {
        Bukkit.getPluginManager().registerEvents(new FishingListener(), StaticPrisons.getInstance());
        Bukkit.getScheduler().runTaskTimer(StaticPrisons.getInstance(), () -> saveData(), 20 * (60 * 5 + 22), 20 * 60 * 5);

        FishingEnchants.init();

        FishingRewardOutline.load();

        loadData();
    }

    /**
     * Load all data from disk
     */
    public static void loadData() {
        EnchantableItemStack.getMap(PrisonFishingRod.class).clear();
        ConfigurationSection data = YamlConfiguration.loadConfiguration(StaticFileSystemManager.getFileOrCreate("data/fishingrod_data.yml"));

        data.getKeys(false).forEach(key -> {
            ConfigurationSection fishingRodData = Objects.requireNonNull(data.getConfigurationSection(key));
            ConfigurationSection stats = fishingRodData.getConfigurationSection("stats");
            ConfigurationSection enchants = fishingRodData.getConfigurationSection("enchants");

            PrisonFishingRod rod = new PrisonFishingRod(UUID.fromString(key));

            if (stats != null) {
                rod.setXp(stats.getLong("xp"));
                rod.setLevel(stats.getInt("level"));
                rod.setName(ComponentUtil.fromString(stats.getString("name", "<bold><yellow>Fishing Rod")));
                rod.setItemsCaught(stats.getInt("itemsCaught", 0));
                rod.setCaughtNothing(stats.getInt("caughtNothing", 0));
                rod.setDurability(stats.getDouble("durability", 0));
            }

            SpreadOutExecutor.remove(rod);


        });

//        FileConfiguration ymlData = YamlConfiguration.loadConfiguration(pickaxeData);


//        for (String key : ymlData.getKeys(false)) {
//            ConfigurationSection pickaxeSection = ymlData.getConfigurationSection(key);
//            ConfigurationSection statsSection = pickaxeSection.getConfigurationSection("stats");
//            ConfigurationSection enchantsSection = pickaxeSection.getConfigurationSection("enchants");
//            ConfigurationSection abilitiesSection = pickaxeSection.getConfigurationSection("attributes");
//
//
//            PrisonFishingRod fishingRod = new PrisonFishingRod(UUID.fromString(key));
//
//            //Stats
//            fishingRod.setXp(statsSection.getLong("xp"));
//            fishingRod.setLevel(statsSection.getInt("level"));
//            fishingRod.setBlocksBroken(statsSection.getLong("blocksBroken"));
//            fishingRod.setRawBlocksBroken(statsSection.getLong("rawBlocksBroken"));
//            fishingRod.setTopLore(statsSection.getStringList("topLore").stream().map(StaticPrisons.miniMessage()::deserialize).toList());
//            fishingRod.setBottomLore(statsSection.getStringList("bottomLore").stream().map(StaticPrisons.miniMessage()::deserialize).toList());
//
//            String name = statsSection.getString("name");
//            fishingRod.setName(name != null ? StaticPrisons.miniMessage().deserialize(name) : DEFAULT_PICKAXE_NAME);
//
//
//            fishingRod.deserialize(enchantsSection, (enchant, config) -> fishingRod.setEnchantmentTier(enchant.getClass(), config.getInt("tier", 0)));
//
//            //Don't update the lore
//            SpreadOutExecutor.remove(fishingRod);
//        }
    }

    /**
     * Save all pickaxe data to disk asynchronously
     */
    public static void saveData() {
        Map<UUID, PrisonFishingRod> temp = new HashMap<>(EnchantableItemStack.getMap(PrisonFishingRod.class));
        Bukkit.getScheduler().runTaskAsynchronously(StaticPrisons.getInstance(), () -> saveData(temp));
    }

    /**
     * Save all pickaxe data to disk synchronously
     */
    public static void saveDataNow() {
        saveData(EnchantableItemStack.getMap(PrisonFishingRod.class));
    }

    /**
     * Save all pickaxe data to disk synchronously
     *
     * @param dataMap The map of data to save
     */
    private static void saveData(Map<UUID, PrisonFishingRod> dataMap) {
        YamlConfiguration data = new YamlConfiguration();

        dataMap.forEach((uuid, rod) -> {
            ConfigurationSection rodSection = data.createSection(uuid.toString());

            ConfigurationSection stats = rodSection.createSection("stats");
            stats.set("xp", rod.getXp());
            stats.set("level", rod.getLevel());
            stats.set("name", ComponentUtil.toString(rod.getName()));
            stats.set("itemsCaught", rod.getItemsCaught());
            stats.set("caughtNothing", rod.getCaughtNothing());
            stats.set("durability", rod.getDurability());

        });

        try {
            data.save(StaticFileSystemManager.getFileOrCreate("data/fishingrod_data.yml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


//        FileConfiguration allData = new YamlConfiguration();
//
//        for (UUID uuid : dataMap.keySet()) {
//            ConfigurationSection pickaxeSection = allData.createSection(uuid.toString());
//            ConfigurationSection statsSection = pickaxeSection.createSection("stats");
//            ConfigurationSection abilitiesSection = pickaxeSection.createSection("attributes");
//
//
//            PrisonPickaxe pickaxe = dataMap.get(uuid);
//
//            //Stats
//            statsSection.set("xp", pickaxe.getXp());
//            statsSection.set("level", pickaxe.getLevel());
//            statsSection.set("blocksBroken", pickaxe.getBlocksBroken());
//            statsSection.set("rawBlocksBroken", pickaxe.getRawBlocksBroken());
//            statsSection.set("topLore", pickaxe.getTopLore().stream().map(StaticPrisons.miniMessage()::serialize).toList());
//            statsSection.set("bottomLore", pickaxe.getBottomLore().stream().map(StaticPrisons.miniMessage()::serialize).toList());
//            statsSection.set("name", StaticPrisons.miniMessage().serialize(pickaxe.getName()));
//
//            pickaxeSection.set("enchants", pickaxe.serialize((enchantHolder, config) -> config.set("tier", pickaxe.getEnchantmentTier(enchantHolder.enchantment()))));
//
//        }
//        try {
//            allData.save(new File(StaticPrisons.getInstance().getDataFolder(), "data/fishingrod_data.yml"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
