package me.staticstudios.prisons.gameplay.gui.menus;

import me.staticstudios.prisons.core.data.serverData.PlayerData;
import me.staticstudios.prisons.gameplay.gui.GUI;
import me.staticstudios.prisons.gameplay.gui.GUIPage;
import me.staticstudios.prisons.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.math.BigInteger;
import java.util.ArrayList;

public class BackpackMenus {
    //Main menu
    public static void main() {
        int slotCost = 1;
        int slotsPerCost = 1000;
        GUIPage guiPage = new GUIPage() {
            @Override
            public void onOpen(Player player) {
                PlayerData playerData = new PlayerData(player);
                menuItems = new ArrayList<>();
                BigInteger amount = BigInteger.valueOf(slotsPerCost);
                menuItems.add(GUI.createMenuItem(identifier, Material.CHEST, ChatColor.GREEN + "" + ChatColor.BOLD + "Add " + Utils.addCommasToNumber(amount) + " Slot","",
                        ChatColor.AQUA + "Current Size: " + ChatColor.WHITE + Utils.addCommasToNumber(playerData.getBackpackSize()) + " Slots",
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.addCommasToNumber(BigInteger.valueOf(slotCost).multiply(amount.divide(BigInteger.valueOf(slotsPerCost)))) + " Tokens",
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Size: a lot"));
                menuItems.get(0).setAmount(1);
                amount = BigInteger.valueOf(slotsPerCost * 10);
                menuItems.add(GUI.createMenuItem(identifier, Material.CHEST, ChatColor.GREEN + "" + ChatColor.BOLD + "Add " + Utils.addCommasToNumber(amount) + " Slots","",
                        ChatColor.AQUA + "Current Size: " + ChatColor.WHITE + Utils.addCommasToNumber(playerData.getBackpackSize()) + " Slots",
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.addCommasToNumber(BigInteger.valueOf(slotCost).multiply(amount.divide(BigInteger.valueOf(slotsPerCost)))) + " Tokens",
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Size: a lot"));
                menuItems.get(1).setAmount(2);
                amount = BigInteger.valueOf(slotsPerCost * 100);
                menuItems.add(GUI.createMenuItem(identifier, Material.CHEST, ChatColor.GREEN + "" + ChatColor.BOLD + "Add " + Utils.addCommasToNumber(amount) + " Slots","",
                        ChatColor.AQUA + "Current Size: " + ChatColor.WHITE + Utils.addCommasToNumber(playerData.getBackpackSize()) + " Slots",
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.addCommasToNumber(BigInteger.valueOf(slotCost).multiply(amount.divide(BigInteger.valueOf(slotsPerCost)))) + " Tokens",
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Size: a lot"));
                menuItems.get(2).setAmount(3);
                amount = BigInteger.valueOf(slotsPerCost * 1000);
                menuItems.add(GUI.createMenuItem(identifier, Material.CHEST, ChatColor.GREEN + "" + ChatColor.BOLD + "Add " + Utils.addCommasToNumber(amount) + " Slots","",
                        ChatColor.AQUA + "Current Size: " + ChatColor.WHITE + Utils.addCommasToNumber(playerData.getBackpackSize()) + " Slots",
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.addCommasToNumber(BigInteger.valueOf(slotCost).multiply(amount.divide(BigInteger.valueOf(slotsPerCost)))) + " Tokens",
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Size: a lot"));
                menuItems.get(3).setAmount(4);
                amount = BigInteger.valueOf(slotsPerCost * 10000);
                menuItems.add(GUI.createMenuItem(identifier, Material.CHEST, ChatColor.GREEN + "" + ChatColor.BOLD + "Add " + Utils.addCommasToNumber(amount) + " Slots","",
                        ChatColor.AQUA + "Current Size: " + ChatColor.WHITE + Utils.addCommasToNumber(playerData.getBackpackSize()) + " Slots",
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.addCommasToNumber(BigInteger.valueOf(slotCost).multiply(amount.divide(BigInteger.valueOf(slotsPerCost)))) + " Tokens",
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Size: a lot"));
                menuItems.get(4).setAmount(5);
                amount = BigInteger.valueOf(slotsPerCost * 100000);
                menuItems.add(GUI.createMenuItem(identifier, Material.CHEST, ChatColor.GREEN + "" + ChatColor.BOLD + "Add " + Utils.addCommasToNumber(amount) + " Slots","",
                        ChatColor.AQUA + "Current Size: " + ChatColor.WHITE + Utils.addCommasToNumber(playerData.getBackpackSize()) + " Slots",
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.addCommasToNumber(BigInteger.valueOf(slotCost).multiply(amount.divide(BigInteger.valueOf(slotsPerCost)))) + " Tokens",
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Size: a lot"));
                menuItems.get(5).setAmount(6);
                amount = BigInteger.valueOf(slotsPerCost * 1000000);
                menuItems.add(GUI.createMenuItem(identifier, Material.CHEST, ChatColor.GREEN + "" + ChatColor.BOLD + "Add " + Utils.addCommasToNumber(amount) + " Slots","",
                        ChatColor.AQUA + "Current Size: " + ChatColor.WHITE + Utils.addCommasToNumber(playerData.getBackpackSize()) + " Slots",
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.addCommasToNumber(BigInteger.valueOf(slotCost).multiply(amount.divide(BigInteger.valueOf(slotsPerCost)))) + " Tokens",
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Size: a lot"));
                menuItems.get(6).setAmount(7);
                amount = playerData.getTokens().divide(BigInteger.valueOf(slotCost)).multiply(BigInteger.valueOf(slotsPerCost));
                menuItems.add(GUI.createMenuItem(identifier, Material.CHEST, ChatColor.GREEN + "" + ChatColor.BOLD + "Add " + Utils.addCommasToNumber(amount) + " Slots", ChatColor.GRAY + "" + ChatColor.ITALIC + "Max you can buy with your current tokens", "",
                        ChatColor.AQUA + "Current Size: " + ChatColor.WHITE + Utils.addCommasToNumber(playerData.getBackpackSize()) + " Slots",
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.addCommasToNumber(BigInteger.valueOf(slotCost).multiply(amount.divide(BigInteger.valueOf(slotsPerCost)))) + " Tokens",
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Size: a lot"));
                menuItems.get(7).setAmount(64);
            }
            void upgrade(Player player, BigInteger slotsToBuy) {
                PlayerData playerData = new PlayerData(player);
                BigInteger price = slotsToBuy.multiply(BigInteger.valueOf(slotCost)).divide(BigInteger.valueOf(slotsPerCost));
                if (playerData.getTokens().compareTo(price) > -1) {
                    playerData.removeTokens(price);
                    playerData.setBackpackSize(playerData.getBackpackSize().add(slotsToBuy));
                    playerData.updateBackpackIsFull(); //Cannot add blocks to backpack if backpack is full, then upgraded, then this method is not called
                    player.sendMessage(ChatColor.AQUA + "You've successfully upgraded your backpack!");
                    open(player);
                } else player.sendMessage(ChatColor.RED + "You do not have enough tokens to prestige!");
            }
            @Override
            public void item0Clicked(InventoryClickEvent e) {
                upgrade((Player) e.getWhoClicked(), BigInteger.valueOf(slotsPerCost));
            }
            @Override
            public void item1Clicked(InventoryClickEvent e) {
                upgrade((Player) e.getWhoClicked(), BigInteger.valueOf(slotsPerCost * 10));
            }
            @Override
            public void item2Clicked(InventoryClickEvent e) {
                upgrade((Player) e.getWhoClicked(), BigInteger.valueOf(slotsPerCost * 100));
            }
            @Override
            public void item3Clicked(InventoryClickEvent e) {
                upgrade((Player) e.getWhoClicked(), BigInteger.valueOf(slotsPerCost * 1000));
            }
            @Override
            public void item4Clicked(InventoryClickEvent e) {
                upgrade((Player) e.getWhoClicked(), BigInteger.valueOf(slotsPerCost * 10000));
            }
            @Override
            public void item5Clicked(InventoryClickEvent e) {
                upgrade((Player) e.getWhoClicked(), BigInteger.valueOf(slotsPerCost * 100000));
            }
            @Override
            public void item6Clicked(InventoryClickEvent e) {
                upgrade((Player) e.getWhoClicked(), BigInteger.valueOf(slotsPerCost * 1000000));
            }
            @Override
            public void item7Clicked(InventoryClickEvent e) {
                upgrade((Player) e.getWhoClicked(), new PlayerData((Player) e.getWhoClicked()).getTokens().divide(BigInteger.valueOf(slotCost)).multiply(BigInteger.valueOf(slotsPerCost)));
            }
        };
        guiPage.identifier = "upgradeBackpack";
        guiPage.guiTitle = ChatColor.translateAlternateColorCodes('&', "&dUpgrade Your Backpack");
        guiPage.onCloseGoToMenu = "main";
        guiPage.register();
    }
}
