package me.staticstudios.prisons.gameplay.gambling;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.math.BigInteger;


public class Flip {
    public Player owner;
    public String uuid;
    public BigInteger amount;
    public boolean isHeads;
    public Material headsIcon;
    public Material tailsIcon;
    public long expireAt;
}
