package net.staticstudios.prisons.levelup.prestige.gui;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.md_5.bungee.api.ChatColor;
import net.staticstudios.gui.legacy.GUICreator;
import net.staticstudios.gui.legacy.GUIUtils;
import net.staticstudios.mines.utils.WeightedElements;
import net.staticstudios.prisons.customitems.CustomItems;
import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.gui.MainMenus;
import net.staticstudios.prisons.utils.ComponentUtil;
import net.staticstudios.prisons.utils.PlayerUtils;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Objects;

public class RewardsMenus extends GUIUtils {

    public static final Component PREFIX = ComponentUtil.BLANK
            .append(Component.text("Rewards")
                    .color(ComponentUtil.LIGHT_PURPLE))
            .append(Component.text(" >> ")
                    .color(ComponentUtil.DARK_GRAY))
            .decorate(TextDecoration.BOLD);

    public static void open(Player player) {
        GUICreator c = new GUICreator(27, "Your Rewards");
        PlayerData playerData = new PlayerData(player);
        long difference = playerData.getPrestige() - playerData.getClaimedPrestigeRewardsAt();
        c.setItem(13, ench(c.createButton(Material.AMETHYST_CLUSTER, "&d&lPrestige Rewards", List.of("You have " + PrisonUtils.addCommasToNumber(difference) + " reward(s) to claim!", "You get a new one every prestige!", "", "&b&oClick to claim"), (p, t) -> {
            if (difference <= 0) {
                p.sendMessage(ComponentUtil.BLANK
                        .append(PREFIX)
                        .append(Component.text("You have no rewards to claim!")
                                .color(ComponentUtil.RED)));
                return;
            }
            playerData.setClaimedPrestigeRewardsAt(playerData.getPrestige());
            long r = difference;
            while (r > 0) {
                ItemStack reward = new WeightedElements<ItemStack>()
                        .add(CustomItems.getLegendaryCrateKey(1), 15)
                        .add(CustomItems.getLegendaryCrateKey(2), 30)
                        .add(CustomItems.getStaticCrateKey(1), 40)
                        .add(CustomItems.getStaticpCrateKey(1), 5)
                        .getRandom(); //
                PlayerUtils.addToInventory(p, reward);
                p.sendMessage(ComponentUtil.BLANK
                        .append(PREFIX)
                        .append(Component.text("You won " + reward.getAmount() + "x ")
                                .color(ComponentUtil.WHITE))
                        .append(Objects.requireNonNull(reward.getItemMeta().displayName())));
                r--;
            }
            open(p);
        })));
        c.fill(createGrayPlaceHolder());
        c.open(player);
        c.setOnCloseRun((p, t) -> MainMenus.open(p));
    }
}
