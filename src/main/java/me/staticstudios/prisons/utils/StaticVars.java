package me.staticstudios.prisons.utils;

import org.bukkit.ChatColor;

import java.math.BigInteger;

public class StaticVars {
    public static final int VOTES_NEEDED_FOR_VOTE_PARTY = 50;
    public static final Long[] PRESTIGE_MINE_REQUIREMENTS = {
            5L,
            50L,
            100L,
            250L,
            500L,
            1000L,
            2500L,
            5000L,
            10000L,
            20000L,
            30500L,
            50000L,
            75000L,
            100000L,
            150000L,
    };
    public static final char[] A_THROUGH_Z = {
            'A',
            'B',
            'C',
            'D',
            'E',
            'F',
            'G',
            'H',
            'I',
            'J',
            'K',
            'L',
            'M',
            'N',
            'O',
            'P',
            'Q',
            'R',
            'S',
            'T',
            'U',
            'V',
            'W',
            'X',
            'Y',
            'Z',
    };
    public static final String[] TIPS = {
            ChatColor.translateAlternateColorCodes('&', "&b&lTips &8&l> &fTired of seeing tips? You can disable them in your settings. &7/settings"),
            ChatColor.translateAlternateColorCodes('&', "&b&lTips &8&l> &fJoin our Discord for update details, giveaways, and more! &9/discord"),
            ChatColor.translateAlternateColorCodes('&', "&b&lTips &8&l> &fWant to help support the server for free? All you have to do is vote! You will receive a vote crate key for every time that you vote! &a/vote"),
            ChatColor.translateAlternateColorCodes('&', "&b&lTips &8&l> &fClaim a free reward every day with &7/dailyrewards&f. You must join our discord server and link your accounts to claim this. &9/discord link"),
            ChatColor.translateAlternateColorCodes('&', "&b&lTips &8&l> &fAt the end of every season we give the top players real money, check the leaderboards to see if you qualify! Join our discord for more information."),
            ChatColor.translateAlternateColorCodes('&', "&b&lTips &8&l> &fYou can purchase a rank, crate keys, private mines, and more on our store! This is by far one of the best ways to help support the server! &d/store"),
            ChatColor.translateAlternateColorCodes('&', "&b&lTips &8&l> &fHave a question/concern? We would love to discuss it further with you, join our discord and make a ticket! &9/discord"),
            ChatColor.translateAlternateColorCodes('&', "&b&lTips &8&l> &fLost your pickaxe and need a new one? Type &7/pick"),
    };
}
