package me.staticstudios.prisons.blockBroken;

import me.staticstudios.prisons.enchants.handler.BaseEnchant;
import me.staticstudios.prisons.newData.dataHandling.PlayerData;
import me.staticstudios.prisons.mines.BaseMine;
import me.staticstudios.prisons.mines.MineManager;
import me.staticstudios.prisons.enchants.handler.PrisonPickaxe;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.math.BigDecimal;

public class BlockBreakEvent {
    public static void onBlockBreak(org.bukkit.event.block.BlockBreakEvent e) {
        Player player = e.getPlayer();
        //Check if the block broken was in a prison mine
        if (!player.getWorld().getName().equals("mines")) return;
        PrisonPickaxe pickaxe = PrisonPickaxe.fromItem(player.getInventory().getItemInMainHand());
        if (pickaxe == null) return;
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


        PrisonBlockBroken bb = new PrisonBlockBroken(player, playerData, pickaxe, mine, e.getBlock());
        for (BaseEnchant enchant : pickaxe.getEnchants()) enchant.onBlockBreak(bb);
        //todo finish up the event using all multipliers
        long totalBlocksBroken = (long) (bb.blocksBroken * bb.blocksBrokenMultiplier);
        pickaxe.addBlocksBroken(totalBlocksBroken);
        pickaxe.addRawBlocksBroken(1);
        pickaxe.addXp((long) (totalBlocksBroken * 2 * bb.xpMultiplier));

        //Put blocks broken in the player's backpack
        for (Material key : bb.blockTypesBroken.keySet()) playerData.addBackpackAmountOf(key, new BigDecimal(bb.blockTypesBroken.get(key)).multiply(BigDecimal.valueOf(bb.blocksBrokenMultiplier)).toBigInteger());
        //PrisonPickaxe.updateLore(player.getInventory().getItemInMainHand());

        /*

        ItemStack pickaxe = player.getInventory().getItemInMainHand();
        Map<String, Integer> cachedStats = PrisonPickaxe.getCachedEnchants(pickaxe);
        if (cachedStats == null) return;
        PrisonPickaxe.verifyPickIsInBuffer(pickaxe);
        playerData.addRawBlocksMined(BigInteger.ONE);
        Map<Material, BigInteger> blocksBroken = new HashMap<>();
        blocksBroken.put(e.getBlock().getType(), BigInteger.ONE);



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


        BigInteger totalAmountOfBlocksBroken = BigInteger.ZERO;
        boolean backpackWasFull = playerData.getBackpackIsFull();
        int fortuneMultiplier = cachedStats.get("fortune") + 1;
        int oreSplitter = cachedStats.get("oreSplitter");
        if (oreSplitter > 0 && oreSplitter <= Utils.randomInt(1, PrisonEnchants.ORE_SPLITTER.MAX_LEVEL)) fortuneMultiplier *= 2;
        for (Material key : blocksBroken.keySet()) {
            playerData.addBackpackAmountOf(key, blocksBroken.get(key).multiply(BigInteger.valueOf(fortuneMultiplier)));
            totalAmountOfBlocksBroken = totalAmountOfBlocksBroken.add(blocksBroken.get(key));
        }

        //Adds one token for every block broken
        //BigInteger tokensToAdd = totalAmountOfBlocksBroken.multiply(BigInteger.valueOf(CustomEnchants.getEnchantLevel(pickaxe, "tokenator") + 1));
        //tokensToAdd = tokensToAdd.add(tokensToAdd.multiply(BigInteger.valueOf(CustomEnchants.getEnchantLevel(pickaxe, "tokenPolisher") + 1)).divide(BigInteger.TEN));
        //if (CustomEnchants.uuidToTempTokenMultiplier.containsKey(player.getUniqueId())) tokensToAdd = tokensToAdd.add(tokensToAdd.multiply(BigInteger.valueOf((long) (CustomEnchants.uuidToTempTokenMultiplier.get(player.getUniqueId()) * 10))).divide(BigInteger.TEN));
        //if (tokensToAdd.compareTo(BigInteger.ZERO) < 0) tokensToAdd = BigInteger.ONE;
        //playerData.addTokens(tokensToAdd);

        int chance = (Utils.randomInt(1, 350 - cachedStats.get("tokenator") / 25)); //Max level requires 150 blocks on average
        if (chance == 1) {
            //The player should receive tokens
            int tokenMultiplier = mine.getTokenMultiplier();
            switch (playerData.getPlayerRank()) {
                case "mythic" -> tokenMultiplier += 5;
                case "static" -> tokenMultiplier += 10;
                case "staticp" -> tokenMultiplier += 20;
            }

            BigInteger tokens = BigInteger.valueOf(Utils.randomInt(200, 800));
            if (mine.mineID.equals("eventMine")) tokens = tokens.add(tokens.multiply(BigInteger.TWO).divide(BigInteger.TEN));
            if (ConsistencyEnchant.uuidToTempTokenMultiplier.containsKey(player.getUniqueId())) tokens = tokens.add(BigDecimal.valueOf(tokens.longValue()).multiply(BigDecimal.valueOf(ConsistencyEnchant.uuidToTempTokenMultiplier.get(player.getUniqueId()))).toBigInteger());
            tokens = tokens.add(tokens.multiply(BigInteger.valueOf(tokenMultiplier)).divide(BigInteger.valueOf(100)));
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
            if (Utils.checkIfPlayerCanAutoSell(playerData) && playerData.getIsAutoSellEnabled()) {
                playerData.sellBackpack(player, true);
            } else if (!backpackWasFull) {
                if (playerData.getIsAutoSellEnabled() && !Utils.checkIfPlayerCanAutoSell(playerData)) playerData.setIsAutoSellEnabled(false);
                player.sendTitle(ChatColor.RED + "" + ChatColor.BOLD + "Your Backpack", ChatColor.RED + "" + ChatColor.BOLD + "Is Full! (" + Utils.prettyNum(playerData.getBackpackSize()) + "/" + Utils.prettyNum(playerData.getBackpackSize()) + ")", 5, 40, 5);
                player.sendMessage(ChatColor.RED + "Your backpack is full!");
            }
        }

         */
    }
}