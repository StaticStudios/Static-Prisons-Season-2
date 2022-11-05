package net.staticstudios.prisons.enchants;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;

import java.util.List;

import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.NamedTextColor.RED;

public class EnchantItemStackListener implements Listener {

    @EventHandler
    public void playerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        List<EnchantableItemStack> enchantableItemStacks = EnchantableItemStack.fromGenericItem(itemStack);
        if (enchantableItemStacks.isEmpty()) return;

        enchantableItemStacks.forEach(item -> item.getEnabledEnchantments()
                .stream()
                .map(EnchantHolder::enchantment)
                .forEach(enchant -> enchant.onHold(item, player)));
    }

    @EventHandler
    public void playerLeave(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        List<EnchantableItemStack> enchantableItemStacks = EnchantableItemStack.fromGenericItem(itemStack);
        if (enchantableItemStacks.isEmpty()) return;

        enchantableItemStacks.forEach(item -> item.getEnabledEnchantments()
                .stream()
                .map(EnchantHolder::enchantment)
                .forEach(enchant -> enchant.onUnHold(item, player)));

        for (ItemStack item : player.getInventory().getContents()) {
            enchantableItemStacks = EnchantableItemStack.fromGenericItem(item);
            if (enchantableItemStacks.isEmpty()) continue;
            enchantableItemStacks.get(0).updateItemNow();
        }
    }

    @EventHandler
    void onChangeItemHeld(PlayerItemHeldEvent e) {
        Player player = e.getPlayer();

        ItemStack oldItem = player.getInventory().getContents()[e.getPreviousSlot()];
        ItemStack newItem = player.getInventory().getContents()[e.getNewSlot()];

        List<EnchantableItemStack> oldEnchantableItemStacks = EnchantableItemStack.fromGenericItem(oldItem);
        List<EnchantableItemStack> newEnchantableItemStacks = EnchantableItemStack.fromGenericItem(newItem);

        if (!oldEnchantableItemStacks.isEmpty()) {
            oldEnchantableItemStacks.forEach(item -> item.getEnabledEnchantments()
                    .stream()
                    .map(EnchantHolder::enchantment)
                    .forEach(enchant -> enchant.onUnHold(item, player)));
        }

        if (!newEnchantableItemStacks.isEmpty()) {
            newEnchantableItemStacks.forEach(item -> item.getEnabledEnchantments()
                    .stream()
                    .map(EnchantHolder::enchantment)
                    .forEach(enchant -> enchant.onHold(item, player)));
        }
    }

    @EventHandler
    void onItemPickup(PlayerAttemptPickupItemEvent e) {
        Player player = e.getPlayer();
        int firstEmpty = player.getInventory().firstEmpty();
        if (firstEmpty == -1 || firstEmpty != player.getInventory().getHeldItemSlot()) return;




        ItemStack itemStack = e.getItem().getItemStack();
        List<EnchantableItemStack> enchantableItemStacks = EnchantableItemStack.fromGenericItem(itemStack);
        if (enchantableItemStacks.isEmpty()) return;

        enchantableItemStacks.forEach(item -> item.getEnabledEnchantments()
                .stream()
                .map(EnchantHolder::enchantment)
                .forEach(enchant -> enchant.onHold(item, player)));
    }

    @EventHandler
    void onDrop(PlayerDropItemEvent e) {
        if (EnchantableItemStack.fromGenericItem(e.getItemDrop().getItemStack()).isEmpty()) return;
        e.setCancelled(true);
        e.getPlayer().sendMessage(text("You cannot drop this item! Type /dropitem to drop it!").color(RED));
    }

    @EventHandler
    void onSwap(PlayerSwapHandItemsEvent e) {
        Player player = e.getPlayer();
        ItemStack oldItem = player.getInventory().getItemInMainHand();
        ItemStack newItem = player.getInventory().getItemInOffHand();

        List<EnchantableItemStack> oldEnchantableItemStacks = EnchantableItemStack.fromGenericItem(oldItem);
        List<EnchantableItemStack> newEnchantableItemStacks = EnchantableItemStack.fromGenericItem(newItem);

        if (!oldEnchantableItemStacks.isEmpty()) {
            oldEnchantableItemStacks.forEach(item -> item.getEnabledEnchantments()
                    .stream()
                    .map(EnchantHolder::enchantment)
                    .forEach(enchant -> enchant.onUnHold(item, player)));
        }

        if (!newEnchantableItemStacks.isEmpty()) {
            newEnchantableItemStacks.forEach(item -> item.getEnabledEnchantments()
                    .stream()
                    .map(EnchantHolder::enchantment)
                    .forEach(enchant -> enchant.onHold(item, player)));
        }
    }

    public static void dropItemCommand(Player player) {
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        List<EnchantableItemStack> enchantableItemStacks = EnchantableItemStack.fromGenericItem(itemStack);
        if (enchantableItemStacks.isEmpty()) return;

        enchantableItemStacks.forEach(item -> item.getEnabledEnchantments()
                .stream()
                .map(EnchantHolder::enchantment)
                .forEach(enchant -> enchant.onUnHold(item, player)));

        player.getInventory().setItemInMainHand(null);
        player.getWorld().dropItem(player.getLocation(), itemStack).setPickupDelay(60);
    }


}
