package me.staticstudios.prisons.commands;

import me.staticstudios.prisons.data.serverData.PlayerData;
import me.staticstudios.prisons.data.serverData.ServerData;
import me.staticstudios.prisons.gui.GUI;
import me.staticstudios.prisons.islands.IslandManager;
import me.staticstudios.prisons.islands.SkyBlockIsland;
import me.staticstudios.prisons.islands.invites.SkyblockIslandInviteManager;
import me.staticstudios.prisons.misc.Warps;
import me.staticstudios.prisons.utils.CommandUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;
import java.util.UUID;

public class IslandCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            CommandUtils.logConsoleCannotUseThisCommand();
            return false;
        }
        Player player = (Player) sender;
        if (args.length == 0) {
            GUI.getGUIPage("islandsMain").open(player); //TODO: finish this
            return false;
        }
        PlayerData playerData = new PlayerData(player);
        switch (args[0].toLowerCase()) {
            case "visit" -> {
                SkyBlockIsland island;
                if (args.length == 1) {
                    player.sendMessage(CommandUtils.getIncorrectCommandUsageMessage("/visit <cell name to visit>"));
                    break;
                }
                if (!new ServerData().getSkyblockIslandNamesToUUIDsMap().keySet().contains(args[1])) {
                    player.sendMessage(ChatColor.RED + "Could not find and island with the name " + ChatColor.AQUA + args[1]);
                    break;
                }
                island = new SkyBlockIsland(new ServerData().getIslandUUIDFromName(args[1]));
                if (!island.getAllowVisitors()) {
                    player.sendMessage(ChatColor.RED + "This cell does not allow warping!");
                    break;
                }
                island.teleportPlayerToVisitorWarp(player);
            }
            case "settings" -> {
                if (!playerData.getIfPlayerHasIsland()) {
                    player.sendMessage(ChatColor.RED + "You cannot run this command as you do not own/are part of a cell!");
                    break;
                }
                GUI.getGUIPage("islandSettings").open(player);
            }
            case "setmemberwarp" -> {
                SkyBlockIsland island = playerData.getPlayerIsland();
                if (!playerData.getIfPlayerHasIsland()) {
                    player.sendMessage(ChatColor.RED + "You cannot run this command as you do not own/are part of a cell!");
                    break;
                }
                if (island.getIslandMemberUUIDS().contains(player.getUniqueId().toString())) {
                    player.sendMessage(ChatColor.RED + "Only cell admins+ can do this!");
                    break;
                }
                if (!IslandManager.playersInIslands.get(player.getUniqueId()).equals(UUID.fromString(island.getUUID()))) {
                    player.sendMessage(ChatColor.RED + "You must be at your cell to run this command!");
                    break;
                }
                island.setIslandMemberWarpX(((double) Math.round(player.getLocation().getX() * 10)) / 10);
                island.setIslandMemberWarpY(((double) Math.round(player.getLocation().getY() * 10)) / 10);
                island.setIslandMemberWarpZ(((double) Math.round(player.getLocation().getZ() * 10)) / 10);
                player.sendMessage(ChatColor.GREEN + "You have set your cell member warp to: " + ChatColor.AQUA + "X=" + new DecimalFormat("0.##").format(island.getIslandMemberWarpX()) + ", y=" + new DecimalFormat("0.##").format(island.getIslandMemberWarpY()) + ", z=" + new DecimalFormat("0.##").format(island.getIslandMemberWarpZ()));
            }
            case "setvisitorwarp" -> {
                SkyBlockIsland island = playerData.getPlayerIsland();
                if (!playerData.getIfPlayerHasIsland()) {
                    player.sendMessage(ChatColor.RED + "You cannot run this command as you do not own/are part of a cell!");
                    break;
                }
                if (island.getIslandMemberUUIDS().contains(player.getUniqueId().toString())) {
                    player.sendMessage(ChatColor.RED + "Only cell admins+ can do this!");
                    break;
                }
                if (!IslandManager.playersInIslands.get(player.getUniqueId()).equals(UUID.fromString(island.getUUID()))) {
                    player.sendMessage(ChatColor.RED + "You must be at your cell to run this command!");
                    break;
                }
                island.setIslandVisitorWarpX(((double) Math.round(player.getLocation().getX() * 10)) / 10);
                island.setIslandVisitorWarpY(((double) Math.round(player.getLocation().getY() * 10)) / 10);
                island.setIslandVisitorWarpZ(((double) Math.round(player.getLocation().getZ() * 10)) / 10);
                player.sendMessage(ChatColor.GREEN + "You have set your cell visitor warp to: " + ChatColor.AQUA + "X=" + new DecimalFormat("0.##").format(island.getIslandVisitorWarpX()) + ", y=" + new DecimalFormat("0.##").format(island.getIslandVisitorWarpY()) + ", z=" + new DecimalFormat("0.##").format(island.getIslandVisitorWarpZ()));
            }
            case "delete" -> {
                SkyBlockIsland island = playerData.getPlayerIsland();
                if (!playerData.getIfPlayerHasIsland()) {
                    player.sendMessage(ChatColor.RED + "You cannot run this command as you do not own/are part of a cell!");
                    break;
                }
                if (!island.getIslandOwnerUUID().equals(player.getUniqueId().toString())) {
                    player.sendMessage(ChatColor.RED + "Only the island owner can do this!");
                    break;
                }
                GUI.getGUIPage("islandDelete").open(player);
                player.sendMessage(ChatColor.RED + "Deleting an island cannot be undone!");
            }
            case "banlist" -> {
                SkyBlockIsland island = playerData.getPlayerIsland();
                if (!playerData.getIfPlayerHasIsland()) {
                    player.sendMessage(ChatColor.RED + "You cannot run this command as you do not own/are part of a cell!");
                    break;
                }
                StringBuilder response = new StringBuilder(ChatColor.RED + "The following users are banned from your cell: \n");
                for (String uuid : island.getBannedPlayerUUIDS()) {
                    response.append(ChatColor.GREEN).append(new ServerData().getPlayerNameFromUUID(uuid)).append("\n");
                }
                player.sendMessage(response.toString());
            }
            case "rename" -> {
                SkyBlockIsland island = playerData.getPlayerIsland();
                if (!playerData.getIfPlayerHasIsland()) {
                    player.sendMessage(ChatColor.RED + "You cannot run this command as you do not own/are part of a cell!");
                    break;
                }
                if (args.length == 1) {
                    player.sendMessage(CommandUtils.getIncorrectCommandUsageMessage("/cell rename <new name>"));
                    break;
                }
                if (island.getIslandMemberUUIDS().contains(player.getUniqueId().toString())) {
                    player.sendMessage(ChatColor.RED + "Only cell admins+ can do this!");
                    break;
                }
                if (island.getIslandName().equals(args[1])) {
                    player.sendMessage(ChatColor.RED + "Your cell is already named " + ChatColor.AQUA + island.getIslandName());
                    break;
                }
                if (new ServerData().getSkyblockIslandNamesToUUIDsMap().keySet().contains(args[1])) {
                    player.sendMessage(ChatColor.RED + "A cell with this name already exists!");
                    break;
                }
                if (args[1].length() > 32) {
                    player.sendMessage(ChatColor.RED + "Your cell name may not be longer than 32 characters!");
                    break;
                }
                island.setIslandName(args[1]);
                for (String uuid : island.getIslandPlayerUUIDS()) {
                    if (Bukkit.getPlayer(UUID.fromString(uuid)) != null) Bukkit.getPlayer(UUID.fromString(uuid)).sendMessage(ChatColor.LIGHT_PURPLE + player.getName() + ChatColor.GREEN + " has just renamed your island to: " + ChatColor.AQUA + island.getIslandName());
                }
            }
            case "about", "info" -> {
                SkyBlockIsland island;
                if (args.length == 1) {
                    if (!playerData.getIfPlayerHasIsland()) {
                        player.sendMessage(ChatColor.RED + "You cannot run this command as you do not own/are part of a cell!");
                        break;
                    }
                    island = playerData.getPlayerIsland();

                } else {
                    if (!new ServerData().getSkyblockIslandNamesToUUIDsMap().keySet().contains(args[1])) {
                        if (new ServerData().getSkyblockIslandUUIDsToNamesMap().containsKey(args[1])) {
                            island = new SkyBlockIsland(args[1]);
                        } else {
                            player.sendMessage(ChatColor.RED + "Could not find and cell with the name " + ChatColor.AQUA + args[1]);
                            break;
                        }
                    } else {
                        island = new SkyBlockIsland(new ServerData().getIslandUUIDFromName(args[1]));
                    }
                }
                StringBuilder message = new StringBuilder(ChatColor.AQUA + island.getIslandName() + "'s Info:" + "\n");
                message.append(ChatColor.GRAY).append("------------").append("\n");
                message.append(ChatColor.LIGHT_PURPLE).append("Members (").append(island.getIslandPlayerUUIDS().size()).append("): ");
                ServerData serverData = new ServerData();
                for (int i = 0; i < island.getIslandPlayerUUIDS().size(); i++) {
                    String memberUUID = island.getIslandPlayerUUIDS().get(i);
                    String name;
                    if (Bukkit.getPlayer(UUID.fromString(memberUUID)) == null) {
                        name = ChatColor.GRAY + serverData.getPlayerNameFromUUID(memberUUID);
                    } else name = ChatColor.GREEN + serverData.getPlayerNameFromUUID(memberUUID);
                    message.append(name);
                    if (i + 1 != island.getIslandPlayerUUIDS().size()) {
                        message.append(", ");
                    }
                }
                message.append("\n");
                message.append(ChatColor.LIGHT_PURPLE).append("Level: ").append(ChatColor.AQUA).append("null").append("\n");
                message.append(ChatColor.LIGHT_PURPLE).append("Value: ").append(ChatColor.AQUA).append("null").append("\n");
                message.append(ChatColor.LIGHT_PURPLE).append("Bank: ").append(ChatColor.AQUA).append("null").append("\n");
                message.append(ChatColor.GRAY).append("------------").append("\n");
                message.append(ChatColor.DARK_GRAY).append(ChatColor.ITALIC).append("ID: ").append(island.getUUID());
                player.sendMessage(message.toString());
            }
            case "create" -> {
                if (playerData.getIfPlayerHasIsland()) {
                    player.sendMessage(ChatColor.RED + "You cannot run this command as you already own/are part of a cell!");
                    break;
                }
                SkyBlockIsland.createNewIsland(player);
            }
            case "manage", "members" -> {
                if (!playerData.getIfPlayerHasIsland()) {
                    player.sendMessage(ChatColor.RED + "You cannot run this command as you do not own/are part of a cell!");
                    break;
                }
                GUI.getGUIPage("islandMembers").open(player);
            }
            case "go" -> {
                if (!playerData.getIfPlayerHasIsland()) {
                    player.sendMessage(ChatColor.RED + "You cannot run this command as you do not own/are part of a cell!");
                    break;
                }
                SkyBlockIsland island = new SkyBlockIsland(playerData.getPlayerIslandUUID());
                island.teleportPlayerToMemberWarp(player);
            }
            case "kick" -> {
                if (!playerData.getIfPlayerHasIsland()) {
                    player.sendMessage(ChatColor.RED + "You cannot run this command as you do not own/are part of a cell!");
                    break;
                }
                String uuid = new ServerData().getPlayerUUIDFromName(args[1]);
                SkyBlockIsland island = new SkyBlockIsland(playerData.getPlayerIslandUUID());
                if (!island.getIslandPlayerUUIDS().contains(uuid)) {
                    player.sendMessage(ChatColor.RED + "This user is not a part of your cell!");
                }
                if (island.getIslandMemberUUIDS().contains(player.getUniqueId().toString())) {
                    if (!island.getIslandMemberUUIDS().contains(uuid)) {
                        if (island.getIslandAdminUUIDS().contains(player.getUniqueId().toString())) {
                            player.sendMessage(ChatColor.RED + "You cannot kick this user as you are not a high enough rank!");
                            break;
                        } else if (island.getIslandManagerUUID().equals(uuid)) {
                            if (island.getIslandManagerUUID().equals(player.getUniqueId().toString())) {
                                player.sendMessage(ChatColor.RED + "You cannot kick yourself silly!");
                                break;
                            }
                        } else if (island.getIslandOwnerUUID().equals(player.getUniqueId().toString())) {
                            if (island.getIslandOwnerUUID().equals(player.getUniqueId().toString())) {
                                player.sendMessage(ChatColor.RED + "You cannot kick yourself silly!");
                            } else {
                                player.sendMessage(ChatColor.RED + "You cannot kick the cell owner!");
                            }
                            break;
                        }
                    }
                    PlayerData _playerData = new PlayerData(uuid);
                    _playerData.setIfPlayerHasIsland(false);
                    _playerData.setPlayerIslandUUID("");
                    if (island.getIslandManagerUUID().equals(uuid)) {
                        island.setIslandManagerUUID("");
                    }
                    if (island.getIslandAdminUUIDS().contains(uuid)) {
                        island.removeIslandAdminUUID(uuid);
                    }
                    if (island.getIslandMemberUUIDS().contains(uuid)) {
                        island.removeIslandMemberUUID(uuid);
                    }
                    island.getIslandPlayerUUIDS().remove(uuid);
                    if (Bukkit.getPlayer(UUID.fromString(uuid)) != null) {
                        Bukkit.getPlayer(UUID.fromString(uuid)).sendMessage(ChatColor.RED + "You have been kicked from your cell.");
                        if (IslandManager.playersInIslands.get(player.getUniqueId()).equals(UUID.fromString(island.getUUID()))) {
                            Warps.warpToSpawn(Bukkit.getPlayer(UUID.fromString(uuid)));
                        }
                    }
                    for (String _uuid : island.getIslandPlayerUUIDS()) {
                        if (Bukkit.getPlayer(UUID.fromString(_uuid)) != null) Bukkit.getPlayer(UUID.fromString(_uuid)).sendMessage(ChatColor.AQUA + new ServerData().getPlayerNameFromUUID(uuid) + ChatColor.GREEN + " has been kicked from your island by: " + ChatColor.LIGHT_PURPLE + new ServerData().getPlayerNameFromUUID(player.getUniqueId().toString()));
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "Only cell admins+ can do this!");
                }
            }
            case "ban" -> {
                if (!playerData.getIfPlayerHasIsland()) {
                    player.sendMessage(ChatColor.RED + "You cannot run this command as you do not own/are part of a cell!");
                    break;
                }
                String uuid = new ServerData().getPlayerUUIDFromName(args[1]);
                SkyBlockIsland island = new SkyBlockIsland(playerData.getPlayerIslandUUID());
                if (island.getIslandManagerUUID().equals(player.getUniqueId().toString()) || island.getIslandOwnerUUID().equals(player.getUniqueId().toString())) {
                    if (island.getIslandManagerUUID().equals(uuid)) {
                        if (uuid.equals(player.getUniqueId().toString())) {
                            player.sendMessage(ChatColor.RED + "You cannot ban yourself silly!");
                            break;
                        }
                    } else if (island.getIslandOwnerUUID().equals(uuid)) {
                        if (uuid.equals(player.getUniqueId().toString())) {
                            player.sendMessage(ChatColor.RED + "You cannot ban yourself silly!");
                        } else {
                            player.sendMessage(ChatColor.RED + "You cannot ban the cell owner.");
                        }
                        break;
                    }
                    if (!island.getIslandPlayerUUIDS().contains(uuid)) {
                        player.sendMessage(ChatColor.RED + "This user is not a part of your cell!");
                    }

                    PlayerData _playerData = new PlayerData(uuid);
                    _playerData.setIfPlayerHasIsland(false);
                    _playerData.setPlayerIslandUUID("");
                    if (island.getIslandManagerUUID().equals(uuid)) {
                        island.setIslandManagerUUID("");
                    }
                    if (island.getIslandAdminUUIDS().contains(uuid)) {
                        island.removeIslandAdminUUID(uuid);
                    }
                    if (island.getIslandMemberUUIDS().contains(uuid)) {
                        island.removeIslandMemberUUID(uuid);
                    }
                    island.getIslandPlayerUUIDS().remove(uuid);
                    island.addBannedPlayerUUID(uuid);
                    if (Bukkit.getPlayer(UUID.fromString(uuid)) != null) {
                        Bukkit.getPlayer(UUID.fromString(uuid)).sendMessage(ChatColor.RED + "You have been banned from your cell.");
                        if (IslandManager.playersInIslands.get(player.getUniqueId()).equals(UUID.fromString(island.getUUID()))) {
                            Warps.warpToSpawn(Bukkit.getPlayer(UUID.fromString(uuid)));
                        }
                    }
                    for (String _uuid : island.getIslandPlayerUUIDS()) {
                        if (Bukkit.getPlayer(UUID.fromString(_uuid)) != null) Bukkit.getPlayer(UUID.fromString(_uuid)).sendMessage(ChatColor.AQUA + new ServerData().getPlayerNameFromUUID(uuid) + ChatColor.GREEN + " has been banned from your cell by: " + ChatColor.LIGHT_PURPLE + new ServerData().getPlayerNameFromUUID(player.getUniqueId().toString()));
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "Only the cell manager and the cell can do this!");
                }
            }
            case "unban" -> {
                if (!playerData.getIfPlayerHasIsland()) {
                    player.sendMessage(ChatColor.RED + "You cannot run this command as you do not own/are part of an island!");
                    break;
                }
                SkyBlockIsland island = new SkyBlockIsland(playerData.getPlayerIslandUUID());
                if (island.getIslandManagerUUID().equals(player.getUniqueId().toString()) || island.getIslandOwnerUUID().equals(player.getUniqueId().toString())) {
                    if (!new ServerData().getPlayerNamesToUUIDsMap().keySet().contains(args[1])) {
                        player.sendMessage(ChatColor.RED + "Could not find the player specified!");
                        break;
                    }
                    String uuid = new ServerData().getPlayerUUIDFromName(args[1]);
                    if (island.getBannedPlayerUUIDS().contains(uuid)) {
                        island.removeBannedPlayerUUID(uuid);
                        player.sendMessage(ChatColor.AQUA + args[1] + ChatColor.GREEN + " has been unbanned from your cell!");
                    } else {
                        player.sendMessage(ChatColor.RED + "This player is not banned!");
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "Only the cell manager and the cell owner can do this!");
                }
            }
            case "leave" -> {
                if (!playerData.getIfPlayerHasIsland()) {
                    player.sendMessage(ChatColor.RED + "You cannot run this command as you do not have a cell to leave!");
                    break;
                }
                SkyBlockIsland island = playerData.getPlayerIsland();
                if (island.getIslandOwnerUUID().equals(player.getUniqueId().toString())) {
                    player.sendMessage(ChatColor.RED + "You cannot leave this cell as you are the cell owner. Please delete it or transfer ownership of this cell before doing this!");
                    break;
                }
                playerData.setIfPlayerHasIsland(false);
                playerData.setPlayerIslandUUID("");
                if (island.getIslandManagerUUID().equals(player.getUniqueId().toString())) {
                    island.setIslandManagerUUID("");
                }
                if (island.getIslandAdminUUIDS().contains(player.getUniqueId().toString())) {
                    island.removeIslandAdminUUID(player.getUniqueId().toString());
                }
                if (island.getIslandMemberUUIDS().contains(player.getUniqueId().toString())) {
                    island.removeIslandMemberUUID(player.getUniqueId().toString());
                }
                island.getIslandPlayerUUIDS().remove(player.getUniqueId().toString());
                    player.sendMessage(ChatColor.RED + "You have left your cell.");
                if (IslandManager.playersInIslands.get(player.getUniqueId()).equals(UUID.fromString(island.getUUID()))) {
                        Warps.warpToSpawn(player);
                    }
                for (String _uuid : island.getIslandPlayerUUIDS()) {
                    if (Bukkit.getPlayer(UUID.fromString(_uuid)) != null) Bukkit.getPlayer(UUID.fromString(_uuid)).sendMessage(ChatColor.AQUA + player.getName() + ChatColor.GREEN + " has left your cell.");
                }
            }
            case "invite" -> {
                if (!playerData.getIfPlayerHasIsland()) {
                    player.sendMessage(ChatColor.RED + "You cannot run this command as you are not part of a cell!");
                    break;
                }
                if (playerData.getPlayerIsland().getIslandMemberUUIDS().contains(player.getUniqueId().toString())) {
                    player.sendMessage(ChatColor.RED + "You do not have permission to invite players to your cell!");
                    break;
                }
                if (playerData.getPlayerIsland().getIslandPlayerUUIDS().size() >= SkyBlockIsland.MAX_PLAYERS_PER_ISLAND) {
                    player.sendMessage(ChatColor.RED + "Your cell is currently full!");
                    break;
                }
                if (!playerData.getPlayerIsland().getAllowInvites()) {
                    player.sendMessage(ChatColor.RED + "Your cell is currently not allowing people to be invited, please change this in your cell's settings.");
                    break;
                }
                if (args.length == 1) {
                    player.sendMessage(CommandUtils.getIncorrectCommandUsageMessage("/cell invite <player>"));
                    break;
                } else if (Bukkit.getPlayer(args[1]) == null) {
                    player.sendMessage(CommandUtils.getIncorrectCommandUsageMessage("/cell invite <player>"));
                    break;
                }
                Player invited = Bukkit.getPlayer(args[1]);
                PlayerData _playerData = new PlayerData(invited);
                if (_playerData.getIfPlayerHasIsland()) {
                    player.sendMessage(ChatColor.RED + "This player is already a member of a cell!");
                    break;
                }
                player.sendMessage(ChatColor.GREEN + "You have just invited " + ChatColor.AQUA + invited.getName() + ChatColor.GREEN + " to join " + ChatColor.LIGHT_PURPLE + playerData.getPlayerIsland().getIslandName());
                invited.sendMessage(ChatColor.GREEN + "You have just been invited to join " + ChatColor.LIGHT_PURPLE + playerData.getPlayerIsland().getIslandName() + ChatColor.RESET + "" + ChatColor.GREEN + " by " + ChatColor.AQUA + player.getName() + ChatColor.RESET + "\n" + ChatColor.GRAY + "Type \"/cell invites\" to view your current invites.");
                SkyblockIslandInviteManager.addIslandInvite(invited.getUniqueId().toString(), player.getUniqueId().toString(), playerData.getPlayerIslandUUID());
            }
            case "invites" -> GUI.getGUIPage("islandInvites").open(player);
        }
        return false;
    }
}
