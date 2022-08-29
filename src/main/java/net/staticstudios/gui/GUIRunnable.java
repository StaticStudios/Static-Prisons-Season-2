package net.staticstudios.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

@Deprecated
public interface GUIRunnable { //todo switch to using comsumers and biconsumers
    void run(Player player, ClickType type);
}
