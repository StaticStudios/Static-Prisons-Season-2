package net.staticstudios.prisons.commands.normal;

import net.staticstudios.prisons.fishing.gui.FishermanMenus;
import net.staticstudios.prisons.gui.MainMenus;
import net.staticstudios.prisons.pvp.PvPManager;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class GUICommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player player)) return false;
        if (args.length > 0) {
            switch (args[0].toLowerCase()) {
                case "fisherman" -> {
                    if (player.getWorld().equals(PvPManager.PVP_WORLD) && player.getLocation().distance(new Location(player.getWorld(), -102.5, 101, 52.5)) < 6) { //the hard-coded location of the fisherman. This should probably soft-coded
                        FishermanMenus.mainMenu(player);
                    }
                    return true;
                }
            }
        }
        MainMenus.open(player);
        return false;
    }
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> list = new ArrayList<>();
        return list;
    }
}
