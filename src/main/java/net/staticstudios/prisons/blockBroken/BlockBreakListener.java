package net.staticstudios.prisons.blockBroken;

import net.md_5.bungee.api.ChatColor;
import net.staticstudios.mines.minesapi.events.BlockBrokenInMineEvent;
import net.staticstudios.prisons.enchants.handler.BaseEnchant;
import net.staticstudios.prisons.enchants.handler.PrisonEnchants;
import net.staticstudios.prisons.enchants.handler.PrisonPickaxe;
import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.privateMines.PrivateMine;
import net.staticstudios.prisons.utils.Constants;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.math.BigInteger;

public class BlockBreakListener implements Listener {

    @EventHandler
    void onMineBlockBroken(BlockBrokenInMineEvent e) {
        if (!e.getPlayer().getWorld().equals(Constants.MINES_WORLD) && !e.getPlayer().getWorld().equals(PrivateMine.PRIVATE_MINES_WORLD)) return;
        e.getBlockBreakEvent().setDropItems(false);
        e.getBlockBreakEvent().setExpToDrop(0);
        Player player = e.getPlayer();
        PrisonPickaxe pickaxe = PrisonPickaxe.fromItem(player.getInventory().getItemInMainHand());
        if (pickaxe == null) return;

        PlayerData playerData = new PlayerData(player);
        PrisonBlockBroken bb = new PrisonBlockBroken(player, playerData, pickaxe, e.getMine(), e.getBlock());

        //Ensure all pickaxes have tokenator
        boolean hasTokenator = false;
        for (BaseEnchant enchant : pickaxe.getEnchants()) {
            if (enchant.equals(PrisonEnchants.TOKENATOR)) hasTokenator = true;
            if (!pickaxe.getIsEnchantEnabled(enchant)) continue;
            enchant.onBlockBreak(bb);
        }
        if (!hasTokenator) { //The pickaxe did not have tokenator, so it was added and the enchant was called
            pickaxe.setEnchantsLevel(PrisonEnchants.TOKENATOR, 1);
            PrisonEnchants.TOKENATOR.onBlockBreak(bb);
        }


        //Event mine
        if (e.getMine().getID().equals("eventMine")) bb.tokenMultiplier += .2d;

        long tokensFound = (long) (bb.totalTokensGained * bb.tokenMultiplier);

        if (!e.hasRunOnProcessEvent()) bb.applyMoneyMulti();
        else e.runOnProcessEvent(bb);

        if (tokensFound > 0) {
            //player.sendMessage(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "+ " + PrisonUtils.addCommasToNumber(tokensFound) + " Tokens" + ChatColor.GRAY + ChatColor.ITALIC + " (Tokenator)");
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', PrisonEnchants.TOKENATOR.DISPLAY_NAME + " &8&l>> &fFound " + PrisonUtils.addCommasToNumber(tokensFound) + " tokens!"));
            playerData.addTokens(BigInteger.valueOf(tokensFound));
        }

        pickaxe.addBlocksBroken(bb.amountOfBlocksBroken);
        pickaxe.addRawBlocksBroken(1);
        playerData.addBlocksMined(BigInteger.valueOf(bb.amountOfBlocksBroken));
        playerData.addRawBlocksMined(BigInteger.ONE);
        pickaxe.addXp((long) (bb.amountOfBlocksBroken * 2 * bb.xpMultiplier));


        e.getMine().removeBlocksBrokenInMine(bb.amountOfBlocksBroken - 1);

        boolean backpackWasFull = playerData.getBackpackIsFull();
        playerData.addAllToBackpack(bb.blocksBroken);
        backpackFullCheck(backpackWasFull, player, playerData);
    }

    public static void backpackFullCheck(boolean wasFullBefore, Player player, PlayerData playerData) {
        if (playerData.getBackpackIsFull()) {
            if (PrisonUtils.Players.canAutoSell(playerData) && playerData.getIsAutoSellEnabled()) {
                playerData.sellBackpack(player, true, ChatColor.translateAlternateColorCodes('&', "&a&lAuto Sell &8&l>> &f(x%MULTI%) Sold &b%TOTAL_BACKPACK_COUNT% &fblocks for: &a$%TOTAL_SELL_PRICE%"));
            } else if (!wasFullBefore) {
                if (playerData.getIsAutoSellEnabled() && !PrisonUtils.Players.canAutoSell(playerData)) playerData.setIsAutoSellEnabled(false);
                player.sendTitle(ChatColor.RED + "" + ChatColor.BOLD + "Your Backpack", ChatColor.RED + "" + ChatColor.BOLD + "Is Full! (" + PrisonUtils.prettyNum(playerData.getBackpackSize()) + "/" + PrisonUtils.prettyNum(playerData.getBackpackSize()) + ")", 5, 40, 5);
                player.sendMessage(ChatColor.RED + "Your backpack is full!");
            }
        }
    }

}
