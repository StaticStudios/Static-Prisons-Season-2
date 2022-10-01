package net.staticstudios.prisons.customitems;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.title.Title;
import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.utils.StaticFileSystemManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.Duration;
import java.util.logging.Logger;

public interface Pouch<T> {

    int timeBetweenFrames = 4;

    void open(Player player);

    default void animateFrame(Player player, T reward, String rewardValue, Component announcementMessage, Component prefix, long currentPos, int finished) {
        if (currentPos == finished) {
            player.sendMessage(announcementMessage);
            addReward(player, reward);
            return;
        }

        Component title = prefix;

        int fadeIn = currentPos != 0 ? 0 : 10;

        for (int i = 0; i < rewardValue.length(); i++) {
            if (currentPos <= i) {
                title = title.append(Component.text(rewardValue.charAt(i)).decorate(TextDecoration.OBFUSCATED));
            } else {
                title = title.append(Component.text(rewardValue.charAt(i)));
            }
        }

        player.showTitle(Title.title(title.color(NamedTextColor.GRAY), Component.empty(), Title.Times.times(Duration.ofMillis(fadeIn * 50), Duration.ofMillis(2000), Duration.ofMillis(500))));

        Bukkit.getScheduler().runTaskLater(StaticPrisons.getInstance(),
                () -> animateFrame(player, reward, rewardValue, announcementMessage, prefix, currentPos + 1, finished),
                timeBetweenFrames);
    }

    default YamlConfiguration getConfig() {
        return YamlConfiguration.loadConfiguration(StaticFileSystemManager.getFile("pouches.yml").orElseThrow(() -> new RuntimeException("Could not find pouches.yml")));
    }

    void addReward(Player player, T reward);

}
