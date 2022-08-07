package net.staticstudios.prisons.levelup;

import net.staticstudios.gui.GUICreator;
import net.staticstudios.gui.GUIUtils;
import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.gui.MainMenus;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;

public class RankUpMenus extends GUIUtils {

    static final Material[] RANK_UP_ICONS = {
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

    public static void open(Player player, boolean fromCommand) {
        GUICreator c = new GUICreator(27, "Rank Up");
        PlayerData playerData = new PlayerData(player);

        for (int i = 0; i < 26; i++) {
            if (playerData.getMineRank() >= i) {
                c.addItem(c.createButton(RANK_UP_ICONS[i / 2], "&a&lMine " + PrisonUtils.getMineRankLetterFromMineRank(i) + " &a| Unlocked", List.of("", "&aClick to warp!"), (p, t) -> {
                    p.sendMessage(ChatColor.GREEN + "You already have this mine unlocked!");
                }));
            } else {
                int finalI = i;
                c.addItem(c.createButton(Material.RED_STAINED_GLASS, "&c&lMine " + PrisonUtils.getMineRankLetterFromMineRank(i) + " &c| Locked", List.of("", "&a&lCosts:",
                        "&a&l│ &a$" + PrisonUtils.prettyNum(LevelUp.calculatePriceToRankUpTo(playerData, i)), "&a&l│ &a$" + PrisonUtils.addCommasToNumber(LevelUp.calculatePriceToRankUpTo(playerData, i)), "", "&cClick to unlock!"), (p, t) -> {
                    long rankUpCost = LevelUp.calculatePriceToRankUpTo(playerData, finalI);
                    if (playerData.getMoney() >= rankUpCost) {
                        playerData.removeMoney(rankUpCost);
                        playerData.setMineRank(finalI);
                        p.sendMessage(ChatColor.AQUA + "You ranked up to Mine " + PrisonUtils.getMineRankLetterFromMineRank(finalI) + "!");
                    } else p.sendMessage(ChatColor.RED + "You don't have enough money to rank up!");
                    open(p, fromCommand);
                }));
            }
        }
        int maxRank = 0;
        for (int i = 25; i > 0; i--) {
            if (playerData.getMoney() >= LevelUp.calculatePriceToRankUpTo(playerData, i)) {
                maxRank = i;
                break;
            }
        }
        int finalMaxRank = maxRank;
        c.addItem(c.createButton(Material.NETHER_STAR, "&b&lRank Up Max", List.of("&bRank Up to Mine:&f " + PrisonUtils.getMineRankLetterFromMineRank(maxRank), "",
                "&b&lCosts:",
                "&b&l│ &f$" + PrisonUtils.prettyNum(LevelUp.calculatePriceToRankUpTo(playerData, maxRank)),
                "&b&l│ &f$" + PrisonUtils.addCommasToNumber(LevelUp.calculatePriceToRankUpTo(playerData, maxRank))), (p, t) -> {
            if (playerData.getMineRank() == finalMaxRank) {
                p.sendMessage(ChatColor.RED + "You do not have enough money to rank up!");
                return;
            }
            long rankUpCost = LevelUp.calculatePriceToRankUpTo(playerData, finalMaxRank);
            if (playerData.getMoney() >= rankUpCost) {
                playerData.removeMoney(rankUpCost);
                playerData.setMineRank(finalMaxRank);
                p.sendMessage(ChatColor.AQUA + "You ranked up to Mine " + PrisonUtils.getMineRankLetterFromMineRank(finalMaxRank) + "!");
            } else p.sendMessage(ChatColor.RED + "You don't have enough money to rank up!");
            open(p, fromCommand);
        }));
        c.open(player);
        c.setOnCloseRun((p, t) -> {
            if (!fromCommand) MainMenus.open(p);
        });
    }
}
