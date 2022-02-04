package me.staticstudios.prisons.utils;

import me.staticstudios.prisons.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
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
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Utils {
    public static boolean writeToAFile(String filePath, List<String> linesToWrite, boolean append) {
        try {
            File file = new File(filePath);
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
    public static boolean writeToAFile(String filePath, String text) {
        try {
            File file = new File(filePath);
            if (!file.exists()) file.createNewFile();
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(text);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
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
        } catch (IOException ignore) {}
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
        } catch (IOException ignore) {}
        return contents.toString();
    }
    public static void copyFileStructure(File source, File target){
        try {
            ArrayList<String> ignore = new ArrayList<>(Arrays.asList("uid.dat", "session.lock"));
            if(!ignore.contains(source.getName())) {
                if(source.isDirectory()) {
                    if(!target.exists())
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

    public static ItemStack customSkull(OfflinePlayer player) {
        ItemStack skullItem = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skullMeta = (SkullMeta) skullItem.getItemMeta();

        skullMeta.setOwningPlayer(player);
        skullItem.setItemMeta(skullMeta);

        return skullItem;
    }
    public static int randomInt(int min, int max) {
        return min + (int)(Math.random() * ((max - min) + 1));
    }


    public static String addCommasToBigInteger(BigInteger value) {
        return NumberFormat.getNumberInstance(Locale.US).format(value);
    }
    public static String addCommasToInt(int value) {
        return NumberFormat.getNumberInstance(Locale.US).format(value);
    }
    public static String addCommasToLong(long value) {
        return NumberFormat.getNumberInstance(Locale.US).format(value);
    }
    public static String prettyNum(BigInteger num) {
        return  prettyNum(num.toString());
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
            } else {
                prettyNum += nums[0] + nums[1] + nums[2] + digit;
            }
        } else {
            prettyNum = num;
        }
        return prettyNum;
    }
    public static void addItemToPlayersInventoryAndDropExtra(Player player, ItemStack item) {
        int count = item.getAmount();
        item.setAmount(1);
        for (int i = 0; i < count; i++) {
            if (hasAvailableSlot(player, item)) {
                player.getInventory().addItem(item);
            } else player.getWorld().dropItem(player.getLocation(), item);
        }
    }
    static boolean hasAvailableSlot(Player player, ItemStack itemToAdd){
        Inventory inv = player.getInventory();
        for (ItemStack item: inv.getStorageContents()) {
            if(item == null) {
                return true;
            }
        }
        for (ItemStack item: inv.getStorageContents()) {
            ItemStack _item = item.clone();
            _item.setAmount(1);
            if(item.getAmount() != 64 && _item.equals(itemToAdd)) {
                return true;
            }
        }
        return false;
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
        return item.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(Main.getMain(), "pickaxeUUID"), PersistentDataType.STRING);
    }

    public static char getMineRankLetterFromMineRank(int mineRank) {
        if (mineRank > StaticVars.A_THROUGH_Z.length) return 'Z';
        return StaticVars.A_THROUGH_Z[mineRank];
    }

    public static ItemStack createNewPickaxe() {
        ItemStack item = new ItemStack(Material.DIAMOND_PICKAXE);
        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(new NamespacedKey(Main.getMain(), "pickaxeUUID"), PersistentDataType.STRING, UUID.randomUUID().toString());
        item.setItemMeta(meta);
        PrisonPickaxe.addLevel(item, 0);
        PrisonPickaxe.addXP(item, 0);
        PrisonPickaxe.addBlocksMined(item, 0);
        meta = item.getItemMeta();
        List<String> lore = meta.getLore();
        lore.add(ChatColor.GRAY + "---------------");
        lore.add("");
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addEnchant(Enchantment.DIG_SPEED, 100, true);
        item.setItemMeta(meta);
        return item;
    }

    public static <T> ArrayList<T> removeDuplicatesInArrayList(List<T> list) {
        ArrayList<T> newList = new ArrayList<T>();
        for (T element : list) {
            if (!newList.contains(element)) newList.add(element);
        }
        return newList;
    }

    public static ItemStack findPickaxeInInventoryFromUUID(Player player, String pickaxeUUID) {
        for (ItemStack item : player.getInventory().getContents()) {
            if (item == null) continue;
            if (!checkIsPrisonPickaxe(item)) continue;
            if (item.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(Main.getMain(), "pickaxeUUID"), PersistentDataType.STRING).equals(pickaxeUUID)) {
                return item;
            }
        }
        return null;
    }
}
