package net.staticstudios.prisons.admin;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.admin.commands.*;
import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.ui.tablist.TeamPrefix;
import net.staticstudios.prisons.utils.ComponentUtil;
import net.staticstudios.prisons.utils.Prefix;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static net.staticstudios.prisons.commands.CommandManager.registerCommand;

public class AdminManager implements Listener {

    private static YamlConfiguration config;

    private static final HashSet<UUID> admins = new HashSet<>();
    private static final HashSet<UUID> hiddenPlayers = new HashSet<>();

    public static void init() {
        loadConfig();

        registerCommand("staffchat", new StaffChatCommand());
        registerCommand("advancednickname", new AdvancedNicknameCommand());
        registerCommand("vanish", new VanishCommand());
        registerCommand("broadcast", new BroadcastMessageCommand());
        registerCommand("modifystats", new ModifyStatsCommand());

        registerCommand("enderchestsee", new EnderChestSeeCommand());

        registerCommand("renameitem", new RenameItemCommand());

        registerCommand("schedulerestart", new ScheduleRestartCommand());
        registerCommand("schedulestop", new ScheduleStopCommand());

        registerCommand("keyall", new KeyallCommand());

        registerCommand("listplayerrank", new ListPlayerRankCommand());
        registerCommand("liststaffrank", new ListStaffRankCommand());

        registerCommand("setplayerrank", new SetPlayerRankCommand());
        registerCommand("setstaffrank", new SetStaffRankCommand());

        registerCommand("givevote", new GiveVoteCommand());

        registerCommand("reload-config", new ReloadConfigCommand());

        registerCommand("uuid", new UuidCommand());

        Bukkit.getPluginManager().registerEvents(new AdminListener(), StaticPrisons.getInstance());

    }

    private static void loadConfig() {
        config = YamlConfiguration.loadConfiguration(
                new File(StaticPrisons.getInstance().getDataFolder() + "/admins.yml"));

        if (!config.contains("admins")) {
            config.set("admins", new ArrayList<>());
        }

        if (!config.contains("hiddenPlayers")) {
            config.set("hiddenPlayers", new ArrayList<>());
        }

        admins.addAll(config.getStringList("admins").stream().map(UUID::fromString).toList());
        hiddenPlayers.addAll(config.getStringList("hiddenPlayers").stream().map(UUID::fromString).toList());
    }

    public static void save() {
        saveConfig();
    }

    private static void saveConfig() {
        config.set("admins", admins.stream().map(UUID::toString).toList());
        config.set("hiddenPlayers", hiddenPlayers.stream().map(UUID::toString).toList());

        try {
            config.save(new File(StaticPrisons.getInstance().getDataFolder() + "/admins.yml"));
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public static boolean removeFromHiddenPlayers(Player player) {
        return hiddenPlayers.remove(player.getUniqueId());
    }

    @SuppressWarnings("UnusedReturnValue")
    public static boolean addToHiddenPlayers(Player player) {
        return hiddenPlayers.add(player.getUniqueId());
    }

    public static boolean checkIfPlayerInVanishAtJoin(Player player) {
        if (hiddenPlayers.contains(player.getUniqueId())) {
            hidePlayer(player);
            player.sendMessage(Prefix.VANISH
                    .append(Component
                            .text("Automatically ")
                            .color(ComponentUtil.GRAY))
                    .append(Component
                            .text("enabled ")
                            .color(ComponentUtil.GOLD))
                    .append(Component
                            .text("vanish mode.")
                            .color(ComponentUtil.GRAY)));
            return true;
        }
        return false;
    }

    public static void hidePlayer(Player player) {
        Bukkit.getOnlinePlayers().forEach(other -> {
            if (hiddenPlayers.contains(player.getUniqueId())) {
                if (!other.hasPermission("static.vanish")) {
                    other.hidePlayer(StaticPrisons.getInstance(), player);
                }
            }
        });

        player.playerListName(Component
                .textOfChildren(
                        TeamPrefix.getFromIdDeserialized(new PlayerData(player).getStaffRank()),
                        Component.text(" "),
                        player.name().color(ComponentUtil.GRAY))
                .decorate(TextDecoration.ITALIC)
                .append(ComponentUtil.BLANK.append(Component.text(" V").color(NamedTextColor.RED)))
        );
    }

    public static void showPlayer(Player player) {
        player.playerListName(null);
        Bukkit.getOnlinePlayers().forEach(other -> other.showPlayer(StaticPrisons.getInstance(), player));
    }


    public static boolean isHidden(Player player) {
        return hiddenPlayers.contains(player.getUniqueId());
    }

    public static Set<UUID> getHiddenPlayers() {
        return hiddenPlayers;
    }
}
