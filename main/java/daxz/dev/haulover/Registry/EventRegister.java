package daxz.dev.haulover.Registry;

import daxz.dev.haulover.Haulover;
import daxz.dev.haulover.Skills.Farming.FarmingTools.FarmingToolHandlers.WateringCanHandler;
import daxz.dev.haulover.Skills.Farming.OverrideFarmland;
import daxz.dev.haulover.Utilities.FlagEvents.NoConsume;
import daxz.dev.haulover.Utilities.FlagEvents.NoInteractions;
import org.bukkit.plugin.PluginManager;

public class EventRegister {

    public static void registerEvents() {
        PluginManager pm = Haulover.getInstance().getServer().getPluginManager();
        pm.registerEvents(new OverrideFarmland(), Haulover.getInstance());
        pm.registerEvents(new WateringCanHandler(), Haulover.getInstance());
        pm.registerEvents(new NoInteractions(), Haulover.getInstance());
        pm.registerEvents(new NoConsume(), Haulover.getInstance());

    }
}
