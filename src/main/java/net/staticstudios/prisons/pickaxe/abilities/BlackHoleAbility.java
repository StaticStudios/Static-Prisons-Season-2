package net.staticstudios.prisons.pickaxe.abilities;

import net.staticstudios.mines.StaticMine;
import net.staticstudios.prisons.blockbreak.BlockBreak;
import net.staticstudios.prisons.pickaxe.PrisonPickaxe;
import net.staticstudios.prisons.pickaxe.abilities.handler.BaseAbility;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import java.util.LinkedList;
import java.util.List;

public class BlackHoleAbility extends BaseAbility {
//
//    public BlackHoleAbility() {
//        super("blackHole", "&5&lBlack Hole", 20, 15, (long) (1000 * 60 * 60 * 5.5),
//                "&oAs you move throught the mine, all of the blocks around you",
//                "&owill be sucked into your pickaxe. Your pickaxe's enchants",
//                "&owill work as if you broke each block individually!",
//                "",
//                "&aEach upgrade will increase the duration by 1 second!",
//                "",
//                "Cooldown: &c" + PrisonUtils.formatTime((long) (1000 * 60 * 60 * 5.5)));
//        requiresMineOnActivate = true;
//    }
//
//    static final int BLOCKS_TO_GET_PER_TICK = 7;
//    static final int RADIUS = 4;
//    public void onTick(int tick, Player player, PrisonPickaxe pickaxe, StaticMine mine) {
//        List<Location> blockLocations = new LinkedList<>(); //List of the block locations closest to the player starting from the closest
//        getLocs:
//            for (int s = 0; s <= RADIUS; s++) {
//                for (int x = player.getLocation().getBlockX() - s; x <= player.getLocation().getBlockX() + s; x++) {
//                    for (int z = player.getLocation().getBlockZ() - s; z <= player.getLocation().getBlockZ() + s; z++) {
//                        for (int y = player.getLocation().getBlockY() - s; y <= player.getLocation().getBlockY() + s; y++) {
//                            Location loc = new Location(player.getWorld(), x, y, z);
//                            if (loc.distance(player.getLocation()) > RADIUS) continue;
//                            if (loc.getBlock().getType() == Material.AIR) continue;
//                            if (!mine.getRegion().contains(x, y, z)) continue;
//                            blockLocations.add(loc);
//                            if (blockLocations.size() >= BLOCKS_TO_GET_PER_TICK) break getLocs;
//                        }
//                    }
//                }
//            }
//        for (Location location : blockLocations) {
//            BlockBreak blockBreak = new BlockBreak(player, pickaxe, mine, location.getBlock());
//            blockBreak.process();
//            location.getWorld().spawnParticle(Particle.REVERSE_PORTAL, location, 4);
//            location.getWorld().spawnParticle(Particle.PORTAL, location, 6);
//        }
//    }
//
//    @Override
//    public int getTimesToTick(int level) {
//        return 5 * 20 + level * 20; //Default 5 seconds + 1 second per level
//    }
}
