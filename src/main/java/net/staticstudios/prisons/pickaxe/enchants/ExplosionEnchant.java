package net.staticstudios.prisons.pickaxe.enchants;

import net.staticstudios.prisons.blockBroken.BlockBreak;
import net.staticstudios.prisons.mines.MineBlock;
import net.staticstudios.prisons.pickaxe.enchants.handler.BaseEnchant;
import net.staticstudios.prisons.mineBombs.MineBomb;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.Material;

import java.math.BigInteger;
import java.util.Map;

public class ExplosionEnchant extends BaseEnchant {
    public ExplosionEnchant() {
        super("explosion", "&c&lExplosion", 5000, BigInteger.valueOf(450), "&7Change to explode part of a mine");
        setPickaxeLevelRequirement(10);
    }
    public void onBlockBreak(BlockBreak blockBreak) {
        if (blockBreak.getBlockLocation() == null) return;
        int explosionLevel = blockBreak.getPickaxe().getEnchantLevel(ENCHANT_ID);
        if (PrisonUtils.randomInt(1, 4500 - (int) (explosionLevel / 3.75)) != 1) return; //Chance to activate enchant
        int radius = 5 + explosionLevel / 750;
        radius += PrisonUtils.randomDouble(0, 0.4) * radius;
        MineBomb bomb = new MineBomb(blockBreak.getBlockLocation(), radius);
        for (Map.Entry<Material, Long> entry : bomb.explode(blockBreak.getMine()).entrySet()) {
            blockBreak.getStats().getMinedBlocks().put(MineBlock.fromMaterial(entry.getKey()), blockBreak.getStats().getMinedBlocks().getOrDefault(MineBlock.fromMaterial(entry.getKey()), 0L) + entry.getValue());
        }
        blockBreak.getStats().setBlocksBroken(blockBreak.getStats().getBlocksBroken() + bomb.blocksChanged - 1);
    }
}
