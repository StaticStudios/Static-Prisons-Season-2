package net.staticstudios.prisons.gambling;

import net.staticstudios.gui.GUICreator;
import net.staticstudios.gui.GUIUtils;
import net.staticstudios.gui.StaticGUI;
import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.utils.PrisonUtils;
import net.staticstudios.utils.WeightedElements;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.math.BigInteger;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class TokenFlip extends Flip {
    Player challenger;

    public static final String PREFIX = ChatColor.translateAlternateColorCodes('&', "&6&lToken Flip &8&l>> &r");

    public static final Material HEADS_ICON = Material.SUNFLOWER;
    public static final Material TAILS_ICON = Material.FEATHER;
    public static void createTokenFlip(Player player, BigInteger amount, boolean isHeads) {
        new TokenFlip(player, amount, isHeads);
    }
    public static boolean checkIfExists(String uuid) {
        return GambleHandler.tokenFlips.containsKey(uuid);
    }
    public static boolean checkIfThereAreTooManyActiveFlips() {
        return GambleHandler.tokenFlips.size() >= GambleHandler.MAX_FLIP_COUNT;
    }
    public static void removeAFlip(String uuid) {
        GambleHandler.tokenFlips.remove(uuid);
    }
    public void remove() {
        GambleHandler.tokenFlips.remove(uuid);
    }
    public void runBet(Player challenger) {
        if (owner == null) {
            remove();
            return;
        }
        this.challenger = challenger;
        remove();
        //Check if owner has enough money
        if (new PlayerData(owner).getTokens().compareTo(amount) < 0) {
            challenger.sendMessage(ChatColor.RED + "The owner of this bet does not have enough tokens to continue.");
            GamblingMenus.openMain(challenger);
            return;
        }
        Flip.WhoWins winner = new WeightedElements<WhoWins>()
                .add(WhoWins.OWNER, 49)
                .add(WhoWins.CHALLENGER, 49)
                .add(WhoWins.HOUSE, 2)
                .getRandom();

        new PlayerData(owner).removeTokens(amount);
        new PlayerData(challenger).removeTokens(amount);

        AtomicInteger animationsRun = new AtomicInteger(PrisonUtils.randomInt(1, 3));
        Bukkit.getScheduler().runTaskTimer(StaticPrisons.getInstance(), task -> {
            GUICreator c = new GUICreator(27, owner.getName() + " vs " + challenger.getName());
            try {
                c.setMenuID(uuid);
                switch (animationsRun.get() % 3) {
                    default -> c.setItem(13, c.createButton(Material.CLOCK, "&e&lHouse", List.of("House wins", "", "&c2% chance"))); //House
                    case 1 -> {
                        if (isHeads)
                            c.setItem(13, c.createButton(headsIcon, "&a&lHeads", List.of(owner.getName() + " wins", "", "&c49% chance")));
                        else
                            c.setItem(13, c.createButton(tailsIcon, "&9&lTails", List.of(owner.getName() + " wins", "", "&c49% chance")));
                    }
                    case 2 -> {
                        if (!isHeads)
                            c.setItem(13, c.createButton(headsIcon, "&a&lHeads", List.of(challenger.getName() + " wins", "", "&c49% chance")));
                        else
                            c.setItem(13, c.createButton(tailsIcon, "&9&lTails", List.of(challenger.getName() + " wins", "", "&c49% chance")));
                    }
                }

                if (animationsRun.get() > 20) {
                    if (winner == WhoWins.OWNER && animationsRun.get() % 3 == 1) {
                        task.cancel();
                        win(winner);
                    }
                    if (winner == WhoWins.CHALLENGER && animationsRun.get() % 3 == 2) {
                        task.cancel();
                        win(winner);
                    }
                    if (winner == WhoWins.HOUSE && animationsRun.get() % 3 == 0) {
                        task.cancel();
                        win(winner);
                    }
                }
                c.open(challenger);
                c.open(owner);
            } catch (Exception e) {
                e.printStackTrace();
                task.cancel();
                win(winner);
            }
            c.fill(GUIUtils.createGrayPlaceHolder());

            animationsRun.getAndIncrement();
        }, 0, 4);
    }

    void win(WhoWins winner) {
        String msg = null;
        try {
            switch (winner) {
                default -> { //house
                    msg = ChatColor.translateAlternateColorCodes('&', PREFIX + "&cThe house won a token flip against " + owner.getName() + " and " + challenger.getName() + " for " + PrisonUtils.prettyNum(amount) + " tokens! &7&o(2% chance)");
                }
                case OWNER -> { //owner
                    msg = ChatColor.translateAlternateColorCodes('&', PREFIX + "&b" + owner.getName() + " won a token flip against " + challenger.getName() + " for " + PrisonUtils.prettyNum(amount) + " tokens! &7&o(49% chance)");
                    new PlayerData(owner).addTokens(amount.multiply(BigInteger.TWO));
                }
                case CHALLENGER -> { //challenger
                    msg = ChatColor.translateAlternateColorCodes('&', PREFIX + "&b" + challenger.getName() + " won a token flip against " + owner.getName() + " for " + PrisonUtils.prettyNum(amount) + " tokens! &7&o(49% chance)");
                    new PlayerData(challenger).addTokens(amount.multiply(BigInteger.TWO));
                }
            }


            Bukkit.getScheduler().runTaskLater(StaticPrisons.getInstance(), () -> {
                if (owner.getOpenInventory().getTopInventory().getHolder() instanceof StaticGUI && ((StaticGUI) owner.getOpenInventory().getTopInventory().getHolder()).getMenuID().equals(uuid)) {
                    owner.closeInventory();
                }
                if (challenger.getOpenInventory().getTopInventory().getHolder() instanceof StaticGUI && ((StaticGUI) challenger.getOpenInventory().getTopInventory().getHolder()).getMenuID().equals(uuid)) {
                    challenger.closeInventory();
                }

            }, 40);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (msg == null) return;
        for (Player p : Bukkit.getOnlinePlayers()) p.sendMessage(msg);
    }


    private TokenFlip(Player player, BigInteger amount, boolean isHeads) {
        uuid = player.getUniqueId().toString();
        owner = player;
        this.amount = amount;
        this.isHeads = isHeads;
        expireAt = Instant.now().toEpochMilli() + 15 * 60 * 1000;

        headsIcon = HEADS_ICON;
        tailsIcon = TAILS_ICON;


        GambleHandler.tokenFlips.put(uuid, this);
    }
}
