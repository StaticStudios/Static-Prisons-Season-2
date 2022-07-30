package net.staticstudios.prisons.lootboxes.handler;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.blockBroken.BlockBreak;
import net.staticstudios.prisons.lootboxes.MoneyLootBox;
import net.staticstudios.prisons.lootboxes.TokenLootBox;
import net.staticstudios.prisons.utils.ComponentUtil;
import net.staticstudios.prisons.utils.ItemUtils;
import net.staticstudios.prisons.utils.Prefix;
import net.staticstudios.prisons.utils.items.SpreadOutExecution;
import net.staticstudios.prisons.utils.items.SpreadOutExecutor;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.io.File;
import java.io.IOException;
import java.util.*;

public abstract class LootBox implements SpreadOutExecution {

    public static final NamespacedKey LOOTBOX_KEY = new NamespacedKey(StaticPrisons.getInstance(), "lootboxUUID");

    public static void init() {
        //Load them from a file
        lootBoxes.clear();
        FileConfiguration data = YamlConfiguration.loadConfiguration(new File(StaticPrisons.getInstance().getDataFolder(), "lootboxes.yml"));
        for (String key : data.getKeys(false)) {
            LootBox lootBox = LootBox.loadFromConfigurationSection(data.getConfigurationSection(key));
            lootBoxes.put(key, lootBox);
            SpreadOutExecutor.remove(lootBox);
        }


        BlockBreak.addListener(blockBreak -> {
            if (blockBreak.getPlayer() == null) return;
            List<LootBox> lootBoxes = new LinkedList<>();
            for (ItemStack itemStack : blockBreak.getPlayer().getInventory().getContents()) {
                LootBox lootBox = fromItem(itemStack);
                if (lootBox != null) lootBoxes.add(lootBox);
            }
            for (LootBox lootBox : lootBoxes) {
                lootBox.onBlockBreak(blockBreak);
            }
        });
        StaticPrisons.getInstance().getServer().getPluginManager().registerEvents(new LootBoxListener(), StaticPrisons.getInstance());

        //Save every 5min
        Bukkit.getScheduler().runTaskTimer(StaticPrisons.getInstance(), LootBox::saveAll, 20 * 60 * 5 + 20 * 32, 20 * 60 * 5);
    }

    public static void saveAllNow() {
        saveData(lootBoxes);
    }

    public static void saveAll() {
        Bukkit.getScheduler().runTaskAsynchronously(StaticPrisons.getInstance(), () -> {
            saveData(new HashMap<>(lootBoxes));
        });
    }

    static void saveData(Map<String, LootBox> data) {
        FileConfiguration fileData = new YamlConfiguration();
        for (Map.Entry<String, LootBox> entry : data.entrySet()) {
            fileData.set(entry.getKey(), entry.getValue().save());
        }
        try {
            fileData.save(new File(StaticPrisons.getInstance().getDataFolder(), "lootboxes.yml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static Map<String, LootBox> lootBoxes = new HashMap<>();

    public static LootBox fromItem(ItemStack item) {
        if (item == null) return null;
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return null;
        LootBox lootBox = fromUUID(meta.getPersistentDataContainer().get(LOOTBOX_KEY, PersistentDataType.STRING));
        if (lootBox == null) return null;
        lootBox.item = item;
        return lootBox;
    }

    static LootBox fromUUID(String uuid) {
        return lootBoxes.get(uuid);
    }

    String uuid;
    ItemStack item = null;
    String name;
    Component componentName = null;
    LootBoxType type;

    public LootBox(String name, LootBoxType type, boolean createItem) {
        this.uuid = UUID.randomUUID().toString();
        this.name = name;
        this.type = type;
        if (createItem) {
            item = ItemUtils.createCustomSkull(type.base64Texture);
            item.editMeta(meta -> meta.getPersistentDataContainer().set(LOOTBOX_KEY, PersistentDataType.STRING, uuid));
        }
        lootBoxes.put(uuid, this);
    }

    public ItemStack getItem() {
        return item;
    }

    public Component getName() {
        if (componentName != null) return componentName;
        componentName = Component.empty().append(LegacyComponentSerializer.legacyAmpersand().deserialize(name));
        return componentName;
    }

    public abstract void onBlockBreak(BlockBreak blockBreak);
    public abstract Component getDisplayName();
    public abstract List<Component> getLore();
    public abstract boolean checkCondition();
    public abstract void onClaim(Player player);

    public abstract ConfigurationSection toConfigurationSection();

    public boolean tryToClaim(Player player) {
        if (checkCondition()) {
            onClaim(player);
            item.setAmount(0);
            lootBoxes.remove(uuid);
            return true;
        }
        player.sendMessage(Prefix.LOOT_BOX.append(Component.text("This loot box is not ready to be claimed!").color(ComponentUtil.RED)));
        return false;
    }

    public void updateItem() {
        queueExecution();
    }
    public void updateItemNow() {
        if (item == null) return;
        ItemMeta meta = item.getItemMeta();
        meta.displayName(getDisplayName());
        meta.lore(getLore());
        item.setItemMeta(meta);
    }
    @Override
    public void runSpreadOutExecution() {
        updateItemNow();
    }

    public ConfigurationSection save() {
        ConfigurationSection section = toConfigurationSection();
        section.set("type", type.name());
        section.set("name", name);
        section.set("uuid", uuid);
        return section;
    }

    public LootBox load(ConfigurationSection section) {
        this.name = section.getString("name");
        this.type = LootBoxType.valueOf(section.getString("type"));
        String newUUID = section.getString("uuid");
        if (!this.uuid.equals(newUUID)) {
            lootBoxes.remove(this.uuid);
            this.uuid = newUUID;
            lootBoxes.put(this.uuid, this);
        }
        return this;
    }

    public static LootBox loadFromConfigurationSection(ConfigurationSection section) {
        return switch (LootBoxType.valueOf(Objects.requireNonNull(section.getString("type")))) {
            case TOKEN -> TokenLootBox.fromConfigurationSection(section);
            case MONEY -> MoneyLootBox.fromConfigurationSection(section);
            default -> null;
        };
    }

}
