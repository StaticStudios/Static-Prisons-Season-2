package net.staticstudios.newgui;

import net.kyori.adventure.text.Component;
import net.staticstudios.newgui.settings.GUISettings;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * This class contains all methods to manage GUIs. To Create a GUI, use the GUICreator class.
 *
 * @author Sam (GitHub: <a href="https://github.com/Sammster10">Sam's GitHub</a>)
 */
public class StaticGUI implements InventoryHolder {

    static final HashSet<StaticGUI> MENUS = new HashSet<>();

    private static JavaPlugin parent;
    private final Inventory inventory;
    private final Component title;
    private final int size;
    private final boolean persistent;
    private final boolean pages;
    private final GUIButton previousPageButton;
    private final GUIButton nextPageButton;
    private final GUIButton pageNumberButton;
    private final BiConsumer<Player, StaticGUI> onOpen;
    private final BiConsumer<Player, StaticGUI> onClose;
    private final GUIButton[] buttons;
    private final GUISettings settings = new GUISettings();
    private Consumer<StaticGUI> onUpdate;
    private int updateInterval;
    private BukkitTask updateTask;
    protected Map<Integer, BiConsumer<StaticGUI, InventoryClickEvent>> listeners = new HashMap<>();

    /**
     * Constructor called by GUIBuilder#build().
     */
    public StaticGUI(Component title, int size, boolean persistent,
                     boolean pages, GUIButton previousPageButton, GUIButton nextPageButton, GUIButton pageNumberButton,
                     Consumer<StaticGUI> onUpdate, int updateInterval,
                     BiConsumer<Player, StaticGUI> onOpen, BiConsumer<Player, StaticGUI> onClose) {
        if (parent == null) {
            throw new IllegalStateException("StaticGUI has not been enabled yet! Call the StaticGUI.enable() method in your plugin!");
        }
        this.title = title;
        this.size = size;
        this.persistent = persistent;
        this.pages = pages;
        this.previousPageButton = previousPageButton;
        this.nextPageButton = nextPageButton;
        this.pageNumberButton = pageNumberButton;
        if (onUpdate != null) {
            this.onUpdate = onUpdate;
            this.updateInterval = updateInterval;
            this.updateTask = Bukkit.getScheduler().runTaskTimer(parent, () -> onUpdate.accept(this), updateInterval, updateInterval);
        }
        this.onOpen = onOpen;
        this.onClose = onClose;
        this.inventory = Bukkit.createInventory(this, size, title);
        this.buttons = new GUIButton[size];
        MENUS.add(this);
    }

    public static JavaPlugin getParent() {
        return parent;
    }

    /**
     * Initializes the StaticGUI class, this method should be called before creating any GUI.
     *
     * @param parent - Your plugin instance.
     */
    public static void enable(JavaPlugin parent) {
        StaticGUI.parent = parent;
        parent.getServer().getPluginManager().registerEvents(new GUIListener(), parent);
    }

    /**
     * This will stop listening for GUI events, kick players out of all GUIs, and will destroy all the GUIs.
     */
    public static void disable() {
        for (StaticGUI menu : MENUS) {
            for (HumanEntity player : new ArrayList<>(menu.getInventory().getViewers())) {
                player.closeInventory();
            }
            if (menu.updateTask != null) {
                menu.updateTask.cancel();
            }
        }
        MENUS.clear();
    }

    /**
     * @return The Bukkit inventory for this GUI.
     */
    @NotNull
    @Override
    public Inventory getInventory() {
        return inventory;
    }

    public Component getTitle() {
        return title;
    }

    public int getSize() {
        return size;
    }

    public boolean isPersistent() {
        return persistent;
    }

    //    public boolean usePages() {
//        return pages;
//    }
//    public GUIButton getPreviousPageButton() {
//        return previousPageButton;
//    }
//    public GUIButton getNextPageButton() {
//        return nextPageButton;
//    }
//    public GUIButton getPageNumberButton() {
//        return pageNumberButton;
//    }
    public Consumer<StaticGUI> onUpdate() {
        return onUpdate;
    }

    public int getUpdateInterval() {
        return updateInterval;
    }

    public BiConsumer<Player, StaticGUI> onOpen() {
        return onOpen;
    }

    public BiConsumer<Player, StaticGUI> onClose() {
        return onClose;
    }

    public GUIButton[] getButtons() {
        return buttons;
    }

    /**
     * Remove all previous buttons (if any) and set the new buttons to the one's specified.
     *
     * @param buttons The buttons to set.
     * @throws IllegalArgumentException If too many buttons are trying to be set.
     */
    public void setButtons(GUIButton... buttons) {
        if (buttons.length > this.buttons.length) {
            throw new IllegalArgumentException("Too many buttons!");
        }
        for (int i = 0; i < this.buttons.length; i++) {
            if (i < buttons.length) {
                this.buttons[i] = buttons[i];
                inventory.setItem(i, buttons[i].item());
            } else {
                this.buttons[i] = null;
                inventory.setItem(i, null);
            }
        }
    }

    public GUISettings getSettings() {
        return settings;
    }

    /**
     * Add a button for this GUI
     *
     * @param index  The index of the button.
     * @param button The button to add.
     */
    public void setButton(int index, GUIButton button) {
        buttons[index] = button;
        inventory.setItem(index, button.item());
    }

    /**
     * Remove one, or more, buttons from this GUI.
     *
     * @param index The index(es) of the button(s) to remove.
     */
    public void removeButton(int... index) {
        for (int i : index) {
            buttons[i] = null;
            inventory.setItem(i, null);
        }
    }

    /**
     * Opens the GUI for the specified player.
     *
     * @param player The player to open the GUI for.
     */
    public void open(Player player) {
        if (!MENUS.contains(this)) {
            throw new IllegalStateException("This GUI has already been destroyed, it no longer exists!");
        }
        player.openInventory(inventory);
    }

    void destroy() {
        MENUS.remove(this);
        if (updateTask != null) {
            updateTask.cancel();
        }
    }

    /**
     * Fills the GUI's inventory with the specified button. It will only fill empty slots.
     */
    public void fill(GUIButton button) {
        for (int i = 0; i < buttons.length; i++) {
            if (buttons[i] == null && inventory.getItem(i) == null) {
                setButton(i, button);
            }
        }
    }

    /**
     * Add a listener to a specific slot in the GUI.
     *
     * @param index   The index of the slot.
     * @param onClick The listener to add. This will be called when the slot is clicked.
     */
    public void listen(int index, BiConsumer<StaticGUI, InventoryClickEvent> onClick) {
        listeners.put(index, onClick);
    }

    /**
     * Remove a listener from a specific slot in the GUI.
     * @param index The index of the slot.
     */
    public void removeListener(int index) {
        listeners.remove(index);
    }


}
