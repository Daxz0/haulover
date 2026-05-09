package daxz.dev.haulover.Skills.Farming;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.MoistureChangeEvent;

public class OverrideFarmland implements Listener {

    @EventHandler
    public void farmlandTicks(MoistureChangeEvent event){
        event.setCancelled(true);
    }

}
