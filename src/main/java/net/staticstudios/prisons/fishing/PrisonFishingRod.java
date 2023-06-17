package net.staticstudios.prisons.fishing;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.enchants.EnchantHolder;
import net.staticstudios.prisons.enchants.Enchantable;
import net.staticstudios.prisons.enchants.EnchantableItemStack;
import net.staticstudios.prisons.enchants.Enchantment;
import net.staticstudios.prisons.pickaxe.PrisonPickaxe;
import net.staticstudios.prisons.utils.ComponentUtil;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class PrisonFishingRod extends EnchantableItemStack {
    private long xp;
    private int level;
    private Component displayName;
    private int itemsCaught;
    private int caughtNothing;
    private double durability = 10;
    private List<Component> cachedLore = null;
    public PrisonFishingRod() {
        super(UUID.randomUUID());
        setItem(new ItemStack(Material.FISHING_ROD));
        assert getItem() != null;
        getItem().editMeta(meta -> {
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE);
            meta.setUnbreakable(true);
        });
        keyItem();

        setName(FishingManager.DEFAULT_NAME);
        updateItemNow();
        register(this);
    }

    public PrisonFishingRod(UUID uuid) {
        super(uuid);

        register(this);
    }

    public PrisonFishingRod(ItemStack item) {
        this(item, true);
    }

    public PrisonFishingRod(ItemStack item, boolean register) {
        super(UUID.randomUUID());
        setItem(item);
        if (register) {
            keyItem();
            register(this);
        }
    }

    public static PrisonFishingRod fromItem(ItemStack item) {
        return fromItem(PrisonFishingRod.class, item);
    }

    public static boolean checkIsPrisonFishingRod(ItemStack item) {
        return isEnchantable(PrisonFishingRod.class, item);
    }

    public static double getMaxDurability(int level) {
        return Math.max(10, 8 + (level * 2));
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
        updateItem();
    }

    public long getXp() {
        return xp;
    }

    public void setXp(long xp) {
        this.xp = xp;
        while (xp >= getXpRequired()) {
            level++;
        }
        updateItem();
    }

    public double getDurability() {
        return durability;
    }

    public void setDurability(double durability) {
        this.durability = durability;
        updateItem();
    }

    public int getItemsCaught() {
        return itemsCaught;
    }

    public void setItemsCaught(int itemsCaught) {
        this.itemsCaught = itemsCaught;
        updateItem();
    }

    public int getCaughtNothing() {
        return caughtNothing;
    }

    public void setCaughtNothing(int caughtNothing) {
        this.caughtNothing = caughtNothing;
        updateItem();
    }

    public Component getName() {
        return displayName;
    }

    public void setName(Component name) {
        displayName = name;
        updateItem();
    }

    private final Map<Class<? extends Enchantment>, EnchantHolder> enchants = new HashMap<>();

    @Override
    public Map<Class<? extends Enchantment>, EnchantHolder> getEnchantmentMap() {
        return enchants;
    }

//    public void onCatchFish(FishCaughtEvent e) {
//
//        //TODO: enchantments
//
//        //TODO: default rewards if enchants havent triggered something
//
//        switch (e.getType()) {
//            case NOTHING -> {
//                setXp(xp + 15);
//                caughtNothing++;
//            }
//            case TOKENS -> {
//                setXp(xp + 25);
//                itemsCaught++;
//            }
//            case SHARDS -> {
//                setXp(xp + 75);
//                itemsCaught++;
//            }
//            case PLAYER_XP -> {
//                setXp(xp + 30);
//                itemsCaught++;
//            }
//            case ITEM -> {
//                setXp(xp + 30);
//                itemsCaught++;
//            }
//        }
//
//        durability -= e.getDurabilityLost();
//
//        updateItem();
//    }

//    public void onCastRod(PlayerFishEvent e) {
//        if (durability <= 0) {
//            e.getPlayer().sendMessage(Component.text("Your fishing rod has broken!"));
//            e.setCancelled(true);
//        }
//
//        e.getHook().setMinWaitTime(2);
//        e.getHook().setMaxWaitTime(10); //todo: temp
//    }

    @Override
    public boolean setEnchantment(Class<? extends Enchantment> enchantment, int level) {
        if (level < 0) {
            enchants.remove(enchantment);
            return false;
        }
        enchants.put(enchantment, new EnchantHolder(Enchantable.getEnchant(enchantment), level, false));
        return true;
    }

    @Override
    public boolean upgrade(Class<? extends Enchantment> enchantment, Player player, int levelsToUpgrade) {
        return false;
    }

    @Override
    public boolean removeEnchantment(Class<? extends Enchantment> enchantment, Player player) {
        enchants.remove(enchantment);
        if (player != null && player.getInventory().getItemInMainHand().equals(getItem())) {
            Enchantable.getEnchant(enchantment).onUnHold(this, player);
        }
        return true;
    }

    @Override
    public boolean disableEnchantment(Class<? extends Enchantment> enchantment, Player player) {
        EnchantHolder holder = enchants.getOrDefault(enchantment, new EnchantHolder(Enchantable.getEnchant(enchantment), 0, false));
        enchants.put(enchantment, new EnchantHolder(holder.enchantment(), holder.level(), true));
        if (holder.level() > 0 && player != null && player.getInventory().getItemInMainHand().equals(getItem())) {
            Enchantable.getEnchant(enchantment).onUnHold(this, player);
        }
        return true;
    }

    @Override
    public boolean enableEnchantment(Class<? extends Enchantment> enchantment, Player player) {
        EnchantHolder holder = enchants.getOrDefault(enchantment, new EnchantHolder(Enchantable.getEnchant(enchantment), 0, false));
        enchants.put(enchantment, new EnchantHolder(holder.enchantment(), holder.level(), false));
        if (holder.level() > 0 && player != null && player.getInventory().getItemInMainHand().equals(getItem())) {
            Enchantable.getEnchant(enchantment).onHold(this, player);
        }
        return true;
    }

    @Override
    public void updateItemName(ItemMeta meta) {
        meta.displayName(displayName.append(Component.text(" [" + itemsCaught + " Items Caught]")
                        .color(ComponentUtil.LIGHT_GRAY).decoration(TextDecoration.BOLD, false))
                .decoration(TextDecoration.ITALIC, false));
    }

    @Override
    public void updateItemLore(ItemMeta meta) {
        updateLore();
        meta.lore(cachedLore);
    }

    public void updateLore() {
        Component durability = Component.text('|');
        Component durabilityBar = Component.empty();

        int durabilityBars = 40;

        for (int i = 0; i < durabilityBars; i++) {
            if (i < (this.durability / getMaxDurability(level) * durabilityBars)) {
                durabilityBar = durabilityBar.append(durability.color(ComponentUtil.GREEN));
            } else {
                durabilityBar = durabilityBar.append(durability.color(ComponentUtil.RED));
            }
        }


        List<Component> lore = new ArrayList<>();
        lore.add(Component.empty().append(Component.text("Level: ").color(ComponentUtil.YELLOW))
                .append(Component.text(PrisonUtils.addCommasToNumber(level)).color(ComponentUtil.WHITE)));
        lore.add(Component.empty().append(Component.text("Experience: ").color(ComponentUtil.YELLOW))
                .append(Component.text(PrisonUtils.prettyNum(getXp()) + " / " + PrisonUtils.prettyNum(getXpRequired())).color(ComponentUtil.WHITE)));
        lore.add(Component.empty().append(Component.text("Items Caught: ").color(ComponentUtil.YELLOW))
                .append(Component.text(PrisonUtils.addCommasToNumber(itemsCaught)).color(ComponentUtil.WHITE)));
//        lore.add(Component.empty().append(Component.text("Caught Nothing: ").color(ComponentUtil.YELLOW))
//                .append(Component.text(PrisonUtils.addCommasToNumber(caughtNothing)).color(ComponentUtil.WHITE)));


        lore.add(PrisonPickaxe.LORE_DIVIDER);

        //Enchantment lore
        for (Enchantment<?> enchantment : Enchantment.getEnchantsInOrder()) {
            int level = getEnchantmentLevel(enchantment);
            if (level <= 0) continue;
            lore.add(enchantment.getNameAsComponent()
                    .append(Component.text(':'))
                    .append(Component.text(' ' + PrisonUtils.addCommasToNumber(level))).color(ComponentUtil.LIGHT_PURPLE));
        }

        lore.add(PrisonPickaxe.LORE_DIVIDER);

        lore.add(Component.text("Durability: ").color(ComponentUtil.YELLOW)
                .append(Component.text('[').color(ComponentUtil.LIGHT_GRAY))
                .append(durabilityBar)
                .append(Component.text(']').color(ComponentUtil.LIGHT_GRAY))
                .append(Component.text(" " + PrisonUtils.addCommasToNumber((int) this.durability) + " / " + PrisonUtils.addCommasToNumber((int) getMaxDurability(level)))
                        .color(ComponentUtil.LIGHT_GRAY)));


        lore.replaceAll(line -> line.decoration(TextDecoration.ITALIC, false));

        cachedLore = lore;
    }

    public List<Component> getLore() {
        if (cachedLore == null) {
            updateLore();
        }

        return cachedLore;
    }

    public long getXpRequired() {
        return getXpRequired(getLevel());
    }


    private static final int BASE_XP_PER_LEVEL = 85;
    private static final double LEVEL_RATE_OF_INCREASE = 1.6;
    public long getXpRequired(int level) {
        if (level < 0) {
            return BASE_XP_PER_LEVEL;
        }
        return (long) ((long) BASE_XP_PER_LEVEL * level + level * Math.pow(LEVEL_RATE_OF_INCREASE * level, LEVEL_RATE_OF_INCREASE));
    }

}
