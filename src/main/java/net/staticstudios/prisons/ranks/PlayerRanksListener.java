package net.staticstudios.prisons.ranks;

import net.staticstudios.prisons.blockbreak.BlockBreak;
import net.staticstudios.prisons.blockbreak.BlockBreakProcessEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerRanksListener implements Listener {
    @EventHandler
    void onBlockBreakProcess(BlockBreakProcessEvent e) {
        BlockBreak blockBreak = e.getBlockBreak();
        switch (blockBreak.getPlayerData().getPlayerRank()) {
            case "mythic" -> blockBreak.stats().setTokenMultiplier(blockBreak.stats().getTokenMultiplier() + 0.05d); //+5%
            case "static" -> blockBreak.stats().setTokenMultiplier(blockBreak.stats().getTokenMultiplier() + 0.1d); //+10%
            case "staticp" -> blockBreak.stats().setTokenMultiplier(blockBreak.stats().getTokenMultiplier() + 0.15d); //+15%
        }
        if (blockBreak.getPlayerData().getIsNitroBoosting()) {
            blockBreak.stats().setTokenMultiplier(blockBreak.stats().getTokenMultiplier() + 0.05d); //+5%
        }
    }
}
