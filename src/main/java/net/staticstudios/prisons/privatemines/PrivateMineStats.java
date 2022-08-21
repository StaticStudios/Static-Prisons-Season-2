package net.staticstudios.prisons.privatemines;

import com.sk89q.worldedit.extent.clipboard.Clipboard;
import net.staticstudios.prisons.mines.MineBlock;
import net.staticstudios.mines.utils.WeightedElements;

public class PrivateMineStats {
    WeightedElements<MineBlock> blocks;
    Clipboard schematic;
    int size;
    int worldborderOffsetX;
    int worldborderOffsetZ;
    int worldborderSize;
    long upgradeCost;

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

    public long getUpgradeCost() {
        return upgradeCost;
    }

    public boolean equals(PrivateMineStats other) {
        return blocks.equals(other.blocks) && schematic.equals(other.schematic) && size == other.size;
    }
}
