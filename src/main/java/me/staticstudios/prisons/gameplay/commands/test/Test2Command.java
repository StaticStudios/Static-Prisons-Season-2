package me.staticstudios.prisons.gameplay.commands.test;

import com.jeff_media.customblockdata.CustomBlockData;
import me.staticstudios.prisons.Main;
import me.staticstudios.prisons.utils.Utils;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Test2Command implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        //Player player = (Player) commandSender;
        List<String> linesToWrite = new ArrayList<>();
        for (String line : Utils.getAllLinesInAFile("./data/tmp/tebexPurchases.txt")) linesToWrite.add(line.split(" \\| ")[1] + " | " + "_ tebex " + line.split(" \\| ")[2] + " {playerName} --l");
        Utils.writeToAFile("./data/season2PostResetTebex.txt", linesToWrite, false);
        return false;
    }
}
