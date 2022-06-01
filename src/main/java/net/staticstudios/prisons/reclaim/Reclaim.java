package net.staticstudios.prisons.reclaim;

import com.google.common.base.Charsets;
import net.staticstudios.prisons.StaticPrisons;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.util.UUID;

public class Reclaim {
    //TODO log all purchase data and add a way to read that and give the proper rewards back to a player upon recaiming
    public static final String SEASON_ID = "2.1";
    public static final String[] RECLAIMABLE_PACKAGES = {
            "staticp_rank" //TODO fill this out
    };

    public static void logPurchase(UUID playerUUID, String packageID, long epochMils) {
        File file = new File(StaticPrisons.getInstance().getDataFolder(), "purchases.yml");
        File backupFile = new File(StaticPrisons.getInstance().getDataFolder(), "purchases.yml.backup");
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        FileConfiguration dataFile = YamlConfiguration.loadConfiguration(file);
        dataFile.set(playerUUID.toString() + ".packageID", packageID);
        dataFile.set(playerUUID.toString() + ".timePurchased", epochMils);
        try {
            dataFile.save(file);
            //update the backupfile to have the same data as the main file


        } catch (IOException e) {
            throw new RuntimeException(e);
            //use the backup file to restore the data of the main file

        }
        //TODO: possibly switch to SQL?
    }
}
