package net.staticstudios.prisons.pickaxe.abilities;

import net.staticstudios.mines.StaticMine;
import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.backpacks.BackpackManager;
import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.minebombs.MultiBombMineBomb;
import net.staticstudios.prisons.mines.MineBlock;
import net.staticstudios.prisons.pickaxe.PrisonPickaxe;
import net.staticstudios.prisons.pickaxe.abilities.handler.BaseAbility;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SnowFallAbility extends BaseAbility {
//
//    private static final MultiBombMineBomb snowFallMineBomb = new MultiBombMineBomb(5);
//
//    private static boolean listenerRegistered = false;
//
//    public SnowFallAbility() {
//        super("snowFall", "&f&lSnow Fall", 10, 13, 1000 * 60 * 240,
//                "&oCarpet bomb a mine with explosive snowballs!",
//                "",
//                "&aEach upgrade will increase the amount of snowballs dropped!",
//                "",
//                "Cooldown: &c" + PrisonUtils.formatTime(1000 * 60 * 240));
//        requiresMineOnActivate = true;
//        snowFallMineBomb.computePositions();
//        if (!listenerRegistered) {
//            StaticPrisons.getInstance().getServer().getPluginManager().registerEvents(new Listener(), StaticPrisons.getInstance());
//            listenerRegistered = true;
//        }
//    }
//
//    public void onTick(int tick, Player player, PrisonPickaxe pickaxe, StaticMine mine) {
//        if (tick % 40 != 0) return; //Only run every 2 seconds
//        SnowFallPickaxe source = new SnowFallPickaxe(player, pickaxe, mine);
//        for (int x = mine.getMinPoint().getBlockX(); x < mine.getMaxPoint().getBlockX(); x++) {
//            for (int z = mine.getMinPoint().getBlockZ(); z < mine.getMaxPoint().getBlockZ(); z++) {
//                if (PrisonUtils.randomInt(1, 50) != 1) continue; //2% chance to spawn snowball
//                Location loc = new Location(mine.getBukkitWorld(), x, mine.getMaxPoint().getBlockY() + 50, z);
//                Snowball snowball = player.getWorld().spawn(loc, Snowball.class);
//                snowball.setShooter(source);
//            }
//        }
//    }
//
//    @Override
//    public int getTimesToTick(int level) {
//        return 20 * 2 + level * 20 * 2; //Default 4 seconds + 2 seconds per level
//    }
//
//    public static class SnowFallPickaxe implements ProjectileSource {
//
//        public Player player;
//        public PrisonPickaxe pickaxe;
//        public StaticMine mine;
//
//        public SnowFallPickaxe(Player player, PrisonPickaxe pickaxe, StaticMine mine) {
//            this.player = player;
//            this.pickaxe = pickaxe;
//            this.mine = mine;
//        }
//
//        boolean started = false;
//        List<Location> locs = new LinkedList<>();
//        void hit(ProjectileHitEvent e) {
//            locs.add(e.getEntity().getLocation());
//            e.getEntity().remove();
//
//
//            if (!started) {
//                Bukkit.getScheduler().runTaskLater(StaticPrisons.getInstance(), () -> {
//                    started = false;
//                    PlayerData playerData = new PlayerData(player);
//                    Map<Material, Long> blocksBroken = snowFallMineBomb.explodeAtComputedPositions(mine, locs);
//                    Map<MineBlock, Long> map = new HashMap<>();
//                    for (Map.Entry<Material, Long> entry : blocksBroken.entrySet()) {
//                        map.put(MineBlock.fromMaterial(entry.getKey()), entry.getValue() * pickaxe.getEnchantLevel(PickaxeEnchants.FORTUNE));
//                    }
//                    BackpackManager.addToBackpacks(player, map);
//                    mine.removeBlocks(snowFallMineBomb.blocksChanged);
//                    locs.clear();
//                }, 4);
//                started = true;
//            }
//
//        }
//
//        public static void onHit(ProjectileHitEvent e, SnowFallPickaxe snowFallPickaxe) {
//
//        }
//
//        @Override
//        public <T extends Projectile> @NotNull T launchProjectile(@NotNull Class<? extends T> projectile) {
//            return null;
//        }
//
//        @Override
//        public <T extends Projectile> T launchProjectile(@NotNull Class<? extends T> projectile, @Nullable Vector velocity) {
//            return null;
//        }
//    }
//
//    public static class Listener implements org.bukkit.event.Listener {
//
//        @EventHandler
//        void onProjectileHit(ProjectileHitEvent e) {
//            if (e.getEntity().getShooter() instanceof SnowFallPickaxe) {
//                ((SnowFallPickaxe) e.getEntity().getShooter()).hit(e);
//            }
//        }
//    }
}
