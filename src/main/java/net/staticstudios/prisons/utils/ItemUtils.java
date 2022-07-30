package net.staticstudios.prisons.utils;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
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

}
