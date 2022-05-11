package net.staticstudios.prisons.gui.doneConverting;

import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.data.dataHandling.PlayerData;
import net.staticstudios.prisons.enchants.handler.CustomEnchants;
import net.staticstudios.prisons.enchants.handler.EnchantEffects;
import net.staticstudios.prisons.enchants.handler.PrisonEnchants;
import net.staticstudios.prisons.gui.GUI;
import net.staticstudios.prisons.gui.GUIPage;
import net.staticstudios.prisons.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.math.BigInteger;
import java.util.ArrayList;

public class EnchantsMenus {
    //Select a pickaxe to enchant
    /*
    public static void selectPick() {
        GUIPage guiPage = new GUIPage() {
            @Override
            public void onOpen(Player player) {
                menuItems = new ArrayList<>();
                ItemStack[] items = player.getInventory().getContents();
                for (ItemStack item : items) {
                    if (!Utils.checkIsPrisonPickaxe(item)) continue;
                    String pickaxeId = item.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(StaticPrisons.getInstance(), "pickaxeUUID"), PersistentDataType.STRING);
                    ItemStack newItem = new ItemStack(Material.DIAMOND_PICKAXE);
                    ItemMeta meta = newItem.getItemMeta();
                    meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                    meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                    meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
                    meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                    meta.addEnchant(Enchantment.DIG_SPEED, 1, false);
                    meta.getPersistentDataContainer().set(new NamespacedKey(StaticPrisons.getInstance(), "guiItem"), PersistentDataType.STRING, "normal");
                    meta.getPersistentDataContainer().set(new NamespacedKey(StaticPrisons.getInstance(), "fromPage"), PersistentDataType.STRING, identifier);
                    meta.getPersistentDataContainer().set(new NamespacedKey(StaticPrisons.getInstance(), "pickaxeUUID"), PersistentDataType.STRING, pickaxeId);
                    if (item.getItemMeta().hasLore()) meta.setLore(item.getItemMeta().getLore());
                    meta.setDisplayName(Utils.getPrettyItemName(item));
                    newItem.setItemMeta(meta);
                    menuItems.add(newItem);
                }
            }
            @Override
            public void itemClicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                ItemStack item = e.getCurrentItem();
                String id = item.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(StaticPrisons.getInstance(), "pickaxeUUID"), PersistentDataType.STRING);
                PrisonEnchants.playerUUIDToPickaxeID.put(player.getUniqueId(), id);
                GUI.getGUIPage("enchantsMain").open(player);
            }
        };
        guiPage.identifier = "enchantsSelectPickaxe";
        guiPage.guiTitle = ChatColor.translateAlternateColorCodes('&', "&dSelect A Pickaxe To Enchant");
        guiPage.onCloseGoToMenu = "main";
        guiPage.register();
    }
    //Main menu
    public static void main() {
        GUIPage guiPage = new GUIPage() {
            @Override
            public void onOpen(Player player) {
                String pickaxeUUID = PrisonEnchants.playerUUIDToPickaxeID.get(player.getUniqueId());
                ItemStack pickaxe = Utils.findPickaxeInInventoryFromUUID(player, pickaxeUUID);
                if (pickaxe == null) {
                    player.closeInventory();
                    return;
                }
                PlayerData playerData = new PlayerData(player);
                menuItems = new ArrayList<>();
                for (int i = 0; i < 9; i++) {
                    menuItems.add(GUI.createDarkGrayPlaceholderItem());
                }
                menuItems.add(GUI.createDarkGrayPlaceholderItem());
                /*
                //menuItems.add(GUIUtils.createEnchantGUIItem(identifier, Material.DIAMOND, ChatColor.AQUA + "" + ChatColor.BOLD + PrisonEnchants.FORTUNE.DISPLAY_NAME, PrisonEnchants.FORTUNE, CustomEnchants.getEnchantLevel(pickaxe, "fortune"), playerData.getTokens()));
                menuItems.add(GUIUtils.createEnchantGUIItem(identifier, Material.AMETHYST_SHARD, ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + PrisonEnchants.ORE_SPLITTER.DISPLAY_NAME, PrisonEnchants.ORE_SPLITTER, CustomEnchants.getEnchantLevel(pickaxe, "oreSplitter"), playerData.getTokens()));
                menuItems.add(GUIUtils.createEnchantGUIItem(identifier, Material.SUNFLOWER, ChatColor.YELLOW + "" + ChatColor.BOLD + PrisonEnchants.TOKENATOR.DISPLAY_NAME, PrisonEnchants.TOKENATOR, CustomEnchants.getEnchantLevel(pickaxe, "tokenator"), playerData.getTokens()));
                menuItems.add(GUIUtils.createEnchantGUIItem(identifier, Material.TRIPWIRE_HOOK, ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + PrisonEnchants.KEY_FINDER.DISPLAY_NAME, PrisonEnchants.KEY_FINDER, CustomEnchants.getEnchantLevel(pickaxe, "keyFinder"), playerData.getTokens()));
                menuItems.add(GUIUtils.createEnchantGUIItem(identifier, Material.RAW_GOLD, ChatColor.RED + "" + ChatColor.BOLD + PrisonEnchants.METAL_DETECTOR.DISPLAY_NAME, PrisonEnchants.METAL_DETECTOR, CustomEnchants.getEnchantLevel(pickaxe, "metalDetector"), playerData.getTokens()));
                menuItems.add(GUI.createDarkGrayPlaceholderItem());
                menuItems.add(GUIUtils.createEnchantGUIItem(identifier, Material.GOLDEN_PICKAXE, ChatColor.YELLOW + "" + ChatColor.BOLD + PrisonEnchants.HASTE.DISPLAY_NAME, PrisonEnchants.HASTE, CustomEnchants.getEnchantLevel(pickaxe, "haste"), playerData.getTokens()));
                menuItems.add(GUI.createDarkGrayPlaceholderItem());
                menuItems.add(GUI.createDarkGrayPlaceholderItem());
                menuItems.add(GUIUtils.createEnchantGUIItem(identifier, Material.TNT, ChatColor.RED + "" + ChatColor.BOLD + PrisonEnchants.EXPLOSION.DISPLAY_NAME, PrisonEnchants.EXPLOSION, CustomEnchants.getEnchantLevel(pickaxe, "explosion"), playerData.getTokens()));
                menuItems.add(GUIUtils.createEnchantGUIItem(identifier, Material.ANVIL, ChatColor.DARK_GRAY + "" + ChatColor.BOLD + PrisonEnchants.JACK_HAMMER.DISPLAY_NAME, PrisonEnchants.JACK_HAMMER, CustomEnchants.getEnchantLevel(pickaxe, "jackHammer"), playerData.getTokens()));
                menuItems.add(GUIUtils.createEnchantGUIItem(identifier, Material.HOPPER, ChatColor.GOLD + "" + ChatColor.BOLD + PrisonEnchants.DOUBLE_WAMMY.DISPLAY_NAME, PrisonEnchants.DOUBLE_WAMMY, CustomEnchants.getEnchantLevel(pickaxe, "doubleWammy"), playerData.getTokens()));
                menuItems.add(GUIUtils.createEnchantGUIItem(identifier, Material.COMPARATOR, ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + PrisonEnchants.MULTI_DIRECTIONAL.DISPLAY_NAME, PrisonEnchants.MULTI_DIRECTIONAL, CustomEnchants.getEnchantLevel(pickaxe, "multiDirectional"), playerData.getTokens()));
                menuItems.add(GUIUtils.createEnchantGUIItem(identifier, Material.MAP, ChatColor.GREEN + "" + ChatColor.BOLD + PrisonEnchants.MERCHANT.DISPLAY_NAME, PrisonEnchants.MERCHANT, CustomEnchants.getEnchantLevel(pickaxe, "merchant"), playerData.getTokens()));
                menuItems.add(GUI.createDarkGrayPlaceholderItem());
                menuItems.add(GUIUtils.createEnchantGUIItem(identifier, Material.FEATHER, ChatColor.AQUA + "" + ChatColor.BOLD + PrisonEnchants.SPEED.DISPLAY_NAME, PrisonEnchants.SPEED, CustomEnchants.getEnchantLevel(pickaxe, "speed"), playerData.getTokens()));
                menuItems.add(GUI.createDarkGrayPlaceholderItem());
                menuItems.add(GUI.createDarkGrayPlaceholderItem());
                menuItems.add(GUIUtils.createEnchantGUIItem(identifier, Material.EMERALD, ChatColor.YELLOW + "" + ChatColor.BOLD + PrisonEnchants.CONSISTENCY.DISPLAY_NAME, PrisonEnchants.CONSISTENCY, CustomEnchants.getEnchantLevel(pickaxe, "consistency"), playerData.getTokens()));
                menuItems.add(GUI.createLightGrayPlaceholderItem());
                menuItems.add(GUI.createLightGrayPlaceholderItem());
                menuItems.add(GUI.createLightGrayPlaceholderItem());
                menuItems.add(GUI.createLightGrayPlaceholderItem());
                menuItems.add(GUI.createDarkGrayPlaceholderItem());
                menuItems.add(GUIUtils.createEnchantGUIItem(identifier, Material.LAPIS_LAZULI, ChatColor.BLUE + "" + ChatColor.BOLD + PrisonEnchants.NIGHT_VISION.DISPLAY_NAME, PrisonEnchants.NIGHT_VISION, CustomEnchants.getEnchantLevel(pickaxe, "nightVision"), playerData.getTokens()));
                menuItems.add(GUI.createDarkGrayPlaceholderItem());
                menuItems.add(GUI.createDarkGrayPlaceholderItem());
                for (int i = 0; i < 8; i++) {
                    menuItems.add(GUI.createDarkGrayPlaceholderItem());
                }
                menuItems.add(GUI.createLightGrayPlaceholderItem());

                 */
    /*
            }
            @Override
            public void item10Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                String pickaxeUUID = PrisonEnchants.playerUUIDToPickaxeID.get(player.getUniqueId());
                ItemStack pickaxe = Utils.findPickaxeInInventoryFromUUID(player, pickaxeUUID);
                if (pickaxe == null) {
                    player.closeInventory();
                    return;
                }
                GUI.getGUIPage("enchantsFortune").open(player);
            }
            @Override
            public void item11Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                String pickaxeUUID = PrisonEnchants.playerUUIDToPickaxeID.get(player.getUniqueId());
                ItemStack pickaxe = Utils.findPickaxeInInventoryFromUUID(player, pickaxeUUID);
                if (pickaxe == null) {
                    player.closeInventory();
                    return;
                }
                GUI.getGUIPage("enchantsOreSplitter").open(player);
            }
            @Override
            public void item12Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                String pickaxeUUID = PrisonEnchants.playerUUIDToPickaxeID.get(player.getUniqueId());
                ItemStack pickaxe = Utils.findPickaxeInInventoryFromUUID(player, pickaxeUUID);
                if (pickaxe == null) {
                    player.closeInventory();
                    return;
                }
                GUI.getGUIPage("enchantsTokenator").open(player);
            }
            @Override
            public void item13Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                String pickaxeUUID = PrisonEnchants.playerUUIDToPickaxeID.get(player.getUniqueId());
                ItemStack pickaxe = Utils.findPickaxeInInventoryFromUUID(player, pickaxeUUID);
                if (pickaxe == null) {
                    player.closeInventory();
                    return;
                }
                GUI.getGUIPage("enchantsKeyFinder").open(player);
            }
            @Override
            public void item14Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                String pickaxeUUID = PrisonEnchants.playerUUIDToPickaxeID.get(player.getUniqueId());
                ItemStack pickaxe = Utils.findPickaxeInInventoryFromUUID(player, pickaxeUUID);
                if (pickaxe == null) {
                    player.closeInventory();
                    return;
                }
                GUI.getGUIPage("enchantsMetalDetector").open(player);
            }
            @Override
            public void item16Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                String pickaxeUUID = PrisonEnchants.playerUUIDToPickaxeID.get(player.getUniqueId());
                ItemStack pickaxe = Utils.findPickaxeInInventoryFromUUID(player, pickaxeUUID);
                if (pickaxe == null) {
                    player.closeInventory();
                    return;
                }
                GUI.getGUIPage("enchantsHaste").open(player);
            }
            @Override
            public void item19Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                String pickaxeUUID = PrisonEnchants.playerUUIDToPickaxeID.get(player.getUniqueId());
                ItemStack pickaxe = Utils.findPickaxeInInventoryFromUUID(player, pickaxeUUID);
                if (pickaxe == null) {
                    player.closeInventory();
                    return;
                }
                GUI.getGUIPage("enchantsExplosion").open(player);
            }
            @Override
            public void item20Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                String pickaxeUUID = PrisonEnchants.playerUUIDToPickaxeID.get(player.getUniqueId());
                ItemStack pickaxe = Utils.findPickaxeInInventoryFromUUID(player, pickaxeUUID);
                if (pickaxe == null) {
                    player.closeInventory();
                    return;
                }
                GUI.getGUIPage("enchantsJackhammer").open(player);
            }
            @Override
            public void item21Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                String pickaxeUUID = PrisonEnchants.playerUUIDToPickaxeID.get(player.getUniqueId());
                ItemStack pickaxe = Utils.findPickaxeInInventoryFromUUID(player, pickaxeUUID);
                if (pickaxe == null) {
                    player.closeInventory();
                    return;
                }
                GUI.getGUIPage("enchantsDoubleWammy").open(player);
            }
            @Override
            public void item22Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                String pickaxeUUID = PrisonEnchants.playerUUIDToPickaxeID.get(player.getUniqueId());
                ItemStack pickaxe = Utils.findPickaxeInInventoryFromUUID(player, pickaxeUUID);
                if (pickaxe == null) {
                    player.closeInventory();
                    return;
                }
                GUI.getGUIPage("enchantsMultiDirectional").open(player);
            }
            @Override
            public void item23Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                String pickaxeUUID = PrisonEnchants.playerUUIDToPickaxeID.get(player.getUniqueId());
                ItemStack pickaxe = Utils.findPickaxeInInventoryFromUUID(player, pickaxeUUID);
                if (pickaxe == null) {
                    player.closeInventory();
                    return;
                }
                GUI.getGUIPage("enchantsMerchant").open(player);
            }
            @Override
            public void item28Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                String pickaxeUUID = PrisonEnchants.playerUUIDToPickaxeID.get(player.getUniqueId());
                ItemStack pickaxe = Utils.findPickaxeInInventoryFromUUID(player, pickaxeUUID);
                if (pickaxe == null) {
                    player.closeInventory();
                    return;
                }
                GUI.getGUIPage("enchantsConsistency").open(player);
            }
            @Override
            public void item25Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                String pickaxeUUID = PrisonEnchants.playerUUIDToPickaxeID.get(player.getUniqueId());
                ItemStack pickaxe = Utils.findPickaxeInInventoryFromUUID(player, pickaxeUUID);
                if (pickaxe == null) {
                    player.closeInventory();
                    return;
                }
                GUI.getGUIPage("enchantsSpeed").open(player);
            }
            @Override
            public void item34Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                String pickaxeUUID = PrisonEnchants.playerUUIDToPickaxeID.get(player.getUniqueId());
                ItemStack pickaxe = Utils.findPickaxeInInventoryFromUUID(player, pickaxeUUID);
                if (pickaxe == null) {
                    player.closeInventory();
                    return;
                }
                GUI.getGUIPage("enchantsNightVision").open(player);
            }


        };
        guiPage.identifier = "enchantsMain";
        guiPage.guiTitle = ChatColor.translateAlternateColorCodes('&', "&dEnchants");
        guiPage.onCloseGoToMenu = "main";
        guiPage.register();
    }
    //Fortune menu
    public static void fortune() {
        GUIPage guiPage = new GUIPage() {
            @Override
            public void onOpen(Player player) {
                String pickaxeUUID = PrisonEnchants.playerUUIDToPickaxeID.get(player.getUniqueId());
                ItemStack pickaxe = Utils.findPickaxeInInventoryFromUUID(player, pickaxeUUID);
                if (pickaxe == null) {
                    player.closeInventory();
                    return;
                }
                PlayerData playerData = new PlayerData(player);
                menuItems = new ArrayList<>();
                menuItems.add(GUI.createLightGrayPlaceholderItem());
                menuItems.add(GUI.createLightGrayPlaceholderItem());
                menuItems.add(GUI.createMenuItem(identifier, Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "Buy 1 Level(s) Of Fortune", "",
                        ChatColor.AQUA + "Current Level: " + ChatColor.WHITE + Utils.prettyNum(CustomEnchants.getEnchantLevel(pickaxe, "fortune") + ""),
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.FORTUNE.PRICE),
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Level: " + Utils.addCommasToNumber(PrisonEnchants.FORTUNE.MAX_LEVEL)));
                menuItems.add(GUI.createMenuItem(identifier, Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "Buy 10 Level(s) Of Fortune", "",
                        ChatColor.AQUA + "Current Level: " + ChatColor.WHITE + Utils.prettyNum(CustomEnchants.getEnchantLevel(pickaxe, "fortune") + ""),
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.FORTUNE.PRICE.multiply(BigInteger.valueOf(10))),
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Level: " + Utils.addCommasToNumber(PrisonEnchants.FORTUNE.MAX_LEVEL)));
                menuItems.add(GUI.createMenuItem(identifier, Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "Buy 100 Level(s) Of Fortune", "",
                        ChatColor.AQUA + "Current Level: " + ChatColor.WHITE + Utils.prettyNum(CustomEnchants.getEnchantLevel(pickaxe, "fortune") + ""),
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.FORTUNE.PRICE.multiply(BigInteger.valueOf(100))),
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Level: " + Utils.addCommasToNumber(PrisonEnchants.FORTUNE.MAX_LEVEL)));
                menuItems.add(GUI.createMenuItem(identifier, Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "Buy 1,000 Level(s) Of Fortune", "",
                        ChatColor.AQUA + "Current Level: " + ChatColor.WHITE + Utils.prettyNum(CustomEnchants.getEnchantLevel(pickaxe, "fortune") + ""),
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.FORTUNE.PRICE.multiply(BigInteger.valueOf(1000))),
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Level: " + Utils.addCommasToNumber(PrisonEnchants.FORTUNE.MAX_LEVEL)));
                menuItems.add(GUI.createMenuItem(identifier, Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "Buy 10,000 Level(s) Of Fortune", "",
                        ChatColor.AQUA + "Current Level: " + ChatColor.WHITE + Utils.prettyNum(CustomEnchants.getEnchantLevel(pickaxe, "fortune") + ""),
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.FORTUNE.PRICE.multiply(BigInteger.valueOf(10000))),
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Level: " + Utils.addCommasToNumber(PrisonEnchants.FORTUNE.MAX_LEVEL)));
            }
            @Override
            public void item2Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                String pickaxeUUID = PrisonEnchants.playerUUIDToPickaxeID.get(player.getUniqueId());
                ItemStack pickaxe = Utils.findPickaxeInInventoryFromUUID(player, pickaxeUUID);
                if (pickaxe == null) {
                    player.closeInventory();
                    return;
                }
                if (PrisonEnchants.FORTUNE.tryToBuyLevels(player, pickaxe, 1)) open(player);
            }
            @Override
            public void item3Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                String pickaxeUUID = PrisonEnchants.playerUUIDToPickaxeID.get(player.getUniqueId());
                ItemStack pickaxe = Utils.findPickaxeInInventoryFromUUID(player, pickaxeUUID);
                if (pickaxe == null) {
                    player.closeInventory();
                    return;
                }
                if (PrisonEnchants.FORTUNE.tryToBuyLevels(player, pickaxe, 10)) open(player);
            }
            @Override
            public void item4Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                String pickaxeUUID = PrisonEnchants.playerUUIDToPickaxeID.get(player.getUniqueId());
                ItemStack pickaxe = Utils.findPickaxeInInventoryFromUUID(player, pickaxeUUID);
                if (pickaxe == null) {
                    player.closeInventory();
                    return;
                }
                if (PrisonEnchants.FORTUNE.tryToBuyLevels(player, pickaxe, 100)) open(player);
            }
            @Override
            public void item5Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                String pickaxeUUID = PrisonEnchants.playerUUIDToPickaxeID.get(player.getUniqueId());
                ItemStack pickaxe = Utils.findPickaxeInInventoryFromUUID(player, pickaxeUUID);
                if (pickaxe == null) {
                    player.closeInventory();
                    return;
                }
                if (PrisonEnchants.FORTUNE.tryToBuyLevels(player, pickaxe, 1000)) open(player);
            }
            @Override
            public void item6Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                String pickaxeUUID = PrisonEnchants.playerUUIDToPickaxeID.get(player.getUniqueId());
                ItemStack pickaxe = Utils.findPickaxeInInventoryFromUUID(player, pickaxeUUID);
                if (pickaxe == null) {
                    player.closeInventory();
                    return;
                }
                if (PrisonEnchants.FORTUNE.tryToBuyLevels(player, pickaxe, 10000)) open(player);
            }
        };
        guiPage.identifier = "enchantsFortune";
        guiPage.guiTitle = ChatColor.translateAlternateColorCodes('&', "&dUpgrade Fortune");
        guiPage.onCloseGoToMenu = "enchantsMain";
        guiPage.register();
    }
    //Jack Hammer menu
    public static void jackHammer() {
        GUIPage guiPage = new GUIPage() {
            @Override
            public void onOpen(Player player) {
                String pickaxeUUID = PrisonEnchants.playerUUIDToPickaxeID.get(player.getUniqueId());
                ItemStack pickaxe = Utils.findPickaxeInInventoryFromUUID(player, pickaxeUUID);
                if (pickaxe == null) {
                    player.closeInventory();
                    return;
                }
                PlayerData playerData = new PlayerData(player);
                menuItems = new ArrayList<>();
                menuItems.add(GUI.createLightGrayPlaceholderItem());
                menuItems.add(GUI.createLightGrayPlaceholderItem());
                menuItems.add(GUI.createMenuItem(identifier, Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "Buy 1 Level(s) Of Jack Hammer", "",
                        ChatColor.AQUA + "Current Level: " + ChatColor.WHITE + Utils.prettyNum(CustomEnchants.getEnchantLevel(pickaxe, "jackHammer") + ""),
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.JACK_HAMMER.PRICE),
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Level: " + Utils.addCommasToNumber(PrisonEnchants.JACK_HAMMER.MAX_LEVEL)));
                menuItems.add(GUI.createMenuItem(identifier, Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "Buy 10 Level(s) Of Jack Hammer", "",
                        ChatColor.AQUA + "Current Level: " + ChatColor.WHITE + Utils.prettyNum(CustomEnchants.getEnchantLevel(pickaxe, "jackHammer") + ""),
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.JACK_HAMMER.PRICE.multiply(BigInteger.valueOf(10))),
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Level: " + Utils.addCommasToNumber(PrisonEnchants.JACK_HAMMER.MAX_LEVEL)));
                menuItems.add(GUI.createMenuItem(identifier, Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "Buy 100 Level(s) Of Jack Hammer", "",
                        ChatColor.AQUA + "Current Level: " + ChatColor.WHITE + Utils.prettyNum(CustomEnchants.getEnchantLevel(pickaxe, "jackHammer") + ""),
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.JACK_HAMMER.PRICE.multiply(BigInteger.valueOf(100))),
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Level: " + Utils.addCommasToNumber(PrisonEnchants.JACK_HAMMER.MAX_LEVEL)));
                menuItems.add(GUI.createMenuItem(identifier, Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "Buy 1,000 Level(s) Of Jack Hammer", "",
                        ChatColor.AQUA + "Current Level: " + ChatColor.WHITE + Utils.prettyNum(CustomEnchants.getEnchantLevel(pickaxe, "jackHammer") + ""),
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.JACK_HAMMER.PRICE.multiply(BigInteger.valueOf(1000))),
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Level: " + Utils.addCommasToNumber(PrisonEnchants.JACK_HAMMER.MAX_LEVEL)));
                menuItems.add(GUI.createMenuItem(identifier, Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "Buy 10,000 Level(s) Of Jack Hammer", "",
                        ChatColor.AQUA + "Current Level: " + ChatColor.WHITE + Utils.prettyNum(CustomEnchants.getEnchantLevel(pickaxe, "jackHammer") + ""),
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.JACK_HAMMER.PRICE.multiply(BigInteger.valueOf(10000))),
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Level: " + Utils.addCommasToNumber(PrisonEnchants.JACK_HAMMER.MAX_LEVEL)));
            }
            @Override
            public void item2Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                String pickaxeUUID = PrisonEnchants.playerUUIDToPickaxeID.get(player.getUniqueId());
                ItemStack pickaxe = Utils.findPickaxeInInventoryFromUUID(player, pickaxeUUID);
                if (pickaxe == null) {
                    player.closeInventory();
                    return;
                }
                if (PrisonEnchants.JACK_HAMMER.tryToBuyLevels(player, pickaxe, 1)) open(player);
            }
            @Override
            public void item3Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                String pickaxeUUID = PrisonEnchants.playerUUIDToPickaxeID.get(player.getUniqueId());
                ItemStack pickaxe = Utils.findPickaxeInInventoryFromUUID(player, pickaxeUUID);
                if (pickaxe == null) {
                    player.closeInventory();
                    return;
                }
                if (PrisonEnchants.JACK_HAMMER.tryToBuyLevels(player, pickaxe, 10)) open(player);
            }
            @Override
            public void item4Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                String pickaxeUUID = PrisonEnchants.playerUUIDToPickaxeID.get(player.getUniqueId());
                ItemStack pickaxe = Utils.findPickaxeInInventoryFromUUID(player, pickaxeUUID);
                if (pickaxe == null) {
                    player.closeInventory();
                    return;
                }
                if (PrisonEnchants.JACK_HAMMER.tryToBuyLevels(player, pickaxe, 100)) open(player);
            }
            @Override
            public void item5Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                String pickaxeUUID = PrisonEnchants.playerUUIDToPickaxeID.get(player.getUniqueId());
                ItemStack pickaxe = Utils.findPickaxeInInventoryFromUUID(player, pickaxeUUID);
                if (pickaxe == null) {
                    player.closeInventory();
                    return;
                }
                if (PrisonEnchants.JACK_HAMMER.tryToBuyLevels(player, pickaxe, 1000)) open(player);
            }
            @Override
            public void item6Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                String pickaxeUUID = PrisonEnchants.playerUUIDToPickaxeID.get(player.getUniqueId());
                ItemStack pickaxe = Utils.findPickaxeInInventoryFromUUID(player, pickaxeUUID);
                if (pickaxe == null) {
                    player.closeInventory();
                    return;
                }
                if (PrisonEnchants.JACK_HAMMER.tryToBuyLevels(player, pickaxe, 10000)) open(player);
            }
        };
        guiPage.identifier = "enchantsJackhammer";
        guiPage.guiTitle = ChatColor.translateAlternateColorCodes('&', "&dUpgrade Jack Hammer");
        guiPage.onCloseGoToMenu = "enchantsMain";
        guiPage.register();
    }

    //Double Wammy menu
    public static void doubleWammy() {
        GUIPage guiPage = new GUIPage() {
            @Override
            public void onOpen(Player player) {
                String pickaxeUUID = PrisonEnchants.playerUUIDToPickaxeID.get(player.getUniqueId());
                ItemStack pickaxe = Utils.findPickaxeInInventoryFromUUID(player, pickaxeUUID);
                if (pickaxe == null) {
                    player.closeInventory();
                    return;
                }
                PlayerData playerData = new PlayerData(player);
                menuItems = new ArrayList<>();
                menuItems.add(GUI.createLightGrayPlaceholderItem());
                menuItems.add(GUI.createLightGrayPlaceholderItem());
                menuItems.add(GUI.createMenuItem(identifier, Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "Buy 1 Level(s) Of Double Wammy", "",
                        ChatColor.AQUA + "Current Level: " + ChatColor.WHITE + Utils.prettyNum(CustomEnchants.getEnchantLevel(pickaxe, "doubleWammy") + ""),
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.DOUBLE_JACK_HAMMER.PRICE),
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Level: " + Utils.addCommasToNumber(PrisonEnchants.DOUBLE_JACK_HAMMER.MAX_LEVEL)));
                menuItems.add(GUI.createMenuItem(identifier, Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "Buy 10 Level(s) Of Double Wammy", "",
                        ChatColor.AQUA + "Current Level: " + ChatColor.WHITE + Utils.prettyNum(CustomEnchants.getEnchantLevel(pickaxe, "doubleWammy") + ""),
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.DOUBLE_JACK_HAMMER.PRICE.multiply(BigInteger.valueOf(10))),
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Level: " + Utils.addCommasToNumber(PrisonEnchants.DOUBLE_JACK_HAMMER.MAX_LEVEL)));
                menuItems.add(GUI.createMenuItem(identifier, Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "Buy 100 Level(s) Of Double Wammy", "",
                        ChatColor.AQUA + "Current Level: " + ChatColor.WHITE + Utils.prettyNum(CustomEnchants.getEnchantLevel(pickaxe, "doubleWammy") + ""),
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.DOUBLE_JACK_HAMMER.PRICE.multiply(BigInteger.valueOf(100))),
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Level: " + Utils.addCommasToNumber(PrisonEnchants.DOUBLE_JACK_HAMMER.MAX_LEVEL)));
                menuItems.add(GUI.createMenuItem(identifier, Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "Buy 1,000 Level(s) Of Double Wammy", "",
                        ChatColor.AQUA + "Current Level: " + ChatColor.WHITE + Utils.prettyNum(CustomEnchants.getEnchantLevel(pickaxe, "doubleWammy") + ""),
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.DOUBLE_JACK_HAMMER.PRICE.multiply(BigInteger.valueOf(1000))),
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Level: " + Utils.addCommasToNumber(PrisonEnchants.DOUBLE_JACK_HAMMER.MAX_LEVEL)));
                menuItems.add(GUI.createMenuItem(identifier, Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "Buy 10,000 Level(s) Of Double Wammy", "",
                        ChatColor.AQUA + "Current Level: " + ChatColor.WHITE + Utils.prettyNum(CustomEnchants.getEnchantLevel(pickaxe, "doubleWammy") + ""),
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.DOUBLE_JACK_HAMMER.PRICE.multiply(BigInteger.valueOf(10000))),
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Level: " + Utils.addCommasToNumber(PrisonEnchants.DOUBLE_JACK_HAMMER.MAX_LEVEL)));
            }
            @Override
            public void item2Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                String pickaxeUUID = PrisonEnchants.playerUUIDToPickaxeID.get(player.getUniqueId());
                ItemStack pickaxe = Utils.findPickaxeInInventoryFromUUID(player, pickaxeUUID);
                if (pickaxe == null) {
                    player.closeInventory();
                    return;
                }
                if (PrisonEnchants.DOUBLE_JACK_HAMMER.tryToBuyLevels(player, pickaxe, 1)) open(player);
            }
            @Override
            public void item3Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                String pickaxeUUID = PrisonEnchants.playerUUIDToPickaxeID.get(player.getUniqueId());
                ItemStack pickaxe = Utils.findPickaxeInInventoryFromUUID(player, pickaxeUUID);
                if (pickaxe == null) {
                    player.closeInventory();
                    return;
                }
                if (PrisonEnchants.DOUBLE_JACK_HAMMER.tryToBuyLevels(player, pickaxe, 10)) open(player);
            }
            @Override
            public void item4Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                String pickaxeUUID = PrisonEnchants.playerUUIDToPickaxeID.get(player.getUniqueId());
                ItemStack pickaxe = Utils.findPickaxeInInventoryFromUUID(player, pickaxeUUID);
                if (pickaxe == null) {
                    player.closeInventory();
                    return;
                }
                if (PrisonEnchants.DOUBLE_JACK_HAMMER.tryToBuyLevels(player, pickaxe, 100)) open(player);
            }
            @Override
            public void item5Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                String pickaxeUUID = PrisonEnchants.playerUUIDToPickaxeID.get(player.getUniqueId());
                ItemStack pickaxe = Utils.findPickaxeInInventoryFromUUID(player, pickaxeUUID);
                if (pickaxe == null) {
                    player.closeInventory();
                    return;
                }
                if (PrisonEnchants.DOUBLE_JACK_HAMMER.tryToBuyLevels(player, pickaxe, 1000)) open(player);
            }
            @Override
            public void item6Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                String pickaxeUUID = PrisonEnchants.playerUUIDToPickaxeID.get(player.getUniqueId());
                ItemStack pickaxe = Utils.findPickaxeInInventoryFromUUID(player, pickaxeUUID);
                if (pickaxe == null) {
                    player.closeInventory();
                    return;
                }
                if (PrisonEnchants.DOUBLE_JACK_HAMMER.tryToBuyLevels(player, pickaxe, 10000)) open(player);
            }
        };
        guiPage.identifier = "enchantsDoubleWammy";
        guiPage.guiTitle = ChatColor.translateAlternateColorCodes('&', "&dUpgrade Double Wammy");
        guiPage.onCloseGoToMenu = "enchantsMain";
        guiPage.register();
    }

    //MultiDirectional menu
    public static void multiDirectional() {
        GUIPage guiPage = new GUIPage() {
            @Override
            public void onOpen(Player player) {
                String pickaxeUUID = PrisonEnchants.playerUUIDToPickaxeID.get(player.getUniqueId());
                ItemStack pickaxe = Utils.findPickaxeInInventoryFromUUID(player, pickaxeUUID);
                if (pickaxe == null) {
                    player.closeInventory();
                    return;
                }
                PlayerData playerData = new PlayerData(player);
                menuItems = new ArrayList<>();
                menuItems.add(GUI.createLightGrayPlaceholderItem());
                menuItems.add(GUI.createLightGrayPlaceholderItem());
                menuItems.add(GUI.createMenuItem(identifier, Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "Buy 1 Level(s) Of Multi-Directional", "",
                        ChatColor.AQUA + "Current Level: " + ChatColor.WHITE + Utils.prettyNum(CustomEnchants.getEnchantLevel(pickaxe, "multiDirectional") + ""),
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.MULTI_DIRECTIONAL.PRICE),
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Level: " + Utils.addCommasToNumber(PrisonEnchants.MULTI_DIRECTIONAL.MAX_LEVEL)));
                menuItems.add(GUI.createMenuItem(identifier, Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "Buy 10 Level(s) Of Multi-Directional", "",
                        ChatColor.AQUA + "Current Level: " + ChatColor.WHITE + Utils.prettyNum(CustomEnchants.getEnchantLevel(pickaxe, "multiDirectional") + ""),
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.MULTI_DIRECTIONAL.PRICE.multiply(BigInteger.valueOf(10))),
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Level: " + Utils.addCommasToNumber(PrisonEnchants.MULTI_DIRECTIONAL.MAX_LEVEL)));
                menuItems.add(GUI.createMenuItem(identifier, Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "Buy 100 Level(s) Of Multi-Directional", "",
                        ChatColor.AQUA + "Current Level: " + ChatColor.WHITE + Utils.prettyNum(CustomEnchants.getEnchantLevel(pickaxe, "multiDirectional") + ""),
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.MULTI_DIRECTIONAL.PRICE.multiply(BigInteger.valueOf(100))),
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Level: " + Utils.addCommasToNumber(PrisonEnchants.MULTI_DIRECTIONAL.MAX_LEVEL)));
                menuItems.add(GUI.createMenuItem(identifier, Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "Buy 1,000 Level(s) Of Multi-Directional", "",
                        ChatColor.AQUA + "Current Level: " + ChatColor.WHITE + Utils.prettyNum(CustomEnchants.getEnchantLevel(pickaxe, "multiDirectional") + ""),
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.MULTI_DIRECTIONAL.PRICE.multiply(BigInteger.valueOf(1000))),
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Level: " + Utils.addCommasToNumber(PrisonEnchants.MULTI_DIRECTIONAL.MAX_LEVEL)));
                menuItems.add(GUI.createMenuItem(identifier, Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "Buy 10,000 Level(s) Of Multi-Directional", "",
                        ChatColor.AQUA + "Current Level: " + ChatColor.WHITE + Utils.prettyNum(CustomEnchants.getEnchantLevel(pickaxe, "multiDirectional") + ""),
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.MULTI_DIRECTIONAL.PRICE.multiply(BigInteger.valueOf(10000))),
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Level: " + Utils.addCommasToNumber(PrisonEnchants.MULTI_DIRECTIONAL.MAX_LEVEL)));
            }
            @Override
            public void item2Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                String pickaxeUUID = PrisonEnchants.playerUUIDToPickaxeID.get(player.getUniqueId());
                ItemStack pickaxe = Utils.findPickaxeInInventoryFromUUID(player, pickaxeUUID);
                if (pickaxe == null) {
                    player.closeInventory();
                    return;
                }
                if (PrisonEnchants.MULTI_DIRECTIONAL.tryToBuyLevels(player, pickaxe, 1)) open(player);
            }
            @Override
            public void item3Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                String pickaxeUUID = PrisonEnchants.playerUUIDToPickaxeID.get(player.getUniqueId());
                ItemStack pickaxe = Utils.findPickaxeInInventoryFromUUID(player, pickaxeUUID);
                if (pickaxe == null) {
                    player.closeInventory();
                    return;
                }
                if (PrisonEnchants.MULTI_DIRECTIONAL.tryToBuyLevels(player, pickaxe, 10)) open(player);
            }
            @Override
            public void item4Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                String pickaxeUUID = PrisonEnchants.playerUUIDToPickaxeID.get(player.getUniqueId());
                ItemStack pickaxe = Utils.findPickaxeInInventoryFromUUID(player, pickaxeUUID);
                if (pickaxe == null) {
                    player.closeInventory();
                    return;
                }
                if (PrisonEnchants.MULTI_DIRECTIONAL.tryToBuyLevels(player, pickaxe, 100)) open(player);
            }
            @Override
            public void item5Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                String pickaxeUUID = PrisonEnchants.playerUUIDToPickaxeID.get(player.getUniqueId());
                ItemStack pickaxe = Utils.findPickaxeInInventoryFromUUID(player, pickaxeUUID);
                if (pickaxe == null) {
                    player.closeInventory();
                    return;
                }
                if (PrisonEnchants.MULTI_DIRECTIONAL.tryToBuyLevels(player, pickaxe, 1000)) open(player);
            }
            @Override
            public void item6Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                String pickaxeUUID = PrisonEnchants.playerUUIDToPickaxeID.get(player.getUniqueId());
                ItemStack pickaxe = Utils.findPickaxeInInventoryFromUUID(player, pickaxeUUID);
                if (pickaxe == null) {
                    player.closeInventory();
                    return;
                }
                if (PrisonEnchants.MULTI_DIRECTIONAL.tryToBuyLevels(player, pickaxe, 10000)) open(player);
            }
        };
        guiPage.identifier = "enchantsMultiDirectional";
        guiPage.guiTitle = ChatColor.translateAlternateColorCodes('&', "&dUpgrade Multi-Directional");
        guiPage.onCloseGoToMenu = "enchantsMain";
        guiPage.register();
    }

    //Tokenator menu
    public static void tokenator() {
        GUIPage guiPage = new GUIPage() {
            @Override
            public void onOpen(Player player) {
                String pickaxeUUID = PrisonEnchants.playerUUIDToPickaxeID.get(player.getUniqueId());
                ItemStack pickaxe = Utils.findPickaxeInInventoryFromUUID(player, pickaxeUUID);
                if (pickaxe == null) {
                    player.closeInventory();
                    return;
                }
                PlayerData playerData = new PlayerData(player);
                menuItems = new ArrayList<>();
                menuItems.add(GUI.createLightGrayPlaceholderItem());
                menuItems.add(GUI.createLightGrayPlaceholderItem());
                menuItems.add(GUI.createMenuItem(identifier, Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "Buy 1 Level(s) Of Tokenator", "",
                        ChatColor.AQUA + "Current Level: " + ChatColor.WHITE + Utils.prettyNum(CustomEnchants.getEnchantLevel(pickaxe, "tokenator") + ""),
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.TOKENATOR.PRICE),
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Level: " + Utils.addCommasToNumber(PrisonEnchants.TOKENATOR.MAX_LEVEL)));
                menuItems.add(GUI.createMenuItem(identifier, Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "Buy 10 Level(s) Of Tokenator", "",
                        ChatColor.AQUA + "Current Level: " + ChatColor.WHITE + Utils.prettyNum(CustomEnchants.getEnchantLevel(pickaxe, "tokenator") + ""),
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.TOKENATOR.PRICE.multiply(BigInteger.valueOf(10))),
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Level: " + Utils.addCommasToNumber(PrisonEnchants.TOKENATOR.MAX_LEVEL)));
                menuItems.add(GUI.createMenuItem(identifier, Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "Buy 100 Level(s) Of Tokenator", "",
                        ChatColor.AQUA + "Current Level: " + ChatColor.WHITE + Utils.prettyNum(CustomEnchants.getEnchantLevel(pickaxe, "tokenator") + ""),
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.TOKENATOR.PRICE.multiply(BigInteger.valueOf(100))),
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Level: " + Utils.addCommasToNumber(PrisonEnchants.TOKENATOR.MAX_LEVEL)));
                menuItems.add(GUI.createMenuItem(identifier, Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "Buy 1,000 Level(s) Of Tokenator", "",
                        ChatColor.AQUA + "Current Level: " + ChatColor.WHITE + Utils.prettyNum(CustomEnchants.getEnchantLevel(pickaxe, "tokenator") + ""),
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.TOKENATOR.PRICE.multiply(BigInteger.valueOf(1000))),
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Level: " + Utils.addCommasToNumber(PrisonEnchants.TOKENATOR.MAX_LEVEL)));
                menuItems.add(GUI.createMenuItem(identifier, Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "Buy 10,000 Level(s) Of Tokenator", "",
                        ChatColor.AQUA + "Current Level: " + ChatColor.WHITE + Utils.prettyNum(CustomEnchants.getEnchantLevel(pickaxe, "tokenator") + ""),
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.TOKENATOR.PRICE.multiply(BigInteger.valueOf(10000))),
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Level: " + Utils.addCommasToNumber(PrisonEnchants.TOKENATOR.MAX_LEVEL)));
            }
            @Override
            public void item2Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                String pickaxeUUID = PrisonEnchants.playerUUIDToPickaxeID.get(player.getUniqueId());
                ItemStack pickaxe = Utils.findPickaxeInInventoryFromUUID(player, pickaxeUUID);
                if (pickaxe == null) {
                    player.closeInventory();
                    return;
                }
                if (PrisonEnchants.TOKENATOR.tryToBuyLevels(player, pickaxe, 1)) open(player);
            }
            @Override
            public void item3Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                String pickaxeUUID = PrisonEnchants.playerUUIDToPickaxeID.get(player.getUniqueId());
                ItemStack pickaxe = Utils.findPickaxeInInventoryFromUUID(player, pickaxeUUID);
                if (pickaxe == null) {
                    player.closeInventory();
                    return;
                }
                if (PrisonEnchants.TOKENATOR.tryToBuyLevels(player, pickaxe, 10)) open(player);
            }
            @Override
            public void item4Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                String pickaxeUUID = PrisonEnchants.playerUUIDToPickaxeID.get(player.getUniqueId());
                ItemStack pickaxe = Utils.findPickaxeInInventoryFromUUID(player, pickaxeUUID);
                if (pickaxe == null) {
                    player.closeInventory();
                    return;
                }
                if (PrisonEnchants.TOKENATOR.tryToBuyLevels(player, pickaxe, 100)) open(player);
            }
            @Override
            public void item5Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                String pickaxeUUID = PrisonEnchants.playerUUIDToPickaxeID.get(player.getUniqueId());
                ItemStack pickaxe = Utils.findPickaxeInInventoryFromUUID(player, pickaxeUUID);
                if (pickaxe == null) {
                    player.closeInventory();
                    return;
                }
                if (PrisonEnchants.TOKENATOR.tryToBuyLevels(player, pickaxe, 1000)) open(player);
            }
            @Override
            public void item6Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                String pickaxeUUID = PrisonEnchants.playerUUIDToPickaxeID.get(player.getUniqueId());
                ItemStack pickaxe = Utils.findPickaxeInInventoryFromUUID(player, pickaxeUUID);
                if (pickaxe == null) {
                    player.closeInventory();
                    return;
                }
                if (PrisonEnchants.TOKENATOR.tryToBuyLevels(player, pickaxe, 10000)) open(player);
            }
        };
        guiPage.identifier = "enchantsTokenator";
        guiPage.guiTitle = ChatColor.translateAlternateColorCodes('&', "&dUpgrade Tokenator");
        guiPage.onCloseGoToMenu = "enchantsMain";
        guiPage.register();
    }

    //Merchant menu
    public static void merchant() {
        GUIPage guiPage = new GUIPage() {
            @Override
            public void onOpen(Player player) {
                String pickaxeUUID = PrisonEnchants.playerUUIDToPickaxeID.get(player.getUniqueId());
                ItemStack pickaxe = Utils.findPickaxeInInventoryFromUUID(player, pickaxeUUID);
                if (pickaxe == null) {
                    player.closeInventory();
                    return;
                }
                PlayerData playerData = new PlayerData(player);
                menuItems = new ArrayList<>();
                menuItems.add(GUI.createLightGrayPlaceholderItem());
                menuItems.add(GUI.createLightGrayPlaceholderItem());
                menuItems.add(GUI.createMenuItem(identifier, Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "Buy 1 Level(s) Of Merchant", "",
                        ChatColor.AQUA + "Current Level: " + ChatColor.WHITE + Utils.prettyNum(CustomEnchants.getEnchantLevel(pickaxe, "merchant") + ""),
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.MERCHANT.PRICE),
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Level: " + Utils.addCommasToNumber(PrisonEnchants.MERCHANT.MAX_LEVEL)));
                menuItems.add(GUI.createMenuItem(identifier, Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "Buy 10 Level(s) Of Merchant", "",
                        ChatColor.AQUA + "Current Level: " + ChatColor.WHITE + Utils.prettyNum(CustomEnchants.getEnchantLevel(pickaxe, "merchant") + ""),
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.MERCHANT.PRICE.multiply(BigInteger.valueOf(10))),
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Level: " + Utils.addCommasToNumber(PrisonEnchants.MERCHANT.MAX_LEVEL)));
                menuItems.add(GUI.createMenuItem(identifier, Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "Buy 100 Level(s) Of Merchant", "",
                        ChatColor.AQUA + "Current Level: " + ChatColor.WHITE + Utils.prettyNum(CustomEnchants.getEnchantLevel(pickaxe, "merchant") + ""),
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.MERCHANT.PRICE.multiply(BigInteger.valueOf(100))),
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Level: " + Utils.addCommasToNumber(PrisonEnchants.MERCHANT.MAX_LEVEL)));
                menuItems.add(GUI.createMenuItem(identifier, Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "Buy 1,000 Level(s) Of Merchant", "",
                        ChatColor.AQUA + "Current Level: " + ChatColor.WHITE + Utils.prettyNum(CustomEnchants.getEnchantLevel(pickaxe, "merchant") + ""),
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.MERCHANT.PRICE.multiply(BigInteger.valueOf(1000))),
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Level: " + Utils.addCommasToNumber(PrisonEnchants.MERCHANT.MAX_LEVEL)));
                menuItems.add(GUI.createMenuItem(identifier, Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "Buy 10,000 Level(s) Of Merchant", "",
                        ChatColor.AQUA + "Current Level: " + ChatColor.WHITE + Utils.prettyNum(CustomEnchants.getEnchantLevel(pickaxe, "merchant") + ""),
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.MERCHANT.PRICE.multiply(BigInteger.valueOf(10000))),
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Level: " + Utils.addCommasToNumber(PrisonEnchants.MERCHANT.MAX_LEVEL)));
            }
            @Override
            public void item2Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                String pickaxeUUID = PrisonEnchants.playerUUIDToPickaxeID.get(player.getUniqueId());
                ItemStack pickaxe = Utils.findPickaxeInInventoryFromUUID(player, pickaxeUUID);
                if (pickaxe == null) {
                    player.closeInventory();
                    return;
                }
                if (PrisonEnchants.MERCHANT.tryToBuyLevels(player, pickaxe, 1)) open(player);
            }
            @Override
            public void item3Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                String pickaxeUUID = PrisonEnchants.playerUUIDToPickaxeID.get(player.getUniqueId());
                ItemStack pickaxe = Utils.findPickaxeInInventoryFromUUID(player, pickaxeUUID);
                if (pickaxe == null) {
                    player.closeInventory();
                    return;
                }
                if (PrisonEnchants.MERCHANT.tryToBuyLevels(player, pickaxe, 10)) open(player);
            }
            @Override
            public void item4Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                String pickaxeUUID = PrisonEnchants.playerUUIDToPickaxeID.get(player.getUniqueId());
                ItemStack pickaxe = Utils.findPickaxeInInventoryFromUUID(player, pickaxeUUID);
                if (pickaxe == null) {
                    player.closeInventory();
                    return;
                }
                if (PrisonEnchants.MERCHANT.tryToBuyLevels(player, pickaxe, 100)) open(player);
            }
            @Override
            public void item5Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                String pickaxeUUID = PrisonEnchants.playerUUIDToPickaxeID.get(player.getUniqueId());
                ItemStack pickaxe = Utils.findPickaxeInInventoryFromUUID(player, pickaxeUUID);
                if (pickaxe == null) {
                    player.closeInventory();
                    return;
                }
                if (PrisonEnchants.MERCHANT.tryToBuyLevels(player, pickaxe, 1000)) open(player);
            }
            @Override
            public void item6Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                String pickaxeUUID = PrisonEnchants.playerUUIDToPickaxeID.get(player.getUniqueId());
                ItemStack pickaxe = Utils.findPickaxeInInventoryFromUUID(player, pickaxeUUID);
                if (pickaxe == null) {
                    player.closeInventory();
                    return;
                }
                if (PrisonEnchants.MERCHANT.tryToBuyLevels(player, pickaxe, 10000)) open(player);
            }
        };
        guiPage.identifier = "enchantsMerchant";
        guiPage.guiTitle = ChatColor.translateAlternateColorCodes('&', "&dUpgrade Merchant");
        guiPage.onCloseGoToMenu = "enchantsMain";
        guiPage.register();
    }
    //Ore Splitter menu
    public static void oreSplitter() {
        GUIPage guiPage = new GUIPage() {
            @Override
            public void onOpen(Player player) {
                String pickaxeUUID = PrisonEnchants.playerUUIDToPickaxeID.get(player.getUniqueId());
                ItemStack pickaxe = Utils.findPickaxeInInventoryFromUUID(player, pickaxeUUID);
                if (pickaxe == null) {
                    player.closeInventory();
                    return;
                }
                PlayerData playerData = new PlayerData(player);
                menuItems = new ArrayList<>();
                menuItems.add(GUI.createLightGrayPlaceholderItem());
                menuItems.add(GUI.createLightGrayPlaceholderItem());
                menuItems.add(GUI.createMenuItem(identifier, Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "Buy 1 Level(s) Of Ore Splitter", "",
                        ChatColor.AQUA + "Current Level: " + ChatColor.WHITE + Utils.prettyNum(CustomEnchants.getEnchantLevel(pickaxe, "oreSplitter") + ""),
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.ORE_SPLITTER.PRICE),
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Level: " + Utils.addCommasToNumber(PrisonEnchants.ORE_SPLITTER.MAX_LEVEL)));
                menuItems.add(GUI.createMenuItem(identifier, Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "Buy 10 Level(s) Of Ore Splitter", "",
                        ChatColor.AQUA + "Current Level: " + ChatColor.WHITE + Utils.prettyNum(CustomEnchants.getEnchantLevel(pickaxe, "oreSplitter") + ""),
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.ORE_SPLITTER.PRICE.multiply(BigInteger.valueOf(10))),
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Level: " + Utils.addCommasToNumber(PrisonEnchants.ORE_SPLITTER.MAX_LEVEL)));
                menuItems.add(GUI.createMenuItem(identifier, Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "Buy 100 Level(s) Of Ore Splitter", "",
                        ChatColor.AQUA + "Current Level: " + ChatColor.WHITE + Utils.prettyNum(CustomEnchants.getEnchantLevel(pickaxe, "oreSplitter") + ""),
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.ORE_SPLITTER.PRICE.multiply(BigInteger.valueOf(100))),
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Level: " + Utils.addCommasToNumber(PrisonEnchants.ORE_SPLITTER.MAX_LEVEL)));
                menuItems.add(GUI.createMenuItem(identifier, Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "Buy 1,000 Level(s) Of Ore Splitter", "",
                        ChatColor.AQUA + "Current Level: " + ChatColor.WHITE + Utils.prettyNum(CustomEnchants.getEnchantLevel(pickaxe, "oreSplitter") + ""),
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.ORE_SPLITTER.PRICE.multiply(BigInteger.valueOf(1000))),
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Level: " + Utils.addCommasToNumber(PrisonEnchants.ORE_SPLITTER.MAX_LEVEL)));
                menuItems.add(GUI.createMenuItem(identifier, Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "Buy 10,000 Level(s) Of Ore Splitter", "",
                        ChatColor.AQUA + "Current Level: " + ChatColor.WHITE + Utils.prettyNum(CustomEnchants.getEnchantLevel(pickaxe, "oreSplitter") + ""),
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.ORE_SPLITTER.PRICE.multiply(BigInteger.valueOf(10000))),
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Level: " + Utils.addCommasToNumber(PrisonEnchants.ORE_SPLITTER.MAX_LEVEL)));
            }
            @Override
            public void item2Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                String pickaxeUUID = PrisonEnchants.playerUUIDToPickaxeID.get(player.getUniqueId());
                ItemStack pickaxe = Utils.findPickaxeInInventoryFromUUID(player, pickaxeUUID);
                if (pickaxe == null) {
                    player.closeInventory();
                    return;
                }
                if (PrisonEnchants.ORE_SPLITTER.tryToBuyLevels(player, pickaxe, 1)) open(player);
            }
            @Override
            public void item3Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                String pickaxeUUID = PrisonEnchants.playerUUIDToPickaxeID.get(player.getUniqueId());
                ItemStack pickaxe = Utils.findPickaxeInInventoryFromUUID(player, pickaxeUUID);
                if (pickaxe == null) {
                    player.closeInventory();
                    return;
                }
                if (PrisonEnchants.ORE_SPLITTER.tryToBuyLevels(player, pickaxe, 10)) open(player);
            }
            @Override
            public void item4Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                String pickaxeUUID = PrisonEnchants.playerUUIDToPickaxeID.get(player.getUniqueId());
                ItemStack pickaxe = Utils.findPickaxeInInventoryFromUUID(player, pickaxeUUID);
                if (pickaxe == null) {
                    player.closeInventory();
                    return;
                }
                if (PrisonEnchants.ORE_SPLITTER.tryToBuyLevels(player, pickaxe, 100)) open(player);
            }
            @Override
            public void item5Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                String pickaxeUUID = PrisonEnchants.playerUUIDToPickaxeID.get(player.getUniqueId());
                ItemStack pickaxe = Utils.findPickaxeInInventoryFromUUID(player, pickaxeUUID);
                if (pickaxe == null) {
                    player.closeInventory();
                    return;
                }
                if (PrisonEnchants.ORE_SPLITTER.tryToBuyLevels(player, pickaxe, 1000)) open(player);
            }
            @Override
            public void item6Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                String pickaxeUUID = PrisonEnchants.playerUUIDToPickaxeID.get(player.getUniqueId());
                ItemStack pickaxe = Utils.findPickaxeInInventoryFromUUID(player, pickaxeUUID);
                if (pickaxe == null) {
                    player.closeInventory();
                    return;
                }
                if (PrisonEnchants.ORE_SPLITTER.tryToBuyLevels(player, pickaxe, 10000)) open(player);
            }
        };
        guiPage.identifier = "enchantsOreSplitter";
        guiPage.guiTitle = ChatColor.translateAlternateColorCodes('&', "&dUpgrade Ore Splitter");
        guiPage.onCloseGoToMenu = "enchantsMain";
        guiPage.register();
    }
    //Key finder menu
    public static void keyFinder() {
        GUIPage guiPage = new GUIPage() {
            @Override
            public void onOpen(Player player) {
                String pickaxeUUID = PrisonEnchants.playerUUIDToPickaxeID.get(player.getUniqueId());
                ItemStack pickaxe = Utils.findPickaxeInInventoryFromUUID(player, pickaxeUUID);
                if (pickaxe == null) {
                    player.closeInventory();
                    return;
                }
                PlayerData playerData = new PlayerData(player);
                menuItems = new ArrayList<>();
                menuItems.add(GUI.createLightGrayPlaceholderItem());
                menuItems.add(GUI.createLightGrayPlaceholderItem());
                menuItems.add(GUI.createMenuItem(identifier, Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "Buy 1 Level(s) Of Key Finder", "",
                        ChatColor.AQUA + "Current Level: " + ChatColor.WHITE + Utils.prettyNum(CustomEnchants.getEnchantLevel(pickaxe, "keyFinder") + ""),
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.KEY_FINDER.PRICE),
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Level: " + Utils.addCommasToNumber(PrisonEnchants.KEY_FINDER.MAX_LEVEL)));
                menuItems.add(GUI.createMenuItem(identifier, Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "Buy 10 Level(s) Of Key Finder", "",
                        ChatColor.AQUA + "Current Level: " + ChatColor.WHITE + Utils.prettyNum(CustomEnchants.getEnchantLevel(pickaxe, "keyFinder") + ""),
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.KEY_FINDER.PRICE.multiply(BigInteger.valueOf(10))),
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Level: " + Utils.addCommasToNumber(PrisonEnchants.KEY_FINDER.MAX_LEVEL)));
                menuItems.add(GUI.createMenuItem(identifier, Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "Buy 100 Level(s) Of Key Finder", "",
                        ChatColor.AQUA + "Current Level: " + ChatColor.WHITE + Utils.prettyNum(CustomEnchants.getEnchantLevel(pickaxe, "keyFinder") + ""),
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.KEY_FINDER.PRICE.multiply(BigInteger.valueOf(100))),
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Level: " + Utils.addCommasToNumber(PrisonEnchants.KEY_FINDER.MAX_LEVEL)));
                menuItems.add(GUI.createMenuItem(identifier, Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "Buy 1,000 Level(s) Of Key Finder", "",
                        ChatColor.AQUA + "Current Level: " + ChatColor.WHITE + Utils.prettyNum(CustomEnchants.getEnchantLevel(pickaxe, "keyFinder") + ""),
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.KEY_FINDER.PRICE.multiply(BigInteger.valueOf(1000))),
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Level: " + Utils.addCommasToNumber(PrisonEnchants.KEY_FINDER.MAX_LEVEL)));
                menuItems.add(GUI.createMenuItem(identifier, Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "Buy 10,000 Level(s) Of Key Finder", "",
                        ChatColor.AQUA + "Current Level: " + ChatColor.WHITE + Utils.prettyNum(CustomEnchants.getEnchantLevel(pickaxe, "keyFinder") + ""),
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.KEY_FINDER.PRICE.multiply(BigInteger.valueOf(10000))),
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Level: " + Utils.addCommasToNumber(PrisonEnchants.KEY_FINDER.MAX_LEVEL)));
            }
            @Override
            public void item2Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                String pickaxeUUID = PrisonEnchants.playerUUIDToPickaxeID.get(player.getUniqueId());
                ItemStack pickaxe = Utils.findPickaxeInInventoryFromUUID(player, pickaxeUUID);
                if (pickaxe == null) {
                    player.closeInventory();
                    return;
                }
                if (PrisonEnchants.KEY_FINDER.tryToBuyLevels(player, pickaxe, 1)) open(player);
            }
            @Override
            public void item3Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                String pickaxeUUID = PrisonEnchants.playerUUIDToPickaxeID.get(player.getUniqueId());
                ItemStack pickaxe = Utils.findPickaxeInInventoryFromUUID(player, pickaxeUUID);
                if (pickaxe == null) {
                    player.closeInventory();
                    return;
                }
                if (PrisonEnchants.KEY_FINDER.tryToBuyLevels(player, pickaxe, 10)) open(player);
            }
            @Override
            public void item4Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                String pickaxeUUID = PrisonEnchants.playerUUIDToPickaxeID.get(player.getUniqueId());
                ItemStack pickaxe = Utils.findPickaxeInInventoryFromUUID(player, pickaxeUUID);
                if (pickaxe == null) {
                    player.closeInventory();
                    return;
                }
                if (PrisonEnchants.KEY_FINDER.tryToBuyLevels(player, pickaxe, 100)) open(player);
            }
            @Override
            public void item5Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                String pickaxeUUID = PrisonEnchants.playerUUIDToPickaxeID.get(player.getUniqueId());
                ItemStack pickaxe = Utils.findPickaxeInInventoryFromUUID(player, pickaxeUUID);
                if (pickaxe == null) {
                    player.closeInventory();
                    return;
                }
                if (PrisonEnchants.KEY_FINDER.tryToBuyLevels(player, pickaxe, 1000)) open(player);
            }
            @Override
            public void item6Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                String pickaxeUUID = PrisonEnchants.playerUUIDToPickaxeID.get(player.getUniqueId());
                ItemStack pickaxe = Utils.findPickaxeInInventoryFromUUID(player, pickaxeUUID);
                if (pickaxe == null) {
                    player.closeInventory();
                    return;
                }
                if (PrisonEnchants.KEY_FINDER.tryToBuyLevels(player, pickaxe, 10000)) open(player);
            }
        };
        guiPage.identifier = "enchantsKeyFinder";
        guiPage.guiTitle = ChatColor.translateAlternateColorCodes('&', "&dUpgrade Key Finder");
        guiPage.onCloseGoToMenu = "enchantsMain";
        guiPage.register();
    }
    //xp menu
    public static void xp() {
        GUIPage guiPage = new GUIPage() {
            @Override
            public void onOpen(Player player) {
                String pickaxeUUID = PrisonEnchants.playerUUIDToPickaxeID.get(player.getUniqueId());
                ItemStack pickaxe = Utils.findPickaxeInInventoryFromUUID(player, pickaxeUUID);
                if (pickaxe == null) {
                    player.closeInventory();
                    return;
                }
                PlayerData playerData = new PlayerData(player);
                menuItems = new ArrayList<>();
                menuItems.add(GUI.createLightGrayPlaceholderItem());
                menuItems.add(GUI.createLightGrayPlaceholderItem());
                menuItems.add(GUI.createMenuItem(identifier, Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "Buy 1 Level(s) Of Metal Detector", "",
                        ChatColor.AQUA + "Current Level: " + ChatColor.WHITE + Utils.prettyNum(CustomEnchants.getEnchantLevel(pickaxe, "metalDetector") + ""),
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.METAL_DETECTOR.PRICE),
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Level: " + Utils.addCommasToNumber(PrisonEnchants.METAL_DETECTOR.MAX_LEVEL)));
                menuItems.add(GUI.createMenuItem(identifier, Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "Buy 10 Level(s) Of Metal Detector", "",
                        ChatColor.AQUA + "Current Level: " + ChatColor.WHITE + Utils.prettyNum(CustomEnchants.getEnchantLevel(pickaxe, "metalDetector") + ""),
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.METAL_DETECTOR.PRICE.multiply(BigInteger.valueOf(10))),
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Level: " + Utils.addCommasToNumber(PrisonEnchants.METAL_DETECTOR.MAX_LEVEL)));
                menuItems.add(GUI.createMenuItem(identifier, Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "Buy 100 Level(s) Of Metal Detector", "",
                        ChatColor.AQUA + "Current Level: " + ChatColor.WHITE + Utils.prettyNum(CustomEnchants.getEnchantLevel(pickaxe, "metalDetector") + ""),
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.METAL_DETECTOR.PRICE.multiply(BigInteger.valueOf(100))),
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Level: " + Utils.addCommasToNumber(PrisonEnchants.METAL_DETECTOR.MAX_LEVEL)));
                menuItems.add(GUI.createMenuItem(identifier, Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "Buy 1,000 Level(s) Of Metal Detector", "",
                        ChatColor.AQUA + "Current Level: " + ChatColor.WHITE + Utils.prettyNum(CustomEnchants.getEnchantLevel(pickaxe, "metalDetector") + ""),
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.METAL_DETECTOR.PRICE.multiply(BigInteger.valueOf(1000))),
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Level: " + Utils.addCommasToNumber(PrisonEnchants.METAL_DETECTOR.MAX_LEVEL)));
                menuItems.add(GUI.createMenuItem(identifier, Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "Buy 10,000 Level(s) Of Metal Detector", "",
                        ChatColor.AQUA + "Current Level: " + ChatColor.WHITE + Utils.prettyNum(CustomEnchants.getEnchantLevel(pickaxe, "metalDetector") + ""),
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.METAL_DETECTOR.PRICE.multiply(BigInteger.valueOf(10000))),
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Level: " + Utils.addCommasToNumber(PrisonEnchants.METAL_DETECTOR.MAX_LEVEL)));
            }
            @Override
            public void item2Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                String pickaxeUUID = PrisonEnchants.playerUUIDToPickaxeID.get(player.getUniqueId());
                ItemStack pickaxe = Utils.findPickaxeInInventoryFromUUID(player, pickaxeUUID);
                if (pickaxe == null) {
                    player.closeInventory();
                    return;
                }
                if (PrisonEnchants.METAL_DETECTOR.tryToBuyLevels(player, pickaxe, 1)) open(player);
            }
            @Override
            public void item3Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                String pickaxeUUID = PrisonEnchants.playerUUIDToPickaxeID.get(player.getUniqueId());
                ItemStack pickaxe = Utils.findPickaxeInInventoryFromUUID(player, pickaxeUUID);
                if (pickaxe == null) {
                    player.closeInventory();
                    return;
                }
                if (PrisonEnchants.METAL_DETECTOR.tryToBuyLevels(player, pickaxe, 10)) open(player);
            }
            @Override
            public void item4Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                String pickaxeUUID = PrisonEnchants.playerUUIDToPickaxeID.get(player.getUniqueId());
                ItemStack pickaxe = Utils.findPickaxeInInventoryFromUUID(player, pickaxeUUID);
                if (pickaxe == null) {
                    player.closeInventory();
                    return;
                }
                if (PrisonEnchants.METAL_DETECTOR.tryToBuyLevels(player, pickaxe, 100)) open(player);
            }
            @Override
            public void item5Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                String pickaxeUUID = PrisonEnchants.playerUUIDToPickaxeID.get(player.getUniqueId());
                ItemStack pickaxe = Utils.findPickaxeInInventoryFromUUID(player, pickaxeUUID);
                if (pickaxe == null) {
                    player.closeInventory();
                    return;
                }
                if (PrisonEnchants.METAL_DETECTOR.tryToBuyLevels(player, pickaxe, 1000)) open(player);
            }
            @Override
            public void item6Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                String pickaxeUUID = PrisonEnchants.playerUUIDToPickaxeID.get(player.getUniqueId());
                ItemStack pickaxe = Utils.findPickaxeInInventoryFromUUID(player, pickaxeUUID);
                if (pickaxe == null) {
                    player.closeInventory();
                    return;
                }
                if (PrisonEnchants.METAL_DETECTOR.tryToBuyLevels(player, pickaxe, 10000)) open(player);
            }
        };
        guiPage.identifier = "enchantsMetalDetector";
        guiPage.guiTitle = ChatColor.translateAlternateColorCodes('&', "&dUpgrade Metal Detector");
        guiPage.onCloseGoToMenu = "enchantsMain";
        guiPage.register();
    }
    //explosion
    public static void explosion() {
        GUIPage guiPage = new GUIPage() {
            @Override
            public void onOpen(Player player) {
                String pickaxeUUID = PrisonEnchants.playerUUIDToPickaxeID.get(player.getUniqueId());
                ItemStack pickaxe = Utils.findPickaxeInInventoryFromUUID(player, pickaxeUUID);
                if (pickaxe == null) {
                    player.closeInventory();
                    return;
                }
                PlayerData playerData = new PlayerData(player);
                menuItems = new ArrayList<>();
                menuItems.add(GUI.createLightGrayPlaceholderItem());
                menuItems.add(GUI.createLightGrayPlaceholderItem());
                menuItems.add(GUI.createMenuItem(identifier, Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "Buy 1 Level(s) Of Explosion", "",
                        ChatColor.AQUA + "Current Level: " + ChatColor.WHITE + Utils.prettyNum(CustomEnchants.getEnchantLevel(pickaxe, "explosion") + ""),
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.EXPLOSION.PRICE),
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Level: " + Utils.addCommasToNumber(PrisonEnchants.EXPLOSION.MAX_LEVEL)));
                menuItems.add(GUI.createMenuItem(identifier, Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "Buy 10 Level(s) Of Explosion", "",
                        ChatColor.AQUA + "Current Level: " + ChatColor.WHITE + Utils.prettyNum(CustomEnchants.getEnchantLevel(pickaxe, "explosion") + ""),
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.EXPLOSION.PRICE.multiply(BigInteger.valueOf(10))),
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Level: " + Utils.addCommasToNumber(PrisonEnchants.EXPLOSION.MAX_LEVEL)));
                menuItems.add(GUI.createMenuItem(identifier, Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "Buy 100 Level(s) Of Explosion", "",
                        ChatColor.AQUA + "Current Level: " + ChatColor.WHITE + Utils.prettyNum(CustomEnchants.getEnchantLevel(pickaxe, "explosion") + ""),
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.EXPLOSION.PRICE.multiply(BigInteger.valueOf(100))),
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Level: " + Utils.addCommasToNumber(PrisonEnchants.EXPLOSION.MAX_LEVEL)));
                menuItems.add(GUI.createMenuItem(identifier, Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "Buy 1,000 Level(s) Of Explosion", "",
                        ChatColor.AQUA + "Current Level: " + ChatColor.WHITE + Utils.prettyNum(CustomEnchants.getEnchantLevel(pickaxe, "explosion") + ""),
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.EXPLOSION.PRICE.multiply(BigInteger.valueOf(1000))),
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Level: " + Utils.addCommasToNumber(PrisonEnchants.EXPLOSION.MAX_LEVEL)));
                menuItems.add(GUI.createMenuItem(identifier, Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "Buy 10,000 Level(s) Of Explosion", "",
                        ChatColor.AQUA + "Current Level: " + ChatColor.WHITE + Utils.prettyNum(CustomEnchants.getEnchantLevel(pickaxe, "explosion") + ""),
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.EXPLOSION.PRICE.multiply(BigInteger.valueOf(10000))),
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Level: " + Utils.addCommasToNumber(PrisonEnchants.EXPLOSION.MAX_LEVEL)));
            }
            @Override
            public void item2Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                String pickaxeUUID = PrisonEnchants.playerUUIDToPickaxeID.get(player.getUniqueId());
                ItemStack pickaxe = Utils.findPickaxeInInventoryFromUUID(player, pickaxeUUID);
                if (pickaxe == null) {
                    player.closeInventory();
                    return;
                }
                if (PrisonEnchants.EXPLOSION.tryToBuyLevels(player, pickaxe, 1)) open(player);
            }
            @Override
            public void item3Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                String pickaxeUUID = PrisonEnchants.playerUUIDToPickaxeID.get(player.getUniqueId());
                ItemStack pickaxe = Utils.findPickaxeInInventoryFromUUID(player, pickaxeUUID);
                if (pickaxe == null) {
                    player.closeInventory();
                    return;
                }
                if (PrisonEnchants.EXPLOSION.tryToBuyLevels(player, pickaxe, 10)) open(player);
            }
            @Override
            public void item4Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                String pickaxeUUID = PrisonEnchants.playerUUIDToPickaxeID.get(player.getUniqueId());
                ItemStack pickaxe = Utils.findPickaxeInInventoryFromUUID(player, pickaxeUUID);
                if (pickaxe == null) {
                    player.closeInventory();
                    return;
                }
                if (PrisonEnchants.EXPLOSION.tryToBuyLevels(player, pickaxe, 100)) open(player);
            }
            @Override
            public void item5Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                String pickaxeUUID = PrisonEnchants.playerUUIDToPickaxeID.get(player.getUniqueId());
                ItemStack pickaxe = Utils.findPickaxeInInventoryFromUUID(player, pickaxeUUID);
                if (pickaxe == null) {
                    player.closeInventory();
                    return;
                }
                if (PrisonEnchants.EXPLOSION.tryToBuyLevels(player, pickaxe, 1000)) open(player);
            }
            @Override
            public void item6Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                String pickaxeUUID = PrisonEnchants.playerUUIDToPickaxeID.get(player.getUniqueId());
                ItemStack pickaxe = Utils.findPickaxeInInventoryFromUUID(player, pickaxeUUID);
                if (pickaxe == null) {
                    player.closeInventory();
                    return;
                }
                if (PrisonEnchants.EXPLOSION.tryToBuyLevels(player, pickaxe, 10000)) open(player);
            }
        };
        guiPage.identifier = "enchantsExplosion";
        guiPage.guiTitle = ChatColor.translateAlternateColorCodes('&', "&dUpgrade Explosion");
        guiPage.onCloseGoToMenu = "enchantsMain";
        guiPage.register();
    }
    //xp menu
    public static void consistency() {
        GUIPage guiPage = new GUIPage() {
            @Override
            public void onOpen(Player player) {
                String pickaxeUUID = PrisonEnchants.playerUUIDToPickaxeID.get(player.getUniqueId());
                ItemStack pickaxe = Utils.findPickaxeInInventoryFromUUID(player, pickaxeUUID);
                if (pickaxe == null) {
                    player.closeInventory();
                    return;
                }
                PlayerData playerData = new PlayerData(player);
                menuItems = new ArrayList<>();
                menuItems.add(GUI.createLightGrayPlaceholderItem());
                menuItems.add(GUI.createLightGrayPlaceholderItem());
                menuItems.add(GUI.createLightGrayPlaceholderItem());
                menuItems.add(GUI.createLightGrayPlaceholderItem());
                menuItems.add(GUI.createMenuItem(identifier, Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "Buy 1 Level(s) Of Consistency", "",
                        ChatColor.AQUA + "Current Level: " + ChatColor.WHITE + Utils.prettyNum(CustomEnchants.getEnchantLevel(pickaxe, "consistency") + ""),
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.CONSISTENCY.PRICE),
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Level: " + Utils.addCommasToNumber(PrisonEnchants.CONSISTENCY.MAX_LEVEL)));
            }
            @Override
            public void item4Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                String pickaxeUUID = PrisonEnchants.playerUUIDToPickaxeID.get(player.getUniqueId());
                ItemStack pickaxe = Utils.findPickaxeInInventoryFromUUID(player, pickaxeUUID);
                if (pickaxe == null) {
                    player.closeInventory();
                    return;
                }
                if (PrisonEnchants.CONSISTENCY.tryToBuyLevels(player, pickaxe, 1)) open(player);
                EnchantEffects.giveEffect(player, player.getInventory().getItemInMainHand());
            }
        };
        guiPage.identifier = "enchantsConsistency";
        guiPage.guiTitle = ChatColor.translateAlternateColorCodes('&', "&dUpgrade Consistency");
        guiPage.onCloseGoToMenu = "enchantsMain";
        guiPage.register();
    }

    //Haste menu
    public static void haste() {
        GUIPage guiPage = new GUIPage() {
            @Override
            public void onOpen(Player player) {
                String pickaxeUUID = PrisonEnchants.playerUUIDToPickaxeID.get(player.getUniqueId());
                ItemStack pickaxe = Utils.findPickaxeInInventoryFromUUID(player, pickaxeUUID);
                if (pickaxe == null) {
                    player.closeInventory();
                    return;
                }
                PlayerData playerData = new PlayerData(player);
                menuItems = new ArrayList<>();
                menuItems.add(GUI.createLightGrayPlaceholderItem());
                menuItems.add(GUI.createLightGrayPlaceholderItem());
                menuItems.add(GUI.createLightGrayPlaceholderItem());
                menuItems.add(GUI.createLightGrayPlaceholderItem());
                menuItems.add(GUI.createMenuItem(identifier, Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "Buy 1 Level(s) Of Haste", "",
                        ChatColor.AQUA + "Current Level: " + ChatColor.WHITE + Utils.prettyNum(CustomEnchants.getEnchantLevel(pickaxe, "haste") + ""),
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.HASTE.PRICE),
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Level: " + Utils.addCommasToNumber(PrisonEnchants.HASTE.MAX_LEVEL)));
            }
            @Override
            public void item4Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                String pickaxeUUID = PrisonEnchants.playerUUIDToPickaxeID.get(player.getUniqueId());
                ItemStack pickaxe = Utils.findPickaxeInInventoryFromUUID(player, pickaxeUUID);
                if (pickaxe == null) {
                    player.closeInventory();
                    return;
                }
                if (PrisonEnchants.HASTE.tryToBuyLevels(player, pickaxe, 1)) open(player);
                EnchantEffects.giveEffect(player, player.getInventory().getItemInMainHand());
            }
        };
        guiPage.identifier = "enchantsHaste";
        guiPage.guiTitle = ChatColor.translateAlternateColorCodes('&', "&dUpgrade Haste");
        guiPage.onCloseGoToMenu = "enchantsMain";
        guiPage.register();
    }

    //Speed menu
    public static void speed() {
        GUIPage guiPage = new GUIPage() {
            @Override
            public void onOpen(Player player) {
                String pickaxeUUID = PrisonEnchants.playerUUIDToPickaxeID.get(player.getUniqueId());
                ItemStack pickaxe = Utils.findPickaxeInInventoryFromUUID(player, pickaxeUUID);
                if (pickaxe == null) {
                    player.closeInventory();
                    return;
                }
                PlayerData playerData = new PlayerData(player);
                menuItems = new ArrayList<>();
                menuItems.add(GUI.createLightGrayPlaceholderItem());
                menuItems.add(GUI.createLightGrayPlaceholderItem());
                menuItems.add(GUI.createLightGrayPlaceholderItem());
                menuItems.add(GUI.createLightGrayPlaceholderItem());
                menuItems.add(GUI.createMenuItem(identifier, Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "Buy 1 Level(s) Of Speed", "",
                        ChatColor.AQUA + "Current Level: " + ChatColor.WHITE + Utils.prettyNum(CustomEnchants.getEnchantLevel(pickaxe, "speed") + ""),
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.SPEED.PRICE),
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Level: " + Utils.addCommasToNumber(PrisonEnchants.SPEED.MAX_LEVEL)));
            }
            @Override
            public void item4Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                String pickaxeUUID = PrisonEnchants.playerUUIDToPickaxeID.get(player.getUniqueId());
                ItemStack pickaxe = Utils.findPickaxeInInventoryFromUUID(player, pickaxeUUID);
                if (pickaxe == null) {
                    player.closeInventory();
                    return;
                }
                if (PrisonEnchants.SPEED.tryToBuyLevels(player, pickaxe, 1)) open(player);
                EnchantEffects.giveEffect(player, player.getInventory().getItemInMainHand());
            }
        };
        guiPage.identifier = "enchantsSpeed";
        guiPage.guiTitle = ChatColor.translateAlternateColorCodes('&', "&dUpgrade Speed");
        guiPage.onCloseGoToMenu = "enchantsMain";
        guiPage.register();
    }

    //Night Vision menu
    public static void nightVision() {
        GUIPage guiPage = new GUIPage() {
            @Override
            public void onOpen(Player player) {
                String pickaxeUUID = PrisonEnchants.playerUUIDToPickaxeID.get(player.getUniqueId());
                ItemStack pickaxe = Utils.findPickaxeInInventoryFromUUID(player, pickaxeUUID);
                if (pickaxe == null) {
                    player.closeInventory();
                    return;
                }
                PlayerData playerData = new PlayerData(player);
                menuItems = new ArrayList<>();
                menuItems.add(GUI.createLightGrayPlaceholderItem());
                menuItems.add(GUI.createLightGrayPlaceholderItem());
                menuItems.add(GUI.createLightGrayPlaceholderItem());
                menuItems.add(GUI.createLightGrayPlaceholderItem());
                menuItems.add(GUI.createMenuItem(identifier, Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "Buy 1 Level(s) Of Night Vision", "",
                        ChatColor.AQUA + "Current Level: " + ChatColor.WHITE + Utils.prettyNum(CustomEnchants.getEnchantLevel(pickaxe, "nightVision") + ""),
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.NIGHT_VISION.PRICE),
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Level: " + Utils.addCommasToNumber(PrisonEnchants.NIGHT_VISION.MAX_LEVEL)));
            }
            @Override
            public void item4Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                String pickaxeUUID = PrisonEnchants.playerUUIDToPickaxeID.get(player.getUniqueId());
                ItemStack pickaxe = Utils.findPickaxeInInventoryFromUUID(player, pickaxeUUID);
                if (pickaxe == null) {
                    player.closeInventory();
                    return;
                }
                if (PrisonEnchants.NIGHT_VISION.tryToBuyLevels(player, pickaxe, 1)) open(player);
                EnchantEffects.giveEffect(player, player.getInventory().getItemInMainHand());
            }
        };
        guiPage.identifier = "enchantsNightVision";
        guiPage.guiTitle = ChatColor.translateAlternateColorCodes('&', "&dUpgrade Night Vision");
        guiPage.onCloseGoToMenu = "enchantsMain";
        guiPage.register();
    }

     */

}
