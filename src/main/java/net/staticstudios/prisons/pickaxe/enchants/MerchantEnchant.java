package net.staticstudios.prisons.pickaxe.enchants;

import net.staticstudios.prisons.pickaxe.PrisonPickaxe;
import net.staticstudios.prisons.pickaxe.enchants.handler.BaseEnchant;
import net.staticstudios.prisons.pickaxe.enchants.handler.EnchantTier;
import net.staticstudios.prisons.pickaxe.enchants.handler.PickaxeEnchants;

public class MerchantEnchant extends BaseEnchant {
    public MerchantEnchant() {
        super("merchant", "&a&lMerchant", 6_000, 650, "&7Permanent +150% sell multiplier at max level");
        setPickaxeLevelRequirement(15);

        setTiers(
                new EnchantTier(3000, 0),
                new EnchantTier(6000, 25)
        );
    }

    public static double getMultiplier(PrisonPickaxe pickaxe) {
        if (!pickaxe.getIsEnchantEnabled(PickaxeEnchants.MERCHANT)) return 0;
        return (double) pickaxe.getEnchantLevel(PickaxeEnchants.MERCHANT) / PickaxeEnchants.MERCHANT.MAX_LEVEL * 1.5;
    }
}
