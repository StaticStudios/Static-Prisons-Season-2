package net.staticstudios.prisons.pickaxe.enchants;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.staticstudios.mines.utils.WeightedElements;
import net.staticstudios.prisons.blockbreak.BlockBreakProcessEvent;
import net.staticstudios.prisons.customitems.CustomItems;
import net.staticstudios.prisons.pickaxe.enchants.handler.PickaxeEnchant;
import net.staticstudios.prisons.utils.ComponentUtil;
import net.staticstudios.prisons.utils.PlayerUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class MetalDetectorEnchant extends PickaxeEnchant {

    private static WeightedElements<Item> ITEMS;

    public MetalDetectorEnchant() {
        super(MetalDetectorEnchant.class, "pickaxe-metaldetector");

        ITEMS = new WeightedElements<>();

        ConfigurationSection items = getConfig().getConfigurationSection("items");
        if (items == null) {
            throw new IllegalStateException("No items section found in the " + getId() + " enchant config!");
        }
        for (String key : items.getKeys(false)) {
            key = key.replace("+", "");
            ITEMS.add(new Item(key, items.getInt(key + ".amount", 1)), items.getDouble(key + ".chance"));
        }
    }

    @Override
    public void onEvent(BlockBreakProcessEvent event) {
        Item item = ITEMS.getRandom();
        ItemStack itemStack = item.getItem(event.getPlayer());

        PlayerUtils.addToInventory(event.getPlayer(), itemStack);

        event.getPlayer().sendMessage(getDisplayName()
                .append(Component.text(" >> ")
                        .color(ComponentUtil.DARK_GRAY)
                        .decorate(TextDecoration.BOLD))
                .append(Component.text("Found " + item.amount + "x "))
                .append(Objects.requireNonNull(itemStack.getItemMeta().displayName()).append(Component.text("!"))));
    }

    record Item(String itemID, int amount) {

        ItemStack getItem(Player player) {
            ItemStack item = CustomItems.getItem(itemID, player);

            if (item == null) {
                throw new IllegalStateException("[Metal Detector] No item found with the id " + itemID + "!");
            }

            item.setAmount(amount);
            return item;
        }

    }
}