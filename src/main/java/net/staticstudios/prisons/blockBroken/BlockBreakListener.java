package net.staticstudios.prisons.blockBroken;

import net.md_5.bungee.api.ChatColor;
import net.staticstudios.mines.minesapi.events.BlockBrokenInMineEvent;
import net.staticstudios.prisons.enchants.handler.BaseEnchant;
import net.staticstudios.prisons.enchants.handler.PrisonPickaxe;
import net.staticstudios.prisons.data.dataHandling.PlayerData;
import net.staticstudios.prisons.utils.Utils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.math.BigDecimal;
import java.math.BigInteger;

public class BlockBreakListener implements Listener {

    @EventHandler
    void onMineBlockBroken(BlockBrokenInMineEvent e) {
        e.getBlockBreakEvent().setDropItems(false);
        e.getBlockBreakEvent().setExpToDrop(0);
        Player player = e.getPlayer();
        PrisonPickaxe pickaxe = PrisonPickaxe.fromItem(player.getInventory().getItemInMainHand());
        if (pickaxe == null) return;
        PlayerData playerData = new PlayerData(player);


        PrisonBlockBroken bb = new PrisonBlockBroken(player, playerData, pickaxe, e.getMine(), e.getBlock());
        for (BaseEnchant enchant : pickaxe.getEnchants()) enchant.onBlockBreak(bb);
        //todo finish up the event using all multipliers
        long totalBlocksBroken = (long) (bb.blocksBroken * bb.blocksBrokenMultiplier);
        long tokensFound = (long) (bb.totalTokensGained * bb.tokenMultiplier);
        if (tokensFound > 0) {
            player.sendMessage(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "+ " + Utils.addCommasToNumber(tokensFound) + ChatColor.GRAY + ChatColor.ITALIC + " (Tokenator)");
            playerData.addTokens(BigInteger.valueOf(tokensFound));
        }
        pickaxe.addBlocksBroken(totalBlocksBroken);
        pickaxe.addRawBlocksBroken(1);
        pickaxe.addXp((long) (totalBlocksBroken * 2 * bb.xpMultiplier));
        e.getMine().removeBlocksBrokenInMine(bb.blocksBroken - 1);

        boolean backpackWasFull = playerData.getBackpackIsFull();
        //Put blocks broken in the player's backpack
        for (Material key : bb.blockTypesBroken.keySet()) playerData.addBackpackAmountOf(key, new BigDecimal(bb.blockTypesBroken.get(key)).multiply(BigDecimal.valueOf(bb.blocksBrokenMultiplier)).toBigInteger());

        backpackFullCheck(backpackWasFull, player, playerData);
    }

    public static void backpackFullCheck(boolean wasFullBefore, Player player, PlayerData playerData) {
        if (playerData.getBackpackIsFull()) {
            if (Utils.checkIfPlayerCanAutoSell(playerData) && playerData.getIsAutoSellEnabled()) {
                playerData.sellBackpack(player, true);
            } else if (!wasFullBefore) {
                if (playerData.getIsAutoSellEnabled() && !Utils.checkIfPlayerCanAutoSell(playerData)) playerData.setIsAutoSellEnabled(false);
                player.sendTitle(ChatColor.RED + "" + ChatColor.BOLD + "Your Backpack", ChatColor.RED + "" + ChatColor.BOLD + "Is Full! (" + Utils.prettyNum(playerData.getBackpackSize()) + "/" + Utils.prettyNum(playerData.getBackpackSize()) + ")", 5, 40, 5);
                player.sendMessage(ChatColor.RED + "Your backpack is full!");
            }
        }
    }

}
