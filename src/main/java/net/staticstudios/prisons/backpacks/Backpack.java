package net.staticstudios.prisons.backpacks;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.staticstudios.prisons.backpacks.config.BackpackConfig;
import net.staticstudios.prisons.utils.ComponentUtil;
import net.staticstudios.prisons.utils.PrisonUtils;
import net.staticstudios.prisons.utils.items.SpreadOutExecution;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.UUID;

public class Backpack implements SpreadOutExecution {

    /**
     * The UUID of this backpack, as a string.
     */
    final String uuid;
    /**
     * The ItemStack representing this backpack.
     */
    ItemStack item;
    int tier = 1;
    long size = 0;
    long itemCount;
    long value;

    /**
     * Create a new Backpack object.
     *
     * @param item The ItemStack to represent this backpack.
     * @param tier The tier of this backpack, this will determine the skin, name, and max capacity of the new backpack.
     */
    public Backpack(ItemStack item, int tier) {
        uuid = UUID.randomUUID().toString();
        this.item = item;
        this.tier = tier;
        item.editMeta(meta -> {
            meta.getPersistentDataContainer().set(BackpackConfig.BACKPACK_KEY, PersistentDataType.STRING, uuid);
            meta.setCustomModelData(100 + tier - 1);
        });
        updateItemNow();
        BackpackManager.ALL_BACKPACKS.put(uuid, this);
    }

    /**
     * Create a new Backpack object without knowing the ItemStack.
     *
     * @param uuid The UUID of the backpack, this should be the same as the UUID oo the ItemStack that represents this, so that this new instance can be retrieved later.
     */
    Backpack(String uuid) {
        this.uuid = uuid;
        BackpackManager.ALL_BACKPACKS.put(uuid, this);
    }

    /**
     * Get a Backpack object from an ItemStack.
     * This will check to see if the ItemStack has a backpack UUID stored on it, if so, it will grab the backpack from the UUID.
     *
     * @param item The ItemStack to get the backpack from.
     * @return The Backpack object, or null if there is no backpack on the ItemStack.
     */
    @Nullable
    public static Backpack fromItem(ItemStack item) {
        if (item == null) return null;
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return null;
        Backpack backpack = fromUUID(meta.getPersistentDataContainer().get(BackpackConfig.BACKPACK_KEY, PersistentDataType.STRING));
        if (backpack == null) return null;
        backpack.item = item;
        return backpack;
    }

    /**
     * Get a Backpack object from a UUID.
     *
     * @param uuid The UUID to get the backpack from.
     * @return The Backpack object, or null if there is no backpack with the UUID.
     */
    @Nullable
    private static Backpack fromUUID(String uuid) {
        return BackpackManager.ALL_BACKPACKS.get(uuid);
    }

    /**
     * Get this backpack's UUID as a string.
     *
     * @return The UUID of this backpack as a string.
     */
    @NotNull
    public String getUUID() {
        return uuid;
    }

    /**
     * Get the ItemStack representing this backpack.
     *
     * @return The ItemStack representing this backpack, or null if the ItemStack is unknown.
     */
    @Nullable
    public ItemStack getItem() {
        return item;
    }

    /**
     * Get the tier of this backpack.
     *
     * @return The tier of this backpack.
     */
    @Range(from = 1, to = 10)
    public int getTier() {
        return tier;
    }

    /**
     * Get the amount of blocks this backpack can hold.
     *
     * @return The amount of blocks this backpack can hold.
     */
    public long getCapacity() {
        return size;
    }

    /**
     * Set a new max capacity for this backpack.
     * Note, calling this method will also update the ItemStack representing this backpack. Call {@link #setCapacity(long, boolean)} to avoid updating the ItemStack.
     *
     * @param size The new max capacity for this backpack.
     */
    public void setCapacity(long size) {
        setCapacity(size, true);
    }

    /**
     * Get the amount of blocks currently stored in this backpack.
     * Note, the blocks that are "stored" in this backpack are only stored as a count, along with their total value.
     *
     * @return The amount of blocks currently stored in this backpack.
     */
    public long getItemCount() {
        return itemCount;
    }

    /**
     * Set the amount of blocks currently stored in this backpack.
     * Note, the blocks that are "stored" in this backpack are only stored as a count, along with their total value.
     * Note, calling this method will also update the ItemStack representing this backpack. Call {@link #setItemCount(long, boolean)} to avoid updating the ItemStack.
     *
     * @param itemCount The amount of blocks currently stored in this backpack.
     */
    public void setItemCount(long itemCount) {
        setItemCount(itemCount, true);
    }

    /**
     * Get the total value of all blocks currently stored in this backpack.
     * Note, the blocks that are "stored" in this backpack are only stored as a count, along with their total value.
     *
     * @return The total value of all blocks currently stored in this backpack.
     */
    public long getValue() {
        return value;
    }

    /**
     * Set the total value of all blocks currently stored in this backpack.
     * Note, the blocks that are "stored" in this backpack are only stored as a count, along with their total value.
     * Note, calling this method will also update the ItemStack representing this backpack. Call {@link #setValue(long, boolean)} to avoid updating the ItemStack.
     *
     * @param value The total value of all blocks currently stored in this backpack.
     */
    public void setValue(long value) {
        setValue(value, true);
    }

    /**
     * Check to see if this backpack can hold more blocks.
     *
     * @return True if this backpack can hold more blocks, false otherwise.
     */
    public boolean isFull() {
        return itemCount >= size;
    }

    /**
     * Set a new max capacity for this backpack.
     *
     * @param size       The new max capacity for this backpack.
     * @param updateItem Whether to update the ItemStack representing this backpack.
     */
    public void setCapacity(long size, boolean updateItem) {
        this.size = size;
        if (updateItem) {
            updateItem();
        }
    }

    /**
     * Set the amount of blocks currently stored in this backpack.
     * Note, the blocks that are "stored" in this backpack are only stored as a count, along with their total value.
     *
     * @param itemCount  The amount of blocks currently stored in this backpack.
     * @param updateItem Whether to update the ItemStack representing this backpack.
     */
    public void setItemCount(long itemCount, boolean updateItem) {
        this.itemCount = itemCount;
        if (updateItem) {
            updateItem();
        }
    }

    /**
     * Set the total value of all blocks currently stored in this backpack.
     * Note, the blocks that are "stored" in this backpack are only stored as a count, along with their total value.
     *
     * @param value      The total value of all blocks currently stored in this backpack.
     * @param updateItem Whether to update the ItemStack representing this backpack.
     */
    public void setValue(long value, boolean updateItem) {
        this.value = value;
        if (updateItem) {
            updateItem();
        }
    }

    /**
     * Queue a task to update the ItemStack representing this backpack.
     * Call {@link #updateItemNow()} to update the ItemStack immediately.
     */
    public void updateItem() {
        queueExecution();
    }

    /**
     * Update the ItemStack representing this backpack.
     * Call {@link #updateItem()} to queue a task to update the ItemStack at a later time.
     */
    public void updateItemNow() {
        updateItemName();
        updateItemLore();
    }

    /**
     * Update the name of the ItemStack representing this backpack.
     */
    private void updateItemName() {
        if (item == null) return; //Ensure that the item is known before trying to update it.
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;

        meta.displayName(BackpackConfig.tier(tier).name()
                .append(Component.text(" [" + PrisonUtils.prettyNum(itemCount) + "/" + PrisonUtils.prettyNum(size) + " | ").color(ComponentUtil.LIGHT_GRAY))
                .append(Component.text(BigDecimal.valueOf(size <= 0 ? 100 : (double) itemCount / size * 100).setScale(2, RoundingMode.FLOOR) + "% Full").color(isFull() ? ComponentUtil.RED : ComponentUtil.LIGHT_GRAY))
                .append(Component.text("]").color(ComponentUtil.LIGHT_GRAY))
                .decoration(TextDecoration.ITALIC, false));
        item.setItemMeta(meta);
    }

    /**
     * Update the lore of the ItemStack representing this backpack.
     */
    private void updateItemLore() {
        if (item == null) return; //Ensure that the item is known before trying to update it.
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;

        meta.lore(List.of(
                BackpackConfig.DESCRIPTION_TEXT,
                Component.empty(),
                Component.empty().append(Component.text("| ").color(ComponentUtil.GREEN).decorate(TextDecoration.BOLD)).append(Component.text("Tier: ").color(ComponentUtil.GREEN)).append(Component.text(PrisonUtils.addCommasToNumber(tier)).color(ComponentUtil.WHITE)).decoration(TextDecoration.ITALIC, false),
                Component.empty().append(Component.text("| ").color(ComponentUtil.GREEN).decorate(TextDecoration.BOLD)).append(Component.text("Capacity: ").color(ComponentUtil.GREEN)).append(Component.text(PrisonUtils.addCommasToNumber(size) + " Items").color(ComponentUtil.WHITE)).decoration(TextDecoration.ITALIC, false),
                Component.empty(),
                BackpackConfig.CLICK_TO_UPGRADE_TEXT
        ));
        item.setItemMeta(meta);
    }

    @Override
    public void execute() {
        updateItemNow();
    }


}
