package net.staticstudios.prisons.commands;

import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.cells.IslandCommand;
import net.staticstudios.prisons.commands.normal.*;
import net.staticstudios.prisons.commands.test.Test2Command;
import net.staticstudios.prisons.commands.test.TestCommand;
import net.staticstudios.prisons.commands.votestore.VoteStoreListener;
import net.staticstudios.prisons.customitems.currency.commands.WithdrawCommand;
import net.staticstudios.prisons.gangs.GangCommand;
import net.staticstudios.prisons.privatemines.commands.PrivateMineCommand;
import net.staticstudios.prisons.pvp.commands.PvPCommand;
import net.staticstudios.prisons.reclaim.ReclaimCommand;
import org.bukkit.command.CommandExecutor;

import java.util.Objects;

public class CommandManager {
    public static void loadCommands() {
        //Developer commands - start
        registerCommand("test", new TestCommand());
        registerCommand("test2", new Test2Command());
        //Developer commands - end

        //Normal commands - start
        registerCommand("rules", new RulesCommand());
        registerCommand("multiplier", new MultiplierCommand());
        registerCommand("trash", new TrashCommand());


        registerCommand("shards", new ShardsCommand());
        registerCommand("tokens", new TokensCommand());
        registerCommand("balance", new BalanceCommand());
        registerCommand("island", new IslandCommand());
        registerCommand("dailyrewards", new DailyRewardsCommand());

        registerCommand("reclaim", new ReclaimCommand());
        registerCommand("dropitem", new DropItemCommand());
        registerCommand("pay", new PayCommand());
        registerCommand("withdraw", new WithdrawCommand());

        registerCommand("votes", new VotesCommand());
        registerCommand("crates", new CratesCommand());
        registerCommand("fly", new FlyCommand());
        registerCommand("store", new StoreCommand());
        registerCommand("mines", new MinesCommand());
        registerCommand("warps", new WarpsCommand());
        registerCommand("spawn", new SpawnCommand());
        registerCommand("coinflip", new CoinFlipCommand());
        registerCommand("tokenflip", new TokenFlipCommand());
//        registerCommand("discord", new DiscordCommand());
        registerCommand("stats", new StatsCommand());
        registerCommand("color", new ColorCommand());
        registerCommand("mobilesupport", new MobileSupportCommand());
        registerCommand("privatemine", new PrivateMineCommand());


        registerCommand("enderchest", new EnderChestCommand());

        registerCommand("settings", new SettingsCommand());


        registerCommand("gui", new GUICommand());
        registerCommand("npcdiag", new NPCDialogCommand());

        registerCommand("gang", new GangCommand());
        registerCommand("pvp", new PvPCommand());
        registerCommand("rewards", new RewardsCommand());
        registerCommand("resetrank", new ResetRankCommand());

        registerCommand("_", new VoteStoreListener());

        //Normal commands - end


    }

    public static void registerCommand(String cmd, CommandExecutor executor) {
        try {
            Objects.requireNonNull(StaticPrisons.getInstance().getCommand(cmd)).setExecutor(executor);
        } catch (NullPointerException e) {
            StaticPrisons.getInstance().getLogger().warning("Could not register the following command: " + cmd);
        }
    }
}
