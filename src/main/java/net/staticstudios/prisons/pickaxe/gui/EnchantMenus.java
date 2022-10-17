package net.staticstudios.prisons.pickaxe.gui;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.staticstudios.newgui.GUIButton;
import net.staticstudios.newgui.GUIPlaceholders;
import net.staticstudios.newgui.StaticGUI;
import net.staticstudios.newgui.builder.ButtonBuilder;
import net.staticstudios.newgui.builder.GUIBuilder;
import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.enchants.Enchantable;
import net.staticstudios.prisons.enchants.Enchantment;
import net.staticstudios.prisons.pickaxe.PrisonPickaxe;
import net.staticstudios.prisons.pickaxe.enchants.*;
import net.staticstudios.prisons.pickaxe.enchants.handler.PickaxeEnchant;
import net.staticstudios.prisons.utils.ComponentUtil;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class EnchantMenus {
    //todo: settings menu & abilities menu

    public static void selectPickaxe(Player player) {
        StaticGUI gui = GUIBuilder.builder()
                .title("Select a pickaxe to enchant")
                .size(36)
                .fillWith(GUIPlaceholders.GRAY)
                .build();
        int i = 0;
        for (ItemStack item : player.getInventory().getContents()) {
            if (PrisonPickaxe.checkIsPrisonPickaxe(item)) {
                gui.setButton(i, ButtonBuilder.builder()
                        .fromItem(item)
                        .onLeftClick(plr -> mainMenu(plr, PrisonPickaxe.fromItem(item)))
                        .build());
                i++;
            }
        }
        gui.open(player);
    }

    public static void mainMenu(Player player, PrisonPickaxe pickaxe) {
        PlayerData playerData = new PlayerData(player);
        StaticGUI gui = GUIBuilder.builder()
                .title("Pickaxe Enchantments")
                .size(54)
                .onClose((plr, g) -> PickaxeMenus.open(plr, pickaxe))
                .build();

        gui.setButtons(
                GUIPlaceholders.GRAY,
                GUIPlaceholders.GRAY,
                GUIPlaceholders.GRAY,
                GUIPlaceholders.GRAY,
                GUIPlaceholders.GRAY,
                GUIPlaceholders.GRAY,
                GUIPlaceholders.GRAY,
                GUIPlaceholders.GRAY,
                GUIPlaceholders.GRAY,
                GUIPlaceholders.GRAY,
                createEnchantButton(playerData, pickaxe, FortuneEnchant.class, Material.DIAMOND),
                createEnchantButton(playerData, pickaxe, OreSplitterEnchant.class, Material.AMETHYST_SHARD),
                createEnchantButton(playerData, pickaxe, TokenatorEnchant.class, Material.SUNFLOWER),
                createEnchantButton(playerData, pickaxe, KeyFinderEnchant.class, Material.TRIPWIRE_HOOK),
                createEnchantButton(playerData, pickaxe, MetalDetectorEnchant.class, Material.FLINT_AND_STEEL),
                GUIPlaceholders.GRAY,
                createEnchantButton(playerData, pickaxe, HasteEnchant.class, Material.GOLDEN_PICKAXE),
                GUIPlaceholders.GRAY,
                GUIPlaceholders.GRAY,
                createEnchantButton(playerData, pickaxe, ExplosionEnchant.class, Material.TNT),
                createEnchantButton(playerData, pickaxe, JackHammerEnchant.class, Material.ANVIL),
                createEnchantButton(playerData, pickaxe, DoubleWammyEnchant.class, Material.HOPPER),
                createEnchantButton(playerData, pickaxe, MultiDirectionalEnchant.class, Material.COMPARATOR),
                createEnchantButton(playerData, pickaxe, EggShooterEnchant.class, Material.EGG),
                GUIPlaceholders.GRAY,
                createEnchantButton(playerData, pickaxe, SpeedEnchant.class, Material.FEATHER),
                GUIPlaceholders.GRAY,
                GUIPlaceholders.GRAY,
                createEnchantButton(playerData, pickaxe, ConsistencyEnchant.class, Material.EMERALD),
                createEnchantButton(playerData, pickaxe, MerchantEnchant.class, Material.MAP),
                createEnchantButton(playerData, pickaxe, AutoSellEnchant.class, Material.GOLD_NUGGET),
                createEnchantButton(playerData, pickaxe, XPFinderEnchant.class, Material.EXPERIENCE_BOTTLE),
                createEnchantButton(playerData, pickaxe, BackpackFinderEnchant.class, Material.CHEST_MINECART),
                GUIPlaceholders.GRAY,
                createEnchantButton(playerData, pickaxe, NightVisionEnchant.class, Material.LAPIS_LAZULI),
                GUIPlaceholders.GRAY,
                GUIPlaceholders.GRAY,
                GUIPlaceholders.GRAY,
                GUIPlaceholders.GRAY,
                GUIPlaceholders.GRAY,
                GUIPlaceholders.GRAY,
                GUIPlaceholders.GRAY,
                GUIPlaceholders.GRAY,
                GUIPlaceholders.GRAY,
                GUIPlaceholders.GRAY,

                ButtonBuilder.builder()
                        .icon(Material.SUNFLOWER)
                        .name("&e&lYour Tokens: &f" + PrisonUtils.prettyNum(playerData.getTokens()))
                        .build(),
                GUIPlaceholders.LIGHT_GRAY,
                GUIPlaceholders.LIGHT_GRAY,
                GUIPlaceholders.LIGHT_GRAY,
                ButtonBuilder.builder()
                        .icon(Material.GUNPOWDER)
                        .name("&c&lEnchant Settings")
                        .lore(List.of(Component.text("Enable/disable your enchantments").color(ComponentUtil.LIGHT_GRAY).decoration(TextDecoration.ITALIC, false)))
                        .onLeftClick(plr -> openSettings(plr, pickaxe))
                        .build(),
                GUIPlaceholders.LIGHT_GRAY,
                GUIPlaceholders.LIGHT_GRAY,
                GUIPlaceholders.LIGHT_GRAY,
                ButtonBuilder.builder()
                        .icon(Material.AMETHYST_CLUSTER)
                        .name("&d&lYour Prestige Tokens: &f" + PrisonUtils.prettyNum(playerData.getPrestigeTokens()))
                        .build()
        );

        gui.open(player);
    }

    public static void openSettings(Player player, PrisonPickaxe pickaxe) {
//        GUICreator c = new GUICreator(45, "Enchant Settings");
//        c.setItems(
//                GUIPlaceholders.GRAY,
//                GUIPlaceholders.GRAY,
//                GUIPlaceholders.GRAY,
//                GUIPlaceholders.GRAY,
//                GUIPlaceholders.GRAY,
//                GUIPlaceholders.GRAY,
//                GUIPlaceholders.GRAY,
//                GUIPlaceholders.GRAY,
//                GUIPlaceholders.GRAY,
//                GUIPlaceholders.GRAY,
//                createEnchantSettingsButton( pickaxe, FortuneEnchant.class, Material.DIAMOND, true),
//                createEnchantSettingsButton(pickaxe, OreSplitterEnchant.class, Material.AMETHYST_SHARD, true),
//                createEnchantSettingsButton(pickaxe, TokenatorEnchant.class, Material.SUNFLOWER, true),
//                createEnchantSettingsButton(pickaxe, KeyFinderEnchant.class, Material.TRIPWIRE_HOOK, true),
//                createEnchantSettingsButton(pickaxe, MetalDetectorEnchant.class, Material.FLINT_AND_STEEL, true),
//                GUIPlaceholders.GRAY,
//                createEnchantSettingsButton(pickaxe, HasteEnchant.class, Material.GOLDEN_PICKAXE, true),
//                GUIPlaceholders.GRAY,
//                GUIPlaceholders.GRAY,
//                createEnchantSettingsButton(pickaxe, ExplosionEnchant.class, Material.TNT, true),
//                createEnchantSettingsButton(pickaxe, JackHammerEnchant.class, Material.ANVIL, true),
//                createEnchantSettingsButton(pickaxe, DoubleWammyEnchant.class, Material.HOPPER, true),
//                createEnchantSettingsButton(pickaxe, MultiDirectionalEnchant.class, Material.COMPARATOR, true),
//                createEnchantSettingsButton(pickaxe, EggShooterEnchant.class, Material.EGG, true),
//                GUIPlaceholders.GRAY,
//                createEnchantSettingsButton(pickaxe, SpeedEnchant.class, Material.FEATHER, true),
//                GUIPlaceholders.GRAY,
//                GUIPlaceholders.GRAY,
//                createEnchantSettingsButton(pickaxe, ConsistencyEnchant.class, Material.EMERALD, true),
//                createEnchantSettingsButton(pickaxe, MerchantEnchant.class, Material.MAP, true),
//                createEnchantSettingsButton(pickaxe, AutoSellEnchant.class, Material.GOLD_NUGGET, true),
//                createEnchantSettingsButton(pickaxe, XPFinderEnchant.class, Material.EXPERIENCE_BOTTLE, true),
//                createEnchantSettingsButton(pickaxe, BackpackFinderEnchant.class, Material.CHEST_MINECART, true),
//                GUIPlaceholders.GRAY,
//                createEnchantSettingsButton(pickaxe, NightVisionEnchant.class, Material.LAPIS_LAZULI, true),
//                GUIPlaceholders.GRAY,
//                GUIPlaceholders.GRAY,
//                GUIPlaceholders.GRAY,
//                GUIPlaceholders.GRAY,
//                GUIPlaceholders.GRAY,
//                GUIPlaceholders.GRAY,
//                GUIPlaceholders.GRAY,
//                GUIPlaceholders.GRAY,
//                GUIPlaceholders.GRAY,
//                GUIPlaceholders.GRAY
//        );
//        c.open(player);
//        c.setOnCloseRun((p, t) -> mainMenu(p, pickaxe));
    }


    //---------- vvv Util methods vvv ----------
    static GUIButton createEnchantButton(PlayerData playerData, PrisonPickaxe pickaxe, Class<? extends Enchantment> enchantClass, Material icon) {
        PickaxeEnchant pickaxeEnchantment = (PickaxeEnchant) Enchantable.getEnchant(enchantClass);

        List<Component> desc = new ArrayList<>(pickaxeEnchantment.getDescription());
        desc.add(Component.empty());
        desc.add(Component.empty().decoration(TextDecoration.ITALIC, false)
                .append(Component.text("Current Level: ")
                        .color(ComponentUtil.AQUA))
                .append(Component.text(PrisonUtils.addCommasToNumber(pickaxe.getEnchantmentLevel(enchantClass)))
                        .color(ComponentUtil.WHITE))
        );
        desc.add(Component.empty().decoration(TextDecoration.ITALIC, false)
                .append(Component.text("Upgrade Cost: ")
                        .color(ComponentUtil.AQUA))
                .append(Component.text(PrisonUtils.addCommasToNumber(pickaxeEnchantment.getUpgradeCost()) + " Tokens")
                        .color(ComponentUtil.WHITE))
        );
        desc.add(Component.empty().decoration(TextDecoration.ITALIC, false)
                .append(Component.text("Your Tokens: ")
                        .color(ComponentUtil.AQUA))
                .append(Component.text(PrisonUtils.prettyNum(playerData.getTokens()))
                        .color(ComponentUtil.WHITE))
        );
        desc.add(Component.empty());
        desc.add(Component.empty().decoration(TextDecoration.ITALIC, false)
                .append(Component.text("Max Level: ")
                        .color(ComponentUtil.AQUA))
                .append(Component.text(PrisonUtils.addCommasToNumber(pickaxeEnchantment.getMaxLevel(pickaxe.getEnchantmentTier(enchantClass))))
                        .color(ComponentUtil.WHITE))
        );

        if (pickaxeEnchantment.getLevelRequirement() > pickaxe.getLevel()) {
            desc.add(Component.empty());
            desc.add(Component.empty().decoration(TextDecoration.ITALIC, false)
                    .append(Component.text("Minimum Pickaxe Level: ")
                            .color(ComponentUtil.RED))
                    .append(Component.text(pickaxeEnchantment.getLevelRequirement())
                            .color(ComponentUtil.WHITE))
            );

            return ButtonBuilder.builder()
                    .icon(icon)
                    .name(Component.text("[Locked] ")
                            .color(ComponentUtil.RED)
                            .decorate(TextDecoration.BOLD)
                            .decoration(TextDecoration.ITALIC, false)
                            .append(pickaxeEnchantment.getDisplayName()))
                    .lore(desc)
                    .onLeftClick(plr -> {
                        plr.sendMessage(pickaxeEnchantment.getDisplayName()
                                .append(Component.text(" >> ")
                                        .color(ComponentUtil.DARK_GRAY)
                                        .decorate(TextDecoration.BOLD))
                                .append(Component.text("Your pickaxe is not a high enough level to unlock this enchant!")
                                        .color(ComponentUtil.RED)
                                        .decoration(TextDecoration.BOLD, false))
                        );
                    })
                    .build();
        }

        return ButtonBuilder.builder()
                .icon(icon)
                .name(Component.text(pickaxe.getDisabledEnchantments().containsKey(enchantClass) ? "[Disabled]" : "")
                        .color(ComponentUtil.RED)
                        .decorate(TextDecoration.BOLD)
                        .decoration(TextDecoration.ITALIC, false)
                        .append(pickaxeEnchantment.getDisplayName()))
                .lore(desc)
                .onLeftClick(plr -> {
                    upgradeEnchant(plr, enchantClass, pickaxe);
                })
                .build();
    }

    static void upgradeEnchant(Player player, Class<? extends Enchantment> enchantClass, PrisonPickaxe pickaxe) {
        PickaxeEnchant pickaxeEnchantment = (PickaxeEnchant) Enchantable.getEnchant(enchantClass);
        StaticGUI gui = GUIBuilder.builder()
                .title(pickaxeEnchantment.getDisplayName())
                .size(9)
                .onClose((plr, g) -> mainMenu(plr, pickaxe))
                .build();

        PlayerData playerData = new PlayerData(player);


        gui.setButtons(
                GUIPlaceholders.GRAY,
                GUIPlaceholders.GRAY,
                makeUpgradeButton(enchantClass, playerData, pickaxe, 1),
                makeUpgradeButton(enchantClass, playerData, pickaxe, 10),
                makeUpgradeButton(enchantClass, playerData, pickaxe, 100),
                makeUpgradeButton(enchantClass, playerData, pickaxe, 1000),
                makeUpgradeButton(enchantClass, playerData, pickaxe, (int) Math.min(pickaxeEnchantment.getMaxLevel(pickaxe), playerData.getTokens() / pickaxeEnchantment.getUpgradeCost())),
                GUIPlaceholders.GRAY,
                GUIPlaceholders.GRAY
        );

        if (pickaxeEnchantment.getTier(pickaxe.getEnchantmentTier(enchantClass) + 1) != null) {
            gui.setButton(0, ButtonBuilder.builder()
                    .icon(Material.NETHER_STAR)
                    .name("&b&lIncrease Tier")
                    .lore(List.of(
                            Component.empty().decoration(TextDecoration.ITALIC, false)
                                    .append(Component.text("Increasing the tier of this enchantment"))
                                    .color(ComponentUtil.LIGHT_GRAY),
                            Component.empty().decoration(TextDecoration.ITALIC, false)
                                    .append(Component.text("will set its max level to " + PrisonUtils.addCommasToNumber(pickaxeEnchantment.getMaxLevel(pickaxe.getEnchantmentTier(enchantClass) + 1)) + ". This will"))
                                    .color(ComponentUtil.LIGHT_GRAY),
                            Component.empty().decoration(TextDecoration.ITALIC, false)
                                    .append(Component.text("cost " + PrisonUtils.addCommasToNumber(pickaxeEnchantment.getTier(pickaxe.getEnchantmentTier(enchantClass) + 1).prestigeTokensRequired()) + " Prestige Token(s), which can"))
                                    .color(ComponentUtil.LIGHT_GRAY),
                            Component.empty().decoration(TextDecoration.ITALIC, false)
                                    .append(Component.text("be obtained from prestiging."))
                                    .color(ComponentUtil.LIGHT_GRAY),
                            Component.empty(),
                            Component.empty().decoration(TextDecoration.ITALIC, false)
                                    .append(Component.text("Current Tier: "))
                                    .color(ComponentUtil.YELLOW)
                                    .append(Component.text(pickaxe.getEnchantmentTier(enchantClass))
                                            .color(ComponentUtil.WHITE)),
                            Component.empty().decoration(TextDecoration.ITALIC, false)
                                    .append(Component.text("Next Tier: "))
                                    .color(ComponentUtil.YELLOW)
                                    .append(Component.text(pickaxe.getEnchantmentTier(enchantClass) + 1)
                                            .color(ComponentUtil.WHITE)),
                            Component.empty().decoration(TextDecoration.ITALIC, false)
                                    .append(Component.text("Upgrade Cost: "))
                                    .color(ComponentUtil.YELLOW)
                                    .append(Component.text(PrisonUtils.addCommasToNumber(pickaxeEnchantment.getTier(pickaxe.getEnchantmentTier(enchantClass) + 1).prestigeTokensRequired()) + " Prestige Tokens")
                                            .color(ComponentUtil.WHITE)),
                            Component.empty().decoration(TextDecoration.ITALIC, false)
                                    .append(Component.text("Your Prestige Tokens: "))
                                    .color(ComponentUtil.YELLOW)
                                    .append(Component.text(PrisonUtils.prettyNum(playerData.getPrestigeTokens()))
                                            .color(ComponentUtil.WHITE))
                    ))
                    .onLeftClick(plr -> {
                        if (playerData.getPrestigeTokens() >= pickaxeEnchantment.getTier(pickaxe.getEnchantmentTier(enchantClass) + 1).prestigeTokensRequired()) {
                            playerData.removePrestigeTokens(pickaxeEnchantment.getTier(pickaxe.getEnchantmentTier(enchantClass) + 1).prestigeTokensRequired());
                            pickaxe.setEnchantmentTier(enchantClass, pickaxe.getEnchantmentTier(enchantClass) + 1);
                            plr.sendMessage(pickaxeEnchantment.getDisplayName()
                                    .append(Component.text(" >> ")
                                            .color(ComponentUtil.DARK_GRAY)
                                            .decorate(TextDecoration.BOLD))
                                    .append(Component.text("You upgraded your pickaxe!")
                                            .color(ComponentUtil.WHITE)
                                            .decoration(TextDecoration.BOLD, false))
                            );
                            upgradeEnchant(plr, enchantClass, pickaxe);
                        } else {
                            plr.sendMessage(pickaxeEnchantment.getDisplayName()
                                    .append(Component.text(" >> ")
                                            .color(ComponentUtil.DARK_GRAY)
                                            .decorate(TextDecoration.BOLD))
                                    .append(Component.text("You do not have enough Prestige Tokens to upgrade your pickaxe!")
                                            .color(ComponentUtil.RED)
                                            .decoration(TextDecoration.BOLD, false))
                            );
                        }
                    })
                    .build());
        }

        gui.open(player);
    }

    static GUIButton makeUpgradeButton(Class<? extends Enchantment> enchantClass, PlayerData playerData, PrisonPickaxe pickaxe, int levelsToUpgrade) {
        PickaxeEnchant pickaxeEnchantment = (PickaxeEnchant) Enchantable.getEnchant(enchantClass);

        return ButtonBuilder.builder()
                .icon(Material.LIME_STAINED_GLASS_PANE)
                .name(Component.text("Buy " + PrisonUtils.addCommasToNumber(levelsToUpgrade) + " level" + (levelsToUpgrade > 1 ? "s" : "") + " of ")
                        .color(ComponentUtil.GREEN)
                        .append(pickaxeEnchantment.getNameAsComponent()))
                .lore(List.of(
                        Component.empty().decoration(TextDecoration.ITALIC, false)
                                .append(Component.text("Current Level: ")
                                        .color(ComponentUtil.AQUA))
                                .append(Component.text(PrisonUtils.addCommasToNumber(pickaxe.getEnchantmentLevel(enchantClass)))
                                        .color(ComponentUtil.WHITE)),
                        Component.empty().decoration(TextDecoration.ITALIC, false)
                                .append(Component.text("Upgrade Cost: ")
                                        .color(ComponentUtil.AQUA))
                                .append(Component.text(PrisonUtils.addCommasToNumber(pickaxeEnchantment.getUpgradeCost() * levelsToUpgrade) + " Tokens")
                                        .color(ComponentUtil.WHITE)),
                        Component.empty().decoration(TextDecoration.ITALIC, false)
                                .append(Component.text("Your Tokens: ")
                                        .color(ComponentUtil.AQUA))
                                .append(Component.text(PrisonUtils.prettyNum(playerData.getTokens()))
                                        .color(ComponentUtil.WHITE)),
                        Component.empty(),
                        Component.empty().decoration(TextDecoration.ITALIC, false)
                                .append(Component.text("Max Level: ")
                                        .color(ComponentUtil.AQUA))
                                .append(Component.text(PrisonUtils.addCommasToNumber(pickaxeEnchantment.getMaxLevel(pickaxe.getEnchantmentTier(enchantClass))))
                                        .color(ComponentUtil.WHITE))
                ))

                .onLeftClick(plr -> {
                    if (pickaxe.upgrade(enchantClass, plr, levelsToUpgrade)) {
                        upgradeEnchant(plr, enchantClass, pickaxe);
                    }
                })
                .build();
    }

//    static ItemStack createEnchantSettingsButton(PrisonPickaxe pickaxe, Class<? extends Enchantment> enchant, GUICreator c, Material icon, boolean enchantIcon) {
//        ItemStack item = createEnchantSettingsButton(pickaxe, enchant, c, icon);
//        if (enchantIcon) ench(item);
//        return item;
//    }
//    static ItemStack createEnchantSettingsButton(PrisonPickaxe pickaxe, Class<? extends Enchantment> enchant, GUICreator c, Material icon) {
//        PickaxeEnchant pickaxeEnchantment = (PickaxeEnchant) Enchantable.getEnchant(enchant);
//        boolean isEnabled = pickaxe.getEnabledEnchantments().containsKey(enchant);
//        List<Component> desc = new ArrayList<>(pickaxeEnchantment.getDescription());
//        desc.add(Component.empty());
//        desc.add(LegacyComponentSerializer.legacyAmpersand().deserialize("&bLevel: &f" + PrisonUtils.addCommasToNumber(pickaxe.getEnchantmentLevel(enchant)) + " / " + PrisonUtils.addCommasToNumber(pickaxeEnchantment.getMaxLevel(pickaxe.getEnchantmentTier(enchant)))));
//        desc.add(LegacyComponentSerializer.legacyAmpersand().deserialize("&bCurrent State: &f" + (isEnabled ? "&aEnabled" : "&cDisabled")));
//        desc.add(Component.empty());
//        desc.add(LegacyComponentSerializer.legacyAmpersand().deserialize("&7&oClick to " + (isEnabled ? "disable" : "enable")));
//        ItemStack button = c.createButton(icon, pickaxeEnchantment.getNameAsComponent(), desc, (p, t) -> {
//            if (pickaxe.getEnabledEnchantments().containsKey(enchant)) {
//                pickaxe.disableEnchantment(enchant, p);
//            } else {
//                pickaxe.enableEnchantment(enchant, p);
//            }
//            openSettings(p, pickaxe);
//        });
//        if (isEnabled) button = ench(button);
//        return button;
//    }
//    static ItemStack createEnchantButton(PlayerData playerData, PrisonPickaxe pickaxe, Class<? extends Enchantment> enchant, GUICreator c, Material icon, boolean enchantIcon) {
//        ItemStack item = createEnchantButton(playerData, pickaxe, enchant, c, icon);
//        if (enchantIcon) {
//            return ench(item);
//        }
//        return item;
//    }
//
//    static void buildMenuContent(GUICreator c, Player player, Class<? extends Enchantment> enchant, PrisonPickaxe pickaxe) {
//        PickaxeEnchant pickaxeEnchantment = (PickaxeEnchant) Enchantable.getEnchant(enchant);
//        PlayerData playerData = new PlayerData(player);
//        c.setItems(
//                GUIPlaceholders.GRAY,
//                GUIPlaceholders.GRAY,
//                c.createButton(Material.LIME_STAINED_GLASS_PANE, "&aBuy 1 level of " + pickaxeEnchantment.getName(), List.of("",
//                        "&bCurrent Level: &f" + PrisonUtils.addCommasToNumber(pickaxe.getEnchantmentLevel(enchant)),
//                        "&bUpgrade Cost: &f" + PrisonUtils.addCommasToNumber(pickaxeEnchantment.getUpgradeCost()) + " Tokens",
//                        "&bYour Tokens: &f" + PrisonUtils.prettyNum(playerData.getTokens()) + " Tokens",
//                        "",
//                        "&bMax Level: &f" + PrisonUtils.addCommasToNumber(pickaxeEnchantment.getMaxLevel(pickaxe.getEnchantmentTier(enchant)))
//                ), (p, _t) -> {
//                    pickaxe.upgrade(enchant, player, 1);
//                    buildMenuContent(c, p, enchant, pickaxe);
//                }),
//                c.createButton(Material.LIME_STAINED_GLASS_PANE, "&aBuy 10 levels of " + pickaxeEnchantment.getName(), List.of("",
//                        "&bCurrent Level: &f" + PrisonUtils.addCommasToNumber(pickaxe.getEnchantmentLevel(enchant)),
//                        "&bUpgrade Cost: &f" + PrisonUtils.addCommasToNumber(pickaxeEnchantment.getUpgradeCost() * 10) + " Tokens",
//                        "&bYour Tokens: &f" + PrisonUtils.prettyNum(playerData.getTokens()) + " Tokens",
//                        "",
//                        "&bMax Level: &f" + PrisonUtils.addCommasToNumber(pickaxeEnchantment.getMaxLevel(pickaxe.getEnchantmentTier(enchant)))
//                ), (p, _t) -> {
//                    pickaxe.upgrade(enchant, player, 10);
//                    buildMenuContent(c, p, enchant, pickaxe);
//                }),
//                c.createButton(Material.LIME_STAINED_GLASS_PANE, "&aBuy 100 levels of " + pickaxeEnchantment.getName(), List.of("",
//                        "&bCurrent Level: &f" + PrisonUtils.addCommasToNumber(pickaxe.getEnchantmentLevel(enchant)),
//                        "&bUpgrade Cost: &f" + PrisonUtils.addCommasToNumber(pickaxeEnchantment.getUpgradeCost() * 100) + " Tokens",
//                        "&bYour Tokens: &f" + PrisonUtils.prettyNum(playerData.getTokens()) + " Tokens",
//                        "",
//                        "&bMax Level: &f" + PrisonUtils.addCommasToNumber(pickaxeEnchantment.getMaxLevel(pickaxe.getEnchantmentTier(enchant)))
//                ), (p, _t) -> {
//                    pickaxe.upgrade(enchant, player, 100);
//                    buildMenuContent(c, p, enchant, pickaxe);
//                }),
//                c.createButton(Material.LIME_STAINED_GLASS_PANE, "&aBuy 1,000 levels of " + pickaxeEnchantment.getName(), List.of("",
//                        "&bCurrent Level: &f" + PrisonUtils.addCommasToNumber(pickaxe.getEnchantmentLevel(enchant)),
//                        "&bUpgrade Cost: &f" + PrisonUtils.addCommasToNumber(pickaxeEnchantment.getUpgradeCost() * 1000) + " Tokens",
//                        "&bYour Tokens: &f" + PrisonUtils.prettyNum(playerData.getTokens()) + " Tokens",
//                        "",
//                        "&bMax Level: &f" + PrisonUtils.addCommasToNumber(pickaxeEnchantment.getMaxLevel(pickaxe.getEnchantmentTier(enchant)))
//                ), (p, _t) -> {
//                    pickaxe.upgrade(enchant, player, 1000);
//                    buildMenuContent(c, p, enchant, pickaxe);
//                }),
//                c.createButton(Material.LIME_STAINED_GLASS_PANE, "&aBuy MAX (" + Math.min((long) pickaxeEnchantment.getMaxLevel(pickaxe.getEnchantmentTier(enchant)) - pickaxe.getEnchantmentLevel(enchant), playerData.getTokens() / pickaxeEnchantment.getUpgradeCost()) + ") levels of " + pickaxeEnchantment.getName(), List.of("",
//                        "&bCurrent Level: &f" + PrisonUtils.addCommasToNumber(pickaxe.getEnchantmentLevel(enchant)),
//                        "&bUpgrade Cost: &f" + PrisonUtils.addCommasToNumber(pickaxeEnchantment.getUpgradeCost() * Math.min((long) pickaxeEnchantment.getMaxLevel(pickaxe.getEnchantmentTier(enchant)) - pickaxe.getEnchantmentLevel(enchant), playerData.getTokens() / pickaxeEnchantment.getUpgradeCost())) + " Tokens",
//                        "&bYour Tokens: &f" + PrisonUtils.prettyNum(playerData.getTokens()) + " Tokens",
//                        "",
//                        "&bMax Level: &f" + PrisonUtils.addCommasToNumber(pickaxeEnchantment.getMaxLevel(pickaxe.getEnchantmentTier(enchant)))
//                ), (p, _t) -> {
//                    pickaxe.upgrade(enchant, player, (int) Math.min(pickaxeEnchantment.getMaxLevel(pickaxe.getEnchantmentTier(enchant)) - pickaxe.getEnchantmentLevel(enchant), playerData.getTokens() / pickaxeEnchantment.getUpgradeCost()));
//                    buildMenuContent(c, p, enchant, pickaxe);
//                }),
//                GUIPlaceholders.GRAY,
//                GUIPlaceholders.GRAY
//        );
//
//        if (pickaxeEnchantment.getTier(pickaxe.getEnchantmentTier(enchant) + 1) != null) {
//            c.setItem(0, c.createButton(Material.NETHER_STAR, "&b&lIncrease Tier", List.of(
//                    "Increasing the tier of this enchantment",
//                    "will set its max level to " + PrisonUtils.addCommasToNumber(pickaxeEnchantment.getMaxLevel(pickaxe.getEnchantmentTier(enchant) + 1)) + ". This will",
//                    "cost " + PrisonUtils.addCommasToNumber(pickaxeEnchantment.getTier(pickaxe.getEnchantmentTier(enchant) + 1).prestigeTokensRequired()) + " Prestige Token(s), which can",
//                    "be obtained from prestiging.",
//                    "",
//                    "&eCurrent Tier: &f" + pickaxe.getEnchantmentTier(enchant),
//                    "&eNext Tier: &f" + (pickaxe.getEnchantmentTier(enchant) + 1),
//                    "&eUpgrade Cost: &f" + PrisonUtils.addCommasToNumber(pickaxeEnchantment.getTier(pickaxe.getEnchantmentTier(enchant) + 1).prestigeTokensRequired()) + " Prestige Tokens",
//                    "&eYour Prestige Tokens: &f" + PrisonUtils.prettyNum(playerData.getPrestigeTokens())
//            ), (p, t) -> {
//                if (playerData.getPrestigeTokens() >= pickaxeEnchantment.getTier(pickaxe.getEnchantmentTier(enchant) + 1).prestigeTokensRequired()) {
//                    playerData.removePrestigeTokens(pickaxeEnchantment.getTier(pickaxe.getEnchantmentTier(enchant) + 1).prestigeTokensRequired());
//                    pickaxe.setEnchantmentTier(enchant, pickaxe.getEnchantmentTier(enchant) + 1);
//                    p.sendMessage(org.bukkit.ChatColor.AQUA + "You successfully upgraded your pickaxe!");
//                    buildMenuContent(c, p, enchant, pickaxe);
//                } else {
//                    p.sendMessage(ChatColor.RED + "You do not have enough Prestige Tokens to upgrade this!");
//                }
//            }));
//        }
//
//    }
}