package net.staticstudios.prisons.leaderboards;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.trait.CommandTrait;
import net.citizensnpcs.trait.HologramTrait;
import net.citizensnpcs.trait.SkinTrait;
import net.staticstudios.citizens.VisibleNameTrait;
import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.data.serverdata.ServerData;
import net.staticstudios.prisons.utils.PrisonUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BlocksMinedTop {
    static final String npc1Name = "leaderboardsBlocksMined1";
    static final String npc2Name = "leaderboardsBlocksMined2";
    static final String npc3Name = "leaderboardsBlocksMined3";
    public static List<UUID> top100UUIDs = new ArrayList<>();

    public static void calculateLeaderBoard() {
        List<UUID> topUUIDs = new ArrayList<>();
        List<Long> topValues = new ArrayList<>();
        for (UUID uuid : ServerData.PLAYERS.getAllUUIDs()) {
            boolean ranked = false;
            PlayerData playerData = new PlayerData(uuid);
            if (playerData.getIsExemptFromLeaderboards()) continue;
            for (int i = 0; i < topUUIDs.size(); i++) {
                if (topValues.get(i) < playerData.getBlocksMined()) {
                    ranked = true;
                    topValues.add(i, playerData.getBlocksMined());
                    topUUIDs.add(i, playerData.getUUID());
                    break;
                }
            }
            if (!ranked && topUUIDs.size() < 100) {
                topValues.add(playerData.getBlocksMined());
                topUUIDs.add(playerData.getUUID());
            }
        }
        top100UUIDs = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            if (topUUIDs.size() >= i + 1) top100UUIDs.add(topUUIDs.get(i));
        }
        updateNPC();
    }

    static void updateNPC() {
        NPC top1 = CitizensAPI.getNPCRegistry().getById(50);

        SkinTrait top1skinTrait = top1.getOrAddTrait(SkinTrait.class);
        top1skinTrait.setSkinName(ServerData.PLAYERS.getName(top100UUIDs.get(0)), true);
        HologramTrait top1hologramTrait = top1.getOrAddTrait(HologramTrait.class);
        top1hologramTrait.setLine(0, "&a&l" + ServerData.PLAYERS.getName(top100UUIDs.get(0)) + " (" + PrisonUtils.prettyNum(new PlayerData(top100UUIDs.get(0)).getBlocksMined()) + ")");
        top1hologramTrait.setLine(1, "&7#1 TEST");
        top1hologramTrait.setDirection(HologramTrait.HologramDirection.TOP_DOWN);

        CommandTrait top1commandTrait = top1.getOrAddTrait(CommandTrait.class);
        top1commandTrait.removeCommandById(0);
        top1commandTrait.addCommand(new CommandTrait.NPCCommandBuilder("stats " + ServerData.PLAYERS.getName(top100UUIDs.get(0)), CommandTrait.Hand.BOTH));

        VisibleNameTrait top1VisibleNameTrait = top1.getOrAddTrait(VisibleNameTrait.class);
        top1VisibleNameTrait.setVisibleName(false);

//        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc select " + npc1Name);
//        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc skin " + ServerData.PLAYERS.getName(top100UUIDs.get(0)));
//        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc hologram remove 1");
//        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc hologram add &a&l" + ServerData.PLAYERS.getName(top100UUIDs.get(0)) + " (" + PrisonUtils.prettyNum(new PlayerData(top100UUIDs.get(0)).getBlocksMined()) + ")");
//        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc command remove 1");
//        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc command add stats " + ServerData.PLAYERS.getName(top100UUIDs.get(0)) + " -r -l -p");
//
//        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc select " + npc2Name);
//        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc skin " + ServerData.PLAYERS.getName(top100UUIDs.get(1)));
//        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc hologram remove 1");
//        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc hologram add &a&l" + ServerData.PLAYERS.getName(top100UUIDs.get(1)) + " (" + PrisonUtils.prettyNum(new PlayerData(top100UUIDs.get(1)).getBlocksMined()) + ")");
//        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc command remove 1");
//        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc command add stats " + ServerData.PLAYERS.getName(top100UUIDs.get(1)) + " -r -l -p");
//
//        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc select " + npc3Name);
//        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc skin " + ServerData.PLAYERS.getName(top100UUIDs.get(2)));
//        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc hologram remove 1");
//        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc hologram add &a&l" + ServerData.PLAYERS.getName(top100UUIDs.get(2)) + " (" + PrisonUtils.prettyNum(new PlayerData(top100UUIDs.get(2)).getBlocksMined()) + ")");
//        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc command remove 1");
//        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc command add stats " + ServerData.PLAYERS.getName(top100UUIDs.get(2)) + " -r -l -p");
    }
}
