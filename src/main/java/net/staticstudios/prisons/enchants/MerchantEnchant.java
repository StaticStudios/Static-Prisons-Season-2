package net.staticstudios.prisons.enchants;

import net.staticstudios.prisons.blockBroken.PrisonBlockBroken;
import net.staticstudios.prisons.enchants.handler.BaseEnchant;
import net.staticstudios.prisons.enchants.handler.PrisonPickaxe;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

import java.math.BigInteger;

public class MerchantEnchant extends BaseEnchant {
    public MerchantEnchant() {
        super("merchant", "&a&lMerchant", 5000, BigInteger.valueOf(500), "&7Permanent +400% sell multiplier at max level");
        setPickaxeLevelRequirement(30);
        setPlayerLevelRequirement(5);
    }
}
