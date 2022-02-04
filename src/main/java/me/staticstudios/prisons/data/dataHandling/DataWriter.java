package me.staticstudios.prisons.data.dataHandling;

import java.io.*;
import java.time.Instant;
import java.util.HashMap;

public class DataWriter {
    static void changeOldData() {
        File oldData = new File("./serverData.db");
        if (oldData.exists()) {
            oldData.renameTo(new File("./data/old/" + Instant.now().toEpochMilli() + ".db"));
        }
    }
    public static void saveData() {
        changeOldData();
        try
        {
            FileOutputStream fos = new FileOutputStream("serverData.db");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(DataSets.dataSets);
            oos.close();
            fos.close();
        }catch(IOException ioe)
        {
            ioe.printStackTrace();
        }
    }

    /**
     * This will OVERWRITE any data that is currently loaded!
     */
    public static void loadData() {
        try
        {
            FileInputStream fis = new FileInputStream("serverData.db");
            ObjectInputStream ois = new ObjectInputStream(fis);
            DataSets.dataSets = (HashMap) ois.readObject();
            ois.close();
            fis.close();
        }catch(IOException ioe)
        {
            ioe.printStackTrace();
            return;
        }catch(ClassNotFoundException c)
        {
            System.out.println("Class not found");
            c.printStackTrace();
            return;
        }
    }
}
