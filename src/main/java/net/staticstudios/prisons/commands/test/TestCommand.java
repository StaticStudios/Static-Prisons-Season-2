package net.staticstudios.prisons.commands.test;

import net.staticstudios.gui.GUICreator;
import net.staticstudios.gui.GUIRunnable;
import net.staticstudios.prisons.data.dataHandling.PlayerData;
import net.staticstudios.prisons.data.dataHandling.serverData.ServerData;
import net.staticstudios.prisons.enchants.handler.PrisonEnchants;
import net.staticstudios.prisons.enchants.handler.PrisonPickaxe;
import net.staticstudios.prisons.gui.newGui.BackpackMenus;
import net.staticstudios.prisons.gui.newGui.WarpMenus;
import net.staticstudios.prisons.newAuctionHouse.AuctionHouseMenus;
import net.staticstudios.prisons.utils.Utils;
import net.staticstudios.prisons.utils.WeightedElement;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class TestCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        Player player = null;
        if (commandSender instanceof Player) {
            player = (Player) commandSender;
        }
        assert player != null;
        //AuctionHouseMenus.openMenu(player, 0);
        player.sendMessage(Utils.getRandomWeightedElement(
                new WeightedElement<>("hi", 10),
                new WeightedElement<>("hi2", 10),
                new WeightedElement<>("hello", 80))
        );
        return false;
    }
}
