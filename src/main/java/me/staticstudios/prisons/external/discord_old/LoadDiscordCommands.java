package me.staticstudios.prisons.external.discord_old;


import me.staticstudios.prisons.external.discord_old.commands.*;

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
