package net.staticstudios.prisons.customitems;

import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.commands.CommandManager;
import net.staticstudios.prisons.customitems.commands.CustomItemsCommand;
import net.staticstudios.prisons.customitems.currency.MoneyNote;
import net.staticstudios.prisons.customitems.currency.MultiplierVoucher;
import net.staticstudios.prisons.customitems.currency.TokenNote;
import net.staticstudios.prisons.customitems.icebomb.IceBomb;
import net.staticstudios.prisons.customitems.pouches.MoneyPouch;
import net.staticstudios.prisons.customitems.pouches.MultiPouch;
import net.staticstudios.prisons.customitems.pouches.TokenPouch;
import net.staticstudios.prisons.customitems.vouchers.Voucher;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;
import java.util.Map;

public class CustomItems implements Listener {
    public static void init() {

        //todo: multipliers should get out of the voucher system and into the custom items system
        //todo: kits should be out of the voucher system and should use a config file-

        CommandManager.registerCommand("customitems", new CustomItemsCommand());

        StaticPrisons.getInstance().getServer().getPluginManager().registerEvents(new CustomItems(), StaticPrisons.getInstance());

        TokenPouch.TIER_1.getId(); //Call this so that all the token pouch enums are initialized
        MoneyPouch.TIER_1.getId(); //Call this so that all the money pouch enums are initialized
        MultiPouch.TIER_1.getId(); //Call this so that all the multi pouch enums are initialized
        PickaxeTemplates.DEFAULT.getId(); //Call this so that all the pickaxe templates are initialized
        MineBombCustomItem.TIER_1.getId(); //Call this so that all the mine bomb custom items are initialized
        CrateKeyCustomItem.COMMON.getId(); //Call this so that all the crate key custom items are initialized
        BackpackCustomItem.TIER_1.getId(); //Call this so that all the backpack custom items are initialized

        MoneyNote.INSTANCE.getId();
        TokenNote.INSTANCE.getId();
        MultiplierVoucher.INSTANCE.getId();

        Voucher.init();

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

    public static ItemStack getItem(String id, Player player, String[] args) {
        if (args.length == 0) {
            return getItem(id, player);
        }
        CustomItem item = ITEMS.get(id);
        if (item == null) {
            return null;
        }
        return item.getItem(player, args);
    }


    @EventHandler
    void onClick(PlayerInteractEvent e) {
        ItemStack item = e.getItem();
        if (item == null) {
            return;
        }
        if (!item.hasItemMeta()) {
            return;
        }
        if (!item.getItemMeta().getPersistentDataContainer().has(CustomItem.CUSTOM_ITEM_ID, PersistentDataType.STRING)) {
            return;
        }
        String id = item.getItemMeta().getPersistentDataContainer().get(CustomItem.CUSTOM_ITEM_ID, PersistentDataType.STRING);
        CustomItem customItem = ITEMS.get(id);
        if (customItem == null) {
            return;
        }
        customItem.onInteract(e);
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
