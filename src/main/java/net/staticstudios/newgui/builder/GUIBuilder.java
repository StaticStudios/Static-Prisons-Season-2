package net.staticstudios.newgui.builder;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.staticstudios.newgui.GUIButton;
import net.staticstudios.newgui.StaticGUI;
import org.bukkit.entity.Player;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * @author Sam (GitHub: <a href="https://github.com/Sammster10">Sam's GitHub</a>)
 */
public final class GUIBuilder {

    /**
     * @return A new GUIBuilder instance.
     */
    public static GUIBuilder builder() {
        return new GUIBuilder();
    }

    private Component title = Defaults.DEFAULT_TITLE;
    private int size = Defaults.DEFAULT_SIZE;
    private boolean persistent = false;

    private boolean pages = false; //TODO: add support for pages
    private GUIButton previousPageButton;
    private GUIButton nextPageButton;
    private GUIButton pageNumberButton;

    private Consumer<StaticGUI> onUpdate = (gui) -> {
    };
    private int updateInterval; //In ticks

    private BiConsumer<Player, StaticGUI> onOpen;
    private BiConsumer<Player, StaticGUI> onClose = (player, gui) -> {
    };

    private GUIButton fillWith;

    public GUIBuilder() {
    }


    /**
     * Sets the title of the GUI.
     *
     * @param title The title of the GUI.
     * @return The Builder instance.
     */
    public GUIBuilder title(Component title) {
        this.title = title;
        return this;
    }

    /**
     * Sets the title of the GUI.
     *
     * @param title The title of the GUI.
     * @return The Builder instance.
     */
    public GUIBuilder title(String title) {
        this.title = LegacyComponentSerializer.legacyAmpersand().deserialize(title);
        return this;
    }

    /**
     * Sets the size of the GUI.
     *
     * @param size The size of the GUI.
     * @return The Builder instance.
     */
    public GUIBuilder size(int size) {
        if (size % 9 != 0 || size <= 0) {
            throw new IllegalArgumentException("Size must be a multiple of 9 and greater than 0!");
        }
        this.size = size;
        return this;
    }

    /**
     * Sets whether the GUI should exist forever, by default it will be destroyed as soon as it is closed by a player.
     *
     * @param persistent Whether the GUI should exist forever.
     * @return The Builder instance.
     */
    public GUIBuilder persistent(boolean persistent) {
        this.persistent = persistent;
        return this;
    }

    /**
     * Sets whether the GUI should have pages.
     * @param pages Whether the GUI should have pages.
     * @return The Builder instance.
     */
//    public GUIBuilder pages(boolean pages) {
//        this.pages = pages;
//        return this;
//    }

    /**
     * Sets the previous page button.
     * @param previousPageButton The previous page button.
     * @return The Builder instance.
     */
//    public GUIBuilder previousPageButton(GUIButton previousPageButton) {
//        this.previousPageButton = previousPageButton;
//        return this;
//    }

    /**
     * Sets the next page button.
     * @param nextPageButton The next page button.
     * @return The Builder instance.
     */
//    public GUIBuilder nextPageButton(GUIButton nextPageButton) {
//        this.nextPageButton = nextPageButton;
//        return this;
//    }

    /**
     * Sets a consumer that will get called every updateInterval ticks.
     *
     * @param onUpdate              The consumer that will get called every updateInterval ticks.
     * @param updateIntervalInTicks The update interval in ticks.
     * @return The Builder instance.
     */
    public GUIBuilder onUpdate(Consumer<StaticGUI> onUpdate, int updateIntervalInTicks) {
        this.onUpdate = onUpdate;
        this.updateInterval = updateIntervalInTicks;
        return this;
    }

    /**
     * Sets a consumer that will get called when the GUI is opened.
     *
     * @param onOpen The consumer that will get called when the GUI is opened.
     * @return The Builder instance.
     */
    public GUIBuilder onOpen(BiConsumer<Player, StaticGUI> onOpen) {
        this.onOpen = onOpen;
        return this;
    }

    /**
     * Sets a consumer that will get called when the GUI is closed.
     *
     * @param onClose The consumer that will get called when the GUI is closed.
     * @return The Builder instance.
     */
    public GUIBuilder onClose(BiConsumer<Player, StaticGUI> onClose) {
        this.onClose = onClose;
        return this;
    }

    /**
     * Fill all empty slots of the GUI with a button.
     * Typically, this would be done with placeholders.
     * This functions the same as calling build().fill(button)
     *
     * @param fillWith The button that will fill the GUI with.
     * @return The Builder instance.
     */
    public GUIBuilder fillWith(GUIButton fillWith) {
        this.fillWith = fillWith;
        return this;
    }


    /**
     * Builds the GUI.
     *
     * @return The GUI instance.
     */
    public StaticGUI build() {
        StaticGUI gui = new StaticGUI(title, size, persistent,
                pages, previousPageButton,
                nextPageButton, pageNumberButton,
                onUpdate, updateInterval,
                onOpen, onClose);

        if (fillWith != null) {
            gui.fill(fillWith);
        }

        return gui;
    }

}
