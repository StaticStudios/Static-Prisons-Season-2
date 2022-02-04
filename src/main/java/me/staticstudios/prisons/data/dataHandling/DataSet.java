package me.staticstudios.prisons.data.dataHandling;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class DataSet implements Serializable {
    public Map<String, DataContainer> dataSet = new HashMap<>();
    DataTypes type;
    String containerKey;
    public DataSet(DataTypes type, String containerKey) {
        this.type = type;
        this.containerKey = containerKey;
    }
    public Data getData(String dataKey) {
        if (!getDataContainer().allData.containsKey(dataKey)) {
            setData(dataKey, new Data());
        }
        return getDataContainer().allData.get(dataKey);
    }
    public Data setData(String dataKey, Data newData) {
        getDataContainer().allData.put(dataKey, newData);
        return getData(dataKey);
    }
    public DataContainer getDataContainer() {
        if (!DataSets.getDataSet(type, containerKey).dataSet.containsKey(containerKey)) setDataContainer(new DataContainer());
        return DataSets.getDataSet(type, containerKey).dataSet.get(containerKey);
    }
    public DataContainer setDataContainer(DataContainer newDataContainer) {
        DataSets.getDataSet(type, containerKey).dataSet.put(containerKey, newDataContainer);
        return getDataContainer();
    }

}
