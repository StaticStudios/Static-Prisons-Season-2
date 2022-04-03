package me.staticstudios.prisons.gui.menus;

import me.staticstudios.prisons.data.serverData.PlayerData;
import me.staticstudios.prisons.gui.GUI;
import me.staticstudios.prisons.gui.GUIPage;
import me.staticstudios.prisons.chat.ChatTags;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.ArrayList;

public class ChatTagsMenus {
    //Pick a tag menu
    public static void tag() {
        GUIPage guiPage = new GUIPage() {
            @Override
            public void onOpen(Player player) {
                PlayerData playerData = new PlayerData(player);
                menuItems = new ArrayList<>();
                menuItems.add(GUI.createMenuItem(identifier, Material.PAPER, ChatColor.RED + "Remove all active chat tags", ChatColor.GRAY + "This will make them not display in chat", ChatColor.GRAY + "You may re-enable them later."));
                for (String tag : playerData.getChatTags()) {
                    menuItems.add(GUI.createEnchantedMenuItem(identifier, Material.NAME_TAG, ChatTags.getChatTagFromID(tag), ChatColor.GRAY + "Click to apply/remove this tag"));
                }
            }
            @Override
            public void item0Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                PlayerData playerData = new PlayerData(player);
                playerData.setChatTag1("");
                playerData.setChatTag2("");
                player.sendMessage(ChatColor.GREEN + "Done! Send a message in chat to see how you look!");
                player.closeInventory();
            }
            @Override
            public void itemClicked(InventoryClickEvent e) {
                if (e.getSlot() == 0) return;
                Player player = (Player) e.getWhoClicked();
                PlayerData playerData = new PlayerData(player);
                GUI.getGUIPage("chatTagsEnable").args = playerData.getChatTags().get(e.getSlot() - 1);
                GUI.getGUIPage("chatTagsEnable").open(player);
            }
        };
        guiPage.identifier = "chatTags";
        guiPage.guiTitle = ChatColor.translateAlternateColorCodes('&', "&dYour Chat Tags");
        guiPage.onCloseGoToMenu = "main";
        guiPage.register();
    }

    //Enable tag
    public static void enableTag() {
        GUIPage guiPage = new GUIPage() {
            String tagID;
            @Override
            public void onOpen(Player player) {
                PlayerData playerData = new PlayerData(player);
                tagID = args;
                menuItems = new ArrayList<>();
                for (int i = 0; i < 8; i++) menuItems.add(GUI.createDarkGrayPlaceholderItem());
                menuItems.set(3, GUI.createMenuItem(identifier, Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "Set tag in slot #1", ChatColor.GRAY + "Current tag in slot #1: " + ChatTags.getChatTagFromID(playerData.getChatTag1())));
                menuItems.set(4, GUI.createEnchantedMenuItem(identifier, Material.NAME_TAG, ChatTags.getChatTagFromID(tagID), ""));
                menuItems.set(5, GUI.createMenuItem(identifier, Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "Set tag in slot #2", ChatColor.GRAY + "Current tag in slot #2: " + ChatTags.getChatTagFromID(playerData.getChatTag2())));
            }
            @Override
            public void item3Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                PlayerData playerData = new PlayerData(player);
                if (playerData.getChatTag2().equals(tagID)) {
                    player.sendMessage(ChatColor.RED + "You cannot apply the same tag twice! You already have this tag applied in slot #2.");
                    GUI.getGUIPage("chatTags").open(player);
                    return;
                }
                playerData.setChatTag1(tagID);
                player.sendMessage(ChatColor.GREEN + "Done! Send a message in chat to see how you look!");
                GUI.getGUIPage("chatTags").open(player);
            }
            @Override
            public void item5Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                PlayerData playerData = new PlayerData(player);
                if (playerData.getChatTag1().equals(tagID)) {
                    player.sendMessage(ChatColor.RED + "You cannot apply the same tag twice! You already have this tag applied in slot #1.");
                    GUI.getGUIPage("chatTags").open(player);
                    return;
                }
                playerData.setChatTag2(tagID);
                player.sendMessage(ChatColor.GREEN + "Done! Send a message in chat to see how you look!");
                GUI.getGUIPage("chatTags").open(player);
            }
        };
        guiPage.identifier = "chatTagsEnable";
        guiPage.guiTitle = ChatColor.translateAlternateColorCodes('&', "&dEnable/Disable A Chat Tag");
        guiPage.onCloseGoToMenu = "chatTags";
        guiPage.register();
    }
}
