package net.staticstudios.mines.api.events;

import net.staticstudios.mines.StaticMine;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class MineCreatedEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    private final StaticMine mine;

    public MineCreatedEvent(StaticMine mine) {
        this.mine = mine;
    }

    public StaticMine getMine() {
        return mine;
    }

}
