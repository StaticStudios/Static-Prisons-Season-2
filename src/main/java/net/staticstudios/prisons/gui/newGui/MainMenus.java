package net.staticstudios.prisons.gui.newGui;

import net.staticstudios.gui.GUICreator;
import net.staticstudios.gui.GUIUtils;
import net.staticstudios.prisons.data.dataHandling.PlayerData;
import net.staticstudios.prisons.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.math.BigInteger;
import java.util.List;

public class MainMenus extends GUIUtils {

    public static void open(Player player) {
        GUICreator c = new GUICreator(54, "Static Prisons");
        c.open(player);
    }
}
