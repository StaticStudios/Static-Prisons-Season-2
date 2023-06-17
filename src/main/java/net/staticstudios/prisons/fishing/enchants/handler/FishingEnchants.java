package net.staticstudios.prisons.fishing.enchants.handler;

import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.enchants.EnchantableItemStack;
import net.staticstudios.prisons.fishing.PrisonFishingRod;
import net.staticstudios.prisons.fishing.enchants.ExampleEnchant;
import org.bukkit.NamespacedKey;

public class FishingEnchants {

    public static void init() {
        EnchantableItemStack.setNamespacedKey(PrisonFishingRod.class, new NamespacedKey(StaticPrisons.getInstance(), "fishingRod"));

        new ExampleEnchant();
    }
}
