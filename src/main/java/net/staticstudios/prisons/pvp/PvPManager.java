package net.staticstudios.prisons.pvp;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.pvp.commands.PvPEventCommand;
import net.staticstudios.prisons.pvp.koth.KingOfTheHillManager;
import net.staticstudios.prisons.pvp.outposts.OutpostManager;
import net.staticstudios.prisons.utils.Warps;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class PvPManager {

    public static final String PREFIX = ChatColor.translateAlternateColorCodes('&', "&c&lPvP &8&l>> &r");

    public static World PVP_WORLD;
    public static com.sk89q.worldedit.world.World WE_PVP_WORLD;

    public static Map<Player, Long> playerWarpOutIn = new HashMap<>();

    public static void init() {
        PVP_WORLD = new WorldCreator("pvp").createWorld(); //Ensure the world exists
        WE_PVP_WORLD = BukkitAdapter.adapt(PVP_WORLD);

        Bukkit.getPluginManager().registerEvents(new PvPListener(), StaticPrisons.getInstance());

        KingOfTheHillManager.init();
        OutpostManager.init();

        StaticPrisons.getInstance().getCommand("pvpevent").setExecutor(new PvPEventCommand());

        //Worker for managing warp-out timings
        Bukkit.getScheduler().runTaskTimer(StaticPrisons.getInstance(), () -> {
            for (Map.Entry<Player, Long> entry : playerWarpOutIn.entrySet()) {
                if (entry.getValue() > 0 && entry.getValue() <= 5)
                    entry.getKey().sendMessage(PREFIX + ChatColor.GREEN + "Warping to spawn in " + entry.getValue() + "...");
                entry.setValue(entry.getValue() - 1);
                if (entry.getValue() <= -1) Warps.warpToSpawn(entry.getKey());
            }
        }, 20, 20);
    }


}
