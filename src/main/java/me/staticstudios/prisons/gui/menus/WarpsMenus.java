package me.staticstudios.prisons.gui.menus;

import me.staticstudios.prisons.data.serverData.PlayerData;
import me.staticstudios.prisons.gui.GUI;
import me.staticstudios.prisons.gui.GUIPage;
import me.staticstudios.prisons.misc.Warps;
import me.staticstudios.prisons.utils.StaticVars;
import me.staticstudios.prisons.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.math.BigInteger;
import java.util.ArrayList;

public class WarpsMenus {
    static final Material[] publicMineWarpMaterials = {
            Material.STONE,
            Material.COBBLESTONE,
            Material.COAL_ORE,
            Material.IRON_ORE,
            Material.GOLD_ORE,
            Material.LAPIS_ORE,
            Material.REDSTONE_ORE,
            Material.DIAMOND_ORE,
            Material.EMERALD_ORE
    };
    static final Material[] prestigeMineWarpMaterials = {
            Material.COAL_BLOCK,
            Material.IRON_BLOCK,
            Material.GOLD_BLOCK,
            Material.LAPIS_BLOCK,
            Material.REDSTONE_BLOCK,
            Material.DIAMOND_BLOCK,
            Material.EMERALD_BLOCK,
            Material.NETHERRACK,
            Material.NETHER_BRICKS,
            Material.QUARTZ_BLOCK,
            Material.END_STONE,
            Material.OBSIDIAN,
            Material.CRYING_OBSIDIAN,
            Material.PRISMARINE,
            Material.AMETHYST_BLOCK
    };
    //Main menu
    public static void main() {
        GUIPage guiPage = new GUIPage() {
            @Override
            public void item0Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                Warps.warpToSpawn(player);
            }
            @Override
            public void item1Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                GUI.getGUIPage("warpsMines").open(player);
            }
            @Override
            public void item2Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                Warps.warpToCrates(player);
            }
            @Override
            public void item3Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                Warps.warpToLeaderboards(player);
            }
        };
        guiPage.identifier = "warps";
        guiPage.menuItems.add(GUI.createEnchantedMenuItem(guiPage.identifier, Material.COMPASS, ChatColor.GREEN + "" + ChatColor.BOLD + "Spawn", ChatColor.GRAY + "Warp to spawn"));
        guiPage.menuItems.add(GUI.createEnchantedMenuItem(guiPage.identifier, Material.NETHERITE_PICKAXE, ChatColor.YELLOW + "" + ChatColor.BOLD + "Mines", ChatColor.GRAY + "Warp to a mine"));
        guiPage.menuItems.add(GUI.createEnchantedMenuItem(guiPage.identifier, Material.TRIPWIRE_HOOK, ChatColor.AQUA + "" + ChatColor.BOLD + "Crates", ChatColor.GRAY + "Warp to crates"));
        guiPage.menuItems.add(GUI.createEnchantedMenuItem(guiPage.identifier, Material.DIAMOND, ChatColor.AQUA + "" + ChatColor.BOLD + "Leaderboards", ChatColor.GRAY + "Warp to leaderboards"));
        guiPage.guiTitle = ChatColor.translateAlternateColorCodes('&', "&dWarps");
        guiPage.onCloseGoToMenu = "main";
        guiPage.register();
    }
    //Mines menu
    public static void mines() {
        GUIPage guiPage = new GUIPage() {
            @Override
            public void onOpen(Player player) {
                PlayerData playerData = new PlayerData(player);
                menuItems = new ArrayList<>();
                for (int i = 0; i < 26; i++) {
                    if (playerData.getMineRank() >= i) {
                        menuItems.add(GUI.createMenuItem(identifier, publicMineWarpMaterials[i / 3], ChatColor.GREEN + "" + ChatColor.BOLD + "Mine " + Utils.getMineRankLetterFromMineRank(i), ChatColor.GREEN + "Click to warp!", "", ChatColor.GRAY + "" + ChatColor.ITALIC + "Size: 69x98x69"));
                    } else menuItems.add(GUI.createMenuItem(identifier, Material.RED_STAINED_GLASS, ChatColor.RED + "" + ChatColor.BOLD + "Mine " + Utils.getMineRankLetterFromMineRank(i), ChatColor.RED + "You do not have this mine unlocked!", "", ChatColor.GRAY + "" + ChatColor.ITALIC + "Size: 69x98x69"));
                }
                int currentPMine;
                currentPMine = 0;
                if (playerData.getPrestige().compareTo(BigInteger.valueOf(StaticVars.PRESTIGE_MINE_REQUIREMENTS[currentPMine])) > -1) {
                    menuItems.add(9, GUI.createEnchantedMenuItem(identifier, prestigeMineWarpMaterials[currentPMine], ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Prestige Mine #" + (currentPMine + 1), ChatColor.AQUA + "" + ChatColor.ITALIC + "Requires " + Utils.addCommasToNumber(StaticVars.PRESTIGE_MINE_REQUIREMENTS[currentPMine]) + " prestiges", "", ChatColor.GREEN + "Click to warp!", "", ChatColor.GRAY + "" + ChatColor.ITALIC + "Size: 69x98x69"));
                } else menuItems.add(9, GUI.createEnchantedMenuItem(identifier, prestigeMineWarpMaterials[currentPMine], ChatColor.RED + "" + ChatColor.BOLD + "Prestige Mine #" + (currentPMine + 1), ChatColor.AQUA + "" + ChatColor.ITALIC + "Requires " + Utils.addCommasToNumber(StaticVars.PRESTIGE_MINE_REQUIREMENTS[currentPMine]) + " prestiges", "", ChatColor.RED + "You do not have this mine unlocked!", "", ChatColor.GRAY + "" + ChatColor.ITALIC + "Size: 69x98x69"));
                currentPMine = 1;
                if (playerData.getPrestige().compareTo(BigInteger.valueOf(StaticVars.PRESTIGE_MINE_REQUIREMENTS[currentPMine])) > -1) {
                    menuItems.add(17, GUI.createEnchantedMenuItem(identifier, prestigeMineWarpMaterials[currentPMine], ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Prestige Mine #" + (currentPMine + 1), ChatColor.AQUA + "" + ChatColor.ITALIC + "Requires " + Utils.addCommasToNumber(StaticVars.PRESTIGE_MINE_REQUIREMENTS[currentPMine]) + " prestiges", "", ChatColor.GREEN + "Click to warp!", "", ChatColor.GRAY + "" + ChatColor.ITALIC + "Size: 69x98x69"));
                } else menuItems.add(17, GUI.createEnchantedMenuItem(identifier, prestigeMineWarpMaterials[currentPMine], ChatColor.RED + "" + ChatColor.BOLD + "Prestige Mine #" + (currentPMine + 1), ChatColor.AQUA + "" + ChatColor.ITALIC + "Requires " + Utils.addCommasToNumber(StaticVars.PRESTIGE_MINE_REQUIREMENTS[currentPMine]) + " prestiges", "", ChatColor.RED + "You do not have this mine unlocked!", "", ChatColor.GRAY + "" + ChatColor.ITALIC + "Size: 69x98x69"));
               currentPMine = 2;
                if (playerData.getPrestige().compareTo(BigInteger.valueOf(StaticVars.PRESTIGE_MINE_REQUIREMENTS[currentPMine])) > -1) {
                    menuItems.add(25, GUI.createEnchantedMenuItem(identifier, prestigeMineWarpMaterials[currentPMine], ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Prestige Mine #" + (currentPMine + 1), ChatColor.AQUA + "" + ChatColor.ITALIC + "Requires " + Utils.addCommasToNumber(StaticVars.PRESTIGE_MINE_REQUIREMENTS[currentPMine]) + " prestiges", "", ChatColor.GREEN + "Click to warp!", "", ChatColor.GRAY + "" + ChatColor.ITALIC + "Size: 69x98x69"));
                } else menuItems.add(25, GUI.createEnchantedMenuItem(identifier, prestigeMineWarpMaterials[currentPMine], ChatColor.RED + "" + ChatColor.BOLD + "Prestige Mine #" + (currentPMine + 1), ChatColor.AQUA + "" + ChatColor.ITALIC + "Requires " + Utils.addCommasToNumber(StaticVars.PRESTIGE_MINE_REQUIREMENTS[currentPMine]) + " prestiges", "", ChatColor.RED + "You do not have this mine unlocked!", "", ChatColor.GRAY + "" + ChatColor.ITALIC + "Size: 69x98x69"));
                currentPMine = 3;
                if (playerData.getPrestige().compareTo(BigInteger.valueOf(StaticVars.PRESTIGE_MINE_REQUIREMENTS[currentPMine])) > -1) {
                    menuItems.add(26, GUI.createEnchantedMenuItem(identifier, prestigeMineWarpMaterials[currentPMine], ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Prestige Mine #" + (currentPMine + 1), ChatColor.AQUA + "" + ChatColor.ITALIC + "Requires " + Utils.addCommasToNumber(StaticVars.PRESTIGE_MINE_REQUIREMENTS[currentPMine]) + " prestiges", "", ChatColor.GREEN + "Click to warp!", "", ChatColor.GRAY + "" + ChatColor.ITALIC + "Size: 69x98x69"));
                } else menuItems.add(26, GUI.createEnchantedMenuItem(identifier, prestigeMineWarpMaterials[currentPMine], ChatColor.RED + "" + ChatColor.BOLD + "Prestige Mine #" + (currentPMine + 1), ChatColor.AQUA + "" + ChatColor.ITALIC + "Requires " + Utils.addCommasToNumber(StaticVars.PRESTIGE_MINE_REQUIREMENTS[currentPMine]) + " prestiges", "", ChatColor.RED + "You do not have this mine unlocked!", "", ChatColor.GRAY + "" + ChatColor.ITALIC + "Size: 69x98x69"));
                currentPMine = 4;
                if (playerData.getPrestige().compareTo(BigInteger.valueOf(StaticVars.PRESTIGE_MINE_REQUIREMENTS[currentPMine])) > -1) {
                    menuItems.add(27, GUI.createEnchantedMenuItem(identifier, prestigeMineWarpMaterials[currentPMine], ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Prestige Mine #" + (currentPMine + 1), ChatColor.AQUA + "" + ChatColor.ITALIC + "Requires " + Utils.addCommasToNumber(StaticVars.PRESTIGE_MINE_REQUIREMENTS[currentPMine]) + " prestiges", "", ChatColor.GREEN + "Click to warp!", "", ChatColor.GRAY + "" + ChatColor.ITALIC + "Size: 69x98x69"));
                } else menuItems.add(27, GUI.createEnchantedMenuItem(identifier, prestigeMineWarpMaterials[currentPMine], ChatColor.RED + "" + ChatColor.BOLD + "Prestige Mine #" + (currentPMine + 1), ChatColor.AQUA + "" + ChatColor.ITALIC + "Requires " + Utils.addCommasToNumber(StaticVars.PRESTIGE_MINE_REQUIREMENTS[currentPMine]) + " prestiges", "", ChatColor.RED + "You do not have this mine unlocked!", "", ChatColor.GRAY + "" + ChatColor.ITALIC + "Size: 69x98x69"));
                for (int i = 0; i < 5; i++) menuItems.add(GUI.createLightGrayPlaceholderItem());
                currentPMine = 5;
                if (playerData.getPrestige().compareTo(BigInteger.valueOf(StaticVars.PRESTIGE_MINE_REQUIREMENTS[currentPMine])) > -1) {
                    menuItems.add(GUI.createEnchantedMenuItem(identifier, prestigeMineWarpMaterials[currentPMine], ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Prestige Mine #" + (currentPMine + 1), ChatColor.AQUA + "" + ChatColor.ITALIC + "Requires " + Utils.addCommasToNumber(StaticVars.PRESTIGE_MINE_REQUIREMENTS[currentPMine]) + " prestiges", "", ChatColor.GREEN + "Click to warp!", "", ChatColor.GRAY + "" + ChatColor.ITALIC + "Size: 89x98x89"));
                } else menuItems.add(GUI.createEnchantedMenuItem(identifier, prestigeMineWarpMaterials[currentPMine], ChatColor.RED + "" + ChatColor.BOLD + "Prestige Mine #" + (currentPMine + 1), ChatColor.AQUA + "" + ChatColor.ITALIC + "Requires " + Utils.addCommasToNumber(StaticVars.PRESTIGE_MINE_REQUIREMENTS[currentPMine]) + " prestiges", "", ChatColor.RED + "You do not have this mine unlocked!", "", ChatColor.GRAY + "" + ChatColor.ITALIC + "Size: 89x98x89"));
                currentPMine = 6;
                if (playerData.getPrestige().compareTo(BigInteger.valueOf(StaticVars.PRESTIGE_MINE_REQUIREMENTS[currentPMine])) > -1) {
                    menuItems.add(GUI.createEnchantedMenuItem(identifier, prestigeMineWarpMaterials[currentPMine], ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Prestige Mine #" + (currentPMine + 1), ChatColor.AQUA + "" + ChatColor.ITALIC + "Requires " + Utils.addCommasToNumber(StaticVars.PRESTIGE_MINE_REQUIREMENTS[currentPMine]) + " prestiges", "", ChatColor.GREEN + "Click to warp!", "", ChatColor.GRAY + "" + ChatColor.ITALIC + "Size: 89x98x89"));
                } else menuItems.add(GUI.createEnchantedMenuItem(identifier, prestigeMineWarpMaterials[currentPMine], ChatColor.RED + "" + ChatColor.BOLD + "Prestige Mine #" + (currentPMine + 1), ChatColor.AQUA + "" + ChatColor.ITALIC + "Requires " + Utils.addCommasToNumber(StaticVars.PRESTIGE_MINE_REQUIREMENTS[currentPMine]) + " prestiges", "", ChatColor.RED + "You do not have this mine unlocked!", "", ChatColor.GRAY + "" + ChatColor.ITALIC + "Size: 89x98x89"));
                currentPMine = 7;
                if (playerData.getPrestige().compareTo(BigInteger.valueOf(StaticVars.PRESTIGE_MINE_REQUIREMENTS[currentPMine])) > -1) {
                    menuItems.add(GUI.createEnchantedMenuItem(identifier, prestigeMineWarpMaterials[currentPMine], ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Prestige Mine #" + (currentPMine + 1), ChatColor.AQUA + "" + ChatColor.ITALIC + "Requires " + Utils.addCommasToNumber(StaticVars.PRESTIGE_MINE_REQUIREMENTS[currentPMine]) + " prestiges", "", ChatColor.GREEN + "Click to warp!", "", ChatColor.GRAY + "" + ChatColor.ITALIC + "Size: 89x98x89"));
                } else menuItems.add(GUI.createEnchantedMenuItem(identifier, prestigeMineWarpMaterials[currentPMine], ChatColor.RED + "" + ChatColor.BOLD + "Prestige Mine #" + (currentPMine + 1), ChatColor.AQUA + "" + ChatColor.ITALIC + "Requires " + Utils.addCommasToNumber(StaticVars.PRESTIGE_MINE_REQUIREMENTS[currentPMine]) + " prestiges", "", ChatColor.RED + "You do not have this mine unlocked!", "", ChatColor.GRAY + "" + ChatColor.ITALIC + "Size: 89x98x89"));
                currentPMine = 8;
                if (playerData.getPrestige().compareTo(BigInteger.valueOf(StaticVars.PRESTIGE_MINE_REQUIREMENTS[currentPMine])) > -1) {
                    menuItems.add(GUI.createEnchantedMenuItem(identifier, prestigeMineWarpMaterials[currentPMine], ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Prestige Mine #" + (currentPMine + 1), ChatColor.AQUA + "" + ChatColor.ITALIC + "Requires " + Utils.addCommasToNumber(StaticVars.PRESTIGE_MINE_REQUIREMENTS[currentPMine]) + " prestiges", "", ChatColor.GREEN + "Click to warp!", "", ChatColor.GRAY + "" + ChatColor.ITALIC + "Size: 89x98x89"));
                } else menuItems.add(GUI.createEnchantedMenuItem(identifier, prestigeMineWarpMaterials[currentPMine], ChatColor.RED + "" + ChatColor.BOLD + "Prestige Mine #" + (currentPMine + 1), ChatColor.AQUA + "" + ChatColor.ITALIC + "Requires " + Utils.addCommasToNumber(StaticVars.PRESTIGE_MINE_REQUIREMENTS[currentPMine]) + " prestiges", "", ChatColor.RED + "You do not have this mine unlocked!", "", ChatColor.GRAY + "" + ChatColor.ITALIC + "Size: 89x98x89"));
                currentPMine = 9;
                if (playerData.getPrestige().compareTo(BigInteger.valueOf(StaticVars.PRESTIGE_MINE_REQUIREMENTS[currentPMine])) > -1) {
                    menuItems.add(GUI.createEnchantedMenuItem(identifier, prestigeMineWarpMaterials[currentPMine], ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Prestige Mine #" + (currentPMine + 1), ChatColor.AQUA + "" + ChatColor.ITALIC + "Requires " + Utils.addCommasToNumber(StaticVars.PRESTIGE_MINE_REQUIREMENTS[currentPMine]) + " prestiges", "", ChatColor.GREEN + "Click to warp!", "", ChatColor.GRAY + "" + ChatColor.ITALIC + "Size: 89x98x89"));
                } else menuItems.add(GUI.createEnchantedMenuItem(identifier, prestigeMineWarpMaterials[currentPMine], ChatColor.RED + "" + ChatColor.BOLD + "Prestige Mine #" + (currentPMine + 1), ChatColor.AQUA + "" + ChatColor.ITALIC + "Requires " + Utils.addCommasToNumber(StaticVars.PRESTIGE_MINE_REQUIREMENTS[currentPMine]) + " prestiges", "", ChatColor.RED + "You do not have this mine unlocked!", "", ChatColor.GRAY + "" + ChatColor.ITALIC + "Size: 89x98x89"));
                currentPMine = 10;
                if (playerData.getPrestige().compareTo(BigInteger.valueOf(StaticVars.PRESTIGE_MINE_REQUIREMENTS[currentPMine])) > -1) {
                    menuItems.set(31, GUI.createEnchantedMenuItem(identifier, prestigeMineWarpMaterials[currentPMine], ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Prestige Mine #" + (currentPMine + 1), ChatColor.AQUA + "" + ChatColor.ITALIC + "Requires " + Utils.addCommasToNumber(StaticVars.PRESTIGE_MINE_REQUIREMENTS[currentPMine]) + " prestiges", "", ChatColor.GREEN + "Click to warp!", "", ChatColor.GRAY + "" + ChatColor.ITALIC + "Size: 109x98x109"));
                } else menuItems.set(31, GUI.createEnchantedMenuItem(identifier, prestigeMineWarpMaterials[currentPMine], ChatColor.RED + "" + ChatColor.BOLD + "Prestige Mine #" + (currentPMine + 1), ChatColor.AQUA + "" + ChatColor.ITALIC + "Requires " + Utils.addCommasToNumber(StaticVars.PRESTIGE_MINE_REQUIREMENTS[currentPMine]) + " prestiges", "", ChatColor.RED + "You do not have this mine unlocked!", "", ChatColor.GRAY + "" + ChatColor.ITALIC + "Size: 109x98x109"));
                currentPMine = 11;
                if (playerData.getPrestige().compareTo(BigInteger.valueOf(StaticVars.PRESTIGE_MINE_REQUIREMENTS[currentPMine])) > -1) {
                    menuItems.set(32, GUI.createEnchantedMenuItem(identifier, prestigeMineWarpMaterials[currentPMine], ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Prestige Mine #" + (currentPMine + 1), ChatColor.AQUA + "" + ChatColor.ITALIC + "Requires " + Utils.addCommasToNumber(StaticVars.PRESTIGE_MINE_REQUIREMENTS[currentPMine]) + " prestiges", "", ChatColor.GREEN + "Click to warp!", "", ChatColor.GRAY + "" + ChatColor.ITALIC + "Size: 109x98x109"));
                } else menuItems.set(32, GUI.createEnchantedMenuItem(identifier, prestigeMineWarpMaterials[currentPMine], ChatColor.RED + "" + ChatColor.BOLD + "Prestige Mine #" + (currentPMine + 1), ChatColor.AQUA + "" + ChatColor.ITALIC + "Requires " + Utils.addCommasToNumber(StaticVars.PRESTIGE_MINE_REQUIREMENTS[currentPMine]) + " prestiges", "", ChatColor.RED + "You do not have this mine unlocked!", "", ChatColor.GRAY + "" + ChatColor.ITALIC + "Size: 109x98x109"));
                currentPMine = 12;
                if (playerData.getPrestige().compareTo(BigInteger.valueOf(StaticVars.PRESTIGE_MINE_REQUIREMENTS[currentPMine])) > -1) {
                    menuItems.set(33, GUI.createEnchantedMenuItem(identifier, prestigeMineWarpMaterials[currentPMine], ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Prestige Mine #" + (currentPMine + 1), ChatColor.AQUA + "" + ChatColor.ITALIC + "Requires " + Utils.addCommasToNumber(StaticVars.PRESTIGE_MINE_REQUIREMENTS[currentPMine]) + " prestiges", "", ChatColor.GREEN + "Click to warp!", "", ChatColor.GRAY + "" + ChatColor.ITALIC + "Size: 109x98x109"));
                } else menuItems.set(33, GUI.createEnchantedMenuItem(identifier, prestigeMineWarpMaterials[currentPMine], ChatColor.RED + "" + ChatColor.BOLD + "Prestige Mine #" + (currentPMine + 1), ChatColor.AQUA + "" + ChatColor.ITALIC + "Requires " + Utils.addCommasToNumber(StaticVars.PRESTIGE_MINE_REQUIREMENTS[currentPMine]) + " prestiges", "", ChatColor.RED + "You do not have this mine unlocked!", "", ChatColor.GRAY + "" + ChatColor.ITALIC + "Size: 109x98x109"));
                currentPMine = 13;
                if (playerData.getPrestige().compareTo(BigInteger.valueOf(StaticVars.PRESTIGE_MINE_REQUIREMENTS[currentPMine])) > -1) {
                    menuItems.add(25, GUI.createEnchantedMenuItem(identifier, prestigeMineWarpMaterials[currentPMine], ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Prestige Mine #" + (currentPMine + 1), ChatColor.AQUA + "" + ChatColor.ITALIC + "Requires " + Utils.addCommasToNumber(StaticVars.PRESTIGE_MINE_REQUIREMENTS[currentPMine]) + " prestiges", "", ChatColor.GREEN + "Click to warp!", "", ChatColor.GRAY + "" + ChatColor.ITALIC + "Size: 109x98x109"));
                } else menuItems.add(25, GUI.createEnchantedMenuItem(identifier, prestigeMineWarpMaterials[currentPMine], ChatColor.RED + "" + ChatColor.BOLD + "Prestige Mine #" + (currentPMine + 1), ChatColor.AQUA + "" + ChatColor.ITALIC + "Requires " + Utils.addCommasToNumber(StaticVars.PRESTIGE_MINE_REQUIREMENTS[currentPMine]) + " prestiges", "", ChatColor.RED + "You do not have this mine unlocked!", "", ChatColor.GRAY + "" + ChatColor.ITALIC + "Size: 109x98x109"));
                currentPMine = 14;
                if (playerData.getPrestige().compareTo(BigInteger.valueOf(StaticVars.PRESTIGE_MINE_REQUIREMENTS[currentPMine])) > -1) {
                    menuItems.add(17, GUI.createEnchantedMenuItem(identifier, prestigeMineWarpMaterials[currentPMine], ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Prestige Mine #" + (currentPMine + 1), ChatColor.AQUA + "" + ChatColor.ITALIC + "Requires " + Utils.addCommasToNumber(StaticVars.PRESTIGE_MINE_REQUIREMENTS[currentPMine]) + " prestiges", "", ChatColor.GREEN + "Click to warp!", "", ChatColor.GRAY + "" + ChatColor.ITALIC + "Size: 151x98x151"));
                } else menuItems.add(17, GUI.createEnchantedMenuItem(identifier, prestigeMineWarpMaterials[currentPMine], ChatColor.RED + "" + ChatColor.BOLD + "Prestige Mine #" + (currentPMine + 1), ChatColor.AQUA + "" + ChatColor.ITALIC + "Requires " + Utils.addCommasToNumber(StaticVars.PRESTIGE_MINE_REQUIREMENTS[currentPMine]) + " prestiges", "", ChatColor.RED + "You do not have this mine unlocked!", "", ChatColor.GRAY + "" + ChatColor.ITALIC + "Size: 151x98x151"));
                menuItems.add(GUI.createLightGrayPlaceholderItem());
                menuItems.add(GUI.createLightGrayPlaceholderItem());
                menuItems.add(GUI.createEnchantedMenuItem(identifier, Material.ENCHANTING_TABLE, ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Event Mine", ChatColor.AQUA + "Click to warp!", "", ChatColor.GRAY + "" + ChatColor.ITALIC + "Refills every 3 hours"));
                menuItems.add(GUI.createLightGrayPlaceholderItem());
                if (playerData.getPlayerRanks().contains("warrior")) {
                    menuItems.add(GUI.createMenuItem(identifier, Material.DEEPSLATE_COAL_ORE, ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Warrior Mine", ChatColor.AQUA + "" + ChatColor.ITALIC + "Requires " + ChatColor.UNDERLINE + "warrior" + ChatColor.RESET + ChatColor.AQUA + ChatColor.ITALIC + " rank (or higher)", ChatColor.AQUA + "" + ChatColor.ITALIC + "Purchasable on: https://store.static-studios.net", "", ChatColor.GREEN + "Click to warp!", "", ChatColor.GRAY + "" + ChatColor.ITALIC + "Size: 79x98x79"));
                } else menuItems.add(GUI.createMenuItem(identifier, Material.DEEPSLATE_COAL_ORE, ChatColor.RED + "" + ChatColor.BOLD + "Warrior Mine", ChatColor.AQUA + "" + ChatColor.ITALIC + "Requires " + ChatColor.UNDERLINE + "warrior" + ChatColor.RESET + ChatColor.AQUA + ChatColor.ITALIC + " rank (or higher)", ChatColor.AQUA + "" + ChatColor.ITALIC + "Purchasable on: https://store.static-studios.net", "", ChatColor.RED + "You do not have this mine unlocked!", "", ChatColor.GRAY + "" + ChatColor.ITALIC + "Size: 79x98x79"));
                if (playerData.getPlayerRanks().contains("master")) {
                    menuItems.add(GUI.createMenuItem(identifier, Material.DEEPSLATE_IRON_ORE, ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Master Mine", ChatColor.AQUA + "" + ChatColor.ITALIC + "Requires " + ChatColor.UNDERLINE + "master" + ChatColor.RESET + ChatColor.AQUA + ChatColor.ITALIC + " rank (or higher)", ChatColor.AQUA + "" + ChatColor.ITALIC + "Purchasable on: https://store.static-studios.net", "", ChatColor.GREEN + "Click to warp!", "", ChatColor.GRAY + "" + ChatColor.ITALIC + "Size: 89x98x89"));
                } else menuItems.add(GUI.createMenuItem(identifier, Material.DEEPSLATE_IRON_ORE, ChatColor.RED + "" + ChatColor.BOLD + "Master Mine", ChatColor.AQUA + "" + ChatColor.ITALIC + "Requires " + ChatColor.UNDERLINE + "master" + ChatColor.RESET + ChatColor.AQUA + ChatColor.ITALIC + " rank (or higher)", ChatColor.AQUA + "" + ChatColor.ITALIC + "Purchasable on: https://store.static-studios.net", "", ChatColor.RED + "You do not have this mine unlocked!", "", ChatColor.GRAY + "" + ChatColor.ITALIC + "Size: 89x98x89"));
                if (playerData.getPlayerRanks().contains("mythic")) {
                    menuItems.add(GUI.createMenuItem(identifier, Material.DEEPSLATE_GOLD_ORE, ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Mythic Mine", ChatColor.AQUA + "" + ChatColor.ITALIC + "Requires " + ChatColor.UNDERLINE + "mythic" + ChatColor.RESET + ChatColor.AQUA + ChatColor.ITALIC + " rank (or higher)", ChatColor.AQUA + "" + ChatColor.ITALIC + "Purchasable on: https://store.static-studios.net", "", ChatColor.GREEN + "Click to warp!", "", ChatColor.GRAY + "" + ChatColor.ITALIC + "Size: 99x98x99"));
                } else menuItems.add(GUI.createMenuItem(identifier, Material.DEEPSLATE_GOLD_ORE, ChatColor.RED + "" + ChatColor.BOLD + "Mythic Mine", ChatColor.AQUA + "" + ChatColor.ITALIC + "Requires " + ChatColor.UNDERLINE + "mythic" + ChatColor.RESET + ChatColor.AQUA + ChatColor.ITALIC + " rank (or higher)", ChatColor.AQUA + "" + ChatColor.ITALIC + "Purchasable on: https://store.static-studios.net", "", ChatColor.RED + "You do not have this mine unlocked!", "", ChatColor.GRAY + "" + ChatColor.ITALIC + "Size: 99x98x99"));
                if (playerData.getPlayerRanks().contains("static")) {
                    menuItems.add(GUI.createMenuItem(identifier, Material.DEEPSLATE_DIAMOND_ORE, ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Static Mine", ChatColor.AQUA + "" + ChatColor.ITALIC + "Requires " + ChatColor.UNDERLINE + "static" + ChatColor.RESET + ChatColor.AQUA + ChatColor.ITALIC + " rank (or higher)", ChatColor.AQUA + "" + ChatColor.ITALIC + "Purchasable on: https://store.static-studios.net", "", ChatColor.GREEN + "Click to warp!", "", ChatColor.GRAY + "" + ChatColor.ITALIC + "Size: 129x98x129"));
                } else menuItems.add(GUI.createMenuItem(identifier, Material.DEEPSLATE_DIAMOND_ORE, ChatColor.RED + "" + ChatColor.BOLD + "Static Mine", ChatColor.AQUA + "" + ChatColor.ITALIC + "Requires " + ChatColor.UNDERLINE + "static" + ChatColor.RESET + ChatColor.AQUA + ChatColor.ITALIC + " rank (or higher)", ChatColor.AQUA + "" + ChatColor.ITALIC + "Purchasable on: https://store.static-studios.net", "", ChatColor.RED + "You do not have this mine unlocked!", "", ChatColor.GRAY + "" + ChatColor.ITALIC + "Size: 129x98x129"));
                if (playerData.getPlayerRanks().contains("staticp")) {
                    menuItems.add(GUI.createMenuItem(identifier, Material.DEEPSLATE_EMERALD_ORE, ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Static+ Mine", ChatColor.AQUA + "" + ChatColor.ITALIC + "Requires " + ChatColor.UNDERLINE + "static+" + ChatColor.RESET + ChatColor.AQUA + ChatColor.ITALIC + " rank (or higher)", ChatColor.AQUA + "" + ChatColor.ITALIC + "Purchasable on: https://store.static-studios.net", "", ChatColor.GREEN + "Click to warp!", "", ChatColor.GRAY + "" + ChatColor.ITALIC + "Size: 151x98x151"));
                } else menuItems.add(GUI.createMenuItem(identifier, Material.DEEPSLATE_EMERALD_ORE, ChatColor.RED + "" + ChatColor.BOLD + "Static+ Mine", ChatColor.AQUA + "" + ChatColor.ITALIC + "Requires " + ChatColor.UNDERLINE + "static+" + ChatColor.RESET + ChatColor.AQUA + ChatColor.ITALIC + " rank (or higher)", ChatColor.AQUA + "" + ChatColor.ITALIC + "Purchasable on: https://store.static-studios.net", "", ChatColor.RED + "You do not have this mine unlocked!", "", ChatColor.GRAY + "" + ChatColor.ITALIC + "Size: 151x98x151"));
            }
            @Override
            public void item0Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                PlayerData playerData = new PlayerData(player);
                if (playerData.getMineRank() >= 0) {
                    Warps.warpToMine(player, 0);
                } else player.sendMessage(ChatColor.RED + "You do not have this mine unlocked!");
            }
            @Override
            public void item1Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                PlayerData playerData = new PlayerData(player);
                if (playerData.getMineRank() >= 1) {
                    Warps.warpToMine(player, 1);
                } else player.sendMessage(ChatColor.RED + "You do not have this mine unlocked!");
            }
            @Override
            public void item2Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                PlayerData playerData = new PlayerData(player);
                if (playerData.getMineRank() >= 2) {
                    Warps.warpToMine(player, 2);
                } else player.sendMessage(ChatColor.RED + "You do not have this mine unlocked!");
            }
            @Override
            public void item3Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                PlayerData playerData = new PlayerData(player);
                if (playerData.getMineRank() >= 3) {
                    Warps.warpToMine(player, 3);
                } else player.sendMessage(ChatColor.RED + "You do not have this mine unlocked!");
            }
            @Override
            public void item4Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                PlayerData playerData = new PlayerData(player);
                if (playerData.getMineRank() >= 4) {
                    Warps.warpToMine(player, 4);
                } else player.sendMessage(ChatColor.RED + "You do not have this mine unlocked!");
            }
            @Override
            public void item5Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                PlayerData playerData = new PlayerData(player);
                if (playerData.getMineRank() >= 5) {
                    Warps.warpToMine(player, 5);
                } else player.sendMessage(ChatColor.RED + "You do not have this mine unlocked!");
            }
            @Override
            public void item6Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                PlayerData playerData = new PlayerData(player);
                if (playerData.getMineRank() >= 6) {
                    Warps.warpToMine(player, 6);
                } else player.sendMessage(ChatColor.RED + "You do not have this mine unlocked!");
            }
            @Override
            public void item7Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                PlayerData playerData = new PlayerData(player);
                if (playerData.getMineRank() >= 7) {
                    Warps.warpToMine(player, 7);
                } else player.sendMessage(ChatColor.RED + "You do not have this mine unlocked!");
            }
            @Override
            public void item8Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                PlayerData playerData = new PlayerData(player);
                if (playerData.getMineRank() >= 8) {
                    Warps.warpToMine(player, 8);
                } else player.sendMessage(ChatColor.RED + "You do not have this mine unlocked!");
            }
            @Override
            public void item10Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                PlayerData playerData = new PlayerData(player);
                if (playerData.getMineRank() >= 9) {
                    Warps.warpToMine(player, 9);
                } else player.sendMessage(ChatColor.RED + "You do not have this mine unlocked!");
            }
            @Override
            public void item11Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                PlayerData playerData = new PlayerData(player);
                if (playerData.getMineRank() >= 10) {
                    Warps.warpToMine(player, 10);
                } else player.sendMessage(ChatColor.RED + "You do not have this mine unlocked!");
            }
            @Override
            public void item12Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                PlayerData playerData = new PlayerData(player);
                if (playerData.getMineRank() >= 11) {
                    Warps.warpToMine(player, 11);
                } else player.sendMessage(ChatColor.RED + "You do not have this mine unlocked!");
            }
            @Override
            public void item13Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                PlayerData playerData = new PlayerData(player);
                if (playerData.getMineRank() >= 12) {
                    Warps.warpToMine(player, 12);
                } else player.sendMessage(ChatColor.RED + "You do not have this mine unlocked!");
            }
            @Override
            public void item14Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                PlayerData playerData = new PlayerData(player);
                if (playerData.getMineRank() >= 13) {
                    Warps.warpToMine(player, 13);
                } else player.sendMessage(ChatColor.RED + "You do not have this mine unlocked!");
            }
            @Override
            public void item15Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                PlayerData playerData = new PlayerData(player);
                if (playerData.getMineRank() >= 14) {
                    Warps.warpToMine(player, 14);
                } else player.sendMessage(ChatColor.RED + "You do not have this mine unlocked!");
            }
            @Override
            public void item16Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                PlayerData playerData = new PlayerData(player);
                if (playerData.getMineRank() >= 15) {
                    Warps.warpToMine(player, 15);
                } else player.sendMessage(ChatColor.RED + "You do not have this mine unlocked!");
            }
            @Override
            public void item19Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                PlayerData playerData = new PlayerData(player);
                if (playerData.getMineRank() >= 16) {
                    Warps.warpToMine(player, 16);
                } else player.sendMessage(ChatColor.RED + "You do not have this mine unlocked!");
            }
            @Override
            public void item20Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                PlayerData playerData = new PlayerData(player);
                if (playerData.getMineRank() >= 17) {
                    Warps.warpToMine(player, 17);
                } else player.sendMessage(ChatColor.RED + "You do not have this mine unlocked!");
            }
            @Override
            public void item21Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                PlayerData playerData = new PlayerData(player);
                if (playerData.getMineRank() >= 18) {
                    Warps.warpToMine(player, 18);
                } else player.sendMessage(ChatColor.RED + "You do not have this mine unlocked!");
            }
            @Override
            public void item22Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                PlayerData playerData = new PlayerData(player);
                if (playerData.getMineRank() >= 19) {
                    Warps.warpToMine(player, 19);
                } else player.sendMessage(ChatColor.RED + "You do not have this mine unlocked!");
            }
            @Override
            public void item23Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                PlayerData playerData = new PlayerData(player);
                if (playerData.getMineRank() >= 20) {
                    Warps.warpToMine(player, 20);
                } else player.sendMessage(ChatColor.RED + "You do not have this mine unlocked!");
            }
            @Override
            public void item24Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                PlayerData playerData = new PlayerData(player);
                if (playerData.getMineRank() >= 21) {
                    Warps.warpToMine(player, 21);
                } else player.sendMessage(ChatColor.RED + "You do not have this mine unlocked!");
            }
            @Override
            public void item25Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                PlayerData playerData = new PlayerData(player);
                if (playerData.getMineRank() >= 22) {
                    Warps.warpToMine(player, 22);
                } else player.sendMessage(ChatColor.RED + "You do not have this mine unlocked!");
            }
            @Override
            public void item30Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                PlayerData playerData = new PlayerData(player);
                if (playerData.getMineRank() >= 23) {
                    Warps.warpToMine(player, 23);
                } else player.sendMessage(ChatColor.RED + "You do not have this mine unlocked!");
            }
            @Override
            public void item31Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                PlayerData playerData = new PlayerData(player);
                if (playerData.getMineRank() >= 24) {
                    Warps.warpToMine(player, 24);
                } else player.sendMessage(ChatColor.RED + "You do not have this mine unlocked!");
            }
            @Override
            public void item32Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                PlayerData playerData = new PlayerData(player);
                if (playerData.getMineRank() >= 25) {
                    Warps.warpToMine(player, 25);
                } else player.sendMessage(ChatColor.RED + "You do not have this mine unlocked!");
            }
            @Override
            public void item9Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                PlayerData playerData = new PlayerData(player);
                if (playerData.getPrestige().compareTo(BigInteger.valueOf(StaticVars.PRESTIGE_MINE_REQUIREMENTS[0])) > -1) {
                    Warps.warpPrestigeMine(player, 0);
                } else player.sendMessage(ChatColor.RED + "You do not have this mine unlocked!");
            }
            @Override
            public void item18Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                PlayerData playerData = new PlayerData(player);
                if (playerData.getPrestige().compareTo(BigInteger.valueOf(StaticVars.PRESTIGE_MINE_REQUIREMENTS[1])) > -1) {
                    Warps.warpPrestigeMine(player, 1);
                } else player.sendMessage(ChatColor.RED + "You do not have this mine unlocked!");
            }
            @Override
            public void item27Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                PlayerData playerData = new PlayerData(player);
                if (playerData.getPrestige().compareTo(BigInteger.valueOf(StaticVars.PRESTIGE_MINE_REQUIREMENTS[2])) > -1) {
                    Warps.warpPrestigeMine(player, 2);
                } else player.sendMessage(ChatColor.RED + "You do not have this mine unlocked!");
            }
            @Override
            public void item28Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                PlayerData playerData = new PlayerData(player);
                if (playerData.getPrestige().compareTo(BigInteger.valueOf(StaticVars.PRESTIGE_MINE_REQUIREMENTS[3])) > -1) {
                    Warps.warpPrestigeMine(player, 3);
                } else player.sendMessage(ChatColor.RED + "You do not have this mine unlocked!");
            }
            @Override
            public void item29Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                PlayerData playerData = new PlayerData(player);
                if (playerData.getPrestige().compareTo(BigInteger.valueOf(StaticVars.PRESTIGE_MINE_REQUIREMENTS[4])) > -1) {
                    Warps.warpPrestigeMine(player, 4);
                } else player.sendMessage(ChatColor.RED + "You do not have this mine unlocked!");
            }
            @Override
            public void item38Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                PlayerData playerData = new PlayerData(player);
                if (playerData.getPrestige().compareTo(BigInteger.valueOf(StaticVars.PRESTIGE_MINE_REQUIREMENTS[5])) > -1) {
                    Warps.warpPrestigeMine(player, 5);
                } else player.sendMessage(ChatColor.RED + "You do not have this mine unlocked!");
            }
            @Override
            public void item39Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                PlayerData playerData = new PlayerData(player);
                if (playerData.getPrestige().compareTo(BigInteger.valueOf(StaticVars.PRESTIGE_MINE_REQUIREMENTS[6])) > -1) {
                    Warps.warpPrestigeMine(player, 6);
                } else player.sendMessage(ChatColor.RED + "You do not have this mine unlocked!");
            }
            @Override
            public void item40Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                PlayerData playerData = new PlayerData(player);
                if (playerData.getPrestige().compareTo(BigInteger.valueOf(StaticVars.PRESTIGE_MINE_REQUIREMENTS[7])) > -1) {
                    Warps.warpPrestigeMine(player, 7);
                } else player.sendMessage(ChatColor.RED + "You do not have this mine unlocked!");
            }
            @Override
            public void item41Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                PlayerData playerData = new PlayerData(player);
                if (playerData.getPrestige().compareTo(BigInteger.valueOf(StaticVars.PRESTIGE_MINE_REQUIREMENTS[8])) > -1) {
                    Warps.warpPrestigeMine(player, 8);
                } else player.sendMessage(ChatColor.RED + "You do not have this mine unlocked!");
            }
            @Override
            public void item42Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                PlayerData playerData = new PlayerData(player);
                if (playerData.getPrestige().compareTo(BigInteger.valueOf(StaticVars.PRESTIGE_MINE_REQUIREMENTS[9])) > -1) {
                    Warps.warpPrestigeMine(player, 9);
                } else player.sendMessage(ChatColor.RED + "You do not have this mine unlocked!");
            }
            @Override
            public void item33Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                PlayerData playerData = new PlayerData(player);
                if (playerData.getPrestige().compareTo(BigInteger.valueOf(StaticVars.PRESTIGE_MINE_REQUIREMENTS[10])) > -1) {
                    Warps.warpPrestigeMine(player, 10);
                } else player.sendMessage(ChatColor.RED + "You do not have this mine unlocked!");
            }
            @Override
            public void item34Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                PlayerData playerData = new PlayerData(player);
                if (playerData.getPrestige().compareTo(BigInteger.valueOf(StaticVars.PRESTIGE_MINE_REQUIREMENTS[11])) > -1) {
                    Warps.warpPrestigeMine(player, 11);
                } else player.sendMessage(ChatColor.RED + "You do not have this mine unlocked!");
            }
            @Override
            public void item35Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                PlayerData playerData = new PlayerData(player);
                if (playerData.getPrestige().compareTo(BigInteger.valueOf(StaticVars.PRESTIGE_MINE_REQUIREMENTS[12])) > -1) {
                    Warps.warpPrestigeMine(player, 12);
                } else player.sendMessage(ChatColor.RED + "You do not have this mine unlocked!");
            }
            @Override
            public void item26Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                PlayerData playerData = new PlayerData(player);
                if (playerData.getPrestige().compareTo(BigInteger.valueOf(StaticVars.PRESTIGE_MINE_REQUIREMENTS[13])) > -1) {
                    Warps.warpPrestigeMine(player, 13);
                } else player.sendMessage(ChatColor.RED + "You do not have this mine unlocked!");
            }
            @Override
            public void item17Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                PlayerData playerData = new PlayerData(player);
                if (playerData.getPrestige().compareTo(BigInteger.valueOf(StaticVars.PRESTIGE_MINE_REQUIREMENTS[14])) > -1) {
                    Warps.warpPrestigeMine(player, 14);
                } else player.sendMessage(ChatColor.RED + "You do not have this mine unlocked!");
            }
            @Override
            public void item47Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                PlayerData playerData = new PlayerData(player);
                if (playerData.getPlayerRanks().contains("warrior")) {
                    Warps.warpRankMine(player, 0);
                } else player.sendMessage(ChatColor.RED + "You do not have this mine unlocked!");
            }
            @Override
            public void item48Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                PlayerData playerData = new PlayerData(player);
                if (playerData.getPlayerRanks().contains("master")) {
                    Warps.warpRankMine(player, 1);
                } else player.sendMessage(ChatColor.RED + "You do not have this mine unlocked!");
            }
            @Override
            public void item49Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                PlayerData playerData = new PlayerData(player);
                if (playerData.getPlayerRanks().contains("mythic")) {
                    Warps.warpRankMine(player, 2);
                } else player.sendMessage(ChatColor.RED + "You do not have this mine unlocked!");
            }
            @Override
            public void item50Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                PlayerData playerData = new PlayerData(player);
                if (playerData.getPlayerRanks().contains("static")) {
                    Warps.warpRankMine(player, 3);
                } else player.sendMessage(ChatColor.RED + "You do not have this mine unlocked!");
            }
            @Override
            public void item51Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                PlayerData playerData = new PlayerData(player);
                if (playerData.getPlayerRanks().contains("staticp")) {
                    Warps.warpRankMine(player, 4);
                } else player.sendMessage(ChatColor.RED + "You do not have this mine unlocked!");
            }
            @Override
            public void item45Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                PlayerData playerData = new PlayerData(player);
                Warps.warEventMine(player);
            }
        };
        guiPage.identifier = "warpsMines";
        guiPage.guiTitle = ChatColor.translateAlternateColorCodes('&', "&dMine Warps");
        guiPage.onCloseGoToMenu = "warps";
        guiPage.register();
    }
}
