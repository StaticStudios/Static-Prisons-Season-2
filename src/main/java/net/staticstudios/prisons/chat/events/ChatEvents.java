package net.staticstudios.prisons.chat.events;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.customItems.CustomItems;
import net.staticstudios.prisons.utils.PrisonUtils;
import net.staticstudios.utils.WeightedElements;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.time.Instant;
import java.util.*;
import java.util.function.BiConsumer;

public class ChatEvents {

    public enum EventType {
        MATH,
        WORD_UNSCRAMBLE,
        TRIVIA
    }

    public static String PREFIX = ChatColor.translateAlternateColorCodes('&', "&6&lChat Events &8&l>> &r");

    static List<ChatEvent> activeEvents = new ArrayList<>();
    public static List<String> WORLD_LIST = new ArrayList<>();
    public static List<String[]> MATH_LIST = new ArrayList<>();

    public static void init() {
        FileConfiguration config = YamlConfiguration.loadConfiguration(new File(StaticPrisons.getInstance().getDataFolder(), "chat-events/config.yml"));
        WORLD_LIST.addAll(config.getStringList("words"));
        config.addDefault("math", new YamlConfiguration());
        for (String question : config.getConfigurationSection("math").getKeys(false)) MATH_LIST.add(new String[] {question.replace('#', '.'), config.getString("math." + question)});
        StaticPrisons.getInstance().getServer().getPluginManager().registerEvents(new Listener(), StaticPrisons.getInstance());

        //Timer to run the events
        Bukkit.getScheduler().runTaskTimer(StaticPrisons.getInstance(), () -> new WeightedElements<Runnable>()
                .add(() -> runEvent(EventType.MATH), 1)
                .add(() -> runEvent(EventType.WORD_UNSCRAMBLE), 2)
                .getRandom().run(), 20 * 60 * 13, 20 * (60 * 12 + 45)); //randomish time so that the events don't all happen at the same time
    }

    public static void gotAnswer(String answer, Player player) {
        List<ChatEvent> expire = new ArrayList<>();
        for (ChatEvent event : new ArrayList<>(activeEvents)) {
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
                int i = PrisonUtils.randomInt(0, MATH_LIST.size() - 1);
                String question = MATH_LIST.get(i)[0];
                String answer = MATH_LIST.get(i)[1];

                new ChatEvent((player, event) -> {
                    ItemStack reward = new WeightedElements<ItemStack>()
                            .add(CustomItems.getCommonCrateKey(4), 10)
                            .add(CustomItems.getRareCrateKey(3), 20)
                            .add(CustomItems.getLegendaryCrateKey(2), 10)
                            .add(CustomItems.getLegendaryCrateKey(1), 10)
                            .add(CustomItems.getStaticCrateKey(1), 10)
                            .getRandom();
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', PREFIX + player.getName() + " solved &b" + event.question + "!&f Correct answer: &a" + event.correctAnswer + "!&f " +
                                player.getName() + " won " + reward.getAmount() + "x " + PrisonUtils.Items.getPrettyItemName(reward) + "!"));
                    }
                    PrisonUtils.Players.addToInventory(player, reward);
                }, question, answer);
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.sendMessage(Component.text(ChatColor.translateAlternateColorCodes('&', PREFIX + "The first person to solve &b" + question + "&f will receive a reward!")).hoverEvent(Component.text(ChatColor.RED + "Question: " + ChatColor.WHITE + question)));
                }
            }
            case WORD_UNSCRAMBLE -> {
                String answer = WORLD_LIST.get(PrisonUtils.randomInt(0, WORLD_LIST.size() - 1));
                StringBuilder question = new StringBuilder();

                List<Character> chars = new ArrayList<>(); //randomize the order of the characters in the answer
                for (char c : answer.toCharArray()) chars.add(c);
                while (chars.size() > 0) {
                    int i = PrisonUtils.randomInt(0, chars.size() - 1);
                    question.append(chars.get(i));
                    chars.remove(i);
                }
                new ChatEvent((player, event) -> {
                    ItemStack reward = new WeightedElements<ItemStack>()
                            .add(CustomItems.getCommonCrateKey(4), 10)
                            .add(CustomItems.getRareCrateKey(3), 20)
                            .add(CustomItems.getLegendaryCrateKey(2), 10)
                            .add(CustomItems.getLegendaryCrateKey(1), 10)
                            .add(CustomItems.getStaticCrateKey(1), 10)
                            .getRandom();

                    PrisonUtils.Players.addToInventory(player, reward);
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', PREFIX + player.getName() + " correctly unscrambled &b" + event.question + "!&f Correct answer: &a" + event.correctAnswer + "!&f " +
                        player.getName() + " won " + reward.getAmount() + "x " + PrisonUtils.Items.getPrettyItemName(reward) + "!"));
                    }
                    PrisonUtils.Players.addToInventory(player, reward);
                }, question.toString(), answer);
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', PREFIX + "The first person to correctly unscramble &b" + question + "&f will receive a reward!"));
                }
            }
        }
    }

    static class ChatEvent {
        String correctAnswer;
        String question;
        BiConsumer<Player, ChatEvent> rewards;

        public ChatEvent(BiConsumer<Player, ChatEvent> rewards, String question, String correctAnswer) {
            this.rewards = rewards;
            activeEvents.add(this);
            this.question = question;
            this.correctAnswer = correctAnswer;
        }
        void onAnswer(Player player) {
            ChatEvents.activeEvents.remove(this);
            rewards.accept(player, this);
        }
        public long expireAt = Instant.now().getEpochSecond() + 120;
    }

    static class Listener implements org.bukkit.event.Listener {
        @EventHandler
        public void onChat(AsyncChatEvent e) {
            e.viewers().remove(Bukkit.getConsoleSender());
            String msg = PlainTextComponentSerializer.plainText().serialize(e.message());
            Bukkit.getScheduler().runTask(StaticPrisons.getInstance(), () -> gotAnswer(msg, e.getPlayer()));
        }
    }
}

