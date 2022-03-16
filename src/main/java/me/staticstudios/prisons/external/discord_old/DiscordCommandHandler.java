package me.staticstudios.prisons.external.discord_old;

import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.*;

public class DiscordCommandHandler {
    public final static String prefix = "prisons!";
    public static Map<String, BaseDiscordCommand> commands = new HashMap<>();
    public static void registerCommand(BaseDiscordCommand c) {
        commands.put(c.commandName, c);
        if (c.aliases != null) {
            for (String alias : c.aliases) {
                BaseDiscordCommand _c = new BaseDiscordCommand();
                _c.isAlias = true;
                _c.fromCommand = c.commandName;
                commands.put(alias, _c);
            }
        }
    }
    public static void messageReceived(MessageReceivedEvent e) {
        String message = e.getMessage().getContentRaw();
        if (message.toLowerCase().startsWith(prefix)) {
            message = message.substring(prefix.length());
            if (commands.containsKey(message.toLowerCase().split(" ")[0])) {
                BaseDiscordCommand command = commands.get(message.toLowerCase().split(" ")[0]);
                if (command.isAlias) command = commands.get(command.fromCommand);
                command.e = e;
                command.args = message.substring(message.toLowerCase().split(" ")[0].length()).split(" ");
                if (command.args[0].equals("")) {
                    List<String> temp = new ArrayList<String>(Arrays.asList(command.args));
                    temp.remove(0);
                    command.args = temp.toArray(new String[0]);
                }
                if (!message.substring(message.toLowerCase().split(" ")[0].length()).contains(" ")) command.args = new String[0];
                if (command.requiresCertainRole) {
                    List<Role> roles = e.getMessage().getGuild().getMemberById(e.getAuthor().getId()).getRoles();
                    for (Role role : roles) {
                        for (String id : command.allowedRoles) {
                            if (role.getId().equals(id)) {
                                DiscordBot.sendMessage(e.getTextChannel(), id + " " + role.getName());
                                command.codeToRun.run();
                                return;
                            }
                        }
                    }
                    DiscordBot.sendMessage(e.getTextChannel(), "You do not have permission to run this command!");
                    return;
                }
                command.codeToRun.run();
            } else {
                DiscordBot.sendMessage(e.getTextChannel(), "Command was not recognised! Type `" + prefix + "help` for a full list of commands.");
            }
        }
    }
}
