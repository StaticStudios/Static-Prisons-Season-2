package net.staticstudios.prisons.commands;

import net.staticstudios.prisons.data.dataHandling.PlayerData;
import net.staticstudios.prisons.data.dataHandling.serverData.ServerData;
import net.staticstudios.prisons.utils.CommandUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.math.BigInteger;

public class ModifyStatsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 4) {
            sender.sendMessage(CommandUtils.getIncorrectCommandUsageMessage("/modstats <stat> <who> <modify> <value>"));
        }
        if (!ServerData.PLAYERS.getAllNamesLowercase().contains(args[1].toLowerCase()) && !args[1].equalsIgnoreCase("self")) {
            sender.sendMessage(ChatColor.RED + "Could not find the player specified!");
            return false;
        }
        PlayerData playerData;
        if (args[1].equalsIgnoreCase("self")) {
            if (sender instanceof Player) {
                playerData = new PlayerData(((Player) sender).getUniqueId());
            } else {
                sender.sendMessage(ChatColor.RED + "You cannot use self from console!");
                return false;
            }
        } else playerData = new PlayerData(ServerData.PLAYERS.getUUIDIgnoreCase(args[1].toLowerCase()));
        switch (args[0].toLowerCase()) {
            case "minerank" -> {
                int amount;
                try {
                    amount = Integer.parseInt(args[3]);
                } catch (NumberFormatException e) {
                    sender.sendMessage(CommandUtils.getIncorrectCommandUsageMessage("/modstats minerank <who> <add|remove|set|reset> <amount>"));
                    return false;
                }
                switch (args[2].toLowerCase()) {
                    case "add" -> playerData.addMineRank(amount);
                    case "remove" -> playerData.removeMineRank(amount);
                    case "set" -> playerData.setMineRank(amount);
                    case "reset" -> playerData.setMineRank(0);
                    default -> sender.sendMessage(CommandUtils.getIncorrectCommandUsageMessage("/modstats minerank <who> <add|remove|set|reset> <amount>"));
                }
            }
            case "prestige" -> {
                BigInteger amount;
                try {
                    amount = new BigInteger(args[3]);
                } catch (NumberFormatException e) {
                    sender.sendMessage(CommandUtils.getIncorrectCommandUsageMessage("/modstats prestige <who> <add|remove|set|reset> <amount>"));
                    return false;
                }
                switch (args[2].toLowerCase()) {
                    case "add" -> playerData.addPrestige(amount);
                    case "remove" -> playerData.removePrestige(amount);
                    case "set" -> playerData.setPrestige(amount);
                    case "reset" -> playerData.setPrestige(BigInteger.ZERO);
                    default -> sender.sendMessage(CommandUtils.getIncorrectCommandUsageMessage("/modstats prestige <who> <add|remove|set|reset> <amount>"));
                }
            }
            case "backpack" -> {
                BigInteger amount;
                try {
                    amount = new BigInteger(args[3]);
                } catch (NumberFormatException e) {
                    sender.sendMessage(CommandUtils.getIncorrectCommandUsageMessage("/modstats backpack <who> <add|remove|set|reset> <amount>"));
                    return false;
                }
                switch (args[2].toLowerCase()) {
                    case "add" -> playerData.setBackpackSize(playerData.getBackpackSize().add(amount));
                    case "remove" -> playerData.setBackpackSize(playerData.getBackpackSize().subtract(amount));
                    case "set" -> playerData.setBackpackSize(amount);
                    case "reset" -> playerData.setBackpackSize(BigInteger.ZERO);
                    default -> sender.sendMessage(CommandUtils.getIncorrectCommandUsageMessage("/modstats backpack <who> <add|remove|set|reset> <amount>"));
                }
            }
            case "blocksmined" -> {
                BigInteger amount;
                try {
                    amount = new BigInteger(args[3]);
                } catch (NumberFormatException e) {
                    sender.sendMessage(CommandUtils.getIncorrectCommandUsageMessage("/modstats blocksmined <who> <add|remove|set|reset> <amount>"));
                    return false;
                }
                switch (args[2].toLowerCase()) {
                    case "add" -> playerData.addBlocksMined(amount);
                    case "remove" -> playerData.removeBlocksMined(amount);
                    case "set" -> playerData.setBlocksMined(amount);
                    case "reset" -> playerData.setBlocksMined(BigInteger.ZERO);
                    default -> sender.sendMessage(CommandUtils.getIncorrectCommandUsageMessage("/modstats blocksmined <who> <add|remove|set|reset> <amount>"));
                }
            }
            case "rawblocks", "rawblocksmined" -> {
                BigInteger amount;
                try {
                    amount = new BigInteger(args[3]);
                } catch (NumberFormatException e) {
                    sender.sendMessage(CommandUtils.getIncorrectCommandUsageMessage("/modstats rawblocks <who> <add|remove|set|reset> <amount>"));
                    return false;
                }
                switch (args[2].toLowerCase()) {
                    case "add" -> playerData.addRawBlocksMined(amount);
                    case "remove" -> playerData.removeRawBlocksMined(amount);
                    case "set" -> playerData.setRawBlocksMined(amount);
                    case "reset" -> playerData.setRawBlocksMined(BigInteger.ZERO);
                    default -> sender.sendMessage(CommandUtils.getIncorrectCommandUsageMessage("/modstats rawblocks <who> <add|remove|set|reset> <amount>"));
                }
            }
            case "pmine", "privatemine" -> {
                switch (args[2].toLowerCase()) {
                    case "set" -> {
                        int amount;
                        try {
                            amount = Integer.parseInt(args[3]);
                        } catch (NumberFormatException e) {
                            sender.sendMessage(CommandUtils.getIncorrectCommandUsageMessage("/modstats pmine <who> <set> <amount>"));
                            return false;
                        }
                        playerData.setPrivateMineSquareSize(amount);
                        playerData.setHasPrivateMine(true);
                        playerData.setPrivateMineMat(Material.STONE);
                    }
                    case "reset" -> playerData.setHasPrivateMine(false);
                    default -> sender.sendMessage(CommandUtils.getIncorrectCommandUsageMessage("/modstats pmine <who> <set|reset>"));
                }
            }
            case "votes", "vote" -> {
                BigInteger amount;
                try {
                    amount = new BigInteger(args[3]);
                } catch (NumberFormatException e) {
                    sender.sendMessage(CommandUtils.getIncorrectCommandUsageMessage("/modstats votes <who> <add|remove|set|reset> <amount>"));
                    return false;
                }
                switch (args[2].toLowerCase()) {
                    case "add" -> playerData.addVotes(amount);
                    case "remove" -> playerData.removeVotes(amount);
                    case "set" -> playerData.setVotes(amount);
                    case "reset" -> playerData.setVotes(BigInteger.ZERO);
                    default -> sender.sendMessage(CommandUtils.getIncorrectCommandUsageMessage("/modstats votes <who> <add|remove|set|reset> <amount>"));
                }
            }
            case "timeplayed" -> {
                BigInteger amount;
                try {
                    amount = new BigInteger(args[3]);
                } catch (NumberFormatException e) {
                    sender.sendMessage(CommandUtils.getIncorrectCommandUsageMessage("/modstats timeplayed <who> <add|remove|set|reset> <amount>"));
                    return false;
                }
                switch (args[2].toLowerCase()) {
                    case "add" -> playerData.addTimePlayed(amount);
                    case "remove" -> playerData.removeTimePlayed(amount);
                    case "set" -> playerData.setTimePlayed(amount);
                    case "reset" -> playerData.setTimePlayed(BigInteger.ZERO);
                    default -> sender.sendMessage(CommandUtils.getIncorrectCommandUsageMessage("/modstats timeplayed <who> <add|remove|set|reset> <amount>"));
                }
            }
            case "money" -> {
                BigInteger amount;
                try {
                    amount = new BigInteger(args[3]);
                } catch (NumberFormatException e) {
                    sender.sendMessage(CommandUtils.getIncorrectCommandUsageMessage("/modstats money <who> <add|remove|set|reset> <amount>"));
                    return false;
                }
                switch (args[2].toLowerCase()) {
                    case "add" -> playerData.addMoney(amount);
                    case "remove" -> playerData.removeMoney(amount);
                    case "set" -> playerData.setMoney(amount);
                    case "reset" -> playerData.setMoney(BigInteger.ZERO);
                    default -> sender.sendMessage(CommandUtils.getIncorrectCommandUsageMessage("/modstats money <who> <add|remove|set|reset> <amount>"));
                }
            }
            case "tokens" -> {
                BigInteger amount;
                try {
                    amount = new BigInteger(args[3]);
                } catch (NumberFormatException e) {
                    sender.sendMessage(CommandUtils.getIncorrectCommandUsageMessage("/modstats tokens <who> <add|remove|set|reset> <amount>"));
                    return false;
                }
                switch (args[2].toLowerCase()) {
                    case "add" -> playerData.addTokens(amount);
                    case "remove" -> playerData.removeTokens(amount);
                    case "set" -> playerData.setTokens(amount);
                    case "reset" -> playerData.setTokens(BigInteger.ZERO);
                    default -> sender.sendMessage(CommandUtils.getIncorrectCommandUsageMessage("/modstats tokens <who> <add|remove|set|reset> <amount>"));
                }
            }
        }
        return true;
    }
}
