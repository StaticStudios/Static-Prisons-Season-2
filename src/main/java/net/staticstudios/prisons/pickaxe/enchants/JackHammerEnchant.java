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
import net.staticstudios.prisons.pickaxe.enchants.handler.PickaxeEnchants;
import org.bukkit.Location;
import org.bukkit.Material;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class JackHammerEnchant extends BaseEnchant {
    public JackHammerEnchant() {
        super("jackHammer", "&8&lJack Hammer", 20000, BigInteger.valueOf(250), "&7Chance to destroy a layer of a mine");

        //40 Tiers
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
                new EnchantTier(15000, 29),
                new EnchantTier(15500, 30),
                new EnchantTier(16000, 31),
                new EnchantTier(16500, 32),
                new EnchantTier(17000, 33),
                new EnchantTier(17500, 34),
                new EnchantTier(18000, 35),
                new EnchantTier(18500, 36),
                new EnchantTier(19000, 37),
                new EnchantTier(19500, 38),
                new EnchantTier(20000, 39)
        );


        setUseChances(true);
        setDefaultPercentChance(1d / 2000 * 100); //1 out of 2,000
        setPercentChancePerLevel((1d / 150 * 100 - getDefaultPercentChance()) / MAX_LEVEL); //it will activate 1 out of 150 times at max level
    }

    public void onBlockBreak(BlockBreak blockBreak) {
        if (!activate(blockBreak.getPickaxe())) return;
        int howDeepToGo = 1;
        if (PickaxeEnchants.DOUBLE_JACK_HAMMER.activate(blockBreak.getPickaxe())) {
            howDeepToGo = 2;
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


