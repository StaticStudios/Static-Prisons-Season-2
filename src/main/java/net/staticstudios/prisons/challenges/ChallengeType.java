package net.staticstudios.prisons.challenges;

import net.kyori.adventure.text.Component;
import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.blockbreak.BlockBreakProcessEvent;
import net.staticstudios.prisons.challenges.challengetypes.*;
import net.staticstudios.prisons.levelup.prestige.PrestigeEvent;
import net.staticstudios.prisons.levelup.rankup.RankUpEvent;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerFishEvent;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

public abstract class ChallengeType<E extends Event> implements Listener {
//    USE_PICKAXE_ABILITY
//    USE_MINEBOMB
//    OPEN_CRATE,
//    WIN_ITEM_FROM_CRATE; -- rarity, eg >2.5% chance

    /**
     * Map of ID to challenge type
     */
    private static final Map<String, ChallengeType<?>> CHALLENGE_TYPES = new HashMap<>();


    public static ChallengeType<BlockBreakProcessEvent> MINING;
    public static ChallengeType<BlockBreakProcessEvent> RAW_MINING;
    public static ChallengeType<RankUpEvent> RANK_UP;
    public static ChallengeType<BlockPlaceEvent> BUILDING;
    public static ChallengeType<PlayerDeathEvent> PVP_KILLS;
    public static ChallengeType<PlayerDeathEvent> PVP_DEATHS;
    public static ChallengeType<PlayerFishEvent> FISHING;
    public static ChallengeType<PrestigeEvent> PRESTIGE;


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
     * A BiConsumer that is called when the event (specified by E) is called. This is where the challenge's progress should be updated.
     */
    private final BiConsumer<E, Challenge> onEvent;

    /**
     * @param id          The challenge type's identifier.
     * @param displayName The challenge type's display name.
     * @param description The challenge type's description.
     * @param icon        The challenge type's icon for use in a GUI.
     * @param onEvent     A BiConsumer that is called when the event (specified by E) is called. This is where the challenge's progress should be updated.
     */
    protected ChallengeType(String id, Component displayName, List<Component> description, Material icon, BiConsumer<E, Challenge> onEvent) {
        this.ID = id;
        this.DISPLAY_NAME = displayName;
        this.ICON = icon;
        this.DESCRIPTION = description;
        this.onEvent = onEvent;

        CHALLENGE_TYPES.put(id, this);

        StaticPrisons.getInstance().getServer().getPluginManager().registerEvents(this, StaticPrisons.getInstance());
    }

    public static void init() {
        MINING = new MiningChallenge();
        RAW_MINING = new RawMiningChallenge();
        RANK_UP = new RankUpChallenge();
        BUILDING = new BuildingChallenge();
        PVP_KILLS = new KillsChallenge();
        PVP_DEATHS = new DeathsChallenge();
        FISHING = new FishingChallenge();
        PRESTIGE = new PrestigeChallenge();
    }

    /**
     * Get the challenge type with the specified ID.
     *
     * @param id The ID of the challenge type.
     * @return The challenge type with the specified ID or null if no challenge type with the specified ID exists.
     */
    public static ChallengeType<?> getChallengeType(String id) {
        return CHALLENGE_TYPES.get(id);
    }

    /**
     * This method should be called by each implementation of this class when the event (specified by E) is called.
     * When implementing this class, you should listen for the desired event (specified by E) and call this method when that event is fired.
     * <p>
     * This method will call the onEvent BiConsumer and will perform a check to see if the challenge has been completed; the player will be rewarded if so.
     *
     * @param e The event that was fired.
     * @param player The player that the event was fired for.
     */
    protected void onEvent(E e, Player player) {
        List<Challenge> toRemove = new LinkedList<>();
        Challenge.getChallenges(ID, player).forEach(challenge -> {
            if (challenge.shouldExpire()) {
                toRemove.add(challenge);
                return;
            }

            onEvent.accept(e, challenge);
            if (challenge.getProgress() >= challenge.getGoal()) { //The challenge is complete and the player is being rewarded
                List<ChallengeReward> possibleRewards = Challenge.CHALLENGE_REWARDS.get(challenge.getTier());
                possibleRewards.get(PrisonUtils.randomInt(0, possibleRewards.size() - 1)).giveReward(player);
                toRemove.add(challenge);
            }
        });
        Challenge.getChallenges(ID, player).removeAll(toRemove);
    }


}
