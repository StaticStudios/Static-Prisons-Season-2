package net.staticstudios.prisons.gui;

import net.md_5.bungee.api.ChatColor;
import net.staticstudios.gui.GUICreator;
import net.staticstudios.gui.GUIUtils;
import net.staticstudios.prisons.customItems.CustomItems;
import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.utils.PrisonUtils;
import net.staticstudios.utils.WeightedElements;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.math.BigInteger;
import java.util.List;

public class RewardsMenus extends GUIUtils {

    public static final String PREFIX = ChatColor.translateAlternateColorCodes('&', "&b&lRewards &8&l>> &r");

    public static void open(Player player) {
        GUICreator c = new GUICreator(27, "Your Rewards");
        PlayerData playerData = new PlayerData(player);
        BigInteger difference = playerData.getPrestige().subtract(playerData.getClaimedPrestigeRewardsAt());
        BigInteger rewards = difference.divide(BigInteger.valueOf(250));
        c.setItem(13, ench(c.createButton(Material.AMETHYST_CLUSTER, "&d&lPrestige Rewards", List.of("You have " + PrisonUtils.addCommasToNumber(rewards) + " reward(s) to claim!", "You get a new one every 250 prestiges!", "", "&b&oClick to claim"), (p, t) -> {
            if (rewards.compareTo(BigInteger.ZERO) == 0) {
                p.sendMessage(PREFIX + ChatColor.RED + "You have no rewards to claim!");
                return;
            }
            playerData.setClaimedPrestigeRewardsAt(playerData.getPrestige());
            long r = rewards.longValue();
            while (r > 0) {
                ItemStack reward = new WeightedElements<ItemStack>()
                        .add(CustomItems.getCommonCrateKey(5), 20)
                        .add(CustomItems.getRareCrateKey(4), 20)
                        .add(CustomItems.getEpicCrateKey(2), 10)
                        .add(CustomItems.getEpicCrateKey(3), 5)
                        .add(CustomItems.getEpicCrateKey(5), 5)
                        .add(CustomItems.getLegendaryCrateKey(1), 15)
                        .add(CustomItems.getLegendaryCrateKey(2), 5)
                        .add(CustomItems.getLegendaryCrateKey(3), 5)
                        .add(CustomItems.getStaticCrateKey(1), 5)
                        .add(CustomItems.getStaticCrateKey(2), 5)
                        .add(CustomItems.getStaticpCrateKey(1), 2)
                        .add(CustomItems.getPickaxeCrateKey(3), 1)
                        .add(CustomItems.getPickaxeCrateKey(1), 1)
                        .add(CustomItems.getKitCrateKey(2), 1)
                        .getRandom(); //
                PrisonUtils.Players.addToInventory(p, reward);
                p.sendMessage(PREFIX + "You've been given " + reward.getAmount() + "x " + PrisonUtils.Items.getPrettyItemName(reward));
                r--;
            }
            open(p);
        })));
        c.fill(createGrayPlaceHolder());
        c.open(player);
        c.setOnCloseRun((p, t) -> MainMenus.open(p));
    }
}
