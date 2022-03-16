package me.staticstudios.prisons.external.discord_old.commands;


import me.staticstudios.prisons.external.discord_old.BaseDiscordCommand;

public class DiscordVotelinksCommand extends BaseDiscordCommand {
    public DiscordVotelinksCommand() {
        commandName = "votelinks";
        description = "This command will send a message with all of the vote links";
        codeToRun = () -> {
            sendMessage("**__Server Vote Links:__**\n" +
                    "**Site 1:** http://static-studios.net/vote1\n" +
                    "**Site 2:** http://static-studios.net/vote2\n" +
                    "**Site 3:** http://static-studios.net/vote3\n" +
                    "**Site 4:** http://static-studios.net/vote4\n" +
                    "**Site 5:** http://static-studios.net/vote5\n" +
                    "**Site 6:** http://static-studios.net/vote6\n" +
                    "**Site 7:** http://static-studios.net/vote7\n"
            );
        };
        register();
    }
}
