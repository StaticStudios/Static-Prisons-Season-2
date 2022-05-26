package net.staticstudios.prisons.commands.test;

import net.staticstudios.gui.GUICreator;
import net.staticstudios.prisons.gui.newGui.DailyRewardMenus;
import net.staticstudios.prisons.gui.newGui.StatsMenus;
import net.staticstudios.prisons.newAuctionHouse.Auction;
import net.staticstudios.prisons.newAuctionHouse.AuctionManager;
import net.staticstudios.prisons.utils.Utils;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class Test2Command implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        Player player = (Player) commandSender;
        for (int i = 0; i < 30; i++)
        AuctionManager.auctions.add(new Auction(UUID.randomUUID(), player.getInventory().getItemInMainHand().clone(), player.getUniqueId(), Instant.now().getEpochSecond() + 10, BigInteger.valueOf(735635)));
        return false;
    }
}
