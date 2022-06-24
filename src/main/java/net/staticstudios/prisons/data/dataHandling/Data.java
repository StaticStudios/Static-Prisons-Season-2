package net.staticstudios.prisons.data.dataHandling;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.util.*;

//Data types can be added here, they will get serialized without a problem as long as that type is serializable.
public class Data {

    public Data(String key) {
        this.key = key;
    }

    String valueType = "";

    public final String key;

    private List<String> stringList = new ArrayList<>();
    private List<UUID> uuidList = new ArrayList<>();
    private List<? extends ConfigurationSerializable> list = new ArrayList<>();
    private Map<? extends ConfigurationSerializable, ? extends ConfigurationSerializable> map = new HashMap<>();
    private Map<String, ? extends ConfigurationSerializable> stringObjectMap = new HashMap<>();
    private Map<UUID, ? extends ConfigurationSerializable> uuidObjectMap = new HashMap<>();

    /**
     * Generic class of all generic lists and maps
     */
    private Class<?> genericClass1;

    /**
     * Generic class of the value of the genericMap
     */
    private Class<?> genericClass2;
    private Map<String, UUID> stringUuidMap = new HashMap<>();
    private Map<UUID, String> uuidStringMap = new HashMap<>();
    private boolean _boolean = false;
    private int _int = 0;
    private long _long = 0;
    private double _double = 0;
    private String _string = "";
    private BigInteger bigInt = BigInteger.ZERO;


    public List<String> getStringList() {
        if (valueType.isEmpty()) {
            valueType = "stringList";
        } else if (!valueType.equals("stringList")) throw new IllegalStateException("Trying to get a string list from a " + valueType + " data object");
        return stringList;
    }
    public List<UUID> getUuidList() {
        if (valueType.isEmpty()) {
            valueType = "uuidList";
        } else if (!valueType.equals("uuidList")) throw new IllegalStateException("Trying to get a uuid list from a " + valueType + " data object");
        return uuidList;
    }
    public List<? extends ConfigurationSerializable> getGenericList() {
        if (valueType.isEmpty()) {
            valueType = "genericList";
        } else if (!valueType.equals("genericList")) throw new IllegalStateException("Trying to get a generic list from a " + valueType + " data object");
        return list;
    }
    public Map<? extends ConfigurationSerializable, ? extends ConfigurationSerializable> getGenericMap() {
        if (valueType.isEmpty()) {
            valueType = "genericMap";
        } else if (!valueType.equals("genericMap")) throw new IllegalStateException("Trying to get a generic map from a " + valueType + " data object");
        return map;
    }
    public Map<String, ? extends ConfigurationSerializable> getStringObjectMap() {
        if (valueType.isEmpty()) {
            valueType = "stringObjectMap";
        } else if (!valueType.equals("stringObjectMap")) throw new IllegalStateException("Trying to get a string object map from a " + valueType + " data object");
        return stringObjectMap;
    }
    public Map<UUID, ? extends ConfigurationSerializable> getUuidObjectMap() {
        if (valueType.isEmpty()) {
            valueType = "uuidObjectMap";
        } else if (!valueType.equals("uuidObjectMap")) throw new IllegalStateException("Trying to get a uuid object map from a " + valueType + " data object");
        return uuidObjectMap;
    }
    public Map<String, UUID> getStringUuidMap() {
        if (valueType.isEmpty()) {
            valueType = "stringUuidMap";
        } else if (!valueType.equals("stringUuidMap")) throw new IllegalStateException("Trying to get a string uuid map from a " + valueType + " data object");
        return stringUuidMap;
    }
    public Map<UUID, String> getUuidStringMap() {
        if (valueType.isEmpty()) {
            valueType = "uuidStringMap";
        } else if (!valueType.equals("uuidStringMap")) throw new IllegalStateException("Trying to get a uuid string map from a " + valueType + " data object");
        return uuidStringMap;
    }
    public boolean getBoolean() {
        if (valueType.isEmpty()) {
            valueType = "boolean";
        } else if (!valueType.equals("boolean")) throw new IllegalStateException("Trying to get a boolean from a " + valueType + " data object");
        return _boolean;
    }
    public int getInt() {
        if (valueType.isEmpty()) {
            valueType = "int";
        } else if (!valueType.equals("int")) throw new IllegalStateException("Trying to get an int from a " + valueType + " data object");
        return _int;
    }
    public long getLong() {
        if (valueType.isEmpty()) {
            valueType = "long";
        } else if (!valueType.equals("long")) throw new IllegalStateException("Trying to get a long from a " + valueType + " data object");
        return _long;
    }
    public double getDouble() {
        if (valueType.isEmpty()) {
            valueType = "double";
        } else if (!valueType.equals("double")) throw new IllegalStateException("Trying to get a double from a " + valueType + " data object");
        return _double;
    }
    public String getString() {
        if (valueType.isEmpty()) {
            valueType = "string";
        } else if (!valueType.equals("string")) throw new IllegalStateException("Trying to get a string from a " + valueType + " data object");
        return _string;
    }
    public BigInteger getBigInt() {
        if (valueType.isEmpty()) {
            valueType = "bigInt";
        } else if (!valueType.equals("bigInt")) throw new IllegalStateException("Trying to get a big int from a " + valueType + " data object");
        return bigInt;
    }

    public void setStringList(List<String> stringList) {
        this.stringList = stringList;
        valueType = "stringList";
    }
    public void setUuidList(List<UUID> uuidList) {
        this.uuidList = uuidList;
        valueType = "uuidList";
    }
    public void setGenericList(List<? extends ConfigurationSerializable> list) {
        if (list.isEmpty()) return;
        this.genericClass1 = list.get(0).getClass();
        this.list = list;
        valueType = "genericList";
    }
    public void setGenericMap(Map<? extends ConfigurationSerializable, ? extends ConfigurationSerializable> map) {
        if (map.isEmpty()) return;
        this.genericClass1 = map.keySet().iterator().next().getClass();
        this.genericClass2 = map.values().iterator().next().getClass();
        this.map = map;
        valueType = "genericMap";
    }
    public void setStringObjectMap(Map<String, ? extends ConfigurationSerializable> stringObjectMap) {
        if (stringObjectMap.isEmpty()) return;
        this.genericClass1 = stringObjectMap.values().iterator().next().getClass();
        this.stringObjectMap = stringObjectMap;
        valueType = "stringObjectMap";
    }
    public void setUuidObjectMap(Map<UUID, ? extends ConfigurationSerializable> uuidObjectMap) {
        if (uuidObjectMap.isEmpty()) return;
        this.genericClass1 = uuidObjectMap.values().iterator().next().getClass();
        this.uuidObjectMap = uuidObjectMap;
        valueType = "uuidObjectMap";
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
    public void setBigInt(BigInteger bigInt) {
        this.bigInt = bigInt;
        valueType = "bigInt";
    }


    public ConfigurationSection toConfigurationSection() {
        ConfigurationSection section = new YamlConfiguration();
        switch (valueType) {
            case "stringList" -> section.set("value", stringList);
            case "uuidList" -> {
                List<String> uuidList__String = new ArrayList<>();
                for (UUID uuid : uuidList) uuidList__String.add(uuid.toString());
                section.set("uuidList", uuidList__String);
            }
            case "genericList" -> {
                List<Map<String, Object>> list__ConfigurationSerializable = new ArrayList<>();
                for (ConfigurationSerializable obj : list) list__ConfigurationSerializable.add(obj.serialize());
                section.set("genericList", list__ConfigurationSerializable);
            }
            case "genericMap" -> { //todo: test this, if this doesn't work, try using base64 encoding
                List<Map<String, Object>> map__ConfigurationSerializable = new ArrayList<>();
                for (Map.Entry<? extends ConfigurationSerializable, ? extends ConfigurationSerializable> entry : map.entrySet()) {
                    Map<String, Object> entry__ConfigurationSerializable = new HashMap<>();
                    entry__ConfigurationSerializable.put("key", entry.getKey().serialize());
                    entry__ConfigurationSerializable.put("value", entry.getValue().serialize());
                    map__ConfigurationSerializable.add(entry__ConfigurationSerializable);
                }
                section.set("genericMap", map__ConfigurationSerializable);
                section.set("genericClass1", genericClass1.getName());
                section.set("genericClass2", genericClass2.getName());
            }
            case "stringObjectMap" -> {
                for (Map.Entry<String, ? extends ConfigurationSerializable> entry : stringObjectMap.entrySet()) section.set("stringObjectMap." + key, entry.getValue().serialize());
                section.set("genericClass1", genericClass1.getName());
            }
            case "uuidObjectMap" -> {
                for (Map.Entry<UUID, ? extends ConfigurationSerializable> entry : uuidObjectMap.entrySet()) section.set("uuidObjectMap." + key, entry.getValue().serialize());
                section.set("genericClass1", genericClass1.getName());
            }
            case "stringUuidMap" -> {
                for (Map.Entry<String, UUID> entry : stringUuidMap.entrySet()) section.set("stringUuidMap." + entry.getKey(), entry.getValue().toString());
            }
            case "uuidStringMap" -> {
                for (Map.Entry<UUID, String> entry : uuidStringMap.entrySet()) section.set("uuidStringMap." + entry.getKey().toString(), entry.getValue());
            }
            case "boolean" -> section.set("value", _boolean);
            case "int" -> section.set("value", _int);
            case "long" -> section.set("value", _long);
            case "double" -> section.set("value", _double);
            case "string" -> section.set("value", _string);
            case "bigInt" -> section.set("value", bigInt.toString());
        }
        section.set("valueType", valueType);
        return section;
    }
    public static Data fromConfigurationSection(String key, ConfigurationSection section) {
        Data data = new Data(key);

        Method deserializeMethod1 = null;
        Method deserializeMethod2 = null;
        if (section.getString("genericClass1") != null) {
            try {
                Class<?> genericClass = Class.forName(section.getString("genericClass1"));
                deserializeMethod1 = genericClass.getMethod("deserialize", Map.class);
            } catch (ClassNotFoundException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        if (section.getString("genericClass2") != null) {
            try {
                Class<?> genericClass = Class.forName(section.getString("genericClass2"));
                deserializeMethod2 = genericClass.getMethod("deserialize", Map.class);
            } catch (ClassNotFoundException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        try {
            switch (section.getString("valueType")) {
                case "stringList" -> data.setStringList(section.getStringList("value"));
                case "uuidList" -> {
                    List<String> uuidList__String = section.getStringList("uuidList");
                    List<UUID> uuidList = new ArrayList<>();
                    for (String uuid : uuidList__String) uuidList.add(UUID.fromString(uuid));
                    data.setUuidList(uuidList);
                }
                case "genericList" -> {
                    List<Map<?, ?>> list__ConfigurationSerializable = section.getMapList("genericList");
                    List<ConfigurationSerializable> list = new ArrayList<>();
                    for (Map<?, ?> map : list__ConfigurationSerializable) {
                        ConfigurationSerializable obj = (ConfigurationSerializable) deserializeMethod1.invoke(null, map);
                        list.add(obj);
                    }
                    data.setGenericList(list);
                }
                case "genericMap" -> {
                    List<Map<?, ?>> map__ConfigurationSerializable = section.getMapList("genericMap");
                    Map<ConfigurationSerializable, ConfigurationSerializable> map = new HashMap<>();
                    for (Map<?, ?> map__ : map__ConfigurationSerializable) {
                        for (Map.Entry<?, ?> entry : map__.entrySet()) {
                            ConfigurationSerializable _key = (ConfigurationSerializable) deserializeMethod1.invoke(null, entry.getKey());
                            ConfigurationSerializable value = (ConfigurationSerializable) deserializeMethod2.invoke(null, entry.getValue());
                            map.put(_key, value);
                        }
                    }
                    data.setGenericMap(map);
                }
                case "stringObjectMap" -> {
                    Map<String, ConfigurationSerializable> map = new HashMap<>();
                    for (String key_ : section.getConfigurationSection("stringObjectMap").getKeys(false)) {
                        ConfigurationSerializable obj = (ConfigurationSerializable) deserializeMethod1.invoke(null, section.getConfigurationSection("stringObjectMap." + key_).getValues(false));
                        map.put(key_, obj);
                    }
                    data.setStringObjectMap(map);
                }
                case "uuidObjectMap" -> {
                    Map<UUID, ConfigurationSerializable> map = new HashMap<>();
                    for (String key_ : section.getConfigurationSection("uuidObjectMap").getKeys(false)) {
                        ConfigurationSerializable obj = (ConfigurationSerializable) deserializeMethod1.invoke(null, section.getConfigurationSection("uuidObjectMap." + key_).getValues(false));
                        map.put(UUID.fromString(key_), obj);
                    }
                    data.setUuidObjectMap(map);
                }
                case "stringUuidMap" -> {
                    Map<String, UUID> map = new HashMap<>();
                    for (String _key : section.getConfigurationSection("stringUuidMap").getKeys(false)) map.put(_key, UUID.fromString(section.getString("stringUuidMap." + _key)));
                    data.setStringUuidMap(map);
                }
                case "uuidStringMap" -> {
                    Map<UUID, String> map = new HashMap<>();
                    for (String _key : section.getConfigurationSection("uuidStringMap").getKeys(false)) map.put(UUID.fromString(_key), section.getString("uuidStringMap." + _key));
                    data.setUuidStringMap(map);
                }
                case "boolean" -> data.setBoolean(section.getBoolean("value"));
                case "int" -> data.setInt(section.getInt("value"));
                case "long" -> data.setLong(section.getLong("value"));
                case "double" -> data.setDouble(section.getDouble("value"));
                case "string" -> data.setString(section.getString("value"));
                case "bigInt" -> data.setBigInt(new BigInteger(section.getString("value")));
            }
            data.valueType = section.getString("valueType");
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
        return data;
    }
}
