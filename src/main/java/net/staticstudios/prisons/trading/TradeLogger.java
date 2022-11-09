package net.staticstudios.prisons.trading;

import net.staticstudios.prisons.StaticPrisons;
import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class TradeLogger {

    private final String id;

    private final File logFile;
    private final Logger logger;

    private final Executor executor = Executors.newSingleThreadExecutor();

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public TradeLogger(String tradeId) {
        this.id = tradeId;
        logger = LoggerFactory.getLogger("Logger for trade " + tradeId);
        logFile = new File(StaticPrisons.getInstance().getDataFolder(), "data/tradeLogs/" + tradeId + ".log");

        executor.execute(() -> {
            if (!Files.exists(logFile.toPath())) {
                try {
                    logFile.getParentFile().mkdirs();
                    logFile.createNewFile();

                    Files.writeString(logFile.toPath(), "Trade history of trade with id " + tradeId + " started.\n", StandardOpenOption.APPEND);
                } catch (IOException e) {
                    logger.error("Could not create log file for trade " + tradeId);
                }
            }
        });
    }

    public void removed(Player player, ItemStack itemStack) {
        String playerName = player.getName();
        String itemName = itemStack.getType().name();
        int amount = itemStack.getAmount();

        executor.execute(() -> {
            try {
                Files.writeString(logFile.toPath(), playerName + " removed " + amount + " " + itemName + " from trade\n", StandardOpenOption.APPEND);
            } catch (IOException e) {
                logger.error("Could not write to log file for trade " + logFile.getName());
                logger.error(playerName + " removed " + amount + " " + itemName + " from trade");
            }
        });
    }

    public void added(Player player, ItemStack itemStack) {
        String playerName = player.getName();
        String itemName = itemStack.getType().name();
        int amount = itemStack.getAmount();

        executor.execute(() -> {
            try {
                Files.writeString(logFile.toPath(), playerName + " added " + amount + " " + itemName + " to trade\n", StandardOpenOption.APPEND);
            } catch (IOException e) {
                logger.error("Could not write to log file for trade " + logFile.getName());
                logger.error(playerName + " added " + amount + " " + itemName + " to trade");
            }
        });
    }

    public void accepted(Player player) {
        String playerName = player.getName();

        executor.execute(() -> {
            try {
                Files.writeString(logFile.toPath(), playerName + " accepted trade\n", StandardOpenOption.APPEND);
            } catch (IOException e) {
                logger.error("Could not write to log file for trade " + logFile.getName());
                logger.error(playerName + " accepted trade");
            }
        });
    }

    public void retract(Player player) {
        String playerName = player.getName();

        executor.execute(() -> {
            try {
                Files.writeString(logFile.toPath(), playerName + " retracted the trade\n", StandardOpenOption.APPEND);
            } catch (IOException e) {
                logger.error("Could not write to log file for trade " + logFile.getName());
                logger.error(playerName + " retracted the trade");
            }
        });
    }


    public void completed() {
        executor.execute(() -> {
            try {
                Files.writeString(logFile.toPath(), "Trade with ID " + id + " completed\n", StandardOpenOption.APPEND);
            } catch (IOException e) {
                logger.error("Could not write to log file for trade " + logFile.getName());
                logger.error("Trade completed");
            }
            finish();
        });
    }

    public void cancelled(Player player) {
        String playerName = player.getName();

        executor.execute(() -> {
            try {
                Files.writeString(logFile.toPath(), playerName + " cancelled the trade with id: " + id + "\n", StandardOpenOption.APPEND);
            } catch (IOException e) {
                logger.error("Could not write to log file for trade " + logFile.getName());
                logger.error(playerName + " cancelled trade");
            }
        });
    }

    public void finish() {
        try {
            if (StringUtils.countMatches(Files.readString(logFile.toPath()), "completed") > 2) {
                logger.error("Trade with id " + id + " has been completed more than twice. Please check the log file for more information.");
            }
        } catch (IOException exception) {
            logger.error("Could not read log file for trade " + logFile.getName() + " - aborting finishing.");
        }
    }
}
