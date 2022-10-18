package net.staticstudios.prisons.crates;

import net.kyori.adventure.text.Component;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public class CrateReward {
    public Component rewardName;
    public ItemStack itemReward = null;
    public ItemStack icon;
    public Consumer<Player> runnableReward = null;

    public CrateReward(ItemStack itemReward) {
        this.itemReward = itemReward.clone();
        icon = itemReward.clone();
        rewardName = itemReward.getItemMeta().displayName();
    }
    public CrateReward(ItemStack icon, Consumer<Player> runnableReward, Component rewardName) {
        this.icon = icon.clone();
        this.runnableReward = runnableReward;
        this.rewardName = rewardName;
    }
}
