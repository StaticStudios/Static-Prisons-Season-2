package net.staticstudios.prisons.gangs;

import net.staticstudios.prisons.blockbreak.BlockBreak;
import net.staticstudios.prisons.blockbreak.BlockBreakProcessEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class GangListener implements Listener {
    @EventHandler
    void onBlockBreakProcess(BlockBreakProcessEvent e) {
        BlockBreak blockBreak = e.getBlockBreak();
        blockBreak.addAfterProcess(bb -> {
            Gang gang = Gang.getGang(bb.getPlayerData().getUUID());
            if (gang != null) {
                gang.addRawBlocksMined(1);
                gang.addBlocksMined((long) (bb.stats().getBlocksBroken() * bb.stats().getBlocksBrokenMultiplier()));
                if (bb.stats().getTokensEarned() > 0) {
                    gang.addTokensFound(bb.stats().getTokensEarned());
                }
            }
        });
    }
}
