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
                MenuUtils.createEnchantButton(playerData, pickaxe, FortuneEnchant.class, Material.DIAMOND),
                MenuUtils.createEnchantButton(playerData, pickaxe, OreSplitterEnchant.class, Material.AMETHYST_SHARD),
                MenuUtils.createEnchantButton(playerData, pickaxe, TokenatorEnchant.class, Material.SUNFLOWER),
                MenuUtils.createEnchantButton(playerData, pickaxe, KeyFinderEnchant.class, Material.TRIPWIRE_HOOK),
                MenuUtils.createEnchantButton(playerData, pickaxe, MetalDetectorEnchant.class, Material.FLINT_AND_STEEL),
                GUIPlaceholders.GRAY,
                MenuUtils.createEnchantButton(playerData, pickaxe, HasteEnchant.class, Material.GOLDEN_PICKAXE),
                GUIPlaceholders.GRAY,
                GUIPlaceholders.GRAY,
                MenuUtils.createEnchantButton(playerData, pickaxe, ExplosionEnchant.class, Material.TNT),
                MenuUtils.createEnchantButton(playerData, pickaxe, JackHammerEnchant.class, Material.ANVIL),
                MenuUtils.createEnchantButton(playerData, pickaxe, DoubleWammyEnchant.class, Material.HOPPER),
                MenuUtils.createEnchantButton(playerData, pickaxe, MultiDirectionalEnchant.class, Material.COMPARATOR),
                MenuUtils.createEnchantButton(playerData, pickaxe, EggShooterEnchant.class, Material.EGG),
                GUIPlaceholders.GRAY,
                MenuUtils.createEnchantButton(playerData, pickaxe, SpeedEnchant.class, Material.FEATHER),
                GUIPlaceholders.GRAY,
                GUIPlaceholders.GRAY,
                MenuUtils.createEnchantButton(playerData, pickaxe, ConsistencyEnchant.class, Material.EMERALD),
                MenuUtils.createEnchantButton(playerData, pickaxe, MerchantEnchant.class, Material.MAP),
                MenuUtils.createEnchantButton(playerData, pickaxe, AutoSellEnchant.class, Material.GOLD_NUGGET),
                MenuUtils.createEnchantButton(playerData, pickaxe, XPFinderEnchant.class, Material.EXPERIENCE_BOTTLE),
                MenuUtils.createEnchantButton(playerData, pickaxe, BackpackFinderEnchant.class, Material.CHEST_MINECART),
                GUIPlaceholders.GRAY,
                MenuUtils.createEnchantButton(playerData, pickaxe, NightVisionEnchant.class, Material.LAPIS_LAZULI),
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
        PlayerData playerData = new PlayerData(player);
        StaticGUI gui = GUIBuilder.builder()
                .title("Enchantment Settings")
                .size(45)
                .onClose((plr, g) -> mainMenu(plr, pickaxe))
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
                MenuUtils.makeSettingsButton(playerData, pickaxe, FortuneEnchant.class, Material.DIAMOND),
                MenuUtils.makeSettingsButton(playerData, pickaxe, OreSplitterEnchant.class, Material.AMETHYST_SHARD),
                MenuUtils.makeSettingsButton(playerData, pickaxe, TokenatorEnchant.class, Material.SUNFLOWER),
                MenuUtils.makeSettingsButton(playerData, pickaxe, KeyFinderEnchant.class, Material.TRIPWIRE_HOOK),
                MenuUtils.makeSettingsButton(playerData, pickaxe, MetalDetectorEnchant.class, Material.FLINT_AND_STEEL),
                GUIPlaceholders.GRAY,
                MenuUtils.makeSettingsButton(playerData, pickaxe, HasteEnchant.class, Material.GOLDEN_PICKAXE),
                GUIPlaceholders.GRAY,
                GUIPlaceholders.GRAY,
                MenuUtils.makeSettingsButton(playerData, pickaxe, ExplosionEnchant.class, Material.TNT),
                MenuUtils.makeSettingsButton(playerData, pickaxe, JackHammerEnchant.class, Material.ANVIL),
                MenuUtils.makeSettingsButton(playerData, pickaxe, DoubleWammyEnchant.class, Material.HOPPER),
                MenuUtils.makeSettingsButton(playerData, pickaxe, MultiDirectionalEnchant.class, Material.COMPARATOR),
                MenuUtils.makeSettingsButton(playerData, pickaxe, EggShooterEnchant.class, Material.EGG),
                GUIPlaceholders.GRAY,
                MenuUtils.makeSettingsButton(playerData, pickaxe, SpeedEnchant.class, Material.FEATHER),
                GUIPlaceholders.GRAY,
                GUIPlaceholders.GRAY,
                MenuUtils.makeSettingsButton(playerData, pickaxe, ConsistencyEnchant.class, Material.EMERALD),
                MenuUtils.makeSettingsButton(playerData, pickaxe, MerchantEnchant.class, Material.MAP),
                MenuUtils.makeSettingsButton(playerData, pickaxe, AutoSellEnchant.class, Material.GOLD_NUGGET),
                MenuUtils.makeSettingsButton(playerData, pickaxe, XPFinderEnchant.class, Material.EXPERIENCE_BOTTLE),
                MenuUtils.makeSettingsButton(playerData, pickaxe, BackpackFinderEnchant.class, Material.CHEST_MINECART),
                GUIPlaceholders.GRAY,
                MenuUtils.makeSettingsButton(playerData, pickaxe, NightVisionEnchant.class, Material.LAPIS_LAZULI),
                GUIPlaceholders.GRAY,
                GUIPlaceholders.GRAY,
                GUIPlaceholders.GRAY,
                GUIPlaceholders.GRAY,
                GUIPlaceholders.GRAY,
                GUIPlaceholders.GRAY,
                GUIPlaceholders.GRAY,
                GUIPlaceholders.GRAY,
                GUIPlaceholders.GRAY,
                GUIPlaceholders.GRAY
        );

        gui.open(player);
    }




    static void upgradeEnchant(Player player, Class<? extends Enchantment> enchantClass, PrisonPickaxe pickaxe) {
        PickaxeEnchant pickaxeEnchantment = (PickaxeEnchant) Enchantable.getEnchant(enchantClass);
        PlayerData playerData = new PlayerData(player);

        StaticGUI gui = GUIBuilder.builder()
                .title(pickaxeEnchantment.getDisplayName())
                .size(9)
                .onClose((plr, g) -> mainMenu(plr, pickaxe))
                .build();
        gui.setButtons(MenuUtils.makeUpgradeMenuButtons(enchantClass, pickaxeEnchantment, playerData, pickaxe, gui));
        gui.open(player);
    }



    private static class MenuUtils {

        static GUIButton makeSettingsButton(PlayerData playerData, PrisonPickaxe pickaxe, Class<? extends Enchantment> enchantClass, Material icon) {
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


            boolean disabled = pickaxe.isDisabled(enchantClass);
            boolean locked = pickaxeEnchantment.getLevelRequirement() > pickaxe.getLevel();

            if (locked) {
                desc.add(Component.empty());
                desc.add(Component.empty().decoration(TextDecoration.ITALIC, false)
                        .append(Component.text("Minimum Pickaxe Level: ")
                                .color(ComponentUtil.RED))
                        .append(Component.text(pickaxeEnchantment.getLevelRequirement())
                                .color(ComponentUtil.WHITE))
                );
            }

            desc.add(Component.empty());
            if (disabled) {
                desc.add(Component.empty().decoration(TextDecoration.ITALIC, true)
                        .append(Component.text("Click to enable this enchant")
                                .color(ComponentUtil.YELLOW)));
            } else {
                desc.add(Component.empty().decoration(TextDecoration.ITALIC, true)
                        .append(Component.text("Click to disable this enchant")
                                .color(ComponentUtil.YELLOW)));
            }

            if (locked) {
                return ButtonBuilder.builder()
                        .icon(icon)
                        .name(Component.text("[Locked] ")
                                .color(ComponentUtil.RED)
                                .decorate(TextDecoration.BOLD)
                                .decoration(TextDecoration.ITALIC, false)
                                .append(pickaxeEnchantment.getDisplayName()))
                        .lore(desc)
                        .enchanted(!disabled)
                        .onLeftClick(plr -> enchantDisabled(disabled, plr, pickaxe, enchantClass))
                        .build();
            }


            return ButtonBuilder.builder()
                    .icon(icon)
                    .name(Component.text(disabled ? "[Disabled] " : "")
                            .color(ComponentUtil.RED)
                            .decorate(TextDecoration.BOLD)
                            .decoration(TextDecoration.ITALIC, false)
                            .append(pickaxeEnchantment.getDisplayName()))
                    .lore(desc)
                    .enchanted(!disabled)
                    .onLeftClick(plr -> enchantDisabled(disabled, plr, pickaxe, enchantClass))
                    .build();
        }
        static void enchantDisabled(boolean disabled, Player player, PrisonPickaxe pickaxe, Class<? extends Enchantment> enchantClass) {
            if (disabled) {
                pickaxe.enableEnchantment(enchantClass, player);
                player.sendMessage(Enchantable.getEnchant(enchantClass).getDisplayName()
                        .append(Component.text(" >> ")
                                .color(ComponentUtil.DARK_GRAY)
                                .decorate(TextDecoration.BOLD))
                        .append(Component.text("Enchant enabled!")
                                .color(ComponentUtil.GREEN)
                                .decoration(TextDecoration.BOLD, false))
                );
            } else {
                pickaxe.disableEnchantment(enchantClass, player);
                player.sendMessage(Enchantable.getEnchant(enchantClass).getDisplayName()
                        .append(Component.text(" >> ")
                                .color(ComponentUtil.DARK_GRAY)
                                .decorate(TextDecoration.BOLD))
                        .append(Component.text("Enchant disabled!")
                                .color(ComponentUtil.RED)
                                .decoration(TextDecoration.BOLD, false))
                );
            }
            openSettings(player, pickaxe);
        }


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
                    .name(Component.text(pickaxe.isDisabled(enchantClass) ? "[Disabled] " : "")
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

        static GUIButton[] makeUpgradeMenuButtons(Class<? extends Enchantment> enchantClass, PickaxeEnchant pickaxeEnchantment, PlayerData playerData, PrisonPickaxe pickaxe, StaticGUI gui) {
            GUIButton[] buttons = new GUIButton[]{
                    GUIPlaceholders.GRAY,
                    GUIPlaceholders.GRAY,
                    MenuUtils.makeUpgradeButton(enchantClass, playerData, pickaxe, 1, gui),
                    MenuUtils.makeUpgradeButton(enchantClass, playerData, pickaxe, 10, gui),
                    MenuUtils.makeUpgradeButton(enchantClass, playerData, pickaxe, 100, gui),
                    MenuUtils.makeUpgradeButton(enchantClass, playerData, pickaxe, 1000, gui),
                    MenuUtils.makeUpgradeButton(enchantClass, playerData, pickaxe, (int) Math.min(pickaxeEnchantment.getMaxLevel(pickaxe), playerData.getTokens() / pickaxeEnchantment.getUpgradeCost()), gui),
                    GUIPlaceholders.GRAY,
                    GUIPlaceholders.GRAY
            };

            if (pickaxeEnchantment.getTier(pickaxe.getEnchantmentTier(enchantClass) + 1) == null) return buttons;
            buttons[0] = ButtonBuilder.builder()
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
                            gui.setButtons(MenuUtils.makeUpgradeMenuButtons(enchantClass, pickaxeEnchantment, playerData, pickaxe, gui));
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
                    .build();

            return buttons;
        }

        static GUIButton makeUpgradeButton(Class<? extends Enchantment> enchantClass, PlayerData playerData, PrisonPickaxe pickaxe, int levelsToUpgrade, StaticGUI gui) {
            PickaxeEnchant pickaxeEnchantment = (PickaxeEnchant) Enchantable.getEnchant(enchantClass);

            return ButtonBuilder.builder()
                    .icon(Material.LIME_STAINED_GLASS_PANE)
                    .name(Component.text("Buy " + PrisonUtils.addCommasToNumber(levelsToUpgrade) + " level" + (levelsToUpgrade == 1 ? "s" : "") + " of ")
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
                            gui.setButtons(MenuUtils.makeUpgradeMenuButtons(enchantClass, pickaxeEnchantment, playerData, pickaxe, gui));
                        }
                    })
                    .build();
        }
    }
}