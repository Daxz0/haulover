package daxz.dev.haulover.Skills.Farming.Helpers;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFertilizeEvent;

public class DisabledBonemeal implements Listener {

    @EventHandler
    public void onBonemeal(BlockFertilizeEvent event) {
        event.setCancelled(true);
    }



}
