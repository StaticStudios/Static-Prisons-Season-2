package net.staticstudios.prisons.crates;

import net.kyori.adventure.text.Component;
import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.customitems.handler.CustomItems;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public class CrateReward {
    private Component rewardName;
    private ItemStack icon;


    private String rewardItemID;
    private String[] rewardArgs;
    private int rewardItemAmount = 1;

    public CrateReward(String rewardItemID, String[] args, ItemStack displayItem) {
        this.rewardItemID = rewardItemID;
        this.rewardArgs = args;
        this.icon = displayItem;
        if (displayItem == null) {
            StaticPrisons.log("Display item for reward " + rewardItemID + " is null!");
        }
    }
    public CrateReward setRewardItemAmount(int rewardItemAmount) {
        this.rewardItemAmount = rewardItemAmount;
        this.icon.setAmount(rewardItemAmount);
        return this;
    }

    public Component getRewardName() {
        return rewardName;
    }

    public ItemStack getItemReward(Player player) {
        return CustomItems.getItem(rewardItemID, player, rewardArgs);
    }

    public ItemStack getIcon() {
        return icon;
    }
}
