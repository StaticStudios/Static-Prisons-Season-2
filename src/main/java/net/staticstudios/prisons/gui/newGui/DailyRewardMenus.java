package net.staticstudios.prisons.gui.newGui;

import net.staticstudios.gui.GUICreator;
import net.staticstudios.gui.GUIUtils;
import net.staticstudios.prisons.customItems.CustomItems;
import net.staticstudios.prisons.data.dataHandling.PlayerData;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.time.Instant;
import java.util.List;

public class DailyRewardMenus extends GUIUtils {

    final static int SECONDS_BETWEEN_CLAIMS = 60 * 60 * 24;

    public static void mainMenu(Player player) {
        GUICreator c = new GUICreator(9, "Daily Rewards");
        PlayerData playerData = new PlayerData(player);
        if (playerData.getClaimedDailyRewardsRank1At() + SECONDS_BETWEEN_CLAIMS <= Instant.now().getEpochSecond()) {
            c.setItem(2, ench(c.createButton(Material.DIAMOND, "&bClaim Reward &e(Warrior)", List.of("&fYou can claim this reward every", "&f24 hours to get random crate keys!", "", "Requires &nWarrior rank&7 or higher"), (p, t) -> {
                if (!playerData.getPlayerRanks().contains("warrior")) {
                    p.sendMessage(ChatColor.RED + "You must have Warrior rank or higher to claim this!");
                    return;
                }
                ItemStack reward;
                switch (PrisonUtils.randomInt(1, 11)) {
                    default -> reward = CustomItems.getCommonCrateKey(5);
                    case 2, 3 -> reward = CustomItems.getCommonCrateKey(8);
                    case 4, 5 -> reward = CustomItems.getRareCrateKey(4);
                    case 6, 7 -> reward = CustomItems.getRareCrateKey(6);
                    case 8, 9 -> reward = CustomItems.getLegendaryCrateKey(2);
                    case 10 -> reward = CustomItems.getLegendaryCrateKey(3);
                    case 11 -> reward = CustomItems.getStaticCrateKey(1);
                }
                playerData.setClaimedDailyRewardsRank1At(Instant.now().getEpochSecond());
                PrisonUtils.Players.addToInventory(p, reward);
                p.sendMessage(ChatColor.WHITE + "You've been given " + reward.getAmount() + "x " + PrisonUtils.getPrettyItemName(reward));
                mainMenu(p);
            })));
        } else c.setItem(2, c.createButton(Material.DIAMOND, "&bClaim Reward &e(Warrior)", List.of("&fYou can claim this reward every", "&f24 hours to get random crate keys!", "",
                "&cClaim this again in: " + PrisonUtils.formatTime(playerData.getClaimedDailyRewardsRank1At() + SECONDS_BETWEEN_CLAIMS - Instant.now().getEpochSecond()), "", "Requires &nWarrior rank&7 or higher")));

        if (playerData.getClaimedDailyRewardsAt() + SECONDS_BETWEEN_CLAIMS <= Instant.now().getEpochSecond()) {
            c.setItem(4, ench(c.createButton(Material.NETHER_STAR, "&bClaim Reward &a(Member)", List.of("&fYou can claim this reward every", "&f24 hours to get random crate keys!"), (p, t) -> {
                ItemStack reward;
                switch (PrisonUtils.randomInt(1, 10)) {
                    default -> reward = CustomItems.getCommonCrateKey(5);
                    case 2, 3 -> reward = CustomItems.getCommonCrateKey(8);
                    case 4, 5 -> reward = CustomItems.getRareCrateKey(4);
                    case 6, 7 -> reward = CustomItems.getRareCrateKey(6);
                    case 8, 9 -> reward = CustomItems.getLegendaryCrateKey(2);
                    case 10 -> reward = CustomItems.getLegendaryCrateKey(3);
                }
                playerData.setClaimedDailyRewardsAt(Instant.now().getEpochSecond());
                PrisonUtils.Players.addToInventory(p, reward);
                p.sendMessage(ChatColor.WHITE + "You've been given " + reward.getAmount() + "x " + PrisonUtils.getPrettyItemName(reward));
                mainMenu(p);
            })));
        } else c.setItem(4, c.createButton(Material.NETHER_STAR, "&bClaim Reward &a(Member)", List.of("&fYou can claim this reward every", "&f24 hours to get random crate keys!", "",
                "&cClaim this again in: " + PrisonUtils.formatTime(playerData.getClaimedDailyRewardsAt() + SECONDS_BETWEEN_CLAIMS - Instant.now().getEpochSecond()))));

        if (playerData.getClaimedDailyRewardsRank2At() + SECONDS_BETWEEN_CLAIMS <= Instant.now().getEpochSecond()) {
            c.setItem(6, ench(c.createButton(Material.AMETHYST_SHARD, "&bClaim Reward &d(Static)", List.of("&fYou can claim this reward every", "&f24 hours to get random crate keys!", "", "Requires &nStatic rank&7 or higher"), (p, t) -> {
                if (!playerData.getPlayerRanks().contains("static")) {
                    p.sendMessage(ChatColor.RED + "You must have Static rank or higher to claim this!");
                    return;
                }
                ItemStack reward;
                switch (PrisonUtils.randomInt(1, 12)) {
                    default -> reward = CustomItems.getCommonCrateKey(5);
                    case 2, 3 -> reward = CustomItems.getCommonCrateKey(8);
                    case 4, 5 -> reward = CustomItems.getRareCrateKey(4);
                    case 6, 7 -> reward = CustomItems.getRareCrateKey(6);
                    case 8, 9 -> reward = CustomItems.getLegendaryCrateKey(2);
                    case 10 -> reward = CustomItems.getLegendaryCrateKey(3);
                    case 11 -> reward = CustomItems.getStaticCrateKey(1);
                    case 12 -> reward = CustomItems.getStaticpCrateKey(1);
                }
                playerData.setClaimedDailyRewardsRank2At(Instant.now().getEpochSecond());
                PrisonUtils.Players.addToInventory(p, reward);
                p.sendMessage(ChatColor.WHITE + "You've been given " + reward.getAmount() + "x " + PrisonUtils.getPrettyItemName(reward));
                mainMenu(p);
            })));
        } else c.setItem(6, c.createButton(Material.AMETHYST_SHARD, "&bClaim Reward &d(Static)", List.of("&fYou can claim this reward every", "&f24 hours to get random crate keys!", "",
                "&cClaim this again in: " + PrisonUtils.formatTime(playerData.getClaimedDailyRewardsRank2At() + SECONDS_BETWEEN_CLAIMS - Instant.now().getEpochSecond()), "", "Requires &nStatic rank&7 or higher")));
        c.fill(createGrayPlaceHolder());
        c.open(player);
        c.setOnCloseRun((p, t) -> MainMenus.open(p));
    }

}
