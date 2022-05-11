package net.staticstudios.prisons.gui;

import net.staticstudios.prisons.StaticPrisons;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.persistence.PersistentDataType;

public class GUIListener implements Listener {
    @EventHandler
    public void clickEvent(InventoryClickEvent e) {
        if (e.getCurrentItem() == null) return;
        if (!e.getCurrentItem().hasItemMeta()) return;
        if (!e.getCurrentItem().getItemMeta().getPersistentDataContainer().has(new NamespacedKey(StaticPrisons.getInstance(), "guiItem"), PersistentDataType.STRING)) return;
        Player player = (Player) e.getWhoClicked();
        int itemSlot = e.getSlot();
        String giuItemType = e.getCurrentItem().getItemMeta().getPersistentDataContainer().get(new NamespacedKey(StaticPrisons.getInstance(), "guiItem"), PersistentDataType.STRING);
        String fromPage;
        switch (giuItemType) {
            case "normal":
                e.setCancelled(true);
                fromPage = e.getCurrentItem().getItemMeta().getPersistentDataContainer().get(new NamespacedKey(StaticPrisons.getInstance(), "fromPage"), PersistentDataType.STRING);
                GUIPage guiPage = GUIPage.guiPages.get(fromPage);
                itemInSlotClicked(guiPage, itemSlot, e);
                break;
            case "placeholder":
                e.setCancelled(true);
                break;
            case "backButton":
                fromPage = e.getCurrentItem().getItemMeta().getPersistentDataContainer().get(new NamespacedKey(StaticPrisons.getInstance(), "fromPage"), PersistentDataType.STRING);
                String menuToGoTo = e.getCurrentItem().getItemMeta().getPersistentDataContainer().get(new NamespacedKey(StaticPrisons.getInstance(), "menuToGoTo"), PersistentDataType.STRING);
                GUIPage.guiPages.get(fromPage).onClose(player);
                GUIPage.guiPages.get(menuToGoTo).open(player);
                e.setCancelled(true);
                break;
        }
    }
    static void itemInSlotClicked(GUIPage guiPage, int slot, InventoryClickEvent e) {
        guiPage.itemClicked(e);
        switch (slot) {
            case 0 -> guiPage.item0Clicked(e);
            case 1 -> guiPage.item1Clicked(e);
            case 2 -> guiPage.item2Clicked(e);
            case 3 -> guiPage.item3Clicked(e);
            case 4 -> guiPage.item4Clicked(e);
            case 5 -> guiPage.item5Clicked(e);
            case 6 -> guiPage.item6Clicked(e);
            case 7 -> guiPage.item7Clicked(e);
            case 8 -> guiPage.item8Clicked(e);
            case 9 -> guiPage.item9Clicked(e);
            case 10 -> guiPage.item10Clicked(e);
            case 11 -> guiPage.item11Clicked(e);
            case 12 -> guiPage.item12Clicked(e);
            case 13 -> guiPage.item13Clicked(e);
            case 14 -> guiPage.item14Clicked(e);
            case 15 -> guiPage.item15Clicked(e);
            case 16 -> guiPage.item16Clicked(e);
            case 17 -> guiPage.item17Clicked(e);
            case 18 -> guiPage.item18Clicked(e);
            case 19 -> guiPage.item19Clicked(e);
            case 20 -> guiPage.item20Clicked(e);
            case 21 -> guiPage.item21Clicked(e);
            case 22 -> guiPage.item22Clicked(e);
            case 23 -> guiPage.item23Clicked(e);
            case 24 -> guiPage.item24Clicked(e);
            case 25 -> guiPage.item25Clicked(e);
            case 26 -> guiPage.item26Clicked(e);
            case 27 -> guiPage.item27Clicked(e);
            case 28 -> guiPage.item28Clicked(e);
            case 29 -> guiPage.item29Clicked(e);
            case 30 -> guiPage.item30Clicked(e);
            case 31 -> guiPage.item31Clicked(e);
            case 32 -> guiPage.item32Clicked(e);
            case 33 -> guiPage.item33Clicked(e);
            case 34 -> guiPage.item34Clicked(e);
            case 35 -> guiPage.item35Clicked(e);
            case 36 -> guiPage.item36Clicked(e);
            case 37 -> guiPage.item37Clicked(e);
            case 38 -> guiPage.item38Clicked(e);
            case 39 -> guiPage.item39Clicked(e);
            case 40 -> guiPage.item40Clicked(e);
            case 41 -> guiPage.item41Clicked(e);
            case 42 -> guiPage.item42Clicked(e);
            case 43 -> guiPage.item43Clicked(e);
            case 44 -> guiPage.item44Clicked(e);
            case 45 -> guiPage.item45Clicked(e);
            case 46 -> guiPage.item46Clicked(e);
            case 47 -> guiPage.item47Clicked(e);
            case 48 -> guiPage.item48Clicked(e);
            case 49 -> guiPage.item49Clicked(e);
            case 50 -> guiPage.item50Clicked(e);
            case 51 -> guiPage.item51Clicked(e);
            case 52 -> guiPage.item52Clicked(e);
            case 53 -> guiPage.item53Clicked(e);
        }
    }
}
