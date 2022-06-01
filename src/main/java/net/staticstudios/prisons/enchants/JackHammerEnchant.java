package net.staticstudios.prisons.enchants;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.world.block.BlockTypes;
import net.staticstudios.mines.StaticMine;
import net.staticstudios.prisons.blockBroken.PrisonBlockBroken;
import net.staticstudios.prisons.enchants.handler.BaseEnchant;
import net.staticstudios.prisons.enchants.handler.PrisonEnchants;
import net.staticstudios.prisons.enchants.handler.PrisonPickaxe;
import net.staticstudios.prisons.utils.Constants;
import net.staticstudios.prisons.utils.Utils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class JackHammerEnchant extends BaseEnchant {
    public JackHammerEnchant() {
        super("jackHammer", "&8&lJack Hammer", 20000, BigInteger.valueOf(250), "&7Chance to destroy a layer of a mine");
    }


    @Override
    public void onBlockBreak(PrisonBlockBroken bb) {
        if (Utils.randomInt(1, 75) != 1) return;
        int jackHammerLevel = bb.pickaxe.getEnchantLevel(ENCHANT_ID);
        int doubleWammyLevel = bb.pickaxe.getEnchantLevel(PrisonEnchants.DOUBLE_JACK_HAMMER);
        if (Utils.randomInt(1, MAX_LEVEL + MAX_LEVEL / 10) <= jackHammerLevel + MAX_LEVEL / 10) {
            int howDeepToGo = 1;
            if (doubleWammyLevel > 0) if (Utils.randomInt(1, PrisonEnchants.DOUBLE_JACK_HAMMER.MAX_LEVEL + PrisonEnchants.DOUBLE_JACK_HAMMER.MAX_LEVEL / 10) <= doubleWammyLevel + PrisonEnchants.DOUBLE_JACK_HAMMER.MAX_LEVEL / 10) howDeepToGo += 1;
            BreakLayer bl = new BreakLayer(bb.mine);
            bb.blockTypesBroken.putAll(bl.destroyLayer(bb.blockLocation.getBlockY(), howDeepToGo));
            bb.blocksBroken += bl.totalBlocksBroken;
        }
    }

    @Override
    public void onPickaxeHeld(Player player, PrisonPickaxe pickaxe) {

    }

    @Override
    public void onPickaxeUnHeld(Player player, PrisonPickaxe pickaxe) {

    }

    @Override
    public void whileRightClicking(PlayerInteractEvent e, PrisonPickaxe pickaxe) {

    }
    static class BreakLayer {
        private final StaticMine mine;
        public long totalBlocksBroken = 0;
        public BreakLayer(StaticMine mine) {
            this.mine = mine;
        }

        public Map<Material, BigInteger> destroyLayer(int yLevel, int howDeepToGo) {
            Map<Material, BigInteger> blocksBroken = new HashMap<>();
            for (int y = Math.max(1, yLevel - howDeepToGo + 1); y <= yLevel; y++) {
                for (int x = mine.getMinVector().getBlockX(); x <= mine.getMaxVector().getBlockX(); x++) {
                    for (int z = mine.getMinVector().getBlockX(); z <= mine.getMaxVector().getBlockZ(); z++) {
                        Material mat = new Location(Constants.MINES_WORLD, x, y, z).getBlock().getType();
                        if (!mat.equals(Material.AIR)) {
                            totalBlocksBroken++;
                            if (!blocksBroken.containsKey(mat)) {
                                blocksBroken.put(mat, BigInteger.ONE);
                            } else blocksBroken.put(mat, blocksBroken.get(mat).add(BigInteger.ONE));
                        }
                    }
                }
            }
            Region region = new CuboidRegion(mine.getWEWorld(), BlockVector3.at(mine.getMinVector().getBlockX(), yLevel, mine.getMinVector().getBlockZ()), BlockVector3.at(mine.getMaxVector().getBlockX(), Math.max(1, yLevel - howDeepToGo + 1), mine.getMaxVector().getBlockZ()));
            EditSession editSession = WorldEdit.getInstance().newEditSession(region.getWorld());
            editSession.setBlocks(region, BlockTypes.AIR);
            editSession.close();
            /*
            if (!mine.brokeBlocksInMine(totalBlocksBroken)) {
                //Bukkit.getScheduler().runTaskAsynchronously(Main.getMain(), () -> {
                Region region = new CuboidRegion(BukkitAdapter.adapt(mine.minLocation.getWorld()), BlockVector3.at(mine.minLocation.getX(), yLevel, mine.minLocation.getZ()), BlockVector3.at(mine.maxLocation.getX(), Math.max(1, yLevel - howDeepToGo + 1), mine.maxLocation.getZ()));
                EditSession editSession = WorldEdit.getInstance().newEditSession(region.getWorld());
                editSession.setBlocks(region, BlockTypes.AIR);
                editSession.close();
                //});
            }

             */

            return blocksBroken;
        }
    }
}


