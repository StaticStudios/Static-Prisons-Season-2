package net.staticstudios.prisons.pickaxe.abilities.handler;

import net.md_5.bungee.api.ChatColor;
import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.blockBroken.BlockBreak;
import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.pickaxe.PrisonPickaxe;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.math.BigInteger;
import java.util.*;

public abstract class BaseAbility { //todo: save abilities on the pickaxe

    private static final Set<AbilityHolder> activeAbilities = new HashSet<>();
    static Map<PrisonPickaxe, Set<AbilityHolder>> pickaxeAbilities = new HashMap<>();

    public static void tickActivateAbilities() {
        Set<AbilityHolder> finished = new HashSet<>();
        for (AbilityHolder abilityHolder : activeAbilities) {
            abilityHolder.ability.onTick(abilityHolder.player, abilityHolder.pickaxe);
            abilityHolder.timesTicked++;
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
    public final BigInteger PRICE;
    public final String ABILITY_ID;
    public final String DISPLAY_NAME;
    public final String UNFORMATTED_DISPLAY_NAME;
    public final List<String> DESCRIPTION;
    public final long COOL_DOWN;



    private int pickaxeLevelRequirement;
    public int getPickaxeLevelRequirement() {
        return pickaxeLevelRequirement;
    }
    private int playerLevelRequirement;
    public int getPlayerLevelRequirement() {
        return playerLevelRequirement;
    }


    public BaseAbility(String id, String name, int maxLevel, BigInteger price, long coolDownMS, String... desc) {
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

    public boolean tryToBuyLevels(Player player, PrisonPickaxe pickaxe, long levelsToBuy) {
        PlayerData playerData = new PlayerData(player);
        if (levelsToBuy <= 0) {
            player.sendMessage(org.bukkit.ChatColor.RED + "You do not have enough tokens to buy this!");
            return false;
        }
        levelsToBuy = Math.min(MAX_LEVEL, pickaxe.getEnchantLevel(ABILITY_ID) + levelsToBuy) - pickaxe.getEnchantLevel(ABILITY_ID);
        if (levelsToBuy <= 0) {
            player.sendMessage(org.bukkit.ChatColor.RED + "This enchant is already at its max level!");
            return false;
        }
        if (playerData.getTokens().compareTo(PRICE.multiply(BigInteger.valueOf(levelsToBuy))) > -1) {
            playerData.removeTokens(PRICE.multiply(BigInteger.valueOf(levelsToBuy)));
            int oldLevel = pickaxe.getEnchantLevel(ABILITY_ID);
            pickaxe.addEnchantLevel(ABILITY_ID, (int) levelsToBuy);
            int newLevel = pickaxe.getEnchantLevel(ABILITY_ID);
            pickaxe.tryToUpdateLore();
            if (pickaxe.getIsEnchantEnabled(ABILITY_ID)) onUpgrade(player, pickaxe, oldLevel, newLevel);
            player.sendMessage(org.bukkit.ChatColor.AQUA + "You successfully upgraded your pickaxe!");
            return true;
        }
        player.sendMessage(org.bukkit.ChatColor.RED + "You do not have enough tokens to buy this!");
        return false;
    }

    public void beginActivation(Player player, PrisonPickaxe pickaxe) {
        pickaxe.setLastActivatedAbilityAt(this, System.currentTimeMillis());
        final int[] countdown = {5};
        Bukkit.getScheduler().runTaskTimer(StaticPrisons.getInstance(), task -> {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', DISPLAY_NAME + " &8&l>> &rActivating in " + countdown[0] + "..."));
            countdown[0]--;
            if (countdown[0] <= 0) {
                int level = pickaxe.getAbilityLevel(ABILITY_ID);
                AbilityHolder abilityHolder = new AbilityHolder(this, level, player,  pickaxe, getTimesToTick(level));
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
    public void onTick(Player player, PrisonPickaxe pickaxe) {}
    public void onUpgrade(Player player, PrisonPickaxe pickaxe, int oldLevel, int newLevel) {}

    public static class AbilityHolder {
        private final BaseAbility ability;
        private final int level;
        private final Player player;
        private final PrisonPickaxe pickaxe;
        private final int timesToTick;

        private int timesTicked;

        public AbilityHolder(BaseAbility ability, int level, Player player, PrisonPickaxe pickaxe, int timesToTick) {
            this.ability = ability;
            this.level = level;
            this.player = player;
            this.pickaxe = pickaxe;
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
