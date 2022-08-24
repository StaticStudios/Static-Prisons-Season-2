package net.staticstudios.core.interfaces;

import org.bukkit.plugin.java.JavaPlugin;

public interface Saveable extends Initializable {

    /**
     * this method is automatically called by the {@link JavaPlugin#onDisable()} Class
     */
    void save();

}
