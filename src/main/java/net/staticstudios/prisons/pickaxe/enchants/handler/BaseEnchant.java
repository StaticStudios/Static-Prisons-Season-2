package net.staticstudios.prisons.pickaxe.enchants.handler;

import net.md_5.bungee.api.ChatColor;
import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.blockbreak.BlockBreak;
import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.pickaxe.PrisonPickaxe;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public abstract class BaseEnchant implements Listener {
    public static void init() {
        Bukkit.getScheduler().runTaskTimer(StaticPrisons.getInstance(), () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (PrisonUtils.Players.isHoldingRightClick(player)) {
                    ItemStack item = player.getInventory().getItemInMainHand();
                    PrisonPickaxe pickaxe = PrisonPickaxe.fromItem(item);
                    if (pickaxe == null) continue;
                    for (BaseEnchant enchant : pickaxe.getEnchants()) {
                        if (!pickaxe.getIsEnchantEnabled(enchant)) continue;
                        enchant.whileRightClicking(player, pickaxe);
                    }
                }
            }
        }, 20, 7);
    }

    public final int MAX_LEVEL;
    public final long PRICE;
    public final String ENCHANT_ID;
    public final String DISPLAY_NAME;
    public final String UNFORMATTED_DISPLAY_NAME;
    public final List<String> DESCRIPTION;

    private EnchantTier[] tiers;
    public EnchantTier getTier(int tier) {
        if (tiers == null) return null;
        if (tier < 0 || tier >= tiers.length) return null;
        return tiers[tier];
    }
    public void setTiers(EnchantTier... tiers) {
        this.tiers = tiers;
    }
    public int getMaxLevel(int tier) {
        if (tiers == null) return MAX_LEVEL;
        EnchantTier _tier = getTier(tier);
        if (_tier == null) return MAX_LEVEL;
        return _tier.maxLevel();
    }

    private int pickaxeLevelRequirement;
    public int getPickaxeLevelRequirement() {
        return pickaxeLevelRequirement;
    }
    private int playerLevelRequirement;
    public int getPlayerLevelRequirement() {
        return playerLevelRequirement;
    }


    public BaseEnchant(String id, String name, int maxLevel, long price, String... desc) {
        ENCHANT_ID = id;
        DISPLAY_NAME = ChatColor.translateAlternateColorCodes('&', name);
        UNFORMATTED_DISPLAY_NAME= ChatColor.stripColor(DISPLAY_NAME);
        MAX_LEVEL = maxLevel;
        PRICE = price;
        for (int i = 0; i < desc.length; i++) desc[i] = ChatColor.translateAlternateColorCodes('&', desc[i]);
        DESCRIPTION = List.of(desc);
        PickaxeEnchants.enchantIDToEnchant.put(ENCHANT_ID, this);
    }

    public BaseEnchant setPickaxeLevelRequirement(int level) {
        pickaxeLevelRequirement = level;
        return this;
    }
    public BaseEnchant setPlayerLevelRequirement(int level) {
        playerLevelRequirement = level;
        return this;
    }

    boolean useChances = false;
    double defaultChance = 0d;
    double chancePerLevel = 0d;
    public BaseEnchant setUseChances(boolean use) {
        useChances = use;
        return this;
    }
    public BaseEnchant setDefaultPercentChance(double chance) {
        defaultChance = chance;
        return this;
    }
    public BaseEnchant setPercentChancePerLevel(double chance) {
        chancePerLevel = chance;
        return this;
    }
    public boolean getUseChances() {
        return useChances;
    }
    public double getDefaultPercentChance() {
        return defaultChance;
    }
    public double getPercentChancePerLevel() {
        return chancePerLevel;
    }

    public double getPercentChance(PrisonPickaxe pickaxe) {
        return getPercentChance(pickaxe.getEnchantLevel(ENCHANT_ID));
    }

    public double getPercentChance(int level) {
        if (!useChances) return 100;
        return defaultChance + level * chancePerLevel;
    }

    public boolean activate(PrisonPickaxe pickaxe) {
        if (!pickaxe.getIsEnchantEnabled(this)) return false;
        return activate(pickaxe.getEnchantLevel(ENCHANT_ID));
    }

    public boolean activate(int level) {
        if (!useChances) return true;
        return PrisonUtils.randomDouble(0d, 100d) <= getPercentChance(level);
    }










    public boolean tryToBuyLevels(Player player, PrisonPickaxe pickaxe, long levelsToBuy) {
        PlayerData playerData = new PlayerData(player);
        if (levelsToBuy <= 0) {
            player.sendMessage(org.bukkit.ChatColor.RED + "You do not have enough tokens to buy this!");
            return false;
        }
        levelsToBuy = Math.min(getMaxLevel(pickaxe.getEnchantTier(this)), pickaxe.getEnchantLevel(ENCHANT_ID) + levelsToBuy) - pickaxe.getEnchantLevel(ENCHANT_ID);
        if (levelsToBuy <= 0) {
            player.sendMessage(org.bukkit.ChatColor.RED + "This enchant is already at its max level!");
            return false;
        }
        if (playerData.getTokens() >= PRICE * levelsToBuy) {
            playerData.removeTokens(PRICE * levelsToBuy);
            int oldLevel = pickaxe.getEnchantLevel(ENCHANT_ID);
            pickaxe.addEnchantLevel(ENCHANT_ID, (int) levelsToBuy);
            int newLevel = pickaxe.getEnchantLevel(ENCHANT_ID);
            pickaxe.tryToUpdateLore();
            if (pickaxe.getIsEnchantEnabled(ENCHANT_ID)) onUpgrade(player, pickaxe, oldLevel, newLevel);
            player.sendMessage(org.bukkit.ChatColor.AQUA + "You successfully upgraded your pickaxe!");
            return true;
        }
        player.sendMessage(org.bukkit.ChatColor.RED + "You do not have enough tokens to buy this!");
        return false;
    }


    public void onBlockBreak(BlockBreak blockBreak) {}
    public void onPickaxeHeld(Player player, PrisonPickaxe pickaxe) {}
    public void onPickaxeUnHeld(Player player, PrisonPickaxe pickaxe) {}
    public void whileRightClicking(Player player, PrisonPickaxe pickaxe) {}
    public void onUpgrade(Player player, PrisonPickaxe pickaxe, int oldLevel, int newLevel) {}

}

