package net.staticstudios.prisons.enchants;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

public interface Enchantable {


    /**
     * Get the instance of the enchantment from its class.
     *
     * @param enchantment The class of the enchantment.
     * @return The instance of the enchantment.
     */
    static Enchantment<?> getEnchant(Class<? extends Enchantment> enchantment) {
        return Enchantment.ALL_ENCHANTMENTS.get(enchantment);
    }

    /**
     * Get the instance of the enchantment from its id.
     *
     * @param id The id of the enchantment.
     * @return The instance of the enchantment.
     */
    static Enchantment<?> getEnchant(String id) {
        return Enchantment.ENCHANTS_BY_ID.get(id);
    }

    /**
     * Convert all the enchants on this object to a ConfigurationSection.
     *
     * @return The ConfigurationSection.
     */
    default ConfigurationSection serialize() {
        return serialize((a, b) -> {});
    }

    /**
     * Convert all the enchants on this object to a ConfigurationSection.
     *
     * @param extra Extra data to add to each ConfigurationSection.
     * @return The ConfigurationSection.
     */
    default ConfigurationSection serialize(BiConsumer<EnchantHolder, ConfigurationSection> extra) {
        ConfigurationSection config = new YamlConfiguration();
        for (Map.Entry<Class<? extends Enchantment>, EnchantHolder> entry : getEnchantmentMap().entrySet()) {
            EnchantHolder holder = entry.getValue();
            if (holder.level() > 0) {
                ConfigurationSection enchantSection = config.createSection(holder.enchantment().getId());
                enchantSection.set("level", holder.level());
                enchantSection.set("disabled", holder.isDisabled());
                extra.accept(holder, enchantSection);
            }
        }
        return config;
    }

    /**
     * Apply all the enchants defined in the ConfigurationSection to this object.
     *
     * @param config The ConfigurationSection.
     */
    default void deserialize(ConfigurationSection config) {
        deserialize(config, (a, b) -> {});
    }

    /**
     * Apply all the enchants defined in the ConfigurationSection to this object.
     *
     * @param config The ConfigurationSection.
     * @param extra Extra data to process from each ConfigurationSection.
     */
    default void deserialize(ConfigurationSection config, BiConsumer<Enchantment, ConfigurationSection> extra) {
        if (config == null) return;

        for (String enchantId : config.getKeys(false)) {
            ConfigurationSection enchantSection = config.getConfigurationSection(enchantId);
            assert enchantSection != null;
            Enchantment<?> enchantment = getEnchant(enchantId);
            if (enchantment == null) continue;
            int level = enchantSection.getInt("level");
            setEnchantment(enchantment.getClass(), level);
            if (enchantSection.getBoolean("disabled")) {
                getEnchantmentMap().put(getEnchant(enchantId).getClass(), new EnchantHolder(getEnchant(enchantId), level, true));
            }
            extra.accept(enchantment, enchantSection);
        }
    }


    /**
     * Get all the enchantments on this item
     *
     * @return A set of all the enchantments on this item
     */
    Map<Class<? extends Enchantment>, EnchantHolder> getEnchantmentMap();

    default Set<EnchantHolder> getEnchantments() {
        return getEnchantmentMap()
                .values()
                .stream()
                .filter(holder -> holder.level() > 0)
                .collect(Collectors.toUnmodifiableSet());
    }

    /**
     * Get a specific enchant holder on an Enchantable, from the enchantment class.
     * @param enchantment The enchantment class.
     * @return The enchantment holder.
     */
    default EnchantHolder getEnchantment(Class<? extends Enchantment> enchantment) {
        return getEnchantmentMap().getOrDefault(enchantment, new EnchantHolder(getEnchant(enchantment), 0, false));
    }

    /**
     * Add an enchantment to this item
     *
     * @param enchantment The enchantment to add
     * @param level       The level of the enchantment
     * @return True if the enchantment was added, false if it was not
     */
    boolean setEnchantment(Class<? extends Enchantment> enchantment, int level);

    /**
     * Attempt to upgrade an enchantment on this item.
     *
     * @param enchantment     The enchantment to upgrade.
     * @param player          The player upgrading the enchantment.
     * @param levelsToUpgrade The number of levels to upgrade the enchantment by.
     * @return True if the enchantment was upgraded, false if it was not.
     */
    boolean upgrade(Class<? extends Enchantment> enchantment, Player player, int levelsToUpgrade);

    /**
     * Remove an enchantment from this item
     *
     * @param enchantment The enchantment to remove
     * @param player      The player who removed the enchantment
     * @return True if the enchantment was removed, false if it was not
     */
    boolean removeEnchantment(Class<? extends Enchantment> enchantment, Player player);

    /**
     * Disable an enchantment on this item
     *
     * @param enchantment The enchantment to disable
     * @param player      The player who disabled the enchantment
     * @return True if the enchantment was disabled, false if it was not
     */
    boolean disableEnchantment(Class<? extends Enchantment> enchantment, Player player);

    /**
     * Enable an enchantment on this item
     *
     * @param enchantment The enchantment to enable
     * @param player      The player who enabled the enchantment
     * @return True if the enchantment was enabled, false if it was not
     */
    boolean enableEnchantment(Class<? extends Enchantment> enchantment, Player player);

    default boolean isDisabled(Class<? extends Enchantment> enchantment) {
        return getEnchantmentMap().getOrDefault(enchantment, new EnchantHolder(getEnchant(enchantment), 0, false)).isDisabled();
    }

    /**
     * Call this method when the Enchantable is held by a player.
     *
     * @param enchantable The Enchantable.
     * @param player      The player holding the Enchantable.
     */
    default void onHold(Enchantable enchantable, Player player) {
        for (EnchantHolder holder : getEnabledEnchantments()) {
            holder.enchantment().onHold(enchantable, player);
        }
    }

    /**
     * Call this method when the Enchantable is un-held by a player.
     *
     * @param enchantable The Enchantable.
     * @param player      The player un-holding the Enchantable.
     */
    default void onUnHold(Enchantable enchantable, Player player) {
        for (EnchantHolder holder : getEnabledEnchantments()) {
            holder.enchantment().onUnHold(enchantable, player);
        }
    }

    /**
     * Call this method when the Enchantable is upgraded by a player.
     *
     * @param enchantable The Enchantable.
     * @param enchantment The enchantment that was upgraded.
     * @param player      The player upgrading the Enchantable.
     * @param oldLevel    The level the Enchantable was upgraded to.
     * @param newLevel    The level the Enchantable was upgraded to.
     */
    default void onUpgrade(Enchantable enchantable, Enchantment<?> enchantment, Player player, int oldLevel, int newLevel) {
        enchantment.onUpgrade(enchantable, player, oldLevel, newLevel);
    }

    /**
     * Get a set containing all the disabled enchantments on this item
     *
     * @return A set containing all the disabled enchantments on this item
     */
    default Map<Class<? extends Enchantment>, EnchantHolder> getDisabledEnchantmentMap() {
        return getEnchantmentMap().entrySet().stream()
                .filter(entry -> entry.getValue().isDisabled())
                .collect(HashMap::new, (map, entry) -> map.put(entry.getKey(), entry.getValue()), HashMap::putAll);
    }

    /**
     * Get a set containing all the disabled enchantments on this item
     * @return A set containing all the disabled enchantments on this item
     */
    default Set<EnchantHolder> getDisabledEnchantments() {
        return getEnchantmentMap()
                .values()
                .stream()
                .filter(holder -> holder.level() > 0 && holder.isDisabled())
                .collect(Collectors.toUnmodifiableSet());
    }

    /**
     * Get a set containing all the enabled enchantments on this item
     *
     * @return A set containing all the enabled enchantments on this item
     */
    default Map<Class<? extends Enchantment>, EnchantHolder> getEnabledEnchantmentMap() {
        return getEnchantmentMap().entrySet().stream()
                .filter(entry -> !entry.getValue().isDisabled())
                .collect(HashMap::new, (map, entry) -> map.put(entry.getKey(), entry.getValue()), HashMap::putAll);
    }

    /**
     * Get a set containing all the enabled enchantments on this item
     * @return A set containing all the enabled enchantments on this item
     */
    default Set<EnchantHolder> getEnabledEnchantments() {
        return getEnchantmentMap()
                .values()
                .stream()
                .filter(holder -> holder.level() > 0 && !holder.isDisabled())
                .collect(Collectors.toUnmodifiableSet());
    }

    /**
     * Get the level of an enchantment on this item
     *
     * @param enchantment The enchantment to get the level of
     * @return The level of the enchantment, or 0 if it is not on this item
     */
    default int getEnchantmentLevel(Class<? extends Enchantment> enchantment) {
        EnchantHolder holder = getEnchantmentMap().get(enchantment);
        return holder == null ? 0 : holder.level();
    }

    /**
     * Get the level of an enchantment on this item
     *
     * @param enchantment The enchantment to get the level of
     * @return The level of the enchantment, or 0 if it is not on this item
     */
    default int getEnchantmentLevel(Enchantment enchantment) {
        EnchantHolder holder = getEnchantmentMap().get(enchantment.getClass());
        return holder == null ? 0 : holder.level();
    }

    /**
     * Check if this item has an enchantment
     *
     * @param enchantment The enchantment to check for
     * @return True if the item has the enchantment, false if it does not
     */
    default boolean hasEnchantment(Class<? extends Enchantment> enchantment) {
        return getEnchantmentMap().containsKey(enchantment);
    }

}
