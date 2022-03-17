package me.staticstudios.prisons.gameplay.gui.menus;

import me.staticstudios.prisons.gameplay.customItems.CustomItems;
import me.staticstudios.prisons.core.data.serverData.PlayerData;
import me.staticstudios.prisons.gameplay.gui.GUI;
import me.staticstudios.prisons.gameplay.gui.GUIPage;
import me.staticstudios.prisons.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.time.Instant;
import java.util.*;

public class DailyRewardsMenus {
    //Main menu
    public static void main() {
        GUIPage guiPage = new GUIPage() {
            @Override
            public void onOpen(Player player) {
                PlayerData playerData = new PlayerData(player);
                menuItems = new ArrayList<>();
                menuItems.add(GUI.createLightGrayPlaceholderItem());
                menuItems.add(GUI.createLightGrayPlaceholderItem());
                menuItems.add(GUI.createLightGrayPlaceholderItem());
                menuItems.add(GUI.createLightGrayPlaceholderItem());
                if (playerData.getClaimedDailyRewardsAt() + 1000 * 60 * 60 * 24 < Instant.now().toEpochMilli()) {
                    menuItems.add(GUI.createEnchantedMenuItem(identifier, Material.DIAMOND, ChatColor.GREEN + "Claim Daily Rewards", ChatColor.WHITE + "Claim this every 24h", ChatColor.WHITE + "to get a random crate key!"));
                } else {
                    long hours = ((playerData.getClaimedDailyRewardsAt() + 1000 * 60 * 60 * 24) - Instant.now().toEpochMilli()) / (1000 * 60 * 60);
                    menuItems.add(GUI.createMenuItem(identifier, Material.REDSTONE, ChatColor.GREEN + "Claim Daily Rewards", ChatColor.WHITE + "Claim this every 24h", ChatColor.WHITE + "to get a random crate key!", "", ChatColor.RED + "You can claim this in: " + hours + " hour(s)"));
                }
            }
            @Override
            public void item4Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                PlayerData playerData = new PlayerData(player);
                if (playerData.getClaimedDailyRewardsAt() + 1000 * 60 * 60 * 24 < Instant.now().toEpochMilli()) {
                    //if (!LinkHandler.checkIfLinkedFromUUID(player.getUniqueId().toString())) {
                    //    player.sendMessage(ChatColor.RED + "You must link your discord account to claim daily rewards! type \"/discord link\"");
                    //    return;
                    //}
                    ItemStack item = null;
                    switch (Utils.randomInt(1, 10)) {
                        case 1 -> item = CustomItems.getCommonCrateKey(2);
                        case 2, 3 -> item = CustomItems.getCommonCrateKey(4);
                        case 4, 5 -> item = CustomItems.getRareCrateKey(1);
                        case 6, 7 -> item = CustomItems.getRareCrateKey(2);
                        case 8, 9 -> item = CustomItems.getLegendaryCrateKey(1);
                        case 10 -> item = CustomItems.getLegendaryCrateKey(2);
                    }
                    Utils.addItemToPlayersInventoryAndDropExtra(player, item);
                    player.sendMessage(ChatColor.WHITE + "You've been given " + item.getAmount() + "x " + Utils.getPrettyItemName(item));
                    playerData.setClaimedDailyRewardsAt(Instant.now().toEpochMilli());
                    player.closeInventory();
                } else {
                    long hours = ((playerData.getClaimedDailyRewardsAt() + 1000 * 60 * 60 * 24) - Instant.now().toEpochMilli()) / (1000 * 60 * 60);
                    player.sendMessage(ChatColor.RED + "You can claim this again in: " + hours + " hour(s)!");
                }
            }
        };
        guiPage.identifier = "dailyRewards";
        guiPage.guiTitle = ChatColor.translateAlternateColorCodes('&', "&dDaily Rewards");
        guiPage.onCloseGoToMenu = "main";
        guiPage.register();
    }
}
