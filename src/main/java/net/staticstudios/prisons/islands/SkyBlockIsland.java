package net.staticstudios.prisons.islands;


import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import io.papermc.lib.PaperLib;
import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.data.IslandData;
import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.islands.invites.IslandInvites;
import net.staticstudios.prisons.islands.invites.SkyblockIslandInviteManager;
import net.staticstudios.prisons.misc.Warps;
import net.staticstudios.prisons.data.serverData.ServerData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;
import java.util.logging.Level;

public class SkyBlockIsland extends IslandData {
    public static final int MAX_PLAYERS_PER_ISLAND = 5;
    String uuid; //Use random uuids, not player ids as ownership can be transferred
    public SkyBlockIsland(String uuid) {
        super(uuid);
        this.uuid = uuid;
    }
    public SkyBlockIsland(UUID uuid) {
        super(uuid);
        this.uuid = uuid.toString();
    }
    public void teleportPlayerToMemberWarp(Player player) {
        PaperLib.teleportAsync(player, new Location(Bukkit.getWorld("islands"), getIslandMemberWarpX(), getIslandMemberWarpY(), getIslandMemberWarpZ()));
        IslandManager.playersInIslands.put(player.getUniqueId(), UUID.fromString(getUUID()));
        player.setAllowFlight(true);
        Bukkit.getScheduler().runTaskLater(StaticPrisons.getInstance(), () -> player.setAllowFlight(true), 30);
    }
    public void teleportPlayerToVisitorWarp(Player player) {
        PaperLib.teleportAsync(player, new Location(Bukkit.getWorld("islands"), getIslandVisitorWarpX(), getIslandVisitorWarpY(), getIslandVisitorWarpZ()));
        IslandManager.playersInIslands.put(player.getUniqueId(), UUID.fromString(getUUID()));
        player.setAllowFlight(true);
        Bukkit.getScheduler().runTaskLater(StaticPrisons.getInstance(), () -> player.setAllowFlight(true), 30);
    }
    public void playerJoined(Player player) {
        PlayerData playerData = new PlayerData(player);
        playerData.setIfPlayerHasIsland(true);
        playerData.setPlayerIslandUUID(getUUID());
        SkyBlockIsland island = playerData.getPlayerIsland();
        IslandInvites invites = SkyblockIslandInviteManager.getIslandInvites(player.getUniqueId().toString());
        for (String uuid : island.getIslandPlayerUUIDS()) {
            if (Bukkit.getPlayer(UUID.fromString(uuid)) == null) continue;
            Bukkit.getPlayer(UUID.fromString(uuid)).sendMessage(ChatColor.LIGHT_PURPLE + player.getName() + ChatColor.WHITE + " joined your cell! They were invited by: " + ChatColor.AQUA + ServerData.PLAYERS.getUUID(invites.invites.get(getUUID()).inviterUUID));
        }
        island.addIslandPlayerUUID(player.getUniqueId().toString());
        island.addIslandMemberUUID(player.getUniqueId().toString());
        island.teleportPlayerToMemberWarp(player);
        SkyblockIslandInviteManager.getIslandInvites(player.getUniqueId().toString()).invites.remove(getUUID());
        ProtectedRegion region = WorldGuard.getInstance().getPlatform().getRegionContainer().get(BukkitAdapter.adapt(Bukkit.getWorld("islands"))).getRegion(getUUID() + "-island");
        DefaultDomain members = region.getMembers();
        members.addPlayer(player.getUniqueId());
        region.setMembers(members);

    }
    public void playerLeft(Player player) {
        playerRemoved(player.getUniqueId());
        for (String _uuid : getIslandPlayerUUIDS()) {
            if (Bukkit.getPlayer(UUID.fromString(_uuid)) != null) Bukkit.getPlayer(UUID.fromString(_uuid)).sendMessage(ChatColor.LIGHT_PURPLE + player.getName() + ChatColor.WHITE + " has left your cell");
        }
    }
    public void playerKicked(Player kicker, UUID uuid) {
       playerRemoved(uuid);
        for (String _uuid : getIslandPlayerUUIDS()) {
            if (Bukkit.getPlayer(UUID.fromString(_uuid)) != null) Bukkit.getPlayer(UUID.fromString(_uuid)).sendMessage(ChatColor.LIGHT_PURPLE + ServerData.PLAYERS.getName(uuid) + ChatColor.WHITE + " has been kicked from your cell");
        }

    }
    public void playerBanned(Player banner, UUID uuid) {
        playerRemoved(uuid);
        addBannedPlayerUUID(uuid.toString());
        for (String _uuid : getIslandPlayerUUIDS()) {
            if (Bukkit.getPlayer(UUID.fromString(_uuid)) != null) Bukkit.getPlayer(UUID.fromString(_uuid)).sendMessage(ChatColor.LIGHT_PURPLE + ServerData.PLAYERS.getName(uuid) + ChatColor.WHITE + " has been banned from your cell");
        }
    }
    public void delete() {
        for (String playerUUID : new ArrayList<>(getIslandPlayerUUIDS())) {
            playerRemoved(UUID.fromString(playerUUID));
            if (Bukkit.getPlayer(UUID.fromString(playerUUID)) != null) {
                Bukkit.getPlayer(UUID.fromString(playerUUID)).sendMessage(ChatColor.RED + "Your cell was deleted.");
            }
        }
        //All players have been kicked from the island, delete the island from the DB
        Bukkit.getLogger().log(Level.INFO, "Deleted an island with the ID: " + getUUID() + " | Owner: " + getIslandOwnerUUID() + " (" + ServerData.PLAYERS.getName(UUID.fromString(getIslandOwnerUUID())) + ")");
        ServerData.ISLANDS.delete(UUID.fromString(getUUID()));
        WorldGuard.getInstance().getPlatform().getRegionContainer().get(BukkitAdapter.adapt(Bukkit.getWorld("islands"))).removeRegion(getUUID() + "-island");
    }
    void playerRemoved(UUID uuid) {
        PlayerData _playerData = new PlayerData(uuid);
        _playerData.setIfPlayerHasIsland(false);
        _playerData.setPlayerIslandUUID("");
        if (getIslandManagerUUID().equals(uuid.toString())) {
            setIslandManagerUUID("");
        }
        if (getIslandAdminUUIDS().contains(uuid.toString())) {
            removeIslandAdminUUID(uuid.toString());
        }
        if (getIslandMemberUUIDS().contains(uuid.toString())) {
            removeIslandMemberUUID(uuid.toString());
        }
        getIslandPlayerUUIDS().remove(uuid.toString());
        ProtectedRegion region = WorldGuard.getInstance().getPlatform().getRegionContainer().get(BukkitAdapter.adapt(Bukkit.getWorld("islands"))).getRegion(getUUID() + "-island");
        DefaultDomain members = region.getMembers();
        members.removePlayer(uuid);
        region.setMembers(members);
        if (Bukkit.getPlayer(uuid) == null) return;
        if (IslandManager.playersInIslands.containsKey(uuid)) {
            if (IslandManager.playersInIslands.get(uuid).equals(UUID.fromString(getUUID()))) {
                Warps.warpToSpawn(Bukkit.getPlayer(uuid));
            }
        }
    }
    public static SkyBlockIsland createNewIsland(Player islandOwner) {
        SkyBlockIsland island = createNewIsland(islandOwner.getUniqueId().toString());
        try (EditSession editSession = WorldEdit.getInstance().newEditSession(BukkitAdapter.adapt(Bukkit.getWorld("islands")))) {
            Operation operation = new ClipboardHolder(IslandManager.islandTemplate)
                    .createPaste(editSession)
                    .to(BlockVector3.at(island.getCenterPosOfIslandOnGrid()[0], 100, island.getCenterPosOfIslandOnGrid()[1]))
                    .build();
            Operations.complete(operation);
        }
        island.teleportPlayerToMemberWarp(islandOwner);
        Bukkit.getLogger().log(Level.INFO, "Created an island with the ID: " + island.getUUID() + " | Owner: " + islandOwner.getUniqueId() + " (" + islandOwner.getName() + ")");
        return island;
    }
    public static SkyBlockIsland createNewIsland(String islandOwnerUUID) {
        String uuid = UUID.randomUUID().toString();
        SkyBlockIsland island = new SkyBlockIsland(uuid);
        island.setGridNumber(IslandManager.createNewIslandOnGrid());
        int[] posOffset = island.getCenterPosOfIslandOnGrid();
        island.setIslandMemberWarpX(IslandData.DEFAULT_WARP_X + posOffset[0]);
        island.setIslandMemberWarpY(IslandData.DEFAULT_WARP_Y);
        island.setIslandMemberWarpZ(IslandData.DEFAULT_WARP_Z + posOffset[1]);
        island.setIslandVisitorWarpX(IslandData.DEFAULT_WARP_X + posOffset[0]);
        island.setIslandVisitorWarpY(IslandData.DEFAULT_WARP_Y);
        island.setIslandVisitorWarpZ(IslandData.DEFAULT_WARP_Z + posOffset[1]);
        island.setAllowInvites(true);
        island.setAllowVisitors(true);
        island.setIslandOwnerUUID(islandOwnerUUID);
        island.addIslandPlayerUUID(islandOwnerUUID);
        String originalName = ServerData.PLAYERS.getName(UUID.fromString(islandOwnerUUID)) + "'s_Island";
        String name;
        int i = 1;
        while(true) {
            if (ServerData.ISLANDS.getAllNames().contains(originalName)) {
                name = originalName + "(" + i + ")";
                i++;
                if (!ServerData.ISLANDS.getAllNames().contains(name)) {
                    break;
                }
            } else {
                name = originalName;
                break;
            }
        }
        island.setIslandName(name);
        ServerData.ISLANDS.putNameToUUID(name, UUID.fromString(uuid));
        ServerData.ISLANDS.putUUIDToName(UUID.fromString(uuid), name);
        PlayerData playerData = new PlayerData(islandOwnerUUID);
        playerData.setIfPlayerHasIsland(true);
        playerData.setPlayerIslandUUID(island.getUUID());
        ProtectedCuboidRegion region = new ProtectedCuboidRegion(uuid + "-island", BlockVector3.at(posOffset[0] - 250, 0, posOffset[1] - 250), BlockVector3.at(posOffset[0] + 250, 255, posOffset[1] + 250));
        DefaultDomain members = region.getMembers();
        members.addPlayer(UUID.fromString(islandOwnerUUID));
        region.setMembers(members);
        region.setPriority(1);
        WorldGuard.getInstance().getPlatform().getRegionContainer().get(BukkitAdapter.adapt(Bukkit.getWorld("islands"))).addRegion(region);
        if (Bukkit.getPlayer(UUID.fromString(islandOwnerUUID)) != null) Bukkit.getPlayer(UUID.fromString(islandOwnerUUID)).sendMessage(ChatColor.WHITE + "You have just created a cell! Type " + ChatColor.GREEN + "/island" + ChatColor.WHITE + " to manage it!");
        return island;
    }
}
