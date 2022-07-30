package net.staticstudios.prisons.utils;

import org.bukkit.ChatColor;
import org.bukkit.World;

public class Constants {
    public static World MINES_WORLD;
    public static final int VOTES_NEEDED_FOR_VOTE_PARTY = 50;
    public static long[] PRESTIGE_MINE_REQUIREMENTS = new long[15];
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
    public static String[] TIPS = {
            ChatColor.translateAlternateColorCodes('&', "&b&lTips &8&l> &fTired of seeing tips? You can disable them in your settings. &7/settings"),
            ChatColor.translateAlternateColorCodes('&', "&b&lTips &8&l> &fJoin our Discord for update details, giveaways, and more! &9/discord"),
            ChatColor.translateAlternateColorCodes('&', "&b&lTips &8&l> &fWant to help support the server for free? All you have to do is vote! You will receive a vote crate key for every time that you vote! &a/vote"),
            ChatColor.translateAlternateColorCodes('&', "&b&lTips &8&l> &fClaim a free reward every day with &7/dailyrewards&f. You must join our discord server and link your accounts to claim this. &9/discord link"),
            ChatColor.translateAlternateColorCodes('&', "&b&lTips &8&l> &fAt the end of every season we give the top players real money, check the leaderboards to see if you qualify! Join our discord for more information."),
            ChatColor.translateAlternateColorCodes('&', "&b&lTips &8&l> &fYou can purchase a rank, crate keys, private mines, and more on our store! This is by far one of the best ways to help support the server! &d/store"),
            ChatColor.translateAlternateColorCodes('&', "&b&lTips &8&l> &fHave a question/concern? We would love to discuss it further with you, join our discord and make a ticket! &9/discord"),
            ChatColor.translateAlternateColorCodes('&', "&b&lTips &8&l> &fLost your pickaxe and need a new one? Type &7/pick"),
            ChatColor.translateAlternateColorCodes('&', "&b&lTips &8&l> &fYou can rankup multiple times at once by using the &7/rankupmax&f command!"),
            ChatColor.translateAlternateColorCodes('&', "&b&lTips &8&l> &fDid you know that you can get auto sell if you boost our Discord?! You get other perks as well!"),
    };
}
