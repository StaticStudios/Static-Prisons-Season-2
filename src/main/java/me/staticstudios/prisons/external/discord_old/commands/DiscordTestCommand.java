package me.staticstudios.prisons.external.discord_old.commands;

import me.staticstudios.prisons.external.discord_old.BaseDiscordCommand;

public class DiscordTestCommand extends BaseDiscordCommand {
    public DiscordTestCommand() {
        commandName = "test";
        description = "This is a test command to verify that the bot is working.";
        String response = "im working";
        codeToRun = () -> {
            sendMessage(response);
        };
        register();
    }
}
