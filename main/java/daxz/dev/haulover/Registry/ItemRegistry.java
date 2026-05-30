package daxz.dev.haulover.Registry;


import daxz.dev.haulover.Haulover;
import daxz.dev.haulover.Skills.Farming.Crops.Tomato.TomatoItem;
import daxz.dev.haulover.Skills.Farming.Crops.Tomato.TomatoSeed;
import daxz.dev.haulover.Skills.Farming.FarmingTools.WateringCans.AdvancedWateringCan;
import daxz.dev.haulover.Skills.Farming.FarmingTools.WateringCans.BasicWateringCan;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ItemRegistry {
    private static final Map<String, HauloverItem> REGISTRY = new HashMap<>();
    public static void registerItems() {

        register(BasicWateringCan.INSTANCE);
        register(AdvancedWateringCan.INSTANCE);
        register(TomatoItem.INSTANCE);
        register(TomatoSeed.INSTANCE);

    }

    private static void register(HauloverItem item) {

        REGISTRY.put(item.getID(), item);
        ShapedRecipe recipe = item.getRecipe();

        if (recipe != null) {
            Haulover.getInstance().getServer().addRecipe(recipe);
        }
    }

    public static ItemStack getItem(String id) {
        HauloverItem item = REGISTRY.get(id);
        return item != null ? item.createItem() : null;
    }

    public static boolean giveItem(Player player, String id) {
        HauloverItem item = REGISTRY.get(id);
        if (item != null) {
            player.getInventory().addItem(item.createItem());
            return true;
        }
        return false;
    }

    public static Map<String, HauloverItem> getRegisteredItems() {return REGISTRY;}

}
