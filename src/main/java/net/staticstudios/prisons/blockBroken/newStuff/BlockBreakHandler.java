package net.staticstudios.prisons.blockBroken.newStuff;

import net.staticstudios.mines.minesapi.events.BlockBrokenInMineEvent;
import net.staticstudios.prisons.pickaxe.PrisonPickaxe;
import net.staticstudios.prisons.privateMines.PrivateMine;
import net.staticstudios.prisons.utils.Constants;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import java.util.HashSet;
import java.util.Set;

public class BlockBreakHandler {

    /**
     * Different ways that a block break event can be handled/called
     */
    enum EventType {
        BLOCK_BREAK_EVENT,
        SIMULATE
    }

    private static Set<World> MINE_WORLDS = new HashSet<>();

    public static void init() {
        MINE_WORLDS.add(Constants.MINES_WORLD);
        MINE_WORLDS.add(PrivateMine.PRIVATE_MINES_WORLD);
    }


    static class Listener implements org.bukkit.event.Listener {
        @EventHandler
        void onBlockBreak(BlockBrokenInMineEvent e) {
            Player player = e.getPlayer();

            if (!MINE_WORLDS.contains(player.getWorld())) return;

            e.getBlockBreakEvent().setDropItems(false);
            e.getBlockBreakEvent().setExpToDrop(0);
            PrisonPickaxe pickaxe = PrisonPickaxe.fromItem(player.getInventory().getItemInMainHand());
            if (pickaxe == null) {
                e.getBlockBreakEvent().setCancelled(true);
                return;
            }
            BlockBreak bb = new BlockBreak(e.getBlockLocation(), player, pickaxe);
            bb.handle(b -> {
                //Default action
                e.setCancelled(b.isCanceled()); //If the BlockBreak has been canceled, then don't change the block to air; cancel it
                //todo: put a listener in the pickaxe package to apply events
            });
        }
    }
}
