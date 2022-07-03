package net.staticstudios.prisons.gui.newGui;

import net.staticstudios.gui.GUICreator;
import net.staticstudios.gui.GUIUtils;
import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.misc.Warps;
import net.staticstudios.prisons.rankup.RankUp;
import net.staticstudios.prisons.utils.Constants;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.math.BigInteger;
import java.util.List;

public class PrestigeMenus extends GUIUtils {

    public static void open(Player player, boolean fromCommand) {
        GUICreator c = new GUICreator(27, "Prestige | Requires Mine Rank: " + ChatColor.BOLD + "Z");
        PlayerData playerData = new PlayerData(player);
        c.setItems(
                createGrayPlaceHolder(),
                createGrayPlaceHolder(),
                createGrayPlaceHolder(),
                createGrayPlaceHolder(),
                createGrayPlaceHolder(),
                createGrayPlaceHolder(),
                createGrayPlaceHolder(),
                createGrayPlaceHolder(),
                createGrayPlaceHolder(),
                createPrestigeButton(Material.COAL, c, 1, player, fromCommand),
                createPrestigeButton(Material.RAW_IRON, c, 5, player, fromCommand),
                createPrestigeButton(Material.RAW_GOLD, c, 10, player, fromCommand),
                createPrestigeButton(Material.DIAMOND, c, 50, player, fromCommand),
                createPrestigeButton(Material.EMERALD, c, 100, player, fromCommand),
                createPrestigeButton(Material.NETHERITE_INGOT, c, 500, player, fromCommand),
                createPrestigeButton(Material.AMETHYST_CLUSTER, c, 1000, player, fromCommand),
                createPrestigeButton(Material.NETHER_STAR, c, 5000, player, fromCommand),
                createPrestigeButton(Material.BEACON, c, 10000, player, fromCommand)
        );
        c.fill(createGrayPlaceHolder());
        c.open(player);
        c.setOnCloseRun((p, t) -> {
            if (!fromCommand) MainMenus.open(p);
        });
    }

    static ItemStack createPrestigeButton(Material icon, GUICreator c, int prestige, Player player, boolean fromCommand) {
        return ench(c.createButton(icon, "&d&lPrestige " + PrisonUtils.addCommasToNumber(prestige) + " Time" + (prestige > 1 ? "s" : ""), List.of(
                "",
                "&bCosts:",
                "&b&l│ &b$" + PrisonUtils.prettyNum(RankUp.getPrestigePrice(new PlayerData(player).getPrestige(), prestige)),
                "&b&l│ &b$" + PrisonUtils.addCommasToNumber(RankUp.getPrestigePrice(new PlayerData(player).getPrestige(), prestige))
        ), (p, t) -> {
            PlayerData playerData = new PlayerData(p);
            if (playerData.getMineRank() < 25) {
                p.sendMessage(ChatColor.RED + "You must be mine rank " + ChatColor.BOLD + "Z " + ChatColor.RED + "in order to prestige!");
                return;
            }
            if (playerData.getMoney().compareTo(RankUp.getPrestigePrice(playerData.getPrestige(), prestige)) > -1) {
                playerData.removeMoney(RankUp.getPrestigePrice(playerData.getPrestige(), prestige));
                playerData.addPrestige(BigInteger.valueOf(prestige));
                playerData.setMineRank(0);
                p.sendMessage(ChatColor.AQUA + "You have just prestiged!");
                open(player, fromCommand);
                if (p.getWorld().equals(Constants.MINES_WORLD)) Warps.warpToSpawn(p);
            } else p.sendMessage(ChatColor.RED + "You do not have enough money to prestige!");
        }));
    }
}
