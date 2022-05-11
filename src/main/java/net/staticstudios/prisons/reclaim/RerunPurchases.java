package net.staticstudios.prisons.reclaim;

import net.staticstudios.prisons.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RerunPurchases {
    static List<String> allPurchases = Utils.getAllLinesInAFile("./data/season2PostResetTebex.txt");
    public static void playerJoined(Player player) {
        String uuid = player.getUniqueId().toString();
        List<Integer> linesUsed = new ArrayList<>();
        boolean modified = false;
        for (int i = 0; i < allPurchases.size(); i++) {
            String line = allPurchases.get(i);
            if (line.split(" \\| ")[0].equals(uuid)) {
                modified = true;
                linesUsed.add(i);
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), line.split(" \\| ")[1].replace("{playerName}", player.getName()));
            }
        }
        if (!modified) return;
        Collections.reverse(linesUsed);
        for (int i : linesUsed) allPurchases.remove(i);
        Utils.writeToAFile("./data/season2PostResetTebex.txt", allPurchases, false);
    }
}
