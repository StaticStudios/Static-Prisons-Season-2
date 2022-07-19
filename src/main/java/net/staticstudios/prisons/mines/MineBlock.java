package net.staticstudios.prisons.mines;

import org.bukkit.Material;

import java.util.HashMap;
import java.util.Map;

public record MineBlock(Material material, long value) {

    private static final Map<Material, MineBlock> mineBlocks = new HashMap<>();

    static {
        new MineBlock(Material.AIR, 0);
    }

    public MineBlock(Material material, long value) {
        this.material = material;
        this.value = value;
        mineBlocks.put(material, this);
    }

    public static MineBlock fromMaterial(Material material) {
        return mineBlocks.get(material);
    }
}
