package net.staticstudios.prisons.pickaxe.enchants;

import net.staticstudios.mines.StaticMine;
import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.backpacks.PrisonBackpacks;
import net.staticstudios.prisons.mineBombs.MineBomb;
import net.staticstudios.prisons.mines.MineBlock;
import net.staticstudios.prisons.pickaxe.PrisonPickaxe;
import net.staticstudios.prisons.pickaxe.enchants.handler.BaseEnchant;
import net.staticstudios.prisons.pickaxe.enchants.handler.PickaxeEnchants;
import net.staticstudios.prisons.privateMines.PrivateMine;
import net.staticstudios.prisons.utils.Constants;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class EggShooterEnchant extends BaseEnchant {
    public EggShooterEnchant() {
        super("eggShooter", "&6&lEgg Shooter", 1000, BigInteger.valueOf(500), "&7Shoot explosive eggs while right-clicking");
        setPickaxeLevelRequirement(50);
    }
    public void whileRightClicking(Player player, PrisonPickaxe pickaxe) {
        if (!player.getWorld().equals(Constants.MINES_WORLD) && !player.getWorld().equals(PrivateMine.PRIVATE_MINES_WORLD)) return;
        Egg egg = player.getWorld().spawn(player.getEyeLocation(), Egg.class);
        egg.setShooter(new EggShooterPickaxe(player, pickaxe));
        egg.setVelocity(player.getLocation().getDirection().multiply(1));
    }

    public static void eggHit(ProjectileHitEvent e, EggShooterPickaxe eggShooterPickaxe) { //todo: this code is so messy, add a map of precalculated vectors to speed up the process
        Player player = eggShooterPickaxe.player;
        if (e.getHitEntity() != null) if (e.getHitEntity().equals(player)) {
            e.setCancelled(true);
            return;
        }
        if (e.getHitBlock() == null) return;
        PrisonPickaxe pickaxe = eggShooterPickaxe.pickaxe;
        StaticMine mine = StaticMine.fromLocationXZ(e.getHitBlock().getLocation());
        if (mine == null) return;
        MineBomb bomb = new MineBomb(e.getHitBlock().getLocation(), (double) pickaxe.getEnchantLevel(PickaxeEnchants.EGG_SHOOTER) / (PickaxeEnchants.EGG_SHOOTER.MAX_LEVEL / 5) + 1);
        Bukkit.getScheduler().runTaskAsynchronously(StaticPrisons.getInstance(), () -> {
            Map<Material, Long> blocksBroken = bomb.explode(mine, 10);
            Bukkit.getScheduler().runTask(StaticPrisons.getInstance(), () -> {
                mine.removeBlocksBrokenInMine(bomb.blocksChanged);
                pickaxe.addBlocksBroken(bomb.blocksChanged);
                pickaxe.addXp(bomb.blocksChanged * 2);
                int fortune = pickaxe.getEnchantLevel(PickaxeEnchants.FORTUNE);
                if (PrisonUtils.randomInt(0, PickaxeEnchants.DOUBLE_FORTUNE.MAX_LEVEL) < pickaxe.getEnchantLevel(PickaxeEnchants.DOUBLE_FORTUNE)) fortune *= 2;
//                PlayerData playerData = new PlayerData(player);
                Map<MineBlock, Long> map = new HashMap<>();
                for (Map.Entry<Material, Long> entry: blocksBroken.entrySet()) {
                    map.put(MineBlock.fromMaterial(entry.getKey()), entry.getValue() * fortune);
                }
                PrisonBackpacks.addToBackpacks(player, map);
//                boolean backpackWasFull = playerData.getBackpackIsFull();
//                if (!backpackWasFull) {
//
//                    playerData.addAllToBackpack(map);
//                }
//                PrisonUtils.Players.backpackFullCheck(backpackWasFull, player, playerData);
            });
        });
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
