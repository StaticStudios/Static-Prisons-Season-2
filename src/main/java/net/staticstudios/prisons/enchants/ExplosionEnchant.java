package net.staticstudios.prisons.enchants;

import net.staticstudios.prisons.blockBroken.PrisonBlockBroken;
import net.staticstudios.prisons.enchants.handler.BaseEnchant;
import net.staticstudios.prisons.enchants.handler.PrisonPickaxe;
import net.staticstudios.prisons.mineBombs.MineBomb;
import net.staticstudios.prisons.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

import java.math.BigInteger;

public class ExplosionEnchant extends BaseEnchant {
    public ExplosionEnchant() {
        super("explosion", "&c&lExplosion", 5000, BigInteger.valueOf(450), "&7Change to explode part of a mine");
    }


    @Override
    public void onBlockBreak(PrisonBlockBroken bb) {
        int explosionLevel = bb.pickaxe.getEnchantLevel(ENCHANT_ID);
        if (Utils.randomInt(1, 4500 - (int) (explosionLevel / 3.75)) != 1) return;
        int radius = 5 + explosionLevel / 1500;
        radius += Utils.randomDouble(0, 0.4) * radius;
        MineBomb bomb = new MineBomb(bb.blockLocation, radius);
        bb.blockTypesBroken.putAll(bomb.explode(bb.mine));
        bb.blocksBroken += bomb.blocksChanged - 1;
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
