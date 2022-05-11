package net.staticstudios.prisons.enchants;

import net.staticstudios.prisons.blockBroken.PrisonBlockBroken;
import net.staticstudios.prisons.enchants.handler.BaseEnchant;
import net.staticstudios.prisons.enchants.handler.PrisonPickaxe;
import net.staticstudios.prisons.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

import java.math.BigInteger;

public class DoubleFortuneEnchant extends BaseEnchant {
    public DoubleFortuneEnchant() {
        super("doubleFortune", "&d&lOre Splitter", 1000, BigInteger.valueOf(5000), "&7Chance to get 2x blocks from fortune");
    }


    @Override
    public void onBlockBreak(PrisonBlockBroken bb) {
        if (Utils.randomInt(0, MAX_LEVEL) < bb.pickaxe.getEnchantLevel(ENCHANT_ID))
        bb.blocksBrokenMultiplier *= 2;
    }

    @Override
    public void onPickaxeHeld(Player player, PrisonPickaxe pickaxe) {

    }

    @Override
    public void onPickaxeUnHeld(Player player, PrisonPickaxe pickaxe) {

    }

    @Override
    public void whileRightClicking(PlayerInteractEvent e, PrisonPickaxe pickaxe) {

    }
}
