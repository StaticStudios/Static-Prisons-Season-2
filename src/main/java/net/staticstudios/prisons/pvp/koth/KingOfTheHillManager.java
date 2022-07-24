package net.staticstudios.prisons.pvp.koth;

import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.pvp.koth.commands.KingOfTheHillCommand;
import net.staticstudios.prisons.pvp.koth.runnables.KingOfTheHillGameRunnable;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class KingOfTheHillManager {

    static YamlConfiguration config;

    private static boolean eventRunning = false;

    private static Location kothBlock;

    private static final Map<Player, Integer> timeInKothAreaPerPlayer = new LinkedHashMap<>();
    private static KingOfTheHillGameRunnable eventRunnable;

    public static void init() {
        loadConfig();

        Objects.requireNonNull(StaticPrisons.getInstance().getCommand("kingofthehill")).setExecutor(new KingOfTheHillCommand());
    }

    private static void loadConfig() {
        config = YamlConfiguration.loadConfiguration(
                new File(StaticPrisons.getInstance().getDataFolder() + "/koth.yml"));

        if (config.contains("kothBlock")) {
            kothBlock = config.getLocation("kothBlock");
        }

        if (!config.contains("kothTime")) {
            //10 = default value
            config.set("kothTime", 10);
        }
    }


    private static void saveConfig() {
        config.set("kothBlock", kothBlock);

        try {
            config.save(StaticPrisons.getInstance().getDataFolder() + "/koth.yml");
        } catch (IOException e) {
            StaticPrisons.getInstance().getLogger().severe("Unable to save koth.yml");
        }

    }

    public static void setKothBlock(Location location) {
        kothBlock = location;
        saveConfig();
    }

    public static void removeKothBlock() {
        kothBlock = null;
        saveConfig();
    }

    public static boolean isKothArea(Location location, double radius) {
        return kothBlock.distanceSquared(location) <= (StrictMath.pow(radius, 2));
    }

    public static void startEvent() {
        eventRunning = true;
        eventRunnable = new KingOfTheHillGameRunnable(60 * config.getInt("kothTime"));
        Bukkit.getScheduler().runTaskAsynchronously(StaticPrisons.getInstance(), eventRunnable);
    }

    public static void stopEvent() {
        stopEvent(false);
    }

    public static boolean isEventRunning() {
        return eventRunning;
    }

    public static void incrementPlayerInKothArea(Player player) {
        timeInKothAreaPerPlayer.putIfAbsent(player, 0);
        timeInKothAreaPerPlayer.put(player, timeInKothAreaPerPlayer.get(player) + 1);
    }

    public static Map<Player, Integer> getTimeInKothAreaPerPlayer() {
        return Map.copyOf(timeInKothAreaPerPlayer);
    }

    public static void stopEvent(boolean withEvaluation) {
        eventRunning = false;
        if (withEvaluation) eventRunnable.evaluateWinner();
        timeInKothAreaPerPlayer.clear();
    }

    public static Location getKothBlock() {
        return kothBlock;
    }

    public static void onWin(Player player) {
        //todo
    }
}
