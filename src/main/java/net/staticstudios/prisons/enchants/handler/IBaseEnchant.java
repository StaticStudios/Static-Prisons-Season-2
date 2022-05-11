package net.staticstudios.prisons.enchants.handler;

import net.staticstudios.prisons.blockBroken.PrisonBlockBroken;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

public interface IBaseEnchant {

    void onBlockBreak(PrisonBlockBroken bb);
    void onPickaxeHeld(Player player, PrisonPickaxe pickaxe);
    void onPickaxeUnHeld(Player player, PrisonPickaxe pickaxe);
    void whileRightClicking(PlayerInteractEvent e, PrisonPickaxe pickaxe);

}
