package net.staticstudios.prisons;

import com.github.yannicklamprecht.worldborder.api.WorldBorderApi;
import com.sk89q.worldedit.WorldEdit;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.luckperms.api.LuckPerms;
import net.staticstudios.citizens.CitizensUtils;
import net.staticstudios.gui.StaticGUI;
import net.staticstudios.mines.StaticMines;
import net.staticstudios.prisons.admin.AdminManager;
import net.staticstudios.prisons.auctionhouse.AuctionManager;
import net.staticstudios.prisons.backpacks.BackpackManager;
import net.staticstudios.prisons.blockbreak.BlockBreak;
import net.staticstudios.prisons.cells.CellManager;
import net.staticstudios.prisons.challenges.ChallengeManager;
import net.staticstudios.prisons.chat.ChatManager;
import net.staticstudios.prisons.chat.nicknames.NickColors;
import net.staticstudios.prisons.commands.CommandManager;
import net.staticstudios.prisons.crates.Crates;
import net.staticstudios.prisons.customitems.CustomItems;
import net.staticstudios.prisons.customitems.Kits;
import net.staticstudios.prisons.data.backups.DataBackup;
import net.staticstudios.prisons.data.datahandling.DataSet;
import net.staticstudios.prisons.data.sql.MySQLConnection;
import net.staticstudios.prisons.enchants.Enchantment;
import net.staticstudios.prisons.events.EventManager;
import net.staticstudios.prisons.external.DiscordLink;
import net.staticstudios.prisons.fishing.FishingManager;
import net.staticstudios.prisons.gangs.Gang;
import net.staticstudios.prisons.leaderboards.LeaderboardManager;
import net.staticstudios.prisons.levelup.LevelUp;
import net.staticstudios.prisons.levelup.prestige.Prestige;
import net.staticstudios.prisons.levelup.rankup.RankUp;
import net.staticstudios.prisons.lootboxes.LootBox;
import net.staticstudios.prisons.mines.MineBlock;
import net.staticstudios.prisons.mines.MineManager;
import net.staticstudios.prisons.pickaxe.PickaxeManager;
import net.staticstudios.prisons.pickaxe.abilities.handler.PickaxeAbilities;
import net.staticstudios.prisons.pickaxe.enchants.handler.PickaxeEnchants;
import net.staticstudios.prisons.privatemines.PrivateMineManager;
import net.staticstudios.prisons.pvp.PvPManager;
import net.staticstudios.prisons.pvp.koth.KingOfTheHillManager;
import net.staticstudios.prisons.pvp.outposts.OutpostManager;
import net.staticstudios.prisons.ranks.PlayerRanks;
import net.staticstudios.prisons.ui.tablist.TabList;
import net.staticstudios.prisons.ui.tablist.TeamPrefix;
import net.staticstudios.prisons.utils.*;
import net.staticstudios.prisons.utils.items.SpreadOutExecutor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;

public final class StaticPrisons extends JavaPlugin implements Listener {

    private static StaticPrisons plugin;
    public static LuckPerms luckPerms;
    public static final WorldEdit worldEdit = WorldEdit.getInstance();
    public static WorldBorderApi worldBorderAPI;
    public static long currentTick = 0;

    private final ExecutorService executorService = Executors.newCachedThreadPool();

    private boolean citizensEnabled = false;



    public static StaticPrisons getInstance() {
        return plugin;
    }


    public static MiniMessage miniMessage() {
        return PrisonUtils.miniMessage;
    }

    @Override
    public void onEnable() {
        plugin = this;

        safe(this::enableCitizens);
        safe(this::loadWorldBoarderAPI);
        safe(TeamPrefix::init);
        safe(PrisonUtils::init);
        safe(MineManager::init);
        safe(PrivateMineManager::init);
        safe(Enchantment::init);
        safe(PickaxeManager::init);
//        safe(PickaxeAbilities::init);
        safe(CustomItems::init);
        safe(Crates::init);
        safe(CellManager::init);
        safe(Gang::init);
        safe(PvPManager::init);
        safe(TimedTasks::init);
        safe(DataSet::init);
        safe(DataBackup::init);
        safe(AuctionManager::init);
        safe(Kits::init);
        safe(LevelUp::init);
        safe(TabList::init);
        safe(EventManager::init);
        safe(PlayerRanks::init);
        safe(BlockBreak::init);
        safe(FishingManager::init);
        safe(KingOfTheHillManager::init);
        safe(BackpackManager::init);
        safe(AdminManager::init);
        safe(ChatManager::init);
        safe(NickColors::init);
        safe(LeaderboardManager::init);
        safe(CitizensUtils::init);
        safe(ChallengeManager::init);

        StaticGUI.enable(this);
        net.staticstudios.newgui.StaticGUI.enable(this);


        safe(this::loadConfig);
        safe(LootBox::init);

        Constants.MINES_WORLD = Bukkit.getWorld("mines"); //Loaded by StaticMines
        luckPerms = getServer().getServicesManager().load(LuckPerms.class);

        //Register Commands

        CommandManager.loadCommands();

        //Register Events
        getServer().getPluginManager().registerEvents(new EventListener(), plugin);
        getServer().getPluginManager().registerEvents(new Events(), plugin);
//        getServer().getPluginManager().registerEvents(new BlockBreakListener(), plugin);

    }



    private void enableCitizens() {
        if (getServer().getPluginManager().getPlugin("Citizens") == null) {
            getLogger().warning("Citizens not found, disabling Citizens support");
            return;
        }

        citizensEnabled = true;
    }


    public static void log(String message) {
        plugin.getLogger().info(message);
    }

    void loadWorldBoarderAPI() {
        RegisteredServiceProvider<WorldBorderApi> worldBorderApiRegisteredServiceProvider = Bukkit.getServer().getServicesManager().getRegistration(WorldBorderApi.class);
        assert worldBorderApiRegisteredServiceProvider != null;
        worldBorderAPI = worldBorderApiRegisteredServiceProvider.getProvider();
    }

    public void loadConfig() {
        reloadConfig();

        FileConfiguration config = getConfig();

        if (config.getBoolean("discordLink", true)) {
            safe(DiscordLink::init);
        }


        //Load block sell prices
        ConfigurationSection sellPricesSection = config.getConfigurationSection("sellPrices");
        if (sellPricesSection == null) {
            sellPricesSection = config.createSection("sellPrices");
        }

        for (String key : sellPricesSection.getKeys(false)) {
            try {
                new MineBlock(Material.valueOf(key), config.getLong("sellPrices." + key));
            } catch (IllegalArgumentException e) {
                Bukkit.getServer().getLogger().warning("There was an error while loading the config! Unknown material '" + key + "'");
            } catch (NullPointerException e) {
                Bukkit.getServer().getLogger().warning("There was en error while loading the config! The value for '" + key + "' should be a valid number! Got: '" + config.getString("sellPrices." + key) + "' instead");
            }
        }

        //Tips
        List<Component> tips = new ArrayList<>();
//        for (String tip : config.getStringList("tips")) tips.add(Prefix.TIPS.append(Component.text(tip)));

        for (String tip : config.getStringList("tips")) {
            // TODO: 23/08/2022 - change that to use minimessages in config
            tips.add(Prefix.TIPS.append(LegacyComponentSerializer.legacyAmpersand().deserialize(tip)));
        }

        Constants.TIPS = tips;

        //Load prestige mine requirements
        for (int i = 0; i < 15; i++)
            Constants.PRESTIGE_MINE_REQUIREMENTS[i] = config.getLong("prestiges.mineRequirements." + (i + 1));
        //Load rankup prices
        for (int i = 0; i < 26; i++) {
            RankUp.RANK_PRICES[i] = config.getLong("rankup.prices." + (i + 1));
        }
        Prestige.INITIAL_PRESTIGE_PRICE = config.getLong("prestiges.price.basePrice");


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

        getLogger().log(Level.INFO, "Finished loading config.yml");
    }


    @Override
    public void onDisable() {
        safe(net.staticstudios.newgui.StaticGUI::disable);
        safe(StaticMines::disable);
        safe(DataSet::saveDataSync);
        safe(CellManager::saveSync);
        safe(PrivateMineManager::saveSync);
        safe(AuctionManager::saveAllAuctionsSync);
        safe(PickaxeManager::savePickaxeDataNow);
        safe(SpreadOutExecutor::flushQue);
        safe(Gang::saveAllSync);
        safe(BackpackManager::saveBackpacksNow);
        safe(LootBox::saveAllNow);
        safe(ChallengeManager::saveToFileNow);
        safe(AdminManager::save);
        safe(OutpostManager::save);
        safe(TabList::stop);

        //Take a data backup
        safe(DataBackup::takeBackup);
        executorService.shutdownNow();
    }

    /**
     * Safely run a piece of code in a try-catch block.
     * Good for init tasks to prevent other modules from not loading due to errors.
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

    public boolean isCitizensEnabled() {
        return citizensEnabled;
    }
}
