package daxz.dev.haulover;

import daxz.dev.haulover.Skills.Farming.OverrideFarmland;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Haulover extends JavaPlugin {

    public static Haulover instance;

    @Override
    public void onEnable() {
        instance = this;

        registerEvents();

    }

    @Override
    public void onDisable() {
    }



    public static Haulover getInstance() {return instance;}
}
