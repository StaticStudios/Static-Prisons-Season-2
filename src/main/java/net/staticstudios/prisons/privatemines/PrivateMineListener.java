package net.staticstudios.prisons.privatemines;

import net.staticstudios.prisons.blockbreak.BlockBreak;
import net.staticstudios.prisons.blockbreak.BlockBreakProcessEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PrivateMineListener implements Listener {
    @EventHandler
    void onBlockBreakProcess(BlockBreakProcessEvent e) {
        BlockBreak blockBreak = e.getBlockBreak();
        if (!blockBreak.getMine().getId().startsWith("private_mine")) return;
        PrivateMine privateMine = PrivateMine.MINE_ID_TO_PRIVATE_MINE.get(blockBreak.getMine().getId());
        privateMine.blockBroken(blockBreak);
    }
}
