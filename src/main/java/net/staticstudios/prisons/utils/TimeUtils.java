package net.staticstudios.prisons.utils;

import net.kyori.adventure.text.Component;

public class TimeUtils {

    public static Component formatTime(int time) {
        int hours = time / 3600;
        int minutes = (time % 3600) / 60;
        int seconds = time % 60;
        StringBuilder sb = new StringBuilder();

        if (hours > 0) {
            sb.append(hours).append(":");
        }

        if (minutes < 10) {
            sb.append("0");
        }

        sb.append(minutes).append(":");

        if (seconds < 10) {
            sb.append("0");
        }

        sb.append(seconds);

        return Component.text(sb.toString());
    }

}
