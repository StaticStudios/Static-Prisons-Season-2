package net.staticstudios.prisons.events;

import net.staticstudios.prisons.blockbreak.BlockBreak;
import net.staticstudios.prisons.data.PlayerData;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class EventManager implements Listener {

    public static void init() {
        BlockBreak.addListener(blockBreak -> {
            if (blockBreak.getMine().getID().equals("eventMine")) { //Apply the event mine 120% token multiplier
                blockBreak.getStats().setTokenMultiplier(blockBreak.getStats().getTokenMultiplier() + 0.2d);
            }
        });
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        PlayerData playerData = new PlayerData(event.getPlayer());

        if (playerData.getPlayerRanks().contains("staticp")) {

        }
    }
}
