package me.staticstudios.prisons.mines;

import org.bukkit.Location;

import java.util.UUID;

public class PrivateMine extends BaseMine {

    public PrivateMine(UUID mineUUID, Location loc1, Location loc2) {
        super("privateMine-" + mineUUID.toString(), loc1, loc2);
    }






}
