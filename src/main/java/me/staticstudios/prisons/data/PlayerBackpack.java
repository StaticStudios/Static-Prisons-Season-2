package me.staticstudios.prisons.data;

import me.staticstudios.prisons.data.serverData.PlayerData;
import me.staticstudios.prisons.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class PlayerBackpack implements Serializable {

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


    public void sellBackpack(Player player, boolean sendChatMessage) {
        BigInteger totalSellPrice = BigInteger.ZERO;
        if (getItemCount().compareTo(BigInteger.ZERO) > 0) {
            for (Material key : contents.keySet()) {
                totalSellPrice = totalSellPrice.add(contents.get(key).multiply(SellPrices.getSellPriceOf(key)));
            }
        }
        new PlayerData(player).addMoney(totalSellPrice);
        if (sendChatMessage) {
            player.sendMessage(ChatColor.GREEN + "Sold " + Utils.addCommasToBigInteger(itemCount) + " blocks for: $" + Utils.addCommasToBigInteger(totalSellPrice));
        }

        isFull = false;
        itemCount = BigInteger.ZERO;
        contents = new HashMap<>();
    }

}
