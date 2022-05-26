package net.staticstudios.prisons.misc;

import com.destroystokyo.paper.event.server.ServerTickStartEvent;
import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.enchants.EggShooterEnchant;
import net.staticstudios.prisons.enchants.handler.BaseEnchant;
import net.staticstudios.prisons.data.dataHandling.PlayerData;
import net.staticstudios.prisons.enchants.handler.PrisonPickaxe;
import net.staticstudios.prisons.external.DiscordLink;
import net.staticstudios.prisons.crates.CrateManager;
import net.staticstudios.prisons.customItems.Vouchers;
import net.staticstudios.prisons.UI.scoreboard.CustomScoreboard;
import net.staticstudios.prisons.UI.tablist.TabList;
import net.staticstudios.prisons.data.dataHandling.serverData.ServerData;
import net.staticstudios.prisons.gui.newGui.EnchantMenus;
import net.staticstudios.prisons.reclaim.RerunPurchases;
import net.staticstudios.prisons.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class EventListener implements Listener {

    @EventHandler
    void onNewTick(ServerTickStartEvent e) {
        StaticPrisons.currentTick += 1;
    }
    @EventHandler
    void playerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        e.setJoinMessage(ChatColor.translateAlternateColorCodes('&', "&a&lJoined&a -> &f" + player.getName()));
        Warps.warpToSpawn(player);
        //Update the player name to UUID mappings
        ServerData.playerJoined(player);

        TabList.addPlayer(player);
        CustomScoreboard.updatePlayerScoreboard(player);
        Utils.updateLuckPermsForPlayerRanks(player); //Gives the player permission to use certain features if they have a rank
        //EnchantEffects.giveEffect(player, Utils.getItemInMainHand(player));

        //Updates a player's discord name
        DiscordLink.playerJoined(player);

        //Gives anyone who bought something during March their packages back
        RerunPurchases.playerJoined(player);
    }
    @EventHandler
    void playerQuit(PlayerQuitEvent e) {
        e.setQuitMessage(ChatColor.translateAlternateColorCodes('&', "&a&lLeft&a -> &f" + e.getPlayer().getName()));
        Player player = e.getPlayer();
        //Dump all the pickaxe stats from the buffer to all the items in the players inv
        for (ItemStack item : player.getInventory().getContents()) {
            if (!Utils.checkIsPrisonPickaxe(item)) continue;
            //PrisonPickaxe.dumpStatsToPickaxe(item);
        }

        //Remove player from the scoreboard map to prevent updating an offline player's scoreboard
        CustomScoreboard.playerLeft(player.getUniqueId().toString());
        PlayerData playerData = new PlayerData(player);
        playerData.setIsNitroBoosting(false);
    }
    @EventHandler
    void onMove(PlayerMoveEvent e) {
        Player player = e.getPlayer();

        if (e.getTo().getY() < 0) {
            Warps.warpToSpawn(player);
            return;
        }

        //Prevent players from flying between virtual grids
        int gridSize = 0; //Size of the grid
        int distanceAllowed = 0; //The distance that players may go from the center of the grid before the event gets canceled
        boolean useGrid = false; //Set if a world should lock people in a grid
        switch (player.getWorld().getName()) {
            case "mines" -> {
                gridSize = 500;
                distanceAllowed = 200;
                useGrid = true;
            }
            case "islands" -> {
                gridSize = 1000;
                distanceAllowed = 100;
                useGrid = true;
            }
        }
        if (useGrid) {
            double distanceX = Math.abs(e.getTo().getX()) % gridSize;
            double distanceZ = Math.abs(e.getTo().getZ()) % gridSize;
            if ((distanceX + distanceAllowed) / gridSize >= 1) distanceX = gridSize - distanceX;
            if ((distanceZ + distanceAllowed) / gridSize >= 1) distanceZ = gridSize - distanceZ;
            if (distanceX > distanceAllowed || distanceZ > distanceAllowed) e.setCancelled(true);
        }
    }

    @EventHandler
    void onInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if (Objects.equals(e.getHand(), EquipmentSlot.OFF_HAND)) return;
        if (Vouchers.onInteract(e)) return;
        if (CrateManager.checkIfCrateWasClicked(e)) return;



        if (e.getClickedBlock() != null && !e.getClickedBlock().getType().equals(Material.AIR)) {
            //Check if it is also a block place event
            if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                if (player.getInventory().getItemInMainHand().getType().isBlock() && !e.getClickedBlock().getType().isInteractable()) return;
            }
        }
        //Check if the player is holding a pickaxe and is trying to open the enchants menu
        if (e.getAction().isRightClick()) {
            if (player.isSneaking()) {
                if (Utils.checkIsPrisonPickaxe(player.getInventory().getItemInMainHand())) {
                    if (new PlayerData(player).getIsMobile()) return;
                    EnchantMenus.mainMenu(player, PrisonPickaxe.fromItem(player.getInventory().getItemInMainHand()));
                    e.setCancelled(true);
                    return;
                }
            }
            PrisonPickaxe pickaxe = PrisonPickaxe.fromItem(player.getInventory().getItemInMainHand());
            if (pickaxe != null) {
                //if (StaticPrisons.currentTick % 3 != 0) return;
                for (BaseEnchant enchant : pickaxe.getEnchants()) enchant.whileRightClicking(e, pickaxe);
            }
        }
    }

    @EventHandler
    void onChangeItemHeld(PlayerItemHeldEvent e) {
        Player player = e.getPlayer();
        ItemStack[] contents = player.getInventory().getContents();
        if (Utils.checkIsPrisonPickaxe(contents[e.getPreviousSlot()])) {
            PrisonPickaxe pickaxe = PrisonPickaxe.fromItem(contents[e.getPreviousSlot()]);
            for (BaseEnchant enchant : pickaxe.getEnchants()) enchant.onPickaxeUnHeld(player, pickaxe);
        }
        if (Utils.checkIsPrisonPickaxe(contents[e.getNewSlot()])) {
            PrisonPickaxe pickaxe = PrisonPickaxe.fromItem(contents[e.getNewSlot()]);
            //TODO error is being thrown because data is not loading properly
            for (BaseEnchant enchant : pickaxe.getEnchants()) enchant.onPickaxeHeld(player, pickaxe);
        }
    }

    @EventHandler
    void onItemPickup(PlayerAttemptPickupItemEvent e) {
        Player player = e.getPlayer();
        if (player.getInventory().firstEmpty() == -1) return;
        if (player.getInventory().firstEmpty() == player.getInventory().getHeldItemSlot()) {
            if (!Utils.checkIsPrisonPickaxe(e.getItem().getItemStack())) return;
            PrisonPickaxe pickaxe = PrisonPickaxe.fromItem(e.getItem().getItemStack());
            for (BaseEnchant enchant : pickaxe.getEnchants()) enchant.onPickaxeHeld(player, pickaxe);
        }
    }

    @EventHandler
    void onClick(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player)) return;
        Player player = (Player) e.getWhoClicked();
        if (Utils.checkIsPrisonPickaxe(e.getCurrentItem())) {
            PrisonPickaxe pickaxe = PrisonPickaxe.fromItem(e.getCurrentItem());
            if (!player.getInventory().getItemInMainHand().equals(e.getCurrentItem())) return;
            for (BaseEnchant enchant : pickaxe.getEnchants()) enchant.onPickaxeUnHeld(player, pickaxe);
            return;
        }
        if (Utils.checkIsPrisonPickaxe(e.getCursor())) {
            if (e.getClick().equals(ClickType.DOUBLE_CLICK)) return;
            if (player.getInventory().getHeldItemSlot() != e.getSlot()) return;
            PrisonPickaxe pickaxe = PrisonPickaxe.fromItem(e.getCursor());
            for (BaseEnchant enchant : pickaxe.getEnchants()) enchant.onPickaxeHeld(player, pickaxe);
        }
    }

    @EventHandler
    void onProjectileHit(ProjectileHitEvent e) {
        if (e.getEntity().getShooter() instanceof EggShooterEnchant.EggShooterPickaxe) EggShooterEnchant.eggHit(e, (EggShooterEnchant.EggShooterPickaxe) e.getEntity().getShooter());
    }
}
