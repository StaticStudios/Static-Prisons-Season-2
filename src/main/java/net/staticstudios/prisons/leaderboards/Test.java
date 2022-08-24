package net.staticstudios.prisons.leaderboards;

import net.citizensnpcs.api.event.CitizensEnableEvent;
import net.staticstudios.prisons.StaticPrisons;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class Test implements Listener {

    @EventHandler
    public void onCitizenEnable(CitizensEnableEvent event) {
        StaticPrisons.getInstance().getLogger().info("Enabled Citizens");
    }
}
