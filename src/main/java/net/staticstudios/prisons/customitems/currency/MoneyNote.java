package net.staticstudios.prisons.customitems.currency;

import net.kyori.adventure.text.Component;
import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.customitems.CustomItem;
import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.utils.ComponentUtil;
import net.staticstudios.prisons.utils.Prefix;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

public class MoneyNote implements CustomItem {

    public static final MoneyNote INSTANCE = new MoneyNote();

    static final NamespacedKey CURRENCY_VALUE = new NamespacedKey(StaticPrisons.getInstance(), "currency_note_value");

    MoneyNote() {
        register();
    }

    public static ItemStack getMoneyNote(Player player, long amount) {
        return INSTANCE.getItem(player, new String[]{String.valueOf(amount)});
    }

    @Override
    public String getId() {
        return "money_note";
    }

    @Override
    public ItemStack getItem(CommandSender sender) {
        throw new UnsupportedOperationException("This requires args! Please use MoneyNote#getItem(Player, String[])");
    }

    @Override
    public ItemStack getItem(CommandSender sender, String[] args) {

        var optionalLong = validateInput(sender, args[0]);

        if (optionalLong.isEmpty() || optionalLong.get() == -1) {
            return null;
        }

        long value = optionalLong.get();

        if (sender instanceof Player player) {
            PlayerData playerData = new PlayerData(player);

            if (playerData.getMoney() < value) {
                player.sendMessage(Prefix.STATIC_PRISONS
                        .append(Component.text("You don't have enough money to make a note for that amount!")));
                return null;
            }

            playerData.removeMoney(value);
        }

        Component creator = sender instanceof Player player ?
                player.name().color(ComponentUtil.WHITE) :
                Component.text("Server").color(ComponentUtil.RED);

        ItemStack item = new ItemStack(Material.PAPER);
        item.editMeta(meta -> {
            meta.displayName(ComponentUtil.BLANK
                    .append(Component.text("Money Note: ")
                            .color(ComponentUtil.GREEN))
                    .append(Component.text('$' + PrisonUtils.prettyNum(value))));
            meta.lore(List.of(
                    ComponentUtil.BLANK
                            .append(Component.text("Created By: ")
                                    .color(ComponentUtil.GREEN)
                                    .append(creator)),
                    ComponentUtil.BLANK
                            .append(Component.text("Redeem Amount: ")
                                    .color(ComponentUtil.GREEN)
                                    .append(Component.text("$" + PrisonUtils.addCommasToNumber(value))
                                            .color(ComponentUtil.WHITE))),
                    Component.empty(),
                    ComponentUtil.BLANK
                            .append(Component.text("Right-click to redeem!")
                                    .color(ComponentUtil.YELLOW)),
                    ComponentUtil.BLANK
                            .append(Component.text("Shift + right-click to redeem a full stack!")
                                    .color(ComponentUtil.YELLOW))
            ));

            meta.getPersistentDataContainer().set(CURRENCY_VALUE, PersistentDataType.LONG, value);
        });

        setCustomItem(item, this);

        return item;
    }

    @Override
    public boolean onInteract(PlayerInteractEvent e) {
        if (!e.getAction().isRightClick()) {
            return false;
        }
        if (e.getItem() == null || !e.getItem().hasItemMeta()) {
            return false;
        }

        if (!e.getItem().getItemMeta().getPersistentDataContainer().has(CURRENCY_VALUE, PersistentDataType.LONG)) {
            return false;
        }

        long value = e.getItem().getItemMeta().getPersistentDataContainer().get(CURRENCY_VALUE, PersistentDataType.LONG);

        int itemCount = 1;
        if (e.getPlayer().isSneaking()) {
            itemCount = e.getItem().getAmount();
        }
        value *= e.getItem().getAmount();

        PlayerData playerData = new PlayerData(e.getPlayer());
        playerData.addMoney(value);

        e.getPlayer().sendMessage(Prefix.STATIC_PRISONS
                .append(Component.text("You redeemed a money note for ")
                        .color(ComponentUtil.GREEN)
                        .append(Component.text("$" + PrisonUtils.prettyNum(value))
                                .color(ComponentUtil.WHITE))));

        e.getItem().setAmount(e.getItem().getAmount() - itemCount);

        return true;
    }
}
