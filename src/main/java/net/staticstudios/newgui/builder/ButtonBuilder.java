package net.staticstudios.newgui.builder;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.staticstudios.newgui.GUIButton;
import net.staticstudios.newgui.StaticGUI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * @author Sam (GitHub: <a href="https://github.com/Sammster10">Sam's GitHub</a>)
 */
public final class ButtonBuilder {

    /**
     * @return A new ButtonBuilder instance.
     */
    public static ButtonBuilder getBuilder() {
        return new ButtonBuilder();
    }

    private Consumer<Player> onLeftClick = (player) -> {};
    private Consumer<Player> onRightClick = (player) -> {};
    private Consumer<Player> onMiddleClick = (player) -> {};
    private BiConsumer<InventoryClickEvent, StaticGUI> onClick = (event, gui) -> {};
    private Material icon = Material.STONE;
    private ItemStack skullItem;
    private Component name = Component.text("GUI Button");
    private List<Component> lore = List.of();
    private boolean enchanted = false;

    public ButtonBuilder() {}


    /**
     * Sets the onLeftClick action.
     * @param onLeftClick The action to perform when the player left-clicks the button.
     * @return This ButtonBuilder instance.
     */
    public ButtonBuilder onLeftClick(Consumer<Player> onLeftClick) {
        this.onLeftClick = onLeftClick;
        return this;
    }

    /**
     * Sets the onRightClick action.
     * @param onRightClick The action to perform when the player right-clicks the button.
     * @return This ButtonBuilder instance.
     */
    public ButtonBuilder onRightClick(Consumer<Player> onRightClick) {
        this.onRightClick = onRightClick;
        return this;
    }

    /**
     * Sets the onMiddleClick action.
     * @param onMiddleClick The action to perform when the player middle-clicks the button.
     * @return This ButtonBuilder instance.
     */
    public ButtonBuilder onMiddleClick(Consumer<Player> onMiddleClick) {
        this.onMiddleClick = onMiddleClick;
        return this;
    }

    /**
     * Sets the onClick action. This action will be called along-side the other onClick actions.
     * @param onClick The action to perform when the player clicks the button.
     * @return This ButtonBuilder instance.
     */
    public ButtonBuilder onClick(BiConsumer<InventoryClickEvent, StaticGUI> onClick) {
        this.onClick = onClick;
        return this;
    }

    /**
     * Sets the icon of the button.
     * @param icon The icon of the button.
     * @return This ButtonBuilder instance.
     */
    public ButtonBuilder icon(Material icon) {
        this.icon = icon;
        return this;
    }

    /**
     * Sets the icon of the button to a player skull.
     * @param player The player's head to use as the icon.
     * @return This ButtonBuilder instance.
     */
    public ButtonBuilder skull(OfflinePlayer player) {
        skullItem = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skullMeta = (SkullMeta) skullItem.getItemMeta();
        skullMeta.setOwningPlayer(player);
        skullItem.setItemMeta(skullMeta);
        return this;
    }

    /**
     * Sets the icon of the button to a player skull.
     * @param base64 An encoded base64 texture to use as the icon.
     * @return This ButtonBuilder instance.
     */
    public ButtonBuilder skull(String base64) {
        skullItem = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skullMeta = (SkullMeta) skullItem.getItemMeta();
        PlayerProfile profile = Bukkit.createProfile(UUID.randomUUID(), null);
        profile.setProperty(new ProfileProperty("textures", base64));
        skullMeta.setPlayerProfile(profile);
        skullItem.setItemMeta(skullMeta);
        return this;
    }

    /**
     * Sets the name of the button.
     * @param name The name of the button.
     * @return This ButtonBuilder instance.
     */
    public ButtonBuilder name(String name) {
        this.name = pretty(LegacyComponentSerializer.legacyAmpersand().deserialize(name));
        return this;
    }

    /**
     * Sets the name of the button.
     * @param name The name of the button.
     * @return This ButtonBuilder instance.
     */
    public ButtonBuilder name(Component name) {
        this.name = pretty(name);
        return this;
    }

    /**
     * Sets the lore of the button.
     * @param lore The lore of the button.
     * @return This ButtonBuilder instance.
     */
    public ButtonBuilder lore(List<Component> lore) {
        lore = lore.stream()
                .map(ButtonBuilder::pretty)
                .map(component -> component.colorIfAbsent(NamedTextColor.GRAY)).toList();
        this.lore = lore;
        return this;
    }

    /**
     * Sets whether the button is enchanted.
     * @return This ButtonBuilder instance.
     */
    public ButtonBuilder enchanted(boolean enchanted) {
        this.enchanted = enchanted;
        return this;
    }

    /**
     * @param gui The GUI that the button will be added to.
     *            This can be set to null, however this will mean that the onClick method will not be called.
     * @return a new GUIButton
     */
    public GUIButton build(@Nullable StaticGUI gui) {
        if (skullItem != null) {
            return new GUIButton(onLeftClick, onRightClick, onMiddleClick,
                    onClick,
                    skullItem, name, lore, enchanted,
                    gui);
        }

        return new GUIButton(onLeftClick, onRightClick, onMiddleClick,
                onClick,
                icon, name, lore, enchanted,
                gui);
    }

    private static Component pretty(Component component) {
        return component.decoration(TextDecoration.ITALIC).equals(TextDecoration.State.NOT_SET) ? component.decoration(TextDecoration.ITALIC, false) : component;
    }

}
