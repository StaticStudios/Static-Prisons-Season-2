package net.staticstudios.prisons.utils;

import com.destroystokyo.paper.event.server.ServerTickStartEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.staticstudios.gui.StaticGUI;
import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.UI.scoreboard.CustomScoreboard;
import net.staticstudios.prisons.UI.tablist.TabList;
import net.staticstudios.prisons.commands.admin.AdminManager;
import net.staticstudios.prisons.customItems.Vouchers;
import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.data.serverData.ServerData;
import net.staticstudios.prisons.external.DiscordLink;
import net.staticstudios.prisons.pickaxe.PrisonPickaxe;
import net.staticstudios.prisons.pickaxe.enchants.EggShooterEnchant;
import net.staticstudios.prisons.pickaxe.enchants.handler.BaseEnchant;
import org.bukkit.GameMode;
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
    void playerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        player.setInvisible(false);

        if (AdminManager.checkIfPlayerInVanishAtJoin(event.getPlayer())) {
            event.joinMessage(Component.empty());
        } else {
            event.joinMessage(Component.text("Joined", ComponentUtil.GREEN).decorate(TextDecoration.BOLD)
                    .append(Component.text(" -> ")).decoration(TextDecoration.BOLD, false)
                    .append(player.name().color(ComponentUtil.WHITE)));
        }

        Warps.warpToSpawn(player);
        //Update the player name to UUID mappings
        ServerData.playerJoined(player);

        TabList.addPlayer(player);
        CustomScoreboard.updatePlayerScoreboard(player);
        PrisonUtils.updateLuckPermsForPlayerRanks(player); //Gives the player permission to use certain features if they have a rank
        //EnchantEffects.giveEffect(player, Utils.getItemInMainHand(player));

        //Updates a player's discord name
        DiscordLink.playerJoined(player);
    }
    @EventHandler
    void playerQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();

        if (AdminManager.containedInHiddenPlayers(player)) {
            e.quitMessage(Component.empty());
        } else {
//            e.setQuitMessage(ChatColor.translateAlternateColorCodes('&', "&a&lLeft&a -> &f" + e.getPlayer().getName()));
            e.quitMessage(Component.text("Left").color(ComponentUtil.RED).decorate(TextDecoration.BOLD)
                    .append(Component.text(" -> ")).decoration(TextDecoration.BOLD, false)
                    .append(player.name().color(ComponentUtil.WHITE)));
        }

        for (ItemStack item : player.getInventory().getContents()) {
            if (PrisonUtils.checkIsPrisonPickaxe(item)) PrisonPickaxe.updateLore(item);
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
            if (e.getPlayer().getGameMode().equals(GameMode.CREATIVE) || e.getPlayer().getGameMode().equals(GameMode.SPECTATOR)) return;
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
        //if (CrateManager.checkIfCrateWasClicked(e)) return;



        if (e.getClickedBlock() != null && !e.getClickedBlock().getType().equals(Material.AIR)) {
            //Check if it is also a block place event
            if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                if (player.getInventory().getItemInMainHand().getType().isBlock() && !e.getClickedBlock().getType().isInteractable()) return;
            }
        }
    }

    @EventHandler
    void onChangeItemHeld(PlayerItemHeldEvent e) {
        Player player = e.getPlayer();
        ItemStack[] contents = player.getInventory().getContents();
        if (PrisonUtils.checkIsPrisonPickaxe(contents[e.getPreviousSlot()])) {
            PrisonPickaxe pickaxe = PrisonPickaxe.fromItem(contents[e.getPreviousSlot()]);
            for (BaseEnchant enchant : pickaxe.getEnchants()) {
                if (!pickaxe.getIsEnchantEnabled(enchant)) continue;
                enchant.onPickaxeUnHeld(player, pickaxe);
            }
        }
        if (PrisonUtils.checkIsPrisonPickaxe(contents[e.getNewSlot()])) {
            PrisonPickaxe pickaxe = PrisonPickaxe.fromItem(contents[e.getNewSlot()]);
            for (BaseEnchant enchant : pickaxe.getEnchants()) {
                if (!pickaxe.getIsEnchantEnabled(enchant)) continue;
                enchant.onPickaxeHeld(player, pickaxe);
            }
        }
    }

    @EventHandler
    void onItemPickup(PlayerAttemptPickupItemEvent e) {
        Player player = e.getPlayer();
        if (player.getInventory().firstEmpty() == -1) return;
        if (player.getInventory().firstEmpty() == player.getInventory().getHeldItemSlot()) {
            if (!PrisonUtils.checkIsPrisonPickaxe(e.getItem().getItemStack())) return;
            PrisonPickaxe pickaxe = PrisonPickaxe.fromItem(e.getItem().getItemStack());
            for (BaseEnchant enchant : pickaxe.getEnchants()) {
                if (!pickaxe.getIsEnchantEnabled(enchant)) continue;
                enchant.onPickaxeHeld(player, pickaxe);
            }
        }
    }

    @EventHandler
    void onClick(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player player)) return;
        if (PrisonUtils.checkIsPrisonPickaxe(e.getCurrentItem())) {
            if (e.getClickedInventory() != null && e.getClickedInventory().getHolder() instanceof StaticGUI) return;
            PrisonPickaxe pickaxe = PrisonPickaxe.fromItem(e.getCurrentItem());
            if (!player.getInventory().getItemInMainHand().equals(e.getCurrentItem())) return;
            for (BaseEnchant enchant : pickaxe.getEnchants()) {
                if (!pickaxe.getIsEnchantEnabled(enchant)) continue;
                enchant.onPickaxeUnHeld(player, pickaxe);
            }
            return;
        }
        if (PrisonUtils.checkIsPrisonPickaxe(e.getCursor())) {
            if (e.getClickedInventory() != null && e.getClickedInventory().getHolder() instanceof StaticGUI) return;
            if (e.getClick().equals(ClickType.DOUBLE_CLICK)) return;
            if (player.getInventory().getHeldItemSlot() != e.getSlot()) return;
            PrisonPickaxe pickaxe = PrisonPickaxe.fromItem(e.getCursor());
            for (BaseEnchant enchant : pickaxe.getEnchants()) {
                if (!pickaxe.getIsEnchantEnabled(enchant)) continue;
                enchant.onPickaxeHeld(player, pickaxe);
            }
        }
    }

    @EventHandler
    void onProjectileHit(ProjectileHitEvent e) {
        if (e.getEntity().getShooter() instanceof EggShooterEnchant.EggShooterPickaxe) {
            EggShooterEnchant.eggHit(e, (EggShooterEnchant.EggShooterPickaxe) e.getEntity().getShooter());
        }
    }
}
