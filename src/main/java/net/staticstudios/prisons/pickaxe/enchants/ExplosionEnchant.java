package net.staticstudios.prisons.pickaxe.enchants;

import net.staticstudios.prisons.blockBroken.BlockBreak;
import net.staticstudios.prisons.mineBombs.MineBomb;
import net.staticstudios.prisons.mines.MineBlock;
import net.staticstudios.prisons.pickaxe.enchants.handler.BaseEnchant;
import net.staticstudios.prisons.pickaxe.enchants.handler.EnchantTier;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.Material;

import java.math.BigInteger;
import java.util.Map;

public class ExplosionEnchant extends BaseEnchant {
    public ExplosionEnchant() {
        super("explosion", "&c&lExplosion", 5000, BigInteger.valueOf(450), "&7Change to explode part of a mine");
        setPickaxeLevelRequirement(31);

        setTiers(
                new EnchantTier(100, 0),
                new EnchantTier(200, 1),
                new EnchantTier(500, 2),
                new EnchantTier(750, 3),
                new EnchantTier(1000, 4),
                new EnchantTier(1250, 5),
                new EnchantTier(1500, 6),
                new EnchantTier(1750, 7),
                new EnchantTier(2000, 8),
                new EnchantTier(2250, 9),
                new EnchantTier(2500, 10),
                new EnchantTier(3000, 11),
                new EnchantTier(3500, 12),
                new EnchantTier(4000, 13),
                new EnchantTier(4500, 14),
                new EnchantTier(5000, 15)
        );

        setUseChances(true);
        setDefaultPercentChance(1d / 5000 * 100); //1 out of 5,000
        setPercentChancePerLevel((1d / 3000 * 100 - getDefaultPercentChance()) / MAX_LEVEL); //it will activate 1 out of 3,000 times at max level
    }
    //todo: have some precomputed explosions for this
    public void onBlockBreak(BlockBreak blockBreak) {
        if (blockBreak.getBlockLocation() == null) return;
        if (!activate(blockBreak.getPickaxe())) return;
        int radius = 5 + blockBreak.getPickaxe().getEnchantLevel(ENCHANT_ID) / 750;
        radius += PrisonUtils.randomDouble(0, 0.4) * radius;
        MineBomb bomb = new MineBomb(blockBreak.getBlockLocation(), radius);
        for (Map.Entry<Material, Long> entry : bomb.explode(blockBreak.getMine()).entrySet()) {
            blockBreak.getStats().getMinedBlocks().put(MineBlock.fromMaterial(entry.getKey()), blockBreak.getStats().getMinedBlocks().getOrDefault(MineBlock.fromMaterial(entry.getKey()), 0L) + entry.getValue());
        }
        blockBreak.getStats().setBlocksBroken(blockBreak.getStats().getBlocksBroken() + bomb.blocksChanged - 1);
    }
}
