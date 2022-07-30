package net.staticstudios.prisons.trading;

import net.staticstudios.gui.GUICreator;
import net.staticstudios.gui.GUIUtils;
import net.staticstudios.gui.StaticGUI;
import net.staticstudios.prisons.customItems.CustomItems;
import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.pvp.PvPManager;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.math.BigInteger;
import java.util.List;

public class Trade {
    public static final String PREFIX = "&b&lTrading &8&l>> &r";

    private static final int CONT_DOWN = 9; //Seconds that have to be waited after both players confirm the trade. Players can cancel during this time.


    private StaticGUI player1GUI; //The GUI that is displayed to the player1
    private StaticGUI player2GUI; //The GUI that is displayed to the player2

    private Player player1; //Player will have access to the left side of the GUI
    private Player player2; //Player will have access to the right side of the GUI

    private ItemStack[] player1ItemOffer = new ItemStack[16];
    private ItemStack[] player2ItemOffer = new ItemStack[16];

    private BigInteger player1MoneyOffer = BigInteger.ZERO; //The amount of money that player 1 is offering to trade
    private BigInteger player1TokenOffer = BigInteger.ZERO; //The amount of tokens that player 1 is offering to trade
    private BigInteger player2MoneyOffer = BigInteger.ZERO; //The amount of money that player 2 is offering to trade
    private BigInteger player2TokenOffer = BigInteger.ZERO; //The amount of tokens that player 2 is offering to trade

    private boolean player1Accepted;
    private boolean player2Accepted;



    private boolean verifyThatTradeCanHappen() {
        return player1 != null && player2 != null && !player1.getWorld().equals(PvPManager.PVP_WORLD) && !player2.getWorld().equals(PvPManager.PVP_WORLD);
    }

    public static void test(Player p) {
        Trade t = new Trade();
        t.player1 = p;
        t.player2 = Bukkit.getPlayer("Sirkeatonv");
        t.createGUI();
    }

    private void createGUI() { //todo, if a player closes the gui, the trade should be canceled. offer money and tokens, accept countdown
        player1ItemOffer[7] = CustomItems.getPickaxeCrateKey(3);
        player1GUI = new GUICreator(54, "Trading with " + player2.getName());
        player2GUI = new GUICreator(54, "Trading with " + player1.getName());

        updateMenuContents();

        player1GUI.setOnClickEmptySlot(e -> {
            if (!((e.getSlot() + 1) % 9 < 5)) { //Ensure it is on the player1 side of the GUI
                e.setCancelled(true);
                return;
            }
            if (e.getCursor() == null) return; //They are not putting an item in the GUI
            e.setCancelled(true);
            player1ItemOffer[((e.getSlot() + 1) % 9 + (e.getSlot() + 1) / 9 * 4) - 1] = e.getCursor();
            e.setCursor(null);
            e.setCurrentItem(null);
            updateMenuContents();
        });
        player2GUI.setOnClickEmptySlot(e -> {
            if (!((e.getSlot() + 1) % 9 < 5)) { //Ensure it is on the player2 side of the GUI
                e.setCancelled(true);
                return;
            }
            if (e.getCursor() == null) return; //They are not putting an item in the GUI
            e.setCancelled(true);
            player2ItemOffer[((e.getSlot() + 1) % 9 + (e.getSlot() + 1) / 9 * 4) - 1] = e.getCursor();
            e.setCursor(null);
            e.setCurrentItem(null);
            updateMenuContents();
        });

        //temp
        player1GUI.open(player1);
        player2GUI.open(player2);
    }

    void updateMenuContents() {
        updatePlayer1Menu();
        updatePlayer2Menu();
    }

    void updatePlayer1Menu() {
        player1GUI.setItems(); //Clear the menu

        player1GUI.setItem(4, GUIUtils.createGrayPlaceHolder());
        player1GUI.setItem(13, GUIUtils.createGrayPlaceHolder());
        player1GUI.setItem(22, GUIUtils.createGrayPlaceHolder());
        player1GUI.setItem(31, GUIUtils.createGrayPlaceHolder());
        for (int i = 36; i < 45; i++) player1GUI.setItem(i, GUIUtils.createGrayPlaceHolder());
        player1GUI.setItem(49, GUIUtils.createGrayPlaceHolder());
        player1GUI.setItem(46, GUIUtils.createGrayPlaceHolder());
        player1GUI.setItem(52, GUIUtils.createGrayPlaceHolder());


        //Accept trade buttons
        player1GUI.setItem(45, player1GUI.createButton(Material.LIME_STAINED_GLASS_PANE, "&a&lAccept Trade", List.of(
                "Once both players accept the trade",
                "a " + CONT_DOWN + " second countdown will begin. Both",
                "players will have the option to cancel",
                "the trade during this time. After the",
                "count down is over, the trade will finish",
                "", "&b&oClick to accept this trade"
        )));
        player1GUI.setItem(53, player1GUI.createButton(Material.LIME_STAINED_GLASS_PANE, "&a&lHas " + player2.getName() + " Accepted", List.of(
                "Once both players accept the trade",
                "a " + CONT_DOWN + " second countdown will begin. Both",
                "players will have the option to cancel",
                "the trade during this time. After the",
                "count down is over, the trade will finish"
        )));

        //Your tokens & money
        player1GUI.setItem(47, player1GUI.createButton(Material.SUNFLOWER, "&c&lOffer Tokens", List.of(
                "&cCurrently Offering: &f" + PrisonUtils.prettyNum(player1TokenOffer) + " Tokens",
                "&cYour Tokens: &f$" + PrisonUtils.prettyNum(new PlayerData(player1).getTokens()),
                "",
                "&oClick to edit this offer"
        ), (p, t) -> {
            //todo
        }));
        player1GUI.setItem(48, player1GUI.createButton(Material.PAPER, "&a&lOffer Money", List.of(
                "&aCurrently Offering: &f$" + PrisonUtils.prettyNum(player1MoneyOffer),
                "&aYour Balance: &f$" + PrisonUtils.prettyNum(new PlayerData(player1).getMoney()),
                "",
                "&oClick to edit this offer"
        ), (p, t) -> {
            //todo
        }));

        //Their tokens & money
        player1GUI.setItem(50, player1GUI.createButton(Material.PAPER, "&a" + player2.getName() + "'s Money Offer", List.of(
                "&aOffering: &f$" + PrisonUtils.prettyNum(player2MoneyOffer),
                "&aTheir Balance: &f$" + PrisonUtils.prettyNum(new PlayerData(player2).getMoney())
        ), (p, t) -> {
            player1.sendMessage(ChatColor.translateAlternateColorCodes('&', PREFIX + player2.getName() + " is offering &a$" + PrisonUtils.addCommasToNumber(player2MoneyOffer)));
        }));
        player1GUI.setItem(51, player1GUI.createButton(Material.SUNFLOWER, "&c" + player2.getName() + "'s Tokens Offer", List.of(
                "&cOffering: &f" + PrisonUtils.prettyNum(player2TokenOffer) + " Tokens",
                "&cTheir Tokens: &f" + PrisonUtils.prettyNum(new PlayerData(player2).getTokens())
        ), (p, t) -> {
            player1.sendMessage(ChatColor.translateAlternateColorCodes('&', PREFIX + player2.getName() + " is offering &c" + PrisonUtils.addCommasToNumber(player2TokenOffer) + " Tokens"));
        }));

        //Display the actual items that are being offered by player1
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                ItemStack item = player1ItemOffer[x + y * 4];
                if (item == null || item.getType().equals(Material.AIR)) continue;
                player1GUI.setItem(x + y * 9, player1GUI.createButton(item.clone(), (e -> {
                    e.setCancelled(true);
                    player1ItemOffer[((e.getSlot() + 1) % 9 + (e.getSlot() + 1) / 9 * 4) - 1] = e.getCursor();
                    e.setCursor(item);
                    updateMenuContents();
                })));
            }
        }

        //Display the actual items that are being offered by player2
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                ItemStack item = player2ItemOffer[x + y * 4];
                if (item == null || item.getType().equals(Material.AIR)) continue;
                player1GUI.setItem(x + 5 + y * 9, item.clone());
            }
        }
    }
    void updatePlayer2Menu() {
        player2GUI.setItems(); //Clear the menu

        player2GUI.setItem(4, GUIUtils.createGrayPlaceHolder());
        player2GUI.setItem(13, GUIUtils.createGrayPlaceHolder());
        player2GUI.setItem(22, GUIUtils.createGrayPlaceHolder());
        player2GUI.setItem(31, GUIUtils.createGrayPlaceHolder());
        for (int i = 36; i < 45; i++) player2GUI.setItem(i, GUIUtils.createGrayPlaceHolder());
        player2GUI.setItem(49, GUIUtils.createGrayPlaceHolder());
        player2GUI.setItem(46, GUIUtils.createGrayPlaceHolder());
        player2GUI.setItem(52, GUIUtils.createGrayPlaceHolder());


        //Accept trade buttons
        player2GUI.setItem(45, player2GUI.createButton(Material.LIME_STAINED_GLASS_PANE, "&a&lAccept Trade", List.of(
                "Once both players accept the trade",
                "a " + CONT_DOWN + " second countdown will begin. Both",
                "players will have the option to cancel",
                "the trade during this time. After the",
                "count down is over, the trade will finish",
                "", "&b&oClick to accept this trade"
        )));
        player2GUI.setItem(53, player2GUI.createButton(Material.LIME_STAINED_GLASS_PANE, "&a&lHas " + player1.getName() + " Accepted", List.of(
                "Once both players accept the trade",
                "a " + CONT_DOWN + " second countdown will begin. Both",
                "players will have the option to cancel",
                "the trade during this time. After the",
                "count down is over, the trade will finish"
        )));

        //Your tokens & money
        player2GUI.setItem(47, player2GUI.createButton(Material.SUNFLOWER, "&c&lOffer Tokens", List.of(
                "&cCurrently Offering: &f" + PrisonUtils.prettyNum(player2TokenOffer) + " Tokens",
                "&cYour Tokens: &f$" + PrisonUtils.prettyNum(new PlayerData(player2).getTokens()),
                "",
                "&oClick to edit this offer"
        ), (p, t) -> {
            //todo
        }));
        player2GUI.setItem(48, player2GUI.createButton(Material.PAPER, "&a&lOffer Money", List.of(
                "&aCurrently Offering: &f$" + PrisonUtils.prettyNum(player2MoneyOffer),
                "&aYour Balance: &f$" + PrisonUtils.prettyNum(new PlayerData(player2).getMoney()),
                "",
                "&oClick to edit this offer"
        ), (p, t) -> {
            //todo
        }));

        //Their tokens & money
        player2GUI.setItem(50, player2GUI.createButton(Material.PAPER, "&a" + player1.getName() + "'s Money Offer", List.of(
                "&aOffering: &f$" + PrisonUtils.prettyNum(player1MoneyOffer),
                "&aTheir Balance: &f$" + PrisonUtils.prettyNum(new PlayerData(player1).getMoney())
        ), (p, t) -> {
            player2.sendMessage(ChatColor.translateAlternateColorCodes('&', PREFIX + player1.getName() + " is offering &a$" + PrisonUtils.addCommasToNumber(player1MoneyOffer)));
        }));
        player2GUI.setItem(51, player2GUI.createButton(Material.SUNFLOWER, "&c" + player1.getName() + "'s Tokens Offer", List.of(
                "&cOffering: &f" + PrisonUtils.prettyNum(player1TokenOffer) + " Tokens",
                "&cTheir Tokens: &f" + PrisonUtils.prettyNum(new PlayerData(player1).getTokens())
        ), (p, t) -> {
            player2.sendMessage(ChatColor.translateAlternateColorCodes('&', PREFIX + player1.getName() + " is offering &c" + PrisonUtils.addCommasToNumber(player1TokenOffer) + " Tokens"));
        }));

        //Display the actual items that are being offered by player2
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                ItemStack item = player2ItemOffer[x + y * 4];
                if (item == null || item.getType().equals(Material.AIR)) continue;
                player2GUI.setItem(x + y * 9, player2GUI.createButton(item.clone(), (e -> {
                    e.setCancelled(true);
                    player2ItemOffer[((e.getSlot() + 1) % 9 + (e.getSlot() + 1) / 9 * 4) - 1] = e.getCursor();
                    e.setCursor(item);
                    updateMenuContents();
                })));
            }
        }

        //Display the actual items that are being offered by player1
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                ItemStack item = player1ItemOffer[x + y * 4];
                if (item == null || item.getType().equals(Material.AIR)) continue;
                player2GUI.setItem(x + 5 + y * 9, item.clone());
            }
        }
    }



}
