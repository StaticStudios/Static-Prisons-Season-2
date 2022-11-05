package net.staticstudios.prisons.fishing.events;

import net.staticstudios.mines.utils.WeightedElements;
import net.staticstudios.prisons.fishing.CaughtType;
import net.staticstudios.prisons.fishing.FishingReward;
import net.staticstudios.prisons.fishing.PrisonFishingRod;
import org.bukkit.entity.Item;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class FishCaughtEvent extends PlayerEvent {
    private static final HandlerList handlers = new HandlerList();
    private final PlayerFishEvent event;
    private final PrisonFishingRod fishingRod;
    private final WeightedElements<FishingReward> lootTable = new WeightedElements<>();
    private FishingReward reward = null;
    private ItemStack displayItem = null;
    private double durabilityLost = 1.0;

    public FishCaughtEvent(PlayerFishEvent e, PrisonFishingRod fishingRod) {
        super(e.getPlayer());
        this.event = e;
        this.fishingRod = fishingRod;
    }
    /*
    The loot table should be created from all the enchantments,
    after the loot table is created, a random reward should be pulled as the actual reward.
     */

    @SuppressWarnings("unused")
    public static HandlerList getHandlerList() {
        return handlers;
    }

    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    public PlayerFishEvent getPlayerFishEvent() {
        return event;
    }

    public PrisonFishingRod getFishingRod() {
        return fishingRod;
    }

    public WeightedElements<FishingReward> getLootTable() {
        return lootTable;
    }

    /**
     * This method will return the reward that the player will receive.
     * If a reward has not yet been chosen from the loot table, this method will generate one.
     * It will also set the item on the fishing rod accordingly.
     *
     * @return The reward that the player will receive.
     */
    public FishingReward getReward() {
        if (reward == null) {
            double weight = 100 - lootTable.getTotalWeight();
            if (weight < 100) {
                lootTable.add(new FishingReward(CaughtType.NOTHING, 0, 0, 0, null), weight);
            }
            reward = lootTable.getRandom();

            displayItem = new ItemStack(reward.type().getDisplayItem());

            if (reward.type() == CaughtType.ITEM) {
                displayItem = reward.item();
            }

            Item item = (Item) Objects.requireNonNull(event.getCaught());
            item.setItemStack(displayItem);
            item.setPickupDelay(Integer.MAX_VALUE);
            item.setWillAge(true);
            item.setTicksLived(600 - 3 * 20);

        }
        return reward;
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
