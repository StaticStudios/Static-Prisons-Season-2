package net.staticstudios.prisons.privateMines;

import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.mines.MineBlock;
import net.staticstudios.utils.WeightedElements;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

public class PrivateMineConfigManager {

    static Map<String, Clipboard> schematics = new HashMap<>();
    static PrivateMineStats[] STATS_PER_LEVEL;
    static PrivateMineStats DEFAULT = new PrivateMineStats();
    public static int UNLOCK_AT_PLAYER_LEVEL; //todo load this config info and make sure that this works
    public static double DEFAULT_SELL_PERCENTAGE; //todo
    public static double DEFAULT_TAX; //todo config ^

    public static PrivateMineStats getStats(int level) {
        if (STATS_PER_LEVEL.length <= level) return STATS_PER_LEVEL[STATS_PER_LEVEL.length - 1];
        return STATS_PER_LEVEL[level];
    }


    protected static CompletableFuture<Void> init() {
        CompletableFuture<Void> future = new CompletableFuture<>();
        long startTime = System.currentTimeMillis();
        StaticPrisons.log("[Private-Mines] Loading config data...");
        loadSchematics().thenRun(() -> {
            loadConfigOptions();
            StaticPrisons.log("[Private-Mines] Finished loading config data! Took a total of " + (System.currentTimeMillis() - startTime) + "ms");
            future.complete(null);
        });
        return future;
    }
    private static void loadConfigOptions() {
        FileConfiguration config = YamlConfiguration.loadConfiguration(new File(StaticPrisons.getInstance().getDataFolder(), "private_mines_config.yml"));
        ConfigurationSection stats = config.getConfigurationSection("stats");
        //Find the highest level
        int highestLevel = 0;
        for (String key : stats.getKeys(false)) {
            int l = Integer.parseInt(key);
            if (l > highestLevel) highestLevel = l;
        }
        STATS_PER_LEVEL = new PrivateMineStats[highestLevel + 1];

        //Load default stats
        ConfigurationSection d = config.getConfigurationSection("default_stats");
        DEFAULT.blocks = getMineBlocks(d.getConfigurationSection("blocks"));
        DEFAULT.schematic = schematics.get(d.getString("schem"));
        DEFAULT.size = d.getInt("size");
        DEFAULT.worldborderOffsetX = d.getInt("border.offset_x");
        DEFAULT.worldborderOffsetZ = d.getInt("border.offset_z");
        DEFAULT.worldborderSize = d.getInt("border.size");
        DEFAULT.upgradeCost = BigInteger.ZERO;

        int lastIndex = 0;
        STATS_PER_LEVEL[0] = DEFAULT;
        for (String key : stats.getKeys(false)) {
            int level = Integer.parseInt(key);
            ConfigurationSection s = stats.getConfigurationSection(key);
            PrivateMineStats mineStats = new PrivateMineStats();
            if (s.contains("blocks")) mineStats.blocks = getMineBlocks(s.getConfigurationSection("blocks"));
            else mineStats.blocks = STATS_PER_LEVEL[lastIndex].blocks;
            if (s.contains("schem")) mineStats.schematic = schematics.get(s.getString("schem"));
            else mineStats.schematic = STATS_PER_LEVEL[lastIndex].schematic;

            if (s.contains("size")) mineStats.size = s.getInt("size");
            else mineStats.size = STATS_PER_LEVEL[lastIndex].size;

            if (s.contains("border.offset_x")) mineStats.worldborderOffsetX = s.getInt("border.offset_x");
            else mineStats.worldborderOffsetX = STATS_PER_LEVEL[lastIndex].worldborderOffsetX;

            if (s.contains("border.offset_z")) mineStats.worldborderOffsetZ = s.getInt("border.offset_z");
            else mineStats.worldborderOffsetZ = STATS_PER_LEVEL[lastIndex].worldborderOffsetZ;

            if (s.contains("border.size")) mineStats.worldborderSize = s.getInt("border.size");
            else mineStats.worldborderSize = STATS_PER_LEVEL[lastIndex].worldborderSize;

            if (s.contains("cost")) mineStats.upgradeCost = new BigInteger(s.getString("cost"));
            else mineStats.upgradeCost = STATS_PER_LEVEL[lastIndex].upgradeCost;

            STATS_PER_LEVEL[level] = mineStats;
            lastIndex = level;
        }
        for (int i = 0; i < STATS_PER_LEVEL.length; i++) { //Fill empty slots in the list with the previous loaded level
            if (STATS_PER_LEVEL[i] != null) continue;
            STATS_PER_LEVEL[i] = STATS_PER_LEVEL[i - 1];
        }
    }
    private static CompletableFuture<Void> loadSchematics() {
        CompletableFuture<Void> future = new CompletableFuture<>();
        long startTime = System.currentTimeMillis();
        StaticPrisons.log("[Private-Mines] Loading schematics on an async thread...");
        AtomicInteger schematicsLoaded = new AtomicInteger();
        Map<String, Clipboard> _schematics = new HashMap<>();
        Bukkit.getScheduler().runTaskAsynchronously(StaticPrisons.getInstance(), () -> {
            File schematicsFolder = new File(StaticPrisons.getInstance().getDataFolder(), "private_mines/schematics/");
            for (File schemFile : schematicsFolder.listFiles()) {
                try (ClipboardReader reader = ClipboardFormats.findByFile(schemFile).getReader(new FileInputStream(schemFile))) {
                    _schematics.put(schemFile.getName(), reader.read());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                schematicsLoaded.getAndIncrement();
            }
            Bukkit.getScheduler().runTask(StaticPrisons.getInstance(), () -> { //Finish on the main threa
                schematics.putAll(_schematics);
                StaticPrisons.log("[Private-Mines] Finished loading " + schematicsLoaded.get() + " schematics! Took a total of " + (System.currentTimeMillis() - startTime) + "ms");
                future.complete(null);
            });
        });
        return future;
    }

    private static WeightedElements<MineBlock> getMineBlocks(ConfigurationSection blocks) {
        WeightedElements<MineBlock> mineBlocks = new WeightedElements<>();
        for (String k : blocks.getKeys(false)) {
            mineBlocks.add(new MineBlock(Material.valueOf(k)), blocks.getDouble(k));
        }
        return mineBlocks;
    }


}
