package fr.shark_zekrom.Trashcan;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;

public class Event implements Listener {


    @EventHandler
    public void onBlockClick(PlayerInteractEvent event) {

        Player player = event.getPlayer();

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

                Inventory inventory = Bukkit.createInventory(null, size2, name2);
                player.openInventory(inventory);

            }
        }
    }

    @EventHandler
    public void onBlockClick(PlayerInteractAtEntityEvent event) {
        Player player = event.getPlayer();
        if (event.getRightClicked() instanceof ArmorStand) {
            String key = ".";
            File file = new File(Main.getInstance().getDataFolder(), "trashcanentity.yml");
            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
            ConfigurationSection configsection = config.getConfigurationSection(key);


            Double locx = event.getRightClicked().getLocation().getX();
            Double locy = event.getRightClicked().getLocation().getY();
            Double locz = event.getRightClicked().getLocation().getZ();
            String locworld = event.getRightClicked().getLocation().getWorld().getName();

            String loc = config.getString("trashcan." + locworld + "." + locx + "." + locy + "." + locz);
            if (loc != null) {
                int size1 = Config.get().getInt("GUISize");
                int size2 = size1 * 9;
                String name1 = Config.get().getString("GUIName");
                String name2 = name1.replaceAll("&", "§");

                Boolean song = Config.get().getBoolean("GUISong");

                Inventory inventory = Bukkit.createInventory(null, size2, name2);
                if (song.equals(true)) {
                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 8.0F);
                }

                player.openInventory(inventory);
            }
        }
    }





    @EventHandler
    private void onInventoryClick(InventoryClickEvent event) throws IOException {
        Inventory inventory = event.getInventory();
        Player player = (Player) event.getWhoClicked();
        int slot = event.getSlot();
        boolean TrashInInventory = Config.get().getBoolean("TrashInInventory");
        if (TrashInInventory) {
            int TrashInInventorySlot = Config.get().getInt("TrashInInventorySlot");

            if (slot == TrashInInventorySlot) {
                event.setCancelled(true);
                player.setItemOnCursor(new ItemStack(Material.AIR));
            }
        }
    }



    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        boolean TrashInInventory = Config.get().getBoolean("TrashInInventory");
        if (TrashInInventory) {
            int TrashInInventorySlot = Config.get().getInt("TrashInInventorySlot");
            String TrashInInventoryName = Config.get().getString("TrashInInventoryName");
            String TrashInInventoryName2 =  TrashInInventoryName.replaceAll("&", "§");

            ItemStack hopper = new ItemStack(Material.HOPPER, 1);
            ItemMeta hp = hopper.getItemMeta();
            hp.setDisplayName(TrashInInventoryName2);
            hopper.setItemMeta(hp);

            player.getInventory().setItem(TrashInInventorySlot, hopper);
        }

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        boolean TrashInInventory = Config.get().getBoolean("TrashInInventory");
        if (TrashInInventory) {
            int TrashInInventorySlot = Config.get().getInt("TrashInInventorySlot");
            player.getInventory().setItem(TrashInInventorySlot, new ItemStack(Material.AIR));
        }

    }
}