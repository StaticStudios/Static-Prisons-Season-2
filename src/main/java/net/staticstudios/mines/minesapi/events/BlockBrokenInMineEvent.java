package net.staticstudios.mines.minesapi.events;

import net.staticstudios.mines.StaticMine;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBrokenInMineEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled;private final Block block;
    private final Player player;
    private final String mindID;
    private final BlockBreakEvent blockBreakEvent;
    private final StaticMine mine;

    public boolean isCancelled() {
        return cancelled;
    }
    public void setCancelled(boolean cancel) {
        cancelled = cancel;
    }
    public HandlerList getHandlers() {
        return handlers;
    }
    public static HandlerList getHandlerList() {
        return handlers;
    }

    public BlockBrokenInMineEvent(BlockBreakEvent blockBreakEvent, String mineID) {
        this.blockBreakEvent = blockBreakEvent;
        this.block = blockBreakEvent.getBlock();
        this.player = blockBreakEvent.getPlayer();
        this.mindID = mineID;
        this.mine = StaticMine.getMine(mineID);
    }

    public BlockBreakEvent getBlockBreakEvent() { return blockBreakEvent; }
    public Block getBlock() { return block; }
    public Location getBlockLocation() { return block.getLocation(); }
    public Material getBlockType() { return block.getType(); }
    public Player getPlayer() { return player; }
    public String getMindID() { return mindID; }
    public StaticMine getMine() { return mine; }

}
