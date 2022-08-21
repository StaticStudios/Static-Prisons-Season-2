package net.staticstudios.prisons.pickaxe.abilities;

import net.staticstudios.mines.StaticMine;
import net.staticstudios.prisons.blockbreak.BlockBreak;
import net.staticstudios.prisons.minebombs.MultiBombMineBomb;
import net.staticstudios.prisons.mines.MineBlock;
import net.staticstudios.prisons.pickaxe.abilities.handler.BaseAbility;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class LightningStrikeAbility extends BaseAbility {

    private static final MultiBombMineBomb lightningStrikeBomb = new MultiBombMineBomb(6);

    public LightningStrikeAbility() {
        super("lightningStrike", "&b&lLightning Strike", 11, 9, 1000 * 60 * 150,
                "&oWhen breaking a block, you will have a 5% chance to",
                "&ocreate a lighting strike that will break all the blocks",
                "&oaround you going all the way down to the bottom of the mine!",
                "",
                "&aEach upgrade will increase the duration by 5 seconds",
                "",
                "Cooldown: &c" + PrisonUtils.formatTime(1000 * 60 * 150));
        lightningStrikeBomb.computePositions();
        lightningStrikeBomb.setUseParticles(false);
    }

    static int STEP = 2;
    public void onBlockBreak(BlockBreak blockBreak) {
        if (PrisonUtils.randomInt(1, 20) != 1) return; //5% chance to activate
        StaticMine mine = blockBreak.getMine();
        Location loc = blockBreak.getBlockLocation().clone();
        loc.setY(mine.getMaxPoint().getBlockY());
        List<Location> locs = new LinkedList<>();
        for (int y = mine.getMaxPoint().getBlockY(); y > mine.getMinPoint().getBlockY(); y -= STEP) {
            loc.add(0, -STEP, 0);
            locs.add(loc.clone().add(PrisonUtils.randomDouble(-2, 2), -1, PrisonUtils.randomDouble(-2, 2)));
        }
        Map<Material, Long> blocksBroken = lightningStrikeBomb.explodeAtComputedPositions(mine, locs);
        for (Map.Entry<Material, Long> entry: blocksBroken.entrySet()) {
            MineBlock mb = MineBlock.fromMaterial(entry.getKey());
            blockBreak.getStats().getMinedBlocks().put(mb, blockBreak.getStats().getMinedBlocks().getOrDefault(mb, 0L) + entry.getValue());
        }
        blockBreak.getStats().setBlocksBroken(blockBreak.getStats().getBlocksBroken() + lightningStrikeBomb.blocksChanged);
        loc.getWorld().strikeLightningEffect(loc);
    }

    @Override
    public int getTimesToTick(int level) {
        return 5 * 20 + (level * 20 * 5); //Default 10 seconds + 5 seconds per level
    }
}
