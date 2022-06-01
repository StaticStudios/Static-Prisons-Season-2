package net.staticstudios.prisons.commands;

import net.staticstudios.prisons.crates.CrateManager;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class DebugCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(PrisonUtils.Commands.getCorrectUsage("/debug <what>"));
            return false;
        }
        switch (args[0].toLowerCase()) {
            case "crates", "crate" -> CrateManager.debugCrates();
        }
        return false;
    }
}
