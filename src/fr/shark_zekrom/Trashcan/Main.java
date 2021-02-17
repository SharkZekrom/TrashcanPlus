package fr.shark_zekrom.Trashcan;

import fr.shark_zekrom.Trashcan.Commands.Commands;
import fr.shark_zekrom.Trashcan.Listener.OnRightClick;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {


    private static Main instance;

    public static Main getInstance() {
        return instance;
    }


    @Override
    public void onEnable() {
        instance = this;

        Bukkit.getLogger().info("Plugin enabled !");
        PluginManager pm = getServer().getPluginManager();

        this.getCommand("trashcan").setExecutor(new Commands());
        pm.registerEvents(new OnRightClick(), this);

        getConfig().options().copyDefaults();


        Config.setup();

        Config.get().addDefault("GUISize", 1);
        Config.get().addDefault("GUIName", "&7Trashcan");
        Config.get().options().copyDefaults(true);
        Config.save();

    }


    @Override
    public void onDisable() {
        Bukkit.getLogger().info("Plugin disabled !");
    }
}