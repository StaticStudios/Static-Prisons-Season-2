package net.staticstudios.prisons.pickaxe.enchants;

import net.staticstudios.prisons.enchants.Enchantable;
import net.staticstudios.prisons.pickaxe.PrisonPickaxe;
import net.staticstudios.prisons.pickaxe.enchants.handler.PickaxeEnchant;

public class MerchantEnchant extends PickaxeEnchant {

    private static double MULTIPLIER = 1.5;

    public MerchantEnchant() {
        super(MerchantEnchant.class, "pickaxe-merchant");

        MULTIPLIER = getConfig().getDouble("multiplier", MULTIPLIER);
    }

    public static double getMultiplier(PrisonPickaxe pickaxe) {
        if (pickaxe.isDisabled(MerchantEnchant.class)) return 0;
        return (double) pickaxe.getEnchantmentLevel(MerchantEnchant.class) / ((PickaxeEnchant) Enchantable.getEnchant(MerchantEnchant.class)).getMaxLevel(pickaxe) * MULTIPLIER;
    }
}
