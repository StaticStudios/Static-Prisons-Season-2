package net.staticstudios.prisons.gambling;

import net.staticstudios.gui.GUICreator;
import net.staticstudios.gui.GUIUtils;
import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.gui.MainMenus;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;

public class GamblingMenus extends GUIUtils {

    public static void openMain(Player player) {
        GUICreator c = new GUICreator(27, "Casino");

        c.setItem(12, ench(c.createButton(Material.PLAYER_HEAD, "&a&lCoin Flip (Money)", List.of(
                "2 Players will have a chance to ",
                "win or lose the prize pool. The",
                "house will have a 2% chance to win.",
                "Each player will have a 49% chance",
                "to win the prize pool.",
                "", "&b/cf <amount> <heads|teails>"
        ), (p, t) -> {
            coinFlip(p);
        })));
        c.setItem(14, ench(c.createButton(Material.SUNFLOWER, "&e&lToken Flip (Tokens)", List.of(
                "2 Players will have a chance to ",
                "win or lose the prize pool. The",
                "house will have a 2% chance to win.",
                "Each player will have a 49% chance",
                "to win the prize pool.",
                "", "&b/tf <amount> <heads|teails>"
        ), (p, t) -> {
            tokenFlip(p);
        })));
        c.fill(createGrayPlaceHolder());
        c.open(player);
        c.setOnCloseRun((p, t) -> MainMenus.open(p));
    }

    public static void coinFlip(Player player) {
        GUICreator c = new GUICreator(54, "Coin Flip (Money)");
        c.setItem(0, createLimePlaceHolder());
        c.setItem(8, createLimePlaceHolder());
        c.setItem(9, createLimePlaceHolder());
        c.setItem(17, createLimePlaceHolder());
        c.setItem(18, createLimePlaceHolder());
        c.setItem(26, createLimePlaceHolder());
        c.setItem(27, createLimePlaceHolder());
        c.setItem(35, createLimePlaceHolder());
        c.setItem(36, createLimePlaceHolder());
        c.setItem(44, createLimePlaceHolder());
        c.setItem(45, createLimePlaceHolder());
        c.setItem(53, createLimePlaceHolder());

        int index = 0;
        for (String key : GambleHandler.coinFlips.keySet()) {
            CoinFlip cf = GambleHandler.coinFlips.get(key);
            Material icon = cf.isHeads ? cf.headsIcon : cf.tailsIcon;
            String side = cf.isHeads ? "Heads" : "Tails";
            if ((index + 1) % 7 == 0) index += 3;
            c.setItem(index + 1, c.createButton(icon, "&d&l" + cf.owner.getName(), List.of(
                    "&bBet: &f$" + PrisonUtils.prettyNum(cf.amount),
                    "&bSide: &f" + side,
                    "",
                    (cf.owner.equals(player) ? "&c&lClick to cancel" : "&a&oClick to challenge")
            ), (p, t) -> {
                if (cf.owner.equals(p)) {
                    CoinFlip.removeAFlip(cf.uuid);
                    coinFlip(p);
                    return;
                }
                if (!CoinFlip.checkIfExists(cf.uuid)) {
                    p.sendMessage(ChatColor.RED + "This CoinFlip no longer exists!");
                    coinFlip(p);
                    return;
                }
                if (cf.amount.compareTo(new PlayerData(p.getUniqueId()).getMoney()) > 0) {
                    p.sendMessage(ChatColor.RED + "You do not have enough money to make this bet!");
                    return;
                }
                cf.runBet(p);
            }));
            index++;
        }
        c.fill(createLightGrayPlaceHolder());
        c.open(player);
        c.setOnCloseRun((p, t) -> openMain(p));
    }

    public static void tokenFlip(Player player) {
        GUICreator c = new GUICreator(54, "Token Flip (Tokens)");
        c.setItem(0, createLightBluePlaceHolder());
        c.setItem(8, createLightBluePlaceHolder());
        c.setItem(9, createLightBluePlaceHolder());
        c.setItem(17, createLightBluePlaceHolder());
        c.setItem(18, createLightBluePlaceHolder());
        c.setItem(26, createLightBluePlaceHolder());
        c.setItem(27, createLightBluePlaceHolder());
        c.setItem(35, createLightBluePlaceHolder());
        c.setItem(36, createLightBluePlaceHolder());
        c.setItem(44, createLightBluePlaceHolder());
        c.setItem(45, createLightBluePlaceHolder());
        c.setItem(53, createLightBluePlaceHolder());

        int index = 0;
        for (String key : GambleHandler.tokenFlips.keySet()) {
            TokenFlip tf = GambleHandler.tokenFlips.get(key);
            Material icon = tf.isHeads ? tf.headsIcon : tf.tailsIcon;
            String side = tf.isHeads ? "Heads" : "Tails";
            if ((index + 1) % 7 == 0) index += 3;
            c.setItem(index + 1, c.createButton(icon, "&d&l" + tf.owner.getName(), List.of(
                    "&bBet: &f" + PrisonUtils.prettyNum(tf.amount) + " Tokens",
                    "&bSide: &f" + side,
                    "",
                    (tf.owner.equals(player) ? "&c&lClick to cancel" : "&a&oClick to challenge")
            ), (p, t) -> {
                if (tf.owner.equals(p)) {
                    TokenFlip.removeAFlip(tf.uuid);
                    tokenFlip(p);
                    return;
                }
                if (!TokenFlip.checkIfExists(tf.uuid)) {
                    p.sendMessage(ChatColor.RED + "This TokenFlip no longer exists!");
                    tokenFlip(p);
                    return;
                }
                if (tf.amount.compareTo(new PlayerData(p.getUniqueId()).getTokens()) > 0) {
                    p.sendMessage(ChatColor.RED + "You do not have enough tokens to make this bet!");
                    return;
                }
                tf.runBet(p);
            }));
            index++;
        }
        c.fill(createLightGrayPlaceHolder());
        c.open(player);
        c.setOnCloseRun((p, t) -> openMain(p));
    }

}
