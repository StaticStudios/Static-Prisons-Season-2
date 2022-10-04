package net.staticstudios.prisons.enchants;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.HashMap;
import java.util.Map;

public interface Enchantable {


    /**
     * Get the instance of the enchantment from its class.
     * @param enchantment The class of the enchantment.
     * @return The instance of the enchantment.
     */
    static Enchantment<?> getEnchant(Class<? extends Enchantment> enchantment) {
        return Enchantment.ALL_ENCHANTMENTS.get(enchantment);
    }

    /**
     * Get the instance of the enchantment from its id.
     * @param id The id of the enchantment.
     * @return The instance of the enchantment.
     */
    static Enchantment<?> getEnchant(String id) {
        return Enchantment.ENCHANTS_BY_ID.get(id);
    }

    /**
     * Convert all the enchants on this object to a ConfigurationSection.
     * @return The ConfigurationSection.
     */
    default ConfigurationSection serialize() {
        ConfigurationSection config = new YamlConfiguration();
        for (Map.Entry<Class<? extends Enchantment>, EnchantHolder> entry : getEnchantments().entrySet()) {
            EnchantHolder holder = entry.getValue();
            if (holder.level() > 0) {
                ConfigurationSection enchantSection = config.createSection(holder.enchantment().getId());
                enchantSection.set("level", holder.level());
                enchantSection.set("disabled", holder.isDisabled());
            }
        }
        return config;
    }

    /**
     * Apply all the enchants defined in the ConfigurationSection to this object.
     * @param config The ConfigurationSection.
     */
    default void deserialize(ConfigurationSection config) {

        if (config == null) return;

        for (String enchantId : config.getKeys(false)) {
            ConfigurationSection enchantSection = config.getConfigurationSection(enchantId);
            assert enchantSection != null;
            Enchantment<?> enchantment = getEnchant(enchantId);
            if (enchantment == null) continue;
            int level = enchantSection.getInt("level");
            setEnchantment(enchantment.getClass(), level);
            if (enchantSection.getBoolean("disabled")) {
                getEnchantments().put(getEnchant(enchantId).getClass(), new EnchantHolder(getEnchant(enchantId), level, true));
            }
        }
    }


    /**
     * Get all the enchantments on this item
     * @return A set of all the enchantments on this item
     */
    Map<Class<? extends Enchantment>, EnchantHolder> getEnchantments();

    /**
     * Add an enchantment to this item
     * @param enchantment The enchantment to add
     * @param level The level of the enchantment
     * @return True if the enchantment was added, false if it was not
     */
    boolean setEnchantment(Class<? extends Enchantment> enchantment, int level);

    /**
     * Remove an enchantment from this item
     * @param enchantment The enchantment to remove
     * @return True if the enchantment was removed, false if it was not
     */
    boolean removeEnchantment(Class<? extends Enchantment> enchantment);

    /**
     * Disable an enchantment on this item
     * @param enchantment The enchantment to disable
     * @return True if the enchantment was disabled, false if it was not
     */
    boolean disableEnchantment(Class<? extends Enchantment> enchantment);

    /**
     * Enable an enchantment on this item
     * @param enchantment The enchantment to enable
     * @return True if the enchantment was enabled, false if it was not
     */
    boolean enableEnchantment(Class<? extends Enchantment> enchantment);

    /**
     * Get a set containing all the disabled enchantments on this item
     * @return A set containing all the disabled enchantments on this item
     */
    default Map<Class<? extends Enchantment>, EnchantHolder> getDisabledEnchantments() {
        return getEnchantments().entrySet().stream().filter(entry -> entry.getValue().isDisabled()).collect(HashMap::new, (map, entry) -> map.put(entry.getKey(), entry.getValue()), HashMap::putAll);
    }

    /**
     * Get a set containing all the enabled enchantments on this item
     * @return A set containing all the enabled enchantments on this item
     */
    default Map<Class<? extends Enchantment>, EnchantHolder> getEnabledEnchantments() {
        return getEnchantments().entrySet().stream().filter(entry -> !entry.getValue().isDisabled()).collect(HashMap::new, (map, entry) -> map.put(entry.getKey(), entry.getValue()), HashMap::putAll);
    }

    /**
     * Get the level of an enchantment on this item
     * @param enchantment The enchantment to get the level of
     * @return The level of the enchantment, or 0 if it is not on this item
     */
    default int getEnchantmentLevel(Class<? extends Enchantment> enchantment) {
        EnchantHolder holder = getEnchantments().get(enchantment);
        return holder == null ? 0 : holder.level();
    }
    /**
     * Get the level of an enchantment on this item
     * @param enchantment The enchantment to get the level of
     * @return The level of the enchantment, or 0 if it is not on this item
     */
    default int getEnchantmentLevel(Enchantment enchantment) {
        EnchantHolder holder = getEnchantments().get(enchantment.getClass());
        return holder == null ? 0 : holder.level();
    }

    /**
     * Check if this item has an enchantment
     * @param enchantment The enchantment to check for
     * @return True if the item has the enchantment, false if it does not
     */
    default boolean hasEnchantment(Class<? extends Enchantment> enchantment) {
        return getEnchantments().containsKey(enchantment);
    }

}
