package net.staticstudios.prisons.trading.domain;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.staticstudios.gui.GUIPlaceholders;
import net.staticstudios.gui.builder.ButtonBuilder;
import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.trading.TradeLogger;
import net.staticstudios.prisons.trading.inventory.DefaultTradeInventory;
import net.staticstudios.prisons.trading.task.ConfirmTask;
import net.staticstudios.prisons.utils.Prefix;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.util.Objects;
import java.util.UUID;

public final class Trade implements Listener {
    private final UUID uuid;

    private final Player initiator;
    private boolean isInitiatorConfirmed;

    private final Player trader;
    private boolean isTraderConfirmed;

    private final DefaultTradeInventory tradeInventory;

    private final TradeLogger tradeLogger;

    private final Audience audience;

    private boolean inCompletedState = false;

    public Trade(UUID uuid, Player initiator, Player trader) {
        this.uuid = uuid;
        this.initiator = initiator;
        this.trader = trader;
        this.tradeLogger = new TradeLogger(uuid.toString());
        this.audience = Audience.audience(trader, initiator);

        this.tradeInventory = new DefaultTradeInventory(this);

        tradeLogger.start(this);
    }


    public void start() {
        tradeInventory.open();
    }

    public void startTimer() {
        tradeInventory.setMiddleColumn(GUIPlaceholders.LIME);

        Bukkit.getScheduler().runTaskLater(StaticPrisons.getInstance(), new ConfirmTask(this), 20);
        Bukkit.getScheduler().runTaskLater(StaticPrisons.getInstance(), new ConfirmTask(this), 25);
    }

    public synchronized void complete() {
        if (inCompletedState) {
            return;
        }

        inCompletedState = true;

        if (!isInitiatorConfirmed || !isTraderConfirmed) {
            return;
        }

        tradeInventory.complete();
        audience.sendMessage(Prefix.TRADING.append(Component.text("Trade completed!").color(NamedTextColor.GREEN)));
        sendId();

        initiator.closeInventory(InventoryCloseEvent.Reason.PLUGIN);
        trader.closeInventory(InventoryCloseEvent.Reason.PLUGIN);
        tradeLogger.completed(this);
    }

    public void updateTimer(int secondsLeft) {
        tradeInventory.updateTimer(secondsLeft);
    }

    public void sendMessage(Component message) {
        audience.sendMessage(Prefix.TRADING.append(message));
    }

    public void sendId() {
        audience.sendMessage(Prefix.TRADING.append(Component.text("Click to copy trade ID")
                .color(NamedTextColor.GOLD)
                .decorate(TextDecoration.UNDERLINED)
                .clickEvent(ClickEvent.copyToClipboard(uuid.toString()))
                .hoverEvent(HoverEvent.showText(Component.text("Click to copy ID").color(NamedTextColor.GOLD))))
        );
    }

    public Player initiator() {
        return initiator;
    }

    public Player trader() {
        return trader;
    }

    public boolean isInitiatorConfirmed() {
        return isInitiatorConfirmed;
    }

    public boolean isTraderConfirmed() {
        return isTraderConfirmed;
    }

    public TradeLogger getTradeLogger() {
        return tradeLogger;
    }

    public void isInitiatorConfirmed(boolean b) {
        isInitiatorConfirmed = b;
    }

    public void isTraderConfirmed(boolean b) {
        isTraderConfirmed = b;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Trade) obj;
        return Objects.equals(this.uuid, that.uuid) &&
                Objects.equals(this.initiator, that.initiator) &&
                Objects.equals(this.trader, that.trader);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, initiator, trader);
    }

    @Override
    public String toString() {
        return "Trade[" +
                "uuid=" + uuid + ", " +
                "initiator=" + initiator + ", " +
                "trader=" + trader + ']';
    }

    public boolean isInCompletedState() {
        return inCompletedState;
    }

    public UUID getUuid() {
        return uuid;
    }
}
