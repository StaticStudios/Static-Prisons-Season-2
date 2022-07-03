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

public class Gang {//todo: this

    public static final String PREFIX = ChatColor.translateAlternateColorCodes('&', "&6&lGangs &8&l>> &r");

    public static int MAX_GANG_SIZE = 5;

    static final Map<UUID, Gang> GANGS = new HashMap<>();
    static final Map<UUID, Gang> PLAYER_GANGS = new HashMap<>();

    private UUID uuid;

    private UUID owner;
    private String name;
    private List<UUID> members;
    public UUID getOwner() {
        return owner;
    }
    public String getName() {
        return name;
    }
    public List<UUID> getMembers() {
        return members;
    }

    //Settings
    private boolean isPublic = false;
    private boolean acceptingInvites = true;
    private boolean friendlyFire = false;
    private boolean canMembersWithdrawFomBank = true;
    public boolean isPublic() {
        return isPublic;
    }
    public boolean isAcceptingInvites() {
        return acceptingInvites;
    }
    public boolean isFriendlyFire() {
        return friendlyFire;
    }
    public boolean canMembersWithdrawFomBank() {
        return canMembersWithdrawFomBank;
    }
    public void setPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }
    public void setAcceptingInvites(boolean acceptingInvites) {
        this.acceptingInvites = acceptingInvites;
    }
    public void setFriendlyFire(boolean friendlyFire) {
        this.friendlyFire = friendlyFire;
    }
    public void setCanMembersWithdrawFomBank(boolean canMembersWithdrawFomBank) {
        this.canMembersWithdrawFomBank = canMembersWithdrawFomBank;
    }

    //Stats
    private long rawBlocksMined = 0;
    private long blocksMined = 0;
    private long secondsPlayed = 0;
    private BigInteger moneyMade = BigInteger.ZERO;
    private BigInteger tokensFound = BigInteger.ZERO;
    public long getRawBlocksMined() {
        return rawBlocksMined;
    }
    public long getBlocksMined() {
        return blocksMined;
    }
    public long getSecondsPlayed() {
        return secondsPlayed;
    }
    public BigInteger getMoneyMade() {
        return moneyMade;
    }
    public BigInteger getTokensFound() {
        return tokensFound;
    }

    //Bank
    private BigInteger bankMoney = BigInteger.ZERO;
    private BigInteger bankTokens = BigInteger.ZERO;

    //TODO: gang chest

    private Gang() {}

    public static Gang getGang(UUID gang) {
        return GANGS.get(gang);
    }
    public static Gang getGang(Player player) {
        return PLAYER_GANGS.get(player.getUniqueId());
    }
    public static boolean hasGang(Player player) {
        return PLAYER_GANGS.containsKey(player.getUniqueId());
    }

    public static Gang createGang(UUID owner, String name) {
        Gang gang = new Gang();
        gang.uuid = UUID.randomUUID();
        gang.owner = owner;
        gang.name = name;
        gang.members = new ArrayList<>();
        gang.members.add(owner);
        GANGS.put(gang.uuid, gang);
        PLAYER_GANGS.put(owner, gang);
        return gang;
    }
    public static Gang loadGang(UUID uuid,
                                UUID owner, String name, List<UUID> members,
                                boolean isPublic, boolean acceptingInvites, boolean friendlyFire, boolean canMembersWithdrawFomBank,
                                long rawBlocksMined, long blocksMined, long secondsPlayed, BigInteger moneyMade, BigInteger tokensFound,
                                BigInteger bankMoney, BigInteger bankTokens) {
        Gang gang = new Gang();
        gang.uuid = uuid;
        gang.owner = owner;
        gang.name = name;
        gang.members = members;
        gang.isPublic = isPublic;
        gang.acceptingInvites = acceptingInvites;
        gang.friendlyFire = friendlyFire;
        gang.canMembersWithdrawFomBank = canMembersWithdrawFomBank;
        gang.rawBlocksMined = rawBlocksMined;
        gang.blocksMined = blocksMined;
        gang.secondsPlayed = secondsPlayed;
        gang.moneyMade = moneyMade;
        gang.tokensFound = tokensFound;
        gang.bankMoney = bankMoney;
        gang.bankTokens = bankTokens;
        GANGS.put(gang.uuid, gang);
        for (UUID member : members) PLAYER_GANGS.put(member, gang);
        return gang;
    }
    public static ConfigurationSection saveGang(Gang gang) {
        ConfigurationSection section = new YamlConfiguration();
        section.set("uuid", gang.uuid.toString());
        section.set("owner", gang.owner.toString());
        section.set("name", gang.name);
        section.set("members", gang.members);
        section.set("isPublic", gang.isPublic);
        section.set("acceptingInvites", gang.acceptingInvites);
        section.set("friendlyFire", gang.friendlyFire);
        section.set("canMembersWithdrawFomBank", gang.canMembersWithdrawFomBank);
        section.set("rawBlocksMined", gang.rawBlocksMined);
        section.set("blocksMined", gang.blocksMined);
        section.set("secondsPlayed", gang.secondsPlayed);
        section.set("moneyMade", gang.moneyMade.toString());
        section.set("tokensFound", gang.tokensFound.toString());
        section.set("bankMoney", gang.bankMoney.toString());
        section.set("bankTokens", gang.bankTokens.toString());
        return section;
    }
    public static void saveAllSync() {
        try {
            FileConfiguration fileData = new YamlConfiguration();
            for (Gang gang : GANGS.values()) fileData.set(gang.uuid.toString(), saveGang(gang));
            fileData.save(new File(StaticPrisons.getInstance().getDataFolder(), "gangs.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void saveAll() { //todo save on timer and load and save on start/close
        Bukkit.getScheduler().runTaskAsynchronously(StaticPrisons.getInstance(), Gang::saveAllSync);
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
            boolean canMembersWithdrawFomBank = section.getBoolean("canMembersWithdrawFomBank");
            long rawBlocksMined = section.getLong("rawBlocksMined");
            long blocksMined = section.getLong("blocksMined");
            long secondsPlayed = section.getLong("secondsPlayed");
            section.addDefault("moneyMade", "0");
            section.addDefault("tokensFound", "0");
            section.addDefault("bankMoney", "0");
            section.addDefault("bankTokens", "0");
            BigInteger moneyMade = new BigInteger(section.getString("moneyMade"));
            BigInteger tokensFound = new BigInteger(section.getString("tokensFound"));
            BigInteger bankMoney = new BigInteger(section.getString("bankMoney"));
            BigInteger bankTokens = new BigInteger(section.getString("bankTokens"));
            loadGang(uuid, owner, name, members, isPublic, acceptingInvites, friendlyFire, canMembersWithdrawFomBank, rawBlocksMined, blocksMined, secondsPlayed, moneyMade, tokensFound, bankMoney, bankTokens);
        }
    }

    public boolean addMember(UUID member) {
        if (members.size() >= MAX_GANG_SIZE) return false;
        if (members.contains(member)) return false;
        messageAllMembers(Gang.PREFIX + ChatColor.translateAlternateColorCodes('&', "&a" + ServerData.PLAYERS.getName(member) + "&f joined your gang!"));
        if (Bukkit.getPlayer(member) != null) Bukkit.getPlayer(member).sendMessage(Gang.PREFIX + ChatColor.translateAlternateColorCodes('&', "You joined &b" + name + "!"));
        members.add(member);
        PLAYER_GANGS.put(member, this);
        return true;
    }
    public void removeMember(UUID member) {
        members.remove(member);
        PLAYER_GANGS.remove(member);
    }
    public boolean isFull() {
        return members.size() >= MAX_GANG_SIZE;
    }


    public void messageAllMembers(String message) {
        for (UUID member : members) if (Bukkit.getPlayer(member) != null) Bukkit.getPlayer(member).sendMessage(message);
    }
}
