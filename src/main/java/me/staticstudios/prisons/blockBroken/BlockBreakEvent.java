package me.staticstudios.prisons.blockBroken;

import me.staticstudios.prisons.customItems.CustomItems;
import me.staticstudios.prisons.data.serverData.PlayerData;
import me.staticstudios.prisons.enchants.CustomEnchants;
import me.staticstudios.prisons.enchants.PrisonEnchants;
import me.staticstudios.prisons.mines.BaseMine;
import me.staticstudios.prisons.mines.MineManager;
import me.staticstudios.prisons.utils.PrisonPickaxe;
import me.staticstudios.prisons.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class BlockBreakEvent implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    void onBlockBreak(org.bukkit.event.block.BlockBreakEvent e) {
        Player player = e.getPlayer();
        //Check if the block broken was in a prison mine
        if (!player.getWorld().getName().equals("mines")) return;


        //Check if it is in the mine area
        BaseMine mine = MineManager.allMines.get(MineManager.getMineIDFromLocation(player.getLocation()));
        if (!(e.getBlock().getX() >= mine.minLocation.getX() && e.getBlock().getX() <= mine.maxLocation.getX() &&
                e.getBlock().getZ() >= mine.minLocation.getZ() && e.getBlock().getZ() <= mine.maxLocation.getZ() &&
                e.getBlock().getY() >= mine.minLocation.getY() && e.getBlock().getY() <= mine.maxLocation.getY()))
            return;
        e.setDropItems(false);
        e.setExpToDrop(0);

        PlayerData playerData = new PlayerData(player);

        //Reduce the block count in the mine, used to auto refill it
        mine.brokeBlocksInMine(1);

        //Get what item was used to break the block
        ItemStack pickaxe = player.getInventory().getItemInMainHand();
        //Verify that it is a prison pickaxe
        if (!Utils.checkIsPrisonPickaxe(pickaxe)) return;

        //Add a raw block mines to player data
        playerData.addRawBlocksMined(BigInteger.ONE);

        //Create a map of all blocks broken for this event
        Map<Material, BigInteger> blocksBroken = new HashMap<>();
        blocksBroken.put(e.getBlock().getType(), BigInteger.ONE);


        int jackHammerLevel = (int) CustomEnchants.getEnchantLevel(pickaxe, "jackHammer");
        int doubleWammyLevel = (int) CustomEnchants.getEnchantLevel(pickaxe, "doubleWammy");
        int multiDirectionalLevel = (int) CustomEnchants.getEnchantLevel(pickaxe, "multiDirectional");
        int keyFinderLevel = (int) CustomEnchants.getEnchantLevel(pickaxe, "keyFinder");
        boolean doubleFortune = false;
        if (CustomEnchants.getEnchantLevel(pickaxe, "oreSplitter") > 0)
            doubleFortune = CustomEnchants.getEnchantLevel(pickaxe, "oreSplitter") <= Utils.randomInt(1, PrisonEnchants.ORE_SPLITTER.MAX_LEVEL);
        if (jackHammerLevel > 0) {
            //Jack Hammer
            if (Utils.randomInt(1, 12) == 1) {
                if (Utils.randomInt(1, PrisonEnchants.JACK_HAMMER.MAX_LEVEL + PrisonEnchants.JACK_HAMMER.MAX_LEVEL / 100) <= jackHammerLevel + PrisonEnchants.JACK_HAMMER.MAX_LEVEL / 100) {
                    //Enchant should activate
                    int howDeepToGo = 1;
                    if (Utils.randomInt(1, PrisonEnchants.DOUBLE_WAMMY.MAX_LEVEL + PrisonEnchants.DOUBLE_WAMMY.MAX_LEVEL / 100) <= doubleWammyLevel + PrisonEnchants.DOUBLE_WAMMY.MAX_LEVEL / 100)
                        howDeepToGo += 1;
                    blocksBroken.putAll(new JackHammer(mine, e.getBlock().getY()).destroyLayer(e.getBlock().getY(), howDeepToGo));
                }
            }
        }
        if (multiDirectionalLevel > 0) {
            //Multi-directional
            if (Utils.randomInt(1, 18) == 1) {
                if (Utils.randomInt(1, PrisonEnchants.MULTI_DIRECTIONAL.MAX_LEVEL + PrisonEnchants.MULTI_DIRECTIONAL.MAX_LEVEL / 100) <= multiDirectionalLevel + PrisonEnchants.MULTI_DIRECTIONAL.MAX_LEVEL / 100) {
                    //Enchant should activate
                    int howDeepToGo = Math.max(1, e.getBlock().getY() - multiDirectionalLevel * 250 / PrisonEnchants.MULTI_DIRECTIONAL.MAX_LEVEL);
                    blocksBroken.putAll(new MultiDirectional(mine, e.getBlock().getLocation()).destroySection(e.getBlock().getY(), howDeepToGo, e.getBlock().getX(), e.getBlock().getZ()));
                }
            }
        }
        //Key finder
        if (keyFinderLevel > 0) {
            if (Utils.randomInt(0, 2500) == 1) {
                if (Utils.randomInt(1, PrisonEnchants.KEY_FINDER.MAX_LEVEL) <= keyFinderLevel) {
                    //Enchant should activate
                    ItemStack reward;
                    int randomReward = Utils.randomInt(1, 100);
                    if (randomReward <= 15) {
                        //15%
                        reward = CustomItems.getCommonCrateKey(2);
                    } else if (randomReward <= 45) {
                        //30%
                        reward = CustomItems.getRareCrateKey(1);
                    } else if (randomReward <= 90) {
                        //45%
                        reward = CustomItems.getEpicCrateKey(1);
                    } else if (randomReward <= 95) {
                        //5%
                        reward = CustomItems.getLegendaryCrateKey(1);
                    } else if (randomReward <= 99) {
                        //4%
                        reward = CustomItems.getStaticCrateKey(1);
                    } else {
                        //1%
                        reward = CustomItems.getStaticpCrateKey(1);
                    }
                    player.sendMessage(ChatColor.LIGHT_PURPLE + "[Key Finder] " + ChatColor.WHITE + "You have just found a " + Utils.getPrettyItemName(reward) + ChatColor.WHITE + " while mining!");
                    Utils.addItemToPlayersInventoryAndDropExtra(player, reward);
                }
            }
        }


        BigInteger totalAmountOfBlocksBroken = BigInteger.ZERO;
        //Check if the backpack is full before adding
        boolean backpackWasFull = playerData.getBackpack().isFull();
        //Factor in fortune and ore splitter
        long fortuneMultiplier = CustomEnchants.getEnchantLevel(pickaxe, "fortune") + 1;
        if (doubleFortune) fortuneMultiplier *= 2;
        //Add the blocks broken to a player's backpack
        for (Material key : blocksBroken.keySet()) {
            playerData.addBlocksToBackpack(key, blocksBroken.get(key).multiply(BigInteger.valueOf(fortuneMultiplier)));
            totalAmountOfBlocksBroken = totalAmountOfBlocksBroken.add(blocksBroken.get(key));
        }
        //Adds one token for every block broken
        BigInteger tokensToAdd = totalAmountOfBlocksBroken.multiply(BigInteger.valueOf(CustomEnchants.getEnchantLevel(pickaxe, "tokenator") + 1));
        tokensToAdd = tokensToAdd.add(tokensToAdd.multiply(BigInteger.valueOf(CustomEnchants.getEnchantLevel(pickaxe, "tokenPolisher") + 1)).divide(BigInteger.TEN));
        if (tokensToAdd.compareTo(BigInteger.ZERO) < 0) tokensToAdd = BigInteger.ONE;
        playerData.addTokens(tokensToAdd);
        //Add the blocks mined to the player and pickaxe
        playerData.addBlocksMined(totalAmountOfBlocksBroken);
        PrisonPickaxe.addBlocksBroken(pickaxe, totalAmountOfBlocksBroken.longValue());
        PrisonPickaxe.addBlocksMined(pickaxe, 1); //Raw blocks
        if (PrisonPickaxe.addXP(pickaxe, totalAmountOfBlocksBroken.longValue() * PrisonPickaxe.BASE_XP_PER_BLOCK_BROKEN * (CustomEnchants.getEnchantLevel(pickaxe, "xpFinder") + 1))) {
            player.sendMessage(ChatColor.LIGHT_PURPLE + "Your pickaxe has just leveled to level " + ChatColor.WHITE + ChatColor.BOLD + PrisonPickaxe.getLevel(pickaxe) + "!");
            if (PrisonPickaxe.getLevel(pickaxe) % 25 == 0) {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.sendMessage(ChatColor.LIGHT_PURPLE + player.getName() + "'s pickaxe has just leveled up to level " + ChatColor.WHITE + ChatColor.BOLD + PrisonPickaxe.getLevel(pickaxe) + "!");
                }
            }
        }
        //If the player's backpack was not full but now is, tell them it has filled up or auto sell
        if (playerData.checkIfBackpackIsFull()) {
            if (playerData.getIsAutoSellEnabled()) {
                playerData.sellBackpack(player, false);
            } else if (!backpackWasFull) {
                player.sendTitle(ChatColor.RED + "" + ChatColor.BOLD + "Your Backpack", ChatColor.RED + "" + ChatColor.BOLD + "Is Full! (" + Utils.prettyNum(playerData.getBackpack().getSize()) + "/" + Utils.prettyNum(playerData.getBackpack().getSize()) + ")", 5, 40, 5);
                player.sendMessage(ChatColor.RED + "Your backpack is full!");
            }
        }
    }
}