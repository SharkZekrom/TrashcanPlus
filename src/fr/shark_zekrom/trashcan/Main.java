package fr.shark_zekrom.trashcan;

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
        pm.registerEvents(new Event(), this);

        getConfig().options().copyDefaults();


        Config.setup();

        Config.get().addDefault("GUISize", 1);
        Config.get().addDefault("GUIName", "&7Trashcan");
        Config.get().addDefault("GUISong", true);
        Config.get().addDefault("TrashInInventory", false);
        Config.get().addDefault("TrashInInventorySlot", 35);
        Config.get().addDefault("TrashInInventoryName", "&8test");
        Config.get().options().copyDefaults(true);
        Config.save();

    }


    @Override
    public void onDisable() {
        Bukkit.getLogger().info("Plugin disabled !");
    }
}