package net.staticstudios.prisons.gangs;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GangChest implements InventoryHolder {

    private final List<Map<String, Object>> serializedItems;
    private Inventory chestInventory;

    public GangChest(List<Map<String, Object>> serializedItems) {
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
        if (chestInventory == null) return serializedItems;
        List<Map<String, Object>> serializedItems = new ArrayList<>();
        for (ItemStack item : chestInventory.getContents()) {
            if (item == null) serializedItems.add(new HashMap<>());
            else serializedItems.add(item.serialize());
        }
        return serializedItems;
    }
}
