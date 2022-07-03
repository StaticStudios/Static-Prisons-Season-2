package net.staticstudios.prisons.crates;

import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public class CrateReward {
    public String rewardName;
    public ItemStack itemReward = null;
    public ItemStack icon;
    public Consumer<Player> runnableReward = null;

    public CrateReward(ItemStack itemReward) {
        this.itemReward = itemReward.clone();
        icon = itemReward.clone();
        rewardName = PrisonUtils.Items.getPrettyItemName(itemReward);
    }
    public CrateReward(ItemStack icon, Consumer<Player> runnableReward, String rewardName) {
        this.icon = icon.clone();
        this.runnableReward = runnableReward;
        this.rewardName = rewardName;
    }
}
