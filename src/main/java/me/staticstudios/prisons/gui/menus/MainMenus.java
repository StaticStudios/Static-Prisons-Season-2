package me.staticstudios.prisons.gui.menus;

import me.staticstudios.prisons.gui.GUI;
import me.staticstudios.prisons.gui.GUIPage;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class MainMenus {
    //Main menu
    public static void main() {
        GUIPage guiPage = new GUIPage() {
            @Override
            public void item22Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                GUI.getGUIPage("enchantsSelectPickaxe").open(player);
            }
            @Override
            public void item45Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                GUI.getGUIPage("rankUp").open(player);
            }
            @Override
            public void item13Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                GUI.getGUIPage("warps").open(player);
            }

        };
        guiPage.identifier = "main";

        guiPage.menuItems.add(GUI.createDarkGrayPlaceholderItem());
        guiPage.menuItems.add(GUI.createDarkGrayPlaceholderItem());
        guiPage.menuItems.add(GUI.createDarkGrayPlaceholderItem());
        guiPage.menuItems.add(GUI.createDarkGrayPlaceholderItem());
        guiPage.menuItems.add(GUI.createDarkGrayPlaceholderItem());
        guiPage.menuItems.add(GUI.createDarkGrayPlaceholderItem());
        guiPage.menuItems.add(GUI.createDarkGrayPlaceholderItem());
        guiPage.menuItems.add(GUI.createDarkGrayPlaceholderItem());
        guiPage.menuItems.add(GUI.createDarkGrayPlaceholderItem());


        guiPage.menuItems.add(GUI.createDarkGrayPlaceholderItem());
        guiPage.menuItems.add(GUI.createDarkGrayPlaceholderItem());
        guiPage.menuItems.add(GUI.createDarkGrayPlaceholderItem());
        guiPage.menuItems.add(GUI.createMenuItem(guiPage.identifier, Material.PLAYER_HEAD, ChatColor.LIGHT_PURPLE + "Your Stats", new String[]{ChatColor.GRAY + "-View your current stats"}));
        guiPage.menuItems.add(GUI.createMenuItem(guiPage.identifier, Material.COMPASS, ChatColor.RED + "Warps", new String[]{ChatColor.GRAY + "-Spawn", ChatColor.GRAY + "-Public Mines (A-Z)", ChatColor.GRAY + "-Prestige Mines", ChatColor.GRAY + "-Ranked Mines", ChatColor.GRAY + "-PvP Arena", ChatColor.GRAY + "-Parkour", ChatColor.GRAY + "-Crates"}));
        guiPage.menuItems.add(GUI.createRedPlaceholderItem());
        guiPage.menuItems.add(GUI.createDarkGrayPlaceholderItem());
        guiPage.menuItems.add(GUI.createDarkGrayPlaceholderItem());
        guiPage.menuItems.add(GUI.createDarkGrayPlaceholderItem());

        guiPage.menuItems.add(GUI.createDarkGrayPlaceholderItem());
        guiPage.menuItems.add(GUI.createMenuItem(guiPage.identifier, Material.PAPER, ChatColor.GOLD + "Auction House", new String[]{ChatColor.GRAY + "-View/buy other players' auctions", ChatColor.GRAY + "-Remove your current auction(s)"}));
        guiPage.menuItems.add(GUI.createMenuItem(guiPage.identifier, Material.OAK_SIGN, ChatColor.GREEN + "Player Shops", new String[]{ChatColor.GRAY + "-Manage your player shop", ChatColor.GRAY + "-Buy/sell items to/from a player's shop"}));
        guiPage.menuItems.add(GUI.createMenuItem(guiPage.identifier, Material.GOLDEN_APPLE, ChatColor.AQUA + "Your Multipliers", new String[]{ChatColor.GRAY + "-Active your multiplier(s)"}));
        guiPage.menuItems.add(GUI.createMenuItem(guiPage.identifier, Material.DIAMOND_PICKAXE, ChatColor.LIGHT_PURPLE + "Enchants", new String[]{ChatColor.GRAY + "-Enchant a pickaxe", ChatColor.GRAY + "-Upgrade your backpack"}));
        guiPage.menuItems.add(GUI.createMenuItem(guiPage.identifier, Material.IRON_BARS, ChatColor.DARK_GRAY + "Cells", new String[]{ChatColor.GRAY + "-Warp to your cell", ChatColor.GRAY + "-Access the cell blocks shop"}));
        guiPage.menuItems.add(GUI.createMenuItem(guiPage.identifier, Material.CHEST, ChatColor.GREEN + "Private Vaults", new String[]{ChatColor.GRAY + "-Open one of your private vaults"}));
        guiPage.menuItems.add(GUI.createMenuItem(guiPage.identifier, Material.ENDER_CHEST, ChatColor.GREEN + "Ender Chest", new String[]{ChatColor.GRAY + "-Open your ender chest"}));
        guiPage.menuItems.add(GUI.createDarkGrayPlaceholderItem());

        guiPage.menuItems.add(GUI.createDarkGrayPlaceholderItem());
        guiPage.menuItems.add(GUI.createDarkGrayPlaceholderItem());
        guiPage.menuItems.add(GUI.createDarkGrayPlaceholderItem());
        guiPage.menuItems.add(GUI.createMenuItem(guiPage.identifier, Material.DIAMOND_ORE, ChatColor.GREEN + "Private Mines", new String[]{ChatColor.GRAY + "-Manage your private mine", ChatColor.GRAY + "-Go to your cellmates' private mines"}));
        guiPage.menuItems.add(GUI.createMenuItem(guiPage.identifier, Material.AMETHYST_CLUSTER, ChatColor.LIGHT_PURPLE + "Your Rewards", new String[]{ChatColor.GRAY + "Claim your rewards"}));
        guiPage.menuItems.add(GUI.createMenuItem(guiPage.identifier, Material.SUNFLOWER, ChatColor.GOLD + "Casino", new String[]{ChatColor.GRAY + "-Coin Flip", ChatColor.GRAY + "-Token Flip"}));
        guiPage.menuItems.add(GUI.createDarkGrayPlaceholderItem());
        guiPage.menuItems.add(GUI.createDarkGrayPlaceholderItem());
        guiPage.menuItems.add(GUI.createDarkGrayPlaceholderItem());

        guiPage.menuItems.add(GUI.createDarkGrayPlaceholderItem());
        guiPage.menuItems.add(GUI.createDarkGrayPlaceholderItem());
        guiPage.menuItems.add(GUI.createDarkGrayPlaceholderItem());
        guiPage.menuItems.add(GUI.createDarkGrayPlaceholderItem());
        guiPage.menuItems.add(GUI.createDarkGrayPlaceholderItem());
        guiPage.menuItems.add(GUI.createDarkGrayPlaceholderItem());
        guiPage.menuItems.add(GUI.createDarkGrayPlaceholderItem());
        guiPage.menuItems.add(GUI.createDarkGrayPlaceholderItem());
        guiPage.menuItems.add(GUI.createDarkGrayPlaceholderItem());


        guiPage.menuItems.add(GUI.createMenuItem(guiPage.identifier, Material.EMERALD, ChatColor.GREEN + "Rank Up", new String[]{ChatColor.GRAY + "-Rank up to unlock new mines!"}));
        guiPage.menuItems.add(GUI.createLightGrayPlaceholderItem());
        guiPage.menuItems.add(GUI.createLightGrayPlaceholderItem());
        guiPage.menuItems.add(GUI.createLightGrayPlaceholderItem());
        guiPage.menuItems.add(GUI.createMenuItem(guiPage.identifier, Material.REDSTONE_TORCH, ChatColor.RED + "Settings", new String[]{ChatColor.GRAY + "Manage your settings"}));
        guiPage.menuItems.add(GUI.createLightGrayPlaceholderItem());
        guiPage.menuItems.add(GUI.createLightGrayPlaceholderItem());
        guiPage.menuItems.add(GUI.createLightGrayPlaceholderItem());
        guiPage.menuItems.add(GUI.createMenuItem(guiPage.identifier, Material.NETHER_STAR, ChatColor.LIGHT_PURPLE + "Prestige", new String[]{ChatColor.GRAY + "-Prestige to unlock new mines!", "", ChatColor.GRAY + "-This will set you back to mine A"}));


        guiPage.guiTitle = ChatColor.translateAlternateColorCodes('&', "&9Static &bPrisons");
        guiPage.onCloseGoToMenu = null;
        guiPage.register();
    }
}
