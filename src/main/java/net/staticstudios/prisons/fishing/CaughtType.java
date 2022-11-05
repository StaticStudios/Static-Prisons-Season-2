package net.staticstudios.prisons.fishing;

import org.bukkit.Material;

public enum CaughtType {
    PLAYER_XP(Material.EXPERIENCE_BOTTLE),
    TOKENS(Material.SUNFLOWER),
    SHARDS(Material.PRISMARINE_SHARD),
    ITEM(Material.STICK),

    NOTHING(Material.DEAD_BUSH);


    final Material displayItem;


    CaughtType(Material displayItem) {
        this.displayItem = displayItem;
    }

    public Material getDisplayItem() {
        return displayItem;
    }
}
