package net.staticstudios.prisons.enchants;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class EnchantableEvent<E extends Event> extends Event {
    private static final HandlerList handlers = new HandlerList();

    private final E event;
    private final Enchantable enchantable;

    public EnchantableEvent(E event, Enchantable enchantable) {
        this.event = event;
        this.enchantable = enchantable;
    }

    @SuppressWarnings("unused")
    public static HandlerList getHandlerList() {
        return handlers;
    }

    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    public E getEvent() {
        return event;
    }

    public Enchantable getEnchantable() {
        return enchantable;
    }
}
