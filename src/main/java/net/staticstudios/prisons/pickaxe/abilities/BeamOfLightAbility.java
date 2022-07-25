package net.staticstudios.prisons.pickaxe.abilities;

import net.staticstudios.mines.StaticMine;
import net.staticstudios.prisons.blockBroken.BlockBreak;
import net.staticstudios.prisons.mineBombs.MineBomb;
import net.staticstudios.prisons.mines.MineBlock;
import net.staticstudios.prisons.pickaxe.abilities.handler.BaseAbility;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.Location;
import org.bukkit.Material;

import java.math.BigInteger;
import java.util.Map;

public class BeamOfLightAbility extends BaseAbility { //todo

    public BeamOfLightAbility() {
        super("beamOfLight", "&d&lBeam O' Light", 10, BigInteger.valueOf(12), 1000 * 60 * 60 * 4,
                "&oSend a pulse of light through the mine every",
                "&osecond destroying every block in its path!",
                "&oIt will grow in size with each pulse.",
                "",
                "&aEach upgrade will increase the amount of pulses!",
                "",
                "Cool down: &c" + PrisonUtils.formatTime(1000 * 60 * 60 * 4));
    }

    //todo: functionality

    @Override
    public int getTimesToTick(int level) {
        return 4 * 20 + (level * 20 * 2); //Default 4 seconds + 2 seconds per level
    }
}