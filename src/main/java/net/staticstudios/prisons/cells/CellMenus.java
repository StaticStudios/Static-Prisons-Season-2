package net.staticstudios.prisons.cells;

import net.staticstudios.gui.legacy.GUICreator;
import net.staticstudios.gui.legacy.GUIUtils;
import net.staticstudios.prisons.gui.MainMenus;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;

public class CellMenus extends GUIUtils {

    public static void openMenu(Player player, boolean fromCommand) {
        GUICreator c = new GUICreator(9, "Your Cell");

        if (Cell.getCell(player) == null)
            c.setItem(4, c.createButton(Material.NETHER_STAR, "&aCreate a cell", List.of("A cell is a personal space where you can", "store personal items and have creative freedom."), (p, t) -> Cell.createCell(p)));
        else {
            c.setItem(3, ench(c.createButton(Material.COMPASS, "&bWarp to your cell", List.of("A cell is a personal space where you can", "store personal items and have creative freedom."), (p, t) -> Cell.getCell(p).warpTo(p))));

            c.setItem(5, ench(c.createButton(Material.OAK_PLANKS, "&aBlock Shop", List.of("&7&oBuy blocks to customize your cell!"), (p, t) -> CellBlockShop.openMenu(p, 0, fromCommand))));

        }

        c.fill(createGrayPlaceHolder());

        if (!fromCommand) c.setOnCloseRun((p, t) -> MainMenus.open(p));
        c.open(player);
    }
}
