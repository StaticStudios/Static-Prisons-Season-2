package net.staticstudios.prisons.gambling;

import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.data.dataHandling.PlayerData;
import net.staticstudios.prisons.gui.GUI;
import net.staticstudios.prisons.gui.GUIPage;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.math.BigInteger;
import java.time.Instant;

public class TokenFlip extends Flip {
    public static final Material HEADS_ICON = Material.SUNFLOWER;
    public static final Material TAILS_ICON = Material.FEATHER;
    public static void createTokenFlip(Player player, BigInteger amount, boolean isHeads) {
        new TokenFlip(player, amount, isHeads);
    }
    public static boolean checkIfExists(String uuid) {
        return GambleHandler.tokenFlips.containsKey(uuid);
    }
    public static boolean checkIfThereAreTooManyActiveFlips() {
        return GambleHandler.coinFlips.size() > GambleHandler.MAX_FLIP_COUNT;
    }
    public static void removeAFlip(String uuid) {
        GambleHandler.tokenFlips.remove(uuid);
    }
    public void remove() {
        GambleHandler.tokenFlips.remove(uuid);
    }
    public void runBet(Player challenger) {
        remove();
        //Check if owner has enough money
        if (new PlayerData(owner).getTokens().compareTo(amount) < 0) {
            challenger.sendMessage(ChatColor.RED + "The owner of this bet does not have enough money to continue.");
            GUI.getGUIPage("tf").open(challenger);
            return;
        }
        int win = PrisonUtils.randomInt(0, 1);
        runAnimation(challenger, (win == 0 && isHeads) || (win == 1 && !isHeads));
        new PlayerData(owner).removeTokens(amount);
        new PlayerData(challenger).removeTokens(amount);
        Bukkit.getScheduler().runTaskLater(StaticPrisons.getInstance(), () -> {
            if (win == 0) {
                new PlayerData(owner).addTokens(amount.multiply(BigInteger.TWO));
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + owner.getName() + " has won a TokenFlip against " + challenger.getName() + " for " + PrisonUtils.prettyNum(amount) + " Tokens");
                }
            } else {
                new PlayerData(challenger).addTokens(amount.multiply(BigInteger.TWO));
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + challenger.getName() + " has won a TokenFlip against " + owner.getName() + " for " + PrisonUtils.prettyNum(amount) + " Tokens");
                }
            }
        }, 110);
    }
    void runAnimation(Player challenger, boolean headsWins) {
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            Bukkit.getScheduler().runTaskLater(StaticPrisons.getInstance(), () -> {
                if (finalI % 2 == 0) {
                    GUIPage.guiPages.get("gambleAnimationTF").args = "h";
                } else GUIPage.guiPages.get("gambleAnimationTF").args = "t";
                GUIPage.guiPages.get("gambleAnimationTF").open(challenger);
                GUIPage.guiPages.get("gambleAnimationTF").open(owner);
            }, 10 * i);
        }
        if (headsWins) {
            Bukkit.getScheduler().runTaskLater(StaticPrisons.getInstance(), () -> {
                GUIPage.guiPages.get("gambleAnimationTF").args = "h";
                GUIPage.guiPages.get("gambleAnimationTF").open(challenger);
                GUIPage.guiPages.get("gambleAnimationTF").open(owner);
            }, 100);
        }
    }
    public TokenFlip(Player player, BigInteger amount, boolean isHeads) {
        uuid = player.getUniqueId().toString();
        owner = player;
        this.amount = amount;
        this.isHeads = isHeads;
        expireAt = Instant.now().toEpochMilli() + 15 * 60 * 1000;

        headsIcon = HEADS_ICON;
        tailsIcon = TAILS_ICON;


        GambleHandler.tokenFlips.put(uuid, this);
    }
}
