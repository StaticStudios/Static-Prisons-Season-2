package net.staticstudios.prisons.pickaxe.newenchants.handler;

import net.staticstudios.prisons.pickaxe.newenchants.AutoSellEnchant;
import net.staticstudios.prisons.pickaxe.newenchants.BackpackFinderEnchant;
import net.staticstudios.prisons.pickaxe.newenchants.FortuneEnchant;
import net.staticstudios.prisons.pickaxe.newenchants.TokenatorEnchant;

public class PickaxeEnchants {

    public static void init() {
        new FortuneEnchant();
        new TokenatorEnchant();
        new AutoSellEnchant();
        new BackpackFinderEnchant();
    }
}
