package net.staticstudios.citizens;

import net.citizensnpcs.api.trait.Trait;
import net.staticstudios.prisons.StaticPrisons;
import org.bukkit.metadata.FixedMetadataValue;

public class VisibleNameTrait extends Trait {

    public VisibleNameTrait() {
        super("visible_name");
    }

    private boolean visibleName = false;

    public VisibleNameTrait setVisibleName(boolean visibleName) {
        this.visibleName = visibleName;
        return this;
    }

    @Override
    public void onAttach() {
        if (getNPC().isSpawned()) {
            getNPC().getEntity().setMetadata("nameplate-visible", new FixedMetadataValue(StaticPrisons.getInstance(), visibleName));
        }
    }

    @Override
    public void onSpawn() {
        getNPC().getEntity().setMetadata("nameplate-visible", new FixedMetadataValue(StaticPrisons.getInstance(), visibleName));
    }
}
