package net.staticstudios.prisons.pickaxe.enchants;

import net.staticstudios.prisons.pickaxe.PrisonPickaxe;
import net.staticstudios.prisons.pickaxe.enchants.handler.BaseEnchant;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import java.math.BigInteger;

public class NightVisionEnchant extends BaseEnchant {
    public NightVisionEnchant() {
        super("nightVision", "&9&lNight Vision", 1, BigInteger.valueOf(10000), "&7Gives the vanilla night vision effect");
    }

    public void onUpgrade(Player player, PrisonPickaxe pickaxe, int oldLevel, int newLevel) {
        player.addPotionEffect(PotionEffectType.NIGHT_VISION.createEffect(Integer.MAX_VALUE, Math.min(10, pickaxe.getEnchantLevel(ENCHANT_ID) - 1)));
    }
    public void onPickaxeHeld(Player player, PrisonPickaxe pickaxe) {
        player.addPotionEffect(PotionEffectType.NIGHT_VISION.createEffect(Integer.MAX_VALUE, Math.min(10, pickaxe.getEnchantLevel(ENCHANT_ID) - 1)));
    }
    public void onPickaxeUnHeld(Player player, PrisonPickaxe pickaxe) {
        player.removePotionEffect(PotionEffectType.NIGHT_VISION);
    }
}
