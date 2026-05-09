package daxz.dev.haulover.Skills.Farming.FarmingTools.FarmingToolHandlers;

import com.destroystokyo.paper.event.player.PlayerReadyArrowEvent;
import daxz.dev.haulover.Haulover;
import io.papermc.paper.datacomponent.item.ChargedProjectiles;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class WateringCanHandler implements Listener {

    private static final NamespacedKey wateringCanID = new NamespacedKey(Haulover.getInstance(), "haulover-watering_can");
    @EventHandler
    public void wateringCanUsage(PlayerInteractEvent event) {
        ItemStack item = event.getItem();
        if (Boolean.TRUE.equals(item.getItemMeta().getPersistentDataContainer().get(wateringCanID, PersistentDataType.BOOLEAN))) return;
        Location loc = event.getClickedBlock().getLocation().toCenterLocation();

        Particle.DRIPPING_WATER.builder()
                .location(loc)
                .offset(0.3,0.1,0.3)
                .count(25)
                .spawn();


    }

    @EventHandler
    public void wateringCanUsed(PlayerItemConsumeEvent event) {}

}
