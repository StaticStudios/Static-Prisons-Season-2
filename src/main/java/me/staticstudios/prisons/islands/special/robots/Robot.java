package me.staticstudios.prisons.islands.special.robots;

import me.staticstudios.prisons.Main;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Zombie;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.persistence.PersistentDataType;

public abstract class Robot {
    public static void entityClicked(EntityInteractEvent e) {
        //check if it was a robot and do stuff
    }
    public void spawnRobot(Location loc, String type, Color armorColor, String name) {
        //Dye Armor
        ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
        LeatherArmorMeta leatherHelmet = (LeatherArmorMeta) helmet.getItemMeta();
        leatherHelmet.setColor(armorColor);
        leatherHelmet.setUnbreakable(true);
        helmet.setItemMeta(leatherHelmet);
        ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
        LeatherArmorMeta leatherChestplate = (LeatherArmorMeta) chestplate.getItemMeta();
        leatherChestplate.setColor(armorColor);
        chestplate.setItemMeta(leatherChestplate);
        ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
        LeatherArmorMeta leatherLeggings = (LeatherArmorMeta) leggings.getItemMeta();
        leatherLeggings.setColor(armorColor);
        leggings.setItemMeta(leatherLeggings);
        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
        LeatherArmorMeta leatherBoots = (LeatherArmorMeta) boots.getItemMeta();
        leatherBoots.setColor(armorColor);
        boots.setItemMeta(leatherBoots);

        //Spawn Zombie
        Zombie zombie = loc.getWorld().spawn(loc, Zombie.class);
        zombie.setBaby();

        zombie.setCustomName(name);
        zombie.setCustomNameVisible(true);
        zombie.setAI(false);
        zombie.setSilent(true);
        zombie.setInvulnerable(true);

        zombie.getEquipment().setItemInMainHand(new ItemStack(Material.DIAMOND_PICKAXE));
        zombie.getEquipment().setHelmet(helmet);
        zombie.getEquipment().setChestplate(chestplate);
        zombie.getEquipment().setLeggings(leggings);
        zombie.getEquipment().setBoots(boots);

        //Persistant data container
        zombie.getPersistentDataContainer().set(new NamespacedKey(Main.getMain(), "type"), PersistentDataType.STRING, type);
        zombie.getPersistentDataContainer().set(new NamespacedKey(Main.getMain(), "level"), PersistentDataType.INTEGER, 1);
        zombie.getPersistentDataContainer().set(new NamespacedKey(Main.getMain(), "oil"), PersistentDataType.DOUBLE, 0d);
        zombie.getPersistentDataContainer().set(new NamespacedKey(Main.getMain(), "value"), PersistentDataType.STRING, "0");
        zombie.getPersistentDataContainer().set(new NamespacedKey(Main.getMain(), "lastUpdated"), PersistentDataType.LONG, System.currentTimeMillis());
    }
}
