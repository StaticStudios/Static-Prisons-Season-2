package net.staticstudios.prisons.gui.newGui;

import net.staticstudios.gui.GUICreator;
import net.staticstudios.gui.GUIUtils;
import net.staticstudios.prisons.data.dataHandling.PlayerData;
import net.staticstudios.prisons.enchants.handler.BaseEnchant;
import net.staticstudios.prisons.enchants.handler.PrisonEnchants;
import net.staticstudios.prisons.enchants.handler.PrisonPickaxe;
import net.staticstudios.prisons.utils.Utils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class EnchantMenus extends GUIUtils {
    public static void selectPickaxe(Player player) {
        //TODO this and make all commands point to this and enchant effects
        GUICreator c = new GUICreator(36, "Select a pickaxe to enchant");
        int x = 0;
        for (ItemStack item : player.getInventory().getContents()) {
            if (Utils.checkIsPrisonPickaxe(item)) {
                c.addItem(c.createNormalItem(item, (p, t) -> mainMenu(p, PrisonPickaxe.fromItem(item))));
                x++;
            }
        }
        for (int i = x; i < 36; i++) c.setItem(i, createGrayPlaceHolder());
        c.open(player);
    }
    public static void mainMenu(Player player, PrisonPickaxe pickaxe) {
        PlayerData playerData = new PlayerData(player);
        GUICreator c = new GUICreator(45, "Enchants");

        c.setItems(
                createGrayPlaceHolder(),
                createGrayPlaceHolder(),
                createGrayPlaceHolder(),
                createGrayPlaceHolder(),
                createGrayPlaceHolder(),
                createGrayPlaceHolder(),
                createGrayPlaceHolder(),
                createGrayPlaceHolder(),
                createGrayPlaceHolder(),
                createGrayPlaceHolder(),
                createEnchantButton(playerData, pickaxe, PrisonEnchants.FORTUNE, c, Material.DIAMOND, true),
                createEnchantButton(playerData, pickaxe, PrisonEnchants.DOUBLE_FORTUNE, c, Material.AMETHYST_SHARD, true),
                createEnchantButton(playerData, pickaxe, PrisonEnchants.TOKENATOR, c, Material.SUNFLOWER, true),
                createEnchantButton(playerData, pickaxe, PrisonEnchants.KEY_FINDER, c, Material.TRIPWIRE_HOOK, true),
                createEnchantButton(playerData, pickaxe, PrisonEnchants.METAL_DETECTOR, c, Material.RAW_GOLD, true),
                createGrayPlaceHolder(),
                createEnchantButton(playerData, pickaxe, PrisonEnchants.HASTE, c, Material.GOLDEN_PICKAXE, true),
                createGrayPlaceHolder(),
                createGrayPlaceHolder(),
                createEnchantButton(playerData, pickaxe, PrisonEnchants.EXPLOSION, c, Material.TNT, true),
                createEnchantButton(playerData, pickaxe, PrisonEnchants.JACK_HAMMER, c, Material.ANVIL, true),
                createEnchantButton(playerData, pickaxe, PrisonEnchants.DOUBLE_JACK_HAMMER, c, Material.HOPPER, true),
                createEnchantButton(playerData, pickaxe, PrisonEnchants.MULTI_DIRECTIONAL, c, Material.COMPARATOR, true),
                createEnchantButton(playerData, pickaxe, PrisonEnchants.MERCHANT, c, Material.MAP, true),
                createGrayPlaceHolder(),
                createEnchantButton(playerData, pickaxe, PrisonEnchants.SPEED, c, Material.FEATHER, true),
                createGrayPlaceHolder(),
                createGrayPlaceHolder(),
                createEnchantButton(playerData, pickaxe, PrisonEnchants.CONSISTENCY, c, Material.EMERALD, true),
                createEnchantButton(playerData, pickaxe, PrisonEnchants.EGG_SHOOTER, c, Material.EGG, true),
                createLightBluePlaceHolder(),
                createLightBluePlaceHolder(),
                createLightBluePlaceHolder(),
                createGrayPlaceHolder(),
                createEnchantButton(playerData, pickaxe, PrisonEnchants.NIGHT_VISION, c, Material.LAPIS_LAZULI, true),
                createGrayPlaceHolder(),
                createGrayPlaceHolder(),
                createGrayPlaceHolder(),
                createGrayPlaceHolder(),
                createGrayPlaceHolder(),
                createGrayPlaceHolder(),
                createGrayPlaceHolder(),
                createGrayPlaceHolder(),
                createGrayPlaceHolder(),
                createGrayPlaceHolder()
        );
        c.open(player);
    }
    //---------- vvv Util methods vvv ----------
    static ItemStack createEnchantButton(PlayerData playerData, PrisonPickaxe pickaxe, BaseEnchant enchant, GUICreator c, Material icon) {
        List<String> desc = new ArrayList<>(enchant.DESCRIPTION);
        desc.add("");
        desc.add("&bCurrent Level: &f" + Utils.addCommasToNumber(pickaxe.getEnchantLevel(enchant)));
        desc.add("&bUpgrade Cost: &f" + Utils.addCommasToNumber(enchant.PRICE) + " Tokens");
        desc.add("&bYour Tokens: &f" + Utils.prettyNum(playerData.getTokens()));
        desc.add("");
        desc.add("&bMax Level: &f" + Utils.addCommasToNumber(enchant.MAX_LEVEL));
        return c.createNormalItem(icon, enchant.DISPLAY_NAME, desc, (p, t) -> {
            GUICreator _c = new GUICreator(9, enchant.DISPLAY_NAME);
            _c.setOnCloseRun((_p, _t) -> mainMenu(p, pickaxe));
            buildMenuContent(_c, p, enchant, pickaxe);
            _c.open(p);
        });
    }
    static ItemStack createEnchantButton(PlayerData playerData, PrisonPickaxe pickaxe, BaseEnchant enchant, GUICreator c, Material icon, boolean enchantIcon) {
        ItemStack item = createEnchantButton(playerData, pickaxe, enchant, c, icon);
        if (enchantIcon) ench(item);
        return item;
    }

    static void buildMenuContent(GUICreator c, Player player, BaseEnchant enchant, PrisonPickaxe pickaxe) {
        PlayerData playerData = new PlayerData(player);
        c.setItems(
                createGrayPlaceHolder(),
                createGrayPlaceHolder(),
                c.createNormalItem(Material.LIME_STAINED_GLASS_PANE, "&aBuy 1 level of " + enchant.UNFORMATTED_DISPLAY_NAME, List.of("",
                        "&bCurrent Level: &f" + Utils.addCommasToNumber(pickaxe.getEnchantLevel(enchant)),
                        "&bUpgrade Cost: &f" + Utils.addCommasToNumber(enchant.PRICE) + " Tokens",
                        "&bYour Tokens: &f" + Utils.prettyNum(playerData.getTokens()) + " Tokens"
                ), (p, _t) -> {
                    enchant.tryToBuyLevels(p, pickaxe, 1);
                    buildMenuContent(c, p, enchant, pickaxe);
                }),
                c.createNormalItem(Material.LIME_STAINED_GLASS_PANE, "&aBuy 10 levels of " + enchant.UNFORMATTED_DISPLAY_NAME, List.of("",
                        "&bCurrent Level: &f" + Utils.addCommasToNumber(pickaxe.getEnchantLevel(enchant)),
                        "&bUpgrade Cost: &f" + Utils.addCommasToNumber(enchant.PRICE) + " Tokens",
                        "&bYour Tokens: &f" + Utils.prettyNum(playerData.getTokens()) + " Tokens"
                ), (p, _t) -> {
                    enchant.tryToBuyLevels(p, pickaxe, 10);
                    buildMenuContent(c, p, enchant, pickaxe);
                }),
                c.createNormalItem(Material.LIME_STAINED_GLASS_PANE, "&aBuy 100 levels of " + enchant.UNFORMATTED_DISPLAY_NAME, List.of("",
                        "&bCurrent Level: &f" + Utils.addCommasToNumber(pickaxe.getEnchantLevel(enchant)),
                        "&bUpgrade Cost: &f" + Utils.addCommasToNumber(enchant.PRICE) + " Tokens",
                        "&bYour Tokens: &f" + Utils.prettyNum(playerData.getTokens()) + " Tokens"
                ), (p, _t) -> {
                    enchant.tryToBuyLevels(p, pickaxe, 100);
                    buildMenuContent(c, p, enchant, pickaxe);
                }),
                c.createNormalItem(Material.LIME_STAINED_GLASS_PANE, "&aBuy 1,000 levels of " + enchant.UNFORMATTED_DISPLAY_NAME, List.of("",
                        "&bCurrent Level: &f" + Utils.addCommasToNumber(pickaxe.getEnchantLevel(enchant)),
                        "&bUpgrade Cost: &f" + Utils.addCommasToNumber(enchant.PRICE) + " Tokens",
                        "&bYour Tokens: &f" + Utils.prettyNum(playerData.getTokens()) + " Tokens"
                ), (p, _t) -> {
                    enchant.tryToBuyLevels(p, pickaxe, 1000);
                    buildMenuContent(c, p, enchant, pickaxe);
                }),
                c.createNormalItem(Material.LIME_STAINED_GLASS_PANE, "&aBuy MAX (" + Math.min(enchant.MAX_LEVEL - pickaxe.getEnchantLevel(enchant), playerData.getTokens().divide(enchant.PRICE).intValue()) + ") levels of " + enchant.UNFORMATTED_DISPLAY_NAME, List.of("",
                        "&bCurrent Level: &f" + Utils.addCommasToNumber(pickaxe.getEnchantLevel(enchant)),
                        "&bUpgrade Cost: &f" + Utils.addCommasToNumber(enchant.PRICE) + " Tokens",
                        "&bYour Tokens: &f" + Utils.prettyNum(playerData.getTokens()) + " Tokens"
                ), (p, _t) -> {
                    enchant.tryToBuyLevels(p, pickaxe, Math.min(enchant.MAX_LEVEL - pickaxe.getEnchantLevel(enchant), playerData.getTokens().divide(enchant.PRICE).intValue()));
                    buildMenuContent(c, p, enchant, pickaxe);
                }),
                createGrayPlaceHolder(),
                createGrayPlaceHolder()
        );
    }
}