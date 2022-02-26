package me.staticstudios.prisons;

import me.staticstudios.prisons.crates.CrateManager;
import me.staticstudios.prisons.customItems.Vouchers;
import me.staticstudios.prisons.data.serverData.PlayerData;
import me.staticstudios.prisons.data.serverData.ServerData;
import me.staticstudios.prisons.discord.DiscordAddRoles;
import me.staticstudios.prisons.discord.DiscordBot;
import me.staticstudios.prisons.discord.LinkHandler;
import me.staticstudios.prisons.enchants.EnchantEffects;
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

public class Events implements Listener {
    @EventHandler
    void playerJoin(PlayerJoinEvent e) {
        e.setJoinMessage(ChatColor.translateAlternateColorCodes('&', "&d&l[Join] &f" + e.getPlayer().getName()));
        Player player = e.getPlayer();
        Warps.warpToSpawn(player);
        //Check if they have joined before
        if (!new ServerData().checkIfPlayerHasJoinedBeforeByUUID(player.getUniqueId().toString())) {
            Bukkit.broadcastMessage(org.bukkit.ChatColor.LIGHT_PURPLE + player.getName() + org.bukkit.ChatColor.GREEN + " has joined the server for the first time! " + org.bukkit.ChatColor.GRAY + "(" + org.bukkit.ChatColor.AQUA + "#" + Utils.addCommasToInt(new ServerData().getPlayerUUIDsToNamesMap().size() + 1) + org.bukkit.ChatColor.GRAY + ")");
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
                User user = DiscordBot.jda.retrieveUserById(LinkHandler.getLinkedDiscordIDFromUUID(player.getUniqueId().toString())).complete();
                playerData.setDiscordName(user.getName() + "#" + user.getDiscriminator());
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
        e.setQuitMessage(ChatColor.translateAlternateColorCodes('&', "&d&l[Left] &f" + e.getPlayer().getName()));
        Player player = e.getPlayer();
        //Remove player from the scoreboard map to prevent updating an offline player's scoreboard
        CustomScoreboard.playerLeft(player.getUniqueId().toString());
        PlayerData playerData = new PlayerData(player);
        playerData.setIsNitroBoosting(false);
    }
    @EventHandler
    void onChat(AsyncPlayerChatEvent e) {
        new CustomChatMessage(e).sendFormatted();
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

        //Prevent players from flying between mines
        if (player.getWorld().getName().equals("mines")) {
            int x = Math.abs((int) e.getTo().getX());
            int z = ((int) e.getTo().getZ());
            if (x % 250 <= 50 && (x % 500 > 100 || e.getTo().getX() < 0) && x > 200) {
                e.setCancelled(true);
            } else if (z > 250 || z < -250) e.setCancelled(true);
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
                GUI.getGUIPage("enchantsMain").args = player.getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer().get(new NamespacedKey(Main.getMain(), "pickaxeUUID"), PersistentDataType.STRING);
                GUI.getGUIPage("enchantsMain").open(player);
                e.setCancelled(true);
                return;
            }
        }
        if (CrateManager.checkIfCrateWasClicked(e)) return;

    }
}
