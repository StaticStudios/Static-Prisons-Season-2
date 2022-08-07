package net.staticstudios.prisons.privatemines;


import net.staticstudios.mines.StaticMineUtils;
import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.data.serverdata.ServerData;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PrivateMineCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player player)) return false;
        if (!PrivateMine.finishedInitTasks) return false;
        PlayerData playerData = new PlayerData(player);
        if (args.length == 0) {
            PrivateMineMenus.open(player, true);
            return false;
        }
        PrivateMine unloadedPrivateMine = PrivateMine.getPrivateMineFromPlayerWithoutLoading(player);
        switch (args[0].toLowerCase()) {
            default -> PrivateMineMenus.open(player, true);
            case "refill" -> {
                if (!PrivateMine.playerHasPrivateMine(player)) {
                    PrivateMine.sendMessage(player, "&cYou don't have a private mine!");
                    return false;
                }
                if (!unloadedPrivateMine.isLoaded) {
                    unloadedPrivateMine.messageOwner("&bLoading your private mine...");
                    PrivateMine.getPrivateMineFromPlayer(player).thenAccept(pm -> pm.warpTo(player));
                } else PrivateMine.getPrivateMineFromPlayer(player).thenAccept(pm -> pm.manualRefill(player));
            }
            case "info", "about" -> {
                if (unloadedPrivateMine == null) {
                    PrivateMine.sendMessage(player, "&cYou don't have a private mine!");
                    return false;
                }
                unloadedPrivateMine.sendInfo(player);
            }
            case "go", "warp" -> {
                if (!PrivateMine.playerHasPrivateMine(player)) {
                    PrivateMine.sendMessage(player, "&cYou don't have a private mine!");
                    return false;
                }
                if (!unloadedPrivateMine.isLoaded) {
                    unloadedPrivateMine.messageOwner("&bLoading your private mine...");
                }
                PrivateMine.getPrivateMineFromPlayer(player).thenAccept(pm -> pm.warpTo(player));
            }
            case "invite", "inv" -> {
                if (!PrivateMine.playerHasPrivateMine(player)) {
                    PrivateMine.sendMessage(player, "&cYou don't have a private mine!");
                    return false;
                }
                if (args.length == 1) {
                    player.sendMessage(PrisonUtils.Commands.getCorrectUsage("/pmine invite <who>"));
                    return false;
                }
                UUID uuid = ServerData.PLAYERS.getUUIDIgnoreCase(args[1]);
                if (uuid == null) {
                    PrivateMine.sendMessage(player, "&cPlayer not found!");
                    return false;
                }
                if (uuid.equals(player.getUniqueId())) {
                    PrivateMine.sendMessage(player, "&cYou can't invite yourself!");
                    return false;
                }
                if (unloadedPrivateMine.getWhitelist().contains(uuid)) {
                    PrivateMine.sendMessage(player, "&cThat player is already invited to your private mine!");
                    return false;
                }
                unloadedPrivateMine.addToWhitelist(uuid);
                player.sendMessage("You Invited " + ChatColor.AQUA + args[1] + ChatColor.WHITE + " to your private mine!");
                if (Bukkit.getPlayer(uuid) != null) PrivateMine.sendMessage(Bukkit.getPlayer(uuid), ChatColor.AQUA + player.getName() + ChatColor.WHITE + " invited you to their private mine!");
            }
            case "upgrade" -> PrivateMineMenus.upgrade(player, true);
        }
        return false;
    }
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> list = new ArrayList<>();
        if (args.length == 1) {
            list.add("refill");
            list.add("info");
            list.add("go");
            list.add("invite");
            list.add("inv");
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("invite")) list.addAll(StaticMineUtils.filterStringList(ServerData.PLAYERS.getAllNames(), args[1]));
        }
        return list;
    }
}
