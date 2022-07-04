package net.staticstudios.prisons.privateMines;


import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.data.serverData.ServerData;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PrivateMineCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player player)) return false;
        if (!PrivateMine.finishedInitTasks) return false;
        PlayerData playerData = new PlayerData(player);
        if (args.length == 0) {
            PrivateMineMenus.open(player, true);
            return false;
        }
        switch (args[0].toLowerCase()) {
            default -> PrivateMineMenus.open(player, true);
            case "refill" -> {
                if (!PrivateMine.playerHasPrivateMine(player)) {
                    player.sendMessage("You don't have a private mine!");
                    return false;
                }
                if (!PrivateMine.getPrivateMineFromPlayerWithoutLoading(player).isLoaded) {
                    player.sendMessage(ChatColor.AQUA + "Loading your private mine...");
                    PrivateMine.getPrivateMineFromPlayer(player).thenAccept(pm -> pm.warpTo(player));
                } else PrivateMine.getPrivateMineFromPlayer(player).thenAccept(pm -> pm.manualRefill(player));
            }
            case "info", "about" -> PrivateMine.getPrivateMineFromPlayerWithoutLoading(player).sendInfo(player);
            case "go", "warp" -> {
                if (!PrivateMine.playerHasPrivateMine(player)) {
                    player.sendMessage("You don't have a private mine!");
                    return false;
                }
                if (!PrivateMine.getPrivateMineFromPlayerWithoutLoading(player).isLoaded) player.sendMessage(ChatColor.AQUA + "Loading your private mine...");
                PrivateMine.getPrivateMineFromPlayer(player).thenAccept(pm -> pm.warpTo(player));
            }
            case "invite", "inv" -> {
                if (!PrivateMine.playerHasPrivateMine(player)) {
                    player.sendMessage("You don't have a private mine!");
                    return false;
                }
                if (args.length == 1) {
                    player.sendMessage(PrisonUtils.Commands.getCorrectUsage("/pmine invite <who>"));
                    return false;
                }
                UUID uuid = ServerData.PLAYERS.getUUIDIgnoreCase(args[1]);
                if (uuid == null) {
                    player.sendMessage(ChatColor.RED + "Player not found!");
                    return false;
                }
                if (uuid.equals(player.getUniqueId())) {
                    player.sendMessage(ChatColor.RED + "You can't invite yourself!");
                    return false;
                }
                PrivateMine pmine = PrivateMine.getPrivateMineFromPlayerWithoutLoading(player);
                if (pmine.getWhitelist().contains(uuid)) {
                    player.sendMessage(ChatColor.RED + "That player is already invited to your private mine!");
                    return false;
                }
                pmine.addToWhitelist(uuid);
                player.sendMessage("You Invited " + ChatColor.AQUA + args[1] + ChatColor.WHITE + " to your private mine!");
                if (Bukkit.getPlayer(uuid) != null) Bukkit.getPlayer(uuid).sendMessage(ChatColor.AQUA + player.getName() + ChatColor.WHITE + " invited you to their private mine!");
            }
        }
        return false;
    }
    //todo tab completion
}
