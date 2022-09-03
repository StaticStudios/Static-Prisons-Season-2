package net.staticstudios.prisons.pvp;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.backpacks.Backpack;
import net.staticstudios.prisons.customitems.Vouchers;
import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.gangs.Gang;
import net.staticstudios.prisons.pickaxe.PrisonPickaxe;
import net.staticstudios.prisons.pvp.commands.PvPEventCommand;
import net.staticstudios.prisons.pvp.koth.KingOfTheHillManager;
import net.staticstudios.prisons.pvp.outposts.OutpostManager;
import net.staticstudios.prisons.utils.PrisonUtils;
import net.staticstudios.prisons.utils.Warps;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class PvPManager {

    public static final String PREFIX = ChatColor.translateAlternateColorCodes('&', "&c&lPvP &8&l>> &r");

    public static World PVP_WORLD;
    public static com.sk89q.worldedit.world.World WE_PVP_WORLD;

    public static Map<Player, Long> playerWarpOutIn = new HashMap<>();

    public static void init() {
        PVP_WORLD = new WorldCreator("pvp").createWorld(); //Ensure the world exists
        WE_PVP_WORLD = BukkitAdapter.adapt(PVP_WORLD);

        Bukkit.getPluginManager().registerEvents(new Listener(), StaticPrisons.getInstance());

        KingOfTheHillManager.init();
        OutpostManager.init();

        StaticPrisons.getInstance().getCommand("pvpevent").setExecutor(new PvPEventCommand());

        //Worker for managing warp-out timings
        Bukkit.getScheduler().runTaskTimer(StaticPrisons.getInstance(), () -> {
            for (Map.Entry<Player, Long> entry : playerWarpOutIn.entrySet()) {
                if (entry.getValue() > 0 && entry.getValue() <= 5)
                    entry.getKey().sendMessage(PREFIX + ChatColor.GREEN + "Warping to spawn in " + entry.getValue() + "...");
                entry.setValue(entry.getValue() - 1);
                if (entry.getValue() <= -1) Warps.warpToSpawn(entry.getKey());
            }
        }, 20, 20);
    }


    static class Listener implements org.bukkit.event.Listener {

        @EventHandler
        void onDeath(PlayerDeathEvent e) {
            //Players should not drop any pickaxes
            //A money voucher should be created for 15% of a players total money
            Player player = e.getEntity();
            if (!player.getWorld().equals(PVP_WORLD)) return;
            List<ItemStack> pickaxes = new LinkedList<>();
            for (ItemStack item : e.getDrops()) {
                if (PrisonPickaxe.checkIsPrisonPickaxe(item)) pickaxes.add(item);
            }
            e.getDrops().removeAll(pickaxes);
            e.getItemsToKeep().addAll(pickaxes);
            List<ItemStack> backpacks = new LinkedList<>();
            for (ItemStack item : e.getDrops()) {
                if (Backpack.fromItem(item) != null) backpacks.add(item);
            }
            e.getDrops().removeAll(backpacks);
            e.getItemsToKeep().addAll(backpacks);

            PlayerData playerData = new PlayerData(player);
            long moneyToDrop = playerData.getMoney() / 15;
            if (moneyToDrop > 0) {
                playerData.removeMoney(moneyToDrop);
                player.sendMessage(PREFIX + ChatColor.RED + "You lost $" + PrisonUtils.prettyNum(moneyToDrop) + " because you died");
                e.getDrops().add(Vouchers.getMoneyNote(player.getName() + ChatColor.RED + " (From Death)", moneyToDrop));
            }
        }

        @EventHandler
        void onRespawn(PlayerRespawnEvent e) {
            Warps.warpToSpawn(e.getPlayer()); //tp to spawn on respawn
        }

        @EventHandler
        void onHit(EntityDamageByEntityEvent e) {
            if (!(e.getDamager() instanceof Player attacker && e.getEntity() instanceof Player gotHit))
                return; //Ensure a player hit a player
            Location loc = e.getDamager().getLocation();
            if (!loc.getWorld().equals(PVP_WORLD)) return; //Ensure the player is in the pvp world

            Location loc2 = e.getEntity().getLocation();
            if (loc.getY() > 135 && loc.getY() < 150 && loc.getX() > 30 && loc.getX() < 50 && loc.getZ() > 40 && loc.getZ() < 60 &&
                    loc2.getY() > 135 && loc2.getY() < 150 && loc2.getX() > 30 && loc2.getX() < 50 && loc2.getZ() > 40 && loc2.getZ() < 60) {
                e.setCancelled(true); //Player is at the PvP warp
                return;
            }


            Gang attackerGang = Gang.getGang(attacker);
            Gang gotHitGang = Gang.getGang(gotHit);
            if (attackerGang != null && attackerGang.equals(gotHitGang) && !attackerGang.isFriendlyFire()) {
                e.setCancelled(true); //Player is in the same gang and friendly fire is not enabled
                return;
            }
        }

        @EventHandler
        void onMove(PlayerMoveEvent e) {
            Player player = e.getPlayer();
            if (!player.getWorld().equals(PVP_WORLD)) return;
            if (playerWarpOutIn.containsKey(player)) {
                playerWarpOutIn.remove(player);
                player.sendMessage(PREFIX + ChatColor.RED + "Warping canceled...");
            }
        }

        @EventHandler
        void onTeleport(PlayerTeleportEvent e) {
            Player player = e.getPlayer();
            if (!player.getWorld().equals(PVP_WORLD)) return;
            if (player.getGameMode().equals(GameMode.CREATIVE) || player.getGameMode().equals(GameMode.SPECTATOR))
                return;
            if (playerWarpOutIn.getOrDefault(player, 0L) > -1) {
                e.setCancelled(true);
                player.sendMessage(PREFIX + ChatColor.RED + "You cannot use that here! The only command you can use is " + ChatColor.GRAY + ChatColor.ITALIC + "/spawn");
            }
            playerWarpOutIn.remove(player);
        }

        @EventHandler
        void onCommand(PlayerCommandPreprocessEvent e) {
            Player player = e.getPlayer();
            if (!player.getWorld().equals(PVP_WORLD)) return;
            if (player.getGameMode().equals(GameMode.CREATIVE) || player.getGameMode().equals(GameMode.SPECTATOR))
                return;

            if (!(e.getMessage().split(" ")[0].equalsIgnoreCase("/spawn") || e.getMessage().split(" ")[0].equalsIgnoreCase("/s"))) {
                player.sendMessage(PREFIX + ChatColor.RED + "You cannot use that here! The only command you can use is " + ChatColor.GRAY + ChatColor.ITALIC + "/spawn");
            } else {
                player.sendMessage(PREFIX + ChatColor.GREEN + "Warping to spawn, don't move!");
                playerWarpOutIn.putIfAbsent(player, 5L);
            }
            e.setCancelled(true);
        }
    }
}
