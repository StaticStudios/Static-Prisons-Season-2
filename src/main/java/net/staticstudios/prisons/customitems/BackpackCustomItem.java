package net.staticstudios.prisons.customitems;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.backpacks.Backpack;
import net.staticstudios.prisons.backpacks.BackpackManager;
import net.staticstudios.prisons.backpacks.config.BackpackConfig;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public enum BackpackCustomItem implements CustomItem {
    TIER_1("backpack_tier_1", 1),
    TIER_2("backpack_tier_2", 2),
    TIER_3("backpack_tier_3", 3),
    TIER_4("backpack_tier_4", 4),
    TIER_5("backpack_tier_5", 5),
    TIER_6("backpack_tier_6", 6),
    TIER_7("backpack_tier_7", 7),
    TIER_8("backpack_tier_8", 8),
    TIER_9("backpack_tier_9", 9),
    TIER_10("backpack_tier_10", 10);


    private final String id;
    private final int tier;

    BackpackCustomItem(String id, int tier) {
        this.id = id;
        this.tier = tier;
        register();
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public ItemStack getItem(Audience audience) {
        if (audience == null) {
            audience = Audience.empty();
        }

        return getItem(audience, new String[]{"true", "0"});
    }

    @Override
    public ItemStack getItem(Audience audience, String[] args) {
        boolean registered;
        long maxSize;
        try {
            registered = Boolean.parseBoolean(args[0]);
            maxSize = Integer.parseInt(args[1]);
        } catch (Exception e) {
            // TODO: 16/11/2022 component
            String msg = "Got an error while parsing args for " + id + "! Using default values instead... [true, 0]\n" + "Expected: [boolean(should be tracked by the server), int(max capacity)]\n" + "Got: " + Arrays.toString(args);
            if (audience != null) {
                audience.sendMessage(Component.text(msg));
            }
            StaticPrisons.log(msg);
            registered = true;
            maxSize = 0;
        }

        Backpack bp = BackpackManager.createBackpack(tier);
        if (!registered) {
            BackpackManager.ALL_BACKPACKS.remove(bp.getUUID());
        }
        bp.setCapacity(Math.min(maxSize, BackpackConfig.tier(tier).maxSize()), false);
        bp.updateItemNow();
        return bp.getItem();
    }
}
