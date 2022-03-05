package me.staticstudios.prisons;

import me.staticstudios.prisons.auctionHouse.AuctionHouseManager;
import me.staticstudios.prisons.data.dataHandling.DataWriter;
import me.staticstudios.prisons.data.serverData.PlayerData;
import me.staticstudios.prisons.discord.DiscordBot;
import me.staticstudios.prisons.enchants.EnchantEffects;
import me.staticstudios.prisons.events.EventManager;
import me.staticstudios.prisons.leaderboards.LeaderboardManager;
import me.staticstudios.prisons.mines.MineManager;
import me.staticstudios.prisons.misc.scoreboard.CustomScoreboard;
import me.staticstudios.prisons.misc.tablist.TabList;
import me.staticstudios.prisons.utils.StaticVars;
import me.staticstudios.prisons.utils.Utils;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.math.BigInteger;
import java.time.Instant;

public class TimedTasks {

    public static void initializeTasks() {
        //Auto saves data
        Bukkit.getScheduler().runTaskTimer(Main.getMain(), DataWriter::saveData, 0, 20 * 60 * 5);
        //Update the bots status
        Bukkit.getScheduler().runTaskTimer(Main.getMain(), DiscordBot::updatePlayersOnline, 200, 20 * 60);
        //Manages mine refills
        Bukkit.getScheduler().runTaskTimer(Main.getMain(), MineManager::refillManager, 0, 2);
        //Show all players their backpacks
        Bukkit.getScheduler().runTaskTimer(Main.getMain(), () -> {
            for (Player p : Bukkit.getOnlinePlayers()) {
                PlayerData playerData = new PlayerData(p);
                p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.GREEN + "Your Backpack: " + Utils.addCommasToNumber(playerData.getBackpack().getItemCount()) + "/" + Utils.addCommasToNumber(playerData.getBackpack().getSize()) + " Blocks"));
            }
        }, 0, 1);
        //Scoreboard
        Bukkit.getScheduler().runTaskTimer(Main.getMain(), CustomScoreboard::updateAllScoreboards, 0, 2);
        //Time played
        Bukkit.getScheduler().runTaskTimer(Main.getMain(), () -> {
            for (Player p : Bukkit.getOnlinePlayers()) new PlayerData(p).addTimePlayed(BigInteger.ONE);
        }, 0, 20);
        //Update tablist for all players
        Bukkit.getScheduler().runTaskTimer(Main.getMain(), () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                TabList.updateTabList(player);
            }
        }, 0, 60);
        //Reminder to vote
        Bukkit.getScheduler().runTaskTimer(Main.getMain(), () -> {
            for (Player p : Bukkit.getOnlinePlayers()) {
                PlayerData playerData = new PlayerData(p);
                if (playerData.getLastVotedAt() < Instant.now().toEpochMilli() - 24 * 60 * 60 * 1000) {
                    p.sendMessage(ChatColor.RED + "You have not voted today! In order to win free rewards from the vote party, vote everyday. You can vote by typing " + ChatColor.GREEN + "/vote");
                    p.sendTitle(ChatColor.RED + "" + ChatColor.BOLD + "/vote", ChatColor.RED + "" + ChatColor.ITALIC + "You haven't voted today!", 5, 40, 5);

                }
            }
        }, 0, 20 * 60 * 30);
        //Update Expired Auctions
        Bukkit.getScheduler().runTaskTimer(Main.getMain(), AuctionHouseManager::expireAllExpiredAuctions, 120, 2);
        //Give everyone with potion enchants their effect(s)
        Bukkit.getScheduler().runTaskTimer(Main.getMain(), EnchantEffects::giveEffects, 10, 400);
        //Update all of the leaderboards
        Bukkit.getScheduler().runTaskTimer(Main.getMain(), LeaderboardManager::updateAll, 20, 20 * 60 * 30);
        //Tips
        Bukkit.getScheduler().runTaskTimer(Main.getMain(), () -> {
            String tip = StaticVars.TIPS[Utils.randomInt(0, StaticVars.TIPS.length - 1)];
            for (Player p : Bukkit.getOnlinePlayers()) if (!new PlayerData(p).getAreTipsDisabled()) p.sendMessage(tip);
        }, 20 * 60 * 5, 20 * 60 * 10);
        //Chat Events
        Bukkit.getScheduler().runTaskTimer(Main.getMain(), EventManager::runNewEvent, 20 * 60 * 12, 20 * 60 * 25);


    }
}
