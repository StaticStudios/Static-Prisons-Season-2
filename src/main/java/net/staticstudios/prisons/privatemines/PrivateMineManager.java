package net.staticstudios.prisons.privatemines;

import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.blockbreak.BlockBreak;
import net.staticstudios.prisons.data.serverdata.ServerData;
import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class PrivateMineManager {

    public static Map<UUID, Set<PrivateMine>> INVITED_MINES  = new HashMap<>();

    public static final int DISTANCE_BETWEEN_GRID_POSITIONS = 1000;

    public static void saveSync() {
        FileConfiguration fileData = new YamlConfiguration();
        for (Map.Entry<UUID, PrivateMine> entry : PrivateMine.PRIVATE_MINES.entrySet()) {
            ConfigurationSection section = fileData.createSection(entry.getKey().toString());
            section.set("gridPosition", entry.getValue().gridPosition);
            section.set("owner", entry.getValue().owner.toString());
            section.set("name", entry.getValue().name);
            section.set("xp", entry.getValue().getXp());
            section.set("level", entry.getValue().getLevel());
            section.set("size", entry.getValue().getMineSize());
            section.set("visitorTax", entry.getValue().visitorTax);
            section.set("isPublic", entry.getValue().isPublic);
            section.set("sellPercentage", entry.getValue().sellPercentage);
            section.set("lastUpgradePurchaseLevel", entry.getValue().getLastUpgradePurchaseLevel());
            List<String> members = new ArrayList<>();
            for (UUID member : entry.getValue().getWhitelist()) members.add(member.toString());
            section.set("whitelist", members);
        }
        try {
            fileData.save(new File(StaticPrisons.getInstance().getDataFolder(), "data/private_mines.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void save() {
        Map<UUID, PrivateMine> temp = new HashMap<>(PrivateMine.PRIVATE_MINES);
        Bukkit.getScheduler().runTaskAsynchronously(StaticPrisons.getInstance(), () -> {
            FileConfiguration fileData = new YamlConfiguration();
            for (Map.Entry<UUID, PrivateMine> entry : temp.entrySet()) {
                ConfigurationSection section = fileData.createSection(entry.getKey().toString());
                section.set("gridPosition", entry.getValue().gridPosition);
                section.set("owner", entry.getValue().owner.toString());
                section.set("name", entry.getValue().name);
                section.set("xp", entry.getValue().getXp());
                section.set("level", entry.getValue().getLevel());
                section.set("size", entry.getValue().getMineSize());
                section.set("visitorTax", entry.getValue().visitorTax);
                section.set("isPublic", entry.getValue().isPublic);
                section.set("sellPercentage", entry.getValue().sellPercentage);
                section.set("lastUpgradePurchaseLevel", entry.getValue().getLastUpgradePurchaseLevel());
                List<String> members = new ArrayList<>();
                for (UUID member : entry.getValue().getWhitelist()) members.add(member.toString());
                section.set("whitelist", members);
            }
            try {
                fileData.save(new File(StaticPrisons.getInstance().getDataFolder(), "data/private_mines.yml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    public static void init() {
        PrivateMine.PRIVATE_MINES_WORLD = new WorldCreator("private_mines").createWorld();
        PrivateMineConfigManager.init().thenRun(() -> {
            FileConfiguration fileData = YamlConfiguration.loadConfiguration(new File(StaticPrisons.getInstance().getDataFolder(), "data/private_mines.yml"));
            for (String key : fileData.getKeys(false)) {
                ConfigurationSection section = fileData.getConfigurationSection(key);
                PrivateMine mine = new PrivateMine(
                        UUID.fromString(key),
                        section.getInt("gridPosition"),
                        UUID.fromString(section.getString("owner")),
                        section.getString("name"),
                        section.getLong("xp"),
                        section.getDouble("visitorTax"),
                        section.getBoolean("isPublic"),
                        section.getDouble("sellPercentage"),
                        section.getInt("lastUpgradePurchaseLevel")
                );
                List<String> members = section.getStringList("whitelist");
                for (String member : members) {
                    UUID uuid = UUID.fromString(member);
                    mine.addToWhitelist(uuid);
                }
            }

            StaticPrisons.getInstance().getServer().getPluginManager().registerEvents(new PrivateMineListener(), StaticPrisons.getInstance());

            PrivateMine.finishedInitTasks = true;
        });
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
