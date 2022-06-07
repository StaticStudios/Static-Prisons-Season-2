package net.staticstudios.prisons.crates;

import net.staticstudios.gui.GUICreator;
import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.utils.PrisonUtils;
import net.staticstudios.utils.WeightedElements;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Crate {
    public static final String CRATE_PREFIX = ChatColor.GOLD + "" + ChatColor.BOLD + "Crates " + ChatColor.DARK_GRAY + ChatColor.BOLD + ">> " + ChatColor.RESET;

    public static void init() {
        StaticPrisons.getInstance().getServer().getPluginManager().registerEvents(new Listener(), StaticPrisons.getInstance());
        CRATE_KEY_NAMESPACE_KEY = new NamespacedKey(StaticPrisons.getInstance(), "crateKey");
    }

    private static final List<Crate> crates = new ArrayList<>();

    public final String ID;
    public final String DISPLAY_NAME;
    public final String CRATE_KEY_ID;
    private static NamespacedKey CRATE_KEY_NAMESPACE_KEY;
    public final Location LOCATION;
    public final WeightedElements<CrateReward> REWARDS;

    private Consumer<Player> runOnOpen;

    public void setRunOnOpen(Consumer<Player> runOnOpen) {
        this.runOnOpen = runOnOpen;
    }
    public Consumer<Player> getRunOnOpen() {
        return runOnOpen;
    }


    public Crate(String id, String displayName, String crateKeyID, Location location, WeightedElements<CrateReward> rewards) {
        ID = id;
        DISPLAY_NAME = displayName;
        CRATE_KEY_ID = crateKeyID;
        LOCATION = location.toBlockLocation();
        REWARDS = rewards;
        crates.add(this);
    }


    public void open(Player player) {
        LOCATION.getWorld().spawnParticle(Particle.TOTEM, LOCATION.getBlockX() + 0.5d, LOCATION.getBlockY() + 1, LOCATION.getBlockZ() + 0.5d, 100);
        CrateReward reward = REWARDS.getRandom();


        String rewardString = "";
        if (reward.itemReward != null) rewardString = reward.itemReward.getAmount() + "x " + PrisonUtils.getPrettyItemName(reward.itemReward);
        else rewardString = reward.rewardName;
        double chance = REWARDS.getChance(reward);
        String colorPrefix = "&a";
        if (chance <= 1) colorPrefix = "&4&l";
        else if (chance <= 5) colorPrefix = "&c&l";
        else if (chance <= 25) colorPrefix = "&e";
        else if (chance <= 50) colorPrefix = "&6";
        colorPrefix = ChatColor.translateAlternateColorCodes('&', colorPrefix);
        player.sendMessage(CRATE_PREFIX + "You've won " + rewardString + "!" + ChatColor.RESET + colorPrefix + " (" + new DecimalFormat("#.##").format(chance) + "% chance)" + ChatColor.RESET + " from a " + DISPLAY_NAME + "!");
        if (chance < 5) for (Player p : Bukkit.getOnlinePlayers()) p.sendMessage(CRATE_PREFIX + player.getName() + " won " + rewardString + "!" + ChatColor.RESET + colorPrefix + " (" + new DecimalFormat("#.##").format(chance) + "% chance)" + ChatColor.RESET + " from a " + DISPLAY_NAME + "!");
        if (reward.itemReward != null) PrisonUtils.Players.addToInventory(player, new ItemStack(reward.itemReward));
        else reward.runnableReward.accept(player);

        if (runOnOpen != null) runOnOpen.accept(player);
    }

    public void preview(Player player) {
        int size = REWARDS.getElements().size();
        if (size % 9 != 0) size = (size / 9 + 1 ) * 9;
        GUICreator c = new GUICreator(size, DISPLAY_NAME + " (Rewards)");

        for (int i = 0; i < REWARDS.getElements().size(); i++) {
            CrateReward reward = REWARDS.getElement(i).getElement();
            String colorPrefix = "&a";
            double chance = REWARDS.getChance(i);
            if (chance <= 1) colorPrefix = "&4";
            else if (chance <= 5) colorPrefix = "&c";
            else if (chance <= 25) colorPrefix = "&e";
            else if (chance <= 50) colorPrefix = "&6";
            c.setItem(i, PrisonUtils.Items.appendLoreToItem(reward.icon, List.of(
                    "",
                    "&f--------------------",
                    colorPrefix + "Chance to win: &f" + new DecimalFormat("##0.00").format(BigDecimal.valueOf(chance)) + "%",
                    "&f--------------------")));
        }
        c.open(player);
    }


    static class Listener implements org.bukkit.event.Listener {
        @EventHandler
        void onInteract(PlayerInteractEvent e) {
            if (e.getClickedBlock() == null) return;
            Crate crate = null;
            for (Crate c : crates) if (e.getClickedBlock().getLocation().equals(c.LOCATION)) {
                    crate = c;
                    break;
                }
            if (crate == null) return;
            Player player = e.getPlayer();
            ItemStack key = player.getInventory().getItemInMainHand();
            e.setCancelled(true);
            if (key.hasItemMeta() && key.getItemMeta().getPersistentDataContainer().has(CRATE_KEY_NAMESPACE_KEY)) {
                boolean usingKey = key.getItemMeta().getPersistentDataContainer().get(CRATE_KEY_NAMESPACE_KEY, PersistentDataType.STRING).equals(crate.CRATE_KEY_ID);
                if (usingKey && e.getAction().isRightClick()) {
                    crate.open(player);
                    key.setAmount(key.getAmount() - 1);
                } else crate.preview(player);
            } else crate.preview(player);
        }
    }

}
