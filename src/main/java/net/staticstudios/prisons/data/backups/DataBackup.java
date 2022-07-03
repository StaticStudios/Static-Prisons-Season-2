package net.staticstudios.prisons.data.backups;

import net.staticstudios.prisons.StaticPrisons;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataBackup {
    private static final List<File> filesToBackup = new ArrayList<>();

    public static void init() {
        filesToBackup.add(new File(StaticPrisons.getInstance().getDataFolder(), "auctionHouse"));
        filesToBackup.add(new File(StaticPrisons.getInstance().getDataFolder(), "data/pickaxeData.yml"));
        filesToBackup.add(new File(StaticPrisons.getInstance().getDataFolder(), "private_mines/data.yml"));
        filesToBackup.add(new File(StaticPrisons.getInstance().getDataFolder(), "auctionHouse.yml"));
        filesToBackup.add(new File(StaticPrisons.getInstance().getDataFolder(), "cells.yml"));
        filesToBackup.add(new File(StaticPrisons.getInstance().getDataFolder(), "data.yml"));
        Bukkit.getScheduler().runTaskTimerAsynchronously(StaticPrisons.getInstance(), DataBackup::saveShortTermBackUp, 20 * 60 * 30, 20 * 60 * 30); //30 minutes
    }


    private static void saveShortTermBackUp() {
        long day = System.currentTimeMillis() / 1000 / 60 / 60 / 24;

        //Check if the current day has a backup folder, if not, add one and call the shift method
        File shortTermBackups = new File(StaticPrisons.getInstance().getDataFolder(), "short-term-backups");
        if (!shortTermBackups.exists()) shortTermBackups.mkdirs();
        File dayFolder = new File(shortTermBackups, String.valueOf(day));
        if (!dayFolder.exists()) dayFolder.mkdirs();
        File thisBackup = new File(dayFolder, System.currentTimeMillis() + "");
        thisBackup.mkdirs();
        for (File file : filesToBackup) {
            try {
                if (file.isDirectory()) FileUtils.copyDirectory(file, new File(thisBackup, file.getName()));
                else FileUtils.copyFile(file, new File(thisBackup, file.getName()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            shiftShortTermBackups(shortTermBackups, new File(StaticPrisons.getInstance().getDataFolder(), "long-term-backups"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        StaticPrisons.log("[Data-Backup] Saved a short term backup");
    }

    private static void saveLongTermBackUp(File longTermBackupFolder, File directory) throws IOException {
        FileUtils.copyDirectory(directory, new File(longTermBackupFolder, directory.getName()));
        //todo zip
        StaticPrisons.log("[Data-Backup] Saved a long term backup");
    }
    private static void shiftShortTermBackups(File shortTermBackUpFolder, File longTermBackUpFolder) throws IOException {
        if (!shortTermBackUpFolder.exists()) shortTermBackUpFolder.mkdirs();
        String[] directoryNames = shortTermBackUpFolder.list();
        if (directoryNames == null) return;
        if (directoryNames.length < 8) return; //There are not enough backups to shift
        File oldestDirectory = new File(shortTermBackUpFolder, getYoungestEpoc(directoryNames));
        if (!longTermBackUpFolder.exists()) longTermBackUpFolder.mkdirs();
        String[] subDirectoryNames = oldestDirectory.list();
        if (subDirectoryNames != null && subDirectoryNames.length > 0) {
            String startOfTheDay = getYoungestEpoc(subDirectoryNames);
            File firstSaveOfTheDay = new File(oldestDirectory, startOfTheDay);
            saveLongTermBackUp(longTermBackUpFolder, firstSaveOfTheDay);
        }
        FileUtils.deleteDirectory(oldestDirectory);
        directoryNames = shortTermBackUpFolder.list();
        if (directoryNames == null) return;
        if (directoryNames.length >= 8) shiftShortTermBackups(shortTermBackUpFolder, longTermBackUpFolder); //If there are still more than 7 days of data saved, recursively call this method again
        StaticPrisons.log("[Data-Backup] Shifted short term backups");
    }


    private static String getYoungestEpoc(String[] epocs) {
        long youngestEpoc = Long.MAX_VALUE;
        for (String str : epocs) {
            long epoc = Long.parseLong(str);
            if (epoc < youngestEpoc) youngestEpoc = epoc;
        }
        return String.valueOf(youngestEpoc);
    }
}
