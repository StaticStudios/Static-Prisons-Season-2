package net.staticstudios.prisons.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.auctionhouse.AuctionManager;
import net.staticstudios.prisons.cells.CellManager;
import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.data.datahandling.DataSet;
import net.staticstudios.prisons.external.DiscordLink;
import net.staticstudios.prisons.gangs.Gang;
import net.staticstudios.prisons.leaderboards.LeaderboardManager;
import net.staticstudios.prisons.pickaxe.PrisonPickaxe;
import net.staticstudios.prisons.privatemines.PrivateMineManager;
import net.staticstudios.prisons.ui.PlayerUI;
import net.staticstudios.prisons.ui.scoreboard.CustomScoreboard;
import net.staticstudios.prisons.ui.tablist.TabList;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.time.Instant;

import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.NamedTextColor.GREEN;
import static net.kyori.adventure.text.format.NamedTextColor.RED;
import static net.kyori.adventure.text.format.TextDecoration.BOLD;
import static net.kyori.adventure.text.format.TextDecoration.ITALIC;

public class TimedTasks {

    public static void init() {
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
        //Save Pickaxe Data
        Bukkit.getScheduler().runTaskTimer(StaticPrisons.getInstance(), PrisonPickaxe::savePickaxeData, 20 * 60 * 5, 20 * 60 * 5);
        //Save Gang Data
        Bukkit.getScheduler().runTaskTimer(StaticPrisons.getInstance(), Gang::saveAll, 20 * 60 * 6, 20 * 60 * 5);


        //Show all players their backpacks
        Bukkit.getScheduler().runTaskTimer(StaticPrisons.getInstance(), () -> {
            for (Player p : Bukkit.getOnlinePlayers()) {
                PlayerUI.sendActionbar(p);
            }
        }, 0, 1);
        //Scoreboard
        Bukkit.getScheduler().runTaskTimer(StaticPrisons.getInstance(), CustomScoreboard::updateAllScoreboards, 0, 2);
        //Time played
        Bukkit.getScheduler().runTaskTimer(StaticPrisons.getInstance(), () -> {
            for (Player p : Bukkit.getOnlinePlayers()) {
                new PlayerData(p).addTimePlayed(1);
                Gang gang = Gang.getGang(p); //Gang stats
                if (gang != null) gang.addSecondsPlayed(1);
            }
        }, 0, 20);
        //Update tablist for all players
        Bukkit.getScheduler().runTaskTimer(StaticPrisons.getInstance(), () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                TabList.updateTabList(player);
            }
        }, 0, 60);
        //Reminder to vote
        Bukkit.getScheduler().runTaskTimer(StaticPrisons.getInstance(), () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                PlayerData playerData = new PlayerData(player);
                if (playerData.getLastVotedAt() < Instant.now().toEpochMilli() - 24 * 60 * 60 * 1000) {
                    player.sendMessage(text("You have not voted today! In order to win free rewards from the vote party, vote everyday. You can vote by typing ").color(RED).append(text("/vote").color(GREEN)));
                    player.showTitle(Title.title(text("/vote").color(RED).decorate(BOLD), text("You haven't voted today!").color(RED).decorate(ITALIC), Title.Times.times(Duration.ofMillis(5), Duration.ofMillis(40), Duration.ofMillis(5))));
                }
            }
        }, 0, 20 * 60 * 30);
        //Tips
        Bukkit.getScheduler().runTaskTimer(StaticPrisons.getInstance(), () -> {
            Component tip = Constants.TIPS.get(PrisonUtils.randomInt(0, Constants.TIPS.size() - 1));
            for (Player p : Bukkit.getOnlinePlayers()) if (!new PlayerData(p).getAreTipsDisabled()) p.sendMessage(tip);
        }, 20 * 60 * 5, 20 * 60 * 10);
        //Update all the leaderboards


        if (StaticPrisons.getInstance().isCitizensEnabled()) {
            System.out.println("asdf");
            Bukkit.getScheduler().runTaskTimer(StaticPrisons.getInstance(), LeaderboardManager::updateAll, 20, 20 * 60 * 30);
        }



        //Manages mine refills
        //Bukkit.getScheduler().runTaskTimer(StaticPrisons.getPlugin(), MineManager::refillManager, 0, 2);
        //Update Expired Auctions
        //Bukkit.getScheduler().runTaskTimer(Main.getMain(), AuctionHouseManager::expireAllExpiredAuctions, 120, 2);
        //Give everyone with potion enchants their effect(s)
        //Bukkit.getScheduler().runTaskTimer(StaticPrisons.getInstance(), EnchantEffects::giveEffects, 10, 400);

        //Chat Events
//        Bukkit.getScheduler().runTaskTimer(StaticPrisons.getInstance(), ChatEvents::runNewEvent, 20 * 60 * 12, 20 * 60 * 25);
        //Update Pickaxe Lore With Stats
        //Bukkit.getScheduler().runTaskTimer(StaticPrisons.getInstance(), PrisonPickaxe::dumpStatsToAllPickaxe, 0, 20 * 10);
        //Consistency enchant
        //Bukkit.getScheduler().runTaskTimer(StaticPrisons.getInstance(), ConsistencyEnchant::worker, 30, 20);


    }
}
