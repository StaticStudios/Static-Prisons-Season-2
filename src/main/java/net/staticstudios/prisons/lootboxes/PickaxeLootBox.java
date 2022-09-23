package net.staticstudios.prisons.lootboxes;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.staticstudios.prisons.blockbreak.BlockBreak;
import net.staticstudios.prisons.customitems.pickaxes.PickaxeTemplates;
import net.staticstudios.prisons.lootboxes.handler.LootBox;
import net.staticstudios.prisons.lootboxes.handler.LootBoxType;
import net.staticstudios.prisons.pickaxe.PrisonPickaxe;
import net.staticstudios.prisons.utils.ComponentUtil;
import net.staticstudios.prisons.utils.PlayerUtils;
import net.staticstudios.prisons.utils.Prefix;
import net.staticstudios.prisons.utils.PrisonUtils;
import net.staticstudios.mines.utils.WeightedElements;
import org.bukkit.Particle;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PickaxeLootBox extends LootBox { //todo: finish config options for rewards

    record PickaxeLootBoxOutline(int tier, long requires, WeightedElements<String> rewards) {}

    static Map<Integer, PickaxeLootBoxOutline> TIERS = new HashMap<>();

    public static void loadTiers(ConfigurationSection section) {
        TIERS.clear();
        for (String key : section.getKeys(false)) {
            int tier = Integer.parseInt(key);
            long requires = section.getLong(key + ".requires");
            ConfigurationSection rewards = section.getConfigurationSection(key + ".rewards.pickaxes");
            WeightedElements<String> weightedRewards = new WeightedElements<>();
            for (String k : rewards.getKeys(false)) {
                weightedRewards.add(k, Double.parseDouble(rewards.getString(k)));
            }
            TIERS.put(tier, new PickaxeLootBoxOutline(tier, requires, weightedRewards));
        }
    }

    public static PickaxeLootBoxOutline getTier(int tier) {
        return TIERS.get(tier);
    }

    static String displayName = "&b&lPickaxe Loot Box";

    public PickaxeLootBox(int tier) {
        super(displayName, LootBoxType.PICKAXE, true);
        this.blocksRequired = getTier(tier).requires;
        this.tier = tier;
        updateItemNow();
    }

    /**
     * Constructor that gets called when loading a loot box from a configuration section
     */
    public PickaxeLootBox(long blocksRequired, int tier) {
        super(displayName, LootBoxType.TOKEN, false);
        this.blocksRequired = blocksRequired;
        this.tier = tier;
    }

    private long blocksMined;
    private long blocksRequired;
    private int tier;

    @Override
    public void onBlockBreak(BlockBreak blockBreak) {
        if (blocksMined >= blocksRequired) return;
        blockBreak.addAfterProcess(bb -> {
            blocksMined += bb.getStats().getRawBlockBroken();
            if (blocksMined >= blocksRequired) {
                Player player = bb.getPlayer();
                player.sendMessage(Prefix.LOOT_BOX.append(Component.text("One of your loot boxes is ready to be claimed!")));
                player.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, player.getLocation(), 100, 2, 2, 2);
            }
            updateItem();
        });
    }

    @Override
    public Component getDisplayName() {
        double percentage = (double) blocksMined / blocksRequired * 100;
        Component percent;
        if (percentage >= 100d) {
            percent = Component.text("100.00%").color(ComponentUtil.GREEN).decorate(TextDecoration.BOLD);
        } else percent = Component.text(BigDecimal.valueOf(percentage).setScale(2, RoundingMode.FLOOR) + "%").color(ComponentUtil.LIGHT_GRAY);
        return getName().append(Component.text(" [" + PrisonUtils.prettyNum(blocksMined) + "/" + PrisonUtils.prettyNum(blocksRequired) + " Blocks Mined | ").color(ComponentUtil.LIGHT_GRAY)).append(percent).append(Component.text("]").color(ComponentUtil.LIGHT_GRAY)).decoration(TextDecoration.ITALIC, false);
    }

    static final Component LORE_PREFIX = Component.empty().append(Component.text("| ").color(ComponentUtil.DARK_GRAY).decorate(TextDecoration.BOLD));
    @Override
    public List<Component> getLore() {
        return List.of(
                Component.empty(),
                LORE_PREFIX.append(Component.text("Open this loot box to receive a random").color(ComponentUtil.LIGHT_GRAY)).decoration(TextDecoration.ITALIC, false),
                LORE_PREFIX.append(Component.text("pickaxe! This loot box's challenge").color(ComponentUtil.LIGHT_GRAY)).decoration(TextDecoration.ITALIC, false),
                LORE_PREFIX.append(Component.text("requires you to mine " + PrisonUtils.addCommasToNumber(blocksRequired) + " blocks").color(ComponentUtil.LIGHT_GRAY)).decoration(TextDecoration.ITALIC, false),
                LORE_PREFIX.append(Component.text("You are currently ").color(ComponentUtil.LIGHT_GRAY)).append(Component.text(BigDecimal.valueOf((double) blocksMined / blocksRequired * 100).setScale(2, RoundingMode.FLOOR) + "%").color(ComponentUtil.GREEN).decorate(TextDecoration.BOLD)).append(Component.text(" done with this!").color(ComponentUtil.LIGHT_GRAY)).decoration(TextDecoration.ITALIC, false),
                LORE_PREFIX.decoration(TextDecoration.ITALIC, false),
                LORE_PREFIX.append(Component.text("Tier: ").color(ComponentUtil.AQUA)).append(Component.text(tier + "").color(ComponentUtil.WHITE)).decoration(TextDecoration.ITALIC, false),
                LORE_PREFIX.append(Component.text("Reward: ").color(ComponentUtil.AQUA)).append(Component.text("AAAAAAA").decorate(TextDecoration.OBFUSCATED).color(ComponentUtil.WHITE)).append(Component.text(" Pickaxe").color(ComponentUtil.WHITE)).decoration(TextDecoration.ITALIC, false)
        );
    }

    @Override
    public boolean checkCondition() {
        return blocksMined >= blocksRequired;
    }

    @Override
    public void onClaim(Player player) {
        PrisonPickaxe pickaxe = switch (getTier(tier).rewards().getRandom()) {
            default -> PickaxeTemplates.TIER_1.buildPickaxe();
            case "tier_2" -> PickaxeTemplates.TIER_2.buildPickaxe();
            case "tier_3" -> PickaxeTemplates.TIER_3.buildPickaxe();
            case "tier_4" -> PickaxeTemplates.TIER_4.buildPickaxe();
            case "tier_5" -> PickaxeTemplates.TIER_5.buildPickaxe();
            case "tier_6" -> PickaxeTemplates.TIER_6.buildPickaxe();
            case "tier_7" -> PickaxeTemplates.TIER_7.buildPickaxe();
            case "tier_8" -> PickaxeTemplates.TIER_8.buildPickaxe();
            case "tier_9" -> PickaxeTemplates.TIER_9.buildPickaxe();
            case "tier_10" -> PickaxeTemplates.TIER_10.buildPickaxe();
        };
        ItemStack item = pickaxe.item;
        PlayerUtils.addToInventory(player, item);
        player.sendMessage(Prefix.LOOT_BOX.append(Component.text("You've been given 1x " + PrisonUtils.Items.getPrettyItemName(item) + "!")));
    }

    @Override
    public ConfigurationSection toConfigurationSection() {
        ConfigurationSection section = new YamlConfiguration();
        section.set("blocksMined", blocksMined);
        section.set("blocksRequired", blocksRequired);
        section.set("tier", tier);
        return section;
    }

    public static PickaxeLootBox fromConfigurationSection(ConfigurationSection section) {
        PickaxeLootBox lootBox = new PickaxeLootBox(section.getLong("blocksRequired"), section.getInt("tier"));
        lootBox.blocksMined = section.getLong("blocksMined");
        lootBox.load(section);
        return lootBox;
    }
}
