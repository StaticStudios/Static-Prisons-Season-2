package net.staticstudios.prisons.customitems.minebombs;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.staticstudios.mines.StaticMine;
import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.backpacks.PrisonBackpacks;
import net.staticstudios.prisons.customitems.CustomItem;
import net.staticstudios.prisons.minebombs.MultiBombMineBomb;
import net.staticstudios.prisons.minebombs.PreComputerMineBomb;
import net.staticstudios.prisons.mines.MineBlock;
import net.staticstudios.prisons.utils.ComponentUtil;
import org.bukkit.Location;
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

public class MineBombTier1 implements CustomItem {

    static NamespacedKey MINE_BOMB_KEY;
    static NamespacedKey getMineBombKey() {
        if (MINE_BOMB_KEY == null) {
            MINE_BOMB_KEY = new NamespacedKey(StaticPrisons.getInstance(), "mineBomb");
        }
        return MINE_BOMB_KEY;
    }

    static ItemStack getMineBomb(int tier, Component name) {
        ItemStack item = new ItemStack(Material.TNT);
        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(getMineBombKey(), PersistentDataType.INTEGER, tier);
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

    public MineBombTier1() {
        register();
    }

    @Override
    public String getID() {
        return "mine_bomb_1";
    }

    @Override
    public ItemStack getItem(Player player) {
        return getMineBomb(1, Component.empty().append(Component.text("Small Mine Bomb").color(ComponentUtil.YELLOW).decorate(TextDecoration.BOLD)).decoration(TextDecoration.ITALIC, false));
    }

    private static final PreComputerMineBomb MINE_BOMB_1 = new PreComputerMineBomb(15);
    private static final PreComputerMineBomb MINE_BOMB_2 = new PreComputerMineBomb(20);
    private static final PreComputerMineBomb MINE_BOMB_3 = new PreComputerMineBomb(27);
    private static final PreComputerMineBomb MINE_BOMB_4 = new PreComputerMineBomb(40);
    private static final int VIRTUAL_FORTUNE = 500;

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
        if (!itemMeta.getPersistentDataContainer().has(getMineBombKey(), PersistentDataType.INTEGER)) {
            return false;
        }
        StaticMine mine = StaticMine.fromLocationXZ(block.getLocation());
        if (mine == null) {
            return false;
        }
        int bombTier = itemMeta.getPersistentDataContainer().get(getMineBombKey(), PersistentDataType.INTEGER);
        PreComputerMineBomb mineBomb = null;
        switch (bombTier) {
            default -> mineBomb = MINE_BOMB_1;
            case 2 -> mineBomb = MINE_BOMB_2;
            case 3 -> mineBomb = MINE_BOMB_3;
            case 4 -> mineBomb = MINE_BOMB_4;
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
}
