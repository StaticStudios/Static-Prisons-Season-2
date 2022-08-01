package net.staticstudios.prisons.gui;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.md_5.bungee.api.ChatColor;
import net.staticstudios.gui.GUICreator;
import net.staticstudios.gui.GUIUtils;
import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SettingsMenus extends GUIUtils {

    public static void open(Player player, boolean fromCommand) {
        GUICreator c = new GUICreator(9, "Your Settings");
        PlayerData playerData = new PlayerData(player);
        if (playerData.getIsAutoSellEnabled()) {
            c.setItem(1, ench(c.createButton(Material.DIAMOND, "&bAuto Sell &8(&aEnabled&8)", List.of("&oAutomatically sell your backpack when it's full", "", "&cClick to disable"), (p, t) -> {
                playerData.setIsAutoSellEnabled(false);
                open(p, fromCommand);
            })));
        } else
            c.setItem(1, c.createButton(Material.DIAMOND, "&bAuto Sell &8(&cDisabled&8)", List.of("&oAutomatically sell your backpack when it's full", "", "&aClick to enable"), (p, t) -> {
                if (PrisonUtils.Players.canAutoSell(p)) playerData.setIsAutoSellEnabled(true);
                else p.sendMessage(ChatColor.RED + "You cannot enable auto sell!");
                open(p, fromCommand);
            }));
        if (!playerData.getAreTipsDisabled()) {
            c.setItem(3, ench(c.createButton(Material.EMERALD, "&eTips &8(&aEnabled&8)", List.of("&oReceive helpful tips in chat every few minutes", "", "&cClick to disable"), (p, t) -> {
                playerData.setAreTipsDisabled(true);
                open(p, fromCommand);
            })));
        } else
            c.setItem(3, c.createButton(Material.EMERALD, "&eTips &8(&cDisabled&8)", List.of("&oReceive helpful tips in chat every few minutes", "", "&aClick to enable"), (p, t) -> {
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

        Component currentChat = getCurrentChat(playerData);


        Component boldToggle = Component.text( "Click to toggle off").color(NamedTextColor.RED);
        if (!playerData.getIsChatBold()) boldToggle = Component.text("Click to toggle on").color(NamedTextColor.GREEN);
        Component italicToggle = Component.text( "Click to toggle off").color(NamedTextColor.RED);
        if (!playerData.getIsChatItalic()) italicToggle = Component.text("Click to toggle on").color(NamedTextColor.GREEN);
        Component underlineToggle = Component.text( "Click to toggle off").color(NamedTextColor.RED);
        if (!playerData.getIsChatUnderlined()) underlineToggle = Component.text("Click to toggle on").color(NamedTextColor.GREEN);

        c.setItem(1, c.createButton(Material.ORANGE_DYE, Component.text("Color").color(NamedTextColor.GOLD), List.of(Component.text("Change your chat color!").decorate(TextDecoration.ITALIC), Component.empty(), currentChat), (p, t) -> {
            if (!playerData.getPlayerRanks().contains("warrior")) {
                p.sendMessage(Component.text("This action requires Warrior rank!").color(NamedTextColor.RED));
                return;
            }
            openChatColorSettings(p, fromCommand);
        }));
        c.setItem(3, c.createButton(Material.GLOW_INK_SAC, Component.text("Bold").color(NamedTextColor.DARK_AQUA).decorate(TextDecoration.BOLD), List.of(Component.text("Make your chat bold!").decorate(TextDecoration.ITALIC), boldToggle, Component.empty(), currentChat), (p, t) -> {
            if (!playerData.getPlayerRanks().contains("mythic")) {
                p.sendMessage(Component.text("This action requires Mythic rank!").color(NamedTextColor.RED));
                return;
            }
            playerData.setIsChatBold(!playerData.getIsChatBold());
            openChatSettings(p, fromCommand);
        }));
        c.setItem(5, c.createButton(Material.SPECTRAL_ARROW, Component.text("Italic").color(NamedTextColor.YELLOW).decoration(TextDecoration.ITALIC, true), List.of(Component.text("Make your chat italic!").decorate(TextDecoration.ITALIC), italicToggle, Component.empty(), currentChat), (p, t) -> {
            if (!playerData.getPlayerRanks().contains("mythic")) {
                p.sendMessage(Component.text("This action requires Mythic rank!").color(NamedTextColor.RED));
                return;
            }
            playerData.setIsChatItalic(!playerData.getIsChatItalic());
            openChatSettings(p, fromCommand);
        }));
        c.setItem(7, c.createButton(Material.IRON_TRAPDOOR, Component.text("Underline").decorate(TextDecoration.UNDERLINED), List.of(Component.text("Make your chat underlined!").decorate(TextDecoration.ITALIC), underlineToggle, Component.empty(), currentChat), (p, t) -> {
            if (!playerData.getPlayerRanks().contains("mythic")) {
                p.sendMessage(Component.text("This action requires Mythic rank!").color(NamedTextColor.RED));
                return;
            }
            playerData.setIsChatUnderlined(!playerData.getIsChatUnderlined());
            openChatSettings(p, fromCommand);
        }));

        c.fill(createGrayPlaceHolder());
        c.open(player);
        c.setOnCloseRun((p, t) -> open(p, fromCommand));
    }
    static final TextColor[] CUSTOM_COLORS = new TextColor[]{
            TextColor.fromHexString("#8945ff"), //Twitch Purple
            TextColor.fromHexString("#d452ff"), //Pink
            TextColor.fromHexString("#ff8c00"), //Orange
            TextColor.fromHexString("#61b8ff"), //Baby blue
            TextColor.fromHexString("#00bd5a"), //Light-Forest Green
            TextColor.fromHexString("#0ffc03"), //Lime Green
            TextColor.fromHexString("#ff0000"), //Red
    };

    public static void openChatColorSettings(Player player, boolean fromCommand) {
        GUICreator c = new GUICreator(27, "Chat Color");
        PlayerData playerData = new PlayerData(player);

        Component chatFormat = Component.empty();

        Map<TextDecoration, TextDecoration.State> decorationStates = new HashMap<>();
        decorationStates.put(TextDecoration.BOLD, playerData.getIsChatBold() ? TextDecoration.State.TRUE : TextDecoration.State.FALSE);
        decorationStates.put(TextDecoration.ITALIC, playerData.getIsChatItalic() ? TextDecoration.State.TRUE : TextDecoration.State.FALSE);
        decorationStates.put(TextDecoration.UNDERLINED, playerData.getIsChatUnderlined() ? TextDecoration.State.TRUE : TextDecoration.State.FALSE);

        Component currentChat = Component.text("This is what your chat currently looks like").color(playerData.getChatColor()).decorations(decorationStates);

        c.setItems(
                c.createButton(Material.WHITE_DYE, Component.text("Make your chat look like this").color(NamedTextColor.WHITE).append(chatFormat), List.of(Component.text("Change your chat color!").decorate(TextDecoration.ITALIC), Component.empty(), currentChat), (p, t) -> {
                    if (!playerData.getPlayerRanks().contains("warrior")) {
                        p.sendMessage(Component.text("This action requires Warrior rank!").color(NamedTextColor.RED));
                        return;
                    }
                    playerData.setChatColor(NamedTextColor.WHITE);
                    openChatSettings(p, fromCommand);
                }),
                c.createButton(Material.LIGHT_GRAY_DYE, Component.text("Make your chat look like this").color(NamedTextColor.GRAY).append(chatFormat), List.of(Component.text("Change your chat color!").decorate(TextDecoration.ITALIC), Component.empty(), currentChat), (p, t) -> {
                    if (!playerData.getPlayerRanks().contains("warrior")) {
                        p.sendMessage(Component.text("This action requires Warrior rank!").color(NamedTextColor.RED));
                        return;
                    }
                    playerData.setChatColor(NamedTextColor.GRAY);
                    openChatSettings(p, fromCommand);
                }),
                c.createButton(Material.GRAY_DYE, Component.text("Make your chat look like this").color(NamedTextColor.DARK_GRAY).append(chatFormat), List.of(Component.text("Change your chat color!").decorate(TextDecoration.ITALIC), Component.empty(), currentChat), (p, t) -> {
                    if (!playerData.getPlayerRanks().contains("warrior")) {
                        p.sendMessage(Component.text("This action requires Warrior rank!").color(NamedTextColor.RED));
                        return;
                    }
                    playerData.setChatColor(NamedTextColor.DARK_GRAY);
                    openChatSettings(p, fromCommand);
                }),

                c.createButton(Material.BLACK_DYE, Component.text("Make your chat look like this").color(NamedTextColor.BLACK).append(chatFormat), List.of(Component.text("Change your chat color!").decorate(TextDecoration.ITALIC), Component.empty(), currentChat), (p, t) -> {
                    if (!playerData.getPlayerRanks().contains("warrior")) {
                        p.sendMessage(Component.text("This action requires Warrior rank!").color(NamedTextColor.RED));
                        return;
                    }
                    playerData.setChatColor(NamedTextColor.BLACK);
                    openChatSettings(p, fromCommand);
                }),
                c.createButton(Material.YELLOW_DYE, Component.text("Make your chat look like this").color(NamedTextColor.YELLOW).append(chatFormat), List.of(Component.text("Change your chat color!").decorate(TextDecoration.ITALIC), Component.empty(), currentChat), (p, t) -> {
                    if (!playerData.getPlayerRanks().contains("warrior")) {
                        p.sendMessage(Component.text("This action requires Warrior rank!").color(NamedTextColor.RED));
                        return;
                    }
                    playerData.setChatColor(NamedTextColor.YELLOW);
                    openChatSettings(p, fromCommand);
                }),
                c.createButton(Material.ORANGE_DYE, Component.text("Make your chat look like this").color(NamedTextColor.GOLD).append(chatFormat), List.of(Component.text("Change your chat color!").decorate(TextDecoration.ITALIC), Component.empty(), currentChat), (p, t) -> {
                    if (!playerData.getPlayerRanks().contains("warrior")) {
                        p.sendMessage(Component.text("This action requires Warrior rank!").color(NamedTextColor.RED));
                        return;
                    }
                    playerData.setChatColor(NamedTextColor.GOLD);
                    openChatSettings(p, fromCommand);
                }),
                c.createButton(Material.RED_DYE, Component.text("Make your chat look like this").color(NamedTextColor.RED).append(chatFormat), List.of(Component.text("Change your chat color!").decorate(TextDecoration.ITALIC), Component.empty(), currentChat), (p, t) -> {
                    if (!playerData.getPlayerRanks().contains("warrior")) {
                        p.sendMessage(Component.text("This action requires Warrior rank!").color(NamedTextColor.RED));
                        return;
                    }
                    playerData.setChatColor(NamedTextColor.RED);
                    openChatSettings(p, fromCommand);
                }),
                c.createButton(Material.REDSTONE, Component.text("Make your chat look like this").color(NamedTextColor.DARK_RED).append(chatFormat), List.of(Component.text("Change your chat color!").decorate(TextDecoration.ITALIC), Component.empty(), currentChat), (p, t) -> {
                    if (!playerData.getPlayerRanks().contains("warrior")) {
                        p.sendMessage(Component.text("This action requires Warrior rank!").color(NamedTextColor.RED));
                        return;
                    }
                    playerData.setChatColor(NamedTextColor.DARK_RED);
                    openChatSettings(p, fromCommand);
                }),
                c.createButton(Material.LIGHT_BLUE_DYE, Component.text("Make your chat look like this").color(NamedTextColor.AQUA).append(chatFormat), List.of(Component.text("Change your chat color!").decorate(TextDecoration.ITALIC), Component.empty(), currentChat), (p, t) -> {
                    if (!playerData.getPlayerRanks().contains("warrior")) {
                        p.sendMessage(Component.text("This action requires Warrior rank!").color(NamedTextColor.RED));
                        return;
                    }
                    playerData.setChatColor(NamedTextColor.AQUA);
                    openChatSettings(p, fromCommand);
                }),
                createBlackPlaceHolder(),
                c.createButton(Material.CYAN_DYE, Component.text("Make your chat look like this").color(NamedTextColor.DARK_AQUA).append(chatFormat), List.of(Component.text("Change your chat color!").decorate(TextDecoration.ITALIC), Component.empty(), currentChat), (p, t) -> {
                    if (!playerData.getPlayerRanks().contains("warrior")) {
                        p.sendMessage(Component.text("This action requires Warrior rank!").color(NamedTextColor.RED));
                        return;
                    }
                    playerData.setChatColor(NamedTextColor.DARK_AQUA);
                    openChatSettings(p, fromCommand);
                }),
                c.createButton(Material.BLUE_DYE, Component.text("Make your chat look like this").color(NamedTextColor.BLUE).append(chatFormat), List.of(Component.text("Change your chat color!").decorate(TextDecoration.ITALIC), Component.empty(), currentChat), (p, t) -> {
                    if (!playerData.getPlayerRanks().contains("warrior")) {
                        p.sendMessage(Component.text("This action requires Warrior rank!").color(NamedTextColor.RED));
                        return;
                    }
                    playerData.setChatColor(NamedTextColor.BLUE);
                    openChatSettings(p, fromCommand);
                }),
                c.createButton(Material.LAPIS_LAZULI, Component.text("Make your chat look like this").color(NamedTextColor.DARK_BLUE).append(chatFormat), List.of(Component.text("Change your chat color!").decorate(TextDecoration.ITALIC), Component.empty(), currentChat), (p, t) -> {
                    if (!playerData.getPlayerRanks().contains("warrior")) {
                        p.sendMessage(Component.text("This action requires Warrior rank!").color(NamedTextColor.RED));
                        return;
                    }
                    playerData.setChatColor(NamedTextColor.DARK_BLUE);
                    openChatSettings(p, fromCommand);
                }),
                c.createButton(Material.LIME_DYE, Component.text("Make your chat look like this").color(NamedTextColor.GREEN).append(chatFormat), List.of(Component.text("Change your chat color!").decorate(TextDecoration.ITALIC), Component.empty(), currentChat), (p, t) -> {
                    if (!playerData.getPlayerRanks().contains("warrior")) {
                        p.sendMessage(Component.text("This action requires Warrior rank!").color(NamedTextColor.RED));
                        return;
                    }
                    playerData.setChatColor(NamedTextColor.GREEN);
                    openChatSettings(p, fromCommand);
                }),
                c.createButton(Material.GREEN_DYE, Component.text("Make your chat look like this").color(NamedTextColor.DARK_GREEN).append(chatFormat), List.of(Component.text("Change your chat color!").decorate(TextDecoration.ITALIC), Component.empty(), currentChat), (p, t) -> {
                    if (!playerData.getPlayerRanks().contains("warrior")) {
                        p.sendMessage(Component.text("This action requires Warrior rank!").color(NamedTextColor.RED));
                        return;
                    }
                    playerData.setChatColor(NamedTextColor.DARK_GREEN);
                    openChatSettings(p, fromCommand);
                }),
                c.createButton(Material.PINK_DYE, Component.text("Make your chat look like this").color(NamedTextColor.LIGHT_PURPLE).append(chatFormat), List.of(Component.text("Change your chat color!").decorate(TextDecoration.ITALIC), Component.empty(), currentChat), (p, t) -> {
                    if (!playerData.getPlayerRanks().contains("warrior")) {
                        p.sendMessage(Component.text("This action requires Warrior rank!").color(NamedTextColor.RED));
                        return;
                    }
                    playerData.setChatColor(NamedTextColor.LIGHT_PURPLE);
                    openChatSettings(p, fromCommand);
                }),
                c.createButton(Material.PURPLE_DYE, Component.text("Make your chat look like this").color(NamedTextColor.DARK_PURPLE).append(chatFormat), List.of(Component.text("Change your chat color!").decorate(TextDecoration.ITALIC), Component.empty(), currentChat), (p, t) -> {
                    if (!playerData.getPlayerRanks().contains("warrior")) {
                        p.sendMessage(Component.text("This action requires Warrior rank!").color(NamedTextColor.RED));
                        return;
                    }
                    playerData.setChatColor(NamedTextColor.DARK_PURPLE);
                    openChatSettings(p, fromCommand);
                }),
                createBlackPlaceHolder(),
                createBlackPlaceHolder(),
                ench(c.createButton(Material.PAPER, Component.text("Make your chat look like this").color(CUSTOM_COLORS[0]).append(chatFormat), List.of(Component.text("Change your chat color!").decorate(TextDecoration.ITALIC), Component.empty(), currentChat), (p, t) -> {
                    if (!playerData.getPlayerRanks().contains("warrior")) {
                        p.sendMessage(Component.text("This action requires Warrior rank!").color(NamedTextColor.RED));
                        return;
                    }
                    playerData.setChatColor(CUSTOM_COLORS[0]);
                    openChatSettings(p, fromCommand);
                })),
                ench(c.createButton(Material.PAPER, Component.text("Make your chat look like this").color(CUSTOM_COLORS[1]).append(chatFormat), List.of(Component.text("Change your chat color!").decorate(TextDecoration.ITALIC), Component.empty(), currentChat), (p, t) -> {
                    if (!playerData.getPlayerRanks().contains("warrior")) {
                        p.sendMessage(Component.text("This action requires Warrior rank!").color(NamedTextColor.RED));
                        return;
                    }
                    playerData.setChatColor(CUSTOM_COLORS[1]);
                    openChatSettings(p, fromCommand);
                })),
                ench(c.createButton(Material.PAPER, Component.text("Make your chat look like this").color(CUSTOM_COLORS[2]).append(chatFormat), List.of(Component.text("Change your chat color!").decorate(TextDecoration.ITALIC), Component.empty(), currentChat), (p, t) -> {
                    if (!playerData.getPlayerRanks().contains("warrior")) {
                        p.sendMessage(Component.text("This action requires Warrior rank!").color(NamedTextColor.RED));
                        return;
                    }
                    playerData.setChatColor(CUSTOM_COLORS[2]);
                    openChatSettings(p, fromCommand);
                })),
                ench(c.createButton(Material.PAPER, Component.text("Make your chat look like this").color(CUSTOM_COLORS[3]).append(chatFormat), List.of(Component.text("Change your chat color!").decorate(TextDecoration.ITALIC), Component.empty(), currentChat), (p, t) -> {
                    if (!playerData.getPlayerRanks().contains("warrior")) {
                        p.sendMessage(Component.text("This action requires Warrior rank!").color(NamedTextColor.RED));
                        return;
                    }
                    playerData.setChatColor(CUSTOM_COLORS[3]);
                    openChatSettings(p, fromCommand);
                })),
                ench(c.createButton(Material.PAPER, Component.text("Make your chat look like this").color(CUSTOM_COLORS[4]).append(chatFormat), List.of(Component.text("Change your chat color!").decorate(TextDecoration.ITALIC), Component.empty(), currentChat), (p, t) -> {
                    if (!playerData.getPlayerRanks().contains("warrior")) {
                        p.sendMessage(Component.text("This action requires Warrior rank!").color(NamedTextColor.RED));
                        return;
                    }
                    playerData.setChatColor(CUSTOM_COLORS[4]);
                    openChatSettings(p, fromCommand);
                })),
                ench(c.createButton(Material.PAPER, Component.text("Make your chat look like this").color(CUSTOM_COLORS[5]).append(chatFormat), List.of(Component.text("Change your chat color!").decorate(TextDecoration.ITALIC), Component.empty(), currentChat), (p, t) -> {
                    if (!playerData.getPlayerRanks().contains("warrior")) {
                        p.sendMessage(Component.text("This action requires Warrior rank!").color(NamedTextColor.RED));
                        return;
                    }
                    playerData.setChatColor(CUSTOM_COLORS[5]);
                    openChatSettings(p, fromCommand);
                })),
                ench(c.createButton(Material.PAPER, Component.text("Make your chat look like this").color(CUSTOM_COLORS[6]).append(chatFormat), List.of(Component.text("Change your chat color!").decorate(TextDecoration.ITALIC), Component.empty(), currentChat), (p, t) -> {
                    if (!playerData.getPlayerRanks().contains("warrior")) {
                        p.sendMessage(Component.text("This action requires Warrior rank!").color(NamedTextColor.RED));
                        return;
                    }
                    playerData.setChatColor(CUSTOM_COLORS[6]);
                    openChatSettings(p, fromCommand);
                })),
                createBlackPlaceHolder()
        );
        c.open(player);
        c.setOnCloseRun((p, t) -> {
            openChatSettings(p, fromCommand);
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
        currentTheme = playerData.getPrimaryUITheme() + currentTheme;
        c.setItems(createGrayPlaceHolder(),
                createGrayPlaceHolder(),
                c.createButton(Material.LIGHT_BLUE_DYE, PlayerData.getPrimaryUITheme("b") + "Change Theme To: Blue", List.of("&oCurrent theme: " + currentTheme, "", "&cClick to change"), (p, t) -> {
                    playerData.setUIThemeID("b");
                    openUISettings(p, fromCommand);
                }),
                c.createButton(Material.PURPLE_DYE, PlayerData.getPrimaryUITheme("5") + "Change Theme To: Purple", List.of("&oCurrent theme: " + currentTheme, "", "&cClick to change"), (p, t) -> {
                    playerData.setUIThemeID("5");
                    openUISettings(p, fromCommand);
                }),
                c.createButton(Material.LIME_DYE, PlayerData.getPrimaryUITheme("2") + "Change Theme To: Green", List.of("&oCurrent theme: " + currentTheme, "", "&cClick to change"), (p, t) -> {
                    playerData.setUIThemeID("2");
                    openUISettings(p, fromCommand);
                }),
                c.createButton(Material.RED_DYE, PlayerData.getPrimaryUITheme("4") + "Change Theme To: Red", List.of("&oCurrent theme: " + currentTheme, "", "&cClick to change"), (p, t) -> {
                    playerData.setUIThemeID("4");
                    openUISettings(p, fromCommand);
                }),
                c.createButton(Material.ORANGE_DYE, PlayerData.getPrimaryUITheme("6") + "Change Theme To: Orange", List.of("&oCurrent theme: " + currentTheme, "", "&cClick to change"), (p, t) -> {
                    playerData.setUIThemeID("6");
                    openUISettings(p, fromCommand);
                }),
                createGrayPlaceHolder(),
                createGrayPlaceHolder()
        );
        c.fill(createGrayPlaceHolder());
        c.open(player);
        c.setOnCloseRun((p, t) -> open(p, fromCommand));
    }

    private static Component getCurrentChat(PlayerData playerData) {
        Component currentChat = Component.text("This is what your chat currently looks like").decoration(TextDecoration.ITALIC, false);
        if (playerData.getIsChatItalic()) currentChat = currentChat.decorate(TextDecoration.ITALIC);
        if (playerData.getIsChatBold()) currentChat = currentChat.decorate(TextDecoration.BOLD);
        if (playerData.getIsChatUnderlined()) currentChat = currentChat.decorate(TextDecoration.UNDERLINED);
        currentChat = currentChat.color(playerData.getChatColor());

        return currentChat;
    }
}
