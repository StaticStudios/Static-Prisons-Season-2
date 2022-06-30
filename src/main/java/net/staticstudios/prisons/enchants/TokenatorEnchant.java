package net.staticstudios.prisons.enchants;

import net.staticstudios.prisons.blockBroken.PrisonBlockBroken;
import net.staticstudios.prisons.enchants.handler.BaseEnchant;
import net.staticstudios.prisons.enchants.handler.PrisonPickaxe;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

import java.math.BigInteger;

public class TokenatorEnchant extends BaseEnchant {
    public TokenatorEnchant() {
        super("tokenator", "&6&lTokenator", 5000, BigInteger.valueOf(450), "&7Increases the chance to find tokens while mining");
    }

    public void onBlockBreak(PrisonBlockBroken bb) {
        int chance = (PrisonUtils.randomInt(1, 350 - bb.pickaxe.getEnchantLevel(ENCHANT_ID) / 25)); //Max level requires 150 blocks on average //todo: ensure that this is balanced
        if (chance == 1) bb.totalTokensGained += PrisonUtils.randomInt(200, 800);
    }
}
