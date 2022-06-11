package net.staticstudios.prisons.privateMines;

import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.cells.Cell;
import net.staticstudios.prisons.data.dataHandling.serverData.ServerData;
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

public class PrivateMineManager {
    public static final int DISTANCE_BETWEEN_GRID_POSITIONS = 1000;
    //todo save and load to file
    public static void saveSync() {
        FileConfiguration fileData = new YamlConfiguration();
        for (Map.Entry<UUID, PrivateMine> entry : PrivateMine.PRIVATE_MINES.entrySet()) {
            ConfigurationSection section = fileData.createSection(entry.getKey().toString());
            section.set("gridPosition", entry.getValue().gridPosition);
            section.set("owner", entry.getValue().owner.toString());
            section.set("name", entry.getValue().name);
            section.set("xp", entry.getValue().getXp());
            section.set("level", entry.getValue().getLevel());
            section.set("size", entry.getValue().getSize());
            section.set("visitorTax", entry.getValue().visitorTax);
            section.set("isPublic", entry.getValue().isPublic);
            section.set("sellPercentage", entry.getValue().sellPercentage);
        }
        try {
            fileData.save(new File(StaticPrisons.getInstance().getDataFolder(), "private_mines/data.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void save() {
        Bukkit.getScheduler().runTaskAsynchronously(StaticPrisons.getInstance(), PrivateMineManager::saveSync);
    }
    public static void load() {
        FileConfiguration fileData = YamlConfiguration.loadConfiguration(new File(StaticPrisons.getInstance().getDataFolder(), "private_mines/data.yml"));
        for (String key : fileData.getKeys(false)) {
            ConfigurationSection section = fileData.getConfigurationSection(key);
            PrivateMine mine = new PrivateMine(
                    UUID.fromString(key),
                    section.getInt("gridPosition"),
                    UUID.fromString(section.getString("owner")),
                    section.getString("name"),
                    section.getLong("xp"),
                    section.getInt("size"),
                    section.getDouble("visitorTax"),
                    section.getBoolean("isPublic"),
                    section.getDouble("sellPercentage")
            );
        }
    }

    /**
     * @return the grid position of the new island which can be used to find the x and z location of the island with the getPosOfIslandOnGrid method
     *
     * Increments the amount of islands on the grid by 1
     */
    public static int createNewIslandOnGrid() {
        ServerData.PRIVATE_MINES.setAmountOfMinesOnGrid(ServerData.PRIVATE_MINES.getAmountOfMinesOnGrid() + 1);
        return ServerData.PRIVATE_MINES.getAmountOfMinesOnGrid();
    }
    public static int[] getPosition(int pos) {
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
