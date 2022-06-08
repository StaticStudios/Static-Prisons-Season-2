package net.staticstudios.prisons.privateMines;

import net.staticstudios.mines.minesapi.events.BlockBrokenInMineEvent;
import net.staticstudios.prisons.blockBroken.PrisonBlockBroken;
import net.staticstudios.prisons.data.dataHandling.PlayerData;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;
import java.util.UUID;

public class PrivateMineBlockBreakListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    void onBlockBreak(BlockBrokenInMineEvent e) {
        if (!e.getMine().getID().startsWith("private_mine")) return;
        e.setRunOnProcessEvent(p -> {
            PrisonBlockBroken bb = (PrisonBlockBroken) p;
            PrivateMine privateMine = PrivateMine.MINE_ID_TO_PRIVATE_MINE.get(e.getMine().getID());
            bb.moneyMultiplier *= privateMine.sellPercentage;
            if (e.getPlayer().getUniqueId().equals(privateMine.owner)) {
                bb.applyMoneyMulti();
                return;
            }
            bb.convertFromLegacySellValues();
            BigDecimal totalSellValue = BigDecimal.ZERO;
            for (Map.Entry<BigInteger, BigDecimal> entry : bb.blocksBroken.entrySet()) totalSellValue = totalSellValue.add(entry.getValue().multiply(new BigDecimal(entry.getKey())));
            new PlayerData(privateMine.owner).addMoney(totalSellValue.multiply(BigDecimal.valueOf(privateMine.visitorTax * bb.moneyMultiplier * bb.blocksBrokenMultiplier)).toBigInteger());
            bb.moneyMultiplier *= 1 - privateMine.visitorTax;
            bb.applyMoneyMulti();
        });
    }
}
