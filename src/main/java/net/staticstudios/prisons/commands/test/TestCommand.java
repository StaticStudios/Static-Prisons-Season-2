package net.staticstudios.prisons.commands.test;

import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.data.serverData.ServerData;
import net.staticstudios.prisons.pickaxe.PrisonPickaxe;
import net.staticstudios.prisons.privateMines.PrivateMine;
import net.staticstudios.prisons.trading.Trade;
import net.staticstudios.prisons.utils.PrisonUtils;
import net.staticstudios.utils.WeightedElements;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.UUID;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.function.Supplier;

public class TestCommand implements CommandExecutor {

    //noah was here
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        Player player = null;
        if (commandSender instanceof Player) {
            player = (Player) commandSender;
        }
        //AuctionHouseMenus.openMenu(player, 0);
//        player.sendMessage(
//                new WeightedElements<String>()
//                        .add("10%", 10)
//                        .add("20%", 20)
//                        .add("20% again", 20)
//                        .add("50%", 50)
//                        .getRandom()
//        );
//        long start = System.currentTimeMillis();
//        for (int x = 0; x < 100; x++) {
//            for (int i = 0; i < 10000; i++) {
//                player.sendBlockChange(player.getLocation(), Bukkit.createBlockData(Material.STONE));
//            }
//        }
//        player.sendMessage("Total time taken: " + (System.currentTimeMillis() - start) + "ms");
        //Crates.COMMON.preview(player);
        //Cell.createCell(player);
//        PrivateMine.getPrivateMineFromPlayer(player).thenAccept(pm -> pm.setXp(Integer.parseInt(args[0]), true));
//        for (int i = 0; i < 100; i++)
//            player.sendMessage(PrisonUtils.prettyNum(PrivateMine.getLevelRequirement(i)) + " | Level: " + i);
        //SettingsMenus.open(player, true);
//        Player finalPlayer = player;
//        TickUtils.init(StaticPrisons.getInstance());
//        Bukkit.getScheduler().runTaskTimer(StaticPrisons.getInstance(), () -> {
//            finalPlayer.sendMessage(TickUtils.getMSPT(20) + "ms | " + TickUtils.getTPS() + "tps");
//        }, 0, 1);
//        PlayerData playerData = new PlayerData(player);
//        long start = System.currentTimeMillis();
//        for (int i = 0; i < 10000; i++) {
//            playerData.setChatTag1("dev");
//        }
//        player.sendMessage("Total time taken: " + (System.currentTimeMillis() - start) + "ms");
        //EventManager.runWordUnscramble();
//        player.spigot().send(TitlePart.TITLE, "");
//        player.showTitle(Title.title(Component.text("&a&lHello"), Component.text("&b&lWorld"), Title.Times.of(Duration.ofMillis(500), Duration.ofMillis(2000), Duration.ofMillis(500))));
//        ChatEvents.runEvent(ChatEvents.EventType.MATH);
//

//        Trade.test(player); //todo test

//        FileConfiguration current = YamlConfiguration.loadConfiguration(new File("current.yml"));
//        FileConfiguration recent = YamlConfiguration.loadConfiguration(new File("recent.yml"));
//
//        for (String k : recent.getKeys(false)) {
//            if (!k.endsWith("-votes")) continue;
//            if (!k.startsWith("PLAYERS")) continue;
//            current.set(k, recent.get(k));
//        }
//        try {
//            current.save(new File("current.yml"));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

//        Bukkit.getScheduler().getActiveWorkers();

//        System.out.println("test");
//        for (int i = 0; i < 1000; i++) {
//            PrisonPickaxe p = PrisonPickaxe.fromItem(PrisonUtils.createNewPickaxe());
//            p.setXp(1);
//            p.setBlocksBroken(1);
//            PrisonUtils.Players.addToInventory(player, p.item);
//        }
        //I added a thing!
//        for (int i = Integer.parseInt(args[0]) - 50; i < Integer.parseInt(args[0]); i++) {
//            player.sendMessage(PrisonUtils.addCommasToNumber(PrivateMine.getLevelRequirement(i)) + " | Level: " + i);
//        }
        return false;





    }


}
