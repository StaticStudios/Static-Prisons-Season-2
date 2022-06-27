package net.staticstudios.prisons.gangs;

import net.md_5.bungee.api.ChatColor;
import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.data.serverData.ServerData;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.*;

public class PrisonGang {//todo: this

    public static int MAX_GANG_SIZE = 5;

    static final Map<UUID, PrisonGang> GANGS = new HashMap<>();
    static final Map<UUID, PrisonGang> PLAYER_GANGS = new HashMap<>();

    private UUID uuid;

    private UUID owner;
    private String name;
    private List<UUID> members;

    //Settings
    private boolean isPublic = false;
    private boolean acceptingInvites = true;
    private boolean friendlyFire = false;

    //Stats
    private int rawBlocksMined = 0;
    private int blocksMined = 0;
    private long xp = 0;
    private int level = 0;

    //Bank
    private BigInteger bankMoney = BigInteger.ZERO;
    private BigInteger bankTokens = BigInteger.ZERO;

    //TODO: gang chest

    private PrisonGang() {}

    public static PrisonGang getGang(UUID gang) {
        return GANGS.get(gang);
    }
    public static PrisonGang getGang(Player player) {
        return PLAYER_GANGS.get(player.getUniqueId());
    }
    public static boolean hasGang(Player player) {
        return PLAYER_GANGS.containsKey(player.getUniqueId());
    }

    public static PrisonGang createGang(UUID owner, String name) {
        PrisonGang gang = new PrisonGang();
        gang.uuid = UUID.randomUUID();
        gang.owner = owner;
        gang.name = name;
        gang.members = new ArrayList<>();
        gang.members.add(owner);
        GANGS.put(gang.uuid, gang);
        PLAYER_GANGS.put(owner, gang);
        return gang;
    }
    public static PrisonGang loadGang(UUID uuid,
                                      UUID owner, String name, List<UUID> members,
                                      boolean isPublic, boolean acceptingInvites, boolean friendlyFire,
                                      int rawBlocksMined, int blocksMined, long xp, int level,
                                      BigInteger bankMoney, BigInteger bankTokens) {
        PrisonGang gang = new PrisonGang();
        gang.uuid = uuid;
        gang.owner = owner;
        gang.name = name;
        gang.members = members;
        gang.isPublic = isPublic;
        gang.acceptingInvites = acceptingInvites;
        gang.friendlyFire = friendlyFire;
        gang.rawBlocksMined = rawBlocksMined;
        gang.blocksMined = blocksMined;
        gang.xp = xp;
        gang.level = level;
        gang.bankMoney = bankMoney;
        gang.bankTokens = bankTokens;
        GANGS.put(gang.uuid, gang);
        for (UUID member : members) PLAYER_GANGS.put(member, gang);
        return gang;
    }
    public static ConfigurationSection saveGang(PrisonGang gang) {
        ConfigurationSection section = new YamlConfiguration();
        section.set("uuid", gang.uuid.toString());
        section.set("owner", gang.owner.toString());
        section.set("name", gang.name);
        section.set("members", gang.members);
        section.set("isPublic", gang.isPublic);
        section.set("acceptingInvites", gang.acceptingInvites);
        section.set("friendlyFire", gang.friendlyFire);
        section.set("rawBlocksMined", gang.rawBlocksMined);
        section.set("blocksMined", gang.blocksMined);
        section.set("xp", gang.xp);
        section.set("level", gang.level);
        section.set("bankMoney", gang.bankMoney.toString());
        section.set("bankTokens", gang.bankTokens.toString());
        return section;
    }
    public static void saveAllSync() {
        try {
            FileConfiguration fileData = new YamlConfiguration();
            for (PrisonGang gang : GANGS.values()) fileData.set(gang.uuid.toString(), saveGang(gang));
            fileData.save(new File(StaticPrisons.getInstance().getDataFolder(), "gangs.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void saveAll() { //todo save on timer and load and save on start/close
        Bukkit.getScheduler().runTaskAsynchronously(StaticPrisons.getInstance(), PrisonGang::saveAllSync);
    }
    public static void loadAll() {
        FileConfiguration fileData = YamlConfiguration.loadConfiguration(new File(StaticPrisons.getInstance().getDataFolder(), "gangs.yml"));
        for (String key : fileData.getKeys(false)) {
            ConfigurationSection section = fileData.getConfigurationSection(key);
            UUID uuid = UUID.fromString(section.getString("uuid"));
            UUID owner = UUID.fromString(section.getString("owner"));
            String name = section.getString("name");
            List<UUID> members = new ArrayList<>();
            for (String strUUID : section.getStringList("members")) members.add(UUID.fromString(strUUID));
            boolean isPublic = section.getBoolean("isPublic");
            boolean acceptingInvites = section.getBoolean("acceptingInvites");
            boolean friendlyFire = section.getBoolean("friendlyFire");
            int rawBlocksMined = section.getInt("rawBlocksMined");
            int blocksMined = section.getInt("blocksMined");
            long xp = section.getLong("xp");
            int level = section.getInt("level");
            BigInteger bankMoney = new BigInteger(section.getString("bankMoney"));
            BigInteger bankTokens = new BigInteger(section.getString("bankTokens"));
            loadGang(uuid, owner, name, members, isPublic, acceptingInvites, friendlyFire, rawBlocksMined, blocksMined, xp, level, bankMoney, bankTokens);
        }
    }

    public boolean addMember(UUID member) {
        if (members.size() >= MAX_GANG_SIZE) return false;
        messageAllMembers(ChatColor.translateAlternateColorCodes('&', "&a" + ServerData.PLAYERS.getName(member) + "&f joined your gang!"));
        if (Bukkit.getPlayer(member) != null) Bukkit.getPlayer(member).sendMessage(ChatColor.translateAlternateColorCodes('&', "You joined &b" + name + "!"));
        members.add(member);
        PLAYER_GANGS.put(member, this);
        return true;
    }
    public void removeMember(UUID member) {
        members.remove(member);
        PLAYER_GANGS.remove(member);
        messageAllMembers(ChatColor.translateAlternateColorCodes('&', "&c" + ServerData.PLAYERS.getName(member) + "&f left your gang!"));
    }


    static final Map<UUID, List<Invite>> PLAYER_INVITES = new HashMap<>();

    public static class Invite {
        private UUID receiver = null;
        private UUID gangUUID = null;
        private long expireAt;

        private Invite() {}

        public static void updatePlayerInvites(UUID player) {
            List<Invite> newInvites = new ArrayList<>();
            for (Invite invite : PLAYER_INVITES.getOrDefault(player, new ArrayList<>())) {
                if (invite.isExpired()) newInvites.add(invite);
            }

            if (newInvites.isEmpty()) PLAYER_INVITES.remove(player);
            else PLAYER_INVITES.put(player, newInvites);
        }


        public static Invite createInvite(Player sender, Player receiver) {
            Invite invite = new Invite();
            invite.receiver = receiver.getUniqueId();
            invite.gangUUID = getGang(sender).uuid;
            invite.expireAt = System.currentTimeMillis() + 1000 * 300; //5 minutes
            List<Invite> invites = PLAYER_INVITES.getOrDefault(receiver.getUniqueId(), new ArrayList<>());
            invites.add(invite);
            PLAYER_INVITES.put(receiver.getUniqueId(), invites);
            return invite;
        }

        public boolean isExpired() {
            return System.currentTimeMillis() > expireAt;
        }

        public void accept() {
            if (isExpired()) {
                if (Bukkit.getPlayer(receiver) != null) Bukkit.getPlayer(receiver).sendMessage(ChatColor.translateAlternateColorCodes('&', "&cThis invite is expired!"));
                return;
            }
            PrisonGang gang = PrisonGang.getGang(this.gangUUID);
            if (gang == null) return;
            if (gang.addMember(receiver)) return;
            if (Bukkit.getPlayer(receiver) != null) Bukkit.getPlayer(receiver).sendMessage(ChatColor.translateAlternateColorCodes('&', "&c" + gang.name + "&f is full!"));
        }
        public void decline() {
            expireAt = 0;
            updatePlayerInvites(receiver);
        }
    }




    public void messageAllMembers(String message) {
        for (UUID member : members) if (Bukkit.getPlayer(member) != null) Bukkit.getPlayer(member).sendMessage(message);
    }
}
