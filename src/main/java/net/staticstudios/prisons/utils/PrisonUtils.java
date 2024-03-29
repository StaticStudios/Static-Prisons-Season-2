package net.staticstudios.prisons.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import net.md_5.bungee.api.ChatColor;
import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.data.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.text.NumberFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class PrisonUtils {
    public static void init() {
        PlayerUtils.init(StaticPrisons.getInstance());
    }

    public static final MiniMessage miniMessage = MiniMessage.builder().tags(TagResolver.resolver(
            TagResolver.standard(),
            TagResolver.resolver("light_gray", Tag.styling(style -> style.color(ComponentUtil.LIGHT_GRAY))),
            TagResolver.resolver("light_grey", Tag.styling(style -> style.color(ComponentUtil.LIGHT_GRAY)))
    )).build();

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

    public static long randomLong(long min, long max) {
        return new Random().nextLong((max - min) + 1) + min;
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
        else if (((number.toString().length() - 1) / 3) - 1 >= 0)
            digit = abbreviations[((number.toString().length() - 1) / 3) - 1];
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

    static String capitalizeEachWord(String words) {
        words = words.toLowerCase();
        return Stream.of(words.trim().split("\\s"))
                .filter(word -> word.length() > 0)
                .map(word -> word.substring(0, 1).toUpperCase() + word.substring(1))
                .collect(Collectors.joining(" "));
    }

    public static char getMineRankLetterFromMineRank(int mineRank) {
        if (mineRank > 25) return 'Z';
        return (char) (mineRank + 65);
    }

    public static <T> ArrayList<T> removeDuplicatesInArrayList(List<T> list) {
        ArrayList<T> newList = new ArrayList<T>();
        for (T element : list) {
            if (!newList.contains(element)) {
                newList.add(element);
            }
        }
        return newList;
    }

    //Create a method that can calculate compound intrest without using a for loop
    public static double compoundInterest(double principal, double interest, int years) {
        return principal * Math.pow(1 + interest, years);
    }

    public static void saveConfig(YamlConfiguration config, String name) {
        try {
            config.save(new File(StaticPrisons.getInstance().getDataFolder(), name));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean isValidComponent(Component toCheck) {
        if (toCheck == null) {
            return false;
        }

        return PlainTextComponentSerializer.plainText().serialize(toCheck).isEmpty();
    }


}
