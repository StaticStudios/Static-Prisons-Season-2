package me.staticstudios.prisons.discord;


import me.staticstudios.prisons.discord.commands.*;

public class LoadDiscordCommands {
    public static void initialize() {
        new DiscordHelpCommand();
        new DiscordTestCommand();
        new DiscordInviteCommand();
        new DiscordLinkCommand();
        new DiscordIPCommand();
        new DiscordUnlinkCommand();
        new DiscordPlayerCountCommand();
        new DiscordGetStatsCommand();
        new DiscordGetMinecraftAccountCommand();
        new DiscordVotelinksCommand();
    }
}
