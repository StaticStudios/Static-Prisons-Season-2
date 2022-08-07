package net.staticstudios.prisons.customItems.minebombs;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.staticstudios.prisons.customItems.ICustomItem;
import net.staticstudios.prisons.utils.ComponentUtil;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class MineBombTier2 implements ICustomItem {

    public MineBombTier2() {
        register();
    }

    @Override
    public String getID() {
        return "mine_bomb_2";
    }

    @Override
    public ItemStack getItem(Player player) {
        return MineBombTier1.getMineBomb(2, Component.empty().append(Component.text("Medium Mine Bomb").color(ComponentUtil.GOLD).decorate(TextDecoration.BOLD)).decoration(TextDecoration.ITALIC, false));
    }
}
