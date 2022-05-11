package net.staticstudios.prisons.enchants.handler;

import net.staticstudios.prisons.enchants.*;

import java.util.HashMap;
import java.util.Map;

public class PrisonEnchants {
    public static BaseEnchant FORTUNE;
    public static BaseEnchant DOUBLE_FORTUNE;
    public static BaseEnchant TOKENATOR;
    public static BaseEnchant KEY_FINDER;
    public static BaseEnchant METAL_DETECTOR;
    public static BaseEnchant EXPLOSION;
    public static BaseEnchant JACK_HAMMER;
    public static BaseEnchant DOUBLE_JACK_HAMMER;
    public static BaseEnchant MULTI_DIRECTIONAL;
    public static BaseEnchant MERCHANT;
    public static BaseEnchant CONSISTENCY;
    public static BaseEnchant EGG_SHOOTER;
    public static BaseEnchant HASTE;
    public static BaseEnchant SPEED;
    public static BaseEnchant NIGHT_VISION;


    public static Map<String, BaseEnchant> enchantIDToEnchant = new HashMap<>();

    public static void createEnchants() {
        FORTUNE = new FortuneEnchant();
        DOUBLE_FORTUNE = new DoubleFortuneEnchant();
        TOKENATOR = new TokenatorEnchant();
        KEY_FINDER = new KeyFinderEnchant();
        METAL_DETECTOR = new SpecialFinderEnchant();
        EXPLOSION = new ExplosionEnchant();
        JACK_HAMMER = new JackHammerEnchant();
        DOUBLE_JACK_HAMMER = new DoubleJackHammerEnchant();
        MULTI_DIRECTIONAL = new MultiDirectionalEnchant();
        MERCHANT = new MerchantEnchant();
        CONSISTENCY = new ConsistencyEnchant();
        EGG_SHOOTER = new EggShooterEnchant();

        HASTE = new HasteEnchant();
        SPEED = new SpeedEnchant();
        NIGHT_VISION = new NightVisionEnchant();
    }
}
