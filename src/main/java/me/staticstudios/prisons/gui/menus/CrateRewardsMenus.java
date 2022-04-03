package me.staticstudios.prisons.gui.menus;

import me.staticstudios.prisons.Main;
import me.staticstudios.prisons.crates.*;
import me.staticstudios.prisons.gui.GUI;
import me.staticstudios.prisons.gui.GUIPage;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CrateRewardsMenus {
    //Common Crate Rewards
    public static void common() {
        GUIPage guiPage = new GUIPage() {
            @Override
            public void onOpen(Player player) {
                menuItems = new ArrayList<>();
                int totalChance = 0;
                for(CrateReward reward : CommonCrate.rewards) {
                    totalChance += reward.chance;
                }
                for(CrateReward reward : CommonCrate.rewards) {
                    DecimalFormat df = new DecimalFormat();
                    df.setMaximumFractionDigits(2);
                    ItemStack item = new ItemStack(reward.reward);
                    ItemMeta itemMeta = item.getItemMeta();
                    itemMeta.getPersistentDataContainer().set(new NamespacedKey(Main.getMain(), "guiItem"), PersistentDataType.STRING, "placeholder");
                    List<String> lore = itemMeta.getLore();
                    if (!itemMeta.hasLore()) lore = new ArrayList<>();
                    lore.add(ChatColor.GRAY + "--------------------");
                    lore.add(ChatColor.AQUA + "Chance To Win: " + ChatColor.WHITE + "%" + reward.chance);
                    lore.add(ChatColor.GRAY + "--------------------");
                    itemMeta.setLore(lore);
                    item.setItemMeta(itemMeta);
                    menuItems.add(item);
                }
                while (menuItems.size() < 27) {
                    menuItems.add(GUI.createLightGrayPlaceholderItem());
                }
            }
        };
        guiPage.identifier = "crateRewardsCommon";
        guiPage.guiTitle = ChatColor.translateAlternateColorCodes('&', "&dCommon Crate Rewards");
        guiPage.onCloseGoToMenu = null;
        guiPage.register();
    }
    //Rare Crate Rewards
    public static void rare() {
        GUIPage guiPage = new GUIPage() {
            @Override
            public void onOpen(Player player) {
                menuItems = new ArrayList<>();
                int totalChance = 0;
                for(CrateReward reward : RareCrate.rewards) {
                    totalChance += reward.chance;
                }
                for(CrateReward reward : RareCrate.rewards) {
                    DecimalFormat df = new DecimalFormat();
                    df.setMaximumFractionDigits(2);
                    ItemStack item = new ItemStack(reward.reward);
                    ItemMeta itemMeta = item.getItemMeta();
                    itemMeta.getPersistentDataContainer().set(new NamespacedKey(Main.getMain(), "guiItem"), PersistentDataType.STRING, "placeholder");
                    List<String> lore = itemMeta.getLore();
                    if (!itemMeta.hasLore()) lore = new ArrayList<>();
                    lore.add(ChatColor.GRAY + "--------------------");
                    lore.add(ChatColor.AQUA + "Chance To Win: " + ChatColor.WHITE + "%" + reward.chance);
                    lore.add(ChatColor.GRAY + "--------------------");
                    itemMeta.setLore(lore);
                    item.setItemMeta(itemMeta);
                    menuItems.add(item);
                }
                while (menuItems.size() < 27) {
                    menuItems.add(GUI.createLightGrayPlaceholderItem());
                }
            }
        };
        guiPage.identifier = "crateRewardsRare";
        guiPage.guiTitle = ChatColor.translateAlternateColorCodes('&', "&dRare Crate Rewards");
        guiPage.onCloseGoToMenu = null;
        guiPage.register();
    }
    //Epic Crate Rewards
    public static void epic() {
        GUIPage guiPage = new GUIPage() {
            @Override
            public void onOpen(Player player) {
                menuItems = new ArrayList<>();
                int totalChance = 0;
                for(CrateReward reward : EpicCrate.rewards) {
                    totalChance += reward.chance;
                }
                for(CrateReward reward : EpicCrate.rewards) {
                    DecimalFormat df = new DecimalFormat();
                    df.setMaximumFractionDigits(2);
                    ItemStack item = new ItemStack(reward.reward);
                    ItemMeta itemMeta = item.getItemMeta();
                    itemMeta.getPersistentDataContainer().set(new NamespacedKey(Main.getMain(), "guiItem"), PersistentDataType.STRING, "placeholder");
                    List<String> lore = itemMeta.getLore();
                    if (!itemMeta.hasLore()) lore = new ArrayList<>();
                    lore.add(ChatColor.GRAY + "--------------------");
                    lore.add(ChatColor.AQUA + "Chance To Win: " + ChatColor.WHITE + "%" + reward.chance);
                    lore.add(ChatColor.GRAY + "--------------------");
                    itemMeta.setLore(lore);
                    item.setItemMeta(itemMeta);
                    menuItems.add(item);
                }
                while (menuItems.size() < 27) {
                    menuItems.add(GUI.createLightGrayPlaceholderItem());
                }
            }
        };
        guiPage.identifier = "crateRewardsEpic";
        guiPage.guiTitle = ChatColor.translateAlternateColorCodes('&', "&dEpic Crate Rewards");
        guiPage.onCloseGoToMenu = null;
        guiPage.register();
    }
    //Legendary Crate Rewards
    public static void legendary() {
        GUIPage guiPage = new GUIPage() {
            @Override
            public void onOpen(Player player) {
                menuItems = new ArrayList<>();
                int totalChance = 0;
                for(CrateReward reward : LegendaryCrate.rewards) {
                    totalChance += reward.chance;
                }
                for(CrateReward reward : LegendaryCrate.rewards) {
                    DecimalFormat df = new DecimalFormat();
                    df.setMaximumFractionDigits(2);
                    ItemStack item = new ItemStack(reward.reward);
                    ItemMeta itemMeta = item.getItemMeta();
                    itemMeta.getPersistentDataContainer().set(new NamespacedKey(Main.getMain(), "guiItem"), PersistentDataType.STRING, "placeholder");
                    List<String> lore = itemMeta.getLore();
                    if (!itemMeta.hasLore()) lore = new ArrayList<>();
                    lore.add(ChatColor.GRAY + "--------------------");
                    lore.add(ChatColor.AQUA + "Chance To Win: " + ChatColor.WHITE + "%" + reward.chance);
                    lore.add(ChatColor.GRAY + "--------------------");
                    itemMeta.setLore(lore);
                    item.setItemMeta(itemMeta);
                    menuItems.add(item);
                }
                while (menuItems.size() < 27) {
                    menuItems.add(GUI.createLightGrayPlaceholderItem());
                }
            }
        };
        guiPage.identifier = "crateRewardsLegendary";
        guiPage.guiTitle = ChatColor.translateAlternateColorCodes('&', "&dLegendary Crate Rewards");
        guiPage.onCloseGoToMenu = null;
        guiPage.register();
    }
    //Static Crate Rewards
    public static void _static() {
        GUIPage guiPage = new GUIPage() {
            @Override
            public void onOpen(Player player) {
                menuItems = new ArrayList<>();
                int totalChance = 0;
                for(CrateReward reward : StaticCrate.rewards) {
                    totalChance += reward.chance;
                }
                for(CrateReward reward : StaticCrate.rewards) {
                    DecimalFormat df = new DecimalFormat();
                    df.setMaximumFractionDigits(2);
                    ItemStack item = new ItemStack(reward.reward);
                    ItemMeta itemMeta = item.getItemMeta();
                    itemMeta.getPersistentDataContainer().set(new NamespacedKey(Main.getMain(), "guiItem"), PersistentDataType.STRING, "placeholder");
                    List<String> lore = itemMeta.getLore();
                    if (!itemMeta.hasLore()) lore = new ArrayList<>();
                    lore.add(ChatColor.GRAY + "--------------------");
                    lore.add(ChatColor.AQUA + "Chance To Win: " + ChatColor.WHITE + "%" + reward.chance);
                    lore.add(ChatColor.GRAY + "--------------------");
                    itemMeta.setLore(lore);
                    item.setItemMeta(itemMeta);
                    menuItems.add(item);
                }
                while (menuItems.size() < 27) {
                    menuItems.add(GUI.createLightGrayPlaceholderItem());
                }
            }
        };
        guiPage.identifier = "crateRewardsStatic";
        guiPage.guiTitle = ChatColor.translateAlternateColorCodes('&', "&dStatic Crate Rewards");
        guiPage.onCloseGoToMenu = null;
        guiPage.register();
    }
    //Static+ Crate Rewards
    public static void staticp() {
        GUIPage guiPage = new GUIPage() {
            @Override
            public void onOpen(Player player) {
                menuItems = new ArrayList<>();
                int totalChance = 0;
                for(CrateReward reward : StaticpCrate.rewards) {
                    totalChance += reward.chance;
                }
                for(CrateReward reward : StaticpCrate.rewards) {
                    DecimalFormat df = new DecimalFormat();
                    df.setMaximumFractionDigits(2);
                    ItemStack item = new ItemStack(reward.reward);
                    ItemMeta itemMeta = item.getItemMeta();
                    itemMeta.getPersistentDataContainer().set(new NamespacedKey(Main.getMain(), "guiItem"), PersistentDataType.STRING, "placeholder");
                    List<String> lore = itemMeta.getLore();
                    if (!itemMeta.hasLore()) lore = new ArrayList<>();
                    lore.add(ChatColor.GRAY + "--------------------");
                    lore.add(ChatColor.AQUA + "Chance To Win: " + ChatColor.WHITE + "%" + reward.chance);
                    lore.add(ChatColor.GRAY + "--------------------");
                    itemMeta.setLore(lore);
                    item.setItemMeta(itemMeta);
                    menuItems.add(item);
                }
                while (menuItems.size() < 27) {
                    menuItems.add(GUI.createLightGrayPlaceholderItem());
                }
            }
        };
        guiPage.identifier = "crateRewardsStaticp";
        guiPage.guiTitle = ChatColor.translateAlternateColorCodes('&', "&dStatic+ Crate Rewards");
        guiPage.onCloseGoToMenu = null;
        guiPage.register();
    }
    //Vote Crate Rewards
    public static void vote() {
        GUIPage guiPage = new GUIPage() {
            @Override
            public void onOpen(Player player) {
                menuItems = new ArrayList<>();
                int totalChance = 0;
                for(CrateReward reward : VoteCrate.rewards) {
                    totalChance += reward.chance;
                }
                for(CrateReward reward : VoteCrate.rewards) {
                    DecimalFormat df = new DecimalFormat();
                    df.setMaximumFractionDigits(2);
                    ItemStack item = new ItemStack(reward.reward);
                    ItemMeta itemMeta = item.getItemMeta();
                    itemMeta.getPersistentDataContainer().set(new NamespacedKey(Main.getMain(), "guiItem"), PersistentDataType.STRING, "placeholder");
                    List<String> lore = itemMeta.getLore();
                    if (!itemMeta.hasLore()) lore = new ArrayList<>();
                    lore.add(ChatColor.GRAY + "--------------------");
                    lore.add(ChatColor.AQUA + "Chance To Win: " + ChatColor.WHITE + "%" + reward.chance);
                    lore.add(ChatColor.GRAY + "--------------------");
                    itemMeta.setLore(lore);
                    item.setItemMeta(itemMeta);
                    menuItems.add(item);
                }
                while (menuItems.size() < 27) {
                    menuItems.add(GUI.createLightGrayPlaceholderItem());
                }
            }
        };
        guiPage.identifier = "crateRewardsVote";
        guiPage.guiTitle = ChatColor.translateAlternateColorCodes('&', "&dVote Crate Rewards");
        guiPage.onCloseGoToMenu = null;
        guiPage.register();
    }
    //Pickaxe Crate Rewards
    public static void pickaxe() {
        GUIPage guiPage = new GUIPage() {
            @Override
            public void onOpen(Player player) {
                menuItems = new ArrayList<>();
                int totalChance = 0;
                for(CrateReward reward : PickaxeCrate.rewards) {
                    totalChance += reward.chance;
                }
                for(CrateReward reward : PickaxeCrate.rewards) {
                    DecimalFormat df = new DecimalFormat();
                    df.setMaximumFractionDigits(2);
                    ItemStack item = new ItemStack(reward.reward);
                    ItemMeta itemMeta = item.getItemMeta();
                    itemMeta.getPersistentDataContainer().set(new NamespacedKey(Main.getMain(), "guiItem"), PersistentDataType.STRING, "placeholder");
                    List<String> lore = itemMeta.getLore();
                    if (!itemMeta.hasLore()) lore = new ArrayList<>();
                    lore.add(ChatColor.GRAY + "--------------------");
                    lore.add(ChatColor.AQUA + "Chance To Win: " + ChatColor.WHITE + "%" + reward.chance);
                    lore.add(ChatColor.GRAY + "--------------------");
                    itemMeta.setLore(lore);
                    item.setItemMeta(itemMeta);
                    menuItems.add(item);
                }
                while (menuItems.size() < 27) {
                    menuItems.add(GUI.createLightGrayPlaceholderItem());
                }
            }
        };
        guiPage.identifier = "crateRewardsPickaxe";
        guiPage.guiTitle = ChatColor.translateAlternateColorCodes('&', "&dPickaxe Crate Rewards");
        guiPage.onCloseGoToMenu = null;
        guiPage.register();
    }
    //Kit Crate Rewards
    public static void kit() {
        GUIPage guiPage = new GUIPage() {
            @Override
            public void onOpen(Player player) {
                menuItems = new ArrayList<>();
                int totalChance = 0;
                for(CrateReward reward : KitCrate.rewards) {
                    totalChance += reward.chance;
                }
                for(CrateReward reward : KitCrate.rewards) {
                    DecimalFormat df = new DecimalFormat();
                    df.setMaximumFractionDigits(2);
                    ItemStack item = new ItemStack(reward.reward);
                    ItemMeta itemMeta = item.getItemMeta();
                    itemMeta.getPersistentDataContainer().set(new NamespacedKey(Main.getMain(), "guiItem"), PersistentDataType.STRING, "placeholder");
                    List<String> lore = itemMeta.getLore();
                    if (!itemMeta.hasLore()) lore = new ArrayList<>();
                    lore.add(ChatColor.GRAY + "--------------------");
                    lore.add(ChatColor.AQUA + "Chance To Win: " + ChatColor.WHITE + "%" + reward.chance);
                    lore.add(ChatColor.GRAY + "--------------------");
                    itemMeta.setLore(lore);
                    item.setItemMeta(itemMeta);
                    menuItems.add(item);
                }
                while (menuItems.size() < 27) {
                    menuItems.add(GUI.createLightGrayPlaceholderItem());
                }
            }
        };
        guiPage.identifier = "crateRewardsKit";
        guiPage.guiTitle = ChatColor.translateAlternateColorCodes('&', "&dKit Crate Rewards");
        guiPage.onCloseGoToMenu = null;
        guiPage.register();
    }
}
