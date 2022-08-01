package net.staticstudios.prisons.lootboxes.handler;

public enum LootBoxType {
    TOKEN("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTE5ZDI4YTg2MzJmYTRkODdjYTE5OWJiYzJlODhjZjM2OGRlZGQ1NTc0NzAxN2FlMzQ4NDM1NjlmN2E2MzRjNSJ9fX0="),
    MONEY("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTFhNWJkNmZjZDE3OTY4NTZiZDhhNGM2OTk4ODc5NGY4ZWM0NjY0ZTU1MDk4ZDhjZGU5YzdhNzg0MjNjNzFmIn19fQ=="),
    MULTIPLIER("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWUyZTRjZGYyN2UzMmRjMThiNjA2NGNkZGNmZTFmZmIxZjM4ZDE0ODFiZDdjNDcyZWExMjMwNzZhMDc4NTAzZiJ9fX0="),
    MINE_BOMB("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDE5ZWYyNTQ2MDU2Zjk0OWI4NDMxY2RhMGQxMzg1ZmQzYmUyYjcxMWQ2YzQ1ZTQ0YmQ0OGNkZWE2OTBhN2RkIn19fQ=="),
    PICKAXE("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGQzMTZhNjQwOTlkODk2ZjY2OWQwZjA4ODUyMDIxN2E4M2RlY2Q0YTNiNjdlNTdhZjg5YjMzZDIwYzMyMWYzNCJ9fX0=");




    public String base64Texture;

    LootBoxType(String skullBase64texture) {
        this.base64Texture = skullBase64texture;
    }
}
