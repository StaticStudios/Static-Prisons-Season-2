package net.staticstudios.prisons.commands.test;

import net.staticstudios.prisons.cells.Cell;
import net.staticstudios.prisons.crates.Crate;
import net.staticstudios.prisons.crates.CrateReward;
import net.staticstudios.prisons.crates.Crates;
import net.staticstudios.prisons.customItems.pickaxes.PickaxeTemplates;
import net.staticstudios.prisons.utils.PrisonUtils;
import net.staticstudios.utils.WeightedElements;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class TestCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        Player player = null;
        if (commandSender instanceof Player) {
            player = (Player) commandSender;
        }
        assert player != null;
        //AuctionHouseMenus.openMenu(player, 0);
        player.sendMessage(
                new WeightedElements<String>()
                        .add("10%", 10)
                        .add("20%", 20)
                        .add("20% again", 20)
                        .add("50%", 50)
                        .getRandom()
                );
//        long start = System.currentTimeMillis();
//        for (int x = 0; x < 100; x++) {
//            for (int i = 0; i < 10000; i++) {
//                player.sendBlockChange(player.getLocation(), Bukkit.createBlockData(Material.STONE));
//            }
//        }
//        player.sendMessage("Total time taken: " + (System.currentTimeMillis() - start) + "ms");
        //Crates.COMMON.preview(player);
        Cell.createCell(player);
        return false;
    }
}
