package net.staticstudios.prisons.backpacks;

import net.md_5.bungee.api.ChatColor;
import net.staticstudios.prisons.backpacks.selling.SellReceipt;
import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.mines.MineBlock;
import net.staticstudios.prisons.utils.ItemUtils;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.apache.logging.log4j.util.BiConsumer;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class PrisonBackpacks {

    private static final Map<Player, Boolean> BACKPACKS_FULL = new HashMap<>();
    private static final Map<Player, Long> LAST_FULL_MESSAGE = new HashMap<>();

    public static List<PrisonBackpack> getPlayerBackpacks(Player player) {
        List<PrisonBackpack> backpacks = new LinkedList<>();
        ItemStack[] items = player.getInventory().getContents();
        for (ItemStack item : items) {
            PrisonBackpack backpack = PrisonBackpack.fromItem(item);
            if (backpack != null) {
                backpacks.add(backpack);
            }
        }
        return backpacks;
    }

    public static void addToBackpacks(Player player, Map<MineBlock, Long> whatToAdd) {
        whatToAdd.remove(null);
        final Map<MineBlock, Long> initialThingsToAdd = new HashMap<>(whatToAdd);
        for (PrisonBackpack backpack : getPlayerBackpacks(player)) {
            whatToAdd = backpack.addToBackpack(whatToAdd);
        }
        ItemStack[] initialInventoryContents = player.getInventory().getContents();
        boolean changedInventory = false;
        if (!whatToAdd.isEmpty()) { //All the player's backpacks filled up, add the remaining items to the player's inventory
            PlayerInventory inventory = player.getInventory();
            List<MineBlock> whatToRemove = new LinkedList<>();
            for (Map.Entry<MineBlock, Long> entry : whatToAdd.entrySet()) {
                ItemStack itemStack = new ItemStack(entry.getKey().material());
                long amountToAdd = entry.getValue();
                while (amountToAdd > 0) {
                    int amountToAddThisTime = (int) Math.min(amountToAdd, itemStack.getMaxStackSize());
                    ItemStack item = itemStack.clone();
                    item.setAmount(amountToAddThisTime);
                    changedInventory = true;
                    if (!inventory.addItem(item).isEmpty())
                        break; //The player's inventory is full. The player might be able to add items with a different material tho, so we'll try again with the next material.
                    amountToAdd -= amountToAddThisTime;
                }
                if (amountToAdd == 0) {
                    whatToRemove.add(entry.getKey());
                }
            }
            for (MineBlock block : whatToRemove) {
                whatToAdd.remove(block);
            }
        }
        if (!initialThingsToAdd.equals(whatToAdd)) {
            BACKPACKS_FULL.remove(player); //The player's backpack(s) were not completely full initially so if they were in the map, remove them as something has changed
        }
        if (!whatToAdd.isEmpty()) {
            if (backpacksAreFull(player, BACKPACKS_FULL.containsKey(player))) {
                if (changedInventory) {
                    player.getInventory().setContents(initialInventoryContents);
                }
            } else BACKPACKS_FULL.put(player, true); //The player's backpack(s) are now full
        }
    }

    /**
     * Tell the player their backpack is full
     *
     * @return true if auto sold
     */
    public static boolean backpacksAreFull(Player player, boolean wasFullBefore) {
        PlayerData playerData = new PlayerData(player);
        if (playerData.getIsAutoSellEnabled()) {
            sell(player, (p, r) -> {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&lAuto Sell &8&l>> &f(x" + r.multiplier().setScale(2, RoundingMode.FLOOR) + ") Sold &b" + PrisonUtils.prettyNum(r.blocksSold()) + " &fblocks for: &a$" + PrisonUtils.prettyNum(r.soldFor())));
            });
            return true;
        }
        if (wasFullBefore) return false;
        if (LAST_FULL_MESSAGE.getOrDefault(player, 0L) > System.currentTimeMillis() - 2000)
            return false; //Don't spam the player with messages
        long totalItems = 0;
        for (PrisonBackpack backpack : getPlayerBackpacks(player)) {
            totalItems += backpack.getItemCount();
        }
        for (ItemStack item : player.getInventory().getContents()) {
            if (item != null && item.getType() != Material.AIR) {
                if (MineBlock.fromMaterial(item.getType()) != null) {
                    totalItems += item.getAmount();
                }
            }
        }
        player.sendTitle(ChatColor.RED + "" + ChatColor.BOLD + "Your Inventory", ChatColor.RED + "" + ChatColor.BOLD + "Is Full! (" + PrisonUtils.prettyNum(totalItems) + " Items)", 5, 40, 5);
        player.sendMessage(ChatColor.RED + "Your inventory is full!");
        LAST_FULL_MESSAGE.put(player, System.currentTimeMillis());
        return false;
    }

    public static void sell(Player player) {
        sell(player, (p, r) -> {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f(x" + r.multiplier().setScale(2, RoundingMode.FLOOR) + ") Sold &b" + PrisonUtils.prettyNum(r.blocksSold()) + " &fblocks for: &a$" + PrisonUtils.prettyNum(r.soldFor())));
        });
    }

    public static void sell(Player player, BiConsumer<Player, SellReceipt> runIfWasNotEmpty) {
        PlayerData playerData = new PlayerData(player);
        List<PrisonBackpack> backpacks = getPlayerBackpacks(player);
        long blocksSold = 0;
        long soldFor = 0;

        for (PrisonBackpack backpack : backpacks) {
            blocksSold += backpack.getItemCount();
            soldFor += backpack.getValue();
            backpack.resetCount();
        }

        ItemStack[] items = player.getInventory().getContents();
        for (ItemStack item : items) {
            if (item == null) continue;
            if (item.getType() == Material.AIR) continue;
            if (item.getAmount() == 0) continue;
            MineBlock mineBlock = MineBlock.fromMaterial(item.getType());
            if (mineBlock == null) continue;
            blocksSold += item.getAmount();
            soldFor += mineBlock.value() * item.getAmount();
            item.setAmount(0);
        }

        double multiplier = playerData.getMoneyMultiplier();
        playerData.addMoney((long) (soldFor * multiplier));

        if (blocksSold > 0) {
            runIfWasNotEmpty.accept(player, new SellReceipt(BigDecimal.valueOf(multiplier), blocksSold, soldFor));
        }

        BACKPACKS_FULL.remove(player);
    }

    public static PrisonBackpack createBackpack(int tier) {
        String texture = BackpackConstants.tier1Skin;
        switch (tier) {
            case 2 -> texture = BackpackConstants.tier2Skin;
            case 3 -> texture = BackpackConstants.tier3Skin;
            case 4 -> texture = BackpackConstants.tier4Skin;
            case 5 -> texture = BackpackConstants.tier5Skin;
            case 6 -> texture = BackpackConstants.tier6Skin;
            case 7 -> texture = BackpackConstants.tier7Skin;
            case 8 -> texture = BackpackConstants.tier8Skin;
            case 9 -> texture = BackpackConstants.tier9Skin;
            case 10 -> texture = BackpackConstants.tier10Skin;
        }
        return new PrisonBackpack(ItemUtils.createCustomSkull(texture), tier);
    }

    public static long getMaxSize(int tier) {
        switch (tier) {
            default -> {
                return BackpackConstants.tier1MaxSize;
            }
            case 2 -> {
                return BackpackConstants.tier2MaxSize;
            }
            case 3 -> {
                return BackpackConstants.tier3MaxSize;
            }
            case 4 -> {
                return BackpackConstants.tier4MaxSize;
            }
            case 5 -> {
                return BackpackConstants.tier5MaxSize;
            }
            case 6 -> {
                return BackpackConstants.tier6MaxSize;
            }
            case 7 -> {
                return BackpackConstants.tier7MaxSize;
            }
            case 8 -> {
                return BackpackConstants.tier8MaxSize;
            }
            case 9 -> {
                return BackpackConstants.tier9MaxSize;
            }
            case 10 -> {
                return BackpackConstants.tier10MaxSize;
            }
        }
    }


}
