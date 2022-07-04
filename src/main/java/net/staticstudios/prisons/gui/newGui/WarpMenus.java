package net.staticstudios.prisons.gui.newGui;

import net.staticstudios.gui.GUICreator;
import net.staticstudios.gui.GUIRunnable;
import net.staticstudios.gui.GUIUtils;
import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.misc.Warps;
import net.staticstudios.prisons.utils.Constants;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.math.BigInteger;
import java.util.List;

public class WarpMenus extends GUIUtils {

    public static void mainMenu(Player player) {
        GUICreator c = new GUICreator(9, "Warps");
        c.setItem(0, createGrayPlaceHolder());
        c.setItem(1, createGrayPlaceHolder());
        c.addItem(ench(c.createButton(Material.COMPASS, "&a&lSpawn", List.of("Warp to spawn"), (p, t) -> Warps.warpToSpawn(p))));
        c.addItem(ench(c.createButton(Material.NETHERITE_PICKAXE, "&e&lMines", List.of("Warp to a mine"), (p, t) -> minesMenuAZ(p))));
        c.addItem(ench(c.createButton(Material.DIAMOND_SWORD, "&c&lPvP", List.of("Warp to the PvP arena"), (p, t) -> Warps.warpToPvP(p))));
        c.addItem(ench(c.createButton(Material.TRIPWIRE_HOOK, "&b&lCrates", List.of("Warp to crates"), (p, t) -> Warps.warpToCrates(p))));
        c.addItem(ench(c.createButton(Material.DIAMOND, "&6&lLeaderboards", List.of("Warp to leaderboards"), (p, t) -> Warps.warpToLeaderboards(p))));
        c.fill(createGrayPlaceHolder());
        c.open(player);
        c.setOnCloseRun((p, t) -> MainMenus.open(p));
    }

    public static void minesMenuAZ(Player player) {
        PlayerData playerData = new PlayerData(player);
        GUICreator c = new GUICreator(36, "Warp to a mine (A-Z)");
        c.setItems(
                createMineWarp(c, playerData.getMineRank() >= 0, "&c&lMine A", "&a&lMine A",
                        new String[]{"&cYou do not have this unlocked!", "", "&7&oSize: 69x98x69"}, new String[]{"&aClick to warp!", "", "&7&oSize: 69x98x69"},
                        Material.STONE, Material.STONE, (p, t) -> {
                            p.sendMessage(ChatColor.RED + "You do not have this mine unlocked!");
                        }, (p, t) -> {
                            Warps.warpToMine(p, 0);
                        }, true),
                createMineWarp(c, playerData.getMineRank() >= 1, "&c&lMine B", "&a&lMine B",
                        new String[]{"&cYou do not have this unlocked!", "", "&7&oSize: 69x98x69"}, new String[]{"&aClick to warp!", "", "&7&oSize: 69x98x69"},
                        Material.STONE, Material.STONE, (p, t) -> {
                            p.sendMessage(ChatColor.RED + "You do not have this mine unlocked!");
                        }, (p, t) -> {
                            Warps.warpToMine(p, 1);
                        }, true),
                createMineWarp(c, playerData.getMineRank() >= 2, "&c&lMine C", "&a&lMine C",
                        new String[]{"&cYou do not have this unlocked!", "", "&7&oSize: 69x98x69"}, new String[]{"&aClick to warp!", "", "&7&oSize: 69x98x69"},
                        Material.STONE, Material.STONE, (p, t) -> {
                            p.sendMessage(ChatColor.RED + "You do not have this mine unlocked!");
                        }, (p, t) -> {
                            Warps.warpToMine(p, 2);
                        }, true),
                createMineWarp(c, playerData.getMineRank() >= 3, "&c&lMine D", "&a&lMine D",
                        new String[]{"&cYou do not have this unlocked!", "", "&7&oSize: 69x98x69"}, new String[]{"&aClick to warp!", "", "&7&oSize: 69x98x69"},
                        Material.COBBLESTONE, Material.COBBLESTONE, (p, t) -> {
                            p.sendMessage(ChatColor.RED + "You do not have this mine unlocked!");
                        }, (p, t) -> {
                            Warps.warpToMine(p, 3);
                        }, true),
                createMineWarp(c, playerData.getMineRank() >= 4, "&c&lMine E", "&a&lMine E",
                        new String[]{"&cYou do not have this unlocked!", "", "&7&oSize: 69x98x69"}, new String[]{"&aClick to warp!", "", "&7&oSize: 69x98x69"},
                        Material.COBBLESTONE, Material.COBBLESTONE, (p, t) -> {
                            p.sendMessage(ChatColor.RED + "You do not have this mine unlocked!");
                        }, (p, t) -> {
                            Warps.warpToMine(p, 4);
                        }, true),
                createMineWarp(c, playerData.getMineRank() >= 5, "&c&lMine F", "&a&lMine F",
                        new String[]{"&cYou do not have this unlocked!", "", "&7&oSize: 69x98x69"}, new String[]{"&aClick to warp!", "", "&7&oSize: 69x98x69"},
                        Material.COBBLESTONE, Material.COBBLESTONE, (p, t) -> {
                            p.sendMessage(ChatColor.RED + "You do not have this mine unlocked!");
                        }, (p, t) -> {
                            Warps.warpToMine(p, 5);
                        }, true),
                createMineWarp(c, playerData.getMineRank() >= 6, "&c&lMine G", "&a&lMine G",
                        new String[]{"&cYou do not have this unlocked!", "", "&7&oSize: 69x98x69"}, new String[]{"&aClick to warp!", "", "&7&oSize: 69x98x69"},
                        Material.COAL_ORE, Material.COAL_ORE, (p, t) -> {
                            p.sendMessage(ChatColor.RED + "You do not have this mine unlocked!");
                        }, (p, t) -> {
                            Warps.warpToMine(p, 6);
                        }, true),
                createMineWarp(c, playerData.getMineRank() >= 7, "&c&lMine H", "&a&lMine H",
                        new String[]{"&cYou do not have this unlocked!", "", "&7&oSize: 69x98x69"}, new String[]{"&aClick to warp!", "", "&7&oSize: 69x98x69"},
                        Material.COAL_ORE, Material.COAL_ORE, (p, t) -> {
                            p.sendMessage(ChatColor.RED + "You do not have this mine unlocked!");
                        }, (p, t) -> {
                            Warps.warpToMine(p, 7);
                        }, true),
                createMineWarp(c, playerData.getMineRank() >= 8, "&c&lMine I", "&a&lMine I",
                        new String[]{"&cYou do not have this unlocked!", "", "&7&oSize: 69x98x69"}, new String[]{"&aClick to warp!", "", "&7&oSize: 69x98x69"},
                        Material.COAL_ORE, Material.COAL_ORE, (p, t) -> {
                            p.sendMessage(ChatColor.RED + "You do not have this mine unlocked!");
                        }, (p, t) -> {
                            Warps.warpToMine(p, 8);
                        }, true),
                createMineWarp(c, playerData.getMineRank() >= 9, "&c&lMine J", "&a&lMine J",
                        new String[]{"&cYou do not have this unlocked!", "", "&7&oSize: 69x98x69"}, new String[]{"&aClick to warp!", "", "&7&oSize: 69x98x69"},
                        Material.IRON_ORE, Material.IRON_ORE, (p, t) -> {
                            p.sendMessage(ChatColor.RED + "You do not have this mine unlocked!");
                        }, (p, t) -> {
                            Warps.warpToMine(p, 9);
                        }, true),
                createMineWarp(c, playerData.getMineRank() >= 10, "&c&lMine K", "&a&lMine K",
                        new String[]{"&cYou do not have this unlocked!", "", "&7&oSize: 69x98x69"}, new String[]{"&aClick to warp!", "", "&7&oSize: 69x98x69"},
                        Material.IRON_ORE, Material.IRON_ORE, (p, t) -> {
                            p.sendMessage(ChatColor.RED + "You do not have this mine unlocked!");
                        }, (p, t) -> {
                            Warps.warpToMine(p, 10);
                        }, true),
                createMineWarp(c, playerData.getMineRank() >= 11, "&c&lMine L", "&a&lMine L",
                        new String[]{"&cYou do not have this unlocked!", "", "&7&oSize: 69x98x69"}, new String[]{"&aClick to warp!", "", "&7&oSize: 69x98x69"},
                        Material.IRON_ORE, Material.IRON_ORE, (p, t) -> {
                            p.sendMessage(ChatColor.RED + "You do not have this mine unlocked!");
                        }, (p, t) -> {
                            Warps.warpToMine(p, 11);
                        }, true),
                createMineWarp(c, playerData.getMineRank() >= 12, "&c&lMine M", "&a&lMine M",
                        new String[]{"&cYou do not have this unlocked!", "", "&7&oSize: 69x98x69"}, new String[]{"&aClick to warp!", "", "&7&oSize: 69x98x69"},
                        Material.GOLD_ORE, Material.GOLD_ORE, (p, t) -> {
                            p.sendMessage(ChatColor.RED + "You do not have this mine unlocked!");
                        }, (p, t) -> {
                            Warps.warpToMine(p, 12);
                        }, true),
                createMineWarp(c, playerData.getMineRank() >= 13, "&c&lMine N", "&a&lMine N",
                        new String[]{"&cYou do not have this unlocked!", "", "&7&oSize: 69x98x69"}, new String[]{"&aClick to warp!", "", "&7&oSize: 69x98x69"},
                        Material.GOLD_ORE, Material.GOLD_ORE, (p, t) -> {
                            p.sendMessage(ChatColor.RED + "You do not have this mine unlocked!");
                        }, (p, t) -> {
                            Warps.warpToMine(p, 13);
                        }, true),
                createMineWarp(c, playerData.getMineRank() >= 14, "&c&lMine O", "&a&lMine O",
                        new String[]{"&cYou do not have this unlocked!", "", "&7&oSize: 69x98x69"}, new String[]{"&aClick to warp!", "", "&7&oSize: 69x98x69"},
                        Material.GOLD_ORE, Material.GOLD_ORE, (p, t) -> {
                            p.sendMessage(ChatColor.RED + "You do not have this mine unlocked!");
                        }, (p, t) -> {
                            Warps.warpToMine(p, 14);
                        }, true),
                createMineWarp(c, playerData.getMineRank() >= 15, "&c&lMine P", "&a&lMine P",
                        new String[]{"&cYou do not have this unlocked!", "", "&7&oSize: 69x98x69"}, new String[]{"&aClick to warp!", "", "&7&oSize: 69x98x69"},
                        Material.LAPIS_ORE, Material.LAPIS_ORE, (p, t) -> {
                            p.sendMessage(ChatColor.RED + "You do not have this mine unlocked!");
                        }, (p, t) -> {
                            Warps.warpToMine(p, 15);
                        }, true),
                createMineWarp(c, playerData.getMineRank() >= 16, "&c&lMine Q", "&a&lMine Q",
                        new String[]{"&cYou do not have this unlocked!", "", "&7&oSize: 69x98x69"}, new String[]{"&aClick to warp!", "", "&7&oSize: 69x98x69"},
                        Material.LAPIS_ORE, Material.LAPIS_ORE, (p, t) -> {
                            p.sendMessage(ChatColor.RED + "You do not have this mine unlocked!");
                        }, (p, t) -> {
                            Warps.warpToMine(p, 16);
                        }, true),
                createMineWarp(c, playerData.getMineRank() >= 17, "&c&lMine R", "&a&lMine R",
                        new String[]{"&cYou do not have this unlocked!", "", "&7&oSize: 69x98x69"}, new String[]{"&aClick to warp!", "", "&7&oSize: 69x98x69"},
                        Material.LAPIS_ORE, Material.LAPIS_ORE, (p, t) -> {
                            p.sendMessage(ChatColor.RED + "You do not have this mine unlocked!");
                        }, (p, t) -> {
                            Warps.warpToMine(p, 17);
                        }, true),
                createMineWarp(c, playerData.getMineRank() >= 18, "&c&lMine S", "&a&lMine S",
                        new String[]{"&cYou do not have this unlocked!", "", "&7&oSize: 69x98x69"}, new String[]{"&aClick to warp!", "", "&7&oSize: 69x98x69"},
                        Material.REDSTONE_ORE, Material.REDSTONE_ORE, (p, t) -> {
                            p.sendMessage(ChatColor.RED + "You do not have this mine unlocked!");
                        }, (p, t) -> {
                            Warps.warpToMine(p, 18);
                        }, true),
                createMineWarp(c, playerData.getMineRank() >= 19, "&c&lMine T", "&a&lMine T",
                        new String[]{"&cYou do not have this unlocked!", "", "&7&oSize: 69x98x69"}, new String[]{"&aClick to warp!", "", "&7&oSize: 69x98x69"},
                        Material.REDSTONE_ORE, Material.REDSTONE_ORE, (p, t) -> {
                            p.sendMessage(ChatColor.RED + "You do not have this mine unlocked!");
                        }, (p, t) -> {
                            Warps.warpToMine(p, 19);
                        }, true),
                createMineWarp(c, playerData.getMineRank() >= 20, "&c&lMine U", "&a&lMine U",
                        new String[]{"&cYou do not have this unlocked!", "", "&7&oSize: 69x98x69"}, new String[]{"&aClick to warp!", "", "&7&oSize: 69x98x69"},
                        Material.REDSTONE_ORE, Material.REDSTONE_ORE, (p, t) -> {
                            p.sendMessage(ChatColor.RED + "You do not have this mine unlocked!");
                        }, (p, t) -> {
                            Warps.warpToMine(p, 20);
                        }, true),
                createMineWarp(c, playerData.getMineRank() >= 21, "&c&lMine V", "&a&lMine V",
                        new String[]{"&cYou do not have this unlocked!", "", "&7&oSize: 69x98x69"}, new String[]{"&aClick to warp!", "", "&7&oSize: 69x98x69"},
                        Material.DIAMOND_ORE, Material.DIAMOND_ORE, (p, t) -> {
                            p.sendMessage(ChatColor.RED + "You do not have this mine unlocked!");
                        }, (p, t) -> {
                            Warps.warpToMine(p, 21);
                        }, true),
                createMineWarp(c, playerData.getMineRank() >= 22, "&c&lMine W", "&a&lMine W",
                        new String[]{"&cYou do not have this unlocked!", "", "&7&oSize: 69x98x69"}, new String[]{"&aClick to warp!", "", "&7&oSize: 69x98x69"},
                        Material.DIAMOND_ORE, Material.DIAMOND_ORE, (p, t) -> {
                            p.sendMessage(ChatColor.RED + "You do not have this mine unlocked!");
                        }, (p, t) -> {
                            Warps.warpToMine(p, 22);
                        }, true),
                createMineWarp(c, playerData.getMineRank() >= 23, "&c&lMine X", "&a&lMine X",
                        new String[]{"&cYou do not have this unlocked!", "", "&7&oSize: 69x98x69"}, new String[]{"&aClick to warp!", "", "&7&oSize: 69x98x69"},
                        Material.DIAMOND_ORE, Material.DIAMOND_ORE, (p, t) -> {
                            p.sendMessage(ChatColor.RED + "You do not have this mine unlocked!");
                        }, (p, t) -> {
                            Warps.warpToMine(p, 23);
                        }, true),
                createMineWarp(c, playerData.getMineRank() >= 24, "&c&lMine Y", "&a&lMine Y",
                        new String[]{"&cYou do not have this unlocked!", "", "&7&oSize: 69x98x69"}, new String[]{"&aClick to warp!", "", "&7&oSize: 69x98x69"},
                        Material.EMERALD_ORE, Material.EMERALD_ORE, (p, t) -> {
                            p.sendMessage(ChatColor.RED + "You do not have this mine unlocked!");
                        }, (p, t) -> {
                            Warps.warpToMine(p, 24);
                        }, true),
                createMineWarp(c, playerData.getMineRank() >= 25, "&c&lMine Z", "&a&lMine Z",
                        new String[]{"&cYou do not have this unlocked!", "", "&7&oSize: 69x98x69"}, new String[]{"&aClick to warp!", "", "&7&oSize: 69x98x69"},
                        Material.EMERALD_ORE, Material.EMERALD_ORE, (p, t) -> {
                            p.sendMessage(ChatColor.RED + "You do not have this mine unlocked!");
                        }, (p, t) -> {
                            Warps.warpToMine(p, 25);
                        }, true),
                ench(c.createButton(Material.ENCHANTING_TABLE, "&d&lEvent Mine", List.of("&bClick to warp!", "", "&7&o120& Token multiplier!"), (p, t) -> Warps.warEventMine(p))),
                createGrayPlaceHolder(),
                createGrayPlaceHolder(),
                createGrayPlaceHolder(),
                ench(c.createButton(Material.NETHER_STAR, "&d&lPrestige Mines", List.of("&bClick to view prestige mines", "", "&7&oWarp to a prestige mine"), (p, t) -> {
                    minesMenuPrestige(p);
                })),
                createGrayPlaceHolder(),
                ench(c.createButton(Material.BLAZE_POWDER, "&c&lRanked Mines", List.of("&bClick to view ranked mines", "", "&7&oWarp to a ranked mine"), (p, t) -> {
                    minesMenuRanked(p);
                })),
                createGrayPlaceHolder(),
                createGrayPlaceHolder(),
                createGrayPlaceHolder()

                );


        c.setOnCloseRun((p, t) -> mainMenu(p));
        c.open(player);
    }
    public static void minesMenuPrestige(Player player) {
        PlayerData playerData = new PlayerData(player);
        GUICreator c = new GUICreator(27, "Warp to a mine (Prestige mines)");
        c.setItems(
                createMineWarp(c, playerData.getPrestige().compareTo(BigInteger.valueOf(Constants.PRESTIGE_MINE_REQUIREMENTS[0])) > -1, "&c&lPrestige Mine #1", "&d&lPrestige Mine #1",
                        new String[]{"&bRequires " + PrisonUtils.addCommasToNumber(Constants.PRESTIGE_MINE_REQUIREMENTS[0]) + " prestiges", "", "&cYou do not have this unlocked!", "", "&7&oSize: 69x98x69"},
                        new String[]{"&bRequires " + PrisonUtils.addCommasToNumber(Constants.PRESTIGE_MINE_REQUIREMENTS[0]) + " prestiges", "", "&aClick to warp!", "", "&7&oSize: 69x98x69"},
                        Material.COAL_BLOCK, Material.COAL_BLOCK, (p, t) -> {
                            p.sendMessage(ChatColor.RED + "You do not have this mine unlocked!");
                        }, (p, t) -> {
                            Warps.warpToPrestigeMine(p, 0);
                        }, true),
                createMineWarp(c, playerData.getPrestige().compareTo(BigInteger.valueOf(Constants.PRESTIGE_MINE_REQUIREMENTS[1])) > -1, "&c&lPrestige Mine #2", "&d&lPrestige Mine #2",
                        new String[]{"&bRequires " + PrisonUtils.addCommasToNumber(Constants.PRESTIGE_MINE_REQUIREMENTS[1]) + " prestiges", "", "&cYou do not have this unlocked!", "", "&7&oSize: 69x98x69"},
                        new String[]{"&bRequires " + PrisonUtils.addCommasToNumber(Constants.PRESTIGE_MINE_REQUIREMENTS[1]) + " prestiges", "", "&aClick to warp!", "", "&7&oSize: 69x98x69"},
                        Material.IRON_BLOCK, Material.IRON_BLOCK, (p, t) -> {
                            p.sendMessage(ChatColor.RED + "You do not have this mine unlocked!");
                        }, (p, t) -> {
                            Warps.warpToPrestigeMine(p, 1);
                        }, true),
                createMineWarp(c, playerData.getPrestige().compareTo(BigInteger.valueOf(Constants.PRESTIGE_MINE_REQUIREMENTS[2])) > -1, "&c&lPrestige Mine #3", "&d&lPrestige Mine #3",
                        new String[]{"&bRequires " + PrisonUtils.addCommasToNumber(Constants.PRESTIGE_MINE_REQUIREMENTS[2]) + " prestiges", "", "&cYou do not have this unlocked!", "", "&7&oSize: 69x98x69"},
                        new String[]{"&bRequires " + PrisonUtils.addCommasToNumber(Constants.PRESTIGE_MINE_REQUIREMENTS[2]) + " prestiges", "", "&aClick to warp!", "", "&7&oSize: 69x98x69"},
                        Material.GOLD_BLOCK, Material.GOLD_BLOCK, (p, t) -> {
                            p.sendMessage(ChatColor.RED + "You do not have this mine unlocked!");
                        }, (p, t) -> {
                            Warps.warpToPrestigeMine(p, 2);
                        }, true),
                createMineWarp(c, playerData.getPrestige().compareTo(BigInteger.valueOf(Constants.PRESTIGE_MINE_REQUIREMENTS[3])) > -1, "&c&lPrestige Mine #4", "&d&lPrestige Mine #4",
                        new String[]{"&bRequires " + PrisonUtils.addCommasToNumber(Constants.PRESTIGE_MINE_REQUIREMENTS[3]) + " prestiges", "", "&cYou do not have this unlocked!", "", "&7&oSize: 69x98x69"},
                        new String[]{"&bRequires " + PrisonUtils.addCommasToNumber(Constants.PRESTIGE_MINE_REQUIREMENTS[3]) + " prestiges", "", "&aClick to warp!", "", "&7&oSize: 69x98x69"},
                        Material.LAPIS_BLOCK, Material.LAPIS_BLOCK, (p, t) -> {
                            p.sendMessage(ChatColor.RED + "You do not have this mine unlocked!");
                        }, (p, t) -> {
                            Warps.warpToPrestigeMine(p, 3);
                        }, true),
                createMineWarp(c, playerData.getPrestige().compareTo(BigInteger.valueOf(Constants.PRESTIGE_MINE_REQUIREMENTS[4])) > -1, "&c&lPrestige Mine #5", "&d&lPrestige Mine #5",
                        new String[]{"&bRequires " + PrisonUtils.addCommasToNumber(Constants.PRESTIGE_MINE_REQUIREMENTS[4]) + " prestiges", "", "&cYou do not have this unlocked!", "", "&7&oSize: 69x98x69"},
                        new String[]{"&bRequires " + PrisonUtils.addCommasToNumber(Constants.PRESTIGE_MINE_REQUIREMENTS[4]) + " prestiges", "", "&aClick to warp!", "", "&7&oSize: 69x98x69"},
                        Material.REDSTONE_BLOCK, Material.REDSTONE_BLOCK, (p, t) -> {
                            p.sendMessage(ChatColor.RED + "You do not have this mine unlocked!");
                        }, (p, t) -> {
                            Warps.warpToPrestigeMine(p, 4);
                        }, true),
                createMineWarp(c, playerData.getPrestige().compareTo(BigInteger.valueOf(Constants.PRESTIGE_MINE_REQUIREMENTS[5])) > -1, "&c&lPrestige Mine #6", "&d&lPrestige Mine #6",
                        new String[]{"&bRequires " + PrisonUtils.addCommasToNumber(Constants.PRESTIGE_MINE_REQUIREMENTS[5]) + " prestiges", "", "&cYou do not have this unlocked!", "", "&7&oSize: 89x98x89"},
                        new String[]{"&bRequires " + PrisonUtils.addCommasToNumber(Constants.PRESTIGE_MINE_REQUIREMENTS[5]) + " prestiges", "", "&aClick to warp!", "", "&7&oSize: 89x98x89"},
                        Material.DIAMOND_BLOCK, Material.DIAMOND_BLOCK, (p, t) -> {
                            p.sendMessage(ChatColor.RED + "You do not have this mine unlocked!");
                        }, (p, t) -> {
                            Warps.warpToPrestigeMine(p, 5);
                        }, true),
                createMineWarp(c, playerData.getPrestige().compareTo(BigInteger.valueOf(Constants.PRESTIGE_MINE_REQUIREMENTS[6])) > -1, "&c&lPrestige Mine #7", "&d&lPrestige Mine #7",
                        new String[]{"&bRequires " + PrisonUtils.addCommasToNumber(Constants.PRESTIGE_MINE_REQUIREMENTS[6]) + " prestiges", "", "&cYou do not have this unlocked!", "", "&7&oSize: 89x98x89"},
                        new String[]{"&bRequires " + PrisonUtils.addCommasToNumber(Constants.PRESTIGE_MINE_REQUIREMENTS[6]) + " prestiges", "", "&aClick to warp!", "", "&7&oSize: 89x98x89"},
                        Material.EMERALD_BLOCK, Material.EMERALD_BLOCK, (p, t) -> {
                            p.sendMessage(ChatColor.RED + "You do not have this mine unlocked!");
                        }, (p, t) -> {
                            Warps.warpToPrestigeMine(p, 6);
                        }, true),
                createMineWarp(c, playerData.getPrestige().compareTo(BigInteger.valueOf(Constants.PRESTIGE_MINE_REQUIREMENTS[7])) > -1, "&c&lPrestige Mine #8", "&d&lPrestige Mine #8",
                        new String[]{"&bRequires " + PrisonUtils.addCommasToNumber(Constants.PRESTIGE_MINE_REQUIREMENTS[7]) + " prestiges", "", "&cYou do not have this unlocked!", "", "&7&oSize: 89x98x89"},
                        new String[]{"&bRequires " + PrisonUtils.addCommasToNumber(Constants.PRESTIGE_MINE_REQUIREMENTS[7]) + " prestiges", "", "&aClick to warp!", "", "&7&oSize: 89x98x89"},
                        Material.NETHERRACK, Material.NETHERRACK, (p, t) -> {
                            p.sendMessage(ChatColor.RED + "You do not have this mine unlocked!");
                        }, (p, t) -> {
                            Warps.warpToPrestigeMine(p, 7);
                        }, true),
                createMineWarp(c, playerData.getPrestige().compareTo(BigInteger.valueOf(Constants.PRESTIGE_MINE_REQUIREMENTS[8])) > -1, "&c&lPrestige Mine #9", "&d&lPrestige Mine #9",
                        new String[]{"&bRequires " + PrisonUtils.addCommasToNumber(Constants.PRESTIGE_MINE_REQUIREMENTS[8]) + " prestiges", "", "&cYou do not have this unlocked!", "", "&7&oSize: 89x98x89"},
                        new String[]{"&bRequires " + PrisonUtils.addCommasToNumber(Constants.PRESTIGE_MINE_REQUIREMENTS[8]) + " prestiges", "", "&aClick to warp!", "", "&7&oSize: 89x98x89"},
                        Material.NETHER_BRICKS, Material.NETHER_BRICKS, (p, t) -> {
                            p.sendMessage(ChatColor.RED + "You do not have this mine unlocked!");
                        }, (p, t) -> {
                            Warps.warpToPrestigeMine(p, 8);
                        }, true),
                createMineWarp(c, playerData.getPrestige().compareTo(BigInteger.valueOf(Constants.PRESTIGE_MINE_REQUIREMENTS[9])) > -1, "&c&lPrestige Mine #10", "&d&lPrestige Mine #10",
                        new String[]{"&bRequires " + PrisonUtils.addCommasToNumber(Constants.PRESTIGE_MINE_REQUIREMENTS[9]) + " prestiges", "", "&cYou do not have this unlocked!", "", "&7&oSize: 89x98x89"},
                        new String[]{"&bRequires " + PrisonUtils.addCommasToNumber(Constants.PRESTIGE_MINE_REQUIREMENTS[9]) + " prestiges", "", "&aClick to warp!", "", "&7&oSize: 89x98x89"},
                        Material.QUARTZ_BLOCK, Material.QUARTZ_BLOCK, (p, t) -> {
                            p.sendMessage(ChatColor.RED + "You do not have this mine unlocked!");
                        }, (p, t) -> {
                            Warps.warpToPrestigeMine(p, 9);
                        }, true),
                createMineWarp(c, playerData.getPrestige().compareTo(BigInteger.valueOf(Constants.PRESTIGE_MINE_REQUIREMENTS[10])) > -1, "&c&lPrestige Mine #11", "&d&lPrestige Mine #11",
                        new String[]{"&bRequires " + PrisonUtils.addCommasToNumber(Constants.PRESTIGE_MINE_REQUIREMENTS[10]) + " prestiges", "", "&cYou do not have this unlocked!", "", "&7&oSize: 109x98x109"},
                        new String[]{"&bRequires " + PrisonUtils.addCommasToNumber(Constants.PRESTIGE_MINE_REQUIREMENTS[10]) + " prestiges", "", "&aClick to warp!", "", "&7&oSize: 109x98x109"},
                        Material.END_STONE, Material.END_STONE, (p, t) -> {
                            p.sendMessage(ChatColor.RED + "You do not have this mine unlocked!");
                        }, (p, t) -> {
                            Warps.warpToPrestigeMine(p, 10);
                        }, true),
                createMineWarp(c, playerData.getPrestige().compareTo(BigInteger.valueOf(Constants.PRESTIGE_MINE_REQUIREMENTS[11])) > -1, "&c&lPrestige Mine #12", "&d&lPrestige Mine #12",
                        new String[]{"&bRequires " + PrisonUtils.addCommasToNumber(Constants.PRESTIGE_MINE_REQUIREMENTS[11]) + " prestiges", "", "&cYou do not have this unlocked!", "", "&7&oSize: 109x98x109"},
                        new String[]{"&bRequires " + PrisonUtils.addCommasToNumber(Constants.PRESTIGE_MINE_REQUIREMENTS[11]) + " prestiges", "", "&aClick to warp!", "", "&7&oSize: 109x98x109"},
                        Material.OBSIDIAN, Material.OBSIDIAN, (p, t) -> {
                            p.sendMessage(ChatColor.RED + "You do not have this mine unlocked!");
                        }, (p, t) -> {
                            Warps.warpToPrestigeMine(p, 11);
                        }, true),
                createMineWarp(c, playerData.getPrestige().compareTo(BigInteger.valueOf(Constants.PRESTIGE_MINE_REQUIREMENTS[12])) > -1, "&c&lPrestige Mine #13", "&d&lPrestige Mine #13",
                        new String[]{"&bRequires " + PrisonUtils.addCommasToNumber(Constants.PRESTIGE_MINE_REQUIREMENTS[12]) + " prestiges", "", "&cYou do not have this unlocked!", "", "&7&oSize: 109x98x109"},
                        new String[]{"&bRequires " + PrisonUtils.addCommasToNumber(Constants.PRESTIGE_MINE_REQUIREMENTS[12]) + " prestiges", "", "&aClick to warp!", "", "&7&oSize: 109x98x109"},
                        Material.CRYING_OBSIDIAN, Material.CRYING_OBSIDIAN, (p, t) -> {
                            p.sendMessage(ChatColor.RED + "You do not have this mine unlocked!");
                        }, (p, t) -> {
                            Warps.warpToPrestigeMine(p, 12);
                        }, true),
                createMineWarp(c, playerData.getPrestige().compareTo(BigInteger.valueOf(Constants.PRESTIGE_MINE_REQUIREMENTS[13])) > -1, "&c&lPrestige Mine #14", "&d&lPrestige Mine #14",
                        new String[]{"&bRequires " + PrisonUtils.addCommasToNumber(Constants.PRESTIGE_MINE_REQUIREMENTS[13]) + " prestiges", "", "&cYou do not have this unlocked!", "", "&7&oSize: 109x98x109"},
                        new String[]{"&bRequires " + PrisonUtils.addCommasToNumber(Constants.PRESTIGE_MINE_REQUIREMENTS[13]) + " prestiges", "", "&aClick to warp!", "", "&7&oSize: 109x98x109"},
                        Material.PRISMARINE, Material.PRISMARINE, (p, t) -> {
                            p.sendMessage(ChatColor.RED + "You do not have this mine unlocked!");
                        }, (p, t) -> {
                            Warps.warpToPrestigeMine(p, 13);
                        }, true),
                createMineWarp(c, playerData.getPrestige().compareTo(BigInteger.valueOf(Constants.PRESTIGE_MINE_REQUIREMENTS[14])) > -1, "&c&lPrestige Mine #15", "&d&lPrestige Mine #15",
                        new String[]{"&bRequires " + PrisonUtils.addCommasToNumber(Constants.PRESTIGE_MINE_REQUIREMENTS[14]) + " prestiges", "", "&cYou do not have this unlocked!", "", "&7&oSize: 151x98x151"},
                        new String[]{"&bRequires " + PrisonUtils.addCommasToNumber(Constants.PRESTIGE_MINE_REQUIREMENTS[14]) + " prestiges", "", "&aClick to warp!", "", "&7&oSize: 151x98x151"},
                        Material.AMETHYST_BLOCK, Material.AMETHYST_BLOCK, (p, t) -> {
                            p.sendMessage(ChatColor.RED + "You do not have this mine unlocked!");
                        }, (p, t) -> {
                            Warps.warpToPrestigeMine(p, 14);
                        }, true),
                createLightBluePlaceHolder(),
                createLightBluePlaceHolder(),
                createLightBluePlaceHolder(),
                createGrayPlaceHolder(),
                createGrayPlaceHolder(),
                createGrayPlaceHolder(),
                ench(c.createButton(Material.COAL, "&a&lNormal Mines (A-Z)", List.of("&bClick to view normal mines", "", "&7&oWarp to a normal mine"), (p, t) -> {
                    minesMenuAZ(p);
                })),
                createGrayPlaceHolder(),
                ench(c.createButton(Material.BLAZE_POWDER, "&c&lRanked Mines", List.of("&bClick to view ranked mines", "", "&7&oWarp to a ranked mine"), (p, t) -> {
                    minesMenuRanked(p);
                })),
                createGrayPlaceHolder(),
                createGrayPlaceHolder(),
                createGrayPlaceHolder()
        );
        c.setOnCloseRun((p, t) -> mainMenu(p));
        c.open(player);
    }
    public static void minesMenuRanked(Player player) {
        PlayerData playerData = new PlayerData(player);
        GUICreator c = new GUICreator(18, "Warp to a mine (Ranked mines)");
        c.setItems(
                createLightBluePlaceHolder(),
                createLightBluePlaceHolder(),
                createMineWarp(c, playerData.getPlayerRanks().contains("warrior"), "&c&lWarrior Mine", "&4&lWarrior Mine",
                        new String[]{"&b&oRequires &nwarrior&b&o rank (or higher)", "&b&oPurchasable at &nstore.static-studios.net", "", "&cYou do not have this unlocked!", "", "&7&oSize: 79x98x79"},
                        new String[]{"&b&oRequires &nwarrior&b&o rank (or higher)", "&b&oPurchasable at &nstore.static-studios.net", "", "&aClick to warp!", "", "&7&oSize: 79x98x79"},
                        Material.DEEPSLATE_COAL_ORE, Material.DEEPSLATE_COAL_ORE, (p, t) -> {
                            p.sendMessage(ChatColor.RED + "You do not have this mine unlocked!");
                        }, (p, t) -> {
                            Warps.warpToRankMine(p, 0);
                        }, true),
                createMineWarp(c, playerData.getPlayerRanks().contains("master"), "&c&lMaster Mine", "&4&lMaster Mine",
                        new String[]{"&b&oRequires &nmaster&b&o rank (or higher)", "&b&oPurchasable at &nstore.static-studios.net", "", "&cYou do not have this unlocked!", "", "&7&oSize: 89x98x89"},
                        new String[]{"&b&oRequires &nmaster&b&o rank (or higher)", "&b&oPurchasable at &nstore.static-studios.net", "", "&aClick to warp!", "", "&7&oSize: 89x98x89"},
                        Material.DEEPSLATE_IRON_ORE, Material.DEEPSLATE_IRON_ORE, (p, t) -> {
                            p.sendMessage(ChatColor.RED + "You do not have this mine unlocked!");
                        }, (p, t) -> {
                            Warps.warpToRankMine(p, 1);
                        }, true),
                createMineWarp(c, playerData.getPlayerRanks().contains("mythic"), "&c&lMythic Mine", "&4&lMythic Mine",
                        new String[]{"&b&oRequires &nmythic&b&o rank (or higher)", "&b&oPurchasable at &nstore.static-studios.net", "", "&cYou do not have this unlocked!", "", "&7&oSize: 99x98x99"},
                        new String[]{"&b&oRequires &nmythic&b&o rank (or higher)", "&b&oPurchasable at &nstore.static-studios.net", "", "&aClick to warp!", "", "&7&oSize: 99x98x99"},
                        Material.DEEPSLATE_GOLD_ORE, Material.DEEPSLATE_GOLD_ORE, (p, t) -> {
                            p.sendMessage(ChatColor.RED + "You do not have this mine unlocked!");
                        }, (p, t) -> {
                            Warps.warpToRankMine(p, 2);
                        }, true),
                createMineWarp(c, playerData.getPlayerRanks().contains("static"), "&c&lStatic Mine", "&4&lStatic Mine",
                        new String[]{"&b&oRequires &nstatic&b&o rank (or higher)", "&b&oPurchasable at &nstore.static-studios.net", "", "&cYou do not have this unlocked!", "", "&7&oSize: 129x98x129"},
                        new String[]{"&b&oRequires &nstatic&b&o rank (or higher)", "&b&oPurchasable at &nstore.static-studios.net", "", "&aClick to warp!", "", "&7&oSize: 129x98x129"},
                        Material.DEEPSLATE_DIAMOND_ORE, Material.DEEPSLATE_DIAMOND_ORE, (p, t) -> {
                            p.sendMessage(ChatColor.RED + "You do not have this mine unlocked!");
                        }, (p, t) -> {
                            Warps.warpToRankMine(p, 3);
                        }, true),
                createMineWarp(c, playerData.getPlayerRanks().contains("staticp"), "&c&lStatic+ Mine", "&4&lStatic+ Mine",
                        new String[]{"&b&oRequires &nstatic+&b&o rank (or higher)", "&b&oPurchasable at &nstore.static-studios.net", "", "&cYou do not have this unlocked!", "", "&7&oSize: 151x98x151"},
                        new String[]{"&b&oRequires &nstatic+&b&o rank (or higher)", "&b&oPurchasable at &nstore.static-studios.net", "", "&aClick to warp!", "", "&7&oSize: 151x98x151"},
                        Material.DEEPSLATE_EMERALD_ORE, Material.DEEPSLATE_EMERALD_ORE, (p, t) -> {
                            p.sendMessage(ChatColor.RED + "You do not have this mine unlocked!");
                        }, (p, t) -> {
                            Warps.warpToRankMine(p, 4);
                        }, true),
                createLightBluePlaceHolder(),
                createLightBluePlaceHolder(),
                createGrayPlaceHolder(),
                createGrayPlaceHolder(),
                createGrayPlaceHolder(),
                ench(c.createButton(Material.NETHER_STAR, "&d&lPrestige Mines", List.of("&bClick to view prestige mines", "", "&7&oWarp to a prestige mine"), (p, t) -> {
                    minesMenuPrestige(p);
                })),
                createGrayPlaceHolder(),
                ench(c.createButton(Material.COAL, "&a&lNormal Mines (A-Z)", List.of("&bClick to view normal mines", "", "&7&oWarp to a normal mine"), (p, t) -> {
                    minesMenuAZ(p);
                })),
                createGrayPlaceHolder(),
                createGrayPlaceHolder(),
                createGrayPlaceHolder()
        );
        c.setOnCloseRun((p, t) -> mainMenu(p));
        c.open(player);
    }


    private static ItemStack createMineWarp(GUICreator c, boolean unlockCondition, String nameLocked, String nameUnlocked, String[] loreLocked, String[] loreUnlocked, Material matLocked, Material matUnlocked, GUIRunnable callbackLocked, GUIRunnable callbackUnlocked) {
        if (unlockCondition) return c.createButton(matUnlocked, nameUnlocked, List.of(loreUnlocked), callbackUnlocked);
        return c.createButton(matLocked, nameLocked, List.of(loreLocked), callbackLocked);
    }
    private static ItemStack createMineWarp(GUICreator c, boolean unlockCondition, String nameLocked, String nameUnlocked, String[] loreLocked, String[] loreUnlocked, Material matLocked, Material matUnlocked, GUIRunnable callbackLocked, GUIRunnable callbackUnlocked, boolean enchantIfUnlocked) {
        ItemStack item = createMineWarp(c, unlockCondition, nameLocked, nameUnlocked, loreLocked, loreUnlocked, matLocked, matUnlocked, callbackLocked, callbackUnlocked);
        if (enchantIfUnlocked && unlockCondition) ench(item);
        return item;
    }
}
