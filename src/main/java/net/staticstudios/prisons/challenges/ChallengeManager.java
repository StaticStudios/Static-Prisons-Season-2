package net.staticstudios.prisons.challenges;

import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.challenges.commands.ChallengesCommand;
import net.staticstudios.prisons.commands.CommandManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class ChallengeManager {
    public static void init() {
        ChallengeType.init();
        loadFromFile();

        CommandManager.registerCommand("challenges", new ChallengesCommand());

        Bukkit.getScheduler().runTaskTimer(StaticPrisons.getInstance(), ChallengeManager::saveToFile, 20 * 60 * 3, 20 * 60 * 5);

        File file = new File(StaticPrisons.getInstance().getDataFolder(), "challenges.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        ChallengeTemplate.loadTemplates(Objects.requireNonNull(config.getConfigurationSection("challenges")));
        ChallengeReward.loadRewards(Objects.requireNonNull(config.getConfigurationSection("rewards")));
    }

    public static void loadFromFile() {
        File file = new File(StaticPrisons.getInstance().getDataFolder(), "data/challenges.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        for (String key : config.getKeys(false)) {
            UUID uuid = UUID.fromString(key);
            Map<String, List<Challenge>> challenges = new HashMap<>();
            for (String challengeDuration : Objects.requireNonNull(config.getConfigurationSection(key)).getKeys(false)) {
                ChallengeDuration duration = ChallengeDuration.valueOf(challengeDuration);
                for (String n : Objects.requireNonNull(config.getConfigurationSection(key + "." + challengeDuration)).getKeys(false)) {
                    String challengeID = config.getString(key + "." + challengeDuration + "." + n + ".type");
                    long goal = config.getLong(key + "." + challengeDuration + "." + n + ".goal");
                    long progress = config.getLong(key + "." + challengeDuration + "." + n + ".progress");
                    int tier = config.getInt(key + "." + challengeDuration + "." + n + ".tier");
                    long startTime = config.getLong(key + "." + challengeDuration + "." + n + ".startTime");
                    if (!challenges.containsKey(challengeID)) {
                        challenges.put(challengeID, new LinkedList<>());
                    }
                    Challenge challenge = new Challenge(ChallengeType.getChallengeType(challengeID), duration, goal, progress, tier, startTime);
                    if (!challenge.shouldExpire()) { //Make sure the challenge has not expired
                        challenges.get(challengeID).add(challenge);
                    }
                }
            }

            Challenge.PLAYER_CHALLENGES.put(uuid, challenges);
        }
    }

    /**
     * Save all challenge data to a file asynchronously
     */
    public static void saveToFile() {
        var data = new HashMap<>(Challenge.PLAYER_CHALLENGES);
        Bukkit.getScheduler().runTaskAsynchronously(StaticPrisons.getInstance(), () -> save(data));
    }

    /**
     * Save all challenge data to a file synchronously
     */
    public static void saveToFileNow() {
        save(Challenge.PLAYER_CHALLENGES);
    }

    private static void save(Map<UUID, Map<String, List<Challenge>>> data) {
        File file = new File(StaticPrisons.getInstance().getDataFolder(), "data/challenges.yml");
        FileConfiguration config = new YamlConfiguration();
        for (UUID uuid : data.keySet()) {
            int n = 0;
            Map<String, List<Challenge>> challenges = data.get(uuid);
            for (String challengeID : challenges.keySet()) {
                for (Challenge challenge : challenges.get(challengeID)) {
                    config.set(uuid + "." + challenge.getDuration() + "." + n + ".type", challenge.getChallengeType().ID);
                    config.set(uuid + "." + challenge.getDuration() + "." + n + ".goal", challenge.getGoal());
                    config.set(uuid + "." + challenge.getDuration() + "." + n + ".progress", challenge.getProgress());
                    config.set(uuid + "." + challenge.getDuration() + "." + n + ".tier", challenge.getTier());
                    config.set(uuid + "." + challenge.getDuration() + "." + n + ".startTime", challenge.getStartTime());
                    n++;
                }
            }
        }
        try {
            config.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
