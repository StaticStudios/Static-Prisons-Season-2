package net.staticstudios.prisons.UI;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.backpacks.PrisonBackpack;
import net.staticstudios.prisons.backpacks.PrisonBackpacks;
import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.pvp.koth.KingOfTheHillManager;
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

        //Your time: 1m 5s | #1 Sammster10: 2, 13s | Time left: 6m 2s
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
        for (PrisonBackpack backpacks : PrisonBackpacks.getPlayerBackpacks(player)) {
            totalItems += backpacks.getItemCount();
            totalSize += backpacks.getSize();
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
        ACTIONBARS.put(player, actionbar);
    }


}
