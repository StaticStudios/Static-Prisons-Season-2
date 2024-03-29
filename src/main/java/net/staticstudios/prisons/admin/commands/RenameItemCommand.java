package net.staticstudios.prisons.admin.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.staticstudios.prisons.StaticPrisons;
import net.staticstudios.prisons.customitems.CustomItem;
import net.staticstudios.prisons.pickaxe.PrisonPickaxe;
import net.staticstudios.prisons.utils.CommandUtils;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public class RenameItemCommand implements TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (!(sender instanceof Player player)) return false;

        if (args.length == 0) {
            player.sendMessage(CommandUtils.getCorrectUsage("/renameitem <format> <name>"));
            return false;
        }

        ItemStack item = player.getInventory().getItemInMainHand();

        if (item.getType().equals(Material.AIR)) {
            player.sendMessage(Component.text("You cannot rename this item.").color(NamedTextColor.RED));
            return false;
        }

        String name = String.join(" ", args);

        Component displayName;

        displayName = switch (args[0]) {
            case "none" -> Component.text(name.substring(name.indexOf("name") + 5));
            case "mini" -> {
                if (name.matches(".*<click:.*>.*")) {
                    player.sendMessage(Component.text("You can't use click events in your item's name!").color(NamedTextColor.RED));
                    yield null;
                }

                yield deserializeMini(name.substring(name.indexOf("mini") + 5));
            }
            case "ampersand" -> deserializeAmpersand(name.substring(name.indexOf("ampersand") + 10));
            default -> Component.text(name);
        };

        if (displayName == null) return false;

        if (PrisonUtils.isValidComponent(displayName)) {
            player.sendMessage(Component.text("Invalid name. ").color(NamedTextColor.RED));
            return false;
        }

        if (item.getItemMeta().getPersistentDataContainer().has(CustomItem.customItemNamespace)) {

            PrisonPickaxe pickaxe = PrisonPickaxe.fromItem(item);
            if (pickaxe == null) {
                player.sendMessage(Component.text("You cannot rename this item.").color(NamedTextColor.RED));
                return false;
            }

            pickaxe.setName(displayName);
            pickaxe.updateItem();
            player.sendMessage(Component.text("Renamed item to ").color(NamedTextColor.GREEN).append(displayName));
            return true;
        }

        item.editMeta(itemMeta -> itemMeta.displayName(displayName));

        player.sendMessage(Component.text("Renamed item to ").color(NamedTextColor.GREEN).append(displayName));

        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (args.length == 1) return List.of("mini", "none", "ampersand");
        if (args.length == 2) return List.of("<name>");
        return Collections.emptyList();
    }

    private Component deserializeAmpersand(String string) {
        return LegacyComponentSerializer.legacyAmpersand().deserialize(string);
    }

    private Component deserializeMini(String string) {
        return StaticPrisons.miniMessage().deserialize(string);
    }
}
