package me.staticstudios.prisons.data.dataHandling;

import com.owlike.genson.GenericType;
import com.owlike.genson.Genson;
import com.owlike.genson.GensonBuilder;
import me.staticstudios.prisons.Main;
import me.staticstudios.prisons.utils.Utils;
import org.bukkit.Bukkit;

import java.io.*;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class DataWriter {
    static void changeOldData() {
        File oldData = new File("./data.json");
        if (oldData.exists()) {
            oldData.renameTo(new File("./data/old/" + Instant.now().toEpochMilli() + ".json"));
        }
    }
    public static void saveData() {
        changeOldData();
        Map<DataTypes, DataSet> dataMap = new HashMap<>(DataSets.dataSets);
        Bukkit.getScheduler().runTaskAsynchronously(Main.getMain(), () -> {
            Utils.writeToAFile("data.json", new Genson().serialize(dataMap, new GenericType<HashMap<DataTypes, DataSet>>(){}));
            Bukkit.getLogger().log(Level.INFO, "Successfully saved all server data");
        });
    }
    public static void saveDataSync() {
        changeOldData();
        Utils.writeToAFile("data.json", new Genson().serialize(DataSets.dataSets, new GenericType<HashMap<DataTypes, DataSet>>(){}));
        Bukkit.getLogger().log(Level.INFO, "Successfully saved all server data");
    }
    /**
     * This will OVERWRITE any data that is currently loaded!
     */
    public static void loadData() {
        DataSets.dataSets = new Genson().deserialize(Utils.getFileContents("data.json"), new GenericType<HashMap<DataTypes, DataSet>>(){});
        if (DataSets.dataSets == null) {
            DataSets.dataSets = new HashMap<>();
            Bukkit.getLogger().warning("Could not load server data... creating new data instead");
        } else Bukkit.getLogger().log(Level.INFO, "Successfully loaded all server data");
    }
}
