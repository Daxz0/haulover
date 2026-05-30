package daxz.dev.haulover.Registry;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import javax.annotation.Nullable;

public interface HauloverItem {
    String getID();

    ItemStack createItem();
    @Nullable ShapedRecipe getRecipe();
}