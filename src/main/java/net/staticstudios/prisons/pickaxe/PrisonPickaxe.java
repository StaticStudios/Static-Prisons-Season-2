package net.staticstudios.prisons.pickaxe;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.staticstudios.prisons.customitems.pickaxes.PickaxeTemplates;
import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.enchants.*;
import net.staticstudios.prisons.pickaxe.enchants.handler.PickaxeEnchant;
import net.staticstudios.prisons.utils.ComponentUtil;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class PrisonPickaxe extends EnchantableItemStack {

    private final static Component LORE_DIVIDER = Component.text("---------------").color(ComponentUtil.LIGHT_GRAY);
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
        this(item, true);
    }

    /**
     * Create a completely new PrisonPickaxe with a new UUID
     *
     * @param item The ItemStack that the PrisonPickaxe will be based on. This will be the pickaxe that the player will use.
     * @param register Whether the pickaxe should be registered, if not, it will not be tracked.
     */
    public PrisonPickaxe(ItemStack item, boolean register) {
        super(UUID.randomUUID());
        setItem(item);
        if (register) {
            keyItem();
            register(this);
        }
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
    public static PrisonPickaxe createNewPickaxe() {
        return PickaxeTemplates.DEFAULT.getPickaxe();
    }

    public static long getLevelRequirement(long level) {
        if (level <= 0) return 2500;
        return (long) (2500 * level + level * Math.pow(2.4 * level, 2.4));
    }


    public long getLevel() {
        return level;
    }

    public void setLevel(long level) {
        if (this.level != level) {
            updateItem();
        }
        this.level = level;
    }

    public long getXp() {
        return xp;
    }

    public void setXp(long xp) {
        if (this.xp != xp) {
            updateItem();
        }
        this.xp = xp;
    }

    public long getBlocksBroken() {
        return blocksBroken;
    }

    public void setBlocksBroken(long blocksBroken) {
        if (this.blocksBroken != blocksBroken) {
            updateItem();
        }
        this.blocksBroken = blocksBroken;
    }

    public long getRawBlocksBroken() {
        return rawBlocksBroken;
    }

    public void setRawBlocksBroken(long rawBlocksBroken) {
        if (this.rawBlocksBroken != rawBlocksBroken) {
            updateItem();
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

    public List<Component> getTopLore() {
        return topLore;
    }

    public List<Component> getBottomLore() {
        return bottomLore;
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
        for (Enchantment<?> enchantment : Enchantment.getEnchantsInOrder()) {
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
    private final Map<Class<? extends Enchantment>, Integer> ENCHANT_TIERS = new HashMap<>();

    public int getEnchantmentTier(Enchantment enchantment) {
        return getEnchantmentTier(enchantment.getClass());
    }

    public int getEnchantmentTier(Class<? extends Enchantment> enchantment) {
        return ENCHANT_TIERS.getOrDefault(enchantment, 0);
    }

    public void setEnchantmentTier(Class<? extends Enchantment> enchantment, int tier) {
        ENCHANT_TIERS.put(enchantment, tier);
    }

    @Override
    public Map<Class<? extends Enchantment>, EnchantHolder> getEnchantments() {
        return ENCHANTS;
    }
    //todo: getEnchants() should return the key set only if the holder level is > 0 | isDisabled() should check if the ench is disabled | getEnchant() should return the holder if it exists, otherwise return a new holder with level 0

    @Override
    public boolean setEnchantment(Class<? extends Enchantment> enchantment, int level) {
        if (level < 0) {
            ENCHANTS.remove(enchantment);
            return false;
        }
        ENCHANTS.put(enchantment, new EnchantHolder(Enchantable.getEnchant(enchantment), level, false));
        return true;
    }

    @Override
    public boolean upgrade(Class<? extends Enchantment> enchantment, Player player, int levelsToUpgrade) {
        if (!(Enchantable.getEnchant(enchantment) instanceof PickaxeEnchant enchant)) return false;
        EnchantHolder holder = ENCHANTS.getOrDefault(enchantment, new EnchantHolder(enchant, 0, false));

        levelsToUpgrade = Math.min(levelsToUpgrade, enchant.getMaxLevel(getEnchantmentTier(enchantment)) - holder.level());

        if (enchant.getLevelRequirement() > getLevel()) {
            player.sendMessage(enchant.getDisplayName()
                    .append(Component.text(" >> ")
                            .color(ComponentUtil.DARK_GRAY)
                            .decorate(TextDecoration.BOLD))
                    .append(Component.text("Your pickaxe needs to be at least level " + enchant.getLevelRequirement() + " to upgrade this enchantment!")
                            .color(ComponentUtil.RED)));
            return false;
        }

        if (levelsToUpgrade <= 0) {
            player.sendMessage(enchant.getDisplayName()
                    .append(Component.text(" >> ")
                            .color(ComponentUtil.DARK_GRAY)
                            .decorate(TextDecoration.BOLD))
                    .append(Component.text("Your pickaxe is already at the max level for this enchantment!")
                            .color(ComponentUtil.RED)
                            .decoration(TextDecoration.BOLD, false)));
            return false;
        }

        PlayerData playerData = new PlayerData(player);
        final long upgradeCost = levelsToUpgrade * enchant.getUpgradeCost(getEnchantmentLevel(enchantment));

        if (upgradeCost > playerData.getTokens()) {
            player.sendMessage(enchant.getDisplayName()
                    .append(Component.text(" >> ")
                            .color(ComponentUtil.DARK_GRAY)
                            .decorate(TextDecoration.BOLD))
                    .append(Component.text("Your do not have enough tokens to upgrade this enchantment!")
                            .color(ComponentUtil.RED)
                            .decoration(TextDecoration.BOLD, false)));
            return false;
        }

        playerData.removeTokens(upgradeCost);

        setEnchantment(enchantment, holder.level() + levelsToUpgrade);
        onUpgrade(this, player, holder.level() - levelsToUpgrade, holder.level());

        player.sendMessage(enchant.getDisplayName()
                .append(Component.text(" >> ")
                        .color(ComponentUtil.DARK_GRAY)
                        .decorate(TextDecoration.BOLD))
                .append(Component.text("You upgraded your pickaxe!")
                        .color(ComponentUtil.WHITE)
                        .decoration(TextDecoration.BOLD, false)));
        updateItem();

        return true;
    }

    @Override
    public boolean removeEnchantment(Class<? extends Enchantment> enchantment, Player player) {
        ENCHANTS.remove(enchantment);
        if (player != null && player.getInventory().getItemInMainHand().equals(getItem())) {
            Enchantable.getEnchant(enchantment).onUnHold(this, player);
        }
        return true;
    }

    @Override
    public boolean disableEnchantment(Class<? extends Enchantment> enchantment, Player player) {
        EnchantHolder holder = ENCHANTS.getOrDefault(enchantment, new EnchantHolder(Enchantable.getEnchant(enchantment), 0, false));
        ENCHANTS.put(enchantment, new EnchantHolder(holder.enchantment(), holder.level(), true));
        if (holder.level() > 0 && player != null && player.getInventory().getItemInMainHand().equals(getItem())) {
            Enchantable.getEnchant(enchantment).onUnHold(this, player);
        }
        return true;
    }

    @Override
    public boolean enableEnchantment(Class<? extends Enchantment> enchantment, Player player) {
        EnchantHolder holder = ENCHANTS.getOrDefault(enchantment, new EnchantHolder(Enchantable.getEnchant(enchantment), 0, false));
        ENCHANTS.put(enchantment, new EnchantHolder(holder.enchantment(), holder.level(), false));
        if (holder.level() > 0 && player != null && player.getInventory().getItemInMainHand().equals(getItem())) {
            Enchantable.getEnchant(enchantment).onHold(this, player);
        }
        return true;
    }
}
