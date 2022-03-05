package me.staticstudios.prisons.gui.menus;

import me.staticstudios.prisons.Main;
import me.staticstudios.prisons.data.serverData.PlayerData;
import me.staticstudios.prisons.enchants.CustomEnchants;
import me.staticstudios.prisons.enchants.EnchantEffects;
import me.staticstudios.prisons.enchants.PrisonEnchants;
import me.staticstudios.prisons.gui.GUI;
import me.staticstudios.prisons.gui.GUIPage;
import me.staticstudios.prisons.utils.Utils;
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

import java.util.ArrayList;

public class EnchantsMenus {
    //Select a pickaxe to enchant
    public static void selectPick() {
        GUIPage guiPage = new GUIPage() {
            @Override
            public void onOpen(Player player) {
                menuItems = new ArrayList<>();
                ItemStack[] items = player.getInventory().getContents();
                for (ItemStack item : items) {
                    if (!Utils.checkIsPrisonPickaxe(item)) continue;
                    String pickaxeId = item.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(Main.getMain(), "pickaxeUUID"), PersistentDataType.STRING);
                    ItemStack newItem = new ItemStack(Material.DIAMOND_PICKAXE);
                    ItemMeta meta = newItem.getItemMeta();
                    meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                    meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                    meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
                    meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                    meta.addEnchant(Enchantment.DIG_SPEED, 1, false);
                    meta.getPersistentDataContainer().set(new NamespacedKey(Main.getMain(), "guiItem"), PersistentDataType.STRING, "normal");
                    meta.getPersistentDataContainer().set(new NamespacedKey(Main.getMain(), "fromPage"), PersistentDataType.STRING, identifier);
                    meta.getPersistentDataContainer().set(new NamespacedKey(Main.getMain(), "pickaxeUUID"), PersistentDataType.STRING, pickaxeId);
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
                GUI.getGUIPage("enchantsMain").args = item.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(Main.getMain(), "pickaxeUUID"), PersistentDataType.STRING);
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
                PrisonEnchants.playerUUIDToPickaxeID.put(player.getUniqueId(), args);
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
                menuItems.add(GUI.createEnchantedMenuItem(identifier, Material.DIAMOND, ChatColor.AQUA + "" + ChatColor.BOLD + "Fortune", ChatColor.GRAY + "Get more blocks in your backpack when you mine", "",
                        ChatColor.AQUA + "Current Level: " + ChatColor.WHITE + Utils.prettyNum(CustomEnchants.getEnchantLevel(pickaxe, "fortune") + ""),
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.FORTUNE.calculatePrice((int) CustomEnchants.getEnchantLevel(pickaxe, "fortune"), 1)),
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Level: " + Utils.addCommasToNumber(PrisonEnchants.FORTUNE.MAX_LEVEL)));
                menuItems.add(GUI.createEnchantedMenuItem(identifier, Material.SUNFLOWER, ChatColor.YELLOW + "" + ChatColor.BOLD + "Tokenator", ChatColor.GRAY + "Gives additional tokens for every block mined", "",
                        ChatColor.AQUA + "Current Level: " + ChatColor.WHITE + Utils.prettyNum(CustomEnchants.getEnchantLevel(pickaxe, "tokenator") + ""),
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.TOKENATOR.calculatePrice((int) CustomEnchants.getEnchantLevel(pickaxe, "tokenator"), 1)),
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Level: " + Utils.addCommasToNumber(PrisonEnchants.TOKENATOR.MAX_LEVEL)));
                menuItems.add(GUI.createEnchantedMenuItem(identifier, Material.ANVIL, ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "Jack Hammer", ChatColor.GRAY + "Chance to destroy a full mine layer", "",
                        ChatColor.AQUA + "Current Level: " + ChatColor.WHITE + Utils.prettyNum(CustomEnchants.getEnchantLevel(pickaxe, "jackHammer") + ""),
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.JACK_HAMMER.calculatePrice((int) CustomEnchants.getEnchantLevel(pickaxe, "jackHammer"), 1)),
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Level: " + Utils.addCommasToNumber(PrisonEnchants.JACK_HAMMER.MAX_LEVEL)));
                menuItems.add(GUI.createEnchantedMenuItem(identifier, Material.HOPPER, ChatColor.GOLD + "" + ChatColor.BOLD + "Double Wammy", ChatColor.GRAY + "Chance for Jack Hammer to destroy 2 layers", "",
                        ChatColor.AQUA + "Current Level: " + ChatColor.WHITE + Utils.prettyNum(CustomEnchants.getEnchantLevel(pickaxe, "doubleWammy") + ""),
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.DOUBLE_WAMMY.calculatePrice((int) CustomEnchants.getEnchantLevel(pickaxe, "doubleWammy"), 1)),
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Level: " + Utils.addCommasToNumber(PrisonEnchants.DOUBLE_WAMMY.MAX_LEVEL)));
                menuItems.add(GUI.createEnchantedMenuItem(identifier, Material.COMPARATOR, ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Multi-Directional", ChatColor.GRAY + "Chance to destroy a " + ChatColor.BOLD + "+" + ChatColor.GRAY + " shaped section of a mine", "",
                        ChatColor.AQUA + "Current Level: " + ChatColor.WHITE + Utils.prettyNum(CustomEnchants.getEnchantLevel(pickaxe, "multiDirectional") + ""),
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.MULTI_DIRECTIONAL.calculatePrice((int) CustomEnchants.getEnchantLevel(pickaxe, "multiDirectional"), 1)),
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Level: " + Utils.addCommasToNumber(PrisonEnchants.MULTI_DIRECTIONAL.MAX_LEVEL)));
                menuItems.add(GUI.createEnchantedMenuItem(identifier, Material.MAP, ChatColor.GREEN + "" + ChatColor.BOLD + "Merchant", ChatColor.GRAY + "Gives a +0.004% sell multiplier per level", "",
                        ChatColor.AQUA + "Current Level: " + ChatColor.WHITE + Utils.prettyNum(CustomEnchants.getEnchantLevel(pickaxe, "merchant") + ""),
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.MERCHANT.calculatePrice((int) CustomEnchants.getEnchantLevel(pickaxe, "merchant"), 1)),
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Level: " + Utils.addCommasToNumber(PrisonEnchants.MERCHANT.MAX_LEVEL)));
                menuItems.add(GUI.createEnchantedMenuItem(identifier, Material.GOLD_NUGGET, ChatColor.GOLD + "" + ChatColor.BOLD + "Cash Grab", ChatColor.GRAY + "Gives a +25% sell multiplier per level", "",
                        ChatColor.AQUA + "Current Level: " + ChatColor.WHITE + Utils.prettyNum(CustomEnchants.getEnchantLevel(pickaxe, "cashGrab") + ""),
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.CASH_GRAB.calculatePrice((int) CustomEnchants.getEnchantLevel(pickaxe, "cashGrab"), 1)),
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Level: " + Utils.addCommasToNumber(PrisonEnchants.CASH_GRAB.MAX_LEVEL)));
                menuItems.add(GUI.createDarkGrayPlaceholderItem());


                menuItems.add(GUI.createDarkGrayPlaceholderItem());
                menuItems.add(GUI.createEnchantedMenuItem(identifier, Material.AMETHYST_SHARD, ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Ore Splitter", ChatColor.GRAY + "Gives a +0.001% chance to get double blocks from fortune", "",
                        ChatColor.AQUA + "Current Level: " + ChatColor.WHITE + Utils.prettyNum(CustomEnchants.getEnchantLevel(pickaxe, "oreSplitter") + ""),
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.ORE_SPLITTER.calculatePrice((int) CustomEnchants.getEnchantLevel(pickaxe, "oreSplitter"), 1)),
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Level: " + Utils.addCommasToNumber(PrisonEnchants.ORE_SPLITTER.MAX_LEVEL)));
                menuItems.add(GUI.createEnchantedMenuItem(identifier, Material.MAGMA_CREAM, ChatColor.GOLD + "" + ChatColor.BOLD + "Token Polisher", ChatColor.GRAY + "Gives a +10% token multiplier per level", "",
                        ChatColor.AQUA + "Current Level: " + ChatColor.WHITE + Utils.prettyNum(CustomEnchants.getEnchantLevel(pickaxe, "tokenPolisher") + ""),
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.TOKEN_POLISHER.calculatePrice((int) CustomEnchants.getEnchantLevel(pickaxe, "tokenPolisher"), 1)),
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Level: " + Utils.addCommasToNumber(PrisonEnchants.TOKEN_POLISHER.MAX_LEVEL)));
                menuItems.add(GUI.createEnchantedMenuItem(identifier, Material.TRIPWIRE_HOOK, ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Key Finder", ChatColor.GRAY + "Chance to find crate keys while mining", "",
                        ChatColor.AQUA + "Current Level: " + ChatColor.WHITE + Utils.prettyNum(CustomEnchants.getEnchantLevel(pickaxe, "keyFinder") + ""),
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.KEY_FINDER.calculatePrice((int) CustomEnchants.getEnchantLevel(pickaxe, "keyFinder"), 1)),
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Level: " + Utils.addCommasToNumber(PrisonEnchants.KEY_FINDER.MAX_LEVEL)));
                menuItems.add(GUI.createEnchantedMenuItem(identifier, Material.EXPERIENCE_BOTTLE, ChatColor.GREEN + "" + ChatColor.BOLD + "XP Finder", ChatColor.GRAY + "+1 pickaxe XP per block mined", "",
                        ChatColor.AQUA + "Current Level: " + ChatColor.WHITE + Utils.prettyNum(CustomEnchants.getEnchantLevel(pickaxe, "xpFinder") + ""),
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.XP_FINDER.calculatePrice((int) CustomEnchants.getEnchantLevel(pickaxe, "xpFinder"), 1)),
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Level: " + Utils.addCommasToNumber(PrisonEnchants.XP_FINDER.MAX_LEVEL)));
                menuItems.add(GUI.createLightGrayPlaceholderItem());
                menuItems.add(GUI.createLightGrayPlaceholderItem());
                menuItems.add(GUI.createLightGrayPlaceholderItem());
                menuItems.add(GUI.createDarkGrayPlaceholderItem());


                menuItems.add(GUI.createDarkGrayPlaceholderItem());
                menuItems.add(GUI.createLightGrayPlaceholderItem());
                menuItems.add(GUI.createLightGrayPlaceholderItem());
                menuItems.add(GUI.createLightGrayPlaceholderItem());
                menuItems.add(GUI.createLightGrayPlaceholderItem());
                menuItems.add(GUI.createEnchantedMenuItem(identifier, Material.GOLDEN_PICKAXE, ChatColor.YELLOW + "" + ChatColor.BOLD + "Haste", ChatColor.GRAY + "Gives the vanilla haste effect", "",
                        ChatColor.AQUA + "Current Level: " + ChatColor.WHITE + Utils.prettyNum(CustomEnchants.getEnchantLevel(pickaxe, "haste") + ""),
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.HASTE.calculatePrice((int) CustomEnchants.getEnchantLevel(pickaxe, "haste"), 1)),
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Level: " + Utils.addCommasToNumber(PrisonEnchants.HASTE.MAX_LEVEL)));
                menuItems.add(GUI.createEnchantedMenuItem(identifier, Material.FEATHER, ChatColor.AQUA + "" + ChatColor.BOLD + "Speed", ChatColor.GRAY + "Gives the vanilla speed effect", "",
                        ChatColor.AQUA + "Current Level: " + ChatColor.WHITE + Utils.prettyNum(CustomEnchants.getEnchantLevel(pickaxe, "speed") + ""),
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.SPEED.calculatePrice((int) CustomEnchants.getEnchantLevel(pickaxe, "speed"), 1)),
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Level: " + Utils.addCommasToNumber(PrisonEnchants.SPEED.MAX_LEVEL)));
                menuItems.add(GUI.createEnchantedMenuItem(identifier, Material.LAPIS_LAZULI, ChatColor.BLUE + "" + ChatColor.BOLD + "Night Vision", ChatColor.GRAY + "Gives the vanilla night vision effect", "",
                        ChatColor.AQUA + "Current Level: " + ChatColor.WHITE + Utils.prettyNum(CustomEnchants.getEnchantLevel(pickaxe, "nightVision") + ""),
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.NIGHT_VISION.calculatePrice((int) CustomEnchants.getEnchantLevel(pickaxe, "nightVision"), 1)),
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Level: " + Utils.addCommasToNumber(PrisonEnchants.NIGHT_VISION.MAX_LEVEL)));
                menuItems.add(GUI.createDarkGrayPlaceholderItem());
                for (int i = 0; i < 9; i++) {
                    menuItems.add(GUI.createDarkGrayPlaceholderItem());
                }
                for (int i = 0; i < 8; i++) {
                    menuItems.add(GUI.createLightGrayPlaceholderItem());
                }
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
                GUI.getGUIPage("enchantsTokenator").open(player);
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
                GUI.getGUIPage("enchantsJackhammer").open(player);
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
                GUI.getGUIPage("enchantsDoubleWammy").open(player);
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
                GUI.getGUIPage("enchantsMultiDirectional").open(player);
            }
            @Override
            public void item15Clicked(InventoryClickEvent e) {
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
            public void item16Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                String pickaxeUUID = PrisonEnchants.playerUUIDToPickaxeID.get(player.getUniqueId());
                ItemStack pickaxe = Utils.findPickaxeInInventoryFromUUID(player, pickaxeUUID);
                if (pickaxe == null) {
                    player.closeInventory();
                    return;
                }
                GUI.getGUIPage("enchantsCashGrab").open(player);
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
                GUI.getGUIPage("enchantsOreSplitter").open(player);
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
                GUI.getGUIPage("enchantsTokenPolisher").open(player);
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
                GUI.getGUIPage("enchantsKeyFinder").open(player);
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
                GUI.getGUIPage("enchantsXpFinder").open(player);
            }





            @Override
            public void item32Clicked(InventoryClickEvent e) {
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
            public void item33Clicked(InventoryClickEvent e) {
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
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.JACK_HAMMER.calculatePrice((int) CustomEnchants.getEnchantLevel(pickaxe, "jackHammer"), 1)),
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Level: " + Utils.addCommasToNumber(PrisonEnchants.JACK_HAMMER.MAX_LEVEL)));
                menuItems.add(GUI.createMenuItem(identifier, Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "Buy 10 Level(s) Of Jack Hammer", "",
                        ChatColor.AQUA + "Current Level: " + ChatColor.WHITE + Utils.prettyNum(CustomEnchants.getEnchantLevel(pickaxe, "jackHammer") + ""),
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.JACK_HAMMER.calculatePrice((int) CustomEnchants.getEnchantLevel(pickaxe, "jackHammer"), 10)),
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Level: " + Utils.addCommasToNumber(PrisonEnchants.JACK_HAMMER.MAX_LEVEL)));
                menuItems.add(GUI.createMenuItem(identifier, Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "Buy 100 Level(s) Of Jack Hammer", "",
                        ChatColor.AQUA + "Current Level: " + ChatColor.WHITE + Utils.prettyNum(CustomEnchants.getEnchantLevel(pickaxe, "jackHammer") + ""),
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.JACK_HAMMER.calculatePrice((int) CustomEnchants.getEnchantLevel(pickaxe, "jackHammer"), 100)),
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Level: " + Utils.addCommasToNumber(PrisonEnchants.JACK_HAMMER.MAX_LEVEL)));
                menuItems.add(GUI.createMenuItem(identifier, Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "Buy 1,000 Level(s) Of Jack Hammer", "",
                        ChatColor.AQUA + "Current Level: " + ChatColor.WHITE + Utils.prettyNum(CustomEnchants.getEnchantLevel(pickaxe, "jackHammer") + ""),
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.JACK_HAMMER.calculatePrice((int) CustomEnchants.getEnchantLevel(pickaxe, "jackHammer"), 1000)),
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Level: " + Utils.addCommasToNumber(PrisonEnchants.JACK_HAMMER.MAX_LEVEL)));
                menuItems.add(GUI.createMenuItem(identifier, Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "Buy 10,000 Level(s) Of Jack Hammer", "",
                        ChatColor.AQUA + "Current Level: " + ChatColor.WHITE + Utils.prettyNum(CustomEnchants.getEnchantLevel(pickaxe, "jackHammer") + ""),
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.JACK_HAMMER.calculatePrice((int) CustomEnchants.getEnchantLevel(pickaxe, "jackHammer"), 10000)),
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
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.DOUBLE_WAMMY.calculatePrice((int) CustomEnchants.getEnchantLevel(pickaxe, "doubleWammy"), 1)),
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Level: " + Utils.addCommasToNumber(PrisonEnchants.DOUBLE_WAMMY.MAX_LEVEL)));
                menuItems.add(GUI.createMenuItem(identifier, Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "Buy 10 Level(s) Of Double Wammy", "",
                        ChatColor.AQUA + "Current Level: " + ChatColor.WHITE + Utils.prettyNum(CustomEnchants.getEnchantLevel(pickaxe, "doubleWammy") + ""),
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.DOUBLE_WAMMY.calculatePrice((int) CustomEnchants.getEnchantLevel(pickaxe, "doubleWammy"), 10)),
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Level: " + Utils.addCommasToNumber(PrisonEnchants.DOUBLE_WAMMY.MAX_LEVEL)));
                menuItems.add(GUI.createMenuItem(identifier, Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "Buy 100 Level(s) Of Double Wammy", "",
                        ChatColor.AQUA + "Current Level: " + ChatColor.WHITE + Utils.prettyNum(CustomEnchants.getEnchantLevel(pickaxe, "doubleWammy") + ""),
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.DOUBLE_WAMMY.calculatePrice((int) CustomEnchants.getEnchantLevel(pickaxe, "doubleWammy"), 100)),
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Level: " + Utils.addCommasToNumber(PrisonEnchants.DOUBLE_WAMMY.MAX_LEVEL)));
                menuItems.add(GUI.createMenuItem(identifier, Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "Buy 1,000 Level(s) Of Double Wammy", "",
                        ChatColor.AQUA + "Current Level: " + ChatColor.WHITE + Utils.prettyNum(CustomEnchants.getEnchantLevel(pickaxe, "doubleWammy") + ""),
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.DOUBLE_WAMMY.calculatePrice((int) CustomEnchants.getEnchantLevel(pickaxe, "doubleWammy"), 1000)),
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Level: " + Utils.addCommasToNumber(PrisonEnchants.DOUBLE_WAMMY.MAX_LEVEL)));
                menuItems.add(GUI.createMenuItem(identifier, Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "Buy 10,000 Level(s) Of Double Wammy", "",
                        ChatColor.AQUA + "Current Level: " + ChatColor.WHITE + Utils.prettyNum(CustomEnchants.getEnchantLevel(pickaxe, "doubleWammy") + ""),
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.DOUBLE_WAMMY.calculatePrice((int) CustomEnchants.getEnchantLevel(pickaxe, "doubleWammy"), 10000)),
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Level: " + Utils.addCommasToNumber(PrisonEnchants.DOUBLE_WAMMY.MAX_LEVEL)));
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
                if (PrisonEnchants.DOUBLE_WAMMY.tryToBuyLevels(player, pickaxe, 1)) open(player);
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
                if (PrisonEnchants.DOUBLE_WAMMY.tryToBuyLevels(player, pickaxe, 10)) open(player);
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
                if (PrisonEnchants.DOUBLE_WAMMY.tryToBuyLevels(player, pickaxe, 100)) open(player);
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
                if (PrisonEnchants.DOUBLE_WAMMY.tryToBuyLevels(player, pickaxe, 1000)) open(player);
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
                if (PrisonEnchants.DOUBLE_WAMMY.tryToBuyLevels(player, pickaxe, 10000)) open(player);
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
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.MULTI_DIRECTIONAL.calculatePrice((int) CustomEnchants.getEnchantLevel(pickaxe, "multiDirectional"), 1)),
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Level: " + Utils.addCommasToNumber(PrisonEnchants.MULTI_DIRECTIONAL.MAX_LEVEL)));
                menuItems.add(GUI.createMenuItem(identifier, Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "Buy 10 Level(s) Of Multi-Directional", "",
                        ChatColor.AQUA + "Current Level: " + ChatColor.WHITE + Utils.prettyNum(CustomEnchants.getEnchantLevel(pickaxe, "multiDirectional") + ""),
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.MULTI_DIRECTIONAL.calculatePrice((int) CustomEnchants.getEnchantLevel(pickaxe, "multiDirectional"), 10)),
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Level: " + Utils.addCommasToNumber(PrisonEnchants.MULTI_DIRECTIONAL.MAX_LEVEL)));
                menuItems.add(GUI.createMenuItem(identifier, Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "Buy 100 Level(s) Of Multi-Directional", "",
                        ChatColor.AQUA + "Current Level: " + ChatColor.WHITE + Utils.prettyNum(CustomEnchants.getEnchantLevel(pickaxe, "multiDirectional") + ""),
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.MULTI_DIRECTIONAL.calculatePrice((int) CustomEnchants.getEnchantLevel(pickaxe, "multiDirectional"), 100)),
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Level: " + Utils.addCommasToNumber(PrisonEnchants.MULTI_DIRECTIONAL.MAX_LEVEL)));
                menuItems.add(GUI.createMenuItem(identifier, Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "Buy 1,000 Level(s) Of Multi-Directional", "",
                        ChatColor.AQUA + "Current Level: " + ChatColor.WHITE + Utils.prettyNum(CustomEnchants.getEnchantLevel(pickaxe, "multiDirectional") + ""),
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.MULTI_DIRECTIONAL.calculatePrice((int) CustomEnchants.getEnchantLevel(pickaxe, "multiDirectional"), 1000)),
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Level: " + Utils.addCommasToNumber(PrisonEnchants.MULTI_DIRECTIONAL.MAX_LEVEL)));
                menuItems.add(GUI.createMenuItem(identifier, Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "Buy 10,000 Level(s) Of Multi-Directional", "",
                        ChatColor.AQUA + "Current Level: " + ChatColor.WHITE + Utils.prettyNum(CustomEnchants.getEnchantLevel(pickaxe, "multiDirectional") + ""),
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.MULTI_DIRECTIONAL.calculatePrice((int) CustomEnchants.getEnchantLevel(pickaxe, "multiDirectional"), 10000)),
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
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.TOKENATOR.calculatePrice((int) CustomEnchants.getEnchantLevel(pickaxe, "tokenator"), 1)),
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Level: " + Utils.addCommasToNumber(PrisonEnchants.TOKENATOR.MAX_LEVEL)));
                menuItems.add(GUI.createMenuItem(identifier, Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "Buy 10 Level(s) Of Tokenator", "",
                        ChatColor.AQUA + "Current Level: " + ChatColor.WHITE + Utils.prettyNum(CustomEnchants.getEnchantLevel(pickaxe, "tokenator") + ""),
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.TOKENATOR.calculatePrice((int) CustomEnchants.getEnchantLevel(pickaxe, "tokenator"), 10)),
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Level: " + Utils.addCommasToNumber(PrisonEnchants.TOKENATOR.MAX_LEVEL)));
                menuItems.add(GUI.createMenuItem(identifier, Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "Buy 100 Level(s) Of Tokenator", "",
                        ChatColor.AQUA + "Current Level: " + ChatColor.WHITE + Utils.prettyNum(CustomEnchants.getEnchantLevel(pickaxe, "tokenator") + ""),
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.TOKENATOR.calculatePrice((int) CustomEnchants.getEnchantLevel(pickaxe, "tokenator"), 100)),
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Level: " + Utils.addCommasToNumber(PrisonEnchants.TOKENATOR.MAX_LEVEL)));
                menuItems.add(GUI.createMenuItem(identifier, Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "Buy 1,000 Level(s) Of Tokenator", "",
                        ChatColor.AQUA + "Current Level: " + ChatColor.WHITE + Utils.prettyNum(CustomEnchants.getEnchantLevel(pickaxe, "tokenator") + ""),
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.TOKENATOR.calculatePrice((int) CustomEnchants.getEnchantLevel(pickaxe, "tokenator"), 1000)),
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Level: " + Utils.addCommasToNumber(PrisonEnchants.TOKENATOR.MAX_LEVEL)));
                menuItems.add(GUI.createMenuItem(identifier, Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "Buy 10,000 Level(s) Of Tokenator", "",
                        ChatColor.AQUA + "Current Level: " + ChatColor.WHITE + Utils.prettyNum(CustomEnchants.getEnchantLevel(pickaxe, "tokenator") + ""),
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.TOKENATOR.calculatePrice((int) CustomEnchants.getEnchantLevel(pickaxe, "tokenator"), 10000)),
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
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.FORTUNE.calculatePrice((int) CustomEnchants.getEnchantLevel(pickaxe, "fortune"), 1)),
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Level: " + Utils.addCommasToNumber(PrisonEnchants.FORTUNE.MAX_LEVEL)));
                menuItems.add(GUI.createMenuItem(identifier, Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "Buy 10 Level(s) Of Fortune", "",
                        ChatColor.AQUA + "Current Level: " + ChatColor.WHITE + Utils.prettyNum(CustomEnchants.getEnchantLevel(pickaxe, "fortune") + ""),
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.FORTUNE.calculatePrice((int) CustomEnchants.getEnchantLevel(pickaxe, "fortune"), 10)),
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Level: " + Utils.addCommasToNumber(PrisonEnchants.FORTUNE.MAX_LEVEL)));
                menuItems.add(GUI.createMenuItem(identifier, Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "Buy 100 Level(s) Of Fortune", "",
                        ChatColor.AQUA + "Current Level: " + ChatColor.WHITE + Utils.prettyNum(CustomEnchants.getEnchantLevel(pickaxe, "fortune") + ""),
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.FORTUNE.calculatePrice((int) CustomEnchants.getEnchantLevel(pickaxe, "fortune"), 100)),
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Level: " + Utils.addCommasToNumber(PrisonEnchants.FORTUNE.MAX_LEVEL)));
                menuItems.add(GUI.createMenuItem(identifier, Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "Buy 1,000 Level(s) Of Fortune", "",
                        ChatColor.AQUA + "Current Level: " + ChatColor.WHITE + Utils.prettyNum(CustomEnchants.getEnchantLevel(pickaxe, "fortune") + ""),
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.FORTUNE.calculatePrice((int) CustomEnchants.getEnchantLevel(pickaxe, "fortune"), 1000)),
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Level: " + Utils.addCommasToNumber(PrisonEnchants.FORTUNE.MAX_LEVEL)));
                menuItems.add(GUI.createMenuItem(identifier, Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "Buy 10,000 Level(s) Of Fortune", "",
                        ChatColor.AQUA + "Current Level: " + ChatColor.WHITE + Utils.prettyNum(CustomEnchants.getEnchantLevel(pickaxe, "fortune") + ""),
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.FORTUNE.calculatePrice((int) CustomEnchants.getEnchantLevel(pickaxe, "fortune"), 10000)),
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
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.MERCHANT.calculatePrice((int) CustomEnchants.getEnchantLevel(pickaxe, "merchant"), 1)),
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Level: " + Utils.addCommasToNumber(PrisonEnchants.MERCHANT.MAX_LEVEL)));
                menuItems.add(GUI.createMenuItem(identifier, Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "Buy 10 Level(s) Of Merchant", "",
                        ChatColor.AQUA + "Current Level: " + ChatColor.WHITE + Utils.prettyNum(CustomEnchants.getEnchantLevel(pickaxe, "merchant") + ""),
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.MERCHANT.calculatePrice((int) CustomEnchants.getEnchantLevel(pickaxe, "merchant"), 10)),
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Level: " + Utils.addCommasToNumber(PrisonEnchants.MERCHANT.MAX_LEVEL)));
                menuItems.add(GUI.createMenuItem(identifier, Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "Buy 100 Level(s) Of Merchant", "",
                        ChatColor.AQUA + "Current Level: " + ChatColor.WHITE + Utils.prettyNum(CustomEnchants.getEnchantLevel(pickaxe, "merchant") + ""),
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.MERCHANT.calculatePrice((int) CustomEnchants.getEnchantLevel(pickaxe, "merchant"), 100)),
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Level: " + Utils.addCommasToNumber(PrisonEnchants.MERCHANT.MAX_LEVEL)));
                menuItems.add(GUI.createMenuItem(identifier, Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "Buy 1,000 Level(s) Of Merchant", "",
                        ChatColor.AQUA + "Current Level: " + ChatColor.WHITE + Utils.prettyNum(CustomEnchants.getEnchantLevel(pickaxe, "merchant") + ""),
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.MERCHANT.calculatePrice((int) CustomEnchants.getEnchantLevel(pickaxe, "merchant"), 1000)),
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Level: " + Utils.addCommasToNumber(PrisonEnchants.MERCHANT.MAX_LEVEL)));
                menuItems.add(GUI.createMenuItem(identifier, Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "Buy 10,000 Level(s) Of Merchant", "",
                        ChatColor.AQUA + "Current Level: " + ChatColor.WHITE + Utils.prettyNum(CustomEnchants.getEnchantLevel(pickaxe, "merchant") + ""),
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.MERCHANT.calculatePrice((int) CustomEnchants.getEnchantLevel(pickaxe, "merchant"), 10000)),
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
    //Cash Grab menu
    public static void cashGrab() {
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
                menuItems.add(GUI.createMenuItem(identifier, Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "Buy 1 Level(s) Of Cash Grab", "",
                        ChatColor.AQUA + "Current Level: " + ChatColor.WHITE + Utils.prettyNum(CustomEnchants.getEnchantLevel(pickaxe, "cashGrab") + ""),
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.CASH_GRAB.calculatePrice((int) CustomEnchants.getEnchantLevel(pickaxe, "cashGrab"), 1)),
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Level: " + Utils.addCommasToNumber(PrisonEnchants.CASH_GRAB.MAX_LEVEL)));
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
                if (PrisonEnchants.CASH_GRAB.tryToBuyLevels(player, pickaxe, 1)) open(player);
                EnchantEffects.giveEffect(player, player.getInventory().getItemInMainHand());
            }
        };
        guiPage.identifier = "enchantsCashGrab";
        guiPage.guiTitle = ChatColor.translateAlternateColorCodes('&', "&dUpgrade Cash Grab");
        guiPage.onCloseGoToMenu = "enchantsMain";
        guiPage.register();
    }
    //Token Polisher menu
    public static void tokenPolisher() {
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
                menuItems.add(GUI.createMenuItem(identifier, Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "Buy 1 Level(s) Of Token Polisher", "",
                        ChatColor.AQUA + "Current Level: " + ChatColor.WHITE + Utils.prettyNum(CustomEnchants.getEnchantLevel(pickaxe, "tokenPolisher") + ""),
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.TOKEN_POLISHER.calculatePrice((int) CustomEnchants.getEnchantLevel(pickaxe, "tokenPolisher"), 1)),
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Level: " + Utils.addCommasToNumber(PrisonEnchants.TOKEN_POLISHER.MAX_LEVEL)));
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
                if (PrisonEnchants.TOKEN_POLISHER.tryToBuyLevels(player, pickaxe, 1)) open(player);
                EnchantEffects.giveEffect(player, player.getInventory().getItemInMainHand());
            }
        };
        guiPage.identifier = "enchantsTokenPolisher";
        guiPage.guiTitle = ChatColor.translateAlternateColorCodes('&', "&dUpgrade Token Polisher");
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
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.ORE_SPLITTER.calculatePrice((int) CustomEnchants.getEnchantLevel(pickaxe, "oreSplitter"), 1)),
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Level: " + Utils.addCommasToNumber(PrisonEnchants.ORE_SPLITTER.MAX_LEVEL)));
                menuItems.add(GUI.createMenuItem(identifier, Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "Buy 10 Level(s) Of Ore Splitter", "",
                        ChatColor.AQUA + "Current Level: " + ChatColor.WHITE + Utils.prettyNum(CustomEnchants.getEnchantLevel(pickaxe, "oreSplitter") + ""),
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.ORE_SPLITTER.calculatePrice((int) CustomEnchants.getEnchantLevel(pickaxe, "oreSplitter"), 10)),
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Level: " + Utils.addCommasToNumber(PrisonEnchants.ORE_SPLITTER.MAX_LEVEL)));
                menuItems.add(GUI.createMenuItem(identifier, Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "Buy 100 Level(s) Of Ore Splitter", "",
                        ChatColor.AQUA + "Current Level: " + ChatColor.WHITE + Utils.prettyNum(CustomEnchants.getEnchantLevel(pickaxe, "oreSplitter") + ""),
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.ORE_SPLITTER.calculatePrice((int) CustomEnchants.getEnchantLevel(pickaxe, "oreSplitter"), 100)),
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Level: " + Utils.addCommasToNumber(PrisonEnchants.ORE_SPLITTER.MAX_LEVEL)));
                menuItems.add(GUI.createMenuItem(identifier, Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "Buy 1,000 Level(s) Of Ore Splitter", "",
                        ChatColor.AQUA + "Current Level: " + ChatColor.WHITE + Utils.prettyNum(CustomEnchants.getEnchantLevel(pickaxe, "oreSplitter") + ""),
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.ORE_SPLITTER.calculatePrice((int) CustomEnchants.getEnchantLevel(pickaxe, "oreSplitter"), 1000)),
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Level: " + Utils.addCommasToNumber(PrisonEnchants.ORE_SPLITTER.MAX_LEVEL)));
                menuItems.add(GUI.createMenuItem(identifier, Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "Buy 10,000 Level(s) Of Ore Splitter", "",
                        ChatColor.AQUA + "Current Level: " + ChatColor.WHITE + Utils.prettyNum(CustomEnchants.getEnchantLevel(pickaxe, "oreSplitter") + ""),
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.ORE_SPLITTER.calculatePrice((int) CustomEnchants.getEnchantLevel(pickaxe, "oreSplitter"), 10000)),
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
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.KEY_FINDER.calculatePrice((int) CustomEnchants.getEnchantLevel(pickaxe, "keyFinder"), 1)),
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Level: " + Utils.addCommasToNumber(PrisonEnchants.KEY_FINDER.MAX_LEVEL)));
                menuItems.add(GUI.createMenuItem(identifier, Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "Buy 10 Level(s) Of Key Finder", "",
                        ChatColor.AQUA + "Current Level: " + ChatColor.WHITE + Utils.prettyNum(CustomEnchants.getEnchantLevel(pickaxe, "keyFinder") + ""),
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.KEY_FINDER.calculatePrice((int) CustomEnchants.getEnchantLevel(pickaxe, "keyFinder"), 10)),
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Level: " + Utils.addCommasToNumber(PrisonEnchants.KEY_FINDER.MAX_LEVEL)));
                menuItems.add(GUI.createMenuItem(identifier, Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "Buy 100 Level(s) Of Key Finder", "",
                        ChatColor.AQUA + "Current Level: " + ChatColor.WHITE + Utils.prettyNum(CustomEnchants.getEnchantLevel(pickaxe, "keyFinder") + ""),
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.KEY_FINDER.calculatePrice((int) CustomEnchants.getEnchantLevel(pickaxe, "keyFinder"), 100)),
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Level: " + Utils.addCommasToNumber(PrisonEnchants.KEY_FINDER.MAX_LEVEL)));
                menuItems.add(GUI.createMenuItem(identifier, Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "Buy 1,000 Level(s) Of Key Finder", "",
                        ChatColor.AQUA + "Current Level: " + ChatColor.WHITE + Utils.prettyNum(CustomEnchants.getEnchantLevel(pickaxe, "keyFinder") + ""),
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.KEY_FINDER.calculatePrice((int) CustomEnchants.getEnchantLevel(pickaxe, "keyFinder"), 1000)),
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Level: " + Utils.addCommasToNumber(PrisonEnchants.KEY_FINDER.MAX_LEVEL)));
                menuItems.add(GUI.createMenuItem(identifier, Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "Buy 10,000 Level(s) Of Key Finder", "",
                        ChatColor.AQUA + "Current Level: " + ChatColor.WHITE + Utils.prettyNum(CustomEnchants.getEnchantLevel(pickaxe, "keyFinder") + ""),
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.KEY_FINDER.calculatePrice((int) CustomEnchants.getEnchantLevel(pickaxe, "keyFinder"), 10000)),
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
                menuItems.add(GUI.createLightGrayPlaceholderItem());
                menuItems.add(GUI.createLightGrayPlaceholderItem());
                menuItems.add(GUI.createMenuItem(identifier, Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "Buy 1 Level(s) Of XP Finder", "",
                        ChatColor.AQUA + "Current Level: " + ChatColor.WHITE + Utils.prettyNum(CustomEnchants.getEnchantLevel(pickaxe, "xpFinder") + ""),
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.XP_FINDER.calculatePrice((int) CustomEnchants.getEnchantLevel(pickaxe, "xpFinder"), 1)),
                        ChatColor.AQUA + "Your Tokens: " + ChatColor.WHITE + Utils.prettyNum(playerData.getTokens()), "",
                        ChatColor.GRAY + "Max Level: " + Utils.addCommasToNumber(PrisonEnchants.XP_FINDER.MAX_LEVEL)));
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
                if (PrisonEnchants.XP_FINDER.tryToBuyLevels(player, pickaxe, 1)) open(player);
                EnchantEffects.giveEffect(player, player.getInventory().getItemInMainHand());
            }
        };
        guiPage.identifier = "enchantsXpFinder";
        guiPage.guiTitle = ChatColor.translateAlternateColorCodes('&', "&dUpgrade XP Finder");
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
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.HASTE.calculatePrice((int) CustomEnchants.getEnchantLevel(pickaxe, "haste"), 1)),
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
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.SPEED.calculatePrice((int) CustomEnchants.getEnchantLevel(pickaxe, "speed"), 1)),
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
                        ChatColor.AQUA + "Costs: " + ChatColor.WHITE + Utils.prettyNum(PrisonEnchants.NIGHT_VISION.calculatePrice((int) CustomEnchants.getEnchantLevel(pickaxe, "nightVision"), 1)),
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
}
