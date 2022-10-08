package net.staticstudios.prisons.privatemines;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.math.Vector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldedit.world.block.BlockType;
import com.sk89q.worldedit.world.block.BlockTypes;
import net.staticstudios.mines.StaticMine;
import net.staticstudios.mines.builder.StaticMineBuilder;
import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.blockbreak.BlockBreak;
import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.data.serverdata.ServerData;
import net.staticstudios.prisons.mines.MineBlock;
import net.staticstudios.prisons.utils.PrisonUtils;
import net.staticstudios.prisons.utils.Warps;
import net.staticstudios.mines.utils.WeightedElement;
import net.staticstudios.mines.utils.WeightedElements;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class PrivateMine {
    public static boolean finishedInitTasks = false;
    public static final String PREFIX = ChatColor.translateAlternateColorCodes('&', "&d&lPrivate Mines &8&l>> &r");

    public static World PRIVATE_MINES_WORLD;
    public static final int REFILL_DELAY = 1000 * 30; //The delay in MS between the time that a player is allowed to refill their mine and the time that the mine is refilled

    public static Map<UUID, PrivateMine> PRIVATE_MINES = new HashMap<>();
    public static TreeMap<Integer, Set<PrivateMine>> PRIVATE_MINES_SORTED_BY_LEVEL = new TreeMap<>();
    public static Map<UUID, PrivateMine> PLAYER_PRIVATE_MINES = new HashMap<>();
    public static Map<String, PrivateMine> MINE_ID_TO_PRIVATE_MINE = new HashMap<>();


    private static final int BASE_XP_PER_LEVEL = 1000;
    private static final double LEVEL_RATE_OF_INCREASE = 1.9;
    public static long getLevelRequirement(int level) {
        if (level <= 0) return BASE_XP_PER_LEVEL;
        return (long) ((long) BASE_XP_PER_LEVEL * level + level * Math.pow(LEVEL_RATE_OF_INCREASE * level, LEVEL_RATE_OF_INCREASE));
    }





    private boolean isLoading = false; //True if the build or mine is being created for the first time. Gets set to false when both of these operations are complete.
    public boolean isLoaded = false; //True once the schematic and mine have been loaded for the first time.

    @NotNull public UUID privateMineId; //Unique ID of the private mine
    public int gridPosition; //The position of the private mine in the grid, used to find the x and z coordinates of the private mine
    @NotNull public UUID owner; //The UUID of the owner of the private mine
    public String name; //The name of the private mine
    private final Set<UUID> whitelist = new HashSet<>(); //The UUIDs of the players who are whitelisted in the private mine and can access it even when it is not open to the public
    private long xp = 0; //The amount of XP that the private mine has, it should increase by 1 whenever someone breaks a block in the mine (raw blocks)
    private int level; //The level of the private mine, this number is not super important and gets recalculated when the private mine is initially loaded
    private int lastUpgradePurchaseLevel = 0; //The last level that an upgrade was unlocked at. Use this value to calculate the private mine's stats rather than its level. //todo: add to save and load
    private int nextAvailableUpgradeLevel = 0; //The next level that an upgrade will be unlocked at.
    private final List<Integer> availableUpgrades = new ArrayList<>(); //List containing the levels of the available upgrades for the private mine. A level is put into this list when some stat of the private mine will change from an upgrade

    public boolean isLoading() { return isLoading; }
    public UUID getPrivateMineId() { return privateMineId; }
    public int getGridPosition() { return gridPosition; }
    public UUID getOwner() { return owner; }
    public String getName() { return name; }

    public Set<UUID> getWhitelist() { return whitelist; }
    public void addToWhitelist(UUID player) {
        whitelist.add(player);
        Set<PrivateMine> invites = PrivateMineManager.INVITED_MINES.getOrDefault(player, new HashSet<>());
        invites.add(this);
        PrivateMineManager.INVITED_MINES.put(player, invites);
    }
    public void removeFromWhitelist(UUID player) {
        whitelist.remove(player);
        Set<PrivateMine> invites = PrivateMineManager.INVITED_MINES.getOrDefault(player, new HashSet<>());
        invites.remove(this);
        if (invites.isEmpty()) PrivateMineManager.INVITED_MINES.remove(player);
        else PrivateMineManager.INVITED_MINES.put(player, invites);
    }


    public long getXp() { return xp; }
    public void setXp(long xp) { this.xp = xp; }
    public boolean setXpAndCalcLevel(long xp) {
        setXp(xp);
        return recalculateLevel();
    }
    public int getLevel() { return level; }
    public long getNextLevelRequirement() {
        return getLevelRequirement(getLevel() + 1);
    }


    private long xpRequiredForNextLevel = -1; //Cached value of the XP required for the next level, use this to check if the private mine has leveled up (compare the XP value) rather than recalculating it all the time
    /**
     * @return true if the level has changed, false otherwise
     */
    public boolean recalculateLevel() {
        boolean levelChanged = false;
        if (xpRequiredForNextLevel == -1) {
            xpRequiredForNextLevel = getNextLevelRequirement(); //If this private mine has not been loaded yet, this will be -1, so we need to calculate it now, so we can use it later
        }
        while (xp >= xpRequiredForNextLevel) { //Keep checking until the XP is less than the amount required for the next level. This will set the private mine's level to its real value using its current XP
            level++;
            xpRequiredForNextLevel = getNextLevelRequirement();
            levelChanged = true;
        }
        if (levelChanged) updateAvailableUpgrades(); //If the level has changed, we need to recalculate the available upgrades
        return levelChanged;
    }

    /**
     * Update the list of available upgrades for the private mine. This should be called whenever the private mine's level changes.
     */
    void updateAvailableUpgrades() {
        List<Integer> lastAvailableUpgrades = new ArrayList<>(availableUpgrades);
        availableUpgrades.clear();
        nextAvailableUpgradeLevel = -1;
        PrivateMineStats lastStat = PrivateMineConfigManager.getStats(lastUpgradePurchaseLevel);
        for (int i = lastUpgradePurchaseLevel + 1; i <= level; i++) {
            if (!PrivateMineConfigManager.getStats(i).equals(lastStat)) {
                lastStat = PrivateMineConfigManager.getStats(i);
                availableUpgrades.add(i);
                if (nextAvailableUpgradeLevel == -1) nextAvailableUpgradeLevel = i;
            }
        }
        if (availableUpgrades.size() > lastAvailableUpgrades.size()) { //Message the private mine owner if a new upgrade is available
            messageOwner("Your private mine has an upgrade available!");
        }
    }
    public List<Integer> getAvailableUpgrades() { return availableUpgrades; }

    /**
     * Attempt to upgrade the private mine
     * @return true if the upgrade was successful, false otherwise. This will depend on if the owner has enough money to purchase the upgrade or not.
     */
    public boolean upgrade() {
        if (nextAvailableUpgradeLevel == -1) {
            messageOwner("Your private mine does not have any upgrades available!");
            return false;
        }
        PrivateMineStats nextStats = PrivateMineConfigManager.getStats(nextAvailableUpgradeLevel);
        //Check if the player has enough money to purchase the upgrade
        PlayerData playerData = new PlayerData(owner);
        if (playerData.getMoney() < nextStats.getUpgradeCost()) {
            messageOwner("You do not have enough money for this!");
            return false;
        }
        messageOwner("You upgraded your private mine!");
        playerData.removeMoney(nextStats.getUpgradeCost());
        lastUpgradePurchaseLevel = nextAvailableUpgradeLevel;
        updateAvailableUpgrades();
        updatePhysical(nextStats.schematic, nextStats.blocks, nextStats.size);
        return true;
    }

    private Clipboard schematic = PrivateMineConfigManager.DEFAULT.getSchematic(); //The clipboard containing the schematic of the private mine
    private WeightedElements<MineBlock> mineBlocks = PrivateMineConfigManager.DEFAULT.getBlocks(); //The list of mine blocks that are in the private mine
    private int mineSize = PrivateMineConfigManager.DEFAULT.getSize(); //The size of the private mine's mine

    public int getMineSize() {
        return mineSize;
    }
    public Clipboard getSchematic() {
        return schematic;
    }
    public WeightedElements<MineBlock> getMineBlocks() {
        return mineBlocks;
    }
    public int getLastUpgradePurchaseLevel() { return lastUpgradePurchaseLevel; }

    /**
     * Update the physical properties of the private mine. This will include the schematic and the mine.
     */
    public CompletableFuture<PrivateMine> updatePhysical(Clipboard schematic, WeightedElements<MineBlock> mineBlocks, int mineSize) {
        CompletableFuture<PrivateMine> future = new CompletableFuture<>();
        if (isLoaded) {
            if (!schematic.equals(this.schematic)) { //The schematic has changed and the build should be updated.
                this.schematic = schematic;
                updateBuild(false).thenAccept(pm -> {
                    if (!pm.mineBlocks.equals(mineBlocks) || pm.mineSize != mineSize) { //The mine has changed, update it
                        pm.mineBlocks = mineBlocks;
                        pm.mineSize = mineSize;
                        updateMine(true).thenRun(() -> {
                            messageAll("This private mine was upgraded!");
                            future.complete(pm);
                        });
                    } else { //The mine has not changed, in this case we just need to refill the mine
                        mine.refill().thenRun(() -> {
                            messageAll("This private mine was upgraded!");
                            future.complete(pm);
                        });
                    }
                });
                return future;
            }
            if (!mineBlocks.equals(this.mineBlocks) || mineSize != this.mineSize) { //The mine has changed, update it
                this.mineBlocks = mineBlocks;
                this.mineSize = mineSize;
                updateMine(true).thenRun(() -> {
                    messageAll("This private mine was upgraded!");
                    future.complete(this);
                });
                return future;
            }
        } else { //The private mine is not loaded, just update the stats
            this.schematic = schematic;
            this.mineBlocks = mineBlocks;
            this.mineSize = mineSize;
        }
        return CompletableFuture.completedFuture(this);
    }

    /**
     * Update the build of the private mine. This will make use of the schematic.
     * @param completeFutureOnMainThread - if true, the completeable future will be completed on the main thread, otherwise, it will be completed on an async thread.
     */
    private CompletableFuture<PrivateMine> updateBuild(boolean completeFutureOnMainThread) {
        CompletableFuture<PrivateMine> future = new CompletableFuture<>();
        int[] position = PrivateMineManager.getPosition(gridPosition);
        Bukkit.getScheduler().runTaskAsynchronously(StaticPrisons.getInstance(), () -> {
            try (EditSession editSession = WorldEdit.getInstance().newEditSession(BukkitAdapter.adapt(PRIVATE_MINES_WORLD))) {
                Operation operation = new ClipboardHolder(schematic)
                        .createPaste(editSession)
                        .to(BlockVector3.at(position[0], 100, position[1]))
                        .build();
                Operations.complete(operation);
            }
            if (completeFutureOnMainThread && !Bukkit.isPrimaryThread()) {
                Bukkit.getScheduler().runTask(StaticPrisons.getInstance(), () -> future.complete(this));
            } else future.complete(this);
        });
        return future;
    }

    private StaticMine mine = null;
    private long lastRefilledAt = 0; //The time the mine was last refilled
    public @Nullable StaticMine getMine() { return mine; }

    /**
     * Delete and create a new StaticMine instance for the private mine.
     * @param completeFutureOnMainThread true if the future should be completed on the main thread, false otherwise
     */
    private CompletableFuture<StaticMine> updateMine(boolean completeFutureOnMainThread) {
        CompletableFuture<StaticMine> future = new CompletableFuture<>();
        mine.delete();
        MINE_ID_TO_PRIVATE_MINE.remove(mine.getId());
        registerMine(completeFutureOnMainThread);
        future.complete(mine);
        return future;
    }
    /**
     * Create a new StaticMine instance for the private mine.
     * @param completeFutureOnMainThread true if the future should be completed on the main thread, false otherwise
     */
    private CompletableFuture<StaticMine> registerMine(boolean completeFutureOnMainThread) {
        CompletableFuture<StaticMine> future = new CompletableFuture<>();
        int distanceFromCenter = getMineSize() / 2;
        int[] center = PrivateMineManager.getPosition(gridPosition);

        WeightedElements<BlockType> mineBlocks = new WeightedElements<>();
        for (WeightedElement<MineBlock> mineBlock : this.mineBlocks.getElements()) {
            mineBlocks.add(BukkitAdapter.asBlockType(mineBlock.element().material()), mineBlock.weight());
        }

        StaticMine mine = StaticMineBuilder.getBuilder()
                .id("private_mine-" + privateMineId)
                .world(PRIVATE_MINES_WORLD)
                .corners(BlockVector3.at(center[0] - distanceFromCenter, 1, center[1] - distanceFromCenter), BlockVector3.at(center[0] + distanceFromCenter, 99, center[1] + distanceFromCenter))
                .saveToFile(false)
                .async(true)
                .refillOnTimer(false)
                .blocks(mineBlocks)
                .onRefill(m -> lastRefilledAt = System.currentTimeMillis())
                .build();

        mine.refill().thenAccept(future::complete);

//        List<StaticMine.MineBlock> mineBlocks = new ArrayList<>(); //Adapt the weighted elements to MineBlocks that the mine can use
//        for (WeightedElement<MineBlock> block : this.mineBlocks.getElements()) {
//            mineBlocks.add(new StaticMine.MineBlock(BukkitAdapter.asBlockType(block.getElement().material()), block.getWeight()));
//        }
//        mine.setMineBlocks(mineBlocks.toArray(new StaticMine.MineBlock[0]));

//        this.mine = mine;
//        MINE_ID_TO_PRIVATE_MINE.put(mine.getId(), this);
//        mine.refill(completeFutureOnMainThread).thenAccept(m -> {
//            lastRefilledAt = System.currentTimeMillis();
//            future.complete(mine);
//        });
        return future;
    }



    public double visitorTax; //When player's mine in someone else's private mine, a percentage of the blocks' value that they mine will be taxed by the owner of the mine. Invited players will not be taxed.
    public boolean isPublic; //Whether the private mine is public or not. If it is public, it will be visible to everyone. If it is not, it will be visible only to the owner and those that have been invited to it
    public double sellPercentage; //The percentage of a block's value that players will get when selling blocks that were mined in this private mine.


    /**
     * @param privateMineId - the id of the private mine to get
     * @return a completable future that will be completed with the private mine, or null if the private mine does not exist. If the private mine is loaded, it will complete instantly, otherwise it will load the private mine on a separate set of threads
     */
    public static CompletableFuture<PrivateMine> getPrivateMine(UUID privateMineId) {
        PrivateMine privateMine = PRIVATE_MINES.get(privateMineId);
        if (privateMine == null) return CompletableFuture.completedFuture(null);
        if (privateMine.isLoaded) return CompletableFuture.completedFuture(privateMine);
        CompletableFuture<PrivateMine> future = new CompletableFuture<>();
        loadPrivateMine(privateMineId).thenAccept(future::complete);
        return future;
    }
    /**
     * @param playerUUID - the UUID of the player to get the private mine of
     * @return a completable future that will be completed with the private mine, or null if the private mine does not exist. If the private mine is loaded, it will complete instantly, otherwise it will load the private mine on a separate set of threads
     */
    public static CompletableFuture<PrivateMine> getPrivateMineFromPlayer(UUID playerUUID) {
        PrivateMine privateMine = PLAYER_PRIVATE_MINES.get(playerUUID);
        if (privateMine == null) return CompletableFuture.completedFuture(null);
        if (privateMine.isLoaded) return CompletableFuture.completedFuture(privateMine);
        CompletableFuture<PrivateMine> future = new CompletableFuture<>();
        loadPrivateMine(privateMine.privateMineId).thenAccept(future::complete);
        return future;
    }
    /**
     * @param player - the player to get the private mine of
     * @return a completable future that will be completed with the private mine, or null if the private mine does not exist. If the private mine is loaded, it will complete instantly, otherwise it will load the private mine on a separate set of threads
     */
    public static CompletableFuture<PrivateMine> getPrivateMineFromPlayer(Player player) {
        return getPrivateMineFromPlayer(player.getUniqueId());
    }
    /**
     * @param player - the player to get the private mine of
     * @return A private mine that may or may not be loaded. If the private mine is not loaded, some of its properties will be null/not defined.
     */
    public static PrivateMine getPrivateMineFromPlayerWithoutLoading(Player player) {
        return getPrivateMineFromPlayerWithoutLoading(player.getUniqueId());
    }

    /**
     * @param playerUUID - the UUID of the player to get the private mine of
     * @return A private mine that may or may not be loaded. If the private mine is not loaded, some of its properties will be null/not defined.
     */
    public static PrivateMine getPrivateMineFromPlayerWithoutLoading(UUID playerUUID) {
        return PLAYER_PRIVATE_MINES.get(playerUUID);
    }

    /**
     * @param playerUUID - the UUID of the player to check
     * @return true if the player has a private mine, false otherwise
     */
    public static boolean playerHasPrivateMine(UUID playerUUID) {
        return PLAYER_PRIVATE_MINES.containsKey(playerUUID);
    }
    /**
     * @param player - the player to check
     * @return true if the player has a private mine, false otherwise
     */
    public static boolean playerHasPrivateMine(Player player) {
        return playerHasPrivateMine(player.getUniqueId());
    }

    /**
     * @param player - the player that should own this private mine
     * @return a completable future that will be completed with the private mine, will complete when the mine is done loading
     */
    public static CompletableFuture<PrivateMine> createPrivateMine(Player player) {
        if (playerHasPrivateMine(player)) {
            throw new IllegalArgumentException("Tried to create a private mine for a player that already has one!");
        }
        PrivateMine privateMine = new PrivateMine(
                UUID.randomUUID(),
                PrivateMineManager.createNewIslandOnGrid(),
                player.getUniqueId(),
                player.getName() + "'s Private Mine",
                0,
                PrivateMineConfigManager.DEFAULT_TAX,
                false,
                PrivateMineConfigManager.DEFAULT_SELL_PERCENTAGE,
                0
        );
        return loadPrivateMine(privateMine.privateMineId);
    }
    public PrivateMine(@NotNull UUID privateMineId, int gridPosition, UUID owner, String name, long xp, double visitorTax, boolean isPublic, double sellPercentage, int lastUpgradePurchaseLevel) {
        this.privateMineId = privateMineId;
        this.gridPosition = gridPosition;
        this.owner = owner;
        this.name = name;
        this.lastUpgradePurchaseLevel = lastUpgradePurchaseLevel;
        this.visitorTax = visitorTax;
        this.isPublic = isPublic;
        this.sellPercentage = sellPercentage;
        if (!setXpAndCalcLevel(xp)) { //Uses some other properties so call this last
            updateAvailableUpgrades();
        }
        schematic = PrivateMineConfigManager.STATS_PER_LEVEL[lastUpgradePurchaseLevel].getSchematic();
        mineBlocks = PrivateMineConfigManager.STATS_PER_LEVEL[lastUpgradePurchaseLevel].getBlocks();
        mineSize = PrivateMineConfigManager.STATS_PER_LEVEL[lastUpgradePurchaseLevel].getSize();

        PRIVATE_MINES.put(privateMineId, this);
        PLAYER_PRIVATE_MINES.put(owner, this);
        updateTreeMap();
    }


    CompletableFuture<PrivateMine> loadingFuture = new CompletableFuture<>();
    public static CompletableFuture<PrivateMine> loadPrivateMine(UUID privateMineId) {
        PrivateMine privateMine = PRIVATE_MINES.get(privateMineId);
        if (privateMine == null) return null;
        if (privateMine.isLoaded) return CompletableFuture.completedFuture(privateMine);
        if (privateMine.isLoading) return privateMine.loadingFuture; //To prevent a private mine being loaded multiple times at once
        privateMine.isLoading = true;
        Bukkit.getScheduler().runTaskAsynchronously(StaticPrisons.getInstance(), () -> {
            //Delete old builds since the private mine is being loaded for the first time
            int[] position = PrivateMineManager.getPosition(privateMine.gridPosition);
            EditSession es = WorldEdit.getInstance().newEditSession(BukkitAdapter.adapt(PRIVATE_MINES_WORLD));
            Region region = new CuboidRegion(BukkitAdapter.adapt(PRIVATE_MINES_WORLD), BlockVector3.at(position[0] + 275, 0, position[1] + 250), BlockVector3.at(position[0] - 250, 255, position[1] - 250));
            es.setBlocks(region, BlockTypes.AIR);
            es.close();
            privateMine.updateBuild(false).thenRun(() -> privateMine.registerMine(true).thenRun(() -> {
                privateMine.loadingFuture.complete(privateMine);
                privateMine.isLoaded = true;
                privateMine.isLoading = false;
            }));
        });
        return privateMine.loadingFuture;
    }

    public static final int XP_PER_BLOCK_BROKEN = 1;
    public void blockBroken(BlockBreak blockBreak) {
        setXpAndCalcLevel(getXp() + XP_PER_BLOCK_BROKEN);
        blockBreak.getStats().setMoneyMultiplier(blockBreak.getStats().getMoneyMultiplier() * sellPercentage);
        if (!(blockBreak.getPlayerData().getUUID().equals(owner) || getWhitelist().contains(blockBreak.getPlayerData().getUUID()))) { //Don't tax owner or whitelisted players

            blockBreak.addAfterProcess(bb -> { //Add tax to the owner
                new PlayerData(owner).addMoney((long) (bb.getStats().getMoneyEarned() * visitorTax));
                bb.getStats().setMoneyMultiplier(bb.getStats().getMoneyMultiplier() * (1 - visitorTax));
            });
        }
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



    public void sendInfo(Player player) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e&l" + name + ":" + "\n&a\n") + buildInfo());
    }
    public String buildInfo() {
        return ChatColor.translateAlternateColorCodes('&',
                        "&cOwner: &f" + ServerData.PLAYERS.getName(owner) + "\n" +
                        "&cLevel: &f" + PrisonUtils.addCommasToNumber(getLevel()) + "\n" +
                        "&cExperience: &f" + PrisonUtils.prettyNum(getXp()) + " / " + PrisonUtils.prettyNum(getNextLevelRequirement()) + "\n" +
                        "&cSize: &f" + getMineSize() + "x" + getMineSize() + "\n" +
                        "&cTax: &f" + new DecimalFormat("0").format(visitorTax * 100) + "%" + "\n" +
                        "&cSell Percentage: &f" + new DecimalFormat("0.0").format(sellPercentage * 100) + "%" + "\n" +
                        "&a" + "\n" +
                        "&c&lSpecial Attributes: &fnone");
    }
    public List<String> buildInfoAsList() {
        return List.of(buildInfo().split("\n"));
    }
    void showWorldBoarder(Player player) {
        int[] position = PrivateMineManager.getPosition(gridPosition);
        PrivateMineStats stats = PrivateMineConfigManager.STATS_PER_LEVEL[lastUpgradePurchaseLevel];
        position[0] += stats.getWorldborderOffsetX();
        position[1] += stats.getWorldborderOffsetZ();
        StaticPrisons.worldBorderAPI.setBorder(player, stats.getWorldborderSize(), new Location(PRIVATE_MINES_WORLD, position[0] + 0.5, 100, position[1] + 0.5));
    }

    /**
     * Sends a message to the private mine owner IF the owner is online.
     * @param message message to send
     * @return true if the message was sent, false if the owner is offline.
     */
    public boolean messageOwner(String message) {
        Player player = Bukkit.getPlayer(owner);
        if (player != null) player.sendMessage(PREFIX + ChatColor.translateAlternateColorCodes('&', message));
        return player != null;
    }

    /**
     * Sends a message to all players currently in this private mine
     * @param message message to send
     */
    public void messageAll(String message) {
        for (Player player : getAllPlayersInPrivateMine()) player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&d&l" + name + " &8&l>> &r" + message));
    }

    /**
     * Send a formatted message to a player
     * @param who player to send the message to
     * @param message message to send
     */
    public static void sendMessage(Player who, String message) {
        who.sendMessage(PREFIX + ChatColor.translateAlternateColorCodes('&', message));
    }



    private void updateTreeMap() {
        if (PRIVATE_MINES_SORTED_BY_LEVEL.containsKey(level)) PRIVATE_MINES_SORTED_BY_LEVEL.get(level).remove(this);
        if (PRIVATE_MINES_SORTED_BY_LEVEL.containsKey(level)) if (PRIVATE_MINES_SORTED_BY_LEVEL.get(level).isEmpty()) PRIVATE_MINES_SORTED_BY_LEVEL.remove(level);
        if (!PRIVATE_MINES_SORTED_BY_LEVEL.containsKey(level)) PRIVATE_MINES_SORTED_BY_LEVEL.put(level, new HashSet<>());
        PRIVATE_MINES_SORTED_BY_LEVEL.get(level).add(this);
    }


}
