package me.staticstudios.prisons.external.discord_old;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class DiscordListener extends ListenerAdapter {
    public void onMessageReceived(MessageReceivedEvent e) {
        DiscordCommandHandler.messageReceived(e);
    }
}
