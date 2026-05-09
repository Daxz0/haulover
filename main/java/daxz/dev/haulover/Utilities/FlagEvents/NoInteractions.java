package daxz.dev.haulover.Utilities.FlagEvents;

import daxz.dev.haulover.Haulover;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class NoInteractions implements Listener {

    private static final NamespacedKey stopInteraction = new NamespacedKey(Haulover.getInstance(), "flag:no_interactions");

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerInteract(PlayerInteractEvent event) {
        ItemStack item  = event.getItem();
        assert item != null;
        if (Boolean.TRUE.equals(item.getItemMeta().getPersistentDataContainer().get(stopInteraction, PersistentDataType.BOOLEAN))) event.setCancelled(true);
    }


}
