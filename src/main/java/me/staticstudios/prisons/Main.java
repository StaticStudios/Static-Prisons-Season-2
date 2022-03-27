package me.staticstudios.prisons;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import me.staticstudios.prisons.core.enchants.PrisonPickaxe;
import me.staticstudios.prisons.external.DiscordLink;
import me.staticstudios.prisons.gameplay.auctionHouse.AuctionHouseManager;
import me.staticstudios.prisons.gameplay.commands.*;
import me.staticstudios.prisons.gameplay.commands.tabCompletion.IslandTabCompletion;
import me.staticstudios.prisons.gameplay.commands.test.Test2Command;
import me.staticstudios.prisons.gameplay.commands.test.TestCommand;
import me.staticstudios.prisons.gameplay.customItems.Kits;
import me.staticstudios.prisons.core.data.dataHandling.DataWriter;
import me.staticstudios.prisons.gameplay.gui.GUIListener;
import me.staticstudios.prisons.gameplay.gui.GUIPage;
import me.staticstudios.prisons.gameplay.islands.IslandManager;
import me.staticstudios.prisons.core.mines.MineManager;
import me.staticstudios.prisons.gameplay.UI.tablist.TabList;
import me.staticstudios.prisons.gameplay.commands.vote_store.VoteStoreListener;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
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
            unloadNetherAndEnd();
            DataWriter.loadData();
            loadMineWorld();
            new WorldCreator("islands").createWorld();

            IslandManager.initialize();
            Kits.initialize();
            MineManager.initialize();
            AuctionHouseManager.loadAllAuctions();
            TabList.initialize();
            GUIPage.initializeGUIPages();
            TimedTasks.initializeTasks();
            DiscordLink.initialize();



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
            getCommand("keyall").setExecutor(new KeyallCommand());
            getCommand("customitems").setExecutor(new CustomItemsCommand());
            getCommand("updateleaderboards").setExecutor(new UpdateLeaderboardsCommand());
            getCommand("refill").setExecutor(new RefillCommand());
            getCommand("listplayerrank").setExecutor(new ListPlayerRankCommand());
            getCommand("liststaffrank").setExecutor(new ListStaffRankCommand());
            getCommand("addpickaxexp").setExecutor(new AddPickaxeXPCommand());
            getCommand("addpickaxeblocksmined").setExecutor(new AddPickaxeBlocksMinedCommand());
            getCommand("exemptfromleaderboards").setExecutor(new ExemptFromLeaderboardsCommand());
            getCommand("givevote").setExecutor(new GiveVoteCommand());
            getCommand("watchmessages").setExecutor(new MessageSpyCommand());
            //--Normal Commands
            getCommand("multiplier").setExecutor(new MultiplierCommand());
            getCommand("trash").setExecutor(new TrashCommand());
            getCommand("reply").setExecutor(new ReplyCommand());
            getCommand("message").setExecutor(new MessageCommand());
            getCommand("backpack").setExecutor(new BackpackCommand());
            getCommand("shards").setExecutor(new ShardsCommand());
            getCommand("tokens").setExecutor(new TokensCommand());
            getCommand("balance").setExecutor(new BalanceCommand());
            getCommand("island").setExecutor(new IslandCommand());
            getCommand("dailyrewards").setExecutor(new DailyRewardsCommand());
            getCommand("enchant").setExecutor(new EnchantCommand());
            getCommand("reclaim").setExecutor(new ReclaimCommand());
            getCommand("dropitem").setExecutor(new DropItemCommand());
            getCommand("pay").setExecutor(new PayCommand());
            getCommand("withdraw").setExecutor(new WithdrawCommand());
            getCommand("nickname").setExecutor(new NicknameCommand());
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
            //Tab completion
            getCommand("island").setTabCompleter(new IslandTabCompletion());
            //Register Events
            getServer().getPluginManager().registerEvents(new me.staticstudios.prisons.core.EventListener(), main);
            getServer().getPluginManager().registerEvents(new GUIListener(), main);
            getServer().getPluginManager().registerEvents(new Events(), main);
            getCommand("_").setExecutor(new VoteStoreListener());
            //Say that the server has loaded
            hasLoaded = true;
        }, 20);
    }



    @Override
    public void onDisable() {
        DataWriter.saveDataSync();
        AuctionHouseManager.saveAllAuctions();
        PrisonPickaxe.dumpStatsToAllPickaxe();
    }

    static void loadMineWorld() {
        new WorldCreator("mines").createWorld();
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
    }
    static void unloadNetherAndEnd() {
        Bukkit.unloadWorld("world_end", false);
        Bukkit.unloadWorld("world_nether", false);
    }
}
