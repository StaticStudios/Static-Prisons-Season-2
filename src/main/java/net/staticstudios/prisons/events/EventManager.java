package net.staticstudios.prisons.events;

import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.customItems.CustomItems;
import net.staticstudios.prisons.utils.Utils;
import net.staticstudios.prisons.utils.WeightedElements;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;

import java.time.Instant;
import java.util.*;

public class EventManager {
    static List<ChatEvent> activeEvents = new ArrayList<>();
    public static final String[] wordUnscrambleWords = new String[]{
            "cat",
            "minecraft",
            "grass",
            "stone",
            "cobblestone",
            "hippo",
            "water",
            "house",
            "vacuum",
            "yellow",
            "static",
            "staticstudios",
            "lion",
            "monitor",
            "computer",
            "spiderman",
            "keyboard",
            "school",
            "lemon",
            "coffee",
            "discord",
            "nitro",
            "airplane",
            "submarine",
            "military",
            "vote",
            "prisons",
            "pinecone",
            "weewoo",
            "prestige",
            "rankup",
            "cell",
            "money",
            "tokens",
            "pickaxe",
            "picture",
            "word"
    };
    //Question, answer
    public static final String[][] mathQuestions = new String[][]{
            {"1 + 10 * 6 - 2", "59"},
            {"156 - 72 + 12 * 4", "132"},
            {"16 - 2^4", "0"},
            {"(142 + 22 + 40 - 8 * 3) / 18 * 10", "1"},
            {"2^6", "64"},
            {"33 - 65 + 72 * 35 + 72 / 12", "2494"},
            {"(23857 + 146 * 1247^3.4 / 69 - 16532 + 12674621 * 2)^0", "1"},
            {"1 + 1", "2"},
            {"1^24897124", "1"},
            {"62 + 18 - 10", "70"},

    };

    public static void chatMessageReceived(AsyncPlayerChatEvent e) {
        if (activeEvents.size() == 0) return;
        try {
            for (ChatEvent chatEvent : activeEvents) {
                if (chatEvent == null) continue;
                if (chatEvent.onGuess(e)) return;
            }
        } catch (ConcurrentModificationException ignore) {}
    }
    public static void runNewEvent() {
        switch (Utils.randomInt(0, 1)) {
            case 0 -> runWordUnscramble();
            case 1 -> runMath();
        }

    }
    public static void runWordUnscramble() {
        ChatEvent event = new ChatEvent() {
            @Override
            void giveRewards(Player player, String guessed) {
                ItemStack reward = new WeightedElements<ItemStack>()
                        .add(CustomItems.getCommonCrateKey(4), 10)
                        .add(CustomItems.getRareCrateKey(3), 20)
                        .add(CustomItems.getLegendaryCrateKey(2), 10)
                        .add(CustomItems.getLegendaryCrateKey(1), 10)
                        .add(CustomItems.getStaticCrateKey(1), 10)
                        .getRandom();
                Utils.Players.addToInventory(player, reward);
                for (Player p : Bukkit.getOnlinePlayers())
                    p.sendMessage(ChatColor.AQUA + player.getName() + ChatColor.WHITE + " has won a chat event! They won " + reward.getAmount() + "x " + Utils.getPrettyItemName(reward) + "!" + ChatColor.WHITE + " They guessed: " + ChatColor.GREEN + guessed);
            }
        };
        StringBuilder scrambledVersionOfTheWord = new StringBuilder();
        String currentWord = wordUnscrambleWords[Utils.randomInt(0, wordUnscrambleWords.length - 1)];
        List<Character> chars = new ArrayList<>();
        for (char c : currentWord.toCharArray()) {
            chars.add(c);
        }
        while (chars.size() > 0) {
            int i = Utils.randomInt(0, chars.size() - 1);
            scrambledVersionOfTheWord.append(chars.get(i));
            chars.remove(i);
        }
        event.listenFor = currentWord;
        activeEvents.add(event);
        Bukkit.broadcastMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "Word Un-Scramble " + ChatColor.DARK_GRAY + "> " + ChatColor.RESET + ChatColor.LIGHT_PURPLE + "EVENT STARTED: " + ChatColor.AQUA + "the first player to currently unscramble this word will get a reward! " + ChatColor.GREEN + "" + ChatColor.BOLD + scrambledVersionOfTheWord);
    }
    public static void runTrivia() {

    }
    public static void runMath() {
        ChatEvent event = new ChatEvent() {
            @Override
            void giveRewards(Player player, String guessed) {
                ItemStack reward = null;
                switch (Utils.randomInt(1, 6)) {
                    case 1 -> reward = CustomItems.getCommonCrateKey(4);
                    case 2, 3 -> reward = CustomItems.getRareCrateKey(3);
                    case 4 -> reward = CustomItems.getLegendaryCrateKey(2);
                    case 5 -> reward = CustomItems.getLegendaryCrateKey(1);
                    case 6 -> reward = CustomItems.getStaticCrateKey(1);
                }
                Utils.Players.addToInventory(player, reward);
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.sendMessage(ChatColor.AQUA + player.getName() + ChatColor.WHITE + " has won a chat event! They won " + reward.getAmount() + "x " + Utils.getPrettyItemName(reward) + "!" + ChatColor.WHITE + " They guessed: " + ChatColor.GREEN + guessed);
                }
            }
        };
        int eq = Utils.randomInt(0, mathQuestions.length - 1);
        String equation = mathQuestions[eq][0];
        event.listenFor = mathQuestions[eq][1];
        activeEvents.add(event);
        Bukkit.broadcastMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "Math Equation " + ChatColor.DARK_GRAY + "> " + ChatColor.RESET + ChatColor.LIGHT_PURPLE + "EVENT STARTED: " + ChatColor.AQUA + "the first player to currently answer the following math equation will win a reward! " + ChatColor.GREEN + equation);
    }

}

class ChatEvent{
    String listenFor = "";
    long expireAt = Instant.now().getEpochSecond() + 120;
    boolean onGuess(AsyncPlayerChatEvent e) {
        if (Instant.now().getEpochSecond() >= expireAt) {
            EventManager.activeEvents.remove(this);
        }
        if (e.getMessage().equalsIgnoreCase(listenFor)) {
            eventWon(e.getPlayer(), e.getMessage());
            return true;
        }
        return false;
    }
    void eventWon(Player player, String guessed) {
        EventManager.activeEvents.remove(this);
        Bukkit.getScheduler().runTask(StaticPrisons.getInstance(), () -> {
            giveRewards(player, guessed);
        });
    }
    void giveRewards(Player player, String guessed) {}
}