package net.staticstudios.prisons.customitems.items;

import net.staticstudios.prisons.customitems.CustomItem;
import net.staticstudios.prisons.lootboxes.LootBoxType;
import net.staticstudios.prisons.lootboxes.lootboxes.MoneyLootBox;
import net.staticstudios.prisons.lootboxes.lootboxes.PickaxeLootBox;
import net.staticstudios.prisons.lootboxes.lootboxes.TokenLootBox;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.InvocationTargetException;

public class LootBoxCustomItem implements CustomItem {

    private final int tier;
    private final String id;
    private final LootBoxType type;

    public LootBoxCustomItem(int tier, LootBoxType type) {
        this.tier = tier;
        this.type = type;
        this.id = type.toString().toLowerCase() + "_lootbox_" + tier;

        register();
    }

    @Override
    public String getID() {
        return id;
    }

    @Override
    public ItemStack getItem(Player player) {
        try {
            return type.getLootBoxClass()
                    .getDeclaredConstructor(int.class)
                    .newInstance(tier).getItem();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
