package net.staticstudios.prisons.pickaxe.gui;

import net.staticstudios.gui.legacy.GUICreator;
import net.staticstudios.gui.legacy.GUIUtils;
import net.staticstudios.prisons.gui.MainMenus;
import net.staticstudios.prisons.pickaxe.PrisonPickaxe;
import net.staticstudios.prisons.utils.PlayerUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class PickaxeMenus extends GUIUtils {

    public static void mainMenu(Player player) {
        GUICreator c = new GUICreator(27, "Pickaxes");

        c.setItem(11, ench(c.createButton(Material.PRISMARINE_CRYSTALS, "&d&lUpgrade Your Pickaxe!", List.of(
                "- Upgrade enchants",
                "- Manage abilities"
        ), (p, t) -> {
            selectPickaxe(p);
        })));


        c.setItem(15, ench(c.createButton(Material.DIAMOND_PICKAXE, "&b&lCreate A Pickaxe", List.of(
                "&oLost your pickaxe and need a new one?",
                "",
                "&bClick here to create a new pickaxe!"
        ), (p, t) -> {
            PlayerUtils.addToInventory(p, PrisonPickaxe.createNewPickaxe().getItem());
            p.closeInventory(); //Prevent spamming of this
        })));

        c.fill(createGrayPlaceHolder());
        c.open(player);
        c.setOnCloseRun((p, t) -> MainMenus.open(p));
    }

    public static void open(Player player, PrisonPickaxe pickaxe) {
        GUICreator c = new GUICreator(27, "Manage Your Pickaxe");

//        c.setItem(10, ench(c.createButton(Material.NETHER_STAR, "&a&lPickaxe Abilities", List.of(
//                "&oUnlock, upgrade, and activate abilities for your pickaxe!"
//        ), (p, t) -> {
//            AbilityMenus.mainMenu(p, pickaxe);
//        })));
        c.setItem(10, ench(c.createButton(Material.BARRIER, "&c&lComing Soon!", List.of(
                "&oUnlock, upgrade, and activate abilities for your pickaxe!"
        ))));

        c.setItem(13, c.createButton(Material.ENCHANTED_BOOK, "&d&lPickaxe Enchants", List.of(
                "&oUpgrade your pickaxe's enchants!"
        ), (p, t) -> EnchantMenus.mainMenu(p, pickaxe)));

        c.setItem(16, pickaxe.getItem());

        c.fill(createGrayPlaceHolder());
        c.open(player);
    }

    public static void selectPickaxe(Player player) {
        GUICreator c = new GUICreator(36, "Select A Pickaxe To Upgrade");
        for (ItemStack item : player.getInventory().getContents()) {
            if (PrisonPickaxe.checkIsPrisonPickaxe(item)) {
                c.addItem(c.createButton(item, (p, t) -> {
                    open(p, PrisonPickaxe.fromItem(item));
                }));
            }
        }
        c.fill(createGrayPlaceHolder());
        c.open(player);
    }
}
