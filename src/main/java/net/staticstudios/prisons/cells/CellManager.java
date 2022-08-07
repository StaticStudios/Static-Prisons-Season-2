package net.staticstudios.prisons.cells;

import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.data.serverdata.ServerData;
import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CellManager {

    public static void init() {
        CellBlockShop.init();
        new WorldCreator("islands").createWorld();

        File file = new File("./data/island-template.schem");
        ClipboardFormat format = ClipboardFormats.findByFile(file);
        try (ClipboardReader reader = format.getReader(new FileInputStream(file))) {
            islandTemplate = reader.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        load();
    }


    public static Clipboard islandTemplate; //The cell schematic
    public static final int DISTANCE_BETWEEN_GRID_POSITIONS = 1000;


    public static Map<UUID, Cell> cells = new HashMap<>();
    public static Map<UUID, UUID> playersToCell = new HashMap<>();
    public static void saveSync() {
        FileConfiguration fileData = new YamlConfiguration();
        for (Map.Entry<UUID, Cell> entry : cells.entrySet()) {
            ConfigurationSection section = fileData.createSection(entry.getKey().toString());
            section.set("owner", entry.getValue().cellOwnerUUID.toString());
            section.set("name", entry.getValue().cellName);
            section.set("gridPos", entry.getValue().gridPosition);
        }
        try {
            fileData.save(new File(StaticPrisons.getInstance().getDataFolder(), "cells.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void save() {
        Map<UUID, Cell> temp = new HashMap<>(cells);
        Bukkit.getScheduler().runTaskAsynchronously(StaticPrisons.getInstance(), () -> {
            FileConfiguration fileData = new YamlConfiguration();
            for (Map.Entry<UUID, Cell> entry : temp.entrySet()) {
                ConfigurationSection section = fileData.createSection(entry.getKey().toString());
                section.set("owner", entry.getValue().cellOwnerUUID.toString());
                section.set("name", entry.getValue().cellName);
                section.set("gridPos", entry.getValue().gridPosition);
            }
            try {
                fileData.save(new File(StaticPrisons.getInstance().getDataFolder(), "cells.yml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    public static void load() {
        FileConfiguration fileData = YamlConfiguration.loadConfiguration(new File(StaticPrisons.getInstance().getDataFolder(), "cells.yml"));
        for (String key : fileData.getKeys(false)) {
            UUID uuid = UUID.fromString(key);
            String name = fileData.getString(key + ".name");
            UUID owner = UUID.fromString(fileData.getString(key + ".owner"));
            int gridPos = fileData.getInt(key + ".gridPos");
            cells.put(uuid, new Cell(uuid, name, owner, gridPos));
            playersToCell.put(owner, uuid);
        }
    }

    /**
     * @return the grid position of the new island which can be used to find the x and z location of the island with the getPosOfIslandOnGrid method
     *
     * Increments the amount of islands on the grid by 1
     */
    public static int createNewIslandOnGrid() {
        ServerData.ISLANDS.setAmountOfIslandsOnGrid(ServerData.ISLANDS.getAmountOfIslandsOnGrid() + 1);
        return ServerData.ISLANDS.getAmountOfIslandsOnGrid();
    }
    public static int[] getCellPosition(int pos) {
        int[] positions = getGridPos(pos);
        positions[0] *= DISTANCE_BETWEEN_GRID_POSITIONS;
        positions[1] *= DISTANCE_BETWEEN_GRID_POSITIONS;
        return positions;
    }
    static int[] getGridPos(int pos) {
        int[] posXY = new int[]{0, 0};
        int step = (int) Math.sqrt(pos - 1) + 1;
        if (pos == 0) step = 0;
        if (step % 2 == 0) step++;
        int insideSqr = (int) Math.pow(Math.max(0, step - 2), 2);
        int outsideSqr = (int) Math.pow(step, 2);
        int sideLength = outsideSqr / step;
        int offset = pos - insideSqr; //How far to wrap
        if (offset > sideLength - 1 && offset < sideLength * 2) { //Check if the X is as positive as it can go
            posXY[0] = sideLength / 2;
        } else if (offset > sideLength * 3 - 3) { //Check if the X is as negative as it can go
            posXY[0] = sideLength / -2;
        } else {
            if (offset > sideLength) { //Y is negative
                posXY[0] = sideLength * 2 - offset + sideLength - 1;
            } else posXY[0] = offset; //Y is positive
            posXY[0] -= sideLength / 2 + 1;
        }
        if (offset < sideLength + 1) { //Check if the Y is as positive as it can go
            posXY[1] = sideLength / 2;
        } else if (offset > sideLength * 2 - 2 && offset < sideLength * 3 - 1) { //Check if the Y is as negative as it can go
            posXY[1] = sideLength / -2;
        } else {
            if (offset > sideLength * 3 - 2) { //X is negative
                posXY[1] = offset - sideLength * 3 + 3;
            } else posXY[1] = sideLength * 2 - offset; //X is positive
            posXY[1] -= sideLength / 2 + 1;
        }
        return posXY;
    }
}
