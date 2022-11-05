package net.staticstudios.prisons.pickaxe.enchants.handler;

import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.enchants.EnchantableItemStack;
import net.staticstudios.prisons.pickaxe.PrisonPickaxe;
import net.staticstudios.prisons.pickaxe.enchants.*;
import org.bukkit.NamespacedKey;

public class PickaxeEnchants {

    public static void init() {
        EnchantableItemStack.setNamespacedKey(PrisonPickaxe.class, new NamespacedKey(StaticPrisons.getInstance(), "prisonPickaxe"));

        new FortuneEnchant();
        new OreSplitterEnchant();
        new TokenatorEnchant();
        new KeyFinderEnchant();
        new MetalDetectorEnchant();
        new ExplosionEnchant();
        new JackHammerEnchant();
        new DoubleWammyEnchant();
        new MultiDirectionalEnchant();
        new MerchantEnchant();
        new ConsistencyEnchant();
        new EggShooterEnchant();
        new AutoSellEnchant();
        new XPFinderEnchant();
        new BackpackFinderEnchant();
        new NightVisionEnchant();
        new HasteEnchant();
        new SpeedEnchant();
    }
}
