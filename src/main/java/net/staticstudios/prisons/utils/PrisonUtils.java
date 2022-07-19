package net.staticstudios.prisons.utils;

import com.destroystokyo.paper.event.server.ServerTickStartEvent;
import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.pickaxe.enchants.handler.PrisonEnchants;
import net.staticstudios.prisons.pickaxe.PrisonPickaxe;
import net.staticstudios.prisons.data.PlayerData;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import org.bukkit.*;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.math.BigInteger;
import java.text.NumberFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class PrisonUtils {
    public static void init() {
        Players.init(StaticPrisons.getInstance());
    }

    public static String formatTime(long milliseconds) {
        long days = milliseconds / 86400000;
        milliseconds -= days * 86400000;
        long hours = milliseconds / 3600000;
        milliseconds -= hours * 3600000;
        long minutes = milliseconds / 60000;
        milliseconds -= minutes * 60000;
        long seconds = milliseconds / 1000;
        StringBuilder sb = new StringBuilder();
        if (days > 0) sb.append(days).append("d ");
        if (hours > 0) sb.append(hours).append("h ");
        if (minutes > 0) sb.append(minutes).append("m ");
        if (seconds > 0) sb.append(seconds).append("s ");
        return sb.toString();
    }

    public static ItemStack applyLoreToItem(ItemStack item, List<String> lore) {
        lore.replaceAll(textToTranslate -> ChatColor.translateAlternateColorCodes('&', textToTranslate));
        ItemMeta meta = item.getItemMeta();
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    public static int randomInt(int min, int max) {
        return new Random().nextInt((max - min) + 1) + min;
    }
    public static float randomFloat(float min, float max) {
        return new Random().nextFloat((max - min) + 1) + min;
    }
    public static double randomDouble(double min, double max) {
        return new Random().nextDouble((max - min) + 1) + min;
    }
    public static BigInteger randomBigInt(BigInteger min, BigInteger max) { //github copilot created this and i have no idea if it works - use numbers not string values
        return min.add(new BigInteger(String.valueOf(Math.round(Math.random() * (max.subtract(min).doubleValue())))));
    }
//    public static BigInteger randomBigInt(BigInteger min, BigInteger max) {
//        BigInteger bigInteger = max.subtract(min);
//        BigInteger res = new BigInteger(max.bitLength(), new Random());
//        if (res.compareTo(min) < 0) res = res.add(min);
//        if (res.compareTo(bigInteger) >= 0) res = res.mod(bigInteger).add(min);
//        return res;
//    }

    public static ItemStack setItemCount(ItemStack itemStack, int amount) {
        itemStack = new ItemStack(itemStack);
        itemStack.setAmount(amount);
        return itemStack;
    }

    public static Location calcMinLocation(Location loc1, Location loc2) {
        if (!Objects.equals(loc1.getWorld(), loc2.getWorld())) {
            throw new IllegalArgumentException("Points must be in the same world");
        } else {
            double minX = Math.min(loc1.getX(), loc2.getX());
            double minY = Math.min(loc1.getY(), loc2.getY());
            double minZ = Math.min(loc1.getZ(), loc2.getZ());
            return new Location(loc1.getWorld(), minX, minY, minZ);
        }
    }

    public static Location calcMaxLocation(Location loc1, Location loc2) {
        if (!Objects.equals(loc1.getWorld(), loc2.getWorld())) {
            throw new IllegalArgumentException("Points must be in the same world");
        } else {
            double maxX = Math.max(loc1.getX(), loc2.getX());
            double maxY = Math.max(loc1.getY(), loc2.getY());
            double maxZ = Math.max(loc1.getZ(), loc2.getZ());
            return new Location(loc1.getWorld(), maxX, maxY, maxZ);
        }
    }

    public static void updateLuckPermsForPlayerRanks(Player player) {
        Bukkit.getScheduler().runTaskAsynchronously(StaticPrisons.getInstance(), () -> {
            PlayerData playerData = new PlayerData(player);
            User user = StaticPrisons.luckPerms.getPlayerAdapter(Player.class).getUser(player);
            user.data().remove(Node.builder("group.prisons-donator-staticp").build());
            user.data().remove(Node.builder("group.prisons-donator-static").build());
            user.data().remove(Node.builder("group.prisons-donator-mythic").build());
            user.data().remove(Node.builder("group.prisons-donator-master").build());
            user.data().remove(Node.builder("group.prisons-donator-warrior").build());
            switch (playerData.getPlayerRank()) {
                case "staticp":
                    user.data().add(Node.builder("group.prisons-donator-staticp").build());
                case "static":
                    user.data().add(Node.builder("group.prisons-donator-static").build());
                case "mythic":
                    user.data().add(Node.builder("group.prisons-donator-mythic").build());
                case "master":
                    user.data().add(Node.builder("group.prisons-donator-master").build());
                case "warrior":
                    user.data().add(Node.builder("group.prisons-donator-warrior").build());
            }
            StaticPrisons.luckPerms.getUserManager().saveUser(user);
        });
    }

    public static boolean writeToAFile(String filePath, List<String> linesToWrite, boolean append) {
        return writeToAFile(new File(filePath), linesToWrite, append);
    }
    public static boolean writeToAFile(File file, List<String> linesToWrite, boolean append) { //todo make this better, this is inefficient
        try {
            file.mkdirs();
            if (!file.exists()) file.createNewFile();
            StringBuilder contents = new StringBuilder();
            if (append) {
                Scanner scanner = new Scanner(file);
                while (scanner.hasNextLine()) {
                    contents.append(scanner.nextLine()).append("\n");
                }
                scanner.close();
            }
            for (String line : linesToWrite) contents.append(line).append("\n");
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(contents.toString());
            fileWriter.close();
        } catch (IOException ignore) {
            return false;
        }
        return true;
    }

    public static List<String> getAllLinesInAFile(String filePath) {
        List<String> contents = new ArrayList<>();
        try {
            File file = new File(filePath);
            if (!file.exists()) return contents;
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                contents.add(scanner.nextLine());
            }
            scanner.close();
        } catch (IOException ignore) {
        }
        return contents;
    }

    public static String getFileContents(String filePath) {
        StringBuilder contents = new StringBuilder();
        try {
            File file = new File(filePath);
            if (!file.exists()) return contents.toString();
            Scanner scanner = new Scanner(file);
            boolean firstLine = true;
            while (scanner.hasNextLine()) {
                if (!firstLine) contents.append("\n");
                contents.append(scanner.nextLine());
                firstLine = false;
            }
            scanner.close();
        } catch (IOException ignore) {
        }
        return contents.toString();
    }

    public static String addCommasToNumber(BigInteger value) {
        return NumberFormat.getNumberInstance(Locale.US).format(value);
    }
    public static String addCommasToNumber(int value) {
        return NumberFormat.getNumberInstance(Locale.US).format(value);
    }
    public static String addCommasToNumber(long value) {
        return NumberFormat.getNumberInstance(Locale.US).format(value);
    }
    public static String prettyNum(BigInteger num) {
        return prettyNum(num.toString());
    }
    public static String prettyNum(long num) {
        return prettyNum(num + "");
    }
    public static String prettyNum(int num) {
        return prettyNum(num + "");
    }


    private static final String[] abbreviations = {
            "K",
            "M",
            "B",
            "T",
            "Qd",
            "Qn",
            "Sx",
            "Sp",
            "O",
            "N",
            "D",
            "Ud",
            "Dd",
            "Td",
            "Qad",
            "Qid",
            "Sxd",
            "Spd",
            "Ocd",
            "Nod",
            "Vg",
            "Uvg",
    };
    public static String prettyNum(String num) {
        BigInteger number = new BigInteger(num);
        String[] nums = num.split("(?!^)");
        String prettyNum = "";
        String digit = "";
        if ((number.toString().length() / 3) - 1 > abbreviations.length) digit = "BIG";
        else if (((number.toString().length() - 1) / 3) - 1 >= 0) digit = abbreviations[((number.toString().length() - 1) / 3) - 1];
        if (number.compareTo(BigInteger.valueOf(999)) == 1) {
            switch (num.length() % 3) {
                case 1 -> prettyNum += nums[0] + "." + nums[1] + nums[2] + digit;
                case 2 -> prettyNum += nums[0] + nums[1] + "." + nums[2] + digit;
                default -> prettyNum += nums[0] + nums[1] + nums[2] + digit;
            }
        } else prettyNum = num;
        return prettyNum;
    }

    public static String getPrettyMaterialName(Material mat) {
        String name = capitalizeEachWord(mat.toString().replace("_", " "));
        name = ChatColor.RESET + "" + ChatColor.WHITE + name;
        return name;
    }

    private static String capitalizeEachWord(String words) {
        words = words.toLowerCase();
        return Stream.of(words.trim().split("\\s"))
                .filter(word -> word.length() > 0)
                .map(word -> word.substring(0, 1).toUpperCase() + word.substring(1))
                .collect(Collectors.joining(" "));
    }

    public static boolean checkIsPrisonPickaxe(ItemStack item) {
        if (item == null) return false;
        if (!item.getType().equals(Material.DIAMOND_PICKAXE)) return false;
        if (!item.hasItemMeta()) return false;
        return item.getItemMeta().getPersistentDataContainer().has(Constants.UUID_NAMESPACEKEY, PersistentDataType.STRING);
    }

    public static char getMineRankLetterFromMineRank(int mineRank) {
        if (mineRank > Constants.A_THROUGH_Z.length) return 'Z';
        return Constants.A_THROUGH_Z[mineRank];
    }

    public static ItemStack createNewPickaxe() {
        ItemStack item = new ItemStack(Material.DIAMOND_PICKAXE);
        PrisonPickaxe pickaxe = new PrisonPickaxe(item);
        ItemMeta meta = item.getItemMeta();
        meta = item.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addEnchant(Enchantment.DIG_SPEED, 100, true);
        item.setItemMeta(meta);
        pickaxe.setEnchantsLevel(PrisonEnchants.FORTUNE, 10);
        pickaxe.setEnchantsLevel(PrisonEnchants.DOUBLE_FORTUNE, 5);
        pickaxe.setEnchantsLevel(PrisonEnchants.TOKENATOR, 1);
        PrisonPickaxe.updateLore(item);
        return item;
    }

    public static <T> ArrayList<T> removeDuplicatesInArrayList(List<T> list) {
        ArrayList<T> newList = new ArrayList<T>();
        for (T element : list) {
            if (!newList.contains(element)) newList.add(element);
        }
        return newList;
    }

    //Create a method that can calculate compound intrest without using a for loop
    public static double compoundInterest(double principal, double interest, int years) {
        return principal * Math.pow(1 + interest, years);
    }

    public static class Items {
        public static ItemStack appendLoreToItem(ItemStack item, List<String> extraLore) {
            item = item.clone();
            List<String> mutableLore = new ArrayList<>(); //Make sure the list is mutable
            for (String line : extraLore) mutableLore.add(ChatColor.translateAlternateColorCodes('&', line));
            ItemMeta meta = item.getItemMeta();
            List<String> lore = new ArrayList<>();
            if (meta.hasLore()) lore = meta.getLore();
            lore.addAll(mutableLore);
            meta.setLore(lore);
            item.setItemMeta(meta);
            return item;
        }

        public static String getPrettyItemName(ItemStack item) {
            String name;
            if (!item.hasItemMeta()) {
                name = capitalizeEachWord(item.getType().toString().replace("_", " "));
                name = ChatColor.RESET + "" + ChatColor.WHITE + name;
            } else {
                if (!item.getItemMeta().hasDisplayName()) {
                    name = capitalizeEachWord(item.getType().toString().replace("_", " "));
                    name = ChatColor.RESET + "" + ChatColor.WHITE + name;
                } else {
                    name = item.getItemMeta().getDisplayName();
                }
            }
            return name;
        }
    }

    public static class Players implements Listener {
        private static JavaPlugin parent;
        private static long currentTick = 0;
        private static final int HOLDING_THRESHOLD = 5;
        public static void init(JavaPlugin parent) {
            Players.parent = parent;
            parent.getServer().getPluginManager().registerEvents(new Players(), parent);

            Bukkit.getScheduler().runTaskTimer(parent, () -> {
                for (Map.Entry<Player, RightClick> entry : new HashSet<>(playerHoldingClicks.entrySet())) {
                    Player player = entry.getKey();
                    RightClick rc = entry.getValue();
                    if (rc.lastUpdatedAt() + HOLDING_THRESHOLD >= currentTick) continue;
                    playerHoldingClicks.remove(player);
                }
            }, 20, 4); //The interact event gets called once every 4 ticks
        }

        //Right-click listener start
        public static final Map<Player, RightClick> playerHoldingClicks = new HashMap<>();

        public static void backpackFullCheck(boolean wasFullBefore, Player player, PlayerData playerData) {
            if (playerData.getBackpackIsFull()) {
                if (canAutoSell(playerData) && playerData.getIsAutoSellEnabled()) {
                    playerData.sellBackpack(player, true, ChatColor.translateAlternateColorCodes('&', "&a&lAuto Sell &8&l>> &f(x%MULTI%) Sold &b%TOTAL_BACKPACK_COUNT% &fblocks for: &a$%TOTAL_SELL_PRICE%"));
                } else if (!wasFullBefore) {
                    if (playerData.getIsAutoSellEnabled() && !canAutoSell(playerData)) playerData.setIsAutoSellEnabled(false);
                    if (player != null) {
                        player.sendTitle(ChatColor.RED + "" + ChatColor.BOLD + "Your Backpack", ChatColor.RED + "" + ChatColor.BOLD + "Is Full! (" + prettyNum(playerData.getBackpackSize()) + "/" + prettyNum(playerData.getBackpackSize()) + ")", 5, 40, 5);
                        player.sendMessage(ChatColor.RED + "Your backpack is full!");
                    }
                }
            }
        }

        private record RightClick(boolean isRightClicking, long heldFor, long lastUpdatedAt) {}
        @EventHandler
        void onInteract(PlayerInteractEvent e) {
            if (!e.getAction().isRightClick()) return;
            long heldFor = 0;
            if (playerHoldingClicks.containsKey(e.getPlayer())) {
                RightClick rc =  playerHoldingClicks.get(e.getPlayer());
                heldFor = rc.heldFor() + currentTick - rc.lastUpdatedAt();
            }
            playerHoldingClicks.put(e.getPlayer(), new RightClick(true, heldFor, currentTick));
        }
        @EventHandler(priority = EventPriority.HIGHEST)
        void tickStarted(ServerTickStartEvent e) {
            currentTick += 1;
        }

        public static boolean isHoldingRightClick(Player player) {
            if (!playerHoldingClicks.containsKey(player)) return false;
            return playerHoldingClicks.get(player).heldFor() > HOLDING_THRESHOLD;
        }
        //Right-click listener end

        public static ItemStack getSkull(OfflinePlayer player) {
            ItemStack skullItem = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta skullMeta = (SkullMeta) skullItem.getItemMeta();

            skullMeta.setOwningPlayer(player);
            skullItem.setItemMeta(skullMeta);

            return skullItem;
        }

        /**
         *
         * This method will add an item to a player's inventory and drop any extra on the ground if the inventory is full
         *
         * @param player Player whose inventory will be affected
         * @param items Item to add
         */
        public static void addToInventory(Player player, ItemStack items) {
            player.getInventory().addItem(items).forEach((slot, item) -> player.getWorld().dropItem(player.getLocation(), item));
        }

        /**
         * @return true if this player can auto sell; this will factor in ranks and booster status
         */
        public static boolean canAutoSell(PlayerData playerData) {
            if (playerData.getCanExplicitlyEnableAutoSell()) return true;
            return playerData.getPlayerRanks().contains("warrior") || playerData.getIsNitroBoosting();
        }

        public static boolean canAutoSell(Player player) {
            return canAutoSell(new PlayerData(player));
        }
    }

    public static class Commands {
        public static final String noPermissionsMessage = org.bukkit.ChatColor.RED + "You do not have permission to use this command!";
        public static final String commandCannotBeUsedInConsole = org.bukkit.ChatColor.AQUA + "This command cannot be run from the console!";
        public static String getCorrectUsage(String correctUsage) {
            return org.bukkit.ChatColor.BOLD + "" + org.bukkit.ChatColor.RED + "Incorrect command usage!\n" + org.bukkit.ChatColor.RESET + "" + org.bukkit.ChatColor.GRAY + correctUsage;
        }
        public static void logConsoleCannotUseThisCommand() {
            Bukkit.getLogger().log(Level.INFO, commandCannotBeUsedInConsole);
        }
    }
}
