package net.staticstudios.prisons.gangs;

import net.md_5.bungee.api.ChatColor;
import net.staticstudios.mines.StaticMineUtils;
import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.data.serverData.ServerData;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GangCommand implements CommandExecutor, TabCompleter {

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
            case "chest" -> {
                if (gang == null) {
                    GangMenus.openCreateGang(player, true);
                    return false;
                }
                player.openInventory(gang.getGangChest().getInventory());
            }
            case "bank" -> {
                if (gang == null) {
                    GangMenus.openCreateGang(player, true);
                    return false;
                }
                if (args.length == 1) {
                    player.sendMessage(Gang.PREFIX + ChatColor.translateAlternateColorCodes('&', "&b&lBalance: &a$" + PrisonUtils.addCommasToNumber(gang.getBankMoney()) + " &8| &e" + PrisonUtils.addCommasToNumber(gang.getBankTokens()) + " Tokens"));
                    return false;
                }
                if (args.length < 4) {
                    player.sendMessage(PrisonUtils.Commands.getCorrectUsage("Usage: /gang bank <money|tokens> <deposit|withdraw> <amount>"));
                    return false;
                }
                switch (args[1].toLowerCase()) {
                    default -> {
                        player.sendMessage(PrisonUtils.Commands.getCorrectUsage("Usage: /gang bank <money|tokens> <deposit|withdraw> <amount>"));
                        return false;
                    }
                    case "money" -> {
                        if (args[2].equalsIgnoreCase("deposit")) {
                            BigInteger amount;
                            try {
                                if (args[3].equalsIgnoreCase("all")) amount = gang.getBankMoney();
                                else amount = new BigInteger(args[3]);
                            } catch (NumberFormatException e) {
                                player.sendMessage(PrisonUtils.Commands.getCorrectUsage("Usage: /gang bank <money|tokens> <deposit|withdraw> <amount>"));
                                return false;
                            }
                            if (amount.compareTo(BigInteger.ZERO) < 1) {
                                player.sendMessage(ChatColor.RED + "Amount must be greater than 0!");
                                return false;
                            }
                            PlayerData playerData = new PlayerData(player);
                            if (playerData.getMoney().compareTo(amount) < 0) {
                                player.sendMessage(ChatColor.RED + "You don't have enough money to do this!");
                                return false;
                            }
                            playerData.removeMoney(amount);
                            gang.addBankMoney(amount);
                            player.sendMessage(Gang.PREFIX + ChatColor.translateAlternateColorCodes('&', "You have deposited &a$" + PrisonUtils.addCommasToNumber(amount) + " &finto your gang's bank!"));
                        } else if (args[2].equalsIgnoreCase("withdraw")) {
                            if (!gang.canMembersWithdrawFomBank() && !gang.getOwner().equals(player.getUniqueId())) {
                                player.sendMessage(Gang.PREFIX + ChatColor.RED + "You can't withdraw from the bank as the owner has disabled it for your gang!");
                                return false;
                            }
                            BigInteger amount;
                            try {
                                if (args[3].equalsIgnoreCase("all")) amount = gang.getBankMoney();
                                else amount = new BigInteger(args[3]);
                            } catch (NumberFormatException e) {
                                player.sendMessage(PrisonUtils.Commands.getCorrectUsage("Usage: /gang bank <money|tokens> <deposit|withdraw> <amount>"));
                                return false;
                            }
                            if (amount.compareTo(BigInteger.ZERO) < 1) {
                                player.sendMessage(ChatColor.RED + "Amount must be greater than 0!");
                                return false;
                            }
                            PlayerData playerData = new PlayerData(player);
                            if (gang.getBankMoney().compareTo(amount) < 0) {
                                player.sendMessage(ChatColor.RED + "Your gang doesn't have enough money to do this!");
                                return false;
                            }
                            playerData.addMoney(amount);
                            gang.removeBankMoney(amount);
                            player.sendMessage(Gang.PREFIX + ChatColor.translateAlternateColorCodes('&', "You withdrew &a$" + PrisonUtils.addCommasToNumber(amount) + " &ffrom your gang's bank!"));
                        } else {
                            player.sendMessage(PrisonUtils.Commands.getCorrectUsage("Usage: /gang bank <money|tokens> <deposit|withdraw> <amount>"));
                            return false;
                        }
                    }
                    case "tokens" -> {
                        if (args[2].equalsIgnoreCase("deposit")) {
                            BigInteger amount;
                            try {
                                if (args[3].equalsIgnoreCase("all")) amount = gang.getBankTokens();
                                else amount = new BigInteger(args[3]);
                            } catch (NumberFormatException e) {
                                player.sendMessage(PrisonUtils.Commands.getCorrectUsage("Usage: /gang bank <money|tokens> <deposit|withdraw> <amount>"));
                                return false;
                            }
                            if (amount.compareTo(BigInteger.ZERO) < 1) {
                                player.sendMessage(ChatColor.RED + "Amount must be greater than 0!");
                                return false;
                            }
                            PlayerData playerData = new PlayerData(player);
                            if (playerData.getTokens().compareTo(amount) < 0) {
                                player.sendMessage(ChatColor.RED + "You don't have enough tokens to do this!");
                                return false;
                            }
                            playerData.removeTokens(amount);
                            gang.addBankTokens(amount);
                            player.sendMessage(Gang.PREFIX + ChatColor.translateAlternateColorCodes('&', "You have deposited &e" + PrisonUtils.addCommasToNumber(amount) + " tokens &finto your gang's bank!"));
                        } else if (args[2].equalsIgnoreCase("withdraw")) {
                            if (!gang.canMembersWithdrawFomBank() && !gang.getOwner().equals(player.getUniqueId())) {
                                player.sendMessage(Gang.PREFIX + ChatColor.RED + "You can't withdraw from the bank as the owner has disabled it for your gang!");
                                return false;
                            }
                            BigInteger amount;
                            try {
                                if (args[3].equalsIgnoreCase("all")) amount = gang.getBankTokens();
                                else amount = new BigInteger(args[3]);
                            } catch (NumberFormatException e) {
                                player.sendMessage(PrisonUtils.Commands.getCorrectUsage("Usage: /gang bank <money|tokens> <deposit|withdraw> <amount>"));
                                return false;
                            }
                            if (amount.compareTo(BigInteger.ZERO) < 1) {
                                player.sendMessage(ChatColor.RED + "Amount must be greater than 0!");
                                return false;
                            }
                            PlayerData playerData = new PlayerData(player);
                            if (gang.getBankTokens().compareTo(amount) < 0) {
                                player.sendMessage(ChatColor.RED + "Your gang doesn't have enough tokens to do this!");
                                return false;
                            }
                            playerData.addTokens(amount);
                            gang.removeBankTokens(amount);
                            player.sendMessage(Gang.PREFIX + ChatColor.translateAlternateColorCodes('&', "You withdrew &e" + PrisonUtils.addCommasToNumber(amount) + " tokens &ffrom your gang's bank!"));
                        } else {
                            player.sendMessage(PrisonUtils.Commands.getCorrectUsage("Usage: /gang bank <money|tokens> <deposit|withdraw> <amount>"));
                            return false;
                        }
                    }
                }

            }
            case "delete" -> {
                if (gang == null) {
                    GangMenus.openCreateGang(player, true);
                    return false;
                }
                if (!gang.getOwner().equals(player.getUniqueId())) {
                    player.sendMessage(Gang.PREFIX + ChatColor.RED + "You can't delete your gang as you are not the owner!");
                    return false;
                }
                if (args.length < 2 || !args[1].equalsIgnoreCase("confirm")) {
                    player.sendMessage(Gang.PREFIX + ChatColor.translateAlternateColorCodes('&', "&cAre you sure you want to delete your gang? Type &7&o/gang delete confirm &cto continue."));
                    return false;
                }
                gang.delete();
            }
        }
        return false;
    }
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> list = new ArrayList<>();
        if (args.length == 1) {
            list.add("create");
            list.add("info");
            list.add("join");
            list.add("leave");
            list.add("kick");
            list.add("promote");
            list.add("demote");
            list.add("bank");
            list.add("delete");
        }
        if (args.length == 4) {
            if (args[0].equalsIgnoreCase("bank")) {
                if (args[1].equalsIgnoreCase("money")) {
                    list.add("all");
                } else if (args[1].equalsIgnoreCase("tokens")) {
                    list.add("all");
                }
            }
        }
        return list;
    }
}
