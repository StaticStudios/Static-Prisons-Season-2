package me.staticstudios.prisons.core.enchants;

import me.staticstudios.prisons.core.data.serverData.PlayerData;
import me.staticstudios.prisons.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PrisonEnchants {
    public static Map<UUID, String> playerUUIDToPickaxeID = new HashMap<>();
    public static final PrisonEnchant FORTUNE = new PrisonEnchant("fortune", 15000, BigInteger.valueOf(200)); //Increases your blocks from mining
    public static final PrisonEnchant ORE_SPLITTER = new PrisonEnchant("oreSplitter", 1000, BigInteger.valueOf(5000)); //Chance to get 2x blocks from fortune
    public static final PrisonEnchant TOKENATOR = new PrisonEnchant("tokenator", 5000, BigInteger.valueOf(450)); //Increases the chance to find tokens while mining
    public static final PrisonEnchant KEY_FINDER = new PrisonEnchant("keyFinder",5000, BigInteger.valueOf(400)); //Find crate keys while mining
    public static final PrisonEnchant XP_FINDER = new PrisonEnchant("xpFinder",100, BigInteger.valueOf(100000)); //Find large amounts of XP while mining
    public static final PrisonEnchant JACK_HAMMER = new PrisonEnchant("jackHammer", 20000, BigInteger.valueOf(250)); //Destroy a layer of the mine
    public static final PrisonEnchant DOUBLE_WAMMY = new PrisonEnchant("doubleWammy", 1000, BigInteger.valueOf(7500)); //Chance to destroy an additional mine layer
    public static final PrisonEnchant MULTI_DIRECTIONAL = new PrisonEnchant("multiDirectional",15000, BigInteger.valueOf(500)); //Destroy a + shape in the mine
    public static final PrisonEnchant CASH_GRAB = new PrisonEnchant("cashGrab",100, BigInteger.valueOf(50000)); //Perm x2.0 sell multiplier at max level
    public static final PrisonEnchant MERCHANT = new PrisonEnchant("merchant",2500, BigInteger.valueOf(500)); //Perm x1.25 sell multiplier at max level
    //public static final PrisonEnchant TOKEN_POLISHER = new PrisonEnchant("tokenPolisher",5, BigInteger.valueOf(10000000000L));
    public static final PrisonEnchant CONSISTENCY  = new PrisonEnchant("consistency",5, BigInteger.valueOf(2500000));

    public static final PrisonEnchant HASTE = new PrisonEnchant("haste",10000, BigInteger.valueOf(100)); //Normal effect
    public static final PrisonEnchant SPEED = new PrisonEnchant("speed",3, BigInteger.valueOf(100)); //Normal effect
    public static final PrisonEnchant NIGHT_VISION = new PrisonEnchant("nightVision",1, BigInteger.valueOf(100)); //normal effect
}
