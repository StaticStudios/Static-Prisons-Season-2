package net.staticstudios.prisons.enchants;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.staticstudios.utils.StaticFileSystemManager;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Event;

import java.util.ArrayList;
import java.util.List;

public abstract class ConfigurableEnchantment<E extends Event> implements Enchantment<E> {
    private static ConfigurationSection configuration;
    private static List<Enchantment> orderedEnchants = new ArrayList<>();

    private final String id;
    private final String name;
    private final Component unformattedName;
    private final Component displayName;
    private final List<Component> description;
    private final int maxLevel;
    private final long upgradeCost;
    private final double defaultChance;
    private final double chanceAtMaxLevel;

    private final ConfigurationSection config;


    public ConfigurableEnchantment(String id) {
        if (configuration == null) {
            configuration = YamlConfiguration.loadConfiguration(StaticFileSystemManager.getFileOrCreate("enchants.yml"));
        }

        ConfigurationSection config = ConfigurableEnchantment.configuration.getConfigurationSection(id);
        if (config == null) {
            throw new IllegalArgumentException("No config found for configurable enchantment: " + id);
        }

        this.config = config;

        try {

            this.id = id;
            this.name = config.getString("name");
            this.unformattedName = MiniMessage.miniMessage().deserialize(config.getString("unformatted_name", name));
            this.displayName = MiniMessage.miniMessage().deserialize(config.getString("display_name", name));
            List<Component> description = new ArrayList<>();
            for (String line : config.getStringList("description")) {
                description.add(MiniMessage.miniMessage().deserialize(line));
            }
            this.description = description;
            this.maxLevel = config.getInt("max_level");
            this.upgradeCost = config.getLong("upgrade_cost");
            this.defaultChance = config.getDouble("default_chance");
            this.chanceAtMaxLevel = config.getDouble("chance_at_max_level");

            orderedEnchants.add(this);
        } catch (NullPointerException exception) {
            throw new IllegalArgumentException("Invalid config for configurable enchantment: " + id, exception);
        }
    }

    public static List<Enchantment> getEnchantsInOrder() {
        return orderedEnchants;
    }

    /**
     * Get the ConfigurationSection for this enchantment.
     * @return The ConfigurationSection for this enchantment.
     */
    public ConfigurationSection getConfig() {
        return config;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Component getNameAsComponent() {
        return unformattedName;
    }

    @Override
    public Component getDisplayName() {
        return displayName;
    }

    @Override
    public List<Component> getDescription() {
        return description;
    }

    @Override
    public int getMaxLevel() {
        return maxLevel;
    }

    @Override
    public long getUpgradeCost() {
        return upgradeCost;
    }

    @Override
    public double getDefaultChanceToActivate() {
        return defaultChance;
    }

    @Override
    public double getChanceToActivateAtMaxLevel() {
        return chanceAtMaxLevel;
    }
}
