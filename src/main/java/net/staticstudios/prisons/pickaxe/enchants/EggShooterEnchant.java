package net.staticstudios.prisons.pickaxe.enchants;

import net.staticstudios.mines.StaticMine;
import net.staticstudios.mines.StaticMines;
import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.backpacks.BackpackManager;
import net.staticstudios.prisons.enchants.Enchantable;
import net.staticstudios.prisons.minebombs.PreComputedMineBomb;
import net.staticstudios.prisons.mines.MineBlock;
import net.staticstudios.prisons.pickaxe.PrisonPickaxe;
import net.staticstudios.prisons.pickaxe.enchants.handler.PickaxeEnchant;
import net.staticstudios.prisons.utils.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Consumer;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class EggShooterEnchant extends PickaxeEnchant implements Listener {

    private static final Map<Integer, PreComputedMineBomb> CACHED_BOMBS = new HashMap<>();
    private static final HashSet<String> ALLOWED_WORLDS = new HashSet<>();
    private static double DEFAULT_RADIUS = 1;
    private static double MAX_RADIUS = 1;

    private static BukkitTask taskTimer;

    public EggShooterEnchant() {
        super(EggShooterEnchant.class, "pickaxe-eggshooter");

        ALLOWED_WORLDS.clear();

        ALLOWED_WORLDS.addAll(getConfig().getStringList("allowed_worlds"));
        DEFAULT_RADIUS = getConfig().getDouble("default_radius", DEFAULT_RADIUS);
        MAX_RADIUS = getConfig().getDouble("max_radius", MAX_RADIUS);

        if (taskTimer != null) {
            taskTimer.cancel();
        }

        taskTimer = Bukkit.getScheduler().runTaskTimer(
                StaticPrisons.getInstance(),
                EggShooterEnchant::shootEggs,
                0,
                getConfig().getInt("shoot_interval", 20)
        );

        StaticPrisons.getInstance().getServer().getPluginManager().registerEvents(this, StaticPrisons.getInstance());
    }

    @EventHandler
    void projectileHit(ProjectileHitEvent e) {
        if (e.getEntity().getShooter() instanceof EggShooterPickaxe eggShooterPickaxe) {
            if (e.getHitEntity() != null && e.getHitEntity().equals(eggShooterPickaxe.player)) {
                e.setCancelled(true);
                return;
            }
            if (e.getHitBlock() == null) return;

            eggShooterPickaxe.explode(e.getHitBlock().getLocation());
        }
    }

    private static void shootEggs() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            if (!PlayerUtils.isHoldingRightClick(player)) return;

            if (!ALLOWED_WORLDS.contains(player.getWorld().getName())) return;

            ItemStack item = player.getInventory().getItemInMainHand();
            PrisonPickaxe pickaxe = PrisonPickaxe.fromItem(item);
            if (pickaxe == null) return;

            if (pickaxe.getEnchantmentLevel(EggShooterEnchant.class) <= 0 ||
                    pickaxe.isDisabled(EggShooterEnchant.class)) return;

            Egg egg = player.getWorld().spawn(player.getEyeLocation(), Egg.class);
            egg.setShooter(new EggShooterPickaxe(player, pickaxe));
            egg.setVelocity(player.getLocation().getDirection().multiply(1));
        });
    }

    private record EggShooterPickaxe(Player player, PrisonPickaxe pickaxe) implements ProjectileSource {

        void explode(Location blockLocation) {
            StaticMine mine = StaticMines.fromLocation(blockLocation, true);
            if (mine == null) return;

            int radius = (int) (DEFAULT_RADIUS + (pickaxe.getEnchantmentLevel(EggShooterEnchant.class) * (MAX_RADIUS - DEFAULT_RADIUS) / Enchantable.getEnchant(EggShooterEnchant.class).getMaxLevel()));
            PreComputedMineBomb mineBomb = CACHED_BOMBS.computeIfAbsent(radius, r -> {
                PreComputedMineBomb bomb = new PreComputedMineBomb(r);
                bomb.setUseParticles(false);
                return bomb;
            });

            boolean useFortune = pickaxe.getEnchantmentLevel(FortuneEnchant.class) > 0 &&
                    !pickaxe.isDisabled(FortuneEnchant.class);
            boolean useOreSplitter = pickaxe.getEnchantmentLevel(OreSplitterEnchant.class) > 0 &&
                    !pickaxe.isDisabled(OreSplitterEnchant.class);

            final int fortune = (1 +
                    (useFortune ? pickaxe().getEnchantmentLevel(FortuneEnchant.class) : 0)
            ) * (useOreSplitter ? 2 : 1);

            BackpackManager.addToBackpacks(player, mineBomb.explode(mine, blockLocation)
                    .entrySet()
                    .stream()
                    .collect(HashMap::new, (map, entry) -> map.put(MineBlock.fromMaterial(entry.getKey()), entry.getValue() * fortune), HashMap::putAll)
            );

            player().getWorld().spawnParticle(Particle.EXPLOSION_HUGE, blockLocation, 3, 2, 2, 2);
            mine.removeBlocks(mineBomb.blocksChanged);
            pickaxe().addBlocksBroken(mineBomb.blocksChanged);
            pickaxe().addXp(mineBomb.blocksChanged * 2);
        }


        @Override
        public <T extends Projectile> @NotNull T launchProjectile(@NotNull Class<? extends T> projectile) {
            return null;
        }

        @Override
        public <T extends Projectile> @NotNull T launchProjectile(@NotNull Class<? extends T> projectile, @Nullable Vector velocity) {
            return null;
        }

        @Override
        public <T extends Projectile> @NotNull T launchProjectile(@NotNull Class<? extends T> aClass, @Nullable Vector vector, @Nullable Consumer<T> consumer) {
            return null;
        }
    }
}
