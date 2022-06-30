package net.staticstudios.prisons.auctionHouse;

import net.md_5.bungee.api.ChatColor;
import net.staticstudios.gui.GUICreator;
import net.staticstudios.gui.GUIUtils;
import net.staticstudios.prisons.data.serverData.ServerData;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class AuctionHouseMenus extends GUIUtils {

    static final int AUCTIONS_PER_PAGE = 45;

    public static void openMenu(Player player, int page) {
        int startIndex = page * AUCTIONS_PER_PAGE;
        GUICreator c = new GUICreator(54, "Auction House (Page " + (page + 1) + ")");
        AuctionManager.updateExpiredAuctions();
        for (int i = 0; i < AUCTIONS_PER_PAGE; i++) {
            if (AuctionManager.auctions.size() - 1 < startIndex + i) break;
            Auction auction = AuctionManager.auctions.get(startIndex + i);
            ItemStack item = auction.item().clone();
            List<String> lore = item.getItemMeta().getLore();
            if (lore == null) lore = new ArrayList<>();
            lore.add("&f---------------");
            lore.add("&aExpires in: &f" + PrisonUtils.formatTime((auction.expireAt() - Instant.now().getEpochSecond()) * 1000));

            lore.add("&aSold by: &f" + ServerData.PLAYERS.getName(auction.owner()));
            lore.add("&aPrice: &f$" + PrisonUtils.prettyNum(auction.price()));
            lore.add("");
            if (auction.owner().equals(player.getUniqueId())) {
                lore.add("&7&oClick to reclaim this!");
            } else lore.add("&7&oClick to buy!");
            PrisonUtils.applyLoreToItem(item, lore);
            c.setItem(i, c.createButton(item, (p, t) -> {
                if (!AuctionManager.auctions.contains(auction)) {
                    p.sendMessage(ChatColor.RED + "This auction no longer exists");
                    openMenu(p, page);
                    return;
                }
                AuctionManager.attemptToBuyAuction(auction, p);
                openMenu(p, page);
            }));
        }

        c.setItem(45, c.createButton(Material.ENDER_CHEST, "&dClaim Expired Auctions", List.of("Claim all of your expired Auctions"), (p, t) -> {
            AuctionManager.reclaimExpiredAuctions(p);
        }));
        c.setItem(46, createGrayPlaceHolder());
        c.setItem(47, createGrayPlaceHolder());
        if (page > 0) {
            c.setItem(48, c.createButton(Material.ARROW, "&aPrevious Page", List.of(), (p, t) -> {
                openMenu(p, page - 1);
            }));
        } else c.setItem(48, createGrayPlaceHolder());
        c.setItem(49, c.createButton(Material.PAPER, "&bCurrent Page: &f" + (page + 1), List.of()));
        if (AuctionManager.auctions.size() - 1 > (page + 1) * AUCTIONS_PER_PAGE) {
            c.setItem(50, c.createButton(Material.ARROW, "&aNext Page", List.of(), (p, t) -> {
                openMenu(p, page + 1);
            }));
        } else c.setItem(50, createGrayPlaceHolder());
        c.setItem(51, createGrayPlaceHolder());
        c.setItem(52, createGrayPlaceHolder());
        c.setItem(53, createGrayPlaceHolder());
        c.open(player);
    }
}
