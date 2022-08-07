package net.staticstudios.prisons.gambling;

import org.bukkit.Material;
import org.bukkit.entity.Player;

public class Flip {
    public Player owner;
    public String uuid;
    public long amount;
    public boolean isHeads;
    public Material headsIcon;
    public Material tailsIcon;
    public long expireAt;

    public enum WhoWins {
        OWNER,
        CHALLENGER,
        HOUSE
    }
}
