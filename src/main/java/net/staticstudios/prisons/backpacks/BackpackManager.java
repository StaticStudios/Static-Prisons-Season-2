package net.staticstudios.prisons.backpacks;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.title.Title;
import net.md_5.bungee.api.ChatColor;
import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.backpacks.commands.BackpackCommand;
import net.staticstudios.prisons.backpacks.config.BackpackConfig;
import net.staticstudios.prisons.backpacks.selling.SellCommand;
import net.staticstudios.prisons.backpacks.selling.SellReceipt;
import net.staticstudios.prisons.commands.CommandManager;
import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.mines.MineBlock;
import net.staticstudios.prisons.pickaxe.PrisonPickaxe;
import net.staticstudios.prisons.pickaxe.enchants.MerchantEnchant;
import net.staticstudios.prisons.utils.ComponentUtil;
import net.staticstudios.prisons.utils.ItemUtils;
import net.staticstudios.prisons.utils.Prefix;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.apache.logging.log4j.util.BiConsumer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.util.*;

public class BackpackManager {

    /**
     * The main map containing all backpacks on the server.
     */
    public static final Map<String, Backpack> ALL_BACKPACKS = new HashMap<>();
    /**
     * A set of players who have their backpacks and inventory full.
     */
    private static final Set<Player> INVENTORY_FULL = new HashSet<>();
    /**
     * A containing the last time a player was told that their inventory is full.
     */
    private static final Map<Player, Long> LAST_FULL_MESSAGE = new HashMap<>();

    /**
     * Run init tasks for the backpack module.
     */
    public static void init() {
        CommandManager.registerCommand("backpack", new BackpackCommand());
        CommandManager.registerCommand("sell", new SellCommand());
        BackpackConfig.loadFromFile();
        loadBackpacks();
        Bukkit.getScheduler().runTaskTimer(StaticPrisons.getInstance(), () -> saveBackpacks(), 20 * 60 * 5 + 20 * 17, 20 * 60); //Save them async every 5min
        StaticPrisons.getInstance().getServer().getPluginManager().registerEvents(new BackpackListener(), StaticPrisons.getInstance());
    }

    /**
     * Load all backpacks from the data/backpacks.yml file.
     */
    public static void loadBackpacks() {
        ALL_BACKPACKS.clear();
        FileConfiguration data = YamlConfiguration.loadConfiguration(new File(StaticPrisons.getInstance().getDataFolder(), "data/backpacks.yml"));
        for (String key : data.getKeys(false)) {
            Backpack backpack = new Backpack(key);
            backpack.tier = data.getInt(key + ".tier");
            backpack.size = data.getLong(key + ".size");
            backpack.itemCount = data.getLong(key + ".itemCount");
            backpack.value = data.getLong(key + ".value");
            ALL_BACKPACKS.put(key, backpack);
        }
    }

    /**
     * Queue an async task to save all the backpack data to the data/backpacks.yml file.
     */
    public static void saveBackpacks() {
        Map<String, Backpack> temp = new HashMap<>(ALL_BACKPACKS);
        Bukkit.getScheduler().runTaskAsynchronously(StaticPrisons.getInstance(), () -> saveBackpacks(temp));
    }

    /**
     * Save all the backpack data to the data/backpacks.yml file on the main thread.
     */
    public static void saveBackpacksNow() {
        saveBackpacks(ALL_BACKPACKS);
    }

    /**
     * Method that handles the actual saving of the backpack data to the data/backpacks.yml file.
     *
     * @param data The map containing the data to save.
     */
    private static void saveBackpacks(Map<String, Backpack> data) {
        FileConfiguration fileConfig = new YamlConfiguration();
        for (Map.Entry<String, Backpack> entry : data.entrySet()) {
            ConfigurationSection section = fileConfig.createSection(entry.getKey());
            section.set("tier", entry.getValue().tier);
            section.set("size", entry.getValue().size);
            section.set("itemCount", entry.getValue().itemCount);
            section.set("value", entry.getValue().value);
        }
        try {
            fileConfig.save(new File(StaticPrisons.getInstance().getDataFolder(), "data/backpacks.yml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get all the backpacks in a player's inventory.
     *
     * @param player The player whose inventory to get the backpacks from.
     * @return A list of all the backpacks in the player's inventory.
     */
    public static List<Backpack> getPlayerBackpacks(Player player) {
        return Arrays.stream(player.getInventory().getContents()).map(Backpack::fromItem).filter(Objects::nonNull).toList();
    }

    /**
     * Add a map of blocks into a player's backpacks.
     * If the player's backpacks are full, the blocks will be put into their inventory.
     * Additionally, if the player has auto-sell enabled, the backpacks and the player's inventory will be sold, assuming that all backpacks are full.
     *
     * @param player    The player to add the blocks to.
     * @param whatToAdd The map of blocks to add.
     */
    public static void addToBackpacks(Player player, Map<MineBlock, Long> whatToAdd) {
        //Clean up the map to something that we can safely use
        whatToAdd.remove(null);

        List<Backpack> playerBackpacks = getPlayerBackpacks(player);
        Set<Backpack> affectedBackpacks = new HashSet<>();

        //Add the items to the backpacks
        for (MineBlock mineBlock : whatToAdd.keySet()) {
            long amount = whatToAdd.get(mineBlock);
            for (Backpack backpack : playerBackpacks) {
                if (backpack.isFull()) continue;
                if (backpack.getItemCount() + amount <= backpack.getCapacity()) { //This backpack can hold all of this block type
                    backpack.setItemCount(backpack.getItemCount() + amount, false);
                    backpack.setValue(backpack.getValue() + mineBlock.value() * amount, false);
                    amount = 0;
                } else { //The backpack will be able to hold only a portion of this block type
                    long amountToAdd = backpack.getCapacity() - backpack.getItemCount();
                    backpack.setItemCount(backpack.getCapacity(), false);
                    backpack.setValue(backpack.getValue() + mineBlock.value() * amountToAdd, false);
                    amount -= amountToAdd;
                }
                affectedBackpacks.add(backpack);
                if (amount <= 0) break;
            }
            whatToAdd.put(mineBlock, amount);
        }

        //Clean up the map removing items we're done with
        whatToAdd.entrySet().removeIf(entry -> entry.getValue() <= 0);

        if (whatToAdd.isEmpty()) { //Everything was added to the backpacks, so we're done
            affectedBackpacks.forEach(Backpack::updateItem);
            return;
        }

        ItemStack[] inventory = player.getInventory().getContents();
        PlayerData playerData = new PlayerData(player);
        Map<MineBlock, Long> itemsToGoIntoInventory = new HashMap<>(inventory.length, 1);
        Set<Integer> fullSlots = new HashSet<>();

        //Figure out what items will fit into the player's inventory without actually adding them
        for (MineBlock mineBlock : whatToAdd.keySet()) {
            for (int i = 0; i < inventory.length; i++) {
                if (fullSlots.contains(i)) continue;
                ItemStack itemStack = inventory[i];
                long amountToAdd;
                if (itemStack == null || itemStack.getType() == Material.AIR) {
                    amountToAdd = Math.min(whatToAdd.get(mineBlock), 64L);
                } else if (itemStack.getType().equals(mineBlock.material()) && itemStack.getAmount() < 64) {
                    amountToAdd = Math.min(whatToAdd.get(mineBlock), 64 - itemStack.getAmount());
                } else {
                    continue;
                }
                fullSlots.add(i);
                itemsToGoIntoInventory.put(mineBlock, itemsToGoIntoInventory.getOrDefault(mineBlock, 0L) + amountToAdd);
                whatToAdd.put(mineBlock, whatToAdd.getOrDefault(mineBlock, 0L) - amountToAdd);
                if (whatToAdd.get(mineBlock) <= 0) { //Move on to the next block type since we are done with this one
                    break;
                }
            }
        }

        //Make an attempt to add any left-over items to the player's inventory
        if (playerData.getIsAutoSellEnabled()) {
            //Since auto-sell is enabled and all the backpacks are full, we know that we are going to trigger a sell, so we do not want to actually add the items to the inventory

            long totalAmount = 0;
            long totalValue = 0;

            for (Backpack backpack : playerBackpacks) {
                totalAmount += backpack.getItemCount();
                totalValue += backpack.getValue();
            }

            for (Map.Entry<MineBlock, Long> entry : itemsToGoIntoInventory.entrySet()) {
                totalAmount += entry.getValue();
                totalValue += entry.getKey().value() * entry.getValue();
            }

            for (ItemStack itemStack : inventory) {
                if (itemStack == null) continue;
                MineBlock mineBlock = MineBlock.fromMaterial(itemStack.getType());
                if (mineBlock == null) continue;
                totalAmount += itemStack.getAmount();
                totalValue += mineBlock.value() * itemStack.getAmount();
                player.getInventory().remove(itemStack);
            }

            double multiplier = playerData.getMoneyMultiplier();
            PrisonPickaxe pickaxe = PrisonPickaxe.fromItem(player.getInventory().getItemInMainHand());
            if (pickaxe != null) {
                multiplier += MerchantEnchant.getMultiplier(pickaxe);
            }

            totalValue *= multiplier;

            playerData.addMoney(totalValue);

            player.sendMessage(Prefix.AUTO_SELL.append(Component.text("(" + BigDecimal.valueOf(multiplier).setScale(2, RoundingMode.FLOOR) + "x) Sold ").color(ComponentUtil.WHITE)
                    .append(Component.text(PrisonUtils.prettyNum(totalAmount)).color(ComponentUtil.AQUA))
                    .append(Component.text(" blocks for: ").color(ComponentUtil.WHITE))
                    .append(Component.text("$" + PrisonUtils.prettyNum(totalValue)).color(ComponentUtil.GREEN))
            ));

            INVENTORY_FULL.remove(player);
            LAST_FULL_MESSAGE.remove(player);

            playerBackpacks.forEach(backpack -> {
                backpack.setItemCount(0, false);
                backpack.setValue(0, true);
            });

            return;
        }
        //Since auto-sell is not enabled, we can just add the items from earlier into the player's inventory

        boolean isInventoryFull = false;
        for (Map.Entry<MineBlock, Long> entry : itemsToGoIntoInventory.entrySet()) {
            if (!player.getInventory().addItem(new ItemStack(entry.getKey().material(), entry.getValue().intValue())).isEmpty()) {
                isInventoryFull = true;
            }
        }

        affectedBackpacks.forEach(Backpack::updateItem);

        //Tell the player that their inventory is full

        if (!isInventoryFull) {
            INVENTORY_FULL.remove(player);
        }
        if (INVENTORY_FULL.contains(player)) { //The player has already been told that their inventory is full
            return;
        }

        if (LAST_FULL_MESSAGE.getOrDefault(player, 0L) < System.currentTimeMillis() - 2000) { //Don't spam the player with messages
            player.showTitle(Title.title(Component.text("Your Inventory").color(ComponentUtil.RED).decorate(TextDecoration.BOLD).decoration(TextDecoration.ITALIC, false),
                    Component.text("Is Full! (" + PrisonUtils.prettyNum(playerBackpacks.stream().mapToLong(Backpack::getCapacity).sum()) + " Items)").color(ComponentUtil.RED).decorate(TextDecoration.BOLD).decoration(TextDecoration.ITALIC, false),
                    Title.Times.times(Duration.ofMillis(250), Duration.ofMillis(2000), Duration.ofMillis(250))));
            player.sendMessage(ChatColor.RED + "Your inventory is full!");
        }

        LAST_FULL_MESSAGE.put(player, System.currentTimeMillis());
        INVENTORY_FULL.add(player);
    }

    /**
     * Sell all the items in the player's inventory and any backpacks that they have.
     *
     * @param player The player involved in this operation.
     */
    public static void sell(Player player) {
        sell(player, (p, r) -> p.sendMessage(Component.text("(" + r.multiplier().setScale(2, RoundingMode.FLOOR) + "x) Sold ").color(ComponentUtil.WHITE)
                .append(Component.text(PrisonUtils.prettyNum(r.blocksSold())).color(ComponentUtil.AQUA))
                .append(Component.text(" blocks for: ").color(ComponentUtil.WHITE))
                .append(Component.text("$" + PrisonUtils.prettyNum(r.soldFor())).color(ComponentUtil.GREEN))
        ));
    }

    /**
     * The method that handles selling items.
     *
     * @param player           The player involved in this operation.
     * @param runIfWasNotEmpty A method that is run if the player's inventory was not empty.
     */
    public static void sell(Player player, BiConsumer<Player, SellReceipt> runIfWasNotEmpty) {
        PlayerData playerData = new PlayerData(player);
        List<Backpack> backpacks = getPlayerBackpacks(player);
        long blocksSold = 0;
        long soldFor = 0;

        for (Backpack backpack : backpacks) {
            blocksSold += backpack.getItemCount();
            soldFor += backpack.getValue();
            backpack.setItemCount(0, false);
            backpack.setValue(0, true);
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
        PrisonPickaxe pickaxe = PrisonPickaxe.fromItem(player.getInventory().getItemInMainHand());
        if (pickaxe != null) {
            multiplier += MerchantEnchant.getMultiplier(pickaxe);
        }

        soldFor *= multiplier;

        playerData.addMoney(soldFor);

        if (blocksSold > 0) {
            runIfWasNotEmpty.accept(player, new SellReceipt(BigDecimal.valueOf(multiplier), blocksSold, soldFor));
        }

        INVENTORY_FULL.remove(player);
        LAST_FULL_MESSAGE.remove(player);
    }

    /**
     * Sell the items in a specific backpack.
     * This method will not do anything special besides put the value of the backpack into the player's balance and empty the backpack.
     *
     * @param player     The player involved in this operation.
     * @param backpack   The backpack to sell.
     * @param updateItem If true, the backpack's updateItem method will be called.
     */
    public static void sellBackpack(Player player, Backpack backpack, boolean updateItem) {
        PlayerData playerData = new PlayerData(player);
        double multiplier = playerData.getMoneyMultiplier();
        playerData.addMoney((long) (backpack.getValue() * multiplier));
        backpack.setItemCount(0);
        backpack.setValue(0);
        if (updateItem) backpack.updateItem();
    }

    /**
     * Create a new backpack.
     *
     * @param tier The tier of backpack to create.
     * @return The newly created backpack.
     */
    public static Backpack createBackpack(int tier) {
        return new Backpack(ItemUtils.createCustomSkull(BackpackConfig.tier(tier).skin()), tier);
    }
}
