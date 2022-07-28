package net.staticstudios.prisons.backpacks;

import java.math.BigDecimal;

public record SellReceipt(BigDecimal multiplier, long blocksSold, long soldFor) {
}
