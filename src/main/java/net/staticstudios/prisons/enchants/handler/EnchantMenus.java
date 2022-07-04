package net.staticstudios.prisons.enchants.handler;

import net.staticstudios.gui.GUICreator;
import net.staticstudios.gui.GUIUtils;
import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.enchants.handler.BaseEnchant;
import net.staticstudios.prisons.enchants.handler.PrisonEnchants;
import net.staticstudios.prisons.enchants.handler.PrisonPickaxe;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class EnchantMenus extends GUIUtils {
    public static void selectPickaxe(Player player) {
        GUICreator c = new GUICreator(36, "Select a pickaxe to enchant");
        c.setMenuID("enchants-selectPickaxe");
        int x = 0;
        for (ItemStack item : player.getInventory().getContents()) {
            if (PrisonUtils.checkIsPrisonPickaxe(item)) {
                c.addItem(c.createButton(item, (p, t) -> mainMenu(p, PrisonPickaxe.fromItem(item))));
                x++;
            }
        }
        for (int i = x; i < 36; i++) c.setItem(i, createGrayPlaceHolder());
        c.open(player);
    }
    public static void mainMenu(Player player, PrisonPickaxe pickaxe) {
        PlayerData playerData = new PlayerData(player);
        GUICreator c = new GUICreator(54, "Enchants");
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
                createEnchantButton(playerData, pickaxe, PrisonEnchants.AUTO_SELL, c, Material.GOLD_NUGGET, true),
                createEnchantButton(playerData, pickaxe, PrisonEnchants.XP_FINDER, c, Material.EXPERIENCE_BOTTLE, true),
                createEnchantButton(playerData, pickaxe, PrisonEnchants.BACKPACK_FINDER, c, Material.CHEST_MINECART, true),
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
                createGrayPlaceHolder(),

                createLightGrayPlaceHolder(),
                createLightGrayPlaceHolder(),
                createLightGrayPlaceHolder(),
                createLightGrayPlaceHolder(),
                c.createButton(Material.GUNPOWDER, "&c&lEnchant Settings", List.of("Enable/disable an enchant"), (p, t) -> {
                    openSettings(p, pickaxe);
                }),
                createLightGrayPlaceHolder(),
                createLightGrayPlaceHolder(),
                createLightGrayPlaceHolder(),
                createLightGrayPlaceHolder()
        );
        c.open(player);
        //c.setOnCloseRun((p, t) -> MainMenus.open(p));
    }

    public static void openSettings(Player player, PrisonPickaxe pickaxe) {
        PlayerData playerData = new PlayerData(player);
        GUICreator c = new GUICreator(45, "Enchant Settings");
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
                createEnchantSettingsButton( pickaxe, PrisonEnchants.FORTUNE, c, Material.DIAMOND, true),
                createEnchantSettingsButton(pickaxe, PrisonEnchants.DOUBLE_FORTUNE, c, Material.AMETHYST_SHARD, true),
                createEnchantSettingsButton(pickaxe, PrisonEnchants.TOKENATOR, c, Material.SUNFLOWER, true),
                createEnchantSettingsButton(pickaxe, PrisonEnchants.KEY_FINDER, c, Material.TRIPWIRE_HOOK, true),
                createEnchantSettingsButton(pickaxe, PrisonEnchants.METAL_DETECTOR, c, Material.RAW_GOLD, true),
                createGrayPlaceHolder(),
                createEnchantSettingsButton(pickaxe, PrisonEnchants.HASTE, c, Material.GOLDEN_PICKAXE, true),
                createGrayPlaceHolder(),
                createGrayPlaceHolder(),
                createEnchantSettingsButton(pickaxe, PrisonEnchants.EXPLOSION, c, Material.TNT, true),
                createEnchantSettingsButton(pickaxe, PrisonEnchants.JACK_HAMMER, c, Material.ANVIL, true),
                createEnchantSettingsButton(pickaxe, PrisonEnchants.DOUBLE_JACK_HAMMER, c, Material.HOPPER, true),
                createEnchantSettingsButton(pickaxe, PrisonEnchants.MULTI_DIRECTIONAL, c, Material.COMPARATOR, true),
                createEnchantSettingsButton(pickaxe, PrisonEnchants.MERCHANT, c, Material.MAP, true),
                createGrayPlaceHolder(),
                createEnchantSettingsButton(pickaxe, PrisonEnchants.SPEED, c, Material.FEATHER, true),
                createGrayPlaceHolder(),
                createGrayPlaceHolder(),
                createEnchantSettingsButton(pickaxe, PrisonEnchants.CONSISTENCY, c, Material.EMERALD, true),
                createEnchantSettingsButton(pickaxe, PrisonEnchants.EGG_SHOOTER, c, Material.EGG, true),
                createEnchantSettingsButton(pickaxe, PrisonEnchants.AUTO_SELL, c, Material.GOLD_NUGGET, true),
                createEnchantSettingsButton(pickaxe, PrisonEnchants.XP_FINDER, c, Material.EXPERIENCE_BOTTLE, true),
                createEnchantSettingsButton(pickaxe, PrisonEnchants.BACKPACK_FINDER, c, Material.CHEST_MINECART, true),
                createGrayPlaceHolder(),
                createEnchantSettingsButton(pickaxe, PrisonEnchants.NIGHT_VISION, c, Material.LAPIS_LAZULI, true),
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
        c.setOnCloseRun((p, t) -> mainMenu(p, pickaxe));
    }


    //---------- vvv Util methods vvv ----------
    static ItemStack createEnchantButton(PlayerData playerData, PrisonPickaxe pickaxe, BaseEnchant enchant, GUICreator c, Material icon) {
        List<String> desc = new ArrayList<>(enchant.DESCRIPTION);
        desc.add("");
        desc.add("&bCurrent Level: &f" + PrisonUtils.addCommasToNumber(pickaxe.getEnchantLevel(enchant)));
        desc.add("&bUpgrade Cost: &f" + PrisonUtils.addCommasToNumber(enchant.PRICE) + " Tokens");
        desc.add("&bYour Tokens: &f" + PrisonUtils.prettyNum(playerData.getTokens()));
        desc.add("");
        desc.add("&bMax Level: &f" + PrisonUtils.addCommasToNumber(enchant.MAX_LEVEL));
        if (!pickaxe.getIsEnchantEnabled(enchant)) {
            desc.add("");
            desc.add("&c&oThis enchant has been disabled in the pickaxe's settings!");
        }
        return c.createButton(icon, enchant.DISPLAY_NAME, desc, (p, t) -> {
            GUICreator _c = new GUICreator(9, enchant.DISPLAY_NAME);
            _c.setOnCloseRun((_p, _t) -> mainMenu(p, pickaxe));
            buildMenuContent(_c, p, enchant, pickaxe);
            _c.open(p);
        });
    }
    static ItemStack createEnchantSettingsButton(PrisonPickaxe pickaxe, BaseEnchant enchant, GUICreator c, Material icon, boolean enchantIcon) {
        ItemStack item = createEnchantSettingsButton(pickaxe, enchant, c, icon);
        if (enchantIcon) ench(item);
        return item;
    }
    static ItemStack createEnchantSettingsButton(PrisonPickaxe pickaxe, BaseEnchant enchant, GUICreator c, Material icon) {
        boolean isEnabled = pickaxe.getIsEnchantEnabled(enchant);
        List<String> desc = new ArrayList<>(enchant.DESCRIPTION);
        desc.add("");
        desc.add("&bLevel: &f" + PrisonUtils.addCommasToNumber(pickaxe.getEnchantLevel(enchant)) + " / " + PrisonUtils.addCommasToNumber(enchant.MAX_LEVEL));
        desc.add("&bCurrent State: &f" + (isEnabled ? "&aEnabled" : "&cDisabled"));
        desc.add("");
        desc.add("&7&oClick to " + (isEnabled ? "disable" : "enable"));
        ItemStack button = c.createButton(icon, enchant.DISPLAY_NAME, desc, (p, t) -> {
            pickaxe.setIsEnchantEnabled(p, enchant, !pickaxe.getIsEnchantEnabled(enchant));
            openSettings(p, pickaxe);
        });
        if (isEnabled) button = ench(button);
        return button;
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
                c.createButton(Material.LIME_STAINED_GLASS_PANE, "&aBuy 1 level of " + enchant.UNFORMATTED_DISPLAY_NAME, List.of("",
                        "&bCurrent Level: &f" + PrisonUtils.addCommasToNumber(pickaxe.getEnchantLevel(enchant)),
                        "&bUpgrade Cost: &f" + PrisonUtils.addCommasToNumber(enchant.PRICE) + " Tokens",
                        "&bYour Tokens: &f" + PrisonUtils.prettyNum(playerData.getTokens()) + " Tokens"
                ), (p, _t) -> {
                    enchant.tryToBuyLevels(p, pickaxe, 1);
                    buildMenuContent(c, p, enchant, pickaxe);
                }),
                c.createButton(Material.LIME_STAINED_GLASS_PANE, "&aBuy 10 levels of " + enchant.UNFORMATTED_DISPLAY_NAME, List.of("",
                        "&bCurrent Level: &f" + PrisonUtils.addCommasToNumber(pickaxe.getEnchantLevel(enchant)),
                        "&bUpgrade Cost: &f" + PrisonUtils.addCommasToNumber(enchant.PRICE.multiply(BigInteger.valueOf(10))) + " Tokens",
                        "&bYour Tokens: &f" + PrisonUtils.prettyNum(playerData.getTokens()) + " Tokens"
                ), (p, _t) -> {
                    enchant.tryToBuyLevels(p, pickaxe, 10);
                    buildMenuContent(c, p, enchant, pickaxe);
                }),
                c.createButton(Material.LIME_STAINED_GLASS_PANE, "&aBuy 100 levels of " + enchant.UNFORMATTED_DISPLAY_NAME, List.of("",
                        "&bCurrent Level: &f" + PrisonUtils.addCommasToNumber(pickaxe.getEnchantLevel(enchant)),
                        "&bUpgrade Cost: &f" + PrisonUtils.addCommasToNumber(enchant.PRICE.multiply(BigInteger.valueOf(100))) + " Tokens",
                        "&bYour Tokens: &f" + PrisonUtils.prettyNum(playerData.getTokens()) + " Tokens"
                ), (p, _t) -> {
                    enchant.tryToBuyLevels(p, pickaxe, 100);
                    buildMenuContent(c, p, enchant, pickaxe);
                }),
                c.createButton(Material.LIME_STAINED_GLASS_PANE, "&aBuy 1,000 levels of " + enchant.UNFORMATTED_DISPLAY_NAME, List.of("",
                        "&bCurrent Level: &f" + PrisonUtils.addCommasToNumber(pickaxe.getEnchantLevel(enchant)),
                        "&bUpgrade Cost: &f" + PrisonUtils.addCommasToNumber(enchant.PRICE.multiply(BigInteger.valueOf(1000))) + " Tokens",
                        "&bYour Tokens: &f" + PrisonUtils.prettyNum(playerData.getTokens()) + " Tokens"
                ), (p, _t) -> {
                    enchant.tryToBuyLevels(p, pickaxe, 1000);
                    buildMenuContent(c, p, enchant, pickaxe);
                }),
                c.createButton(Material.LIME_STAINED_GLASS_PANE, "&aBuy MAX (" + Math.min((long) enchant.MAX_LEVEL - pickaxe.getEnchantLevel(enchant), playerData.getTokens().divide(enchant.PRICE).intValue()) + ") levels of " + enchant.UNFORMATTED_DISPLAY_NAME, List.of("",
                        "&bCurrent Level: &f" + PrisonUtils.addCommasToNumber(pickaxe.getEnchantLevel(enchant)),
                        "&bUpgrade Cost: &f" + PrisonUtils.addCommasToNumber(enchant.PRICE.multiply(BigInteger.valueOf(Math.min((long) enchant.MAX_LEVEL - pickaxe.getEnchantLevel(enchant), playerData.getTokens().divide(enchant.PRICE).intValue())))) + " Tokens",
                        "&bYour Tokens: &f" + PrisonUtils.prettyNum(playerData.getTokens()) + " Tokens"
                ), (p, _t) -> {
                    enchant.tryToBuyLevels(p, pickaxe, Math.min(enchant.MAX_LEVEL - pickaxe.getEnchantLevel(enchant), playerData.getTokens().divide(enchant.PRICE).intValue()));
                    buildMenuContent(c, p, enchant, pickaxe);
                }),
                createGrayPlaceHolder(),
                createGrayPlaceHolder()
        );
    }
}