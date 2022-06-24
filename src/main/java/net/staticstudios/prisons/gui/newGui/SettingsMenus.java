package net.staticstudios.prisons.gui.newGui;

import net.md_5.bungee.api.ChatColor;
import net.staticstudios.gui.GUICreator;
import net.staticstudios.gui.GUIUtils;
import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;

public class SettingsMenus extends GUIUtils {

    public static void open(Player player, boolean fromCommand) {
        GUICreator c = new GUICreator(9, "Your Settings");
        PlayerData playerData = new PlayerData(player);
        if (playerData.getIsAutoSellEnabled()) {
            c.setItem(1, ench(c.createButton(Material.DIAMOND, "&bAuto Sell &8(&aEnabled&8)", List.of("&oAutomatically sell your backpack when it's full", "", "&cClick to disable"), (p, t) -> {
                playerData.setIsAutoSellEnabled(false);
                open(p, fromCommand);
            })));
        } else c.setItem(1, c.createButton(Material.DIAMOND, "&bAuto Sell &8(&cDisabled&8)", List.of("&oAutomatically sell your backpack when it's full", "", "&aClick to enable"), (p, t) -> {
            if (PrisonUtils.Players.canAutoSell(p)) playerData.setIsAutoSellEnabled(true);
            else p.sendMessage(ChatColor.RED + "You cannot enable auto sell!");
                    open(p, fromCommand);
                }));
        if (!playerData.getAreTipsDisabled()) {
            c.setItem(3, ench(c.createButton(Material.EMERALD, "&eTips &8(&aEnabled&8)", List.of("&oReceive helpful tips in chat every few minutes", "", "&cClick to disable"), (p, t) -> {
                playerData.setAreTipsDisabled(true);
                open(p, fromCommand);
            })));
        } else c.setItem(3, c.createButton(Material.EMERALD, "&eTips &8(&cDisabled&8)", List.of("&oReceive helpful tips in chat every few minutes", "", "&aClick to enable"), (p, t) -> {
            playerData.setAreTipsDisabled(false);
            open(p, fromCommand);
        }));
        c.setItem(5, ench(c.createButton(Material.OAK_SIGN, "&6Chat Settings", List.of("&oChange your chat color, font, and more!"), (p, t) -> {
            openChatSettings(p, fromCommand);
        })));
        Material uiMat = Material.LIGHT_BLUE_DYE;
        ChatColor uiColor = playerData.getPrimaryUITheme();
        String currentTheme = "Blue";

        switch (playerData.getUIThemeID()) {
            case "5" -> {
                uiMat = Material.PURPLE_DYE;
                currentTheme = "Purple";
            }
            case "2" -> {
                uiMat = Material.LIME_DYE;
                currentTheme = "Green";
            }
            case "4" -> {
                uiMat = Material.RED_DYE;
                currentTheme = "Red";
            }
            case "6" -> {
                uiMat = Material.ORANGE_DYE;
                currentTheme = "Orange";
            }
        }
        c.setItem(7, ench(c.createButton(uiMat, uiColor + "UI Theme", List.of("&oChange your UI theme!", "Affects: scoreboards, backpack, and tablist", "", uiColor + "Current theme: &f" + currentTheme), (p, t) -> {
            openUISettings(p, fromCommand);
        })));
        c.fill(createGrayPlaceHolder());
        c.open(player);
        c.setOnCloseRun((p, t) -> {
            if (!fromCommand) MainMenus.open(p);
        });
    }

    public static void openChatSettings(Player player, boolean fromCommand) {
        GUICreator c = new GUICreator(9, "Chat Settings");
        PlayerData playerData = new PlayerData(player);

        c.fill(createGrayPlaceHolder());
        c.open(player);
        c.setOnCloseRun((p, t) -> {
            open(p, fromCommand);
        });
    }
    public static void openUISettings(Player player, boolean fromCommand) {
        GUICreator c = new GUICreator(9, "UI Settings");
        PlayerData playerData = new PlayerData(player);
        String currentTheme = "Blue";

        switch (playerData.getUIThemeID()) {
            case "5" -> currentTheme = "Purple";
            case "2" -> currentTheme = "Green";
            case "4" -> currentTheme = "Red";
            case "6" -> currentTheme = "Orange";
        }
        c.setItems(createGrayPlaceHolder(),
                createGrayPlaceHolder(),
                c.createButton(Material.LIGHT_BLUE_DYE, PlayerData.getPrimaryUITheme("b") + "Change Theme To: Blue", List.of("&oCurrent theme: " + currentTheme, "", "&cClick to change"), (p, t) -> {
                    playerData.setUIThemeID("b");
                    open(p, fromCommand);
                }),
                c.createButton(Material.PURPLE_DYE, PlayerData.getPrimaryUITheme("5") + "Change Theme To: Purple", List.of("&oCurrent theme: " + currentTheme, "", "&cClick to change"), (p, t) -> {
                    playerData.setUIThemeID("5");
                    open(p, fromCommand);
                }),
                c.createButton(Material.LIME_DYE, PlayerData.getPrimaryUITheme("2") + "Change Theme To: Green", List.of("&oCurrent theme: " + currentTheme, "", "&cClick to change"), (p, t) -> {
                    playerData.setUIThemeID("2");
                    open(p, fromCommand);
                }),
                c.createButton(Material.RED_DYE, PlayerData.getPrimaryUITheme("4") + "Change Theme To: Red", List.of("&oCurrent theme: " + currentTheme, "", "&cClick to change"), (p, t) -> {
                    playerData.setUIThemeID("4");
                    open(p, fromCommand);
                }),
                c.createButton(Material.ORANGE_DYE, PlayerData.getPrimaryUITheme("6") + "Change Theme To: Orange", List.of("&oCurrent theme: " + currentTheme, "", "&cClick to change"), (p, t) -> {
                    playerData.setUIThemeID("6");
                    open(p, fromCommand);
                }),
                createGrayPlaceHolder(),
                createGrayPlaceHolder()
        );
        c.fill(createGrayPlaceHolder());
        c.open(player);
        c.setOnCloseRun((p, t) -> open(p, fromCommand));
    }
}
