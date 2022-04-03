package me.staticstudios.prisons.commands.test;

import me.staticstudios.prisons.data.sql.MySQLConnection;
import me.staticstudios.prisons.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.sql.Statement;

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
