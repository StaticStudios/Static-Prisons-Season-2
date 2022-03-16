package me.staticstudios.prisons.external.discord_old;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.ArrayList;
import java.util.List;

public class BaseDiscordCommand {
    public MessageReceivedEvent e;
    public String commandName = null;
    public boolean requiresCertainRole = false;
    public List<String> allowedRoles = new ArrayList<>();
    public Runnable codeToRun;
    public String description = null;
    public String[] aliases = null;


    public boolean isAlias = false;
    public String fromCommand;
    public String[] args;
    public void register() {
        DiscordCommandHandler.registerCommand(this);
    }
    public void sendMessage(String message) {
        DiscordBot.sendMessage(e.getTextChannel(), message);
    }
}
