package net.staticstudios.prisons.ranks;

import net.staticstudios.prisons.blockBroken.BlockBreak;

public class PlayerRanks {

    public static void init() {

        BlockBreak.addListener(blockBreak -> {
            switch (blockBreak.getPlayerData().getPlayerRank()) {
                case "mythic" -> blockBreak.getStats().setTokenMultiplier(blockBreak.getStats().getTokenMultiplier() + 0.05d); //+5%
                case "static" -> blockBreak.getStats().setTokenMultiplier(blockBreak.getStats().getTokenMultiplier() + 0.1d); //+10%
                case "staticp" -> blockBreak.getStats().setTokenMultiplier(blockBreak.getStats().getTokenMultiplier() + 0.15d); //+15%
            }
            if (blockBreak.getPlayerData().getIsNitroBoosting()) {
                blockBreak.getStats().setTokenMultiplier(blockBreak.getStats().getTokenMultiplier() + 0.05d); //+5%
            }
        });
    }

}
