package net.staticstudios.prisons.pvp.koth;

import net.kyori.adventure.text.Component;
import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.pvp.koth.commands.KingOfTheHillCommand;
import net.staticstudios.prisons.pvp.koth.events.KingOfTheHillsEvents;
import net.staticstudios.prisons.pvp.koth.runnables.KingOfTheHillGameRunnable;
import net.staticstudios.prisons.utils.ComponentUtil;
import net.staticstudios.utils.Prefix;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static net.staticstudios.prisons.pvp.PvPManager.PVP_WORLD;

public class KingOfTheHillManager {

    static YamlConfiguration config;

    private static boolean eventRunning = false;

    static Set<Location> kothBlocks = new HashSet<>();
    static Map<Location, Set<UUID>> currentKoths = new HashMap<>();

    static Map<Player, Integer> playerInKothArea = new HashMap<>();

    public static void init() {
        System.out.println("init");
        loadConfig();

        Objects.requireNonNull(StaticPrisons.getInstance().getCommand("kingofthehill")).setExecutor(new KingOfTheHillCommand());

        //Bukkit.getPluginManager().registerEvents(new KingOfTheHillsEvents(), StaticPrisons.getInstance());
    }

    private static void loadConfig() {
        config = YamlConfiguration.loadConfiguration(
                new File(StaticPrisons.getInstance().getDataFolder() + "/koth.yml"));

        if (config.contains("kothBlocks")) {
            kothBlocks = config.getList("kothBlocks").stream().map(o -> (Location) o).collect(Collectors.toSet());
        }
    }

    public static void save() {
        try {
            saveConfig();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void saveConfig() throws IOException {
        config.set("kothBlocks", kothBlocks.toArray());

        config.save(StaticPrisons.getInstance().getDataFolder() + "/koth.yml");
    }

    public static Set<Location> getKothBlocks() {
        return Set.copyOf(kothBlocks);
    }

    public static void addKothBlock(Location location) {
        kothBlocks.add(location);

    }

    public static void removeKothBlock(Location location) {
        kothBlocks.stream().filter(kothBlock -> kothBlock.distanceSquared(location) <= StrictMath.pow(5, 2)).findFirst().ifPresent(kothBlocks::remove);
        StaticPrisons.getInstance().getLogger().info(kothBlocks.toString());
    }

    public static Optional<Location> getKothBlockNearLocation(Location location, double radius) {
        if (!location.getWorld().equals(PVP_WORLD)) {
            return Optional.empty();
        }

        if (kothBlocks.contains(location)) {
            return Optional.of(location);
        }


        return kothBlocks.stream()
                .filter(kothBlock -> kothBlock.distanceSquared(location) <= (StrictMath.pow(radius, 2)))
                .findFirst();
    }

    public static boolean isKothBlock(Location location, double radius) {
        return kothBlocks.stream()
                .anyMatch(kothBlock -> kothBlock.distanceSquared(location) <= (StrictMath.pow(radius, 2)));
    }

    public static boolean isKothBlock(Location location) {
        return isKothBlock(location, 0.0);
    }

    public static void startEvent() {
        eventRunning = true;
        Bukkit.getScheduler().runTaskAsynchronously(StaticPrisons.getInstance(), new KingOfTheHillGameRunnable(10));
    }

    public static void stopEvent() {
        eventRunning = false;
        currentKoths.clear();
        playerInKothArea.clear();

        Bukkit.broadcast(Prefix.PVP.append(Component.text("King of the Hill event has ended!")));
    }

    public static void removePlayerFromMaps(Player player) {
        playerInKothArea.remove(player);

        currentKoths.values().stream().filter(uuids -> uuids.contains(player.getUniqueId())).findFirst().ifPresent(uuids -> uuids.remove(player.getUniqueId()));
    }

    private final static int DURATION_IN_SECONDS = 10;

    public static void newKoth(Player player) {
        playerInKothArea.computeIfAbsent(player, k -> {
            Bukkit.getScheduler().runTaskAsynchronously(StaticPrisons.getInstance(), () -> {
                Optional<Location> optionalLocation = getKothBlockNearLocation(player.getLocation(), 5);
                int time = 0;

                if (optionalLocation.isEmpty()) {
                    return;
                }

                currentKoths.putIfAbsent(optionalLocation.get(), new HashSet<>());
                currentKoths.get(optionalLocation.get()).add(player.getUniqueId());

                while (isKothBlock(player.getLocation(), 5) && time < DURATION_IN_SECONDS && player.isOnline()) {
                    try {
                        //no clue if we should give the player feedback
                        if (currentKoths.containsKey(optionalLocation.get()) && (currentKoths.get(optionalLocation.get()).size() > 1)) {
                            player.sendMessage(Prefix.PVP.append(Component.text("Contested")));
                        } else {
                            player.sendMessage(Prefix.PVP.append(Component.text(DURATION_IN_SECONDS - time + " seconds left!")));
                            time++;
                        }

                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        player.sendMessage(Prefix.PVP.append(Component.text("something went wrong").color(ComponentUtil.RED)));
                    }
                }

                if (time >= DURATION_IN_SECONDS) {
                    player.sendMessage(Prefix.PVP.append(Component.text("You are the king of the hill!").color(ComponentUtil.GOLD)));
                    stopEvent();
                    return;
                }

                //todo give rewards
                currentKoths.get(optionalLocation.get()).remove(player.getUniqueId());
                playerInKothArea.remove(player);
            });
            return 0;
        });
    }

    public static boolean isEventRunning() {
        return eventRunning;
    }

}
