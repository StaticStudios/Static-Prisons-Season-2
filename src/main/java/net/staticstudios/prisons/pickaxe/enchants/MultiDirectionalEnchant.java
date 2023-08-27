package net.staticstudios.prisons.pickaxe.enchants;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.world.block.BlockTypes;
import net.staticstudios.mines.StaticMine;
import net.staticstudios.prisons.blockbreak.BlockBreak;
import net.staticstudios.prisons.blockbreak.BlockBreakProcessEvent;
import net.staticstudios.prisons.mines.MineBlock;
import net.staticstudios.prisons.pickaxe.PrisonPickaxe;
import net.staticstudios.prisons.pickaxe.enchants.handler.PickaxeEnchant;
import org.bukkit.Location;
import org.bukkit.Material;

import java.util.HashMap;
import java.util.Map;

public class MultiDirectionalEnchant extends PickaxeEnchant {

    private static double DEFAULT_DEPTH = 1;
    private static double DEPTH_AT_MAX_LEVEL = 150;

    public MultiDirectionalEnchant() {
        super(MultiDirectionalEnchant.class, "pickaxe-multidirectional");

        DEFAULT_DEPTH = getConfig().getDouble("default_depth", DEFAULT_DEPTH);
        DEPTH_AT_MAX_LEVEL = getConfig().getDouble("max_depth", DEPTH_AT_MAX_LEVEL);
    }

    @Override
    public void onEvent(BlockBreakProcessEvent event) {
        BlockBreak blockBreak = event.getBlockBreak();
        PrisonPickaxe pickaxe = blockBreak.getPickaxe();
        StaticMine mine = blockBreak.getMine();

        int blockZ = blockBreak.getBlockLocation().getBlockZ();
        int blockX = blockBreak.getBlockLocation().getBlockX();
        int blockY = blockBreak.getBlockLocation().getBlockY();
        int minY = Math.max(
                mine.getMinPoint().getBlockY(),
                (int) (blockY - (DEFAULT_DEPTH + Math.max(0, (pickaxe.getEnchantmentLevel(MultiDirectionalEnchant.class) * DEPTH_AT_MAX_LEVEL / getMaxLevel()) - DEFAULT_DEPTH)))
        );


        long totalBlocksBroken = 0;
        Map<Material, Long> blocksBroken = new HashMap<>();
        for (int y = minY; y <= blockY; y++) {
            for (int x = mine.getMinPoint().getBlockX(); x < mine.getMaxPoint().getBlockX() + 1; x++) {
                Material mat = new Location(mine.getBukkitWorld(), x, y, blockZ).getBlock().getType();
                if (!mat.equals(Material.AIR)) {
                    totalBlocksBroken++;
                    if (!blocksBroken.containsKey(mat)) {
                        blocksBroken.put(mat, 1L);
                    } else blocksBroken.put(mat, blocksBroken.get(mat) + 1);
                }
            }
            for (int z = mine.getMinPoint().getBlockZ(); z < mine.getMaxPoint().getBlockZ() + 1; z++) {
                if (z == blockZ) continue; //We already got the 0 pos with the x loop
                Material mat = new Location(mine.getBukkitWorld(), blockX, y, z).getBlock().getType();
                if (!mat.equals(Material.AIR)) {
                    totalBlocksBroken++;
                    if (!blocksBroken.containsKey(mat)) {
                        blocksBroken.put(mat, 1L);
                    } else blocksBroken.put(mat, blocksBroken.get(mat) + 1);
                }
            }
        }


        EditSession editSession = WorldEdit.getInstance().newEditSession(mine.getWorld());
        Region lineX = new CuboidRegion(mine.getWorld(),
                BlockVector3.at(mine.getMinPoint().getBlockX(), minY, blockZ),
                BlockVector3.at(mine.getMaxPoint().getBlockX(), blockY, blockZ)
        );
        Region lineZ = new CuboidRegion(mine.getWorld(),
                BlockVector3.at(blockX, minY, mine.getMaxPoint().getBlockZ()),
                BlockVector3.at(blockX, blockY, mine.getMinPoint().getBlockZ())
        );
        editSession.setBlocks(lineX, BlockTypes.AIR);
        editSession.setBlocks(lineZ, BlockTypes.AIR);
        editSession.close();


        for (Map.Entry<Material, Long> entry : blocksBroken.entrySet()) {
            MineBlock mineBlock = MineBlock.fromMaterial(entry.getKey());
            blockBreak.stats().getMinedBlocks().put(
                    mineBlock,
                    blockBreak.stats().getMinedBlocks().getOrDefault(mineBlock, 0L) + entry.getValue()
            );
        }
        blockBreak.stats().setBlocksBroken(blockBreak.stats().getBlocksBroken() + totalBlocksBroken);
    }

}
