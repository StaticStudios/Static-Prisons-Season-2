package net.staticstudios.prisons.blockBroken;

import org.bukkit.entity.Player;

public class BlockBreakEventsdafasf {
    public static void onBlockBreak(org.bukkit.event.block.BlockBreakEvent e) {
        Player player = e.getPlayer();
        //Check if the block broken was in a prison mine
        if (!player.getWorld().getName().equals("mines")) return;

        //PrisonPickaxe.updateLore(player.getInventory().getItemInMainHand());

        /*

        ItemStack pickaxe = player.getInventory().getItemInMainHand();
        Map<String, Integer> cachedStats = PrisonPickaxe.getCachedEnchants(pickaxe);
        if (cachedStats == null) return;
        PrisonPickaxe.verifyPickIsInBuffer(pickaxe);
        playerData.addRawBlocksMined(BigInteger.ONE);
        Map<Material, BigInteger> blocksBroken = new HashMap<>();
        blocksBroken.put(e.getBlock().getType(), BigInteger.ONE);







        BigInteger totalAmountOfBlocksBroken = BigInteger.ZERO;
        boolean backpackWasFull = playerData.getBackpackIsFull();

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


         */
    }
}