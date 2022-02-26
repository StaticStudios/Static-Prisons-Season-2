package me.staticstudios.prisons.customItems;

import me.staticstudios.prisons.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class MultiPouch {
    public int timeBetweenFrames = 7;
    public float minMultiplierAmount;
    public float maxMultiplierAmount;
    public float multiplierAmount;
    public int minMultiplierTime;
    public int maxMultiplierTime;
    public int multiplierTime;
    String formattedRewardValue;
    public boolean announceRewardInChat;

    public void getRewardValue() {
        formattedRewardValue = "+" + multiplierAmount + "x For: " + multiplierTime / 60 + " Minutes";
    }

    public void animateOpeningPouch (Player player, String rewardMessage) {
        getRewardValue();
        for (int i = 0; i < formattedRewardValue.length() + 2; i++) {
            int finalI = i;
            Bukkit.getScheduler().runTaskLater(Main.getMain(), () -> animateFrame(player, formattedRewardValue, rewardMessage.replace("{reward}", "+" + multiplierAmount + "x multiplier for: " + multiplierTime / 60 + " minutes"), finalI, formattedRewardValue.length() + 1), i * timeBetweenFrames);
        }
    }
    void animateFrame(Player player, String rewardValue, String announcementMessage, int currentPos, int finished) {
        if (currentPos == finished) {
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
        player.sendTitle(ChatColor.LIGHT_PURPLE + "" + title, "", fadeIn, 60, 10);
    }
}
