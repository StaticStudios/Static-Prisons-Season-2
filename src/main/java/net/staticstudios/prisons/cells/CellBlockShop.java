package net.staticstudios.prisons.cells;

import net.staticstudios.gui.GUICreator;
import net.staticstudios.gui.GUIUtils;
import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.math.BigInteger;
import java.util.*;

public class CellBlockShop {

    static Map<Material, BigInteger> BLOCK_PRICES = new LinkedHashMap<>();
    static List<Material> ORDERED_BLOCKS = new ArrayList<>();

    protected static void init() {
        FileConfiguration config = YamlConfiguration.loadConfiguration(new File(StaticPrisons.getInstance().getDataFolder(), "cell-block-shop-config.yml"));
        for (String block : config.getConfigurationSection("blocks").getKeys(false)) {
            try {
                BLOCK_PRICES.put(Material.valueOf(block), new BigInteger(config.getString("blocks." + block)));
                ORDERED_BLOCKS.add(Material.valueOf(block));
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
    }

    public static final int BLOCKS_PER_PAGE = 45;
    public static void openMenu(Player player, int page, boolean fromCommand) {
        GUICreator c = new GUICreator(54, "Cell Block Shop");
        int startIndex = page * BLOCKS_PER_PAGE;


        for (int i = 0; i < BLOCKS_PER_PAGE; i++) {
            if (BLOCK_PRICES.size() - 1 < startIndex + i) break;
            Material mat = ORDERED_BLOCKS.get(startIndex + i);
            BigInteger price = BLOCK_PRICES.get(mat);
            String itemName = PrisonUtils.Items.getPrettyItemName(new ItemStack(mat));


            c.setItem(i, PrisonUtils.setItemCount(c.createButton(mat, ChatColor.YELLOW + "64x " + itemName, List.of(
                    "&6Click to buy 64x " + itemName,
                    "&6Price: &f" + PrisonUtils.prettyNum(price) + " Tokens"
            ), (p, t) -> {
                PlayerData playerData = new PlayerData(p);
                if (playerData.getTokens().compareTo(price) < 0) {
                    p.sendMessage(ChatColor.RED + "You don't have enough tokens to buy this!");
                    return;
                }
                playerData.removeTokens(price);
                PrisonUtils.Players.addToInventory(p, new ItemStack(mat, 64));
                p.sendMessage(ChatColor.GREEN + "You bought 64x " + itemName + " for " + PrisonUtils.prettyNum(price) + " Tokens!");
                openMenu(p, page, fromCommand);
            }), 64));
        }
        c.setItem(45, GUIUtils.createGrayPlaceHolder());
        c.setItem(46, GUIUtils.createGrayPlaceHolder());
        c.setItem(47, GUIUtils.createGrayPlaceHolder());
        if (page > 0) {
            c.setItem(48, c.createButton(Material.ARROW, "&aPrevious Page", List.of(), (p, t) -> {
                openMenu(p, page - 1, fromCommand);
            }));
        } else c.setItem(48, GUIUtils.createGrayPlaceHolder());
        c.setItem(49, c.createButton(Material.PAPER, "&bCurrent Page: &f" + (page + 1), List.of()));
        if (BLOCK_PRICES.size() - 1 > (page + 1) * BLOCKS_PER_PAGE) {
            c.setItem(50, c.createButton(Material.ARROW, "&aNext Page", List.of(), (p, t) -> {
                openMenu(p, page + 1, fromCommand);
            }));
        } else c.setItem(50, GUIUtils.createGrayPlaceHolder());
        c.setItem(51, GUIUtils.createGrayPlaceHolder());
        c.setItem(52, GUIUtils.createGrayPlaceHolder());
        c.setItem(53, GUIUtils.createGrayPlaceHolder());
        c.fill(GUIUtils.createLightGrayPlaceHolder());
        c.setOnCloseRun((p, t) -> CellMenus.openMenu(p, fromCommand));
        c.open(player);
    }
}
