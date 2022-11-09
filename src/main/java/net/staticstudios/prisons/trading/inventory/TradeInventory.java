package net.staticstudios.prisons.trading.inventory;

import net.staticstudios.newgui.GUIButton;
import net.staticstudios.newgui.StaticGUI;
import net.staticstudios.newgui.builder.ButtonBuilder;
import net.staticstudios.newgui.builder.GUIBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import static net.kyori.adventure.text.Component.text;

public interface TradeInventory extends Listener {

    default StaticGUI create(String title) {
        StaticGUI inv = GUIBuilder.builder().title(text(title)).onClose(this::onClose).size(54).build();

        inv.getSettings().allowPlayerItems(true);

        inv.setButton(4, getPlaceholder());
        inv.setButton(13, getPlaceholder());
        inv.setButton(22, getPlaceholder());
        inv.setButton(31, getPlaceholder());
        inv.setButtons(36, 53, getPlaceholder());
        return inv;
    }

    void onClose(Player player, StaticGUI gui);

    @EventHandler
    void onClick(InventoryClickEvent event);

    void confirmInitiator(InventoryClickEvent event, StaticGUI gui);

    void confirmTrader(InventoryClickEvent event, StaticGUI gui);

    default int getNextFreeTraderSlot(Inventory inventory, int startingSlot) {
        if (startingSlot == 0) {
            int slot = inventory.firstEmpty();

            if (slot == -1) {
                return -1;
            } else {
                if (slot % 9 <= 3) {
                    slot = getNextFreeTraderSlot(inventory, ++slot);
                }
            }

            return slot;
        }

        var iterator = inventory.iterator(startingSlot);

        while (iterator.hasNext()) {
            ItemStack itemStack = iterator.next();

            if (itemStack == null) {
                int slot = iterator.previousIndex();

                if (slot % 9 <= 3) {
                    slot = getNextFreeTraderSlot(inventory, ++slot);
                }

                return slot;
            }
        }

        return -1;
    }

    default int getNextFreeInitiatorSlot(Inventory inventory, int startingSlot) {
        if (startingSlot == 0) {
            int slot = inventory.firstEmpty();


            if (slot == -1) {
                return -1;
            } else {
                if (slot % 9 > 4) {
                    slot = getNextFreeInitiatorSlot(inventory, ++slot);
                }
            }

            return slot;
        }

        var iterator = inventory.iterator(startingSlot);

        while (iterator.hasNext()) {
            ItemStack itemStack = iterator.next();

            if (itemStack == null) {
                int slot = iterator.previousIndex();

                if (slot % 9 > 4) {
                    slot = getNextFreeInitiatorSlot(inventory, ++slot);
                }

                return slot;
            }
        }

        return -1;
    }


    private GUIButton getPlaceholder() {
        return ButtonBuilder.builder().icon(Material.GRAY_STAINED_GLASS_PANE).onClick(this::onPlaceholderClick).build();
    }

    private void onPlaceholderClick(InventoryClickEvent event, StaticGUI gui) {
        event.setCancelled(true);
    }

    void complete();

    void updateTimer(int secondsLeft);
}
