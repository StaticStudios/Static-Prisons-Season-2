package net.staticstudios.prisons.levelup.rankup;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

public class RankUpEvent extends PlayerEvent {
    private static final HandlerList handlers = new HandlerList();
    private final int from;
    private final int to;
    public RankUpEvent(Player player, int from, int to) {
        super(player);
        this.from = from;
        this.to = to;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }
}