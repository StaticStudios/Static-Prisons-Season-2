package net.staticstudios.prisons.gui.newGui;

import net.staticstudios.gui.GUICreator;
import net.staticstudios.gui.GUIUtils;
import net.staticstudios.prisons.data.dataHandling.PlayerData;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.math.BigInteger;
import java.util.List;

public class BackpackMenus extends GUIUtils {

    private static final int SLOT_COST = 1;
    private static final int SLOTS_PER_COST = 1000;
    public static void upgradeBag(Player player) {
        GUICreator c = new GUICreator(9, "Upgrade Your Backpack");
        PlayerData playerData = new PlayerData(player);
        c.setItems(
                PrisonUtils.setItemCount(c.createButton(Material.CHEST, "&a&lAdd " + PrisonUtils.addCommasToNumber((long) (SLOTS_PER_COST * Math.pow(10, 0))) + " Slots", List.of("","&bCurrent Size: &f" + PrisonUtils.addCommasToNumber(playerData.getBackpackSize()) + " Slots",
                        "&bCosts: &f1 Token", "&bYour Tokens: &f" + PrisonUtils.prettyNum(playerData.getTokens())), (p, t) -> {
                    buySlots(p, BigInteger.valueOf((long) (SLOTS_PER_COST * Math.pow(10, 0))));
                }), 1),
                PrisonUtils.setItemCount(c.createButton(Material.CHEST, "&a&lAdd " + PrisonUtils.addCommasToNumber(((long) (SLOTS_PER_COST * Math.pow(10, 1)))) + " Slots", List.of("","&bCurrent Size: &f" + PrisonUtils.addCommasToNumber(playerData.getBackpackSize()) + " Slots",
                        "&bCosts: &f" + PrisonUtils.prettyNum((long) (SLOT_COST * Math.pow(10, 1))) + " Tokens", "&bYour Tokens: &f" + PrisonUtils.prettyNum(playerData.getTokens())), (p, t) -> {
                    buySlots(p, BigInteger.valueOf((long) (SLOTS_PER_COST * Math.pow(10, 1))));
                }), 2),
                PrisonUtils.setItemCount(c.createButton(Material.CHEST, "&a&lAdd " + PrisonUtils.addCommasToNumber(((long) (SLOTS_PER_COST * Math.pow(10, 2)))) + " Slots", List.of("","&bCurrent Size: &f" + PrisonUtils.addCommasToNumber(playerData.getBackpackSize()) + " Slots",
                        "&bCosts: &f" + PrisonUtils.prettyNum((long) (SLOT_COST * Math.pow(10, 2))) + " Tokens", "&bYour Tokens: &f" + PrisonUtils.prettyNum(playerData.getTokens())), (p, t) -> {
                    buySlots(p, BigInteger.valueOf((long) (SLOTS_PER_COST * Math.pow(10, 2))));
                }), 3),
                PrisonUtils.setItemCount(c.createButton(Material.CHEST, "&a&lAdd " + PrisonUtils.addCommasToNumber(((long) (SLOTS_PER_COST * Math.pow(10, 3)))) + " Slots", List.of("","&bCurrent Size: &f" + PrisonUtils.addCommasToNumber(playerData.getBackpackSize()) + " Slots",
                        "&bCosts: &f" + PrisonUtils.prettyNum((long) (SLOT_COST * Math.pow(10, 3))) + " Tokens", "&bYour Tokens: &f" + PrisonUtils.prettyNum(playerData.getTokens())), (p, t) -> {
                    buySlots(p, BigInteger.valueOf((long) (SLOTS_PER_COST * Math.pow(10, 3))));
                }), 4),
                PrisonUtils.setItemCount(c.createButton(Material.CHEST, "&a&lAdd " + PrisonUtils.addCommasToNumber(((long) (SLOTS_PER_COST * Math.pow(10, 4)))) + " Slots", List.of("","&bCurrent Size: &f" + PrisonUtils.addCommasToNumber(playerData.getBackpackSize()) + " Slots",
                        "&bCosts: &f" + PrisonUtils.prettyNum((long) (SLOT_COST * Math.pow(10, 4))) + " Tokens", "&bYour Tokens: &f" + PrisonUtils.prettyNum(playerData.getTokens())), (p, t) -> {
                    buySlots(p, BigInteger.valueOf((long) (SLOTS_PER_COST * Math.pow(10, 4))));
                }), 5),
                PrisonUtils.setItemCount(c.createButton(Material.CHEST, "&a&lAdd " + PrisonUtils.addCommasToNumber(((long) (SLOTS_PER_COST * Math.pow(10, 5)))) + " Slots", List.of("","&bCurrent Size: &f" + PrisonUtils.addCommasToNumber(playerData.getBackpackSize()) + " Slots",
                        "&bCosts: &f" + PrisonUtils.prettyNum((long) (SLOT_COST * Math.pow(10, 5))) + " Tokens", "&bYour Tokens: &f" + PrisonUtils.prettyNum(playerData.getTokens())), (p, t) -> {
                    buySlots(p, BigInteger.valueOf((long) (SLOTS_PER_COST * Math.pow(10, 5))));
                }), 6),
                PrisonUtils.setItemCount(c.createButton(Material.CHEST, "&a&lAdd " + PrisonUtils.addCommasToNumber(((long) (SLOTS_PER_COST * Math.pow(10, 6)))) + " Slots", List.of("","&bCurrent Size: &f" + PrisonUtils.addCommasToNumber(playerData.getBackpackSize()) + " Slots",
                        "&bCosts: &f" + PrisonUtils.prettyNum((long) (SLOT_COST * Math.pow(10, 6))) + " Tokens", "&bYour Tokens: &f" + PrisonUtils.prettyNum(playerData.getTokens())), (p, t) -> {
                    buySlots(p, BigInteger.valueOf((long) (SLOTS_PER_COST * Math.pow(10, 6))));
                }), 7),
                PrisonUtils.setItemCount(c.createButton(Material.CHEST, "&a&lAdd " + PrisonUtils.addCommasToNumber(((long) (SLOTS_PER_COST * Math.pow(10, 7)))) + " Slots", List.of("","&bCurrent Size: &f" + PrisonUtils.addCommasToNumber(playerData.getBackpackSize()) + " Slots",
                        "&bCosts: &f" + PrisonUtils.prettyNum((long) (SLOT_COST * Math.pow(10, 7))) + " Tokens", "&bYour Tokens: &f" + PrisonUtils.prettyNum(playerData.getTokens())), (p, t) -> {
                    buySlots(p, BigInteger.valueOf((long) (SLOTS_PER_COST * Math.pow(10, 7))));
                }), 8),
                PrisonUtils.setItemCount(c.createButton(Material.CHEST, "&a&lAdd " + PrisonUtils.addCommasToNumber(((long) (SLOTS_PER_COST * Math.pow(10, 8)))) + " Slots", List.of("","&bCurrent Size: &f" + PrisonUtils.addCommasToNumber(playerData.getBackpackSize()) + " Slots",
                        "&bCosts: &f" + PrisonUtils.prettyNum((long) (SLOT_COST * Math.pow(10, 8))) + " Tokens", "&bYour Tokens: &f" + PrisonUtils.prettyNum(playerData.getTokens())), (p, t) -> {
                    buySlots(p, BigInteger.valueOf((long) (SLOTS_PER_COST * Math.pow(10, 8))));
                }), 64)
        );
        c.open(player);
        c.setOnCloseRun((p, t) -> MainMenus.open(p));
    }

    private static void buySlots(Player player, BigInteger slotsToBuy) {
        PlayerData playerData = new PlayerData(player);
        BigInteger price = slotsToBuy.multiply(BigInteger.valueOf(SLOT_COST)).divide(BigInteger.valueOf(SLOTS_PER_COST));
        if (playerData.getTokens().compareTo(price) > -1) {
            playerData.removeTokens(price);
            playerData.setBackpackSize(playerData.getBackpackSize().add(slotsToBuy));
            player.sendMessage(ChatColor.AQUA + "You've successfully upgraded your backpack!");
            upgradeBag(player);
        } else player.sendMessage(ChatColor.RED + "You do not have enough tokens to prestige!");
    }
}
