package net.staticstudios.prisons.crates;

import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.customitems.handler.CustomItems;
import net.staticstudios.mines.utils.WeightedElements;
import net.staticstudios.prisons.utils.StaticFileSystemManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;

public class Crates {

    public static void init() {
        Crate.init();

        ConfigurationSection config = StaticFileSystemManager.getYamlConfiguration("crates.yml");

        new Crate("common", "Common Crate", "common", new Location(Bukkit.getWorld("world"), -51, 80, -137), loadCrate(config, "common"));
        new Crate("rare", "Rare Crate", "rare", new Location(Bukkit.getWorld("world"), -42, 80, -137), loadCrate(config, "rare"));
        new Crate("epic", "Epic Crate", "epic", new Location(Bukkit.getWorld("world"), -33, 80, -137), loadCrate(config, "epic"));
        new Crate("legendary", "Legendary Crate", "legendary", new Location(Bukkit.getWorld("world"), -24, 80, -137), loadCrate(config, "legendary"));
        new Crate("static", "Static Crate", "static", new Location(Bukkit.getWorld("world"), -15, 80, -137), loadCrate(config, "static"));
        new Crate("staticp", "Static+ Crate", "staticp", new Location(Bukkit.getWorld("world"), 3, 80, -137), loadCrate(config, "staticp"));
        new Crate("pickaxe", "Pickaxe Crate", "pickaxe", new Location(Bukkit.getWorld("world"), 12, 80, -137), loadCrate(config, "pickaxe"));
        new Crate("vote", "Vote Crate", "vote", new Location(Bukkit.getWorld("world"), -54, 80, -125), loadCrate(config, "vote"));
        new Crate("kit", "Kit Crate", "kit", new Location(Bukkit.getWorld("world"), -54, 80, -134), loadCrate(config, "kit"));

    }

    private static WeightedElements<CrateReward> loadCrate(ConfigurationSection config, String crate) {
        WeightedElements<CrateReward> rewards = new WeightedElements<>();

        ConfigurationSection crateConfig = config.getConfigurationSection(crate);
        if (crateConfig == null) {
            StaticPrisons.log("Crate " + crate + " not found in crates.yml! Giving it no rewards...");
            return new WeightedElements<>();
        }

        crateConfig.getKeys(false).forEach(key -> {
            ConfigurationSection reward = crateConfig.getConfigurationSection(key);
            String itemID = key.replaceAll("\\+", "");
            int amount = reward.getInt("amount", 1);
            String[] displayArgs = reward.getStringList("display_item_args").toArray(new String[0]);
            String[] args = reward.getStringList("args").toArray(new String[0]);
            rewards.add(new CrateReward(itemID, args, CustomItems.getItem(itemID, null, displayArgs)).setRewardItemAmount(amount), reward.getDouble("chance"));
        });
        if (rewards.getTotalWeight() != 100) {
            StaticPrisons.log("Crate " + crate + " has a total weight of " + rewards.getTotalWeight() + "! It should be 100!");
        }
        return rewards;
    }

}
