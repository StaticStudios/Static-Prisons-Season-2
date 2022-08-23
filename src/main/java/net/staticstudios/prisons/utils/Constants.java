package net.staticstudios.prisons.utils;

import net.kyori.adventure.text.Component;
import org.bukkit.World;

import java.util.List;

import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.NamedTextColor.*;


public class Constants {
    public static World MINES_WORLD;
    public static final int VOTES_NEEDED_FOR_VOTE_PARTY = 50;
    public static long[] PRESTIGE_MINE_REQUIREMENTS = new long[15];

    // TODO: 23/08/2022 - Config file + minimessage
    public static List<Component> TIPS = List.of(
            Prefix.TIPS.append(text("Tired of seeing tips? You can disable them in your settings. ").append(text("/settings").color(GRAY))),
            Prefix.TIPS.append(text("Want to help support the server for free? All you have to do is vote! You will receive a vote crate key for every time that you vote! ").append(text("/vote").color(GREEN))),
            Prefix.TIPS.append(text("Claim a free reward every day with ").append(text("/dailyrewards").color(GRAY)).append(text(" You must join our discord server and link your accounts to claim this. ").append(text("/discord link").color(BLUE)))),
            Prefix.TIPS.append(text("You can rankup multiple times at once by using the ").append(text("/rankupmax").color(GRAY)).append(text(" command!"))),
            Prefix.TIPS.append(text("You can purchase a rank, crate keys, private mines, and more on our store! This is by far one of the best ways to help support the server! ").append(text("/store").color(LIGHT_PURPLE))),
            Prefix.TIPS.append(text("At the end of every season we give the top players real money, check the leaderboards to see if you qualify! Join our discord for more information.")),
            Prefix.TIPS.append(text("Lost your pickaxe and need a new one? Type ").append(text("/pick").color(GRAY)).append(text(" to get a new one!"))),
            Prefix.TIPS.append(text("You can use the ").append(text("/rankup").color(GRAY)).append(text(" command to rankup multiple times at once!"))),
            Prefix.TIPS.append(text("Join our Discord for update details, giveaways, and more! ").append(text("/discord").color(BLUE))),
            Prefix.TIPS.append(text("Have a question/concern? We would love to discuss it further with you, join our discord and make a ticket! ").append(text("/discord").color(BLUE))),
            Prefix.TIPS.append(text("Did you know that you can get auto sell if you boost our Discord?! You get other perks as well!"))
    );
}
