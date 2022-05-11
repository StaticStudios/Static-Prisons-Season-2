package net.staticstudios.minesapi.events;

import org.bukkit.Location;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class MineRefilledEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled;
    private final String mindID;
    private final Location minPoint;
    private final Location maxPoint;

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

    public MineRefilledEvent(String mineID, Location minPoint, Location maxPoint) {
        this.mindID = mineID;
        this.minPoint = minPoint;
        this.maxPoint = maxPoint;
    }

    public String getMindID() { return mindID; }
    public Location getMinPoint() { return minPoint.clone(); }
    public Location getMaxPoint() { return maxPoint.clone(); }

}
