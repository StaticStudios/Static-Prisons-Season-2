package me.staticstudios.prisons;

import me.staticstudios.prisons.auctionHouse.AuctionHouseManager;
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
        Bukkit.getScheduler().runTaskTimer(Main.getMain(), () -> MineManager.refillManager(), 0, 2);
        //Show all players their backpacks
        Bukkit.getScheduler().runTaskTimer(Main.getMain(), () -> {
            for (Player p : Bukkit.getOnlinePlayers()) {
                PlayerData playerData = new PlayerData(p);
                p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.GREEN + "Your Backpack: " + Utils.addCommasToBigInteger(playerData.getBackpack().getItemCount()) + "/" + Utils.addCommasToBigInteger(playerData.getBackpack().getSize()) + " Blocks"));
            }
        }, 0, 1);
        //Scoreboard
        Bukkit.getScheduler().runTaskTimerAsynchronously(Main.getMain(), CustomScoreboard::updateAllScoreboards, 0, 2);
        //Update tablist for all players
        Bukkit.getScheduler().runTaskTimerAsynchronously(Main.getMain(), () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                TabList.updateTabList(player);
            }
        }, 0, 60);
        //Update Expired Auctions
        Bukkit.getScheduler().runTaskTimerAsynchronously(Main.getMain(), AuctionHouseManager::expireAllExpiredAuctions, 120, 60);
    }
}
