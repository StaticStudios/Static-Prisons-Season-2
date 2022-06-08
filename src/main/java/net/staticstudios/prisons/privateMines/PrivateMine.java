package net.staticstudios.prisons.privateMines;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.session.ClipboardHolder;
import net.staticstudios.mines.StaticMine;
import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.cells.CellManager;
import net.staticstudios.prisons.mines.MineBlock;
import net.staticstudios.prisons.misc.Warps;
import net.staticstudios.utils.WeightedElement;
import net.staticstudios.utils.WeightedElements;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class PrivateMine {

    public static int UNLOCK_AT_PLAYER_LEVEL;
    public static int START_SIZE;
    public static int MAX_SIZE;
    public static double DEFAULT_SELL_PERCENTAGE;
    public static LinkedHashMap<Integer, Integer> SIZE_BY_LEVEL;
    public static LinkedHashMap<Integer, WeightedElements<MineBlock>> BLOCKS_BY_LEVEL;
    public static LinkedHashMap<Integer, Clipboard> SCHEMATICS_BY_LEVEL;

    public static World PRIVATE_MINES_WORLD;

    public static Map<UUID, PrivateMine> PRIVATE_MINES = new HashMap<>();
    public static Map<UUID, PrivateMine> PLAYER_PRIVATE_MINES = new HashMap<>();
    public static Map<String, PrivateMine> MINE_ID_TO_PRIVATE_MINE = new HashMap<>();


    public static int getSize(int level) {
        if (SIZE_BY_LEVEL.containsKey(level)) return SIZE_BY_LEVEL.get(level);
        return SIZE_BY_LEVEL.get(SIZE_BY_LEVEL.size() - 1);
    }
    public static WeightedElements<MineBlock> getBlocks(int level) {
        if (BLOCKS_BY_LEVEL.containsKey(level)) return BLOCKS_BY_LEVEL.get(level);
        return BLOCKS_BY_LEVEL.get(BLOCKS_BY_LEVEL.size() - 1);
    }
    public static Clipboard getSchematic(int level) {
        if (SCHEMATICS_BY_LEVEL.containsKey(level)) return SCHEMATICS_BY_LEVEL.get(level);
        return SCHEMATICS_BY_LEVEL.get(SCHEMATICS_BY_LEVEL.size() - 1);
    }

    public static void init() {
        StaticPrisons.getInstance().getServer().getPluginManager().registerEvents(new PrivateMineBlockBreakListener(), StaticPrisons.getInstance());
        PRIVATE_MINES_WORLD = new WorldCreator("private_mines").createWorld();
        File file = new File(StaticPrisons.getInstance().getDataFolder(), "private_mines_config.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        UNLOCK_AT_PLAYER_LEVEL = config.getInt("unlock_at_player_level");
        START_SIZE = config.getInt("start_size");
        MAX_SIZE = config.getInt("max_size");
        DEFAULT_SELL_PERCENTAGE = config.getInt("default_sell_percentage") / 100d;
        int maxDefinedLevel = 0;
        SIZE_BY_LEVEL = new LinkedHashMap<>();
        SIZE_BY_LEVEL.put(0, START_SIZE);
        for (String key : config.getConfigurationSection("size_by_level").getKeys(false)) {
            int level = Integer.parseInt(key);
            SIZE_BY_LEVEL.put(level, config.getInt("size_by_level." + key));
            if (level > maxDefinedLevel) maxDefinedLevel = level;
        }
        int lastDefined = 0;
        for (int i = 0; i < maxDefinedLevel; i++) {
            if (!SIZE_BY_LEVEL.containsKey(i)) SIZE_BY_LEVEL.put(i, SIZE_BY_LEVEL.get(lastDefined));
            else lastDefined = i;
        }
        BLOCKS_BY_LEVEL = new LinkedHashMap<>();
        BLOCKS_BY_LEVEL.put(0, new WeightedElements<MineBlock>().add(new MineBlock(Material.STONE), 100));
        for (String key : config.getConfigurationSection("blocks_by_level").getKeys(false)) {
            int level = Integer.parseInt(key);
            WeightedElements<MineBlock> blocks = new WeightedElements<>();
            for (String blockKey : config.getConfigurationSection("blocks_by_level." + key).getKeys(false)) {
                double weight = config.getDouble("blocks_by_level." + key + "." + blockKey);
                Material material = Material.valueOf(blockKey);
                blocks.add(new MineBlock(material), weight);
            }
            BLOCKS_BY_LEVEL.put(level, blocks);
        }
        SCHEMATICS_BY_LEVEL = new LinkedHashMap<>();
        for (String key : config.getConfigurationSection("schematics_by_level").getKeys(false)) {
            int level = Integer.parseInt(key);
            String path = config.getString("schematics_by_level." + key);
            File schemFile = new File(StaticPrisons.getInstance().getDataFolder(), "private_mines/schematics/" + path);
            ClipboardFormat format = ClipboardFormats.findByFile(schemFile);
            try (ClipboardReader reader = format.getReader(new FileInputStream(schemFile))) {
                SCHEMATICS_BY_LEVEL.put(level, reader.read());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public UUID privateMineId;
    public int gridPosition;
    public UUID owner;
    public String name;
    public int level;
    public int size;
    public double visitorTax;
    public boolean isPublic;
    public double sellPercentage;
    //todo so far a schem is placed and the mine is created, that is all that is done

    public StaticMine mine = null;

    public static PrivateMine getPrivateMine(UUID privateMineId) {
        return PRIVATE_MINES.get(privateMineId);
    }

    public static PrivateMine getPrivateMineFromPlayer(UUID playerUUID) {
        return PLAYER_PRIVATE_MINES.get(playerUUID);
    }
    public static PrivateMine getPrivateMineFromPlayer(Player player) {
        return getPrivateMineFromPlayer(player.getUniqueId());
    }

    public static PrivateMine createPrivateMine(Player player) {
        PrivateMine privateMine = new PrivateMine();
        privateMine.privateMineId = UUID.randomUUID();
        privateMine.gridPosition = PrivateMineManager.createNewIslandOnGrid();
        privateMine.owner = player.getUniqueId();
        privateMine.name = player.getName();
        privateMine.level = 0;
        privateMine.size = START_SIZE;
        privateMine.visitorTax = 0.15;
        privateMine.isPublic = true;
        privateMine.sellPercentage = DEFAULT_SELL_PERCENTAGE;
        privateMine.updateBuild();
        PRIVATE_MINES.put(privateMine.privateMineId, privateMine);
        PLAYER_PRIVATE_MINES.put(privateMine.owner, privateMine);
        return privateMine;
    }

    public void registerMine() {
        int distanceFromCenter = getSize(level) / 2;
        int[] center = PrivateMineManager.getPosition(gridPosition);
        StaticMine mine = new StaticMine("private_mine-" + privateMineId, new Location(PRIVATE_MINES_WORLD, center[0] - distanceFromCenter, 1, center[1] - distanceFromCenter), new Location(PRIVATE_MINES_WORLD, center[0] + distanceFromCenter, 99, center[1] + distanceFromCenter));
        mine.setShouldSaveToFile(false);
        List<StaticMine.MineBlock> mineBlocks = new ArrayList<>();
        for (WeightedElement<MineBlock> block : getBlocks(level).getElements())
            mineBlocks.add(new StaticMine.MineBlock(BukkitAdapter.asBlockType(block.getElement().blockType), block.getWeight()));
        mine.setMineBlocks(mineBlocks.toArray(new StaticMine.MineBlock[0]));
        mine.refill();
        MINE_ID_TO_PRIVATE_MINE.put(mine.getID(), this);
    }


    public void warpTo(Player player) {
        int[] position = PrivateMineManager.getPosition(gridPosition);
        Warps.warpSomewhere(player, new Location(PRIVATE_MINES_WORLD, position[0] + 0.5, 100, position[1] + 0.5, -90, 0), true).thenRun(() -> StaticPrisons.worldBorderAPI.setBorder(player, 500d, new Location(PRIVATE_MINES_WORLD, position[0] + 0.5, 100, position[1] + 0.5)));
    }

    CompletableFuture<Void> updateBuild() {
        CompletableFuture<Void> future = new CompletableFuture<>();
        int[] position = PrivateMineManager.getPosition(gridPosition);
        Bukkit.getScheduler().runTaskAsynchronously(StaticPrisons.getInstance(), () -> {
            try (EditSession editSession = WorldEdit.getInstance().newEditSession(BukkitAdapter.adapt(PRIVATE_MINES_WORLD))) {
                Operation operation = new ClipboardHolder(getSchematic(level))
                        .createPaste(editSession)
                        .to(BlockVector3.at(position[0], 100, position[1]))
                        .build();
                Operations.complete(operation); //TODO I BELIEVE THAT THIS CALL IS MADE SYNC, DO IT ASYNC
            }
            future.complete(null);
        });
        return future;
    }

    void updateMineWorldguardRegion() {
        //todo if the mine is public allow others to mine here, otherwise only allow owner to mine
    }


}
