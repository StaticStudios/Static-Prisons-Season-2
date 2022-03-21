package me.staticstudios.prisons.gameplay.UI;

import me.staticstudios.prisons.core.data.serverData.PlayerData;
import me.staticstudios.prisons.utils.Utils;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class PlayerUI {

    public static void updateActionbar(Player player) {
        Material currentTheme = new PlayerData(player).getUITheme();
        PlayerData playerData = new PlayerData(player);
        ChatColor color;
        switch (currentTheme) {
            case PURPLE_DYE -> color = ChatColor.LIGHT_PURPLE;
            case LIME_DYE -> color = ChatColor.GREEN;
            case RED_DYE -> color = ChatColor.RED;
            case ORANGE_DYE -> color = ChatColor.GOLD;
            default -> color = ChatColor.AQUA;
        }
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(color + "Your Backpack: " + Utils.addCommasToNumber(playerData.getBackpack().getItemCount()) + "/" + Utils.addCommasToNumber(playerData.getBackpack().getSize()) + " Blocks"));

    }
}
