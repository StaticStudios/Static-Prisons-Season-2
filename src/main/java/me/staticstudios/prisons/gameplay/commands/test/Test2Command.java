package me.staticstudios.prisons.gameplay.commands.test;

import com.jeff_media.customblockdata.CustomBlockData;
import me.staticstudios.prisons.Main;
import me.staticstudios.prisons.core.data.sql.MySQLConnection;
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

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Test2Command implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        //Player player = (Player) commandSender;
        for (String line : Utils.getAllLinesInAFile("./data/tmp/discordToUUID.txt")) {
            try (Statement stmt = MySQLConnection.getConnection().createStatement()) {
                stmt.executeUpdate("INSERT INTO `linkedAccounts` (`id`, `accountUUID`, `discordID`) VALUES (NULL, '" + line.split(": ")[1].split(" \\| ")[0] + "', '" + line.split(": ")[0] + "')");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
