package net.staticstudios.prisons.pickaxe.abilities.handler;

import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.blockbreak.BlockBreak;
import net.staticstudios.prisons.pickaxe.PrisonPickaxe;
import net.staticstudios.prisons.pickaxe.abilities.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

public class PickaxeAbilities {

    public static BaseAbility LIGHTNING_STRIKE;
    public static BaseAbility SNOW_FALL;
    public static BaseAbility BEAM_OF_LIGHT;
    public static BaseAbility METEOR_STRIKE;
    public static BaseAbility BLACK_HOLE;


    public static List<BaseAbility> ORDERED_ABILITIES = new ArrayList<>();
    public static Map<String, BaseAbility> abilityIDToAbility = new HashMap<>();

    public static void init() {
        LIGHTNING_STRIKE = new LightningStrikeAbility();
        SNOW_FALL = new SnowFallAbility();
        BEAM_OF_LIGHT = new BeamOfLightAbility();
        METEOR_STRIKE = new MeteorStrikeAbility();
        BLACK_HOLE = new BlackHoleAbility();

        ORDERED_ABILITIES.add(LIGHTNING_STRIKE);
        ORDERED_ABILITIES.add(SNOW_FALL);
        ORDERED_ABILITIES.add(BEAM_OF_LIGHT);
        ORDERED_ABILITIES.add(METEOR_STRIKE);
        ORDERED_ABILITIES.add(BLACK_HOLE);

        Bukkit.getScheduler().runTaskTimer(StaticPrisons.getInstance(), BaseAbility::tickActivateAbilities, 20, 1);
    }

}
