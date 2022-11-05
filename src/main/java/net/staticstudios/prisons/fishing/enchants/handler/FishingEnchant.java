package net.staticstudios.prisons.fishing.enchants.handler;

import net.staticstudios.prisons.enchants.ConfigurableEnchantment;
import net.staticstudios.prisons.enchants.Enchantable;
import net.staticstudios.prisons.fishing.PrisonFishingRod;
import net.staticstudios.prisons.fishing.events.FishCaughtEvent;
import org.bukkit.entity.Player;

public abstract class FishingEnchant extends ConfigurableEnchantment<FishCaughtEvent> {

    private final Class<? extends FishingEnchant> enchantClass;

    public FishingEnchant(Class<? extends FishingEnchant> enchantClass, String id) {
        super(id);
        this.enchantClass = enchantClass;

        register();
    }

    @Override
    public Class<FishCaughtEvent> getListeningFor() {
        return FishCaughtEvent.class;
    }

    @Override
    public boolean beforeEvent(FishCaughtEvent event) {
        PrisonFishingRod fishingRod = event.getFishingRod();
        return fishingRod != null && fishingRod.getEnchantmentLevel(enchantClass) > 0 && shouldActivate(fishingRod);
    }

    @Override
    public void onHold(Enchantable enchantable, Player player) {
    }

    @Override
    public void onUnHold(Enchantable enchantable, Player player) {
    }

    @Override
    public void onUpgrade(Enchantable enchantable, Player player, int oldLevel, int newLevel) {
    }
}
