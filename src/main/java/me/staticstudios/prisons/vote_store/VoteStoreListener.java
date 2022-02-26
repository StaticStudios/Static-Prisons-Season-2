package me.staticstudios.prisons.vote_store;

import me.staticstudios.prisons.customItems.CustomItems;
import me.staticstudios.prisons.data.serverData.PlayerData;
import me.staticstudios.prisons.data.serverData.ServerData;
import me.staticstudios.prisons.misc.Warps;
import me.staticstudios.prisons.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.math.BigInteger;
import java.time.Instant;
import java.util.UUID;

public class VoteStoreListener implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) return false;
        if (args.length < 2) return false;
        switch (args[0].toLowerCase()) {
            case "vote" -> {
                for (String key: new ServerData().getPlayerNamesToUUIDsMap().keySet()) {
                    if (key.equalsIgnoreCase(args[1])) {
                        String uuid = new ServerData().getPlayerUUIDFromName(key);
                        PlayerData playerData = new PlayerData(uuid);
                        playerData.addVotes(BigInteger.ONE);
                        playerData.setLastVotedAt(Instant.now().toEpochMilli());
                        Player player = Bukkit.getPlayer(UUID.fromString(uuid));
                        Utils.addItemToPlayersInventoryAndDropExtra(player, CustomItems.getVoteCrateKey(1));
                        for (Player p : Bukkit.getOnlinePlayers()) p.sendMessage(ChatColor.AQUA + player.getName() + ChatColor.WHITE + " voted for the server with " + ChatColor.GREEN + "/vote");
                        player.sendMessage(ChatColor.AQUA + "You have received 1x Vote Key!");
                        VoteParty.addVoteToVoteParty();
                        return true;
                    }
                }
            }
            case "store" -> {

            }
        }
        return false;
    }
}
