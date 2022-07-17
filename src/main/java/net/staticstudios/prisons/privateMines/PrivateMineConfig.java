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

public class PrivateMineConfig {

    static Map<String, Clipboard> schematics = new HashMap<>();
    static List<PrivateMineStats> STATS_PER_LEVEL = new ArrayList<>();
    static PrivateMineStats DEFAULT = new PrivateMineStats();

    public static PrivateMineStats getStats(int level) {
        if (STATS_PER_LEVEL.size() < level) return STATS_PER_LEVEL.get(STATS_PER_LEVEL.size() - 1);
        return STATS_PER_LEVEL.get(level);
    }


    protected static void init() {
        long startTime = System.currentTimeMillis();
        StaticPrisons.log("[Private-Mines] Loading config data...");
        loadSchematics().thenRun(PrivateMineConfig::loadStats);
        StaticPrisons.log("[Private-Mines] Finished loading config data! Took a total of " + (System.currentTimeMillis() - startTime) + "ms");
    }
    private static void loadStats() {
        FileConfiguration config = YamlConfiguration.loadConfiguration(new File(StaticPrisons.getInstance().getDataFolder(), "private_mines_config.yml"));

        //Load default stats
        ConfigurationSection d = config.getConfigurationSection("default_stats");
        DEFAULT.blocks = getMineBlocks(d.getConfigurationSection("blocks"));
        DEFAULT.schematic = schematics.get(d.getString("schem"));
        DEFAULT.size = d.getInt("size");
        DEFAULT.upgradeCost = BigInteger.ZERO;

        ConfigurationSection stats = config.getConfigurationSection("stats");
        STATS_PER_LEVEL.add(DEFAULT);
        for (String key : stats.getKeys(false)) {
            int level = Integer.parseInt(key);
            ConfigurationSection s = stats.getConfigurationSection(key);
            PrivateMineStats mineStats = new PrivateMineStats();
            mineStats.blocks = getMineBlocks(s.getConfigurationSection("blocks"));
            mineStats.schematic = schematics.get(s.getString("schem"));
            mineStats.size = s.getInt("size");
            mineStats.upgradeCost = BigInteger.valueOf(s.getLong("upgrade_cost"));
            STATS_PER_LEVEL.set(level, mineStats);
        }
        for (int i = 0; i < STATS_PER_LEVEL.size(); i++) { //Fill empty slots in the list with the previous loaded level
            if (STATS_PER_LEVEL.get(i) != null) continue;
            STATS_PER_LEVEL.set(i, STATS_PER_LEVEL.get(i - 1));
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
