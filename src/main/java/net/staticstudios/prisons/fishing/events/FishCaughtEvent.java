package net.staticstudios.prisons.fishing.events;

import net.staticstudios.prisons.fishing.CaughtType;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;

public class FishCaughtEvent {

    private final PlayerFishEvent event;

    private CaughtType type = CaughtType.NOTHING;
    private long playerXp = 0;
    private double playerXpMultiplier = 1.0;
    private long tokens = 0;
    private double tokensMultiplier = 1.0;
    private long shards = 0;
    private double shardsMultiplier = 1.0;
    private ItemStack itemCaught = null;
    private ItemStack displayItem = null;


    private double durabilityLost = 1.0;

    public FishCaughtEvent(PlayerFishEvent e) {
        this.event = e;
    }

    public PlayerFishEvent getPlayerFishEvent() {
        return event;
    }

    public CaughtType getType() {
        return type;
    }

    public void setType(CaughtType type) {
        this.type = type;
    }

    public long getPlayerXp() {
        return playerXp;
    }

    public void setPlayerXp(long playerXp) {
        this.playerXp = playerXp;
    }

    public double getPlayerXpMultiplier() {
        return playerXpMultiplier;
    }

    public void setPlayerXpMultiplier(double playerXpMultiplier) {
        this.playerXpMultiplier = playerXpMultiplier;
    }

    public long getTokens() {
        return tokens;
    }

    public void setTokens(long tokens) {
        this.tokens = tokens;
    }

    public double getTokensMultiplier() {
        return tokensMultiplier;
    }

    public void setTokensMultiplier(double tokensMultiplier) {
        this.tokensMultiplier = tokensMultiplier;
    }

    public long getShards() {
        return shards;
    }

    public void setShards(long shards) {
        this.shards = shards;
    }

    public double getShardsMultiplier() {
        return shardsMultiplier;
    }

    public void setShardsMultiplier(double shardsMultiplier) {
        this.shardsMultiplier = shardsMultiplier;
    }

    public ItemStack getItemCaught() {
        return itemCaught;
    }

    public void setItemCaught(ItemStack itemCaught) {
        this.itemCaught = itemCaught;
    }

    public ItemStack getDisplayItem() {
        return displayItem;
    }

    public void setDisplayItem(ItemStack displayItem) {
        this.displayItem = displayItem;
    }

    public double getDurabilityLost() {
        return durabilityLost;
    }

    public void setDurabilityLost(double durabilityLost) {
        this.durabilityLost = durabilityLost;
    }


}
