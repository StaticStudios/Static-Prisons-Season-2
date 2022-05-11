package net.staticstudios.prisons.enchants.handler;

import net.staticstudios.prisons.data.dataHandling.PlayerData;
import net.staticstudios.prisons.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/*
public class ConsistencyEnchant {
    public static Map<UUID, Integer> uuidToTimeMiningConsistently = new HashMap<>();
    public static Map<UUID, Integer> uuidToTimeSinceLastMined = new HashMap<>();
    public static Map<UUID, BigInteger> uuidToLastBlocksMined = new HashMap<>();
    public static Map<UUID, Double> uuidToTempTokenMultiplier = new HashMap<>();

    public static void worker() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            PlayerData playerData = new PlayerData(p);
            if (!uuidToLastBlocksMined.containsKey(p.getUniqueId())) {
                uuidToLastBlocksMined.put(p.getUniqueId(), playerData.getRawBlocksMined());
                continue;
            }
            if (uuidToLastBlocksMined.get(p.getUniqueId()).compareTo(playerData.getRawBlocksMined()) == 0) { //The player has not mined any blocks since the last check
                if (!uuidToTimeSinceLastMined.containsKey(p.getUniqueId())) uuidToTimeSinceLastMined.put(p.getUniqueId(), 0);
                uuidToTimeSinceLastMined.put(p.getUniqueId(), uuidToTimeSinceLastMined.get(p.getUniqueId()) + 1);
                if (uuidToTimeSinceLastMined.get(p.getUniqueId()) >= 120) {
                    //Player's multiplier has expired
                    if (uuidToTempTokenMultiplier.containsKey(p.getUniqueId())) {
                        p.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "Consistency " + ChatColor.DARK_GRAY + ChatColor.BOLD + ">" + ChatColor.RED + " Your token multiplier expired due to inactivity (120 seconds)!");
                        uuidToTempTokenMultiplier.remove(p.getUniqueId());
                        uuidToTimeMiningConsistently.remove(p.getUniqueId());
                    }
                }
            } else {
                uuidToLastBlocksMined.put(p.getUniqueId(), playerData.getRawBlocksMined());
                uuidToTimeSinceLastMined.put(p.getUniqueId(), 0);
                if (!uuidToTimeMiningConsistently.containsKey(p.getUniqueId())) uuidToTimeMiningConsistently.put(p.getUniqueId(), 0);
                if (uuidToTimeMiningConsistently.get(p.getUniqueId()) % 120 == 0 && uuidToTimeMiningConsistently.get(p.getUniqueId()) != 0) {
                    if (!Utils.checkIsPrisonPickaxe(p.getInventory().getItemInMainHand()) || CustomEnchants.getEnchantLevel(p.getInventory().getItemInMainHand(), "consistency") < 1) continue;
                    if (!uuidToTempTokenMultiplier.containsKey(p.getUniqueId())) uuidToTempTokenMultiplier.put(p.getUniqueId(), 0d);
                    if (uuidToTempTokenMultiplier.get(p.getUniqueId()) >= 0.1 * CustomEnchants.getEnchantLevel(p.getInventory().getItemInMainHand(), "consistency")) continue;
                    uuidToTempTokenMultiplier.put(p.getUniqueId(), uuidToTempTokenMultiplier.get(p.getUniqueId()) + 0.01d);
                    p.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "Consistency " + ChatColor.DARK_GRAY + ChatColor.BOLD + ">" + ChatColor.GREEN + " +1.00% Token multiplier due to your consistent mining activity! (" + Utils.addCommasToNumber(uuidToTimeMiningConsistently.get(p.getUniqueId())) + " seconds) Current multiplier: +"
                            + BigDecimal.valueOf(uuidToTempTokenMultiplier.get(p.getUniqueId()) + 0.0000002).setScale(2, RoundingMode.FLOOR).multiply(BigDecimal.valueOf(100)) + "%");
                }
                uuidToTimeMiningConsistently.put(p.getUniqueId(), uuidToTimeMiningConsistently.get(p.getUniqueId()) + 1);
            }
        }
    }
}


 */