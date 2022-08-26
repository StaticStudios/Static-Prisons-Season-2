package net.staticstudios.citizens;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.event.CitizensEnableEvent;
import net.citizensnpcs.api.event.NPCCreateEvent;
import net.citizensnpcs.api.event.NPCRemoveEvent;
import net.staticstudios.prisons.StaticPrisons;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class CitizensEvents implements Listener {

    @EventHandler
    public void onCitizenEnable(CitizensEnableEvent event) {
        StaticPrisons.getInstance().getLogger().info("Enabled Citizens");
    }

    @EventHandler
    public void onCreate(NPCCreateEvent event) {
        if (CitizensAPI.getNPCRegistry().getName().equalsIgnoreCase(event.getNPC().getOwningRegistry().getName())) {
            CitizensUtils.addNPCToFile(event.getNPC());
            CitizensUtils.save();
        }
    }

    @EventHandler
    public void onRemove(NPCRemoveEvent event) {
        CitizensUtils.removeNPCFromFile(event.getNPC());
        CitizensUtils.save();
    }

}
