package net.staticstudios.prisons.pickaxe.abilities;

import net.staticstudios.mines.StaticMine;
import net.staticstudios.prisons.blockBroken.BlockBreak;
import net.staticstudios.prisons.mineBombs.MineBomb;
import net.staticstudios.prisons.mines.MineBlock;
import net.staticstudios.prisons.pickaxe.abilities.handler.BaseAbility;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.Location;
import org.bukkit.Material;

import java.math.BigInteger;
import java.util.Map;

public class BeamOfLightAbility extends BaseAbility {

    private static MineBomb lightningStrikeBomb = new MineBomb(6);

    public BeamOfLightAbility() {
        super("lightningStrike", "&b&lLightning Strike", 11, BigInteger.ZERO, 1000 * 60 * 90,
                "&7&oWhen breaking a block, you will have a 5% chance to",
                "create a lighting strike that will break all the blocks",
                "around you going all the way down to the bottom of the mine!",
                "",
                "Cool down: &b" + PrisonUtils.formatTime(1000 * 60 * 90));
        lightningStrikeBomb.computePositions();
        lightningStrikeBomb.setUseParticles(false);
    }

    static int STEP = 2;
    public void onBlockBreak(BlockBreak blockBreak) {
        if (PrisonUtils.randomInt(1, 20) != 1) return; //5% chance to activate
        StaticMine mine = blockBreak.getMine();
        Location loc = blockBreak.getBlockLocation().clone();
        loc.setY(mine.getMaxVector().getBlockY());
        for (int y = mine.getMaxVector().getBlockY(); y > mine.getMinVector().getBlockY(); y -= STEP) {
            loc.add(0, -STEP, 0);
            Map<Material, Long> blocksBroken = lightningStrikeBomb.explodeAtComputedPositions(mine, loc.clone().add(PrisonUtils.randomDouble(-2, 2), -1, PrisonUtils.randomDouble(-2, 2)));
            for (Map.Entry<Material, Long> entry: blocksBroken.entrySet()) {
                MineBlock mb = MineBlock.fromMaterial(entry.getKey());
                blockBreak.getStats().getMinedBlocks().put(mb, blockBreak.getStats().getMinedBlocks().getOrDefault(mb, 0L) + entry.getValue());
            }
            blockBreak.getStats().setBlocksBroken(blockBreak.getStats().getBlocksBroken() + lightningStrikeBomb.blocksChanged);
        }
        loc.getWorld().strikeLightningEffect(loc);
    }

    @Override
    public int getTimesToTick(int level) {
        return 5 * 20 + (level * 20 * 5); //Default 10 seconds + 5 seconds per level
    }
}
