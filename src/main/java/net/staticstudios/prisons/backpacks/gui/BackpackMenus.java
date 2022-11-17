package net.staticstudios.prisons.backpacks.gui;


import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.staticstudios.gui.GUIButton;
import net.staticstudios.gui.GUIPlaceholders;
import net.staticstudios.gui.StaticGUI;
import net.staticstudios.gui.builder.ButtonBuilder;
import net.staticstudios.gui.builder.GUIBuilder;
import net.staticstudios.prisons.backpacks.Backpack;
import net.staticstudios.prisons.backpacks.BackpackManager;
import net.staticstudios.prisons.backpacks.config.BackpackConfig;
import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.gui.MainMenus;
import net.staticstudios.prisons.utils.ComponentUtil;
import net.staticstudios.prisons.utils.PlayerUtils;
import net.staticstudios.prisons.utils.Prefix;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

public class BackpackMenus {
    /**
     * The amount of tokens that are required to purchase an additional slot
     */
    private static final int SLOT_COST = 2;
    private static final Component EXPLAIN_COMBINE_BACKPACKS = Prefix.BACKPACKS.append(Component.text("Combine two backpacks of the same tier to upgrade one of them to have an increased tier AND max capacity. This will sacrifice one of the backpacks, only giving back one.").color(ComponentUtil.WHITE));
    private static final GUIButton RESULT_BACKPACK_PLACEHOLDER = ButtonBuilder.builder()
            .icon(Material.BARRIER)
            .name(Component.text("Result").color(ComponentUtil.RED).decorate(TextDecoration.BOLD).decoration(TextDecoration.ITALIC, false))
            .lore(List.of(
                    Component.empty(),
                    Component.text("Combine two backpacks of the same tier to").color(ComponentUtil.GRAY),
                    Component.text("get a new backpack with a higher tier!").color(ComponentUtil.GRAY),
                    Component.empty(),
                    Component.text("Put two backpacks in the empty slots to the right,").color(ComponentUtil.YELLOW).decoration(TextDecoration.ITALIC, false),
                    Component.text("then the upgraded backpack will appear in this slot.").color(ComponentUtil.YELLOW).decoration(TextDecoration.ITALIC, false)
            ))
            .onLeftClick(plr -> plr.sendMessage(EXPLAIN_COMBINE_BACKPACKS))
            .build();
    private static final BiConsumer<StaticGUI, InventoryClickEvent> COMBINE_BACKPACKS_CLICKED_EMPTY_SLOT = (gui, event) -> {
        Player plr = (Player) event.getWhoClicked();

        //Check both slots for backpacks
        ItemStack item1 = event.getInventory().getItem(10);
        ItemStack item2 = event.getInventory().getItem(12);

        if (item1 != null && item2 != null && (event.getCursor() == null || event.getCursor().getType() == Material.AIR)) { //There are 2 valid backpacks in the slots, don't do anything to remove them
            gui.setButton(15, RESULT_BACKPACK_PLACEHOLDER);
            return;
        }

        if (event.isShiftClick()) {
            if (event.getInventory().firstEmpty() == 10) {
                item1 = event.getCurrentItem();
            } else if (event.getInventory().firstEmpty() == 12) {
                item2 = event.getCurrentItem();
            }
        } else {
            if (event.getSlot() == 10) {
                item1 = event.getCursor();
            } else if (event.getSlot() == 12) {
                item2 = event.getCursor();
            }
        }

        if (item1 == null || item2 == null) return;

        Backpack backpack1 = Backpack.fromItem(item1);
        Backpack backpack2 = Backpack.fromItem(item2);

        if (backpack1 == null || backpack2 == null) { //There are 2 items in the slots, but one, or both of them, are not backpacks
            if (event.getInventory().getItem(10) != null) {
                PlayerUtils.addToInventory(plr, event.getInventory().getItem(10));
                gui.removeButton(10);
            }

            if (event.getInventory().getItem(12) != null) {
                PlayerUtils.addToInventory(plr, event.getInventory().getItem(12));
                gui.removeButton(12);
            }
            event.setCancelled(true);
            gui.setButton(15, RESULT_BACKPACK_PLACEHOLDER);
            plr.sendMessage(EXPLAIN_COMBINE_BACKPACKS);
            return;
        }

        if (backpack1.getTier() != backpack2.getTier()) { //The backpacks are not the same tier
            plr.sendMessage(Prefix.BACKPACKS.append(Component.text("Both backpacks must be the same tier to combine them!").color(ComponentUtil.RED)));

            if (event.isShiftClick()) {
                if (event.getInventory().firstEmpty() == 10) {
                    PlayerUtils.addToInventory(plr, item2);
                    gui.removeButton(12);
                } else if (event.getInventory().firstEmpty() == 12) {
                    PlayerUtils.addToInventory(plr, item1);
                    gui.removeButton(10);
                }
            } else {
                if (event.getSlot() == 12) {
                    PlayerUtils.addToInventory(plr, item1);
                    gui.removeButton(10);
                }

                if (event.getSlot() == 10) {
                    PlayerUtils.addToInventory(plr, item2);
                    gui.removeButton(12);
                }
            }
            event.setCancelled(true);
            gui.setButton(15, RESULT_BACKPACK_PLACEHOLDER);
            return;
        }

        if (BackpackConfig.tier(backpack1.getTier()).maxSize() != backpack1.getCapacity() || BackpackConfig.tier(backpack2.getTier()).maxSize() != backpack2.getCapacity()) { //The backpacks' capacities are not maxed
            plr.sendMessage(Prefix.BACKPACKS.append(Component.text("Both of the backpacks must have a maxed-out capacity before you can combine them!").color(ComponentUtil.RED)));

            if (event.isShiftClick()) {
                if (event.getInventory().firstEmpty() == 10) {
                    PlayerUtils.addToInventory(plr, item2);
                    gui.removeButton(12);
                } else if (event.getInventory().firstEmpty() == 12) {
                    PlayerUtils.addToInventory(plr, item1);
                    gui.removeButton(10);
                }
            } else {
                if (event.getSlot() == 12) {
                    PlayerUtils.addToInventory(plr, item1);
                    gui.removeButton(10);
                }

                if (event.getSlot() == 10) {
                    PlayerUtils.addToInventory(plr, item2);
                    gui.removeButton(12);
                }
            }
            event.setCancelled(true);
            gui.setButton(15, RESULT_BACKPACK_PLACEHOLDER);
            return;
        }

        //The backpacks are valid to combine, put the result button in
        Backpack result = BackpackManager.createBackpack(backpack1.getTier() + 1);
        result.setCapacity(backpack1.getCapacity());
        result.updateItemNow();

        assert result.getItem() != null;

        gui.setButton(15, ButtonBuilder.builder()
                .fromItem(result.getItem())
                .onLeftClick(p -> {
                    PlayerUtils.addToInventory(p, result.getItem());
                    BackpackManager.sellBackpack(p, backpack1, false);
                    BackpackManager.sellBackpack(p, backpack2, false);
                    gui.removeButton(10);
                    gui.removeButton(12);
                    gui.setButton(15, RESULT_BACKPACK_PLACEHOLDER);
                })
                .build()
        );
    };

    /**
     * The main entry point for the backpack menus.
     *
     * @param player      The player to open the backpack menu for.
     * @param fromCommand Whether the menu was opened from a command, if true, then the main menu will not be opened when the player closes the backpack menu.
     */
    public static void open(Player player, boolean fromCommand) {

        StaticGUI gui = GUIBuilder.builder()
                .title(Component.text("Backpacks"))
                .size(27)
                .fillWith(GUIPlaceholders.GRAY)
                .onClose((plr, g) -> {
                    if (!fromCommand) {
                        MainMenus.open(plr);
                    }
                })
                .build();
        gui.setButton(11, ButtonBuilder.builder()
                .name(Component.text("Upgrade A Backpack").color(ComponentUtil.GREEN).decorate(TextDecoration.BOLD).decoration(TextDecoration.ITALIC, false))
                .lore(List.of(
                        Component.empty(),
                        Component.text("Increase a backpack's capacity").color(ComponentUtil.GRAY),
                        Component.empty(),
                        Component.text("Left-click to upgrade a backpack").color(ComponentUtil.YELLOW).decoration(TextDecoration.ITALIC, false)
                ))
                .icon(Material.ENDER_CHEST)
                .onLeftClick(plr -> selectBackpack(plr, fromCommand))
                .build());

        gui.setButton(13, ButtonBuilder.builder()
                .name(Component.text("Combine Backpacks").color(ComponentUtil.LIGHT_PURPLE).decorate(TextDecoration.BOLD).decoration(TextDecoration.ITALIC, false))
                .lore(List.of(
                        Component.empty(),
                        Component.text("Combine two backpacks of the same tier to upgrade one").color(ComponentUtil.GRAY),
                        Component.text("of them to have an increased tier AND max capacity. This").color(ComponentUtil.GRAY),
                        Component.text("will sacrifice one of the backpacks, only giving back one.").color(ComponentUtil.GRAY),
                        Component.empty(),
                        Component.text("Left-click to select two backpacks to combine").color(ComponentUtil.YELLOW).decoration(TextDecoration.ITALIC, false),
                        Component.text("Right-click to combine auto-combine any backpacks in your inventory").color(ComponentUtil.YELLOW).decoration(TextDecoration.ITALIC, false)
                ))
                .icon(Material.ANVIL)
                .onLeftClick(plr -> combine(plr, fromCommand))
                .onRightClick(plr -> {
                    List<Backpack> newBackpacks = new ArrayList<>();

                    Map<Integer, List<Backpack>> backpacksByTier = new HashMap<>();
                    for (Backpack backpack : BackpackManager.getPlayerBackpacks(plr)) {
                        if (backpack.getCapacity() >= BackpackConfig.tier(backpack.getTier()).maxSize()) { //It is maxed out and can be added to the list
                            List<Backpack> backpackTier = backpacksByTier.getOrDefault(backpack.getTier(), new ArrayList<>());
                            backpackTier.add(backpack);
                            backpacksByTier.put(backpack.getTier(), backpackTier);
                        }
                    }
                    for (Map.Entry<Integer, List<Backpack>> entry : backpacksByTier.entrySet()) {
                        while (entry.getValue().size() >= 2) {
                            Backpack first = entry.getValue().get(0);
                            Backpack second = entry.getValue().get(1);
                            Backpack newBackpack = BackpackManager.createBackpack(entry.getKey() + 1);
                            newBackpack.setCapacity(first.getCapacity());

                            newBackpacks.add(newBackpack);

                            assert first.getItem() != null;
                            assert second.getItem() != null;

                            first.getItem().setAmount(0);
                            second.getItem().setAmount(0);

                            BackpackManager.sellBackpack(plr, first, false);
                            BackpackManager.sellBackpack(plr, second, false);

                            entry.getValue().remove(first);
                            entry.getValue().remove(second);
                        }
                    }

                    if (newBackpacks.size() > 0) {
                        for (Backpack backpack : newBackpacks) {
                            backpack.updateItemNow();
                            PlayerUtils.addToInventory(plr, backpack.getItem());
                        }
                        plr.sendMessage(Prefix.BACKPACKS.append(Component.text("You combined " + (newBackpacks.size() * 2) + " backpacks!").color(ComponentUtil.GREEN)));
                        plr.closeInventory();
                    } else {
                        plr.sendMessage(Prefix.BACKPACKS.append(Component.text("You did not have any backpacks to combine!").color(ComponentUtil.RED)));
                        plr.closeInventory();
                    }

                })
                .build());

        gui.setButton(15, ButtonBuilder.builder()
                .name(Component.text("Create A Backpack").color(ComponentUtil.GOLD).decorate(TextDecoration.BOLD).decoration(TextDecoration.ITALIC, false))
                .lore(List.of(
                        Component.empty(),
                        Component.text("Need a new backpack?").color(ComponentUtil.GRAY),
                        Component.empty(),
                        Component.text("Left-click to get an empty backpack").color(ComponentUtil.YELLOW).decoration(TextDecoration.ITALIC, false)
                ))
                .icon(Material.CHEST)
                .onLeftClick(plr -> {
                    plr.sendMessage(Prefix.BACKPACKS.append(Component.text("You created a new backpack!")));
                    Backpack backpack = BackpackManager.createBackpack(1);

                    assert backpack.getItem() != null;

                    plr.getInventory().addItem(backpack.getItem());
                    upgradeBackpack(plr, backpack, fromCommand);
                })
                .build());

        gui.open(player);
    }

    /**
     * Entry point for a player to upgrade a backpack. This menu is used to select which backpack (from the player's inventory) to upgrade.
     *
     * @param player      The player to open the backpack menu for.
     * @param fromCommand Whether the menu was opened from a command, if true, then the main menu will not be opened when the player closes the backpack menu.
     */
    public static void selectBackpack(Player player, boolean fromCommand) {
        List<Backpack> backpacks = BackpackManager.getPlayerBackpacks(player);
        StaticGUI gui = GUIBuilder.builder()
                .title(Component.text("Select A Backpack To Upgrade"))
                .size(backpacks.size() / 9 == 0 ? 9 : (backpacks.size() / 9 + 1) * 9)
                .onClose((plr, g) -> open(plr, fromCommand))
                .build();

        for (int i = 0; i < backpacks.size(); i++) {
            Backpack backpack = backpacks.get(i);

            assert backpack.getItem() != null;

            gui.setButton(i, ButtonBuilder.builder()
                    .fromItem(backpack.getItem())
                    .onLeftClick(plr -> upgradeBackpack(plr, backpack, fromCommand))
                    .build()
            );
        }

        gui.open(player);
    }

    /**
     * Menu to upgrade a backpack's capacity.
     *
     * @param player      The player to open the backpack menu for.
     * @param backpack    The backpack to upgrade.
     * @param fromCommand Whether the menu was opened from a command, if true, then the main menu will not be opened when the player closes the backpack menu.
     */
    public static void upgradeBackpack(Player player, Backpack backpack, boolean fromCommand) {
        //Check if the backpack can be upgraded, if not, send them to the combine menu
        if (backpack.getCapacity() >= BackpackConfig.tier(backpack.getTier()).maxSize()) {
            player.sendMessage(Prefix.BACKPACKS.append(Component.text("This backpack has reached its max size! Combine it with another backpack to upgrade it further.")));
            combine(player, fromCommand);
            return;
        }

        StaticGUI gui = GUIBuilder.builder()
                .title(Component.text("Upgrade Your Backpack"))
                .size(9)
                .onClose((plr, g) -> open(plr, fromCommand))
                .build();

        long maxAffordable = new PlayerData(player).getTokens() / SLOT_COST;

        gui.setButtons(
                upgradeSize(5, 1, backpack, fromCommand).build(),
                upgradeSize(50, 2, backpack, fromCommand).build(),
                upgradeSize(500, 3, backpack, fromCommand).build(),
                upgradeSize(5_000, 4, backpack, fromCommand).build(),
                upgradeSize(50_000, 5, backpack, fromCommand).build(),
                upgradeSize(500_000, 6, backpack, fromCommand).build(),
                upgradeSize(5_000_000, 7, backpack, fromCommand).build(),
                upgradeSize(50_000_000, 8, backpack, fromCommand).build(),
                upgradeSize(maxAffordable, 64, backpack, fromCommand) //The max amount of slots that can be bought with the player's current token balance
                        .name(Component.text("+" + PrisonUtils.addCommasToNumber(maxAffordable) + " Capacity (Max you can afford)").decorate(TextDecoration.BOLD).color(ComponentUtil.GREEN).decoration(TextDecoration.ITALIC, false))
                        .build()
        );

        gui.open(player);
    }

    /**
     * Menu for a player to combine backpacks.
     *
     * @param player      The player to open the backpack menu for.
     * @param fromCommand Whether the menu was opened from a command, if true, then the main menu will not be opened when the player closes the backpack menu.
     */
    public static void combine(Player player, boolean fromCommand) {

        StaticGUI gui = GUIBuilder.builder()
                .title(Component.text("Combine Backpacks"))
                .size(27)
                .fillWith(GUIPlaceholders.GRAY)
                .onClose((plr, g) -> open(plr, fromCommand))
                .build();
        gui.removeButton(10, 12); //Set those slots to air, so the player can put backpacks in them

        gui.setButton(15, RESULT_BACKPACK_PLACEHOLDER);

        gui.getSettings().allowPlayerItems(true);


        gui.listen(10, COMBINE_BACKPACKS_CLICKED_EMPTY_SLOT);
        gui.listen(12, COMBINE_BACKPACKS_CLICKED_EMPTY_SLOT);


        gui.open(player);
    }


    /**
     * Utility method for quickly creating buttons for the upgradeBackpack menu.
     *
     * @param amount      The amount of slots to buy.
     * @param stackCount  The amount of the ItemStack.
     * @param backpack    The backpack to upgrade.
     * @param fromCommand Whether the menu was opened from a command, if true, then the main menu will not be opened when the player closes the backpack menu.
     */
    static ButtonBuilder upgradeSize(long amount, int stackCount, Backpack backpack, boolean fromCommand) {

        assert backpack.getItem() != null;

        return ButtonBuilder.builder()
                .fromItem(backpack.getItem()) //We just want the item material/skull meta to be the same
                .name(Component.text("+" + PrisonUtils.addCommasToNumber(amount) + " Capacity").decorate(TextDecoration.BOLD).color(ComponentUtil.GREEN).decoration(TextDecoration.ITALIC, false))
                .lore(List.of(
                        Component.empty(),
                        Component.text("Costs: ").color(ComponentUtil.GREEN).append(Component.text(PrisonUtils.addCommasToNumber(SLOT_COST * amount) + " Tokens").color(ComponentUtil.WHITE)).decoration(TextDecoration.ITALIC, false),
                        Component.text("Current Capacity: ").color(ComponentUtil.GREEN).append(Component.text(PrisonUtils.addCommasToNumber(backpack.getCapacity())).color(ComponentUtil.WHITE)).decoration(TextDecoration.ITALIC, false),
                        Component.text("Max Capacity (For Tier " + backpack.getTier() + "): ").color(ComponentUtil.GREEN).append(Component.text(PrisonUtils.addCommasToNumber(BackpackConfig.tier(backpack.getTier()).maxSize())).color(ComponentUtil.WHITE)).decoration(TextDecoration.ITALIC, false),
                        Component.empty(),
                        Component.text("Left-click to upgrade").color(ComponentUtil.YELLOW).decoration(TextDecoration.ITALIC, false)
                ))
                .count(stackCount)
                .onLeftClick(plr -> {
                    PlayerData playerData = new PlayerData(plr);
                    long slotsToBuy = Math.min(amount, BackpackConfig.tier(backpack.getTier()).maxSize() - backpack.getCapacity());
                    if (playerData.getTokens() < SLOT_COST * amount) {
                        plr.sendMessage(Prefix.BACKPACKS.append(Component.text("You don't have enough tokens to purchase this!").color(ComponentUtil.RED)));
                        return;
                    }
                    playerData.removeTokens(SLOT_COST * slotsToBuy);
                    backpack.setCapacity(backpack.getCapacity() + slotsToBuy);
                    plr.sendMessage(Prefix.BACKPACKS.append(Component.text("You increased your backpack's max capacity! "))
                            .append(Component.text(PrisonUtils.addCommasToNumber(backpack.getCapacity() - slotsToBuy) + " -> " + PrisonUtils.addCommasToNumber(backpack.getCapacity())).color(ComponentUtil.GREEN)));
                    upgradeBackpack(plr, backpack, fromCommand);
                });
    }
}