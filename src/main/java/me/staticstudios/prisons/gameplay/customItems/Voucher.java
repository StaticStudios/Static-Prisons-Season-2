package me.staticstudios.prisons.gameplay.customItems;

import me.staticstudios.prisons.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Voucher {
    public final String id;
    public final ItemStack item;
    public Voucher(String id, Material icon, String displayName, String... lore) {
        this.id = id;
        ItemStack item = new ItemStack(icon);
        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(new NamespacedKey(Main.getMain(), "customItemGroup"), PersistentDataType.STRING, "voucher");
        meta.getPersistentDataContainer().set(new NamespacedKey(Main.getMain(), "customItemType"), PersistentDataType.STRING, id);
        meta.setDisplayName(displayName);
        List<String> itemLore = new ArrayList<>();
        Collections.addAll(itemLore, lore);
        itemLore.add("");
        itemLore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "Right click to claim!");
        meta.setLore(itemLore);
        item.setItemMeta(meta);
        this.item = item;
    }
    void onClaim(Player player) {
    }
}
