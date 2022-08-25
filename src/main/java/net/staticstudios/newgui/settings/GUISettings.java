package net.staticstudios.newgui.settings;

/**
 * @author Sam (GitHub: <a href="https://github.com/Sammster10">Sam's GitHub</a>)
 */
public class GUISettings {
    /**
     * Allow/disallow players to put their own items into the GUI.
     * If this is enabled, then they will be given their items back when they close the GUI as long as the items have not been removed from the inventory.
     */
    private boolean allowPlayerItems = false;

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


}
