package net.staticstudios.prisons.pickaxe.abilities;

import net.staticstudios.mines.StaticMine;
import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.blockBroken.BlockBreak;
import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.mineBombs.MineBomb;
import net.staticstudios.prisons.mines.MineBlock;
import net.staticstudios.prisons.pickaxe.PrisonPickaxe;
import net.staticstudios.prisons.pickaxe.abilities.handler.BaseAbility;
import net.staticstudios.prisons.pickaxe.enchants.EggShooterEnchant;
import net.staticstudios.prisons.pickaxe.enchants.handler.PickaxeEnchants;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class SnowFallAbility extends BaseAbility {

    private static final MineBomb snowFallMineBomb = new MineBomb(5);

    private static boolean listenerRegistered = false;

    public SnowFallAbility() {
        super("snowFall", "&f&lSnow Fall", 10, BigInteger.ZERO, 1000 * 60 * 120,
                "&oCarpet bomb a mine with explosive snowballs!",
                "",
                "&aEach upgrade will increase the amount of snowballs dropped!",
                "",
                "Cooldown: &c" + PrisonUtils.formatTime(1000 * 60 * 120));
        requiresMineOnActivate = true;
        snowFallMineBomb.computePositions();
        if (!listenerRegistered) {
            StaticPrisons.getInstance().getServer().getPluginManager().registerEvents(new Listener(), StaticPrisons.getInstance());
            listenerRegistered = true;
        }
    }

    public void onTick(int tick, Player player, PrisonPickaxe pickaxe, StaticMine mine) {
        if (tick % 40 != 0) return; //Only run every 2 seconds
        SnowFallPickaxe source = new SnowFallPickaxe(player, pickaxe, mine);
        for (int x = mine.getMinVector().getBlockX(); x < mine.getMaxVector().getBlockX(); x++) {
            for (int z = mine.getMinVector().getBlockZ(); z < mine.getMaxVector().getBlockZ(); z++) {
                if (PrisonUtils.randomInt(1, 50) != 1) continue; //2% chance to spawn snowball
                Location loc = new Location(mine.getWorld(), x, mine.getMaxVector().getBlockY() + 50, z);
                Snowball snowball = player.getWorld().spawn(loc, Snowball.class);
                snowball.setShooter(source);
            }
        }
    }

    @Override
    public int getTimesToTick(int level) {
        return 20 * 2 + level * 20 * 2; //Default 4 seconds + 2 seconds per level
    }

    public static class SnowFallPickaxe implements ProjectileSource {

        public Player player;
        public PrisonPickaxe pickaxe;
        public StaticMine mine;

        public SnowFallPickaxe(Player player, PrisonPickaxe pickaxe, StaticMine mine) {
            this.player = player;
            this.pickaxe = pickaxe;
            this.mine = mine;
        }

        public static void onHit(ProjectileHitEvent e, SnowFallPickaxe snowFallPickaxe) {
            PlayerData playerData = new PlayerData(snowFallPickaxe.player);
            Map<Material, Long> blocksBroken = snowFallMineBomb.explodeAtComputedPositions(snowFallPickaxe.mine, e.getEntity().getLocation());
            boolean backpackWasFull = playerData.getBackpackIsFull();
            if (!backpackWasFull) {
                Map<MineBlock, Long> map = new HashMap<>();
                for (Map.Entry<Material, Long> entry : blocksBroken.entrySet()) {
                    map.put(MineBlock.fromMaterial(entry.getKey()), entry.getValue() * snowFallPickaxe.pickaxe.getEnchantLevel(PickaxeEnchants.FORTUNE));
                }
                playerData.addAllToBackpack(map);
            }
            PrisonUtils.Players.backpackFullCheck(backpackWasFull, snowFallPickaxe.player, playerData);
            snowFallPickaxe.mine.removeBlocksBrokenInMine(snowFallMineBomb.blocksChanged);
            e.getEntity().remove();
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

    public static class Listener implements org.bukkit.event.Listener {
        //todo

        @EventHandler
        void onProjectileHit(ProjectileHitEvent e) {
            if (e.getEntity().getShooter() instanceof SnowFallPickaxe) {
                SnowFallPickaxe.onHit(e, (SnowFallPickaxe) e.getEntity().getShooter());
            }
        }
    }
}
