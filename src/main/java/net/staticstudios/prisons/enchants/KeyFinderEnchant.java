package net.staticstudios.prisons.enchants;

import net.staticstudios.prisons.blockBroken.PrisonBlockBroken;
import net.staticstudios.prisons.customItems.CustomItems;
import net.staticstudios.prisons.enchants.handler.BaseEnchant;
import net.staticstudios.prisons.enchants.handler.PrisonPickaxe;
import net.staticstudios.prisons.utils.Utils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.math.BigInteger;

public class KeyFinderEnchant extends BaseEnchant {
    public KeyFinderEnchant() {
        super("keyFinder", "&d&lKey Finder", 5000, BigInteger.valueOf(400), "&7Find crate keys while mining");
    }


    @Override
    public void onBlockBreak(PrisonBlockBroken bb) {
        if (Utils.randomInt(0, 2500) != 1) return;
        int keyFinderLevel = bb.pickaxe.getEnchantLevel(ENCHANT_ID);
        if (Utils.randomInt(1, MAX_LEVEL + MAX_LEVEL / 10) <= keyFinderLevel + MAX_LEVEL / 10) {
            ItemStack reward;
            int randomReward = Utils.randomInt(1, 100);
            if (randomReward <= 15) {
                reward = CustomItems.getCommonCrateKey(2);
            } else if (randomReward <= 45) {
                reward = CustomItems.getRareCrateKey(1);
            } else if (randomReward <= 90) {
                reward = CustomItems.getEpicCrateKey(1);
            } else if (randomReward <= 95) {
                reward = CustomItems.getLegendaryCrateKey(1);
            } else if (randomReward <= 99) {
                reward = CustomItems.getStaticCrateKey(1);
            } else reward = CustomItems.getStaticpCrateKey(1);
            bb.player.sendMessage(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "[Key Finder] " + ChatColor.WHITE + "You found " + reward.getAmount() + "x " + Utils.getPrettyItemName(reward) + ChatColor.WHITE + " while mining!");
            Utils.Players.addToInventory(bb.player, reward);
        }
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
