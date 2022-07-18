package net.staticstudios.prisons.privateMines;

import com.sk89q.worldedit.extent.clipboard.Clipboard;
import net.staticstudios.prisons.mines.MineBlock;
import net.staticstudios.utils.WeightedElements;

import java.math.BigInteger;

public class PrivateMineStats {
    WeightedElements<MineBlock> blocks;
    Clipboard schematic;
    int size;
    int worldborderOffsetX;
    int worldborderOffsetZ;
    int worldborderSize;
    BigInteger upgradeCost;

    public WeightedElements<MineBlock> getBlocks() {
        return blocks;
    }
    public Clipboard getSchematic() {
        return schematic;
    }
    public int getSize() {
        return size;
    }
    public int getWorldborderOffsetX() {
        return worldborderOffsetX;
    }
    public int getWorldborderOffsetZ() {
        return worldborderOffsetZ;
    }
    public int getWorldborderSize() {
        return worldborderSize;
    }

    public BigInteger getUpgradeCost() {
        return upgradeCost;
    }

    public boolean equals(PrivateMineStats other) {
        return blocks.equals(other.blocks) && schematic.equals(other.schematic) && size == other.size;
    }
}
