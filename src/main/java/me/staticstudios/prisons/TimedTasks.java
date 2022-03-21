package me.staticstudios.prisons;

import me.staticstudios.prisons.external.DiscordLink;
import me.staticstudios.prisons.gameplay.UI.PlayerUI;
import me.staticstudios.prisons.gameplay.auctionHouse.AuctionHouseManager;
import me.staticstudios.prisons.core.data.dataHandling.DataWriter;
import me.staticstudios.prisons.core.data.serverData.PlayerData;
import me.staticstudios.prisons.core.enchants.CustomEnchants;
import me.staticstudios.prisons.core.enchants.EnchantEffects;
import me.staticstudios.prisons.gameplay.events.EventManager;
import me.staticstudios.prisons.gameplay.leaderboards.LeaderboardManager;
import me.staticstudios.prisons.core.mines.MineManager;
import me.staticstudios.prisons.gameplay.UI.scoreboard.CustomScoreboard;
import me.staticstudios.prisons.gameplay.tablist.TabList;
import me.staticstudios.prisons.utils.StaticVars;
import me.staticstudios.prisons.utils.Utils;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.time.Instant;

public class TimedTasks {

    public static void initializeTasks() {
        //Auto saves data
        Bukkit.getScheduler().runTaskTimer(Main.getMain(), DataWriter::saveData, 0, 20 * 60 * 5);
        //Update the bots status
        Bukkit.getScheduler().runTaskTimer(Main.getMain(), DiscordLink::updatePlayerCount, 200, 200);
        //Manages mine refills
        Bukkit.getScheduler().runTaskTimer(Main.getMain(), MineManager::refillManager, 0, 2);
        //Show all players their backpacks
        Bukkit.getScheduler().runTaskTimer(Main.getMain(), () -> {
            for (Player p : Bukkit.getOnlinePlayers()) {
                PlayerUI.updateActionbar(p);
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
        //Consistency enchant
        Bukkit.getScheduler().runTaskTimer(Main.getMain(), () -> {
            for (Player p : Bukkit.getOnlinePlayers()) {
                PlayerData playerData = new PlayerData(p);
                if (!CustomEnchants.uuidToLastBlocksMined.containsKey(p.getUniqueId())) {
                    CustomEnchants.uuidToLastBlocksMined.put(p.getUniqueId(), playerData.getRawBlocksMined());
                    continue;
                }
                if (CustomEnchants.uuidToLastBlocksMined.get(p.getUniqueId()).compareTo(playerData.getRawBlocksMined()) == 0) { //The player has not mined any blocks since the last check
                    if (!CustomEnchants.uuidToTimeSinceLastMined.containsKey(p.getUniqueId())) CustomEnchants.uuidToTimeSinceLastMined.put(p.getUniqueId(), 0);
                    CustomEnchants.uuidToTimeSinceLastMined.put(p.getUniqueId(), CustomEnchants.uuidToTimeSinceLastMined.get(p.getUniqueId()) + 1);
                    if (CustomEnchants.uuidToTimeSinceLastMined.get(p.getUniqueId()) >= 120) {
                        //Player's multiplier has expired
                        if (CustomEnchants.uuidToTempTokenMultiplier.containsKey(p.getUniqueId())) {
                            p.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "Consistency " + ChatColor.DARK_GRAY + ChatColor.BOLD + ">" + ChatColor.RED + " Your multiplier has expired due to your inactivity!");
                            CustomEnchants.uuidToTempTokenMultiplier.remove(p.getUniqueId());
                            CustomEnchants.uuidToTimeMiningConsistently.remove(p.getUniqueId());
                        }
                    }
                } else {
                    CustomEnchants.uuidToLastBlocksMined.put(p.getUniqueId(), playerData.getRawBlocksMined());
                    CustomEnchants.uuidToTimeSinceLastMined.put(p.getUniqueId(), 0);
                    if (!CustomEnchants.uuidToTimeMiningConsistently.containsKey(p.getUniqueId())) CustomEnchants.uuidToTimeMiningConsistently.put(p.getUniqueId(), 0);
                    if (CustomEnchants.uuidToTimeMiningConsistently.get(p.getUniqueId()) % 120 == 0 && CustomEnchants.uuidToTimeMiningConsistently.get(p.getUniqueId()) != 0) {
                        if (!Utils.checkIsPrisonPickaxe(p.getInventory().getItemInMainHand()) || CustomEnchants.getEnchantLevel(p.getInventory().getItemInMainHand(), "consistency") < 1) continue;
                        if (!CustomEnchants.uuidToTempTokenMultiplier.containsKey(p.getUniqueId())) CustomEnchants.uuidToTempTokenMultiplier.put(p.getUniqueId(), 0d);
                        if (CustomEnchants.uuidToTempTokenMultiplier.get(p.getUniqueId()) >= 0.1 * CustomEnchants.getEnchantLevel(p.getInventory().getItemInMainHand(), "consistency")) continue;
                        CustomEnchants.uuidToTempTokenMultiplier.put(p.getUniqueId(), CustomEnchants.uuidToTempTokenMultiplier.get(p.getUniqueId()) + 0.01d);
                        p.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "Consistency " + ChatColor.DARK_GRAY + ChatColor.BOLD + ">" + ChatColor.GREEN + " +0.01x token multiplier due to your consistent mining activity! (" + Utils.addCommasToNumber(CustomEnchants.uuidToTimeMiningConsistently.get(p.getUniqueId())) + " seconds) Current multiplier: +x" + new DecimalFormat("#.##").format(CustomEnchants.uuidToTempTokenMultiplier.get(p.getUniqueId())));
                    }
                    CustomEnchants.uuidToTimeMiningConsistently.put(p.getUniqueId(), CustomEnchants.uuidToTimeMiningConsistently.get(p.getUniqueId()) + 1);
                }
            }
        }, 30, 20);



    }
}
