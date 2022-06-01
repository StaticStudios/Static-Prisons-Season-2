package net.staticstudios.prisons.enchants;

import net.staticstudios.prisons.blockBroken.PrisonBlockBroken;
import net.staticstudios.prisons.enchants.handler.BaseEnchant;
import net.staticstudios.prisons.enchants.handler.PrisonPickaxe;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

import java.math.BigInteger;

public class ConsistencyEnchant extends BaseEnchant {
    public ConsistencyEnchant() {
        super("consistency", "&d&lConsistency", 5, BigInteger.valueOf(2500000), "&7+1% token multi every 2 mins of consistent mining", "&7Increases your max multi by 10% for every level", "&7Multiplier expires after 2 minutes of not mining");
    } //todo
}
