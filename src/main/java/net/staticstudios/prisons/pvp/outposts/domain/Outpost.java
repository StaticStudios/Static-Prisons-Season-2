package net.staticstudios.prisons.pvp.outposts.domain;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.staticstudios.prisons.gangs.Gang;
import net.staticstudios.prisons.pvp.outposts.OutpostManager;
import net.staticstudios.prisons.pvp.outposts.OutpostTypes;
import net.staticstudios.prisons.utils.Prefix;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

import static net.staticstudios.prisons.pvp.PvPManager.PVP_WORLD;

public abstract class Outpost implements Runnable {

    private final UUID id;
    private final ProtectedRegion region;
    private final OutpostTypes type;
    private String name;
    private Gang currentGang;
    private int capturePercentage;
    private int taskId;
    private boolean contested;


    public Outpost(String id, String name, Gang currentGang, ProtectedRegion region, OutpostTypes type) {
        this.id = UUID.fromString(id);
        this.name = name;
        this.currentGang = currentGang;
        this.region = region;
        this.type = type;
    }

    public Outpost(UUID id, String name, Gang currentGang, ProtectedRegion region, OutpostTypes type, int capturePercentage) {
        this.id = id;
        this.name = name;
        this.currentGang = currentGang;
        this.region = region;
        this.type = type;
        this.capturePercentage = capturePercentage;
    }

    public Outpost(UUID id, String name, ProtectedRegion region, OutpostTypes type) {
        this(id.toString(), name, null, region, type);
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        OutpostManager.saveConfig();
    }

    public Gang getCurrentGang() {
        return currentGang;
    }

    public void setCurrentGang(Gang newGang) {
        currentGang = newGang;
        OutpostManager.saveConfig();
    }

    public ProtectedRegion getRegion() {
        return region;
    }

    public OutpostTypes getType() {
        return type;
    }

    public int getCapturePercentage() {
        return capturePercentage;
    }

    public void giveBoost(Gang gang) {
        throw new UnsupportedOperationException("This outpost does not support boost");
    }

    @Override
    public void run() {
        RegionManager container = OutpostManager.getRegionManager();
        assert container != null;

        if (currentGang != null) {
            giveBoost(currentGang);
        }

        List<Player> players = PVP_WORLD.getPlayers().stream()
                .filter(player -> {
                    ApplicableRegionSet regions = container.getApplicableRegions(BukkitAdapter.adapt(player).getLocation().toBlockPoint());

                    return regions.getRegions().stream().anyMatch(region -> region.getId().equals(this.region.getId()));
                }).toList();

        if (players.isEmpty()) {
            return;
        }


        if (players.stream().map(Gang::getGang).distinct().count() > 1) {
            contested = true;
            return;
        }

        contested = false;

        Gang gang = players.stream().map(Gang::getGang).findFirst().get();

        if (currentGang == null) {
            if (capturePercentage < 100) {
                capturePercentage += 1;
            }

            if (capturePercentage == 100) {
                setCurrentGang(gang);
                Audience.audience(players).sendMessage(Prefix.OUTPOST.append(Component.text("Captured ").append(Component.text(name))));
            }

        } else if (!gang.getUuid().equals(currentGang.getUuid())) {
            if (capturePercentage == 0) {
                capturePercentage += 1;
                setCurrentGang(null);
            } else if (currentGang == null && capturePercentage >= 100) {
                setCurrentGang(gang);
            } else {
                capturePercentage -= 1;
            }

        } else {
            if (capturePercentage < 100) {
                capturePercentage += 1;
            }
        }

        if (capturePercentage % 10 == 0 && capturePercentage != 100) {
            Audience.audience(players).sendMessage(Prefix.OUTPOST.append(Component.text("Capturing: " + capturePercentage + "%")));
        }
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public int getTaskId() {
        return taskId;
    }

    public boolean isContested() {
        return contested;
    }
}
