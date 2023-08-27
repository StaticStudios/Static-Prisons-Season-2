package net.staticstudios.mines;

import net.staticstudios.mines.api.events.BlockBrokenInMineEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class StaticMinesListener implements Listener {
    @EventHandler
    void onBlockBreak(BlockBreakEvent e) {
        Location loc = e.getBlock().getLocation();
        StaticMine mine = StaticMines.fromLocation(loc, true);
        if (mine == null) return;
        Bukkit.getPluginManager().callEvent(new BlockBrokenInMineEvent(e, mine.getId()));
    }

    @EventHandler(priority = EventPriority.LOWEST)
    void onBlockBroken(BlockBrokenInMineEvent e) {
        e.getMine().removeBlocks(1);
    }
}
