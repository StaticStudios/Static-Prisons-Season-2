package me.staticstudios.prisons.customItems;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Kits {

    public static List<Kit> kits = new ArrayList<>();



    public static Kit tier1 = new Kit();
    public static Kit tier2 = new Kit();
    public static Kit tier3 = new Kit();
    public static Kit tier4 = new Kit();
    public static Kit tier5 = new Kit();
    public static Kit tier6 = new Kit();
    public static Kit potion = new Kit();
    public static Kit weapon = new Kit();

    public static void initialize() {
        //Tier 1
        tier1.name = ChatColor.AQUA + "Kit Tier 1";
        tier1.whatComesWithTheKit.add(" -Iron Helmet: Protection 1 | Unbreaking 1");
        tier1.whatComesWithTheKit.add(" -Iron Chestplate: Protection 1 | Unbreaking 1");
        tier1.whatComesWithTheKit.add(" -Iron Leggings: Protection 1 | Unbreaking 1");
        tier1.whatComesWithTheKit.add(" -Iron Boots: Protection 1 | Unbreaking 1");
        tier1.whatComesWithTheKit.add(" -Iron Sword: Sharpness 1 | Unbreaking 1");
        tier1.whatComesWithTheKit.add(" -Bread: 64");
        tier1.helmet = new ItemStack(Material.IRON_HELMET);
        tier1.helmet.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        tier1.helmet.addEnchantment(Enchantment.DURABILITY, 1);
        tier1.chestplate = new ItemStack(Material.IRON_CHESTPLATE);
        tier1.chestplate.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        tier1.chestplate.addEnchantment(Enchantment.DURABILITY, 1);
        tier1.leggings = new ItemStack(Material.IRON_LEGGINGS);
        tier1.leggings.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        tier1.leggings.addEnchantment(Enchantment.DURABILITY, 1);
        tier1.boots = new ItemStack(Material.IRON_BOOTS);
        tier1.boots.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        tier1.boots.addEnchantment(Enchantment.DURABILITY, 1);
        tier1.sword = new ItemStack(Material.IRON_SWORD);
        tier1.sword.addEnchantment(Enchantment.DAMAGE_ALL, 1);
        tier1.sword.addEnchantment(Enchantment.DURABILITY, 1);
        tier1.food1 = new ItemStack(Material.BREAD);
        tier1.food1.setAmount(64);
        kits.add(tier1);
        //Tier 2
        tier2.name = ChatColor.AQUA + "Kit Tier 2";
        tier2.whatComesWithTheKit.add(" -Iron Helmet: Protection 2 | Unbreaking 2");
        tier2.whatComesWithTheKit.add(" -Iron Chestplate: Protection 2 | Unbreaking 2");
        tier2.whatComesWithTheKit.add(" -Iron Leggings: Protection 2 | Unbreaking 2");
        tier2.whatComesWithTheKit.add(" -Iron Boots: Protection 2 | Unbreaking 2");
        tier2.whatComesWithTheKit.add(" -Iron Sword: Sharpness 2 | Unbreaking 2");
        tier2.whatComesWithTheKit.add(" -Bread: 64");
        tier2.whatComesWithTheKit.add(" -Golden Apple: 1");
        tier2.helmet = new ItemStack(Material.IRON_HELMET);
        tier2.helmet.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        tier2.helmet.addEnchantment(Enchantment.DURABILITY, 2);
        tier2.chestplate = new ItemStack(Material.IRON_CHESTPLATE);
        tier2.chestplate.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        tier2.chestplate.addEnchantment(Enchantment.DURABILITY, 2);
        tier2.leggings = new ItemStack(Material.IRON_LEGGINGS);
        tier2.leggings.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        tier2.leggings.addEnchantment(Enchantment.DURABILITY, 2);
        tier2.boots = new ItemStack(Material.IRON_BOOTS);
        tier2.boots.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        tier2.boots.addEnchantment(Enchantment.DURABILITY, 2);
        tier2.sword = new ItemStack(Material.IRON_SWORD);
        tier2.sword.addEnchantment(Enchantment.DAMAGE_ALL, 2);
        tier2.sword.addEnchantment(Enchantment.DURABILITY, 2);
        tier2.food1 = new ItemStack(Material.BREAD);
        tier2.food1.setAmount(64);
        tier2.food2 = new ItemStack(Material.GOLDEN_APPLE);
        tier2.food2.setAmount(1);
        kits.add(tier2);
        //tier 3
        tier3.name = ChatColor.AQUA + "Kit Tier 3";
        tier3.whatComesWithTheKit.add(" -Diamond Helmet: Protection 1 | Unbreaking 1");
        tier3.whatComesWithTheKit.add(" -Diamond Chestplate: Protection 1 | Unbreaking 1");
        tier3.whatComesWithTheKit.add(" -Diamond Leggings: Protection 1 | Unbreaking 1");
        tier3.whatComesWithTheKit.add(" -Diamond Boots: Protection 1 | Unbreaking 1");
        tier3.whatComesWithTheKit.add(" -Diamond Sword: Sharpness 1 | Unbreaking 1");
        tier3.whatComesWithTheKit.add(" -Bread: 64");
        tier3.whatComesWithTheKit.add(" -Golden Apple: 2");
        tier3.helmet = new ItemStack(Material.DIAMOND_HELMET);
        tier3.helmet.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        tier3.helmet.addEnchantment(Enchantment.DURABILITY, 1);
        tier3.chestplate = new ItemStack(Material.DIAMOND_CHESTPLATE);
        tier3.chestplate.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        tier3.chestplate.addEnchantment(Enchantment.DURABILITY, 1);
        tier3.leggings = new ItemStack(Material.DIAMOND_LEGGINGS);
        tier3.leggings.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        tier3.leggings.addEnchantment(Enchantment.DURABILITY, 1);
        tier3.boots = new ItemStack(Material.DIAMOND_BOOTS);
        tier3.boots.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        tier3.boots.addEnchantment(Enchantment.DURABILITY, 1);
        tier3.sword = new ItemStack(Material.DIAMOND_SWORD);
        tier3.sword.addEnchantment(Enchantment.DAMAGE_ALL, 1);
        tier3.sword.addEnchantment(Enchantment.DURABILITY, 1);
        tier3.food1 = new ItemStack(Material.BREAD);
        tier3.food1.setAmount(64);
        tier3.food2 = new ItemStack(Material.GOLDEN_APPLE);
        tier3.food2.setAmount(2);
        kits.add(tier3);
        //tier 4
        tier4.name = ChatColor.AQUA + "Kit Tier 4";
        tier4.whatComesWithTheKit.add(" -Diamond Helmet: Protection 2 | Unbreaking 2");
        tier4.whatComesWithTheKit.add(" -Diamond Chestplate: Protection 2 | Unbreaking 2");
        tier4.whatComesWithTheKit.add(" -Diamond Leggings: Protection 2 | Unbreaking 2");
        tier4.whatComesWithTheKit.add(" -Diamond Boots: Protection 2 | Unbreaking 2");
        tier4.whatComesWithTheKit.add(" -Diamond Sword: Sharpness 2 | Unbreaking 2");
        tier4.whatComesWithTheKit.add(" -Bread: 64");
        tier4.whatComesWithTheKit.add(" -Golden Apple: 5");
        tier4.helmet = new ItemStack(Material.DIAMOND_HELMET);
        tier4.helmet.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        tier4.helmet.addEnchantment(Enchantment.DURABILITY, 2);
        tier4.chestplate = new ItemStack(Material.DIAMOND_CHESTPLATE);
        tier4.chestplate.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        tier4.chestplate.addEnchantment(Enchantment.DURABILITY, 2);
        tier4.leggings = new ItemStack(Material.DIAMOND_LEGGINGS);
        tier4.leggings.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        tier4.leggings.addEnchantment(Enchantment.DURABILITY, 2);
        tier4.boots = new ItemStack(Material.DIAMOND_BOOTS);
        tier4.boots.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        tier4.boots.addEnchantment(Enchantment.DURABILITY, 2);
        tier4.sword = new ItemStack(Material.DIAMOND_SWORD);
        tier4.sword.addEnchantment(Enchantment.DAMAGE_ALL, 2);
        tier4.sword.addEnchantment(Enchantment.DURABILITY, 2);
        tier4.food1 = new ItemStack(Material.BREAD);
        tier4.food1.setAmount(64);
        tier4.food2 = new ItemStack(Material.GOLDEN_APPLE);
        tier4.food2.setAmount(5);
        kits.add(tier4);
        //tier 5
        tier5.name = ChatColor.AQUA + "Kit Tier 5";
        tier5.whatComesWithTheKit.add(" -Diamond Helmet: Protection 3 | Unbreaking 3");
        tier5.whatComesWithTheKit.add(" -Diamond Chestplate: Protection 3 | Unbreaking 3");
        tier5.whatComesWithTheKit.add(" -Diamond Leggings: Protection 3 | Unbreaking 3");
        tier5.whatComesWithTheKit.add(" -Diamond Boots: Protection 3 | Unbreaking 3");
        tier5.whatComesWithTheKit.add(" -Diamond Sword: Sharpness 3 | Unbreaking 3");
        tier5.whatComesWithTheKit.add(" -Bread: 64");
        tier5.whatComesWithTheKit.add(" -Golden Apple: 8");
        tier5.helmet = new ItemStack(Material.DIAMOND_HELMET);
        tier5.helmet.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
        tier5.helmet.addEnchantment(Enchantment.DURABILITY, 3);
        tier5.chestplate = new ItemStack(Material.DIAMOND_CHESTPLATE);
        tier5.chestplate.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
        tier5.chestplate.addEnchantment(Enchantment.DURABILITY, 3);
        tier5.leggings = new ItemStack(Material.DIAMOND_LEGGINGS);
        tier5.leggings.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
        tier5.leggings.addEnchantment(Enchantment.DURABILITY, 3);
        tier5.boots = new ItemStack(Material.DIAMOND_BOOTS);
        tier5.boots.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
        tier5.boots.addEnchantment(Enchantment.DURABILITY, 3);
        tier5.sword = new ItemStack(Material.DIAMOND_SWORD);
        tier5.sword.addEnchantment(Enchantment.DAMAGE_ALL, 3);
        tier5.sword.addEnchantment(Enchantment.DURABILITY, 3);
        tier5.food1 = new ItemStack(Material.BREAD);
        tier5.food1.setAmount(64);
        tier5.food2 = new ItemStack(Material.GOLDEN_APPLE);
        tier5.food2.setAmount(8);
        kits.add(tier5);
        //tier 6
        tier6.name = ChatColor.AQUA + "Kit Tier 6";
        tier6.whatComesWithTheKit.add(" -Diamond Helmet: Protection 4 | Unbreaking 4");
        tier6.whatComesWithTheKit.add(" -Diamond Chestplate: Protection 4 | Unbreaking 4");
        tier6.whatComesWithTheKit.add(" -Diamond Leggings: Protection 4 | Unbreaking 4");
        tier6.whatComesWithTheKit.add(" -Diamond Boots: Protection 4 | Unbreaking 4");
        tier6.whatComesWithTheKit.add(" -Diamond Sword: Sharpness 4 | Unbreaking 4");
        tier6.whatComesWithTheKit.add(" -Bread: 64");
        tier6.whatComesWithTheKit.add(" -Golden Apple: 12");
        tier6.helmet = new ItemStack(Material.DIAMOND_HELMET);
        tier6.helmet.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
        tier6.helmet.addEnchantment(Enchantment.DURABILITY, 3);
        tier6.chestplate = new ItemStack(Material.DIAMOND_CHESTPLATE);
        tier6.chestplate.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
        tier6.chestplate.addEnchantment(Enchantment.DURABILITY, 3);
        tier6.leggings = new ItemStack(Material.DIAMOND_LEGGINGS);
        tier6.leggings.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
        tier6.leggings.addEnchantment(Enchantment.DURABILITY, 3);
        tier6.boots = new ItemStack(Material.DIAMOND_BOOTS);
        tier6.boots.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
        tier6.boots.addEnchantment(Enchantment.DURABILITY, 3);
        tier6.sword = new ItemStack(Material.DIAMOND_SWORD);
        tier6.sword.addEnchantment(Enchantment.DAMAGE_ALL, 4);
        tier6.sword.addEnchantment(Enchantment.DURABILITY, 3);
        tier6.food1 = new ItemStack(Material.BREAD);
        tier6.food1.setAmount(64);
        tier6.food2 = new ItemStack(Material.GOLDEN_APPLE);
        tier6.food2.setAmount(12);
        kits.add(tier6);
        //potion kit
        potion.name = ChatColor.AQUA + "Kit Potion";
        potion.whatComesWithTheKit.add(" -Golden Helmet: Protection 1 | Unbreaking 1");
        potion.whatComesWithTheKit.add(" -Golden Chestplate: Protection 1 | Unbreaking 1");
        potion.whatComesWithTheKit.add(" -Golden Leggings: Protection 1 | Unbreaking 1");
        potion.whatComesWithTheKit.add(" -Golden Boots: Protection 1 | Unbreaking 1");
        potion.whatComesWithTheKit.add(" -Golden Sword: Sharpness 1 | Unbreaking 1");
        potion.whatComesWithTheKit.add(" -Bread: 64");
        potion.whatComesWithTheKit.add(" -Golden Apple: 6");
        potion.helmet = new ItemStack(Material.GOLDEN_HELMET);
        potion.helmet.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        potion.helmet.addEnchantment(Enchantment.DURABILITY, 1);
        potion.chestplate = new ItemStack(Material.GOLDEN_CHESTPLATE);
        potion.chestplate.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        potion.chestplate.addEnchantment(Enchantment.DURABILITY, 1);
        potion.leggings = new ItemStack(Material.GOLDEN_LEGGINGS);
        potion.leggings.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        potion.leggings.addEnchantment(Enchantment.DURABILITY, 1);
        potion.boots = new ItemStack(Material.GOLDEN_BOOTS);
        potion.boots.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        potion.boots.addEnchantment(Enchantment.DURABILITY, 1);
        potion.food2 = new ItemStack(Material.GOLDEN_APPLE);
        potion.food2.setAmount(6);
        kits.add(potion);
        //weapons kit
        weapon.name = ChatColor.AQUA + "Kit Weapons";
        weapon.whatComesWithTheKit.add(" -Diamond Sword: Sharpness 4 | Unbreaking 3");
        weapon.whatComesWithTheKit.add(" -Diamond Axe: Protection 4 | Unbreaking 3");
        weapon.whatComesWithTheKit.add(" -Bow: Power 4 | Punch 1");
        weapon.whatComesWithTheKit.add(" -Arrow: 64");
        weapon.whatComesWithTheKit.add(" -Bread: 64");
        weapon.whatComesWithTheKit.add(" -Golden Apple: 2");
        weapon.sword = new ItemStack(Material.DIAMOND_SWORD);
        weapon.sword.addEnchantment(Enchantment.DAMAGE_ALL, 4);
        weapon.sword.addEnchantment(Enchantment.DURABILITY, 3);
        weapon.sword = new ItemStack(Material.DIAMOND_AXE);
        weapon.axe = new ItemStack(Material.DIAMOND_AXE);
        weapon.axe.addEnchantment(Enchantment.DAMAGE_ALL, 4);
        weapon.axe.addEnchantment(Enchantment.DURABILITY, 3);
        weapon.bow = new ItemStack(Material.BOW);
        weapon.bow.addEnchantment(Enchantment.ARROW_DAMAGE, 4);
        weapon.bow.addEnchantment(Enchantment.DURABILITY, 3);
        weapon.bow.addEnchantment(Enchantment.ARROW_KNOCKBACK, 1);
        weapon.arrow = new ItemStack(Material.ARROW);
        weapon.arrow.setAmount(64);
        weapon.food1 = new ItemStack(Material.BREAD);
        weapon.food1.setAmount(64);
        weapon.food2 = new ItemStack(Material.GOLDEN_APPLE);
        weapon.food2.setAmount(2);
        kits.add(weapon);
    }
}
