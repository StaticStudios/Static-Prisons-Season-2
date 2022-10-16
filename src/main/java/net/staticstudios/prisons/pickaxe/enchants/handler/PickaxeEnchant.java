package net.staticstudios.prisons.pickaxe.enchants.handler;

import net.staticstudios.prisons.blockbreak.BlockBreakProcessEvent;
import net.staticstudios.prisons.enchants.ConfigurableEnchantment;
import net.staticstudios.prisons.enchants.Enchantable;
import net.staticstudios.prisons.pickaxe.PrisonPickaxe;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public abstract class PickaxeEnchant extends ConfigurableEnchantment<BlockBreakProcessEvent> {

    private final Class<? extends PickaxeEnchant> enchantClass;
    private EnchantTier[] tiers;

    public PickaxeEnchant(Class<? extends PickaxeEnchant> enchantClass, String id) {
        super(id);
        this.enchantClass = enchantClass;

        ConfigurationSection tiersConfig = getConfig().getConfigurationSection("tiers");
        if (tiersConfig != null) {
            List<EnchantTier> tiers = new ArrayList<>();
            for (String key : tiersConfig.getKeys(false)) {
                ConfigurationSection tier = tiersConfig.getConfigurationSection(key);
                assert tier != null;
                tiers.add(new EnchantTier(
                        tier.getInt("max_level"),
                        tier.getLong("cost")
                ));
            }
            setTiers(tiers.toArray(new EnchantTier[0]));
        }

        register();
    }

    @Override
    public Class<BlockBreakProcessEvent> getListeningFor() {
        return BlockBreakProcessEvent.class;
    }

    @Override
    public boolean beforeEvent(BlockBreakProcessEvent event) {
        PrisonPickaxe pickaxe = event.getBlockBreak().getPickaxe();
        return pickaxe == null || pickaxe.getEnchantmentLevel(enchantClass) <= 0 || !shouldActivate(pickaxe);
    }

    @Override
    public void onHold(Enchantable enchantable, Player player) {}

    @Override
    public void onUnHold(Enchantable enchantable, Player player) {}

    @Override
    public void onUpgrade(Enchantable enchantable, Player player, int oldLevel, int newLevel) {}

    protected void setTiers(EnchantTier... tiers) {
        this.tiers = tiers;
    }

    public EnchantTier getTier(int tier) {
        if (tiers == null || tier < 0 || tier >= tiers.length) {
            return null;
        }
        return tiers[tier];
    }

    public int getMaxLevel(int tier) {
        EnchantTier enchantTier = getTier(tier);
        if (enchantTier == null) {
            return getMaxLevel();
        }
        return enchantTier.maxLevel();
    }

    public int getMaxLevel(PrisonPickaxe pickaxe) {
        return getMaxLevel(pickaxe.getEnchantmentTier(getClass()));
    }
}
