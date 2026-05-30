package daxz.dev.haulover.Skills.Farming.Helpers;

import com.jeff_media.customblockdata.CustomBlockData;
import daxz.dev.haulover.Haulover;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.event.block.MoistureChangeEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class OverrideFarmland implements Listener {

    private static final NamespacedKey blockWatering = new NamespacedKey(Haulover.getInstance(), "haulover-block-water_capacity");

    @EventHandler
    public void preventNaturalFarmlandUpdate(MoistureChangeEvent event){
        event.setCancelled(true);
    }

    @EventHandler
    public void growTickHandler(BlockGrowEvent event){

        Block block = event.getBlock();
        Location loc = block.getLocation();

        PersistentDataContainer blockData = new CustomBlockData(block, Haulover.getInstance());

        if (blockData.get(blockWatering, PersistentDataType.FLOAT) != null){
            // TODO
            return; //add future crop stages here
        }
        event.setCancelled(true);
    }

}
