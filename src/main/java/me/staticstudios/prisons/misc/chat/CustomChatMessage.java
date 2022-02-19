package me.staticstudios.prisons.misc.chat;

import me.staticstudios.prisons.data.serverData.PlayerData;
import me.staticstudios.prisons.misc.tablist.TabList;
import me.staticstudios.prisons.utils.Utils;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CustomChatMessage {
    AsyncPlayerChatEvent e;

    String prefix = null;
    String suffix = null;
    String playerName;
    String playerNickName = null;
    boolean useNickName = false;
    PlayerData playerData;
    static List<String> bannedWords = new ArrayList<>();

    public CustomChatMessage(AsyncPlayerChatEvent e) {
        this.e = e;
        this.playerName = e.getPlayer().getName();
        playerData = new PlayerData(e.getPlayer());

        //Check if a player has a nickname
    }

    public void sendFormatted() {
        ComponentBuilder componentBuilder = new ComponentBuilder();


        String rankTag = ChatColor.translateAlternateColorCodes('&', "&7Member");
        for (String[] arr : TabList.TEAMS) {
            if (arr[2].equals(playerData.getTabListPrefixID())) {
                rankTag = arr[1].substring(0, arr[1].length() - 1);
                break;
            }
        }

        String chatMessage = e.getMessage();


        HoverEvent prefixHover = new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.translateAlternateColorCodes('&', "&bReal Name: &f" + e.getPlayer().getName() + "")));





        String prefix = ChatColor.translateAlternateColorCodes('&', "&8[&dP" + Utils.prettyNum(playerData.getPrestige()) + "&8] " + ChatTags.getChatTagFromID(playerData.getChatTag1())  + ChatTags.getChatTagFromID(playerData.getChatTag2()) +
                "&8[" + rankTag + "&8] ");
        String suffix = "";
        String playerName = ChatColor.of("#54f08a") + this.playerName;
        if (playerData.getIsChatNicknameEnabled()) playerName = ChatColor.of("#54f08a") + playerData.getChatNickname();
        if (useNickName) playerName = playerNickName;
        if (this.prefix != null) prefix = this.prefix + " ";
        if (this.suffix != null) suffix = this.suffix + " ";




        String msgPrefix = prefix + ChatColor.RESET + playerName + ChatColor.RESET + suffix + ChatColor.DARK_GRAY + ": ";
        char[] msgPrefixArr = (ChatColor.stripColor(msgPrefix)).toCharArray();
        List<List<ChatColor>> msgPrefixColors = getColorOfEveryCharacter(msgPrefix);
        for (int i = 0; i < msgPrefixArr.length - 1; i++) {
            componentBuilder.append(msgPrefixArr[i] + "");
            componentBuilder.reset();
            componentBuilder.getCurrentComponent().setHoverEvent(prefixHover);
            for (ChatColor color : msgPrefixColors.get(i)) {
                if (ChatColor.BOLD.equals(color)) {
                    componentBuilder.bold(true);
                } else if (ChatColor.ITALIC.equals(color)) {
                    componentBuilder.italic(true);
                } else if (ChatColor.UNDERLINE.equals(color)) {
                    componentBuilder.underlined(true);
                } else if (ChatColor.STRIKETHROUGH.equals(color)) {
                    componentBuilder.strikethrough(true);
                } else if (ChatColor.RESET.equals(color)) {
                    componentBuilder.reset();
                } else if (ChatColor.MAGIC.equals(color)) {
                    componentBuilder.obfuscated(true);
                } else componentBuilder.color(color);
            }
        }
        componentBuilder.append(" ");
        componentBuilder.reset();




        if (chatMessage.contains("[item]")) {
            String msg1 = chatMessage;
            String msg2 = "";
            if (playerData.getPlayerRanks().contains("warrior")) {
                if (!e.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.AIR)) {
                    if (chatMessage.split("\\[item]").length > 1) msg1 = chatMessage.split("\\[item]")[0];
                    if (chatMessage.split("\\[item]").length > 1) msg2 = chatMessage.split("\\[item]")[1];
                    componentBuilder.append(msg1.replaceAll("\\[item]", ""));
                    if (playerData.getIsChatColorEnabled()) componentBuilder.color(playerData.getChatColor());
                    componentBuilder.bold(playerData.getIsChatBold());
                    componentBuilder.italic(playerData.getIsChatItalic());
                    componentBuilder.underlined(playerData.getIsChatUnderlined());


                    StringBuilder itemHoverText = new StringBuilder(ChatColor.stripColor(Utils.getPrettyItemName(e.getPlayer().getInventory().getItemInMainHand())) + "\n");
                    if (e.getPlayer().getInventory().getItemInMainHand().hasItemMeta())
                        if (e.getPlayer().getInventory().getItemInMainHand().getItemMeta().hasLore()) {
                            for (String line : e.getPlayer().getInventory().getItemInMainHand().getItemMeta().getLore()) itemHoverText.append(line).append("\n");
                        }
                    HoverEvent itemHover = new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(itemHoverText.toString()));

                    char[] itemNameArr = (ChatColor.stripColor(Utils.getPrettyItemName(e.getPlayer().getInventory().getItemInMainHand()) + " ")).toCharArray();
                    List<List<ChatColor>> itemNameColors = getColorOfEveryCharacter(Utils.getPrettyItemName(e.getPlayer().getInventory().getItemInMainHand()) + " ");
                    for (int i = 0; i < itemNameArr.length - 1; i++) {
                        componentBuilder.append(itemNameArr[i] + "");
                        componentBuilder.reset();
                        componentBuilder.getCurrentComponent().setHoverEvent(itemHover);
                        for (ChatColor color : itemNameColors.get(i)) {
                            if (ChatColor.BOLD.equals(color)) {
                                componentBuilder.bold(true);
                            } else if (ChatColor.ITALIC.equals(color)) {
                                componentBuilder.italic(true);
                            } else if (ChatColor.UNDERLINE.equals(color)) {
                                componentBuilder.underlined(true);
                            } else if (ChatColor.STRIKETHROUGH.equals(color)) {
                                componentBuilder.strikethrough(true);
                            } else if (ChatColor.RESET.equals(color)) {
                                componentBuilder.reset();
                            } else if (ChatColor.MAGIC.equals(color)) {
                                componentBuilder.obfuscated(true);
                            } else componentBuilder.color(color);
                        }
                    }



                    componentBuilder.append(msg2.replaceAll("\\[item]", ""));
                    componentBuilder.reset();
                } else componentBuilder.append(chatMessage);
            } else componentBuilder.append(chatMessage);
        } else componentBuilder.append(chatMessage);
        if (playerData.getIsChatColorEnabled()) componentBuilder.color(playerData.getChatColor());
        componentBuilder.bold(playerData.getIsChatBold());
        componentBuilder.italic(playerData.getIsChatItalic());
        componentBuilder.underlined(playerData.getIsChatUnderlined());





        for (Player p : Bukkit.getOnlinePlayers()) p.spigot().sendMessage(componentBuilder.create());
        e.setCancelled(true);
    }

    public static List<List<ChatColor>> getColorOfEveryCharacter(String str) {
        str = str.replaceAll("§r§", "§");
        ChatColor defaultColor = ChatColor.WHITE;
        List<List<ChatColor>> colors = new ArrayList<>();
        char[] chars = str.toCharArray();
        List<Character> colorChars = new ArrayList<>();
        int lastSize = 0;
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == '§') {
                if (chars.length >= i + 2) {
                    colorChars.add(chars[i + 1]);
                }
                i++;
            } else {
                int lengthOfFormat = 0;
                for (int z = colorChars.size() - 1; z >= 0; z--) {
                    if (colorChars.get(z) == 'l' || colorChars.get(z) == 'o' || colorChars.get(z) == 'n' || colorChars.get(z) == 'm' || colorChars.get(z) == 'k') lengthOfFormat++;
                    if (colorChars.get(z) == 'x') break;
                }
                if (colorChars.size() == 0) {
                    colors.add(new ArrayList<>());
                    colors.get(colors.size() - 1).add(defaultColor);
                } else if (colorChars.size() >= 7 + lengthOfFormat) {
                    if (colorChars.get(colorChars.size() - 7 - lengthOfFormat) == 'x') {
                        //Should be a hex color, validate it
                        try {
                            Integer.parseInt("" + colorChars.get(colorChars.size() - 6 - lengthOfFormat) +
                                    colorChars.get(colorChars.size() - 5 - lengthOfFormat) +
                                    colorChars.get(colorChars.size() - 4 - lengthOfFormat) +
                                    colorChars.get(colorChars.size() - 3 - lengthOfFormat) +
                                    colorChars.get(colorChars.size() - 2 - lengthOfFormat) +
                                    colorChars.get(colorChars.size() - 1 -  lengthOfFormat), 16);
                            //Is valid
                            colors.add(new ArrayList<>());
                            colors.get(colors.size() - 1).add(ChatColor.of("#" + colorChars.get(colorChars.size() - 6 - lengthOfFormat) +
                                    colorChars.get(colorChars.size() - 5 - lengthOfFormat) +
                                    colorChars.get(colorChars.size() - 4 - lengthOfFormat) +
                                    colorChars.get(colorChars.size() - 3 - lengthOfFormat) +
                                    colorChars.get(colorChars.size() - 2 - lengthOfFormat) +
                                    colorChars.get(colorChars.size() - 1 - lengthOfFormat)));
                            List<ChatColor> li = new ArrayList<>();
                            for (int c = 0; c < lengthOfFormat; c++) li.add(ChatColor.getByChar(colorChars.get(colorChars.size() - 1 - c)));
                            Collections.reverse(li);
                            List<ChatColor> l = colors.get(colors.size() - 1);
                            l.addAll(li);
                            colors.set(colors.size() - 1, l);
                        } catch (NumberFormatException e) {
                            //Was not a valid hex
                            ChatColor color = defaultColor;
                            try {
                                color = ChatColor.getByChar(colorChars.get(colorChars.size() - 1));
                            } catch (IllegalArgumentException x) {
                                for (int z = colorChars.size() - 1; z >= 0; z--) {
                                    try {
                                        color = ChatColor.getByChar(colorChars.get(colorChars.size() - 1));
                                        break;
                                    }  catch (IllegalArgumentException ignore) {
                                    }
                                }
                            }
                            if (!(color.equals(ChatColor.BOLD) || color.equals(ChatColor.ITALIC) || color.equals(ChatColor.UNDERLINE) || color.equals(ChatColor.STRIKETHROUGH) || color.equals(ChatColor.MAGIC))) colors.add(new ArrayList<>());
                            List<ChatColor> l = colors.get(colors.size() - 1);
                            l.add(color);
                            colors.set(colors.size() - 1, l);
                        }
                    } else {
                        //Is a normal minecraft color
                        if (colorChars.size() - lastSize == 0) {
                            colors.add(colors.get(colors.size() - 1));
                        }
                        for (int d = colorChars.size() - lastSize; d > 0; d--) {
                            ChatColor color = defaultColor;
                            try {
                                color = ChatColor.getByChar(colorChars.get(colorChars.size() - d));
                            } catch (IllegalArgumentException x) {
                                for (int z = colorChars.size() - 1; z >= 0; z--) {
                                    try {
                                        color = ChatColor.getByChar(colorChars.get(colorChars.size() - d));
                                        break;
                                    } catch (IllegalArgumentException ignore) {
                                    }
                                }
                            }
                            if (!(color.equals(ChatColor.BOLD) || color.equals(ChatColor.ITALIC) || color.equals(ChatColor.UNDERLINE) || color.equals(ChatColor.STRIKETHROUGH) || color.equals(ChatColor.MAGIC))) colors.add(new ArrayList<>());
                            List<ChatColor> l = colors.get(colors.size() - 1);
                            l.add(color);
                            colors.set(colors.size() - 1, l);
                        }
                    }
                } else {
                    //Is a normal minecraft color
                    if (colorChars.size() - lastSize == 0) {
                        colors.add(colors.get(colors.size() - 1));
                    }
                    for (int d = colorChars.size() - lastSize; d > 0; d--) {
                        ChatColor color = defaultColor;
                        try {
                            color = ChatColor.getByChar(colorChars.get(colorChars.size() - d));
                        } catch (IllegalArgumentException x) {
                            for (int z = colorChars.size() - 1; z >= 0; z--) {
                                try {
                                    color = ChatColor.getByChar(colorChars.get(colorChars.size() - d));
                                    break;
                                } catch (IllegalArgumentException ignore) {
                                }
                            }
                        }
                        if (!(color.equals(ChatColor.BOLD) || color.equals(ChatColor.ITALIC) || color.equals(ChatColor.UNDERLINE) || color.equals(ChatColor.STRIKETHROUGH) || color.equals(ChatColor.MAGIC))) colors.add(new ArrayList<>());
                        List<ChatColor> l = colors.get(colors.size() - 1);
                        l.add(color);
                        colors.set(colors.size() - 1, l);
                    }
                }
                lastSize = colorChars.size();
            }
        }
        return colors;
    }



    public static String chatFilter(String chatMessage) {
        for (String bannedWord : bannedWords){
            StringBuilder censor = new StringBuilder();
            censor.append("*".repeat(bannedWord.length()));
            chatMessage = chatMessage.replaceAll("(?i)" + bannedWord, censor.toString());
        }
        return chatMessage;
    }










































    static {
        bannedWords.add("nigger");
        bannedWords.add("nigga");
        bannedWords.add("fuck");
        bannedWords.add("bitch");
        bannedWords.add("cunt");
        bannedWords.add("pussy");
        bannedWords.add("faggot");
        bannedWords.add("bald");
    }
}
