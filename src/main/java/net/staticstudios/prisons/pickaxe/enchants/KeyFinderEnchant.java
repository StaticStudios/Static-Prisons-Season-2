package net.staticstudios.prisons.pickaxe.enchants;

import net.staticstudios.prisons.blockBroken.BlockBreak;
import net.staticstudios.prisons.customItems.CustomItems;
import net.staticstudios.prisons.pickaxe.enchants.handler.BaseEnchant;
import net.staticstudios.prisons.utils.PrisonUtils;
import net.staticstudios.utils.WeightedElements;
import org.bukkit.inventory.ItemStack;

import java.math.BigInteger;

public class KeyFinderEnchant extends BaseEnchant {
    public KeyFinderEnchant() {
        super("keyFinder", "&d&lKey Finder", 5000, BigInteger.valueOf(400), "&7Find crate keys while mining");
        setPickaxeLevelRequirement(25);
    }

    public void onBlockBreak(BlockBreak blockBreak) {
        if (blockBreak.getPlayer() == null) return;
        if (PrisonUtils.randomInt(0, 2500) != 1) return; //Chance to activate enchant
        int keyFinderLevel = blockBreak.getPickaxe().getEnchantLevel(ENCHANT_ID);
        if (PrisonUtils.randomInt(1, MAX_LEVEL + MAX_LEVEL / 10) <= keyFinderLevel + MAX_LEVEL / 10) {
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
}
