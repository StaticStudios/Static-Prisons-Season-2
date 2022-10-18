package net.staticstudios.mines.utils;

import com.sk89q.worldedit.function.pattern.RandomPattern;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.world.block.BlockType;

import java.util.*;

public class StaticMineUtils {
    /**
     * @param collection The collection to filter.
     * @param currentArg The current argument.
     * @return The same collection, but with all elements that don't match the current argument removed.
     * Example: If the current argument is "ap", and the collection contains "apple", "button", and "application", the collection will be filtered to contain only "apple" and "application".
     */
    public static ArrayList<String> filterStrings(Collection<String> collection, String currentArg) {
        ArrayList<String> filtered = new ArrayList<>(collection);
        filtered.removeIf(s -> !s.toLowerCase().contains(currentArg.toLowerCase()));
        return filtered;
    }

    /**
     * @param loc1 The first location.
     * @param loc2 The second location.
     * @return The negative-most location of the two locations.
     */
    public static BlockVector3 getMinPoint(BlockVector3 loc1, BlockVector3 loc2) {
        double minX = Math.min(loc1.getX(), loc2.getX());
        double minY = Math.min(loc1.getY(), loc2.getY());
        double minZ = Math.min(loc1.getZ(), loc2.getZ());
        return BlockVector3.at(minX, minY, minZ);
    }

    /**
     * @param loc1 The first location.
     * @param loc2 The second location.
     * @return The positive-most location of the two locations.
     */
    public static BlockVector3 getMaxPoint(BlockVector3 loc1, BlockVector3 loc2) {
        double maxX = Math.max(loc1.getX(), loc2.getX());
        double maxY = Math.max(loc1.getY(), loc2.getY());
        double maxZ = Math.max(loc1.getZ(), loc2.getZ());
        return BlockVector3.at(maxX, maxY, maxZ);
    }

    /**
     * @param blocks The blocks as WeightedElements.
     * @return The blocks as a RandomPattern.
     */
    public static RandomPattern toRandomPattern(WeightedElements<BlockType> blocks) {
        RandomPattern pattern = new RandomPattern();
        for (WeightedElement<BlockType> block : blocks.getElements()) {
            pattern.add(block.element(), block.weight());
        }
        return pattern;
    }
}
