package me.staticstudios.prisons.discord;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class DiscordLinkHandler {

    private static Map<String, Invite> invites = new HashMap<>();
    private static Map<String, String> codeToUUID = new HashMap<>();

    public static boolean checkIfCodeExists(String code) {
        return codeToUUID.containsKey(code);
    }

    public static String getUUIDFromCode(String code) {
        return codeToUUID.get(code);
    }

    public static String addInvite(String uuid) {
        String code = "";
        char[] nums = Long.toString(Instant.now().toEpochMilli()).toCharArray();
        code += nums[nums.length - 6];
        code += nums[nums.length - 5];
        code += nums[nums.length - 4];
        code += "-";
        code += nums[nums.length - 3];
        code += nums[nums.length - 2];
        code += nums[nums.length - 1];
        codeToUUID.put(code, uuid);
        invites.put(uuid, new Invite(code));
        return code;
    }

    public static void removeInvite(String code) {
        invites.remove(getUUIDFromCode(code));
        codeToUUID.remove(code);
    }
    public static void updateAllExpiredInvites() {
        for (String key : invites.keySet()) {
            checkIfInviteIsExpiredFromUUID(key);
        }
    }
    public static boolean checkIfInviteIsExpiredFromUUID(String uuid) {
        if (invites.get(uuid).timeCreated + 1000 * 300 > Instant.now().toEpochMilli()) {
            invites.remove(uuid);
            return true;
        }
        return false;
    }
}

class Invite {

    public String code;
    public long timeCreated;
    public Invite(String code) {
        this.code = code;
        timeCreated = Instant.now().getEpochSecond();
    }

}
