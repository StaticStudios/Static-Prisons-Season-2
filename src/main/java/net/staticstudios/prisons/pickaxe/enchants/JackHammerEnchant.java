package net.staticstudios.prisons.pickaxe.enchants;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.world.block.BlockTypes;
import net.staticstudios.mines.StaticMine;
import net.staticstudios.prisons.blockBroken.BlockBreak;
import net.staticstudios.prisons.mines.MineBlock;
import net.staticstudios.prisons.pickaxe.enchants.handler.BaseEnchant;
import net.staticstudios.prisons.pickaxe.enchants.handler.PickaxeEnchants;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.Location;
import org.bukkit.Material;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class JackHammerEnchant extends BaseEnchant {
    public JackHammerEnchant() {
        super("jackHammer", "&8&lJack Hammer", 20000, BigInteger.valueOf(250), "&7Chance to destroy a layer of a mine");
        setPickaxeLevelRequirement(10);
    }

    public void onBlockBreak(BlockBreak blockBreak) {
        if (PrisonUtils.randomInt(1, 75) != 1) return; //Chance to activate enchant
        int jackHammerLevel = blockBreak.getPickaxe().getEnchantLevel(ENCHANT_ID);
        int doubleWammyLevel = blockBreak.getPickaxe().getEnchantLevel(PickaxeEnchants.DOUBLE_JACK_HAMMER);
        if (PrisonUtils.randomInt(1, MAX_LEVEL + MAX_LEVEL / 10) > jackHammerLevel + MAX_LEVEL / 10)
            return; //Chance to activate enchant

        int howDeepToGo = 1;
        if (doubleWammyLevel > 0 && blockBreak.getPickaxe().getIsEnchantEnabled(PickaxeEnchants.DOUBLE_JACK_HAMMER)) {
            if (PrisonUtils.randomInt(1, PickaxeEnchants.DOUBLE_JACK_HAMMER.MAX_LEVEL + PickaxeEnchants.DOUBLE_JACK_HAMMER.MAX_LEVEL / 10) <= doubleWammyLevel + PickaxeEnchants.DOUBLE_JACK_HAMMER.MAX_LEVEL / 10) //Chance to activate double wammy
                howDeepToGo += 1;
        }

        BreakLayer bl = new BreakLayer(blockBreak.getMine());
        for (Map.Entry<Material, Long> entry : bl.destroyLayer(blockBreak.getBlockLocation().getBlockY(), howDeepToGo).entrySet()) {
            blockBreak.getStats().getMinedBlocks().put(MineBlock.fromMaterial(entry.getKey()), blockBreak.getStats().getMinedBlocks().getOrDefault(MineBlock.fromMaterial(entry.getKey()), 0L) + entry.getValue());
        }
        blockBreak.getStats().setBlocksBroken(blockBreak.getStats().getBlocksBroken() + bl.totalBlocksBroken);
    }

    static class BreakLayer {
        private final StaticMine mine;
        public long totalBlocksBroken = 0;
        public BreakLayer(StaticMine mine) {
            this.mine = mine;
        }

        public Map<Material, Long> destroyLayer(int yLevel, int howDeepToGo) {
            Map<Material, Long> blocksBroken = new HashMap<>();
            for (int y = Math.max(1, yLevel - howDeepToGo + 1); y <= yLevel; y++) {
                for (int x = mine.getMinVector().getBlockX(); x <= mine.getMaxVector().getBlockX(); x++) {
                    for (int z = mine.getMinVector().getBlockZ(); z <= mine.getMaxVector().getBlockZ(); z++) {
                        Material mat = new Location(mine.getWorld(), x, y, z).getBlock().getType();
                        if (mat.equals(Material.AIR)) continue;
                        totalBlocksBroken += 1;
                        if (!blocksBroken.containsKey(mat)) {
                            blocksBroken.put(mat, 1L);
                        } else blocksBroken.put(mat, blocksBroken.get(mat) + 1);
                    }
                }
            }



            Region region = new CuboidRegion(mine.getWEWorld(), BlockVector3.at(mine.getMinVector().getBlockX(), yLevel, mine.getMinVector().getBlockZ()), BlockVector3.at(mine.getMaxVector().getBlockX(), Math.max(1, yLevel - howDeepToGo + 1), mine.getMaxVector().getBlockZ()));
            EditSession editSession = WorldEdit.getInstance().newEditSession(region.getWorld());
            editSession.setBlocks(region, BlockTypes.AIR);
            editSession.close();
            return blocksBroken;
        }
    }
}


