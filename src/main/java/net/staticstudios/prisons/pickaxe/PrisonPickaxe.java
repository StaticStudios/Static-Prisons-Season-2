package net.staticstudios.prisons.pickaxe;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.enchants.*;
import net.staticstudios.prisons.pickaxe.enchants.handler.PickaxeEnchants;
import net.staticstudios.prisons.utils.ComponentUtil;
import net.staticstudios.prisons.utils.PrisonUtils;
import net.staticstudios.prisons.utils.items.SpreadOutExecution;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class PrisonPickaxe extends EnchantableItemStack implements SpreadOutExecution {

    static {
        EnchantableItemStack.setNamespacedKey(PrisonPickaxe.class, new NamespacedKey(StaticPrisons.getInstance(), "prisonPickaxe"));
    }

//    private static final Map<UUID, PrisonPickaxe> UUID_TO_PICKAXE_MAP = new HashMap<>();
//    private static final NamespacedKey PICKAXE_KEY = new NamespacedKey(StaticPrisons.getInstance(), "pickaxeUUID");
//
    private final static Component LORE_DIVIDER = Component.text("---------------").color(ComponentUtil.LIGHT_GRAY);
//    private final String uuidAsString;
//    private final Map<String, Integer> enchLevelMap = new HashMap<>();
//    private final Map<String, Integer> enchTierMap = new HashMap<>();
//    private final Map<String, Integer> abilityLevelMap = new HashMap<>();
//    private final Map<String, Long> lastUsedAbilityAtMap = new HashMap<>();
//    private final Set<String> disabledEnchants = new HashSet<>();
    private long level = 0;
    private long xp = 0;
    private long blocksBroken = 0;
    private long rawBlocksBroken = 0;
    private Component displayName = PickaxeManager.DEFAULT_PICKAXE_NAME;
    private List<Component> topLore = new ArrayList<>();
    private List<Component> bottomLore = new ArrayList<>();

    /**
     * Construct a new PrisonPickaxe from a KNOWN uuid
     *
     * @param uuid A UUID (as a string) that is known to be a PrisonPickaxe. The ItemStack may or may not be known at this time but one does exist with this UUID.
     */
    public PrisonPickaxe(UUID uuid) {
        super(uuid);
        register(this);
    }

    /**
     * Create a completely new PrisonPickaxe with a new UUID
     *
     * @param item The ItemStack that the PrisonPickaxe will be based on. This will be the pickaxe that the player will use.
     */
    public PrisonPickaxe(ItemStack item) {
        super(UUID.randomUUID());
        setItem(item);
        keyItem();
        register(this);
    }

    /**
     * Get a PrisonPickaxe from an ItemStack. The ItemStack needs to already have been used to create a PrisonPickaxe at some point.
     *
     * @param item The ItemStack to get the PrisonPickaxe from
     * @return The PrisonPickaxe, or null if it doesn't exist
     */
    public static PrisonPickaxe fromItem(ItemStack item) {
        return fromItem(PrisonPickaxe.class, item);
    }

    /**
     * Simple check to see if the ItemStack has been used to create a PrisonPickaxe at some point.
     *
     * @param item The ItemStack to check
     * @return True if it has been used to create a PrisonPickaxe at some point, false otherwise
     */
    public static boolean checkIsPrisonPickaxe(ItemStack item) {
        return isEnchantable(PrisonPickaxe.class, item);
    }

    /**
     * Create a new PrisonPickaxe & ItemStack.
     *
     * @return A diamond pickaxe with an associated PrisonPickaxe
     */
    public static ItemStack createNewPickaxe() {
        ItemStack item = new ItemStack(Material.DIAMOND_PICKAXE);
        PrisonPickaxe pickaxe = new PrisonPickaxe(item);
        ItemMeta meta = item.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addEnchant(Enchantment.DIG_SPEED, 100, true);
        item.setItemMeta(meta);
        pickaxe.setEnchantsLevel(PickaxeEnchants.FORTUNE, 5);
        pickaxe.setEnchantsLevel(PickaxeEnchants.DOUBLE_FORTUNE, 5);
        pickaxe.setEnchantsLevel(PickaxeEnchants.TOKENATOR, 1);
        pickaxe.setEnchantsLevel(PickaxeEnchants.JACK_HAMMER, 1);
        updateLore(item);
        return item;
    }

    //BASE = 2500
    //ROI = 2.4
    //BASE * lvl + lvl * (ROI * lvl)^ROI
    public static long getLevelRequirement(long level) {
        if (level <= 0) return 2500;
        return (long) (2500 * level + level * Math.pow(2.4 * level, 2.4));
    }


    public long getLevel() {
        return level;
    }

    public void setLevel(long level) {
        if (this.level != level) {
            queueExecution();
        }
        this.level = level;
    }

    public long getXp() {
        return xp;
    }

    public void setXp(long xp) {
        if (this.xp != xp) {
            queueExecution();
        }
        this.xp = xp;
    }

    public long getBlocksBroken() {
        return blocksBroken;
    }

    public void setBlocksBroken(long blocksBroken) {
        if (this.blocksBroken != blocksBroken) {
            queueExecution();
        }
        this.blocksBroken = blocksBroken;
    }

    public long getRawBlocksBroken() {
        return rawBlocksBroken;
    }

    public void setRawBlocksBroken(long rawBlocksBroken) {
        if (this.rawBlocksBroken != rawBlocksBroken) {
            queueExecution();
        }
        this.rawBlocksBroken = rawBlocksBroken;
    }

    void calcLevel() {
        long level = this.level;
        while (getXp() >= getLevelRequirement(level)) level++;
        this.level = level;
    }

    public void addXp(long xp) {
        if (this.xp >= getLevelRequirement(level)) calcLevel(); //The pickaxe should level up
        setXp(this.xp + xp);
    }

    public void addBlocksBroken(long blocksBroken) {
        setBlocksBroken(this.blocksBroken + blocksBroken);
    }

    public void addRawBlocksBroken(long rawBlocksBroken) {
        setRawBlocksBroken(this.rawBlocksBroken + rawBlocksBroken);
    }

    public Component getName() {
        return displayName;
    }

    public void setName(Component name) {
        displayName = name;
    }

    public Component getNameAsMessagePrefix() {
        return Component.empty().append(displayName).append(Component.text(" >> ").color(ComponentUtil.DARK_GRAY).decorate(TextDecoration.BOLD));
    }

    public PrisonPickaxe setTopLore(List<Component> topLore) {
        this.topLore = topLore;
        return this;
    }

    public PrisonPickaxe setBottomLore(List<Component> bottomLore) {
        this.bottomLore = bottomLore;
        return this;
    }

    @Override
    public void updateItemName(ItemMeta meta) {
        meta.displayName(Component.empty().append(displayName)
                .append(Component.text(" [" + PrisonUtils.addCommasToNumber(rawBlocksBroken) + " Blocks Mined]")
                        .color(ComponentUtil.LIGHT_GRAY))
                .decoration(TextDecoration.ITALIC, false));
    }

    @Override
    public void updateItemLore(ItemMeta meta) {
        List<Component> lore = new ArrayList<>();
        if (topLore != null && !topLore.isEmpty()) {
            lore.addAll(topLore);
        }

        //Stats lore
        lore.addAll(List.of(
                Component.empty().append(Component.text("Level: ").color(ComponentUtil.GREEN))
                        .append(Component.text(PrisonUtils.addCommasToNumber(level)).color(ComponentUtil.WHITE)),
                Component.empty().append(Component.text("Experience: ").color(ComponentUtil.GREEN))
                        .append(Component.text(PrisonUtils.prettyNum(xp) + " / " + PrisonUtils.prettyNum(getLevelRequirement(level))).color(ComponentUtil.WHITE)),
                Component.empty().append(Component.text("Blocks Mined: ").color(ComponentUtil.GREEN))
                        .append(Component.text(PrisonUtils.addCommasToNumber(rawBlocksBroken)).color(ComponentUtil.WHITE)),
                Component.empty().append(Component.text("Blocks Broken: ").color(ComponentUtil.GREEN))
                        .append(Component.text(PrisonUtils.addCommasToNumber(blocksBroken)).color(ComponentUtil.WHITE))
        ));
        lore.add(LORE_DIVIDER);

        //Enchantment lore
        for (Enchantment<?> enchantment : ConfigurableEnchantment.getEnchantsInOrder()) {
            int level = getEnchantmentLevel(enchantment);
            if (level <= 0) continue;
            lore.add(enchantment.getNameAsComponent()
                    .append(Component.text(':'))
                    .append(Component.text(' ' + PrisonUtils.addCommasToNumber(level))).color(ComponentUtil.AQUA));
        }

//        lore.addAll(buildAbilityLore());

        if (bottomLore != null && !bottomLore.isEmpty()) {
            lore.addAll(bottomLore);
        }
        lore.replaceAll(line -> line.decoration(TextDecoration.ITALIC, false));

        meta.lore(lore);
    }

    public int getEnchantmentTier(String id) {
        //todo
        return 1;
    }


    /**
     * Remove this pickaxe from the internal mapping, this should only be called on pickaxes that are "templates" and will only ever be used to view a preview of the ItemStack
     * <p>
     * Once a pickaxe is removed, it can no longer be used by a player
     */
    @Deprecated //delete this method
    public PrisonPickaxe delete() {
        UUID_TO_PICKAXE_MAP.remove(uuid);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrisonPickaxe that = (PrisonPickaxe) o;
        return Objects.equals(getUid(), that.getUid());
    }

    @Override
    public int hashCode() {
        return getUid().hashCode();
    }

    private final Map<Class<? extends Enchantment>, EnchantHolder> ENCHANTS = new HashMap<>();

    @Override
    public Map<Class<? extends Enchantment>, EnchantHolder> getEnchantments() {
        return ENCHANTS;
    }

    @Override
    public boolean setEnchantment(Class<? extends Enchantment> enchantment, int level) {
        ENCHANTS.put(enchantment, new EnchantHolder(Enchantable.getEnchant(enchantment), level, false));
        return true;
    }

    @Override
    public boolean removeEnchantment(Class<? extends Enchantment> enchantment) {
        ENCHANTS.remove(enchantment);
        return true;
    }

    @Override
    public boolean disableEnchantment(Class<? extends Enchantment> enchantment) {
        return false;
    }

    @Override
    public boolean enableEnchantment(Class<? extends Enchantment> enchantment) {
        return false;
    }
}
