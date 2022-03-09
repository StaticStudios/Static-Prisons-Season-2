package me.staticstudios.prisons;

import me.staticstudios.prisons.crates.CrateManager;
import me.staticstudios.prisons.customItems.Vouchers;
import me.staticstudios.prisons.data.serverData.PlayerData;
import me.staticstudios.prisons.data.serverData.ServerData;
import me.staticstudios.prisons.discord.DiscordAddRoles;
import me.staticstudios.prisons.discord.DiscordBot;
import me.staticstudios.prisons.discord.LinkHandler;
import me.staticstudios.prisons.enchants.EnchantEffects;
import me.staticstudios.prisons.enchants.PrisonEnchants;
import me.staticstudios.prisons.events.EventManager;
import me.staticstudios.prisons.gui.GUI;
import me.staticstudios.prisons.misc.Warps;
import me.staticstudios.prisons.misc.chat.CustomChatMessage;
import me.staticstudios.prisons.misc.scoreboard.CustomScoreboard;
import me.staticstudios.prisons.misc.tablist.TabList;
import me.staticstudios.prisons.utils.Utils;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.persistence.PersistentDataType;

import java.time.Instant;

public class Events implements Listener {
    @EventHandler
    void playerJoin(PlayerJoinEvent e) {
        e.setJoinMessage(ChatColor.translateAlternateColorCodes('&', "&a&lJoined&a -> &f" + e.getPlayer().getName()));
        Player player = e.getPlayer();
        Warps.warpToSpawn(player);
        //Check if they have joined before
        if (!new ServerData().checkIfPlayerHasJoinedBeforeByUUID(player.getUniqueId().toString())) {
            Bukkit.broadcastMessage(org.bukkit.ChatColor.LIGHT_PURPLE + player.getName() + org.bukkit.ChatColor.GREEN + " joined for the first time! " + org.bukkit.ChatColor.GRAY + "(" + "#" + Utils.addCommasToNumber(new ServerData().getPlayerUUIDsToNamesMap().size() + 1) + ")");
            Utils.addItemToPlayersInventoryAndDropExtra(player, Utils.createNewPickaxe());
            PlayerData playerData = new PlayerData(player);
            playerData.setPlayerRank("member");
            playerData.setStaffRank("member");
        }
        //Update the playernames
        new ServerData().putPlayerNamesToUUID(player.getName(), player.getUniqueId().toString());
        new ServerData().putPlayerUUIDsToName(player.getUniqueId().toString(), player.getName());

        //Set up the player's tablist
        TabList.onJoin(player);
        //Set up the player's scoreboard
        CustomScoreboard.updateSingleScoreboard(player);
        //Update lukperms for player ranks
        Utils.updateLuckPermsForPlayerRanks(player);

        //Update potion effects based off custom enchants
        EnchantEffects.giveEffect(e.getPlayer(), e.getPlayer().getInventory().getItemInMainHand());

        //Updates a player's discord name
        PlayerData playerData = new PlayerData(player);
        playerData.setDiscordName("null");
        //Check if a player is boosting the discord
        playerData.setIsNitroBoosting(false);
        if (LinkHandler.checkIfLinkedFromUUID(player.getUniqueId().toString())) {
            try {
                Bukkit.getScheduler().runTaskAsynchronously(Main.getMain(), () -> {
                    User user = DiscordBot.jda.retrieveUserById(LinkHandler.getLinkedDiscordIDFromUUID(player.getUniqueId().toString())).complete();
                    playerData.setDiscordName(user.getName() + "#" + user.getDiscriminator());
                });
            } catch (Exception ignore) {
                LinkHandler.unlinkFromUUID(player.getUniqueId().toString());
            }
            try {
                DiscordAddRoles.giveRolesFromUUID(player.getUniqueId().toString());
                Bukkit.getScheduler().runTaskAsynchronously(Main.getMain(), () -> {
                    DiscordBot.jda.getGuildById("587372348294955009").retrieveMemberById(LinkHandler.getLinkedDiscordIDFromUUID(player.getUniqueId().toString())).queue(member -> {
                        for (Role role : member.getRoles()) {
                            if (role.getId().equals("629662637625442305")) {
                                playerData.setIsNitroBoosting(true);
                                break;
                            }
                        }
                    });
                });
            } catch (Exception ex) {
                ex.printStackTrace();
                player.sendMessage(ChatColor.RED + "We were unable to get your linked discord information.");
            }
            Bukkit.getScheduler().runTaskLater(Main.getMain(), () -> {
                if (playerData.getIsAutoSellEnabled() && !playerData.getCanEnableAutoSell() && !playerData.getPlayerRanks().contains("warrior") && !playerData.getIsNitroBoosting())
                    playerData.setIsAutoSellEnabled(false);
            }, 20 * 20);
        }

    }
    @EventHandler
    void playerQuit(PlayerQuitEvent e) {
        e.setQuitMessage(ChatColor.translateAlternateColorCodes('&', "&a&lLeft&a -> &f" + e.getPlayer().getName()));
        Player player = e.getPlayer();
        //Remove player from the scoreboard map to prevent updating an offline player's scoreboard
        CustomScoreboard.playerLeft(player.getUniqueId().toString());
        PlayerData playerData = new PlayerData(player);
        playerData.setIsNitroBoosting(false);
    }
    @EventHandler
    void onChat(AsyncPlayerChatEvent e) {
        new CustomChatMessage(e).sendFormatted();
        EventManager.chatMessageReceived(e);
    }
    @EventHandler
    void onChangeHeld(PlayerItemHeldEvent e) {
        EnchantEffects.giveEffect(e.getPlayer(), e.getPlayer().getInventory().getItem(e.getNewSlot()));
    }
    @EventHandler
    void invClick(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player)) return;
        EnchantEffects.giveEffect((Player) e.getWhoClicked(), e.getWhoClicked().getInventory().getItemInMainHand());
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
        if (e.getHand() != null) {
            if (e.getHand().equals(EquipmentSlot.OFF_HAND)) return;
        }
        if (Vouchers.onInteract(e)) return;
        if (e.getClickedBlock() != null && !e.getClickedBlock().getType().equals(Material.AIR)) {
            //Check if it is also a block place event
            if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                if (player.getInventory().getItemInMainHand().getType().isBlock() && !e.getClickedBlock().getType().isInteractable()) {
                    return;
                }
            }
        }
        //Check if the player is holding a pickaxe and is trying to open the enchants menu
        if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (Utils.checkIsPrisonPickaxe(player.getInventory().getItemInMainHand())) {
                if (new PlayerData(player).getIsMobile()) return;
                String id = player.getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer().get(new NamespacedKey(Main.getMain(), "pickaxeUUID"), PersistentDataType.STRING);
                PrisonEnchants.playerUUIDToPickaxeID.put(player.getUniqueId(), id);
                GUI.getGUIPage("enchantsMain").open(player);
                e.setCancelled(true);
                return;
            }
        }
        if (CrateManager.checkIfCrateWasClicked(e)) return;
    }
    @EventHandler
    void onDrop(PlayerDropItemEvent e) {
        if (Utils.checkIsPrisonPickaxe(e.getItemDrop().getItemStack())) {
            e.setCancelled(true);
            e.getPlayer().sendMessage(ChatColor.RED + "You cannot drop this item! Type /dropitem to drop it!");
        }
    }
}
