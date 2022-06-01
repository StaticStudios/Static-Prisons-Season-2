package net.staticstudios.prisons.enchants;

import net.staticstudios.prisons.blockBroken.PrisonBlockBroken;
import net.staticstudios.prisons.enchants.handler.BaseEnchant;
import net.staticstudios.prisons.enchants.handler.PrisonPickaxe;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

import java.math.BigInteger;

public class FortuneEnchant extends BaseEnchant {
    public FortuneEnchant() {
        super("fortune", "&b&lFortune", 25000, BigInteger.valueOf(200), "&7Increase your blocks from mining");
    }

    public void onBlockBreak(PrisonBlockBroken bb) {
        bb.blocksBrokenMultiplier += bb.pickaxe.getEnchantLevel(ENCHANT_ID);
    }
}