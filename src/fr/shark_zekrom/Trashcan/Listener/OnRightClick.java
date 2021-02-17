package fr.shark_zekrom.Trashcan.Listener;

import fr.shark_zekrom.Trashcan.Config;
import fr.shark_zekrom.Trashcan.Main;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;

public class OnRightClick implements Listener {


    @EventHandler
    public void onClick(PlayerInteractEvent event) {

        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        String key = ".";
        File file = new File(Main.getInstance().getDataFolder(), "trashcan.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        ConfigurationSection configsection = config.getConfigurationSection(key);


        if (event.getClickedBlock() != null) {
            Block block = player.getTargetBlock(null, 100);

            long locx = (long) block.getLocation().getX();
            long locy = (long) block.getLocation().getY();
            long locz = (long) block.getLocation().getZ();
            String locworld = block.getLocation().getWorld().getName();

            String loc = config.getString("trashcan." + locworld + "." + locx + "." + locy + "." + locz);

            if (loc != null) {
                int size1 = Config.get().getInt("GUISize");
                int size2 = size1 * 9;
                String name1 = Config.get().getString("GUIName");
                String name2 = name1.replaceAll("&", "§");

                Inventory inventory = Bukkit.createInventory(null,size2, name2);
                player.openInventory(inventory);

            }
        }
    }

}
