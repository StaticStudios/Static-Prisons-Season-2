package net.staticstudios.prisons.pickaxe.enchants;

import net.staticstudios.mines.StaticMine;
import net.staticstudios.mines.StaticMines;
import net.staticstudios.prisons.backpacks.BackpackManager;
import net.staticstudios.prisons.minebombs.PreComputerMineBomb;
import net.staticstudios.prisons.mines.MineBlock;
import net.staticstudios.prisons.pickaxe.PrisonPickaxe;
import net.staticstudios.prisons.pickaxe.enchants.handler.BaseEnchant;
import net.staticstudios.prisons.pickaxe.enchants.handler.EnchantTier;
import net.staticstudios.prisons.pickaxe.enchants.handler.PickaxeEnchants;
import net.staticstudios.prisons.privatemines.PrivateMine;
import net.staticstudios.prisons.utils.Constants;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class EggShooterEnchant extends BaseEnchant {
    public EggShooterEnchant() {
        super("eggShooter", "&6&lEgg Shooter", 1000, 500, "&7Shoot explosive eggs while right-clicking");
        setPickaxeLevelRequirement(50);

        setTiers(
                new EnchantTier(50, 0),
                new EnchantTier(100, 1),
                new EnchantTier(150, 2),
                new EnchantTier(200, 3),
                new EnchantTier(300, 4),
                new EnchantTier(400, 5),
                new EnchantTier(500, 6),
                new EnchantTier(600, 7),
                new EnchantTier(700, 8),
                new EnchantTier(800, 9),
                new EnchantTier(900, 10),
                new EnchantTier(1000, 11)
        );
    }
    public void whileRightClicking(Player player, PrisonPickaxe pickaxe) {
        if (!player.getWorld().equals(Constants.MINES_WORLD) && !player.getWorld().equals(PrivateMine.PRIVATE_MINES_WORLD)) {
            return;
        }
        Egg egg = player.getWorld().spawn(player.getEyeLocation(), Egg.class);
        egg.setShooter(new EggShooterPickaxe(player, pickaxe));
        egg.setVelocity(player.getLocation().getDirection().multiply(1));
    }

    private static final Map<Integer, PreComputerMineBomb> BOMBS = new HashMap<>();

    public static void eggHit(ProjectileHitEvent e, EggShooterPickaxe eggShooterPickaxe) {
        Player player = eggShooterPickaxe.player;
        PrisonPickaxe pickaxe = eggShooterPickaxe.pickaxe;
        if (e.getHitEntity() != null && e.getHitEntity().equals(player)) {
            e.setCancelled(true);
            return;
        }
        if (e.getHitBlock() == null) return;
        StaticMine mine = StaticMines.fromLocation(e.getHitBlock().getLocation(), false);
        if (mine == null) return;
        int radius = (int) (pickaxe.getEnchantLevel(PickaxeEnchants.EGG_SHOOTER) / (PickaxeEnchants.EGG_SHOOTER.MAX_LEVEL / 5d) + 1);

        PreComputerMineBomb mineBomb = BOMBS.get(radius);
        if (mineBomb == null) { //If this is the first time we've seen this radius, create a new bomb, otherwise use an existing one
            mineBomb = new PreComputerMineBomb(radius);
            mineBomb.setUseParticles(false);
            BOMBS.put(radius, mineBomb);
        }

        int fortune = pickaxe.getEnchantLevel(PickaxeEnchants.FORTUNE);
        Map<MineBlock, Long> map = new HashMap<>();
        for (Map.Entry<Material, Long> entry: mineBomb.explode(mine, e.getHitBlock().getLocation()).entrySet()) {
            map.put(MineBlock.fromMaterial(entry.getKey()), entry.getValue() * fortune);
        }
        player.getWorld().spawnParticle(Particle.EXPLOSION_HUGE, e.getHitBlock().getLocation(), 3, 2, 2, 2);
        mine.removeBlocks(mineBomb.blocksChanged);
        pickaxe.addBlocksBroken(mineBomb.blocksChanged);
        pickaxe.addXp(mineBomb.blocksChanged * 2);
        BackpackManager.addToBackpacks(player, map);
    }

    public static class EggShooterPickaxe implements ProjectileSource {

        public Player player;
        public PrisonPickaxe pickaxe;

        public EggShooterPickaxe(Player player, PrisonPickaxe pickaxe) {
            this.player = player;
            this.pickaxe = pickaxe;
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
}
