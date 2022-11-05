package net.staticstudios.prisons.blockbreak;

import net.staticstudios.mines.api.events.BlockBrokenInMineEvent;
import net.staticstudios.prisons.pickaxe.PrisonPickaxe;
import net.staticstudios.prisons.privatemines.PrivateMine;
import net.staticstudios.prisons.utils.Constants;
import org.bukkit.event.EventHandler;

class BlockBreakListener implements org.bukkit.event.Listener {

    @EventHandler
    void onBlockBrokenInMine(BlockBrokenInMineEvent e) {
        if (!e.getPlayer().getWorld().equals(Constants.MINES_WORLD) && !e.getPlayer().getWorld().equals(PrivateMine.PRIVATE_MINES_WORLD))
            return; //Ensure that a player is in a mines world
        PrisonPickaxe pickaxe = PrisonPickaxe.fromItem(e.getPlayer().getInventory().getItemInMainHand());
        if (pickaxe == null) return; //We don't care if the player is not holding a pickaxe
        new BlockBreak(e.getPlayer(), pickaxe, e.getMine(), e.getBlock()).process();
    }

}
