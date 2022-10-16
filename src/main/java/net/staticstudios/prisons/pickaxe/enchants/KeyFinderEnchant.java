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
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class KeyFinderEnchant extends PickaxeEnchant {

    private static WeightedElements<CrateKey> CRATE_KEYS;

    public KeyFinderEnchant() {
        super(KeyFinderEnchant.class, "pickaxe-backpackfinder");

        CRATE_KEYS = new WeightedElements<>();

        ConfigurationSection crateKeys = getConfig().getConfigurationSection("create_keys");
        if (crateKeys == null) {
            throw new IllegalStateException("No crate_keys section found in the " + getId() + " enchant config!");
        }
        for (String key : crateKeys.getKeys(false)) {
            key = key.replace("+", "");
            CRATE_KEYS.add(new CrateKey(key, crateKeys.getInt(key + ".amount", 1)), crateKeys.getDouble(key + ".chance"));
        }
    }

    @Override
    public void onEvent(BlockBreakProcessEvent event) {
        CrateKey crateKey = CRATE_KEYS.getRandom();
        ItemStack item = crateKey.getCrateKey();

        PlayerUtils.addToInventory(event.getPlayer(), item);

        event.getPlayer().sendMessage(getDisplayName()
                .append(Component.text(" >> ")
                        .color(ComponentUtil.DARK_GRAY)
                        .decorate(TextDecoration.BOLD))
                .append(Component.text("Found " + crateKey.amount + "x "))
                .append(Objects.requireNonNull(item.getItemMeta().displayName()).append(Component.text("!"))));
    }

    record CrateKey(String key, int amount) {

        ItemStack getCrateKey() {
            ItemStack item = CustomItems.getItem(key, null);

            if (item == null) {
                throw new IllegalStateException("[Key Finder] No crate itemID found with the id " + key + "!");
            }

            item.setAmount(amount);
            return item;
        }

    }
}