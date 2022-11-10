package net.staticstudios.gui.legacy;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

@Deprecated
public interface GUIRunnable {
    void run(Player player, ClickType type);
}
