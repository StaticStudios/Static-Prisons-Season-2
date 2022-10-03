package net.staticstudios.prisons.enchants.testing;

import net.staticstudios.prisons.enchants.EnchantHolder;
import net.staticstudios.prisons.enchants.Enchantable;
import net.staticstudios.prisons.enchants.Enchantment;

import java.util.Map;

public class TestEnchantable implements Enchantable {

    @Override
    public Map<Class<? extends Enchantment<?>>, EnchantHolder> getEnchantments() {
        return null;
    }

    @Override
    public boolean addEnchantment(Class<? extends Enchantment<?>> enchantment, int level) {
        return false;
    }

    @Override
    public boolean removeEnchantment(Class<? extends Enchantment<?>> enchantment) {
        return false;
    }

    @Override
    public boolean disableEnchantment(Class<? extends Enchantment<?>> enchantment) {
        return false;
    }

    @Override
    public boolean enableEnchantment(Class<? extends Enchantment<?>> enchantment) {
        return false;
    }
}
