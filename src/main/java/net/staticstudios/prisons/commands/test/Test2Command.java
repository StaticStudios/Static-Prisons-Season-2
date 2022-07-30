package net.staticstudios.prisons.commands.test;

import net.staticstudios.prisons.blockBroken.BlockBreak;
import net.staticstudios.prisons.data.backups.DataBackup;
import net.staticstudios.prisons.gambling.GamblingMenus;
import net.staticstudios.prisons.privateMines.PrivateMine;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Test2Command implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        Player player = (Player) commandSender;
        //Crates.COMMON.open(player);
//        Cell cell = Cell.getCell(CellManager.playersToCell.get(player.getUniqueId()));
//        CellManager.cells.remove(cell.cellUuid);
//        CellManager.playersToCell.remove(player.getUniqueId());
        //Bukkit.unloadWorld("private_mines", false);
//        PrivateMine.createPrivateMine(player).thenRun(() -> {
//            PrivateMine privateMine = PrivateMine.getPrivateMineFromPlayer(player);
//            privateMine.registerMine();
//            privateMine.warpTo(player);
//        });
//        PrivateMine privateMine = PrivateMine.getPrivateMineFromPlayerWithoutLoading(player);
//        privateMine.setXpAndCalcLevel(Integer.parseInt(strings[0]));
//        ChatEvents.runEvent(ChatEvents.EventType.MATH);
//        RankUpMenus.open(player, true);
//        GamblingMenus.openMain(player);
//        DataBackup.init();

        return false;
    }
}
