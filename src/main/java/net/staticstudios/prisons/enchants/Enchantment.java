package net.staticstudios.prisons.enchants;

import net.kyori.adventure.text.Component;
import net.staticstudios.prisons.StaticPrisons;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.EventExecutor;

import java.util.HashMap;
import java.util.Map;

public interface Enchantment<E extends Event> extends Listener {

    Map<Class<? extends Event>, Enchantment<? extends Event>> ENCHANT_EVENTS = new HashMap<>();
    Map<Class<? extends Enchantment>, Enchantment> ENCHANTS = new HashMap<>();
    Map<String, Enchantment> ENCHANTS_BY_ID = new HashMap<>();

    Class<E> getListeningFor();

    void onEvent(E event);

    default EventPriority getPriority() {
        return EventPriority.NORMAL;
    }

    String getId();

    String getName();

    Component getNameAsComponent();

    Component getDisplayName();

    int getMaxLevel();

    long getUpgradeCost();

    default long getUpgradeCost(int level) {
        return getUpgradeCost();
    }

    default double getChanceToActivate(Enchantable enchantable) {
        int level = enchantable.getEnchantmentLevel(this.getClass());
        double totalChance = getDefaultChanceToActivate() + getChanceToActivateAtMaxLevel() / getMaxLevel() * level;
        return Math.max(Math.min(totalChance, 1), 0);
    }

    double getDefaultChanceToActivate();

    double getChanceToActivateAtMaxLevel();

    default boolean shouldActivate(Enchantable enchantable) {
        return Math.random() <= getChanceToActivate(enchantable);
    }

    default void register() {
        StaticPrisons.log("[Enchants] Registering enchantment: " + getName());

        ENCHANT_EVENTS.put(getListeningFor(), this);
        ENCHANTS.put(this.getClass(), this);
        ENCHANTS_BY_ID.put(getId(), this);

        try {
            StaticPrisons.getInstance().getServer().getPluginManager().registerEvent(
                    getListeningFor(),
                    this,
                    getPriority(),
                    EventExecutor.create(this.getClass().getMethod("onEvent", getListeningFor()), getListeningFor()),
                    StaticPrisons.getInstance());
        } catch (NoSuchMethodException e) {
            StaticPrisons.log("[Enchants] Failed to register enchantment: " + getName());
            throw new RuntimeException(e);
        }
    }
}
