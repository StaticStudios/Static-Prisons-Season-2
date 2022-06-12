package net.staticstudios.utils;

import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.HashMap;
import java.util.Map;

public class ChatPrompts {
    public static Map<Player, Prompt<Player, AsyncPlayerChatEvent>> prompts = new HashMap<>();

    public static Prompt<Player, AsyncPlayerChatEvent> prompt = new Prompt<>(prompt -> {
        prompt.user.sendMessage("hi");
        prompt.done(false);
    }, new Prompt<>(prompt -> {
        prompt.user.sendMessage("hi, im done now");
        prompts.remove(prompt.user);
    }, null));

    public static void startPrompt(Player player) {
        prompts.put(player, prompt.clone());
    }
    public static void listen(AsyncPlayerChatEvent event) {
        prompts.get(event.getPlayer()).accept(event.getPlayer(), event);
    }
}
