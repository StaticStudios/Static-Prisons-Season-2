package net.staticstudios.prisons.UI;

import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.utils.PrisonUtils;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

public class PlayerUI {

    public static void updateActionbar(Player player) {
        PlayerData playerData = new PlayerData(player);
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(playerData.getSecondaryUITheme() + "Your Backpack: " + PrisonUtils.addCommasToNumber(playerData.getBackpackItemCount()) + "/" + PrisonUtils.addCommasToNumber(playerData.getBackpackSize()) + " Blocks"));
    }


}
