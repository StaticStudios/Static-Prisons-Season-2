package net.staticstudios.prisons.commands.votestore;

import net.staticstudios.mines.utils.StaticMineUtils;
import net.staticstudios.prisons.customitems.CustomItems;
import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.data.serverdata.ServerData;
import net.staticstudios.prisons.reclaim.PackageHandler;
import net.staticstudios.prisons.utils.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class VoteStoreListener implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) return false;
        if (args.length < 2) return false;
        switch (args[0].toLowerCase()) {
            case "vote" -> {
                UUID uuid = ServerData.PLAYERS.getUUIDIgnoreCase(args[1]);
                PlayerData playerData = new PlayerData(uuid);
                playerData.addVotes(1);
                playerData.setLastVotedAt(Instant.now().toEpochMilli());
                Player player = Bukkit.getPlayer(uuid);
                PlayerUtils.addToInventory(player, CustomItems.getVoteCrateKey(1));
                for (Player p : Bukkit.getOnlinePlayers())
                    p.sendMessage(ChatColor.AQUA + player.getName() + ChatColor.WHITE + " voted for the server with " + ChatColor.GREEN + "/vote");
                player.sendMessage(ChatColor.AQUA + "You have received 1x Vote Key!");
                VoteParty.addVoteToVoteParty();
                return true;
            }
            case "tebex" -> {
                if (args.length < 3) {
                    Bukkit.getServer().getLogger().warning("Received an incorrect Tebex request!");
                    return false;
                }
                Player player = Bukkit.getPlayer(args[2]);
                assert player != null;
                PlayerData playerData = new PlayerData(player);
                PackageHandler.claimPackage(player.getUniqueId(), args[1], args);
            }
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (args.length == 1) {
            return List.of("vote", "tebex");
        }

        if (args.length == 2) {
            return StaticMineUtils.filterStrings(ServerData.PLAYERS.getAllNames(), args[1]);
        }

        if (args.length == 3) {
            return List.of("package");
        }
        return null;
    }
}
