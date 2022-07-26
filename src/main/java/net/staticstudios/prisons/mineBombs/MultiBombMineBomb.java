package net.staticstudios.prisons.mineBombs;

import com.fastasyncworldedit.core.extent.processor.lighting.RelightMode;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.function.mask.RegionMask;
import com.sk89q.worldedit.function.pattern.Pattern;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.world.block.BlockType;
import com.sk89q.worldedit.world.block.BlockTypes;
import net.staticstudios.mines.StaticMine;
import net.staticstudios.prisons.StaticPrisons;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;

import java.util.*;

public class MultiBombMineBomb {
    static final Pattern pattern = BlockTypes.AIR;
    private Location location;
    private int originX;
    private int originY;
    private int originZ;
    private World world;
    private double radius;
    private boolean useParticles = true;

    public boolean isUseParticles() {
        return useParticles;
    }

    public void setUseParticles(boolean useParticles) {
        this.useParticles = useParticles;
    }

    public Set<BlockVector3> positions = new HashSet<>();


    private EditSession editSession;
    private Map<Material, Long> blocksChanges = new HashMap<>();
    public long blocksChanged = 0;
    private StaticMine mine;

    public MultiBombMineBomb(Location origin, double radius) {
        this.location = origin;
        this.originX = origin.getBlockX();
        this.originY = origin.getBlockY();
        this.originZ = origin.getBlockZ();
        this.world = origin.getWorld();
        this.radius = radius;
    }
    public MultiBombMineBomb(double radius) {
        this.radius = radius;
    }


    public MultiBombMineBomb computePositions() {
        positions = MineBomb.makeSphere(radius);
        return this;
    }


    public Map<Material, Long> explodeAtComputedPositions(StaticMine mine, List<Location> newOrigins) {
        if (positions.isEmpty()) computePositions();
        blocksChanges.clear();
        blocksChanged = 0;
        if (newOrigins.isEmpty()) {
            return blocksChanges;
        }
        this.world = newOrigins.get(0).getWorld();
        editSession = StaticPrisons.worldEdit.newEditSessionBuilder()
                .world(BukkitAdapter.adapt(world))
                .build();
        editSession.setMask(new RegionMask(mine.getRegion()));
        this.mine = mine;


        for (Location newOrigin : newOrigins) {
            location = newOrigin;
            this.world = newOrigin.getWorld();
            this.originX = newOrigin.getBlockX();
            this.originY = newOrigin.getBlockY();
            this.originZ = newOrigin.getBlockZ();
            explodeAtComputedPositions((int) (radius / 2 * 25) + 75);
        }

        editSession.close();
        blocksChanges.remove(null);
        return blocksChanges;
    }
    void explodeAtComputedPositions(int particles) {
        for (BlockVector3 pos : positions) {
            setBlock(pos.getBlockX() + originX, pos.getBlockY() + originY, pos.getBlockZ() + originZ, pattern);
        }
        if (useParticles) {
            world.spawnParticle(Particle.EXPLOSION_LARGE, location, particles, radius, radius, radius);
        }
    }


    //If the block gets changed, it tracks the changes
    private void setBlock(int x, int y, int z, Pattern pattern) {
        BlockType blockType = editSession.getBlockType(x, y, z);
        if (blockType == BlockTypes.AIR) return;
        if (mine.getRegion().contains(x, y, z) && editSession.setBlock(x, y, z, pattern)) {
            Material mat = BukkitAdapter.adapt(blockType);
            blocksChanges.put(mat, blocksChanges.getOrDefault(mat, 0L) + 1);
            blocksChanged++;
        }
    }
}
