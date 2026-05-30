package daxz.dev.haulover.Skills.Farming.FarmingTools.FarmingToolHandlers;

import com.jeff_media.customblockdata.CustomBlockData;
import daxz.dev.haulover.Haulover;
import daxz.dev.haulover.Skills.Farming.FarmingTools.WateringCans.AdvancedWateringCan;
import daxz.dev.haulover.Skills.Farming.FarmingTools.WateringCans.BasicWateringCan;
import daxz.dev.haulover.Skills.Farming.FarmingTools.WateringCans.WateringCan;
import daxz.dev.haulover.Utilities.Lib.ItemHelper;
import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.ItemLore;
import io.papermc.paper.event.player.PlayerStopUsingItemEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Farmland;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.Sound;

import java.util.*;


public class WateringCanHandler implements Listener {

    private static final NamespacedKey wateringCanID = new NamespacedKey(Haulover.getInstance(), "haulover-watering_can");
    public static final NamespacedKey wateringCanCapacity = new NamespacedKey(Haulover.getInstance(), "haulover-watering_can-capacity");
    public static final NamespacedKey hauloverItemID = new NamespacedKey(Haulover.getInstance(), "haulover_item");
    private static final NamespacedKey blockWatering = new NamespacedKey(Haulover.getInstance(), "haulover-block-water_capacity");

    private static List<UUID> playersWatering = new ArrayList<>();
    private static List<UUID> wateringRatelimit = new ArrayList<>();


    private final float blockThirst = 20;

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
                    float percentage = (float) (Math.floor(canType.getMaxCapacity() * 0.15));
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

                float wateringAmount = 10f;

                if (updateBlockCapacity(targetBlock, wateringAmount)) updateCanCapacity(item, -wateringAmount);

                timeout++;
            }
        }.runTaskTimer(Haulover.getInstance(), 0L, 10L);



    }

    private boolean updateBlockCapacity(Block block, float amount){
        Block farmBlock = block.getBlockData() instanceof Farmland
                ? block
                : block.getLocation().add(0, -1, 0).getBlock();

        PersistentDataContainer wateringData = new CustomBlockData(farmBlock, Haulover.getInstance());
        float current = wateringData.getOrDefault(blockWatering, PersistentDataType.FLOAT, 0f);




        if (!(farmBlock.getBlockData() instanceof Farmland farm)) return false;

        farm.setMoisture(Math.clamp((int) Math.round((current / blockThirst) * 7), 0, 7));
        farmBlock.setBlockData(farm);
        if (current >= blockThirst) return false;
        wateringData.set(blockWatering, PersistentDataType.FLOAT, current + amount);
        return true;


    }


    private void updateCanCapacity(ItemStack item, float amount) {

        ItemMeta meta = item.getItemMeta();
        WateringCan canType = getWateringCanType(item);

        float current = ItemHelper.getItemPDCOrDefault(item, wateringCanCapacity, PersistentDataType.FLOAT, 0f);
        float clamp = Math.clamp(current + amount, 0f, canType.getMaxCapacity());
        ItemHelper.setItemPDC(item, wateringCanCapacity, PersistentDataType.FLOAT, clamp);
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
            case AdvancedWateringCan.ID -> AdvancedWateringCan.INSTANCE;
            default -> null;
        };
    }


    private void wateringCanLoreUpdate(ItemStack item) {
        ItemLore itemLore = item.getData(DataComponentTypes.LORE);
        if (itemLore == null) return;

        List<Component> lore = new ArrayList<>(itemLore.lines());
        float current = ItemHelper.getItemPDCOrDefault(item, wateringCanCapacity, PersistentDataType.FLOAT, 0f);

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
