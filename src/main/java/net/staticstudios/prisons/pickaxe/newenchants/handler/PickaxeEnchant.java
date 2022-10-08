package net.staticstudios.prisons.pickaxe.newenchants.handler;

import net.staticstudios.prisons.enchants.ConfigurableEnchantment;
import net.staticstudios.prisons.enchants.Enchantable;
import net.staticstudios.prisons.pickaxe.PrisonPickaxe;
import net.staticstudios.prisons.pickaxe.enchants.handler.EnchantTier;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class PickaxeEnchant<E extends Event> extends ConfigurableEnchantment<E> {

    private final Class<E> eventClass;
    private EnchantTier[] tiers;

    public PickaxeEnchant(@NotNull Class<E> eventClass, String id) {
        super(id);
        this.eventClass = eventClass;

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
    public Class<E> getListeningFor() {
        return eventClass;
    }

    public void onHold(Enchantable enchantable, Player player) { //todo: functionality
    }

    public void onUnHold(Enchantable enchantable, Player player) {
    }

    public void onUpgrade(Enchantable enchantable, Player player, int oldLevel, int newLevel) {
    }

    protected void setTiers(EnchantTier... tiers) {
        this.tiers = tiers;
    }

    public Optional<EnchantTier> getTier(int tier) {
        if (tiers == null || tier < 0 || tier >= tiers.length) {
            return Optional.empty();
        }
        return Optional.of(tiers[tier]);
    }

    public int getMaxLevel(int tier) {
        EnchantTier enchantTier = getTier(tier).orElse(null);
        if (enchantTier == null) {
            return getMaxLevel();
        }
        return enchantTier.maxLevel();
    }

    public int getMaxLevel(PrisonPickaxe pickaxe) {
        return getMaxLevel(pickaxe.getEnchantmentTier(getId()));
    }
}
