package net.staticstudios.prisons.crates;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.staticstudios.gui.StaticGUI;
import net.staticstudios.gui.builder.ButtonBuilder;
import net.staticstudios.gui.builder.GUIBuilder;
import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.utils.ComponentUtil;
import net.staticstudios.prisons.utils.PlayerUtils;
import net.staticstudios.prisons.utils.Prefix;
import net.staticstudios.mines.utils.WeightedElements;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Crate {
    public static void init() {
        StaticPrisons.getInstance().getServer().getPluginManager().registerEvents(new CrateListener(), StaticPrisons.getInstance());
        CRATE_KEY_NAMESPACE_KEY = new NamespacedKey(StaticPrisons.getInstance(), "crateKey");
    }

    public static final List<Crate> CRATES = new ArrayList<>();

    public final String ID;
    public final String DISPLAY_NAME;
    public final String CRATE_KEY_ID;
    public static NamespacedKey CRATE_KEY_NAMESPACE_KEY;
    public final Location LOCATION;
    public final WeightedElements<CrateReward> REWARDS;


    public Crate(String id, String displayName, String crateKeyID, Location location, WeightedElements<CrateReward> rewards) {
        ID = id;
        DISPLAY_NAME = displayName;
        CRATE_KEY_ID = crateKeyID;
        LOCATION = location.toBlockLocation();
        REWARDS = rewards;
        CRATES.add(this);
    }


    public void open(Player player) {
        LOCATION.getWorld().spawnParticle(Particle.TOTEM, LOCATION.getBlockX() + 0.5d, LOCATION.getBlockY() + 1, LOCATION.getBlockZ() + 0.5d, 100);
        CrateReward reward = REWARDS.getRandom();
        ItemStack itemReward = reward.getItemReward(player);


        TextColor color = ComponentUtil.GREEN;

        double chance = REWARDS.getChance(reward);
        if (chance <= 1) {
            color = ComponentUtil.DARK_RED;
        } else if (chance <= 5) {
            color = ComponentUtil.RED;
        } else if (chance <= 25) {
            color = ComponentUtil.YELLOW;
        } else if (chance <= 50) {
            color = ComponentUtil.GOLD;
        }

        Component rewardMessage = Prefix.CRATES
                .append(Component.text((chance <= 2.5 ? player.getName() : "You") + " won " + itemReward.getAmount() + "x "))
                    .append(Objects.requireNonNull(itemReward.getItemMeta().displayName()).append(Component.text("!")));
        rewardMessage = rewardMessage.append(Component.text(" (" + new DecimalFormat("0.0").format(chance) + "% chance) ").color(color))
                .append(Component.text("from a " + DISPLAY_NAME + "!").color(ComponentUtil.WHITE));

        if (chance <= 2.5) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.sendMessage(rewardMessage);
            }
        } else {
            player.sendMessage(rewardMessage);
        }
        PlayerUtils.addToInventory(player, itemReward);

    }

    public void preview(Player player) {
        int size = REWARDS.getElements().size();
        if (size % 9 != 0) {
            size = (size / 9 + 1) * 9;
        }

        StaticGUI gui = new GUIBuilder()
                .title(DISPLAY_NAME + " (Rewards)")
                .size(size)
                .build();

        for (int i = 0; i < REWARDS.getElements().size(); i++) {
            CrateReward reward = REWARDS.getElement(i).element();
            TextColor color = ComponentUtil.GREEN;

            double chance = REWARDS.getChance(i);
            if (chance <= 1) {
                color = ComponentUtil.DARK_RED;
            } else if (chance <= 5) {
                color = ComponentUtil.RED;
            } else if (chance <= 25) {
                color = ComponentUtil.YELLOW;
            } else if (chance <= 50) {
                color = ComponentUtil.GOLD;
            }

            List<Component> lore = reward.getIcon().lore() == null ? new ArrayList<>() : reward.getIcon().lore();

            lore.add(Component.empty());
            lore.add(Component.text("--------------------").color(ComponentUtil.WHITE));
            lore.add(Component.empty().append(Component.text("Chance to win: ")).color(color)
                    .append(Component.text(new DecimalFormat("0.00").format(chance) + "%").color(ComponentUtil.WHITE)));
            lore.add(Component.text("--------------------").color(ComponentUtil.WHITE));

            gui.setButton(i, ButtonBuilder.builder()
                    .fromItem(reward.getIcon())
                    .count(reward.getIcon().getAmount())
                    .lore(lore)
                    .build());
        }
        gui.open(player);
    }


}
