package net.staticstudios.prisons.customitems.items;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.staticstudios.mines.StaticMine;
import net.staticstudios.mines.StaticMines;
import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.backpacks.BackpackManager;
import net.staticstudios.prisons.customitems.CustomItem;
import net.staticstudios.prisons.minebombs.PreComputedMineBomb;
import net.staticstudios.prisons.mines.MineBlock;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.kyori.adventure.text.format.TextDecoration.ITALIC;
import static net.staticstudios.prisons.utils.ComponentUtil.LIGHT_GRAY;

public enum MineBombCustomItem implements CustomItem {
    TIER_1(1, "mine_bomb_1", "&e&lSmall Mine Bomb", new PreComputedMineBomb(15)),
    TIER_2(2, "mine_bomb_2", "&6&lMedium Mine Bomb", new PreComputedMineBomb(20)),
    TIER_3(3, "mine_bomb_3", "&c&lLarge Mine Bomb", new PreComputedMineBomb(27)),
    TIER_4(4, "mine_bomb_4", "&4&lHUGE Mine Bomb", new PreComputedMineBomb(40));


    private static final NamespacedKey MINE_BOMB_KEY = new NamespacedKey(StaticPrisons.getInstance(), "mineBomb");
    private static final int VIRTUAL_FORTUNE = 500;
    private final int tier;
    private final String id;
    private final Component name;
    private final PreComputedMineBomb mineBomb;

    MineBombCustomItem(int tier, String id, String displayName, PreComputedMineBomb mineBomb) {
        this.tier = tier;
        this.id = id;
        this.name = LegacyComponentSerializer.legacyAmpersand().deserialize(displayName).decoration(ITALIC, false);
        this.mineBomb = mineBomb;
        register();
    }

    static ItemStack getMineBomb(int tier, Component name) {
        ItemStack item = new ItemStack(Material.TNT);
        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(MINE_BOMB_KEY, PersistentDataType.INTEGER, tier);
        meta.displayName(name);
        meta.lore(List.of(
                Component.empty(),
                Component.text("Place me in a mine and I'll explode!").color(LIGHT_GRAY).decorate(ITALIC)
        ));
        meta.addEnchant(Enchantment.LURE, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public String getID() {
        return id;
    }

    @Override
    public ItemStack getItem(Player player) {
        return setCustomItem(getMineBomb(tier, name));
    }

    @Override
    public boolean onInteract(PlayerInteractEvent e) {
        Block block = e.getClickedBlock();
        if (block == null) {
            return false;
        }
        ItemStack itemStack = e.getItem();
        if (itemStack == null) {
            return false;
        }
        if (itemStack.getType() != Material.TNT) {
            return false;
        }
        if (!itemStack.hasItemMeta()) {
            return false;
        }
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (!itemMeta.getPersistentDataContainer().has(MINE_BOMB_KEY, PersistentDataType.INTEGER)) {
            return false;
        }
        if (itemMeta.getPersistentDataContainer().get(MINE_BOMB_KEY, PersistentDataType.INTEGER) != tier) {
            return false;
        }
        StaticMine mine = StaticMines.fromLocation(block.getLocation(), false);
        if (mine == null) {
            return false;
        }


        Map<MineBlock, Long> map = new HashMap<>();
        for (Map.Entry<Material, Long> entry : mineBomb.explode(mine, block.getLocation()).entrySet()) {
            map.put(MineBlock.fromMaterial(entry.getKey()), entry.getValue() * VIRTUAL_FORTUNE);
        }
        mine.removeBlocks(mineBomb.blocksChanged);
        e.getItem().setAmount(e.getItem().getAmount() - 1);
        BackpackManager.addToBackpacks(e.getPlayer(), map);
        return true;
    }

}
