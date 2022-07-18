package net.staticstudios.prisons.pickaxe.enchants;

import net.staticstudios.prisons.pickaxe.enchants.handler.BaseEnchant;
import net.staticstudios.prisons.pickaxe.PrisonPickaxe;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import java.math.BigInteger;

public class SpeedEnchant extends BaseEnchant {
    public SpeedEnchant() {
        super("speed", "&b&lSpeed", 3, BigInteger.valueOf(1000), "&7Gives the vanilla speed effect");
    }


    public void onUpgrade(Player player, PrisonPickaxe pickaxe, int oldLevel, int newLevel) {
        player.addPotionEffect(PotionEffectType.SPEED.createEffect(Integer.MAX_VALUE, Math.min(10, pickaxe.getEnchantLevel(ENCHANT_ID) - 1)));
    }
    public void onPickaxeHeld(Player player, PrisonPickaxe pickaxe) {
        player.addPotionEffect(PotionEffectType.SPEED.createEffect(Integer.MAX_VALUE, Math.min(10, pickaxe.getEnchantLevel(ENCHANT_ID) - 1)));
    }
    public void onPickaxeUnHeld(Player player, PrisonPickaxe pickaxe) {
        player.removePotionEffect(PotionEffectType.SPEED);
    }
}