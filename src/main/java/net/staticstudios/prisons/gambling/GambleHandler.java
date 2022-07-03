package net.staticstudios.prisons.gambling;

import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.math.BigInteger;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class GambleHandler { //todo more gambling thinsg
    public static final int MAX_FLIP_COUNT = 42;
    public static Map<String, TokenFlip> tokenFlips = new HashMap<>();
    public static Map<String, CoinFlip> coinFlips = new HashMap<>();

    public void addTokenFlip(Player player, BigInteger amount, boolean isHeads) {
        if (tokenFlips.containsKey(player.getUniqueId().toString())) {
            player.sendMessage(ChatColor.RED + "You cannot create a new TokenFlip as you already have an active TokenFlip!");
            return;
        }
        if (amount.compareTo(BigInteger.ZERO) != 1) {
            player.sendMessage(ChatColor.RED + "You cannot create a TokenFlip worth less than 1 Token!");
            return;
        }
        TokenFlip.createTokenFlip(player, amount, isHeads);
        player.sendMessage(ChatColor.AQUA + "You have successfully created a TokenFlip for " + ChatColor.GOLD + PrisonUtils.prettyNum(amount) + "Token(s)");
    }
    public void addCoinFlip(Player player, BigInteger amount, boolean isHeads) {
        if (coinFlips.containsKey(player.getUniqueId().toString())) {
            player.sendMessage(ChatColor.RED + "You cannot create a new CoinFlip as you already have an active CoinFlip!");
            return;
        }
        if (amount.compareTo(BigInteger.ZERO) != 1) {
            player.sendMessage(ChatColor.RED + "You cannot create a CoinFlip worth less than $1!");
            return;
        }
        CoinFlip.createCoinFlip(player, amount, isHeads);
        player.sendMessage(ChatColor.AQUA + "You have successfully created a CoinFlip for " + ChatColor.GREEN + "$" + PrisonUtils.prettyNum(amount));
    }


    //Player has left the game and any active flips should be deleted
    public static void playerLeft(String uuid) {
        coinFlips.remove(uuid);
        tokenFlips.remove(uuid);
    }

    //Checks if a flip should be expired or not
    public static void updateFlips() {
        for (String key : coinFlips.keySet()) {
            CoinFlip cf = coinFlips.get(key);
            if (cf.expireAt <= Instant.now().toEpochMilli()) {
                cf.owner.sendMessage(ChatColor.RED + "Your CoinFlip for " + ChatColor.GREEN + "$" + PrisonUtils.prettyNum(cf.amount) + ChatColor.RED + " has expired!");
                cf.remove();
            }
        }
        for (String key : tokenFlips.keySet()) {
            TokenFlip tf = tokenFlips.get(key);
            if (tf.expireAt <= Instant.now().toEpochMilli()) {
                tf.owner.sendMessage(ChatColor.RED + "Your TokenFlip for " + ChatColor.GOLD + PrisonUtils.prettyNum(tf.amount) + ChatColor.RED + " has expired!");
                tf.remove();
            }
        }
    }
}
