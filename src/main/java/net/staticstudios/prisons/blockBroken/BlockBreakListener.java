package net.staticstudios.prisons.blockBroken;

import net.md_5.bungee.api.ChatColor;
import net.staticstudios.mines.minesapi.events.BlockBrokenInMineEvent;
import net.staticstudios.prisons.enchants.handler.BaseEnchant;
import net.staticstudios.prisons.enchants.handler.PrisonPickaxe;
import net.staticstudios.prisons.data.dataHandling.PlayerData;
import net.staticstudios.prisons.utils.BroadcastMessage;
import net.staticstudios.prisons.utils.Constants;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.math.BigDecimal;
import java.math.BigInteger;

public class BlockBreakListener implements Listener {

    @EventHandler
    void onMineBlockBroken(BlockBrokenInMineEvent e) {
        if (!e.getPlayer().getWorld().equals(Constants.MINES_WORLD)) return;
        e.getBlockBreakEvent().setDropItems(false);
        e.getBlockBreakEvent().setExpToDrop(0);
        Player player = e.getPlayer();
        PrisonPickaxe pickaxe = PrisonPickaxe.fromItem(player.getInventory().getItemInMainHand());
        if (pickaxe == null) return;


        PlayerData playerData = new PlayerData(player);
        PrisonBlockBroken bb = new PrisonBlockBroken(player, playerData, pickaxe, e.getMine(), e.getBlock());
        for (BaseEnchant enchant : pickaxe.getEnchants()) enchant.onBlockBreak(bb);
        //Event mine
        if (e.getMine().getID().equals("eventMine")) bb.tokenMultiplier += .2d;

        long totalBlocksBroken = (long) (bb.blocksBroken * bb.blocksBrokenMultiplier);
        long tokensFound = (long) (bb.totalTokensGained * bb.tokenMultiplier);


        if (tokensFound > 0) {
            player.sendMessage(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "+ " + PrisonUtils.addCommasToNumber(tokensFound) + ChatColor.GRAY + ChatColor.ITALIC + " (Tokenator)");
            playerData.addTokens(BigInteger.valueOf(tokensFound));
        }

        pickaxe.addBlocksBroken(totalBlocksBroken);
        pickaxe.addRawBlocksBroken(1);
        pickaxe.addXp((long) (totalBlocksBroken * 2 * bb.xpMultiplier));


        e.getMine().removeBlocksBrokenInMine(bb.blocksBroken - 1);

        boolean backpackWasFull = playerData.getBackpackIsFull();
        for (Material key : bb.blockTypesBroken.keySet()) playerData.addBackpackAmountOf(key, new BigDecimal(bb.blockTypesBroken.get(key)).multiply(BigDecimal.valueOf(bb.blocksBrokenMultiplier)).toBigInteger());
        backpackFullCheck(backpackWasFull, player, playerData);
    }

    public static void backpackFullCheck(boolean wasFullBefore, Player player, PlayerData playerData) {
        if (playerData.getBackpackIsFull()) {
            if (PrisonUtils.Players.canAutoSell(playerData) && playerData.getIsAutoSellEnabled()) {
                playerData.sellBackpack(player, true, ChatColor.LIGHT_PURPLE + "[Auto Sell] " + ChatColor.WHITE + "(x%MULTI%) Sold " + ChatColor.AQUA + "%TOTAL_BACKPACK_COUNT% " + ChatColor.WHITE + "blocks for: " + ChatColor.GREEN + "$%TOTAL_SELL_PRICE%");
            } else if (!wasFullBefore) {
                if (playerData.getIsAutoSellEnabled() && !PrisonUtils.Players.canAutoSell(playerData)) playerData.setIsAutoSellEnabled(false);
                player.sendTitle(ChatColor.RED + "" + ChatColor.BOLD + "Your Backpack", ChatColor.RED + "" + ChatColor.BOLD + "Is Full! (" + PrisonUtils.prettyNum(playerData.getBackpackSize()) + "/" + PrisonUtils.prettyNum(playerData.getBackpackSize()) + ")", 5, 40, 5);
                player.sendMessage(ChatColor.RED + "Your backpack is full!");
            }
        }
    }

}
