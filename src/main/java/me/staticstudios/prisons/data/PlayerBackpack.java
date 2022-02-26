package me.staticstudios.prisons.data;

import me.staticstudios.prisons.data.serverData.PlayerData;
import me.staticstudios.prisons.enchants.CustomEnchants;
import me.staticstudios.prisons.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public final class PlayerBackpack implements Serializable {
//DO NOT CHANGE THIS FILE, IT WILL BREAK SERVER DATA SERIALIZATION
    private Map<Material, BigInteger> contents = new HashMap<>();
    private BigInteger itemCount = BigInteger.ZERO;
    private BigInteger size = BigInteger.valueOf(25000);
    private boolean isFull;
    public BigInteger getAmountOf(Material mat) {
        if (!contents.containsKey(mat)) return BigInteger.ZERO;
        return contents.get(mat);
    }
    public void addAmountOf(Material mat, BigInteger amount) {
        if (isFull) return;
        if (itemCount.add(amount).compareTo(size) > 0) {
            amount = size.subtract(itemCount);
            isFull = true;
        }
        setAmountOf(mat, getAmountOf(mat).add(amount));
        itemCount = itemCount.add(amount);
    }
    public BigInteger getItemCount() {
        return itemCount;
    }
    public BigInteger getSize() {
        return size;
    }
    public void setSize(BigInteger size) {
        this.size = size;
    }
    private void setAmountOf(Material mat, BigInteger amount) {
        contents.put(mat, amount);
    }
    public boolean isFull() {
        return isFull;
    }
    public boolean updateIsFull() {
        isFull = size == itemCount;
        return isFull;
    }
    public void sellBackpack(Player player, boolean sendChatMessage, double multiplier) {
        PlayerBackpack.sellBackPack(player, sendChatMessage, multiplier, this); //pont to static method to prevent losing data when serialized if this method is updated
    }
    static void sellBackPack(Player player, boolean sendChatMessage, double multiplier, PlayerBackpack backpack) {
        BigInteger totalSellPrice = BigInteger.ZERO;
        if (backpack.getItemCount().compareTo(BigInteger.ZERO) > 0) {
            for (Material key : backpack.contents.keySet()) {
                totalSellPrice = totalSellPrice.add(backpack.contents.get(key).multiply(SellPrices.getSellPriceOf(key)));
            }
        }
        totalSellPrice = new BigDecimal(totalSellPrice).multiply(BigDecimal.valueOf(multiplier)).toBigInteger();
        new PlayerData(player).addMoney(totalSellPrice);
        if (sendChatMessage) {
            player.sendMessage(ChatColor.GREEN + "(x" + multiplier + ") Sold " + Utils.addCommasToBigInteger(backpack.itemCount) + " blocks for: $" + Utils.addCommasToBigInteger(totalSellPrice));
        }
        backpack.isFull = false;
        backpack.itemCount = BigInteger.ZERO;
        backpack.contents = new HashMap<>();
    }
}
