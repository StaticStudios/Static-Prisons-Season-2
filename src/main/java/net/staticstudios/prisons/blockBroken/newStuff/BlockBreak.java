package net.staticstudios.prisons.blockBroken.newStuff;

import com.sk89q.worldedit.math.BlockVector3;
import net.staticstudios.mines.StaticMine;
import net.staticstudios.prisons.pickaxe.PrisonPickaxe;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class BlockBreak {

    @NotNull private Player player;
    @NotNull private Location location;
    @Nullable private PrisonPickaxe pickaxe;
    private StaticMine mine;
    public StaticMine getMine() { return mine; }

    private boolean isSimulated = false;
    public void setIsSimulated(boolean b) { isSimulated = b; }
    public boolean getIsSimulated() { return isSimulated; }
    private boolean isCanceled = false;
    public void cancel() { isCanceled = true; }
    public boolean isCanceled() { return isCanceled; }

    private List<Consumer<BlockBreak>> beforeBreak_event = new LinkedList<>();
    private List<Consumer<BlockBreak>> onBreak_event = new LinkedList<>();
    private List<Consumer<BlockBreak>> afterBreak_event = new LinkedList<>();

    public void beforeBreak(Consumer<BlockBreak> b) {
        if (b == null) return;
        beforeBreak_event.add(b);
    }
    public void onBreak(Consumer<BlockBreak> b) {
        if (b == null) return;
        onBreak_event.add(b);
    }
    public void afterBreak(Consumer<BlockBreak> b) {
        if (b == null) return;
        afterBreak_event.add(b);
    }

    public BlockBreak(@NotNull Location location, @NotNull Player player, @Nullable PrisonPickaxe pickaxe) {
        this.location = location;
        this.player = player;
        this.pickaxe = pickaxe;

        for (StaticMine m : StaticMine.getAllMines()) {
            if (!m.getWorld().equals(location.getWorld())) continue;
            BlockVector3 minPoint = m.getMinVector();
            BlockVector3 maxPoint = m.getMaxVector();
            if (minPoint.getBlockX() <= location.getBlockX() &&
                    maxPoint.getBlockX() >= location.getBlockX() &&
                    minPoint.getBlockZ() <= location.getBlockZ() &&
                    maxPoint.getBlockZ() >= location.getBlockZ() &&
                    minPoint.getBlockY() <= location.getBlockY() &&
                    maxPoint.getBlockY() >= location.getBlockY()) {
                mine = m;
                break;
            }
        }
    }
    public void handle(@Nullable Consumer<BlockBreak> defaultOnBreakAction) {
        //Give a chance for any listeners to add on events
        new HandleBlockBreakEvent(this).callEvent();
        if (defaultOnBreakAction != null) onBreak_event.add(defaultOnBreakAction);

        for (Consumer<BlockBreak> b : beforeBreak_event) {
            b.accept(this);
            if (isCanceled) return;
        }
        for (Consumer<BlockBreak> b : onBreak_event) {
            b.accept(this);
            if (isCanceled) return;
        }
        for (Consumer<BlockBreak> b : afterBreak_event) {
            b.accept(this);
            if (isCanceled) return;
        }

    }

}
