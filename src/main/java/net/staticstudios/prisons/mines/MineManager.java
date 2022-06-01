package net.staticstudios.prisons.mines;

import net.staticstudios.mines.StaticMine;
import net.staticstudios.mines.StaticMines;
import net.staticstudios.mines.minesapi.events.MineCreatedEvent;
import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.misc.EventListener;
import net.staticstudios.prisons.misc.Warps;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class MineManager implements Listener {
    static final String MINE_REFILL_MESSAGE = ChatColor.LIGHT_PURPLE + "This mine has been refilled";
    public static void init() {
        StaticPrisons.getInstance().getServer().getPluginManager().registerEvents(new MineManager(), StaticPrisons.getInstance());
        StaticMines.enable(StaticPrisons.getInstance());
    }
    @EventHandler
    void onMineCreate(MineCreatedEvent e) {
        switch (e.getMine().getID()) {
            default -> {
                //Players should be teleported to the center of the mine
                Location midPoint = new Location(e.getMine().getWorld(),
                        (e.getMine().getMaxPoint().getBlockX() - e.getMine().getMinPoint().getBlockX()) / 2d + e.getMine().getMinPoint().getBlockX(),
                        e.getMine().getMaxPoint().getBlockY() + 1,
                        (e.getMine().getMaxPoint().getBlockZ() - e.getMine().getMinPoint().getBlockZ()) / 2d + e.getMine().getMinPoint().getBlockZ()
                );
                e.getMine().setRunOnRefill(mine -> {
                    for (Player player : mine.getPlayersInMine()) {
                        player.sendMessage(MINE_REFILL_MESSAGE);
                        Warps.warpSomewhere(player, midPoint, true);
                    }
                });
            }
            case "publicMine-A" -> e.getMine().setRunOnRefill((mine -> {
                for (Player player : mine.getPlayersInMine()) {
                    player.sendMessage(MINE_REFILL_MESSAGE);
                    Warps.warpToMine(player, 0);
                }
            }));
            case "publicMine-B" -> e.getMine().setRunOnRefill((mine -> {
                for (Player player : mine.getPlayersInMine()) {
                    player.sendMessage(MINE_REFILL_MESSAGE);
                    Warps.warpToMine(player, 1);
                }
            }));
            case "publicMine-C" -> e.getMine().setRunOnRefill((mine -> {
                for (Player player : mine.getPlayersInMine()) {
                    player.sendMessage(MINE_REFILL_MESSAGE);
                    Warps.warpToMine(player, 2);
                }
            }));
            case "publicMine-D" -> e.getMine().setRunOnRefill((mine -> {
                for (Player player : mine.getPlayersInMine()) {
                    player.sendMessage(MINE_REFILL_MESSAGE);
                    Warps.warpToMine(player, 3);
                }
            }));
            case "publicMine-E" -> e.getMine().setRunOnRefill((mine -> {
                for (Player player : mine.getPlayersInMine()) {
                    player.sendMessage(MINE_REFILL_MESSAGE);
                    Warps.warpToMine(player, 4);
                }
            }));
            case "publicMine-F" -> e.getMine().setRunOnRefill((mine -> {
                for (Player player : mine.getPlayersInMine()) {
                    player.sendMessage(MINE_REFILL_MESSAGE);
                    Warps.warpToMine(player, 5);
                }
            }));
            case "publicMine-G" -> e.getMine().setRunOnRefill((mine -> {
                for (Player player : mine.getPlayersInMine()) {
                    player.sendMessage(MINE_REFILL_MESSAGE);
                    Warps.warpToMine(player, 6);
                }
            }));
            case "publicMine-H" -> e.getMine().setRunOnRefill((mine -> {
                for (Player player : mine.getPlayersInMine()) {
                    player.sendMessage(MINE_REFILL_MESSAGE);
                    Warps.warpToMine(player, 7);
                }
            }));
            case "publicMine-I" -> e.getMine().setRunOnRefill((mine -> {
                for (Player player : mine.getPlayersInMine()) {
                    player.sendMessage(MINE_REFILL_MESSAGE);
                    Warps.warpToMine(player, 8);
                }
            }));
            case "publicMine-J" -> e.getMine().setRunOnRefill((mine -> {
                for (Player player : mine.getPlayersInMine()) {
                    player.sendMessage(MINE_REFILL_MESSAGE);
                    Warps.warpToMine(player, 9);
                }
            }));
            case "publicMine-K" -> e.getMine().setRunOnRefill((mine -> {
                for (Player player : mine.getPlayersInMine()) {
                    player.sendMessage(MINE_REFILL_MESSAGE);
                    Warps.warpToMine(player, 10);
                }
            }));
            case "publicMine-L" -> e.getMine().setRunOnRefill((mine -> {
                for (Player player : mine.getPlayersInMine()) {
                    player.sendMessage(MINE_REFILL_MESSAGE);
                    Warps.warpToMine(player, 11);
                }
            }));
            case "publicMine-M" -> e.getMine().setRunOnRefill((mine -> {
                for (Player player : mine.getPlayersInMine()) {
                    player.sendMessage(MINE_REFILL_MESSAGE);
                    Warps.warpToMine(player, 12);
                }
            }));
            case "publicMine-N" -> e.getMine().setRunOnRefill((mine -> {
                for (Player player : mine.getPlayersInMine()) {
                    player.sendMessage(MINE_REFILL_MESSAGE);
                    Warps.warpToMine(player, 13);
                }
            }));
            case "publicMine-O" -> e.getMine().setRunOnRefill((mine -> {
                for (Player player : mine.getPlayersInMine()) {
                    player.sendMessage(MINE_REFILL_MESSAGE);
                    Warps.warpToMine(player, 14);
                }
            }));
            case "publicMine-P" -> e.getMine().setRunOnRefill((mine -> {
                for (Player player : mine.getPlayersInMine()) {
                    player.sendMessage(MINE_REFILL_MESSAGE);
                    Warps.warpToMine(player, 15);
                }
            }));
            case "publicMine-Q" -> e.getMine().setRunOnRefill((mine -> {
                for (Player player : mine.getPlayersInMine()) {
                    player.sendMessage(MINE_REFILL_MESSAGE);
                    Warps.warpToMine(player, 16);
                }
            }));
            case "publicMine-R" -> e.getMine().setRunOnRefill((mine -> {
                for (Player player : mine.getPlayersInMine()) {
                    player.sendMessage(MINE_REFILL_MESSAGE);
                    Warps.warpToMine(player, 17);
                }
            }));
            case "publicMine-S" -> e.getMine().setRunOnRefill((mine -> {
                for (Player player : mine.getPlayersInMine()) {
                    player.sendMessage(MINE_REFILL_MESSAGE);
                    Warps.warpToMine(player, 18);
                }
            }));
            case "publicMine-T" -> e.getMine().setRunOnRefill((mine -> {
                for (Player player : mine.getPlayersInMine()) {
                    player.sendMessage(MINE_REFILL_MESSAGE);
                    Warps.warpToMine(player, 19);
                }
            }));
            case "publicMine-U" -> e.getMine().setRunOnRefill((mine -> {
                for (Player player : mine.getPlayersInMine()) {
                    player.sendMessage(MINE_REFILL_MESSAGE);
                    Warps.warpToMine(player, 20);
                }
            }));
            case "publicMine-V" -> e.getMine().setRunOnRefill((mine -> {
                for (Player player : mine.getPlayersInMine()) {
                    player.sendMessage(MINE_REFILL_MESSAGE);
                    Warps.warpToMine(player, 21);
                }
            }));
            case "publicMine-W" -> e.getMine().setRunOnRefill((mine -> {
                for (Player player : mine.getPlayersInMine()) {
                    player.sendMessage(MINE_REFILL_MESSAGE);
                    Warps.warpToMine(player, 22);
                }
            }));
            case "publicMine-X" -> e.getMine().setRunOnRefill((mine -> {
                for (Player player : mine.getPlayersInMine()) {
                    player.sendMessage(MINE_REFILL_MESSAGE);
                    Warps.warpToMine(player, 23);
                }
            }));
            case "publicMine-Y" -> e.getMine().setRunOnRefill((mine -> {
                for (Player player : mine.getPlayersInMine()) {
                    player.sendMessage(MINE_REFILL_MESSAGE);
                    Warps.warpToMine(player, 24);
                }
            }));
            case "publicMine-Z" -> e.getMine().setRunOnRefill((mine -> {
                for (Player player : mine.getPlayersInMine()) {
                    player.sendMessage(MINE_REFILL_MESSAGE);
                    Warps.warpToMine(player, 25);
                }
            }));

            case "prestigeMine-1" -> e.getMine().setRunOnRefill((mine -> {
                for (Player player : mine.getPlayersInMine()) {
                    player.sendMessage(MINE_REFILL_MESSAGE);
                    Warps.warpToPrestigeMine(player, 0);
                }
            }));
            case "prestigeMine-2" -> e.getMine().setRunOnRefill((mine -> {
                for (Player player : mine.getPlayersInMine()) {
                    player.sendMessage(MINE_REFILL_MESSAGE);
                    Warps.warpToPrestigeMine(player, 1);
                }
            }));
            case "prestigeMine-3" -> e.getMine().setRunOnRefill((mine -> {
                for (Player player : mine.getPlayersInMine()) {
                    player.sendMessage(MINE_REFILL_MESSAGE);
                    Warps.warpToPrestigeMine(player, 2);
                }
            }));
            case "prestigeMine-4" -> e.getMine().setRunOnRefill((mine -> {
                for (Player player : mine.getPlayersInMine()) {
                    player.sendMessage(MINE_REFILL_MESSAGE);
                    Warps.warpToPrestigeMine(player, 3);
                }
            }));
            case "prestigeMine-5" -> e.getMine().setRunOnRefill((mine -> {
                for (Player player : mine.getPlayersInMine()) {
                    player.sendMessage(MINE_REFILL_MESSAGE);
                    Warps.warpToPrestigeMine(player, 4);
                }
            }));
            case "prestigeMine-6" -> e.getMine().setRunOnRefill((mine -> {
                for (Player player : mine.getPlayersInMine()) {
                    player.sendMessage(MINE_REFILL_MESSAGE);
                    Warps.warpToPrestigeMine(player, 5);
                }
            }));
            case "prestigeMine-7" -> e.getMine().setRunOnRefill((mine -> {
                for (Player player : mine.getPlayersInMine()) {
                    player.sendMessage(MINE_REFILL_MESSAGE);
                    Warps.warpToPrestigeMine(player, 6);
                }
            }));
            case "prestigeMine-8" -> e.getMine().setRunOnRefill((mine -> {
                for (Player player : mine.getPlayersInMine()) {
                    player.sendMessage(MINE_REFILL_MESSAGE);
                    Warps.warpToPrestigeMine(player, 7);
                }
            }));
            case "prestigeMine-9" -> e.getMine().setRunOnRefill((mine -> {
                for (Player player : mine.getPlayersInMine()) {
                    player.sendMessage(MINE_REFILL_MESSAGE);
                    Warps.warpToPrestigeMine(player, 8);
                }
            }));
            case "prestigeMine-10" -> e.getMine().setRunOnRefill((mine -> {
                for (Player player : mine.getPlayersInMine()) {
                    player.sendMessage(MINE_REFILL_MESSAGE);
                    Warps.warpToPrestigeMine(player, 9);
                }
            }));
            case "prestigeMine-11" -> e.getMine().setRunOnRefill((mine -> {
                for (Player player : mine.getPlayersInMine()) {
                    player.sendMessage(MINE_REFILL_MESSAGE);
                    Warps.warpToPrestigeMine(player, 10);
                }
            }));
            case "prestigeMine-12" -> e.getMine().setRunOnRefill((mine -> {
                for (Player player : mine.getPlayersInMine()) {
                    player.sendMessage(MINE_REFILL_MESSAGE);
                    Warps.warpToPrestigeMine(player, 11);
                }
            }));
            case "prestigeMine-13" -> e.getMine().setRunOnRefill((mine -> {
                for (Player player : mine.getPlayersInMine()) {
                    player.sendMessage(MINE_REFILL_MESSAGE);
                    Warps.warpToPrestigeMine(player, 12);
                }
            }));
            case "prestigeMine-14" -> e.getMine().setRunOnRefill((mine -> {
                for (Player player : mine.getPlayersInMine()) {
                    player.sendMessage(MINE_REFILL_MESSAGE);
                    Warps.warpToPrestigeMine(player, 13);
                }
            }));
            case "prestigeMine-15" -> e.getMine().setRunOnRefill((mine -> {
                for (Player player : mine.getPlayersInMine()) {
                    player.sendMessage(MINE_REFILL_MESSAGE);
                    Warps.warpToPrestigeMine(player, 14);
                }
            }));

            case "rankMine-1" -> e.getMine().setRunOnRefill((mine -> {
                for (Player player : mine.getPlayersInMine()) {
                    player.sendMessage(MINE_REFILL_MESSAGE);
                    Warps.warpToRankMine(player, 0);
                }
            }));
            case "rankMine-2" -> e.getMine().setRunOnRefill((mine -> {
                for (Player player : mine.getPlayersInMine()) {
                    player.sendMessage(MINE_REFILL_MESSAGE);
                    Warps.warpToRankMine(player, 1);
                }
            }));
            case "rankMine-3" -> e.getMine().setRunOnRefill((mine -> {
                for (Player player : mine.getPlayersInMine()) {
                    player.sendMessage(MINE_REFILL_MESSAGE);
                    Warps.warpToRankMine(player, 2);
                }
            }));
            case "rankMine-4" -> e.getMine().setRunOnRefill((mine -> {
                for (Player player : mine.getPlayersInMine()) {
                    player.sendMessage(MINE_REFILL_MESSAGE);
                    Warps.warpToRankMine(player, 3);
                }
            }));
            case "rankMine-5" -> e.getMine().setRunOnRefill((mine -> {
                for (Player player : mine.getPlayersInMine()) {
                    player.sendMessage(MINE_REFILL_MESSAGE);
                    Warps.warpToRankMine(player, 4);
                }
            }));
        }
    }
}
