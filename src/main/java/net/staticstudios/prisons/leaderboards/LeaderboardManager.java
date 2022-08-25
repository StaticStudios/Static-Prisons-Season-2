package net.staticstudios.prisons.leaderboards;

import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.trait.CommandTrait;
import net.citizensnpcs.trait.HologramTrait;
import net.citizensnpcs.trait.SkinTrait;
import net.staticstudios.citizens.CitizensUtils;
import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.commands.CommandManager;
import net.staticstudios.prisons.leaderboards.commands.ExemptFromLeaderboardsCommand;
import net.staticstudios.prisons.leaderboards.commands.LeaderboardsCommand;
import net.staticstudios.prisons.leaderboards.commands.UpdateLeaderboardsCommand;

public class LeaderboardManager { //todo switch to using api

    public static void init() {
        CommandManager.registerCommand("updateleaderboards", new UpdateLeaderboardsCommand());
        CommandManager.registerCommand("exemptfromleaderboards", new ExemptFromLeaderboardsCommand());
        CommandManager.registerCommand("leaderboards", new LeaderboardsCommand());

//        Bukkit.getScheduler().runTaskTimer(StaticPrisons.getInstance(), LeaderboardManager::updateAll, 20, 20 * 60 * 30);

    }

    public static void updateAll() {
        try {
            BlocksMinedTop.calculateLeaderBoard();
            RawBlocksMinedTop.calculateLeaderBoard();
            BalanceTop.calculateLeaderBoard();
            TokensTop.calculateLeaderBoard();
            TimePlayedTop.calculateLeaderBoard();
            PrestigeTop.calculateLeaderBoard();
            VotesTop.calculateLeaderBoard();
        } catch (IndexOutOfBoundsException ignore) {
        }
    }

    public static void updateLeaderboardNPC(String npcName, String topPlacement, String playerName, String amount) {
        CitizensUtils.getNPC(npcName).ifPresentOrElse(npc -> {
                    npc.getOrAddTrait(SkinTrait.class).setSkinName(playerName, true);
                    HologramTrait top1hologramTrait = npc.getOrAddTrait(HologramTrait.class);

                    for (int i = top1hologramTrait.getLines().size() - 1; i >= 0; i--) {
                        top1hologramTrait.removeLine(i);
                    }

                    top1hologramTrait.addLine("&7" + topPlacement);
                    top1hologramTrait.addLine("&a&l" + playerName + " (" + amount + ")");
                    npc.data().setPersistent(NPC.Metadata.NAMEPLATE_VISIBLE, false);

                    CommandTrait commandTrait = npc.getOrAddTrait(CommandTrait.class);

                    boolean commandExists = true;
                    int i = 0;
                    while (commandExists) {
                        if (!commandTrait.hasCommandId(i)) {
                            commandExists = false;
                            continue;
                        }

                        commandTrait.removeCommandById(i++);
                    }

                    commandTrait.addCommand(new CommandTrait.NPCCommandBuilder("stats " + playerName, CommandTrait.Hand.BOTH).player(true).op(false));
                },
                () -> StaticPrisons.getInstance().getLogger().warning("NPC " + npcName + " not found")
        );
    }
}
