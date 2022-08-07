package net.staticstudios.prisons.customitems.pickaxes;

import net.staticstudios.prisons.pickaxe.PrisonPickaxe;
import net.staticstudios.prisons.pickaxe.enchants.handler.PickaxeEnchants;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class PickaxeTemplates {

    public static final PickaxeTemplates TIER_1 = new PickaxeTemplates(
            1,
            new EnchantHolder[]{
                    new EnchantHolder(PickaxeEnchants.FORTUNE.ENCHANT_ID, 25),
                    new EnchantHolder(PickaxeEnchants.TOKENATOR.ENCHANT_ID, 1)
            },
            null,
            null,
            "&c&lTrash Pickaxe"
    );

    public static final PickaxeTemplates TIER_2 = new PickaxeTemplates(
            2,
            new EnchantHolder[]{
                    new EnchantHolder(PickaxeEnchants.FORTUNE.ENCHANT_ID, 50),
                    new EnchantHolder(PickaxeEnchants.TOKENATOR.ENCHANT_ID, 20)
            },
            null,
            null,
            "&c&lMeh Pickaxe"
    );

    public static final PickaxeTemplates TIER_3 = new PickaxeTemplates(
            3,
            new EnchantHolder[]{
                    new EnchantHolder(PickaxeEnchants.FORTUNE.ENCHANT_ID, 100),
                    new EnchantHolder(PickaxeEnchants.TOKENATOR.ENCHANT_ID, 50),
                    new EnchantHolder(PickaxeEnchants.JACK_HAMMER.ENCHANT_ID, 15)
            },
            null,
            null,
            "&e&lOk Pickaxe"
    );

    public static final PickaxeTemplates TIER_4 = new PickaxeTemplates(
            4,
            new EnchantHolder[]{
                    new EnchantHolder(PickaxeEnchants.FORTUNE.ENCHANT_ID, 250),
                    new EnchantHolder(PickaxeEnchants.TOKENATOR.ENCHANT_ID, 75),
                    new EnchantHolder(PickaxeEnchants.JACK_HAMMER.ENCHANT_ID, 75)

            },
            null,
            null,
            "&e&lDecent Pickaxe"
    );

    public static final PickaxeTemplates TIER_5 = new PickaxeTemplates(
            5,
            new EnchantHolder[]{
                    new EnchantHolder(PickaxeEnchants.FORTUNE.ENCHANT_ID, 350),
                    new EnchantHolder(PickaxeEnchants.TOKENATOR.ENCHANT_ID, 100),
                    new EnchantHolder(PickaxeEnchants.JACK_HAMMER.ENCHANT_ID, 150),
                    new EnchantHolder(PickaxeEnchants.MULTI_DIRECTIONAL.ENCHANT_ID, 75)
            },
            null,
            null,
            "&a&lGood Pickaxe"
    );

    public static final PickaxeTemplates TIER_6 = new PickaxeTemplates(
            6,
            new EnchantHolder[]{
                    new EnchantHolder(PickaxeEnchants.FORTUNE.ENCHANT_ID, 500),
                    new EnchantHolder(PickaxeEnchants.TOKENATOR.ENCHANT_ID, 150),
                    new EnchantHolder(PickaxeEnchants.JACK_HAMMER.ENCHANT_ID, 250),
                    new EnchantHolder(PickaxeEnchants.MULTI_DIRECTIONAL.ENCHANT_ID, 150),
            },
            null,
            null,
            "&a&lPretty Good Pickaxe"
    );

    public static final PickaxeTemplates TIER_7 = new PickaxeTemplates(
            7,
            new EnchantHolder[]{
                    new EnchantHolder(PickaxeEnchants.FORTUNE.ENCHANT_ID, 750),
                    new EnchantHolder(PickaxeEnchants.TOKENATOR.ENCHANT_ID, 250),
                    new EnchantHolder(PickaxeEnchants.JACK_HAMMER.ENCHANT_ID, 250),
                    new EnchantHolder(PickaxeEnchants.MULTI_DIRECTIONAL.ENCHANT_ID, 250),
                    new EnchantHolder(PickaxeEnchants.KEY_FINDER.ENCHANT_ID, 100),
            },
            null,
            null,
            "&b&lEfficient Pickaxe"
    );

    public static final PickaxeTemplates TIER_8 = new PickaxeTemplates(
            8,
            new EnchantHolder[]{
                    new EnchantHolder(PickaxeEnchants.FORTUNE.ENCHANT_ID, 1000),
                    new EnchantHolder(PickaxeEnchants.DOUBLE_FORTUNE.ENCHANT_ID, 75),
                    new EnchantHolder(PickaxeEnchants.TOKENATOR.ENCHANT_ID, 400),
                    new EnchantHolder(PickaxeEnchants.JACK_HAMMER.ENCHANT_ID, 400),
                    new EnchantHolder(PickaxeEnchants.MULTI_DIRECTIONAL.ENCHANT_ID, 400),
                    new EnchantHolder(PickaxeEnchants.KEY_FINDER.ENCHANT_ID, 250),
            },
            null,
            null,
            "&b&lEffective Pickaxe"
    );

    public static final PickaxeTemplates TIER_9 = new PickaxeTemplates(
            9,
            new EnchantHolder[]{
                    new EnchantHolder(PickaxeEnchants.FORTUNE.ENCHANT_ID, 1500),
                    new EnchantHolder(PickaxeEnchants.DOUBLE_FORTUNE.ENCHANT_ID, 250),
                    new EnchantHolder(PickaxeEnchants.TOKENATOR.ENCHANT_ID, 500),
                    new EnchantHolder(PickaxeEnchants.JACK_HAMMER.ENCHANT_ID, 1000),
                    new EnchantHolder(PickaxeEnchants.DOUBLE_JACK_HAMMER.ENCHANT_ID, 25),
                    new EnchantHolder(PickaxeEnchants.MULTI_DIRECTIONAL.ENCHANT_ID, 1000),
                    new EnchantHolder(PickaxeEnchants.KEY_FINDER.ENCHANT_ID, 500),
            },
            null,
            null,
            "&d&lBroken Pickaxe"
    );

    public static final PickaxeTemplates TIER_10 = new PickaxeTemplates(
            10,
            new EnchantHolder[]{
                    new EnchantHolder(PickaxeEnchants.FORTUNE.ENCHANT_ID, 4000),
                    new EnchantHolder(PickaxeEnchants.DOUBLE_FORTUNE.ENCHANT_ID, 500),
                    new EnchantHolder(PickaxeEnchants.TOKENATOR.ENCHANT_ID, 1000),
                    new EnchantHolder(PickaxeEnchants.JACK_HAMMER.ENCHANT_ID, 2500),
                    new EnchantHolder(PickaxeEnchants.DOUBLE_JACK_HAMMER.ENCHANT_ID, 100),
                    new EnchantHolder(PickaxeEnchants.MULTI_DIRECTIONAL.ENCHANT_ID, 2500),
                    new EnchantHolder(PickaxeEnchants.KEY_FINDER.ENCHANT_ID, 1000),
                    new EnchantHolder(PickaxeEnchants.CONSISTENCY.ENCHANT_ID, 1),
            },
            null,
            null,
            "&c&lWooden Pickaxe"
    );





    public final int TIER;
    public final EnchantHolder[] ENCHANTS;
    public final List<String> TOP_LORE;
    public final List<String> BOTTOM_LORE;
    public final String DISPLAY_NAME;

    public PickaxeTemplates(int tier, EnchantHolder[] enchantHolder, List<String> topLore, List<String> bottomLore, String displayName) {
        this.TIER = tier;
        this.ENCHANTS = enchantHolder;
        this.TOP_LORE = topLore;
        this.BOTTOM_LORE = bottomLore;
        this.DISPLAY_NAME = ChatColor.translateAlternateColorCodes('&', displayName);
    }

    public PrisonPickaxe buildPickaxe() {
        ItemStack item = new ItemStack(Material.DIAMOND_PICKAXE);
        PrisonPickaxe pickaxe = new PrisonPickaxe(item);
        pickaxe.setTopLore(TOP_LORE);
        pickaxe.setBottomLore(BOTTOM_LORE);
        pickaxe.setName(DISPLAY_NAME);
        ItemMeta meta = item.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addEnchant(Enchantment.DIG_SPEED, 100, true);
        item.setItemMeta(meta);
        for (EnchantHolder enchantHolder : ENCHANTS) pickaxe.setEnchantsLevel(enchantHolder.enchantID(), enchantHolder.level());
        PrisonPickaxe.updateLore(item);
        return pickaxe;
    }

    private record EnchantHolder(String enchantID, int level) {}
}
