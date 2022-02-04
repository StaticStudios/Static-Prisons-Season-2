package me.staticstudios.prisons.misc.chat;

import me.staticstudios.prisons.data.serverData.PlayerData;
import me.staticstudios.prisons.misc.tablist.TabList;
import me.staticstudios.prisons.utils.Utils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class CustomChatMessage {
    AsyncPlayerChatEvent e;

    String prefix = null;
    String suffix = null;
    String playerName;
    String playerNickName = null;
    boolean useNickName = false;
    PlayerData playerData;

    public CustomChatMessage(AsyncPlayerChatEvent e) {
        this.e = e;
        this.playerName = e.getPlayer().getName();
        playerData = new PlayerData(e.getPlayer());

        //Check if a player has a nickname
    }

    public void sendFormatted() {
        String rankTag = ChatColor.translateAlternateColorCodes('&', "&7Member");
        for (String[] arr : TabList.TEAMS) {
            if (arr[2].equals(playerData.getTabListPrefixID())) {
                rankTag = arr[1].substring(0, arr[1].length() - 1);
                break;
            }
        }
        String prefix = ChatColor.translateAlternateColorCodes('&', "&8[&dP" + Utils.prettyNum(playerData.getPrestige()) + "&8] " + ChatTags.getChatTagFromID(playerData.getChatTag1())  + ChatTags.getChatTagFromID(playerData.getChatTag2()) +
                "&8[" + rankTag + "&8] ");
        String suffix = "";
        String playerName = ChatColor.GREEN + this.playerName;
        if (useNickName) playerName = playerNickName;
        if (this.prefix != null) prefix = this.prefix + " ";
        if (this.suffix != null) suffix = this.suffix + " ";
        e.setFormat(prefix + ChatColor.RESET + playerName + ChatColor.RESET + suffix + ChatColor.RESET + ChatColor.DARK_GRAY + ":" + ChatColor.RESET + " " + ChatColor.translateAlternateColorCodes('&', e.getMessage()));
    }
}
