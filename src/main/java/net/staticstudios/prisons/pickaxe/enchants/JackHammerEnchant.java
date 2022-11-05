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
import net.staticstudios.prisons.enchants.Enchantable;
import net.staticstudios.prisons.mines.MineBlock;
import net.staticstudios.prisons.pickaxe.PrisonPickaxe;
import net.staticstudios.prisons.pickaxe.enchants.handler.PickaxeEnchant;
import org.bukkit.Location;
import org.bukkit.Material;

import java.util.HashMap;
import java.util.Map;

public class JackHammerEnchant extends PickaxeEnchant {

    public JackHammerEnchant() {
        super(JackHammerEnchant.class, "pickaxe-jackhammer");
    }

    @Override
    public void onEvent(BlockBreakProcessEvent event) {
        BlockBreak blockBreak = event.getBlockBreak();
        PrisonPickaxe pickaxe = blockBreak.getPickaxe();
        StaticMine mine = blockBreak.getMine();
        int howDeepToGo = Enchantable.getEnchant(DoubleWammyEnchant.class).shouldActivate(pickaxe) ? 2 : 1;


        Map<Material, Long> blocksBroken = new HashMap<>();
        final int yLevel = blockBreak.getBlockLocation().getBlockY();
        long totalBlocksBroken = 0;
        for (int y = Math.max(1, yLevel - howDeepToGo + 1); y <= yLevel; y++) {
            for (int x = mine.getMinPoint().getBlockX(); x <= mine.getMaxPoint().getBlockX(); x++) {
                for (int z = mine.getMinPoint().getBlockZ(); z <= mine.getMaxPoint().getBlockZ(); z++) {
                    Material mat = new Location(mine.getBukkitWorld(), x, y, z).getBlock().getType();
                    if (mat.equals(Material.AIR)) continue;
                    totalBlocksBroken += 1;
                    if (!blocksBroken.containsKey(mat)) {
                        blocksBroken.put(mat, 1L);
                    } else {
                        blocksBroken.put(mat, blocksBroken.get(mat) + 1);
                    }
                }
            }
        }

        Region region = new CuboidRegion(mine.getWorld(),
                BlockVector3.at(mine.getMinPoint().getBlockX(), yLevel, mine.getMinPoint().getBlockZ()),
                BlockVector3.at(mine.getMaxPoint().getBlockX(), Math.max(1, yLevel - howDeepToGo + 1), mine.getMaxPoint().getBlockZ()));
        EditSession editSession = WorldEdit.getInstance().newEditSession(region.getWorld());
        editSession.setBlocks(region, BlockTypes.AIR);
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
