package net.staticstudios.prisons.data.datahandling;

import net.staticstudios.prisons.StaticPrisons;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

public class DataSet {
    //Map containing all server data including player data and any other data that is being stored by this plugin
    private static Map<String, Data> ALL_DATA = new HashMap<>();

    /**
     * Archive old data in the event that something gets corrupted and a rollback is needed
     */
//    static void changeOldData() {
//        File oldData = new File(StaticPrisons.getInstance().getDataFolder(),"data.yml");
//        if (oldData.exists()) oldData.renameTo(new File(StaticPrisons.getInstance().getDataFolder(), "data/oldServer/" + Instant.now().toEpochMilli() + "-oldData.yml"));
//    }
    /**
     * Save data async, do not use this if data needs to be instantly saved in the event of a server close
     */
    public static void saveData() {
        Map<String, Data> temp = new HashMap<>(ALL_DATA);
        Bukkit.getScheduler().runTaskAsynchronously(StaticPrisons.getInstance(), () -> {
            FileConfiguration fileData = new YamlConfiguration();
            for (Map.Entry<String, Data> entry : temp.entrySet()) {
                try {
                    fileData.set(entry.getKey(), entry.getValue().toConfigurationSection());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            try {
                fileData.save(new File(StaticPrisons.getInstance().getDataFolder(), "data.yml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Bukkit.getLogger().log(Level.INFO, "Finished saving all server data");
        });
    }

    /**
     * Immediately save all data
     */
    public static void saveDataSync() {
        FileConfiguration fileData = new YamlConfiguration();
        for (Map.Entry<String, Data> entry : ALL_DATA.entrySet()) {
            try {
                fileData.set(entry.getKey(), entry.getValue().toConfigurationSection());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            fileData.save(new File(StaticPrisons.getInstance().getDataFolder(), "data.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bukkit.getLogger().log(Level.INFO, "Finished saving all server data");
    }

    /**
     * Load data from file
     */
    public static void init() {
        ALL_DATA = new HashMap<>();
        FileConfiguration fileData = YamlConfiguration.loadConfiguration(new File(StaticPrisons.getInstance().getDataFolder(), "data.yml"));
        for (String key : fileData.getKeys(false)) {
            try {
                ALL_DATA.put(key, Data.fromConfigurationSection(key, fileData.getConfigurationSection(key)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Bukkit.getLogger().log(Level.INFO, "Finished loading all server data");
    }

    /**
     * The key that gets built when a new instance of this class is created
     *<br> Ex: "SERVER-players-" | The container key is then appended onto this key to create the actual key for the data in the map
     *<br> Ex: "SERVER-players-namesToUUIDs" or "PLAYERS-1234567890-money"
     */
    private final String key;
    public DataSet(DataTypes dataType, String container) { key = dataType.name() + "-" + container + "-"; }

    /**
     * Put a new data object into the map using the key that was built in the constructor and the container key
     * @param value The data object to put into the map
     * @param containerKey The final part of the key that will be put into the map, EX: "playerNames", "money", "timePlayed", etc.
     */
    private void setData(String containerKey, Data value) { ALL_DATA.put(this.key + containerKey, value); }

    @Nullable
    private Data getData(String containerKey) { return ALL_DATA.get(this.key + containerKey); }


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



    //          vvv         The following methods should be used by any other class that extends this class to access data.          vvv


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
    @NotNull
    public int getInt(String key) {
        return getDataNotNull(key).getInt();
    }
    public void setDouble(String key, double value) {
        Data data = new Data(key);
        data.setDouble(value);
        setData(key, data);
    }
    @NotNull
    public double getDouble(String key) {
        return getDataNotNull(key).getDouble();
    }
    public void setLong(String key, long value) {
        Data data = new Data(key);
        data.setLong(value);
        setData(key, data);
    }
    @NotNull
    public long getLong(String key) {
        Data data = getDataNotNull(key);

        try {
            return data.getLong();
        } catch (IllegalStateException e) {
            if ("bigInt".equals(data.valueType)) {
                BigInteger bigInteger = data.getBigInt();
                return bigInteger.longValue();
            }
        }

        return 0L;
    }
    public void setBoolean(String key, boolean value) {
        Data data = new Data(key);
        data.setBoolean(value);
        setData(key, data);
    }
    @NotNull
    public boolean getBoolean(String key) {
        return getDataNotNull(key).getBoolean();
    }
    public void setGenericList(String key, List<? extends ConfigurationSerializable> value) {
        Data data = new Data(key);
        data.setGenericList(value);
        setData(key, data);
    }
    public void setStringList(String key, List<String> value) {
        Data data = new Data(key);
        data.setStringList(value);
        setData(key, data);
    }
    public void setUUIDList(String key, List<UUID> value) {
        Data data = new Data(key);
        data.setUuidList(value);
        setData(key, data);
    }
    @NotNull
    public List<? extends ConfigurationSerializable> getGenericList(String key) {
        return getDataNotNull(key).getGenericList();
    }
    @NotNull
    public List<String> getStringList(String key) {
        return getDataNotNull(key).getStringList();
    }
    @NotNull
    public List<UUID> getUUIDList(String key) {
        return getDataNotNull(key).getUuidList();
    }
    /**
     * This has not yet been tested and is not guaranteed to work.
     */
    @Deprecated
    public void setGenericMap(String key, Map<? extends ConfigurationSerializable, ? extends ConfigurationSerializable> value) {
        Data data = new Data(key);
        data.setGenericMap(value);
        setData(key, data);
    }
    /**
     * This has not yet been tested and is not guaranteed to work.
     */
    @Deprecated
    public void setStringObjectMap(String key, Map<String, ? extends ConfigurationSerializable> value) {
        Data data = new Data(key);
        data.setStringObjectMap(value);
        setData(key, data);
    }
    /**
     * This has not yet been tested and is not guaranteed to work.
     */
    @Deprecated
    public void setUUIDObjectMap(String key, Map<UUID, ? extends ConfigurationSerializable> value) {
        Data data = new Data(key);
        data.setUuidObjectMap(value);
        setData(key, data);
    }
    /**
     * This has not yet been tested and is not guaranteed to work.
     */
    @Deprecated
    @NotNull
    public Map<? extends ConfigurationSerializable, ? extends ConfigurationSerializable> getGenericMap(String key) {
        return getDataNotNull(key).getGenericMap();
    }
    /**
     * This has not yet been tested and is not guaranteed to work.
     */
    @Deprecated
    @NotNull
    public Map<String, ? extends ConfigurationSerializable> getStringObjectMap(String key) {
        return getDataNotNull(key).getStringObjectMap();
    }

    /**
     * This has not yet been tested and is not guaranteed to work.
     */
    @Deprecated
    @NotNull
    public Map<UUID, ? extends ConfigurationSerializable> getUUIDObjectMap(String key) {
        return getDataNotNull(key).getUuidObjectMap();
    }
    public void setUUIDStringMap(String key, Map<UUID, String> value) {
        Data data = new Data(key);
        data.setUuidStringMap(value);
        setData(key, data);
    }
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

//    public void setBigInt(String key, BigInteger value) {
//        Data data = new Data(key);
//        data.setBigInt(value);
//        setData(key, data);
//    }
//    @NotNull
//    public BigInteger getBigInt(String key) {
//        return getDataNotNull(key).getBigInt();
//    }
}
