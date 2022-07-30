package net.staticstudios.prisons.pvp.outposts;

import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import net.kyori.adventure.text.Component;
import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.gangs.Gang;
import net.staticstudios.prisons.pvp.outposts.commands.OutpostCommand;
import net.staticstudios.prisons.pvp.outposts.domain.MoneyOutpost;
import net.staticstudios.prisons.pvp.outposts.domain.Outpost;
import net.staticstudios.prisons.pvp.outposts.domain.TokenOutpost;
import net.staticstudios.prisons.utils.Prefix;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static net.staticstudios.prisons.pvp.PvPManager.WE_PVP_WORLD;

public class OutpostManager {

    private static YamlConfiguration config;

    private static RegionManager regionManager;

    private static final Map<String, Outpost> outposts = new HashMap<>();

    public static void init() {
        loadConfig();

        regionManager = WorldGuard.getInstance().getPlatform().getRegionContainer().get(WE_PVP_WORLD);

        Objects.requireNonNull(StaticPrisons.getInstance().getCommand("outpost")).setExecutor(new OutpostCommand());

        startTasks();
    }

    private static void startTasks() {
        outposts.values().forEach(outpost -> outpost.setTaskId(Bukkit.getScheduler().runTaskTimer(StaticPrisons.getInstance(), outpost, 20L, 20L).getTaskId()));
    }

    private static void loadConfig() {

        System.out.println("Loading Outpost Config...");
        System.out.println("Gang loaded: " + Gang.isLoaded());

        config = YamlConfiguration.loadConfiguration(
                new File(StaticPrisons.getInstance().getDataFolder() + "/outposts.yml"));

        if (!config.contains("outposts")) {
            config.createSection("outposts");
        }

        for (String outpost : Objects.requireNonNull(config.getConfigurationSection("outposts")).getKeys(false)) {
            ConfigurationSection section = Objects.requireNonNull(config.getConfigurationSection("outposts")).getConfigurationSection(outpost);

            if (section == null) continue;

            OutpostTypes type = OutpostTypes.valueOf(section.getString("type"));
            String gang = section.getString("currentGang", "");
            System.out.println(gang);
            String name = section.getString("name");
            String id = section.getString("id");
            String region = section.getString("regionName");
            int capturePercentage = section.getInt("capturePercentage");

            createOutpost(id, name, type, gang, region, capturePercentage);
        }
    }

    public static void save() {
        stopTasks();
        saveConfig();
    }

    public static void saveConfig() {
        config.set("outposts", null);

        ConfigurationSection root = config.createSection("outposts");

        //curreng gang doesnt get saved
        for (String outpostName : outposts.keySet()) {
            Outpost outpost = outposts.get(outpostName);
            ConfigurationSection outpostSection = root.createSection(outpostName);
            outpostSection.set("type", outpost.getType().toString());
            outpostSection.set("id", outpost.getId().toString());
            outpostSection.set("name", outpost.getName());
            outpostSection.set("capturePercentage", outpost.getCapturePercentage());

            if (outpost.getCurrentGang() != null) {
                outpostSection.set("currentGang", outpost.getCurrentGang().getUuid().toString());
            }
            outpostSection.set("regionName", outpost.getRegion().getId());
        }

        try {
            config.save(StaticPrisons.getInstance().getDataFolder() + "/outposts.yml");
        } catch (IOException e) {
            StaticPrisons.getInstance().getLogger().severe("Unable to save outposts.yml");
        }
    }

    public static Outpost getOutpost(String name) {
        return outposts.get(name);
    }

    public static List<String> getOutpostRegionNames() {
        return outposts.values().stream().map(outpost -> outpost.getRegion().getId()).toList();
    }

    public static Outpost getOutpostByRegion(String regionName) {
        return outposts.values().stream().filter(outpost1 -> outpost1.getRegion().getId().equals(regionName)).findFirst().orElse(null);
    }

    private static boolean createOutpost(String id, String name, OutpostTypes type, String gangString, String region, int capturePercentage) {
        ProtectedRegion regionObject = Objects.requireNonNull(WorldGuard.getInstance().getPlatform().getRegionContainer().get(WE_PVP_WORLD)).getRegion(region);

        if (regionObject == null) {
            return false;
        }

        Gang gang = null;

        if (!gangString.isEmpty()) {
            gang = Gang.getGang(UUID.fromString(gangString));
        }

        switch (type) {
            case TOKEN ->
                    outposts.putIfAbsent(name, new TokenOutpost(UUID.fromString(id), name, gang, regionObject, OutpostTypes.TOKEN, capturePercentage));
            case MONEY ->
                    outposts.putIfAbsent(name, new MoneyOutpost(UUID.fromString(id), name, gang, regionObject, OutpostTypes.MONEY, capturePercentage));
        }
        return true;
    }

    public static boolean createOutpost(String regionName, String name, String typeString) {
        if (!OutpostTypes.isValidType(typeString)) {
            return false;
        }

        OutpostTypes type = OutpostTypes.valueOf(typeString.toUpperCase());

        return createOutpost(UUID.randomUUID().toString(), name, type, "", regionName, 0);
    }

    public static Component getOutpostOverview() {
        Component overview = Prefix.OUTPOST.append(Component.text("Overview:\n"));

        if (outposts.isEmpty()) {
            overview = overview.append(Component.text("No outposts created yet.\n"));
        }

        for (Outpost outpost : outposts.values()) {
            overview = overview.append(Component.text("- " + outpost.getName() + "\n"));
        }

        return overview;
    }

    public static boolean regionExists(String name) {
        return Objects.requireNonNull(WorldGuard.getInstance().getPlatform().getRegionContainer().get(WE_PVP_WORLD)).getRegion(name) != null;
    }

    public static void deleteOutpost(String name) {
        System.out.println(outposts.keySet());
        System.out.println(name);
        if (outposts.containsKey(name)) {
            Bukkit.getScheduler().cancelTask(getOutpost(name).getTaskId());
            outposts.remove(name);
            saveConfig();
        }
    }

    public static void stopTasks() {
        outposts.values().forEach(outpost -> Bukkit.getScheduler().cancelTask(outpost.getTaskId()));
    }

    public static List<String> getOutpostNames() {
        return outposts.keySet().stream().toList();
    }

    public static RegionManager getRegionManager() {
        return regionManager;
    }
}
