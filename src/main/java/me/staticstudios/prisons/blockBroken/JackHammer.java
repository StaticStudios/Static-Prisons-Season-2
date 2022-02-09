package me.staticstudios.prisons.blockBroken;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.world.block.BlockType;
import com.sk89q.worldedit.world.block.BlockTypes;
import me.staticstudios.prisons.Main;
import me.staticstudios.prisons.mines.BaseMine;
import me.staticstudios.prisons.mines.MineManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

import java.math.BigInteger;
import java.util.*;

public class JackHammer {

    private final int yLevel;
    private final BaseMine mine;
    public JackHammer(BaseMine mine, int yLevel) {
        this.yLevel = yLevel;
        this.mine = mine;
    }

    /**
     * Destroys a layer of the mine in the world specified
     *
     * @return all the blocks that were removed
     */
    public Map<Material, BigInteger> destroyLayer(int yLevel) {
        Map<Material, BigInteger> blocksBroken = new HashMap<>();
        int totalBlocksBroken = 0;
        for(int x = (int) mine.minLocation.getX(); x <= mine.maxLocation.getX(); x++) {
            for (int z = (int) mine.minLocation.getZ(); z <= mine.maxLocation.getZ(); z++) {
                Material mat = new Location(Bukkit.getWorld("mines"), x, yLevel, z).getBlock().getType();
                if (!mat.equals(Material.AIR)) {
                    totalBlocksBroken++;
                    if (!blocksBroken.containsKey(mat)) {
                        blocksBroken.put(mat, BigInteger.ONE);
                    } else blocksBroken.put(mat, blocksBroken.get(mat).add(BigInteger.ONE));
                }
            }
        }
        if (!mine.brokeBlocksInMine(totalBlocksBroken)) {
            Bukkit.getScheduler().runTaskAsynchronously(Main.getMain(), () -> {
                Region region = new CuboidRegion(BukkitAdapter.adapt(mine.minLocation.getWorld()), BlockVector3.at(mine.minLocation.getX(), yLevel, mine.minLocation.getZ()), BlockVector3.at(mine.maxLocation.getX(), yLevel, mine.maxLocation.getZ()));
                EditSession editSession = WorldEdit.getInstance().newEditSession(region.getWorld());
                editSession.setBlocks(region, BlockTypes.AIR);
                editSession.close();
            });
        }

        //BlockChange.addJackHammerBlockChange(Bukkit.getWorld("mines"), (int) mine.minLocation.getX(), yLevel, (int) mine.minLocation.getZ(), (int) mine.maxLocation.getX(), (int) mine.maxLocation.getZ());
        return blocksBroken;
    }
}
