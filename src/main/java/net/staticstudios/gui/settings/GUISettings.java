package net.staticstudios.gui.settings;

/**
 * @author Sam (GitHub: <a href="https://github.com/Sammster10">Sam's GitHub</a>)
 */
@SuppressWarnings("unused")
public class GUISettings {
    /**
     * Allow/disallow players to put their own items into the GUI.
     * If this is enabled, then they will be given their items back when they close the GUI as long as the items have not been removed from the inventory.
     */
    private boolean allowPlayerItems = false;

    private boolean givePlayerItemsBack = true;

    private boolean allowDragItems = false;

    //Getters & Setters

    /**
     * Allow/disallow players to put their own items into the GUI.
     * If this is enabled, then they will be given their items back when they close the GUI as long as the items have not been removed from the inventory.
     */
    public boolean allowPlayerItems() {
        return allowPlayerItems;
    }

    /**
     * Allow/disallow players to put their own items into the GUI.
     * If this is enabled, then they will be given their items back when they close the GUI as long as the items have not been removed from the inventory.
     */
    public void allowPlayerItems(boolean allowPlayerItems) {
        this.allowPlayerItems = allowPlayerItems;
    }

    /**
     * Returns whether the player's items will be given back to them when they close the GUI.
     * @return True if the player's items should be given back to them when they close the GUI, false otherwise.
     */
    public boolean givePlayerItemsBack() {
        return givePlayerItemsBack;
    }

    /**
     * Allow/disallow the player's items to be given back to them when the GUI is being closed.
     * @param givePlayerItemsBack true if the player's items should be given back to them when they close the GUI, false otherwise.
     */
    public void givePlayerItemsBack(boolean givePlayerItemsBack) {
        this.givePlayerItemsBack = givePlayerItemsBack;
    }

    public boolean allowDragItems() {
        return allowDragItems;
    }

    public void allowDragItems(boolean allowDragItems) {
        this.allowDragItems = allowDragItems;
    }
}
