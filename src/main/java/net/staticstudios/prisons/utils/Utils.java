package net.staticstudios.prisons.utils;

import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.enchants.handler.PrisonEnchants;
import net.staticstudios.prisons.enchants.handler.PrisonPickaxe;
import net.staticstudios.prisons.data.dataHandling.PlayerData;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import org.bukkit.*;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;

import java.io.*;
import java.math.BigInteger;
import java.text.NumberFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class Utils {
    private static final Random random = new Random();
    /*
    public static void checkIfPlayerHasJoinedBefore(Player player) {
        if (!new ServerData().checkIfPlayerHasJoinedBeforeByUUID(player.getUniqueId().toString())) {
            Bukkit.broadcastMessage(org.bukkit.ChatColor.LIGHT_PURPLE + player.getName() + org.bukkit.ChatColor.GREEN + " joined for the first time! " + org.bukkit.ChatColor.GRAY + "(" + "#" + Utils.addCommasToNumber(new ServerData().getPlayerUUIDsToNamesMap().size() + 1) + ")");
            Utils.addItemToPlayersInventoryAndDropExtra(player, Utils.createNewPickaxe());
            PlayerData playerData = new PlayerData(player);
            playerData.setPlayerRank("member");
            playerData.setStaffRank("member");
        }
    }

     */

    public static String formatSecondsToTime(long seconds) {
        int sec = (int) (seconds % 60);
        int min = (int) (seconds % 3600 / 60);
        int hour = (int) (seconds % 216000 / 3600);
        int day = (int) (seconds % 5184000 / 216000);
        int month = (int) (seconds % 155520000 / 5184000);
        StringBuilder formatted = new StringBuilder();
        if (month > 0) formatted.append(month).append(" month(s), ");
        if (day > 0) formatted.append(day).append(" day(s), ");
        if (hour > 0) formatted.append(hour).append(" hour(s), ");
        if (min > 0) formatted.append(min).append(" minute(s), ");
        formatted.append(sec).append(" second(s)");
        return formatted.toString();
    }

    public static ItemStack applyLoreToItem(ItemStack item, List<String> lore) {
        for (int i = 0; i < lore.size(); i++) lore.set(i, ChatColor.translateAlternateColorCodes('&', lore.get(i)));
        ItemMeta meta = item.getItemMeta();
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack getItemInMainHand(Player player) {
        return player.getInventory().getItemInMainHand();
    }

    public static int randomInt(int min, int max) {
        return random.nextInt((max - min) + 1) + min;
    }

    public static float randomFloat(float min, float max) {
        return random.nextFloat((max - min) + 1) + min;
    }

    public static double randomDouble(double min, double max) {
        return random.nextDouble((max - min) + 1) + min;
    }

    public static BigInteger randomBigInt(BigInteger min, BigInteger max) {
        BigInteger bigInteger = max.subtract(min);
        BigInteger res = new BigInteger(max.bitLength(), random);
        if (res.compareTo(min) < 0) res = res.add(min);
        if (res.compareTo(bigInteger) >= 0) res = res.mod(bigInteger).add(min);
        return res;
    }

    public static ItemStack setItemCount(ItemStack itemStack, int amount) {
        itemStack = new ItemStack(itemStack);
        itemStack.setAmount(amount);
        return itemStack;
    }

    public static Location calcMinPoint(Location loc1, Location loc2) {
        if (!Objects.equals(loc1.getWorld(), loc2.getWorld())) {
            throw new IllegalArgumentException("Points must be in the same world");
        } else {
            double minX = Math.min(loc1.getX(), loc2.getX());
            double minY = Math.min(loc1.getY(), loc2.getY());
            double minZ = Math.min(loc1.getZ(), loc2.getZ());
            return new Location(loc1.getWorld(), minX, minY, minZ);
        }
    }

    public static Location calcMaxPoint(Location loc1, Location loc2) {
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
        try {
            File file = new File(filePath);
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
            for (String line : linesToWrite) {
                contents.append(line).append("\n");
            }
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(contents.toString());
            fileWriter.close();
        } catch (IOException ignore) {
            return false;
        }
        return true;
    }

    public static void writeToAFile(String filePath, String text) {
        try {
            File file = new File(filePath);
            if (!file.exists()) file.createNewFile();
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(text);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public static void copyFileStructure(File source, File target) {
        try {
            ArrayList<String> ignore = new ArrayList<>(Arrays.asList("uid.dat", "session.lock"));
            if (!ignore.contains(source.getName())) {
                if (source.isDirectory()) {
                    if (!target.exists())
                        if (!target.mkdirs())
                            throw new IOException("Couldn't create world directory!");
                    String files[] = source.list();
                    for (String file : files) {
                        File srcFile = new File(source, file);
                        File destFile = new File(target, file);
                        copyFileStructure(srcFile, destFile);
                    }
                } else {
                    InputStream in = new FileInputStream(source);
                    OutputStream out = new FileOutputStream(target);
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = in.read(buffer)) > 0)
                        out.write(buffer, 0, length);
                    in.close();
                    out.close();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
        return prettyNum(BigInteger.valueOf(num));
    }

    public static String prettyNum(int num) {
        return prettyNum(BigInteger.valueOf(num));
    }

    public static String prettyNum(String num) {
        List<String> abbreviations = new ArrayList<>();
        abbreviations.add("K");
        abbreviations.add("M");
        abbreviations.add("B");
        abbreviations.add("T");
        abbreviations.add("Qd");
        abbreviations.add("Qn");
        abbreviations.add("Sx");
        abbreviations.add("Sp");
        abbreviations.add("O");
        abbreviations.add("N");
        abbreviations.add("D");
        abbreviations.add("Ud");
        abbreviations.add("Dd");
        abbreviations.add("Td");
        abbreviations.add("Qad");
        abbreviations.add("Qid");
        abbreviations.add("Sxd");
        abbreviations.add("Spd");
        abbreviations.add("Ocd");
        abbreviations.add("Nod");
        abbreviations.add("Vg");
        abbreviations.add("Uvg");
        BigInteger number = new BigInteger(num);
        String[] nums = num.split("(?!^)");
        String prettyNum = "";
        String digit = "";
        if ((number.toString().length() / 3) - 1 > abbreviations.size()) {
            digit = "BIG";
        } else {
            if (((number.toString().length() - 1) / 3) - 1 >= 0) {
                digit = abbreviations.get(((number.toString().length() - 1) / 3) - 1);
            }
        }
        if (number.compareTo(BigInteger.valueOf(999)) == 1) {
            if (num.length() % 3 == 1) {
                prettyNum += nums[0] + "." + nums[1] + nums[2] + digit;
            } else if (num.length() % 3 == 2) {
                prettyNum += nums[0] + nums[1] + "." + nums[2] + digit;
            } else prettyNum += nums[0] + nums[1] + nums[2] + digit;
        } else prettyNum = num;
        return prettyNum;
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

    public static class Players {

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
         * @param _item Item to add
         */
        public static void addToInventory(Player player, ItemStack _item) {
            ItemStack item = new ItemStack(_item); //prevent decrementing stack counts
            int count = item.getAmount();
            item.setAmount(1);
            for (int i = 0; i < count; i++) {
                if (hasAvailableSlot(player, item)) {
                    player.getInventory().addItem(item);
                } else player.getWorld().dropItem(player.getLocation(), item);
            }
        }

        static boolean hasAvailableSlot(Player player, ItemStack itemToAdd) {
            Inventory inv = player.getInventory();
            for (ItemStack item : inv.getStorageContents()) {
                if (item == null) {
                    return true;
                }
            }
            for (ItemStack item : inv.getStorageContents()) {
                ItemStack _item = item.clone();
                _item.setAmount(1);
                if (item.getAmount() != 64 && _item.equals(itemToAdd)) {
                    return true;
                }
            }
            return false;
        }

        /*
        public static ItemStack findPickaxeInInventoryFromUUID(Player player, String pickaxeUUID) {
            for (ItemStack item : player.getInventory().getContents()) {
                if (item == null) continue;
                if (!checkIsPrisonPickaxe(item)) continue;
                if (item.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(StaticPrisons.getInstance(), "pickaxeUUID"), PersistentDataType.STRING).equals(pickaxeUUID)) {
                    return item;
                }
            }
            return null;
        }

         */

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

    //create a method to format time in miliseconds to a string that will list out the time in days, hours, minutes, and seconds
    public static String formatTime(long time) {
        long days = time / 86400000;
        time -= days * 86400000;
        long hours = time / 3600000;
        time -= hours * 3600000;
        long minutes = time / 60000;
        time -= minutes * 60000;
        long seconds = time / 1000;
        time -= seconds * 1000;
        long milliseconds = time;
        StringBuilder sb = new StringBuilder();
        if (days > 0) {
            sb.append(days).append("d ");
        }
        if (hours > 0) {
            sb.append(hours).append("h ");
        }
        if (minutes > 0) {
            sb.append(minutes).append("m ");
        }
        if (seconds > 0) {
            sb.append(seconds).append("s ");
        }
        if (milliseconds > 0) {
            sb.append(milliseconds).append("ms");
        }
        return sb.toString();
    }

    public static class CommandUtils {
        public static final String noPermissionsMessage = org.bukkit.ChatColor.RED + "You do not have permission to use this command!";
        public static final String commandCannotBeUsedInConsole = org.bukkit.ChatColor.AQUA + "This command cannot be run from the console!";
        public static String getIncorrectCommandUsageMessage(String correctUsage) {
            return org.bukkit.ChatColor.BOLD + "" + org.bukkit.ChatColor.RED + "Incorrect command usage!\n" + org.bukkit.ChatColor.RESET + "" + org.bukkit.ChatColor.GRAY + correctUsage;
        }
        public static void logConsoleCannotUseThisCommand() {
            Bukkit.getLogger().log(Level.INFO, commandCannotBeUsedInConsole);
        }
    }
}
