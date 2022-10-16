package net.staticstudios.prisons.pickaxe.gui;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.staticstudios.gui.GUICreator;
import net.staticstudios.gui.GUIUtils;
import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.enchants.Enchantable;
import net.staticstudios.prisons.enchants.Enchantment;
import net.staticstudios.prisons.pickaxe.PrisonPickaxe;
import net.staticstudios.prisons.pickaxe.enchants.*;
import net.staticstudios.prisons.pickaxe.enchants.handler.PickaxeEnchant;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class EnchantMenus extends GUIUtils {

    public static void selectPickaxe(Player player) {
        GUICreator c = new GUICreator(36, "Select a pickaxe to enchant");
        for (ItemStack item : player.getInventory().getContents()) {
            if (PrisonPickaxe.checkIsPrisonPickaxe(item)) {
                c.addItem(c.createButton(item, (p, t) -> {
                    mainMenu(p, PrisonPickaxe.fromItem(item));
                }));
            }
        }
        c.fill(createGrayPlaceHolder());
        c.open(player);
    }

    public static void mainMenu(Player player, PrisonPickaxe pickaxe) {
        PlayerData playerData = new PlayerData(player);
        GUICreator c = new GUICreator(54, "Pickaxe Enchants");
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
                createEnchantButton(playerData, pickaxe, FortuneEnchant.class, c, Material.DIAMOND, false),
                createEnchantButton(playerData, pickaxe, OreSplitterEnchant.class, c, Material.AMETHYST_SHARD, false),
                createEnchantButton(playerData, pickaxe, TokenatorEnchant.class, c, Material.SUNFLOWER, false),
                createEnchantButton(playerData, pickaxe, KeyFinderEnchant.class, c, Material.TRIPWIRE_HOOK, false),
                createEnchantButton(playerData, pickaxe, MetalDetectorEnchant.class, c, Material.FLINT_AND_STEEL, false),
                createGrayPlaceHolder(),
                createEnchantButton(playerData, pickaxe, HasteEnchant.class, c, Material.GOLDEN_PICKAXE, false),
                createGrayPlaceHolder(),
                createGrayPlaceHolder(),
                createEnchantButton(playerData, pickaxe, ExplosionEnchant.class, c, Material.TNT, false),
                createEnchantButton(playerData, pickaxe, JackHammerEnchant.class, c, Material.ANVIL, false),
                createEnchantButton(playerData, pickaxe, DoubleWammyEnchant.class, c, Material.HOPPER, false),
                createEnchantButton(playerData, pickaxe, MultiDirectionalEnchant.class, c, Material.COMPARATOR, false),
                createEnchantButton(playerData, pickaxe, EggShooterEnchant.class, c, Material.EGG, false),
                createGrayPlaceHolder(),
                createEnchantButton(playerData, pickaxe, SpeedEnchant.class, c, Material.FEATHER, false),
                createGrayPlaceHolder(),
                createGrayPlaceHolder(),
                createEnchantButton(playerData, pickaxe, ConsistencyEnchant.class, c, Material.EMERALD, false),
                createEnchantButton(playerData, pickaxe, MerchantEnchant.class, c, Material.MAP, false),
                createEnchantButton(playerData, pickaxe, AutoSellEnchant.class, c, Material.GOLD_NUGGET, false),
                createEnchantButton(playerData, pickaxe, XPFinderEnchant.class, c, Material.EXPERIENCE_BOTTLE, false),
                createEnchantButton(playerData, pickaxe, BackpackFinderEnchant.class, c, Material.CHEST_MINECART, false),
                createGrayPlaceHolder(),
                createEnchantButton(playerData, pickaxe, NightVisionEnchant.class, c, Material.LAPIS_LAZULI, false),
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

                c.createButton(Material.SUNFLOWER, "&e&lYour Tokens: &f" + PrisonUtils.prettyNum(playerData.getTokens()), List.of()),
                createLightGrayPlaceHolder(),
                createLightGrayPlaceHolder(),
                createLightGrayPlaceHolder(),
                c.createButton(Material.GUNPOWDER, "&c&lEnchant Settings", List.of("Enable/disable an enchant"), (p, t) -> {
                    openSettings(p, pickaxe);
                }),
                createLightGrayPlaceHolder(),
                createLightGrayPlaceHolder(),
                createLightGrayPlaceHolder(),
                c.createButton(Material.AMETHYST_CLUSTER, "&d&lYour Prestige Tokens: &f" + PrisonUtils.prettyNum(playerData.getPrestigeTokens()), List.of())
        );
        c.open(player);
        c.setOnCloseRun((p, t) -> PickaxeMenus.open(p, pickaxe));
    }

    public static void openSettings(Player player, PrisonPickaxe pickaxe) {
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
                createEnchantSettingsButton( pickaxe, FortuneEnchant.class, c, Material.DIAMOND, true),
                createEnchantSettingsButton(pickaxe, OreSplitterEnchant.class, c, Material.AMETHYST_SHARD, true),
                createEnchantSettingsButton(pickaxe, TokenatorEnchant.class, c, Material.SUNFLOWER, true),
                createEnchantSettingsButton(pickaxe, KeyFinderEnchant.class, c, Material.TRIPWIRE_HOOK, true),
                createEnchantSettingsButton(pickaxe, MetalDetectorEnchant.class, c, Material.FLINT_AND_STEEL, true),
                createGrayPlaceHolder(),
                createEnchantSettingsButton(pickaxe, HasteEnchant.class, c, Material.GOLDEN_PICKAXE, true),
                createGrayPlaceHolder(),
                createGrayPlaceHolder(),
                createEnchantSettingsButton(pickaxe, ExplosionEnchant.class, c, Material.TNT, true),
                createEnchantSettingsButton(pickaxe, JackHammerEnchant.class, c, Material.ANVIL, true),
                createEnchantSettingsButton(pickaxe, DoubleWammyEnchant.class, c, Material.HOPPER, true),
                createEnchantSettingsButton(pickaxe, MultiDirectionalEnchant.class, c, Material.COMPARATOR, true),
                createEnchantSettingsButton(pickaxe, EggShooterEnchant.class, c, Material.EGG, true),
                createGrayPlaceHolder(),
                createEnchantSettingsButton(pickaxe, SpeedEnchant.class, c, Material.FEATHER, true),
                createGrayPlaceHolder(),
                createGrayPlaceHolder(),
                createEnchantSettingsButton(pickaxe, ConsistencyEnchant.class, c, Material.EMERALD, true),
                createEnchantSettingsButton(pickaxe, MerchantEnchant.class, c, Material.MAP, true),
                createEnchantSettingsButton(pickaxe, AutoSellEnchant.class, c, Material.GOLD_NUGGET, true),
                createEnchantSettingsButton(pickaxe, XPFinderEnchant.class, c, Material.EXPERIENCE_BOTTLE, true),
                createEnchantSettingsButton(pickaxe, BackpackFinderEnchant.class, c, Material.CHEST_MINECART, true),
                createGrayPlaceHolder(),
                createEnchantSettingsButton(pickaxe, NightVisionEnchant.class, c, Material.LAPIS_LAZULI, true),
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
    static ItemStack createEnchantButton(PlayerData playerData, PrisonPickaxe pickaxe, Class<? extends Enchantment> enchant, GUICreator c, Material icon) {
        PickaxeEnchant pickaxeEnchantment = (PickaxeEnchant) Enchantable.getEnchant(enchant);
        List<Component> desc = new ArrayList<>(pickaxeEnchantment.getDescription());
        desc.add(Component.empty());
        desc.add(LegacyComponentSerializer.legacyAmpersand().deserialize("&bCurrent Level: &f" + PrisonUtils.addCommasToNumber(pickaxe.getEnchantmentLevel(enchant))));
        desc.add(LegacyComponentSerializer.legacyAmpersand().deserialize("&bUpgrade Cost: &f" + PrisonUtils.addCommasToNumber(pickaxeEnchantment.getUpgradeCost()) + " Tokens"));
        desc.add(LegacyComponentSerializer.legacyAmpersand().deserialize("&bYour Tokens: &f" + PrisonUtils.prettyNum(playerData.getTokens())));
        desc.add(Component.empty());
        desc.add(LegacyComponentSerializer.legacyAmpersand().deserialize("&bMax Level: &f" + PrisonUtils.addCommasToNumber(pickaxeEnchantment.getMaxLevel(pickaxe.getEnchantmentTier(enchant)))));
        boolean locked = pickaxeEnchantment.getLevelRequirement() > pickaxe.getLevel();
        if (locked) {
            desc.add(Component.newline());
            desc.add(LegacyComponentSerializer.legacyAmpersand().deserialize("&cMinimum Pickaxe Level: &f" + pickaxeEnchantment.getLevelRequirement()));
            return c.createButton(icon, LegacyComponentSerializer.legacyAmpersand().deserialize("&c&l[Locked] " + pickaxeEnchantment.getName()), desc, (p, t) -> {
                if (pickaxeEnchantment.getLevelRequirement() > pickaxe.getLevel()) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYour pickaxe is not high enough level to unlock this enchant!"));
                } else{
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou are not a high enough level to unlock this enchant!"));
                }
            });
        }
        String name = pickaxeEnchantment.getName();
        if (!pickaxe.getEnabledEnchantments().containsKey(enchant)) {
            desc.add(Component.empty());
            name = "&c&l[Disabled] " + name;
        }
        return c.createButton(icon, LegacyComponentSerializer.legacyAmpersand().deserialize(name), desc, (p, t) -> {
            GUICreator _c = new GUICreator(9, pickaxeEnchantment.getName());
            _c.setOnCloseRun((_p, _t) -> mainMenu(p, pickaxe));
            buildMenuContent(_c, p, enchant, pickaxe);
            _c.open(p);
        });
    }
    static ItemStack createEnchantSettingsButton(PrisonPickaxe pickaxe, Class<? extends Enchantment> enchant, GUICreator c, Material icon, boolean enchantIcon) {
        ItemStack item = createEnchantSettingsButton(pickaxe, enchant, c, icon);
        if (enchantIcon) ench(item);
        return item;
    }
    static ItemStack createEnchantSettingsButton(PrisonPickaxe pickaxe, Class<? extends Enchantment> enchant, GUICreator c, Material icon) {
        PickaxeEnchant pickaxeEnchantment = (PickaxeEnchant) Enchantable.getEnchant(enchant);
        boolean isEnabled = pickaxe.getEnabledEnchantments().containsKey(enchant);
        List<Component> desc = new ArrayList<>(pickaxeEnchantment.getDescription());
        desc.add(Component.empty());
        desc.add(LegacyComponentSerializer.legacyAmpersand().deserialize("&bLevel: &f" + PrisonUtils.addCommasToNumber(pickaxe.getEnchantmentLevel(enchant)) + " / " + PrisonUtils.addCommasToNumber(pickaxeEnchantment.getMaxLevel(pickaxe.getEnchantmentTier(enchant)))));
        desc.add(LegacyComponentSerializer.legacyAmpersand().deserialize("&bCurrent State: &f" + (isEnabled ? "&aEnabled" : "&cDisabled")));
        desc.add(Component.empty());
        desc.add(LegacyComponentSerializer.legacyAmpersand().deserialize("&7&oClick to " + (isEnabled ? "disable" : "enable")));
        ItemStack button = c.createButton(icon, pickaxeEnchantment.getNameAsComponent(), desc, (p, t) -> {
            if (pickaxe.getEnabledEnchantments().containsKey(enchant)) {
                pickaxe.disableEnchantment(enchant, p);
            } else {
                pickaxe.enableEnchantment(enchant, p);
            }
            openSettings(p, pickaxe);
        });
        if (isEnabled) button = ench(button);
        return button;
    }
    static ItemStack createEnchantButton(PlayerData playerData, PrisonPickaxe pickaxe, Class<? extends Enchantment> enchant, GUICreator c, Material icon, boolean enchantIcon) {
        ItemStack item = createEnchantButton(playerData, pickaxe, enchant, c, icon);
        if (enchantIcon) {
            return ench(item);
        }
        return item;
    }

    static void buildMenuContent(GUICreator c, Player player, Class<? extends Enchantment> enchant, PrisonPickaxe pickaxe) {
        PickaxeEnchant pickaxeEnchantment = (PickaxeEnchant) Enchantable.getEnchant(enchant);
        PlayerData playerData = new PlayerData(player);
        c.setItems(
                createGrayPlaceHolder(),
                createGrayPlaceHolder(),
                c.createButton(Material.LIME_STAINED_GLASS_PANE, "&aBuy 1 level of " + pickaxeEnchantment.getName(), List.of("",
                        "&bCurrent Level: &f" + PrisonUtils.addCommasToNumber(pickaxe.getEnchantmentLevel(enchant)),
                        "&bUpgrade Cost: &f" + PrisonUtils.addCommasToNumber(pickaxeEnchantment.getUpgradeCost()) + " Tokens",
                        "&bYour Tokens: &f" + PrisonUtils.prettyNum(playerData.getTokens()) + " Tokens",
                        "",
                        "&bMax Level: &f" + PrisonUtils.addCommasToNumber(pickaxeEnchantment.getMaxLevel(pickaxe.getEnchantmentTier(enchant)))
                ), (p, _t) -> {
                    pickaxe.upgrade(enchant, player, 1);
                    buildMenuContent(c, p, enchant, pickaxe);
                }),
                c.createButton(Material.LIME_STAINED_GLASS_PANE, "&aBuy 10 levels of " + pickaxeEnchantment.getName(), List.of("",
                        "&bCurrent Level: &f" + PrisonUtils.addCommasToNumber(pickaxe.getEnchantmentLevel(enchant)),
                        "&bUpgrade Cost: &f" + PrisonUtils.addCommasToNumber(pickaxeEnchantment.getUpgradeCost() * 10) + " Tokens",
                        "&bYour Tokens: &f" + PrisonUtils.prettyNum(playerData.getTokens()) + " Tokens",
                        "",
                        "&bMax Level: &f" + PrisonUtils.addCommasToNumber(pickaxeEnchantment.getMaxLevel(pickaxe.getEnchantmentTier(enchant)))
                ), (p, _t) -> {
                    pickaxe.upgrade(enchant, player, 10);
                    buildMenuContent(c, p, enchant, pickaxe);
                }),
                c.createButton(Material.LIME_STAINED_GLASS_PANE, "&aBuy 100 levels of " + pickaxeEnchantment.getName(), List.of("",
                        "&bCurrent Level: &f" + PrisonUtils.addCommasToNumber(pickaxe.getEnchantmentLevel(enchant)),
                        "&bUpgrade Cost: &f" + PrisonUtils.addCommasToNumber(pickaxeEnchantment.getUpgradeCost() * 100) + " Tokens",
                        "&bYour Tokens: &f" + PrisonUtils.prettyNum(playerData.getTokens()) + " Tokens",
                        "",
                        "&bMax Level: &f" + PrisonUtils.addCommasToNumber(pickaxeEnchantment.getMaxLevel(pickaxe.getEnchantmentTier(enchant)))
                ), (p, _t) -> {
                    pickaxe.upgrade(enchant, player, 100);
                    buildMenuContent(c, p, enchant, pickaxe);
                }),
                c.createButton(Material.LIME_STAINED_GLASS_PANE, "&aBuy 1,000 levels of " + pickaxeEnchantment.getName(), List.of("",
                        "&bCurrent Level: &f" + PrisonUtils.addCommasToNumber(pickaxe.getEnchantmentLevel(enchant)),
                        "&bUpgrade Cost: &f" + PrisonUtils.addCommasToNumber(pickaxeEnchantment.getUpgradeCost() * 1000) + " Tokens",
                        "&bYour Tokens: &f" + PrisonUtils.prettyNum(playerData.getTokens()) + " Tokens",
                        "",
                        "&bMax Level: &f" + PrisonUtils.addCommasToNumber(pickaxeEnchantment.getMaxLevel(pickaxe.getEnchantmentTier(enchant)))
                ), (p, _t) -> {
                    pickaxe.upgrade(enchant, player, 1000);
                    buildMenuContent(c, p, enchant, pickaxe);
                }),
                c.createButton(Material.LIME_STAINED_GLASS_PANE, "&aBuy MAX (" + Math.min((long) pickaxeEnchantment.getMaxLevel(pickaxe.getEnchantmentTier(enchant)) - pickaxe.getEnchantmentLevel(enchant), playerData.getTokens() / pickaxeEnchantment.getUpgradeCost()) + ") levels of " + pickaxeEnchantment.getName(), List.of("",
                        "&bCurrent Level: &f" + PrisonUtils.addCommasToNumber(pickaxe.getEnchantmentLevel(enchant)),
                        "&bUpgrade Cost: &f" + PrisonUtils.addCommasToNumber(pickaxeEnchantment.getUpgradeCost() * Math.min((long) pickaxeEnchantment.getMaxLevel(pickaxe.getEnchantmentTier(enchant)) - pickaxe.getEnchantmentLevel(enchant), playerData.getTokens() / pickaxeEnchantment.getUpgradeCost())) + " Tokens",
                        "&bYour Tokens: &f" + PrisonUtils.prettyNum(playerData.getTokens()) + " Tokens",
                        "",
                        "&bMax Level: &f" + PrisonUtils.addCommasToNumber(pickaxeEnchantment.getMaxLevel(pickaxe.getEnchantmentTier(enchant)))
                ), (p, _t) -> {
                    pickaxe.upgrade(enchant, player, (int) Math.min(pickaxeEnchantment.getMaxLevel(pickaxe.getEnchantmentTier(enchant)) - pickaxe.getEnchantmentLevel(enchant), playerData.getTokens() / pickaxeEnchantment.getUpgradeCost()));
                    buildMenuContent(c, p, enchant, pickaxe);
                }),
                createGrayPlaceHolder(),
                createGrayPlaceHolder()
        );

        if (pickaxeEnchantment.getTier(pickaxe.getEnchantmentTier(enchant) + 1) != null) {
            c.setItem(0, c.createButton(Material.NETHER_STAR, "&b&lIncrease Tier", List.of(
                    "Increasing the tier of this enchantment",
                    "will set its max level to " + PrisonUtils.addCommasToNumber(pickaxeEnchantment.getMaxLevel(pickaxe.getEnchantmentTier(enchant) + 1)) + ". This will",
                    "cost " + PrisonUtils.addCommasToNumber(pickaxeEnchantment.getTier(pickaxe.getEnchantmentTier(enchant) + 1).prestigeTokensRequired()) + " Prestige Token(s), which can",
                    "be obtained from prestiging.",
                    "",
                    "&eCurrent Tier: &f" + pickaxe.getEnchantmentTier(enchant),
                    "&eNext Tier: &f" + (pickaxe.getEnchantmentTier(enchant) + 1),
                    "&eUpgrade Cost: &f" + PrisonUtils.addCommasToNumber(pickaxeEnchantment.getTier(pickaxe.getEnchantmentTier(enchant) + 1).prestigeTokensRequired()) + " Prestige Tokens",
                    "&eYour Prestige Tokens: &f" + PrisonUtils.prettyNum(playerData.getPrestigeTokens())
            ), (p, t) -> {
                if (playerData.getPrestigeTokens() >= pickaxeEnchantment.getTier(pickaxe.getEnchantmentTier(enchant) + 1).prestigeTokensRequired()) {
                    playerData.removePrestigeTokens(pickaxeEnchantment.getTier(pickaxe.getEnchantmentTier(enchant) + 1).prestigeTokensRequired());
                    pickaxe.setEnchantmentTier(enchant, pickaxe.getEnchantmentTier(enchant) + 1);
                    p.sendMessage(org.bukkit.ChatColor.AQUA + "You successfully upgraded your pickaxe!");
                    buildMenuContent(c, p, enchant, pickaxe);
                } else {
                    p.sendMessage(ChatColor.RED + "You do not have enough Prestige Tokens to upgrade this!");
                }
            }));
        }

    }
}