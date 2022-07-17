package net.staticstudios.prisons.pickaxe.enchants;

import com.sk89q.worldedit.math.BlockVector3;
import net.staticstudios.mines.StaticMine;
import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.blockBroken.BlockBreakListener;
import net.staticstudios.prisons.data.Prices;
import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.pickaxe.enchants.handler.BaseEnchant;
import net.staticstudios.prisons.pickaxe.enchants.handler.PrisonEnchants;
import net.staticstudios.prisons.pickaxe.PrisonPickaxe;
import net.staticstudios.prisons.customItems.mineBombs.MineBomb;
import net.staticstudios.prisons.privateMines.PrivateMine;
import net.staticstudios.prisons.utils.Constants;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class EggShooterEnchant extends BaseEnchant {
    public EggShooterEnchant() {
        super("eggShooter", "&6&lEgg Shooter", 1000, BigInteger.valueOf(500), "&7Shoot explosive eggs while right-clicking");
        setPickaxeLevelRequirement(50);
        setPlayerLevelRequirement(10);
    }
    public void whileRightClicking(Player player, PrisonPickaxe pickaxe) {
        if (!player.getWorld().equals(Constants.MINES_WORLD) && !player.getWorld().equals(PrivateMine.PRIVATE_MINES_WORLD)) return;
        Egg egg = player.getWorld().spawn(player.getEyeLocation(), Egg.class);
        egg.setShooter(new EggShooterPickaxe(player, pickaxe));
        egg.setVelocity(player.getLocation().getDirection().multiply(1));
    }

    public static void eggHit(ProjectileHitEvent e, EggShooterPickaxe eggShooterPickaxe) {
        Player player = eggShooterPickaxe.player;
        if (e.getHitEntity() != null) if (e.getHitEntity().equals(player)) {
            e.setCancelled(true);
            return;
        }
        if (e.getHitBlock() == null) return;
        PrisonPickaxe pickaxe = eggShooterPickaxe.pickaxe;
        StaticMine mine = null;
        Location loc = e.getHitBlock().getLocation();
        for (StaticMine m : StaticMine.getAllMines()) {  //todo: optimize this, do the same optimization as the one in net.staticstudios.mines.StaticMines.java | make one method for both since they do the same thing? StaticMine#getMineAt(Location)
            if (!loc.getWorld().equals(m.getWorld())) continue;
            BlockVector3 minPoint = m.getMinVector();
            BlockVector3 maxPoint = m.getMaxVector();
            if (minPoint.getBlockX() <= loc.getBlockX() && minPoint.getBlockZ() <= loc.getBlockZ() && maxPoint.getBlockX() >= loc.getBlockX() && maxPoint.getBlockZ() >= loc.getBlockZ()) {
                mine = m;
                break;
            }
        }
        if (mine == null) return;
        MineBomb bomb = new MineBomb(e.getHitBlock().getLocation(), (double) pickaxe.getEnchantLevel(PrisonEnchants.EGG_SHOOTER) / (PrisonEnchants.EGG_SHOOTER.MAX_LEVEL / 5) + 1);
        StaticMine finalMine = mine;
        Bukkit.getScheduler().runTaskAsynchronously(StaticPrisons.getInstance(), () -> {
            Map<Material, BigInteger> blocksBroken = bomb.explode(finalMine, 10);
            Bukkit.getScheduler().runTask(StaticPrisons.getInstance(), () -> {
                finalMine.removeBlocksBrokenInMine(bomb.blocksChanged);
                pickaxe.addBlocksBroken(bomb.blocksChanged);
                pickaxe.addXp(bomb.blocksChanged * 2);
                int fortune = pickaxe.getEnchantLevel(PrisonEnchants.FORTUNE);
                if (PrisonUtils.randomInt(0, PrisonEnchants.DOUBLE_FORTUNE.MAX_LEVEL) < pickaxe.getEnchantLevel(PrisonEnchants.DOUBLE_FORTUNE)) fortune *= 2;
                PlayerData playerData = new PlayerData(player);
                boolean backpackWasFull = playerData.getBackpackIsFull();
                BigDecimal multiplier = BigDecimal.ONE;
                PrivateMine privateMine = PrivateMine.MINE_ID_TO_PRIVATE_MINE.get(finalMine.getID());
                if (privateMine != null) multiplier = BigDecimal.valueOf(privateMine.sellPercentage);
                if (!backpackWasFull) {
                    Map<BigDecimal, BigInteger> map = new HashMap<>();
                    for (Map.Entry<Material, BigInteger> entry: blocksBroken.entrySet()) map.put(Prices.getSellPriceOf(entry.getKey()).multiply(multiplier), entry.getValue().multiply(BigInteger.valueOf(fortune)));
                    playerData.addAllToBackpack(map);
                }
                //for (Material key : blocksBroken.keySet()) playerData.addAllToBackpack(key, blocksBroken.get(key).multiply(BigInteger.valueOf(fortune)));
                BlockBreakListener.backpackFullCheck(backpackWasFull, player, playerData);
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
