package net.staticstudios.prisons.lootboxes;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.blockbreak.BlockBreak;
import net.staticstudios.prisons.utils.ComponentUtil;
import net.staticstudios.prisons.utils.ItemUtils;
import net.staticstudios.prisons.utils.Prefix;
import net.staticstudios.prisons.utils.PrisonUtils;
import net.staticstudios.prisons.utils.items.SpreadOutExecution;
import net.staticstudios.prisons.utils.items.SpreadOutExecutor;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public abstract class LootBox implements SpreadOutExecution {

    public static final NamespacedKey LOOT_BOX_KEY = new NamespacedKey(StaticPrisons.getInstance(), "lootboxUUID");
    public static final Component LORE_PREFIX = Component.empty().append(Component.text("| ").color(ComponentUtil.DARK_GRAY).decorate(TextDecoration.BOLD));

    public static final Map<UUID, LootBox> LOOT_BOXES = new HashMap<>();
    protected final long goal;
    protected final int tier;
    private final LootBoxType type;
    private final UUID uuid;
    private ItemStack item = null;
    protected long progress;

    /**
     * Create a new loot box
     *
     * @param tier       The tier of the loot box
     * @param type       The type of loot box
     * @param uuid       The UUID of the loot box
     * @param createItem Whether to create a new item or not
     */
    public LootBox(int tier, LootBoxType type, UUID uuid, boolean createItem) {
        this.uuid = uuid;
        this.type = type;
        this.tier = tier;
        this.goal = type.getGoal(tier);
        if (createItem) {
            item = ItemUtils.createCustomSkull(type.getBase64Texture());
            item.editMeta(meta -> meta.getPersistentDataContainer().set(LOOT_BOX_KEY, PersistentDataType.STRING, uuid.toString()));
            updateItemNow();
        }
        LOOT_BOXES.put(uuid, this);
    }

    public static void init() {

        LootBoxType.MONEY.getMinTier(); //Call this to load all the enums

        //Load them from a file
        LOOT_BOXES.clear();
        FileConfiguration data = YamlConfiguration.loadConfiguration(new File(StaticPrisons.getInstance().getDataFolder(), "data/lootboxes.yml"));
        for (String key : data.getKeys(false)) {
            LootBox lootBox = LootBox.loadFromConfigurationSection(Objects.requireNonNull(data.getConfigurationSection(key)));
            LOOT_BOXES.put(UUID.fromString(key), lootBox);
            SpreadOutExecutor.remove(lootBox);
        }

        StaticPrisons.getInstance().getServer().getPluginManager().registerEvents(new LootBoxListener(), StaticPrisons.getInstance());

        //Save every 5min
        Bukkit.getScheduler().runTaskTimer(StaticPrisons.getInstance(), LootBox::saveAll, 20 * 60 * 5 + 20 * 32, 20 * 60 * 5);
    }


    /**
     * Save all loot box data to disk synchronously
     */
    public static void saveAllNow() {
        saveData(LOOT_BOXES);
    }

    /**
     * Save all loot box data to disk asynchronously
     */
    public static void saveAll() {
        Bukkit.getScheduler().runTaskAsynchronously(StaticPrisons.getInstance(), () -> saveData(new HashMap<>(LOOT_BOXES)));
    }

    /**
     * Save all loot box data to disk
     *
     * @param data The data to save to
     */
    private static void saveData(Map<UUID, LootBox> data) {
        FileConfiguration fileData = new YamlConfiguration();
        for (Map.Entry<UUID, LootBox> entry : data.entrySet()) {
            fileData.set(entry.getKey().toString(), entry.getValue().toConfigurationSection());
        }
        try {
            fileData.save(new File(StaticPrisons.getInstance().getDataFolder(), "data/lootboxes.yml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get the associated LootBox instance from a loot box ItemStack
     *
     * @param item The item to get the LootBox instance from
     * @return The LootBox instance, or null if the item is not a loot box
     */
    public static LootBox fromItem(ItemStack item) {
        if (item == null) return null;
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return null;
        String uuidAsStr = meta.getPersistentDataContainer().get(LOOT_BOX_KEY, PersistentDataType.STRING);
        if (uuidAsStr == null) return null;
        LootBox lootBox = fromUUID(UUID.fromString(uuidAsStr));
        if (lootBox == null) return null;
        lootBox.item = item;
        return lootBox;
    }

    /**
     * Load a loot box from a configuration section
     *
     * @param section The section to load the data from
     * @return The loaded loot box
     */
    public static LootBox loadFromConfigurationSection(ConfigurationSection section) {
        try {
            LootBoxType type = LootBoxType.valueOf(Objects.requireNonNull(section.getString("type")));
            LootBox lootBox = type.getLootBoxClass()
                    .getDeclaredConstructor(int.class, UUID.class, boolean.class)
                    .newInstance(section.getInt("tier"),
                            UUID.fromString(Objects.requireNonNull(section.getString("uuid"))),
                            false);
            lootBox.progress = section.getLong("progress");
            return lootBox;
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
                 IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get a LootBox instance from a UUID
     *
     * @param uuid The UUID to get the LootBox instance from
     * @return The LootBox instance, or null if the UUID is not associated with a LootBox instance
     */
    private static LootBox fromUUID(UUID uuid) {
        return LOOT_BOXES.get(uuid);
    }

    /**
     * Get the name of this loot box
     *
     * @return The name of this loot box
     */
    public Component getName() {
        return type.getDisplayName();
    }

    /**
     * Get the name of this loot box in a prefixed format
     *
     * @return The prefix for this loot box
     */
    public Component getPrefix() {
        return type.getDisplayName().append(Component.text(" >> ").color(ComponentUtil.DARK_GRAY).decoration(TextDecoration.BOLD, true));
    }

    /**
     * Get the associated ItemStack with this loot box
     *
     * @return The associated ItemStack with this loot box, or null if it is not known yet
     */
    public ItemStack getItem() {
        return item;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setProgress(long progress, boolean updateItem) {
        this.progress = progress;
        if (updateItem) {
            updateItem();
        }
    }

    /**
     * Get the display name for this loot box.
     * The name will be in the following format: Name [Progress/Goal Blocks Mined | XX.XX%]
     *
     * @return The display name for this loot box
     */
    public Component getDisplayName() {
        double percentage = (double) progress / goal * 100;
        Component percent;
        if (percentage >= 100d) {
            percent = Component.text("100.00%").color(ComponentUtil.GREEN).decorate(TextDecoration.BOLD);
        } else
            percent = Component.text(BigDecimal.valueOf(percentage).setScale(2, RoundingMode.FLOOR) + "%").color(ComponentUtil.LIGHT_GRAY);
        return getName().append(Component.text(" [" + PrisonUtils.prettyNum(progress) + "/" + PrisonUtils.prettyNum(goal) + " Blocks Mined | ").color(ComponentUtil.LIGHT_GRAY)).append(percent).append(Component.text("]").color(ComponentUtil.LIGHT_GRAY)).decoration(TextDecoration.ITALIC, false);
    }

    /**
     * Check if the loot box is completed
     *
     * @return True if the loot box is completed, false otherwise
     */
    public boolean checkCondition() {
        return progress >= goal;
    }

    /**
     * Get the type of loot box
     *
     * @return The type of loot box
     */
    public LootBoxType getType() {
        return type;
    }

    /**
     * Convert this loot box into a ConfigurationSection which can be saved to disk
     *
     * @return The ConfigurationSection with this loot box's data
     */
    public ConfigurationSection toConfigurationSection() {
        ConfigurationSection section = new YamlConfiguration();
        section.set("progress", progress);
        section.set("goal", goal);
        section.set("tier", tier);
        section.set("type", type.name());
        section.set("uuid", uuid.toString());
        return section;
    }

    /**
     * Attempt to claim this loot box.
     * If the loot box is not completed, the player will be notified.
     * If the loot box is complete, then the {@link LootBox#onClaim(Player)} method will be called.
     *
     * @param player The player to claim the loot box for
     * @return True if the loot box was claimed, false otherwise
     */
    public boolean tryToClaim(Player player) {
        if (checkCondition()) {
            onClaim(player);
            item.setAmount(0);
            LOOT_BOXES.remove(uuid);
            return true;
        }
        player.sendMessage(Prefix.LOOT_BOX.append(Component.text("This loot box is not ready to be claimed!").color(ComponentUtil.RED)));
        return false;
    }

    /**
     * Get the lore for the loot box ItemStack
     *
     * @return The lore for the loot box ItemStack
     */
    public abstract List<Component> getLore();

    /**
     * What happens when the loot box is claimed
     *
     * @param player The player who claimed the loot box
     */
    public abstract void onClaim(Player player);

    @Override
    public void execute() {
        updateItemNow();
    }

    /**
     * Queue an update for the loot box's ItemStack
     */
    public void updateItem() {
        queueExecution();
    }

    /**
     * Update the loot box's ItemStack immediately
     */
    public void updateItemNow() {
        if (item == null) return;
        ItemMeta meta = item.getItemMeta();
        meta.displayName(getDisplayName());
        meta.lore(getLore());
        item.setItemMeta(meta);
    }

    /**
     * This method is called when a block is broken.
     * It will check if the loot box is finished or not, if not it will add progress to it.
     * If the loot box has just finished, it will tell the player.
     *
     * @param blockBreak The block break event
     */
    protected void onBlockBreak(BlockBreak blockBreak) {
        if (checkCondition()) return;
        blockBreak.addAfterProcess(bb -> {
            progress += bb.stats().getRawBlockBroken();
            checkIfReadyToClaim(blockBreak.getPlayer());
            updateItem();
        });
    }

    /**
     * Check to see if this loot box has just been completed, if so, tell the player and create a particle effect
     *
     * @param player The player to tell
     * @return True if the loot box has just been completed, false otherwise
     */
    protected boolean checkIfReadyToClaim(Player player) {
        boolean isReady = checkCondition();
        if (isReady) {
            player.sendMessage(getPrefix().append(Component.text("One of your loot boxes is ready to be claimed!")));
            player.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, player.getLocation(), 100, 2, 2, 2);
        }
        return isReady;
    }
}
