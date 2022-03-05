package me.staticstudios.prisons.vote_store;

import me.staticstudios.prisons.customItems.CustomItems;
import me.staticstudios.prisons.data.serverData.PlayerData;
import me.staticstudios.prisons.data.serverData.ServerData;
import me.staticstudios.prisons.utils.StaticVars;
import me.staticstudios.prisons.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.time.Instant;

public class VoteParty {
    public static void addVoteToVoteParty() {
        new ServerData().setVoteParty(new ServerData().getVoteParty() + 1);
        if (new ServerData().getVoteParty() >= StaticVars.VOTES_NEEDED_FOR_VOTE_PARTY) {
            new ServerData().setVoteParty(0);
            for (Player p : Bukkit.getOnlinePlayers()) {
                PlayerData playerData = new PlayerData(p);
                if (playerData.getLastVotedAt() > Instant.now().toEpochMilli() - 24 * 60 * 60 * 1000) {
                    //They have voted in the past 24h
                    ItemStack reward = CustomItems.getCommonCrateKey(5);
                    switch (Utils.randomInt(1, 5)) {
                        case 1 -> reward = CustomItems.getCommonCrateKey(5);
                        case 2 -> reward = CustomItems.getRareCrateKey(4);
                        case 3 -> reward = CustomItems.getEpicCrateKey(3);
                        case 4 -> reward = CustomItems.getLegendaryCrateKey(2);
                        case 5 -> reward = CustomItems.getStaticpCrateKey(1);
                    }
                    if (playerData.getIsNitroBoosting()) reward.setAmount(reward.getAmount() * 2);
                    p.sendMessage(ChatColor.WHITE + "You have received " + reward.getAmount() + "x " + Utils.getPrettyItemName(reward) + ChatColor.WHITE + " from the vote party! You won this because you have voted within the past 24 hours!");
                    Utils.addItemToPlayersInventoryAndDropExtra(p, reward);
                } else p.sendMessage(ChatColor.RED + "You missed out on winning rewards from the vote party because you have not voted in the past 24 hours! To make sure this doesn't happen again, type " + ChatColor.GREEN + "/vote");
            }
        }
    }
}
