package net.staticstudios.prisons.trading;

import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.trading.domain.Trade;
import net.staticstudios.prisons.trading.logging.TradeAction;
import net.staticstudios.prisons.trading.logging.TradeActionLogger;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class TradeLogger {

    private final String id;

    private final File logFile;
    private final Logger logger;

    private final Executor executor = Executors.newSingleThreadExecutor();

    private final TradeActionLogger actionLogger = new TradeActionLogger(this);

    private boolean finished = false;

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

                    Files.writeString(logFile.toPath(), "[Trade ID] " + tradeId + "\n\n\n", StandardOpenOption.APPEND);
                } catch (IOException e) {
                    logger.error("Could not create log file for trade " + tradeId);
                }
            }
        });
    }

    private void log(String message) {
        executor.execute(() -> {
            try {
                Files.writeString(logFile.toPath(), message, StandardOpenOption.APPEND);
            } catch (IOException e) {
                logger.error("Could not write to log file for trade " + id);
            }
        });
    }

    public void start(Trade trade) {
        log(actionLogger.startTrade(trade));
    }

    public void removeItem(Player player, ItemStack itemStack) {
        log(actionLogger.removeItem(player, itemStack));
    }

    public void addItem(Player player, ItemStack itemStack) {
        log(actionLogger.addItem(player, itemStack));
    }

    public void confirm(Player player) {
        log(actionLogger.playerLog(TradeAction.CONFIRM, player));
    }

    public void retract(Player player) {
        log(actionLogger.playerLog(TradeAction.UN_CONFIRM, player));
    }


    public void completed() {
        log(actionLogger.actionLog(TradeAction.COMPLETE));
        finish();
    }

    public void cancelled(Player player) {
        log(actionLogger.playerLog(TradeAction.CANCEL, player));
        finish();
    }

    public void finish() {

        log(actionLogger.actionLog(TradeAction.FINISH));

        executor.execute(() -> {
            if (finished) {
                logger.error("Trade with id " + id + " has been completed more than twice. Please check the log file for more information.");
                return;
            }
            finished = true;


            File zipFile = new File(StaticPrisons.getInstance().getDataFolder(), "data/tradeLogs/" + id + ".zip");
            try {
                zipFile.createNewFile();
            } catch (IOException e) {
                logger.error("Could not zip log file for trade " + id);
                e.printStackTrace();
                return;
            }

            try (
                    ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFile.getPath()));
                    FileInputStream fis = new FileInputStream(logFile);
            ) {
                ZipEntry zipEntry = new ZipEntry(logFile.getName());
                zos.putNextEntry(zipEntry);

                byte[] buffer = new byte[1024];
                int len;
                while ((len = fis.read(buffer)) > 0) {
                    zos.write(buffer, 0, len);
                }
                zos.closeEntry();
            } catch (IOException e) {
                logger.error("Could not zip log file for trade " + id);
                e.printStackTrace();
                return;
            }


            logFile.delete();

        });
    }
}
