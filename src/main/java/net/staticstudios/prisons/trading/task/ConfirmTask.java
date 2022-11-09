package net.staticstudios.prisons.trading.task;

import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.trading.domain.Trade;
import org.bukkit.Bukkit;

public class ConfirmTask implements Runnable {

    private final Trade trade;

    public ConfirmTask(Trade trade) {
        this.trade = trade;
    }

    private int secondsLeft = 5;

    @Override
    public void run() {
        if (!trade.isInitiatorConfirmed() || !trade.isTraderConfirmed()) {
            return;
        }


        if (secondsLeft < 0) {
            if (trade.isInCompletedState()) {
                return;
            }

            trade.complete();
            return;
        }

        trade.updateTimer(5 - secondsLeft);

        secondsLeft--;

        Bukkit.getScheduler().runTaskLater(StaticPrisons.getInstance(), this, 20);
    }
}
