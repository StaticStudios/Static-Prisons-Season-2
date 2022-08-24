package net.staticstudios.prisons.chat;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.staticstudios.prisons.chat.formatter.CustomChatMessage;
import net.staticstudios.prisons.chat.games.ChatGames;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public final class ChatEventListener implements Listener {
    @EventHandler
    public void onChat(AsyncChatEvent e) {
        CustomChatMessage.format(e);
        ChatGames.onChat(e);
    }
}
