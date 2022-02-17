package me.staticstudios.prisons.gui.menus;

import me.staticstudios.prisons.data.serverData.PlayerData;
import me.staticstudios.prisons.gui.GUI;
import me.staticstudios.prisons.gui.GUIPage;
import me.staticstudios.prisons.mines.MineManager;
import me.staticstudios.prisons.mines.PrivateMine;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.time.Instant;
import java.util.ArrayList;

public class PrivateMinesMenus {
    //settings menu
    public static void main() {
        GUIPage guiPage = new GUIPage() {
            @Override
            public void onOpen(Player player) {
                PlayerData playerData = new PlayerData(player);
                menuItems = new ArrayList<>();
                menuItems.add(GUI.createEnchantedMenuItem(identifier, Material.COMPASS, ChatColor.AQUA + "" + ChatColor.BOLD + "Go To", ChatColor.GRAY + "" + ChatColor.ITALIC + "Warp to your private mine!"));
                menuItems.add(GUI.createEnchantedMenuItem(identifier, new PlayerData(player).getPrivateMineMat(), ChatColor.GREEN + "" + ChatColor.BOLD + "Change Block", ChatColor.GRAY + "" + ChatColor.ITALIC + "Change the block that is in your private mine!"));
                menuItems.add(GUI.createLightGrayPlaceholderItem());
                menuItems.add(GUI.createLightGrayPlaceholderItem());
                menuItems.add(GUI.createLightGrayPlaceholderItem());
                menuItems.add(GUI.createLightGrayPlaceholderItem());
                menuItems.add(GUI.createLightGrayPlaceholderItem());
                menuItems.add(GUI.createEnchantedMenuItem(identifier, Material.AMETHYST_SHARD, ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Refill", ChatColor.GRAY + "" + ChatColor.ITALIC + "Refill your private mine!", "", ChatColor.GRAY + "" + ChatColor.ITALIC + "Type \"/pmine refill\" to refill your private-mine!"));
            }

            @Override
            public void item0Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                PlayerData playerData = new PlayerData(player);
                if (!MineManager.checkIfMineExists("privateMine-" + player.getUniqueId())) {
                    PrivateMine.create(playerData.getPrivateMineSquareSize(), playerData.getPrivateMineMat(), player);
                } else MineManager.getPrivateMine("privateMine-" + player.getUniqueId()).goTo(player);
            }

            @Override
            public void item7Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                PlayerData playerData = new PlayerData(player);
                if (!MineManager.checkIfMineExists("privateMine-" + player.getUniqueId())) {
                    PrivateMine.create(playerData.getPrivateMineSquareSize(), playerData.getPrivateMineMat(), player);
                } else {
                    PrivateMine mine = MineManager.getPrivateMine("privateMine-" + player.getUniqueId());
                    if (mine.getLastRefilledAt() + 30 < Instant.now().getEpochSecond()) {
                        mine.refill();
                    } else player.sendMessage(ChatColor.RED + "Please wait " + (mine.getLastRefilledAt() + 30 - Instant.now().getEpochSecond()) + " second(s) before refilling this mine!");
                }
            }
        };
        guiPage.identifier = "privateMines";
        guiPage.guiTitle = ChatColor.translateAlternateColorCodes('&', "&dYour Private Mine");
        guiPage.onCloseGoToMenu = "main";
        guiPage.register();
    }
}
