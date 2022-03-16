package me.staticstudios.prisons.gameplay.gui.menus;

import me.staticstudios.prisons.Main;
import me.staticstudios.prisons.gameplay.gui.GUI;
import me.staticstudios.prisons.gameplay.gui.GUIPage;
import me.staticstudios.prisons.gameplay.islands.special.robots.BaseRobot;
import me.staticstudios.prisons.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.UUID;

public class RobotMenus {
    //View a player's stats
    public static void main() {
        GUIPage guiPage = new GUIPage() {
            @Override
            public void onOpen(Player player) {
                menuItems = new ArrayList<>();
                BaseRobot robot = BaseRobot.createRobotFromEntity((Zombie) Bukkit.getEntity(UUID.fromString(args)));
                robot.update();
                ItemStack item;
                ItemMeta meta;
                for (int i = 0; i < 4; i++) menuItems.add(GUI.createDarkGrayPlaceholderItem());
                item = GUI.createEnchantedMenuItem(identifier, Material.COMPARATOR, ChatColor.LIGHT_PURPLE + "Rotate", ChatColor.GRAY + "" + ChatColor.ITALIC + "Change where the robot is facing");
                meta = item.getItemMeta();
                meta.getPersistentDataContainer().set(new NamespacedKey(Main.getMain(), "uuid"), PersistentDataType.STRING, args);
                item.setItemMeta(meta);
                menuItems.add(item);
                for (int i = 0; i < 6; i++) menuItems.add(GUI.createDarkGrayPlaceholderItem());
                switch (robot.getType()) {
                    case "money" -> {
                        guiTitle = ChatColor.translateAlternateColorCodes('&', "&aMoney Robot");
                        item = GUI.createEnchantedMenuItem(identifier, Material.PAPER, ChatColor.GREEN + "Claim Money", ChatColor.WHITE + "---------------", ChatColor.AQUA + "Current Amount: " + ChatColor.WHITE + Utils.prettyNum(robot.getValue()), ChatColor.WHITE + "---------------");
                        meta = item.getItemMeta();
                        meta.getPersistentDataContainer().set(new NamespacedKey(Main.getMain(), "uuid"), PersistentDataType.STRING, args);
                        item.setItemMeta(meta);
                        menuItems.add(item);
                    }
                }
                menuItems.add(GUI.createDarkGrayPlaceholderItem());
                item = GUI.createEnchantedMenuItem(identifier, Material.NETHER_STAR, ChatColor.AQUA + "Upgrade", ChatColor.GRAY + "" + ChatColor.ITALIC + "not done");
                meta = item.getItemMeta();
                meta.getPersistentDataContainer().set(new NamespacedKey(Main.getMain(), "uuid"), PersistentDataType.STRING, args);
                item.setItemMeta(meta);
                menuItems.add(item);
                menuItems.add(GUI.createDarkGrayPlaceholderItem());
                item = GUI.createEnchantedMenuItem(identifier, Material.INK_SAC, ChatColor.YELLOW + "Oil", ChatColor.WHITE + "---------------", ChatColor.AQUA + "Current Amount: " + ChatColor.WHITE + robot.getOil(), ChatColor.WHITE + "---------------", ChatColor.GRAY + "" + ChatColor.ITALIC + "Left-click: add oil", ChatColor.GRAY + "" + ChatColor.ITALIC + "Right-click: remove oil");
                meta = item.getItemMeta();
                meta.getPersistentDataContainer().set(new NamespacedKey(Main.getMain(), "uuid"), PersistentDataType.STRING, args);
                item.setItemMeta(meta);
                menuItems.add(item);
                for (int i = 0; i < 6; i++) menuItems.add(GUI.createDarkGrayPlaceholderItem());
                item = GUI.createEnchantedMenuItem(identifier, Material.PISTON, ChatColor.LIGHT_PURPLE + "Pick up", ChatColor.GRAY + "" + ChatColor.ITALIC + "Move this robot");
                meta = item.getItemMeta();
                meta.getPersistentDataContainer().set(new NamespacedKey(Main.getMain(), "uuid"), PersistentDataType.STRING, args);
                item.setItemMeta(meta);
                menuItems.add(item);
            }
            @Override
            public void item4Clicked(InventoryClickEvent e) {
                String uuid = e.getCurrentItem().getItemMeta().getPersistentDataContainer().get(new NamespacedKey(Main.getMain(), "uuid"), PersistentDataType.STRING);
                BaseRobot robot = BaseRobot.createRobotFromEntity((Zombie) Bukkit.getEntity(UUID.fromString(uuid)));
                robot.zombie.setRotation(robot.zombie.getLocation().getYaw() + 90, 0);
            }
            @Override
            public void item11Clicked(InventoryClickEvent e) {

            }
            @Override
            public void item13Clicked(InventoryClickEvent e) {

            }
            @Override
            public void item15Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                String uuid = e.getCurrentItem().getItemMeta().getPersistentDataContainer().get(new NamespacedKey(Main.getMain(), "uuid"), PersistentDataType.STRING);
                BaseRobot robot = BaseRobot.createRobotFromEntity((Zombie) Bukkit.getEntity(UUID.fromString(uuid)));
                robot.update();
                robot.addOil(1d);
                player.sendMessage(robot.getOil() + ""); //TODO: format the oil number to old show 3 decimal places or whatever
                open(player);
            }
            @Override
            public void item22Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                String uuid = e.getCurrentItem().getItemMeta().getPersistentDataContainer().get(new NamespacedKey(Main.getMain(), "uuid"), PersistentDataType.STRING);
                BaseRobot robot = BaseRobot.createRobotFromEntity((Zombie) Bukkit.getEntity(UUID.fromString(uuid)));
                robot.zombie.remove();
                //TODO: store the values
                player.closeInventory();
            }
        };
        guiPage.identifier = "robotMain";
        guiPage.guiTitle = ChatColor.translateAlternateColorCodes('&', "&dRobot");
        guiPage.onCloseGoToMenu = null;
        guiPage.register();
    }
}
