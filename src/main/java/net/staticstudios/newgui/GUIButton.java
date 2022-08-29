package net.staticstudios.newgui;

import net.kyori.adventure.text.Component;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * @author Sam (GitHub: <a href="https://github.com/Sammster10">Sam's GitHub</a>)
 */
public class GUIButton {
    private final Consumer<Player> onLeftClick;
    private final Consumer<Player> onRightClick;
    private final Consumer<Player> onMiddleClick;
    private final BiConsumer<InventoryClickEvent, StaticGUI> onClick;


    private final ItemStack item;

    public GUIButton(Consumer<Player> onLeftClick, Consumer<Player> onRightClick, Consumer<Player> onMiddleClick,
                     BiConsumer<InventoryClickEvent, StaticGUI> onClick,
                     ItemStack item, Component name, List<Component> lore, boolean enchanted, int stackCount) {
        this.onLeftClick = onLeftClick;
        this.onRightClick = onRightClick;
        this.onMiddleClick = onMiddleClick;
        this.onClick = onClick;
        this.item = item;
        item.setAmount(stackCount);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(name);
        meta.lore(lore);
        if (enchanted) {
            meta.addEnchant(Enchantment.LURE, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        item.setItemMeta(meta);
    }

    //Getters
    public Consumer<Player> onLeftClick() {
        return onLeftClick;
    }
    public Consumer<Player> onRightClick() {
        return onRightClick;
    }
    public Consumer<Player> onMiddleClick() {
        return onMiddleClick;
    }
    public BiConsumer<InventoryClickEvent, StaticGUI> onClick() {
        return onClick;
    }
    public ItemStack item() {
        return item;
    }

}
