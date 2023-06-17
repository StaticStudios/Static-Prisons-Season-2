package net.staticstudios.prisons.minebombs;

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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MineBomb {
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

    public MineBomb(Location origin, double radius) {
        this.location = origin;
        this.originX = origin.getBlockX();
        this.originY = origin.getBlockY();
        this.originZ = origin.getBlockZ();
        this.world = origin.getWorld();
        this.radius = radius;
    }
    public MineBomb(double radius) {
        this.radius = radius;
    }


    public MineBomb computePositions() {
        positions = makeSphere(radius);
        return this;
    }



    public Map<Material, Long> explode(StaticMine mine) {
        return explode(mine, (int) (radius / 2 * 25) + 75);
    }
    public Map<Material, Long> explode(StaticMine mine, int particles) {
        computePositions();
        return explodeAtComputedPositions(mine, particles);
    }
    public Map<Material, Long> explodeAtComputedPositions(StaticMine mine, Location newOrigin) {
        location = newOrigin;
        this.world = newOrigin.getWorld();
        this.originX = newOrigin.getBlockX();
        this.originY = newOrigin.getBlockY();
        this.originZ = newOrigin.getBlockZ();
        return explodeAtComputedPositions(mine, (int) (radius / 2 * 25) + 75);
    }
    public Map<Material, Long> explodeAtComputedPositions(StaticMine mine, int particles) {
        if (positions.isEmpty()) computePositions();
        blocksChanged = 0;
        blocksChanges.clear();
        this.mine = mine;
        editSession = StaticPrisons.worldEdit.newEditSessionBuilder()
                .world(BukkitAdapter.adapt(world))
                .build();
        editSession.setMask(new RegionMask(mine.getRegion()));
        for (BlockVector3 pos : positions) {
            setBlock(pos.getBlockX() + originX, pos.getBlockY() + originY, pos.getBlockZ() + originZ, pattern);
        }
        editSession.close();
        if (useParticles) {
            world.spawnParticle(Particle.EXPLOSION_LARGE, location, particles, radius, radius, radius);
        }
        blocksChanges.remove(null);
        return blocksChanges;
    }


    //Custom implementation -- used so changes can be tracked
    public static HashSet<BlockVector3> makeSphere(double radius) throws MaxChangedBlocksException {
        HashSet<BlockVector3> locations = new HashSet<>();
        double radiusX = radius;
        double radiusY = radius;
        double radiusZ = radius;
        radiusX += 0.5;
        radiusY += 0.5;
        radiusZ += 0.5;

        final double invRadiusX = 1 / radiusX;
        final double invRadiusY = 1 / radiusY;
        final double invRadiusZ = 1 / radiusZ;

        int px = 0;
        int py = 0;
        int pz = 0;

        final int ceilRadiusX = (int) Math.ceil(radiusX);
        final int ceilRadiusY = (int) Math.ceil(radiusY);
        final int ceilRadiusZ = (int) Math.ceil(radiusZ);

        //FAWE start
        int yy;
        //FAWE end

        double nextXn = 0;
        forX:

        for (int x = 0; x <= ceilRadiusX; ++x) {
            final double xn = nextXn;
            double dx = xn * xn;
            nextXn = (x + 1) * invRadiusX;
            double nextZn = 0;
            forZ:
            for (int z = 0; z <= ceilRadiusZ; ++z) {
                final double zn = nextZn;
                double dz = zn * zn;
                double dxz = dx + dz;
                nextZn = (z + 1) * invRadiusZ;
                double nextYn = 0;

                forY:
                for (int y = 0; y <= ceilRadiusY; ++y) {
                    final double yn = nextYn;
                    double dy = yn * yn;
                    double dxyz = dxz + dy;
                    nextYn = (y + 1) * invRadiusY;

                    if (dxyz > 1) {
                        if (y == 0) {
                            if (z == 0) {
                                break forX;
                            }
                            break forZ;
                        }
                        break forY;
                    }

                    //FAWE start
                    yy = py + y;
                    if (yy <= 255) {
                        locations.add(BlockVector3.at(px + x, py + y, pz + z));
                        if (x != 0) {
                            locations.add(BlockVector3.at(px - x, py + y, pz + z));
                        }
                        if (z != 0) {
                            locations.add(BlockVector3.at(px + x, py + y, pz - z));
                            if (x != 0) {
                                locations.add(BlockVector3.at(px - x, py + y, pz - z));
                            }
                        }
                    }
                    yy = py - y;
                    locations.add(BlockVector3.at(px + x, yy, pz + z));
                    if (x != 0) {
                        locations.add(BlockVector3.at(px - x, yy, pz + z));
                    }
                    if (z != 0) {
                        locations.add(BlockVector3.at(px + x, yy, pz - z));
                        if (x != 0) {
                            locations.add(BlockVector3.at(px - x, yy, pz - z));
                        }
                    }
                }
            }
        }
        return locations;
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
