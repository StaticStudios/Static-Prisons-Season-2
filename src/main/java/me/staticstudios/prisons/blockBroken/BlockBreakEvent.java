package me.staticstudios.prisons.blockBroken;

import me.staticstudios.prisons.data.serverData.PlayerData;
import me.staticstudios.prisons.enchants.CustomEnchants;
import me.staticstudios.prisons.enchants.PrisonEnchants;
import me.staticstudios.prisons.mines.BaseMine;
import me.staticstudios.prisons.mines.MineManager;
import me.staticstudios.prisons.utils.PrisonPickaxe;
import me.staticstudios.prisons.utils.Utils;
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
                e.getBlock().getY() >= mine.minLocation.getY() && e.getBlock().getY() <= mine.maxLocation.getY())) return;
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
        if (jackHammerLevel > 0) {
            //Jack Hammer
            if (Utils.randomInt(1, 12) == 1) {
                if (Utils.randomInt(1, PrisonEnchants.JACK_HAMMER.MAX_LEVEL + PrisonEnchants.JACK_HAMMER.MAX_LEVEL / 30) <= jackHammerLevel + PrisonEnchants.JACK_HAMMER.MAX_LEVEL / 30) {
                    //Enchant should activate
                    int howDeepToGo = 1;
                    if (Utils.randomInt(1, PrisonEnchants.DOUBLE_WAMMY.MAX_LEVEL + PrisonEnchants.DOUBLE_WAMMY.MAX_LEVEL / 30) <= doubleWammyLevel + PrisonEnchants.DOUBLE_WAMMY.MAX_LEVEL / 30) howDeepToGo += 1;
                    blocksBroken.putAll(new JackHammer(mine, e.getBlock().getY()).destroyLayer(e.getBlock().getY(), howDeepToGo));
                }
            }
        }
        if (multiDirectionalLevel > 0) {
            //Multi-directional
            if (Utils.randomInt(1, 12) == 1) {
                if (Utils.randomInt(1, PrisonEnchants.MULTI_DIRECTIONAL.MAX_LEVEL + PrisonEnchants.MULTI_DIRECTIONAL.MAX_LEVEL / 30) <= multiDirectionalLevel + PrisonEnchants.MULTI_DIRECTIONAL.MAX_LEVEL / 30) {
                    //Enchant should activate
                    int howDeepToGo = Math.max(1, e.getBlock().getY() - multiDirectionalLevel * 500 / PrisonEnchants.MULTI_DIRECTIONAL.MAX_LEVEL);
                    blocksBroken.putAll(new MultiDirectional(mine, e.getBlock().getLocation()).destroySection(e.getBlock().getY(), howDeepToGo, e.getBlock().getX(), e.getBlock().getZ()));
                }
            }
        }

        BigInteger totalAmountOfBlocksBroken = BigInteger.ZERO;
        //Check if the backpack is full before adding
        boolean backpackWasFull = playerData.getBackpack().isFull();
        //Add the blocks broken to a player's backpack
        for (Material key : blocksBroken.keySet()) {
            playerData.addBlocksToBackpack(key, blocksBroken.get(key).multiply(BigInteger.valueOf(CustomEnchants.getEnchantLevel(pickaxe, "fortune") + 1))); //Factor in their fortune enchant
            totalAmountOfBlocksBroken = totalAmountOfBlocksBroken.add(blocksBroken.get(key));
        }
        //Adds one token for every block broken
        playerData.addTokens(totalAmountOfBlocksBroken.multiply(BigInteger.valueOf(CustomEnchants.getEnchantLevel(pickaxe, "tokenator") + 1)));
        //Add the blocks mined to the player and pickaxe
        playerData.addBlocksMined(totalAmountOfBlocksBroken);
        PrisonPickaxe.addBlocksMined(pickaxe, totalAmountOfBlocksBroken.longValue());
        if (PrisonPickaxe.addXP(pickaxe, totalAmountOfBlocksBroken.longValue() * PrisonPickaxe.BASE_XP_PER_BLOCK_BROKEN * (CustomEnchants.getEnchantLevel(pickaxe, "xpFinder") + 1))) { //TODO: add this enchant
            player.sendMessage(ChatColor.LIGHT_PURPLE + "Your pickaxe has leveled up! It is now level: " + ChatColor.AQUA + PrisonPickaxe.getLevel(pickaxe));
        }
        //If the player's backpack was not full but now is, tell them it has filled up
        if (!backpackWasFull && playerData.checkIfBackpackIsFull()) {
            player.sendTitle(ChatColor.RED + "" + ChatColor.BOLD + "Your Backpack", ChatColor.RED + "" + ChatColor.BOLD + "Is Full! (" + Utils.prettyNum(playerData.getBackpack().getSize()) + "/" + Utils.prettyNum(playerData.getBackpack().getSize()) + ")", 5, 40, 5);
            player.sendMessage(ChatColor.RED + "Your backpack is full!");
        }

    }
}