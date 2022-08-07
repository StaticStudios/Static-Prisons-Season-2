package net.staticstudios.prisons.customItems;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.time.Duration;

public class MultiPouch {
    public int timeBetweenFrames = 4;
    public double multiplierAmount;
    public int multiplierTime;
    String formattedRewardValue;

    public void getRewardValue() {
        formattedRewardValue = "+" + multiplierAmount + "x For: " + multiplierTime + " Minutes";
    }

    public void animateOpeningPouch (Player player, PlayerData playerData, String rewardMessage) {
        getRewardValue();
        for (int i = 0; i < formattedRewardValue.length() + 2; i++) {
            int finalI = i;
            Bukkit.getScheduler().runTaskLater(StaticPrisons.getInstance(), () -> animateFrame(player, formattedRewardValue, rewardMessage.replace("{reward}", formattedRewardValue), finalI, formattedRewardValue.length() + 1), i * timeBetweenFrames);
        }
    }
    void animateFrame(Player player, String rewardValue, String announcementMessage, int currentPos, int finished) {
        if (currentPos == finished) {
            PrisonUtils.Players.addToInventory(player, Vouchers.getMultiplierNote(multiplierAmount, multiplierTime));
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
        player.showTitle(Title.title(Component.text(ChatColor.GRAY + "" + title), Component.text(""), Title.Times.of(Duration.ofMillis(fadeIn * 50), Duration.ofMillis(2000), Duration.ofMillis(500))));
//        player.sendTitle(ChatColor.GRAY + "" + title, "", fadeIn, 60, 10);
    }
}
