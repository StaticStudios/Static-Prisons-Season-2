package net.staticstudios.prisons.backpacks.config;

import net.kyori.adventure.text.Component;

public record BackpackTier(int tier, String skin, long maxSize, Component name) {}