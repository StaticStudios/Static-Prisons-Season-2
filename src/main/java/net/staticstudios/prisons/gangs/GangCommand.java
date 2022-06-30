package net.staticstudios.prisons.gangs;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class GangCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) return false;
        if (args.length == 0) {
            GangMenus.openYourGang(player, true);
            return false;
        }
        switch (args[0].toLowerCase()) {
            default -> {
                //todo open gui
                return false;
            }
            case "create" -> {
                //todo open menu to create gang
            }
            case "join" -> {
                //todo join gang
            }
            case "leave" -> {
                //todo leave gang
            }
            case "kick" -> {
                //todo kick from gang
            }
            case "invite" -> {
                //todo invite to gang
            }
            case "accept" -> {
                //todo accept invite
            }
            case "deny", "decline" -> {
                //todo deny invite
            }
            case "info", "about" -> {
                //todo info about gang
            }
            case "settings" -> {
                //todo settings for gang
            }
        }
        return false;
    }
}
