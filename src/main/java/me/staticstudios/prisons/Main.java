package me.staticstudios.prisons;

import me.staticstudios.prisons.blockBroken.BlockBreakEvent;
import me.staticstudios.prisons.commands.*;
import me.staticstudios.prisons.commands.test.Test2Command;
import me.staticstudios.prisons.commands.test.TestCommand;
import me.staticstudios.prisons.data.dataHandling.DataWriter;
import me.staticstudios.prisons.gui.GUIListener;
import me.staticstudios.prisons.gui.GUIPage;
import me.staticstudios.prisons.mines.MineManager;
import me.staticstudios.prisons.misc.tablist.TabList;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

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

        Bukkit.getScheduler().runTaskLater(this, new Runnable() {
            @Override
            public void run() {
                DataWriter.loadData();

                //Load all worlds in
                loadAllMines();
                //Refill all public mines
                MineManager.initialize();
                //Register tablist teams
                TabList.initialize();
                //Initialize GUI pages
                GUIPage.initializeGUIPages();



                //Start timed tasks
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
                //--Normal Commands
                getCommand("auctionhouse").setExecutor(new AuctionHouseCommand());
                getCommand("prestige").setExecutor(new PrestigeCommand());
                getCommand("enderchest").setExecutor(new EnderChestCommand());
                getCommand("chattags").setExecutor(new ChatTagsCommand());
                getCommand("getnewpickaxe").setExecutor(new GetNewPickaxeCommand());
                getCommand("sell").setExecutor(new SellCommand());
                getCommand("rankup").setExecutor(new RankUpCommand());
                getCommand("rankupmax").setExecutor(new RankUpMaxCommand());
                getCommand("gui").setExecutor(new GUICommand());
                //Register Events
                getServer().getPluginManager().registerEvents(new BlockBreakEvent(), main);
                getServer().getPluginManager().registerEvents(new GUIListener(), main);
                getServer().getPluginManager().registerEvents(new Events(), main);
                //Say that the server has loaded
                hasLoaded = true;
            }
        }, 20);
    }



    @Override
    public void onDisable() {
        // Plugin shutdown logic
        //DataWriter.saveData();
    }

    static void loadAllMines() {
        new WorldCreator("mines").createWorld();
        //TODO: make sure players cannot fly out of their mines
    }
}
