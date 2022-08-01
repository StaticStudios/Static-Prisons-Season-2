package net.staticstudios.prisons.pickaxe.enchants;

import net.staticstudios.prisons.pickaxe.enchants.handler.BaseEnchant;
import net.staticstudios.prisons.pickaxe.enchants.handler.EnchantTier;

import java.math.BigInteger;

public class DoubleJackHammerEnchant extends BaseEnchant {
    public DoubleJackHammerEnchant() {
        super("doubleJackhammer", "&6&lDouble Wammy", 1000, BigInteger.valueOf(7500), "&7Chance to destroy an additional mine layer", "Activates along side Jack Hammer");
        setPickaxeLevelRequirement(70);

        setTiers(
                new EnchantTier(25, 0),
                new EnchantTier(75, 1),
                new EnchantTier(150, 2),
                new EnchantTier(200, 3),
                new EnchantTier(300, 4),
                new EnchantTier(400, 5),
                new EnchantTier(500, 6),
                new EnchantTier(600, 7),
                new EnchantTier(750, 8),
                new EnchantTier(900, 9),
                new EnchantTier(1000, 10)
        );

        setUseChances(true);
        setDefaultPercentChance(0);
        setPercentChancePerLevel(1d / MAX_LEVEL * 100);
    }
}
