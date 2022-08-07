package net.staticstudios.mines;

import java.util.*;

public class StaticMineUtils {
    public static List<String> filterStringList(Collection<String> collection, String currentArg) {
        List<String> sortedList = new LinkedList<>();
        for (String str : collection) {
            if (str.toLowerCase().startsWith(currentArg.toLowerCase())) {
                sortedList.add(str);
            }
        }
        return sortedList;
    }
}
