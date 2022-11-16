package net.staticstudios.prisons.customitems.vouchers;

import net.kyori.adventure.audience.Audience;
import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.customitems.CustomItem;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.function.Function;
import java.util.function.Supplier;

public class Voucher implements CustomItem {

    private static final NamespacedKey NAMESPACED_KEY = new NamespacedKey(StaticPrisons.getInstance(), "voucher");

    private final String id;
    private final Supplier<ItemStack> itemStackSupplier;
    private final Function<Player, Boolean> onClaim;


    public Voucher(String id, Supplier<ItemStack> makeItem, Function<Player, Boolean> onClaim) {
        this.id = "voucher-" + id;
        this.itemStackSupplier = makeItem;
        this.onClaim = onClaim;
        register();
    }

    public static void init() {
        PerkVouchers.AUTO_SELL.getId();
        RankVouchers.WARRIOR.getId();
        KitVouchers.TIER_1.getId();
    }

    private void keyItem(ItemStack item) {
        item.editMeta(meta -> {
            meta.getPersistentDataContainer().set(NAMESPACED_KEY, PersistentDataType.STRING, id);
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_POTION_EFFECTS);
        });
        setCustomItem(item, this);
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public ItemStack getItem(Audience audience) {
        ItemStack item = itemStackSupplier.get();
        keyItem(item);
        return item;
    }

    @Override
    public boolean onInteract(PlayerInteractEvent e) {
        if (!e.getAction().isRightClick()) {
            return false;
        }
        ItemStack item = e.getItem();
        if (item != null && item.hasItemMeta() && item.getItemMeta().getPersistentDataContainer().has(NAMESPACED_KEY, PersistentDataType.STRING)) {
            String id = item.getItemMeta().getPersistentDataContainer().get(NAMESPACED_KEY, PersistentDataType.STRING);
            assert id != null;
            if (!id.equals(this.id)) {
                return false;
            }
            if (!onClaim.apply(e.getPlayer())) {
                e.setCancelled(true);
                return false;
            }
            e.getItem().setAmount(e.getItem().getAmount() - 1);
            return true;
        }
        return false;
    }
}
