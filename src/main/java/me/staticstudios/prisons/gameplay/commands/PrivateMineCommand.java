package me.staticstudios.prisons.gameplay.commands;

import me.staticstudios.prisons.core.data.serverData.PlayerData;
import me.staticstudios.prisons.gameplay.gui.GUI;
import me.staticstudios.prisons.core.mines.MineManager;
import me.staticstudios.prisons.core.mines.PrivateMine;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.time.Instant;

public class PrivateMineCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        if (!new PlayerData(player).getHasPrivateMine()) {
            player.sendMessage(ChatColor.RED + "You do not have a private mine! Private mines can be purchased on our store (store.static-studios.net) or they can be won as prizes from crates and/or events!");
            return false;
        }
        if (args.length == 0){
            GUI.getGUIPage("privateMines").open(player);
            return false;
        }
        if (args[0].equalsIgnoreCase("refill")) {
            PlayerData playerData = new PlayerData(player);
            if (!MineManager.checkIfMineExists("privateMine-" + player.getUniqueId())) {
                PrivateMine.create(playerData.getPrivateMineSquareSize(), playerData.getPrivateMineMat(), player);
            } else {
                PrivateMine mine = MineManager.getPrivateMine("privateMine-" + player.getUniqueId());
                if (mine.getLastRefilledAt() + 30 < Instant.now().getEpochSecond()) {
                    mine.refill();
                } else player.sendMessage(ChatColor.RED + "Please wait " + (mine.getLastRefilledAt() + 30 - Instant.now().getEpochSecond()) + " second(s) before refilling this mine!");
            }
        } else GUI.getGUIPage("privateMines").open(player);
        return false;
    }
}
