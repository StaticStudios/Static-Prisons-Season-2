package net.staticstudios.prisons;

import com.sk89q.worldedit.WorldEdit;
import net.staticstudios.gui.StaticGUI;
import net.staticstudios.mines.StaticMines;
import net.staticstudios.prisons.blockBroken.BlockBreakListener;
import net.staticstudios.prisons.customItems.*;
import net.staticstudios.prisons.data.dataHandling.DataSet;
import net.staticstudios.prisons.data.Prices;
import net.staticstudios.prisons.enchants.AutoSellEnchant;
import net.staticstudios.prisons.enchants.handler.PrisonEnchants;
import net.staticstudios.prisons.enchants.handler.PrisonPickaxe;
import net.staticstudios.prisons.external.DiscordLink;
import net.staticstudios.prisons.commands.*;
import net.staticstudios.prisons.commands.tabCompletion.IslandTabCompletion;
import net.staticstudios.prisons.commands.test.Test2Command;
import net.staticstudios.prisons.commands.test.TestCommand;
import net.staticstudios.prisons.gui.GUIListener;
import net.staticstudios.prisons.gui.GUIPage;
import net.staticstudios.prisons.islands.IslandManager;
import net.staticstudios.prisons.UI.tablist.TabList;
import net.staticstudios.prisons.commands.vote_store.VoteStoreListener;
import net.staticstudios.prisons.misc.EventListener;
import net.staticstudios.prisons.misc.Events;
import net.staticstudios.prisons.misc.TimedTasks;
import net.staticstudios.prisons.data.sql.MySQLConnection;
import net.staticstudios.prisons.auctionHouse.AuctionManager;
import net.staticstudios.prisons.rankup.RankUpPrices;
import net.staticstudios.prisons.utils.Constants;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.logging.Level;

public final class StaticPrisons extends JavaPlugin implements Listener {

    public static StaticPrisons getInstance() {
        return plugin;
    }
    private static StaticPrisons plugin;
    public static LuckPerms luckPerms;
    public static final WorldEdit worldEdit = WorldEdit.getInstance();
    public static long currentTick = 0;

    @Override
    public void onEnable() {
        plugin = this;
        StaticMines.enable(this);
        StaticGUI.enable(this);
        luckPerms = getServer().getServicesManager().load(LuckPerms.class);
        loadConfig();
        unloadNetherAndEnd();
        //DataWriter.loadData();
        DataSet.loadData();
        AuctionManager.loadAllAuctions();
        //PrisonEnchants.initialize(); //todo delete soon
        PrisonEnchants.createEnchants();
        PrisonPickaxe.loadPickaxeData();
        //AuctionHouseManager.loadAllAuctions();
        IslandManager.initialize();
        //MineManager.initialize();
        Constants.MINES_WORLD = new WorldCreator("mines").createWorld();
        GUIPage.initializeGUIPages();
        DiscordLink.initialize();
        TabList.initialize();
        Kits.initialize();
        TimedTasks.startTasks();
        AutoSellEnchant.initTimer();


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
        getCommand("debug").setExecutor(new DebugCommand());
        getCommand("reload-config").setExecutor(new ReloadConfigCommand());
        //--Normal Commands
        getCommand("rules").setExecutor(new RulesCommand());
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
        //getCommand("privatemine").setExecutor(new PrivateMineCommand());
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
        getServer().getPluginManager().registerEvents(new EventListener(), plugin);
        getServer().getPluginManager().registerEvents(new GUIListener(), plugin);
        getServer().getPluginManager().registerEvents(new Events(), plugin);
        getServer().getPluginManager().registerEvents(new BlockBreakListener(), plugin);
        getCommand("_").setExecutor(new VoteStoreListener());
    }


    @Override
    public void onDisable() {
        StaticMines.disable();
        DataSet.saveDataSync();
        AuctionManager.saveAllAuctionsSync();
        //AuctionHouseManager.saveAllAuctions();
        //PrisonPickaxe.dumpStatsToAllPickaxe();
        PrisonPickaxe.savePickaxeDataNow();
        PrisonPickaxe.dumpLoreToAllPickaxes();
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
                Prices.BLOCK_SELL_PRICES.put(Material.valueOf(key), BigInteger.valueOf(config.getInt("sellPrices." + key)));
            } catch (IllegalArgumentException e) {
                Bukkit.getLogger().warning("There was an error while loading the config! Unknown material '" + key + "'");
            } catch (NullPointerException e) {
                Bukkit.getLogger().warning("There was en error while loading the config! The value for '" + key + "' should be a valid number! Got: '" + config.getString("sellPrices." + key) + "' instead");
            }
        }

        /*
        //Load enchants
        PrisonEnchant enchant;
        enchant = new PrisonEnchant("fortune", config.getInt("enchants.fortune.maxLevel"), new BigInteger(config.getString("enchants.fortune.price")));
        enchant.DISPLAY_NAME = config.getString("enchants.fortune.displayName");
        enchant.DESCRIPTION = config.getStringList("enchants.fortune.description");
        PrisonEnchants.FORTUNE = enchant;
        enchant = new PrisonEnchant("oreSplitter", config.getInt("enchants.oreSplitter.maxLevel"), new BigInteger(config.getString("enchants.oreSplitter.price")));
        enchant.DISPLAY_NAME = config.getString("enchants.oreSplitter.displayName");
        enchant.DESCRIPTION = config.getStringList("enchants.oreSplitter.description");
        PrisonEnchants.ORE_SPLITTER = enchant;
        enchant = new PrisonEnchant("tokenator", config.getInt("enchants.tokenator.maxLevel"), new BigInteger(config.getString("enchants.tokenator.price")));
        enchant.DISPLAY_NAME = config.getString("enchants.tokenator.displayName");
        enchant.DESCRIPTION = config.getStringList("enchants.tokenator.description");
        PrisonEnchants.TOKENATOR = enchant;
        enchant = new PrisonEnchant("keyFinder", config.getInt("enchants.keyFinder.maxLevel"), new BigInteger(config.getString("enchants.keyFinder.price")));
        enchant.DISPLAY_NAME = config.getString("enchants.keyFinder.displayName");
        enchant.DESCRIPTION = config.getStringList("enchants.keyFinder.description");
        PrisonEnchants.KEY_FINDER = enchant;
        enchant = new PrisonEnchant("metalDetector", config.getInt("enchants.metalDetector.maxLevel"), new BigInteger(config.getString("enchants.metalDetector.price")));
        enchant.DISPLAY_NAME = config.getString("enchants.metalDetector.displayName");
        enchant.DESCRIPTION = config.getStringList("enchants.metalDetector.description");
        PrisonEnchants.METAL_DETECTOR = enchant;
        enchant = new PrisonEnchant("explosion", config.getInt("enchants.explosion.maxLevel"), new BigInteger(config.getString("enchants.explosion.price")));
        enchant.DISPLAY_NAME = config.getString("enchants.explosion.displayName");
        enchant.DESCRIPTION = config.getStringList("enchants.explosion.description");
        PrisonEnchants.EXPLOSION = enchant;
        enchant = new PrisonEnchant("jackHammer", config.getInt("enchants.jackHammer.maxLevel"), new BigInteger(config.getString("enchants.jackHammer.price")));
        enchant.DISPLAY_NAME = config.getString("enchants.jackHammer.displayName");
        enchant.DESCRIPTION = config.getStringList("enchants.jackHammer.description");
        PrisonEnchants.JACK_HAMMER = enchant;
        enchant = new PrisonEnchant("doubleWammy", config.getInt("enchants.doubleWammy.maxLevel"), new BigInteger(config.getString("enchants.doubleWammy.price")));
        enchant.DISPLAY_NAME = config.getString("enchants.doubleWammy.displayName");
        enchant.DESCRIPTION = config.getStringList("enchants.doubleWammy.description");
        PrisonEnchants.DOUBLE_WAMMY = enchant;
        enchant = new PrisonEnchant("multiDirectional", config.getInt("enchants.multiDirectional.maxLevel"), new BigInteger(config.getString("enchants.multiDirectional.price")));
        enchant.DISPLAY_NAME = config.getString("enchants.multiDirectional.displayName");
        enchant.DESCRIPTION = config.getStringList("enchants.multiDirectional.description");
        PrisonEnchants.MULTI_DIRECTIONAL = enchant;
        enchant = new PrisonEnchant("merchant", config.getInt("enchants.merchant.maxLevel"), new BigInteger(config.getString("enchants.merchant.price")));
        enchant.DISPLAY_NAME = config.getString("enchants.merchant.displayName");
        enchant.DESCRIPTION = config.getStringList("enchants.merchant.description");
        PrisonEnchants.MERCHANT = enchant;
        enchant = new PrisonEnchant("consistency", config.getInt("enchants.consistency.maxLevel"), new BigInteger(config.getString("enchants.consistency.price")));
        enchant.DISPLAY_NAME = config.getString("enchants.consistency.displayName");
        enchant.DESCRIPTION = config.getStringList("enchants.consistency.description");
        PrisonEnchants.CONSISTENCY = enchant;
        enchant = new PrisonEnchant("haste", config.getInt("enchants.haste.maxLevel"), new BigInteger(config.getString("enchants.haste.price")));
        enchant.DISPLAY_NAME = config.getString("enchants.haste.displayName");
        enchant.DESCRIPTION = config.getStringList("enchants.haste.description");
        PrisonEnchants.HASTE = enchant;
        enchant = new PrisonEnchant("speed", config.getInt("enchants.speed.maxLevel"), new BigInteger(config.getString("enchants.speed.price")));
        enchant.DISPLAY_NAME = config.getString("enchants.speed.displayName");
        enchant.DESCRIPTION = config.getStringList("enchants.speed.description");
        PrisonEnchants.SPEED = enchant;
        enchant = new PrisonEnchant("nightVision", config.getInt("enchants.nightVision.maxLevel"), new BigInteger(config.getString("enchants.nightVision.price")));
        enchant.DISPLAY_NAME = config.getString("enchants.nightVision.displayName");
        enchant.DESCRIPTION = config.getStringList("enchants.nightVision.description");
        PrisonEnchants.NIGHT_VISION = enchant;

         */

        //Load prestige mine requirements
        for (int i = 0; i < 15; i++) Constants.PRESTIGE_MINE_REQUIREMENTS[i] = config.getLong("prestiges.mineRequirements." + (i + 1));
        //Load rankup prices
        RankUpPrices.rankPrices = new ArrayList<>();
        for (int i = 0; i < 26; i++) RankUpPrices.rankPrices.add(BigInteger.valueOf(config.getLong("rankup.prices." + (i + 1))));
        RankUpPrices.INITIAL_PRESTIGE_PRICE = BigInteger.valueOf(config.getLong("prestiges.price.basePrice"));

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



}
