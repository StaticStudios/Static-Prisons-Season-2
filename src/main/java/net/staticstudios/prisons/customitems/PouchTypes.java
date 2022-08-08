package net.staticstudios.prisons.customitems;

import net.kyori.adventure.text.Component;
import net.staticstudios.prisons.data.PlayerData;

public enum PouchTypes {
    MONEY(Component.text("$")),
    TOKEN(Component.empty()),
    MULTIPLIER(Component.text("x"));

    private final Component prefix;

    PouchTypes(Component prefix) {
        this.prefix = prefix;
    }

    public Component getPrefix() {
        return prefix;
    }

    public void addReward(PlayerData playerData, long reward) {

    }
}
