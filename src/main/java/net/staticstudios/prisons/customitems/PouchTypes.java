package net.staticstudios.prisons.customitems;

import net.kyori.adventure.text.Component;

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
}
