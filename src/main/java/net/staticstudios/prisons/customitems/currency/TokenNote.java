package net.staticstudios.prisons.customitems.currency;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.staticstudios.prisons.customitems.CustomItem;
import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.utils.ComponentUtil;
import net.staticstudios.prisons.utils.Prefix;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

public class TokenNote implements CustomItem {

    public static final TokenNote INSTANCE = new TokenNote();

    TokenNote() {
        register();
    }

    public static ItemStack getTokenNote(Player player, long amount) {
        return INSTANCE.getItem(player, new String[]{String.valueOf(amount)});
    }

    @Override
    public String getId() {
        return "token_note";
    }

    @Override
    public ItemStack getItem(Audience audience) {
        throw new UnsupportedOperationException("This requires args! Please use TokenNote#getItem(Player, String[])");
    }

    @Override
    public ItemStack getItem(Audience audience, String[] args) {

        var optionalLong = validateInput(audience, args[0]);

        if (optionalLong.isEmpty() || optionalLong.get() == -1) {
            return null;
        }

        long value = optionalLong.get();

        if (audience instanceof Player player) {
            PlayerData playerData = new PlayerData(player);

            if (playerData.getTokens() < value) {
                player.sendMessage(Prefix.STATIC_PRISONS
                        .append(Component.text("You don't have enough tokens to make a note for that amount!")));
                return null;
            }

            playerData.removeTokens(value);
        }

        Component creator = audience instanceof Player player ?
                player.name().color(ComponentUtil.WHITE) :
                Component.text("Server").color(ComponentUtil.RED);

        ItemStack item = new ItemStack(Material.MAGMA_CREAM);
        item.editMeta(meta -> {
            meta.displayName(ComponentUtil.BLANK
                    .append(Component.text("Token Note: ")
                            .color(ComponentUtil.GOLD))
                    .append(Component.text(PrisonUtils.prettyNum(value) + " Tokens")));
            meta.lore(List.of(
                    ComponentUtil.BLANK
                            .append(Component.text("Created By: ")
                                    .color(ComponentUtil.GOLD)
                                    .append(creator)),
                    ComponentUtil.BLANK
                            .append(Component.text("Redeem Amount: ")
                                    .color(ComponentUtil.GOLD)
                                    .append(Component.text(PrisonUtils.addCommasToNumber(value) + " Tokens")))
                            .color(ComponentUtil.WHITE),
                    Component.empty(),
                    ComponentUtil.BLANK
                            .append(Component.text("Right-click to redeem!")
                                    .color(ComponentUtil.YELLOW)),
                    ComponentUtil.BLANK
                            .append(Component.text("Shift + right-click to redeem a full stack!")
                                    .color(ComponentUtil.YELLOW))
            ));

            meta.getPersistentDataContainer().set(MoneyNote.CURRENCY_VALUE, PersistentDataType.LONG, value);
        });

        setCustomItem(item, this);

        return item;
    }

    @Override
    public boolean onInteract(PlayerInteractEvent e) {
        if (e.getItem() != null && !e.getItem().hasItemMeta()) {
            return false;
        }

        if (!e.getItem().getItemMeta().getPersistentDataContainer().has(MoneyNote.CURRENCY_VALUE, PersistentDataType.LONG)) {
            return false;
        }

        long value = e.getItem().getItemMeta().getPersistentDataContainer().get(MoneyNote.CURRENCY_VALUE, PersistentDataType.LONG);

        int itemCount = 1;
        if (e.getPlayer().isSneaking()) {
            itemCount = e.getItem().getAmount();
        }
        value *= e.getItem().getAmount();

        PlayerData playerData = new PlayerData(e.getPlayer());
        playerData.addTokens(value);

        e.getPlayer().sendMessage(Prefix.STATIC_PRISONS
                .append(Component.text("You redeemed a token note for ")
                        .color(ComponentUtil.GOLD)
                        .append(Component.text(PrisonUtils.prettyNum(value) + " Tokens")
                                .color(ComponentUtil.WHITE))));

        e.getItem().setAmount(e.getItem().getAmount() - itemCount);

        return true;
    }
}
