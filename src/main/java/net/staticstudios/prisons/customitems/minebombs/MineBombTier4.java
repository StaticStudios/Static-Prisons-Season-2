package net.staticstudios.prisons.customitems.minebombs;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.staticstudios.prisons.customitems.CustomItem;
import net.staticstudios.prisons.utils.ComponentUtil;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class MineBombTier4 implements CustomItem {

    public MineBombTier4() {
        register();
    }

    @Override
    public String getID() {
        return "mine_bomb_4";
    }

    @Override
    public ItemStack getItem(Player player) {
        return MineBombTier1.getMineBomb(4, Component.empty().append(Component.text("HUGE Mine Bomb").color(ComponentUtil.DARK_RED).decorate(TextDecoration.BOLD)).decoration(TextDecoration.ITALIC, false));
    }
}
