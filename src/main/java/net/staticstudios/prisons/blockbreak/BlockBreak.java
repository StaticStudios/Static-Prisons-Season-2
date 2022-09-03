package net.staticstudios.prisons.blockbreak;

import net.staticstudios.mines.StaticMine;
import net.staticstudios.mines.StaticMines;
import net.staticstudios.mines.api.events.BlockBrokenInMineEvent;
import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.backpacks.BackpackManager;
import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.mines.MineBlock;
import net.staticstudios.prisons.pickaxe.PrisonPickaxe;
import net.staticstudios.prisons.privatemines.PrivateMine;
import net.staticstudios.prisons.utils.Constants;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class BlockBreak {

    public static void init() {
        Bukkit.getPluginManager().registerEvents(new BlockBreak.Listener(), StaticPrisons.getInstance());
    }

    static List<Consumer<BlockBreak>> blockBreakListeners = new LinkedList<>();

    /**
     * Add a listener to be called when a block is broken.
     */
    public static void addListener(Consumer<BlockBreak> listener) {
        blockBreakListeners.add(listener);
    }

    private final BlockBreakStats stats = new BlockBreakStats();

    public BlockBreakStats getStats() {
        return stats;
    }

    private Player player;
    private @NotNull PlayerData playerData;
    private PrisonPickaxe pickaxe;
    private StaticMine mine;
    private Location blockLocation;

    private boolean isSimulated = false; //If true, there is no "real" block to "break"
    private BlockBreakEvent blockBreakEvent;

    private final List<Consumer<BlockBreak>> runAfterProcess = new LinkedList<>();

    public BlockBreak addAfterProcess(Consumer<BlockBreak> consumer) {
        runAfterProcess.add(consumer);
        return this;
    }


    private boolean isCancelled = false;
    public boolean isCancelled() {
        return isCancelled;
    }
    public void setCancelled(boolean cancelled) {
        isCancelled = cancelled;
    }



    public BlockBreak(BlockBreakEvent e) {
        this.blockBreakEvent = e;
        this.player = e.getPlayer();
        this.playerData = new PlayerData(player);
        this.pickaxe = PrisonPickaxe.fromItem(player.getInventory().getItemInMainHand());
        this.mine = StaticMines.fromLocation(e.getBlock().getLocation(), false);
        this.blockLocation = e.getBlock().getLocation();
        this.isSimulated = false;
    }

    public BlockBreak(Player player, PrisonPickaxe pickaxe, StaticMine mine, Block block) {
        this.player = player;
        this.playerData = new PlayerData(player);
        this.pickaxe = pickaxe;
        this.blockLocation = block.getLocation();
        this.mine = mine;
    }
    public BlockBreak(Player player, PrisonPickaxe pickaxe, Block block) {
        this.player = player;
        this.playerData = new PlayerData(player);
        this.pickaxe = pickaxe;
        this.blockLocation = block.getLocation();
        this.mine = StaticMines.fromLocation(blockLocation, false);
    }
    public BlockBreak(@NotNull PlayerData playerData, PrisonPickaxe pickaxe, Block block) {
        this.playerData = playerData;
        this.pickaxe = pickaxe;
        this.blockLocation = block.getLocation();
        this.mine = StaticMines.fromLocation(blockLocation, false);
    }
    public BlockBreak(@NotNull PlayerData playerData, PrisonPickaxe pickaxe, StaticMine mine, Block block) {
        this.playerData = playerData;
        this.pickaxe = pickaxe;
        this.blockLocation = block.getLocation();
        this.mine = mine;
    }
    public BlockBreak setPlayer(Player player) {
        this.player = player;
        return this;
    }
    public BlockBreak setPlayerData(PlayerData playerData) {
        this.playerData = playerData;
        return this;
    }
    public BlockBreak setPickaxe(PrisonPickaxe pickaxe) {
        this.pickaxe = pickaxe;
        return this;
    }
    public BlockBreak setBlockLocation(Location blockLocation) {
        this.blockLocation = blockLocation;
        this.mine = StaticMines.fromLocation(blockLocation, false);
        return this;
    }
    public BlockBreak setIsSimulated(boolean isSimulated) {
        this.isSimulated = isSimulated;
        return this;
    }
    public BlockBreak setBlockBreakEvent(BlockBreakEvent blockBreakEvent) {
        this.blockBreakEvent = blockBreakEvent;
        return this;
    }
    public Player getPlayer() {
        return player;
    }
    public PlayerData getPlayerData() {
        return playerData;
    }
    public PrisonPickaxe getPickaxe() {
        return pickaxe;
    }
    public Location getBlockLocation() {
        return blockLocation;
    }
    public StaticMine getMine() {
        return mine;
    }
    public boolean isSimulated() {
        return isSimulated;
    }

    public BlockBreak messagePlayer(String msg) {
        if (player != null) player.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
        return this;
    }

    /**
     *
     * @return true if the block was successfully "broken", false if it was not.
     */
    public boolean process() {
        for (Consumer<BlockBreak> listener : blockBreakListeners) {
            if (isCancelled) break;
            listener.accept(this);
        }


        if (!isCancelled) {
            if (!isSimulated && blockLocation != null) {
                if (blockBreakEvent != null) {
                    blockBreakEvent.setCancelled(true);
                }
                MineBlock mb = MineBlock.fromMaterial(blockLocation.getBlock().getType());
                if (mb != null && !mb.material().equals(Material.AIR)) {
                    stats.getMinedBlocks().put(mb, stats.getMinedBlocks().getOrDefault(mb, 0L) + 1);
                }
                blockLocation.getBlock().setType(Material.AIR, false);
            }
            stats.setBlocksBroken(stats.getBlocksBroken() + 1);
            stats.setRawBlockBroken(stats.getRawBlockBroken() + 1);
            for (Consumer<BlockBreak> consumer : runAfterProcess) {
                consumer.accept(this);
            }

            //Add stats to playerData and pickaxe
            playerData.addMoney((long) (stats.getMoneyEarned() * stats.getMoneyMultiplier()));
            playerData.addTokens((long) (stats.getTokensEarned() * stats.getTokenMultiplier()));
            playerData.addPlayerXP((long) (stats.getXpEarned() * stats.getXpMultiplier()));
            playerData.addRawBlocksMined(stats.getRawBlockBroken());
            playerData.addBlocksMined(stats.getBlocksBroken());

            //Apply the blocksBrokenMultiplier
            stats.getMinedBlocks().replaceAll((k, v) -> (long) (v * stats.getBlocksBrokenMultiplier()));

//            boolean backpackWasFull = playerData.getBackpackIsFull();
            if (player != null) {
                BackpackManager.addToBackpacks(player, stats.getMinedBlocks());
            }

            if (pickaxe != null) {
                pickaxe.addXp((long) (2 * stats.getRawBlockBroken() * stats.getXpMultiplier()));
                pickaxe.addBlocksBroken(stats.getBlocksBroken());
                pickaxe.addRawBlocksBroken(stats.getRawBlockBroken());
            }

            mine.removeBlocks(stats.getBlocksBroken());
        }
        return isCancelled;
    }


    static class Listener implements org.bukkit.event.Listener {

        @EventHandler
        void onBlockBrokenInMine(BlockBrokenInMineEvent e) {
            if (!e.getPlayer().getWorld().equals(Constants.MINES_WORLD) && !e.getPlayer().getWorld().equals(PrivateMine.PRIVATE_MINES_WORLD)) return; //Ensure that a player is in a mines world
            PrisonPickaxe pickaxe = PrisonPickaxe.fromItem(e.getPlayer().getInventory().getItemInMainHand());
            if (pickaxe == null) return; //We don't care if the player is not holding a pickaxe
            new BlockBreak(e.getPlayer(), pickaxe, e.getMine(), e.getBlock()).process();
        }

    }

}
