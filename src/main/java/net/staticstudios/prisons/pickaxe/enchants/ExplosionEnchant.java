package net.staticstudios.prisons.pickaxe.enchants;

import net.staticstudios.prisons.blockbreak.BlockBreak;
import net.staticstudios.prisons.blockbreak.BlockBreakProcessEvent;
import net.staticstudios.prisons.minebombs.PreComputedMineBomb;
import net.staticstudios.prisons.mines.MineBlock;
import net.staticstudios.prisons.pickaxe.PrisonPickaxe;
import net.staticstudios.prisons.pickaxe.enchants.handler.PickaxeEnchant;
import net.staticstudios.prisons.utils.PrisonUtils;

import java.util.HashMap;
import java.util.Map;

public class ExplosionEnchant extends PickaxeEnchant {

    private static final Map<Integer, PreComputedMineBomb> CACHED_EXPLOSIONS = new HashMap<>();
    private static double DEFAULT_RADIUS = 5;
    private static double RADIUS_INCREASE_PER_LEVEL = 0.001;
    private static double RADIUS_RANDOMNESS = 0.5;

    public ExplosionEnchant() {
        super(ExplosionEnchant.class, "pickaxe-explosion");

        CACHED_EXPLOSIONS.clear();

        DEFAULT_RADIUS = getConfig().getDouble("default_radius", DEFAULT_RADIUS);
        RADIUS_INCREASE_PER_LEVEL = getConfig().getDouble("radius_increase_per_level", RADIUS_INCREASE_PER_LEVEL);
        RADIUS_RANDOMNESS = getConfig().getDouble("radius_randomness", RADIUS_RANDOMNESS);
    }

    @Override
    public void onEvent(BlockBreakProcessEvent event) {
        BlockBreak blockBreak = event.getBlockBreak();
        PrisonPickaxe pickaxe = blockBreak.getPickaxe();
        int radius = (int) (DEFAULT_RADIUS + pickaxe.getEnchantmentLevel(ExplosionEnchant.class) * RADIUS_INCREASE_PER_LEVEL + PrisonUtils.randomDouble(0, RADIUS_RANDOMNESS));

        PreComputedMineBomb mineBomb = CACHED_EXPLOSIONS.computeIfAbsent(radius, PreComputedMineBomb::new);

        mineBomb.explode(blockBreak.getMine(), blockBreak.getBlockLocation()).forEach((mat, amountBroken) -> {
            MineBlock mineBlock = MineBlock.fromMaterial(mat);
            blockBreak.stats().getMinedBlocks().put(mineBlock, blockBreak.stats().getMinedBlocks().getOrDefault(mineBlock, 0L) + amountBroken);
        });
        blockBreak.stats().setBlocksBroken(blockBreak.stats().getBlocksBroken() + mineBomb.blocksChanged - 1);
    }
}
