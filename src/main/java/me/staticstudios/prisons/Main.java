package me.staticstudios.prisons;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import me.staticstudios.prisons.auctionHouse.AuctionHouseManager;
import me.staticstudios.prisons.blockBroken.BlockBreakEvent;
import me.staticstudios.prisons.commands.*;
import me.staticstudios.prisons.commands.test.Test2Command;
import me.staticstudios.prisons.commands.test.TestCommand;
import me.staticstudios.prisons.customItems.Kits;
import me.staticstudios.prisons.data.dataHandling.DataWriter;
import me.staticstudios.prisons.discord.DiscordBot;
import me.staticstudios.prisons.discord.LinkHandler;
import me.staticstudios.prisons.gui.GUIListener;
import me.staticstudios.prisons.gui.GUIPage;
import me.staticstudios.prisons.mines.MineManager;
import me.staticstudios.prisons.misc.tablist.TabList;
import me.staticstudios.prisons.vote_store.VoteStoreListener;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public final class Main extends JavaPlugin implements Listener {

    public static Main getMain() {
        return main;
    }
    private static Main main;
    public static LuckPerms luckPerms;

    private boolean hasLoaded = false;

    @EventHandler
    void playerJoined(PlayerJoinEvent e) {
        if (!hasLoaded) e.getPlayer().kickPlayer("Server is still starting");
    }

    @Override
    public void onEnable() {
        main = this;
        luckPerms = getServer().getServicesManager().load(LuckPerms.class);
        getServer().getPluginManager().registerEvents(this, main);

        Bukkit.getScheduler().runTaskLater(this, () -> {
            DataWriter.loadData();
            loadMineWorld();
            RegionManager rm = WorldGuard.getInstance().getPlatform().getRegionContainer().get(BukkitAdapter.adapt(Bukkit.getWorld("mines")));
            rm.setRegions(new HashMap<>());
            ProtectedRegion r = new ProtectedCuboidRegion("__global__", BlockVector3.ZERO, BlockVector3.ZERO);
            r.setFlag(Flags.INVINCIBILITY, StateFlag.State.ALLOW);
            r.setFlag(Flags.BLOCK_BREAK, StateFlag.State.DENY);
            r.setFlag(Flags.BLOCK_PLACE, StateFlag.State.DENY);
            r.setFlag(Flags.PVP, StateFlag.State.DENY);
            r.setFlag(Flags.INTERACT, StateFlag.State.DENY);
            r.setFlag(Flags.USE, StateFlag.State.DENY);
            rm.addRegion(r);
            Kits.initialize();
            MineManager.initialize();
            AuctionHouseManager.loadAllAuctions();
            TabList.initialize();
            GUIPage.initializeGUIPages();
            TimedTasks.initializeTasks();



            //Register Commands
            //--Staff Commands
            getCommand("test").setExecutor(new TestCommand());
            getCommand("test2").setExecutor(new Test2Command());
            getCommand("modifystats").setExecutor(new ModifyStatsCommand());
            getCommand("setplayerrank").setExecutor(new SetPlayerRankCommand());
            getCommand("setstaffrank").setExecutor(new SetStaffRankCommand());
            getCommand("addchattag").setExecutor(new AddPlayerChatTagCommand());
            getCommand("addallchattags").setExecutor(new AddAllPlayerChatTagsCommand());
            getCommand("removechattag").setExecutor(new RemovePlayerChatTagCommand());
            getCommand("enderchestsee").setExecutor(new EnderChestSeeCommand());
            getCommand("renameitem").setExecutor(new RenameItemCommand());
            getCommand("schedulerestart").setExecutor(new ScheduleRestartCommand());
            getCommand("schedulestop").setExecutor(new ScheduleStopCommand());
            getCommand("broadcast").setExecutor(new BroadcastMessageCommand());
            //--Normal Commands
            getCommand("votes").setExecutor(new VotesCommand());
            getCommand("crates").setExecutor(new CratesCommand());
            getCommand("leaderboards").setExecutor(new LeaderboardsCommand());
            getCommand("fly").setExecutor(new FlyCommand());
            getCommand("store").setExecutor(new StoreCommand());
            getCommand("mines").setExecutor(new MinesCommand());
            getCommand("warps").setExecutor(new WarpsCommand());
            getCommand("spawn").setExecutor(new SpawnCommand());
            getCommand("coinflip").setExecutor(new CoinFlipCommand());
            getCommand("tokenflip").setExecutor(new TokenFlipCommand());
            getCommand("discord").setExecutor(new DiscordCommand());
            getCommand("stats").setExecutor(new StatsCommand());
            getCommand("color").setExecutor(new ColorCommand());
            getCommand("mobilesupport").setExecutor(new MobileSupportCommand());
            getCommand("privatemine").setExecutor(new PrivateMineCommand());
            getCommand("auctionhouse").setExecutor(new AuctionHouseCommand());
            getCommand("prestige").setExecutor(new PrestigeCommand());
            getCommand("enderchest").setExecutor(new EnderChestCommand());
            getCommand("chattags").setExecutor(new ChatTagsCommand());
            getCommand("settings").setExecutor(new SettingsCommand());
            getCommand("getnewpickaxe").setExecutor(new GetNewPickaxeCommand());
            getCommand("sell").setExecutor(new SellCommand());
            getCommand("rankup").setExecutor(new RankUpCommand());
            getCommand("rankupmax").setExecutor(new RankUpMaxCommand());
            getCommand("gui").setExecutor(new GUICommand());
            getCommand("npcdiag").setExecutor(new NPCDialogCommand());
            //Register Events
            getServer().getPluginManager().registerEvents(new BlockBreakEvent(), main);
            getServer().getPluginManager().registerEvents(new GUIListener(), main);
            getServer().getPluginManager().registerEvents(new Events(), main);
            getCommand("_").setExecutor(new VoteStoreListener());
            //Say that the server has loaded
            hasLoaded = true;
            LinkHandler.initialize();
            DiscordBot.initialize();
        }, 20);
    }



    @Override
    public void onDisable() {
        DataWriter.saveData();
        AuctionHouseManager.saveAllAuctions();
        DiscordBot.jda.shutdownNow();
    }

    static void loadMineWorld() {
        new WorldCreator("mines").createWorld();
    }
}
