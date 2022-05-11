package net.staticstudios.prisons.enchants;

import net.staticstudios.prisons.blockBroken.PrisonBlockBroken;
import net.staticstudios.prisons.enchants.handler.BaseEnchant;
import net.staticstudios.prisons.enchants.handler.PrisonPickaxe;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

import java.math.BigInteger;

public class DoubleJackHammerEnchant extends BaseEnchant {
    public DoubleJackHammerEnchant() {
        super("doubleJackhammer", "&6&lDouble Wammy", 1000, BigInteger.valueOf(7500), "&7Chance to destroy an additional mine layer", "Activates along side Jack Hammer");
    }


    @Override
    public void onBlockBreak(PrisonBlockBroken bb) {
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
