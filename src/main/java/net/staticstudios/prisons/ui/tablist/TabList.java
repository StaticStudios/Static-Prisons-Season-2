package net.staticstudios.prisons.ui.tablist;

import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.data.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static net.kyori.adventure.text.Component.text;

public class TabList {

    public static Scoreboard scoreboard;

    private static final Map<String, String> teamNamesForPrefixIds = new HashMap<>();

    private static final String HEADER = """
                      
                      
            <bold><gray>-->><white><italic> Welcome To<colordark> Static Prisons <colordark/><gray><<--
            """;

    private static final String FOOTER = """
                        
            <bold><colorlight>PLAYERS ONLINE:<reset> <playersonline><gray> | <bold><colorlight>YOUR PING: <reset><ping>ms

            <bold><colordark>DISCORD: <reset><discord><gray> | <colordark><bold>WEBSITE: <reset><website>
            <bold><colordark>SERVER IP: <reset><serverip>""";


    public static void init() {
        scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        var prefix = TeamPrefix.getTeamPrefixAsComponents();

        int i = 0;
        for (String name : prefix.keySet()) {
            String teamName = "00" + (i++) + name;

            Team team = scoreboard.getTeam(teamName) == null ? scoreboard.registerNewTeam(teamName) : scoreboard.getTeam(teamName);
            assert team != null;
            team.prefix(prefix.get(name).append(text(" ")));
            team.setOption(Team.Option.COLLISION_RULE, Team.OptionStatus.NEVER);

            teamNamesForPrefixIds.put(name, teamName);
        }

        StaticPrisons.getInstance().addTask(new UpdateGradientRunnable());
    }

    public static void stop() {
        UpdateGradientRunnable.shutdown = true;
    }

    public static void addPlayer(Player player) {
        updateTabList(player);
    }

    public static void updateTabList(Player player) {
        PlayerData playerData = new PlayerData(player);

        TextColor colorDark = playerData.getPrimaryUIThemeAsTextColor();
        TextColor colorLight = playerData.getSecondaryUIThemeAsTextColor();
        MiniMessage miniMessage = MiniMessage.builder().tags(TagResolver.resolver(
                TagResolver.standard(),
                TagResolver.resolver("colorlight", Tag.styling(style -> style.color(colorLight))),
                TagResolver.resolver("colordark", Tag.styling(style -> style.color(colorDark))),
                TagResolver.resolver("playersonline", Tag.inserting(text(Bukkit.getOnlinePlayers().size()))),
                TagResolver.resolver("ping", Tag.inserting(text(player.getPing()))),
                TagResolver.resolver("discord", Tag.inserting(text("discord.gg/9S6K9E5"))),
                TagResolver.resolver("website", Tag.inserting(text("static-studios.net"))),
                TagResolver.resolver("serverip", Tag.inserting(text("play.static-studios.net"))))).build();

        player.sendPlayerListHeaderAndFooter(miniMessage.deserialize(HEADER), miniMessage.deserialize(FOOTER));

        if (scoreboard.getPlayerTeam(player) != null) {
            Objects.requireNonNull(scoreboard.getPlayerTeam(player)).removePlayer(player);
        }

        String rank = "member".equals(playerData.getStaffRank()) ? (
                "member".equals(playerData.getPlayerRank()) && playerData.getIsNitroBoosting() ? "nitro" : playerData.getPlayerRank()
                ) : playerData.getStaffRank();

        Team team = scoreboard.getTeam(teamNamesForPrefixIds.getOrDefault(rank, ""));

        if (team == null) {

            if (!teamNamesForPrefixIds.containsKey("member")) {
                player.setScoreboard(scoreboard);
                return;
            }

            team = scoreboard.getTeam(teamNamesForPrefixIds.get("member"));
        }

        assert team != null;

        team.addPlayer(player);
        player.setScoreboard(scoreboard);
    }

    private static final class UpdateGradientRunnable implements Runnable {

        public static boolean shutdown = false;

        private static double phase = 1.0;

        MiniMessage miniMessage = MiniMessage.builder()
                .preProcessor(s -> s.replace("<phase>", Double.toString(phase)))
                .tags(TagResolver.resolver(TagResolver.standard()))
                .build();

        @Override
        public void run() {
            while (!shutdown) {
                if (phase < -1.0) {
                    phase = 1.0;
                }

                Team staticp = scoreboard.getTeam(teamNamesForPrefixIds.getOrDefault("staticp", ""));

                if (staticp == null) {
                    return;
                }

                staticp.prefix(miniMessage.deserialize(TeamPrefix.getFromId("staticp")).append(text(" ")));

                Team owner = scoreboard.getTeam(teamNamesForPrefixIds.getOrDefault("owner", ""));

                if (owner == null) {
                    return;
                }

                owner.prefix(miniMessage.deserialize(TeamPrefix.getFromId("owner")).append(text(" ")));

                Team manager = scoreboard.getTeam(teamNamesForPrefixIds.getOrDefault("manager", ""));

                if (manager == null) {
                    return;
                }

                manager.prefix(miniMessage.deserialize(TeamPrefix.getFromId("manager")).append(text(" ")));

                phase -= 0.03;

                Bukkit.getOnlinePlayers().forEach(player -> player.setScoreboard(scoreboard));

                try {
                    Thread.sleep(50);
                } catch (InterruptedException ignored) {

                }
            }
        }
    }
}