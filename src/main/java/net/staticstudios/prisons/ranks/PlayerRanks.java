package net.staticstudios.prisons.ranks;

import net.staticstudios.prisons.blockBroken.BlockBreak;

public class PlayerRanks {

    public static void init() {

        BlockBreak.addListener(blockBreak -> {
            switch (blockBreak.getPlayerData().getPlayerRank()) {
                case "mythic" -> blockBreak.getStats().setTokenMultiplier(blockBreak.getStats().getTokenMultiplier() + 0.05d);
                case "static" -> blockBreak.getStats().setTokenMultiplier(blockBreak.getStats().getTokenMultiplier() + 0.1d);
                case "staticp" -> blockBreak.getStats().setTokenMultiplier(blockBreak.getStats().getTokenMultiplier() + 0.2d);
            }
        });
    }

}
