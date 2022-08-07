package net.staticstudios.prisons.customitems.minebombs;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.staticstudios.mines.StaticMine;
import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.backpacks.PrisonBackpacks;
import net.staticstudios.prisons.customitems.CustomItem;
import net.staticstudios.prisons.minebombs.PreComputerMineBomb;
import net.staticstudios.prisons.mines.MineBlock;
import net.staticstudios.prisons.utils.ComponentUtil;
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

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum MineBombs implements CustomItem {
    TIER_1(1, "mine_bomb_1", new PreComputerMineBomb(15)),
    TIER_2(2, "mine_bomb_2", new PreComputerMineBomb(20)),
    TIER_3(3, "mine_bomb_3", new PreComputerMineBomb(27)),
    TIER_4(4, "mine_bomb_4", new PreComputerMineBomb(40));


    private final int tier;
    private final String id;
    private final PreComputerMineBomb mineBomb;

    private static final NamespacedKey MINE_BOMB_KEY = new NamespacedKey(StaticPrisons.getInstance(), "mineBomb");
    private static final int VIRTUAL_FORTUNE = 500;

    MineBombs(int tier, String id, PreComputerMineBomb mineBomb) {
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
        return getMineBomb(tier, Component.empty().append(Component.text("Small Mine Bomb").color(ComponentUtil.YELLOW).decorate(TextDecoration.BOLD)).decoration(TextDecoration.ITALIC, false));
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
        int bombTier = itemMeta.getPersistentDataContainer().get(MINE_BOMB_KEY, PersistentDataType.INTEGER);

        PreComputerMineBomb mineBomb = getMineBombByTier(bombTier);

        Map<MineBlock, Long> map = new HashMap<>();
        for (Map.Entry<Material, Long> entry: mineBomb.explode(mine, block.getLocation()).entrySet()) {
            map.put(MineBlock.fromMaterial(entry.getKey()), entry.getValue() * VIRTUAL_FORTUNE);
        }
        mine.removeBlocksBrokenInMine(mineBomb.blocksChanged);
        e.getItem().setAmount(e.getItem().getAmount() - 1);
        PrisonBackpacks.addToBackpacks(e.getPlayer(), map);
        return true;
    }

    private PreComputerMineBomb getMineBombByTier(int bombTier) {
        return Arrays.stream(values())
                .filter(mineBomb -> mineBomb.tier == bombTier)
                .map(mineBombs -> mineBombs.mineBomb)
                .findFirst().orElse(MineBombs.TIER_1.mineBomb);
    }


    static ItemStack getMineBomb(int tier, Component name) {
        ItemStack item = new ItemStack(Material.TNT);
        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(MINE_BOMB_KEY, PersistentDataType.INTEGER, tier);
        meta.displayName(name);
        meta.lore(
                List.of(
                        Component.empty(),
                        Component.text("Place me in a mine and I'll explode!").color(ComponentUtil.LIGHT_GRAY).decorate(TextDecoration.ITALIC)
                )
        );
        meta.addEnchant(Enchantment.LURE, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);
        return item;
    }

}
