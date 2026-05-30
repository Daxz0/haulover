package daxz.dev.haulover.Skills.Farming.FarmingTools.WateringCans;

import daxz.dev.haulover.Registry.HauloverItem;
import org.bukkit.inventory.ShapedRecipe;

public interface WateringCan extends HauloverItem {
    int getMaxCapacity();
    float getSpeed();
    float getCooldownSpeed();
}


