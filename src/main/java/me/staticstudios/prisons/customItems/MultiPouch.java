package me.staticstudios.prisons.customItems;

import me.staticstudios.prisons.Main;
import me.staticstudios.prisons.newData.dataHandling.PlayerData;
import me.staticstudios.prisons.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.math.BigDecimal;

public class MultiPouch {
    public int timeBetweenFrames = 4;
    public BigDecimal multiplierAmount;
    public int multiplierTime;
    String formattedRewardValue;

    public void getRewardValue() {
        formattedRewardValue = "+" + multiplierAmount + "x For: " + multiplierTime + " Minutes";
    }

    public void animateOpeningPouch (Player player, PlayerData playerData, String rewardMessage) {
        getRewardValue();
        for (int i = 0; i < formattedRewardValue.length() + 2; i++) {
            int finalI = i;
            Bukkit.getScheduler().runTaskLater(Main.getMain(), () -> animateFrame(player, formattedRewardValue, rewardMessage.replace("{reward}", formattedRewardValue), finalI, formattedRewardValue.length() + 1), i * timeBetweenFrames);
        }
    }
    void animateFrame(Player player, String rewardValue, String announcementMessage, int currentPos, int finished) {
        if (currentPos == finished) {
            Utils.addItemToPlayersInventoryAndDropExtra(player, Vouchers.getMultiplierNote(multiplierAmount, multiplierTime));
            player.sendMessage(announcementMessage);
            return;
        }
        StringBuilder title = new StringBuilder();
        int fadeIn = 10;
        if (currentPos != 0) fadeIn = 0;
        for (int i = 0; i < rewardValue.length(); i++) {
            if (currentPos <= i) {
                title.append(ChatColor.translateAlternateColorCodes('&', "&k" + rewardValue.charAt(i)));
            } else {
                title.append(rewardValue.charAt(i));
            }
        }
        player.sendTitle(ChatColor.GRAY + "" + title, "", fadeIn, 60, 10);
    }
}
