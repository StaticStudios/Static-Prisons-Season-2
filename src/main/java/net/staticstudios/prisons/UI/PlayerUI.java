package net.staticstudios.prisons.UI;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.pvp.koth.KingOfTheHillManager;
import net.staticstudios.prisons.utils.PrisonUtils;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import net.staticstudios.prisons.utils.TimeUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static net.staticstudios.prisons.pvp.PvPManager.PVP_WORLD;

public class PlayerUI {

    public static void updateActionbar(Player player) {
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


        //Default backpack actionbar
        player.sendActionBar(LegacyComponentSerializer.legacyAmpersand()
                .deserialize(playerData.getSecondaryUITheme() + "Your Backpack: "
                        + PrisonUtils.addCommasToNumber(playerData.getBackpackItemCount())
                        + "/" + PrisonUtils.addCommasToNumber(playerData.getBackpackSize())
                        + " Blocks"));
    }


}
