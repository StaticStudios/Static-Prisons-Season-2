package net.staticstudios.prisons.challenges;

import net.kyori.adventure.text.Component;
import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.blockbreak.BlockBreakProcessEvent;
import net.staticstudios.prisons.challenges.types.MiningChallenge;
import net.staticstudios.prisons.challenges.types.RawMiningChallenge;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEvent;

import java.util.*;
import java.util.function.BiConsumer;

public abstract class ChallengeType<E extends PlayerEvent> implements Listener {
//    MINING,
//    FISHING,
//    PVP_KILLS,
//    PVP_DEATHS,
//    BUILDING,
//    RANK_UP,
//    PRESTIGE,
//    OPEN_CRATES,
//    WIN_ITEM_FROM_CRATE;

    private static final Map<String, ChallengeType<?>> CHALLENGES = new HashMap<>();
    public static ChallengeType<BlockBreakProcessEvent> MINING;
    public static ChallengeType<BlockBreakProcessEvent> RAW_MINING;
    /**
     * The challenge type's identifier.
     */
    public final String ID;
    /**
     * The challenge type's display name.
     */
    public final Component DISPLAY_NAME;
    /**
     * The challenge type's icon for use in a GUI.
     */
    public final Material ICON;
    /**
     * The challenge type's description.
     */
    public final List<Component> DESCRIPTION;
    /**
     * The predicate to check if the challenge has made progress
     */
    private final BiConsumer<E, Challenge> onEvent;

    /**
     * @param onEvent The predicate to check if the challenge has made progress
     */
    protected ChallengeType(String id, Component prettyName, List<Component> description, Material icon, BiConsumer<E, Challenge> onEvent) {
        this.onEvent = onEvent;
        this.ID = id;
        this.DISPLAY_NAME = prettyName;
        this.DESCRIPTION = description;
        this.ICON = icon;

        CHALLENGES.put(id, this);

        StaticPrisons.getInstance().getServer().getPluginManager().registerEvents(this, StaticPrisons.getInstance());
    }

    public static void init() {
        MINING = new MiningChallenge();
        RAW_MINING = new RawMiningChallenge();
    }

    public static ChallengeType<?> getChallengeType(String id) {
        return CHALLENGES.get(id);
    }



    protected void onEvent(E e) {
        List<Challenge> toRemove = new LinkedList<>();
        Challenge.getChallenges(ID, e.getPlayer()).forEach(challenge -> {
            if (challenge.shouldExpire()) {
                toRemove.add(challenge);
            } else {
                onEvent.accept(e, challenge);
                if (challenge.getProgress() >= challenge.getGoal()) {
                    List<ChallengeReward> possibleRewards = Challenge.CHALLENGE_REWARDS.get(challenge.getTier());
                    System.out.println(possibleRewards);
                    possibleRewards.get(PrisonUtils.randomInt(0, possibleRewards.size() - 1)).giveReward(e.getPlayer());
                    toRemove.add(challenge);
                }
            }
        });
        Challenge.getChallenges(ID, e.getPlayer()).removeAll(toRemove);
    }


}
