package me.staticstudios.prisons.islands;

import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import me.staticstudios.prisons.data.serverData.ServerData;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class IslandManager {
    public static Clipboard islandTemplate;
    public static Map<UUID, UUID> playersInIslands = new HashMap<>();
    public static final int DISTANCE_BETWEEN_GRID_POSITIONS = 1000;
    public static void initialize() {
        new WorldCreator("islands").createWorld();
        File file = new File("./data/island-template.schem");
        ClipboardFormat format = ClipboardFormats.findByFile(file);
        try (ClipboardReader reader = format.getReader(new FileInputStream(file))) {
            islandTemplate = reader.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void playerLeftIsland(Player player) {
        playersInIslands.remove(player.getUniqueId());
    }
    /**
     * @return the x and z coordinates of the center of the grid position
     */
    public static int createNewIslandOnGrid() {
        ServerData serverData = new ServerData();
        serverData.setAmountOfIslands(serverData.getAmountOfIslands() + 1);
        return serverData.getAmountOfIslands();
    }
    public static int[] getPosOfIslandOnGrid(int pos) {
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
