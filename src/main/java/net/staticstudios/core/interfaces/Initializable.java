package net.staticstudios.core.interfaces;

import net.staticstudios.prisons.StaticPrisons;
import org.bukkit.plugin.java.JavaPlugin;

public interface Initializable {

    StaticPrisons instance = StaticPrisons.getInstance();

    /**
     * Note: this method is automatically called by the {@link JavaPlugin#onEnable()} interface
     */
    void init();
}
