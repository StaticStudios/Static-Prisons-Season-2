package net.staticstudios.prisons.pvp.outposts.commands;

import com.sk89q.worldguard.WorldGuard;
import net.kyori.adventure.text.Component;
import net.staticstudios.mines.utils.StaticMineUtils;
import net.staticstudios.prisons.pvp.outposts.OutpostManager;
import net.staticstudios.prisons.pvp.outposts.OutpostTypes;
import net.staticstudios.prisons.utils.ComponentUtil;
import net.staticstudios.prisons.utils.Prefix;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

import static net.staticstudios.prisons.pvp.PvPManager.PVP_WORLD;
import static net.staticstudios.prisons.pvp.PvPManager.WE_PVP_WORLD;

public class OutpostCommand implements TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player player)) {
            sender.sendMessage("You must be a player to use this command.");
            return true;
        }

        if (args.length < 1) {
            player.sendMessage(help());
            return true;
        }

        switch (args[0]) {
            case "create" -> {

                if (args.length < 4) {
                    player.sendMessage(Prefix.OUTPOST.append(Component.text("Usage: /outpost create <name> <region> <type>")));
                    return true;
                }

                if (!PVP_WORLD.equals(player.getWorld())) {
                    player.sendMessage(Prefix.OUTPOST.append(Component.text("You can only create an outpost in the PvP world.")));
                    return true;
                }

                if (!OutpostManager.regionExists(args[2])) {
                    player.sendMessage(Prefix.OUTPOST
                            .append(Component.text("The region "))
                            .append(Component.text(args[2]).color(ComponentUtil.GOLD))
                            .append(Component.text(" does not exist.")));

                    return true;
                }

                if (OutpostManager.getOutpost(args[2]) != null) {
                    player.sendMessage(Prefix.OUTPOST
                            .append(Component.text("The region "))
                            .append(Component.text(args[2]).color(ComponentUtil.GOLD))
                            .append(Component.text(" is already an outpost.")));
                    return true;
                }

                if (OutpostManager.getOutpostNames().contains(args[1])) {
                    player.sendMessage(Prefix.OUTPOST
                            .append(Component.text("The outpost name "))
                            .append(Component.text(args[1]).color(ComponentUtil.GOLD))
                            .append(Component.text(" is already in use.")));
                    return true;
                }

                String name = args[1].isBlank() ? args[2] : args[1];


                if (OutpostManager.createOutpost(args[2], name, args[3].toLowerCase())) {
                    player.sendMessage(Prefix.OUTPOST
                            .append(Component.text("Outpost "))
                            .append(Component.text(name).color(ComponentUtil.GOLD))
                            .append(Component.text(" created successfully.")));
                } else {
                    player.sendMessage(Prefix.OUTPOST.append(Component.text("Failed to create outpost. Check for invalid outpost type.")));
                }

                return true;
            }
            case "list" -> player.sendMessage(OutpostManager.getOutpostOverview());

            case "delete" -> {
                if (args.length < 2) {
                    player.sendMessage(Prefix.OUTPOST.append(Component.text("Usage: /outpost delete <outpost>")));
                    return true;
                }

                if (OutpostManager.getOutpost(args[1]) == null) {
                    player.sendMessage(Prefix.OUTPOST
                            .append(Component.text("The outpost "))
                            .append(Component.text(args[1]).color(ComponentUtil.GOLD))
                            .append(Component.text(" does not exist.")));
                    return true;
                }

                OutpostManager.deleteOutpost(args[1]);
                player.sendMessage(Prefix.OUTPOST
                        .append(Component.text("Outpost "))
                        .append(Component.text(args[1]).color(ComponentUtil.GOLD))
                        .append(Component.text(" deleted successfully.")));
                return true;
            }

            default -> {
                player.sendMessage(help());
                return true;
            }
        }


        return true;
    }

    private Component help() {
        return Prefix.OUTPOST.append(Component.text("Usage: /outpost create <name> <region> <type>"));
    }

    @Override
    public @NotNull List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {

        if (args.length == 1) {
            return StaticMineUtils.filterStrings(List.of("create", "list", "delete"), args[0]);
        }

        List<String> regionNames = new java.util.ArrayList<>(WorldGuard.getInstance().getPlatform().getRegionContainer().get(WE_PVP_WORLD).getRegions().keySet().stream().toList());

        regionNames.remove("__global__");

        if (args.length == 2) {
            if ("create".equalsIgnoreCase(args[0])) {
                return List.of("<name>");
            } else if ("delete".equalsIgnoreCase(args[0])) {
                return StaticMineUtils.filterStrings(OutpostManager.getOutpostNames(), args[1]);
            }
        }

        if (args.length == 3) {
            if ("create".equalsIgnoreCase(args[0])) {
                return StaticMineUtils.filterStrings(regionNames, args[2]);
            }
        }

        if (args.length == 4) {
            if ("create".equalsIgnoreCase(args[0]) && regionNames.contains(args[2])) {
                return StaticMineUtils.filterStrings(OutpostTypes.toStringList(), args[3]);
            }
        }

        return Collections.emptyList();
    }
}
