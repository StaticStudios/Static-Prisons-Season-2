package me.staticstudios.prisons.data.dataHandling;

import com.owlike.genson.GenericType;
import com.owlike.genson.GensonBuilder;
import me.staticstudios.prisons.utils.Utils;

import java.io.*;
import java.time.Instant;
import java.util.HashMap;

public class DataWriter {
    static void changeOldData() {
        File oldData = new File("./data.json");
        if (oldData.exists()) {
            oldData.renameTo(new File("./data/old/" + Instant.now().toEpochMilli() + ".json"));
        }
    }
    public static void saveData() {
        changeOldData();
        GensonBuilder gensonBuilder = new GensonBuilder();
        gensonBuilder.useClassMetadata(true);
        Utils.writeToAFile("data.json", gensonBuilder.create().serialize(DataSets.dataSets, new GenericType<HashMap<DataTypes, DataSet>>(){}));
    }

    /**
     * This will OVERWRITE any data that is currently loaded!
     */
    public static void loadData() {
        GensonBuilder gensonBuilder = new GensonBuilder();
        gensonBuilder.useClassMetadata(true);
        DataSets.dataSets = gensonBuilder.create().deserialize(Utils.getFileContents("data.json"), new GenericType<HashMap<DataTypes, DataSet>>(){});
    }
}
