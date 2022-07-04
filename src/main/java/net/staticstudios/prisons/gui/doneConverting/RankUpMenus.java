package net.staticstudios.prisons.gui.doneConverting;

import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.gui.GUI;
import net.staticstudios.prisons.gui.GUIPage;
import net.staticstudios.prisons.misc.Warps;
import net.staticstudios.prisons.rankup.RankUp;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.ArrayList;

public class RankUpMenus {
    static final Material[] rankUpMaterials = {
            Material.STONE,
            Material.COBBLESTONE,
            Material.COAL_ORE,
            Material.IRON_ORE,
            Material.GOLD_ORE,
            Material.DIAMOND_ORE,
            Material.EMERALD_ORE,
            Material.COAL_BLOCK,
            Material.IRON_BLOCK,
            Material.GOLD_BLOCK,
            Material.DIAMOND_BLOCK,
            Material.EMERALD_BLOCK,
            Material.BEDROCK
    };
    //Rank up menu
    public static void RankUp() {
        GUIPage guiPage = new GUIPage() {
            @Override
            public void onOpen(Player player) {
                PlayerData playerData = new PlayerData(player);
                menuItems = new ArrayList<>();

                for (int i = 0; i < 26; i++) {
                    if (playerData.getMineRank() >= i) {
                        menuItems.add(GUI.createMenuItem(identifier, rankUpMaterials[i / 2], ChatColor.GREEN + "" + ChatColor.BOLD + "Mine " + PrisonUtils.getMineRankLetterFromMineRank(i), ChatColor.AQUA + "You have this mine unlocked!", "", ChatColor.GREEN + "Click to warp!"));
                    } else menuItems.add(GUI.createMenuItem(identifier, Material.RED_STAINED_GLASS, ChatColor.RED + "" + ChatColor.BOLD + "Mine " + PrisonUtils.getMineRankLetterFromMineRank(i), ChatColor.RED + "You do not have this mine unlocked!", ChatColor.GREEN + "" + ChatColor.BOLD + "Costs: ",
                            "" + ChatColor.GREEN + ChatColor.BOLD + "│ " + ChatColor.GREEN + "$" + PrisonUtils.prettyNum(RankUp.calculatePriceToRankUpTo(playerData, i)),
                            "" + ChatColor.GREEN + ChatColor.BOLD + "│ " + ChatColor.GREEN + "$" + PrisonUtils.addCommasToNumber(RankUp.calculatePriceToRankUpTo(playerData, i)), "", ChatColor.GREEN + "Click to unlock!"));

                }

            }
            @Override
            public void itemClicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                PlayerData playerData = new PlayerData(player);
                if (playerData.getMineRank() >= e.getSlot()) {
                    Warps.warpToMine(player, e.getSlot());
                    return;
                }
                if (playerData.getMoney().compareTo(RankUp.calculatePriceToRankUpTo(playerData, e.getSlot())) > -1) {
                    player.sendMessage(ChatColor.GREEN + "You have just ranked up! " + ChatColor.AQUA + PrisonUtils.getMineRankLetterFromMineRank(playerData.getMineRank()) + " -> " + PrisonUtils.getMineRankLetterFromMineRank(e.getSlot()));
                    playerData.removeMoney(RankUp.calculatePriceToRankUpTo(playerData, e.getSlot()));
                    playerData.setMineRank(e.getSlot());
                    open(player);
                    return;
                }
                player.sendMessage(ChatColor.RED + "You do not have enough money to rank up! To rank up, it will cost: $" + PrisonUtils.addCommasToNumber(RankUp.calculatePriceToRankUpTo(playerData, e.getSlot())));
            }
        };
        guiPage.identifier = "rankUp";
        guiPage.guiTitle = ChatColor.translateAlternateColorCodes('&', "&dRank Up");
        guiPage.onCloseGoToMenu = "main";
        guiPage.register();
    }
}