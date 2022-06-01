package net.staticstudios.prisons.gui.newGui;

import net.staticstudios.gui.GUICreator;
import net.staticstudios.gui.GUIUtils;
import org.bukkit.entity.Player;

public class MainMenus extends GUIUtils {

    public static void open(Player player) {
        GUICreator c = new GUICreator(54, "Static Prisons");
        c.open(player);
    }
}
