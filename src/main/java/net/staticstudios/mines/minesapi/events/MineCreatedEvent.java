package net.staticstudios.mines.minesapi.events;

import net.staticstudios.mines.StaticMine;
import org.bukkit.Location;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class MineCreatedEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled;
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

    public MineCreatedEvent(StaticMine mine) {
        this.mine = mine;
    }

    public StaticMine getMine() { return mine; }

}
