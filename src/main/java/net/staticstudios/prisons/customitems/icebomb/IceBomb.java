package net.staticstudios.prisons.customitems.icebomb;

import com.sk89q.worldedit.math.BlockVector3;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.customitems.handler.CustomItem;
import net.staticstudios.prisons.minebombs.MineBomb;
import net.staticstudios.prisons.utils.ComponentUtil;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class IceBomb implements CustomItem {
    public static final double TOKENATOR_PROC_CHANCE = 0.15;
    static final Set<BlockVector3> POSITIONS = new HashSet<>();
    static final NamespacedKey NAMESPACE = new NamespacedKey(StaticPrisons.getInstance(), "ice_bomb");

    public static void init() {
        POSITIONS.clear();
        POSITIONS.addAll(MineBomb.makeSphere(3));

        StaticPrisons.getInstance().getServer().getPluginManager().registerEvents(new IceBombListener(), StaticPrisons.getInstance());

        new IceBomb();
    }

    IceBomb() {
        register();
    }


    @Override
    public String getID() {
        return "ice_bomb";
    }

    @Override
    public ItemStack getItem(Player player) {
        ItemStack itemStack = new ItemStack(Material.SNOWBALL);
        itemStack.editMeta(meta -> {
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
            meta.displayName(Component.empty().decoration(TextDecoration.ITALIC, false)
                    .append(Component.text("Ice Bomb")
                            .color(ComponentUtil.AQUA)
                    .decorate(TextDecoration.BOLD)));
            meta.lore(List.of(
                    Component.empty().append(Component.text("Throw me in a mine to freeze a portion of it!")
                            .color(ComponentUtil.LIGHT_GRAY)).decoration(TextDecoration.ITALIC, false),
                    Component.empty().append(Component.text("Frozen blocks generate lots of tokens!")
                            .color(ComponentUtil.LIGHT_GRAY)).decoration(TextDecoration.ITALIC, false),
                    Component.empty(),
                    Component.empty().append(Component.text("Ice has a " + ((int) (TOKENATOR_PROC_CHANCE * 100)) + "% chance to activate Tokenator")
                            .color(ComponentUtil.YELLOW)).decoration(TextDecoration.ITALIC, false)
                    )
            );
            meta.getPersistentDataContainer().set(NAMESPACE, PersistentDataType.BYTE, (byte) 0);
            meta.addEnchant(Enchantment.LURE, 1, true);
        });

        return itemStack;
    }

    @Override
    public boolean onInteract(PlayerInteractEvent e) {
        if (e.getItem() == null) return false;
        if (e.getItem().getType() != Material.SNOWBALL) return false;
        if (!e.getItem().getItemMeta().getPersistentDataContainer().has(NAMESPACE, PersistentDataType.BYTE)) return false;

        e.setCancelled(true);

        e.getItem().setAmount(e.getItem().getAmount() - 1);

        Snowball snowball = e.getPlayer().getWorld().spawn(e.getPlayer().getEyeLocation(), Snowball.class);
        snowball.setShooter(new IceBombSource(e.getPlayer()));
        snowball.setVelocity(e.getPlayer().getLocation().getDirection().multiply(1));


        return true;
    }
}
