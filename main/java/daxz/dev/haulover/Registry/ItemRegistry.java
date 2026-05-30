package daxz.dev.haulover.Registry;


import daxz.dev.haulover.Skills.Farming.Crops.Tomato.TomatoItem;
import daxz.dev.haulover.Skills.Farming.Crops.Tomato.TomatoSeed;
import daxz.dev.haulover.Skills.Farming.FarmingTools.WateringCans.AdvancedWateringCan;
import daxz.dev.haulover.Skills.Farming.FarmingTools.WateringCans.BasicWateringCan;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ItemRegistry {
    private static final Map<String, Supplier<ItemStack>> REGISTRY = new HashMap<>();
    public static void registerItems() {

        register(BasicWateringCan.ID, () -> new BasicWateringCan().createItem());
        register(AdvancedWateringCan.ID, () -> new AdvancedWateringCan().createItem());
        register(TomatoItem.ID, () -> new TomatoItem().createItem());
        register(TomatoSeed.ID, () -> new TomatoSeed().createItem());

    }

    private static void register(String id, Supplier<ItemStack> supplier) {
        REGISTRY.put(id, supplier);
    }

    public static ItemStack getItem(String id) {
        if (REGISTRY.containsKey(id)) {
            return REGISTRY.get(id).get();
        }
        return null;
    }

    public static boolean giveItem(Player player, String id) {

        if (REGISTRY.containsKey(id)) {

            player.getInventory().addItem(REGISTRY.get(id).get());
            return true;
        }

        return false;

    }

    public static Map<String, Supplier<ItemStack>> getRegisteredItems() {return REGISTRY;}

}
