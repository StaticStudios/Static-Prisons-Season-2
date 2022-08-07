package net.staticstudios.prisons.backpacks;

import net.staticstudios.gui.GUICreator;
import net.staticstudios.gui.GUIUtils;
import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.gui.MainMenus;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class BackpackMenus extends GUIUtils {

    public static void mainMenu(Player player) {
        GUICreator c = new GUICreator(27, "Backpacks");

        c.setItem(11, ench(c.createButton(Material.PRISMARINE_CRYSTALS, "&d&lUpgrade Your Backpack!", List.of(
                "- Increase your backpack's capacity",
                "- Combine your backpacks"
        ), (p, t) -> {
            selectBackpack(p);
        })));


        c.setItem(15, ench(c.createButton(Material.ENDER_CHEST, "&b&lCreate A Backpack", List.of(
                "&oNeed a new backpack to upgrade?",
                "",
                "&bClick here to create a new backpack!"
        ), (p, t) -> {
            PrisonBackpack backpack = PrisonBackpacks.createBackpack(1);
            p.getInventory().addItem(backpack.getItem());
            open(p, backpack);
        })));

        c.fill(createGrayPlaceHolder());
        c.open(player);
        c.setOnCloseRun((p, t) -> MainMenus.open(p));
    }

    public static void open(Player player, PrisonBackpack backpack) {
        GUICreator c = new GUICreator(27, "Upgrade Your Backpack");
        PlayerData playerData = new PlayerData(player);
        if (backpack.getSize() >= PrisonBackpacks.getMaxSize(backpack.getTier())) {
            //The backpack can no longer get a size upgrade and will require it to be combined with another backpack of the same tier that has a maxed size
            combine(player);
            return;
        }
        c.setItem(9, upgradeSize(c, 1, 1, backpack));
        c.setItem(10, upgradeSize(c, 10, 2, backpack));
        c.setItem(11, upgradeSize(c, 100, 3, backpack));
        c.setItem(12, upgradeSize(c, 1000, 4, backpack));
        c.setItem(13, upgradeSize(c, 10000, 5, backpack));
        c.setItem(14, upgradeSize(c, 100000, 6, backpack));
        c.setItem(15, upgradeSize(c, 1000000, 7, backpack));
        c.setItem(16, upgradeSize(c, 10000000, 8, backpack));
        long maxSlots = playerData.getTokens() / SLOT_COST;
        c.setItem(17, PrisonUtils.setItemCount(ench(c.createButton(Material.ENDER_CHEST, "&d&l+" + PrisonUtils.addCommasToNumber(maxSlots) + " Slots (MAX)", List.of( //Max
                "&oIncrease your backpack's capacity!",
                "",
                "&b&l| &bCosts: &f" + PrisonUtils.addCommasToNumber(maxSlots * SLOT_COST) + " Tokens",
                "&b&l| &bCapacity: &f" + PrisonUtils.addCommasToNumber(backpack.getSize()),
                "&b&l| &bMax Capacity: &f" + PrisonUtils.addCommasToNumber(PrisonBackpacks.getMaxSize(backpack.getTier()))
        ), (p, t) -> {
            long toBuy = maxSlots;
            if (backpack.getSize() + toBuy > PrisonBackpacks.getMaxSize(backpack.getTier())) {
                toBuy = PrisonBackpacks.getMaxSize(backpack.getTier()) - backpack.getSize();
            }
            if (playerData.getTokens() >= toBuy * SLOT_COST) {
                playerData.removeTokens(toBuy * SLOT_COST);
                backpack.addSize(toBuy);
                open(p, backpack);
            } else {
                p.sendMessage("You do not have enough tokens to purchase this many slots!");
            }
        })), 64));
        c.fill(createLightBluePlaceHolder());
        c.open(player);

        c.setOnCloseRun((p, t) -> mainMenu(p));
    }

    public static void combine(Player player) {
        GUICreator c = new GUICreator(27, "Combine Your Backpacks");
        //The backpack can no longer get a size upgrade and will require it to be combined with another backpack of the same tier that has a maxed size

        c.setItem(14, ench(c.createButton(Material.NETHER_STAR, "&a&lCombine Backpacks!", List.of(
                "Combine two backpacks of the same tier",
                "to create a new, higher tier backpack!"
        ), (p, t) -> {
            PrisonBackpack backpack1 = PrisonBackpack.fromItem(c.getInventory().getItem(10));
            PrisonBackpack backpack2 = PrisonBackpack.fromItem(c.getInventory().getItem(12));

            if (backpack1 == null || backpack2 == null) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou must put two backpacks in the empty slots!"));
                if (c.getInventory().getItem(10) != null) {
                    PrisonUtils.Players.addToInventory(p, c.getInventory().getItem(10));
                }
                if (c.getInventory().getItem(12) != null) {
                    PrisonUtils.Players.addToInventory(p, c.getInventory().getItem(12));
                }

                c.setItem(10, null);
                c.setItem(12, null);
                return;
            }

            if (backpack1.getTier() != backpack2.getTier()) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cBoth backpacks must be the same tier!"));
                if (c.getInventory().getItem(10) != null) {
                    PrisonUtils.Players.addToInventory(p, c.getInventory().getItem(10));
                }
                if (c.getInventory().getItem(12) != null) {
                    PrisonUtils.Players.addToInventory(p, c.getInventory().getItem(12));
                }

                c.setItem(10, null);
                c.setItem(12, null);
                return;
            }

            if (backpack1.getSize() < PrisonBackpacks.getMaxSize(backpack1.getTier()) || backpack2.getSize() < PrisonBackpacks.getMaxSize(backpack2.getTier())) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cBoth backpacks must be maxed out!"));
                if (c.getInventory().getItem(10) != null) {
                    PrisonUtils.Players.addToInventory(p, c.getInventory().getItem(10));
                }
                if (c.getInventory().getItem(12) != null) {
                    PrisonUtils.Players.addToInventory(p, c.getInventory().getItem(12));
                }

                c.setItem(10, null);
                c.setItem(12, null);
                return;
            }

            c.setItem(10, null);
            c.setItem(12, null);

            PrisonBackpack newBackpack = PrisonBackpacks.createBackpack(backpack1.getTier() + 1);
            newBackpack.setSize(PrisonBackpacks.getMaxSize(backpack1.getTier()));
            newBackpack.updateItemNow();
            PrisonUtils.Players.addToInventory(p, newBackpack.getItem());
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aYou combined your backpacks!"));
        })));

        c.setItem(16, ench(c.createButton(Material.ANVIL, "&b&lHow do I increase my backpack's size?", List.of(
                "Your backpack's tier needs to be upgraded.",
                "To upgrade your backpacks tier, you must",
                "combine it with another backpack of the same",
                "tier that has a maxed out size. This will",
                "allow you to upgrade it further, however,",
                "it will delete the other backpack.",
                "",
                "Put two backpacks in the empty slots to the left",
                "and click the \"&a&lCombine Backpacks!&7\" button."
        ))));

        c.fill(createGrayPlaceHolder());

        c.setItem(10, null);
        c.setItem(12, null);

        c.setOnCloseRun((p, t) -> {
            ItemStack itemStack = c.getInventory().getItem(10);
            if (itemStack != null) {
                PrisonUtils.Players.addToInventory(p, itemStack);
            }
            itemStack = c.getInventory().getItem(12);
            if (itemStack != null) {
                PrisonUtils.Players.addToInventory(p, itemStack);
            }
            mainMenu(p);
        });
        c.open(player);


        c.setOnClickEmptySlot(e -> {
            if (PrisonBackpack.fromItem(e.getCursor()) == null) {
                e.setCancelled(true);
            }
        });

    }

    private static final int SLOT_COST = 1;
    static ItemStack upgradeSize(GUICreator c, long amount, int stackCount, PrisonBackpack backpack) {
        return PrisonUtils.setItemCount(ench(c.createButton(Material.ENDER_CHEST, "&d&l+" + PrisonUtils.addCommasToNumber(amount) + " Slots", List.of(
                "&oIncrease your backpack's capacity!",
                "",
                "&b&l| &bCosts: &f" + PrisonUtils.addCommasToNumber(amount * SLOT_COST) + " Tokens",
                "&b&l| &bCapacity: &f" + PrisonUtils.addCommasToNumber(backpack.getSize()),
                "&b&l| &bMax Capacity: &f" + PrisonUtils.addCommasToNumber(PrisonBackpacks.getMaxSize(backpack.getTier()))
        ), (p, t) -> {
            long toBuy = amount;
            if (backpack.getSize() + toBuy > PrisonBackpacks.getMaxSize(backpack.getTier())) {
                toBuy = PrisonBackpacks.getMaxSize(backpack.getTier()) - backpack.getSize();
            }
            PlayerData playerData = new PlayerData(p);
            if (playerData.getTokens() >= toBuy * SLOT_COST) {
                playerData.removeTokens(toBuy * SLOT_COST);
                backpack.addSize(toBuy);
                open(p, backpack);
            } else {
                p.sendMessage("You do not have enough tokens to purchase this many slots!");
            }
        })), stackCount);
    }

    public static void selectBackpack(Player player) {
        GUICreator c = new GUICreator(36, "Select A Backpack To Upgrade");
        for (ItemStack item : player.getInventory().getContents()) {
            PrisonBackpack backpack = PrisonBackpack.fromItem(item);
            if (backpack != null) {
                c.addItem(c.createButton(item, (p, t) -> {
                    open(p, backpack);
                }));
            }
        }
        c.fill(createGrayPlaceHolder());
        c.open(player);
    }
}