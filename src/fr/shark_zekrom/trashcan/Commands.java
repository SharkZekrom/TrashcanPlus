package fr.shark_zekrom.trashcan;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.command.*;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Commands implements CommandExecutor , TabExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {

        if (cmd.getName().equalsIgnoreCase("trash")) {
            Player player = (Player) sender;

            if (player.hasPermission("trashcan.open")) {
                int size1 = Config.get().getInt("GUISize");
                int size2 = size1 * 9;
                String name1 = Config.get().getString("GUIName");
                String name2 = name1.replaceAll("&", "§");
                Boolean song = Config.get().getBoolean("GUISong");

                Inventory inventory = Bukkit.createInventory(null, size2, name2);
                player.openInventory(inventory);
                if (song.equals(true)) {
                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 8.0F);

                }
            }
        }

        if (cmd.getName().equalsIgnoreCase("trashcan")) {
            if (args.length > 0) {
                if (sender instanceof ConsoleCommandSender) {
                    if (args.length == 2) {
                        if (args[0].equalsIgnoreCase("open")) {

                            Player player = Bukkit.getPlayer(args[1]);

                            int size1 = Config.get().getInt("GUISize");
                            int size2 = size1 * 9;
                            String name1 = Config.get().getString("GUIName");
                            String name2 = name1.replaceAll("&", "§");
                            Boolean song = Config.get().getBoolean("GUISong");


                            Inventory inventory = Bukkit.createInventory(null, size2, name2);
                            player.openInventory(inventory);
                            if (song.equals(true)) {
                                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 8.0F);
                            }

                        }
                    }
                }
                if (sender instanceof Player) {
                    Player player = (Player) sender;

                    if (args[0].equalsIgnoreCase("open")) {
                        if (player.hasPermission("trashcan.open")) {
                            int size1 = Config.get().getInt("GUISize");
                            int size2 = size1 * 9;
                            String name1 = Config.get().getString("GUIName");
                            String name2 = name1.replaceAll("&", "§");
                            Boolean song = Config.get().getBoolean("GUISong");

                            Inventory inventory = Bukkit.createInventory(null, size2, name2);
                            player.openInventory(inventory);
                            if (song.equals(true)) {
                                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 8.0F);

                            }
                        }
                    }
                    if (args[0].equalsIgnoreCase("help")) {
                        if (sender.hasPermission("trashcan.admin")) {

                            player.sendMessage(ChatColor.AQUA + "==========[Trashcan+]==========");
                            player.sendMessage(ChatColor.AQUA + "");
                            player.sendMessage(ChatColor.AQUA + "Commands:");
                            player.sendMessage(ChatColor.AQUA + "");
                            player.sendMessage(ChatColor.AQUA + "/trashcan help §8» §eSee all commands");
                            player.sendMessage(ChatColor.AQUA + "/trashcan create <block/entity> §8» §eCreate a trashcan");
                            player.sendMessage(ChatColor.AQUA + "/trashcan delete <block/entity> §8» §eDelete a trashcan");
                            player.sendMessage(ChatColor.AQUA + "/trashcan open [player] §8» §eOpen a trashcan for you or for a player");
                            player.sendMessage(ChatColor.AQUA + "/trashcan reload §8» §eReload the config");
                        }
                    }
                    if (args[0].equalsIgnoreCase("create")) {
                        if (sender.hasPermission("trashcan.admin")) {

                            if (args.length > 1) {
                                if (args[1].equalsIgnoreCase("block")) {
                                    String key = ".";
                                    File file = new File(Main.getInstance().getDataFolder(), "trashcan.yml");
                                    YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
                                    ConfigurationSection configsection = config.getConfigurationSection(key);


                                    Block block = player.getTargetBlock(null, 100);

                                    long locx = (long) block.getLocation().getX();
                                    long locy = (long) block.getLocation().getY();
                                    long locz = (long) block.getLocation().getZ();
                                    String locworld = block.getLocation().getWorld().getName();

                                    String loc = config.getString("trashcan." + locworld + "." + locx + "." + locy + "." + locz);

                                    if (loc != null) {
                                        player.sendMessage(ChatColor.AQUA + "[Trashcan+]" + ChatColor.YELLOW + " There is already a trashcan at this coordinate.");

                                    } else {

                                        config.set("trashcan." + locworld + "." + locx + "." + locy + "." + locz, true);
                                        player.sendMessage(ChatColor.AQUA + "[Trashcan+]" + ChatColor.YELLOW + " Trash can with coordinate " + locx + ", " + locy + ", " + locz + " set up.");

                                        try {
                                            config.save(file);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                                if (args[1].equalsIgnoreCase("entity")) {

                                    String key = ".";
                                    File file = new File(Main.getInstance().getDataFolder(), "trashcanentity.yml");
                                    YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
                                    ConfigurationSection configsection = config.getConfigurationSection(key);

                                    for (Entity entity : player.getNearbyEntities(0.5D, 0.5D, 0.5D)) {
                                        if (entity instanceof ArmorStand) {
                                            Double locx = entity.getLocation().getX();
                                            Double locy = entity.getLocation().getY();
                                            Double locz = entity.getLocation().getZ();
                                            String locworld = entity.getLocation().getWorld().getName();

                                            String loc = config.getString("trashcan." + locworld + "." + locx + "." + locy + "." + locz);

                                            if (loc != null) {
                                                player.sendMessage(ChatColor.AQUA + "[Trashcan+]" + ChatColor.YELLOW + " There is already a trashcan at this coordinate.");

                                            } else {

                                                config.set("trashcan." + locworld + "." + locx + "." + locy + "." + locz, true);
                                                player.sendMessage(ChatColor.AQUA + "[Trashcan+]" + ChatColor.YELLOW + " Trash can entity with coordinate " + locx + ", " + locy + ", " + locz + " set up.");

                                                try {
                                                    config.save(file);
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }
                                    }
                                }
                            } else {
                                player.sendMessage(ChatColor.AQUA + "==========[Trashcan+]==========");
                                player.sendMessage(ChatColor.AQUA + "");
                                player.sendMessage(ChatColor.AQUA + "/trashcan create <block/entity> §8» §eCreate a trashcan");
                            }
                        }
                    }

                    if (args[0].equalsIgnoreCase("delete")) {
                        if (sender.hasPermission("trashcan.admin")) {

                            if (args.length > 1) {
                                if (args[1].equalsIgnoreCase("block")) {
                                    String key = ".";
                                    File file = new File(Main.getInstance().getDataFolder(), "trashcan.yml");
                                    YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
                                    ConfigurationSection configsection = config.getConfigurationSection(key);

                                    Block block = player.getTargetBlock(null, 100);

                                    long locx = (long) block.getLocation().getX();
                                    long locy = (long) block.getLocation().getY();
                                    long locz = (long) block.getLocation().getZ();
                                    String locworld = block.getLocation().getWorld().getName();

                                    String loc = config.getString("trashcan." + locworld + "." + locx + "." + locy + "." + locz);

                                    if (loc != null) {
                                        config.set("trashcan." + locworld + "." + locx + "." + locy + "." + locz, null);
                                        player.sendMessage(ChatColor.AQUA + "[Trashcan+]" + ChatColor.YELLOW + " Trash can with coordinate " + locx + ", " + locy + ", " + locz + " deleted.");

                                        try {
                                            config.save(file);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }

                                    } else {

                                        player.sendMessage(ChatColor.AQUA + "[Trashcan+]" + ChatColor.YELLOW + " No trash can at this coordinate.");
                                    }
                                }
                                if (args[1].equalsIgnoreCase("entity")) {
                                    String key = ".";
                                    File file = new File(Main.getInstance().getDataFolder(), "trashcanentity.yml");
                                    YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
                                    ConfigurationSection configsection = config.getConfigurationSection(key);

                                    for (Entity entity : player.getNearbyEntities(0.5D, 0.5D, 0.5D)) {
                                        if (entity instanceof ArmorStand) {
                                            Double locx = entity.getLocation().getX();
                                            Double locy = entity.getLocation().getY();
                                            Double locz = entity.getLocation().getZ();
                                            String locworld = entity.getLocation().getWorld().getName();

                                            String loc = config.getString("trashcan." + locworld + "." + locx + "." + locy + "." + locz);

                                            if (loc != null) {
                                                config.set("trashcan." + locworld + "." + locx + "." + locy + "." + locz, null);
                                                player.sendMessage(ChatColor.AQUA + "[Trashcan+]" + ChatColor.YELLOW + " Trash can entity with coordinate " + locx + ", " + locy + ", " + locz + " deleted.");

                                                try {
                                                    config.save(file);
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }
                                            } else {
                                                player.sendMessage(ChatColor.AQUA + "[Trashcan+]" + ChatColor.YELLOW + " No trash can entity at this coordinate.");
                                            }
                                        }
                                    }
                                }
                            } else {
                                player.sendMessage(ChatColor.AQUA + "==========[Trashcan+]==========");
                                player.sendMessage(ChatColor.AQUA + "");
                                player.sendMessage(ChatColor.AQUA + "/trashcan delete <block/entity> §8» §eCreate a trashcan");
                            }
                        }
                    }
                    if (args[0].equalsIgnoreCase("open")) {
                        if (sender.hasPermission("trashcan.admin")) {

                            if (args.length == 2) {
                                Player player1 = Bukkit.getPlayer(args[1]);

                                if (player1.isOnline()) {
                                    int size1 = Config.get().getInt("GUISize");
                                    int size2 = size1 * 9;
                                    String name1 = Config.get().getString("GUIName");
                                    String name2 = name1.replaceAll("&", "§");
                                    Boolean song = Config.get().getBoolean("GUISong");


                                    Inventory inventory = Bukkit.createInventory(null, size2, name2);
                                    player1.openInventory(inventory);
                                    if (song.equals(true)) {
                                        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 8.0F);
                                    }
                                }
                            }
                        }
                    }
                    if (args[0].equalsIgnoreCase("reload")) {
                        if (sender.hasPermission("trashcan.admin")) {

                            Config.reload();
                            player.sendMessage(ChatColor.AQUA + "[Trashcan+]" + ChatColor.YELLOW + " Plugin reloaded.");
                        }
                    }

                }
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            List<String> arguments = new ArrayList<>();
            arguments.add("create");
            arguments.add("delete");
            arguments.add("open");
            arguments.add("reload");
            arguments.add("help");

            return arguments;
        }
        if (args[1].equals("create") || args[1].equals("delete")) {
            List<String> arguments = new ArrayList<>();
            arguments.add("block");
            arguments.add("entity");

            return arguments;
        }
        return null;

    }
}