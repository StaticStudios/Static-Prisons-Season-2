package net.staticstudios.prisons.pickaxe.enchants;

import net.staticstudios.prisons.pickaxe.enchants.handler.BaseEnchant;

import java.math.BigInteger;

public class DoubleJackHammerEnchant extends BaseEnchant {
    public DoubleJackHammerEnchant() {
        super("doubleJackhammer", "&6&lDouble Wammy", 1000, BigInteger.valueOf(7500), "&7Chance to destroy an additional mine layer", "Activates along side Jack Hammer");
        setPickaxeLevelRequirement(60);
    }
}
