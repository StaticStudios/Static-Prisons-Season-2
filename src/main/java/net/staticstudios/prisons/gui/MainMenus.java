package net.staticstudios.prisons.gui;

import net.staticstudios.gui.GUICreator;
import net.staticstudios.gui.GUIUtils;
import net.staticstudios.prisons.auctionHouse.AuctionHouseMenus;
import net.staticstudios.prisons.backpacks.BackpackMenus;
import net.staticstudios.prisons.cells.CellMenus;
import net.staticstudios.prisons.chat.ChatTagMenus;
import net.staticstudios.prisons.gambling.GamblingMenus;
import net.staticstudios.prisons.gangs.GangMenus;
import net.staticstudios.prisons.levelup.RankUpMenus;
import net.staticstudios.prisons.levelup.prestige.PrestigeMenus;
import net.staticstudios.prisons.mines.WarpMenus;
import net.staticstudios.prisons.pickaxe.PickaxeMenus;
import net.staticstudios.prisons.privateMines.PrivateMineMenus;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;

public class MainMenus extends GUIUtils {

    public static void open(Player player) {
        GUICreator c = new GUICreator(54, "Static Prisons");
        c.setItems(
                createGrayPlaceHolder(),
                createGrayPlaceHolder(),
                createGrayPlaceHolder(),
                createGrayPlaceHolder(),
                createGrayPlaceHolder(),
                createGrayPlaceHolder(),
                createGrayPlaceHolder(),
                createGrayPlaceHolder(),
                createGrayPlaceHolder(),

                createGrayPlaceHolder(),
                ench(c.createButton(Material.COMPASS, "&a&lWarps", List.of("&7&oGo somewhere!"), (p, t) -> {
                    WarpMenus.mainMenu(player);
                })),
                ench(c.createButton(Material.DIAMOND_PICKAXE, "&b&lPickaxes", List.of("&7&oManage your pickaxe!"), (p, t) -> {
                    PickaxeMenus.selectPickaxe(player);
                })),
                ench(c.createButton(Material.CLOCK, "&a&lAuction House", List.of("&7&oBuy items sold by other players!", "&7&oType \"/auc had <price>\" to sell an item!"), (p, t) -> {
                    AuctionHouseMenus.openMenu(player, 0);
                })),
                ench(c.createButton(Material.DEEPSLATE_DIAMOND_ORE, "&b&lPrivate Mines", List.of("&7&oMine in your personal mine!"), (p, t) -> {
                    PrivateMineMenus.open(player, false);
                })),
                ench(c.createButton(Material.SUNFLOWER, "&e&lCasino", List.of("&7&oTest your luck!"), (p, t) -> {
                    GamblingMenus.openMain(player);
                })),
                ench(c.createButton(Material.IRON_BARS, "&8&lCells", List.of("&7&oCustomize your personal space!"), (p, t) -> {
                    CellMenus.openMenu(player, false);
                })),
                ench(c.createButton(Material.IRON_HELMET, "&6&lGangs", List.of("&7&oManage your team!"), (p, t) -> {
                    GangMenus.open(player, false);
                })),
                createGrayPlaceHolder(),

                createGrayPlaceHolder(),
                createLightGrayPlaceHolder(),
                createLightGrayPlaceHolder(),
                ench(c.createButton(Material.PRISMARINE_CRYSTALS, "&b&lRewards", List.of("&7&oView rewards you've received from playing!"), (p, t) -> {
                    RewardsMenus.open(player);
                })),
                ench(c.createButton(Material.NAME_TAG, "&b&lChat Tags", List.of("&7&oCustomize how you look!"), (p, t) -> {
                    ChatTagMenus.manageTags(player);
                })),
                createLightBluePlaceHolder(),
                createLightGrayPlaceHolder(),
                createLightGrayPlaceHolder(),
                createGrayPlaceHolder(),

                createGrayPlaceHolder(),
                c.createButtonOfPlayerSkull(player, "&a&lYour stats", List.of("&7&oView your stats!"), (p, t) -> {
                    StatsMenus.viewStats(p, p.getUniqueId());
                }),
                createLightGrayPlaceHolder(),
                createLightGrayPlaceHolder(),
                createLightGrayPlaceHolder(),
                createLightGrayPlaceHolder(),
                createLightGrayPlaceHolder(),
                c.createButton(Material.ENDER_CHEST, "&e&lBackpack", List.of("&7&oUpgrade your backpack!"), (p, t) -> {
                    BackpackMenus.mainMenu(player);
                }),
                createGrayPlaceHolder(),

                createGrayPlaceHolder(),
                createGrayPlaceHolder(),
                createGrayPlaceHolder(),
                createGrayPlaceHolder(),
                createGrayPlaceHolder(),
                createGrayPlaceHolder(),
                createGrayPlaceHolder(),
                createGrayPlaceHolder(),
                createGrayPlaceHolder(),

                ench(c.createButton(Material.EMERALD, "&a&lRank Up", List.of("&7&oUnlock new mines!"), (p, t) -> {
                    RankUpMenus.open(p, false);
                })),
                createLightGrayPlaceHolder(),
                createLightGrayPlaceHolder(),
                createLightGrayPlaceHolder(),
                ench(c.createButton(Material.GUNPOWDER, "&c&lSettings!", List.of("&7&oChange the way you play!"), (p, t) -> {
                    SettingsMenus.open(p, false);
                })),
                createLightGrayPlaceHolder(),
                createLightGrayPlaceHolder(),
                createLightGrayPlaceHolder(),
                ench(c.createButton(Material.NETHER_STAR, "&d&lPrestige", List.of("&7&oRestart and do it all over again!", "With a slight bonus of course..."), (p, t) -> {
                    PrestigeMenus.open(p, false);
                }))
        );
        c.open(player);
    }
}
