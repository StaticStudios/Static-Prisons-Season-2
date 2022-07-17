package net.staticstudios.prisons.blockBroken.newStuff;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class HandleBlockBreakEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }
    private final BlockBreak blockBreak;

    public BlockBreak getBlockBreak() { return blockBreak; }

    public HandleBlockBreakEvent(BlockBreak blockBreak) {
        this.blockBreak = blockBreak;
    }
}
