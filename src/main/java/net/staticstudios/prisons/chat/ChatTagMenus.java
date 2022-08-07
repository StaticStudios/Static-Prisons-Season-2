package net.staticstudios.prisons.chat;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.staticstudios.gui.GUICreator;
import net.staticstudios.gui.GUIUtils;
import net.staticstudios.prisons.chat.ChatTags;
import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.gui.MainMenus;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;

public class ChatTagMenus extends GUIUtils {

    public static void manageTags(Player player) {
        GUICreator c = new GUICreator(9, "Chat Tags");
        PlayerData playerData = new PlayerData(player);
        if (playerData.getChatTag1().equals("")) {
            c.setItem(3, c.createButton(Material.NAME_TAG,
                    Component.text("Slot #1 ").color(NamedTextColor.GOLD).append(Component.text(" (inactive)").color(NamedTextColor.RED)),
                    List.of(Component.text("Select a chat tag to set in this slot")),
                    (p, t) -> {
                        activateTag(p, 1);
                        p.sendMessage(Component.text("Select a chat tag to activate in slot #1").color(NamedTextColor.GREEN));
                    }));
        } else
            c.setItem(3, ench(c.createButton(Material.NAME_TAG,
                    Component.text("Slot #1 ").color(NamedTextColor.GOLD).append(Component.text("(Active)").color(NamedTextColor.GREEN)),
                    List.of(Component.text("Active tag: ").append(ChatTags.getFromID(playerData.getChatTag1())), Component.text("Click to remove this tag")),
                    (p, t) -> {
                        playerData.setChatTag1("");
                        manageTags(p);
                        p.sendMessage(Component.text("You deactivated your chat tag in slot #1").color(NamedTextColor.RED));
                    })));
        if (playerData.getChatTag2().equals("")) {
            c.setItem(5, c.createButton(Material.NAME_TAG,
                    Component.text("Slot #2 ").color(NamedTextColor.GOLD).append(Component.text(" (Inactive)").color(NamedTextColor.RED)),
                    List.of(Component.text("Select a chat tag to set in this slot")),
                    (p, t) -> {
                        activateTag(p, 2);
                        p.sendMessage(Component.text("Select a chat tag to activate in slot #2").color(NamedTextColor.GREEN));
                    }));
        } else
            c.setItem(5, ench(c.createButton(Material.NAME_TAG,
                    Component.text("Slot #2 ").color(NamedTextColor.GOLD).append(Component.text("(Active)").color(NamedTextColor.GREEN)),
                    List.of(Component.text("Active tag: ").append(ChatTags.getFromID(playerData.getChatTag2())), Component.text("Click to remove this tag")),
                    (p, t) -> {
                        playerData.setChatTag2("");
                        manageTags(p);
                        p.sendMessage(Component.text("You deactivated your chat tag in slot #2").color(NamedTextColor.RED));
                    })));
        c.fill(createGrayPlaceHolder());
        c.setOnCloseRun((p, t) -> MainMenus.open(p));
        c.open(player);
    }

    public static void activateTag(Player player, int slot) {
        GUICreator c = new GUICreator(36, "Select a new tag");
        PlayerData playerData = new PlayerData(player);
        for (String tag : ChatTags.getAllKeys()) {
            if (playerData.getChatTags().contains(tag)) {
                c.addItem(ench(c.createButton(Material.NAME_TAG,
                        ChatTags.getFromID(tag),
                        List.of(Component.text("Click to activate this tag in slot #").append(Component.text(slot)), Component.text("You have this tag unlocked!").color(NamedTextColor.GREEN)),
                        (p, t) -> {
                            if (slot == 1) {
                                playerData.setChatTag1(tag);
                            } else playerData.setChatTag2(tag);
                            manageTags(p);
                            p.sendMessage(Component.text("You activated the chat tag ").color(NamedTextColor.GREEN).append(ChatTags.getFromID(tag)).append(Component.text(" in slot #").append(Component.text(slot)).color(NamedTextColor.GREEN)));
                        })));
            } else {
                c.addItem(c.createButton(Material.NAME_TAG,
                        ChatTags.getFromID(tag),
                        List.of(Component.text("You do not have this tag unlocked!").color(NamedTextColor.RED)),
                        (p, t) -> p.sendMessage(Component.text("You do not have this tag unlocked! Consider selecting a different tag?").color(NamedTextColor.RED)
                                .append(Component.newline()).append(Component.newline())
                                .append(Component.text("Chat tags can be purchased on our store.").color(NamedTextColor.AQUA)))));
            }
        }
        c.setOnCloseRun((p, t) -> manageTags(p));
        c.open(player);
    }
}
