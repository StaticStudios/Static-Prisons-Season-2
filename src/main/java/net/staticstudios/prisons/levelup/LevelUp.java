package net.staticstudios.prisons.levelup;

import net.staticstudios.prisons.commands.CommandManager;
import net.staticstudios.prisons.levelup.prestige.PrestigeCommand;
import net.staticstudios.prisons.levelup.rankup.RankUpCommand;
import net.staticstudios.prisons.levelup.rankup.RankUpMaxCommand;
import net.staticstudios.prisons.levelup.xp.LevelCommand;

public class LevelUp {

    public static void init() {
        CommandManager.registerCommand("rankup", new RankUpCommand());
        CommandManager.registerCommand("rankupmax", new RankUpMaxCommand());
        CommandManager.registerCommand("level", new LevelCommand());
        CommandManager.registerCommand("prestige", new PrestigeCommand());
    }
}
