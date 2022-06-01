package net.staticstudios.prisons.data.dataHandling;

import com.owlike.genson.GenericType;
import com.owlike.genson.Genson;
import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.Bukkit;

import java.io.File;
import java.math.BigInteger;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

public class DataSet {
    //All data is stored in this map
    private static Map<String, Data> ALL_DATA = new HashMap<>();

    //Saving/loading
    static void changeOldData() {
        File oldData = new File("./data.json");
        if (oldData.exists()) oldData.renameTo(new File("./data/old/" + Instant.now().toEpochMilli() + ".json"));
        //todo use a more storage efficient approach
    }
    /**
     * Save data async, do not use this if data needs to be instantly saved in the event of a server close
     */
    public static void saveData() {
        changeOldData();
        Map<String, Data> dataMap = new HashMap<>(ALL_DATA);
        Bukkit.getScheduler().runTaskAsynchronously(StaticPrisons.getInstance(), () -> {
            PrisonUtils.writeToAFile("data.json", new Genson().serialize(dataMap, new GenericType<HashMap<String, Data>>(){}));
            Bukkit.getLogger().log(Level.INFO, "Successfully saved all server data");
        });
    }

    /**
     * immediately save all data
     */
    public static void saveDataSync() {
        changeOldData();
        PrisonUtils.writeToAFile("data.json", new Genson().serialize(ALL_DATA, new GenericType<HashMap<String, Data>>(){}));
        Bukkit.getLogger().log(Level.INFO, "Successfully saved all server data");
    }
    public static void loadData() {
        ALL_DATA = new Genson().deserialize(PrisonUtils.getFileContents("data.json"), new GenericType<HashMap<String , Data>>(){});
        if (ALL_DATA == null) {
            ALL_DATA = new HashMap<>();
            Bukkit.getLogger().warning("Could not load server data... creating new data instead");
        } else Bukkit.getLogger().log(Level.INFO, "Successfully loaded all server data");
    }


    private final String key;
    public DataSet(DataTypes dataType, String container) { key = dataType.name() + "-" + container + "-"; }
    private void setData(String containerKey, Data value) { ALL_DATA.put(this.key + containerKey, value); }
    private Data getData(String containerKey) { return ALL_DATA.get(this.key + containerKey); }
    private boolean dataExists(String containerKey) { return ALL_DATA.containsKey(this.key + containerKey); }

    public void setString(String key, String value) {
        Data data = new Data();
        data._string = value;
        setData(key, data);
    }
    public String getString(String key) {
        if (!dataExists(key)) setData(key, new Data());
        return getData(key)._string;
    }
    public void setInt(String key, int value) {
        Data data = new Data();
        data._int = value;
        setData(key, data);
    }
    public int getInt(String key) {
        if (!dataExists(key)) setData(key, new Data());
        return getData(key)._int;
    }
    public void setDouble(String key, double value) {
        Data data = new Data();
        data._double = value;
        setData(key, data);
    }
    public double getDouble(String key) {
        if (!dataExists(key)) setData(key, new Data());
        return getData(key)._double;
    }
    public void setLong(String key, long value) {
        Data data = new Data();
        data._long = value;
        setData(key, data);
    }
    public long getLong(String key) {
        if (!dataExists(key)) setData(key, new Data());
        return getData(key)._long;
    }
    public void setBoolean(String key, boolean value) {
        Data data = new Data();
        data._boolean = value;
        setData(key, data);
    }
    public boolean getBoolean(String key) {
        if (!dataExists(key)) setData(key, new Data());
        return getData(key)._boolean;
    }
    public void setList(String key, List<?> value) {
        Data data = new Data();
        data.list = value;
        setData(key, data);
    }
    public List<?> getList(String key) {
        if (!dataExists(key)) setData(key, new Data());
        return getData(key).list;
    }
    public void setMap(String key, Map<?, ?> value) {
        Data data = new Data();
        data.map = value;
        setData(key, data);
    }
    public Map<?, ?> getMap(String key) {
        if (!dataExists(key)) setData(key, new Data());
        return getData(key).map;
    }
    public void setBigInt(String key, BigInteger value) {
        setString(key, value.toString());
    }
    public BigInteger getBigInt(String key) {
        if (!dataExists(key)) setBigInt(key, BigInteger.ZERO);
        return new BigInteger(getString(key));
    }
}
