package net.staticstudios.prisons.cells;

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
import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.data.serverData.ServerData;
import net.staticstudios.prisons.misc.Warps;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.UUID;

public class Cell { //TODO: currently cells are built on the main thread, consider doing this on a separate thread. If this is done, ensure that a single cell cannot get loaded/built multiple times at once (since it would be running in parallel). Use private mines as a reference
    public final UUID cellUuid;
    public final String cellName;
    public final UUID cellOwnerUUID;
    public final int gridPosition;

    public Cell(UUID cellUuid, String cellName, UUID cellOwnerUUID, int gridPosition) {
        this.cellUuid = cellUuid;
        this.cellName = cellName;
        this.cellOwnerUUID = cellOwnerUUID;
        this.gridPosition = gridPosition;
        CellManager.cells.put(cellUuid, this);
        CellManager.playersToCell.put(cellOwnerUUID, cellUuid);
    }

    public static Cell createCell(Player player) {
        int gridPosition = CellManager.createNewIslandOnGrid();
        Cell cell = new Cell(UUID.randomUUID(), ServerData.PLAYERS.getName(player.getUniqueId()) + "'s Cell", player.getUniqueId(), gridPosition);
        cell.buildCell();
        cell.registerWorldGuard();
        cell.warpTo(player);
        return cell;
    }

    public static Cell getCell(Player player) {
        return CellManager.cells.get(CellManager.playersToCell.get(player.getUniqueId()));
    }
    public static Cell getCell(UUID cellUuid) {
        return CellManager.cells.get(cellUuid);
    }

    public void warpTo(Player player) {
        int[] position = CellManager.getCellPosition(gridPosition);
        Warps.warpSomewhere(player, new Location(Bukkit.getWorld("islands"), position[0] + 0.5, 100, position[1] + 0.5, -90, 0), true).thenRun(() -> StaticPrisons.worldBorderAPI.setBorder(player, 150d, new Location(Bukkit.getWorld("islands"), position[0] + 0.5, 100, position[1] + 0.5)));
    }


    void buildCell() {
        int[] position = CellManager.getCellPosition(gridPosition);
        try (EditSession editSession = WorldEdit.getInstance().newEditSession(BukkitAdapter.adapt(Bukkit.getWorld("islands")))) {
            Operation operation = new ClipboardHolder(CellManager.islandTemplate)
                    .createPaste(editSession)
                    .to(BlockVector3.at(position[0], 100, position[1]))
                    .build();
            Operations.complete(operation);
        }
    }

    void registerWorldGuard() {
        int[] position = CellManager.getCellPosition(gridPosition);
        ProtectedCuboidRegion region = new ProtectedCuboidRegion(cellUuid + "-island", BlockVector3.at(position[0] - 250, 0, position[1] - 250), BlockVector3.at(position[0] + 250, 255, position[1] + 250));
        DefaultDomain members = region.getMembers();
        members.addPlayer(cellOwnerUUID);
        region.setMembers(members);
        region.setPriority(1);
        WorldGuard.getInstance().getPlatform().getRegionContainer().get(BukkitAdapter.adapt(Bukkit.getWorld("islands"))).addRegion(region);
    }
}
