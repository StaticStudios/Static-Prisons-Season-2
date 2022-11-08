package net.staticstudios.prisons.trading;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.trading.commands.TradeCommand;
import net.staticstudios.prisons.trading.domain.Trade;
import net.staticstudios.prisons.utils.Prefix;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class TradeManager {
    private final Map<Player, Player> tradeRequests = new HashMap<>();

    public static void init() {
        Objects.requireNonNull(StaticPrisons.getInstance().getCommand("trade")).setExecutor(new TradeCommand());
    }

    public void newTrade(Player sender, Player player) {
        if (sender.getWorld().getName().equals("pvp") || player.getWorld().getName().equals("pvp")) {
            sender.sendMessage(Prefix.TRADING.append(Component.text("Players cannot trade while in pvp!", NamedTextColor.RED)));
            return;
        }

        UUID uuid = UUID.randomUUID();

        Trade trade = new Trade(uuid, sender, player);

        trade.start();
    }

    public void requestTrade(Player sender, Player trader) {
        tradeRequests.put(sender, trader);

        trader.sendMessage(Prefix.TRADING
                .append(sender.name())
                .append(Component.text(" has requested to trade with you. Type "))
                .append(Component.text("/trade accept").color(NamedTextColor.GOLD))
                .append(Component.text(" to accept."))
                .clickEvent(ClickEvent.runCommand("/trade accept"))
                .hoverEvent(Component.text("Click to accept trade request!")
                        .color(NamedTextColor.GOLD)));

        sender.sendMessage(Prefix.TRADING
                .append(Component.text("Sent a trade request to "))
                .append(trader.name())
                .append(Component.text(".")));
    }

    public void acceptTrade(Player sender) {
        if (!tradeRequests.containsValue(sender)) {
            sender.sendMessage(Prefix.TRADING.append(Component.text("You have no pending trade requests.")));
            return;
        }

        tradeRequests.entrySet().stream().filter(playerPlayerEntry -> playerPlayerEntry.getValue().equals(sender)).findFirst().ifPresent(playerPlayerEntry -> {
            Player player = playerPlayerEntry.getKey();
            newTrade(player, sender);
            tradeRequests.remove(player);
        });
    }

    public void declineTrade(Player sender) {
        if (!tradeRequests.containsValue(sender)) {
            sender.sendMessage(Prefix.TRADING.append(Component.text("You have no pending trade requests.")));
            return;
        }

        tradeRequests.entrySet().stream().filter(playerPlayerEntry -> playerPlayerEntry.getValue().equals(sender)).findFirst().ifPresent(playerPlayerEntry -> {
            Player player = playerPlayerEntry.getKey();
            player.sendMessage(Prefix.TRADING
                    .append(sender.name())
                    .append(Component.text(" has declined your trade request.")));
            sender.sendMessage(Prefix.TRADING
                    .append(Component.text("You have declined the trade request from "))
                    .append(player.name()));
            tradeRequests.remove(player);
        });
    }

    public boolean hasPendingRequest(Player player) {
        return tradeRequests.containsKey(player) || tradeRequests.containsValue(player);
    }

    public void cancelRequest(Player player) {
        if (!tradeRequests.containsKey(player)) {
            player.sendMessage(Prefix.TRADING.append(Component.text("You do not await a response from any requests.")));
            return;
        }

        Player target = tradeRequests.get(player);
        target.sendMessage(Prefix.TRADING
                .append(player.name())
                .append(Component.text(" has cancelled their trade request.")));
        player.sendMessage(Prefix.TRADING.append(Component.text("Cancelled your trade request.")));
        tradeRequests.remove(player);
    }
}
