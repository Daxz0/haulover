package daxz.dev.haulover.Skills.Farming.FarmingTools.FarmingToolHandlers;

import com.destroystokyo.paper.event.player.PlayerReadyArrowEvent;
import daxz.dev.haulover.Haulover;
import io.papermc.paper.datacomponent.item.ChargedProjectiles;
import io.papermc.paper.event.player.PlayerStopUsingItemEvent;
import net.kyori.adventure.text.Component;
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
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class WateringCanHandler implements Listener {

    private static final NamespacedKey wateringCanID = new NamespacedKey(Haulover.getInstance(), "haulover-watering_can");

    private static List<UUID> playersWatering = new ArrayList<>();

    @EventHandler
    public void wateringCanUsage(PlayerInteractEvent event) {
        ItemStack item = event.getItem();
        if (Boolean.TRUE.equals(item.getItemMeta().getPersistentDataContainer().get(wateringCanID, PersistentDataType.BOOLEAN))) return;
        Location loc = event.getClickedBlock().getLocation().toCenterLocation();
        Player player = event.getPlayer();
        playersWatering.remove(player.getUniqueId());
        playersWatering.add(player.getUniqueId());


        new BukkitRunnable() {
            @Override
            public void run() {
                if (!playersWatering.contains(player.getUniqueId())) {
                    cancel();
                    return;
                }

                Particle.SPLASH.builder()
                        .location(loc)
                        .offset(0.3, 0.1, 0.3)
                        .count(25)
                        .spawn();
            }
        }.runTaskTimer(Haulover.getInstance(), 0L, 10L);



    }

    @EventHandler
    public void wateringCanCancelled(PlayerStopUsingItemEvent event) {
        ItemStack item = event.getItem();
        if (Boolean.TRUE.equals(item.getItemMeta().getPersistentDataContainer().get(wateringCanID, PersistentDataType.BOOLEAN))) return;

        Player player = event.getPlayer();
        playersWatering.remove(player.getUniqueId());

    }

    @EventHandler
    public void wateringCanUsed(PlayerItemConsumeEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        if (Boolean.TRUE.equals(item.getItemMeta().getPersistentDataContainer().get(wateringCanID, PersistentDataType.BOOLEAN))) return;
        

        playersWatering.remove(player.getUniqueId());

    }

}
