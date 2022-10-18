package net.staticstudios.prisons.utils;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.UUID;

public class ItemUtils {

    public static ItemStack createCustomSkull(String texture) {
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD);

        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
        PlayerProfile profile = Bukkit.createProfile(UUID.randomUUID(), null);

        profile.setProperty(new ProfileProperty("textures", texture));

        skullMeta.setPlayerProfile(profile);

        skull.setItemMeta(skullMeta);
        return skull;
    }

    public static String getPrettyItemName(ItemStack item) {
        String name;
        if (!item.hasItemMeta()) {
            name = PrisonUtils.capitalizeEachWord(item.getType().toString().replace("_", " "));
            name = ChatColor.RESET + "" + ChatColor.WHITE + name;
        } else {
            if (!item.getItemMeta().hasDisplayName()) {
                name = PrisonUtils.capitalizeEachWord(item.getType().toString().replace("_", " "));
                name = ChatColor.RESET + "" + ChatColor.WHITE + name;
            } else {
                name = item.getItemMeta().getDisplayName();
            }
        }
        return name;
    }
}
