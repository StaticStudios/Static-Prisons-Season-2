package me.staticstudios.prisons.enchants.handler;

import me.staticstudios.prisons.blockBroken.PrisonBlockBroken;

public interface IPrisonEnchant {

    void onBlockBreak(PrisonBlockBroken bb);
    void onPickaxeHeld(PrisonBlockBroken bb);
    void onPickaxeUnHeld(PrisonBlockBroken bb);
}
