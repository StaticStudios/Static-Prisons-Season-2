package net.staticstudios.prisons.pickaxe.enchants;

import net.staticstudios.prisons.blockBroken.BlockBreak;
import net.staticstudios.prisons.customItems.CustomItems;
import net.staticstudios.prisons.pickaxe.enchants.handler.BaseEnchant;
import net.staticstudios.prisons.pickaxe.enchants.handler.EnchantTier;
import net.staticstudios.prisons.utils.PrisonUtils;
import net.staticstudios.utils.WeightedElements;
import org.bukkit.inventory.ItemStack;

public class KeyFinderEnchant extends BaseEnchant {
    public KeyFinderEnchant() {
        super("keyFinder", "&d&lKey Finder", 5000, 400, "&7Find crate keys while mining");
        setPickaxeLevelRequirement(31);

        setTiers(
                new EnchantTier(500, 0),
                new EnchantTier(1000, 1),
                new EnchantTier(1500, 2),
                new EnchantTier(2000, 3),
                new EnchantTier(2500, 4),
                new EnchantTier(3000, 5),
                new EnchantTier(3500, 6),
                new EnchantTier(4000, 7),
                new EnchantTier(4500, 8),
                new EnchantTier(5000, 9)
        );

        setUseChances(true);
        setDefaultPercentChance(1d / 10000 * 100); //1 out of 10,000
        setPercentChancePerLevel((1d / 5000 * 100 - getDefaultPercentChance()) / MAX_LEVEL); //it will activate 1 out of 5,000 times at max level
    }

    public void onBlockBreak(BlockBreak blockBreak) {
        if (blockBreak.getPlayer() == null) return;
        if (!activate(blockBreak.getPickaxe())) return;

        ItemStack reward = new WeightedElements<ItemStack>()
                .add(CustomItems.getCommonCrateKey(2), 15)
                .add(CustomItems.getRareCrateKey(2), 30)
                .add(CustomItems.getEpicCrateKey(2), 45)
                .add(CustomItems.getLegendaryCrateKey(2), 5)
                .add(CustomItems.getStaticCrateKey(2), 4)
                .add(CustomItems.getStaticCrateKey(2), 1)
                .getRandom();
        blockBreak.messagePlayer(DISPLAY_NAME + " &8&l>> &fFound " + reward.getAmount() + "x " + PrisonUtils.Items.getPrettyItemName(reward) + "&f while mining!");
        PrisonUtils.Players.addToInventory(blockBreak.getPlayer(), reward);
    }
}
