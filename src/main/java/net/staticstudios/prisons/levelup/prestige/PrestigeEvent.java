package net.staticstudios.prisons.levelup.prestige;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

public class PrestigeEvent extends PlayerEvent {
    private static final HandlerList handlers = new HandlerList();
    private final long from;
    private final long to;
    private final long moneySpent;
    public PrestigeEvent(Player player, long from, long to, long moneySpent) {
        super(player);
        this.from = from;
        this.to = to;
        this.moneySpent = moneySpent;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    public long getFrom() {
        return from;
    }

    public long getTo() {
        return to;
    }

    public long getMoneySpent() {
        return moneySpent;
    }
}