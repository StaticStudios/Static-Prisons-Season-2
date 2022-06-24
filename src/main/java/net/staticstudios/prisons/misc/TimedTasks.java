package net.staticstudios.prisons.misc;

import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.cells.CellManager;
import net.staticstudios.prisons.enchants.handler.PrisonPickaxe;
import net.staticstudios.prisons.external.DiscordLink;
import net.staticstudios.prisons.UI.PlayerUI;
import net.staticstudios.prisons.data.dataHandling.DataSet;
import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.chat.events.EventManager;
import net.staticstudios.prisons.leaderboards.LeaderboardManager;
import net.staticstudios.prisons.UI.scoreboard.CustomScoreboard;
import net.staticstudios.prisons.UI.tablist.TabList;
import net.staticstudios.prisons.auctionHouse.AuctionManager;
import net.staticstudios.prisons.privateMines.PrivateMineManager;
import net.staticstudios.prisons.utils.Constants;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.math.BigInteger;
import java.time.Instant;

public class TimedTasks {

    public static void initTasks() {
        //Auto saves data
        Bukkit.getScheduler().runTaskTimer(StaticPrisons.getInstance(), DataSet::saveData, 20 * 60 * 5, 20 * 60 * 5);
        //Auto saves private mine data
        Bukkit.getScheduler().runTaskTimer(StaticPrisons.getInstance(), PrivateMineManager::save, 20 * 60 * 6, 20 * 60 * 5);
        //Auto saves cell data
        Bukkit.getScheduler().runTaskTimer(StaticPrisons.getInstance(), CellManager::save, 20 * 60 * 7, 20 * 60 * 5);
        //Save all auctions
        Bukkit.getScheduler().runTaskTimer(StaticPrisons.getInstance(), AuctionManager::saveAllAuctions, 0, 20 * 60 * 5);
        //Update Expired Auctions
        Bukkit.getScheduler().runTaskTimer(StaticPrisons.getInstance(), AuctionManager::updateExpiredAuctions, 60, 20 * 10);
        //Update the bots status
        Bukkit.getScheduler().runTaskTimer(StaticPrisons.getInstance(), DiscordLink::updatePlayerCount, 200, 200);
        //Manages mine refills
        //Bukkit.getScheduler().runTaskTimer(StaticPrisons.getPlugin(), MineManager::refillManager, 0, 2);


        //Save Pickaxe Data
        Bukkit.getScheduler().runTaskTimer(StaticPrisons.getInstance(), PrisonPickaxe::savePickaxeData, 20 * 60 * 5, 20 * 60 * 5);
        //Update All Pickaxe Lore
        Bukkit.getScheduler().runTaskTimer(StaticPrisons.getInstance(), PrisonPickaxe::dumpLoreToAllPickaxes, 20, 20 * 5); //possibly spread this across multiple ticks so like do 1% of the pickaxes every tick


        //Show all players their backpacks
        Bukkit.getScheduler().runTaskTimer(StaticPrisons.getInstance(), () -> {
            for (Player p : Bukkit.getOnlinePlayers()) {
                PlayerUI.updateActionbar(p);
                }
        }, 0, 1);
        //Scoreboard
        Bukkit.getScheduler().runTaskTimer(StaticPrisons.getInstance(), CustomScoreboard::updateAllScoreboards, 0, 2);
        //Time played
        Bukkit.getScheduler().runTaskTimer(StaticPrisons.getInstance(), () -> {
            for (Player p : Bukkit.getOnlinePlayers()) new PlayerData(p).addTimePlayed(BigInteger.ONE);
        }, 0, 20);
        //Update tablist for all players
        Bukkit.getScheduler().runTaskTimer(StaticPrisons.getInstance(), () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                TabList.updateTabList(player);
            }
        }, 0, 60);
        //Reminder to vote
        Bukkit.getScheduler().runTaskTimer(StaticPrisons.getInstance(), () -> {
            for (Player p : Bukkit.getOnlinePlayers()) {
                PlayerData playerData = new PlayerData(p);
                if (playerData.getLastVotedAt() < Instant.now().toEpochMilli() - 24 * 60 * 60 * 1000) {
                    p.sendMessage(ChatColor.RED + "You have not voted today! In order to win free rewards from the vote party, vote everyday. You can vote by typing " + ChatColor.GREEN + "/vote");
                    p.sendTitle(ChatColor.RED + "" + ChatColor.BOLD + "/vote", ChatColor.RED + "" + ChatColor.ITALIC + "You haven't voted today!", 5, 40, 5);
                }
            }
        }, 0, 20 * 60 * 30);
        //Update Expired Auctions
        //Bukkit.getScheduler().runTaskTimer(Main.getMain(), AuctionHouseManager::expireAllExpiredAuctions, 120, 2);
        //Give everyone with potion enchants their effect(s)
        //Bukkit.getScheduler().runTaskTimer(StaticPrisons.getInstance(), EnchantEffects::giveEffects, 10, 400);
        //Update all the leaderboards
        Bukkit.getScheduler().runTaskTimer(StaticPrisons.getInstance(), LeaderboardManager::updateAll, 20, 20 * 60 * 30);
        //Tips
        Bukkit.getScheduler().runTaskTimer(StaticPrisons.getInstance(), () -> {
            String tip = Constants.TIPS[PrisonUtils.randomInt(0, Constants.TIPS.length - 1)];
            for (Player p : Bukkit.getOnlinePlayers()) if (!new PlayerData(p).getAreTipsDisabled()) p.sendMessage(tip);
        }, 20 * 60 * 5, 20 * 60 * 10);
        //Chat Events
        Bukkit.getScheduler().runTaskTimer(StaticPrisons.getInstance(), EventManager::runNewEvent, 20 * 60 * 12, 20 * 60 * 25);
        //Update Pickaxe Lore With Stats
        //Bukkit.getScheduler().runTaskTimer(StaticPrisons.getInstance(), PrisonPickaxe::dumpStatsToAllPickaxe, 0, 20 * 10);
        //Consistency enchant
        //Bukkit.getScheduler().runTaskTimer(StaticPrisons.getInstance(), ConsistencyEnchant::worker, 30, 20);



    }
}
