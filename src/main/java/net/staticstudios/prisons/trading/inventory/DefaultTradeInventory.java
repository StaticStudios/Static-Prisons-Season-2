package net.staticstudios.prisons.trading.inventory;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.staticstudios.gui.GUIButton;
import net.staticstudios.gui.GUIPlaceholders;
import net.staticstudios.gui.StaticGUI;
import net.staticstudios.gui.builder.ButtonBuilder;
import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.trading.domain.Trade;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DefaultTradeInventory implements TradeInventory, Listener {

    private final List<ItemStack> initiatorItems = new ArrayList<>();
    private final List<ItemStack> traderItems = new ArrayList<>();

    private final Trade trade;
    private final StaticGUI inventory;

    public DefaultTradeInventory(Trade trade) {
        this.trade = trade;

        this.inventory = create(trade.initiator().getName() + " | " + trade.trader().getName());

        inventory.getSettings().allowPlayerItems(true);
        inventory.getSettings().givePlayerItemsBack(false);
        inventory.getSettings().allowDragItems(true);

        inventory.setButton(45, ButtonBuilder.builder().name(trade.initiator().getName() + " has not confirmed").icon(Material.RED_STAINED_GLASS_PANE).onClick(this::confirmInitiator).build());
        inventory.setButton(53, ButtonBuilder.builder().name(trade.trader().getName() + " has not confirmed").icon(Material.RED_STAINED_GLASS_PANE).onClick(this::confirmTrader).build());

        Bukkit.getPluginManager().registerEvents(this, StaticPrisons.getInstance());
    }

    public void open() {
        inventory.open(trade.initiator());
        inventory.open(trade.trader());
    }

    @EventHandler
    @Override
    public void onClick(InventoryClickEvent event) {

        if (event.getClickedInventory() == null) {
            return;
        }

        if (!event.getView().getTopInventory().equals(inventory.getInventory())) {
            return;
        }


        switch (event.getAction()) {
            case MOVE_TO_OTHER_INVENTORY -> handleMoveToOtherInventory(event);
            case PLACE_ALL, PLACE_ONE, PLACE_SOME -> handlePlace(event);
            case HOTBAR_SWAP, HOTBAR_MOVE_AND_READD -> handleHotbarSwap(event);
            case PICKUP_ALL -> handlePickup(event);
            case PICKUP_HALF -> handlePickupHalf(event);
//            case PICKUP_ONE, PICKUP_HALF, PICKUP_SOME -> event.setCancelled(true); //cancel for now since it's not recorded how many items have been picked up exactly
//            case DROP_ALL_CURSOR, DROP_ALL_SLOT, DROP_ONE_CURSOR, DROP_ONE_SLOT -> event.setCancelled(true);
            default -> event.setCancelled(true);
        }
    }

    private void handlePickupHalf(InventoryClickEvent event) {
        if (Objects.equals(event.getClickedInventory(), inventory.getInventory())) {
            event.setCancelled(true);
        }
    }

//    private void handleHotbarMoveAndReadd(InventoryClickEvent event) {
//        // TODO: 21/10/2022 allow this in the future maybe atm not possible to get the item swapped
//        event.setCancelled(true);
//    }

    private void handleHotbarSwap(InventoryClickEvent event) {
        // TODO: 21/10/2022 allow this in the future maybe atm not possible to get the item swapped
        event.setCancelled(true);
    }

    private void handlePickup(InventoryClickEvent event) {
        if (event.getCurrentItem() == null) {
            return;
        }

        if (!Objects.equals(event.getClickedInventory(), inventory.getInventory())) {
            return;
        }

        ItemStack currentItem = event.getCurrentItem().clone();

        handle(event, currentItem);
    }

    private void handle(InventoryClickEvent event, ItemStack currentItem) {
        if (event.getWhoClicked().equals(trade.initiator())) {
            if (event.getSlot() % 9 <= 3) {
                if (initiatorItems.remove(currentItem)) {
                    if (trade.isInitiatorConfirmed()) {
                        trade.getTradeLogger().retract(trade.initiator());
                    }

                    trade.isInitiatorConfirmed(false);
                    setInitiatorConfirmButton();
                    trade.getTradeLogger().removed(trade.initiator(), currentItem);
                }
            } else {
                event.setCancelled(true);
            }
        } else if (event.getWhoClicked().equals(trade.trader())) {
            if (event.getSlot() % 9 > 4) {
                if (traderItems.remove(currentItem)) {
                    if (trade.isTraderConfirmed()) {
                        trade.getTradeLogger().retract(trade.trader());
                    }

                    trade.isTraderConfirmed(false);
                    setTraderConfirmButton();
                    trade.getTradeLogger().removed(trade.trader(), currentItem);
                }
            } else {
                event.setCancelled(true);
            }
        }
    }

    private void handlePlace(InventoryClickEvent event) {
        if (event.getCursor() == null) {
            return;
        }

        if (!Objects.equals(event.getClickedInventory(), inventory.getInventory())) {
            return;
        }

        ItemStack currentItem = event.getCursor().clone();

        if (event.getWhoClicked().equals(trade.initiator())) {
            if (event.getSlot() % 9 <= 3) {
                initiatorItems.add(currentItem);

                if (trade.isInitiatorConfirmed()) {
                    trade.getTradeLogger().retract(trade.initiator());
                }

                trade.isInitiatorConfirmed(false);
                setInitiatorConfirmButton();
                trade.getTradeLogger().added(trade.initiator(), currentItem);
            } else {
                event.setCancelled(true);
            }
        } else if (event.getWhoClicked().equals(trade.trader())) {
            if (event.getSlot() % 9 > 4) {
                traderItems.add(currentItem);

                if (trade.isTraderConfirmed()) {
                    trade.getTradeLogger().retract(trade.trader());
                }

                trade.isTraderConfirmed(false);
                setTraderConfirmButton();
                trade.getTradeLogger().added(trade.trader(), currentItem);
            } else {
                event.setCancelled(true);
            }
        }
    }

    private void handleMoveToOtherInventory(InventoryClickEvent event) {

        if (event.getCurrentItem() == null) {
            return;
        }

        ItemStack currentItem = event.getCurrentItem().clone();

        if (Objects.equals(event.getClickedInventory(), trade.initiator().getInventory()) && event.getWhoClicked().equals(trade.initiator())) {

            int nextSlot = getNextFreeInitiatorSlot(inventory.getInventory(), 0);

            if (nextSlot == -1) {
                event.setCancelled(true);
                return;
            }

            inventory.getInventory().setItem(nextSlot, currentItem);

            initiatorItems.add(currentItem);

            if (trade.isInitiatorConfirmed()) {
                trade.getTradeLogger().retract(trade.initiator());
            }

            trade.isInitiatorConfirmed(false);
            setInitiatorConfirmButton();
            trade.getTradeLogger().added(trade.initiator(), currentItem);
        } else if (Objects.equals(event.getClickedInventory(), trade.trader().getInventory()) && event.getWhoClicked().equals(trade.trader())) {

            int nextSlot = getNextFreeTraderSlot(inventory.getInventory(), 0);

            if (nextSlot == -1) {
                event.setCancelled(true);
                return;
            }

            inventory.getInventory().setItem(nextSlot, currentItem);

            traderItems.add(currentItem);

            if (trade.isTraderConfirmed()) {
                trade.getTradeLogger().retract(trade.trader());
            }

            trade.isTraderConfirmed(false);
            setTraderConfirmButton();
            trade.getTradeLogger().added(trade.trader(), currentItem);
        } else if (Objects.equals(event.getClickedInventory(), inventory.getInventory())) {
            handle(event, currentItem);
            return;
        }
        event.getWhoClicked().getInventory().setItem(event.getSlot(), null);
    }

    @Override
    public void onClose(Player player, StaticGUI gui) {
        initiatorItems.forEach(item -> trade.initiator().getInventory().addItem(item).forEach((integer, itemStack) -> trade.initiator().getWorld().dropItemNaturally(trade.initiator().getLocation(), itemStack)));
        traderItems.forEach(item -> trade.trader().getInventory().addItem(item).forEach((integer, itemStack) -> trade.trader().getWorld().dropItemNaturally(trade.trader().getLocation(), itemStack)));

        trade.isTraderConfirmed(false);
        trade.isInitiatorConfirmed(false);

        trade.initiator().closeInventory();
        trade.trader().closeInventory();

        trade.sendMessage(player.name().append(Component.text(" canceled the trade").color(NamedTextColor.RED)));
        trade.sendId();

        trade.getTradeLogger().cancelled(player);
    }

    @EventHandler
    public void onDrag(InventoryDragEvent event) {
        if (event.getRawSlots().stream().anyMatch(slot -> slot < 54)) {
            event.setCancelled(true);
        }
    }

    @Override
    public void confirmInitiator(InventoryClickEvent event, StaticGUI gui) {
        if (event.getWhoClicked() != trade.initiator()) {
            return;
        }

        trade.isInitiatorConfirmed(!trade.isInitiatorConfirmed());

        setInitiatorConfirmButton();

        if (trade.isInitiatorConfirmed()) {
            trade.getTradeLogger().accepted(trade.initiator());
        } else {
            trade.getTradeLogger().retract(trade.initiator());
        }
    }

    private void setInitiatorConfirmButton() {

        inventory.setButton(45,
                trade.isInitiatorConfirmed()
                        ? ButtonBuilder.builder().name(trade.initiator().getName() + " has confirmed").icon(Material.LIME_STAINED_GLASS_PANE).onClick(this::confirmInitiator).build()
                        : ButtonBuilder.builder().name(trade.initiator().getName() + " has not confirmed").icon(Material.RED_STAINED_GLASS_PANE).onClick(this::confirmInitiator).build()
        );

        if (!trade.isInitiatorConfirmed()) {
            setMiddleColumn(GUIPlaceholders.GRAY);
        }

        if (trade.isTraderConfirmed() && trade.isInitiatorConfirmed()) {
            trade.startTimer();
        }
    }

    @Override
    public void confirmTrader(InventoryClickEvent event, StaticGUI gui) {
        if (event.getWhoClicked() != trade.trader()) {
            return;
        }

        trade.isTraderConfirmed(!trade.isTraderConfirmed());

        setTraderConfirmButton();

        if (trade.isTraderConfirmed()) {
            trade.getTradeLogger().accepted(trade.trader());
        } else {
            trade.getTradeLogger().retract(trade.trader());
        }
    }

    @Override
    public void complete() {
        initiatorItems.forEach(item -> trade.trader().getInventory().addItem(item).forEach((integer, itemStack) -> trade.trader().getWorld().dropItemNaturally(trade.trader().getLocation(), itemStack)));
        traderItems.forEach(item -> trade.initiator().getInventory().addItem(item).forEach((integer, itemStack) -> trade.initiator().getWorld().dropItemNaturally(trade.initiator().getLocation(), itemStack)));
    }

    private void setTraderConfirmButton() {
        inventory.setButton(53,
                trade.isTraderConfirmed()
                        ? ButtonBuilder.builder().name(trade.trader().getName() + " has confirmed").onClick(this::confirmTrader).icon(Material.LIME_STAINED_GLASS_PANE).build()
                        : ButtonBuilder.builder().name(trade.trader().getName() + " has not confirmed").onClick(this::confirmTrader).icon(Material.RED_STAINED_GLASS_PANE).build()
        );

        if (!trade.isTraderConfirmed()) {
            setMiddleColumn(GUIPlaceholders.GRAY);
        }

        if (trade.isTraderConfirmed() && trade.isInitiatorConfirmed()) {
            trade.startTimer();
        }
    }

    public void setMiddleColumn(GUIButton button) {
        inventory.setButton(4, button);
        inventory.setButton(13, button);
        inventory.setButton(22, button);
        inventory.setButton(31, button);
        inventory.setButton(40, button);
        inventory.setButton(49, button);
    }

    @Override
    public void updateTimer(int secondsLeft) {
        int index = 4 + (secondsLeft * 9);

        inventory.setButton(index, GUIPlaceholders.GRAY);
    }
}
