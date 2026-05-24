package daxz.dev.haulover.Skills.Farming.FarmingTools.FarmingToolHandlers;

import daxz.dev.haulover.Haulover;
import io.papermc.paper.event.player.PlayerStopUsingItemEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.Sound;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class WateringCanHandler implements Listener {

    private static final NamespacedKey wateringCanID = new NamespacedKey(Haulover.getInstance(), "haulover-watering_can");

    private static List<UUID> playersWatering = new ArrayList<>();
    private static List<UUID> wateringRatelimit = new ArrayList<>();

    @EventHandler
    public void wateringCanUsage(PlayerInteractEvent event) {
        ItemStack item = event.getItem();
        if (item == null) return;
        if (!Boolean.TRUE.equals(item.getItemMeta().getPersistentDataContainer().get(wateringCanID, PersistentDataType.BOOLEAN))) return;
        Player player = event.getPlayer();
        if (wateringRatelimit.contains(player.getUniqueId())) return;
        playersWatering.remove(player.getUniqueId());
        if (event.getAction() != Action.RIGHT_CLICK_AIR){
            Location loc = event.getInteractionPoint();
            Block clickedBlock = event.getClickedBlock();
            if (clickedBlock != null && clickedBlock.getType() == Material.WATER) {
                Sound drink = Sound.ENTITY_GENERIC_DRINK;
                player.playSound(loc, drink, 1.0F, 1.0F);
                return;
            }
        }
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;





        playersWatering.add(player.getUniqueId());

        new BukkitRunnable() {
            int timeout = 0;
            @Override
            public void run() {
                if (timeout >= 20) {
                    cancel();
                    return;
                }
                if (!playersWatering.contains(player.getUniqueId())) {
                    cancel();
                    return;
                }

                var targetBlock = player.getTargetBlockExact(3);
                if (targetBlock == null) return;
                Sound splash = Sound.ENTITY_GENERIC_SPLASH;
                player.playSound(player.getLocation(), splash, 1.0F, 1.0F);
                Particle.SPLASH.builder()
                        .location(targetBlock.getLocation().toCenterLocation())
                        .offset(0.2, 0.1, 0.2)
                        .count(25)
                        .spawn();

                timeout++;
            }
        }.runTaskTimer(Haulover.getInstance(), 0L, 10L);



    }

    @EventHandler
    public void wateringCanCancelled(PlayerStopUsingItemEvent event) {
        ItemStack item = event.getItem();
        if (!Boolean.TRUE.equals(item.getItemMeta().getPersistentDataContainer().get(wateringCanID, PersistentDataType.BOOLEAN))) return;

        Player player = event.getPlayer();
        playersWatering.remove(player.getUniqueId());
        wateringRatelimit.add(player.getUniqueId());

        player.setCooldown(item.getType(), 10);
        Bukkit.getScheduler().runTaskLater(Haulover.getInstance(), () -> {
            wateringRatelimit.remove(player.getUniqueId());

        }, 10);

    }

    @EventHandler
    public void wateringCanUsed(PlayerItemConsumeEvent event) {
        ItemStack item = event.getItem();
        if (!Boolean.TRUE.equals(item.getItemMeta().getPersistentDataContainer().get(wateringCanID, PersistentDataType.BOOLEAN))) return;

        Player player = event.getPlayer();
        playersWatering.remove(player.getUniqueId());
        wateringRatelimit.add(player.getUniqueId());

        Bukkit.getScheduler().runTaskLater(Haulover.getInstance(), () -> {
            wateringRatelimit.remove(player.getUniqueId());

        }, 20);

    }

}
