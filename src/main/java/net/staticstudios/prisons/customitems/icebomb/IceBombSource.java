package net.staticstudios.prisons.customitems.icebomb;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.world.block.BlockTypes;
import net.staticstudios.mines.StaticMine;
import net.staticstudios.mines.StaticMines;
import net.staticstudios.prisons.StaticPrisons;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public record IceBombSource(Player player) implements ProjectileSource {

    void explode(Location blockLocation) {
        StaticMine mine = StaticMines.fromLocation(blockLocation, true);
        if (mine == null) return;

        try (EditSession editSession = StaticPrisons.worldEdit.newEditSession(mine.getWorld())) {
            IceBomb.POSITIONS.forEach(blockVector3 -> {
                BlockVector3 offsetVector = blockVector3.add(blockLocation.getBlockX(), blockLocation.getBlockY(), blockLocation.getBlockZ());
                if (mine.getRegion().contains(offsetVector)) {
                    editSession.setBlock(offsetVector, BlockTypes.PACKED_ICE);
                }
            });
        }

        player().getWorld().spawnParticle(Particle.EXPLOSION_HUGE, blockLocation, 3, 2, 2, 2);
    }


    @Override
    public <T extends Projectile> @NotNull T launchProjectile(@NotNull Class<? extends T> projectile) {
        return null;
    }

    @Override
    public <T extends Projectile> @NotNull T launchProjectile(@NotNull Class<? extends T> projectile, @Nullable Vector velocity) {
        return null;
    }
}
