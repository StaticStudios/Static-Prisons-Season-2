package me.staticstudios.prisons.gameplay.commands.tabCompletion;

import me.staticstudios.prisons.core.data.serverData.PlayerData;
import me.staticstudios.prisons.core.data.serverData.ServerData;
import me.staticstudios.prisons.gameplay.islands.SkyBlockIsland;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class IslandTabCompletion implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        Player player = (Player) sender;
        List<String> tab = new ArrayList<>();
        if (args.length == 1) {
            tab.add("about"); //Done
            tab.add("ban"); //Done
            tab.add("banlist"); //Done
            tab.add("delete"); //Done
            tab.add("unban"); //Done
            tab.add("create"); //Done
            tab.add("go"); //Done
            tab.add("info"); //Done
            tab.add("rename"); //Done
            tab.add("setmemberwarp"); //Done
            tab.add("setvisitorwarp"); //Done
            tab.add("settings"); //Done
            tab.add("members"); //Done
            tab.add("top");
            tab.add("value");
            tab.add("visit"); //Done
            tab.add("invite"); //Done
            tab.add("invites"); //Done
        } else if (args.length == 2) {
            switch (args[0].toLowerCase()) {
                case "invite" -> {
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        tab.add(p.getName());
                    }
                }
                case "info", "visit" -> tab = new LinkedList<>(new ServerData().getSkyblockIslandNamesToUUIDsMap().keySet());
                case "unban" -> {
                    if (!new PlayerData(player).getIfPlayerHasIsland()) return new ArrayList<>();
                    SkyBlockIsland island = new PlayerData(player.getUniqueId().toString()).getPlayerIsland();
                    for (String uuid : island.getBannedPlayerUUIDS()) {
                        tab.add(new ServerData().getPlayerNameFromUUID(uuid));
                    }
                }
            }
        }
        return tab;
    }
}
