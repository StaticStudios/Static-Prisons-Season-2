package net.staticstudios.prisons.cells;

import net.staticstudios.gui.GUICreator;
import net.staticstudios.gui.GUIUtils;
import net.staticstudios.prisons.gui.newGui.MainMenus;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;

public class CellMenus extends GUIUtils {

    public static void openMenu(Player player, boolean fromCommand) {
        GUICreator c = new GUICreator(9, "Your Cell");

        if (Cell.getCell(player) == null) c.setItem(4, c.createButton(Material.NETHER_STAR, "&aCreate a cell", List.of("A cell is a personal space where you can", "store personal items and have creative freedom."), (p, t) -> Cell.createCell(p)));
        else c.setItem(4, ench(c.createButton(Material.COMPASS, "&bWarp to your cell", List.of("A cell is a personal space where you can", "store personal items and have creative freedom."), (p, t) -> Cell.getCell(p).warpTo(p))));

        c.fill(createGrayPlaceHolder());

        if (!fromCommand) c.setOnCloseRun((p, t) -> MainMenus.open(p));
        c.open(player);
    }
}
