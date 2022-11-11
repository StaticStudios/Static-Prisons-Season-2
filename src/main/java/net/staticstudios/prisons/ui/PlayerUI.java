package net.staticstudios.prisons.ui;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.admin.AdminManager;
import net.staticstudios.prisons.backpacks.Backpack;
import net.staticstudios.prisons.backpacks.BackpackManager;
import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.pvp.koth.KingOfTheHillManager;
import net.staticstudios.prisons.pvp.outposts.OutpostManager;
import net.staticstudios.prisons.pvp.outposts.domain.Outpost;
import net.staticstudios.prisons.utils.ComponentUtil;
import net.staticstudios.prisons.utils.PrisonUtils;
import net.staticstudios.prisons.utils.TimeUtils;
import org.bukkit.entity.Player;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static net.staticstudios.prisons.pvp.PvPManager.PVP_WORLD;

public class PlayerUI {

    static Map<Player, Component> ACTIONBARS = new HashMap<>();

    public static void sendActionbar(Player player) {
        PlayerData playerData = new PlayerData(player);

        if (KingOfTheHillManager.isEventRunning() && PVP_WORLD.equals(player.getWorld())) {

            Component message = Component.empty();

            Optional<Player> possiblePlayer = KingOfTheHillManager.getTimeInKothAreaPerPlayer().entrySet().stream()
                    .sorted((o1, o2) -> Integer.compare(o2.getValue(), o1.getValue()))
                    .map(Map.Entry::getKey)
                    .findFirst();

            message = message.append(Component.text("Your time: "))
                    .append(TimeUtils.formatTime(KingOfTheHillManager.getTimeInKothAreaPerPlayer().getOrDefault(player, 0)))
                    .append(Component.text(" | "));

            if (possiblePlayer.isPresent()) {
                message = message.append(Component.text("#1 ")).append(possiblePlayer.get().name()).append(Component.text(": "))
                        .append(TimeUtils.formatTime(KingOfTheHillManager.getTimeInKothAreaPerPlayer().getOrDefault(possiblePlayer.get(), 0)))
                        .append(Component.text(" | "));
            }

            message = message.append(Component.text("Time left: "))
                    .append(TimeUtils.formatTime(KingOfTheHillManager.getTimeLeft()));

            player.sendActionBar(message.color(playerData.getSecondaryUIThemeAsTextColor()));
            return;

        }

        ApplicableRegionSet regions = OutpostManager.getRegionManager().getApplicableRegions(BukkitAdapter.adapt(player).getLocation().toBlockPoint());

        Optional<Outpost> potentialOutpost = regions.getRegions().stream()
                .filter(region -> OutpostManager.getOutpostRegionNames().contains(region.getId()))
                .map(region -> OutpostManager.getOutpostByRegion(region.getId()))
                .findFirst();

        if (potentialOutpost.isPresent()) {
            Outpost outpost = potentialOutpost.get();

            Component actionBar = Component.empty();

            actionBar = actionBar.append(Component.text("Outpost: "))
                    .append(Component.text(outpost.getName()))
                    .append(Component.text(" | "));

            actionBar = outpost.getCurrentGang() == null ? actionBar.append(Component.text("not owned by a gang")) : actionBar.append(Component.text(outpost.getCurrentGang().getName()));

            actionBar = actionBar.append(Component.text(" | "));

            if (outpost.isContested()) {
                actionBar = actionBar.append(Component.text("Contested"));

            } else {
                actionBar = actionBar.append(Component.text(outpost.getCapturePercentage())).append(Component.text("% captured"));
            }

            player.sendActionBar(actionBar.color(playerData.getSecondaryUIThemeAsTextColor()));
            return;
        }

        if (StaticPrisons.currentTick % 20 == 0) {
            updateActionbar(player);
        }
        Component actionbar = ACTIONBARS.get(player);
        if (actionbar == null) {
            updateActionbar(player);
            actionbar = ACTIONBARS.get(player);
        }
        player.sendActionBar(actionbar);
    }

    static void updateActionbar(Player player) {
        PlayerData playerData = new PlayerData(player);
        long totalItems = 0;
        long totalSize = 0;
        for (Backpack backpacks : BackpackManager.getPlayerBackpacks(player)) {
            totalItems += backpacks.getItemCount();
            totalSize += backpacks.getCapacity();
        }
        double percent = 100;
        if (totalSize > 0) {
            percent = (double) totalItems / totalSize * 100;
        }
        Component actionbar = LegacyComponentSerializer.legacyAmpersand()
                .deserialize(playerData.getSecondaryUITheme() + "Your Backpacks: "
                        + PrisonUtils.prettyNum(totalItems)
                        + "/" + PrisonUtils.prettyNum(totalSize)
                        + " Items | "
                        + BigDecimal.valueOf(percent).setScale(2, RoundingMode.FLOOR) + "% Full");

        if (AdminManager.containedInHiddenPlayers(player)) {
            actionbar = ComponentUtil.BLANK
                    .append(Component.text("Vanished")
                            .color(ComponentUtil.RED)
                            .decoration(TextDecoration.BOLD, true))
                    .append(Component.text(" | ")
                            .color(ComponentUtil.LIGHT_GRAY)
                            .decoration(TextDecoration.BOLD, false))
                    .append(actionbar);
        }

        ACTIONBARS.put(player, actionbar);
    }


}
