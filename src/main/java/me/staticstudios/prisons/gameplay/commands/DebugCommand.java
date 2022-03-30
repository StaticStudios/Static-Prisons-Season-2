package me.staticstudios.prisons.gameplay.commands;

import me.staticstudios.prisons.gameplay.crates.CrateManager;
import me.staticstudios.prisons.gameplay.gui.GUI;
import me.staticstudios.prisons.utils.CommandUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DebugCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(CommandUtils.getIncorrectCommandUsageMessage("/debug <what>"));
            return false;
        }
        switch (args[0].toLowerCase()) {
            case "crates", "crate" -> CrateManager.debugCrates();
        }
        return false;
    }
}
