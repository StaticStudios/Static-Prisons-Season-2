package net.staticstudios.prisons.pickaxe;

import net.staticstudios.prisons.blockbreak.BlockBreak;
import net.staticstudios.prisons.blockbreak.BlockBreakProcessEvent;
import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.pickaxe.abilities.handler.BaseAbility;
import net.staticstudios.prisons.pickaxe.enchants.handler.BaseEnchant;
import net.staticstudios.prisons.pickaxe.enchants.handler.PickaxeEnchants;
import net.staticstudios.prisons.pickaxe.gui.PickaxeMenus;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import java.util.HashSet;
import java.util.Objects;

public class PickaxeListener implements Listener {
    @EventHandler
    void onInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if (Objects.equals(e.getHand(), EquipmentSlot.OFF_HAND)) return;
        //Check if the player is holding a pickaxe and is trying to open the enchants menu
        if (e.getAction().isRightClick()) {
            if (player.isSneaking()) {
                if (PrisonPickaxe.checkIsPrisonPickaxe(player.getInventory().getItemInMainHand())) {
                    if (new PlayerData(player).getIsMobile()) return;
                    PickaxeMenus.open(player, PrisonPickaxe.fromItem(player.getInventory().getItemInMainHand()));
                    e.setCancelled(true);
                }
            }
        }
    }
    @EventHandler
    void onBlockBreakProcessAbilities(BlockBreakProcessEvent e) {
        BlockBreak blockBreak = e.getBlockBreak();
        PrisonPickaxe pickaxe = blockBreak.getPickaxe();
        Player player = blockBreak.getPlayer();
        if (pickaxe == null || player == null) return;
        for (BaseAbility.AbilityHolder ability : BaseAbility.pickaxeAbilities.getOrDefault(pickaxe, new HashSet<>())) {
            ability.getAbility().onBlockBreak(blockBreak);
        }
    }
    @EventHandler
    void onBlockBreakProcessEnchants(BlockBreakProcessEvent e) {
        BlockBreak blockBreak = e.getBlockBreak();
        PrisonPickaxe pickaxe = blockBreak.getPickaxe();
        if (pickaxe == null) return;
        boolean hasTokenator = false;
        for (BaseEnchant enchant : pickaxe.getEnchants()) {
            if (pickaxe.getIsEnchantEnabled(enchant)) {
                enchant.onBlockBreak(blockBreak);
            }
            if (enchant.equals(PickaxeEnchants.TOKENATOR)) {
                hasTokenator = true;
            }
        }
        if (!hasTokenator) {
            pickaxe.setEnchantsLevel(PickaxeEnchants.TOKENATOR, 1);
        }
    }
}
