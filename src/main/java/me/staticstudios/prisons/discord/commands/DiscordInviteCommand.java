package me.staticstudios.prisons.discord.commands;

import me.staticstudios.prisons.discord.BaseDiscordCommand;

public class DiscordInviteCommand extends BaseDiscordCommand {
    public DiscordInviteCommand() {
        commandName = "invite";
        aliases = new String[]{"invitecode", "invitelink", "discordcode"};
        description = "This command will give you the main invite link to this discord.";
        codeToRun = () -> {
            sendMessage("https://discord.gg/9S6K9E5");
        };
        register();
    }
}
