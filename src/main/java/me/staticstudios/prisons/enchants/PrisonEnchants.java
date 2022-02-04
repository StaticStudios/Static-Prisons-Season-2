package me.staticstudios.prisons.enchants;

import java.math.BigInteger;

public class PrisonEnchants {
    public static final PrisonEnchant JACK_HAMMER = new PrisonEnchant("jackHammer", 50000, BigInteger.valueOf(1000), 20);
    public static final PrisonEnchant MULTI_DIRECTIONAL = new PrisonEnchant("multiDirectional",50000, BigInteger.valueOf(1500), 20);
    //public static final PrisonEnchant NUKE = new PrisonEnchant("nuke",1, BigInteger.valueOf(10000000000000L), 0);
    public static final PrisonEnchant FORTUNE = new PrisonEnchant("fortune", 100000, BigInteger.valueOf(500), 20);
    public static final PrisonEnchant REFINER = new PrisonEnchant("refiner", 10000, BigInteger.valueOf(5000), 50); //Chance to get 2x amount of blocks from fortune
    public static final PrisonEnchant TOKENATOR = new PrisonEnchant("tokenator", 1000, BigInteger.valueOf(150000), 50);
    public static final PrisonEnchant POLISHER = new PrisonEnchant("fortune", 100000, BigInteger.valueOf(500), 20); //Chance to get a 25% multiplier on top of all multipliers when selling
    public static final PrisonEnchant MERCHANT = new PrisonEnchant("merchant",25000, BigInteger.valueOf(1500), 20);
    public static final PrisonEnchant METAL_DETECTOR = new PrisonEnchant("metalDetector",10000, BigInteger.valueOf(5000), 25);
    public static final PrisonEnchant KEY_FINDER = new PrisonEnchant("keyFinder",10000, BigInteger.valueOf(5000), 25);



    public static final PrisonEnchant HASTE = new PrisonEnchant("haste",3, BigInteger.ONE, 0);
    public static final PrisonEnchant SPEED = new PrisonEnchant("speed",3, BigInteger.ONE, 0);
    public static final PrisonEnchant NIGHT_VISION = new PrisonEnchant("nightVision",1, BigInteger.ONE, 0);
}
