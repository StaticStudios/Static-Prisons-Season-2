package net.staticstudios.prisons.pvp.outposts;

import java.util.Arrays;
import java.util.List;

public enum OutpostTypes {
    TOKEN,
    MONEY;


    public static boolean isValidType(String type) {
        return toStringList().contains(type);
    }

    public static List<String> toStringList() {
        return Arrays.stream(OutpostTypes.values()).map(outpostTypes -> outpostTypes.toString().toLowerCase()).toList();
    }
}
