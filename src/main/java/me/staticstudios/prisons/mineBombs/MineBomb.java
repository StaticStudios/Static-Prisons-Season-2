package me.staticstudios.prisons.mineBombs;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.function.mask.RegionMask;
import com.sk89q.worldedit.function.pattern.Pattern;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.world.block.BlockType;
import com.sk89q.worldedit.world.block.BlockTypes;
import me.staticstudios.prisons.Main;
import me.staticstudios.prisons.mines.BaseMine;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class MineBomb {
    static final Pattern pattern = BlockTypes.AIR;
    private Location location;
    private int originX;
    private int originY;
    private int originZ;
    private World world;
    private double radius;
    private boolean canExplode = true;
    private EditSession editSession;
    private Material lastMat = Material.STONE;
    private Map<Material, BigInteger> blocksChanges = new HashMap<>();
    private int blocksChanged = 0;
    private BaseMine mine;

    public MineBomb(Location origin, double radius) {
        this.location = origin;
        canExplode = location.getWorld().getName().equalsIgnoreCase("mines");
        this.originX = origin.getBlockX();
        this.originY = origin.getBlockY();
        this.originZ = origin.getBlockZ();
        this.world = origin.getWorld();
        this.radius = radius;
    }
    public Map<Material, BigInteger> explode(BaseMine mine) {
        if (!canExplode) return new HashMap<>();
        this.mine = mine;
        editSession = Main.worldEdit.newEditSessionBuilder().world(BukkitAdapter.adapt(world)).build();
        editSession.setMask(new RegionMask(mine.getRegion()));
        makeSphere(BlockVector3.at(originX, originY, originZ), pattern, radius, true);
        editSession.close();
        world.spawnParticle(Particle.EXPLOSION_LARGE, location, (int) (radius / 2 * 25) + 75, radius, radius, radius);
        mine.brokeBlocksInMine(blocksChanged);
        blocksChanges.remove(null);
        return blocksChanges;
    }


    //Custom implementation -- used so changes can be tracked
    private void makeSphere(BlockVector3 pos, Pattern block, double radius, boolean filled) throws MaxChangedBlocksException {
        double radiusX = radius;
        double radiusY = radius;
        double radiusZ = radius;
        radiusX += 0.5;
        radiusY += 0.5;
        radiusZ += 0.5;

        final double invRadiusX = 1 / radiusX;
        final double invRadiusY = 1 / radiusY;
        final double invRadiusZ = 1 / radiusZ;

        int px = pos.getBlockX();
        int py = pos.getBlockY();
        int pz = pos.getBlockZ();

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
                        setBlock(px + x, py + y, pz + z, block);
                        if (x != 0) {
                            setBlock(px - x, py + y, pz + z, block);
                        }
                        if (z != 0) {
                            setBlock(px + x, py + y, pz - z, block);
                            if (x != 0) {
                                setBlock(px - x, py + y, pz - z, block);
                            }
                        }
                    }
                    if (y != 0 && (yy = py - y) >= 0) {
                        setBlock(px + x, yy, pz + z, block);
                        if (x != 0) {
                            setBlock(px - x, yy, pz + z, block);
                        }
                        if (z != 0) {
                            setBlock(px + x, yy, pz - z, block);
                            if (x != 0) {
                                setBlock(px - x, yy, pz - z, block);
                            }
                        }
                    }
                }
            }
        }
    }
    //If the block gets changed, it tracks the changes
    private void setBlock(int x, int y, int z, Pattern pattern) {
        BlockType blockType = editSession.getBlockType(x, y, z);
        if (editSession.setBlock(x, y, z, pattern) && mine.getRegion().contains(x, y, z)) {
            blocksChanged++;
            Material mat = BukkitAdapter.adapt(blockType);
            if (mat.equals(Material.AIR)) return;
            if (!blocksChanges.containsKey(mat)) blocksChanges.put(mat, BigInteger.ZERO);
            blocksChanges.put(mat, blocksChanges.get(mat).add(BigInteger.ONE));
        }
    }
}
