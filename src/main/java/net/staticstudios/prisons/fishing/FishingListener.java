package net.staticstudios.prisons.fishing;

import net.kyori.adventure.text.Component;
import net.staticstudios.prisons.blockbreak.BlockBreak;
import net.staticstudios.prisons.blockbreak.BlockBreakProcessEvent;
import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.fishing.events.FishCaughtEvent;
import net.staticstudios.prisons.pickaxe.PrisonPickaxe;
import net.staticstudios.prisons.pickaxe.enchants.TokenatorEnchant;
import net.staticstudios.prisons.pvp.PvPManager;
import net.staticstudios.mines.utils.WeightedElements;
import net.staticstudios.prisons.utils.ComponentUtil;
import net.staticstudios.prisons.utils.PlayerUtils;
import net.staticstudios.prisons.utils.Prefix;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.persistence.PersistentDataType;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

public class FishingListener implements Listener {

    static WeightedElements<Consumer<PlayerFishEvent>> FISHING_REWARDS = null;

    @EventHandler
    void onFish(PlayerFishEvent e) {
        Player player = e.getPlayer();
        if (!player.getWorld().equals(PvPManager.PVP_WORLD)) {
            e.setCancelled(true);
            player.sendMessage(Prefix.FISHING
                    .append(Component.text("You can only fish in the PvP arena!")
                    .color(ComponentUtil.RED)));
            return;
        }

        PrisonFishingRod fishingRod = PrisonFishingRod.fromItem(e.getPlayer().getInventory().getItemInMainHand());
        if (fishingRod == null) {
            //This is not a prison fishing rod so make it unable to catch fish
            player.sendMessage(Prefix.FISHING
                    .append(Component.text("You cannot fish with this fishing rod!")
                    .color(ComponentUtil.RED)));
            e.getHook().remove();
        }

        assert fishingRod != null;

        if (fishingRod.getDurability() <= 0) {
            player.sendMessage(Prefix.FISHING
                    .append(Component.text("Your fishing rod has broken!")
                    .color(ComponentUtil.RED)));
            e.setCancelled(true);
        }

        e.getHook().setMinWaitTime(0);
        e.getHook().setMaxWaitTime(0);

        if (!e.getState().equals(PlayerFishEvent.State.CAUGHT_FISH)) return;
        //Something was caught

        FishCaughtEvent fishCaughtEvent = new FishCaughtEvent(e, fishingRod);


        fishCaughtEvent.callEvent();

        //todo: default actions. add items from the enchants and such

        e.setCancelled(true);
        e.getHook().remove();
    }

    @EventHandler(priority = EventPriority.LOW)
    void onFishProcess(FishCaughtEvent e) {
        PlayerFishEvent fishEvent = e.getPlayerFishEvent();
        PrisonFishingRod fishingRod = e.getFishingRod();
        Player player = fishEvent.getPlayer();
        PlayerData playerData = new PlayerData(player);

        fishingRod.setDurability(Math.max(0, fishingRod.getDurability() - e.getDurabilityLost()));

        if (e.getReward().item() != null) {
            PlayerUtils.addToInventory(player, e.getReward().item());
        }

        fishingRod.setItemsCaught(fishingRod.getItemsCaught() + 1);
        fishingRod.setXp(fishingRod.getXp() + e.getRodXp());
        playerData.addPlayerXP(e.getReward().playerXp());

        player.sendMessage(fishingRod.getDurability() + " " + PrisonFishingRod.getMaxDurability(fishingRod.getLevel()));
    }

    @EventHandler
    void onEat(PlayerItemConsumeEvent e) {
        if (e.getItem().getItemMeta() == null) return;
        if (e.getItem().getItemMeta().getPersistentDataContainer().has(FishingRewardOutline.KEY, PersistentDataType.INTEGER)) e.setCancelled(true);
    }

    @EventHandler
    void onCraft(CraftItemEvent e) {
        if (e.getCurrentItem() == null) return;
        if (e.getCurrentItem().getType().equals(Material.FISHING_ROD)) e.setCancelled(true);
    }
}
