package net.staticstudios.prisons.customitems.pickaxes;

import net.kyori.adventure.text.Component;
import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.customitems.handler.CustomItem;
import net.staticstudios.prisons.enchants.Enchantable;
import net.staticstudios.prisons.enchants.Enchantment;
import net.staticstudios.prisons.pickaxe.PrisonPickaxe;
import net.staticstudios.prisons.utils.StaticFileSystemManager;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public enum PickaxeTemplates implements CustomItem {

    DEFAULT("default"),
    TIER_1("tier_1"),
    TIER_2("tier_2"),
    TIER_3("tier_3"),
    TIER_4("tier_4"),
    TIER_5("tier_5"),
    TIER_6("tier_6"),
    TIER_7("tier_7"),
    TIER_8("tier_8"),
    TIER_9("tier_9"),
    TIER_10("tier_10");

    private final String id;

    private final ItemStack templateItem;

    private final Material pickaxeMaterial;
    private final Component name;
    private final List<Component> topLore = new ArrayList<>();
    private final List<Component> bottomLore = new ArrayList<>();
    private final Map<Class<? extends Enchantment>, Integer> enchants = new HashMap<>();

    PickaxeTemplates(String id) {
        this.id = "pickaxe_" + id;
        YamlConfiguration config = YamlConfiguration.loadConfiguration(StaticFileSystemManager.getFile("pickaxeTemplates.yml").orElseThrow(() -> new RuntimeException("Could not find pickaxeTemplates.yml")));

        ConfigurationSection section = config.getConfigurationSection(id);
        ConfigurationSection enchantsSection = Objects.requireNonNull(section).getConfigurationSection("enchants");
        for (String key : Objects.requireNonNull(enchantsSection).getKeys(false)) {
            Enchantment<?> enchantment = Enchantable.getEnchant(key);
            if (enchantment == null) continue;
            enchants.put(enchantment.getClass(), enchantsSection.getInt(key));
        }
        pickaxeMaterial = Material.valueOf(section.getString("material"));
        name = StaticPrisons.miniMessage().deserialize(section.getString("name", "Prison Pickaxe"));
        section.getStringList("top_lore").forEach(s -> topLore.add(StaticPrisons.miniMessage().deserialize(s)));
        section.getStringList("bottom_lore").forEach(s -> bottomLore.add(StaticPrisons.miniMessage().deserialize(s)));

        templateItem = buildPickaxe(false).getItem();

        register();
    }

    /**
     * Returns an ItemStack that is intended to be used in a GUI as a button.
     * It will NOT be tracked by the server as a PrisonPickaxe.
     * @return an ItemStack that is intended to be used in a GUI as a button.
     */
    public ItemStack getTemplateItem() {
        return templateItem;
    }

    /**
     * Create a new PrisonPickaxe instance
     * @return A new PrisonPickaxe using this template's values as a reference
     */
    public PrisonPickaxe getPickaxe() {
        return buildPickaxe(true);
    }



    private PrisonPickaxe buildPickaxe(boolean register) {
        ItemStack item = new ItemStack(pickaxeMaterial);
        PrisonPickaxe pickaxe = new PrisonPickaxe(item, register);
        pickaxe.setName(name);
        pickaxe.setTopLore(topLore);
        pickaxe.setBottomLore(bottomLore);
        item.editMeta(meta -> {
            meta.setUnbreakable(true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            meta.addEnchant(org.bukkit.enchantments.Enchantment.DIG_SPEED, 100, true);
        });
        enchants.forEach(pickaxe::setEnchantment);
        pickaxe.updateItemNow();

        assert pickaxe.getItem() != null;
        setCustomItem(pickaxe.getItem());

        return pickaxe;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public ItemStack getItem(Player player) {
        return getPickaxe().getItem();
    }
}
