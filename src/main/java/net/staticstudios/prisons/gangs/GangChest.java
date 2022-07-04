package net.staticstudios.prisons.gangs;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class GangChest implements InventoryHolder {

    public static Map<UUID, GangChest> GANG_UUID_TO_GANG_CHEST = new HashMap<>();

    private UUID gangUUID;
    private List<Map<String, Object>> serializedItems;
    private Inventory chestInventory;

    public GangChest(UUID gangID, List<Map<String, Object>> serializedItems) {
        this.gangUUID = gangID;
        this.serializedItems = serializedItems;
    }

    @Override
    public @NotNull Inventory getInventory() {
        if (chestInventory != null) return chestInventory;
        List<ItemStack> items = new ArrayList<>();
        for (Map<String, Object> serializedItem : serializedItems) {
            if (serializedItem == null) items.add(null);
            else items.add(ItemStack.deserialize(serializedItem));
        }
        chestInventory = Bukkit.createInventory(this, 27, "Gang Chest");
        chestInventory.setContents(items.toArray(new ItemStack[0]));
        return chestInventory;
    }

    public List<Map<String, Object>> serializeContents() {
        List<Map<String, Object>> serializedItems = new ArrayList<>();
        for (ItemStack item : chestInventory.getContents()) {
            if (item == null) serializedItems.add(new HashMap<>());
            else serializedItems.add(item.serialize());
        }
        return serializedItems;
    }
}
