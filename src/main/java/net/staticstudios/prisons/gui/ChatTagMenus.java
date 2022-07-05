package net.staticstudios.prisons.gui;

import net.staticstudios.gui.GUICreator;
import net.staticstudios.gui.GUIUtils;
import net.staticstudios.prisons.chat.ChatTags;
import net.staticstudios.prisons.data.PlayerData;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;

public class ChatTagMenus extends GUIUtils {

    public static void manageTags(Player player) {
        GUICreator c = new GUICreator(9, "Chat Tags");
        PlayerData playerData = new PlayerData(player);
        if (playerData.getChatTag1().equals("")) {
            c.setItem(3, c.createButton(Material.NAME_TAG, "&6Slot #1 &c(Inactive)", List.of("Select a chat tag to set in this slot"), (p, t) -> {
                activateTag(p, 1);
                p.sendMessage(ChatColor.GREEN + "Select a chat tag to activate in slot #1");
            }));
        } else c.setItem(3, ench(c.createButton(Material.NAME_TAG, "&6Slot #1 &a(Active)", List.of("Active tag: " + ChatTags.getFromID(playerData.getChatTag1()), "Click to remove this tag"), (p, t) -> {
            playerData.setChatTag1("");
            manageTags(p);
            p.sendMessage(ChatColor.RED + "You deactivated your chat tag in slot #1");
            })));
        if (playerData.getChatTag2().equals("")) {
            c.setItem(5, c.createButton(Material.NAME_TAG, "&6Slot #2 &c(Inactive)", List.of("Select a chat tag to set in this slot"), (p, t) -> {
                activateTag(p, 2);
                p.sendMessage(ChatColor.GREEN + "Select a chat tag to activate in slot #2");
            }));
        } else c.setItem(5, ench(c.createButton(Material.NAME_TAG, "&6Slot #2 &a(Active)", List.of("Active tag: " + ChatTags.getFromID(playerData.getChatTag2()), "Click to remove this tag"), (p, t) -> {
            playerData.setChatTag2("");
            manageTags(p);
            p.sendMessage(ChatColor.RED + "You deactivated your chat tag in slot #2");
        })));
        c.fill(createGrayPlaceHolder());
        c.setOnCloseRun((p, t) -> MainMenus.open(p));
        c.open(player);
    }

    public static void activateTag(Player player, int slot) {
        GUICreator c = new GUICreator(36, "Select a tag");
        PlayerData playerData = new PlayerData(player);
        for (String tag : ChatTags.TAGS.keySet()) {
            if (playerData.getChatTags().contains(tag)) {
                c.addItem(ench(c.createButton(Material.NAME_TAG, ChatTags.getFromID(tag), List.of("Click to activate this tag in slot #" + slot, "&aYou have this tag unlocked!"), (p, t) -> {
                    if (slot == 1) {
                        playerData.setChatTag1(tag);
                    } else playerData.setChatTag2(tag);
                    manageTags(p);
                    p.sendMessage(ChatColor.GREEN + "You activated the chat tag " + ChatTags.getFromID(tag) + ChatColor.GREEN + "in slot #" + slot);
                })));
            } else {
                c.addItem(c.createButton(Material.NAME_TAG, ChatTags.getFromID(tag), List.of("&cYou do not have this tag unlocked!"), (p, t) -> {
                    p.sendMessage(ChatColor.RED + "You do not have this tag unlocked! Consider selecting a different tag?\n\n" + ChatColor.AQUA + "Chat tags can be purchased on our store.");
                }));
            }
        }
        c.setOnCloseRun((p, t) -> manageTags(p));
        c.open(player);
    }
}
