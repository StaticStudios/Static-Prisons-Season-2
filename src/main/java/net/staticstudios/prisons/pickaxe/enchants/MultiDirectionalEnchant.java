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
import net.staticstudios.prisons.pickaxe.enchants.handler.EnchantTier;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.Location;
import org.bukkit.Material;

import java.util.HashMap;
import java.util.Map;

public class MultiDirectionalEnchant extends BaseEnchant {
    public MultiDirectionalEnchant() {
        super("multiDirectional", "&a&lMulti-Directional", 15000, 500, "&7Chance to destroy a &l+&7 shape in a mine");
        setPickaxeLevelRequirement(31);

        setTiers(
                new EnchantTier(250, 0),
                new EnchantTier(500, 1),
                new EnchantTier(750, 1),
                new EnchantTier(1000, 2),
                new EnchantTier(1250, 2),
                new EnchantTier(1500, 2),
                new EnchantTier(2000, 3),
                new EnchantTier(2500, 4),
                new EnchantTier(3000, 5),
                new EnchantTier(3500, 6),
                new EnchantTier(4000, 7),
                new EnchantTier(4500, 8),
                new EnchantTier(5000, 9),
                new EnchantTier(5500, 10),
                new EnchantTier(6000, 11),
                new EnchantTier(6500, 12),
                new EnchantTier(7000, 13),
                new EnchantTier(7500, 14),
                new EnchantTier(8000, 15),
                new EnchantTier(8500, 16),
                new EnchantTier(9000, 17),
                new EnchantTier(9500, 18),
                new EnchantTier(10000, 19),
                new EnchantTier(10500, 20),
                new EnchantTier(11000, 21),
                new EnchantTier(11500, 22),
                new EnchantTier(12000, 23),
                new EnchantTier(12500, 24),
                new EnchantTier(13000, 25),
                new EnchantTier(13500, 26),
                new EnchantTier(14000, 27),
                new EnchantTier(14500, 28),
                new EnchantTier(15000, 29)
        );

    }

    public void onBlockBreak(BlockBreak blockBreak) {
        if (blockBreak.getBlockLocation() == null) return;
        if (PrisonUtils.randomInt(1, 125) != 1) return; //Chance to activate enchant
        int multiDirectionalLevel = blockBreak.getPickaxe().getEnchantLevel(ENCHANT_ID);
        if (PrisonUtils.randomInt(1, MAX_LEVEL + MAX_LEVEL / 10) > multiDirectionalLevel + MAX_LEVEL / 10) return; //Chance to activate enchant
        int howDeepToGo = Math.max(1, blockBreak.getBlockLocation().getBlockY() - multiDirectionalLevel * 250 / MAX_LEVEL);


        BreakPlus bp = new BreakPlus(blockBreak.getMine(), blockBreak.getBlockLocation());
        for (Map.Entry<Material, Long> entry : bp.destroySection(blockBreak.getBlockLocation().getBlockY(), howDeepToGo, blockBreak.getBlockLocation().getBlockX(), blockBreak.getBlockLocation().getBlockZ()).entrySet()) {
            blockBreak.getStats().getMinedBlocks().put(MineBlock.fromMaterial(entry.getKey()), blockBreak.getStats().getMinedBlocks().getOrDefault(MineBlock.fromMaterial(entry.getKey()), 0L) + entry.getValue());
        }
        blockBreak.getStats().setBlocksBroken(blockBreak.getStats().getBlocksBroken() + bp.totalBlocksBroken);
    }

    static class BreakPlus {

        private final StaticMine mine;
        private final Location loc;
        public long totalBlocksBroken = 0;
        public BreakPlus(StaticMine mine, Location loc) {
            this.loc = loc;
            this.mine = mine;
        }

        /**
         * Destroys a section of the mine in the world specified
         *
         * @return all the blocks that were removed
         */
        public Map<Material, Long> destroySection(int fromY, int minY, int xCord, int zCord) {
            Map<Material, Long> blocksBroken = new HashMap<>();
            int blockZ = loc.getBlockZ();
            int blockX = loc.getBlockX();
            for(int y = minY; y <= fromY; y++) {
                for (int x = mine.getMinVector().getBlockX(); x < mine.getMaxVector().getBlockX() + 1; x++) {
                    Material mat = new Location(mine.getWorld(), x, y, blockZ).getBlock().getType();
                    if (!mat.equals(Material.AIR)) {
                        totalBlocksBroken++;
                        if (!blocksBroken.containsKey(mat)) {
                            blocksBroken.put(mat, 1L);
                        } else blocksBroken.put(mat, blocksBroken.get(mat) + 1);
                    }
                }
                for (int z = mine.getMinVector().getBlockZ(); z < mine.getMaxVector().getBlockZ() + 1; z++) {
                    if (z == blockZ) continue; //We already got the 0 pos with the x loop
                    Material mat = new Location(mine.getWorld(), blockX, y, z).getBlock().getType();
                    if (!mat.equals(Material.AIR)) {
                        totalBlocksBroken++;
                        if (!blocksBroken.containsKey(mat)) {
                            blocksBroken.put(mat, 1L);
                        } else blocksBroken.put(mat, blocksBroken.get(mat) + 1);
                    }
                }
            }
            Region region = new CuboidRegion(mine.getWEWorld(), BlockVector3.at(mine.getMinVector().getBlockX(), minY, zCord), BlockVector3.at(mine.getMaxVector().getBlockX(), fromY, zCord));
            EditSession editSession = WorldEdit.getInstance().newEditSession(region.getWorld());
            editSession.setBlocks(region, BlockTypes.AIR);
            editSession.close();
            region = new CuboidRegion(mine.getWEWorld(), BlockVector3.at(xCord, minY,mine.getMaxVector().getBlockZ()), BlockVector3.at(xCord, fromY, mine.getMinVector().getBlockZ()));
            editSession.setBlocks(region, BlockTypes.AIR);
            editSession.close();
            return blocksBroken;
        }
    }
}
