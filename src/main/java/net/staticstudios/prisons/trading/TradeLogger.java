package net.staticstudios.prisons.trading;

import net.staticstudios.prisons.StaticPrisons;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

public class TradeLogger {

    private final String id;

    private final File logFile;
    private final Logger logger;

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public TradeLogger(String tradeId) {
        this.id = tradeId;
        logger = LoggerFactory.getLogger("Logger for trade " + tradeId);
        logFile = new File(StaticPrisons.getInstance().getDataFolder(), "data/tradeLogs/"+ tradeId + ".log");
        if (!Files.exists(logFile.toPath())) {
            try {
                logFile.getParentFile().mkdirs();
                logFile.createNewFile();

                Files.writeString(logFile.toPath(), "Trade history of trade with id " + tradeId + " started.\n", StandardOpenOption.APPEND);
            } catch (IOException e) {
                logger.error("Could not create log file for trade " + tradeId);
            }
        }
    }

    public void removed(Player player, ItemStack itemStack) {
        try {
            Files.writeString(logFile.toPath(), player.getName() + " removed " + itemStack.getAmount() + " " + itemStack.getType().name() + " from trade\n", StandardOpenOption.APPEND);
        } catch (IOException e) {
            logger.error("Could not write to log file for trade " + logFile.getName());
            logger.error(player.getName() + " removed " + itemStack.getAmount() + " " + itemStack.getType().name() + " from trade");
        }
    }

    public void added(Player player, ItemStack itemStack) {
        try {
            Files.writeString(logFile.toPath(), player.getName() + " added " + itemStack.getAmount() + " " + itemStack.getType().name() + " to trade\n", StandardOpenOption.APPEND);
        } catch (IOException e) {
            logger.error("Could not write to log file for trade " + logFile.getName());
            logger.error(player.getName() + " added " + itemStack.getAmount() + " " + itemStack.getType().name() + " to trade");
        }
    }

    public void accepted(Player player) {
        try {
            Files.writeString(logFile.toPath(), player.getName() + " accepted the trade\n", StandardOpenOption.APPEND);
        } catch (IOException e) {
            logger.error("Could not write to log file for trade " + logFile.getName());
            logger.error(player.getName() + " accepted the trade");
        }
    }

    public void retract(Player player) {
        try {
            Files.writeString(logFile.toPath(), player.getName() + " retracted the trade\n", StandardOpenOption.APPEND);
        } catch (IOException e) {
            logger.error("Could not write to log file for trade " + logFile.getName());
            logger.error(player.getName() + " retracted the trade");
        }
    }


    public void completed() {
        try {
            Files.writeString(logFile.toPath(), "Trade with ID " + id + " completed\n", StandardOpenOption.APPEND);
        } catch (IOException e) {
            logger.error("Could not write to log file for trade " + logFile.getName());
            logger.error("Trade completed");
        }
    }

    public void cancelled(Player player) {
        try {
            Files.writeString(logFile.toPath(), player.getName() + " cancelled the trade with id: " + id + "\n", StandardOpenOption.APPEND);
        } catch (IOException e) {
            logger.error("Could not write to log file for trade " + logFile.getName());
            logger.error(player.getName() + " cancelled the trade with id: " + id);
        }
    }
}
