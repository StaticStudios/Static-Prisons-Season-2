package net.staticstudios.prisons.privateMines;

import net.staticstudios.mines.minesapi.events.BlockBrokenInMineEvent;
import net.staticstudios.prisons.blockBroken.PrisonBlockBroken;
import net.staticstudios.prisons.data.PlayerData;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;

public class PrivateMineBlockBreakListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    void onBlockBreak(BlockBrokenInMineEvent e) {
        if (!e.getMine().getID().startsWith("private_mine")) return;
        e.setRunOnProcessEvent(p -> {
            PrisonBlockBroken bb = (PrisonBlockBroken) p;
            PrivateMine privateMine = PrivateMine.MINE_ID_TO_PRIVATE_MINE.get(e.getMine().getID());
            privateMine.blockBroken();
            bb.moneyMultiplier *= privateMine.sellPercentage;
            if (e.getPlayer().getUniqueId().equals(privateMine.owner) || privateMine.getWhitelist().contains(e.getPlayer().getUniqueId())) { //no taxes for the owner or invited players
                bb.applyMoneyMulti();
                return;
            }
            bb.convertFromLegacySellValues();
            BigDecimal totalSellValue = BigDecimal.ZERO;
            for (Map.Entry<BigDecimal, BigInteger> entry : bb.blocksBroken.entrySet()) totalSellValue = totalSellValue.add(entry.getKey().multiply(new BigDecimal(entry.getValue())));
            new PlayerData(privateMine.owner).addMoney(totalSellValue.multiply(BigDecimal.valueOf(privateMine.visitorTax * bb.moneyMultiplier * bb.blocksBrokenMultiplier)).toBigInteger());
            bb.moneyMultiplier *= 1 - privateMine.visitorTax;
            bb.applyMoneyMulti();
        });
    }
}
