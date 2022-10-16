package net.staticstudios.prisons.pickaxe.enchants;

import net.staticstudios.prisons.enchants.Enchantable;
import net.staticstudios.prisons.pickaxe.enchants.handler.PickaxeEnchant;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

public class HasteEnchant extends PickaxeEnchant {

    public HasteEnchant() {
        super(HasteEnchant.class, "pickaxe-haste");
    }

    @Override
    public void onHold(Enchantable enchantable, Player player) {
        player.addPotionEffect(PotionEffectType.FAST_DIGGING.createEffect(Integer.MAX_VALUE, Math.min(10, enchantable.getEnchantmentLevel(HasteEnchant.class) - 1)));
    }

    @Override
    public void onUnHold(Enchantable enchantable, Player player) {
        player.removePotionEffect(PotionEffectType.FAST_DIGGING);
    }

    @Override
    public void onUpgrade(Enchantable enchantable, Player player, int oldLevel, int newLevel) {
        player.addPotionEffect(PotionEffectType.FAST_DIGGING.createEffect(Integer.MAX_VALUE, Math.min(10, enchantable.getEnchantmentLevel(HasteEnchant.class) - 1)));
    }
}
