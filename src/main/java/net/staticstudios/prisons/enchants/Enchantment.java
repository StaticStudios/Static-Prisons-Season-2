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

    Map<Class<? extends Event>, Enchantment<? extends Event>> ENCHANTMENTS = new HashMap<>();

    Class<E> getListeningFor();

    void onEvent(E event);

    default EventPriority getPriority() {
        return EventPriority.NORMAL;
    }

    String getName();

    Component getDisplayName();

    Component getUnformattedDisplayName();

    int getMaxLevel();

    long getUpgradeCost();

    default long getUpgradeCost(int level) {
        return getUpgradeCost();
    }

    default void register() {
        StaticPrisons.log("[Enchants] Registering enchantment: " + getName());

        ENCHANTMENTS.put(getListeningFor(), this);

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
