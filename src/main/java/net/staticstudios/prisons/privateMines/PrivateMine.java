package net.staticstudios.prisons.privateMines;

import com.github.yannicklamprecht.worldborder.api.WorldBorderApi;
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
import com.sk89q.worldedit.math.Vector3;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import net.staticstudios.mines.StaticMine;
import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.data.serverData.ServerData;
import net.staticstudios.prisons.mines.MineBlock;
import net.staticstudios.prisons.misc.Warps;
import net.staticstudios.prisons.utils.PrisonUtils;
import net.staticstudios.utils.WeightedElement;
import net.staticstudios.utils.WeightedElements;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
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
    public static LinkedHashMap<Integer, Integer[]> WORLD_BOARDER_BY_LEVEL;

    public static World PRIVATE_MINES_WORLD;

    public static Map<UUID, PrivateMine> PRIVATE_MINES = new HashMap<>();
    public static TreeMap<Integer, List<PrivateMine>> PRIVATE_MINES_SORTED_BY_LEVEL = new TreeMap<>();
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
    public static Integer[] getWorldBorder(int level) {
        if (WORLD_BOARDER_BY_LEVEL.containsKey(level)) return WORLD_BOARDER_BY_LEVEL.get(level);
        return WORLD_BOARDER_BY_LEVEL.get(WORLD_BOARDER_BY_LEVEL.size() - 1);
    }

    public static void init() {
        StaticPrisons.getInstance().getServer().getPluginManager().registerEvents(new PrivateMineBlockBreakListener(), StaticPrisons.getInstance());
        PRIVATE_MINES_WORLD = new WorldCreator("private_mines").createWorld();

        //Remove all WorldGuard regions in the world from the previous time the server was loaded | Set the only region to the global region
        Map<String, ProtectedRegion> regions = WorldGuard.getInstance().getPlatform().getRegionContainer().get(BukkitAdapter.adapt(PRIVATE_MINES_WORLD)).getRegions();
        Map<String, ProtectedRegion> globalRegionMap = new HashMap<>();
        globalRegionMap.put("__global__", regions.get("__global__"));
        WorldGuard.getInstance().getPlatform().getRegionContainer().get(BukkitAdapter.adapt(PRIVATE_MINES_WORLD)).setRegions(globalRegionMap);

        //Load config data
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
        maxDefinedLevel = 0;
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
            if (level > maxDefinedLevel) maxDefinedLevel = level;
        }
        lastDefined = 0;
        for (int i = 0; i < maxDefinedLevel; i++) {
            if (!BLOCKS_BY_LEVEL.containsKey(i)) BLOCKS_BY_LEVEL.put(i, BLOCKS_BY_LEVEL.get(lastDefined));
            else lastDefined = i;
        }
        maxDefinedLevel = 0;
        WORLD_BOARDER_BY_LEVEL = new LinkedHashMap<>();
        for (String key : config.getConfigurationSection("border_by_level").getKeys(false)) {
            int level = Integer.parseInt(key);
            WORLD_BOARDER_BY_LEVEL.put(level, new Integer[]{
                    config.getInt("border_by_level." + key + ".offset.x"),
                    config.getInt("border_by_level." + key + ".offset.z"),
                    config.getInt("border_by_level." + key + ".size")
            });
            if (level > maxDefinedLevel) maxDefinedLevel = level;
        }
        lastDefined = 0;
        for (int i = 0; i < maxDefinedLevel; i++) {
            if (!WORLD_BOARDER_BY_LEVEL.containsKey(i)) WORLD_BOARDER_BY_LEVEL.put(i, WORLD_BOARDER_BY_LEVEL.get(lastDefined));
            else lastDefined = i;
        }
        maxDefinedLevel = 0;
        SCHEMATICS_BY_LEVEL = new LinkedHashMap<>();
        for (String key : config.getConfigurationSection("schematics_by_level").getKeys(false)) {
            int level = Integer.parseInt(key);
            String path = config.getString("schematics_by_level." + key);
            File schemFile = new File(StaticPrisons.getInstance().getDataFolder(), "private_mines/schematics/" + path);
            ClipboardFormat format = ClipboardFormats.findByFile(schemFile);
            try (ClipboardReader reader = format.getReader(new FileInputStream(schemFile))) {
                SCHEMATICS_BY_LEVEL.put(level, reader.read());
                if (level > maxDefinedLevel) maxDefinedLevel = level;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        lastDefined = 0;
        for (int i = 0; i < maxDefinedLevel; i++) {
            if (!SCHEMATICS_BY_LEVEL.containsKey(i)) SCHEMATICS_BY_LEVEL.put(i, SCHEMATICS_BY_LEVEL.get(lastDefined));
            else lastDefined = i;
        }
    }

    public UUID privateMineId;
    public int gridPosition;
    public UUID owner;
    public String name;
    private long xp = 0;
    public long getXp() {
        return xp;
    }
    public void setXp(long xp, boolean normalUpdate) {
        if (normalUpdate) setXp(xp);
        else {
            this.xp = xp;
            int l = 0;
            while (xp >= getLevelRequirement(l + 1)) {
                xp -= getLevelRequirement(l + 1);
                l += 1;
            }
            if (l > 0) l += 1;
            setLevel(l, false);
        }
    }
    public void setXp(long xp) {
        this.xp = xp;
        if (xp >= getNextLevelRequirement()) {
            setLevel(getLevel() + 1);
        }
    }
    private int level;
    public int getLevel() {
        return level;
    }
    public void setLevel(int level, boolean updateStats) {
        if (updateStats) setLevel(level);
        else this.level = level;
    }
    public void setLevel(int level) {
        int oldLevel = this.level;
        this.level = level;
        if (oldLevel != level) { //check if this level needs to have stats changed like schem, mine size, blocks, ect. if so do it
            if (!getSchematic(level).equals(getSchematic(oldLevel))) {
                updateBuild().thenRun(() -> {
                    updateMine().thenRun(() -> {
                        for (Player p : getAllPlayersInPrivateMine()) {
                            p.sendMessage(ChatColor.GREEN + "This private mine has leveled up!");
                            warpTo(p);
                        }
                    });
                });
            } else if (getSize(level) != getSize(oldLevel) || !getBlocks(level).equals(getBlocks(oldLevel))) updateMine().thenRun(() -> {
                    for (Player p : getAllPlayersInPrivateMine()) {
                        p.sendMessage(ChatColor.GREEN + "This private mine has leveled up!");
                        warpTo(p);
                    }
                });
            updateTreeMap();
        }
    }
    private void updateTreeMap() {
        if (PRIVATE_MINES_SORTED_BY_LEVEL.containsKey(level)) PRIVATE_MINES_SORTED_BY_LEVEL.get(level).remove(this);
        if (PRIVATE_MINES_SORTED_BY_LEVEL.containsKey(level)) if (PRIVATE_MINES_SORTED_BY_LEVEL.get(level).isEmpty()) PRIVATE_MINES_SORTED_BY_LEVEL.remove(level);
        if (!PRIVATE_MINES_SORTED_BY_LEVEL.containsKey(level)) PRIVATE_MINES_SORTED_BY_LEVEL.put(level, new ArrayList<>());
        PRIVATE_MINES_SORTED_BY_LEVEL.get(level).add(this);
    }
    private static final int BASE_XP_PER_LEVEL = 1000;
    private static final double LEVEL_RATE_OF_INCREASE = 1.9;
    public static long getLevelRequirement(int level) {
        if (level <= 0) return BASE_XP_PER_LEVEL;
        return (long) ((long) BASE_XP_PER_LEVEL * level + level * Math.pow(LEVEL_RATE_OF_INCREASE * level, LEVEL_RATE_OF_INCREASE));
    }
    public long getNextLevelRequirement() {
        return getLevelRequirement(getLevel() + 1);
    }

    public int getSize() {
        return getSize(getLevel());
    }
    //public int size;
    public double visitorTax;
    public boolean isPublic;
    public double sellPercentage;

    public StaticMine mine = null;

    public boolean isLoaded = false;
    public static final int REFILL_DELAY = 1000 * 30;
    private long lastRefilledAt;

    public static CompletableFuture<PrivateMine> getPrivateMine(UUID privateMineId) {
        PrivateMine privateMine = PRIVATE_MINES.get(privateMineId);
        if (privateMine == null) return CompletableFuture.completedFuture(null);
        if (privateMine.isLoaded) return CompletableFuture.completedFuture(privateMine);
        CompletableFuture<PrivateMine> future = new CompletableFuture<>();
        loadPrivateMine(privateMineId).thenAccept(future::complete);
        return future;
    }

    public static CompletableFuture<PrivateMine> getPrivateMineFromPlayer(UUID playerUUID) {
        PrivateMine privateMine = PLAYER_PRIVATE_MINES.get(playerUUID);
        if (privateMine == null) return CompletableFuture.completedFuture(null);
        if (privateMine.isLoaded) return CompletableFuture.completedFuture(privateMine);
        CompletableFuture<PrivateMine> future = new CompletableFuture<>();
        loadPrivateMine(privateMine.privateMineId).thenAccept(future::complete);
        return future;
    }
    public static CompletableFuture<PrivateMine> getPrivateMineFromPlayer(Player player) {
        return getPrivateMineFromPlayer(player.getUniqueId());
    }
    public static PrivateMine getPrivateMineWithoutLoading(UUID privateMineId) {
        return PRIVATE_MINES.get(privateMineId);
    }
    public static PrivateMine getPrivateMineFromPlayerWithoutLoading(Player player) {
        return getPrivateMineFromPlayerWithoutLoading(player.getUniqueId());
    }
    public static PrivateMine getPrivateMineFromPlayerWithoutLoading(UUID playerUUID) {
        return PLAYER_PRIVATE_MINES.get(playerUUID);
    }

    public static boolean playerHasPrivateMine(UUID playerUUID) {
        return PLAYER_PRIVATE_MINES.containsKey(playerUUID);
    }
    public static boolean playerHasPrivateMine(Player player) {
        return playerHasPrivateMine(player.getUniqueId());
    }
    public static boolean privateMineExists(UUID privateMineId) {
        return PRIVATE_MINES.containsKey(privateMineId);
    }

    public static CompletableFuture<PrivateMine> createPrivateMine(Player player) {
        CompletableFuture<PrivateMine> future = new CompletableFuture<>();
        PrivateMine privateMine = new PrivateMine();
        privateMine.privateMineId = UUID.randomUUID();
        privateMine.gridPosition = PrivateMineManager.createNewIslandOnGrid();
        privateMine.owner = player.getUniqueId();
        privateMine.name = player.getName() + "'s Private Mine";
        privateMine.level = 0;
        //privateMine.size = START_SIZE;
        privateMine.visitorTax = 0.05;
        privateMine.isPublic = true;
        privateMine.sellPercentage = DEFAULT_SELL_PERCENTAGE;
        PRIVATE_MINES.put(privateMine.privateMineId, privateMine);
        PLAYER_PRIVATE_MINES.put(privateMine.owner, privateMine);
        privateMine.updateTreeMap();
        privateMine.updateBuild().thenAccept(PrivateMine::registerMine).thenRun(() -> future.complete(privateMine));
        return future;
    }
    public PrivateMine(UUID privateMineId, int gridPosition, UUID owner, String name, long xp, int size, double visitorTax, boolean isPublic, double sellPercentage) {
        this.privateMineId = privateMineId;
        this.gridPosition = gridPosition;
        this.owner = owner;
        this.name = name;
        //this.size = size;
        setXp(xp, false);
        //setLevel(level, false); -- calculated from xp
        this.visitorTax = visitorTax;
        this.isPublic = isPublic;
        this.sellPercentage = sellPercentage;
        PRIVATE_MINES.put(privateMineId, this);
        PLAYER_PRIVATE_MINES.put(owner, this);
        updateTreeMap();
    }
    private PrivateMine() {}
    public static CompletableFuture<PrivateMine> loadPrivateMine(UUID privateMineId) {
        CompletableFuture<PrivateMine> future = new CompletableFuture<>();
        PrivateMine privateMine = PRIVATE_MINES.get(privateMineId);
        if (privateMine == null) return null;
        privateMine.updateBuild().thenRun(() -> privateMine.registerMine().thenRun(() -> future.complete(privateMine)));
        return future;
    }

    public static final int XP_PER_BLOCK_BROKEN = 1;
    public void blockBroken() {
        setXp(getXp() + XP_PER_BLOCK_BROKEN);
    }

    public CompletableFuture<StaticMine> updateMine() {
        CompletableFuture<StaticMine> future = new CompletableFuture<>();
        mine.delete();
        MINE_ID_TO_PRIVATE_MINE.remove(mine.getID());
        registerMine();
        future.complete(mine);
        return future;
    }
    public CompletableFuture<StaticMine> registerMine() {
        CompletableFuture<StaticMine> future = new CompletableFuture<>();
        int distanceFromCenter = getSize(level) / 2;
        int[] center = PrivateMineManager.getPosition(gridPosition);
        StaticMine mine = new StaticMine("private_mine-" + privateMineId, new Location(PRIVATE_MINES_WORLD, center[0] - distanceFromCenter, 1, center[1] - distanceFromCenter), new Location(PRIVATE_MINES_WORLD, center[0] + distanceFromCenter, 99, center[1] + distanceFromCenter));
        mine.setShouldSaveToFile(false);
        mine.shouldRefillOnTimer = false;
        List<StaticMine.MineBlock> mineBlocks = new ArrayList<>();
        for (WeightedElement<MineBlock> block : getBlocks(level).getElements())
            mineBlocks.add(new StaticMine.MineBlock(BukkitAdapter.asBlockType(block.getElement().blockType), block.getWeight()));
        mine.setMineBlocks(mineBlocks.toArray(new StaticMine.MineBlock[0]));
        this.mine = mine;
        MINE_ID_TO_PRIVATE_MINE.put(mine.getID(), this);
        isLoaded = true;
        mine.refill().thenAccept(m -> {
            lastRefilledAt = System.currentTimeMillis();
            future.complete(mine);
        });
        return future;
    }
    public void manualRefill(Player player) {
        if (mine == null) return;
        if (lastRefilledAt + REFILL_DELAY > System.currentTimeMillis()) {
            player.sendMessage(ChatColor.RED + "You must wait " + ((lastRefilledAt + REFILL_DELAY - System.currentTimeMillis()) / 1000) + " seconds before you can refill this mine");
            return;
        }
        lastRefilledAt = System.currentTimeMillis();
        mine.refill();
    }


    public void warpTo(Player player) {
        int[] position = PrivateMineManager.getPosition(gridPosition);
        Warps.warpSomewhere(player, new Location(PRIVATE_MINES_WORLD, position[0] + 0.5, 100, position[1] + 0.5, -90, 0), true).thenRun(() -> showWorldBoarder(player));
    }
    public List<Player> getAllPlayersInPrivateMine() {
        List<Player> players = new ArrayList<>();
        for (Player player : PRIVATE_MINES_WORLD.getPlayers()) if (isPlayerInMine(player)) players.add(player);
        return players;
    }
    public boolean isPlayerInMine(Player player) {
        int[] position = PrivateMineManager.getPosition(gridPosition);
        Vector3 min = Vector3.at(position[0] - 300, 0, position[1] - 300);
        Vector3 max = Vector3.at(position[0] + 300, 255, position[1] + 300);
        return Vector3.at(player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ()).containedWithin(min, max);
    }

    public CompletableFuture<PrivateMine> updateBuild() {
        CompletableFuture<PrivateMine> future = new CompletableFuture<>();
        int[] position = PrivateMineManager.getPosition(gridPosition);
        Bukkit.getScheduler().runTaskAsynchronously(StaticPrisons.getInstance(), () -> {
            try (EditSession editSession = WorldEdit.getInstance().newEditSession(BukkitAdapter.adapt(PRIVATE_MINES_WORLD))) {
                Operation operation = new ClipboardHolder(getSchematic(level))
                        .createPaste(editSession)
                        .to(BlockVector3.at(position[0], 100, position[1]))
                        .build();
                Operations.complete(operation);
            }
            Bukkit.getScheduler().runTask(StaticPrisons.getInstance(), () -> {
                for (Player p : getAllPlayersInPrivateMine()) warpTo(p);
                future.complete(this);
            });
        });
        return future;
    }

    public void sendInfo(Player player) {
        player.sendMessage(
                ChatColor.translateAlternateColorCodes('&',
                "&e&l" + name + ":" + "\n&a\n" +
                "&cOwner: &f" + ServerData.PLAYERS.getName(owner) + "\n" +
                "&cLevel: &f" + PrisonUtils.addCommasToNumber(getLevel()) + "\n" +
                "&cExperience: &f" + PrisonUtils.prettyNum(getXp()) + " / " + PrisonUtils.prettyNum(getNextLevelRequirement()) + "\n" +
                "&cSize: &f" + (getSize() + 1) + "x" + (getSize() + 1) + "\n" +
                "&cTax: &f" + new DecimalFormat("0").format(visitorTax * 100) + "%" + "\n" +
                "&cSell Percentage: &f" +  new DecimalFormat("0.0").format(sellPercentage * 100) + "%" + "\n" +
                "&a" + "\n" +
                "&c&lSpecial Attributes: &fnone"));
    }

    public void levelUp(int newLevel) {
        setLevel(newLevel);
        updateBuild().thenRun(() -> {
            mine.delete();
            MINE_ID_TO_PRIVATE_MINE.remove(mine.getID());
            registerMine();
        });
    }

    void updateMineWorldguardRegion() {
        //todo if the mine is public allow others to mine here, otherwise only allow owner to mine
    }
    void showWorldBoarder(Player player) {
        WorldBorderApi worldBorderApi = StaticPrisons.worldBorderAPI;
        int[] position = PrivateMineManager.getPosition(gridPosition);
        Integer[] borderData = getWorldBorder(level);
        position[0] += borderData[0];
        position[1] += borderData[1];
        worldBorderApi.setBorder(player, borderData[2], new Location(PRIVATE_MINES_WORLD, position[0] + 0.5, 100, position[1] + 0.5));
    }


}
