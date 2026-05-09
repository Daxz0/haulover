package daxz.dev.haulover.Registry;

import daxz.dev.haulover.Haulover;
import daxz.dev.haulover.Skills.Farming.OverrideFarmland;
import org.bukkit.plugin.PluginManager;

public class EventRegister {

    public static void registerEvents() {
        PluginManager pm = Haulover.getInstance().getServer().getPluginManager();
        pm.registerEvents(new OverrideFarmland(), Haulover.getInstance());

    }
}
