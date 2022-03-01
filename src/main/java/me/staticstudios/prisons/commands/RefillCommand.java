package me.staticstudios.prisons.commands;

import me.staticstudios.prisons.gui.GUI;
import me.staticstudios.prisons.mines.MineManager;
import me.staticstudios.prisons.utils.CommandUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RefillCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        if (args.length == 0) {
            player.sendMessage(CommandUtils.getIncorrectCommandUsageMessage("/refill <mine>"));
            return false;
        }
        switch (args[0].toLowerCase()) {
            case "a" -> MineManager.allMines.get("publicMine-A").refill();
            case "b" -> MineManager.allMines.get("publicMine-B").refill();
            case "c" -> MineManager.allMines.get("publicMine-C").refill();
            case "d" -> MineManager.allMines.get("publicMine-D").refill();
            case "e" -> MineManager.allMines.get("publicMine-E").refill();
            case "f" -> MineManager.allMines.get("publicMine-F").refill();
            case "g" -> MineManager.allMines.get("publicMine-G").refill();
            case "h" -> MineManager.allMines.get("publicMine-H").refill();
            case "i" -> MineManager.allMines.get("publicMine-I").refill();
            case "j" -> MineManager.allMines.get("publicMine-J").refill();
            case "k" -> MineManager.allMines.get("publicMine-K").refill();
            case "l" -> MineManager.allMines.get("publicMine-L").refill();
            case "m" -> MineManager.allMines.get("publicMine-M").refill();
            case "n" -> MineManager.allMines.get("publicMine-N").refill();
            case "o" -> MineManager.allMines.get("publicMine-O").refill();
            case "p" -> MineManager.allMines.get("publicMine-P").refill();
            case "q" -> MineManager.allMines.get("publicMine-Q").refill();
            case "r" -> MineManager.allMines.get("publicMine-R").refill();
            case "s" -> MineManager.allMines.get("publicMine-S").refill();
            case "t" -> MineManager.allMines.get("publicMine-T").refill();
            case "u" -> MineManager.allMines.get("publicMine-U").refill();
            case "v" -> MineManager.allMines.get("publicMine-V").refill();
            case "w" -> MineManager.allMines.get("publicMine-W").refill();
            case "x" -> MineManager.allMines.get("publicMine-X").refill();
            case "y" -> MineManager.allMines.get("publicMine-Y").refill();
            case "z" -> MineManager.allMines.get("publicMine-Z").refill();
            case "p1" -> MineManager.allMines.get("prestigeMine-1").refill();
            case "p2" -> MineManager.allMines.get("prestigeMine-2").refill();
            case "p3" -> MineManager.allMines.get("prestigeMine-3").refill();
            case "p4" -> MineManager.allMines.get("prestigeMine-4").refill();
            case "p5" -> MineManager.allMines.get("prestigeMine-5").refill();
            case "p6" -> MineManager.allMines.get("prestigeMine-6").refill();
            case "p7" -> MineManager.allMines.get("prestigeMine-7").refill();
            case "p8" -> MineManager.allMines.get("prestigeMine-8").refill();
            case "p9" -> MineManager.allMines.get("prestigeMine-9").refill();
            case "p10" -> MineManager.allMines.get("prestigeMine-10").refill();
            case "p11" -> MineManager.allMines.get("prestigeMine-11").refill();
            case "p12" -> MineManager.allMines.get("prestigeMine-12").refill();
            case "p13" -> MineManager.allMines.get("prestigeMine-13").refill();
            case "p14" -> MineManager.allMines.get("prestigeMine-14").refill();
            case "p15" -> MineManager.allMines.get("prestigeMine-15").refill();
            case "r1" -> MineManager.allMines.get("rankMine-1").refill();
            case "r2" -> MineManager.allMines.get("rankMine-2").refill();
            case "r3" -> MineManager.allMines.get("rankMine-3").refill();
            case "r4" -> MineManager.allMines.get("rankMine-4").refill();
            case "r5" -> MineManager.allMines.get("rankMine-5").refill();
            case "event" -> MineManager.allMines.get("eventMine").refill();
            default -> player.sendMessage(CommandUtils.getIncorrectCommandUsageMessage("/refill <mine>"));
        }
        return false;
    }
}
