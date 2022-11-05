package net.staticstudios.newgui.event;

import net.staticstudios.newgui.GUIButton;
import net.staticstudios.newgui.StaticGUI;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;

public class StaticGuiClickEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private final StaticGUI gui;

    private final GUIButton button;

    private final int slot;

    private final InventoryClickEvent inventoryClickEvent;

    public StaticGuiClickEvent(StaticGUI gui, GUIButton button, int slot, InventoryClickEvent inventoryClickEvent) {
        this.gui = gui;
        this.button = button;
        this.slot = slot;
        this.inventoryClickEvent = inventoryClickEvent;
    }

    public StaticGUI getGui() {
        return gui;
    }

    public GUIButton getButton() {
        return button;
    }

    public int getSlot() {
        return slot;
    }

    public InventoryClickEvent getInventoryClickEvent() {
        return inventoryClickEvent;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}

