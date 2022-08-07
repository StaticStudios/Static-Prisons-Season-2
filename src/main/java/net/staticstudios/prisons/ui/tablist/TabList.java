package net.staticstudios.prisons.ui.tablist;

import net.md_5.bungee.api.ChatColor;
import net.staticstudios.prisons.data.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class TabList {
    public static final String[][] TEAMS = new String[][] {
            //Rank order char, rank prefix, rank ID
            {"a", ChatColor.translateAlternateColorCodes('&', "&x&6&0&0&0&f&d&lO&x&7&f&0&0&f&d&lw&x&9&d&0&0&f&d&ln&x&b&c&0&0&f&d&le&x&d&a&0&0&f&d&lr "), "owner"},
            {"b", ChatColor.of("#803dfc") + "" + ChatColor.BOLD + "Manager ", "manager"},
            {"c", ChatColor.DARK_RED + "" + ChatColor.BOLD + "Sr.Admin ", "sradmin"},
            {"d", ChatColor.RED + "" + ChatColor.BOLD + "Admin ", "admin"},
            {"e", ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Sr.Mod ", "srmod"},
            {"f", ChatColor.BLUE + "" + ChatColor.BOLD + "Mod ", "mod"},
            {"g", ChatColor.AQUA + "" + ChatColor.BOLD + "Helper ", "helper"},
            {"h", ChatColor.of("#00fbec") + "S" + ChatColor.of("#01d1ef") + "t" + ChatColor.of("#02a7f2") + "a" + ChatColor.of("#037ef5") + "t" + ChatColor.of("#0354f7") + "i" + ChatColor.of("#042afa") + "c" + ChatColor.of("#0500fd") + "+ ", "staticp"},
            {"i", ChatColor.BLUE + "Static ", "static"},
            {"j", ChatColor.RED + "Mythic ", "mythic"},
            {"k", ChatColor.YELLOW + "Master ", "master"},
            {"l", ChatColor.GOLD + "Warrior ", "warrior"},
            {"m", ChatColor.LIGHT_PURPLE + "Nitro ", "nitro"},
    };
    static final String[] defaultTeam = new String[] {
            "zzzDefault", ChatColor.GRAY + "Member "
    };
    public static Scoreboard sb;

    public static void init() { //todo: look into ProtocolLib and see if this can be done with packets
        sb = Bukkit.getScoreboardManager().getMainScoreboard();
        for (String[] arr : TEAMS) {
            if (sb.getTeam(arr[0]) != null) sb.getTeam(arr[0]).unregister();
            Team team = sb.registerNewTeam(arr[0]);
            team.setPrefix(arr[1]);
            team.setOption(Team.Option.COLLISION_RULE, Team.OptionStatus.NEVER);
        }
        if (sb.getTeam(defaultTeam[0]) != null) sb.getTeam(defaultTeam[0]).unregister();
        Team team = sb.registerNewTeam(defaultTeam[0]);
        team.setPrefix(defaultTeam[1]);
        team.setOption(Team.Option.COLLISION_RULE, Team.OptionStatus.NEVER);
    }

    public static void addPlayer(Player player) {
        updateTabList(player);
    }
    public static void updateTabList(Player player) {
        //Update head and footer
        //player.setPlayerListHeaderFooter(ChatColor.translateAlternateColorCodes('&', "\n\n &7&l-->> &r&f&o&lWelcome To &b&o&lStatic Prisons &r&7&l<<--\n"), ChatColor.translateAlternateColorCodes('&', "\n&a&lPLAYERS ONLINE: &r&f" + Bukkit.getOnlinePlayers().size() + " &r&7| &a&lYOUR PING: &r&f" + player.getPing() + "ms\n\n&d&lDISCORD: &r&fdiscord.gg/staticmc&r &7| &r&d&lIP: &rplay.static-studios.net"));

        PlayerData playerData = new PlayerData(player);

        ChatColor colorDark = playerData.getPrimaryUITheme();
        ChatColor colorLight = playerData.getSecondaryUITheme();

        player.setPlayerListHeaderFooter(ChatColor.translateAlternateColorCodes('&', "\n\n &7&l-->> &r&f&o&lWelcome To " + colorDark + "&o&lStatic Prisons &r&7&l<<--\n"),
                ChatColor.translateAlternateColorCodes('&', "\n" + colorLight + "&lPLAYERS ONLINE: &r&f" + Bukkit.getOnlinePlayers().size() + " &r&7| " + colorLight + "&lYOUR PING: &r&f" +
                        player.getPing() + "ms\n\n" + colorDark + "&lDISCORD: &r&fdiscord.gg/static&r &7| &r" + colorDark + "&lWEBSITE: &r&fstatic-studios.net\n" + colorDark + "&lSERVER IP: &rplay.static-studios.net"));


        //Remove player from old teams
        for (String[] _arr : TEAMS) {
            sb.getTeam(_arr[0]).removeEntry(player.getName());
        }
        playerData.updateTabListPrefixID();
        sb.getTeam(defaultTeam[0]).removeEntry(player.getName());
        for (String[] arr : TEAMS) {
            if (arr[2].equals(playerData.getTabListPrefixID())) {
                //Set new team
                sb.getTeam(arr[0]).addEntry(player.getName());
                return;
            }
        }
        //Player does not have a "special" tab team; set to default
        sb.getTeam(defaultTeam[0]).addEntry(player.getName());
        player.setScoreboard(sb);
    }
}