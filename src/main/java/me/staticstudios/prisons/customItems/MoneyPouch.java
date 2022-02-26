package me.staticstudios.prisons.customItems;

import me.staticstudios.prisons.Main;
import me.staticstudios.prisons.data.serverData.PlayerData;
import me.staticstudios.prisons.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.math.BigInteger;
import java.text.NumberFormat;
import java.util.Locale;

public class MoneyPouch {
    public int timeBetweenFrames = 4;
    public BigInteger minReward;
    public BigInteger maxReward;
    public BigInteger reward = null;
    String formattedRewardValue;
    public boolean announceRewardInChat;

    public void getRewardValue() {
        formattedRewardValue = NumberFormat.getNumberInstance(Locale.US).format(reward);
    }

    public void animateOpeningPouch (Player player, PlayerData playerData, String rewardMessage) {
        getRewardValue();
        if (reward == null) return;
        for (int i = 0; i < formattedRewardValue.length() + 2; i++) {
            int finalI = i;
            Bukkit.getScheduler().runTaskLater(Main.getMain(), () -> animateFrame(player, playerData, formattedRewardValue, rewardMessage.replace("{reward}", Utils.prettyNum(reward)), finalI, formattedRewardValue.length() + 1), i * timeBetweenFrames);
        }
    }
    void animateFrame(Player player, PlayerData playerData, String rewardValue, String announcementMessage, int currentPos, int finished) {
        if (currentPos == finished) {
            playerData.addMoney(reward);
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
        player.sendTitle(ChatColor.GRAY + "$" + title, "", fadeIn, 60, 10);
    }
}
