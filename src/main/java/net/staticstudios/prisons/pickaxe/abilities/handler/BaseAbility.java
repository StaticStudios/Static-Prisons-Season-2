package net.staticstudios.prisons.pickaxe.abilities.handler;

import net.md_5.bungee.api.ChatColor;
import net.staticstudios.mines.StaticMine;
import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.blockbreak.BlockBreak;
import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.pickaxe.PrisonPickaxe;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

public abstract class BaseAbility { //todo: save abilities on the pickaxe

    private static final Set<AbilityHolder> activeAbilities = new HashSet<>();
    public static Map<PrisonPickaxe, Set<AbilityHolder>> pickaxeAbilities = new HashMap<>();

    public static void tickActivateAbilities() {
        Set<AbilityHolder> finished = new HashSet<>();
        for (AbilityHolder abilityHolder : activeAbilities) {
            abilityHolder.timesTicked++;
            abilityHolder.ability.onTick(abilityHolder.timesTicked, abilityHolder.player, abilityHolder.pickaxe, abilityHolder.mine);
            int ticksLeft = abilityHolder.timesToTick - abilityHolder.timesTicked;
            if (ticksLeft % 20 == 0 && ticksLeft / 20 <= 3 && ticksLeft != 0) {
                abilityHolder.player.sendMessage(ChatColor.translateAlternateColorCodes('&', abilityHolder.ability.DISPLAY_NAME + " &8&l>> &rEnding in " + (ticksLeft / 20) + "..."));
            }
            if (ticksLeft <= 0) {
                finished.add(abilityHolder);
            }
        }
        activeAbilities.removeAll(finished);
        for (AbilityHolder abilityHolder : finished) {
            Set<AbilityHolder> active = pickaxeAbilities.get(abilityHolder.pickaxe);
            active.remove(abilityHolder);
            if (active.isEmpty()) {
                pickaxeAbilities.remove(abilityHolder.pickaxe);
            } else {
                pickaxeAbilities.put(abilityHolder.pickaxe, active);
            }
        }
    }

    public final int MAX_LEVEL;
    public final long PRICE;
    public final String ABILITY_ID;
    public final String DISPLAY_NAME;
    public final String UNFORMATTED_DISPLAY_NAME;
    public final List<String> DESCRIPTION;
    public final long COOL_DOWN;
    public boolean requiresMineOnActivate = false;



    private int pickaxeLevelRequirement;
    public int getPickaxeLevelRequirement() {
        return pickaxeLevelRequirement;
    }
    private int playerLevelRequirement;
    public int getPlayerLevelRequirement() {
        return playerLevelRequirement;
    }


    public BaseAbility(String id, String name, int maxLevel, long price, long coolDownMS, String... desc) {
        ABILITY_ID = id;
        DISPLAY_NAME = ChatColor.translateAlternateColorCodes('&', name);
        UNFORMATTED_DISPLAY_NAME= ChatColor.stripColor(DISPLAY_NAME);
        MAX_LEVEL = maxLevel;
        PRICE = price;
        COOL_DOWN = coolDownMS;
        for (int i = 0; i < desc.length; i++) desc[i] = ChatColor.translateAlternateColorCodes('&', desc[i]);
        DESCRIPTION = List.of(desc);
        PickaxeAbilities.abilityIDToAbility.put(ABILITY_ID, this);
    }

    public BaseAbility setPickaxeLevelRequirement(int level) {
        pickaxeLevelRequirement = level;
        return this;
    }
    public BaseAbility setPlayerLevelRequirement(int level) {
        playerLevelRequirement = level;
        return this;
    }

    public long getPrice(long level) {
        return PRICE * (level + 1);
    }

    public boolean tryToBuyLevels(Player player, PrisonPickaxe pickaxe, long levelsToBuy) {
        PlayerData playerData = new PlayerData(player);
        if (levelsToBuy <= 0) {
            player.sendMessage(ChatColor.RED + "You do not have enough shards to buy this!");
            return false;
        }
        if (pickaxe.getAbilityLevel(this) >= MAX_LEVEL) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cThis ability has reached its max level!"));
            return false;
        }
        if (pickaxe.getLevel() < getPickaxeLevelRequirement()) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYour pickaxe is not high enough level to upgrade this ability!"));
            return false;
        }
        if (playerData.getPlayerLevel() <  getPlayerLevelRequirement()) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou are not a high enough level to upgrade this ability!"));
            return false;
        }
        if (playerData.getShards() >= getPrice(pickaxe.getAbilityLevel(this))) {
            playerData.removeShards(getPrice(pickaxe.getAbilityLevel(this)));
            pickaxe.setAbilityLevel(this, pickaxe.getAbilityLevel(this) + 1);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bYou upgraded " + UNFORMATTED_DISPLAY_NAME + "!"));
            return true;
        }
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou do not have enough shards to upgrade this ability!"));
        return false;
    }

    public void beginActivation(Player player, PrisonPickaxe pickaxe, StaticMine mine) {
        pickaxe.setLastActivatedAbilityAt(this, System.currentTimeMillis());
        final int[] countdown = {5};
        Bukkit.getScheduler().runTaskTimer(StaticPrisons.getInstance(), task -> {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', DISPLAY_NAME + " &8&l>> &rActivating in " + countdown[0] + "..."));
            countdown[0]--;
            if (countdown[0] <= 0) {
                int level = pickaxe.getAbilityLevel(ABILITY_ID);
                AbilityHolder abilityHolder = new AbilityHolder(this, level, player,  pickaxe, mine, getTimesToTick(level));
                activeAbilities.add(abilityHolder);
                Set<AbilityHolder> _playerAbilities = pickaxeAbilities.getOrDefault(pickaxe, new HashSet<>());
                _playerAbilities.add(abilityHolder);
                pickaxeAbilities.put(pickaxe, _playerAbilities);
                task.cancel();
            }
        }, 0, 20);
    }


    public abstract int getTimesToTick(int level);

    public void onBlockBreak(BlockBreak blockBreak) {}
    public void onActivate(Player player, PrisonPickaxe pickaxe) {}
    public void onDeactivate(Player player, PrisonPickaxe pickaxe) {}
    public void onTick(int timesTicked, Player player, PrisonPickaxe pickaxe, StaticMine mine) {}
    public void onUpgrade(Player player, PrisonPickaxe pickaxe, int oldLevel, int newLevel) {}

    public static class AbilityHolder {
        private final BaseAbility ability;
        private final int level;
        private final Player player;
        private final PrisonPickaxe pickaxe;
        private final StaticMine mine;
        private final int timesToTick;

        private int timesTicked;

        public AbilityHolder(BaseAbility ability, int level, Player player, PrisonPickaxe pickaxe, StaticMine mine, int timesToTick) {
            this.ability = ability;
            this.level = level;
            this.player = player;
            this.pickaxe = pickaxe;
            this.mine = mine;
            this.timesToTick = timesToTick;
            timesTicked = 0;
        }

        public BaseAbility getAbility() {
            return ability;
        }
        public int getLevel() {
            return level;
        }
        public Player getPlayer() {
            return player;
        }
        public PrisonPickaxe getPickaxe() {
            return pickaxe;
        }
        public int getTimesToTick() {
            return timesToTick;
        }
        public int getTimesTicked() {
            return timesTicked;
        }
    }


}
