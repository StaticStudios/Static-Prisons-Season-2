package net.staticstudios.prisons.pickaxe.enchants;

import net.staticstudios.prisons.enchants.Enchantable;
import net.staticstudios.prisons.pickaxe.enchants.handler.PickaxeEnchant;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

public class SpeedEnchant extends PickaxeEnchant {

    public SpeedEnchant() {
        super(SpeedEnchant.class, "pickaxe-speed");
    }

    @Override
    public void onHold(Enchantable enchantable, Player player) {
        player.addPotionEffect(PotionEffectType.SPEED.createEffect(Integer.MAX_VALUE, Math.min(10, enchantable.getEnchantmentLevel(SpeedEnchant.class) - 1)));
    }

    @Override
    public void onUnHold(Enchantable enchantable, Player player) {
        player.removePotionEffect(PotionEffectType.SPEED);
    }

    @Override
    public void onUpgrade(Enchantable enchantable, Player player, int oldLevel, int newLevel) {
        System.out.println("oldLevel: " + oldLevel);
        player.addPotionEffect(PotionEffectType.SPEED.createEffect(Integer.MAX_VALUE, Math.min(10, newLevel - 1)));
    }
}
