package net.staticstudios.prisons;

import com.github.yannicklamprecht.worldborder.api.WorldBorderApi;
import com.sk89q.worldedit.WorldEdit;
import net.md_5.bungee.api.ChatColor;
import net.staticstudios.gui.StaticGUI;
import net.staticstudios.mines.StaticMines;
import net.staticstudios.prisons.blockBroken.BlockBreakListener;
import net.staticstudios.prisons.cells.CellManager;
import net.staticstudios.prisons.cells.IslandCommand;
import net.staticstudios.prisons.chat.events.ChatEvents;
import net.staticstudios.prisons.commands.normal.*;
import net.staticstudios.prisons.crates.Crates;
import net.staticstudios.prisons.customItems.*;
import net.staticstudios.prisons.data.backups.DataBackup;
import net.staticstudios.prisons.data.dataHandling.DataSet;
import net.staticstudios.prisons.data.Prices;
import net.staticstudios.prisons.enchants.AutoSellEnchant;
import net.staticstudios.prisons.enchants.ConsistencyEnchant;
import net.staticstudios.prisons.enchants.handler.BaseEnchant;
import net.staticstudios.prisons.enchants.handler.PrisonEnchants;
import net.staticstudios.prisons.enchants.handler.PrisonPickaxe;
import net.staticstudios.prisons.external.DiscordLink;
import net.staticstudios.prisons.commands.tabCompletion.IslandTabCompletion;
import net.staticstudios.prisons.commands.test.Test2Command;
import net.staticstudios.prisons.commands.test.TestCommand;
import net.staticstudios.prisons.gangs.Gang;
import net.staticstudios.prisons.gangs.GangCommand;
import net.staticstudios.prisons.UI.tablist.TabList;
import net.staticstudios.prisons.commands.vote_store.VoteStoreListener;
import net.staticstudios.prisons.mines.MineManager;
import net.staticstudios.prisons.misc.EventListener;
import net.staticstudios.prisons.misc.Events;
import net.staticstudios.prisons.misc.TimedTasks;
import net.staticstudios.prisons.data.sql.MySQLConnection;
import net.staticstudios.prisons.auctionHouse.AuctionManager;
import net.staticstudios.prisons.privateMines.PrivateMine;
import net.staticstudios.prisons.privateMines.PrivateMineCommand;
import net.staticstudios.prisons.privateMines.PrivateMineManager;
import net.staticstudios.prisons.pvp.PvPCommand;
import net.staticstudios.prisons.pvp.PvPManager;
import net.staticstudios.prisons.rankup.RankUp;
import net.staticstudios.prisons.reclaim.ReclaimCommand;
import net.staticstudios.prisons.utils.Constants;
import net.luckperms.api.LuckPerms;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public final class StaticPrisons extends JavaPlugin implements Listener {

    public static StaticPrisons getInstance() {
        return plugin;
    }
    private static StaticPrisons plugin;
    public static LuckPerms luckPerms;
    public static final WorldEdit worldEdit = WorldEdit.getInstance();
    public static WorldBorderApi worldBorderAPI;
    public static long currentTick = 0;

    public static void log(String message) {
        plugin.getLogger().info(message);
    }

    void loadWorldBoarderAPI() {
        RegisteredServiceProvider<WorldBorderApi> worldBorderApiRegisteredServiceProvider = Bukkit.getServer().getServicesManager().getRegistration(WorldBorderApi.class);
        worldBorderAPI = worldBorderApiRegisteredServiceProvider.getProvider();
    }

    @Override
    public void onEnable() {
        plugin = this;
        safe(StaticPrisons::unloadNetherAndEnd);
        safe(this::loadWorldBoarderAPI);
        safe(PrisonUtils::init);
        safe(MineManager::init);
        safe(PrivateMine::init);
        safe(BaseEnchant::init);
        safe(PrisonEnchants::init);
        safe(CustomItems::init);
        safe(Crates::init);
        safe(CellManager::init);
        safe(PvPManager::init);
        safe(Gang::loadAll);
        safe(ChatEvents::init);
        safe(TimedTasks::init);
        safe(DataSet::init);
        safe(DataBackup::init);
        safe(PrisonPickaxe::init);
        safe(PrisonPickaxe::dumpLoreToAllPickaxes);
        safe(AuctionManager::init);
        safe(AutoSellEnchant::initTimer);
        safe(ConsistencyEnchant::init);
        safe(Kits::init);
        safe(TabList::init);
        safe(DiscordLink::init);




        StaticGUI.enable(this);
        luckPerms = getServer().getServicesManager().load(LuckPerms.class);
        safe(this::loadConfig);
        Constants.MINES_WORLD = new WorldCreator("mines").createWorld();


        //Register Commands
        //--Staff Commands
        getCommand("test").setExecutor(new TestCommand());
        getCommand("test2").setExecutor(new Test2Command());
        getCommand("modifystats").setExecutor(new ModifyStatsCommand()); getCommand("modifystats").setTabCompleter(new ModifyStatsCommand());
        getCommand("setplayerrank").setExecutor(new SetPlayerRankCommand()); getCommand("setplayerrank").setTabCompleter(new SetPlayerRankCommand());
        getCommand("setstaffrank").setExecutor(new SetStaffRankCommand()); getCommand("setstaffrank").setTabCompleter(new SetStaffRankCommand());
        getCommand("addchattag").setExecutor(new AddPlayerChatTagCommand()); getCommand("addchattag").setTabCompleter(new AddPlayerChatTagCommand());

        getCommand("addallchattags").setExecutor(new AddAllPlayerChatTagsCommand()); getCommand("addallchattags").setTabCompleter(new AddAllPlayerChatTagsCommand());

        getCommand("removechattag").setExecutor(new RemovePlayerChatTagCommand()); getCommand("removechattag").setTabCompleter(new RemovePlayerChatTagCommand());
        getCommand("enderchestsee").setExecutor(new EnderChestSeeCommand()); getCommand("enderchestsee").setTabCompleter(new EnderChestSeeCommand());
        getCommand("renameitem").setExecutor(new RenameItemCommand()); getCommand("renameitem").setTabCompleter(new RenameItemCommand());
        getCommand("schedulerestart").setExecutor(new ScheduleRestartCommand()); getCommand("schedulerestart").setTabCompleter(new ScheduleRestartCommand());
        getCommand("schedulestop").setExecutor(new ScheduleStopCommand()); getCommand("schedulestop").setTabCompleter(new ScheduleStopCommand());
        getCommand("broadcast").setExecutor(new BroadcastMessageCommand()); getCommand("broadcast").setTabCompleter(new BroadcastMessageCommand());
        getCommand("keyall").setExecutor(new KeyallCommand()); getCommand("keyall").setTabCompleter(new KeyallCommand());
        getCommand("customitems").setExecutor(new CustomItemsCommand()); getCommand("customitems").setTabCompleter(new CustomItemsCommand());
        getCommand("updateleaderboards").setExecutor(new UpdateLeaderboardsCommand()); getCommand("updateleaderboards").setTabCompleter(new UpdateLeaderboardsCommand());
//        getCommand("refill").setExecutor(new RefillCommand()); getCommand("refill").setTabCompleter(new RefillCommand()); //todo remove
        getCommand("listplayerrank").setExecutor(new ListPlayerRankCommand()); getCommand("listplayerrank").setTabCompleter(new ListPlayerRankCommand());
        getCommand("liststaffrank").setExecutor(new ListStaffRankCommand()); getCommand("liststaffrank").setTabCompleter(new ListStaffRankCommand());
        getCommand("addpickaxexp").setExecutor(new AddPickaxeXPCommand()); getCommand("addpickaxexp").setTabCompleter(new AddPickaxeXPCommand());
        getCommand("addpickaxeblocksmined").setExecutor(new AddPickaxeBlocksMinedCommand()); getCommand("addpickaxeblocksmined").setTabCompleter(new AddPickaxeBlocksMinedCommand());
        getCommand("exemptfromleaderboards").setExecutor(new ExemptFromLeaderboardsCommand()); getCommand("exemptfromleaderboards").setTabCompleter(new ExemptFromLeaderboardsCommand());
        getCommand("givevote").setExecutor(new GiveVoteCommand()); getCommand("givevote").setTabCompleter(new GiveVoteCommand());
        getCommand("watchmessages").setExecutor(new MessageSpyCommand()); getCommand("watchmessages").setTabCompleter(new MessageSpyCommand());
        //getCommand("debug").setExecutor(new DebugCommand());
        getCommand("reload-config").setExecutor(new ReloadConfigCommand()); getCommand("reload-config").setTabCompleter(new ReloadConfigCommand());
        //--Normal Commands
        getCommand("rules").setExecutor(new RulesCommand()); getCommand("rules").setTabCompleter(new RulesCommand());
        getCommand("multiplier").setExecutor(new MultiplierCommand()); getCommand("multiplier").setTabCompleter(new MultiplierCommand());
        getCommand("trash").setExecutor(new TrashCommand()); getCommand("trash").setTabCompleter(new TrashCommand());
        getCommand("reply").setExecutor(new ReplyCommand()); getCommand("reply").setTabCompleter(new ReplyCommand());
        getCommand("message").setExecutor(new MessageCommand()); getCommand("message").setTabCompleter(new MessageCommand());
        getCommand("backpack").setExecutor(new BackpackCommand()); getCommand("backpack").setTabCompleter(new BackpackCommand());
        getCommand("shards").setExecutor(new ShardsCommand()); getCommand("shards").setTabCompleter(new ShardsCommand());
        getCommand("tokens").setExecutor(new TokensCommand()); getCommand("tokens").setTabCompleter(new TokensCommand());
        getCommand("balance").setExecutor(new BalanceCommand()); getCommand("balance").setTabCompleter(new BalanceCommand());
        getCommand("island").setExecutor(new IslandCommand()); getCommand("island").setTabCompleter(new IslandCommand());
        getCommand("dailyrewards").setExecutor(new DailyRewardsCommand()); getCommand("dailyrewards").setTabCompleter(new DailyRewardsCommand());
        getCommand("enchant").setExecutor(new EnchantCommand()); getCommand("enchant").setTabCompleter(new EnchantCommand());
        getCommand("reclaim").setExecutor(new ReclaimCommand()); getCommand("reclaim").setTabCompleter(new ReclaimCommand()); //todo redo with new module
        getCommand("dropitem").setExecutor(new DropItemCommand()); getCommand("dropitem").setTabCompleter(new DropItemCommand());
        getCommand("pay").setExecutor(new PayCommand()); getCommand("pay").setTabCompleter(new PayCommand());
        getCommand("withdraw").setExecutor(new WithdrawCommand()); getCommand("withdraw").setTabCompleter(new WithdrawCommand());
        getCommand("nickname").setExecutor(new NicknameCommand()); getCommand("nickname").setTabCompleter(new NicknameCommand());
        getCommand("votes").setExecutor(new VotesCommand()); getCommand("votes").setTabCompleter(new VotesCommand());
        getCommand("crates").setExecutor(new CratesCommand()); getCommand("crates").setTabCompleter(new CratesCommand());
        getCommand("leaderboards").setExecutor(new LeaderboardsCommand()); getCommand("leaderboards").setTabCompleter(new LeaderboardsCommand());
        getCommand("fly").setExecutor(new FlyCommand()); getCommand("fly").setTabCompleter(new FlyCommand());
        getCommand("store").setExecutor(new StoreCommand()); getCommand("store").setTabCompleter(new StoreCommand());
        getCommand("mines").setExecutor(new MinesCommand()); getCommand("mines").setTabCompleter(new MinesCommand());
        getCommand("warps").setExecutor(new WarpsCommand()); getCommand("warps").setTabCompleter(new WarpsCommand());
        getCommand("spawn").setExecutor(new SpawnCommand()); getCommand("spawn").setTabCompleter(new SpawnCommand());
        getCommand("coinflip").setExecutor(new CoinFlipCommand()); getCommand("coinflip").setTabCompleter(new CoinFlipCommand());
        getCommand("tokenflip").setExecutor(new TokenFlipCommand()); getCommand("tokenflip").setTabCompleter(new TokenFlipCommand());
        getCommand("discord").setExecutor(new DiscordCommand()); getCommand("discord").setTabCompleter(new DiscordCommand());
        getCommand("stats").setExecutor(new StatsCommand()); getCommand("stats").setTabCompleter(new StatsCommand());
        getCommand("color").setExecutor(new ColorCommand()); getCommand("color").setTabCompleter(new ColorCommand());
        getCommand("mobilesupport").setExecutor(new MobileSupportCommand()); getCommand("mobilesupport").setTabCompleter(new MobileSupportCommand());
        getCommand("privatemine").setExecutor(new PrivateMineCommand()); getCommand("privatemine").setTabCompleter(new PrivateMineCommand());
        getCommand("auctionhouse").setExecutor(new AuctionHouseCommand()); getCommand("auctionhouse").setTabCompleter(new AuctionHouseCommand());
        getCommand("prestige").setExecutor(new PrestigeCommand()); getCommand("prestige").setTabCompleter(new PrestigeCommand());
        getCommand("enderchest").setExecutor(new EnderChestCommand()); getCommand("enderchest").setTabCompleter(new EnderChestCommand());
        getCommand("chattags").setExecutor(new ChatTagsCommand()); getCommand("chattags").setTabCompleter(new ChatTagsCommand());
        getCommand("settings").setExecutor(new SettingsCommand()); getCommand("settings").setTabCompleter(new SettingsCommand());
        getCommand("getnewpickaxe").setExecutor(new GetNewPickaxeCommand()); getCommand("getnewpickaxe").setTabCompleter(new GetNewPickaxeCommand());
        getCommand("sell").setExecutor(new SellCommand()); getCommand("sell").setTabCompleter(new SellCommand());
        getCommand("rankup").setExecutor(new RankUpCommand()); getCommand("rankup").setTabCompleter(new RankUpCommand());
        getCommand("rankupmax").setExecutor(new RankUpMaxCommand()); getCommand("rankupmax").setTabCompleter(new RankUpMaxCommand());
        getCommand("gui").setExecutor(new GUICommand()); getCommand("gui").setTabCompleter(new GUICommand());
        getCommand("npcdiag").setExecutor(new NPCDialogCommand()); getCommand("npcdiag").setTabCompleter(new NPCDialogCommand());
        getCommand("level").setExecutor(new LevelCommand()); getCommand("level").setTabCompleter(new LevelCommand());
        getCommand("gang").setExecutor(new GangCommand()); getCommand("gang").setTabCompleter(new GangCommand());
        getCommand("pvp").setExecutor(new PvPCommand()); getCommand("pvp").setTabCompleter(new PvPCommand());
        //Tab completion
        getCommand("island").setTabCompleter(new IslandTabCompletion());
        //Register Events
        getServer().getPluginManager().registerEvents(new EventListener(), plugin);
        getServer().getPluginManager().registerEvents(new Events(), plugin);
        getServer().getPluginManager().registerEvents(new BlockBreakListener(), plugin);
        getCommand("_").setExecutor(new VoteStoreListener());
    }


    @Override
    public void onDisable() { //hi
        safe(StaticMines::disable);
        safe(DataSet::saveDataSync);
        safe(CellManager::saveSync);
        safe(PrivateMineManager::saveSync);
        safe(AuctionManager::saveAllAuctionsSync);
        safe(PrisonPickaxe::savePickaxeDataNow);
        safe(PrisonPickaxe::dumpLoreToAllPickaxes);
        safe(Gang::saveAllSync);
    }
    static void unloadNetherAndEnd() {
        Bukkit.unloadWorld("world_end", false);
        Bukkit.unloadWorld("world_nether", false);
    }

    public void loadConfig() {
        reloadConfig();
        FileConfiguration config = getConfig();

        //Load block sell prices
        for (String key : config.getConfigurationSection("sellPrices").getKeys(false)) {
            try {
                Prices.BLOCK_SELL_PRICES.put(Material.valueOf(key), BigDecimal.valueOf(config.getInt("sellPrices." + key)));
            } catch (IllegalArgumentException e) {
                Bukkit.getLogger().warning("There was an error while loading the config! Unknown material '" + key + "'");
            } catch (NullPointerException e) {
                Bukkit.getLogger().warning("There was en error while loading the config! The value for '" + key + "' should be a valid number! Got: '" + config.getString("sellPrices." + key) + "' instead");
            }
        }
        //Tips
        final String prefix = "&b&lTips &8&l>> &r";
        List<String> tips = new ArrayList<>();
        for (String tip : config.getStringList("tips")) tips.add(ChatColor.translateAlternateColorCodes('&', prefix + tip));
        Constants.TIPS = tips.toArray(new String[0]);

        //Load prestige mine requirements
        for (int i = 0; i < 15; i++) Constants.PRESTIGE_MINE_REQUIREMENTS[i] = config.getLong("prestiges.mineRequirements." + (i + 1));
        //Load rankup prices
        RankUp.rankPrices = new ArrayList<>();
        for (int i = 0; i < 26; i++) RankUp.rankPrices.add(BigInteger.valueOf(config.getLong("rankup.prices." + (i + 1))));
        RankUp.INITIAL_PRESTIGE_PRICE = BigInteger.valueOf(config.getLong("prestiges.price.basePrice"));

        //Load Pouches
        MoneyPouchTier1.minValue = new BigInteger(config.getString("pouches.money.1.min"));
        MoneyPouchTier1.maxValue = new BigInteger(config.getString("pouches.money.1.max"));
        MoneyPouchTier2.minValue = new BigInteger(config.getString("pouches.money.2.min"));
        MoneyPouchTier2.maxValue = new BigInteger(config.getString("pouches.money.2.max"));
        MoneyPouchTier3.minValue = new BigInteger(config.getString("pouches.money.3.min"));
        MoneyPouchTier3.maxValue = new BigInteger(config.getString("pouches.money.3.max"));
        TokenPouchTier1.minValue = new BigInteger(config.getString("pouches.token.1.min"));
        TokenPouchTier1.maxValue = new BigInteger(config.getString("pouches.token.1.max"));
        TokenPouchTier2.minValue = new BigInteger(config.getString("pouches.token.2.min"));
        TokenPouchTier2.maxValue = new BigInteger(config.getString("pouches.token.2.max"));
        TokenPouchTier3.minValue = new BigInteger(config.getString("pouches.token.3.min"));
        TokenPouchTier3.maxValue = new BigInteger(config.getString("pouches.token.3.max"));
        MultiPouchTier1.minAmount = config.getInt("pouches.multi.1.amount.min");
        MultiPouchTier1.maxAmount = config.getInt("pouches.multi.1.amount.max");
        MultiPouchTier1.minTime = config.getInt("pouches.multi.1.time.min");
        MultiPouchTier1.maxTime = config.getInt("pouches.multi.1.time.max");
        MultiPouchTier2.minAmount = config.getInt("pouches.multi.2.amount.min");
        MultiPouchTier2.maxAmount = config.getInt("pouches.multi.2.amount.max");
        MultiPouchTier2.minTime = config.getInt("pouches.multi.2.time.min");
        MultiPouchTier2.maxTime = config.getInt("pouches.multi.2.time.max");
        MultiPouchTier3.minAmount = config.getInt("pouches.multi.3.amount.min");
        MultiPouchTier3.maxAmount = config.getInt("pouches.multi.3.amount.max");
        MultiPouchTier3.minTime = config.getInt("pouches.multi.3.time.min");
        MultiPouchTier3.maxTime = config.getInt("pouches.multi.3.time.max");



        //SQL config
        File sqlConfigFile = new File(getDataFolder(), "sqlConfig.yml");
        FileConfiguration sqlConfig = YamlConfiguration.loadConfiguration(sqlConfigFile);
        sqlConfig.addDefault("host", "unknown");
        sqlConfig.addDefault("database", "unknown");
        sqlConfig.addDefault("port", "unknown");
        sqlConfig.addDefault("username", "unknown");
        sqlConfig.addDefault("password", "unknown");
        sqlConfig.options().copyDefaults(true);
        try {
            sqlConfig.save(sqlConfigFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        MySQLConnection.host = sqlConfig.getString("host");
        MySQLConnection.database = sqlConfig.getString("database");
        MySQLConnection.port = sqlConfig.getString("port");
        MySQLConnection.username = sqlConfig.getString("username");
        MySQLConnection.password = sqlConfig.getString("password");

        Bukkit.getLogger().log(Level.INFO, "Finished loading config.yml");
    }


    /**
     * Safely run a piece of code in a try-catch block. Good for init tasks to prevent other modules from loading.
     */
    public static void safe(Runnable r) {
        try {
            r.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
