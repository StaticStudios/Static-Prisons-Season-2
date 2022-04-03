package me.staticstudios.prisons.mines;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.world.block.BlockTypes;
import io.papermc.lib.PaperLib;
import me.staticstudios.prisons.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PrivateMine extends BaseMine {
    private final UUID uuid;
    private boolean isLoaded = false;

    public UUID getUuid() {
        return uuid;
    }

    private PrivateMine(UUID uuid, Location loc1, Location loc2) {
        super("privateMine-" + uuid, loc1, loc2);
        this.uuid = uuid;
    }

    public static void unload(String id) {
        MineManager.allMines.remove(id);
    }

    /**
     *
     * Call this method async
     *
     * Clears the space of any previous private mine by setting it to air
     */
   public static void create(int squareSize, Material blockType, Player player) {
       UUID ownerUUID = player.getUniqueId();
       player.sendMessage(ChatColor.AQUA + "Loading your private mine, please wait...");
       Bukkit.getScheduler().runTaskAsynchronously(Main.getMain(), () -> {
           PrivateMine mine = new PrivateMine(ownerUUID, new Location(Bukkit.getWorld("mines"), -squareSize / 2, 1, -squareSize / 2), new Location(Bukkit.getWorld("mines"), squareSize / 2, 149, squareSize / 2));
           mine.setBlockPattern(new MineBlock[]{new MineBlock(blockType, 100)});
           mine.setWhereToTpPlayerOnRefill(new Location(Bukkit.getWorld("mines"), mine.mineOffset, 150, 0));
           mine.clear();
           EditSession editSession = WorldEdit.getInstance().newEditSession(mine.region.getWorld());
           editSession.setBlocks((Region) new CuboidRegion(BukkitAdapter.adapt(mine.minLocation.getWorld()), BlockVector3.at(mine.minLocation.getX() - 3, 0, mine.minLocation.getZ() - 3), BlockVector3.at(mine.maxLocation.getX() + 3, 255, mine.minLocation.getZ() - 3)), BlockTypes.BARRIER);
           editSession.setBlocks((Region) new CuboidRegion(BukkitAdapter.adapt(mine.minLocation.getWorld()), BlockVector3.at(mine.minLocation.getX() - 3, 0, mine.maxLocation.getZ() + 3), BlockVector3.at(mine.maxLocation.getX() + 3, 255, mine.maxLocation.getZ() + 3)), BlockTypes.BARRIER);
           editSession.setBlocks((Region) new CuboidRegion(BukkitAdapter.adapt(mine.minLocation.getWorld()), BlockVector3.at(mine.minLocation.getX() - 3, 0, mine.minLocation.getZ() - 3), BlockVector3.at(mine.minLocation.getX() - 3, 255, mine.maxLocation.getZ() + 3)), BlockTypes.BARRIER);
           editSession.setBlocks((Region) new CuboidRegion(BukkitAdapter.adapt(mine.minLocation.getWorld()), BlockVector3.at(mine.maxLocation.getX() + 3, 0, mine.minLocation.getZ() - 3), BlockVector3.at(mine.maxLocation.getX() + 3, 255, mine.maxLocation.getZ() + 3)), BlockTypes.BARRIER);
           editSession.setBlocks((Region) new CuboidRegion(BukkitAdapter.adapt(mine.minLocation.getWorld()), BlockVector3.at(mine.minLocation.getX() - 3, 255, mine.minLocation.getZ() - 3), BlockVector3.at(mine.maxLocation.getX() + 3, 255, mine.maxLocation.getZ() + 3)), BlockTypes.BARRIER);
           editSession.setBlocks((Region) new CuboidRegion(BukkitAdapter.adapt(mine.minLocation.getWorld()), BlockVector3.at(mine.minLocation.getX() - 2, 0, mine.minLocation.getZ() - 2), BlockVector3.at(mine.maxLocation.getX() + 2, 150, mine.minLocation.getZ() - 2)), BlockTypes.BEDROCK);
           editSession.setBlocks((Region) new CuboidRegion(BukkitAdapter.adapt(mine.minLocation.getWorld()), BlockVector3.at(mine.minLocation.getX() - 2, 0, mine.maxLocation.getZ() + 2), BlockVector3.at(mine.maxLocation.getX() + 2, 150, mine.maxLocation.getZ() + 2)), BlockTypes.BEDROCK);
           editSession.setBlocks((Region) new CuboidRegion(BukkitAdapter.adapt(mine.minLocation.getWorld()), BlockVector3.at(mine.minLocation.getX() - 2, 0, mine.minLocation.getZ() - 2), BlockVector3.at(mine.minLocation.getX() - 2, 150, mine.maxLocation.getZ() + 2)), BlockTypes.BEDROCK);
           editSession.setBlocks((Region) new CuboidRegion(BukkitAdapter.adapt(mine.minLocation.getWorld()), BlockVector3.at(mine.maxLocation.getX() + 2, 0, mine.minLocation.getZ() - 2), BlockVector3.at(mine.maxLocation.getX() + 2, 150, mine.maxLocation.getZ() + 2)), BlockTypes.BEDROCK);
           editSession.setBlocks((Region) new CuboidRegion(BukkitAdapter.adapt(mine.minLocation.getWorld()), BlockVector3.at(mine.minLocation.getX() - 2, 0, mine.minLocation.getZ() - 2), BlockVector3.at(mine.maxLocation.getX() + 3, 0, mine.maxLocation.getZ() + 2)), BlockTypes.BEDROCK);
           editSession.close();
           mine.isLoaded = true;
           mine.refill();
           player.sendMessage(ChatColor.AQUA + "Your private mine has been successfully loaded!");
           Bukkit.getScheduler().runTask(Main.getMain(), () -> mine.goTo(player));
       });
   }

    void clear() {
       Region region = new CuboidRegion(BukkitAdapter.adapt(minLocation.getWorld()), BlockVector3.at(mineOffset - 150, 0, -150), BlockVector3.at(mineOffset + 150, 255, 150));
       EditSession editSession = WorldEdit.getInstance().newEditSession(region.getWorld());
       editSession.setBlocks(region, BlockTypes.AIR);
       editSession.close();
   }

   public void goTo(Player player) {
        if (!isLoaded) {
            player.sendMessage(ChatColor.RED + "This mine is still loading...");
            return;
        }
        PaperLib.teleportAsync(player, getWhereToTpPlayerOnRefill());
        player.setAllowFlight(true);
   }






}
