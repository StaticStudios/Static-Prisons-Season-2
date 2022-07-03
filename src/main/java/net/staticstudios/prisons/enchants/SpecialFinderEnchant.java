package net.staticstudios.prisons.enchants;

import net.staticstudios.prisons.blockBroken.PrisonBlockBroken;
import net.staticstudios.prisons.customItems.CustomItems;
import net.staticstudios.prisons.customItems.Vouchers;
import net.staticstudios.prisons.enchants.handler.BaseEnchant;
import net.staticstudios.prisons.utils.PrisonUtils;
import net.md_5.bungee.api.ChatColor;
import net.staticstudios.utils.WeightedElements;
import org.bukkit.inventory.ItemStack;

import java.math.BigDecimal;
import java.math.BigInteger;

public class SpecialFinderEnchant extends BaseEnchant {
    public SpecialFinderEnchant() {
        super("specialFinder", "&c&lMetal Detector", 5000, BigInteger.valueOf(400), "&7Find special items while mining");
    }

    public void onBlockBreak(PrisonBlockBroken bb) {
        if (PrisonUtils.randomInt(0, 1750) != 1) return;
        int metalDetectorEnchant = bb.pickaxe.getEnchantLevel(ENCHANT_ID);
        if (PrisonUtils.randomInt(1, MAX_LEVEL + MAX_LEVEL / 10) <= metalDetectorEnchant + MAX_LEVEL / 10) {
            ItemStack reward = new WeightedElements<ItemStack>()
                    .add(Vouchers.getMultiplierNote(BigDecimal.valueOf(PrisonUtils.randomInt(12, 75)).divide(BigDecimal.valueOf(100)), PrisonUtils.randomInt(20, 120)), 50)
                    .add(CustomItems.getMineBombTier1(), 25)
                    .add(CustomItems.getMineBombTier2(), 15)
                    .add(CustomItems.getMineBombTier3(), 5)
                    .add(CustomItems.getMineBombTier4(), 5)
                    .getRandom();
            //bb.player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "[Metal Detector] " + ChatColor.AQUA + "You found " + reward.getAmount() + "x " + PrisonUtils.getPrettyItemName(reward) + ChatColor.AQUA + " while mining!");
            bb.player.sendMessage(ChatColor.translateAlternateColorCodes('&', DISPLAY_NAME + " &8&l>> &fFound " + reward.getAmount() + "x " + PrisonUtils.Items.getPrettyItemName(reward) + "&f while mining!"));
            PrisonUtils.Players.addToInventory(bb.player, reward);
        }
    }
}
