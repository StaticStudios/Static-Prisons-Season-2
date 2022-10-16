package net.staticstudios.prisons.enchants;

import net.kyori.adventure.text.Component;
import net.staticstudios.prisons.StaticPrisons;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.EventExecutor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface Enchantment<E extends Event> extends Listener {

    Map<Class<? extends Event>, Enchantment<? extends Event>> ENCHANT_EVENTS = new HashMap<>();
    Map<Class<? extends Enchantment>, Enchantment> ALL_ENCHANTMENTS = new HashMap<>();
    Map<String, Enchantment> ENCHANTS_BY_ID = new HashMap<>();
    List<Enchantment> ORDERED_ENCHANTS = new ArrayList<>();

    static void init() {
        ENCHANT_EVENTS.clear();
        ALL_ENCHANTMENTS.clear();
        ENCHANTS_BY_ID.clear();
        ORDERED_ENCHANTS.clear();


        StaticPrisons.getInstance().getServer().getPluginManager().registerEvents(new EnchantItemStackListener(), StaticPrisons.getInstance());
    }

    static List<Enchantment> getEnchantsInOrder() {
        return ORDERED_ENCHANTS;
    }

    /**
     * Get the class of the event that this enchantment listens for.
     *
     * @return The class of the event.
     */
    Class<E> getListeningFor();

    /**
     * This method will be called when the event, specified by {@link #getListeningFor()} is called.
     *
     * @param event The event.
     */
    default void onEvent(E event) {
    }

    /**
     * Check if the event should be called.
     * This should check to make sure all conditions are met before the event is called.
     *
     * @param event The event.
     * @return True if the event should be called, false otherwise.
     */
    boolean beforeEvent(E event);

    private void eventCalled(E event) {
        if (beforeEvent(event)) {
            onEvent(event);
        }
    }

    /**
     * Get the event priority of this enchantment.
     *
     * @return The event priority.
     */
    default EventPriority getPriority() {
        return EventPriority.NORMAL;
    }

    /**
     * Get the id of this enchantment.
     *
     * @return The id.
     */
    String getId();

    /**
     * Get the name of this enchantment.
     *
     * @return The enchantment name.
     */
    String getName();

    /**
     * Get the name of this enchantment as a Component.
     *
     * @return The enchantment name as a Component.
     */
    Component getNameAsComponent();

    /**
     * Get a formatted name of this enchantment.
     *
     * @return The formatted name.
     */
    Component getDisplayName();

    /**
     * Get the description of this enchantment.
     *
     * @return The description.
     */
    List<Component> getDescription();

    /**
     * Get the maximum level of this enchantment.
     *
     * @return The maximum level.
     */
    int getMaxLevel();

    /**
     * Get the upgrade cost for this enchantment.
     *
     * @return The upgrade cost.
     */
    long getUpgradeCost();

    /**
     * Get the upgrade cost for this enchantment at a specific level.
     *
     * @param level The level.
     * @return The upgrade cost.
     */
    default long getUpgradeCost(int level) {
        return getUpgradeCost();
    }

    /**
     * Get the chance to activate this enchantment.
     *
     * @param enchantable The enchantable item that this enchantment is on.
     * @return The chance to activate.
     */
    default double getChanceToActivate(Enchantable enchantable) {
        int level = enchantable.getEnchantmentLevel(this.getClass());
        double totalChance = getDefaultChanceToActivate() + getChanceToActivateAtMaxLevel() / getMaxLevel() * level;
        return Math.max(Math.min(totalChance, 1), 0);
    }

    /**
     * Get the default chance to activate this enchantment.
     *
     * @return The default chance to activate.
     */
    double getDefaultChanceToActivate();

    /**
     * Get the chance to activate this enchantment at max level.
     *
     * @return The chance to activate at max level.
     */
    double getChanceToActivateAtMaxLevel();

    /**
     * Get the minimum level requirement for this enchantment.
     *
     * @return The level requirement.
     */
    int getLevelRequirement();


    /**
     * Called when an Enchantable item is held that has this enchantment on it.
     *
     * @param enchantable The enchantable item.
     * @param player      The player holding the item.
     */
    void onHold(Enchantable enchantable, Player player);

    /**
     * Called when an Enchantable item is un-held that has this enchantment on it.
     *
     * @param enchantable The enchantable item.
     * @param player      The player un-holding the item.
     */
    void onUnHold(Enchantable enchantable, Player player);

    /**
     * Called when an Enchantable item is upgraded with this enchantment.
     *
     * @param enchantable The enchantable item.
     * @param player      The player upgrading the item.
     * @param oldLevel    The old level of the enchantment.
     * @param newLevel    The new level of the enchantment.
     */
    void onUpgrade(Enchantable enchantable, Player player, int oldLevel, int newLevel);


    /**
     * Figure out if this enchantment should activate.
     * This will check a random number against the chance to activate.
     *
     * @param enchantable The enchantable item that this enchantment is on.
     * @return True if the enchantment should activate, false otherwise.
     */
    default boolean shouldActivate(Enchantable enchantable) {
        return Math.random() <= getChanceToActivate(enchantable);
    }

    /**
     * Register this enchantment.
     */
    default void register() {
        StaticPrisons.log("[Enchants] Registering enchantment: " + getName());

        ENCHANT_EVENTS.put(getListeningFor(), this);
        ALL_ENCHANTMENTS.put(this.getClass(), this);
        ENCHANTS_BY_ID.put(getId(), this);

        ORDERED_ENCHANTS.add(this);

        try {
            StaticPrisons.getInstance().getServer().getPluginManager().registerEvent(
                    getListeningFor(),
                    this,
                    getPriority(),
                    EventExecutor.create(this.getClass().getMethod("eventCalled", getListeningFor()), getListeningFor()),
                    StaticPrisons.getInstance());
        } catch (NoSuchMethodException e) {
            StaticPrisons.log("[Enchants] Failed to register enchantment: " + getName());
            throw new RuntimeException(e);
        }
    }
}
