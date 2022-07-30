package net.staticstudios.prisons.pvp.outposts.domain;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.gangs.Gang;
import net.staticstudios.prisons.pvp.outposts.OutpostTypes;

import java.math.BigInteger;
import java.util.UUID;

public class TokenOutpost extends Outpost {
    public TokenOutpost(String id, String name, Gang currentGang, ProtectedRegion region, OutpostTypes type) {
        super(id, name, currentGang, region, type);
    }

    public TokenOutpost(UUID id, String name, Gang currentGang, ProtectedRegion region, OutpostTypes type, int capturePercentage) {
        super(id, name, currentGang, region, type, capturePercentage);
    }

    public TokenOutpost(UUID id, String name, ProtectedRegion region, OutpostTypes type) {
        super(id, name, region, type);
    }

    @Override
    public void giveBoost(Gang gang) { //todo: change this to give a multiplier/ hook into this from PlayerData
        gang.getMembers().forEach(uuid -> {
            PlayerData data = new PlayerData(uuid);

            data.addTokens(BigInteger.valueOf(100));
        });
    }
}
