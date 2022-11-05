package net.staticstudios.prisons.customitems.handler;

import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.commands.CommandManager;
import net.staticstudios.prisons.customitems.commands.CustomItemsCommand;
import net.staticstudios.prisons.customitems.CrateKeyCustomItem;
import net.staticstudios.prisons.customitems.icebomb.IceBomb;
import net.staticstudios.prisons.customitems.pickaxes.PickaxeTemplates;
import net.staticstudios.prisons.customitems.pouches.MoneyPouch;
import net.staticstudios.prisons.customitems.pouches.MultiPouch;
import net.staticstudios.prisons.customitems.pouches.TokenPouch;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class CustomItems implements Listener {
    public static void init() {

        //todo: multipliers should get out of the voucher system and into the custom items system
        //todo: kits should be out of the voucher system and should use a config file-

        CommandManager.registerCommand("customitems", new CustomItemsCommand());

        StaticPrisons.getInstance().getServer().getPluginManager().registerEvents(new CustomItems(), StaticPrisons.getInstance());

        TokenPouch.TIER_1.getID(); //Call this so that all the token pouch enums are initialized
        MoneyPouch.TIER_1.getID(); //Call this so that all the money pouch enums are initialized
        MultiPouch.TIER_1.getID(); //Call this so that all the multi pouch enums are initialized
        PickaxeTemplates.DEFAULT.getID(); //Call this so that all the pickaxe templates are initialized

        IceBomb.init();
    }

    public static final Map<String, CustomItem> ITEMS = new HashMap<>();

    public static ItemStack getItem(String id, Player player) {
        CustomItem item = ITEMS.get(id);
        if (item == null) {
            return null;
        }
        return item.getItem(player);
    }


    @EventHandler
    void onClick(PlayerInteractEvent e) {
        for (CustomItem item : ITEMS.values()) {
            if (item.onInteract(e)) {
                e.setCancelled(true);
                return;
            }
        }
    }
    public static ItemStack getVoteCrateKey(int amount) {
        ItemStack item = CrateKeyCustomItem.VOTE.getItem(null);
        item.setAmount(amount);
        return item;
    }
    public static ItemStack getKitCrateKey(int amount) {
        ItemStack item = CrateKeyCustomItem.KIT.getItem(null);
        item.setAmount(amount);
        return item;
    }
    public static ItemStack getPickaxeCrateKey(int amount) {
        ItemStack item = CrateKeyCustomItem.PICKAXE.getItem(null);
        item.setAmount(amount);
        return item;
    }
    public static ItemStack getCommonCrateKey(int amount) {
        ItemStack item = CrateKeyCustomItem.COMMON.getItem(null);
        item.setAmount(amount);
        return item;
    }
    public static ItemStack getRareCrateKey(int amount) {
        ItemStack item = CrateKeyCustomItem.RARE.getItem(null);
        item.setAmount(amount);
        return item;
    }
    public static ItemStack getEpicCrateKey(int amount) {
        ItemStack item = CrateKeyCustomItem.EPIC.getItem(null);
        item.setAmount(amount);
        return item;
    }
    public static ItemStack getLegendaryCrateKey(int amount) {
        ItemStack item = CrateKeyCustomItem.LEGENDARY.getItem(null);
        item.setAmount(amount);
        return item;
    }
    public static ItemStack getStaticCrateKey(int amount) {
        ItemStack item = CrateKeyCustomItem.STATIC.getItem(null);
        item.setAmount(amount);
        return item;
    }
    public static ItemStack getStaticpCrateKey(int amount) {
        ItemStack item = CrateKeyCustomItem.STATICP.getItem(null);
        item.setAmount(amount);
        return item;
    }
}
