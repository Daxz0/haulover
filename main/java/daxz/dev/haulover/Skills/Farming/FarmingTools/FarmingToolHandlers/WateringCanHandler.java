package daxz.dev.haulover.Skills.Farming.FarmingTools.FarmingToolHandlers;

import daxz.dev.haulover.Haulover;
import daxz.dev.haulover.Skills.Farming.FarmingTools.WateringCans.BasicWateringCan;
import daxz.dev.haulover.Skills.Farming.FarmingTools.WateringCans.WateringCan;
import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.ItemLore;
import io.papermc.paper.event.player.PlayerStopUsingItemEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.Sound;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;



public class WateringCanHandler implements Listener {

    private static final NamespacedKey wateringCanID = new NamespacedKey(Haulover.getInstance(), "haulover-watering_can");
    public static final NamespacedKey wateringCanCapacity = new NamespacedKey(Haulover.getInstance(), "haulover-watering_can-capacity");
    public static final NamespacedKey hauloverItemID = new NamespacedKey(Haulover.getInstance(), "haulover_item");



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

                var targetBlock = player.getTargetBlockExact(3, FluidCollisionMode.ALWAYS);
                if (targetBlock == null) return;

                if (targetBlock.getType() == Material.WATER) {
                    Sound drink = Sound.ENTITY_GENERIC_DRINK;
                    player.playSound(player.getLocation(), drink, 1.0F, 1.0F);


                    WateringCan canType = getWateringCanType(item);
                    float percentage = (float) (canType.getMaxCapacity() * 0.15);
                    updateCanCapacity(item, percentage);


                    return;
                }
                ItemMeta meta = item.getItemMeta();
                float current = meta.getPersistentDataContainer().getOrDefault(wateringCanCapacity, PersistentDataType.FLOAT, 0f);

                if (current <= 0){
                    Sound tink = Sound.BLOCK_AMETHYST_BLOCK_BREAK;
                    player.playSound(player.getLocation(), tink, 1.0F, 0.5F);
                    return;

                }

                Sound splash = Sound.ENTITY_GENERIC_SPLASH;
                player.playSound(player.getLocation(), splash, 1.0F, 1.0F);
                Particle.SPLASH.builder()
                        .location(targetBlock.getLocation().toCenterLocation())
                        .offset(0.2, 0.1, 0.2)
                        .count(25)
                        .spawn();

                updateCanCapacity(item, -10f);

                timeout++;
            }
        }.runTaskTimer(Haulover.getInstance(), 0L, 10L);



    }

    private void updateCanCapacity(ItemStack item, float amount) {

        ItemMeta meta = item.getItemMeta();
        WateringCan canType = getWateringCanType(item);


        float current = meta.getPersistentDataContainer().getOrDefault(wateringCanCapacity, PersistentDataType.FLOAT, 0f);

        if (current < 0) {
            meta.getPersistentDataContainer().set(wateringCanCapacity, PersistentDataType.FLOAT, 0f);
            return;
        }
        if (current > canType.getMaxCapacity()){
            meta.getPersistentDataContainer().set(wateringCanCapacity, PersistentDataType.FLOAT, current);
            return;
        }

        meta.getPersistentDataContainer().set(wateringCanCapacity, PersistentDataType.FLOAT, current + amount);
        item.setItemMeta(meta);

        wateringCanLoreUpdate(item);

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

    private WateringCan getWateringCanType(ItemStack item) {
        String id = item.getItemMeta().getPersistentDataContainer()
                .get(hauloverItemID, PersistentDataType.STRING);
        return switch (id) {
            case BasicWateringCan.ID -> BasicWateringCan.INSTANCE;
            default -> null;
        };
    }


    private void wateringCanLoreUpdate(ItemStack item) {
        ItemLore itemLore = item.getData(DataComponentTypes.LORE);
        if (itemLore == null) return;

        List<Component> lore = new ArrayList<>(itemLore.lines());
        float current = item.getItemMeta().getPersistentDataContainer().get(wateringCanCapacity, PersistentDataType.FLOAT);

        System.out.println(current);
        WateringCan canType = getWateringCanType(item);

        for (int i = 0; i < lore.size(); i++) {
            if (MiniMessage.miniMessage().serialize(lore.get(i)).contains("Capacity:")){
                lore.set(i, MiniMessage.miniMessage()
                        .deserialize("<gray>  Capacity: <aqua>" + current + "<dark_aqua>/<dark_aqua>" + canType.getMaxCapacity())
                        .decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE));
                break;
            }
        }


        item.lore(lore);
    }

}
