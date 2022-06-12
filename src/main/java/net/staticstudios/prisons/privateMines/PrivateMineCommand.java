package net.staticstudios.prisons.privateMines;


import net.staticstudios.prisons.data.dataHandling.PlayerData;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PrivateMineCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player player)) return false;
        PlayerData playerData = new PlayerData(player);
        if (args.length == 0) {
            PrivateMineMenus.open(player, true);
            return false;
        }
        switch (args[0].toLowerCase()) {
            default -> {
                PrivateMineMenus.open(player, true);
            }
            case "refill" -> {
                if (!PrivateMine.playerHasPrivateMine(player)) {
                    player.sendMessage("You don't have a private mine!");
                    return false;
                }
                if (!PrivateMine.getPrivateMineFromPlayerWithoutLoading(player).isLoaded) {
                    player.sendMessage(ChatColor.AQUA + "Loading your private mine...");
                    PrivateMine.getPrivateMineFromPlayer(player).thenAccept(pm -> pm.warpTo(player));
                } else PrivateMine.getPrivateMineFromPlayer(player).thenAccept(pm -> pm.manualRefill(player));
            }
            case "info", "about" -> PrivateMine.getPrivateMineFromPlayerWithoutLoading(player).sendInfo(player);
            case "go", "warp" -> {
                if (!PrivateMine.playerHasPrivateMine(player)) {
                    player.sendMessage("You don't have a private mine!");
                    return false;
                }
                if (!PrivateMine.getPrivateMineFromPlayerWithoutLoading(player).isLoaded) player.sendMessage(ChatColor.AQUA + "Loading your private mine...");
                PrivateMine.getPrivateMineFromPlayer(player).thenAccept(pm -> pm.warpTo(player));
            }
        }
        return false;
    }
    //todo tab completion
}
