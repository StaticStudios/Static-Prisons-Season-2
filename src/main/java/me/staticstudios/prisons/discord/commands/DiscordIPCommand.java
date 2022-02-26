package me.staticstudios.prisons.discord.commands;

import me.staticstudios.prisons.discord.BaseDiscordCommand;

public class DiscordIPCommand extends BaseDiscordCommand {
    public DiscordIPCommand() {
        commandName = "ip";
        aliases = new String[]{"serverip"};
        description = "Gives the Minecraft server IP";
        String response = "Server IP: `play.static-studios.net` (Port 25565)";
        codeToRun = () -> {
            sendMessage(response);
        };
        register();
    }
}
