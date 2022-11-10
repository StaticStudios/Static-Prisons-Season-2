package net.staticstudios.prisons.customitems;

import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.customitems.lootboxes.LootBox;
import net.staticstudios.prisons.customitems.lootboxes.LootBoxType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

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
    public String getId() {
        return id;
    }

    @Override
    public ItemStack getItem(Player player) {
        return getItem(player, new String[]{"true", "0"});
    }

    @Override
    public ItemStack getItem(Player player, String[] args) {
        boolean registered;
        long progress;
        try {
            registered = Boolean.parseBoolean(args[0]);
            progress = Long.parseLong(args[1]);
        } catch (Exception e) {
            String msg = "Got an error while parsing args for " + id + "! Using default values instead... [true, 0]\n" + "Expected: [boolean(should be tracked by the server), long(progress)]\n" + "Got: " + Arrays.toString(args);
            if (player != null) {
                player.sendMessage(msg);
            }
            StaticPrisons.log(msg);
            registered = true;
            progress = 0;
        }

        try {
            LootBox lootbox = type.getLootBoxClass()
                    .getDeclaredConstructor(int.class)
                    .newInstance(tier);
            if (!registered) {
                LootBox.LOOT_BOXES.remove(lootbox.getUuid());
            }
            lootbox.setProgress(progress, false);
            lootbox.updateItemNow();
            ItemStack item = lootbox.getItem();
            setCustomItem(item, this);
            return item;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
