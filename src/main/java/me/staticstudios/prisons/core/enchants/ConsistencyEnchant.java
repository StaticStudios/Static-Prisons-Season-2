package me.staticstudios.prisons.core.enchants;

import me.staticstudios.prisons.core.data.serverData.PlayerData;
import me.staticstudios.prisons.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class ConsistencyEnchant {
    public static void worker() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            PlayerData playerData = new PlayerData(p);
            if (!CustomEnchants.uuidToLastBlocksMined.containsKey(p.getUniqueId())) {
                CustomEnchants.uuidToLastBlocksMined.put(p.getUniqueId(), playerData.getRawBlocksMined());
                continue;
            }
            if (CustomEnchants.uuidToLastBlocksMined.get(p.getUniqueId()).compareTo(playerData.getRawBlocksMined()) == 0) { //The player has not mined any blocks since the last check
                if (!CustomEnchants.uuidToTimeSinceLastMined.containsKey(p.getUniqueId())) CustomEnchants.uuidToTimeSinceLastMined.put(p.getUniqueId(), 0);
                CustomEnchants.uuidToTimeSinceLastMined.put(p.getUniqueId(), CustomEnchants.uuidToTimeSinceLastMined.get(p.getUniqueId()) + 1);
                if (CustomEnchants.uuidToTimeSinceLastMined.get(p.getUniqueId()) >= 120) {
                    //Player's multiplier has expired
                    if (CustomEnchants.uuidToTempTokenMultiplier.containsKey(p.getUniqueId())) {
                        p.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "Consistency " + ChatColor.DARK_GRAY + ChatColor.BOLD + ">" + ChatColor.RED + " Your token multiplier expired due to inactivity (120 seconds)!");
                        CustomEnchants.uuidToTempTokenMultiplier.remove(p.getUniqueId());
                        CustomEnchants.uuidToTimeMiningConsistently.remove(p.getUniqueId());
                    }
                }
            } else {
                CustomEnchants.uuidToLastBlocksMined.put(p.getUniqueId(), playerData.getRawBlocksMined());
                CustomEnchants.uuidToTimeSinceLastMined.put(p.getUniqueId(), 0);
                if (!CustomEnchants.uuidToTimeMiningConsistently.containsKey(p.getUniqueId())) CustomEnchants.uuidToTimeMiningConsistently.put(p.getUniqueId(), 0);
                if (CustomEnchants.uuidToTimeMiningConsistently.get(p.getUniqueId()) % 120 == 0 && CustomEnchants.uuidToTimeMiningConsistently.get(p.getUniqueId()) != 0) {
                    if (!Utils.checkIsPrisonPickaxe(p.getInventory().getItemInMainHand()) || CustomEnchants.getEnchantLevel(p.getInventory().getItemInMainHand(), "consistency") < 1) continue;
                    if (!CustomEnchants.uuidToTempTokenMultiplier.containsKey(p.getUniqueId())) CustomEnchants.uuidToTempTokenMultiplier.put(p.getUniqueId(), 0d);
                    if (CustomEnchants.uuidToTempTokenMultiplier.get(p.getUniqueId()) >= 0.1 * CustomEnchants.getEnchantLevel(p.getInventory().getItemInMainHand(), "consistency")) continue;
                    CustomEnchants.uuidToTempTokenMultiplier.put(p.getUniqueId(), CustomEnchants.uuidToTempTokenMultiplier.get(p.getUniqueId()) + 0.01d);
                    p.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "Consistency " + ChatColor.DARK_GRAY + ChatColor.BOLD + ">" + ChatColor.GREEN + " +1.00% Token multiplier due to your consistent mining activity! (" + Utils.addCommasToNumber(CustomEnchants.uuidToTimeMiningConsistently.get(p.getUniqueId())) + " seconds) Current multiplier: +"
                            + BigDecimal.valueOf(CustomEnchants.uuidToTempTokenMultiplier.get(p.getUniqueId()) + 0.0000002).setScale(2, RoundingMode.FLOOR).multiply(BigDecimal.valueOf(100)) + "%");
                }
                CustomEnchants.uuidToTimeMiningConsistently.put(p.getUniqueId(), CustomEnchants.uuidToTimeMiningConsistently.get(p.getUniqueId()) + 1);
            }
        }
    }
}
