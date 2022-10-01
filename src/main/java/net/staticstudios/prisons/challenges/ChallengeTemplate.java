package net.staticstudios.prisons.challenges;

import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;

public class ChallengeTemplate {
    public final ChallengeType<?> TYPE;
    public final ChallengeDuration DURATION;
    public final long GOAL;
    public final int TIER;

    ChallengeTemplate(ChallengeType<?> type, ChallengeDuration duration, long goal, int tier) {
        this.TYPE = type;
        this.DURATION = duration;
        this.GOAL = goal;
        this.TIER = tier;
    }

    public static void loadTemplates(ConfigurationSection config) {
        Challenge.CHALLENGE_TEMPLATES.clear();

        for (String key : config.getKeys(false)) {
            ChallengeDuration duration;
            try {
                duration = ChallengeDuration.valueOf(key.toUpperCase());
            } catch (IllegalArgumentException e) {
                continue;
            }
            ConfigurationSection typeConfig = config.getConfigurationSection(key);
            assert typeConfig != null;
            for (String tierKey : typeConfig.getKeys(false)) {
                ConfigurationSection tierConfig = typeConfig.getConfigurationSection(tierKey);
                int tier;
                try {
                    tier = Integer.parseInt(tierKey.replace("tier_", ""));
                } catch (NumberFormatException e) {
                    continue;
                }
                assert tierConfig != null;
                for (String challengeTypeKey : tierConfig.getKeys(false)) {
                    ChallengeType<?> type = ChallengeType.getChallengeType(challengeTypeKey);
                    if (type == null) continue;
                    tierConfig.getIntegerList(challengeTypeKey).forEach(goal -> {
                        List<ChallengeTemplate> templates = Challenge.CHALLENGE_TEMPLATES.get(duration);
                        ChallengeTemplate template = new ChallengeTemplate(type, duration, goal, tier);
                        if (templates == null) {
                            templates = new ArrayList<>();
                            templates.add(template);
                            Challenge.CHALLENGE_TEMPLATES.put(duration, templates);
                        } else templates.add(template);
                    });
                }
            }
        }
    }

    /**
     * Convert this template into a challenge
     *
     * @return A challenge which uses this template's properties
     */
    public Challenge toChallenge() {
        return new Challenge(TYPE, DURATION, GOAL, 0, TIER, System.currentTimeMillis());
    }
}
