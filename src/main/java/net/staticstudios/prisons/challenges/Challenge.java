package net.staticstudios.prisons.challenges;

import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.entity.Player;

import java.util.*;

public class Challenge {

    public static final Map<ChallengeDuration, List<ChallengeTemplate>> CHALLENGE_TEMPLATES = new HashMap<>();
    public static final Map<Integer, List<ChallengeReward>> CHALLENGE_REWARDS = new HashMap<>();
    public static final Map<UUID, Map<String, List<Challenge>>> PLAYER_CHALLENGES = new HashMap<>();

    private final ChallengeType<?> challengeType;
    private final ChallengeDuration duration;
    private final long startTime;
    private final int tier;
    private final long goal;
    private long progress;

    public Challenge(ChallengeType<?> challengeType, ChallengeDuration duration, long goal, long progress, int tier, long startTime) {
        this.challengeType = challengeType;
        this.duration = duration;
        this.goal = goal;
        this.progress = progress;
        this.tier = tier;
        this.startTime = startTime;
    }

    public static List<Challenge> getChallenges(String challengeID, Player player) {
        return PLAYER_CHALLENGES.getOrDefault(player.getUniqueId(), Collections.emptyMap()).getOrDefault(challengeID, Collections.emptyList());
    }

    public static List<Challenge> getChallenges(Player player) {
        Map<String, List<Challenge>> map = PLAYER_CHALLENGES.getOrDefault(player.getUniqueId(), Collections.emptyMap());
        List<Challenge> list = new ArrayList<>();
        for (List<Challenge> challenges : map.values()) {
            list.addAll(challenges);
        }
        return list;
    }

    public static void removePlayerChallenge(Player player, Challenge challenge) {
        PLAYER_CHALLENGES.getOrDefault(player.getUniqueId(), Collections.emptyMap()).forEach((s, challenges) -> challenges.remove(challenge));
    }

    public static void createPlayerChallenge(Player player, Challenge challenge) {
        if (!PLAYER_CHALLENGES.containsKey(player.getUniqueId())) {
            PLAYER_CHALLENGES.put(player.getUniqueId(), new HashMap<>());
        }
        if (!PLAYER_CHALLENGES.get(player.getUniqueId()).containsKey(challenge.getChallengeType().ID)) {
            PLAYER_CHALLENGES.get(player.getUniqueId()).put(challenge.getChallengeType().ID, new LinkedList<>());
        }
        PLAYER_CHALLENGES.get(player.getUniqueId()).get(challenge.getChallengeType().ID).add(challenge);
    }

    public static void expirePlayerChallenges(Player player) {
        List<Challenge> toRemove = new LinkedList<>();
        for (Challenge challenge : getChallenges(player)) {
            if (challenge.shouldExpire()) {
                toRemove.add(challenge);
            }
        }
        toRemove.forEach(challenge -> removePlayerChallenge(player, challenge));
    }

    public static Challenge createRandomChallenge(ChallengeDuration duration) {
        List<ChallengeTemplate> templates = CHALLENGE_TEMPLATES.get(duration);
        if (templates == null) {
            return null;
        }
        return templates.get(PrisonUtils.randomInt(0, templates.size() - 1)).toChallenge();
    }

    public boolean shouldExpire() {
        return System.currentTimeMillis() - startTime > duration.durationInMillis();
    }

    public ChallengeType<?> getChallengeType() {
        return challengeType;
    }

    public long getGoal() {
        return goal;
    }

    public long getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public void addProgress(long progress) {
        this.progress += progress;
    }

    public int getTier() {
        return tier;
    }

    public ChallengeDuration getDuration() {
        return duration;
    }

    public long getStartTime() {
        return startTime;
    }
}
