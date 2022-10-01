package net.staticstudios.prisons.customitems.items;

import net.staticstudios.prisons.customitems.CustomItem;
import net.staticstudios.prisons.lootboxes.MoneyLootBox;
import net.staticstudios.prisons.lootboxes.PickaxeLootBox;
import net.staticstudios.prisons.lootboxes.TokenLootBox;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public enum LootBoxCustomItem implements CustomItem {
    MONEY_1(1, "money", "money_lootbox_1"),
    MONEY_2(2, "money", "money_lootbox_2"),
    MONEY_3(3, "money", "money_lootbox_3"),
    MONEY_4(4, "money", "money_lootbox_4"),
    MONEY_5(5, "money", "money_lootbox_5"),
    TOKEN_1(1, "token", "token_lootbox_1"),
    TOKEN_2(2, "token", "token_lootbox_2"),
    TOKEN_3(3, "token", "token_lootbox_3"),
    TOKEN_4(4, "token", "token_lootbox_4"),
    TOKEN_5(5, "token", "token_lootbox_5"),
    TOKEN_6(6, "token", "token_lootbox_6"),
    TOKEN_7(7, "token", "token_lootbox_7"),
    TOKEN_8(8, "token", "token_lootbox_8"),
    TOKEN_9(9, "token", "token_lootbox_9"),
    TOKEN_10(10, "token", "token_lootbox_10"),
    PICKAXE_1(1, "pickaxe", "pickaxe_lootbox_1");
    //todo: add more pickaxe lootboxes

    private final int tier;
    private final String type;
    private final String id;

    LootBoxCustomItem(int tier, String type, String id) {
        this.tier = tier;
        this.type = type;
        this.id = id;
        register();
    }

    @Override
    public String getID() {
        return id;
    }

    @Override
    public ItemStack getItem(Player player) {
        return switch (type) {
            case "money" -> new MoneyLootBox(tier).getItem();
            case "token" -> new TokenLootBox(tier).getItem();
            case "pickaxe" -> new PickaxeLootBox(tier).getItem();
            default -> null;
        };
    }
}
