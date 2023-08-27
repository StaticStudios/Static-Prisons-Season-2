package net.staticstudios.mines.api.events;

import net.staticstudios.mines.StaticMine;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.BlockBreakEvent;
import org.jetbrains.annotations.NotNull;

public class BlockBrokenInMineEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    private final Block block;
    private final Player player;
    private final String mindID;
    private final BlockBreakEvent blockBreakEvent;
    private final StaticMine mine;

    public BlockBrokenInMineEvent(BlockBreakEvent blockBreakEvent, String mineID) {
        this.blockBreakEvent = blockBreakEvent;
        this.block = blockBreakEvent.getBlock();
        this.player = blockBreakEvent.getPlayer();
        this.mindID = mineID;
        this.mine = StaticMine.getMine(mineID);
    }

    public BlockBreakEvent getBlockBreakEvent() {
        return blockBreakEvent;
    }

    public Block getBlock() {
        return block;
    }

    public Location getBlockLocation() {
        return block.getLocation().toBlockLocation();
    }

    public Material getBlockType() {
        return block.getType();
    }

    public Player getPlayer() {
        return player;
    }

    public String getMindID() {
        return mindID;
    }

    public StaticMine getMine() {
        return mine;
    }
}
