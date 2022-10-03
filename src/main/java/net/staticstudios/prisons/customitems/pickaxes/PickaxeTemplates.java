package net.staticstudios.prisons.customitems.pickaxes;

import net.staticstudios.prisons.customitems.CustomItem;
import net.staticstudios.prisons.pickaxe.PrisonPickaxe;
import net.staticstudios.prisons.pickaxe.enchants.handler.PickaxeEnchants;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class PickaxeTemplates implements CustomItem {

    public static final PickaxeTemplates TIER_1 = new PickaxeTemplates(
            1,
            new EnchantHolder[]{
                    new EnchantHolder(PickaxeEnchants.FORTUNE.ENCHANT_ID, 25),
                    new EnchantHolder(PickaxeEnchants.TOKENATOR.ENCHANT_ID, 5),
                    new EnchantHolder(PickaxeEnchants.JACK_HAMMER.ENCHANT_ID, 25)
            },
            null,
            null,
            "&c&lTrash Pickaxe"
    ).setMaterial(Material.STONE_PICKAXE);

    public static final PickaxeTemplates TIER_2 = new PickaxeTemplates(
            2,
            new EnchantHolder[]{
                    new EnchantHolder(PickaxeEnchants.FORTUNE.ENCHANT_ID, 50),
                    new EnchantHolder(PickaxeEnchants.TOKENATOR.ENCHANT_ID, 20),
                    new EnchantHolder(PickaxeEnchants.JACK_HAMMER.ENCHANT_ID, 50),
                    new EnchantHolder(PickaxeEnchants.MULTI_DIRECTIONAL.ENCHANT_ID, 25)
            },
            null,
            null,
            "&c&lMeh Pickaxe"
    ).setMaterial(Material.STONE_PICKAXE);

    public static final PickaxeTemplates TIER_3 = new PickaxeTemplates(
            3,
            new EnchantHolder[]{
                    new EnchantHolder(PickaxeEnchants.FORTUNE.ENCHANT_ID, 100),
                    new EnchantHolder(PickaxeEnchants.TOKENATOR.ENCHANT_ID, 50),
                    new EnchantHolder(PickaxeEnchants.JACK_HAMMER.ENCHANT_ID, 75),
                    new EnchantHolder(PickaxeEnchants.MULTI_DIRECTIONAL.ENCHANT_ID, 50)
            },
            null,
            null,
            "&e&lOk Pickaxe"
    ).setMaterial(Material.GOLDEN_PICKAXE);

    public static final PickaxeTemplates TIER_4 = new PickaxeTemplates(
            4,
            new EnchantHolder[]{
                    new EnchantHolder(PickaxeEnchants.FORTUNE.ENCHANT_ID, 175),
                    new EnchantHolder(PickaxeEnchants.TOKENATOR.ENCHANT_ID, 80),
                    new EnchantHolder(PickaxeEnchants.JACK_HAMMER.ENCHANT_ID, 120),
                    new EnchantHolder(PickaxeEnchants.MULTI_DIRECTIONAL.ENCHANT_ID, 65),
                    new EnchantHolder(PickaxeEnchants.MERCHANT.ENCHANT_ID, 40)

            },
            null,
            null,
            "&e&lDecent Pickaxe"
    ).setMaterial(Material.GOLDEN_PICKAXE);

    public static final PickaxeTemplates TIER_5 = new PickaxeTemplates(
            5,
            new EnchantHolder[]{
                    new EnchantHolder(PickaxeEnchants.FORTUNE.ENCHANT_ID, 250),
                    new EnchantHolder(PickaxeEnchants.TOKENATOR.ENCHANT_ID, 100),
                    new EnchantHolder(PickaxeEnchants.JACK_HAMMER.ENCHANT_ID, 175),
                    new EnchantHolder(PickaxeEnchants.MULTI_DIRECTIONAL.ENCHANT_ID, 150),
                    new EnchantHolder(PickaxeEnchants.MERCHANT.ENCHANT_ID, 130),
                    new EnchantHolder(PickaxeEnchants.AUTO_SELL.ENCHANT_ID, 1)
            },
            null,
            null,
            "&a&lGood Pickaxe"
    ).setMaterial(Material.IRON_PICKAXE);

    public static final PickaxeTemplates TIER_6 = new PickaxeTemplates(
            6,
            new EnchantHolder[]{
                    new EnchantHolder(PickaxeEnchants.FORTUNE.ENCHANT_ID, 400),
                    new EnchantHolder(PickaxeEnchants.DOUBLE_FORTUNE.ENCHANT_ID, 20),
                    new EnchantHolder(PickaxeEnchants.TOKENATOR.ENCHANT_ID, 230),
                    new EnchantHolder(PickaxeEnchants.JACK_HAMMER.ENCHANT_ID, 250),
                    new EnchantHolder(PickaxeEnchants.MULTI_DIRECTIONAL.ENCHANT_ID, 250),
                    new EnchantHolder(PickaxeEnchants.MERCHANT.ENCHANT_ID, 200),
                    new EnchantHolder(PickaxeEnchants.AUTO_SELL.ENCHANT_ID, 1)
            },
            null,
            null,
            "&a&lPretty Good Pickaxe"
    ).setMaterial(Material.IRON_PICKAXE);

    public static final PickaxeTemplates TIER_7 = new PickaxeTemplates(
            7,
            new EnchantHolder[]{
                    new EnchantHolder(PickaxeEnchants.FORTUNE.ENCHANT_ID, 700),
                    new EnchantHolder(PickaxeEnchants.DOUBLE_FORTUNE.ENCHANT_ID, 25),
                    new EnchantHolder(PickaxeEnchants.TOKENATOR.ENCHANT_ID, 400),
                    new EnchantHolder(PickaxeEnchants.JACK_HAMMER.ENCHANT_ID, 400),
                    new EnchantHolder(PickaxeEnchants.MULTI_DIRECTIONAL.ENCHANT_ID, 400),
                    new EnchantHolder(PickaxeEnchants.MERCHANT.ENCHANT_ID, 400),
                    new EnchantHolder(PickaxeEnchants.AUTO_SELL.ENCHANT_ID, 1),
                    new EnchantHolder(PickaxeEnchants.KEY_FINDER.ENCHANT_ID, 250),
                    new EnchantHolder(PickaxeEnchants.METAL_DETECTOR.ENCHANT_ID, 250)
            },
            null,
            null,
            "&b&lEfficient Pickaxe"
    ).setMaterial(Material.DIAMOND_PICKAXE);

    public static final PickaxeTemplates TIER_8 = new PickaxeTemplates(
            8,
            new EnchantHolder[]{
                    new EnchantHolder(PickaxeEnchants.FORTUNE.ENCHANT_ID, 900),
                    new EnchantHolder(PickaxeEnchants.DOUBLE_FORTUNE.ENCHANT_ID, 30),
                    new EnchantHolder(PickaxeEnchants.TOKENATOR.ENCHANT_ID, 600),
                    new EnchantHolder(PickaxeEnchants.JACK_HAMMER.ENCHANT_ID, 1000),
                    new EnchantHolder(PickaxeEnchants.MULTI_DIRECTIONAL.ENCHANT_ID, 1000),
                    new EnchantHolder(PickaxeEnchants.MERCHANT.ENCHANT_ID, 400),
                    new EnchantHolder(PickaxeEnchants.AUTO_SELL.ENCHANT_ID, 6000),
                    new EnchantHolder(PickaxeEnchants.KEY_FINDER.ENCHANT_ID, 400),
                    new EnchantHolder(PickaxeEnchants.METAL_DETECTOR.ENCHANT_ID, 300)
            },
            null,
            null,
            "&b&lEffective Pickaxe"
    ).setMaterial(Material.DIAMOND_PICKAXE);

    public static final PickaxeTemplates TIER_9 = new PickaxeTemplates(
            9,
            new EnchantHolder[]{
                    new EnchantHolder(PickaxeEnchants.FORTUNE.ENCHANT_ID, 900),
                    new EnchantHolder(PickaxeEnchants.DOUBLE_FORTUNE.ENCHANT_ID, 30),
                    new EnchantHolder(PickaxeEnchants.TOKENATOR.ENCHANT_ID, 700),
                    new EnchantHolder(PickaxeEnchants.JACK_HAMMER.ENCHANT_ID, 3000),
                    new EnchantHolder(PickaxeEnchants.MULTI_DIRECTIONAL.ENCHANT_ID, 2000),
                    new EnchantHolder(PickaxeEnchants.MERCHANT.ENCHANT_ID, 900),
                    new EnchantHolder(PickaxeEnchants.AUTO_SELL.ENCHANT_ID, 7500),
                    new EnchantHolder(PickaxeEnchants.KEY_FINDER.ENCHANT_ID, 600),
                    new EnchantHolder(PickaxeEnchants.METAL_DETECTOR.ENCHANT_ID, 500)
            },
            null,
            null,
            "&d&lExtraordinary Pickaxe"
    ).setMaterial(Material.NETHERITE_PICKAXE);

    public static final PickaxeTemplates TIER_10 = new PickaxeTemplates(
            10,
            new EnchantHolder[]{
                    new EnchantHolder(PickaxeEnchants.FORTUNE.ENCHANT_ID, 1000),
                    new EnchantHolder(PickaxeEnchants.DOUBLE_FORTUNE.ENCHANT_ID, 50),
                    new EnchantHolder(PickaxeEnchants.TOKENATOR.ENCHANT_ID, 950),
                    new EnchantHolder(PickaxeEnchants.JACK_HAMMER.ENCHANT_ID, 5000),
                    new EnchantHolder(PickaxeEnchants.MULTI_DIRECTIONAL.ENCHANT_ID, 4000),
                    new EnchantHolder(PickaxeEnchants.MERCHANT.ENCHANT_ID, 1750),
                    new EnchantHolder(PickaxeEnchants.AUTO_SELL.ENCHANT_ID, 12500),
                    new EnchantHolder(PickaxeEnchants.KEY_FINDER.ENCHANT_ID, 1000),
                    new EnchantHolder(PickaxeEnchants.METAL_DETECTOR.ENCHANT_ID, 1000),
                    new EnchantHolder(PickaxeEnchants.CONSISTENCY.ENCHANT_ID, 1),
                    new EnchantHolder(PickaxeEnchants.EGG_SHOOTER.ENCHANT_ID, 50)
            },
            null,
            null,
            "&c&lWooden Pickaxe"
    ).setMaterial(Material.WOODEN_PICKAXE);





    public final int TIER;
    public final EnchantHolder[] ENCHANTS;
    public final List<String> TOP_LORE;
    public final List<String> BOTTOM_LORE;
    public final String DISPLAY_NAME;
    private Material material = Material.DIAMOND_PICKAXE;

    public PickaxeTemplates(int tier, EnchantHolder[] enchantHolder, List<String> topLore, List<String> bottomLore, String displayName) {
        this.TIER = tier;
        this.ENCHANTS = enchantHolder;
        this.TOP_LORE = topLore;
        this.BOTTOM_LORE = bottomLore;
        this.DISPLAY_NAME = ChatColor.translateAlternateColorCodes('&', displayName);

        register();
    }

    public PickaxeTemplates setMaterial(Material material) {
        this.material = material;
        return this;
    }

    public PrisonPickaxe buildPickaxe() {
        ItemStack item = setCustomItem(new ItemStack(material));
        
        PrisonPickaxe pickaxe = new PrisonPickaxe(item);
        pickaxe.setTopLore(TOP_LORE);
        pickaxe.setBottomLore(BOTTOM_LORE);
        pickaxe.setName(DISPLAY_NAME);

        item.editMeta(meta -> {
            meta.setUnbreakable(true);
            meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            meta.addEnchant(Enchantment.DIG_SPEED, 100, true);

        });

        for (EnchantHolder enchantHolder : ENCHANTS) {
            pickaxe.setEnchantsLevel(enchantHolder.enchantID(), enchantHolder.level());
        }

        PrisonPickaxe.updateLore(item);

        return pickaxe;
    }

    @Override
    public String getID() {
        return "pickaxe_tier_" + TIER;
    }

    @Override
    public ItemStack getItem(Player player) {
        return setCustomItem(buildPickaxe().item);
    }

    private record EnchantHolder(String enchantID, int level) {}
}
