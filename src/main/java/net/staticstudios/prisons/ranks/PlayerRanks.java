package net.staticstudios.prisons.ranks;

import net.staticstudios.prisons.StaticPrisons;

public class PlayerRanks {

    public static void init() {

        StaticPrisons.getInstance().getServer().getPluginManager().registerEvents(new PlayerRanksListener(), StaticPrisons.getInstance());
    }

}
