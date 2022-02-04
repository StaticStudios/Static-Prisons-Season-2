package me.staticstudios.prisons.data.dataHandling;

import java.util.HashMap;
import java.util.Map;

public class DataSets {
    public static Map<DataTypes, DataSet> dataSets = new HashMap<>();

    public static DataSet getDataSet(DataTypes type, String containerKey) {
        if (dataSets.containsKey(type)) {
            return dataSets.get(type);
        } else dataSets.put(type, new DataSet(type, containerKey));
        return dataSets.get(type);
    }

    public static DataSet putDataSet(DataTypes type, DataSet newDataSet) {
        dataSets.put(type, newDataSet);
        return getDataSet(type, newDataSet.containerKey);
    }
}


