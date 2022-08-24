package net.staticstudios.core.interfaces;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * This inteface contains all basic methods needed to create a new feature.
 * {@link #init()} will be called by the plugins {@link JavaPlugin#onEnable()} method.
 */
public interface Feature extends Saveable, Configurable {

    /**
     * Use this method to load your feature.
     */
    default void loadFeature() {

    }

    /**
     * Use this method to disable the feature.
     */
    default void unloadFeature() {

    }


    /**
     * This method gets executed by the plugin when its being loaded
     * use the {@link #loadFeature()} method to initialize feature logic
     */
    @Override
    default void init() {
        loadConfig();
        loadFeature();
    }

    /**
     * This method gets executed by the plugin when its being disabled
     * use the {@link #unloadFeature()} method to disable the feature.
     */
    @Override
    default void save() {
        saveConfig();
        unloadFeature();
    }
}
