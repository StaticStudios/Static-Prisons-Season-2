package net.staticstudios.prisons.levelup;

import net.staticstudios.gui.GUICreator;
import net.staticstudios.gui.GUIUtils;
import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.gui.MainMenus;
import net.staticstudios.prisons.utils.Warps;
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
        GUICreator c = new GUICreator(45, "Prestige | Requires Mine Rank: " + ChatColor.BOLD + "Z");
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
                createGrayPlaceHolder(),
                createPrestigeButton(Material.COAL, c, 1, player, fromCommand),
                createPrestigeButton(Material.RAW_IRON, c, 5, player, fromCommand),
                createPrestigeButton(Material.RAW_GOLD, c, 10, player, fromCommand),
                createPrestigeButton(Material.DIAMOND, c, 50, player, fromCommand),
                createPrestigeButton(Material.EMERALD, c, 100, player, fromCommand),
                createPrestigeButton(Material.NETHERITE_INGOT, c, 500, player, fromCommand),
                createPrestigeButton(Material.AMETHYST_CLUSTER, c, 1000, player, fromCommand),
                createGrayPlaceHolder(),
                createGrayPlaceHolder(),
                createGrayPlaceHolder(),
                createGrayPlaceHolder(),
                createGrayPlaceHolder(),
                createGrayPlaceHolder(),
                createGrayPlaceHolder(),
                createGrayPlaceHolder(),
                createGrayPlaceHolder(),
                createGrayPlaceHolder(),
                createGrayPlaceHolder(),
                createGrayPlaceHolder(),
                createPrestigeButton(Material.ENDER_EYE, c, 10000, player, fromCommand),
                createPrestigeButton(Material.PRISMARINE_SHARD, c, 20000, player, fromCommand),
                createPrestigeButton(Material.PRISMARINE_CRYSTALS, c, 30000, player, fromCommand),
                createPrestigeButton(Material.BLAZE_POWDER, c, 40000, player, fromCommand),
                createPrestigeButton(Material.NETHER_STAR, c, 50000, player, fromCommand)
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
                "&b&l│ &b$" + PrisonUtils.prettyNum(LevelUp.getPrestigePrice(new PlayerData(player).getPrestige().longValue(), prestige)),
                "&b&l│ &b$" + PrisonUtils.addCommasToNumber(LevelUp.getPrestigePrice(new PlayerData(player).getPrestige().longValue(), prestige))
        ), (p, t) -> {
            PlayerData playerData = new PlayerData(p);
            if (playerData.getMineRank() < 25) {
                p.sendMessage(ChatColor.RED + "You must be mine rank " + ChatColor.BOLD + "Z " + ChatColor.RED + "in order to prestige!");
                return;
            }
            BigInteger price = BigInteger.valueOf(LevelUp.getPrestigePrice(playerData.getPrestige().longValue(), prestige));
            if (playerData.getMoney().compareTo(price) > -1) {
                BigInteger oldPrestigeRewards = playerData.getClaimedPrestigeRewardsAt().subtract(playerData.getPrestige()).divide(BigInteger.valueOf(250));
                playerData.removeMoney(price);
                playerData.addPrestige(BigInteger.valueOf(prestige));
                playerData.setMineRank(0);
                p.sendMessage(ChatColor.AQUA + "You have just prestiged!");
                if (playerData.getClaimedPrestigeRewardsAt().subtract(playerData.getPrestige()).divide(BigInteger.valueOf(250)).compareTo(oldPrestigeRewards) != 0) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "You have received reward(s) for prestiging &b250&r times! Claim them with &7&o/rewards"));
                }
                open(player, fromCommand);
                if (p.getWorld().equals(Constants.MINES_WORLD)) Warps.warpToSpawn(p);
            } else p.sendMessage(ChatColor.RED + "You do not have enough money to prestige!");
        }));
    }
}
