package net.staticstudios.prisons.customitems;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.customitems.handler.CustomItem;
import net.staticstudios.prisons.utils.ComponentUtil;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

public enum CrateKeyCustomItem implements CustomItem {
    COMMON("common_key", Component.text("COMMON CRATE KEY").color(ComponentUtil.AQUA).decorate(TextDecoration.BOLD)),
    RARE("rare_key", Component.text("RARE CRATE KEY").color(TextColor.color(0x03fca1)).decorate(TextDecoration.BOLD)),
    EPIC("epic_key", Component.text("EPIC CRATE KEY").color(TextColor.color(0x9B37FF)).decorate(TextDecoration.BOLD)),
    LEGENDARY("legendary_key", Component.text("LEGENDARY CRATE KEY").color(ComponentUtil.GOLD).decorate(TextDecoration.BOLD)),
    STATIC("static_key", StaticPrisons.miniMessage().deserialize("<gradient:#fa04ff:#00b3ff>STATIC CRATE KEY").decorate(TextDecoration.BOLD)),
    STATICP("staticp_key", StaticPrisons.miniMessage().deserialize("<gradient:#ff0000:#9400d3>STATIC+ CRATE KEY").decorate(TextDecoration.BOLD)),
    KIT("kit_key", Component.text("KIT CRATE KEY").color(ComponentUtil.RED).decorate(TextDecoration.BOLD)),
    VOTE("vote_key", Component.text("VOTE CRATE KEY").color(ComponentUtil.LIGHT_PURPLE).decorate(TextDecoration.BOLD)),
    PICKAXE("pickaxe_key", Component.text("PICKAXE CRATE KEY").color(ComponentUtil.GREEN).decorate(TextDecoration.BOLD));


    private final String id;
    private final Component displayName;

    CrateKeyCustomItem(String id, Component displayName) {
        this.id = id;
        this.displayName = displayName.decoration(TextDecoration.ITALIC, false);

        register();
    }

    @Override
    public String getID() {
        return id;
    }

    @Override
    public ItemStack getItem(Player player) {
        ItemStack item = setCustomItem(new ItemStack(Material.TRIPWIRE_HOOK));

        item.editMeta(itemMeta -> {
            itemMeta.getPersistentDataContainer().set(new NamespacedKey(StaticPrisons.getInstance(), "crateKey"), PersistentDataType.STRING, id.split("_key")[0]);
            itemMeta.displayName(displayName);
            itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            itemMeta.addEnchant(Enchantment.LURE, 100, true);
            itemMeta.lore(List.of(
                    Component.empty().append(Component.text("Open a crate with this key for").color(ComponentUtil.LIGHT_GRAY)).decoration(TextDecoration.ITALIC, false),
                    Component.empty().append(Component.text("a random reward!").color(ComponentUtil.LIGHT_GRAY))
                            .append(Component.text(" /warp crates").color(ComponentUtil.AQUA)).decoration(TextDecoration.ITALIC, false)
            ));
        });

        return item;
    }
}
