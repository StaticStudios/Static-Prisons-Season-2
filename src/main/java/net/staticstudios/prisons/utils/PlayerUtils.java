package net.staticstudios.prisons.utils;

import com.destroystokyo.paper.event.server.ServerTickStartEvent;
import net.staticstudios.prisons.data.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class PlayerUtils implements Listener {
    private static JavaPlugin parent;
    private static long currentTick = 0;
    private static final int HOLDING_THRESHOLD = 5;

    public static void init(JavaPlugin parent) {
        PlayerUtils.parent = parent;
        parent.getServer().getPluginManager().registerEvents(new PlayerUtils(), parent);

        Bukkit.getScheduler().runTaskTimer(parent, () -> {
            for (Map.Entry<Player, RightClick> entry : new HashSet<>(playerHoldingClicks.entrySet())) {
                Player player = entry.getKey();
                RightClick rc = entry.getValue();
                if (rc.lastUpdatedAt() + HOLDING_THRESHOLD >= currentTick) continue;
                playerHoldingClicks.remove(player);
            }
        }, 20, 4); //The interact event gets called once every 4 ticks
    }

    //Right-click listener start
    public static final Map<Player, RightClick> playerHoldingClicks = new HashMap<>();

//        public static void backpackFullCheck(boolean wasFullBefore, Player player, PlayerData playerData) {
//            if (playerData.getBackpackIsFull()) {
//                if (canAutoSell(playerData) && playerData.getIsAutoSellEnabled()) {
//                    playerData.sellBackpack(player, true, ChatColor.translateAlternateColorCodes('&', "&a&lAuto Sell &8&l>> &f(x%MULTI%) Sold &b%TOTAL_BACKPACK_COUNT% &fblocks for: &a$%TOTAL_SELL_PRICE%"));
//                } else if (!wasFullBefore) {
//                    if (playerData.getIsAutoSellEnabled() && !canAutoSell(playerData)) playerData.setIsAutoSellEnabled(false);
//                    if (player != null) {
//                        player.sendTitle(ChatColor.RED + "" + ChatColor.BOLD + "Your Backpack", ChatColor.RED + "" + ChatColor.BOLD + "Is Full! (" + prettyNum(playerData.getBackpackSize()) + "/" + prettyNum(playerData.getBackpackSize()) + ")", 5, 40, 5);
//                        player.sendMessage(ChatColor.RED + "Your backpack is full!");
//                    }
//                }
//            }
//        }

    private record RightClick(boolean isRightClicking, long heldFor, long lastUpdatedAt) {
    }

    @EventHandler
    void onInteract(PlayerInteractEvent e) {
        if (!e.getAction().isRightClick()) return;
        long heldFor = 0;
        if (playerHoldingClicks.containsKey(e.getPlayer())) {
            RightClick rc = playerHoldingClicks.get(e.getPlayer());
            heldFor = rc.heldFor() + currentTick - rc.lastUpdatedAt();
        }
        playerHoldingClicks.put(e.getPlayer(), new RightClick(true, heldFor, currentTick));
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    void tickStarted(ServerTickStartEvent e) {
        currentTick += 1;
    }

    public static boolean isHoldingRightClick(Player player) {
        if (!playerHoldingClicks.containsKey(player)) return false;
        return playerHoldingClicks.get(player).heldFor() > HOLDING_THRESHOLD;
    }
    //Right-click listener end

    public static ItemStack getSkull(OfflinePlayer player) {
        ItemStack skullItem = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skullMeta = (SkullMeta) skullItem.getItemMeta();

        skullMeta.setOwningPlayer(player);
        skullItem.setItemMeta(skullMeta);

        return skullItem;
    }

    /**
     * This method will add an item to a player's inventory and drop any extra on the ground if the inventory is full
     *
     * @param player Player whose inventory will be affected
     * @param items  Item to add
     */
    public static void addToInventory(Player player, ItemStack items) {
        player.getInventory().addItem(items).forEach((slot, item) -> player.getWorld().dropItem(player.getLocation(), item));
    }

    /**
     * @return true if this player can auto sell; this will factor in ranks and booster status
     */
    public static boolean canAutoSell(PlayerData playerData) {
        if (playerData.getCanExplicitlyEnableAutoSell()) return true;
        return playerData.getPlayerRanks().contains("warrior") || playerData.getIsNitroBoosting();
    }

    public static boolean canAutoSell(Player player) {
        return canAutoSell(new PlayerData(player));
    }
}
