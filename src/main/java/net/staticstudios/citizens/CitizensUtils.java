package net.staticstudios.citizens;

import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class CitizensUtils {
    private static final YamlConfiguration npcFile = YamlConfiguration.loadConfiguration(new File(StaticPrisons.getInstance().getDataFolder(), "leaderboardNPCs.yml"));

    public static void init() {
//        StaticPrisons.getInstance().getServer().getPluginManager().registerEvents(new CitizensEvents(), StaticPrisons.getInstance());
//        checkForNewNPCs();
    }

//    private static void checkForNewNPCs() {
//        CitizensAPI.getNPCRegistry().forEach(CitizensUtils::addNPCToFile);
//
//        save();
//    }
//
//    public static void addNPCToFile(NPC npc) {
//        if (npcFile.contains(npc.getName())) {
//            if (!npc.getUniqueId().toString().equals(npcFile.get(npc.getName()))) {
//                throw new IllegalStateException("NPC " + npc.getName() + " already exists in the file but has different UUID");
//            }
//            return;
//        }
//
//        npcFile.set(npc.getName(), npc.getUniqueId().toString());
//    }

//    public static void removeNPCFromFile(NPC npc) {
//        npcFile.set(npc.getName(), null);
//    }


    public static void save() {
        PrisonUtils.saveConfig(npcFile, "leaderboardNPCs.yml");
    }

//    public static Optional<NPC> getNPC(String name) {
//        if (!npcFile.contains(name)) {
//            return Optional.empty();
//        }
//
//        return Optional.of(CitizensAPI.getNPCRegistry().getByUniqueId(UUID.fromString(Objects.requireNonNull(npcFile.getString(name)))));
//    }
}
