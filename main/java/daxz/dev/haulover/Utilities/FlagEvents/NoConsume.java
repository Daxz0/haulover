package daxz.dev.haulover.Utilities.FlagEvents;

import daxz.dev.haulover.Haulover;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class NoConsume implements Listener {

    private static final NamespacedKey stopConsume = new NamespacedKey(Haulover.getInstance(), "flag-no_consume");

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerConsumes(PlayerItemConsumeEvent event) {
        ItemStack item  = event.getItem();
        assert item != null;
        if (Boolean.TRUE.equals(item.getItemMeta().getPersistentDataContainer().get(stopConsume, PersistentDataType.BOOLEAN))) event.setCancelled(true);
    }


}
