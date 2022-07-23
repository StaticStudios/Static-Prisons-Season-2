package net.staticstudios.prisons.fishing;

import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.customItems.CustomItems;
import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.utils.PrisonUtils;
import net.staticstudios.utils.WeightedElements;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;

import java.math.BigInteger;
import java.util.function.Consumer;

public class FishingManager {

    public static final String PREFIX = ChatColor.translateAlternateColorCodes('&', "&e&lFishing &8&l>> &r");

    public static void init() {
        Bukkit.getPluginManager().registerEvents(new FishingListener(), StaticPrisons.getInstance());

        FishingListener.FISHING_REWARDS = new WeightedElements<Consumer<PlayerFishEvent>>()
                .add(e -> {
                    Player player = e.getPlayer();
                    player.sendMessage(FishingManager.PREFIX + "You caught nothing!");
                    ((Item) e.getCaught()).getItemStack().setAmount(0);
                }, 70)
                .add(e -> {
                    Player player = e.getPlayer();
                    ItemStack reward = CustomItems.getCommonCrateKey(1);
                    player.sendMessage(FishingManager.PREFIX + "You caught a " + PrisonUtils.Items.getPrettyItemName(reward) + "!");
                    ((Item) e.getCaught()).setItemStack(reward);
                }, 10)
                .add(e -> {
                    Player player = e.getPlayer();
                    ItemStack reward = CustomItems.getRareCrateKey(1);
                    player.sendMessage(FishingManager.PREFIX + "You caught a " + PrisonUtils.Items.getPrettyItemName(reward) + "!");
                    ((Item) e.getCaught()).setItemStack(reward);
                }, 5)
                .add(e -> {
                    Player player = e.getPlayer();
                    ItemStack reward = CustomItems.getEpicCrateKey(1);
                    player.sendMessage(FishingManager.PREFIX + "You caught a " + PrisonUtils.Items.getPrettyItemName(reward) + "!");
                    ((Item) e.getCaught()).setItemStack(reward);
                }, 3)
                .add(e -> {
                    Player player = e.getPlayer();
                    ItemStack reward = CustomItems.getLegendaryCrateKey(1);
                    player.sendMessage(FishingManager.PREFIX + "You caught a " + PrisonUtils.Items.getPrettyItemName(reward) + "!");
                    ((Item) e.getCaught()).setItemStack(reward);
                }, 2)
                .add(e -> {
                    Player player = e.getPlayer();
                    ItemStack reward = new ItemStack(Material.PRISMARINE_SHARD);
                    reward.editMeta(meta -> meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&9&l1 Shard")));
                    player.sendMessage(FishingManager.PREFIX + "You caught " + ChatColor.BLUE + "1 shard!");
                    Item item = (Item) e.getCaught();
                    item.setItemStack(reward);
                    item.setPickupDelay(9999);
                    item.setWillAge(true);
                    item.setTicksLived(20 * 60 * 5 - 20); //Despawn in 1 second
                    new PlayerData(player).addShards(BigInteger.ONE);
                }, 5)
                .add(e -> {
                    Player player = e.getPlayer();
                    ItemStack reward = new ItemStack(Material.PRISMARINE_SHARD);
                    reward.editMeta(meta -> meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&9&l2 Shards")));
                    player.sendMessage(FishingManager.PREFIX + "You caught " + ChatColor.BLUE + "2 shards!");
                    Item item = (Item) e.getCaught();
                    item.setItemStack(reward);
                    item.setPickupDelay(9999);
                    item.setWillAge(true);
                    item.setTicksLived(20 * 60 * 5 - 20); //Despawn in 1 second
                    new PlayerData(player).addShards(BigInteger.TWO);
                }, 4)
                .add(e -> {
                    Player player = e.getPlayer();
                    ItemStack reward = new ItemStack(Material.PRISMARINE_SHARD);
                    reward.editMeta(meta -> meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&9&l3 Shards")));
                    player.sendMessage(FishingManager.PREFIX + "You caught " + ChatColor.BLUE + "3 shards!");
                    Item item = (Item) e.getCaught();
                    item.setItemStack(reward);
                    item.setPickupDelay(9999);
                    item.setWillAge(true);
                    item.setTicksLived(20 * 60 * 5 - 20); //Despawn in 1 second
                    new PlayerData(player).addShards(BigInteger.valueOf(3));
                }, 1);

    }
}
