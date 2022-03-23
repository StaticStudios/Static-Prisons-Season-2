package me.staticstudios.prisons.core.blockBroken;

import me.staticstudios.prisons.core.enchants.CustomEnchants;
import me.staticstudios.prisons.gameplay.customItems.CustomItems;
import me.staticstudios.prisons.core.data.serverData.PlayerData;
import me.staticstudios.prisons.core.enchants.PrisonEnchants;
import me.staticstudios.prisons.core.mines.BaseMine;
import me.staticstudios.prisons.core.mines.MineManager;
import me.staticstudios.prisons.core.enchants.PrisonPickaxe;
import me.staticstudios.prisons.utils.Utils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.math.BigInteger;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class BlockBreakEvent {
    public static void onBlockBreak(org.bukkit.event.block.BlockBreakEvent e) {
        Player player = e.getPlayer();
        //Check if the block broken was in a prison mine
        if (!player.getWorld().getName().equals("mines")) return;
        //Check if it is in the mine area
        BaseMine mine = MineManager.allMines.get(MineManager.getMineIDFromLocation(player.getLocation()));
        int locX = e.getBlock().getX();
        int locY = e.getBlock().getY();
        int locZ = e.getBlock().getZ();
        if (!(locX >= mine.minLocation.getX() && locX <= mine.maxLocation.getX() &&
                locZ >= mine.minLocation.getZ() && locZ <= mine.maxLocation.getZ() &&
                locY >= mine.minLocation.getY() && locY <= mine.maxLocation.getY()))
            return;
        e.setDropItems(false);
        e.setExpToDrop(0);
        PlayerData playerData = new PlayerData(player);
        mine.brokeBlocksInMine(1);



        ItemStack pickaxe = player.getInventory().getItemInMainHand();
        Map<String, Integer> cachedStats = PrisonPickaxe.getCachedEnchants(player);
        if (cachedStats == null) return;
        PrisonPickaxe.verifyPickIsInBuffer(pickaxe);
        playerData.addRawBlocksMined(BigInteger.ONE);
        Map<Material, BigInteger> blocksBroken = new HashMap<>();
        blocksBroken.put(e.getBlock().getType(), BigInteger.ONE);

        //TODO: cache pick data in a map and get the data from that instead of directly off of the item, call a method to check if the item data is cached when this event is called, if not, cache it. find a mthod that gets called whenever enchants are updated, update the cache for that pick whenever it is updated. When a player leaves the game, remove all of their pickaxes from the cached items.
        //TODO: use a que system to update the data on the pick every couple seconds instead of instantly. whenever a pick is dropped, moved, or hand slot changes, instantly update its data from the temo storage map. update the data every second otherwise. do not search for the pick in the player's inv, assume it is always in their main hand. if they leave, update the picks data instantly


        if (Utils.randomInt(1, 75) == 1) { //Jack Hammer
            int jackHammerLevel = cachedStats.get("jackHammer");
            if (jackHammerLevel > 0) {
                int doubleWammyLevel = cachedStats.get("doubleWammy");
                if (Utils.randomInt(1, PrisonEnchants.JACK_HAMMER.MAX_LEVEL + PrisonEnchants.JACK_HAMMER.MAX_LEVEL / 10) <= jackHammerLevel + PrisonEnchants.JACK_HAMMER.MAX_LEVEL / 10) {
                    //Enchant should activate
                    int howDeepToGo = 1;
                    if (Utils.randomInt(1, PrisonEnchants.DOUBLE_WAMMY.MAX_LEVEL + PrisonEnchants.DOUBLE_WAMMY.MAX_LEVEL / 10) <= doubleWammyLevel + PrisonEnchants.DOUBLE_WAMMY.MAX_LEVEL / 10)
                        howDeepToGo += 1;
                    blocksBroken.putAll(new JackHammer(mine, e.getBlock().getY()).destroyLayer(e.getBlock().getY(), howDeepToGo));
                }
            }
        }
        if (Utils.randomInt(1, 125) == 1) {//Multi-directional
            int multiDirectionalLevel = cachedStats.get("multiDirectional");
            if (multiDirectionalLevel > 0) {
                if (Utils.randomInt(1, PrisonEnchants.MULTI_DIRECTIONAL.MAX_LEVEL + PrisonEnchants.MULTI_DIRECTIONAL.MAX_LEVEL / 10) <= multiDirectionalLevel + PrisonEnchants.MULTI_DIRECTIONAL.MAX_LEVEL / 10) {
                    //Enchant should activate
                    int howDeepToGo = (int) Math.max(1, e.getBlock().getY() - multiDirectionalLevel * 250 / PrisonEnchants.MULTI_DIRECTIONAL.MAX_LEVEL);
                    blocksBroken.putAll(new MultiDirectional(mine, e.getBlock().getLocation()).destroySection(e.getBlock().getY(), howDeepToGo, e.getBlock().getX(), e.getBlock().getZ()));
                }
            }
        }
        //Key finder
        if (Utils.randomInt(0, 2500) == 1) {
            int keyFinderLevel = cachedStats.get("keyFinder");
            if (keyFinderLevel > 0) {
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
        boolean backpackWasFull = playerData.getBackpackIsFull();
        int fortuneMultiplier = cachedStats.get("fortune") + 1;
        int oreSplitter = cachedStats.get("oreSplitter");
        if (oreSplitter > 0 && oreSplitter <= Utils.randomInt(1, PrisonEnchants.ORE_SPLITTER.MAX_LEVEL)) fortuneMultiplier *= 2;
        for (Material key : blocksBroken.keySet()) { //TODO: Try to find a way around this
            playerData.addBackpackAmountOf(key, blocksBroken.get(key).multiply(BigInteger.valueOf(fortuneMultiplier)));
            totalAmountOfBlocksBroken = totalAmountOfBlocksBroken.add(blocksBroken.get(key));
        }

        //Adds one token for every block broken
        //BigInteger tokensToAdd = totalAmountOfBlocksBroken.multiply(BigInteger.valueOf(CustomEnchants.getEnchantLevel(pickaxe, "tokenator") + 1));
        //tokensToAdd = tokensToAdd.add(tokensToAdd.multiply(BigInteger.valueOf(CustomEnchants.getEnchantLevel(pickaxe, "tokenPolisher") + 1)).divide(BigInteger.TEN));
        //if (CustomEnchants.uuidToTempTokenMultiplier.containsKey(player.getUniqueId())) tokensToAdd = tokensToAdd.add(tokensToAdd.multiply(BigInteger.valueOf((long) (CustomEnchants.uuidToTempTokenMultiplier.get(player.getUniqueId()) * 10))).divide(BigInteger.TEN));
        //if (tokensToAdd.compareTo(BigInteger.ZERO) < 0) tokensToAdd = BigInteger.ONE;
        //playerData.addTokens(tokensToAdd);

        int chance = (Utils.randomInt(1, (int) (450 - cachedStats.get("tokenator") / 16.5))); //Max level requires 147 blocks on average
        if (chance == 1) {
            //The player should receive tokens
            BigInteger tokens = BigInteger.valueOf(Utils.randomInt(200, 800));
            if (mine.mineID.equals("eventMine")) tokens = tokens.add(tokens.multiply(BigInteger.TWO).divide(BigInteger.TEN));
            if (CustomEnchants.uuidToTempTokenMultiplier.containsKey(player.getUniqueId())) tokens = tokens.add(tokens.multiply(BigInteger.valueOf((long) (CustomEnchants.uuidToTempTokenMultiplier.get(player.getUniqueId()) * 1))).divide(BigInteger.TEN));
            player.sendMessage(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "+ " + tokens + " Tokens");
            playerData.addTokens(tokens);
        }
        playerData.addBlocksMined(totalAmountOfBlocksBroken);
        PrisonPickaxe.addBlocksBroken(pickaxe, totalAmountOfBlocksBroken.longValue());
        PrisonPickaxe.addBlocksMined(pickaxe, 1); //Raw blocks
        if (PrisonPickaxe.addXP(pickaxe, PrisonPickaxe.BASE_XP_PER_BLOCK_BROKEN * totalAmountOfBlocksBroken.longValue())) {
            player.sendMessage(ChatColor.AQUA + "Your pickaxe leveled up to level " + ChatColor.WHITE + ChatColor.BOLD + PrisonPickaxe.getLevel(pickaxe) + "!");
            if (PrisonPickaxe.getLevel(pickaxe) % 25 == 0) {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.sendMessage(ChatColor.AQUA + player.getName() + "'s pickaxe leveled up to level " + ChatColor.WHITE + ChatColor.BOLD + PrisonPickaxe.getLevel(pickaxe) + "!");
                }
            }
        }

        //If the player's backpack was not full but now is, tell them it has filled up or auto sell
        if (playerData.getBackpackIsFull()) {
            if (Utils.checkIfPlayerCanAutoSell(playerData)) {
                playerData.sellBackpack(player, false);
            } else if (!backpackWasFull) {
                if (playerData.getIsAutoSellEnabled()) playerData.setIsAutoSellEnabled(false);
                player.sendTitle(ChatColor.RED + "" + ChatColor.BOLD + "Your Backpack", ChatColor.RED + "" + ChatColor.BOLD + "Is Full! (" + Utils.prettyNum(playerData.getBackpackSize()) + "/" + Utils.prettyNum(playerData.getBackpackSize()) + ")", 5, 40, 5);
                player.sendMessage(ChatColor.RED + "Your backpack is full!");
            }
        }
    }
}