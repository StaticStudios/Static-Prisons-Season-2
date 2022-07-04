package net.staticstudios.prisons.commands.normal;

import net.staticstudios.mines.StaticMineUtils;
import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.data.serverData.ServerData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class NPCDialogCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        if (args.length == 0) return false;
        switch (args[0]) {
            case "0" -> {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&l[Keaton] &fHey! Who are you?!?"));
                Bukkit.getScheduler().runTaskLater(StaticPrisons.getInstance(), () -> {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&l[Keaton] &fOh... I forgot Static Studios is a Java server now... Did you know that before Static Studios was a Java server it was a bunch of MCBE Realms?!"));
                }, 20 * 2);
                Bukkit.getScheduler().runTaskLater(StaticPrisons.getInstance(), () -> {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&l[Keaton] &fI know right! It's amazing that Static has come this far! Did you know that I used to be the community manager for Static? Sadly life caught up with me and I can no long keep up with it all... :'("));
                }, 20 * 7);
                Bukkit.getScheduler().runTaskLater(StaticPrisons.getInstance(), () -> {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&l[Keaton] &fYou may see me occasionally pop on the server and say hello, it's nice see what's new."));
                }, 20 * 12);
                Bukkit.getScheduler().runTaskLater(StaticPrisons.getInstance(), () -> {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&l[Keaton] &fI'll stop telling you things that you probably don't care about... Have fun mining!"));
                }, 20 * 16);
            }
            case "1" -> {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&l[ToXXic] &fweewoo!"));
            }
            case "2" -> {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&d&l[Kuul] &fWhat are you doing in my house?!?"));
                Bukkit.getScheduler().runTaskLater(StaticPrisons.getInstance(), () -> {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&d&l[Kuul] &fQuite litterally this is my house... I built it myself!"));
                }, 20 * 2);
                Bukkit.getScheduler().runTaskLater(StaticPrisons.getInstance(), () -> {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&d&l[Kuul] &fSorry if I came off a little hostile... Let me introduce myself, in a less hostile way."));
                }, 20 * 5);
                Bukkit.getScheduler().runTaskLater(StaticPrisons.getInstance(), () -> {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&d&l[Kuul] &fHi, I'm kuul (im pretty cool ikr), I build things... like this map! It took a while but I think the results speak for themselves!"));
                }, 20 * 9);
                Bukkit.getScheduler().runTaskLater(StaticPrisons.getInstance(), () -> {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&d&l[Kuul] &fWant to know a fun fact!?!?"));
                }, 20 * 14);
                Bukkit.getScheduler().runTaskLater(StaticPrisons.getInstance(), () -> {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&d&l[Kuul] &fNo, not really...? :'("));
                }, 20 * 18);
                Bukkit.getScheduler().runTaskLater(StaticPrisons.getInstance(), () -> {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&d&l[Kuul] &fWell, I don't care, I'm going to tell you anyways!"));
                }, 20 * 22);
                Bukkit.getScheduler().runTaskLater(StaticPrisons.getInstance(), () -> {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&d&l[Kuul] &fThere was this one time in which I dumped a whole bucket of ICE WATER on myself for a MINECRAFT SERVER! It was not worth it to be honest but I would probably do it again."));
                }, 20 * 26);
                Bukkit.getScheduler().runTaskLater(StaticPrisons.getInstance(), () -> {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&d&l[Kuul] &fIt was pretty cold not gonna lie..."));
                }, 20 * 33);
            }
            case "3" -> {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&l[Sam] &fHeyyyy what's up guyyysss!!! In today's video I am going to..."));
                Bukkit.getScheduler().runTaskLater(StaticPrisons.getInstance(), () -> {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&l[Sam] &f&oughh... that intro was so bad! &fWOAH! WHO ARE YOU?"));
                }, 20 * 3);
                Bukkit.getScheduler().runTaskLater(StaticPrisons.getInstance(), () -> {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&l[Sam] &fOh, nice to meet you, I'm Sam!"));
                }, 20 * 7);
                Bukkit.getScheduler().runTaskLater(StaticPrisons.getInstance(), () -> {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&l[Sam] &fI'm the guy who created this server, it's pretty bad I know haha... &o*sigh*"));
                }, 20 * 11);
                Bukkit.getScheduler().runTaskLater(StaticPrisons.getInstance(), () -> {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&l[Sam] &fAnyways you caught me in the middle of recording a video, I need to finish showing people how to do commands."));
                }, 20 * 15);
                Bukkit.getScheduler().runTaskLater(StaticPrisons.getInstance(), () -> {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&l[Sam] &fCya!"));
                }, 20 * 19);
            }
        }

        return false;
    }
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> list = new ArrayList<>();
        return list;
    }
}
