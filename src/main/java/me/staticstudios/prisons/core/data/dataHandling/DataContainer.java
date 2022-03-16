package me.staticstudios.prisons.core.data.dataHandling;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class DataContainer implements Serializable {
    Map<String, Data> allData = new HashMap<>();
}
