package net.staticstudios.prisons.privateMines;

import com.sk89q.worldedit.extent.clipboard.Clipboard;
import net.staticstudios.prisons.mines.MineBlock;
import net.staticstudios.utils.WeightedElements;

import java.math.BigInteger;

public class PrivateMineStats {
    WeightedElements<MineBlock> blocks;
    Clipboard schematic;
    int size;
    BigInteger upgradeCost;
}
