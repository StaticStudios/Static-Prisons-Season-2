package net.staticstudios.prisons.commands.test;

import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.data.serverData.ServerData;
import net.staticstudios.prisons.privateMines.PrivateMine;
import net.staticstudios.prisons.utils.PrisonUtils;
import net.staticstudios.utils.WeightedElements;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class TestCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        Player player = null;
        if (commandSender instanceof Player) {
            player = (Player) commandSender;
        }
        //AuctionHouseMenus.openMenu(player, 0);
        player.sendMessage(
                new WeightedElements<String>()
                        .add("10%", 10)
                        .add("20%", 20)
                        .add("20% again", 20)
                        .add("50%", 50)
                        .getRandom()
        );
//        long start = System.currentTimeMillis();
//        for (int x = 0; x < 100; x++) {
//            for (int i = 0; i < 10000; i++) {
//                player.sendBlockChange(player.getLocation(), Bukkit.createBlockData(Material.STONE));
//            }
//        }
//        player.sendMessage("Total time taken: " + (System.currentTimeMillis() - start) + "ms");
        //Crates.COMMON.preview(player);
        //Cell.createCell(player);
        PrivateMine.getPrivateMineFromPlayer(player).thenAccept(pm -> pm.setXp(Integer.parseInt(args[0]), true));
        for (int i = 0; i < 100; i++)
            player.sendMessage(PrisonUtils.prettyNum(PrivateMine.getLevelRequirement(i)) + " | Level: " + i);
        //SettingsMenus.open(player, true);
//        Player finalPlayer = player;
//        TickUtils.init(StaticPrisons.getInstance());
//        Bukkit.getScheduler().runTaskTimer(StaticPrisons.getInstance(), () -> {
//            finalPlayer.sendMessage(TickUtils.getMSPT(20) + "ms | " + TickUtils.getTPS() + "tps");
//        }, 0, 1);
        PlayerData playerData = new PlayerData(player);
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
//        String[] arr = new String[]{
//                "ebe0ca46-abad-4c59-8324-a1f3f4e141d9 |?? SPACECATIZZY |?? warrior",
//                "00000000-0000-0000-0009-01f74206ce64 |?? *SmartFlea9 |?? warrior",
//                "48fe82ff-eab2-477f-bb05-9515f2b915c7 |?? Clashbrothers08 |?? master",
//                "00000000-0000-0000-0009-01f7dd4fabdd |?? *GothAngel167701 |?? warrior",
//                "66c597a6-a57c-4554-be20-5ce6f839665c |?? zREDPandaz4lief |?? warrior",
//                "00000000-0000-0000-0009-01ff65e64299 |?? *SadCarrot908912 |?? warrior",
//                "00000000-0000-0000-0009-01fb547afc53 |?? *MXNANY2 |?? warrior",
//                "fdcef9f5-87e2-4330-8f48-77cb0fba3b2e |?? brazengamer |?? warrior",
//                "00000000-0000-0000-0009-01f518b73294 |?? *I3I3I3I3I |?? warrior",
//                "00000000-0000-0000-0009-01fc2b960c70 |?? *Prontomamba2790 |?? warrior",
//                "00000000-0000-0000-0009-01f81169b964 |?? *MissPots4061 |?? warrior",
//                "57674706-4a05-448b-adf1-e1778e6e76bc |?? Branfurd |?? warrior",
//                "248f4204-1fb7-423e-b5ce-04a124c0dc95 |?? fightfordeath |?? warrior",
//                "00000000-0000-0000-0009-01f78b27515b |?? *AilingCoder7761 |?? master",
//                "c4530694-1b98-44d1-985a-5a70fe966996 |?? wnxbobo |?? master",
//                "c86194fb-a575-47d3-b046-f2bbf5a6a00b |?? azzatheslayer |?? master",
//                "00000000-0000-0000-0009-01fe58f489a1 |?? *BEANBURRlTO |?? master",
//                "b8e55835-0282-42a8-94e1-6560210ef99c |?? Denfen |?? master",
//                "00000000-0000-0000-0009-01f1cb6603fa |?? *a1frostbite0100 |?? master",
//                "f0515161-be02-422c-ae2f-343b0aad6130 |?? Kareneric |?? master",
//                "45d20e5f-adff-4af3-ac82-0036e44dd986 |?? GalaxyGaming09 |?? master",
//                "00000000-0000-0000-0009-01f022dff456 |?? *ToggledAuraL |?? master",
//                "3d2daf1a-3c94-437f-aee2-d5e36cec2ed2 |?? SugarFreeb |?? master",
//                "00000000-0000-0000-0009-00000b7fcaee |?? *Dz_On_60hz |?? master",
//                "00000000-0000-0000-0009-0000043b1bdb |?? *vT_Lazy |?? master",
//                "bd10bba9-cd5c-4e63-be5e-ec23a901a93e |?? ToggledAuraL |?? master",
//                "00000000-0000-0000-0009-01f82ed4e325 |?? *LovelyGoober |?? master",
//                "9567dbb6-fec1-4e9c-9cd3-ff8b9a9cd21a |?? Whaler_ |?? master",
//                "128ba827-0d59-42ca-a836-969d3b1acd9b |?? RuthlessDevin |?? master",
//                "539748c3-6bc2-4354-9fb9-c90bf629da96 |?? ImAddiction |?? mythic",
//                "00000000-0000-0000-0009-01f0749783bc |?? *AmTrash3 |?? mythic",
//                "21398574-6c4e-486f-9798-455aea06157a |?? PuzsnBoots |?? mythic",
//                "72d3b996-7212-44de-a17f-5c1d66a1817d |?? itsmeKanna |?? mythic",
//                "00000000-0000-0000-0009-01fb51cb34d9 |?? *XxCrazykid950xX |?? mythic",
//                "00000000-0000-0000-0009-000005ac632b |?? *l_am_Cj |?? mythic",
//                "bd10bba9-cd5c-4e63-be5e-ec23a901a93e |?? ToggledAuraL |?? mythic",
//                "00000000-0000-0000-0009-01f82ed4e325 |?? *LovelyGoober |?? mythic",
//                "128ba827-0d59-42ca-a836-969d3b1acd9b |?? RuthlessDevin |?? mythic",
//                "128ba827-0d59-42ca-a836-969d3b1acd9b |?? RuthlessDevin |?? static",
//                "049b884b-4023-4d6c-a69f-4a8f688edbfc |?? LilChead |?? static",
//                "00000000-0000-0000-0009-01f518b73294 |?? *I3I3I3I3I |?? static",
//                "750230fb-659d-4bc5-b57e-bb9e567d7207 |?? deadsniper52627 |?? static",
//                "00000000-0000-0000-0009-01fa761f7133 |?? *xAlphaBloodShot |?? static",
//                "00000000-0000-0000-0009-01fb718962a3 |?? Imtheunknown121 |?? staticp",
//                "991c3b42-ce5d-4b18-a953-bbb10a7ea4d0 |?? AJRP2 |?? staticp",
//                "00000000-0000-0000-0009-01f78b27515b |?? *AilingCoder7761 |?? staticp",
//                "00000000-0000-0000-0009-01fbdcabfcc5 |?? *chancereed20 |?? staticp",
//                "00000000-0000-0000-0009-01ffcf603a73 |?? *Her0brineG0D |?? staticp",
//                "21398574-6c4e-486f-9798-455aea06157a |?? PuzsnBoots |?? staticp",
//                "0b1a449b-34f0-4a3c-a408-20fa19261b05 |?? Boweeena |?? staticp",
//                "00000000-0000-0000-0009-01ffec4fb4ca |?? *ItsPrkBro666 |?? staticp",
//                "320308a6-c300-4070-bec4-a59d2b834615 |?? BigLemon96 |?? staticp",
//                "00000000-0000-0000-0009-01fa5c31e44b |?? Bul13tpr00f5180 |?? mythic",
//                "00000000-0000-0000-0009-01f441daf552 |?? *IrelandVPN |?? master",
//                "45989be3-2a42-4a86-a868-ec44247a7133 |?? Sammster10 |?? static",
//                "45989be3-2a42-4a86-a868-ec44247a7133 |?? Sammster10 |?? tag-lgbtq",
//                "0c92cb97-028e-4b27-821c-be4629b9df3a |?? BLXXDSPORT |?? staticp",
//                "0c92cb97-028e-4b27-821c-be4629b9df3a |?? BLXXDSPORT |?? tag-banned",
//                "00000000-0000-0000-0009-01f9ae5dd437 |?? *CanICry9626 |?? tag-trash",
//                "00000000-0000-0000-0009-01f9ae5dd437 |?? *CanICry9626 |?? tag-banned",
//                "00000000-0000-0000-0009-01f9ae5dd437 |?? *CanICry9626 |?? tag-thiccc",
//                "00000000-0000-0000-0009-01f9ae5dd437 |?? *CanICry9626 |?? tag-effort",
//                "00000000-0000-0000-0009-01f9ae5dd437 |?? *CanICry9626 |?? tag-weewoo",
//                "45989be3-2a42-4a86-a868-ec44247a7133 |?? Sammster10 |?? warrior",
//                "00000000-0000-0000-0009-01f9ae5dd437 |?? *CanICry9626 |?? staticp",
//                "00000000-0000-0000-0009-01f327e475bb |?? *thedominator006 |?? master",
//                "1367eb05-38c4-4b2a-b925-07ecee8e036b |?? Cinnamon_Waffle |?? mythic",
//                "750230fb-659d-4bc5-b57e-bb9e567d7207 |?? deadsniper52627 |?? tag-24/7",
//                "750230fb-659d-4bc5-b57e-bb9e567d7207 |?? deadsniper52627 |?? tag-afk",
//                "750230fb-659d-4bc5-b57e-bb9e567d7207 |?? deadsniper52627 |?? tag-banned",
//                "750230fb-659d-4bc5-b57e-bb9e567d7207 |?? deadsniper52627 |?? tag-i_h8_2021",
//                "750230fb-659d-4bc5-b57e-bb9e567d7207 |?? deadsniper52627 |?? tag-toxic",
//                "750230fb-659d-4bc5-b57e-bb9e567d7207 |?? deadsniper52627 |?? tag-effort",
//                "750230fb-659d-4bc5-b57e-bb9e567d7207 |?? deadsniper52627 |?? tag-weewoo",
//                "750230fb-659d-4bc5-b57e-bb9e567d7207 |?? deadsniper52627 |?? tag-thiccc",
//                "00000000-0000-0000-0009-01fbc929ae50 |?? *Drazuk1396 |?? master",
//                "da4760d3-270f-41dc-a71f-397f8e2629d2 |?? DisneyDreams |?? static",
//                "00000000-0000-0000-0009-01f7ed35148d |?? *brassmonkey5858 |?? master",
//                "e9535880-ca89-4280-a9fd-3bd85c43ed57 |?? midnght7261 |?? mythic",
//                "00000000-0000-0000-0009-01f585a1edad |?? *Le10up |?? mythic",
//                "00000000-0000-0000-0009-01f585a1edad |?? *Le10up |?? tag-lgbtq",
//                "00000000-0000-0000-0009-01f585a1edad |?? *Le10up |?? tag-weewoo",
//                "00000000-0000-0000-0009-01f585a1edad |?? *Le10up |?? tag-effort",
//                "b4bdc479-3116-4c57-b7f7-fa6ee0c87a45 |?? ToXXic5175 |?? tag-lgbtq",
//                "320308a6-c300-4070-bec4-a59d2b834615 |?? BigLemon96 |?? tag-banned",
//                "00000000-0000-0000-0009-01f58d1689ce |?? *InducedSumo8772 |?? master",
//                "88013b34-142c-44f4-a86e-51446b9ff691 |?? PandaGaming45 |?? master",
//                "097794c9-e3fa-4c61-a1c2-bf6e89f3c332 |?? Aphrodetes |?? warrior",
//                "2972713b-cd8b-4f8e-be6f-4b86b42001cd |?? Supreme_Stormz |?? master",
//                "00000000-0000-0000-0009-00000b6234e6 |?? *Airsavage666786 |?? tag-banned",
//                "00000000-0000-0000-0009-01ffcfc859cd |?? *SAVGxSock |?? tag-weewoo",
//                "c86194fb-a575-47d3-b046-f2bbf5a6a00b |?? azzatheslayer |?? staticp",
//                "00000000-0000-0000-0009-01f3234e28b5 |?? *KingJack182366 |?? warrior",
//                "00000000-0000-0000-0009-01f3234e28b5 |?? *KingJack182366 |?? staticp",
//                "00000000-0000-0000-0009-000007de66ee |?? *MrTurtle4758079 |?? master",
//                "50cd71a1-22f3-481d-b39d-b45e06bdeb03 |?? Gichishooter |?? warrior",
//                "00000000-0000-0000-0009-01fb7dc54885 |?? *the_ace671 |?? warrior",
//                "00000000-0000-0000-0009-01f1683f2272 |?? *Peacful |?? warrior",
//                "60f87077-3344-451a-b251-3699bffb080b |?? DrDivx2k |?? master",
//                "00000000-0000-0000-0009-01f60a8a6ec2 |?? *PepperMintLALA |?? warrior",
//                "00000000-0000-0000-0009-01f279d3a94a |?? *TTV_sweawarrior |?? warrior",
//                "00000000-0000-0000-0009-01f0749783bc |?? *AmTrash3 |?? mythic",
//                "00000000-0000-0000-0009-01feb988a24e |?? *Lonleydog2431 |?? master",
//                "00000000-0000-0000-0009-01f82ed4e325 |?? *LovelyGoober |?? tag-muted",
//                "00000000-0000-0000-0009-01f82ed4e325 |?? *LovelyGoober |?? tag-miner",
//                "335086f8-cfc1-4755-992c-d59fb70717f2 |?? ZxNEOxZ |?? staticp",
//                "903e0895-fb43-42ad-9e39-5b0d95e126a0 |?? FlightCancelled |?? master",
//                "00000000-0000-0000-0009-01f843251745 |?? *Djcool441 |?? warrior",
//                "44ba5245-7301-477f-81b1-577a75ccf44d |?? Zombie_killer9p |?? master",
//                "cbda6a6f-cd06-4765-a757-f4e159600776 |?? Fantastic_Melon |?? warrior",
//                "00000000-0000-0000-0009-01f67f863f4d |?? *PyroManIqc |?? warrior"
//        };
//
//        for (String _s : arr) {
//            String[] split = _s.split(" \\|\\?\\? ");
//            UUID uuid = UUID.fromString(split[0]);
//            String packageName = split[2];
//        }


        return false;
    }


}
