package net.staticstudios.core.interfaces;

/**
 * Use this Interface if you create a new feature with custom commands.
 * @see Feature for creating a new feature without commands.
 */
public interface CommandFeature extends Feature, Command {

    @Override
    default void init() {
        Feature.super.init();
        loadCommands();
    }
}
