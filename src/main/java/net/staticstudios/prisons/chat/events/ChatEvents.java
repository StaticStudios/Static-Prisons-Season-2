package net.staticstudios.prisons.chat.events;

import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.customItems.CustomItems;
import net.staticstudios.prisons.utils.PrisonUtils;
import net.staticstudios.utils.WeightedElements;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.time.Instant;
import java.util.*;
import java.util.function.Consumer;

public class ChatEvents { //todo: rewite this class to use random math events, and clean it up

    public enum EventType {
        MATH,
        WORD_UNSCRAMBLE,
        TRIVIA
    }

    static List<ChatEvent> activeEvents = new ArrayList<>(); //todo this class is still broken
    public static List<String> wordUnscrambleWords = new ArrayList<>();

    public static void init() {
        //todo get words from config file and put them in the list
        FileConfiguration config = YamlConfiguration.loadConfiguration(new File(StaticPrisons.getInstance().getDataFolder(), "chat-events/config.yml"));
        wordUnscrambleWords.addAll(config.getStringList("words"));
        //todo: math
        //todo: trivia
    }

    //Question, answer
    public static final String[][] mathQuestions = new String[][]{ //TODO: randomize
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
//"cat",
//        "minecraft",
//        "grass",
//        "stone",
//        "cobblestone",
//        "hippo",
//        "water",
//        "house",
//        "vacuum",
//        "yellow",
//        "static",
//        "staticstudios",
//        "lion",
//        "monitor",
//        "computer",
//        "spiderman",
//        "keyboard",
//        "school",
//        "lemon",
//        "coffee",
//        "discord",
//        "nitro",
//        "airplane",
//        "submarine",
//        "military",
//        "vote",
//        "prisons",
//        "pinecone",
//        "weewoo",
//        "prestige",
//        "rankup",
//        "cell",
//        "money",
//        "tokens",
//        "pickaxe",
//        "picture",
//        "word"
    public static void gotAnswer(String answer, Player player) {
        List<ChatEvent> expire = new ArrayList<>();
        for (ChatEvent event : activeEvents) {
            if (Instant.now().getEpochSecond() > event.expireAt) {
                expire.add(event);
                continue;
            }
            if (event.correctAnswer.equalsIgnoreCase(answer)) { //Check if it is correct
                event.onAnswer(player);
                expire.add(event);
            }
        }
        activeEvents.removeAll(expire);
    }

    public static void runEvent(EventType eventType) {
        switch (eventType) {
            case MATH -> {

            }
            case WORD_UNSCRAMBLE -> {
                new ChatEvent((player) -> {

                    ItemStack reward = new WeightedElements<ItemStack>()
                            .add(CustomItems.getCommonCrateKey(4), 10)
                            .add(CustomItems.getRareCrateKey(3), 20)
                            .add(CustomItems.getLegendaryCrateKey(2), 10)
                            .add(CustomItems.getLegendaryCrateKey(1), 10)
                            .add(CustomItems.getStaticCrateKey(1), 10)
                            .getRandom();

                    PrisonUtils.Players.addToInventory(player, reward);
                    for (Player p : Bukkit.getOnlinePlayers()) { //TODO use a predefined prefix
//                        p.sendMessage(ChatColor.AQUA + player.getName() + ChatColor.WHITE + " has won a chat event! They won " + reward.getAmount() + "x " + PrisonUtils.getPrettyItemName(reward) + "!" + ChatColor.WHITE + " They guessed: " + ChatColor.GREEN + guessed);
                    }

                });
            }
        }
    }



    public static void chatMessageReceived(AsyncPlayerChatEvent e) {
        Bukkit.getScheduler().runTask(StaticPrisons.getInstance(), () -> {
            if (activeEvents.size() == 0) return;
            for (ChatEvent chatEvent : activeEvents) {
                if (chatEvent == null) continue;
//                if (chatEvent.onGuess(e)) return;
            }
        });
    }
    public static void runNewEvent() {
        switch (PrisonUtils.randomInt(0, 1)) {
            case 0 -> runWordUnscramble();
//            case 1 -> runMath();
        }

    }
    public static void runWordUnscramble() {
        ChatEvent event = new ChatEvent(null) {
            @Override
            void giveRewards(Player player, String guessed) {
                ItemStack reward = new WeightedElements<ItemStack>()
                        .add(CustomItems.getCommonCrateKey(4), 10)
                        .add(CustomItems.getRareCrateKey(3), 20)
                        .add(CustomItems.getLegendaryCrateKey(2), 10)
                        .add(CustomItems.getLegendaryCrateKey(1), 10)
                        .add(CustomItems.getStaticCrateKey(1), 10)
                        .getRandom();
                PrisonUtils.Players.addToInventory(player, reward);
                for (Player p : Bukkit.getOnlinePlayers()) p.sendMessage(ChatColor.AQUA + player.getName() + ChatColor.WHITE + " has won a chat event! They won " + reward.getAmount() + "x " + PrisonUtils.getPrettyItemName(reward) + "!" + ChatColor.WHITE + " They guessed: " + ChatColor.GREEN + guessed);
            }
        };
        StringBuilder scrambledVersionOfTheWord = new StringBuilder();
//        String currentWord = wordUnscrambleWords[PrisonUtils.randomInt(0, wordUnscrambleWords.length - 1)];
        List<Character> chars = new ArrayList<>();
//        for (char c : currentWord.toCharArray()) {
//            chars.add(c);
//        }
        while (chars.size() > 0) {
            int i = PrisonUtils.randomInt(0, chars.size() - 1);
            scrambledVersionOfTheWord.append(chars.get(i));
            chars.remove(i);
        }
//        event.correctAnswer = currentWord;
        activeEvents.add(event);
        Bukkit.broadcastMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "Word Un-Scramble " + ChatColor.DARK_GRAY + "> " + ChatColor.RESET + ChatColor.LIGHT_PURPLE + "EVENT STARTED: " + ChatColor.AQUA + "the first player to currently unscramble this word will get a reward! " + ChatColor.GREEN + "" + ChatColor.BOLD + scrambledVersionOfTheWord);
    }
    public static void runTrivia() {

    }
//    public static void runMath() {
//        ChatEvent event = new ChatEvent() {
//            @Override
//            void giveRewards(Player player, String guessed) {
//                ItemStack reward = new WeightedElements<ItemStack>()
//                        .add(CustomItems.getCommonCrateKey(4), 10)
//                        .add(CustomItems.getRareCrateKey(3), 20)
//                        .add(CustomItems.getLegendaryCrateKey(2), 10)
//                        .add(CustomItems.getLegendaryCrateKey(1), 10)
//                        .add(CustomItems.getStaticCrateKey(1), 10)
//                        .getRandom();
//                PrisonUtils.Players.addToInventory(player, reward);
//                for (Player p : Bukkit.getOnlinePlayers()) {
//                    p.sendMessage(ChatColor.AQUA + player.getName() + ChatColor.WHITE + " has won a chat event! They won " + reward.getAmount() + "x " + PrisonUtils.getPrettyItemName(reward) + "!" + ChatColor.WHITE + " They guessed: " + ChatColor.GREEN + guessed);
//                }
//            }
//        };
//        int eq = PrisonUtils.randomInt(0, mathQuestions.length - 1);
//        String equation = mathQuestions[eq][0];
//        event.correctAnswer = mathQuestions[eq][1];
//        activeEvents.add(event);
//        Bukkit.broadcastMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "Math Equation " + ChatColor.DARK_GRAY + "> " + ChatColor.RESET + ChatColor.LIGHT_PURPLE + "EVENT STARTED: " + ChatColor.AQUA + "the first player to currently answer the following math equation will win a reward! " + ChatColor.GREEN + equation);
//    }

    static class ChatEvent {
        String correctAnswer = "";
        public long expireAt = Instant.now().getEpochSecond() + 120;
        Consumer<Player> rewards;

        public ChatEvent(Consumer<Player> rewards) {
            this.rewards = rewards;
            activeEvents.add(this);
        }


        void onAnswer(Player player) {
            ChatEvents.activeEvents.remove(this);
            rewards.accept(player);
        }
        void giveRewards(Player player, String guessed) {}
    }

}

