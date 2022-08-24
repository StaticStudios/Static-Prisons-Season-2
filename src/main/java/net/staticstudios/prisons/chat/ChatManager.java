package net.staticstudios.prisons.chat;

import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.chat.games.ChatGames;
import net.staticstudios.prisons.chat.messaging.commands.MessageCommand;
import net.staticstudios.prisons.chat.messaging.commands.MessageSpyCommand;
import net.staticstudios.prisons.chat.nicknames.NicknameCommand;
import net.staticstudios.prisons.chat.tags.ChatTags;
import net.staticstudios.prisons.commands.CommandManager;
import net.staticstudios.prisons.commands.normal.ReplyCommand;

public final class ChatManager {
    public static void init() {
        CommandManager.registerCommand("message", new MessageCommand());
        CommandManager.registerCommand("reply", new ReplyCommand());
        CommandManager.registerCommand("watchmessages", new MessageSpyCommand());
        CommandManager.registerCommand("nickname", new NicknameCommand());

        ChatTags.init();
        ChatGames.init();

        StaticPrisons.getInstance().getServer().getPluginManager().registerEvents(new ChatEventListener(), StaticPrisons.getInstance());
    }
}
