package net.staticstudios.prisons.enchants;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.world.block.BlockTypes;
import net.staticstudios.mines.StaticMine;
import net.staticstudios.prisons.blockBroken.PrisonBlockBroken;
import net.staticstudios.prisons.enchants.handler.BaseEnchant;
import net.staticstudios.prisons.enchants.handler.PrisonPickaxe;
import net.staticstudios.prisons.utils.StaticVars;
import net.staticstudios.prisons.utils.Utils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class MultiDirectionalEnchant extends BaseEnchant {
    public MultiDirectionalEnchant() {
        super("multiDirectional", "&a&lMulti-Directional", 15000, BigInteger.valueOf(500), "&7Chance to destroy a &l+&7 shape in a mine");
    }


    @Override
    public void onBlockBreak(PrisonBlockBroken bb) {
        if (Utils.randomInt(1, 125) != 1) return;
        int multiDirectionalLevel = bb.pickaxe.getEnchantLevel(ENCHANT_ID);
        if (Utils.randomInt(1, MAX_LEVEL + MAX_LEVEL / 10) <= multiDirectionalLevel + MAX_LEVEL / 10) {
            //Enchant should activate
            int howDeepToGo = Math.max(1, bb.blockLocation.getBlockY() - multiDirectionalLevel * 250 / MAX_LEVEL);
            BreakPlus bp = new BreakPlus(bb.mine, bb.blockLocation);
            bb.blockTypesBroken.putAll(bp.destroySection(bb.blockLocation.getBlockY(), howDeepToGo, bb.blockLocation.getBlockX(), bb.blockLocation.getBlockZ()));
            bb.blocksBroken += bp.totalBlocksBroken;
        }
    }

    @Override
    public void onPickaxeHeld(Player player, PrisonPickaxe pickaxe) {

    }

    @Override
    public void onPickaxeUnHeld(Player player, PrisonPickaxe pickaxe) {

    }

    @Override
    public void whileRightClicking(PlayerInteractEvent e, PrisonPickaxe pickaxe) {

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
        public Map<Material, BigInteger> destroySection(int fromY, int minY, int xCord, int zCord) {
            Map<Material, BigInteger> blocksBroken = new HashMap<>();
            int blockZ = loc.getBlockZ();
            int blockX = loc.getBlockX();
            for(int y = minY; y <= fromY; y++) {
                for (int x = mine.getMinVector().getBlockX(); x < mine.getMaxVector().getBlockX() + 1; x++) {
                    Material mat = new Location(StaticVars.MINES_WORLD, x, y, blockZ).getBlock().getType();
                    if (!mat.equals(Material.AIR)) {
                        totalBlocksBroken++;
                        if (!blocksBroken.containsKey(mat)) {
                            blocksBroken.put(mat, BigInteger.ONE);
                        } else blocksBroken.put(mat, blocksBroken.get(mat).add(BigInteger.ONE));
                    }
                }
                for (int z = mine.getMinVector().getBlockZ(); z < mine.getMaxVector().getBlockZ() + 1; z++) {
                    if (z == blockZ) continue; //We already got the 0 pos with the x loop
                    Material mat = new Location(StaticVars.MINES_WORLD, blockX, y, z).getBlock().getType();
                    if (!mat.equals(Material.AIR)) {
                        totalBlocksBroken++;
                        if (!blocksBroken.containsKey(mat)) {
                            blocksBroken.put(mat, BigInteger.ONE);
                        } else blocksBroken.put(mat, blocksBroken.get(mat).add(BigInteger.ONE));
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
            /*
            if (!mine.brokeBlocksInMine(totalBlocksBroken)) {
                // Bukkit.getScheduler().runTaskAsynchronously(Main.getMain(), () -> {
                Region region = new CuboidRegion(BukkitAdapter.adapt(mine.minLocation.getWorld()), BlockVector3.at(mine.minLocation.getX(), minY, zCord), BlockVector3.at(mine.maxLocation.getX(), fromY, zCord));
                EditSession editSession = WorldEdit.getInstance().newEditSession(region.getWorld());
                editSession.setBlocks(region, BlockTypes.AIR);
                editSession.close();
                region = new CuboidRegion(BukkitAdapter.adapt(mine.minLocation.getWorld()), BlockVector3.at(xCord, minY, mine.maxLocation.getZ()), BlockVector3.at(xCord, fromY, mine.minLocation.getZ()));
                editSession.setBlocks(region, BlockTypes.AIR);
                editSession.close();
                //});
            }

             */

            //BlockChange.addMultiDirectionalBlockChange(Bukkit.getWorld("mines"), (int) mine.minLocation.getX(), (int) loc.getY(), (int) mine.minLocation.getZ(), (int) mine.maxLocation.getX(), minY, (int) mine.maxLocation.getZ(), (int) loc.getX(), (int) loc.getZ());
            return blocksBroken;
        }
    }
}
