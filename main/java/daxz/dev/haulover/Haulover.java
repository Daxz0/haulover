package daxz.dev.haulover;

import daxz.dev.haulover.Commands.HauloverCommandHandler;
import daxz.dev.haulover.Registry.ItemRegistry;
import daxz.dev.haulover.Registry.EventRegister;
import daxz.dev.haulover.Skills.Farming.OverrideFarmland;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Haulover extends JavaPlugin {

    public static Haulover instance;

    @Override
    public void onEnable() {
        instance = this;

        EventRegister.registerEvents();
        ItemRegistry.registerItems();

        this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, commands -> {
            commands.registrar().register(HauloverCommandHandler.haulover());
        });

    }

    @Override
    public void onDisable() {
    }



    public static Haulover getInstance() {return instance;}
}
