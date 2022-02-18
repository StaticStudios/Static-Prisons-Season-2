package me.staticstudios.prisons.gui.menus;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import me.staticstudios.prisons.data.serverData.PlayerData;
import me.staticstudios.prisons.gui.GUI;
import me.staticstudios.prisons.gui.GUIPage;
import me.staticstudios.prisons.mines.MineManager;
import me.staticstudios.prisons.mines.PrivateMine;
import me.staticstudios.prisons.utils.StaticVars;
import me.staticstudios.prisons.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.math.BigInteger;
import java.time.Instant;
import java.util.ArrayList;

public class PrivateMinesMenus {
    //pmines menu
    public static void main() {
        GUIPage guiPage = new GUIPage() {
            @Override
            public void onOpen(Player player) {
                menuItems = new ArrayList<>();
                menuItems.add(GUI.createEnchantedMenuItem(identifier, Material.COMPASS, ChatColor.AQUA + "" + ChatColor.BOLD + "Go To", ChatColor.GRAY + "" + ChatColor.ITALIC + "Warp to your private mine!"));
                menuItems.add(GUI.createEnchantedMenuItem(identifier, new PlayerData(player).getPrivateMineMat(), ChatColor.GREEN + "" + ChatColor.BOLD + "Change Block", ChatColor.GRAY + "" + ChatColor.ITALIC + "Change the block that is in your private mine!"));
                menuItems.add(GUI.createLightGrayPlaceholderItem());
                menuItems.add(GUI.createLightGrayPlaceholderItem());
                menuItems.add(GUI.createLightGrayPlaceholderItem());
                menuItems.add(GUI.createLightGrayPlaceholderItem());
                menuItems.add(GUI.createLightGrayPlaceholderItem());
                menuItems.add(GUI.createEnchantedMenuItem(identifier, Material.AMETHYST_SHARD, ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Refill", ChatColor.GRAY + "" + ChatColor.ITALIC + "Refill your private mine!", "", ChatColor.GRAY + "" + ChatColor.ITALIC + "Type \"/pmine refill\" to refill your mine faster!"));
            }

            @Override
            public void item0Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                PlayerData playerData = new PlayerData(player);
                if (!MineManager.checkIfMineExists("privateMine-" + player.getUniqueId())) {
                    PrivateMine.create(playerData.getPrivateMineSquareSize(), playerData.getPrivateMineMat(), player);
                } else MineManager.getPrivateMine("privateMine-" + player.getUniqueId()).goTo(player);
            }
            @Override
            public void item1Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                GUI.getGUIPage("privateMinesChangeBlock").open(player);
            }

            @Override
            public void item7Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                PlayerData playerData = new PlayerData(player);
                if (!MineManager.checkIfMineExists("privateMine-" + player.getUniqueId())) {
                    PrivateMine.create(playerData.getPrivateMineSquareSize(), playerData.getPrivateMineMat(), player);
                } else {
                    PrivateMine mine = MineManager.getPrivateMine("privateMine-" + player.getUniqueId());
                    if (mine.getLastRefilledAt() + 30 < Instant.now().getEpochSecond()) {
                        mine.refill();
                    } else player.sendMessage(ChatColor.RED + "Please wait " + (mine.getLastRefilledAt() + 30 - Instant.now().getEpochSecond()) + " second(s) before refilling this mine!");
                }
            }
        };
        guiPage.identifier = "privateMines";
        guiPage.guiTitle = ChatColor.translateAlternateColorCodes('&', "&dYour Private Mine");
        guiPage.onCloseGoToMenu = "main";
        guiPage.register();
    }

    //change pmine block menu
    public static void changeBlock() {
        GUIPage guiPage = new GUIPage() {
            @Override
            public void onOpen(Player player) {
                PlayerData playerData = new PlayerData(player);
                menuItems = new ArrayList<>();
                String lore = ChatColor.GRAY + "" + ChatColor.ITALIC + "Click to change your private mine block to this!";
                Material type;
                type = Material.STONE;
                menuItems.add(GUI.createMenuItem(identifier, type, Utils.getPrettyItemName(new ItemStack(type)), lore));
                type = Material.COBBLESTONE;
                menuItems.add(GUI.createMenuItem(identifier, type, Utils.getPrettyItemName(new ItemStack(type)), lore));
                type = Material.COAL_ORE;
                menuItems.add(GUI.createMenuItem(identifier, type, Utils.getPrettyItemName(new ItemStack(type)), lore));
                type = Material.IRON_ORE;
                menuItems.add(GUI.createMenuItem(identifier, type, Utils.getPrettyItemName(new ItemStack(type)), lore));
                type = Material.GOLD_ORE;
                menuItems.add(GUI.createMenuItem(identifier, type, Utils.getPrettyItemName(new ItemStack(type)), lore));
                type = Material.LAPIS_ORE;
                menuItems.add(GUI.createMenuItem(identifier, type, Utils.getPrettyItemName(new ItemStack(type)), lore));
                type = Material.REDSTONE_ORE;
                menuItems.add(GUI.createMenuItem(identifier, type, Utils.getPrettyItemName(new ItemStack(type)), lore));
                type = Material.DIAMOND_ORE;
                menuItems.add(GUI.createMenuItem(identifier, type, Utils.getPrettyItemName(new ItemStack(type)), lore));
                type = Material.EMERALD_ORE;
                menuItems.add(GUI.createMenuItem(identifier, type, Utils.getPrettyItemName(new ItemStack(type)), lore));
                type = Material.COAL_BLOCK;
                menuItems.add(GUI.createMenuItem(identifier, type, Utils.getPrettyItemName(new ItemStack(type)), lore));
                type = Material.IRON_BLOCK;
                menuItems.add(GUI.createMenuItem(identifier, type, Utils.getPrettyItemName(new ItemStack(type)), lore));
                type = Material.GOLD_BLOCK;
                menuItems.add(GUI.createMenuItem(identifier, type, Utils.getPrettyItemName(new ItemStack(type)), lore));
                type = Material.LAPIS_BLOCK;
                menuItems.add(GUI.createMenuItem(identifier, type, Utils.getPrettyItemName(new ItemStack(type)), lore));
                type = Material.REDSTONE_BLOCK;
                menuItems.add(GUI.createMenuItem(identifier, type, Utils.getPrettyItemName(new ItemStack(type)), lore));
                type = Material.DIAMOND_BLOCK;
                menuItems.add(GUI.createMenuItem(identifier, type, Utils.getPrettyItemName(new ItemStack(type)), lore));
                type = Material.EMERALD_BLOCK;
                menuItems.add(GUI.createMenuItem(identifier, type, Utils.getPrettyItemName(new ItemStack(type)), lore));
                if (!(playerData.getPrestige().compareTo(BigInteger.valueOf(StaticVars.amountOfPrestigesRequiredForPrestigeMines[2])) < 0 || playerData.getPlayerRanks().contains("warrior"))) return;
                type = Material.NETHERRACK;
                menuItems.add(GUI.createMenuItem(identifier, type, Utils.getPrettyItemName(new ItemStack(type)), lore));
                if (!(playerData.getPrestige().compareTo(BigInteger.valueOf(StaticVars.amountOfPrestigesRequiredForPrestigeMines[2])) < 0 || playerData.getPlayerRanks().contains("master"))) return;
                type = Material.NETHER_BRICKS;
                menuItems.add(GUI.createMenuItem(identifier, type, Utils.getPrettyItemName(new ItemStack(type)), lore));
                if (!(playerData.getPrestige().compareTo(BigInteger.valueOf(StaticVars.amountOfPrestigesRequiredForPrestigeMines[2])) < 0 || playerData.getPlayerRanks().contains("master"))) return;
                type = Material.QUARTZ_BLOCK;
                menuItems.add(GUI.createMenuItem(identifier, type, Utils.getPrettyItemName(new ItemStack(type)), lore));
                if (!(playerData.getPrestige().compareTo(BigInteger.valueOf(StaticVars.amountOfPrestigesRequiredForPrestigeMines[2])) < 0 || playerData.getPlayerRanks().contains("mythic"))) return;
                type = Material.END_STONE;
                menuItems.add(GUI.createMenuItem(identifier, type, Utils.getPrettyItemName(new ItemStack(type)), lore));
                if (!(playerData.getPrestige().compareTo(BigInteger.valueOf(StaticVars.amountOfPrestigesRequiredForPrestigeMines[2])) < 0 || playerData.getPlayerRanks().contains("mythic"))) return;
                type = Material.OBSIDIAN;
                menuItems.add(GUI.createMenuItem(identifier, type, Utils.getPrettyItemName(new ItemStack(type)), lore));
                if (!(playerData.getPrestige().compareTo(BigInteger.valueOf(StaticVars.amountOfPrestigesRequiredForPrestigeMines[2])) < 0 || playerData.getPlayerRanks().contains("static"))) return;
                type = Material.CRYING_OBSIDIAN;
                menuItems.add(GUI.createMenuItem(identifier, type, Utils.getPrettyItemName(new ItemStack(type)), lore));
                if (!(playerData.getPrestige().compareTo(BigInteger.valueOf(StaticVars.amountOfPrestigesRequiredForPrestigeMines[2])) < 0 || playerData.getPlayerRanks().contains("staticp"))) return;
                type = Material.PRISMARINE;
                menuItems.add(GUI.createMenuItem(identifier, type, Utils.getPrettyItemName(new ItemStack(type)), lore));
                if (!(playerData.getPrestige().compareTo(BigInteger.valueOf(StaticVars.amountOfPrestigesRequiredForPrestigeMines[2])) < 0 || playerData.getPlayerRanks().contains("staticp"))) return;
                type = Material.AMETHYST_BLOCK;
                menuItems.add(GUI.createMenuItem(identifier, type, Utils.getPrettyItemName(new ItemStack(type)), lore));
                if (playerData.getPrestige().compareTo(BigInteger.valueOf(StaticVars.amountOfPrestigesRequiredForPrestigeMines[10])) < 0) return;
                type = Material.DEEPSLATE_COAL_ORE;
                menuItems.add(GUI.createMenuItem(identifier, type, Utils.getPrettyItemName(new ItemStack(type)), lore));
                if (playerData.getPrestige().compareTo(BigInteger.valueOf(StaticVars.amountOfPrestigesRequiredForPrestigeMines[11])) < 0) return;
                type = Material.DEEPSLATE_IRON_ORE;
                menuItems.add(GUI.createMenuItem(identifier, type, Utils.getPrettyItemName(new ItemStack(type)), lore));
                if (playerData.getPrestige().compareTo(BigInteger.valueOf(StaticVars.amountOfPrestigesRequiredForPrestigeMines[12])) < 0) return;
                type = Material.DEEPSLATE_GOLD_ORE;
                menuItems.add(GUI.createMenuItem(identifier, type, Utils.getPrettyItemName(new ItemStack(type)), lore));
                if (playerData.getPrestige().compareTo(BigInteger.valueOf(StaticVars.amountOfPrestigesRequiredForPrestigeMines[13])) < 0) return;
                type = Material.DEEPSLATE_DIAMOND_ORE;
                menuItems.add(GUI.createMenuItem(identifier, type, Utils.getPrettyItemName(new ItemStack(type)), lore));
                if (playerData.getPrestige().compareTo(BigInteger.valueOf(StaticVars.amountOfPrestigesRequiredForPrestigeMines[14])) < 0) return;
                type = Material.DEEPSLATE_EMERALD_ORE;
                menuItems.add(GUI.createMenuItem(identifier, type, Utils.getPrettyItemName(new ItemStack(type)), lore));
            }
            @Override
            public void itemClicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                PlayerData playerData = new PlayerData(player);
                playerData.setPrivateMineMat(e.getCurrentItem().getType());
                if (MineManager.checkIfMineExists("privateMine-" + player.getUniqueId())) MineManager.getPrivateMine("privateMine-" + player.getUniqueId()).setType(BukkitAdapter.asBlockType(playerData.getPrivateMineMat()));
                player.sendMessage(ChatColor.LIGHT_PURPLE + "Done! Refill your private mine to see this take an affect!");
                GUI.getGUIPage("privateMines").open(player);
            }
        };
        guiPage.identifier = "privateMinesChangeBlock";
        guiPage.guiTitle = ChatColor.translateAlternateColorCodes('&', "&dChange Your Private Mine Block");
        guiPage.onCloseGoToMenu = "privateMines";
        guiPage.register();
    }
}
