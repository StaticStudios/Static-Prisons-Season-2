package net.staticstudios.prisons.customitems.minebombs;

import net.kyori.adventure.text.Component;
import net.staticstudios.mines.StaticMine;
import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.backpacks.PrisonBackpacks;
import net.staticstudios.prisons.customitems.CustomItem;
import net.staticstudios.prisons.minebombs.PreComputerMineBomb;
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

import static net.kyori.adventure.text.Component.empty;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.TextDecoration.BOLD;
import static net.kyori.adventure.text.format.TextDecoration.ITALIC;
import static net.staticstudios.prisons.utils.ComponentUtil.LIGHT_GRAY;
import static net.staticstudios.prisons.utils.ComponentUtil.YELLOW;

public enum MineBomb implements CustomItem {
    TIER_1(1, "mine_bomb_1", new PreComputerMineBomb(15)),
    TIER_2(2, "mine_bomb_2", new PreComputerMineBomb(20)),
    TIER_3(3, "mine_bomb_3", new PreComputerMineBomb(27)),
    TIER_4(4, "mine_bomb_4", new PreComputerMineBomb(40));


    private final int tier;
    private final String id;
    private final PreComputerMineBomb mineBomb;

    private static final NamespacedKey MINE_BOMB_KEY = new NamespacedKey(StaticPrisons.getInstance(), "mineBomb");
    private static final int VIRTUAL_FORTUNE = 500;

    MineBomb(int tier, String id, PreComputerMineBomb mineBomb) {
        this.tier = tier;
        this.id = id;
        this.mineBomb = mineBomb;
        register();
    }

    @Override
    public String getID() {
        return id;
    }

    @Override
    public ItemStack getItem(Player player) {
        return getMineBomb(tier, empty().append(text("Small Mine Bomb").color(YELLOW).decorate(BOLD)).decoration(ITALIC, false));
    }

    @Override //Listener for all Mine bomb tiers, since the listeners would be very similar
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
        StaticMine mine = StaticMine.fromLocationXZ(block.getLocation());
        if (mine == null) {
            return false;
        }


        Map<MineBlock, Long> map = new HashMap<>();
        for (Map.Entry<Material, Long> entry: mineBomb.explode(mine, block.getLocation()).entrySet()) {
            map.put(MineBlock.fromMaterial(entry.getKey()), entry.getValue() * VIRTUAL_FORTUNE);
        }
        mine.removeBlocksBrokenInMine(mineBomb.blocksChanged);
        e.getItem().setAmount(e.getItem().getAmount() - 1);
        PrisonBackpacks.addToBackpacks(e.getPlayer(), map);
        return true;
    }

    static ItemStack getMineBomb(int tier, Component name) {
        ItemStack item = new ItemStack(Material.TNT);
        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(MINE_BOMB_KEY, PersistentDataType.INTEGER, tier);
        meta.displayName(name);
        meta.lore(
                List.of(
                        empty(),
                        text("Place me in a mine and I'll explode!").color(LIGHT_GRAY).decorate(ITALIC)
                )
        );
        meta.addEnchant(Enchantment.LURE, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);
        return item;
    }

}
