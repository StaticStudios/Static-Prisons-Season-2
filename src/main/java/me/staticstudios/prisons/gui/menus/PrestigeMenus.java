package me.staticstudios.prisons.gui.menus;

import me.staticstudios.prisons.newData.dataHandling.PlayerData;
import me.staticstudios.prisons.gui.GUI;
import me.staticstudios.prisons.gui.GUIPage;
import me.staticstudios.prisons.misc.Warps;
import me.staticstudios.prisons.rankup.RankUpPrices;
import me.staticstudios.prisons.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.math.BigInteger;
import java.util.ArrayList;

public class PrestigeMenus {
    //Main menu
    public static void main() {
        GUIPage guiPage = new GUIPage() {
            @Override
            public void onOpen(Player player) {
                PlayerData playerData = new PlayerData(player);
                menuItems = new ArrayList<>();
                int amount = 1;
                menuItems.add(GUI.createEnchantedMenuItem(identifier, Material.COAL, ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Prestige 1 Time","", ChatColor.AQUA + "" + ChatColor.BOLD + "Costs:", ChatColor.AQUA + "" + ChatColor.BOLD + "│" + ChatColor.AQUA + " $" + Utils.prettyNum(RankUpPrices.getPrestigePrice(playerData.getPrestige(), amount)), ChatColor.AQUA + "" + ChatColor.BOLD + "│" + ChatColor.AQUA + " $" + Utils.addCommasToNumber(RankUpPrices.getPrestigePrice(playerData.getPrestige(), amount))));
                amount = 5;
                menuItems.add(GUI.createEnchantedMenuItem(identifier, Material.RAW_IRON, ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Prestige " + Utils.addCommasToNumber(amount) + " Times", "", ChatColor.AQUA + "" + ChatColor.BOLD + "Costs:", ChatColor.AQUA + "" + ChatColor.BOLD + "│" + ChatColor.AQUA + " $" + Utils.prettyNum(RankUpPrices.getPrestigePrice(playerData.getPrestige(), amount)), ChatColor.AQUA + "" + ChatColor.BOLD + "│" + ChatColor.AQUA + " $" + Utils.addCommasToNumber(RankUpPrices.getPrestigePrice(playerData.getPrestige(), amount))));
                amount = 10;
                menuItems.add(GUI.createEnchantedMenuItem(identifier, Material.RAW_GOLD, ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Prestige " + Utils.addCommasToNumber(amount) + " Times", "", ChatColor.AQUA + "" + ChatColor.BOLD + "Costs:", ChatColor.AQUA + "" + ChatColor.BOLD + "│" + ChatColor.AQUA + " $" + Utils.prettyNum(RankUpPrices.getPrestigePrice(playerData.getPrestige(), amount)), ChatColor.AQUA + "" + ChatColor.BOLD + "│" + ChatColor.AQUA + " $" + Utils.addCommasToNumber(RankUpPrices.getPrestigePrice(playerData.getPrestige(), amount))));
                amount = 50;
                menuItems.add(GUI.createEnchantedMenuItem(identifier, Material.DIAMOND, ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Prestige " + Utils.addCommasToNumber(amount) + " Times", "", ChatColor.AQUA + "" + ChatColor.BOLD + "Costs:", ChatColor.AQUA + "" + ChatColor.BOLD + "│" + ChatColor.AQUA + " $" + Utils.prettyNum(RankUpPrices.getPrestigePrice(playerData.getPrestige(), amount)), ChatColor.AQUA + "" + ChatColor.BOLD + "│" + ChatColor.AQUA + " $" + Utils.addCommasToNumber(RankUpPrices.getPrestigePrice(playerData.getPrestige(), amount))));
                amount = 100;
                menuItems.add(GUI.createEnchantedMenuItem(identifier, Material.EMERALD, ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Prestige " + Utils.addCommasToNumber(amount) + " Times", "", ChatColor.AQUA + "" + ChatColor.BOLD + "Costs:", ChatColor.AQUA + "" + ChatColor.BOLD + "│" + ChatColor.AQUA + " $" + Utils.prettyNum(RankUpPrices.getPrestigePrice(playerData.getPrestige(), amount)), ChatColor.AQUA + "" + ChatColor.BOLD + "│" + ChatColor.AQUA + " $" + Utils.addCommasToNumber(RankUpPrices.getPrestigePrice(playerData.getPrestige(), amount))));
                amount = 500;
                menuItems.add(GUI.createEnchantedMenuItem(identifier, Material.NETHERITE_INGOT, ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Prestige " + Utils.addCommasToNumber(amount) + " Times", "", ChatColor.AQUA + "" + ChatColor.BOLD + "Costs:", ChatColor.AQUA + "" + ChatColor.BOLD + "│" + ChatColor.AQUA + " $" + Utils.prettyNum(RankUpPrices.getPrestigePrice(playerData.getPrestige(), amount)), ChatColor.AQUA + "" + ChatColor.BOLD + "│" + ChatColor.AQUA + " $" + Utils.addCommasToNumber(RankUpPrices.getPrestigePrice(playerData.getPrestige(), amount))));
                amount = 1000;
                menuItems.add(GUI.createEnchantedMenuItem(identifier, Material.AMETHYST_CLUSTER, ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Prestige " + Utils.addCommasToNumber(amount) + " Times", "", ChatColor.AQUA + "" + ChatColor.BOLD + "Costs:", ChatColor.AQUA + "" + ChatColor.BOLD + "│" + ChatColor.AQUA + " $" + Utils.prettyNum(RankUpPrices.getPrestigePrice(playerData.getPrestige(), amount)), ChatColor.AQUA + "" + ChatColor.BOLD + "│" + ChatColor.AQUA + " $" + Utils.addCommasToNumber(RankUpPrices.getPrestigePrice(playerData.getPrestige(), amount))));
                amount = 5000;
                menuItems.add(GUI.createEnchantedMenuItem(identifier, Material.NETHER_STAR, ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Prestige " + Utils.addCommasToNumber(amount) + " Times", "", ChatColor.AQUA + "" + ChatColor.BOLD + "Costs:", ChatColor.AQUA + "" + ChatColor.BOLD + "│" + ChatColor.AQUA + " $" + Utils.prettyNum(RankUpPrices.getPrestigePrice(playerData.getPrestige(), amount)), ChatColor.AQUA + "" + ChatColor.BOLD + "│" + ChatColor.AQUA + " $" + Utils.addCommasToNumber(RankUpPrices.getPrestigePrice(playerData.getPrestige(), amount))));
            }

            void prestige(Player player, int amount) {
                PlayerData playerData = new PlayerData(player);
                if (playerData.getMineRank() < 25) {
                    player.sendMessage(ChatColor.RED + "You must be mine rank " + ChatColor.BOLD + "Z " + ChatColor.RED + "in order to prestige!");
                    return;
                }
                if (playerData.getMoney().compareTo(RankUpPrices.getPrestigePrice(playerData.getPrestige(), amount)) > -1) {
                    playerData.removeMoney(RankUpPrices.getPrestigePrice(playerData.getPrestige(), amount));
                    playerData.addPrestige(BigInteger.valueOf(amount));
                    playerData.setMineRank(0);
                    player.sendMessage(ChatColor.AQUA + "You have just prestiged!");
                    open(player);
                    Warps.warpToSpawn(player);
                } else player.sendMessage(ChatColor.RED + "You do not have enough money to prestige!");
            }
            @Override
            public void item0Clicked(InventoryClickEvent e) {
                prestige((Player) e.getWhoClicked(), 1);
            }
            @Override
            public void item1Clicked(InventoryClickEvent e) {
                prestige((Player) e.getWhoClicked(), 5);
            }
            @Override
            public void item2Clicked(InventoryClickEvent e) {
                prestige((Player) e.getWhoClicked(), 10);
            }
            @Override
            public void item3Clicked(InventoryClickEvent e) {
                prestige((Player) e.getWhoClicked(), 50);
            }
            @Override
            public void item4Clicked(InventoryClickEvent e) {
                prestige((Player) e.getWhoClicked(), 100);
            }
            @Override
            public void item5Clicked(InventoryClickEvent e) {
                prestige((Player) e.getWhoClicked(), 500);
            }
            @Override
            public void item6Clicked(InventoryClickEvent e) {
                prestige((Player) e.getWhoClicked(), 1000);
            }
            @Override
            public void item7Clicked(InventoryClickEvent e) {
                prestige((Player) e.getWhoClicked(), 5000);
            }
        };
        guiPage.identifier = "prestige";

        guiPage.guiTitle = ChatColor.translateAlternateColorCodes('&', "&dPrestige");
        guiPage.onCloseGoToMenu = "main";
        guiPage.register();
    }
}
