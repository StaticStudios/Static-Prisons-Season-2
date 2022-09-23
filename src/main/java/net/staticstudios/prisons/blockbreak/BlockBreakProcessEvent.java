package net.staticstudios.prisons.blockbreak;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

public class BlockBreakProcessEvent extends PlayerEvent {
    private static final HandlerList handlers = new HandlerList();
    private final BlockBreak blockBreak;

    public BlockBreakProcessEvent(BlockBreak blockBreak) {
        super(blockBreak.getPlayer());
        this.blockBreak = blockBreak;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    public BlockBreak getBlockBreak() {
        return blockBreak;
    }
}
