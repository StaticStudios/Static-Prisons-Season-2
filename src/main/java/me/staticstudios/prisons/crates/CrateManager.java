package me.staticstudios.prisons.crates;

import me.staticstudios.prisons.Main;
import me.staticstudios.prisons.gui.GUI;
import org.bukkit.NamespacedKey;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class CrateManager {

    public static boolean checkIfCrateWasClicked(PlayerInteractEvent e) {
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (!isCrateKeyType(e.getPlayer().getInventory().getItemInMainHand(), "common") && e.getClickedBlock().getLocation().equals(CommonCrate.LOCATION)) {
                GUI.getGUIPage("crateRewardsCommon").open(e.getPlayer());
                e.setCancelled(true);
                return false;
            }
            if (!isCrateKeyType(e.getPlayer().getInventory().getItemInMainHand(), "rare") && e.getClickedBlock().getLocation().equals(RareCrate.LOCATION)) {
                GUI.getGUIPage("crateRewardsRare").open(e.getPlayer());
                e.setCancelled(true);
                return false;
            }
            if (!isCrateKeyType(e.getPlayer().getInventory().getItemInMainHand(), "epic") && e.getClickedBlock().getLocation().equals(EpicCrate.LOCATION)) {
                GUI.getGUIPage("crateRewardsEpic").open(e.getPlayer());
                e.setCancelled(true);
                return false;
            }
            if (!isCrateKeyType(e.getPlayer().getInventory().getItemInMainHand(), "legendary") && e.getClickedBlock().getLocation().equals(LegendaryCrate.LOCATION)) {
                GUI.getGUIPage("crateRewardsLegendary").open(e.getPlayer());
                e.setCancelled(true);
                return false;
            }
            if (!isCrateKeyType(e.getPlayer().getInventory().getItemInMainHand(), "static") && e.getClickedBlock().getLocation().equals(StaticCrate.LOCATION)) {
                GUI.getGUIPage("crateRewardsStatic").open(e.getPlayer());
                e.setCancelled(true);
                return false;
            }
            if (!isCrateKeyType(e.getPlayer().getInventory().getItemInMainHand(), "staticp") && e.getClickedBlock().getLocation().equals(StaticpCrate.LOCATION)) {
                GUI.getGUIPage("crateRewardsStaticp").open(e.getPlayer());
                e.setCancelled(true);
                return false;
            }
            if (!isCrateKeyType(e.getPlayer().getInventory().getItemInMainHand(), "vote") && e.getClickedBlock().getLocation().equals(VoteCrate.LOCATION)) {
                GUI.getGUIPage("crateRewardsVote").open(e.getPlayer());
                e.setCancelled(true);
                return false;
            }
            if (!isCrateKeyType(e.getPlayer().getInventory().getItemInMainHand(), "pickaxe") && e.getClickedBlock().getLocation().equals(PickaxeCrate.LOCATION)) {
                GUI.getGUIPage("crateRewardsPickaxe").open(e.getPlayer());
                e.setCancelled(true);
                return false;
            }
            if (!isCrateKeyType(e.getPlayer().getInventory().getItemInMainHand(), "kit") && e.getClickedBlock().getLocation().equals(KitCrate.LOCATION)) {
                GUI.getGUIPage("crateRewardsKit").open(e.getPlayer());
                e.setCancelled(true);
                return false;
            }
            if (e.getClickedBlock().getLocation().equals(CommonCrate.LOCATION) && isCrateKeyType(e.getPlayer().getInventory().getItemInMainHand(), "common")) {
                CommonCrate.open(e.getPlayer());
                e.getPlayer().getInventory().getItemInMainHand().setAmount(e.getPlayer().getInventory().getItemInMainHand().getAmount() - 1);
                e.setCancelled(true);
                return true;
            }
            if (e.getClickedBlock().getLocation().equals(RareCrate.LOCATION) && isCrateKeyType(e.getPlayer().getInventory().getItemInMainHand(), "rare")) {
                RareCrate.open(e.getPlayer());
                e.getPlayer().getInventory().getItemInMainHand().setAmount(e.getPlayer().getInventory().getItemInMainHand().getAmount() - 1);
                e.setCancelled(true);
                return true;
            }
            if (e.getClickedBlock().getLocation().equals(EpicCrate.LOCATION) && isCrateKeyType(e.getPlayer().getInventory().getItemInMainHand(), "epic")) {
                EpicCrate.open(e.getPlayer());
                e.getPlayer().getInventory().getItemInMainHand().setAmount(e.getPlayer().getInventory().getItemInMainHand().getAmount() - 1);
                e.setCancelled(true);
                return true;
            }
            if (e.getClickedBlock().getLocation().equals(LegendaryCrate.LOCATION) && isCrateKeyType(e.getPlayer().getInventory().getItemInMainHand(), "legendary")) {
                LegendaryCrate.open(e.getPlayer());
                e.getPlayer().getInventory().getItemInMainHand().setAmount(e.getPlayer().getInventory().getItemInMainHand().getAmount() - 1);
                e.setCancelled(true);
                return true;
            }
            if (e.getClickedBlock().getLocation().equals(StaticCrate.LOCATION) && isCrateKeyType(e.getPlayer().getInventory().getItemInMainHand(), "static")) {
                StaticCrate.open(e.getPlayer());
                e.getPlayer().getInventory().getItemInMainHand().setAmount(e.getPlayer().getInventory().getItemInMainHand().getAmount() - 1);
                e.setCancelled(true);
                return true;
            }
            if (e.getClickedBlock().getLocation().equals(StaticpCrate.LOCATION) && isCrateKeyType(e.getPlayer().getInventory().getItemInMainHand(), "staticp")) {
                StaticpCrate.open(e.getPlayer());
                e.getPlayer().getInventory().getItemInMainHand().setAmount(e.getPlayer().getInventory().getItemInMainHand().getAmount() - 1);
                e.setCancelled(true);
                return true;
            }
            if (e.getClickedBlock().getLocation().equals(VoteCrate.LOCATION) && isCrateKeyType(e.getPlayer().getInventory().getItemInMainHand(), "vote")) {
                VoteCrate.open(e.getPlayer());
                e.getPlayer().getInventory().getItemInMainHand().setAmount(e.getPlayer().getInventory().getItemInMainHand().getAmount() - 1);
                e.setCancelled(true);
                return true;
            }
            if (e.getClickedBlock().getLocation().equals(PickaxeCrate.LOCATION) && isCrateKeyType(e.getPlayer().getInventory().getItemInMainHand(), "pickaxe")) {
                PickaxeCrate.open(e.getPlayer());
                e.getPlayer().getInventory().getItemInMainHand().setAmount(e.getPlayer().getInventory().getItemInMainHand().getAmount() - 1);
                e.setCancelled(true);
                return true;
            }
            if (e.getClickedBlock().getLocation().equals(KitCrate.LOCATION) && isCrateKeyType(e.getPlayer().getInventory().getItemInMainHand(), "kit")) {
                KitCrate.open(e.getPlayer());
                e.getPlayer().getInventory().getItemInMainHand().setAmount(e.getPlayer().getInventory().getItemInMainHand().getAmount() - 1);
                e.setCancelled(true);
                return true;
            }
        } else if (e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
            //Display possible rewards
            if (e.getClickedBlock().getLocation().equals(CommonCrate.LOCATION)) {
                GUI.getGUIPage("crateRewardsCommon").open(e.getPlayer());
                e.setCancelled(true);
                return true;
            }
            if (e.getClickedBlock().getLocation().equals(RareCrate.LOCATION)) {
                GUI.getGUIPage("crateRewardsRare").open(e.getPlayer());
                e.setCancelled(true);
                return true;
            }
            if (e.getClickedBlock().getLocation().equals(EpicCrate.LOCATION)) {
                GUI.getGUIPage("crateRewardsEpic").open(e.getPlayer());
                e.setCancelled(true);
                return true;
            }
            if (e.getClickedBlock().getLocation().equals(LegendaryCrate.LOCATION)) {
                GUI.getGUIPage("crateRewardsLegendary").open(e.getPlayer());
                e.setCancelled(true);
                return true;
            }
            if (e.getClickedBlock().getLocation().equals(StaticCrate.LOCATION)) {
                GUI.getGUIPage("crateRewardsStatic").open(e.getPlayer());
                e.setCancelled(true);
                return true;
            }
            if (e.getClickedBlock().getLocation().equals(StaticpCrate.LOCATION)) {
                GUI.getGUIPage("crateRewardsStaticp").open(e.getPlayer());
                e.setCancelled(true);
                return true;
            }
            if (e.getClickedBlock().getLocation().equals(VoteCrate.LOCATION)) {
                GUI.getGUIPage("crateRewardsVote").open(e.getPlayer());
                e.setCancelled(true);
                return true;
            }
            if (e.getClickedBlock().getLocation().equals(PickaxeCrate.LOCATION)) {
                GUI.getGUIPage("crateRewardsPickaxe").open(e.getPlayer());
                e.setCancelled(true);
                return true;
            }
            if (e.getClickedBlock().getLocation().equals(KitCrate.LOCATION)) {
                GUI.getGUIPage("crateRewardsKit").open(e.getPlayer());
                e.setCancelled(true);
                return true;
            }
        }
        return false;
    }
    static boolean isCrateKey(ItemStack item) {
        if (item == null) return false;
        if (!item.hasItemMeta()) return false;
        if (!item.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(Main.getMain(), "customItemGroup"), PersistentDataType.STRING)) return false;
        return item.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(Main.getMain(), "customItemGroup"), PersistentDataType.STRING).equals("crateKey");
    }

    static boolean isCrateKeyType(ItemStack item, String type) {
        if (!isCrateKey(item)) return false;
        if (!item.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(Main.getMain(), "customItemType"), PersistentDataType.STRING)) return false;
        return item.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(Main.getMain(), "customItemType"), PersistentDataType.STRING).equals(type);
    }
}
