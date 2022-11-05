package net.staticstudios.prisons.pickaxe.enchants;

import net.staticstudios.prisons.enchants.Enchantable;
import net.staticstudios.prisons.pickaxe.enchants.handler.PickaxeEnchant;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

public class NightVisionEnchant extends PickaxeEnchant {

    public NightVisionEnchant() {
        super(NightVisionEnchant.class, "pickaxe-nightvision");
    }

    @Override
    public void onHold(Enchantable enchantable, Player player) {
        player.addPotionEffect(PotionEffectType.NIGHT_VISION.createEffect(Integer.MAX_VALUE, Math.min(10, enchantable.getEnchantmentLevel(NightVisionEnchant.class) - 1)));
    }

    @Override
    public void onUnHold(Enchantable enchantable, Player player) {
        player.removePotionEffect(PotionEffectType.NIGHT_VISION);
    }

    @Override
    public void onUpgrade(Enchantable enchantable, Player player, int oldLevel, int newLevel) {
        player.addPotionEffect(PotionEffectType.NIGHT_VISION.createEffect(Integer.MAX_VALUE, Math.min(10, newLevel - 1)));
    }
}
