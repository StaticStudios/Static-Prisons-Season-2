package me.staticstudios.prisons.islands.special.robots;

import me.staticstudios.prisons.Main;
import me.staticstudios.prisons.gui.GUI;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Zombie;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;

public abstract class BaseRobot {
    private BaseRobot() {} //Get rid of the constructor
    //Robot rates -- generate resources | per second
    public static final BigDecimal MONEY_GENERATE_BASE = BigDecimal.valueOf(1);
    public static final BigDecimal MONEY_GENERATE_PER_LEVEL = BigDecimal.valueOf(1);
    public static final BigDecimal TOKEN_GENERATE_BASE = BigDecimal.valueOf(1);
    public static final BigDecimal TOKEN_GENERATE_PER_LEVEL = BigDecimal.valueOf(1);
    public static final BigDecimal SHARD_GENERATE_BASE = BigDecimal.valueOf(1);
    public static final BigDecimal SHARD_GENERATE_PER_LEVEL = BigDecimal.valueOf(1);
    //Robot rates -- use oil | per second
    public static final double MONEY_CONSUME_BASE = 0.001;
    public static final double MONEY_CONSUME_PER_LEVEL = 0.001;
    public static final double TOKEN_CONSUME_BASE = 0.001;
    public static final double TOKEN_CONSUME_PER_LEVEL = 0.001;
    public static final double SHARD_CONSUME_BASE = 0.001;
    public static final double SHARD_CONSUME_PER_LEVEL = 0.001;

    public Zombie zombie;
    public PersistentDataContainer PDC;
    public static boolean entityClicked(PlayerInteractEntityEvent e) {
        if (!(e.getRightClicked() instanceof Zombie)) return false;
        if (!e.getRightClicked().getPersistentDataContainer().has(new NamespacedKey(Main.getMain(), "isRobot"), PersistentDataType.INTEGER)) return false;
        GUI.getGUIPage("robotMain").args = e.getRightClicked().getUniqueId().toString();
        GUI.getGUIPage("robotMain").open(e.getPlayer());
        return true;
    }
    public static void spawnRobot(Location loc, String type, Color armorColor, String name) {
        //Create Armor
        ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
        LeatherArmorMeta leatherHelmet = (LeatherArmorMeta) helmet.getItemMeta();
        leatherHelmet.setColor(armorColor);
        leatherHelmet.setUnbreakable(true);
        helmet.setItemMeta(leatherHelmet);
        ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
        LeatherArmorMeta leatherChestplate = (LeatherArmorMeta) chestplate.getItemMeta();
        leatherChestplate.setColor(armorColor);
        chestplate.setItemMeta(leatherChestplate);
        ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
        LeatherArmorMeta leatherLeggings = (LeatherArmorMeta) leggings.getItemMeta();
        leatherLeggings.setColor(armorColor);
        leggings.setItemMeta(leatherLeggings);
        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
        LeatherArmorMeta leatherBoots = (LeatherArmorMeta) boots.getItemMeta();
        leatherBoots.setColor(armorColor);
        boots.setItemMeta(leatherBoots);

        //Spawn Zombie
        Zombie zombie = loc.getWorld().spawn(loc, Zombie.class);
        zombie.setBaby();

        zombie.setCustomName(name);
        zombie.setCustomNameVisible(true);
        zombie.setAI(false);
        zombie.setSilent(true);
        zombie.setInvulnerable(true);

        zombie.getEquipment().setItemInMainHand(new ItemStack(Material.DIAMOND_PICKAXE));
        zombie.getEquipment().setHelmet(helmet);
        zombie.getEquipment().setChestplate(chestplate);
        zombie.getEquipment().setLeggings(leggings);
        zombie.getEquipment().setBoots(boots);

        zombie.getPersistentDataContainer().set(new NamespacedKey(Main.getMain(), "isRobot"), PersistentDataType.INTEGER, 1);

        //Default stats
        zombie.getPersistentDataContainer().set(new NamespacedKey(Main.getMain(), "type"), PersistentDataType.STRING, type);
        zombie.getPersistentDataContainer().set(new NamespacedKey(Main.getMain(), "level"), PersistentDataType.INTEGER, 1);
        zombie.getPersistentDataContainer().set(new NamespacedKey(Main.getMain(), "oil"), PersistentDataType.DOUBLE, 0d);
        zombie.getPersistentDataContainer().set(new NamespacedKey(Main.getMain(), "value"), PersistentDataType.STRING, "0");
        zombie.getPersistentDataContainer().set(new NamespacedKey(Main.getMain(), "lastUpdated"), PersistentDataType.LONG, Instant.now().getEpochSecond());
    }
    public static BaseRobot createRobotFromEntity(Zombie zombie) {
        BaseRobot robot = new BaseRobot() {};
        if (!zombie.getPersistentDataContainer().has(new NamespacedKey(Main.getMain(), "isRobot"), PersistentDataType.INTEGER)) return null; //Not a robot
        robot.zombie = zombie;
        robot.PDC = zombie.getPersistentDataContainer();
        return robot;
    }
    public void setLevel(int amount) {
        PDC.set(new NamespacedKey(Main.getMain(), "level"), PersistentDataType.INTEGER, Math.min(5, amount));
    }
    public int getLevel() {
        return PDC.get(new NamespacedKey(Main.getMain(), "level"), PersistentDataType.INTEGER);
    }
    public String getType() {
        return PDC.get(new NamespacedKey(Main.getMain(), "type"), PersistentDataType.STRING);
    }
    public void setOil(double amount) {
        PDC.set(new NamespacedKey(Main.getMain(), "oil"), PersistentDataType.DOUBLE, amount);
    }
    public double getOil() {
        return PDC.get(new NamespacedKey(Main.getMain(), "oil"), PersistentDataType.DOUBLE);
    }
    public void addOil(double amount) {
        setOil(getOil() + amount);
    }
    public void removeOil(double amount) {
        setOil(getOil() - amount);
    }
    public void setValue(BigInteger amount) {
        PDC.set(new NamespacedKey(Main.getMain(), "value"), PersistentDataType.STRING, amount.toString());
    }
    public BigInteger getValue() {
        return new BigInteger(PDC.get(new NamespacedKey(Main.getMain(), "value"), PersistentDataType.STRING));
    }
    public void addValue(BigInteger amount) {
        setValue(getValue().add(amount));
    }
    public void removeValue(BigInteger amount) {
        setValue(getValue().subtract(amount));
    }

    /**
     * Calculates the values of resources used since this method was last called
     */
    public void update() {
        switch (getType()) {
            case "money" -> {
                long seconds = getSecondsSinceLastUpdated();
                double oilUsed = Math.min(getOil(), (MONEY_CONSUME_BASE + MONEY_CONSUME_PER_LEVEL * (getLevel() - 1)) * seconds);
                removeOil(oilUsed);
                addValue(MONEY_GENERATE_BASE.add(MONEY_GENERATE_PER_LEVEL.multiply(BigDecimal.valueOf(getLevel() - 1))).multiply(BigDecimal.valueOf(oilUsed / MONEY_CONSUME_BASE + MONEY_CONSUME_PER_LEVEL * (getLevel() - 1))).toBigInteger());
            }
            case "token" -> {
                long seconds = getSecondsSinceLastUpdated();
                double oilUsed = Math.min(getOil(), (TOKEN_CONSUME_BASE + TOKEN_CONSUME_PER_LEVEL * (getLevel() - 1)) * seconds);
                removeOil(oilUsed);
                addValue(TOKEN_GENERATE_BASE.add(TOKEN_GENERATE_PER_LEVEL.multiply(BigDecimal.valueOf(getLevel() - 1))).multiply(BigDecimal.valueOf(oilUsed / TOKEN_CONSUME_BASE + TOKEN_CONSUME_PER_LEVEL * (getLevel() - 1))).toBigInteger());
            }
            case "shard" -> {
                long seconds = getSecondsSinceLastUpdated();
                double oilUsed = Math.min(getOil(), (SHARD_CONSUME_BASE + SHARD_CONSUME_PER_LEVEL * (getLevel() - 1)) * seconds);
                removeOil(oilUsed);
                addValue(SHARD_GENERATE_BASE.add(SHARD_GENERATE_PER_LEVEL.multiply(BigDecimal.valueOf(getLevel() - 1))).multiply(BigDecimal.valueOf(oilUsed / SHARD_CONSUME_BASE + SHARD_CONSUME_PER_LEVEL * (getLevel() - 1))).toBigInteger());
            }
        }
        //update the timestamp
        zombie.getPersistentDataContainer().set(new NamespacedKey(Main.getMain(), "lastUpdated"), PersistentDataType.LONG, Instant.now().getEpochSecond());
    }
    public long getSecondsSinceLastUpdated() {
        return Instant.now().getEpochSecond() - zombie.getPersistentDataContainer().get(new NamespacedKey(Main.getMain(), "lastUpdated"), PersistentDataType.LONG);
    }
}
