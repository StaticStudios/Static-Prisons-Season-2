package net.staticstudios.prisons.commands.votestore;

import net.staticstudios.prisons.customitems.handler.CustomItems;
import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.data.serverdata.ServerData;
import net.staticstudios.prisons.utils.Constants;
import net.staticstudios.prisons.utils.ItemUtils;
import net.staticstudios.prisons.utils.PlayerUtils;
import net.staticstudios.mines.utils.WeightedElements;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.time.Instant;

public class VoteParty {
    public static void addVoteToVoteParty() {
        ServerData.SERVER.setVoteParty(ServerData.SERVER.getVoteParty() + 1);
        if (ServerData.SERVER.getVoteParty() >= Constants.VOTES_NEEDED_FOR_VOTE_PARTY) {
            ServerData.SERVER.setVoteParty(0);
            for (Player p : Bukkit.getOnlinePlayers()) {
                PlayerData playerData = new PlayerData(p);
                if (playerData.getLastVotedAt() > Instant.now().toEpochMilli() - 24 * 60 * 60 * 1000) {
                    //They have voted in the past 24h
                    ItemStack reward = new WeightedElements<ItemStack>()
                            .add(CustomItems.getCommonCrateKey(5), 1)
                            .add(CustomItems.getRareCrateKey(4), 1)
                            .add(CustomItems.getEpicCrateKey(3), 1)
                            .add(CustomItems.getLegendaryCrateKey(2), 1)
                            .add(CustomItems.getStaticCrateKey(1), 1)
                            .getRandom();
                    if (playerData.getIsNitroBoosting()) reward.setAmount(reward.getAmount() * 2);
                    p.sendMessage(ChatColor.WHITE + "You have received " + reward.getAmount() + "x " + ItemUtils.getPrettyItemName(reward) + ChatColor.WHITE + " from the vote party! You won this because you have voted within the past 24 hours!");
                    PlayerUtils.addToInventory(p, reward);
                } else p.sendMessage(ChatColor.RED + "You missed out on winning rewards from the vote party because you have not voted in the past 24 hours! To make sure this doesn't happen again, type " + ChatColor.GREEN + "/vote");
            }
        }
    }
}
