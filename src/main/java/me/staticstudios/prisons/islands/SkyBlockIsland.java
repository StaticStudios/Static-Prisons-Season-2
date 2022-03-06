package me.staticstudios.prisons.islands;


import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.session.ClipboardHolder;
import io.papermc.lib.PaperLib;
import me.staticstudios.prisons.data.serverData.IslandData;
import me.staticstudios.prisons.data.serverData.PlayerData;
import me.staticstudios.prisons.data.serverData.ServerData;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

public class SkyBlockIsland extends IslandData {
    public static final int MAX_PLAYERS_PER_ISLAND = 5;
    String uuid; //Use random uuids, not player ids as ownership can be transferred
    public SkyBlockIsland(String uuid) {
        super(uuid);
        this.uuid = uuid;
    }
    public void teleportPlayerToMemberWarp(Player player) {
        PaperLib.teleportAsync(player, new Location(Bukkit.getWorld("islands"), getIslandMemberWarpX(), getIslandMemberWarpY(), getIslandMemberWarpZ()));
        IslandManager.playersInIslands.put(player.getUniqueId(), UUID.fromString(getUUID()));
    }
    public void teleportPlayerToVisitorWarp(Player player) {
        PaperLib.teleportAsync(player, new Location(Bukkit.getWorld("islands"), getIslandVisitorWarpX(), getIslandVisitorWarpY(), getIslandVisitorWarpZ()));
        IslandManager.playersInIslands.put(player.getUniqueId(), UUID.fromString(getUUID()));
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
        String originalName = new ServerData().getPlayerNameFromUUID(islandOwnerUUID) + "'s_Island";
        String name;
        int i = 1;
        while(true) {
            if (new ServerData().getSkyblockIslandNamesToUUIDsMap().containsKey(originalName)) {
                name = originalName + "(" + i + ")";
                i++;
                if (!new ServerData().getSkyblockIslandNamesToUUIDsMap().containsKey(name)) {
                    break;
                }
            } else {
                name = originalName;
                break;
            }
        }
        island.setIslandName(name);
        new ServerData().putSkyblockIslandNameToUUID(name, uuid);
        new ServerData().putSkyblockIslandUUIDToName(uuid, name);
        PlayerData playerData = new PlayerData(islandOwnerUUID);
        playerData.setIfPlayerHasIsland(true);
        playerData.setPlayerIslandUUID(island.getUUID());
        return island;
    }
}
