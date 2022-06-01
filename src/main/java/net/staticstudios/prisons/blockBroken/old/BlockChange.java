package net.staticstudios.prisons.blockBroken.old;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.time.Instant;

public class BlockChange {

    enum BlockChangeTypes {
        JACKHAMMER,
        MULTIDIRECTIONAL
    }

    static BlockChange[] que = new BlockChange[1024];
    static int amountOfItemsInQue = 0;
    static World currentWorld;
    static long startedAt;
    static final int MAX_MS_BETWEEN_TICK = 4;

    //1: mine refill, 2: jack hammer, 3: multi-directional
    BlockChangeTypes type;
    int currentIteration;
    int maxAmountOfIterations;
    World world;
    int startX;
    int startY;
    int startZ;
    int endX;
    int endY;
    int endZ;
    int blockX;
    int blockZ;
    boolean useSingleType;
    Material blockType;

    static void addToQue(BlockChange blockChange) {
        que[amountOfItemsInQue] = blockChange;
        amountOfItemsInQue++;
    }
    public static void addJackHammerBlockChange(World world, int startX, int yLevel, int startZ, int endX, int endZ) {
        BlockChange blockChange = new BlockChange();
        blockChange.world = world;
        blockChange.startX = startX;
        blockChange.startY = yLevel;
        blockChange.startZ = startZ;
        blockChange.endX = endX;
        blockChange.endZ = endZ;
        blockChange.blockType = Material.AIR;
        blockChange.useSingleType = true;
        blockChange.maxAmountOfIterations = (Math.abs(startX) + Math.abs(endX) + 1) * (Math.abs(startZ) + Math.abs(endZ) + 1);
        blockChange.type = BlockChangeTypes.JACKHAMMER;
        addToQue(blockChange);
    }
    public static void addMultiDirectionalBlockChange(World world, int minX, int startY, int minZ, int maxX, int endY, int maxZ, int blockX, int blockZ) {
        BlockChange blockChange = new BlockChange();
        blockChange.world = world;
        blockChange.startX = minX;
        blockChange.startY = startY;
        blockChange.startZ = minZ;
        blockChange.endX = maxX;
        blockChange.endY = startY;
        blockChange.endZ = maxZ;
        blockChange.blockX = blockX;
        blockChange.blockZ = blockZ;
        blockChange.blockType = Material.AIR;
        blockChange.useSingleType = true;
        blockChange.maxAmountOfIterations = (Math.abs(minX) + Math.abs(maxX) + Math.abs(minZ) + Math.abs(maxZ) + 1) * (startY - endY + 1);
        blockChange.type = BlockChangeTypes.MULTIDIRECTIONAL;
        addToQue(blockChange);
    }

    public static void worker() {
        startedAt = Instant.now().toEpochMilli();
        while (Instant.now().toEpochMilli() <= startedAt + MAX_MS_BETWEEN_TICK && amountOfItemsInQue != 0) {
                BlockChange blockChange = que[0];
                if (blockChange.currentIteration == 0) {
                    currentWorld = blockChange.world;
                }
                Material block = blockChange.blockType;
                switch (blockChange.type) {
                    case JACKHAMMER -> {
                        System.out.println(blockChange.currentIteration + " | " + blockChange.maxAmountOfIterations);
                        for (int t = 0; t < 5; t++) {
                            //Loop through the X direction t amount of times before checking if it can no longer run due to TPS limits
                            int amountOfIterationsInTheX = Math.abs(blockChange.startX) + Math.abs(blockChange.endX) + 1; //add 1 because it crosses 0
                            int amountOfIterationsInTheZ = Math.abs(blockChange.startZ) + Math.abs(blockChange.endZ) + 1; //add 1 because it crosses 0
                            int z = blockChange.startZ + blockChange.currentIteration / amountOfIterationsInTheX % amountOfIterationsInTheZ;
                            int y = blockChange.startY;
                            for (int x = blockChange.startX; x < blockChange.endX + 1; x++) {
                                place(x, y, z, block);
                                blockChange.currentIteration++;
                                if (blockChange.currentIteration == blockChange.maxAmountOfIterations) break;
                            }
                            if (blockChange.currentIteration == blockChange.maxAmountOfIterations) break;
                        }
                    }
                    case MULTIDIRECTIONAL -> {
                        for (int t = 0; t < 7; t++) {
                            //Do 7 layers before checking if it can no longer run due to TPS limits
                            int y = blockChange.startY - blockChange.currentIteration / (Math.abs(blockChange.startX) + Math.abs(blockChange.endX) + Math.abs(blockChange.startZ) + Math.abs(blockChange.endZ) + 1);
                            int x;
                            int z;
                            z = blockChange.blockZ;
                            for (x = blockChange.startX; x < blockChange.endX + 1; x++) {
                                place(x, y, z, block);
                                blockChange.currentIteration++;
                            }
                            x = blockChange.blockX;
                            for (z = blockChange.startZ; z < blockChange.endZ + 1; z++) {
                                if (z == blockChange.blockZ) continue; //We already got the 0 pos with the x loop
                                place(x, y, z, block);
                                blockChange.currentIteration++;
                                if (blockChange.currentIteration == blockChange.maxAmountOfIterations) break;
                            }
                            if (blockChange.currentIteration == blockChange.maxAmountOfIterations) break;
                        }
                    }
                }
                if (blockChange.currentIteration == blockChange.maxAmountOfIterations) {
                    if (amountOfItemsInQue - 1 >= 0) System.arraycopy(que, 1, que, 0, amountOfItemsInQue - 1);
                    que[amountOfItemsInQue - 1] = null;
                    amountOfItemsInQue--;
                }
        }
    }

    static void place(int x, int y, int z, Material blockType) {
        Block block = new Location(currentWorld, x, y, z).getBlock();
        if (!block.getType().equals(blockType)) {
            block.setType(blockType, false);
        }
    }
}
