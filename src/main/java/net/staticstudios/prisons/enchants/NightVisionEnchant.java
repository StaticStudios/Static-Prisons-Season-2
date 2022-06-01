package net.staticstudios.prisons.enchants;

import net.staticstudios.prisons.blockBroken.PrisonBlockBroken;
import net.staticstudios.prisons.enchants.handler.BaseEnchant;
import net.staticstudios.prisons.enchants.handler.PrisonPickaxe;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffectType;

import java.math.BigInteger;

public class NightVisionEnchant extends BaseEnchant {
    public NightVisionEnchant() {
        super("nightVision", "&9&lNight Vision", 1, BigInteger.valueOf(10000), "&7Gives the vanilla night vision effect");
    }

    public void onPickaxeHeld(Player player, PrisonPickaxe pickaxe) {
        player.addPotionEffect(PotionEffectType.NIGHT_VISION.createEffect(Integer.MAX_VALUE, Math.min(10, pickaxe.getEnchantLevel(ENCHANT_ID) - 1)));
    }
}