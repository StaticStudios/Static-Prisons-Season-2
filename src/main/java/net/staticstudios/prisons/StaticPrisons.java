package net.staticstudios.prisons;

import com.github.yannicklamprecht.worldborder.api.WorldBorderApi;
import com.sk89q.worldedit.WorldEdit;
import net.luckperms.api.LuckPerms;
import net.md_5.bungee.api.ChatColor;
import net.staticstudios.gui.StaticGUI;
import net.staticstudios.mines.StaticMines;
import net.staticstudios.prisons.auctionhouse.AuctionManager;
import net.staticstudios.prisons.backpacks.BackpackCommand;
import net.staticstudios.prisons.backpacks.PrisonBackpack;
import net.staticstudios.prisons.blockbreak.BlockBreak;
import net.staticstudios.prisons.cells.CellManager;
import net.staticstudios.prisons.cells.IslandCommand;
import net.staticstudios.prisons.chat.ChatTags;
import net.staticstudios.prisons.chat.NickColors;
import net.staticstudios.prisons.chat.events.ChatEvents;
import net.staticstudios.prisons.commands.admin.AdminManager;
import net.staticstudios.prisons.commands.admin.AdvancedNicknameCommand;
import net.staticstudios.prisons.commands.normal.*;
import net.staticstudios.prisons.commands.test.Test2Command;
import net.staticstudios.prisons.commands.test.TestCommand;
import net.staticstudios.prisons.commands.votestore.VoteStoreListener;
import net.staticstudios.prisons.crates.Crates;
import net.staticstudios.prisons.customitems.CustomItems;
import net.staticstudios.prisons.customitems.CustomItemsCommand;
import net.staticstudios.prisons.customitems.Kits;
import net.staticstudios.prisons.data.backups.DataBackup;
import net.staticstudios.prisons.data.datahandling.DataSet;
import net.staticstudios.prisons.data.sql.MySQLConnection;
import net.staticstudios.prisons.events.EventManager;
import net.staticstudios.prisons.external.DiscordLink;
import net.staticstudios.prisons.fishing.FishingManager;
import net.staticstudios.prisons.gangs.Gang;
import net.staticstudios.prisons.gangs.GangCommand;
import net.staticstudios.prisons.levelup.LevelUp;
import net.staticstudios.prisons.lootboxes.MoneyLootBox;
import net.staticstudios.prisons.lootboxes.PickaxeLootBox;
import net.staticstudios.prisons.lootboxes.TokenLootBox;
import net.staticstudios.prisons.lootboxes.handler.LootBox;
import net.staticstudios.prisons.mines.MineBlock;
import net.staticstudios.prisons.mines.MineManager;
import net.staticstudios.prisons.pickaxe.EnchantCommand;
import net.staticstudios.prisons.pickaxe.PickaxeCommand;
import net.staticstudios.prisons.pickaxe.PrisonPickaxe;
import net.staticstudios.prisons.pickaxe.abilities.handler.PickaxeAbilities;
import net.staticstudios.prisons.pickaxe.enchants.AutoSellEnchant;
import net.staticstudios.prisons.pickaxe.enchants.ConsistencyEnchant;
import net.staticstudios.prisons.pickaxe.enchants.handler.BaseEnchant;
import net.staticstudios.prisons.pickaxe.enchants.handler.PickaxeEnchants;
import net.staticstudios.prisons.privatemines.PrivateMineCommand;
import net.staticstudios.prisons.privatemines.PrivateMineManager;
import net.staticstudios.prisons.pvp.PvPManager;
import net.staticstudios.prisons.pvp.commands.PvPCommand;
import net.staticstudios.prisons.pvp.koth.KingOfTheHillManager;
import net.staticstudios.prisons.pvp.outposts.OutpostManager;
import net.staticstudios.prisons.ranks.PlayerRanks;
import net.staticstudios.prisons.reclaim.ReclaimCommand;
    import net.staticstudios.prisons.ui.tablist.TabList;
import net.staticstudios.prisons.ui.tablist.TeamPrefix;
import net.staticstudios.prisons.utils.*;
import net.staticstudios.prisons.utils.items.SpreadOutExecutor;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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

    private static FileConfiguration config;

    private final ExecutorService executorService = Executors.newCachedThreadPool();

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

        safe(TeamPrefix::init);
        safe(StaticPrisons::unloadNetherAndEnd);
        safe(this::loadWorldBoarderAPI);
        safe(PrisonUtils::init);
        safe(MineManager::init);
        safe(PrivateMineManager::init);
        safe(BaseEnchant::init);
        safe(PickaxeEnchants::init);
        safe(PickaxeAbilities::init);
        safe(CustomItems::init);
        safe(Crates::init);
        safe(CellManager::init);
        safe(Gang::init);
        safe(PvPManager::init);
        safe(ChatEvents::init);
        safe(TimedTasks::init);
        safe(DataSet::init);
        safe(DataBackup::init);
        safe(PrisonPickaxe::init);
        safe(AuctionManager::init);
        safe(AutoSellEnchant::initTimer);
        safe(ConsistencyEnchant::init);
        safe(Kits::init);
        safe(TabList::init);
        safe(EventManager::init);
        safe(PlayerRanks::init);
        safe(BlockBreak::init);
        safe(FishingManager::init);
        safe(KingOfTheHillManager::init);
        safe(PrisonBackpack::init);
        safe(AdminManager::init);
        safe(ChatTags::init);
        safe(NickColors::init);

        StaticGUI.enable(this);


        safe(this::loadConfig);
        safe(LootBox::init);

        Constants.MINES_WORLD = Bukkit.getWorld("mines"); //Loaded by StaticMines
        luckPerms = getServer().getServicesManager().load(LuckPerms.class);


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
        getCommand("renameitem").setExecutor(new RenameItemCommand()); //todo: remove
        getCommand("schedulerestart").setExecutor(new ScheduleRestartCommand());
        getCommand("schedulestop").setExecutor(new ScheduleStopCommand());
        getCommand("broadcast").setExecutor(new BroadcastMessageCommand());
        getCommand("keyall").setExecutor(new KeyallCommand());
        getCommand("customitems").setExecutor(new CustomItemsCommand());
        getCommand("updateleaderboards").setExecutor(new UpdateLeaderboardsCommand());
//        getCommand("refill").setExecutor(new RefillCommand()); //todo remove
        getCommand("listplayerrank").setExecutor(new ListPlayerRankCommand());
        getCommand("liststaffrank").setExecutor(new ListStaffRankCommand());
        getCommand("addpickaxexp").setExecutor(new AddPickaxeXPCommand());
        getCommand("addpickaxeblocksmined").setExecutor(new AddPickaxeBlocksMinedCommand());
        getCommand("exemptfromleaderboards").setExecutor(new ExemptFromLeaderboardsCommand());
        getCommand("givevote").setExecutor(new GiveVoteCommand());
        getCommand("watchmessages").setExecutor(new MessageSpyCommand());
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
        getCommand("privatemine").setExecutor(new PrivateMineCommand());
        getCommand("auctionhouse").setExecutor(new AuctionHouseCommand());
        getCommand("prestige").setExecutor(new PrestigeCommand());
        getCommand("enderchest").setExecutor(new EnderChestCommand());
        getCommand("chattags").setExecutor(new ChatTagsCommand());
        getCommand("settings").setExecutor(new SettingsCommand());
        getCommand("getnewpickaxe").setExecutor(new PickaxeCommand());
        getCommand("sell").setExecutor(new SellCommand());
        getCommand("rankup").setExecutor(new RankUpCommand());
        getCommand("rankupmax").setExecutor(new RankUpMaxCommand());
        getCommand("gui").setExecutor(new GUICommand());
        getCommand("npcdiag").setExecutor(new NPCDialogCommand());
        getCommand("level").setExecutor(new LevelCommand());
        getCommand("gang").setExecutor(new GangCommand());
        getCommand("pvp").setExecutor(new PvPCommand());
        getCommand("rewards").setExecutor(new RewardsCommand());
        getCommand("advancednickname").setExecutor(new AdvancedNicknameCommand());
        Objects.requireNonNull(getCommand("resetrank")).setExecutor(new ResetRankCommand());
        //Register Events
        getServer().getPluginManager().registerEvents(new EventListener(), plugin);
        getServer().getPluginManager().registerEvents(new Events(), plugin);
//        getServer().getPluginManager().registerEvents(new BlockBreakListener(), plugin);
        getCommand("_").setExecutor(new VoteStoreListener());
    }


    @Override
    public void onDisable() {

        safe(StaticMines::disable);
        safe(DataSet::saveDataSync);
        safe(CellManager::saveSync);
        safe(PrivateMineManager::saveSync);
        safe(AuctionManager::saveAllAuctionsSync);
        safe(PrisonPickaxe::savePickaxeDataNow);
        safe(SpreadOutExecutor::flushQue);
        safe(Gang::saveAllSync);
        safe(PrisonBackpack::saveBackpacksNow);
        safe(LootBox::saveAllNow);
        safe(AdminManager::save);
        safe(OutpostManager::save);
        safe(TabList::stop);

        //Take a data backup
        safe(DataBackup::takeBackup);
        executorService.shutdownNow();
    }

    static void unloadNetherAndEnd() {
        Bukkit.unloadWorld("world_end", false);
        Bukkit.unloadWorld("world_nether", false);
    }

    public void loadConfig() {
        reloadConfig();

        config = getConfig();

        if (config.getBoolean("discordLink", true)) {
            safe(DiscordLink::init);
        }


        //Load block sell prices
        for (String key : config.getConfigurationSection("sellPrices").getKeys(false)) {
            try {
                new MineBlock(Material.valueOf(key), config.getLong("sellPrices." + key));
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
        for (int i = 0; i < 26; i++) {
            LevelUp.rankPrices[i] = config.getLong("rankup.prices." + (i + 1));
        }
        LevelUp.INITIAL_PRESTIGE_PRICE = config.getLong("prestiges.price.basePrice");

        //Load loot boxes
        TokenLootBox.loadTiers(config.getConfigurationSection("lootboxes.token"));
        MoneyLootBox.loadTiers(config.getConfigurationSection("lootboxes.money"));
        PickaxeLootBox.loadTiers(config.getConfigurationSection("lootboxes.pickaxe"));


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
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public void addTask(Runnable runnable) {
        executorService.submit(runnable);
    }
}
