package me.staticstudios.prisons.enchants.handler;

import me.staticstudios.prisons.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class EnchantEffects {

    public static void giveEffects() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            giveEffect(p, p.getInventory().getItemInMainHand());
        }
    }
    public static void giveEffect(Player player, ItemStack pickaxe) {
        player.removePotionEffect(PotionEffectType.SPEED);
        player.removePotionEffect(PotionEffectType.FAST_DIGGING);
        player.removePotionEffect(PotionEffectType.NIGHT_VISION);
        if (!Utils.checkIsPrisonPickaxe(pickaxe)) return;
        if (CustomEnchants.getEnchantLevel(pickaxe, "speed") > 0) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 9999999 * 20, (int) CustomEnchants.getEnchantLevel(pickaxe, "speed") - 1, true));
        }
        if (CustomEnchants.getEnchantLevel(pickaxe, "haste") > 0) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 9999999 * 20, (int) Math.min(CustomEnchants.getEnchantLevel(pickaxe, "haste") - 1, 10), true));
        }
        if (CustomEnchants.getEnchantLevel(pickaxe, "nightVision") > 0) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 9999999 * 20, (int) CustomEnchants.getEnchantLevel(pickaxe, "nightVision") - 1, true));
        }
    }
}
