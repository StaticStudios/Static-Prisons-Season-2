package me.staticstudios.prisons.utils;

import org.bukkit.World;

import java.util.UUID;

public class LocationHolder {
    double x;
    double y;
    double z;
    UUID worldUUID;
    public LocationHolder(World world, double x, double y, double z) {
        this.worldUUID = world.getUID();
        this.x = x;
        this.y = y;
        this.z = z;
    }
}
