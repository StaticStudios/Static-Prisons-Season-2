package net.staticstudios.prisons.trading.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.staticstudios.mines.utils.StaticMineUtils;
import net.staticstudios.prisons.trading.TradeManager;
import net.staticstudios.prisons.utils.Prefix;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TradeCommand implements TabExecutor {

    private static final TradeManager tradeManager = new TradeManager();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("You must be a player to use this command");
            return false;
        }

        if (args.length == 1) {

            if ("accept".equalsIgnoreCase(args[0])) {
                tradeManager.acceptTrade(player);
                return true;
            }

            if ("decline".equalsIgnoreCase(args[0])) {
                tradeManager.declineTrade(player);
                return true;
            }

            player.sendMessage(Prefix.TRADING.append(Component.text("Usage: /trade <accept|decline|request> <player>")));
        }

        if (args.length == 2) {
            if ("accept".equalsIgnoreCase(args[0])) {
                tradeManager.acceptTrade(player);
                return true;
            }

            if ("decline".equalsIgnoreCase(args[0])) {
                tradeManager.declineTrade(player);
                return true;
            }

            if (!"request".equalsIgnoreCase(args[0])) {
                player.sendMessage(Prefix.TRADING.append(Component.text("Usage: /trade <accept|decline|request> <player>")));
                return false;
            }

            if ("cancel".equalsIgnoreCase(args[1])) {
                tradeManager.cancelRequest(player);
                return true;
            }

            if (tradeManager.hasPendingRequest(player)) {
                player.sendMessage(Prefix.TRADING.append(Component.text("You already have a pending trade request.")));
                return false;
            }

            Player target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                player.sendMessage(Prefix.TRADING
                        .append(Component.text("Player "))
                        .append(Component.text(args[1]).color(NamedTextColor.GOLD))
                        .append(Component.text(" is not online."))
                        .color(NamedTextColor.RED));
                return false;
            }

            if (Objects.equals(player, target)) {
                player.sendMessage(Prefix.TRADING.append(Component.text("You cannot trade with yourself.")));
                return false;
            }

            tradeManager.requestTrade(player, target);
        }

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {

        if (args.length == 1) {
            return StaticMineUtils.filterStrings(List.of("accept", "decline", "request"), args[0]);
        }

        if (args.length == 2 && args[0].equalsIgnoreCase("request")) {

            List<String> possibleArgs = new ArrayList<>(Bukkit.getOnlinePlayers().stream().map(Player::getName).toList());

            possibleArgs.add("cancel");

            return StaticMineUtils.filterStrings(possibleArgs, args[1]);
        }

        return null;
    }
}
