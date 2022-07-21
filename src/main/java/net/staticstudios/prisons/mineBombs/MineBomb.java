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
import net.staticstudios.prisons.privateMines.PrivateMine;
import net.staticstudios.prisons.utils.Constants;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MineBomb {
    static final Pattern pattern = BlockTypes.AIR;
    private Location location;
    private int originX;
    private int originY;
    private int originZ;
    private World world;
    private double radius;
    private boolean useParticles = true;

    private boolean usedBefore = false;

    public boolean isUseParticles() {
        return useParticles;
    }

    public void setUseParticles(boolean useParticles) {
        this.useParticles = useParticles;
    }

    public List<BlockVector3> positions = new LinkedList<>();


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
        positions.clear();
        makeSphere(radius, true);
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
        if (!usedBefore) { //If a mine bomb is being used a lot, it will compute lightning and slow down the server
            editSession = StaticPrisons.worldEdit.newEditSessionBuilder()
                    .world(BukkitAdapter.adapt(world))
                    .build();
        } else {
            editSession = StaticPrisons.worldEdit.newEditSessionBuilder()
                    .world(BukkitAdapter.adapt(world))
                    .relightMode(RelightMode.NONE)
                    .build();
        }
        editSession.setMask(new RegionMask(mine.getRegion()));
        for (BlockVector3 pos : positions) {
            setBlock(pos.getBlockX() + originX, pos.getBlockY() + originY, pos.getBlockZ() + originZ, pattern);
        }
        editSession.close();
        if (useParticles){
            world.spawnParticle(Particle.EXPLOSION_LARGE, location, particles, radius, radius, radius);
        }
        blocksChanges.remove(null);
        usedBefore = true;
        return blocksChanges;
    }


    //Custom implementation -- used so changes can be tracked
    private void makeSphere(double radius, boolean filled) throws MaxChangedBlocksException {
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

                    if (!filled) {
                        if (nextXn * nextXn + dy + dz <= 1 && nextYn * nextYn + dx + dz <= 1 && nextZn * nextZn + dx + dy <= 1) {
                            continue;
                        }
                    }
                    //FAWE start
                    yy = py + y;
                    if (yy <= 255) {
                        positions.add(BlockVector3.at(px + x, py + y, pz + z));
//                        setBlock(px + x, py + y, pz + z, block);
                        if (x != 0) {
                            positions.add(BlockVector3.at(px - x, py + y, pz + z));
//                            setBlock(px - x, py + y, pz + z, block);
                        }
                        if (z != 0) {
//                            setBlock(px + x, py + y, pz - z, block);
                            positions.add(BlockVector3.at(px + x, py + y, pz - z));
                            if (x != 0) {
                                positions.add(BlockVector3.at(px - x, py + y, pz - z));
//                                setBlock(px - x, py + y, pz - z, block);
                            }
                        }
                    }
                    yy = py - y;
                    positions.add(BlockVector3.at(px + x, yy, pz + z));
//                        setBlock(px + x, yy, pz + z, block);
                    if (x != 0) {
                        positions.add(BlockVector3.at(px - x, yy, pz + z));
//                            setBlock(px - x, yy, pz + z, block);
                    }
                    if (z != 0) {
                        positions.add(BlockVector3.at(px + x, yy, pz - z));
//                            setBlock(px + x, yy, pz - z, block);
                        if (x != 0) {
                            positions.add(BlockVector3.at(px - x, yy, pz - z));
//                                setBlock(px - x, yy, pz - z, block);
                        }
                    }
                }
            }
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
