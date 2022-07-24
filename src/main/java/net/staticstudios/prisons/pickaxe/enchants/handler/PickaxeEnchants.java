package net.staticstudios.prisons.pickaxe.enchants.handler;

import net.staticstudios.prisons.blockBroken.BlockBreak;
import net.staticstudios.prisons.pickaxe.PrisonPickaxe;
import net.staticstudios.prisons.pickaxe.enchants.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PickaxeEnchants {
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
    public static BaseEnchant AUTO_SELL;
    public static BaseEnchant XP_FINDER;
    public static BaseEnchant BACKPACK_FINDER;
    public static BaseEnchant HASTE;
    public static BaseEnchant SPEED;
    public static BaseEnchant NIGHT_VISION;


    public static List<BaseEnchant> ORDERED_ENCHANTS = new ArrayList<>();
    public static Map<String, BaseEnchant> enchantIDToEnchant = new HashMap<>();

    public static void init() {
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
        AUTO_SELL = new AutoSellEnchant();
        XP_FINDER = new XPFinderEnchant();
        BACKPACK_FINDER = new BackpackFinderEnchant();

        HASTE = new HasteEnchant();
        SPEED = new SpeedEnchant();
        NIGHT_VISION = new NightVisionEnchant();

        ORDERED_ENCHANTS.add(FORTUNE);
        ORDERED_ENCHANTS.add(DOUBLE_FORTUNE);
        ORDERED_ENCHANTS.add(TOKENATOR);
        ORDERED_ENCHANTS.add(KEY_FINDER);
        ORDERED_ENCHANTS.add(METAL_DETECTOR);
        ORDERED_ENCHANTS.add(EXPLOSION);
        ORDERED_ENCHANTS.add(JACK_HAMMER);
        ORDERED_ENCHANTS.add(DOUBLE_JACK_HAMMER);
        ORDERED_ENCHANTS.add(MULTI_DIRECTIONAL);
        ORDERED_ENCHANTS.add(MERCHANT);
        ORDERED_ENCHANTS.add(CONSISTENCY);
        ORDERED_ENCHANTS.add(EGG_SHOOTER);
        ORDERED_ENCHANTS.add(AUTO_SELL);
        ORDERED_ENCHANTS.add(XP_FINDER);
        ORDERED_ENCHANTS.add(BACKPACK_FINDER);
        ORDERED_ENCHANTS.add(HASTE);
        ORDERED_ENCHANTS.add(SPEED);
        ORDERED_ENCHANTS.add(NIGHT_VISION);



        BlockBreak.addListener(blockBreak -> {
            PrisonPickaxe pickaxe = blockBreak.getPickaxe();
            if (pickaxe == null) return;
            boolean hasTokenator = false;
            for (BaseEnchant enchant : pickaxe.getEnchants()) {
                if (pickaxe.getIsEnchantEnabled(enchant)) {
                    enchant.onBlockBreak(blockBreak);
                }
                if (enchant.equals(TOKENATOR)) {
                    hasTokenator = true;
                }
            }
            if (!hasTokenator) {
                pickaxe.setEnchantsLevel(TOKENATOR, 1);
            }
        });

    }
}
