package net.staticstudios.prisons.commands;

import net.staticstudios.prisons.customItems.CustomItems;
import net.staticstudios.prisons.utils.Utils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class KeyallCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        ItemStack item;
        if (args.length == 0) {
            player.sendMessage(Utils.CommandUtils.getIncorrectCommandUsageMessage("/keyall <common|rare|epic|legendary|static|staticp|vote|kit|pickaxe>"));
            return false;
        }
        switch (args[0].toLowerCase()) {
            case "common" -> item = CustomItems.getCommonCrateKey(1);
            case "rare" -> item = CustomItems.getRareCrateKey(1);
            case "epic" -> item = CustomItems.getEpicCrateKey(1);
            case "legendary" -> item = CustomItems.getLegendaryCrateKey(1);
            case "static" -> item = CustomItems.getStaticCrateKey(1);
            case "staticp" -> item = CustomItems.getStaticpCrateKey(1);
            case "vote" -> item = CustomItems.getVoteCrateKey(1);
            case "kit" -> item = CustomItems.getKitCrateKey(1);
            case "pickaxe" -> item = CustomItems.getPickaxeCrateKey(1);
            default -> {
                player.sendMessage(Utils.CommandUtils.getIncorrectCommandUsageMessage("/keyall <common|rare|epic|legendary|static|staticp|vote|kit|pickaxe>"));
                return false;
            }
        }
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&d&l[Key All] &fYou have received 1x " + Utils.getPrettyItemName(item) + "&f from a key all!"));
            Utils.Players.addToInventory(p, item);
        }
        return false;
    }
}
