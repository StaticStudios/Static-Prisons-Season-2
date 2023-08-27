package net.staticstudios.prisons.data.datahandling;

import net.staticstudios.prisons.StaticPrisons;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;

public class DataSet {
    //Map containing all server data including player data and any other data that is being stored by this plugin
    private static Map<String, Data> ALL_DATA = new HashMap<>();
    private static final Data EMPTY_DATA = new Data("");

    /**
     * Save data async, do not use this if data needs to be instantly saved in the event of a server close
     */
    public static void saveData() {
        Map<String, Data> temp = new HashMap<>(ALL_DATA);
        Bukkit.getScheduler().runTaskAsynchronously(StaticPrisons.getInstance(), () -> {
            saveData(temp);
        });
    }

    /**
     * Immediately save all data
     */
    public static void saveDataSync() {
        saveData(ALL_DATA);
    }

    private static void saveData(Map<String, Data> dataMap) {
        FileConfiguration fileData = new YamlConfiguration();

        dataMap.forEach((key, data) -> {
            try {
                if (data.isDefaultValue()) {
                    return;
                }
                fileData.set(key, data.toConfigurationSection());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        try {
            fileData.save(new File(StaticPrisons.getInstance().getDataFolder(), "data/server_data.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        StaticPrisons.log("Finished saving all server data");
    }

    /**
     * Load data from file
     */
    public static void init() {
        ALL_DATA = new HashMap<>();
        FileConfiguration fileData = YamlConfiguration.loadConfiguration(new File(StaticPrisons.getInstance().getDataFolder(), "data/server_data.yml"));
        for (String key : fileData.getKeys(false)) {
            try {
                ALL_DATA.put(key, Data.fromConfigurationSection(key, Objects.requireNonNull(fileData.getConfigurationSection(key))));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Bukkit.getServer().getLogger().log(Level.INFO, "Finished loading all server data");
    }

    /**
     * The key that gets built when a new instance of this class is created
     * <br> Ex: "SERVER-players-" | The container key is then appended onto this key to create the actual key for the data in the map
     * <br> Ex: "SERVER-players-namesToUUIDs" or "PLAYERS-1234567890-money"
     */
    private final String key;

    public DataSet(DataTypes dataType, String container) {
        key = dataType.name() + "-" + container + "-";
    }

    /**
     * Put a new data object into the map using the key that was built in the constructor and the container key
     *
     * @param value        The data object to put into the map
     * @param containerKey The final part of the key that will be put into the map, EX: "playerNames", "money", "timePlayed", etc.
     */
    private void setData(String containerKey, Data value) {
        ALL_DATA.put(this.key + containerKey, value);
    }

    @Nullable
    private Data getData(String containerKey) {
        return ALL_DATA.get(this.key + containerKey);
    }


    /**
     * @return The data object from the map with the given key, if the object is not found, a new one will be created and added to the map.
     */
    @NotNull
    private Data getDataNotNull(String key) {
        Data data = getData(key);
        if (data == null) { //If the data is null, create a new one and add it to the map
            data = new Data(key);
            setData(key, data);
        }
        return data;
    }


    public void setString(String key, String value) {
        Data data = new Data(key);
        data.setString(value);
        setData(key, data);
    }

    @NotNull
    public String getString(String key) {
        return getDataNotNull(key).getString();
    }

    public void setInt(String key, int value) {
        Data data = new Data(key);
        data.setInt(value);
        setData(key, data);
    }

    public int getInt(String key) {
        return getDataNotNull(key).getInt();
    }

    public void setDouble(String key, double value) {
        Data data = new Data(key);
        data.setDouble(value);
        setData(key, data);
    }

    public double getDouble(String key) {
        return getDataNotNull(key).getDouble();
    }

    public void setLong(String key, long value) {
        Data data = new Data(key);
        data.setLong(value);
        setData(key, data);
    }

    public long getLong(String key) {
        return getDataNotNull(key).getLong();
    }

    public void setBoolean(String key, boolean value) {
        Data data = new Data(key);
        data.setBoolean(value);
        setData(key, data);
    }

    public boolean getBoolean(String key) {
        return getDataNotNull(key).getBoolean();
    }

    public void setStringList(String key, List<String> value) {
        Data data = new Data(key);
        data.setStringList(value);
        setData(key, data);
    }

    @NotNull
    public List<String> getStringList(String key) {
        return getDataNotNull(key).getStringList();
    }

    /**
     * This has not yet been tested and is not guaranteed to work.
     */

    @NotNull
    public Map<UUID, String> getUUIDStringMap(String key) {
        return getDataNotNull(key).getUuidStringMap();
    }

    public void setStringUUIDMap(String key, Map<String, UUID> value) {
        Data data = new Data(key);
        data.setStringUuidMap(value);
        setData(key, data);
    }

    @NotNull
    public Map<String, UUID> getStringUUIDMap(String key) {
        return getDataNotNull(key).getStringUuidMap();
    }

    public void setUUIDStringMap(String key, Map<UUID, String> value) {
        Data data = new Data(key);
        data.setUuidStringMap(value);
        setData(key, data);
    }

}
