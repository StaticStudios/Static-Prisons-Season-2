package net.staticstudios.prisons.mines.old;

import org.bukkit.Material;

public class MineBlock {
    public double chance;
    public Material type;
    public MineBlock(Material type, double chance) {
        this.type = type;
        this.chance = chance;
    }
}
