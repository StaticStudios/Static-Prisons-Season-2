package net.staticstudios.prisons.data.datahandling;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.util.*;


public class Data {

    public Data(String key) {
        this.key = key;
    }

    String valueType = "";

    public final String key;

    private List<String> stringList = new ArrayList<>(0);
    private Map<String, UUID> stringUuidMap = new HashMap<>(0);
    private Map<UUID, String> uuidStringMap = new HashMap<>(0);
    private boolean _boolean = false;
    private int _int = 0;
    private long _long = 0;
    private double _double = 0;
    private String _string = "";


    public List<String> getStringList() {
        if (valueType.isEmpty()) {
            valueType = "stringList";
        } else if (!valueType.equals("stringList")) {
            throw new IllegalStateException("Trying to get a string list from a " + valueType + " data object");
        }
        return stringList;
    }
    public Map<String, UUID> getStringUuidMap() {
        if (valueType.isEmpty()) {
            valueType = "stringUuidMap";
        } else if (!valueType.equals("stringUuidMap")) {
            throw new IllegalStateException("Trying to get a string uuid map from a " + valueType + " data object");
        }
        return stringUuidMap;
    }
    public Map<UUID, String> getUuidStringMap() {
        if (valueType.isEmpty()) {
            valueType = "uuidStringMap";
        } else if (!valueType.equals("uuidStringMap")) {
            throw new IllegalStateException("Trying to get a uuid string map from a " + valueType + " data object");
        }
        return uuidStringMap;
    }
    public boolean getBoolean() {
        if (valueType.isEmpty()) {
            valueType = "boolean";
        } else if (!valueType.equals("boolean")) {
            throw new IllegalStateException("Trying to get a boolean from a " + valueType + " data object");
        }
        return _boolean;
    }
    public int getInt() {
        if (valueType.isEmpty()) {
            valueType = "int";
        } else if (!valueType.equals("int")) {
            throw new IllegalStateException("Trying to get an int from a " + valueType + " data object");
        }
        return _int;
    }
    public long getLong() {
        if (valueType.isEmpty()) {
            valueType = "long";
        } else if (!valueType.equals("long")) {
            throw new IllegalStateException("Trying to get a long from a " + valueType + " data object");
        }
        return _long;
    }
    public double getDouble() {
        if (valueType.isEmpty()) {
            valueType = "double";
        } else if (!valueType.equals("double")) {
            throw new IllegalStateException("Trying to get a double from a " + valueType + " data object");
        }
        return _double;
    }
    public String getString() {
        if (valueType.isEmpty()) {
            valueType = "string";
        } else if (!valueType.equals("string")) {
            throw new IllegalStateException("Trying to get a string from a " + valueType + " data object");
        }
        return _string;
    }

    public void setStringList(List<String> stringList) {
        this.stringList = stringList;
        valueType = "stringList";
    }
    public void setStringUuidMap(Map<String, UUID> stringUuidMap) {
        this.stringUuidMap = stringUuidMap;
        valueType = "stringUuidMap";
    }
    public void setUuidStringMap(Map<UUID, String> uuidStringMap) {
        this.uuidStringMap = uuidStringMap;
        valueType = "uuidStringMap";
    }
    public void setBoolean(boolean _boolean) {
        this._boolean = _boolean;
        valueType = "boolean";
    }
    public void setInt(int _int) {
        this._int = _int;
        valueType = "int";
    }
    public void setLong(long _long) {
        this._long = _long;
        valueType = "long";
    }
    public void setDouble(double _double) {
        this._double = _double;
        valueType = "double";
    }
    public void setString(String _string) {
        this._string = _string;
        valueType = "string";
    }


    public ConfigurationSection toConfigurationSection() {
        ConfigurationSection section = new YamlConfiguration();
        switch (valueType) {
            case "stringList" -> section.set("value", stringList);
            case "stringUuidMap" -> {
                for (Map.Entry<String, UUID> entry : stringUuidMap.entrySet()) {
                    section.set("stringUuidMap." + entry.getKey(), entry.getValue().toString());
                }
            }
            case "uuidStringMap" -> {
                for (Map.Entry<UUID, String> entry : uuidStringMap.entrySet()) {
                    section.set("uuidStringMap." + entry.getKey().toString(), entry.getValue());
                }
            }
            case "boolean" -> section.set("value", _boolean);
            case "int" -> section.set("value", _int);
            case "long" -> section.set("value", _long);
            case "double" -> section.set("value", _double);
            case "string" -> section.set("value", _string);
        }
        section.set("valueType", valueType);
        return section;
    }
    public static Data fromConfigurationSection(String key, ConfigurationSection section) {
        Data data = new Data(key);
        String valueType = section.getString("valueType");
        if (valueType == null) {
            return data;
        }
        switch (valueType) {
            case "stringList" -> data.setStringList(section.getStringList("value"));
            case "stringUuidMap" -> {
                Map<String, UUID> map = new HashMap<>();
                for (String _key : section.getConfigurationSection("stringUuidMap").getKeys(false))
                    map.put(_key, UUID.fromString(section.getString("stringUuidMap." + _key)));
                data.setStringUuidMap(map);
            }
            case "uuidStringMap" -> {
                Map<UUID, String> map = new HashMap<>();
                for (String _key : section.getConfigurationSection("uuidStringMap").getKeys(false))
                    map.put(UUID.fromString(_key), section.getString("uuidStringMap." + _key));
                data.setUuidStringMap(map);
            }
            case "boolean" -> data.setBoolean(section.getBoolean("value"));
            case "int" -> data.setInt(section.getInt("value"));
            case "long" -> data.setLong(section.getLong("value"));
            case "double" -> data.setDouble(section.getDouble("value"));
            case "string" -> data.setString(section.getString("value"));
        }
        data.valueType = section.getString("valueType");
        return data;
    }
}
