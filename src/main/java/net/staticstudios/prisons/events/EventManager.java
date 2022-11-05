package net.staticstudios.prisons.events;

import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.blockbreak.BlockBreak;
import net.staticstudios.prisons.blockbreak.BlockBreakProcessEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class EventManager implements Listener {

    public static void init() {
        StaticPrisons.getInstance().getServer().getPluginManager().registerEvents(new EventManager(), StaticPrisons.getInstance());
    }

    @EventHandler
    void onBlockBreakProcess(BlockBreakProcessEvent e) {
        BlockBreak blockBreak = e.getBlockBreak();
        if (blockBreak.getMine().getId().equals("eventMine")) { //Apply the event mine 120% token multiplier
            blockBreak.stats().setTokenMultiplier(blockBreak.stats().getTokenMultiplier() + 0.2d);
        }
    }

//    @EventHandler
//    public void onPlayerJoin(PlayerJoinEvent event) {
//        PlayerData playerData = new PlayerData(event.getPlayer());
//
//        if (playerData.getPlayerRanks().contains("staticp")) {
//
//        }
//    }
}
