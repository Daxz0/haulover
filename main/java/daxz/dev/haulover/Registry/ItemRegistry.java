package daxz.dev.haulover.Registry;


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

    }

    private static void register(String id, Supplier<ItemStack> supplier) {
        REGISTRY.put(id, supplier);
    }

    public static boolean giveItem(Player player, String id) {

        if (REGISTRY.containsKey(id)) {

            player.getInventory().setItem(player.getInventory().getHeldItemSlot(), REGISTRY.get(id).get());
            return true;
        }

        return false;

    }

    public static Map<String, Supplier<ItemStack>> getRegisteredItems() {return REGISTRY;}

}
