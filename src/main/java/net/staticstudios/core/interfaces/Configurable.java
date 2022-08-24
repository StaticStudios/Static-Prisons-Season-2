package net.staticstudios.core.interfaces;

/**
 * Use this interface if you don't want to create a new feature but need a config.
 * @see Feature The feature interface for creating a new feature.
 */
public interface Configurable extends Saveable {

    /**
     * Load the config.
     * Note: this method is automatically called by the {@link Feature} interface
     */
    void loadConfig();


    /**
     * save the config
     * note: this method is not required
     */
    default void saveConfig() {

    }

    @Override
    default void init() {
        loadConfig();
    }

    @Override
    default void save() {
        saveConfig();
    }
}
