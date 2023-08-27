package net.staticstudios.prisons.backpacks.selling;

import java.math.BigDecimal;

public record SellReceipt(BigDecimal multiplier, long blocksSold, long soldFor) {
}