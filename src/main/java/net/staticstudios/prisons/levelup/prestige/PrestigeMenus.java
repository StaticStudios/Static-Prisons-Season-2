package net.staticstudios.prisons.levelup.prestige;

import net.kyori.adventure.text.Component;
import net.staticstudios.gui.GUICreator;
import net.staticstudios.gui.GUIUtils;
import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.gui.MainMenus;
import net.staticstudios.prisons.levelup.LevelUp;
import net.staticstudios.prisons.utils.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.math.BigInteger;
import java.util.List;

public class PrestigeMenus extends GUIUtils {

    public static void newOpen(Player player, boolean fromCommand) {
        PlayerData playerData = new PlayerData(player);
        GUICreator c = new GUICreator(27, "Prestige | Requires Mine Rank: " + ChatColor.BOLD + "Z");

        c.setItem(10, ench(c.createButton(Material.EMERALD, "&a&lPrestige " + PrisonUtils.addCommasToNumber(playerData.getPrestige()), List.of(
                "Prestiging again will cost you $" + PrisonUtils.prettyNum(LevelUp.getPrestigePrice(playerData.getPrestige().longValue(), 1)) + " and",
                "50,000 Tokens. Each prestige also requires",
                "a certain amount of raw blocks mined. Your next",
                "prestige will require you to have " + PrisonUtils.addCommasToNumber(BigInteger.valueOf(25000).multiply(playerData.getPrestige().add(BigInteger.ONE))),
                "raw blocks mined."
        ))));

        c.setItem(13, c.createButton(Material.NETHER_STAR, "&b&lLet's do it!", List.of(
                "&oReady to prestige? Click here!",
                "",
                "&d&l│ &dCosts: &f$" + PrisonUtils.prettyNum(LevelUp.getPrestigePrice(playerData.getPrestige().longValue(), 1)),
                "&d&l│ &dCosts: &f50,000 Tokens"
        ), (p, t) -> {
            if (playerData.getMineRank() < 25) {
                p.sendMessage(Prefix.PRESTIGE.append(Component.text("You must be rank Z to prestige!").color(ComponentUtil.RED)));
                return;
            }
            if (playerData.getMoney().compareTo(BigInteger.valueOf(LevelUp.getPrestigePrice(playerData.getPrestige().longValue(), 1))) < 0) {
                p.sendMessage(Prefix.PRESTIGE.append(Component.text("You don't have enough money to prestige!").color(ComponentUtil.RED)));
                return;
            }
            if (playerData.getTokens().compareTo(BigInteger.valueOf(50000)) < 0) {
                p.sendMessage(Prefix.PRESTIGE.append(Component.text("You don't have enough tokens to prestige!").color(ComponentUtil.RED)));
                return;
            }
            //Players need to have 25K * prestige to go to raw blocks mined to prestige
            if (playerData.getRawBlocksMined().compareTo(BigInteger.valueOf(25000 * (playerData.getPrestige().longValue() + 1))) < 0) {
                p.sendMessage(Prefix.PRESTIGE.append(Component.text("You don't have enough raw blocks mined to prestige! You need " + PrisonUtils.addCommasToNumber(25000 * (playerData.getPrestige().longValue() + 1)) + " raw blocks mined!").color(ComponentUtil.RED)));
                return;
            }
            playerData.removeMoney(BigInteger.valueOf(LevelUp.getPrestigePrice(playerData.getPrestige().longValue(), 1)));
            playerData.removeTokens(BigInteger.valueOf(50000));
            playerData.setMineRank(0);
            playerData.addPrestige(BigInteger.ONE);
            Bukkit.broadcast(Prefix.PRESTIGE.append(Component.text(player.getName()).color(ComponentUtil.AQUA)).append(Component.text(" has prestiged " + PrisonUtils.addCommasToNumber(playerData.getPrestige()) + " time(s)!")));
            if (p.getWorld().equals(Constants.MINES_WORLD)) Warps.warpToSpawn(p);
            else open(p, fromCommand);
        }));
        c.setItem(16, ench(c.createButton(Material.ANVIL, "&d&lWhat happens when I prestige?", List.of(
                "You must be mine rank Z to prestige.",
                "Each time you prestige, rank up costs will",
                "increase by &a&l+25%. &7Prestiging will give you",
                "access to new mines and will unlock new features.",
                "After you prestige, you will go back to rank A.",
                "",
                "&bReady to do it?"
        ))));

        c.fill(createGrayPlaceHolder());
        c.open(player);
        c.setOnCloseRun((p, t) -> {
            if (!fromCommand) MainMenus.open(p);
        });
    }

    public static void open(Player player, boolean fromCommand) {
        newOpen(player, fromCommand);
        if (true) return;
        GUICreator c = new GUICreator(45, "Prestige | Requires Mine Rank: " + ChatColor.BOLD + "Z");
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
