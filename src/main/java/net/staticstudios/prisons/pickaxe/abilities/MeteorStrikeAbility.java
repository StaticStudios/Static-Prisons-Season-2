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

public class MeteorStrikeAbility extends BaseAbility { //todo

    public MeteorStrikeAbility() {
        super("meteorStrike", "&6&lMeteor Strike", 15, BigInteger.valueOf(15), 1000 * 60 * 300,
                "&oSend meteors through the mine",
                "&odestroying all blocks in their path!",
                "",
                "&aEach upgrade will increase the amount of meteors!",
                "",
                "Cool down: &c" + PrisonUtils.formatTime(1000 * 60 * 300));
    }

    //todo: functionality

    @Override
    public int getTimesToTick(int level) {
        return 5 * 20 + (level * 20 * 5); //Default 10 seconds + 5 seconds per level
    }
}
