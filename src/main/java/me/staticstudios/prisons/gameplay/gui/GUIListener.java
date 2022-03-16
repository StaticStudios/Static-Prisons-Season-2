package me.staticstudios.prisons.gameplay.gui;

import me.staticstudios.prisons.Main;
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
        if (!e.getCurrentItem().getItemMeta().getPersistentDataContainer().has(new NamespacedKey(Main.getMain(), "guiItem"), PersistentDataType.STRING)) return;
        Player player = (Player) e.getWhoClicked();
        int itemSlot = e.getSlot();
        String giuItemType = e.getCurrentItem().getItemMeta().getPersistentDataContainer().get(new NamespacedKey(Main.getMain(), "guiItem"), PersistentDataType.STRING);
        String fromPage;
        switch (giuItemType) {
            case "normal":
                e.setCancelled(true);
                fromPage = e.getCurrentItem().getItemMeta().getPersistentDataContainer().get(new NamespacedKey(Main.getMain(), "fromPage"), PersistentDataType.STRING);
                GUIPage guiPage = GUIPage.guiPages.get(fromPage);
                itemInSlotClicked(guiPage, itemSlot, e);
                break;
            case "placeholder":
                e.setCancelled(true);
                break;
            case "backButton":
                fromPage = e.getCurrentItem().getItemMeta().getPersistentDataContainer().get(new NamespacedKey(Main.getMain(), "fromPage"), PersistentDataType.STRING);
                String menuToGoTo = e.getCurrentItem().getItemMeta().getPersistentDataContainer().get(new NamespacedKey(Main.getMain(), "menuToGoTo"), PersistentDataType.STRING);
                GUIPage.guiPages.get(fromPage).onClose(player);
                GUIPage.guiPages.get(menuToGoTo).open(player);
                e.setCancelled(true);
                break;
        }
    }
    static void itemInSlotClicked(GUIPage guiPage, int slot, InventoryClickEvent e) {
        guiPage.itemClicked(e);
        switch (slot) {
            case 0:
                guiPage.item0Clicked(e);
                break;
            case 1:
                guiPage.item1Clicked(e);
                break;
            case 2:
                guiPage.item2Clicked(e);
                break;
            case 3:
                guiPage.item3Clicked(e);
                break;
            case 4:
                guiPage.item4Clicked(e);
                break;
            case 5:
                guiPage.item5Clicked(e);
                break;
            case 6:
                guiPage.item6Clicked(e);
                break;
            case 7:
                guiPage.item7Clicked(e);
                break;
            case 8:
                guiPage.item8Clicked(e);
                break;
            case 9:
                guiPage.item9Clicked(e);
                break;
            case 10:
                guiPage.item10Clicked(e);
                break;
            case 11:
                guiPage.item11Clicked(e);
                break;
            case 12:
                guiPage.item12Clicked(e);
                break;
            case 13:
                guiPage.item13Clicked(e);
                break;
            case 14:
                guiPage.item14Clicked(e);
                break;
            case 15:
                guiPage.item15Clicked(e);
                break;
            case 16:
                guiPage.item16Clicked(e);
                break;
            case 17:
                guiPage.item17Clicked(e);
                break;
            case 18:
                guiPage.item18Clicked(e);
                break;
            case 19:
                guiPage.item19Clicked(e);
                break;
            case 20:
                guiPage.item20Clicked(e);
                break;
            case 21:
                guiPage.item21Clicked(e);
                break;
            case 22:
                guiPage.item22Clicked(e);
                break;
            case 23:
                guiPage.item23Clicked(e);
                break;
            case 24:
                guiPage.item24Clicked(e);
                break;
            case 25:
                guiPage.item25Clicked(e);
                break;
            case 26:
                guiPage.item26Clicked(e);
                break;
            case 27:
                guiPage.item27Clicked(e);
                break;
            case 28:
                guiPage.item28Clicked(e);
                break;
            case 29:
                guiPage.item29Clicked(e);
                break;
            case 30:
                guiPage.item30Clicked(e);
                break;
            case 31:
                guiPage.item31Clicked(e);
                break;
            case 32:
                guiPage.item32Clicked(e);
                break;
            case 33:
                guiPage.item33Clicked(e);
                break;
            case 34:
                guiPage.item34Clicked(e);
                break;
            case 35:
                guiPage.item35Clicked(e);
                break;
            case 36:
                guiPage.item36Clicked(e);
                break;
            case 37:
                guiPage.item37Clicked(e);
                break;
            case 38:
                guiPage.item38Clicked(e);
                break;
            case 39:
                guiPage.item39Clicked(e);
                break;
            case 40:
                guiPage.item40Clicked(e);
                break;
            case 41:
                guiPage.item41Clicked(e);
                break;
            case 42:
                guiPage.item42Clicked(e);
                break;
            case 43:
                guiPage.item43Clicked(e);
                break;
            case 44:
                guiPage.item44Clicked(e);
                break;
            case 45:
                guiPage.item45Clicked(e);
                break;
            case 46:
                guiPage.item46Clicked(e);
                break;
            case 47:
                guiPage.item47Clicked(e);
                break;
            case 48:
                guiPage.item48Clicked(e);
                break;
            case 49:
                guiPage.item49Clicked(e);
                break;
            case 50:
                guiPage.item50Clicked(e);
                break;
            case 51:
                guiPage.item51Clicked(e);
                break;
            case 52:
                guiPage.item52Clicked(e);
                break;
            case 53:
                guiPage.item53Clicked(e);
                break;

        }
    }
}
