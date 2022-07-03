package net.staticstudios.prisons.gangs;

import net.md_5.bungee.api.ChatColor;
import net.staticstudios.prisons.data.serverData.ServerData;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.UUID;

public class GangCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) return false;
        if (args.length == 0) {
            GangMenus.openYourGang(player, true);
            return false;
        }
        Gang gang = Gang.getGang(player);
        switch (args[0].toLowerCase()) {
            default -> GangMenus.open(player, true); //"create" will do the same thing
            case "leave" -> {
                if (gang == null) {
                    GangMenus.openCreateGang(player, true);
                    return false;
                }
                if (args.length > 2 && args[1].equalsIgnoreCase("confirm")) {
                    gang.removeMember(player.getUniqueId());
                    player.sendMessage(Gang.PREFIX + ChatColor.translateAlternateColorCodes('&', "&bYou left &a" + gang.getName() + "!"));
                    gang.messageAllMembers(Gang.PREFIX + ChatColor.translateAlternateColorCodes('&', "&c" + player.getName() + "&f left your gang!"));
                } else player.sendMessage(Gang.PREFIX + ChatColor.translateAlternateColorCodes('&', "&cAre you sure you want to leave &a" + gang.getName() + "? &7&o/gang leave confirm"));
            }
            case "kick" -> {
                if (gang == null) {
                    GangMenus.openCreateGang(player, true);
                    return false;
                }
                if (!gang.getOwner().equals(player.getUniqueId())) {
                    player.sendMessage(Gang.PREFIX + ChatColor.RED + "Only the gang owner can do this action!");
                    return false;
                }
                if (args.length == 1 ) {
                    player.sendMessage(Gang.PREFIX + ChatColor.RED + "Usage: /gang kick <player>");
                    return false;
                }
                UUID uuid = ServerData.PLAYERS.getUUIDIgnoreCase(args[1]);
                if (uuid == null || !gang.getMembers().contains(uuid)) {
                    player.sendMessage(Gang.PREFIX + ChatColor.RED + "This player is not in your gang!");
                    return false;
                }
                if (uuid.equals(gang.getOwner())) {
                    player.sendMessage(Gang.PREFIX + ChatColor.RED + "You can't kick the gang owner!");
                    return false;
                }
                gang.removeMember(uuid);
                gang.messageAllMembers(Gang.PREFIX + ChatColor.translateAlternateColorCodes('&', "&b" + player.getName() + " &fkicked &a" + ServerData.PLAYERS.getName(uuid) + " &ffrom your gang!"));
            }
            case "invite" -> {
                if (gang == null) {
                    GangMenus.openCreateGang(player, true);
                    return false;
                }
                if (args.length < 2) {
                    player.sendMessage(PrisonUtils.Commands.getCorrectUsage("Usage: /gang invite <player>"));
                    return false;
                }
                if (!gang.getOwner().equals(player.getUniqueId()) && !gang.isAcceptingInvites()) {
                    player.sendMessage(ChatColor.RED + "The gang owner has disabled invites!");
                    return false;
                }
                Player target = Bukkit.getPlayer(args[1]);
                if (target == null) {
                    player.sendMessage(ChatColor.RED + "Player not found!");
                    return false;
                }
                if (Gang.hasGang(target)) {
                    player.sendMessage(ChatColor.RED + "This player is already a member of a gang!");
                    return false;
                }
                for (GangInvite invite : GangInvite.PLAYER_INVITES.getOrDefault(target.getUniqueId(), new ArrayList<>())) {
                    if (invite.getWhat().equals(gang)) {
                        player.sendMessage(ChatColor.RED + "This player is already invited to your gang!");
                        return false;
                    }
                }
                player.sendMessage(Gang.PREFIX + ChatColor.translateAlternateColorCodes('&', "You have invited &a" + target.getName() + "&f to join your gang!"));
                new GangInvite(player, target);
            }
            case "invites", "accept", "deny", "decline", "join" -> {
                GangMenus.openGangInvites(player, true);
            }
            case "info", "about", "stats" -> {
                if (gang == null) {
                    GangMenus.openCreateGang(player, true);
                    return false;
                }
                GangMenus.openGangStats(player, true);
            }
            case "settings" -> {
                if (gang == null) {
                    GangMenus.openCreateGang(player, true);
                    return false;
                }
                if (!gang.getOwner().equals(player.getUniqueId())) {
                    player.sendMessage(Gang.PREFIX + ChatColor.translateAlternateColorCodes('&', "Only the gang owner can do this action!"));
                    return false;
                }
                GangMenus.openGangSettings(player, true);
            }
        }
        return false;
    }
}
