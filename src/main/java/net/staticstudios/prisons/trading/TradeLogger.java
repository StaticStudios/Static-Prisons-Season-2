package net.staticstudios.prisons.trading;

import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.trading.domain.Trade;
import net.staticstudios.prisons.trading.logging.TradeAction;
import net.staticstudios.prisons.trading.logging.TradeActionLogger;
import net.staticstudios.prisons.utils.StaticFileSystemManager;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class TradeLogger {

    private final String id;

    private final File logFile;
    private final Logger logger;

    private final Executor executor = Executors.newSingleThreadExecutor();
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    private final TradeActionLogger actionLogger = new TradeActionLogger(this);

    private boolean finished = false;


    @SuppressWarnings("ResultOfMethodCallIgnored")
    public TradeLogger(String tradeId) {
        this.id = tradeId;
        logger = LoggerFactory.getLogger("Trade " + tradeId);
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

        logger.info(trade.initiator().getName() + " started a trade with " + trade.trader().getName());

        try {
            Files.writeString(StaticFileSystemManager.getFileOrCreate("data/tradeLogs/trades.log").toPath(),
                    '[' + dateFormat.format(new Date()) + "] " + trade.initiator().getName() + " started a trade with " + trade.trader().getName() + " | ID: " + trade.getUuid() + '\n', StandardOpenOption.APPEND);
        } catch (IOException e) {
            logger.error("Could not write to trades.log log file");
            e.printStackTrace();
        }
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


    public void completed(Trade trade) {
        log(actionLogger.actionLog(TradeAction.COMPLETE));

        logger.info(trade.initiator().getName() + " completed a trade with " + trade.trader().getName());
        finish();


        try {
            Files.writeString(StaticFileSystemManager.getFileOrCreate("data/tradeLogs/trades.log").toPath(),
                    '[' + dateFormat.format(new Date()) + "] " + trade.initiator().getName() + " completed a trade with " + trade.trader().getName() + " | ID: " + trade.getUuid() + '\n', StandardOpenOption.APPEND);
        } catch (IOException e) {
            logger.error("Could not write to trades.log log file");
            e.printStackTrace();
        }
    }

    public void cancelled(Trade trade, Player player) {
        log(actionLogger.playerLog(TradeAction.CANCEL, player));

        logger.info(player.getName() + " cancelled a trade with " + (player.equals(trade.initiator()) ? trade.trader().getName() : trade.initiator().getName()));
        finish();


        try {
            Files.writeString(StaticFileSystemManager.getFileOrCreate("data/tradeLogs/trades.log").toPath(),
                    '[' + dateFormat.format(new Date()) + "] " + player.getName() + " canceled a trade with " +
                            (player.equals(trade.initiator()) ? trade.trader().getName() : trade.initiator().getName()) + " | ID: " + trade.getUuid() + '\n', StandardOpenOption.APPEND);
        } catch (IOException e) {
            logger.error("Could not write to trades.log log file");
            e.printStackTrace();
        }
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
