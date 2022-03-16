package me.staticstudios.prisons.gameplay.gambling;

import me.staticstudios.prisons.Main;
import me.staticstudios.prisons.core.data.serverData.PlayerData;
import me.staticstudios.prisons.gameplay.gui.GUI;
import me.staticstudios.prisons.gameplay.gui.GUIPage;
import me.staticstudios.prisons.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.math.BigInteger;
import java.time.Instant;

public class CoinFlip extends Flip {
    public static final Material HEADS_ICON = Material.PLAYER_HEAD;
    public static final Material TAILS_ICON = Material.RABBIT_FOOT;
    public static void createCoinFlip(Player player, BigInteger amount, boolean isHeads) {
        new CoinFlip(player, amount, isHeads);
    }
    public static boolean checkIfExists(String uuid) {
        return GambleHandler.coinFlips.containsKey(uuid);
    }
    public static boolean checkIfThereAreTooManyActiveFlips() {
        return GambleHandler.coinFlips.size() >= GambleHandler.MAX_FLIP_COUNT;
    }
    public static void removeAFlip(String uuid) {
        GambleHandler.coinFlips.remove(uuid);
    }
    public void remove() {
        GambleHandler.coinFlips.remove(uuid);
    }
    public void runBet(Player challenger) {
        remove();
        //Check if owner has enough money
        if (new PlayerData(owner).getMoney().compareTo(amount) < 0) {
            challenger.sendMessage(ChatColor.RED + "The owner of this bet does not have enough money to continue.");
            GUIPage GUIPage;
            GUI.getGUIPage("cf").open(challenger);
            return;
        }
        int win = Utils.randomInt(0, 1);
        runAnimation(challenger, (win == 0 && isHeads) || (win == 1 && !isHeads));
        new PlayerData(owner).removeMoney(amount);
        new PlayerData(challenger).removeMoney(amount);
        Bukkit.getScheduler().runTaskLater(Main.getMain(), new Runnable() {
            @Override
            public void run() {
                if (win == 0) {
                    new PlayerData(owner).addMoney(amount.multiply(BigInteger.TWO));
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        p.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + owner.getName() + " has won a CoinFlip against " + challenger.getName() + " for $" + Utils.prettyNum(amount));
                    }
                } else {
                    new PlayerData(challenger).addMoney(amount.multiply(BigInteger.TWO));
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        p.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + challenger.getName() + " has won a CoinFlip against " + owner.getName() + " for $" + Utils.prettyNum(amount));
                    }
                }
            }
        }, 110);
    }
    void runAnimation(Player challenger, boolean headsWins) {
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            Bukkit.getScheduler().runTaskLater(Main.getMain(), () -> {
                if (finalI % 2 == 0) {
                    GUIPage.guiPages.get("gambleAnimationCF").args = "h";
                } else GUIPage.guiPages.get("gambleAnimationCF").args = "t";
                GUIPage.guiPages.get("gambleAnimationCF").open(challenger);
                GUIPage.guiPages.get("gambleAnimationCF").open(owner);
            }, 10 * i);
        }
        if (headsWins) {
            Bukkit.getScheduler().runTaskLater(Main.getMain(), () -> {
                GUIPage.guiPages.get("gambleAnimationCF").args = "h";
                GUIPage.guiPages.get("gambleAnimationCF").open(challenger);
                GUIPage.guiPages.get("gambleAnimationCF").open(owner);
            }, 100);
        }
    }
    public CoinFlip(Player player, BigInteger amount, boolean isHeads) {
        uuid = player.getUniqueId().toString();
        owner = player;
        this.amount = amount;
        this.isHeads = isHeads;
        expireAt = Instant.now().toEpochMilli() + 15 * 60 * 1000;

        headsIcon = HEADS_ICON;
        tailsIcon = TAILS_ICON;


        GambleHandler.coinFlips.put(uuid, this);
    }
}
