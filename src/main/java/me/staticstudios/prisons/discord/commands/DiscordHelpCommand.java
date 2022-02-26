package me.staticstudios.prisons.discord.commands;

import me.staticstudios.prisons.discord.BaseDiscordCommand;
import me.staticstudios.prisons.discord.DiscordCommandHandler;

public class DiscordHelpCommand extends BaseDiscordCommand {
    public DiscordHelpCommand() {
        commandName = "help";
        aliases = new String[]{"h", "halp"};
        description = "Running this command will list all available commands with their description(s)";
        codeToRun = () -> {
            StringBuilder response = new StringBuilder("Here is a list of all of the bot's commands:\n");
            for (String key : DiscordCommandHandler.commands.keySet()) {
                BaseDiscordCommand command = DiscordCommandHandler.commands.get(key);
                if (command.isAlias) continue;
                response.append(command.commandName);
                if (command.aliases != null) {
                    response.append("(");
                    for (int i = 0; i < command.aliases.length; i++) {
                        String alias = command.aliases[i];
                        response.append(alias);
                        if (i + 1 != command.aliases.length) {
                            response.append(", ");
                        } else response.append(")");
                    }
                }
                response.append(" - ").append(command.description).append("\n");
            }
            sendMessage(response.toString());
        };
        register();
    }
}
