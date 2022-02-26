package me.staticstudios.prisons.discord.commands;

import me.staticstudios.prisons.discord.BaseDiscordCommand;
import org.bukkit.Bukkit;

public class DiscordPlayerCountCommand extends BaseDiscordCommand {
    public DiscordPlayerCountCommand() {
        commandName = "playercount";
        aliases = new String[]{"pc", "players"};
        description = "Running this command will send a message stating how many players are currently online.";
        codeToRun = () -> {
            sendMessage("There are currently `" + Bukkit.getOnlinePlayers().size() + "` player(s) online.");
        };
        register();
    }
}
