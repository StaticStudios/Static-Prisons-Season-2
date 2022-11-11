package net.staticstudios.prisons.utils;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
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

    public static String toBase64(Object o) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);
            dataOutput.writeObject(o);
            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static Object fromBase64(String data) {
        try {
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(new ByteArrayInputStream(Base64Coder.decodeLines(data)));
            Object o = dataInput.readObject();
            dataInput.close();
            return o;
        } catch (ClassNotFoundException e) {
            try {
                throw new IOException("Unable to decode class type.", e);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
