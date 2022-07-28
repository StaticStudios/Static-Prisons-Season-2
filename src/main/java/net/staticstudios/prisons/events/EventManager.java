package net.staticstudios.prisons.events;

import net.staticstudios.prisons.blockBroken.BlockBreak;

public class EventManager {

    public static void init() {
        BlockBreak.addListener(blockBreak -> {
            if (blockBreak.getMine().getID().equals("eventMine")) { //Apply the event mine 120% token multiplier
                blockBreak.getStats().setTokenMultiplier(blockBreak.getStats().getTokenMultiplier() + 0.2d);
            }
        });
    }

}
