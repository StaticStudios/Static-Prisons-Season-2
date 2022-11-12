package net.staticstudios.prisons.commands.test;

import net.staticstudios.prisons.backpacks.BackpackManager;
import net.staticstudios.prisons.customitems.CustomItems;
import net.staticstudios.prisons.customitems.vouchers.KitVouchers;
import net.staticstudios.prisons.pickaxe.PrisonPickaxe;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class TestCommand implements CommandExecutor {

    //noah was here
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        Player player = null;
        if (commandSender instanceof Player) {
            player = (Player) commandSender;
        }
        //AuctionHouseMenus.openMenu(player, 0);
//        player.sendMessage(
//                new WeightedElements<String>()
//                        .add("10%", 10)
//                        .add("20%", 20)
//                        .add("20% again", 20)
//                        .add("50%", 50)
//                        .getRandom()
//        );
//        long start = System.currentTimeMillis();
//        for (int x = 0; x < 100; x++) {
//            for (int i = 0; i < 10000; i++) {
//                player.sendBlockChange(player.getLocation(), Bukkit.createBlockData(Material.STONE));
//            }
//        }
//        player.sendMessage("Total time taken: " + (System.currentTimeMillis() - start) + "ms");
        //Crates.COMMON.preview(player);
        //Cell.createCell(player);
//        PrivateMine.getPrivateMineFromPlayer(player).thenAccept(pm -> pm.setXp(Integer.parseInt(args[0]), true));
//        for (int i = 0; i < 100; i++)
//            player.sendMessage(PrisonUtils.prettyNum(PrivateMine.getLevelRequirement(i)) + " | Level: " + i);
        //SettingsMenus.open(player, true);
//        Player finalPlayer = player;
//        TickUtils.init(StaticPrisons.getInstance());
//        Bukkit.getScheduler().runTaskTimer(StaticPrisons.getInstance(), () -> {
//            finalPlayer.sendMessage(TickUtils.getMSPT(20) + "ms | " + TickUtils.getTPS() + "tps");
//        }, 0, 1);
//        PlayerData playerData = new PlayerData(player);
//        long start = System.currentTimeMillis();
//        for (int i = 0; i < 10000; i++) {
//            playerData.setChatTag1("dev");
//        }
//        player.sendMessage("Total time taken: " + (System.currentTimeMillis() - start) + "ms");
        //EventManager.runWordUnscramble();
//        player.spigot().send(TitlePart.TITLE, "");
//        player.showTitle(Title.title(Component.text("&a&lHello"), Component.text("&b&lWorld"), Title.Times.of(Duration.ofMillis(500), Duration.ofMillis(2000), Duration.ofMillis(500))));
//        ChatEvents.runEvent(ChatEvents.EventType.MATH);
//

//        Trade.test(player);

//        FileConfiguration current = YamlConfiguration.loadConfiguration(new File("current.yml"));
//        FileConfiguration recent = YamlConfiguration.loadConfiguration(new File("recent.yml"));
//
//        for (String k : recent.getKeys(false)) {
//            if (!k.endsWith("-votes")) continue;
//            if (!k.startsWith("PLAYERS")) continue;
//            current.set(k, recent.get(k));
//        }
//        try {
//            current.save(new File("current.yml"));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

//        Bukkit.getScheduler().getActiveWorkers();

//        System.out.println("test");
//        for (int i = 0; i < 1000; i++) {
//            PrisonPickaxe p = PrisonPickaxe.fromItem(PrisonUtils.createNewPickaxe());
//            p.setXp(1);
//            p.setBlocksBroken(1);
//            PrisonUtils.Players.addToInventory(player, p.item);
//        }
        //I added a thing!
//        for (int i = Integer.parseInt(args[0]) - 50; i < Integer.parseInt(args[0]); i++) {
//            player.sendMessage(PrisonUtils.addCommasToNumber(PrivateMine.getLevelRequirement(i)) + " | Level: " + i);
//        }

//        Location location = player.getLocation();
//        if (mineBomb.positions.isEmpty()) mineBomb.computePositions();
//        for (int i = 1; i < 30; i++) {
//            int finalI = i;
//            Bukkit.getScheduler().runTaskLater(StaticPrisons.getInstance(), () -> {
//                StaticMine mine = StaticMine.fromLocation(location);
//                mineBomb.explodeAtComputedPositions(mine, location.add(0, -finalI, 0));
//                mine.removeBlocksBrokenInMine(mineBomb.blocksChanged);
//                System.out.println(mine.blocksInMine + " blocks in mine | " + mine.blocksInFullMine + " blocks when full | " + mineBomb.blocksChanged + " blocks broken");
//                System.out.println(mineBomb.positions.size());
//            }, i * 3);
//        }

//        PrisonPickaxe.fromItem(player.getInventory().getItemInMainHand()).setAbilityLevel(PickaxeAbilities.LIGHTNING_STRIKE, 0);
//        PrisonPickaxe.fromItem(player.getInventory().getItemInMainHand()).setAbilityLevel(PickaxeAbilities.SNOW_FALL, 0);
//        PrisonPickaxe.fromItem(player.getInventory().getItemInMainHand()).setAbilityLevel(PickaxeAbilities.METEOR_STRIKE, 0);
//        PrisonPickaxe.fromItem(player.getInventory().getItemInMainHand()).setAbilityLevel(PickaxeAbilities.BEAM_OF_LIGHT, 0);
//        PrisonPickaxe.fromItem(player.getInventory().getItemInMainHand()).setAbilityLevel(PickaxeAbilities.BLACK_HOLE, 0);
//        PrisonPickaxe.fromItem(player.getInventory().getItemInMainHand()).setLastActivatedAbilityAt(PickaxeAbilities.LIGHTNING_STRIKE, 0);
//        PrisonPickaxe.fromItem(player.getInventory().getItemInMainHand()).setLastActivatedAbilityAt(PickaxeAbilities.SNOW_FALL, 0);
//        PrisonPickaxe.fromItem(player.getInventory().getItemInMainHand()).setLastActivatedAbilityAt(PickaxeAbilities.METEOR_STRIKE, 0);
//        PrisonPickaxe.fromItem(player.getInventory().getItemInMainHand()).setLastActivatedAbilityAt(PickaxeAbilities.BEAM_OF_LIGHT, 0);
//        PrisonPickaxe.fromItem(player.getInventory().getItemInMainHand()).setLastActivatedAbilityAt(PickaxeAbilities.BLACK_HOLE, 0);

//        int prestigesToBuy = 10;
//        BigInteger price = BigInteger.valueOf(5000000000L); //10B
//        BigInteger currentPrestige = BigInteger.valueOf(10);
//        BigInteger totalRankupPrice = BigInteger.valueOf(0);
//        for (int i = 0 + 1; i <= 25; i++) {
//            totalRankupPrice = totalRankupPrice.add(LevelUp.getBaseRankUpPriceForRank(i));
//        }
//        System.out.println(totalRankupPrice);
//            //The player should pay the price of the rank-ups for all the prestiges (besides the first) they are buying
//            for (int i = 0; i < prestigesToBuy; i++) {
//                BigInteger p = BigInteger.ZERO;
//                for (int r = 1; r <= 25; r++) {
//                    p = p.add(LevelUp.getBaseRankUpPriceForRank(r));
//                }
//                p = p.add(
//                        p.multiply(currentPrestige).divide(BigInteger.valueOf(3))
//                );
//                p = p.add(price.multiply(BigInteger.valueOf(prestigesToBuy)));
//                currentPrestige = currentPrestige.add(BigInteger.valueOf(1));
//                System.out.println(p);
//            }

//        PickaxeAbilities.LIGHTNING_STRIKE.beginActivation(player, PrisonPickaxe.fromItem(player.getInventory().getItemInMainHand()));
//        new PlayerData(player).setLastUsedPickaxeAbility(0);
//        PrisonBackpack backpack = PrisonBackpacks.createBackpack(1);


//        player.getInventory().addItem(new TokenLootBox(1).getItem());
//        player.getInventory().addItem(new TokenLootBox(2).getItem());
//        player.getInventory().addItem(new TokenLootBox(3).getItem());
//        player.getInventory().addItem(new TokenLootBox(4).getItem());
//        player.getInventory().addItem(new TokenLootBox(5).getItem());
//        player.getInventory().addItem(new TokenLootBox(6).getItem());
//        player.getInventory().addItem(new TokenLootBox(7).getItem());


//        List<Component> exampleLore = List.of(
//                Component.text("hi"),
//                Component.text("hi"),
//                Component.text("hi")
//        );
//        ItemStack item = new ItemStack(Material.BLAZE_POWDER);
//        ItemMeta meta = item.getItemMeta();
//        Bukkit.getScheduler().runTaskAsynchronously(StaticPrisons.getInstance(), () -> {
//            for (int i = 0; i < 10000; i++) {
//                meta.lore(exampleLore);
//            }
//        });
//
//        long start = System.currentTimeMillis();
//
//        player.sendMessage("Apply to meta: " + (System.currentTimeMillis() - start));
//
//        start = System.currentTimeMillis();
//        for (int i = 0; i < 10000; i++) {
//           item.setItemMeta(meta);
//        }
//        player.sendMessage("Apply meta to item: " + (System.currentTimeMillis() - start));
//
//        String texture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGQzMTZhNjQwOTlkODk2ZjY2OWQwZjA4ODUyMDIxN2E4M2RlY2Q0YTNiNjdlNTdhZjg5YjMzZDIwYzMyMWYzNCJ9fX0=";
//        player.getInventory().addItem(SkullCreator.itemFromBase64(texture));

//        player.getInventory().addItem(new MoneyLootBox(1).getItem());
//        player.getInventory().addItem(new MoneyLootBox(2).getItem());
//        player.getInventory().addItem(new MoneyLootBox(3).getItem());
//        player.getInventory().addItem(new MoneyLootBox(4).getItem());
//        player.getInventory().addItem(new MoneyLootBox(5).getItem());
//
//
//
//        player.getInventory().addItem(new TokenLootBox(1).getItem());
//        player.getInventory().addItem(new TokenLootBox(2).getItem());
//        player.getInventory().addItem(new TokenLootBox(3).getItem());
//        player.getInventory().addItem(new TokenLootBox(4).getItem());
//        player.getInventory().addItem(new TokenLootBox(5).getItem());
//        player.getInventory().addItem(new TokenLootBox(6).getItem());
//        player.getInventory().addItem(new TokenLootBox(7).getItem());
//        player.getInventory().addItem(new TokenLootBox(8).getItem());
//        player.getInventory().addItem(new TokenLootBox(9).getItem());
//        player.getInventory().addItem(new TokenLootBox(10).getItem());


//        System.out.println(calc(1_000_000) + " 500K");
//        System.out.println(calc(500_000) + " 250K");
//        System.out.println(calc(2_000_000) + " 1M");
//        System.out.println(calc(5_000_000) + " 2.5M");
//        System.out.println(calc(10_000_000) + " 5M");
//        System.out.println(calc(15_000_000) + " 7.5M");


//        player.getInventory().addItem(ItemUtils.createCustomSkull(LootBoxType.PICKAXE.base64Texture));
//        player.getInventory().addItem(ItemUtils.createCustomSkull(LootBoxType.MINE_BOMB.base64Texture));
//        PrivateMine mine = PrivateMine.getPrivateMineFromPlayerWithoutLoading(player);
//        mine.setXpAndCalcLevel(Long.parseLong(args[0]));


//        TeamPrefix.init();

//        ArmorStand a = (ArmorStand) player.getWorld().spawnEntity(player.getLocation(), EntityType.ARMOR_STAND);
//        long start = System.currentTimeMillis();
//        for (int i = 0; i < 10_000; i++) {
//            a.teleport(a.getLocation());
//        }
//        player.sendMessage("Time: " + (System.currentTimeMillis() - start));
//        a.setAI(false);
//        a.setCanTick(false);
//        a.setCanMove(false);
//        start = System.currentTimeMillis();
//        for (int i = 0; i < 10_000; i++) {
//            a.teleport(a.getLocation());
//        }
//        player.sendMessage("Time: " + (System.currentTimeMillis() - start));
//        a.remove();

//        ArmorStand a = null;
//        a.setVelocity();

//
//        player.getInventory().addItem(new DefaultFishingRod().getItem());

//        new FortuneEnchant();
//        PrisonPickaxe pickaxe = PrisonPickaxe.fromItem(player.getInventory().getItemInMainHand());
//        pickaxe.setEnchantment(FortuneEnchant.class, 10);
//        pickaxe.setEnchantment(TokenatorEnchant.class, Enchantable.getEnchant(TokenatorEnchant.class).getMaxLevel());

//        player.getInventory().addItem(new PrisonFishingRod().getItem());

//        StaticPrisons.getPlugin()

//        player.getInventory().addItem(KitVouchers.WEAPONS.getItem(player));
//        player.getInventory().addItem(KitVouchers.POTIONS.getItem(player));
//        for (int i = 1; i <= 6; i++) {
//            player.getInventory().addItem(CustomItems.getItem("voucher-kit_tier_" + i, null));
//        }

//        ItemStack item = BackpackManager.createBackpack(2).getItem();
//        item.editMeta(meta -> {
//            meta.setCustomModelData(1);
//        });
//        player.getInventory().addItem(item);
        return false;



    }

    static long calc(long xp) {
        long level = 0;
        while (xp >= PrisonPickaxe.getLevelRequirement(level)) level++;
        return level;
    }


}
