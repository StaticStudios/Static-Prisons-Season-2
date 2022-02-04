package me.staticstudios.prisons;

import me.staticstudios.prisons.blockBroken.BlockChange;
import me.staticstudios.prisons.data.serverData.PlayerData;
import me.staticstudios.prisons.mines.MineManager;
import me.staticstudios.prisons.misc.scoreboard.CustomScoreboard;
import me.staticstudios.prisons.misc.tablist.TabList;
import me.staticstudios.prisons.utils.Utils;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.network.PacketListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class TimedTasks {

    public static void initializeTasks() {
        //Manages mine refills
        Bukkit.getScheduler().runTaskTimer(Main.getMain(), new Runnable() {
            @Override
            public void run() {
                MineManager.refillManager();
            }
        }, 0, 2);
        //Manages block changes
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getMain(), new Runnable() {
            @Override
            public void run() {
                BlockChange.worker();
            }
        }, 20, 1);
        //Show all players their backpacks
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getMain(), new Runnable() {
            @Override
            public void run() {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    PlayerData playerData = new PlayerData(p);
                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.GREEN + "Your Backpack: " + Utils.addCommasToBigInteger(playerData.getBackpack().getItemCount()) + "/" + Utils.addCommasToBigInteger(playerData.getBackpack().getSize()) + " Blocks"));
                }
            }
        }, 0, 1);
        //Scoreboard
        Bukkit.getScheduler().runTaskTimerAsynchronously(Main.getMain(), new Runnable() {
            @Override
            public void run() {
                CustomScoreboard.updateAllScoreboards();
            }
        }, 0, 2);
        //Update tablist for all players
        Bukkit.getScheduler().runTaskTimerAsynchronously(Main.getMain(), new Runnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    TabList.updateTabList(player);
                }
            }
        }, 0, 60);
    }
}
