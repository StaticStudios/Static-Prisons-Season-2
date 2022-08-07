package net.staticstudios.prisons.pickaxe.enchants;

import net.staticstudios.prisons.pickaxe.enchants.handler.BaseEnchant;

public class MerchantEnchant extends BaseEnchant {
    public MerchantEnchant() {
        super("merchant", "&a&lMerchant", 5000, 500, "&7Permanent +400% sell multiplier at max level");
        setPickaxeLevelRequirement(25);

        setPickaxeLevelRequirement(999); //TODO: TEMP
    }
}
