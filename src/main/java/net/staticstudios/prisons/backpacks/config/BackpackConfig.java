package net.staticstudios.prisons.backpacks.config;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.utils.ComponentUtil;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class BackpackConfig {

    public static final NamespacedKey BACKPACK_KEY = new NamespacedKey(StaticPrisons.getInstance(), "backpackUUID");
    public static final Component DESCRIPTION_TEXT = Component.text("Store additional items in your inventory").color(ComponentUtil.LIGHT_GRAY).decorate(TextDecoration.ITALIC);
    public static final Component CLICK_TO_UPGRADE_TEXT = Component.text("Right-click to upgrade!").color(ComponentUtil.LIGHT_GRAY).decoration(TextDecoration.ITALIC, false);

    private static final Map<Integer, BackpackTier> TIERS = new HashMap<>();

    public static BackpackTier tier(int tier) {
        return TIERS.getOrDefault(tier, TIERS.get(1));
    }

    public static void loadFromFile() {
        File file = new File(StaticPrisons.getInstance().getDataFolder(), "backpacks.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        for (String key : config.getKeys(false)) {
            int tier = Integer.parseInt(key);
            TIERS.put(tier,
                    new BackpackTier(tier,
                            config.getString(key + ".skin"),
                            config.getLong(key + ".max_size"),
                            Component.empty().append(StaticPrisons.miniMessage().deserialize(Objects.requireNonNull(config.getString(key + ".name")))))
            );
        }
    }
}
