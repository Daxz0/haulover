package daxz.dev.haulover.Skills.Farming.FarmingTools.WateringCans;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.Objects;

import static daxz.dev.haulover.Skills.Farming.FarmingTools.WateringCans.BasicWateringCan.hauloverItemID;

public class AdvancedWateringCanRecipe implements Listener {

    @EventHandler
    public void craftAdvancedWateringCan(PrepareItemCraftEvent event) {
        ItemStack center = event.getInventory().getMatrix()[4];
        if (center == null || center.getType() == Material.AIR) return;

        var meta = center.getItemMeta();
        if (meta == null) return;

        var pdc = meta.getPersistentDataContainer();
        if (!BasicWateringCan.ID.equals(pdc.get(hauloverItemID, PersistentDataType.STRING))) return;

        for (int i = 0; i < 9; i++) {
            if (i == 4) continue;
            ItemStack slot = event.getInventory().getMatrix()[i];
            if (slot == null || slot.getType() != Material.GOLD_INGOT) return;
        }

        event.getInventory().setResult(AdvancedWateringCan.INSTANCE.createItem());
    }
    
}
