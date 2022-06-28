package net.staticstudios.prisons.commands.test;

import net.staticstudios.prisons.chat.events.EventManager;
import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.gui.newGui.SettingsMenus;
import net.staticstudios.prisons.privateMines.PrivateMine;
import net.staticstudios.utils.WeightedElements;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TestCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        Player player = null;
        if (commandSender instanceof Player) {
            player = (Player) commandSender;
        }
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
        //Cell.createCell(player);
       PrivateMine.getPrivateMineFromPlayer(player).thenAccept(pm -> pm.setXp(Integer.parseInt(args[0]), true));
       //for (int i = 0; i < 150; i++) player.sendMessage(PrisonUtils.prettyNum(PrivateMine.getLevelRequirement(i)) + " | Level: " + i);
        //SettingsMenus.open(player, true);
//        Player finalPlayer = player;
//        TickUtils.init(StaticPrisons.getInstance());
//        Bukkit.getScheduler().runTaskTimer(StaticPrisons.getInstance(), () -> {
//            finalPlayer.sendMessage(TickUtils.getMSPT(20) + "ms | " + TickUtils.getTPS() + "tps");
//        }, 0, 1);
        PlayerData playerData = new PlayerData(player);
//        long start = System.currentTimeMillis();
//        for (int i = 0; i < 10000; i++) {
//            playerData.setChatTag1("dev");
//        }
//        player.sendMessage("Total time taken: " + (System.currentTimeMillis() - start) + "ms");
        //EventManager.runWordUnscramble();
        return false;
    }



    static void test() {
        int i = 0;
        while (i < 10) {
            i++;
        }

        for (int x = 0; x < 10; x++) {

        }
    }

}
