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

        String currentChat = "This is what your chat currently looks like";
        if (playerData.getIsChatItalic()) currentChat = ChatColor.ITALIC + currentChat;
        if (playerData.getIsChatBold()) currentChat = ChatColor.BOLD + currentChat;
        if (playerData.getIsChatUnderlined()) currentChat = ChatColor.UNDERLINE + currentChat;
        currentChat = playerData.getChatColor() + currentChat;


        String boldToggle = "&cClick to toggle off";
        if (!playerData.getIsChatBold()) boldToggle = "&aClick to toggle on";
        String italicToggle = "&cClick to toggle off";
        if (!playerData.getIsChatItalic()) italicToggle = "&aClick to toggle on";
        String underlineToggle = "&cClick to toggle off";
        if (!playerData.getIsChatUnderlined()) underlineToggle = "&aClick to toggle on";

        c.setItem(1, c.createButton(Material.ORANGE_DYE, "&6Color", List.of("&oChange your chat color!", "", currentChat), (p, t) -> {
            if (!playerData.getPlayerRanks().contains("warrior")) {
                p.sendMessage(ChatColor.RED + "This action requires Warrior rank!");
                return;
            }
            openChatColorSettings(p, fromCommand);
        }));
        c.setItem(3, c.createButton(Material.GLOW_INK_SAC, "&3&lBold", List.of("&oMake your chat bold!", boldToggle, "", currentChat), (p, t) -> {
            if (!playerData.getPlayerRanks().contains("mythic")) {
                p.sendMessage(ChatColor.RED + "This action requires Mythic rank!");
                return;
            }
            playerData.setIsChatBold(!playerData.getIsChatBold());
            openChatSettings(p, fromCommand);
        }));
        c.setItem(5, c.createButton(Material.SPECTRAL_ARROW, "&e&oItalic", List.of("&oMake your chat italic!", italicToggle, "", currentChat), (p, t) -> {
            if (!playerData.getPlayerRanks().contains("mythic")) {
                p.sendMessage(ChatColor.RED + "This action requires Mythic rank!");
                return;
            }
            playerData.setIsChatItalic(!playerData.getIsChatItalic());
            openChatSettings(p, fromCommand);
        }));
        c.setItem(7, c.createButton(Material.IRON_TRAPDOOR, "&f&nUnderline", List.of("&oMake your chat underlined!", underlineToggle, "", currentChat), (p, t) -> {
            if (!playerData.getPlayerRanks().contains("mythic")) {
                p.sendMessage(ChatColor.RED + "This action requires Mythic rank!");
                return;
            }
            playerData.setIsChatUnderlined(!playerData.getIsChatUnderlined());
            openChatSettings(p, fromCommand);
        }));

        c.fill(createGrayPlaceHolder());
        c.open(player);
        c.setOnCloseRun((p, t) -> {
            open(p, fromCommand);
        });
    }
    static final ChatColor[] CUSTOM_COLORS = new ChatColor[]{
            ChatColor.of("#8945ff"), //Twitch Purple
            ChatColor.of("#d452ff"), //Pink
            ChatColor.of("#ff8c00"), //Orange
            ChatColor.of("#61b8ff"), //Baby blue
            ChatColor.of("#00bd5a"), //Light-Forest Green
            ChatColor.of("#0ffc03"), //Lime Green
            ChatColor.of("#ff0000"), //Red
    };
    public static void openChatColorSettings(Player player, boolean fromCommand) {
        GUICreator c = new GUICreator(27, "Chat Color");
        PlayerData playerData = new PlayerData(player);

        String chatFormat = "";
        if (playerData.getIsChatItalic()) chatFormat = ChatColor.ITALIC + chatFormat;
        if (playerData.getIsChatBold()) chatFormat = ChatColor.BOLD + chatFormat;
        if (playerData.getIsChatUnderlined()) chatFormat = ChatColor.UNDERLINE + chatFormat;
        String currentChat = playerData.getChatColor() + chatFormat + "This is what your chat currently looks like";

        c.setItems(
                c.createButton(Material.WHITE_DYE, ChatColor.WHITE + chatFormat + "Make your chat look like this", List.of("&oChange your chat color!", "", currentChat), (p, t) -> {
                    if (!playerData.getPlayerRanks().contains("warrior")) {
                        p.sendMessage(ChatColor.RED + "This action requires Warrior rank!");
                        return;
                    }
                    playerData.setChatColor(ChatColor.WHITE);
                    openChatSettings(p, fromCommand);
                }),
                c.createButton(Material.LIGHT_GRAY_DYE, ChatColor.GRAY + chatFormat + "Make your chat look like this", List.of("&oChange your chat color!", "", currentChat), (p, t) -> {
                    if (!playerData.getPlayerRanks().contains("warrior")) {
                        p.sendMessage(ChatColor.RED + "This action requires Warrior rank!");
                        return;
                    }
                    playerData.setChatColor(ChatColor.GRAY);
                    openChatSettings(p, fromCommand);
                }),
                c.createButton(Material.GRAY_DYE, ChatColor.DARK_GRAY + chatFormat + "Make your chat look like this", List.of("&oChange your chat color!", "", currentChat), (p, t) -> {
                    if (!playerData.getPlayerRanks().contains("warrior")) {
                        p.sendMessage(ChatColor.RED + "This action requires Warrior rank!");
                        return;
                    }
                    playerData.setChatColor(ChatColor.DARK_GRAY);
                    openChatSettings(p, fromCommand);
                }),

                c.createButton(Material.BLACK_DYE, ChatColor.BLACK + chatFormat + "Make your chat look like this", List.of("&oChange your chat color!", "", currentChat), (p, t) -> {
                    if (!playerData.getPlayerRanks().contains("warrior")) {
                        p.sendMessage(ChatColor.RED + "This action requires Warrior rank!");
                        return;
                    }
                    playerData.setChatColor(ChatColor.BLACK);
                    openChatSettings(p, fromCommand);
                }),
                c.createButton(Material.YELLOW_DYE, ChatColor.YELLOW + chatFormat + "Make your chat look like this", List.of("&oChange your chat color!", "", currentChat), (p, t) -> {
                    if (!playerData.getPlayerRanks().contains("warrior")) {
                        p.sendMessage(ChatColor.RED + "This action requires Warrior rank!");
                        return;
                    }
                    playerData.setChatColor(ChatColor.YELLOW);
                    openChatSettings(p, fromCommand);
                }),
                c.createButton(Material.ORANGE_DYE, ChatColor.GOLD + chatFormat + "Make your chat look like this", List.of("&oChange your chat color!", "", currentChat), (p, t) -> {
                    if (!playerData.getPlayerRanks().contains("warrior")) {
                        p.sendMessage(ChatColor.RED + "This action requires Warrior rank!");
                        return;
                    }
                    playerData.setChatColor(ChatColor.GOLD);
                    openChatSettings(p, fromCommand);
                }),
                c.createButton(Material.RED_DYE, ChatColor.RED + chatFormat + "Make your chat look like this", List.of("&oChange your chat color!", "", currentChat), (p, t) -> {
                    if (!playerData.getPlayerRanks().contains("warrior")) {
                        p.sendMessage(ChatColor.RED + "This action requires Warrior rank!");
                        return;
                    }
                    playerData.setChatColor(ChatColor.RED);
                    openChatSettings(p, fromCommand);
                }),
                c.createButton(Material.REDSTONE, ChatColor.DARK_RED + chatFormat + "Make your chat look like this", List.of("&oChange your chat color!", "", currentChat), (p, t) -> {
                    if (!playerData.getPlayerRanks().contains("warrior")) {
                        p.sendMessage(ChatColor.RED + "This action requires Warrior rank!");
                        return;
                    }
                    playerData.setChatColor(ChatColor.DARK_RED);
                    openChatSettings(p, fromCommand);
                }),
                c.createButton(Material.LIGHT_BLUE_DYE, ChatColor.AQUA + chatFormat + "Make your chat look like this", List.of("&oChange your chat color!", "", currentChat), (p, t) -> {
                    if (!playerData.getPlayerRanks().contains("warrior")) {
                        p.sendMessage(ChatColor.RED + "This action requires Warrior rank!");
                        return;
                    }
                    playerData.setChatColor(ChatColor.AQUA);
                    openChatSettings(p, fromCommand);
                }),
                createBlackPlaceHolder(),
                c.createButton(Material.CYAN_DYE, ChatColor.DARK_AQUA + chatFormat + "Make your chat look like this", List.of("&oChange your chat color!", "", currentChat), (p, t) -> {
                    if (!playerData.getPlayerRanks().contains("warrior")) {
                        p.sendMessage(ChatColor.RED + "This action requires Warrior rank!");
                        return;
                    }
                    playerData.setChatColor(ChatColor.DARK_AQUA);
                    openChatSettings(p, fromCommand);
                }),
                c.createButton(Material.BLUE_DYE, ChatColor.BLUE + chatFormat + "Make your chat look like this", List.of("&oChange your chat color!", "", currentChat), (p, t) -> {
                    if (!playerData.getPlayerRanks().contains("warrior")) {
                        p.sendMessage(ChatColor.RED + "This action requires Warrior rank!");
                        return;
                    }
                    playerData.setChatColor(ChatColor.BLUE);
                    openChatSettings(p, fromCommand);
                }),
                c.createButton(Material.LAPIS_LAZULI, ChatColor.DARK_BLUE + chatFormat + "Make your chat look like this", List.of("&oChange your chat color!", "", currentChat), (p, t) -> {
                    if (!playerData.getPlayerRanks().contains("warrior")) {
                        p.sendMessage(ChatColor.RED + "This action requires Warrior rank!");
                        return;
                    }
                    playerData.setChatColor(ChatColor.DARK_BLUE);
                    openChatSettings(p, fromCommand);
                }),
                c.createButton(Material.LIME_DYE, ChatColor.GREEN + chatFormat + "Make your chat look like this", List.of("&oChange your chat color!", "", currentChat), (p, t) -> {
                    if (!playerData.getPlayerRanks().contains("warrior")) {
                        p.sendMessage(ChatColor.RED + "This action requires Warrior rank!");
                        return;
                    }
                    playerData.setChatColor(ChatColor.GREEN);
                    openChatSettings(p, fromCommand);
                }),
                c.createButton(Material.GREEN_DYE, ChatColor.DARK_GREEN + chatFormat + "Make your chat look like this", List.of("&oChange your chat color!", "", currentChat), (p, t) -> {
                    if (!playerData.getPlayerRanks().contains("warrior")) {
                        p.sendMessage(ChatColor.RED + "This action requires Warrior rank!");
                        return;
                    }
                    playerData.setChatColor(ChatColor.DARK_GREEN);
                    openChatSettings(p, fromCommand);
                }),
                c.createButton(Material.PINK_DYE, ChatColor.LIGHT_PURPLE + chatFormat + "Make your chat look like this", List.of("&oChange your chat color!", "", currentChat), (p, t) -> {
                    if (!playerData.getPlayerRanks().contains("warrior")) {
                        p.sendMessage(ChatColor.RED + "This action requires Warrior rank!");
                        return;
                    }
                    playerData.setChatColor(ChatColor.LIGHT_PURPLE);
                    openChatSettings(p, fromCommand);
                }),
                c.createButton(Material.PURPLE_DYE, ChatColor.DARK_PURPLE + chatFormat + "Make your chat look like this", List.of("&oChange your chat color!", "", currentChat), (p, t) -> {
                    if (!playerData.getPlayerRanks().contains("warrior")) {
                        p.sendMessage(ChatColor.RED + "This action requires Warrior rank!");
                        return;
                    }
                    playerData.setChatColor(ChatColor.DARK_PURPLE);
                    openChatSettings(p, fromCommand);
                }),
                createBlackPlaceHolder(),
                createBlackPlaceHolder(),
                ench(c.createButton(Material.PAPER, CUSTOM_COLORS[0] + chatFormat + "Make your chat look like this", List.of("&oChange your chat color!", "", currentChat), (p, t) -> {
                    if (!playerData.getPlayerRanks().contains("warrior")) {
                        p.sendMessage(ChatColor.RED + "This action requires Warrior rank!");
                        return;
                    }
                    playerData.setChatColor(CUSTOM_COLORS[0]);
                    openChatSettings(p, fromCommand);
                })),
                ench(c.createButton(Material.PAPER, CUSTOM_COLORS[1] + chatFormat + "Make your chat look like this", List.of("&oChange your chat color!", "", currentChat), (p, t) -> {
                    if (!playerData.getPlayerRanks().contains("warrior")) {
                        p.sendMessage(ChatColor.RED + "This action requires Warrior rank!");
                        return;
                    }
                    playerData.setChatColor(CUSTOM_COLORS[1]);
                    openChatSettings(p, fromCommand);
                })),
                ench(c.createButton(Material.PAPER, CUSTOM_COLORS[2] + chatFormat + "Make your chat look like this", List.of("&oChange your chat color!", "", currentChat), (p, t) -> {
                    if (!playerData.getPlayerRanks().contains("warrior")) {
                        p.sendMessage(ChatColor.RED + "This action requires Warrior rank!");
                        return;
                    }
                    playerData.setChatColor(CUSTOM_COLORS[2]);
                    openChatSettings(p, fromCommand);
                })),
                ench(c.createButton(Material.PAPER, CUSTOM_COLORS[3] + chatFormat + "Make your chat look like this", List.of("&oChange your chat color!", "", currentChat), (p, t) -> {
                    if (!playerData.getPlayerRanks().contains("warrior")) {
                        p.sendMessage(ChatColor.RED + "This action requires Warrior rank!");
                        return;
                    }
                    playerData.setChatColor(CUSTOM_COLORS[3]);
                    openChatSettings(p, fromCommand);
                })),
                ench(c.createButton(Material.PAPER, CUSTOM_COLORS[4] + chatFormat + "Make your chat look like this", List.of("&oChange your chat color!", "", currentChat), (p, t) -> {
                    if (!playerData.getPlayerRanks().contains("warrior")) {
                        p.sendMessage(ChatColor.RED + "This action requires Warrior rank!");
                        return;
                    }
                    playerData.setChatColor(CUSTOM_COLORS[4]);
                    openChatSettings(p, fromCommand);
                })),
                ench(c.createButton(Material.PAPER, CUSTOM_COLORS[5] + chatFormat + "Make your chat look like this", List.of("&oChange your chat color!", "", currentChat), (p, t) -> {
                    if (!playerData.getPlayerRanks().contains("warrior")) {
                        p.sendMessage(ChatColor.RED + "This action requires Warrior rank!");
                        return;
                    }
                    playerData.setChatColor(CUSTOM_COLORS[5]);
                    openChatSettings(p, fromCommand);
                })),
                ench(c.createButton(Material.PAPER, CUSTOM_COLORS[6] + chatFormat + "Make your chat look like this", List.of("&oChange your chat color!", "", currentChat), (p, t) -> {
                    if (!playerData.getPlayerRanks().contains("warrior")) {
                        p.sendMessage(ChatColor.RED + "This action requires Warrior rank!");
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
}
